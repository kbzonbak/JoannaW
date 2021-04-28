package bbr.b2b.logistic.datings.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IDatingResourcPK extends IPrimaryKey {

	public Long getDockid();
	public Long getModuleid();
	public void setDockid(Long dockid);
	public void setModuleid(Long moduleid);
}
