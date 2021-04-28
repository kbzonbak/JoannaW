package bbr.b2b.regional.logistic.fillrate.entities;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.fillrate.data.interfaces.IFillRateDetailPK;

public class FillRateDetailId implements IFillRateDetailPK {

	private Long fillrateid;
	private Long itemid;

	public FillRateDetailId(){
	}
	public FillRateDetailId(Long fillrateid, Long itemid){
		this.fillrateid = fillrateid;
		this.itemid = itemid;
	}

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = fillrateid.longValue() - ((IFillRateDetailPK) arg0).getFillrateid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IFillRateDetailPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}
	public boolean equals(Object o){
		if (o != null && o instanceof FillRateDetailId){
			FillRateDetailId that = (FillRateDetailId) o;
			return this.fillrateid.equals(that.fillrateid) && this.itemid.equals(that.itemid);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return fillrateid.hashCode() + itemid.hashCode();
	}

	public Long getFillrateid(){ 
		return this.fillrateid;
	}
	public Long getItemid(){ 
		return this.itemid;
	}
	public void setFillrateid(Long fillrateid){ 
		this.fillrateid = fillrateid;
	}
	public void setItemid(Long itemid){ 
		this.itemid = itemid;
	}
}
