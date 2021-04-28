package bbr.b2b.logistic.datings.entities;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.datings.data.interfaces.IDatingResourcPK;

public class DatingResourceId implements IDatingResourcPK {

	private Long dockid;
	private Long moduleid;

	public DatingResourceId(){
	}
	public DatingResourceId(Long dockid, Long moduleid){
		this.dockid = dockid;
		this.moduleid = moduleid;
	}

	public boolean equals(Object o){
		if (o != null && o instanceof DatingResourceId){
			DatingResourceId that = (DatingResourceId) o;
			return this.dockid.equals(that.dockid) && this.moduleid.equals(that.moduleid);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return dockid.hashCode() + moduleid.hashCode();
	}
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

	public Long getDockid(){ 
		return this.dockid;
	}
	public Long getModuleid(){ 
		return this.moduleid;
	}
	public void setDockid(Long dockid){ 
		this.dockid = dockid;
	}
	public void setModuleid(Long moduleid){ 
		this.moduleid = moduleid;
	}
}
