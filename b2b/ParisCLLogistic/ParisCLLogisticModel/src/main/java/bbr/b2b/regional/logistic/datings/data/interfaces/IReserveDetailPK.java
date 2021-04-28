package bbr.b2b.regional.logistic.datings.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IReserveDetailPK extends IPrimaryKey {

	public Long getReserveid();
	public Long getModuleid();
	public Long getDockid();
	public void setReserveid(Long reserveid);
	public void setModuleid(Long moduleid);
	public void setDockid(Long dockid);
}
