package bbr.b2b.regional.logistic.fillrate.entities;

import bbr.b2b.regional.logistic.fillrate.entities.FillRate;
import bbr.b2b.regional.logistic.items.entities.Item;
import bbr.b2b.regional.logistic.fillrate.data.interfaces.IFillRateDetail;

public class FillRateDetail implements IFillRateDetail {

	private FillRateDetailId id;
	private Double totalunits;
	private Double receivedunits;
	private FillRate fillrate;
	private Item item;

	public FillRateDetailId getId(){ 
		return this.id;
	}
	public Double getTotalunits(){ 
		return this.totalunits;
	}
	public Double getReceivedunits(){ 
		return this.receivedunits;
	}
	public FillRate getFillrate(){ 
		return this.fillrate;
	}
	public Item getItem(){ 
		return this.item;
	}
	public void setId(FillRateDetailId id){ 
		this.id = id;
	}
	public void setTotalunits(Double totalunits){ 
		this.totalunits = totalunits;
	}
	public void setReceivedunits(Double receivedunits){ 
		this.receivedunits = receivedunits;
	}
	public void setFillrate(FillRate fillrate){ 
		this.fillrate = fillrate;
	}
	public void setItem(Item item){ 
		this.item = item;
	}
}
