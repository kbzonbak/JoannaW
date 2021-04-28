package bbr.b2b.logistic.dvrdeliveries.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.dvrdeliveries.data.interfaces.IDvrOrderDeliveryDetail;
import bbr.b2b.logistic.dvrdeliveries.data.interfaces.IDvrOrderDeliveryDetailPK;

public class DvrOrderDeliveryDetailW implements IDvrOrderDeliveryDetail, IDvrOrderDeliveryDetailPK {

	private Double pendingunits;
	private Double availableunits;
	private Double receivedunits;
	private Long dvrdeliveryid;
	private Long dvrorderid;
	private Long itemid;
	private Long locationid;
	private Integer position;

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = dvrdeliveryid.longValue() - ((IDvrOrderDeliveryDetailPK) arg0).getDvrdeliveryid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = dvrorderid.longValue() - ((IDvrOrderDeliveryDetailPK) arg0).getDvrorderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IDvrOrderDeliveryDetailPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = locationid.longValue() - ((IDvrOrderDeliveryDetailPK) arg0).getLocationid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = position.longValue() - ((IDvrOrderDeliveryDetailPK) arg0).getPosition().longValue();
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
	public Long getDvrdeliveryid(){ 
		return this.dvrdeliveryid;
	}
	public Long getDvrorderid(){ 
		return this.dvrorderid;
	}
	public Long getItemid(){ 
		return this.itemid;
	}
	public Long getLocationid(){ 
		return this.locationid;
	}
	public Integer getPosition(){ 
		return this.position;
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
	public void setDvrdeliveryid(Long dvrdeliveryid){ 
		this.dvrdeliveryid = dvrdeliveryid;
	}
	public void setDvrorderid(Long dvrorderid){ 
		this.dvrorderid = dvrorderid;
	}
	public void setItemid(Long itemid){ 
		this.itemid = itemid;
	}
	public void setLocationid(Long locationid){ 
		this.locationid = locationid;
	}
	public void setPosition(Integer position){ 
		this.position = position;
	}
}
