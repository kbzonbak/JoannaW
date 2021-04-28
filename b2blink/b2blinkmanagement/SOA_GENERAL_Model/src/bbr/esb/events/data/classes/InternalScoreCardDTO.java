package bbr.esb.events.data.classes;

import bbr.common.adtclasses.classes.ElementDTO;

public class InternalScoreCardDTO extends ElementDTO{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Long count; 
	String sitename;
	String retailname;
	String  statusname;
	
	
	public String getRetailname() {
		return retailname;
	}
	public void setRetailname(String retailname) {
		this.retailname = retailname;
	}
	
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public String getSitename() {
		return sitename;
	}
	public void setSitename(String sitename) {
		this.sitename = sitename;
	}
	public String getStatus() {
		return statusname;
	}
	public void setStatus(String statusname) {
		this.statusname = statusname;
	}
	
	
	
	
}
