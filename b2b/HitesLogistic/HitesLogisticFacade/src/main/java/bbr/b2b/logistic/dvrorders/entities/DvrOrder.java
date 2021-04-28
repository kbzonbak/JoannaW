package bbr.b2b.logistic.dvrorders.entities;

import bbr.b2b.logistic.buyorders.entities.Order;
import bbr.b2b.logistic.dvrorders.entities.DvrOrderStateType;

import java.time.LocalDateTime;

import bbr.b2b.logistic.locations.entities.Location;
import bbr.b2b.logistic.dvrorders.data.interfaces.IDvrOrder;

public class DvrOrder extends Order implements IDvrOrder {

	private LocalDateTime deliverydate;
	private LocalDateTime expiration;
	private String paymenttype;
	private String comment;
	private Boolean multidelivery;
	private String warehousecode;
	private Double totalunits;
	private Double todeliveryunits;
	private Double receivedunits;
	private Double pendingunits;
	private Double totalneed;
	private Double totaltodelivery;
	private Double totalreceived;
	private Double totalpending;
	private LocalDateTime currentstatetypedate;
	private String referencenumber;
	private String paymentdays;
	private LocalDateTime clientdeliverydate;
	private Double netamount;
	private Double taxamount;
	private Double totalamountoc;
	private DvrOrderStateType currentstatetype;
	private LocalDateTime reschedulingdate;
	private Integer reschedulingcounter;
	private Location deliverylocation;
	private Location salelocation;

	public LocalDateTime getDeliverydate() {
		return deliverydate;
	}

	public LocalDateTime getExpiration() {
		return expiration;
	}

	public String getPaymenttype() {
		return paymenttype;
	}

	public String getComment() {
		return comment;
	}

	public Boolean getMultidelivery() {
		return multidelivery;
	}

	public String getWarehousecode() {
		return warehousecode;
	}

	public Double getTotalunits() {
		return totalunits;
	}

	public Double getTodeliveryunits() {
		return todeliveryunits;
	}

	public Double getReceivedunits() {
		return receivedunits;
	}

	public Double getPendingunits() {
		return pendingunits;
	}

	public Double getTotalneed() {
		return totalneed;
	}

	public Double getTotaltodelivery() {
		return totaltodelivery;
	}

	public Double getTotalreceived() {
		return totalreceived;
	}

	public Double getTotalpending() {
		return totalpending;
	}

	public LocalDateTime getCurrentstatetypedate() {
		return currentstatetypedate;
	}

	public String getReferencenumber() {
		return referencenumber;
	}

	public String getPaymentdays() {
		return paymentdays;
	}

	public LocalDateTime getClientdeliverydate() {
		return clientdeliverydate;
	}

	public void setClientdeliverydate(LocalDateTime clientdeliverydate) {
		this.clientdeliverydate = clientdeliverydate;
	}

	public Double getNetamount() {
		return netamount;
	}

	public Double getTaxamount() {
		return taxamount;
	}

	public Double getTotalamountoc() {
		return totalamountoc;
	}

	public DvrOrderStateType getCurrentstatetype() {
		return currentstatetype;
	}

	public Location getDeliverylocation() {
		return deliverylocation;
	}

	public void setDeliverydate(LocalDateTime deliverydate) {
		this.deliverydate = deliverydate;
	}

	public void setExpiration(LocalDateTime expiration) {
		this.expiration = expiration;
	}

	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setMultidelivery(Boolean multidelivery) {
		this.multidelivery = multidelivery;
	}

	public void setWarehousecode(String warehousecode) {
		this.warehousecode = warehousecode;
	}

	public void setTotalunits(Double totalunits) {
		this.totalunits = totalunits;
	}

	public void setTodeliveryunits(Double todeliveryunits) {
		this.todeliveryunits = todeliveryunits;
	}

	public void setReceivedunits(Double receivedunits) {
		this.receivedunits = receivedunits;
	}

	public void setPendingunits(Double pendingunits) {
		this.pendingunits = pendingunits;
	}

	public void setTotalneed(Double totalneed) {
		this.totalneed = totalneed;
	}

	public void setTotaltodelivery(Double totaltodelivery) {
		this.totaltodelivery = totaltodelivery;
	}

	public void setTotalreceived(Double totalreceived) {
		this.totalreceived = totalreceived;
	}

	public void setTotalpending(Double totalpending) {
		this.totalpending = totalpending;
	}

	public void setCurrentstatetypedate(LocalDateTime currentstatetypedate) {
		this.currentstatetypedate = currentstatetypedate;
	}

	public void setReferencenumber(String referencenumber) {
		this.referencenumber = referencenumber;
	}

	public void setPaymentdays(String paymentdays) {
		this.paymentdays = paymentdays;
	}

	public void setNetamount(Double netamount) {
		this.netamount = netamount;
	}

	public void setTaxamount(Double taxamount) {
		this.taxamount = taxamount;
	}

	public void setTotalamountoc(Double totalamountoc) {
		this.totalamountoc = totalamountoc;
	}

	public LocalDateTime getReschedulingdate() {
		return reschedulingdate;
	}

	public void setReschedulingdate(LocalDateTime reschedulingdate) {
		this.reschedulingdate = reschedulingdate;
	}

	public Integer getReschedulingcounter() {
		return reschedulingcounter;
	}

	public void setReschedulingcounter(Integer reschedulingcounter) {
		this.reschedulingcounter = reschedulingcounter;
	}

	public void setCurrentstatetype(DvrOrderStateType currentstatetype) {
		this.currentstatetype = currentstatetype;
	}

	public void setDeliverylocation(Location deliverylocation) {
		this.deliverylocation = deliverylocation;
	}

	public Location getSalelocation() {
		return salelocation;
	}

	public void setSalelocation(Location salelocation) {
		this.salelocation = salelocation;
	}

}
