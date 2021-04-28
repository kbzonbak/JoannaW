package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class DvrDeliveryReportInitParamDTO implements Serializable {

	private String vendorcode;
	private String searchvalue;
	private Long dvrdeliverystatetypeid;
	private int pageNumber;
	private int rows;
	private OrderCriteriaDTO[] orderby;
	private int filtertype;

	public String getVendorcode() {
		return vendorcode;
	}

	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}

	public String getSearchvalue() {
		return searchvalue;
	}

	public void setSearchvalue(String searchvalue) {
		this.searchvalue = searchvalue;
	}

	public Long getDvrdeliverystatetypeid() {
		return dvrdeliverystatetypeid;
	}

	public void setDvrdeliverystatetypeid(Long dvrdeliverystatetypeid) {
		this.dvrdeliverystatetypeid = dvrdeliverystatetypeid;
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

	public int getFiltertype() {
		return filtertype;
	}

	public void setFiltertype(int filtertype) {
		this.filtertype = filtertype;
	}

}
