package bbr.b2b.regional.logistic.datings.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.datings.data.interfaces.IDatingResource;
import bbr.b2b.regional.logistic.datings.data.interfaces.IDatingResourcePK;

public class DatingResourceW implements IDatingResourcePK, IDatingResource {

	private Boolean active;
	private Long dockid;
	private Long moduleid;

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = dockid.longValue() - ((IDatingResourcePK) arg0).getDockid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = moduleid.longValue() - ((IDatingResourcePK) arg0).getModuleid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Boolean getActive(){ 
		return this.active;
	}
	public Long getDockid(){ 
		return this.dockid;
	}
	public Long getModuleid(){ 
		return this.moduleid;
	}
	public void setActive(Boolean active){ 
		this.active = active;
	}
	public void setDockid(Long dockid){ 
		this.dockid = dockid;
	}
	public void setModuleid(Long moduleid){ 
		this.moduleid = moduleid;
	}
}
