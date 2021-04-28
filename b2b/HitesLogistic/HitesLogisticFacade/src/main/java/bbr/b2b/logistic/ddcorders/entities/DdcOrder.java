package bbr.b2b.logistic.ddcorders.entities;

import java.time.LocalDateTime;

import bbr.b2b.logistic.buyorders.entities.Order;
import bbr.b2b.logistic.ddcdeliveries.entities.DdcDelivery;
import bbr.b2b.logistic.ddcorders.data.interfaces.IDdcOrder;
import bbr.b2b.logistic.locations.entities.Location;

public class DdcOrder extends Order implements IDdcOrder {

	private LocalDateTime originaldeliverydate;
	private LocalDateTime currentdeliverydate;
	private LocalDateTime expiration;
	private Double needunits;
	private Double todeliveryunits;
	private Double receivedunits;
	private Double pendingunits;
	private Double totalneed;
	private Double totaltodelivery;
	private Double totalreceived;
	private Double totalpending;
	private String paymentdays;
	private String comment;
	private LocalDateTime currentstatetypedate;
	private String currentstatetypewho;
	private String currentstatetypecomment;
	private String referencenumber;
	private Long dispatchguide;
	private Integer reschedulingcounter;
	private DdcOrderStateType currentstatetype;
	private Location salelocation;
	private DdcDelivery currentddcdelivery;

	public LocalDateTime getOriginaldeliverydate() {
		return originaldeliverydate;
	}

	public void setOriginaldeliverydate(LocalDateTime originaldeliverydate) {
		this.originaldeliverydate = originaldeliverydate;
	}

	public LocalDateTime getCurrentdeliverydate() {
		return currentdeliverydate;
	}

	public void setCurrentdeliverydate(LocalDateTime currentdeliverydate) {
		this.currentdeliverydate = currentdeliverydate;
	}

	public LocalDateTime getExpiration() {
		return expiration;
	}

	public void setExpiration(LocalDateTime expiration) {
		this.expiration = expiration;
	}

	public Double getNeedunits() {
		return needunits;
	}

	public void setNeedunits(Double needunits) {
		this.needunits = needunits;
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

	public Double getTotalneed() {
		return totalneed;
	}

	public void setTotalneed(Double totalneed) {
		this.totalneed = totalneed;
	}

	public Double getTotaltodelivery() {
		return totaltodelivery;
	}

	public void setTotaltodelivery(Double totaltodelivery) {
		this.totaltodelivery = totaltodelivery;
	}

	public Double getTotalreceived() {
		return totalreceived;
	}

	public void setTotalreceived(Double totalreceived) {
		this.totalreceived = totalreceived;
	}

	public Double getTotalpending() {
		return totalpending;
	}

	public void setTotalpending(Double totalpending) {
		this.totalpending = totalpending;
	}

	public String getPaymentdays() {
		return paymentdays;
	}

	public void setPaymentdays(String paymentdays) {
		this.paymentdays = paymentdays;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getCurrentstatetypedate() {
		return currentstatetypedate;
	}

	public void setCurrentstatetypedate(LocalDateTime currentstatetypedate) {
		this.currentstatetypedate = currentstatetypedate;
	}

	public String getCurrentstatetypewho() {
		return currentstatetypewho;
	}

	public void setCurrentstatetypewho(String currentstatetypewho) {
		this.currentstatetypewho = currentstatetypewho;
	}

	public String getCurrentstatetypecomment() {
		return currentstatetypecomment;
	}

	public void setCurrentstatetypecomment(String currentstatetypecomment) {
		this.currentstatetypecomment = currentstatetypecomment;
	}

	public String getReferencenumber() {
		return referencenumber;
	}

	public void setReferencenumber(String referencenumber) {
		this.referencenumber = referencenumber;
	}

	public Long getDispatchguide() {
		return dispatchguide;
	}

	public void setDispatchguide(Long dispatchguide) {
		this.dispatchguide = dispatchguide;
	}

	public Integer getReschedulingcounter() {
		return reschedulingcounter;
	}

	public void setReschedulingcounter(Integer reschedulingcounter) {
		this.reschedulingcounter = reschedulingcounter;
	}

	public DdcOrderStateType getCurrentstatetype() {
		return currentstatetype;
	}

	public void setCurrentstatetype(DdcOrderStateType currentstatetype) {
		this.currentstatetype = currentstatetype;
	}

	public Location getSalelocation() {
		return salelocation;
	}

	public void setSalelocation(Location salelocation) {
		this.salelocation = salelocation;
	}

	public DdcDelivery getCurrentddcdelivery() {
		return currentddcdelivery;
	}

	public void setCurrentddcdelivery(DdcDelivery currentddcdelivery) {
		this.currentddcdelivery = currentddcdelivery;
	}

}
