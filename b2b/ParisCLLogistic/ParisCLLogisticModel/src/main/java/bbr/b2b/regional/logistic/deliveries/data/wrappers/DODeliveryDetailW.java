package bbr.b2b.regional.logistic.deliveries.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IDODeliveryDetail;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IDODeliveryDetailPK;

public class DODeliveryDetailW implements IDODeliveryDetailPK, IDODeliveryDetail {

	private Double pendingunits = 0D;
	private Double availableunits = 0D;
	private Double receivedunits = 0D;
	private Long dodeliveryid;
	private Long itemid;

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = dodeliveryid.longValue() - ((IDODeliveryDetailPK) arg0).getDodeliveryid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IDODeliveryDetailPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
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
	public Long getDodeliveryid(){ 
		return this.dodeliveryid;
	}
	public Long getItemid(){ 
		return this.itemid;
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
	public void setDodeliveryid(Long dodeliveryid){ 
		this.dodeliveryid = dodeliveryid;
	}
	public void setItemid(Long itemid){ 
		this.itemid = itemid;
	}
}
