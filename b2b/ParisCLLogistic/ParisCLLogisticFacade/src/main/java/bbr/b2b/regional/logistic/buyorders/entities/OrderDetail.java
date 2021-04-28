package bbr.b2b.regional.logistic.buyorders.entities;

import bbr.b2b.regional.logistic.buyorders.entities.Order;
import bbr.b2b.regional.logistic.items.entities.Item;
import bbr.b2b.regional.logistic.items.entities.Unit;
import bbr.b2b.regional.logistic.buyorders.data.interfaces.IOrderDetail;

public class OrderDetail implements IOrderDetail {

	private OrderDetailId id;
	private Integer visualorder;
	private Double listprice;
	private Double finalprice;
	private Double listcost;
	private Double finalcost;
	private Double units;
	private Double pendingunits;
	private Double receivedunits;
	private Double todeliveryunits;
	private Double outreceivedunits;
	private Double totalneed;
	private Double totalpending;
	private Double totalreceived;
	private Double totaltodelivery;
	private Order order;
	private Item item;
	private Unit packingunit;
	private Boolean hasatc;

	public Boolean getHasatc() {
		return hasatc;
	}
	public void setHasatc(Boolean hasatc) {
		this.hasatc = hasatc;
	}
	public OrderDetailId getId(){ 
		return this.id;
	}
	public Integer getVisualorder(){ 
		return this.visualorder;
	}
	public Double getListprice(){ 
		return this.listprice;
	}
	public Double getFinalprice(){ 
		return this.finalprice;
	}
	public Double getListcost(){ 
		return this.listcost;
	}
	public Double getFinalcost(){ 
		return this.finalcost;
	}
	public Double getUnits(){ 
		return this.units;
	}
	public Double getPendingunits(){ 
		return this.pendingunits;
	}
	public Double getReceivedunits(){ 
		return this.receivedunits;
	}
	public Double getTodeliveryunits(){ 
		return this.todeliveryunits;
	}
	public Double getOutreceivedunits(){ 
		return this.outreceivedunits;
	}
	public Double getTotalneed(){ 
		return this.totalneed;
	}
	public Double getTotalpending(){ 
		return this.totalpending;
	}
	public Double getTotalreceived(){ 
		return this.totalreceived;
	}
	public Double getTotaltodelivery(){ 
		return this.totaltodelivery;
	}
	public Order getOrder(){ 
		return this.order;
	}
	public Item getItem(){ 
		return this.item;
	}
	public Unit getPackingunit(){ 
		return this.packingunit;
	}
	public void setId(OrderDetailId id){ 
		this.id = id;
	}
	public void setVisualorder(Integer visualorder){ 
		this.visualorder = visualorder;
	}
	public void setListprice(Double listprice){ 
		this.listprice = listprice;
	}
	public void setFinalprice(Double finalprice){ 
		this.finalprice = finalprice;
	}
	public void setListcost(Double listcost){ 
		this.listcost = listcost;
	}
	public void setFinalcost(Double finalcost){ 
		this.finalcost = finalcost;
	}
	public void setUnits(Double units){ 
		this.units = units;
	}
	public void setPendingunits(Double pendingunits){ 
		this.pendingunits = pendingunits;
	}
	public void setReceivedunits(Double receivedunits){ 
		this.receivedunits = receivedunits;
	}
	public void setTodeliveryunits(Double todeliveryunits){ 
		this.todeliveryunits = todeliveryunits;
	}
	public void setOutreceivedunits(Double outreceivedunits){ 
		this.outreceivedunits = outreceivedunits;
	}
	public void setTotalneed(Double totalneed){ 
		this.totalneed = totalneed;
	}
	public void setTotalpending(Double totalpending){ 
		this.totalpending = totalpending;
	}
	public void setTotalreceived(Double totalreceived){ 
		this.totalreceived = totalreceived;
	}
	public void setTotaltodelivery(Double totaltodelivery){ 
		this.totaltodelivery = totaltodelivery;
	}
	public void setOrder(Order order){ 
		this.order = order;
	}
	public void setItem(Item item){ 
		this.item = item;
	}
	public void setPackingunit(Unit packingunit){ 
		this.packingunit = packingunit;
	}
}
