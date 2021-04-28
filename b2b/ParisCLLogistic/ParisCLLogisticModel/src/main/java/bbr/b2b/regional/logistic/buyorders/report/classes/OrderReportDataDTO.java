package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class OrderReportDataDTO implements Serializable {

	private Long orderid;   
	private Long ordernumber;
	private String departmentcode;   
	private String departmentdesc;   
	private String orderstatetypedesc;    
	private String orderstatetypecode;  				 
	private String ordertypedesc;   
	private String ordertypecode;  				
	private String deliverylocationdesc;   
	private String deliverylocationcode;   
	private LocalDateTime emitteddate;   
	private LocalDateTime validdate;   
	private LocalDateTime expirationdate;   
	private Double totalunits;
	private Double pendingunits;
	private Double todeliveryunits;
	private Double receivedunits; 
	private Double outreceivedunits;
	private Double totalamount;
	private Double totalpending;   
	private Double totalreceived;   				  
	private Double totaltodelivery;
	private Long originalvendorid;
	private String originalvendorcode;
	private String originalvendorname;
		
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Long getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}	
	public String getDepartmentcode() {
		return departmentcode;
	}
	public void setDepartmentcode(String departmentcode) {
		this.departmentcode = departmentcode;
	}
	public String getDepartmentdesc() {
		return departmentdesc;
	}
	public void setDepartmentdesc(String departmentdesc) {
		this.departmentdesc = departmentdesc;
	}
	public String getOrderstatetypedesc() {
		return orderstatetypedesc;
	}
	public void setOrderstatetypedesc(String orderstatetypedesc) {
		this.orderstatetypedesc = orderstatetypedesc;
	}
	public String getOrderstatetypecode() {
		return orderstatetypecode;
	}
	public void setOrderstatetypecode(String orderstatetypecode) {
		this.orderstatetypecode = orderstatetypecode;
	}
	public String getOrdertypedesc() {
		return ordertypedesc;
	}
	public void setOrdertypedesc(String ordertypedesc) {
		this.ordertypedesc = ordertypedesc;
	}
	public String getOrdertypecode() {
		return ordertypecode;
	}
	public void setOrdertypecode(String ordertypecode) {
		this.ordertypecode = ordertypecode;
	}
	public String getDeliverylocationdesc() {
		return deliverylocationdesc;
	}
	public void setDeliverylocationdesc(String deliverylocationdesc) {
		this.deliverylocationdesc = deliverylocationdesc;
	}
	public String getDeliverylocationcode() {
		return deliverylocationcode;
	}
	public void setDeliverylocationcode(String deliverylocationcode) {
		this.deliverylocationcode = deliverylocationcode;
	}
	public LocalDateTime getEmitteddate() {
		return emitteddate;
	}
	public void setEmitteddate(LocalDateTime emitteddate) {
		this.emitteddate = emitteddate;
	}
	public LocalDateTime getValiddate() {
		return validdate;
	}
	public void setValiddate(LocalDateTime validdate) {
		this.validdate = validdate;
	}
	public LocalDateTime getExpirationdate() {
		return expirationdate;
	}
	public void setExpirationdate(LocalDateTime expirationdate) {
		this.expirationdate = expirationdate;
	}
	public Double getTotalunits() {
		return totalunits;
	}
	public void setTotalunits(Double totalunits) {
		this.totalunits = totalunits;
	}
	public Double getPendingunits() {
		return pendingunits;
	}
	public void setPendingunits(Double pendingunits) {
		this.pendingunits = pendingunits;
	}
	public Double getTodeliveryunits() {
		return todeliveryunits;
	}
	public void setTodeliveryunits(Double todeliveryunits) {
		this.todeliveryunits = todeliveryunits;
	}
	public Double getReceivedunits() {
		return receivedunits;
	}
	public void setReceivedunits(Double receivedunits) {
		this.receivedunits = receivedunits;
	}
	public Double getOutreceivedunits() {
		return outreceivedunits;
	}
	public void setOutreceivedunits(Double outreceivedunits) {
		this.outreceivedunits = outreceivedunits;
	}
	public Double getTotalamount() {
		return totalamount;
	}
	public void setTotalamount(Double totalamount) {
		this.totalamount = totalamount;
	}
	public Double getTotalpending() {
		return totalpending;
	}
	public void setTotalpending(Double totalpending) {
		this.totalpending = totalpending;
	}
	public Double getTotalreceived() {
		return totalreceived;
	}
	public void setTotalreceived(Double totalreceived) {
		this.totalreceived = totalreceived;
	}
	public Double getTotaltodelivery() {
		return totaltodelivery;
	}
	public void setTotaltodelivery(Double totaltodelivery) {
		this.totaltodelivery = totaltodelivery;
	}
	public Long getOriginalvendorid() {
		return originalvendorid;
	}
	public void setOriginalvendorid(Long originalvendorid) {
		this.originalvendorid = originalvendorid;
	}
	public String getOriginalvendorcode() {
		return originalvendorcode;
	}
	public void setOriginalvendorcode(String originalvendorcode) {
		this.originalvendorcode = originalvendorcode;
	}
	public String getOriginalvendorname() {
		return originalvendorname;
	}
	public void setOriginalvendorname(String originalvendorname) {
		this.originalvendorname = originalvendorname;
	}
	
}
