package bbr.b2b.regional.logistic.kpi.data.classes;

import java.io.Serializable;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class KPIdetailInitParamDTO implements Serializable{

	private String vendorcode;
	private Long[] departmentids;
	private Long[] salestoreids;
	private Long[] kpitypeids;
	private String since;
	private String until;
	private int pageNumber;
	private int rows;
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
	public Long[] getKpitypeids() {
		return kpitypeids;
	}
	public void setKpitypeids(Long[] kpitypeids) {
		this.kpitypeids = kpitypeids;
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
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
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