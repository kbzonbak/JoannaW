package bbr.b2b.regional.logistic.directorders.entities;

import bbr.b2b.regional.logistic.directorders.entities.DirectOrder;
import bbr.b2b.regional.logistic.items.entities.Item;
import bbr.b2b.regional.logistic.directorders.data.interfaces.IDirectOrderDetail;

public class DirectOrderDetail implements IDirectOrderDetail {

	private DirectOrderDetailId id;
	private Integer visualorder;
	private Double units;
	private Double listprice;
	private Double listcost;
	private Double finalprice;
	private Double finalcost;
	private Double pendingunits;
	private Double receivedunits;
	private Double todeliveryunits;
	private Double totalneed;
	private Double totalpending;
	private Double totalreceived;
	private Double totaltodelivery;
	private DirectOrder directorder;
	private Item item;

	public DirectOrderDetailId getId(){ 
		return this.id;
	}
	public Integer getVisualorder(){ 
		return this.visualorder;
	}
	public Double getUnits(){ 
		return this.units;
	}
	public Double getListprice(){ 
		return this.listprice;
	}
	public Double getListcost(){ 
		return this.listcost;
	}
	public Double getFinalprice(){ 
		return this.finalprice;
	}
	public Double getFinalcost(){ 
		return this.finalcost;
	}
	public DirectOrder getDirectorder(){ 
		return this.directorder;
	}
	public Item getItem(){ 
		return this.item;
	}
	public void setId(DirectOrderDetailId id){ 
		this.id = id;
	}
	public void setVisualorder(Integer visualorder){ 
		this.visualorder = visualorder;
	}
	public void setUnits(Double units){ 
		this.units = units;
	}
	public void setListprice(Double listprice){ 
		this.listprice = listprice;
	}
	public void setListcost(Double listcost){ 
		this.listcost = listcost;
	}
	public void setFinalprice(Double finalprice){ 
		this.finalprice = finalprice;
	}
	public void setFinalcost(Double finalcost){ 
		this.finalcost = finalcost;
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
	public Double getTotalneed() {
		return totalneed;
	}
	public void setTotalneed(Double totalneed) {
		this.totalneed = totalneed;
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
	public void setDirectorder(DirectOrder directorder){ 
		this.directorder = directorder;
	}
	public void setItem(Item item){ 
		this.item = item;
	}	
}
