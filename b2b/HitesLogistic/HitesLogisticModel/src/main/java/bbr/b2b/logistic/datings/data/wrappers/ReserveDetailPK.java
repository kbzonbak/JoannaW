package bbr.b2b.logistic.datings.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.datings.data.interfaces.IReserveDetailPK;

public class ReserveDetailPK implements IReserveDetailPK {

	private Long reserveid;
	private Long moduleid;
	private Long dockid;

	public ReserveDetailPK(){
	}
	public ReserveDetailPK(Long reserveid, Long moduleid, Long dockid){
		this.reserveid = reserveid;
		this.moduleid = moduleid;
		this.dockid = dockid;
	}

	public boolean equals(Object o){
		if (o != null && o instanceof ReserveDetailPK){
			ReserveDetailPK that = (ReserveDetailPK) o;
			return this.reserveid.equals(that.reserveid) && this.moduleid.equals(that.moduleid) && this.dockid.equals(that.dockid);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return reserveid.hashCode() + moduleid.hashCode() + dockid.hashCode();
	}
	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = reserveid.longValue() - ((IReserveDetailPK) arg0).getReserveid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = moduleid.longValue() - ((IReserveDetailPK) arg0).getModuleid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = dockid.longValue() - ((IReserveDetailPK) arg0).getDockid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Long getReserveid(){ 
		return this.reserveid;
	}
	public Long getModuleid(){ 
		return this.moduleid;
	}
	public Long getDockid(){ 
		return this.dockid;
	}
	public void setReserveid(Long reserveid){ 
		this.reserveid = reserveid;
	}
	public void setModuleid(Long moduleid){ 
		this.moduleid = moduleid;
	}
	public void setDockid(Long dockid){ 
		this.dockid = dockid;
	}
}
