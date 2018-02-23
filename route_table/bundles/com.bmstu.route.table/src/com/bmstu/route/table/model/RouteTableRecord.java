package com.bmstu.route.table.model;

import java.util.Objects;

/**
 *
 * Instance of this class represents route table record.
 *
 * @author Starchenko
 *
 */
public class RouteTableRecord {

	private String webAddress;
	private String webMask;
	private String gateAddress;
	private String interfaze;
	private int metric;

	/**
	 *
	 * Constructor.
	 *
	 * @param webAddress - record web address. Can't be <code>null</code>.
	 * @param webMask - record web mask. Can't be <code>null</code>.
	 * @param gateAddress - record gate address. Can't be <code>null</code>.
	 * @param interfaze - record interface. Can't be <code>null</code>.
	 * @param metric - record metric.
	 */
	public RouteTableRecord(String webAddress, String webMask, String gateAddress, String interfaze, int metric) {
		this.webAddress = webAddress;
		this.webMask = webMask;
		this.gateAddress = gateAddress;
		this.interfaze = interfaze;
		this.metric = metric;
	}

	/**
	 *
	 * Returns web address.
	 *
	 * @return web address. Can't return <code>null</code>.
	 */
	public String getWebAddress() {
		return webAddress;
	}

	/**
	 *
	 * Returns web mask.
	 *
	 * @return web mask. Can't return <code>null</code>.
	 */
	public String getWebMask() {
		return webMask;
	}

	/**
	 *
	 * Returns gate address.
	 *
	 * @return gate address. Can't return <code>null</code>.
	 */
	public String getGateAddress() {
		return gateAddress;
	}

	/**
	 *
	 * Returns interface.
	 *
	 * @return interface. Can't return <code>null</code>.
	 */
	public String getInterface() {
		return interfaze;
	}

	/**
	 *
	 * Returns metric.
	 *
	 * @return metric.
	 */
	public int getMetric() {
		return metric;
	}

	@Override
	public String toString() {
		return String.format("Network Destination: %-15s Netmask: %-15s Gateway: %-15s Interface: %-15s Metric: %4s", webAddress, webMask, gateAddress, interfaze, metric);
	}

	@Override
	public int hashCode() {
		return Objects.hash(webAddress, webMask, gateAddress, interfaze, metric);
	}

	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (object == this) {

			return true;
		}

		if (object instanceof RouteTableRecord) {
			RouteTableRecord otherRecord = (RouteTableRecord)object;

			return getWebAddress().equals(otherRecord.getWebAddress()) && getWebMask().equals(otherRecord.getWebMask()) && getGateAddress().equals(otherRecord.getGateAddress()) && getInterface().equals(otherRecord.getInterface()) && getMetric() == otherRecord.getMetric();
		}
		return false;
	}
}
