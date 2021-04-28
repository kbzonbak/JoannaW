package bbr.b2b.logistic.dvrorders.entities;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.dvrorders.data.interfaces.IDvrOrderDetaiPK;

public class DvrOrderDetailId implements IDvrOrderDetaiPK {

	private Long dvrorderid;
	private Long itemid;
	private Integer position;

	public DvrOrderDetailId(){
	}
	public DvrOrderDetailId(Long dvrorderid, Long itemid, Integer position){
		this.dvrorderid = dvrorderid;
		this.itemid = itemid;
		this.position = position;
	}

	public boolean equals(Object o){
		if (o != null && o instanceof DvrOrderDetailId){
			DvrOrderDetailId that = (DvrOrderDetailId) o;
			return this.dvrorderid.equals(that.dvrorderid) && this.itemid.equals(that.itemid) && this.position.equals(that.position);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return dvrorderid.hashCode() + itemid.hashCode() + position.hashCode();
	}
	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = dvrorderid.longValue() - ((IDvrOrderDetaiPK) arg0).getDvrorderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IDvrOrderDetaiPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = position.longValue() - ((IDvrOrderDetaiPK) arg0).getPosition().longValue();
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
	public Integer getPosition(){ 
		return this.position;
	}
	public void setDvrorderid(Long dvrorderid){ 
		this.dvrorderid = dvrorderid;
	}
	public void setItemid(Long itemid){ 
		this.itemid = itemid;
	}
	public void setPosition(Integer position){ 
		this.position = position;
	}
}
