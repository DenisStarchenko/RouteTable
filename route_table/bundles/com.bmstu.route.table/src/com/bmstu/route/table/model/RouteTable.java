package com.bmstu.route.table.model;

import java.util.Collection;
import java.util.Objects;

/**
 *
 * Instance of this class represents route table.
 *
 * @author Starchenko
 *
 */
public class RouteTable {

	private Collection<RouteTableRecord> records;

	/**
	 *
	 * Constructor.
	 *
	 * @param records - route table records. Can't be <code>null</code>.
	 */
	public RouteTable(Collection<RouteTableRecord> records) {
		this.records = records;
	}

	/**
	 *
	 * Returns route table records.
	 *
	 * @return route table records. Can't return <code>null</code>.
	 */
	public Collection<RouteTableRecord> getRecords() {
		return records;
	}

	@Override
	public String toString() {
		String result = "Route table: \n";
		for (RouteTableRecord record : getRecords())
		{
			result += "\t" + record.toString() + "\n";
		}

		return result;
	}

	@Override
	public int hashCode() {
		return Objects.hash(records);
	}

	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (object == this) {
			return true;
		}

		return object instanceof RouteTable && ((RouteTable)object).getRecords().equals(getRecords());
	}
}
