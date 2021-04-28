package bbr.b2b.regional.logistic.deliveries.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IOrderDeliveryDetail;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IOrderDeliveryDetailPK;

public class OrderDeliveryDetailW implements IOrderDeliveryDetailPK, IOrderDeliveryDetail {

	private Double pendingunits = 0D;
	private Double availableunits = 0D;
	private Double receivedunits = 0D;
	private Long orderid;
	private Long deliveryid;
	private Long itemid;
	private Long locationid;

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = orderid.longValue() - ((IOrderDeliveryDetailPK) arg0).getOrderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = deliveryid.longValue() - ((IOrderDeliveryDetailPK) arg0).getDeliveryid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IOrderDeliveryDetailPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = locationid.longValue() - ((IOrderDeliveryDetailPK) arg0).getLocationid().longValue();
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
	public Long getOrderid(){ 
		return this.orderid;
	}
	public Long getDeliveryid(){ 
		return this.deliveryid;
	}
	public Long getItemid(){ 
		return this.itemid;
	}
	public Long getLocationid(){ 
		return this.locationid;
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
	public void setOrderid(Long orderid){ 
		this.orderid = orderid;
	}
	public void setDeliveryid(Long deliveryid){ 
		this.deliveryid = deliveryid;
	}
	public void setItemid(Long itemid){ 
		this.itemid = itemid;
	}
	public void setLocationid(Long locationid){ 
		this.locationid = locationid;
	}
}
