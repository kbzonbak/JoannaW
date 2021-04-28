package bbr.esb.services.entities;

import bbr.esb.services.data.interfaces.ISiteService;

public class SiteService implements ISiteService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1478735921328617115L;

	private boolean active;

	private SiteServiceKey id;

	private Service service;

	private Site site;

	public boolean getActive() {
		return active;
	}

	public SiteServiceKey getId() {
		return id;
	}

	public void setId(SiteServiceKey id) {
		this.id = id;
	}

	public Service getService() {
		return service;
	}

	public Site getSite() {
		return site;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public void setSite(Site site) {
		this.site = site;
	}

}
