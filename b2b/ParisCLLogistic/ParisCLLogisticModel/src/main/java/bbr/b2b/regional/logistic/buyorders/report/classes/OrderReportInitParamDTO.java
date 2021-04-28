package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class OrderReportInitParamDTO implements Serializable {

	private String vendorcode;
	private String originalvendorcode;
	private String locationcode;	
	private Long[] salestoreid;
	private Long ocnumber;
	private Long orderstatetypeid;
	private LocalDateTime since;
	private LocalDateTime until;	
	private int pageNumber;
	private int rows;	
	private OrderCriteriaDTO[] orderby;
	private int filtertype;
	private String sendnumber;
	

	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getOriginalvendorcode() {
		return originalvendorcode;
	}
	public void setOriginalvendorcode(String originalvendorcode) {
		this.originalvendorcode = originalvendorcode;
	}

	public String getLocationcode() {
		return locationcode;
	}
	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}
	public Long getOcnumber() {
		return ocnumber;
	}
	public void setOcnumber(Long ocnumber) {
		this.ocnumber = ocnumber;
	}
	public Long getOrderstatetypeid() {
		return orderstatetypeid;
	}
	public void setOrderstatetypeid(Long orderstatetypeid) {
		this.orderstatetypeid = orderstatetypeid;
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
	public Long[] getSalestoreid() {
		return salestoreid;
	}
	public void setSalestoreid(Long[] salestoreid) {
		this.salestoreid = salestoreid;
	}
	public String getSendnumber() {
		return sendnumber;
	}
	public void setSendnumber(String sendnumber) {
		this.sendnumber = sendnumber;
	}
	
}
