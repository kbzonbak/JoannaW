package bbr.b2b.logistic.dvrdeliveries.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.dvrdeliveries.data.interfaces.IDvrOrderDeliveryPK;

public class DvrOrderDeliveryPK implements IDvrOrderDeliveryPK {

	private Long dvrorderid;
	private Long dvrdeliveryid;

	public DvrOrderDeliveryPK(){
	}
	public DvrOrderDeliveryPK(Long dvrorderid, Long dvrdeliveryid){
		this.dvrorderid = dvrorderid;
		this.dvrdeliveryid = dvrdeliveryid;
	}

	public boolean equals(Object o){
		if (o != null && o instanceof DvrOrderDeliveryPK){
			DvrOrderDeliveryPK that = (DvrOrderDeliveryPK) o;
			return this.dvrorderid.equals(that.dvrorderid) && this.dvrdeliveryid.equals(that.dvrdeliveryid);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return dvrorderid.hashCode() + dvrdeliveryid.hashCode();
	}
	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = dvrorderid.longValue() - ((IDvrOrderDeliveryPK) arg0).getDvrorderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = dvrdeliveryid.longValue() - ((IDvrOrderDeliveryPK) arg0).getDvrdeliveryid().longValue();
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
	public void setDvrorderid(Long dvrorderid){ 
		this.dvrorderid = dvrorderid;
	}
	public void setDvrdeliveryid(Long dvrdeliveryid){ 
		this.dvrdeliveryid = dvrdeliveryid;
	}
}
