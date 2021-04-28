package bbr.b2b.regional.logistic.deliveries.entities;

import bbr.b2b.regional.logistic.deliveries.entities.Bulk;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IPallet;

public class Pallet extends Bulk implements IPallet {

	private Integer boxcount;

	public Integer getBoxcount(){ 
		return this.boxcount;
	}
	public void setBoxcount(Integer boxcount){ 
		this.boxcount = boxcount;
	}
}
