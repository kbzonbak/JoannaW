package bbr.b2b.regional.logistic.items.report.classes;

import java.io.Serializable;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class VendorItemReportInitParamDTO implements Serializable{

	private String vendorcode;
	private String itemsku;
	private String vendoritemcode;
	private String description;
	private Long ocnumber;
	private int pagenumber;
	private int rows;	
	private OrderCriteriaDTO[] orderby;
	private int filtertype;
	
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getItemsku() {
		return itemsku;
	}
	public void setItemsku(String itemsku) {
		this.itemsku = itemsku;
	}
	public String getVendoritemcode() {
		return vendoritemcode;
	}
	public void setVendoritemcode(String vendoritemcode) {
		this.vendoritemcode = vendoritemcode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getOcnumber() {
		return ocnumber;
	}
	public void setOcnumber(Long ocnumber) {
		this.ocnumber = ocnumber;
	}
	public int getPagenumber() {
		return pagenumber;
	}
	public void setPagenumber(int pagenumber) {
		this.pagenumber = pagenumber;
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
