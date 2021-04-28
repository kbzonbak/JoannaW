package bbr.b2b.logistic.datings.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class PreDatingReserveDetailInitParamDTO implements Serializable {

	private String vendorcode;
	private String locationcode;
	private LocalDateTime date;
	private int pagenumber;
	private int rows;
	private OrderCriteriaDTO[] orderby;
	private Boolean ispageinfo;
	
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getLocationcode() {
		return locationcode;
	}
	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
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
	public Boolean getIspageinfo() {
		return ispageinfo;
	}
	public void setIspageinfo(Boolean ispageinfo) {
		this.ispageinfo = ispageinfo;
	}
	
}
