package bbr.b2b.regional.logistic.datings.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.datings.data.interfaces.IReserveDetail;
import bbr.b2b.regional.logistic.datings.data.interfaces.IReserveDetailPK;

public class ReserveDetailW implements IReserveDetailPK, IReserveDetail {

	private Date when;
	private Long dockid;
	private Long moduleid;
	private Long reserveid;

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

	public Date getWhen() {
		return when;
	}
	public void setWhen(Date when) {
		this.when = when;
	}
	public Long getDockid(){ 
		return this.dockid;
	}
	public Long getModuleid(){ 
		return this.moduleid;
	}
	public Long getReserveid(){ 
		return this.reserveid;
	}
	public void setDockid(Long dockid){ 
		this.dockid = dockid;
	}
	public void setModuleid(Long moduleid){ 
		this.moduleid = moduleid;
	}
	public void setReserveid(Long reserveid){ 
		this.reserveid = reserveid;
	}
}
