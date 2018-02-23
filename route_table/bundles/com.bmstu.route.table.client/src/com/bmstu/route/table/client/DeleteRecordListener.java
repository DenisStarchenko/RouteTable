package com.bmstu.route.table.client;

import org.osgi.service.component.annotations.Component;

import com.bmstu.route.table.IRouteTableListener;
import com.bmstu.route.table.model.RouteTableChangeEvent;

/**
 *
 * Prints deleted route table records.
 *
 * @author Starchenko
 *
 */
@Component
public class DeleteRecordListener implements IRouteTableListener {

	@Override
	public void routeTableChanged(RouteTableChangeEvent event) {
		if (!event.getDeletedRecords().isEmpty()) {
			System.out.println("Deleted records: ");
			event.getDeletedRecords().stream().forEach(record -> System.out.println(record));
			System.out.println("");
		}
	}

}
