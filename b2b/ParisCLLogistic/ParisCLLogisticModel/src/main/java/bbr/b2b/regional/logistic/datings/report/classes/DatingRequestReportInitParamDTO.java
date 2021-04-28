package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class DatingRequestReportInitParamDTO implements Serializable {

	private String locationcode;
	private Long ordertypeid;
	private Long flowtypeid;
	private String vendorcode;	
	private LocalDateTime since;
	private LocalDateTime until;
	private int pagenumber;
	private int rows;	
	private OrderCriteriaDTO[] orderby;
	private Long number;
	private String value;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public String getLocationcode() {
		return locationcode;
	}
	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}	
	public Long getOrdertypeid() {
		return ordertypeid;
	}
	public void setOrdertypeid(Long ordertypeid) {
		this.ordertypeid = ordertypeid;
	}
	public Long getFlowtypeid() {
		return flowtypeid;
	}
	public void setFlowtypeid(Long flowtypeid) {
		this.flowtypeid = flowtypeid;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
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
}
