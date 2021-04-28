package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class DOVirtualReceptionReportInitParamDTO implements Serializable{

	private String vendorcode;
	private String vendortradename;
	private Long[] salestoreids;
	private Long departmentid;
	private Long taxdocumentstatetypeid;
	private LocalDateTime since;
	private LocalDateTime until;
	private Long number;
	private int filtertype;
	private int pageNumber;
	private int rows;	
	private OrderCriteriaDTO[] orderby;
	
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getVendortradename() {
		return vendortradename;
	}
	public void setVendortradename(String vendortradename) {
		this.vendortradename = vendortradename;
	}
	public Long[] getSalestoreids() {
		return salestoreids;
	}
	public void setSalestoreids(Long[] salestoreids) {
		this.salestoreids = salestoreids;
	}
	public Long getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(Long departmentid) {
		this.departmentid = departmentid;
	}
	public Long getTaxdocumentstatetypeid() {
		return taxdocumentstatetypeid;
	}
	public void setTaxdocumentstatetypeid(Long taxdocumentstatetypeid) {
		this.taxdocumentstatetypeid = taxdocumentstatetypeid;
	}
	public LocalDateTime getSince() {
		return since;
	}
	public void setSince(LocalDateTime since) {
		this.since = since;
	}
	public LocalDateTime getUntil() {
		return until;
	}
	public void setUntil(LocalDateTime until) {
		this.until = until;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public int getFiltertype() {
		return filtertype;
	}
	public void setFiltertype(int filtertype) {
		this.filtertype = filtertype;
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
