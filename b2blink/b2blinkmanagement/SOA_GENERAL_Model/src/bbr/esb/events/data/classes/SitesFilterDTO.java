package bbr.esb.events.data.classes;

import bbr.common.adtclasses.classes.BaseResultDTO;
import bbr.esb.services.data.classes.SiteDTO;

public class SitesFilterDTO extends BaseResultDTO{

	private static final long serialVersionUID = 1L;

	private SiteDTO[] sites;

	public SiteDTO[] getSites() {
		return sites;
	}

	public void setSites(SiteDTO[] sites) {
		this.sites = sites;
	}
}
