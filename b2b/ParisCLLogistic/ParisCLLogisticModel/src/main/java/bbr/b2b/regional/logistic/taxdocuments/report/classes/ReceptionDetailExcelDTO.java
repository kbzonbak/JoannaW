package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import java.io.Serializable;

public class ReceptionDetailExcelDTO implements Serializable {

	private Long orderid;
	private Long ordernumber;
	private Long ordertypeid;
	private String ordertypecode;
	private String ordertypename;
	private Long deliverylocationid;
	private String deliverylocationcode;
	private String deliverylocationname;
	private Long taxdocumentid;
	private Long taxdocumentnumber;
	private String receptionnumber;
	private String receptiondate;
	private Long itemid;
	private String iteminternalcode;
	private String itemname;
	private String vendoritemcode;
	private Double receivedunits;
	private Double finalcost;
	private Double totalreceived;
	
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
	public Long getOrdertypeid() {
		return ordertypeid;
	}
	public void setOrdertypeid(Long ordertypeid) {
		this.ordertypeid = ordertypeid;
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
	public Long getDeliverylocationid() {
		return deliverylocationid;
	}
	public void setDeliverylocationid(Long deliverylocationid) {
		this.deliverylocationid = deliverylocationid;
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
	public Long getTaxdocumentid() {
		return taxdocumentid;
	}
	public void setTaxdocumentid(Long taxdocumentid) {
		this.taxdocumentid = taxdocumentid;
	}
	public Long getTaxdocumentnumber() {
		return taxdocumentnumber;
	}
	public void setTaxdocumentnumber(Long taxdocumentnumber) {
		this.taxdocumentnumber = taxdocumentnumber;
	}
	public String getReceptionnumber() {
		return receptionnumber;
	}
	public void setReceptionnumber(String receptionnumber) {
		this.receptionnumber = receptionnumber;
	}
	public String getReceptiondate() {
		return receptiondate;
	}
	public void setReceptiondate(String receptiondate) {
		this.receptiondate = receptiondate;
	}
	public Long getItemid() {
		return itemid;
	}
	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}
	public String getIteminternalcode() {
		return iteminternalcode;
	}
	public void setIteminternalcode(String iteminternalcode) {
		this.iteminternalcode = iteminternalcode;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public String getVendoritemcode() {
		return vendoritemcode;
	}
	public void setVendoritemcode(String vendoritemcode) {
		this.vendoritemcode = vendoritemcode;
	}
	public Double getReceivedunits() {
		return receivedunits;
	}
	public void setReceivedunits(Double receivedunits) {
		this.receivedunits = receivedunits;
	}
	public Double getFinalcost() {
		return finalcost;
	}
	public void setFinalcost(Double finalcost) {
		this.finalcost = finalcost;
	}
	public Double getTotalreceived() {
		return totalreceived;
	}
	public void setTotalreceived(Double totalreceived) {
		this.totalreceived = totalreceived;
	}
}
