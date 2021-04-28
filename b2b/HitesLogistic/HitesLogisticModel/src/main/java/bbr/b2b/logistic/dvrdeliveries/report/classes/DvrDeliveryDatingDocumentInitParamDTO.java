package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class DvrDeliveryDatingDocumentInitParamDTO implements Serializable {

	private String locationcode;
	private String vendorcode;
	private int filtertype; // 0: fecha - 1: nro OC  - 2: nro doc
	private String searchvalue;
	private LocalDateTime when;
	private int pageNumber;
	private int rows;
	private OrderCriteriaDTO[] orderby;

	public String getLocationcode() {
		return locationcode;
	}

	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}

	public String getVendorcode() {
		return vendorcode;
	}

	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}

	public int getFiltertype() {
		return filtertype;
	}

	public void setFiltertype(int filtertype) {
		this.filtertype = filtertype;
	}

	public String getSearchvalue() {
		return searchvalue;
	}

	public void setSearchvalue(String searchvalue) {
		this.searchvalue = searchvalue;
	}

	public LocalDateTime getWhen() {
		return when;
	}

	public void setWhen(LocalDateTime when) {
		this.when = when;
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
