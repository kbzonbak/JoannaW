package bbr.b2b.logistic.dvrorders.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DvrOrderReportDataDTO implements Serializable {

	private String b2blink;
	private Long dvrorderid;
	private Long dvrordernumber;
	private String dvrreferencenumber;
	private String dvrorderstatetypecode;
	private String dvrorderstatetypename;
	private String ordertypecode;
	private String ordertypename;
	private String deliverylocationcode;
	private String deliverylocationname;
	private LocalDateTime emitteddate;
	private LocalDateTime deliverydate;
	private LocalDateTime expirationdate;
	private String paymentdays;
	private Long clientid;
	private String clientname;
	private String clientdni;
	private String clientaddress;
	private String clientcommune;
	private String clientcity;
	private Double totalamount;
	private Double totalpending;
	private Double totalreceived;
	private Double totaltodelivery;
	private Double totalunits;
	private Double todeliveryunits;
	private Double receivedunits;
	private Double pendingunits;
	private String comment;
	private Double netamount;
	
	public String getB2blink() {
		return b2blink;
	}
	public void setB2blink(String b2blink) {
		this.b2blink = b2blink;
	}
	public Long getDvrorderid() {
		return dvrorderid;
	}
	public void setDvrorderid(Long dvrorderid) {
		this.dvrorderid = dvrorderid;
	}
	public Long getDvrordernumber() {
		return dvrordernumber;
	}
	public void setDvrordernumber(Long dvrordernumber) {
		this.dvrordernumber = dvrordernumber;
	}
	public String getDvrreferencenumber() {
		return dvrreferencenumber;
	}
	public void setDvrreferencenumber(String dvrreferencenumber) {
		this.dvrreferencenumber = dvrreferencenumber;
	}
	public String getDvrorderstatetypecode() {
		return dvrorderstatetypecode;
	}
	public void setDvrorderstatetypecode(String dvrorderstatetypecode) {
		this.dvrorderstatetypecode = dvrorderstatetypecode;
	}
	public String getDvrorderstatetypename() {
		return dvrorderstatetypename;
	}
	public void setDvrorderstatetypename(String dvrorderstatetypename) {
		this.dvrorderstatetypename = dvrorderstatetypename;
	}
	public String getOrdertypecode() {
		return ordertypecode;
	}
	public void setOrdertypecode(String ordertypecode) {
		this.ordertypecode = ordertypecode;
	}
	public String getOrdertypename() {
		return ordertypename;
	}
	public void setOrdertypename(String ordertypename) {
		this.ordertypename = ordertypename;
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
	public LocalDateTime getEmitteddate() {
		return emitteddate;
	}
	public void setEmitteddate(LocalDateTime emitteddate) {
		this.emitteddate = emitteddate;
	}
	public LocalDateTime getDeliverydate() {
		return deliverydate;
	}
	public void setDeliverydate(LocalDateTime deliverydate) {
		this.deliverydate = deliverydate;
	}
	public LocalDateTime getExpirationdate() {
		return expirationdate;
	}
	public void setExpirationdate(LocalDateTime expirationdate) {
		this.expirationdate = expirationdate;
	}
	public String getPaymentdays() {
		return paymentdays;
	}
	public void setPaymentdays(String paymentdays) {
		this.paymentdays = paymentdays;
	}
	public Long getClientid() {
		return clientid;
	}
	public void setClientid(Long clientid) {
		this.clientid = clientid;
	}
	public String getClientname() {
		return clientname;
	}
	public void setClientname(String clientname) {
		this.clientname = clientname;
	}
	public String getClientdni() {
		return clientdni;
	}
	public void setClientdni(String clientdni) {
		this.clientdni = clientdni;
	}
	public String getClientaddress() {
		return clientaddress;
	}
	public void setClientaddress(String clientaddress) {
		this.clientaddress = clientaddress;
	}
	public String getClientcommune() {
		return clientcommune;
	}
	public void setClientcommune(String clientcommune) {
		this.clientcommune = clientcommune;
	}
	public String getClientcity() {
		return clientcity;
	}
	public void setClientcity(String clientcity) {
		this.clientcity = clientcity;
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
	public Double getTotalunits() {
		return totalunits;
	}
	public void setTotalunits(Double totalunits) {
		this.totalunits = totalunits;
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
	public Double getPendingunits() {
		return pendingunits;
	}
	public void setPendingunits(Double pendingunits) {
		this.pendingunits = pendingunits;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Double getNetamount() {
		return netamount;
	}
	public void setNetamount(Double netamount) {
		this.netamount = netamount;
	}

}
