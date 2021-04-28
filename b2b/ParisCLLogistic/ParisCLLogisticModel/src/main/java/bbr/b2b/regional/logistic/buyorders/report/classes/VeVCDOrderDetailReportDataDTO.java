package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class VeVCDOrderDetailReportDataDTO implements Serializable {

	private Long orderid;     
	private Long itemid;   				
	private String sku;     
	private String vendoritemcode;    
	private String flowtypecode;
	private String flowtypedesc;
	private String itemdesc;  
	private Double finalcost;
	private Double totalunits;
	private Double pendingunits;				
	private Double receivedunits;				
	private Double todeliveryunits;			
	private Double outreceivedunits;			
	private Double totalamount;				
	private Double totalpending;				
	private Double totalreceived;				
	private Double totaltodelivery;
	private Long curve;
	private Long atcid;
	private String atccode;
	
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
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getVendoritemcode() {
		return vendoritemcode;
	}
	public void setVendoritemcode(String vendoritemcode) {
		this.vendoritemcode = vendoritemcode;
	}	
	public String getFlowtypecode() {
		return flowtypecode;
	}
	public void setFlowtypecode(String flowtypecode) {
		this.flowtypecode = flowtypecode;
	}
	public String getFlowtypedesc() {
		return flowtypedesc;
	}
	public void setFlowtypedesc(String flowtypedesc) {
		this.flowtypedesc = flowtypedesc;
	}
	public String getItemdesc() {
		return itemdesc;
	}
	public void setItemdesc(String itemdesc) {
		this.itemdesc = itemdesc;
	}
	public Double getFinalcost() {
		return finalcost;
	}
	public void setFinalcost(Double finalcost) {
		this.finalcost = finalcost;
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
	public Double getReceivedunits() {
		return receivedunits;
	}
	public void setReceivedunits(Double receivedunits) {
		this.receivedunits = receivedunits;
	}
	public Double getTodeliveryunits() {
		return todeliveryunits;
	}
	public void setTodeliveryunits(Double todeliveryunits) {
		this.todeliveryunits = todeliveryunits;
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
	public Long getCurve() {
		return curve;
	}
	public void setCurve(Long curve) {
		this.curve = curve;
	}
	public Long getAtcid() {
		return atcid;
	}
	public void setAtcid(Long atcid) {
		this.atcid = atcid;
	}
	public String getAtccode() {
		return atccode;
	}
	public void setAtccode(String atccode) {
		this.atccode = atccode;
	}
	
}
