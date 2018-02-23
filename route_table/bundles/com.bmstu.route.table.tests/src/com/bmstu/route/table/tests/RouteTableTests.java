package com.bmstu.route.table.tests;

import static org.junit.Assert.assertNotNull;

import java.util.Collections;

import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

import com.bmstu.route.table.IRouteTableListener;
import com.bmstu.route.table.IRouteTableService;
import com.bmstu.route.table.model.RouteTable;
import com.bmstu.route.table.model.RouteTableChangeEvent;

/**
 *
 * Tests for route table.
 *
 * @author Starchenko
 *
 */public class RouteTableTests {

	@Test
	public void serviceTest() {
		IRouteTableService service = getService(IRouteTableService.class);
		assertNotNull("Route table service is null!", service);

		service.getRouteTable();
	}

	@Test
	public void listenersTest() {
		IRouteTableListener listener = getService(IRouteTableListener.class);
		assertNotNull("Route table listener is null!", listener);

		listener.routeTableChanged(new RouteTableChangeEvent(new RouteTable(Collections.emptyList()), Collections.emptyList(), Collections.emptyList()));
	}

	static <T> T getService(Class<T> clazz) {
		Bundle bundle = FrameworkUtil.getBundle(RouteTableTests.class);
		if (bundle != null) {
			ServiceTracker<T, T> st = new ServiceTracker<T, T>(bundle.getBundleContext(), clazz, null);
			st.open();

			try {
				// give the runtime some time to startup
				return st.waitForService(500);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
