package bbr.b2b.logistic.datings.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.datings.data.interfaces.IDatingResourcPK;
import bbr.b2b.logistic.datings.data.interfaces.IDatingResource;

public class DatingResourceW implements IDatingResourcPK, IDatingResource {

	private Boolean active;
	private Integer visualorder;
	private Long dockid;
	private Long moduleid;

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = dockid.longValue() - ((IDatingResourcPK) arg0).getDockid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = moduleid.longValue() - ((IDatingResourcPK) arg0).getModuleid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Boolean getActive(){ 
		return this.active;
	}
	public Integer getVisualorder(){ 
		return this.visualorder;
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
	public void setVisualorder(Integer visualorder){ 
		this.visualorder = visualorder;
	}
	public void setDockid(Long dockid){ 
		this.dockid = dockid;
	}
	public void setModuleid(Long moduleid){ 
		this.moduleid = moduleid;
	}
}
