package bbr.b2b.logistic.dvrorders.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class DvrOrderReportInitParamDTO implements Serializable {

	private String vendorcode;
	private Long deliverylocationid;
	private int filtertype;
	private Long dvrorderstatetypeid;
	private LocalDateTime since;
	private LocalDateTime until;
	private String searchvalue;
	private int pageNumber;
	private int rows;
	private OrderCriteriaDTO[] orderby;

	public String getVendorcode() {
		return vendorcode;
	}

	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}

	public Long getDeliverylocationid() {
		return deliverylocationid;
	}

	public void setDeliverylocationid(Long deliverylocationid) {
		this.deliverylocationid = deliverylocationid;
	}

	public int getFiltertype() {
		return filtertype;
	}

	public void setFiltertype(int filtertype) {
		this.filtertype = filtertype;
	}

	public Long getDvrorderstatetypeid() {
		return dvrorderstatetypeid;
	}

	public void setDvrorderstatetypeid(Long dvrorderstatetypeid) {
		this.dvrorderstatetypeid = dvrorderstatetypeid;
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

	public String getSearchvalue() {
		return searchvalue;
	}

	public void setSearchvalue(String searchvalue) {
		this.searchvalue = searchvalue;
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
