package bbr.b2b.logistic.dvrdeliveries.entities;

import bbr.b2b.logistic.dvrdeliveries.entities.DvrOrderDelivery;
import bbr.b2b.logistic.dvrorders.entities.DvrPreDeliveryDetail;
import bbr.b2b.logistic.dvrdeliveries.data.interfaces.IDvrOrderDeliveryDetail;

public class DvrOrderDeliveryDetail implements IDvrOrderDeliveryDetail {

	private DvrOrderDeliveryDetailId id;
	private Double pendingunits;
	private Double availableunits;
	private Double receivedunits;
	private DvrOrderDelivery dvrorderdelivery;
	private DvrPreDeliveryDetail dvrpredeliverydetail;

	public DvrOrderDeliveryDetailId getId(){ 
		return this.id;
	}
	public Double getPendingunits(){ 
		return this.pendingunits;
	}
	public Double getAvailableunits(){ 
		return this.availableunits;
	}
	public Double getReceivedunits(){ 
		return this.receivedunits;
	}
	public DvrOrderDelivery getDvrorderdelivery(){ 
		return this.dvrorderdelivery;
	}
	public DvrPreDeliveryDetail getDvrpredeliverydetail(){ 
		return this.dvrpredeliverydetail;
	}
	public void setId(DvrOrderDeliveryDetailId id){ 
		this.id = id;
	}
	public void setPendingunits(Double pendingunits){ 
		this.pendingunits = pendingunits;
	}
	public void setAvailableunits(Double availableunits){ 
		this.availableunits = availableunits;
	}
	public void setReceivedunits(Double receivedunits){ 
		this.receivedunits = receivedunits;
	}
	public void setDvrorderdelivery(DvrOrderDelivery dvrorderdelivery){ 
		this.dvrorderdelivery = dvrorderdelivery;
	}
	public void setDvrpredeliverydetail(DvrPreDeliveryDetail dvrpredeliverydetail){ 
		this.dvrpredeliverydetail = dvrpredeliverydetail;
	}
}
