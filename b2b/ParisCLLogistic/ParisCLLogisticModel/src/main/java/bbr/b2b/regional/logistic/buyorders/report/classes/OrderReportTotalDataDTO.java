package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class OrderReportTotalDataDTO implements Serializable{

	private Integer totalreg;	
	private Double totalunits;
	private Double pendingunits;
	private Double todeliveryunits;
	private Double receivedunits; 
	private Double outreceivedunits;
	private Double totalamount;
	private Double totalpending;   
	private Double totalreceived;   				  
	private Double totaltodelivery;
	
	public Integer getTotalreg() {
		return totalreg;
	}
	public void setTotalreg(Integer totalreg) {
		this.totalreg = totalreg;
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
}
