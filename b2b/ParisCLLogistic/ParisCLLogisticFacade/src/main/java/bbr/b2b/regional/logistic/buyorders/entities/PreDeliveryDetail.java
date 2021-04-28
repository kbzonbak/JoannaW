package bbr.b2b.regional.logistic.buyorders.entities;

import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.buyorders.entities.OrderDetail;
import bbr.b2b.regional.logistic.buyorders.data.interfaces.IPreDeliveryDetail;

public class PreDeliveryDetail implements IPreDeliveryDetail {

	private PreDeliveryDetailId id;
	private Integer sequence;
	private Double units;
	private Double pendingunits;
	private Double receivedunits;
	private Double todeliveryunits;
	private Double outreceivedunits;
	private Double totalneed;
	private Double totalpending;
	private Double totalreceived;
	private Double totaltodelivery;
	private Location location;
	private OrderDetail orderdetail;

	public PreDeliveryDetailId getId(){ 
		return this.id;
	}
	public Integer getSequence(){ 
		return this.sequence;
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
	public Location getLocation(){ 
		return this.location;
	}
	public OrderDetail getOrderdetail(){ 
		return this.orderdetail;
	}
	public void setId(PreDeliveryDetailId id){ 
		this.id = id;
	}
	public void setSequence(Integer sequence){ 
		this.sequence = sequence;
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
	public void setLocation(Location location){ 
		this.location = location;
	}
	public void setOrderdetail(OrderDetail orderdetail){ 
		this.orderdetail = orderdetail;
	}
}
