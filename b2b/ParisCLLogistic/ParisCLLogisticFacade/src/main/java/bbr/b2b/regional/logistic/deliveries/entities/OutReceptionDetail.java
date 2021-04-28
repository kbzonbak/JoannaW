package bbr.b2b.regional.logistic.deliveries.entities;

import bbr.b2b.regional.logistic.deliveries.entities.OutReception;
import bbr.b2b.regional.logistic.buyorders.entities.PreDeliveryDetail;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IOutReceptionDetail;

public class OutReceptionDetail implements IOutReceptionDetail {

	private OutReceptionDetailId id;
	private Double receivedunits;
	private OutReception outreception;
	private PreDeliveryDetail predeliverydetail;

	public OutReceptionDetailId getId(){ 
		return this.id;
	}
	public Double getReceivedunits(){ 
		return this.receivedunits;
	}
	public OutReception getOutreception(){ 
		return this.outreception;
	}
	public PreDeliveryDetail getPredeliverydetail(){ 
		return this.predeliverydetail;
	}
	public void setId(OutReceptionDetailId id){ 
		this.id = id;
	}
	public void setReceivedunits(Double receivedunits){ 
		this.receivedunits = receivedunits;
	}
	public void setOutreception(OutReception outreception){ 
		this.outreception = outreception;
	}
	public void setPredeliverydetail(PreDeliveryDetail predeliverydetail){ 
		this.predeliverydetail = predeliverydetail;
	}
}
