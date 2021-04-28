package bbr.b2b.regional.logistic.buyorders.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.buyorders.data.interfaces.IOrderDetail;
import bbr.b2b.regional.logistic.buyorders.data.interfaces.IOrderDetailPK;

public class OrderDetailW implements IOrderDetail, IOrderDetailPK {

	private Integer visualorder;
	private Double listprice;
	private Double finalprice;
	private Double listcost;
	private Double finalcost;
	private Double units;
	private Double pendingunits = 0D;
	private Double receivedunits = 0D;
	private Double todeliveryunits = 0D;
	private Double outreceivedunits = 0D;
	private Double totalneed = 0D;
	private Double totalpending = 0D;
	private Double totalreceived = 0D;
	private Double totaltodelivery = 0D;
	private Long orderid;
	private Long itemid;
	private Long packingunitid;
	private Boolean hasatc;

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = orderid.longValue() - ((IOrderDetailPK) arg0).getOrderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IOrderDetailPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
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
	public Long getOrderid(){ 
		return this.orderid;
	}
	public Long getItemid(){ 
		return this.itemid;
	}
	public Long getPackingunitid(){ 
		return this.packingunitid;
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
	public void setOrderid(Long orderid){ 
		this.orderid = orderid;
	}
	public void setItemid(Long itemid){ 
		this.itemid = itemid;
	}
	public void setPackingunitid(Long packingunitid){ 
		this.packingunitid = packingunitid;
	}
	public Boolean getHasatc() {
		return hasatc;
	}
	public void setHasatc(Boolean hasatc) {
		this.hasatc = hasatc;
	}
}
