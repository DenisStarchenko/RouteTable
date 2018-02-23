package com.bmstu.route.table.model;

import java.util.Collection;

/**
 *
 * Instance of this class represents route table change event.
 * Contains deleted and added records.
 *
 * @author Starchenko
 *
 */
public class RouteTableChangeEvent {

	private RouteTable routeTable;
	private Collection<RouteTableRecord> addedRecords;
	private Collection<RouteTableRecord> deletedRecords;

	/**
	 *
	 * Constructor.
	 *
	 * @param routeTable - changed route table. Can't be <code>null</code>.
	 * @param addedRecords - added records. Can't be <code>null</code>.
	 * @param deletedRecords - deleted records. Can't be <code>null</code>.
	 */
	public RouteTableChangeEvent(RouteTable routeTable, Collection<RouteTableRecord> addedRecords,
									Collection<RouteTableRecord> deletedRecords) {
		this.routeTable = routeTable;
		this.addedRecords = addedRecords;
		this.deletedRecords = deletedRecords;
	}

	/**
	 *
	 * Returns changed route table.
	 *
	 * @return changed route table. Can't return <code>null</code>.
	 */
	public RouteTable getRouteTable() {
		return routeTable;
	}

	/**
	 *
	 * Returns added records.
	 *
	 * @return added records. Can't return <code>null</code>.
	 */
	public Collection<RouteTableRecord> getAddedRecords() {
		return addedRecords;
	}

	/**
	 *
	 * Returns deleted records.
	 *
	 * @return deleted records. Can't return <code>null</code>.
	 */
	public Collection<RouteTableRecord> getDeletedRecords() {
		return deletedRecords;
	}
}
