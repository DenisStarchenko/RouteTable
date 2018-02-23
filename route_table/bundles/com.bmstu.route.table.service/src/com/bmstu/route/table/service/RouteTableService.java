package com.bmstu.route.table.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import com.bmstu.route.table.IRouteTableListener;
import com.bmstu.route.table.IRouteTableService;
import com.bmstu.route.table.model.RouteTable;
import com.bmstu.route.table.model.RouteTableChangeEvent;
import com.bmstu.route.table.model.RouteTableRecord;

/**
 *
 * Implementation of {@link IRouteTableService}.
 *
 * @author Starchenko
 *
 */
@Component(immediate = true)
public class RouteTableService implements IRouteTableService {

	private static final int AWAIT_TERMINATION_TIME = 10;
	private static final String IP_V_4_ADDRESS_REGEX = "(\\d+\\.){3}\\d+";

	private RouteTable routeTable;
	private Collection<IRouteTableListener> listeners;
	private ExecutorService executorService;
	private boolean isActive;

	/**
	 * Constructor.
	 */
	public RouteTableService() {
		listeners = new HashSet<>();
	}

	@Activate
	public void activate(Map<String, Object> properties) {
		isActive = true;
		routeTable = loadRouteTable();

		executorService = Executors.newFixedThreadPool(1);
		executorService.execute(new RouteTableListener());
	}

	@Deactivate
	public void deactivate() {
		try {
			isActive = false;
			executorService.awaitTermination(AWAIT_TERMINATION_TIME, TimeUnit.SECONDS);
			listeners.clear();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void bindRouteTableListener(IRouteTableListener listener) {
		listeners.add(listener);
	}

	public void unbindRouteTableListener(IRouteTableListener listener) {
		listeners.remove(listener);
	}
	@Override
	public RouteTable getRouteTable() {
		return routeTable;
	}

	private RouteTable loadRouteTable() {
		try {
			Process process = Runtime.getRuntime().exec("route print");
			process.waitFor();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = reader.readLine();
			String result = "";
			while (line != null) {
				result += line + "\n";

				line = reader.readLine();
			}

			result = result.substring(result.indexOf("IPv4"), result.indexOf("IPv6"));
			return createRouteTable(result);
		}
		catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		return null;
	}

	private RouteTable createRouteTable(String result) {
		Collection<RouteTableRecord> records = new ArrayList<>();

		Pattern pattern = Pattern.compile("\\s+(" + IP_V_4_ADDRESS_REGEX + "\\s+){2}.+" + IP_V_4_ADDRESS_REGEX + "\\s+\\d+");
		Matcher matcher = pattern.matcher(result);
		while (matcher.find()) {
			records.add(createRecord(matcher.group()));
		}

		return new RouteTable(records);
	}

	private RouteTableRecord createRecord(String group) {
		String[] parts = group.split("\\s+");

		return new RouteTableRecord(parts[1], parts[2], parts[3], parts[4], Integer.valueOf(parts[5]));
	}

	private class RouteTableListener implements Runnable {
		private static final int SLEEP_TIME = 300;

		@Override
		public void run() {
			while (isActive) {
				RouteTable routeTable = loadRouteTable();

				RouteTableChangeEvent changeEvent = createChangeEvent(routeTable);
				if (changeEvent != null) {
					sendEvent(changeEvent);
					RouteTableService.this.routeTable = routeTable;
				}
				try {
					Thread.sleep(SLEEP_TIME);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		private RouteTableChangeEvent createChangeEvent(RouteTable newRouteTable) {
			Collection<RouteTableRecord> addedRecords = new ArrayList<>();
			newRouteTable.getRecords().stream().forEach(newRecord -> {
				if (!routeTable.getRecords().contains(newRecord)) {
					addedRecords.add(newRecord);
				}
			});

			Collection<RouteTableRecord> deletedRecords = new ArrayList<>();
			routeTable.getRecords().stream().forEach(oldRecord -> {
				if (!newRouteTable.getRecords().contains(oldRecord)) {
					deletedRecords.add(oldRecord);
				}
			});

			return (!addedRecords.isEmpty() || !deletedRecords.isEmpty())
					? new RouteTableChangeEvent(newRouteTable, addedRecords, deletedRecords) : null;
		}

		private void sendEvent(RouteTableChangeEvent changeEvent) {
			listeners.stream().forEach(listener -> listener.routeTableChanged(changeEvent));
		}

	}
}
