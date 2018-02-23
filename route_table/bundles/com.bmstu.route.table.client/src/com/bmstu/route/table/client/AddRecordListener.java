package com.bmstu.route.table.client;

import org.osgi.service.component.annotations.Component;

import com.bmstu.route.table.IRouteTableListener;
import com.bmstu.route.table.model.RouteTableChangeEvent;

/**
 *
 * Prints added route table records.
 *
 * @author Starchenko
 *
 */
@Component
public class AddRecordListener implements IRouteTableListener {

	@Override
	public void routeTableChanged(RouteTableChangeEvent event) {
		if (!event.getAddedRecords().isEmpty()) {
			System.out.println("Added records: ");
			event.getAddedRecords().stream().forEach(record -> System.out.println(record));
			System.out.println("");
		}

	}

}
