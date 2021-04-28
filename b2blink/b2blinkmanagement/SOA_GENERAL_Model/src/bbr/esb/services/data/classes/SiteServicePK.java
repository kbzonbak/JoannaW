package bbr.esb.services.data.classes;

import bbr.common.adtclasses.interfaces.IPrimaryKey;
import bbr.esb.services.data.interfaces.ISiteServicePK;

public class SiteServicePK implements ISiteServicePK {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3261086149109478072L;

	private Long servicekey;

	private Long sitekey;

	public int compareTo(IPrimaryKey arg0) {
		// TODO DVI TEST
		long result = servicekey.longValue() - ((ISiteServicePK) arg0).getServicekey().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = sitekey.longValue() - ((ISiteServicePK) arg0).getSitekey().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Long getServicekey() {
		return servicekey;
	}

	public Long getSitekey() {
		return sitekey;
	}

	public void setServicekey(Long servicekey) {
		this.servicekey = servicekey;
	}

	public void setSitekey(Long sitekey) {
		this.sitekey = sitekey;
	}

}
