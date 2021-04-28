package bbr.b2b.logistic.ddcorders.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class DdcOrderReportInitParamDTO implements Serializable {

	private String vendorcode;
	private String filtervalue;
	private LocalDateTime since;
	private LocalDateTime until;
	private Long ddcorderstatetypeid;
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
	public String getFiltervalue() {
		return filtervalue;
	}
	public void setFiltervalue(String filtervalue) {
		this.filtervalue = filtervalue;
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
	public Long getDdcorderstatetypeid() {
		return ddcorderstatetypeid;
	}
	public void setDdcorderstatetypeid(Long ddcorderstatetypeid) {
		this.ddcorderstatetypeid = ddcorderstatetypeid;
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
