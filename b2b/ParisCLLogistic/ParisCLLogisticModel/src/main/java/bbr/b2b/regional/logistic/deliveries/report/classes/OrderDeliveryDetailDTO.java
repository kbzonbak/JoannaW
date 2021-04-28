package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class OrderDeliveryDetailDTO implements Serializable{

	private double pendingunits;
	private double availableunits;
	private double receivedunits;
	private Long deliveryid;
	private Long itemid;
	private Long locationid;
	private Long orderid;
	private Long atcid;
	private Long departmentid;
	private Long flowtypeid;
	
	public double getPendingunits() {
		return pendingunits;
	}
	public void setPendingunits(double pendingunits) {
		this.pendingunits = pendingunits;
	}
	public double getAvailableunits() {
		return availableunits;
	}
	public void setAvailableunits(double availableunits) {
		this.availableunits = availableunits;
	}
	public double getReceivedunits() {
		return receivedunits;
	}
	public void setReceivedunits(double receivedunits) {
		this.receivedunits = receivedunits;
	}
	public Long getDeliveryid() {
		return deliveryid;
	}
	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}
	public Long getItemid() {
		return itemid;
	}
	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}
	public Long getLocationid() {
		return locationid;
	}
	public void setLocationid(Long locationid) {
		this.locationid = locationid;
	}
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Long getAtcid() {
		return atcid;
	}
	public void setAtcid(Long atcid) {
		this.atcid = atcid;
	}
	public Long getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(Long departmentid) {
		this.departmentid = departmentid;
	}
	public Long getFlowtypeid() {
		return flowtypeid;
	}
	public void setFlowtypeid(Long flowtypeid) {
		this.flowtypeid = flowtypeid;
	}
	
}
