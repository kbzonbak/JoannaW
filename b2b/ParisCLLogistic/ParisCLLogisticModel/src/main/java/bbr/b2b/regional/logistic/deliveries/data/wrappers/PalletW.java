package bbr.b2b.regional.logistic.deliveries.data.wrappers;

import bbr.b2b.regional.logistic.deliveries.data.interfaces.IPallet;

public class PalletW extends BulkW implements IPallet {

	private Integer boxcount;

	public Integer getBoxcount(){ 
		return this.boxcount;
	}
	public void setBoxcount(Integer boxcount){ 
		this.boxcount = boxcount;
	}
}
