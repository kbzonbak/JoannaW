package bbr.b2b.logistic.dvrorders.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class DvrOrderPdfDataDTO implements Serializable {

	private String retailerdescription;
	private String vendortradename;
	private Long number;
	private LocalDateTime emitted;
	private LocalDateTime deliverydate;
	private LocalDateTime expiration;
	private String paymentdays;
	private String deliverylocationcode;
	private String deliverylocationname;
	private String deliverylocationaddress;
	private String responsiblename;
	private Double totalunits;
	private Double finalcost;
	private Double totaldiscounts;
	private Double totalneed;
	
	public String getRetailerdescription() {
		return retailerdescription;
	}
	public void setRetailerdescription(String retailerdescription) {
		this.retailerdescription = retailerdescription;
	}
	public String getVendortradename() {
		return vendortradename;
	}
	public void setVendortradename(String vendortradename) {
		this.vendortradename = vendortradename;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public LocalDateTime getEmitted() {
		return emitted;
	}
	public void setEmitted(LocalDateTime emitted) {
		this.emitted = emitted;
	}
	public LocalDateTime getDeliverydate() {
		return deliverydate;
	}
	public void setDeliverydate(LocalDateTime deliverydate) {
		this.deliverydate = deliverydate;
	}
	public LocalDateTime getExpiration() {
		return expiration;
	}
	public void setExpiration(LocalDateTime expiration) {
		this.expiration = expiration;
	}
	public String getPaymentdays() {
		return paymentdays;
	}
	public void setPaymentdays(String paymentdays) {
		this.paymentdays = paymentdays;
	}
	public String getDeliverylocationcode() {
		return deliverylocationcode;
	}
	public void setDeliverylocationcode(String deliverylocationcode) {
		this.deliverylocationcode = deliverylocationcode;
	}
	public String getDeliverylocationname() {
		return deliverylocationname;
	}
	public void setDeliverylocationname(String deliverylocationname) {
		this.deliverylocationname = deliverylocationname;
	}
	public String getDeliverylocationaddress() {
		return deliverylocationaddress;
	}
	public void setDeliverylocationaddress(String deliverylocationaddress) {
		this.deliverylocationaddress = deliverylocationaddress;
	}
	public String getResponsiblename() {
		return responsiblename;
	}
	public void setResponsiblename(String responsiblename) {
		this.responsiblename = responsiblename;
	}
	public Double getTotalunits() {
		return totalunits;
	}
	public void setTotalunits(Double totalunits) {
		this.totalunits = totalunits;
	}
	public Double getFinalcost() {
		return finalcost;
	}
	public void setFinalcost(Double finalcost) {
		this.finalcost = finalcost;
	}
	public Double getTotaldiscounts() {
		return totaldiscounts;
	}
	public void setTotaldiscounts(Double totaldiscounts) {
		this.totaldiscounts = totaldiscounts;
	}
	public Double getTotalneed() {
		return totalneed;
	}
	public void setTotalneed(Double totalneed) {
		this.totalneed = totalneed;
	}
}
