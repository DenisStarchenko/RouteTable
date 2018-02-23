package com.bmstu.route.table;

import com.bmstu.route.table.model.RouteTableChangeEvent;

/**
 *
 * Instances of this class catches and processes route table change events.
 *
 * @author Starchenko
 *
 */
public interface IRouteTableListener {

	/**
	 *
	 * Event fires when route table changes.
	 *
	 * @param event - route table change event. Can't be <code>null</code>.
	 */
	void routeTableChanged(RouteTableChangeEvent event);

}
