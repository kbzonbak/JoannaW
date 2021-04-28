package bbr.esb.services.entities;

import bbr.common.adtclasses.interfaces.IPrimaryKey;
import bbr.esb.services.data.interfaces.ISiteServicePK;

public class SiteServiceKey implements ISiteServicePK {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4455668100736328080L;
	private Long servicekey;
	private Long sitekey;
	
	public SiteServiceKey() {
	}
	
	public SiteServiceKey(Long sitekey, Long servicekey) {
		this.servicekey = servicekey;
		this.sitekey = sitekey;
	}

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

	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof SiteServiceKey) {
			SiteServiceKey that = (SiteServiceKey) o;
			return this.servicekey.equals(that.servicekey) && this.sitekey.equals(that.sitekey);
		} else {
			return false;
		}
	}

	public Long getServicekey() {
		return servicekey;
	}

	public Long getSitekey() {
		return sitekey;
	}

	@Override
	public int hashCode() {
		return sitekey.hashCode() + servicekey.hashCode();
	}

	public void setServicekey(Long servicekey) {
		this.servicekey = servicekey;
	}

	public void setSitekey(Long sitekey) {
		this.sitekey = sitekey;
	}

}
