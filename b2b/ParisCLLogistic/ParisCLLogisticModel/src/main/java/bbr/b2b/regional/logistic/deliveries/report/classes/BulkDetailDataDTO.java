package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class BulkDetailDataDTO implements Serializable {

	private Long orderid;
	private Long deliveryid;
	private Long locationid;
	private Long itemid;
	private Long atcid;
	private Long departmentid;
	private Long flowtypeid;
	private Double units;
	private Integer bulks;
	
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Long getDeliveryid() {
		return deliveryid;
	}
	public void setDeliveryid(Long deliveryid) {
		this.deliveryid = deliveryid;
	}
	public Long getLocationid() {
		return locationid;
	}
	public void setLocationid(Long locationid) {
		this.locationid = locationid;
	}
	public Long getItemid() {
		return itemid;
	}
	public void setItemid(Long itemid) {
		this.itemid = itemid;
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
	public Double getUnits() {
		return units;
	}
	public void setUnits(Double units) {
		this.units = units;
	}
	public Integer getBulks() {
		return bulks;
	}
	public void setBulks(Integer bulks) {
		this.bulks = bulks;
	}
	
}
