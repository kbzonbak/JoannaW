package bbr.b2b.regional.logistic.buyorders.report.classes;

public class OrderDetailFillRateDTO {

	private long orderid;

	private long itemid;

	private double receivedunits;

	private double needunits;

	private long vendorid;

	public long getOrderid() {
		return orderid;
	}

	public void setOrderid(long orderid) {
		this.orderid = orderid;
	}

	public long getItemid() {
		return itemid;
	}

	public void setItemid(long itemid) {
		this.itemid = itemid;
	}

	public double getReceivedunits() {
		return receivedunits;
	}

	public void setReceivedunits(double receivedunits) {
		this.receivedunits = receivedunits;
	}

	public double getNeedunits() {
		return needunits;
	}

	public void setNeedunits(double needunits) {
		this.needunits = needunits;
	}

	public long getVendorid() {
		return vendorid;
	}

	public void setVendorid(long vendorid) {
		this.vendorid = vendorid;
	}

}
