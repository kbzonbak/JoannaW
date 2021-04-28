package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class PackingListDataDTO implements Serializable{
	
	private String flowtype;	
	private String bulknumber;
	private String palletnumber;
	private String locationcode;	
	private String departmentcode;	
	private String itemsku;	
	private Long ordernumber;	
	private Double units;
	private Long flowtypeid;
	private Long locationid;
	private Long departmentid;
	private Long itemid;
	private Long orderid;
	private Long taxnumber;
	private Boolean bulk;
	private Integer rownumber;
	
	public String getFlowtype() {
		return flowtype;
	}
	public void setFlowtype(String flowtype) {
		this.flowtype = flowtype;
	}
	public Long getFlowtypeid() {
		return flowtypeid;
	}
	public void setFlowtypeid(Long flowtypeid) {
		this.flowtypeid = flowtypeid;
	}
	public String getBulknumber() {
		return bulknumber;
	}
	public void setBulknumber(String bulknumber) {
		this.bulknumber = bulknumber;
	}
	public String getPalletnumber() {
		return palletnumber;
	}
	public void setPalletnumber(String palletnumber) {
		this.palletnumber = palletnumber;
	}
	public String getLocationcode() {
		return locationcode;
	}
	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}
	public Long getLocationid() {
		return locationid;
	}
	public void setLocationid(Long locationid) {
		this.locationid = locationid;
	}
	public String getDepartmentcode() {
		return departmentcode;
	}
	public void setDepartmentcode(String departmentcode) {
		this.departmentcode = departmentcode;
	}
	public Long getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(Long departmentid) {
		this.departmentid = departmentid;
	}
	public String getItemsku() {
		return itemsku;
	}
	public void setItemsku(String itemsku) {
		this.itemsku = itemsku;
	}
	public Long getItemid() {
		return itemid;
	}
	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}
	public Long getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Double getUnits() {
		return units;
	}
	public void setUnits(Double units) {
		this.units = units;
	}
	public Long getTaxnumber() {
		return taxnumber;
	}
	public void setTaxnumber(Long taxnumber) {
		this.taxnumber = taxnumber;
	}	
	public Boolean isBulk() {
		return bulk;
	}
	public void setBulk(Boolean bulk) {
		this.bulk = bulk;
	}
	public Integer getRownumber() {
		return rownumber;
	}
	public void setRownumber(Integer rownumber) {
		this.rownumber = rownumber;
	}	
}
