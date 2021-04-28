package bbr.b2b.logistic.dvrdeliveries.entities;

import bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery;
import bbr.b2b.logistic.dvrorders.entities.DvrOrder;

import java.time.LocalDateTime;

import bbr.b2b.logistic.dvrdeliveries.data.interfaces.IDvrOrderDelivery;

public class DvrOrderDelivery implements IDvrOrderDelivery {

	private DvrOrderDeliveryId id;
	private Boolean closed;
	private Double estimatedunits;
	private LocalDateTime receptiondate;
	private DvrDelivery dvrdelivery;
	private DvrOrder dvrorder;

	public DvrOrderDeliveryId getId(){ 
		return this.id;
	}
	public Boolean getClosed(){ 
		return this.closed;
	}
	public Double getEstimatedunits(){ 
		return this.estimatedunits;
	}
	public LocalDateTime getReceptiondate(){ 
		return this.receptiondate;
	}
	public DvrDelivery getDvrdelivery(){ 
		return this.dvrdelivery;
	}
	public DvrOrder getDvrorder(){ 
		return this.dvrorder;
	}
	public void setId(DvrOrderDeliveryId id){ 
		this.id = id;
	}
	public void setClosed(Boolean closed){ 
		this.closed = closed;
	}
	public void setEstimatedunits(Double estimatedunits){ 
		this.estimatedunits = estimatedunits;
	}
	public void setReceptiondate(LocalDateTime receptiondate){ 
		this.receptiondate = receptiondate;
	}
	public void setDvrdelivery(DvrDelivery dvrdelivery){ 
		this.dvrdelivery = dvrdelivery;
	}
	public void setDvrorder(DvrOrder dvrorder){ 
		this.dvrorder = dvrorder;
	}
}
