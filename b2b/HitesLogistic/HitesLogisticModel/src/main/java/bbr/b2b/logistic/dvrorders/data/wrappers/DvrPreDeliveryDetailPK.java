package bbr.b2b.logistic.dvrorders.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.dvrorders.data.interfaces.IDvrPreDeliveryDetaiPK;

public class DvrPreDeliveryDetailPK implements IDvrPreDeliveryDetaiPK {

	private Long dvrorderid;
	private Long itemid;
	private Long locationid;
	private Integer position;

	public DvrPreDeliveryDetailPK(){
	}
	public DvrPreDeliveryDetailPK(Long dvrorderid, Long itemid, Long locationid, Integer position){
		this.dvrorderid = dvrorderid;
		this.itemid = itemid;
		this.locationid = locationid;
		this.position = position;
	}

	public boolean equals(Object o){
		if (o != null && o instanceof DvrPreDeliveryDetailPK){
			DvrPreDeliveryDetailPK that = (DvrPreDeliveryDetailPK) o;
			return this.dvrorderid.equals(that.dvrorderid) && this.itemid.equals(that.itemid) && this.locationid.equals(that.locationid) && this.position.equals(that.position);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return dvrorderid.hashCode() + itemid.hashCode() + locationid.hashCode() + position.hashCode();
	}
	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = dvrorderid.longValue() - ((IDvrPreDeliveryDetaiPK) arg0).getDvrorderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IDvrPreDeliveryDetaiPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = locationid.longValue() - ((IDvrPreDeliveryDetaiPK) arg0).getLocationid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = position.longValue() - ((IDvrPreDeliveryDetaiPK) arg0).getPosition().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
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
