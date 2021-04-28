package bbr.b2b.regional.logistic.kpi.data.classes;

import java.io.Serializable;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class KPIgeneralInitParamDTO implements Serializable{

	private String vendorcode;
	private String vendorname;
	private Long[] departmentids;
	private String departmentname;
	private Long[] salestoreids;
	private String salestorename;
	private String since;
	private String until;	
	private Boolean courier;
	private int pageNumber;
	private int rows;
	private OrderCriteriaDTO[] orderby;
	
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getVendorname() {
		return vendorname;
	}
	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}
	public Long[] getDepartmentids() {
		return departmentids;
	}
	public void setDepartmentids(Long[] departmentids) {
		this.departmentids = departmentids;
	}
	public String getDepartmentname() {
		return departmentname;
	}
	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}
	public Long[] getSalestoreids() {
		return salestoreids;
	}
	public void setSalestoreids(Long[] salestoreids) {
		this.salestoreids = salestoreids;
	}
	public String getSalestorename() {
		return salestorename;
	}
	public void setSalestorename(String salestorename) {
		this.salestorename = salestorename;
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
	public OrderCriteriaDTO[] getOrderby() {
		return orderby;
	}
	public void setOrderby(OrderCriteriaDTO[] orderby) {
		this.orderby = orderby;
	}
}