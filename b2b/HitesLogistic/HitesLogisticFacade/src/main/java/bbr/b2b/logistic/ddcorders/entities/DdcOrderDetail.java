package bbr.b2b.logistic.ddcorders.entities;

import java.time.LocalDateTime;

import bbr.b2b.logistic.ddcorders.data.interfaces.IDdcOrderDetail;
import bbr.b2b.logistic.items.entities.Item;

public class DdcOrderDetail implements IDdcOrderDetail {

	private DdcOrderDetailId id;
	private Double basecost;
	private Double finalcost;
	private Double normalprice;
	private Double offerprice;
	private String packingcode;
	private String packingdescription;
	private String baseunit;
	private Double productpackingrate;	
	private String ean;
	private String barcode2;
	private String comment;
	private Double tolerance;
	private LocalDateTime itemdeliverydate;
	private Double needunits;
	private Double todeliveryunits;
	private Double receivedunits;
	private Double pendingunits;
	private Double totalneed;
	private Double totaltodelivery;
	private Double totalreceived;
	private Double totalpending;
	private DdcOrder ddcorder;
	private Item item;
	
	public DdcOrderDetailId getId() {
		return id;
	}
	public void setId(DdcOrderDetailId id) {
		this.id = id;
	}
	public Double getBasecost() {
		return basecost;
	}
	public void setBasecost(Double basecost) {
		this.basecost = basecost;
	}
	public Double getFinalcost() {
		return finalcost;
	}
	public void setFinalcost(Double finalcost) {
		this.finalcost = finalcost;
	}
	public Double getNormalprice() {
		return normalprice;
	}
	public void setNormalprice(Double normalprice) {
		this.normalprice = normalprice;
	}
	public Double getOfferprice() {
		return offerprice;
	}
	public void setOfferprice(Double offerprice) {
		this.offerprice = offerprice;
	}
	public String getPackingcode() {
		return packingcode;
	}
	public void setPackingcode(String packingcode) {
		this.packingcode = packingcode;
	}
	public String getPackingdescription() {
		return packingdescription;
	}
	public void setPackingdescription(String packingdescription) {
		this.packingdescription = packingdescription;
	}
	public String getBaseunit() {
		return baseunit;
	}
	public void setBaseunit(String baseunit) {
		this.baseunit = baseunit;
	}
	public Double getProductpackingrate() {
		return productpackingrate;
	}
	public void setProductpackingrate(Double productpackingrate) {
		this.productpackingrate = productpackingrate;
	}
	public String getEan() {
		return ean;
	}
	public void setEan(String ean) {
		this.ean = ean;
	}
	public String getBarcode2() {
		return barcode2;
	}
	public void setBarcode2(String barcode2) {
		this.barcode2 = barcode2;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Double getTolerance() {
		return tolerance;
	}
	public void setTolerance(Double tolerance) {
		this.tolerance = tolerance;
	}
	public LocalDateTime getItemdeliverydate() {
		return itemdeliverydate;
	}
	public void setItemdeliverydate(LocalDateTime itemdeliverydate) {
		this.itemdeliverydate = itemdeliverydate;
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
	public DdcOrder getDdcorder() {
		return ddcorder;
	}
	public void setDdcorder(DdcOrder ddcorder) {
		this.ddcorder = ddcorder;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	
}
