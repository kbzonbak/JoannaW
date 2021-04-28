package bbr.b2b.regional.logistic.datings.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.datings.data.interfaces.IDatingResourcePK;

public class DatingResourcePK implements IDatingResourcePK {

	private Long dockid;
	private Long moduleid;

	public DatingResourcePK(){
	}
	public DatingResourcePK(Long dockid, Long moduleid){
		this.dockid = dockid;
		this.moduleid = moduleid;
	}

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
	public boolean equals(Object o){
		if (o != null && o instanceof DatingResourcePK){
			DatingResourcePK that = (DatingResourcePK) o;
			return this.dockid.equals(that.dockid) && this.moduleid.equals(that.moduleid);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return dockid.hashCode() + moduleid.hashCode();
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
