package bbr.b2b.logistic.dvrdeliveries.entities;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.dvrdeliveries.data.interfaces.IDvrOrderDeliveryDetailPK;

public class DvrOrderDeliveryDetailId implements IDvrOrderDeliveryDetailPK {

	private Long dvrorderid;
	private Long dvrdeliveryid;
	private Long itemid;
	private Long locationid;
	private Integer position;

	public DvrOrderDeliveryDetailId(){
	}
	public DvrOrderDeliveryDetailId(Long dvrorderid, Long dvrdeliveryid, Long itemid, Long locationid, Integer position){
		this.dvrorderid = dvrorderid;
		this.dvrdeliveryid = dvrdeliveryid;
		this.itemid = itemid;
		this.locationid = locationid;
		this.position = position;
	}

	public boolean equals(Object o){
		if (o != null && o instanceof DvrOrderDeliveryDetailId){
			DvrOrderDeliveryDetailId that = (DvrOrderDeliveryDetailId) o;
			return this.dvrorderid.equals(that.dvrorderid) && this.dvrdeliveryid.equals(that.dvrdeliveryid) && this.itemid.equals(that.itemid) && this.locationid.equals(that.locationid) && this.position.equals(that.position);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return dvrorderid.hashCode() + dvrdeliveryid.hashCode() + itemid.hashCode() + locationid.hashCode() + position.hashCode();
	}
	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = dvrorderid.longValue() - ((IDvrOrderDeliveryDetailPK) arg0).getDvrorderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = dvrdeliveryid.longValue() - ((IDvrOrderDeliveryDetailPK) arg0).getDvrdeliveryid().longValue();
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

	public Long getDvrorderid(){ 
		return this.dvrorderid;
	}
	public Long getDvrdeliveryid(){ 
		return this.dvrdeliveryid;
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
	public void setDvrorderid(Long dvrorderid){ 
		this.dvrorderid = dvrorderid;
	}
	public void setDvrdeliveryid(Long dvrdeliveryid){ 
		this.dvrdeliveryid = dvrdeliveryid;
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
