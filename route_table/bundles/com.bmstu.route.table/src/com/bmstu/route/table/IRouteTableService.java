package com.bmstu.route.table;

import com.bmstu.route.table.model.RouteTable;

/**
 *
 * Route table service.
 * Listens route table and sends change events to listeners.
 *
 * @author Starchenko
 *
 */
public interface IRouteTableService {

	/**
	 *
	 * Returns current system route table.
	 *
	 * @return current system route table. Can't return <code>null</code>.
	 */
	RouteTable getRouteTable();

}
