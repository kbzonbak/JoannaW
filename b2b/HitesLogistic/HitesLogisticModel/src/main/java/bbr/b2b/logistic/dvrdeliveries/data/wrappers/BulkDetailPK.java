package bbr.b2b.logistic.dvrdeliveries.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.dvrdeliveries.data.interfaces.IBulkDetailPK;

public class BulkDetailPK implements IBulkDetailPK {

	private Long bulkid;
	private Long dvrdeliveryid;
	private Long dvrorderid;
	private Long itemid;
	private Long locationid;
	private Integer position;

	public BulkDetailPK(){
	}
	public BulkDetailPK(Long bulkid, Long dvrdeliveryid, Long dvrorderid, Long itemid, Long locationid, Integer position){
		this.bulkid = bulkid;
		this.dvrdeliveryid = dvrdeliveryid;
		this.dvrorderid = dvrorderid;
		this.itemid = itemid;
		this.locationid = locationid;
		this.position = position;
	}

	public boolean equals(Object o){
		if (o != null && o instanceof BulkDetailPK){
			BulkDetailPK that = (BulkDetailPK) o;
			return this.bulkid.equals(that.bulkid) && this.dvrdeliveryid.equals(that.dvrdeliveryid) && this.dvrorderid.equals(that.dvrorderid) && this.itemid.equals(that.itemid) && this.locationid.equals(that.locationid) && this.position.equals(that.position);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return bulkid.hashCode() + dvrdeliveryid.hashCode() + dvrorderid.hashCode() + itemid.hashCode() + locationid.hashCode() + position.hashCode();
	}
	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = bulkid.longValue() - ((IBulkDetailPK) arg0).getBulkid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = dvrdeliveryid.longValue() - ((IBulkDetailPK) arg0).getDvrdeliveryid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = dvrorderid.longValue() - ((IBulkDetailPK) arg0).getDvrorderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IBulkDetailPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = locationid.longValue() - ((IBulkDetailPK) arg0).getLocationid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = position.longValue() - ((IBulkDetailPK) arg0).getPosition().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Long getBulkid(){ 
		return this.bulkid;
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
	public void setBulkid(Long bulkid){ 
		this.bulkid = bulkid;
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
