package bbr.b2b.logistic.dvrorders.entities;

import bbr.b2b.logistic.dvrorders.entities.DvrOrderDetail;
import bbr.b2b.logistic.locations.entities.Location;
import bbr.b2b.logistic.dvrorders.data.interfaces.IDvrPreDeliveryDetail;

public class DvrPreDeliveryDetail implements IDvrPreDeliveryDetail {

	private DvrPreDeliveryDetailId id;
	private Double totalunits;
	private Double todeliveryunits;
	private Double receivedunits;
	private Double pendingunits;
	private Double totalneed;
	private Double totaltodelivery;
	private Double totalreceived;
	private Double totalpending;
	private DvrOrderDetail dvrorderdetail;
	private Location location;

	public DvrPreDeliveryDetailId getId(){ 
		return this.id;
	}
	public Double getTotalunits(){ 
		return this.totalunits;
	}
	public Double getTodeliveryunits(){ 
		return this.todeliveryunits;
	}
	public Double getReceivedunits(){ 
		return this.receivedunits;
	}
	public Double getPendingunits(){ 
		return this.pendingunits;
	}
	public Double getTotalneed(){ 
		return this.totalneed;
	}
	public Double getTotaltodelivery(){ 
		return this.totaltodelivery;
	}
	public Double getTotalreceived(){ 
		return this.totalreceived;
	}
	public Double getTotalpending(){ 
		return this.totalpending;
	}
	public DvrOrderDetail getDvrorderdetail(){ 
		return this.dvrorderdetail;
	}
	public Location getLocation(){ 
		return this.location;
	}
	public void setId(DvrPreDeliveryDetailId id){ 
		this.id = id;
	}
	public void setTotalunits(Double totalunits){ 
		this.totalunits = totalunits;
	}
	public void setTodeliveryunits(Double todeliveryunits){ 
		this.todeliveryunits = todeliveryunits;
	}
	public void setReceivedunits(Double receivedunits){ 
		this.receivedunits = receivedunits;
	}
	public void setPendingunits(Double pendingunits){ 
		this.pendingunits = pendingunits;
	}
	public void setTotalneed(Double totalneed){ 
		this.totalneed = totalneed;
	}
	public void setTotaltodelivery(Double totaltodelivery){ 
		this.totaltodelivery = totaltodelivery;
	}
	public void setTotalreceived(Double totalreceived){ 
		this.totalreceived = totalreceived;
	}
	public void setTotalpending(Double totalpending){ 
		this.totalpending = totalpending;
	}
	public void setDvrorderdetail(DvrOrderDetail dvrorderdetail){ 
		this.dvrorderdetail = dvrorderdetail;
	}
	public void setLocation(Location location){ 
		this.location = location;
	}
}
