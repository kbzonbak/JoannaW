package bbr.b2b.regional.logistic.kpi.data.classes;

import java.io.Serializable;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class KPISummaryInitParamDTO implements Serializable{

	private String vendorcode;
	private Long[] departmentids;
	private Long[] salestoreids;
	private String since;
	private String until;	
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
	public Long[] getSalestoreids() {
		return salestoreids;
	}
	public void setSalestoreids(Long[] salestoreids) {
		this.salestoreids = salestoreids;
	}
	public String getSince() {
		return since;
	}
	public void setSince(String since) {
		this.since = since;
	}
	public String getUntil() {
		return until;
	}
	public void setUntil(String until) {
		this.until = until;
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