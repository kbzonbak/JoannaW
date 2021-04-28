package bbr.b2b.logistic.customer.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.customer.data.interfaces.IReceptionLocatioPK;

public class ReceptionLocatioPK implements IReceptionLocatioPK {

	private Long receptionid;
	private Long locationid;

	public ReceptionLocatioPK(){
	}
	public ReceptionLocatioPK(Long receptionid, Long locationid){
		this.receptionid = receptionid;
		this.locationid = locationid;
	}

	public boolean equals(Object o){
		if (o != null && o instanceof ReceptionLocatioPK){
			ReceptionLocatioPK that = (ReceptionLocatioPK) o;
			return this.receptionid.equals(that.receptionid) && this.locationid.equals(that.locationid);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return receptionid.hashCode() + locationid.hashCode();
	}
	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = receptionid.longValue() - ((IReceptionLocatioPK) arg0).getReceptionid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = locationid.longValue() - ((IReceptionLocatioPK) arg0).getLocationid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Long getReceptionid(){ 
		return this.receptionid;
	}
	public Long getLocationid(){ 
		return this.locationid;
	}
	public void setReceptionid(Long receptionid){ 
		this.receptionid = receptionid;
	}
	public void setLocationid(Long locationid){ 
		this.locationid = locationid;
	}
}
