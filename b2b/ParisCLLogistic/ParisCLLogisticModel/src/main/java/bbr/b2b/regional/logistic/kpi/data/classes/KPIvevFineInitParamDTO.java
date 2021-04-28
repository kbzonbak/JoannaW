package bbr.b2b.regional.logistic.kpi.data.classes;

import java.io.Serializable;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class KPIvevFineInitParamDTO implements Serializable{

	private String vendorcode;
	private Long[] departmentids;
	private Integer year;
	private Integer month;
	private Double firstdayfinepercent;
	private Double nextdaysfinepercent;
	private Boolean courier;
	private OrderCriteriaDTO[] orderby;
	
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public Long[] getDepartmentids() {
		return departmentids;
	}
	public void setDepartmentids(Long[] departmentids) {
		this.departmentids = departmentids;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Double getFirstdayfinepercent() {
		return firstdayfinepercent;
	}
	public void setFirstdayfinepercent(Double firstdayfinepercent) {
		this.firstdayfinepercent = firstdayfinepercent;
	}
	public Double getNextdaysfinepercent() {
		return nextdaysfinepercent;
	}
	public void setNextdaysfinepercent(Double nextdaysfinepercent) {
		this.nextdaysfinepercent = nextdaysfinepercent;
	}
	public Boolean getCourier() {
		return courier;
	}
	public void setCourier(Boolean courier) {
		this.courier = courier;
	}
	public OrderCriteriaDTO[] getOrderby() {
		return orderby;
	}
	public void setOrderby(OrderCriteriaDTO[] orderby) {
		this.orderby = orderby;
	}
}
