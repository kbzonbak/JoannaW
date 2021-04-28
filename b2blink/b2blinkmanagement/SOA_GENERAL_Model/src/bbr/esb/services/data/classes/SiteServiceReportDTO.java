package bbr.esb.services.data.classes;

import java.io.Serializable;

public class SiteServiceReportDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7043250689224605027L;

	private boolean active;

	private Long servicekey;

	private Long sitekey;

	private String sitename;

	private String servicename;

	public boolean getActive() {
		return active;
	}

	public Long getServicekey() {
		return servicekey;
	}

	public String getServicename() {
		return servicename;
	}

	public Long getSitekey() {
		return sitekey;
	}

	public String getSitename() {
		return sitename;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setServicekey(Long servicekey) {
		this.servicekey = servicekey;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	public void setSitekey(Long sitekey) {
		this.sitekey = sitekey;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}
}
