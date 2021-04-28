package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class VeVPDOrderDetailReportDataDTO implements Serializable {

	private Long orderid;  
	private Long itemid;   					
	private String itemsku;  
	private String vendoritemcode;  
	private String itemdescription;  					
	private Double listprice;  
	private Double finalprice;  
	private Double listcost;  
	private Double finalcost;  					
	private Double needunits;  
	private Double todeliveryunits;  
	private Double receivedunits;  
	private Double pendingunits;  
	private Double totalneed;  
	private Double totalreceived;  
	private Double totaltodelivery;  
	private Double totalpending;
	
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Long getItemid() {
		return itemid;
	}
	public void setItemid(Long itemid) {
		this.itemid = itemid;
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
	public String getItemdescription() {
		return itemdescription;
	}
	public void setItemdescription(String itemdescription) {
		this.itemdescription = itemdescription;
	}
	public Double getListprice() {
		return listprice;
	}
	public void setListprice(Double listprice) {
		this.listprice = listprice;
	}
	public Double getFinalprice() {
		return finalprice;
	}
	public void setFinalprice(Double finalprice) {
		this.finalprice = finalprice;
	}
	public Double getListcost() {
		return listcost;
	}
	public void setListcost(Double listcost) {
		this.listcost = listcost;
	}
	public Double getFinalcost() {
		return finalcost;
	}
	public void setFinalcost(Double finalcost) {
		this.finalcost = finalcost;
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
	public Double getTotalpending() {
		return totalpending;
	}
	public void setTotalpending(Double totalpending) {
		this.totalpending = totalpending;
	} 
}
