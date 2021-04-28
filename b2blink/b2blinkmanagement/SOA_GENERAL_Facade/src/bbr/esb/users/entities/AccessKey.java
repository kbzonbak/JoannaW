package bbr.esb.users.entities;

import bbr.common.adtclasses.interfaces.IPrimaryKey;
import bbr.esb.users.data.interfaces.IAccessPK;

public class AccessKey implements IAccessPK {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6989432407735491856L;
	private Long companykey;
	private Long sitekey;
	
	public AccessKey() {
	}

	public AccessKey(Long companykey, Long sitekey) {
		this.companykey = companykey;
		this.sitekey = sitekey;
	}

	public int compareTo(IPrimaryKey arg0) {
		// TODO DVI TEST
		long result = companykey.longValue() - ((IAccessPK) arg0).getCompanykey().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = sitekey.longValue() - ((IAccessPK) arg0).getSitekey().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof AccessKey) {
			AccessKey that = (AccessKey) o;
			return this.companykey.equals(that.companykey) && this.sitekey.equals(that.sitekey);
		} else {
			return false;
		}
	}

	public Long getCompanykey() {
		return companykey;
	}

	public Long getSitekey() {
		return sitekey;
	}

	@Override
	public int hashCode() {
		return companykey.hashCode() + sitekey.hashCode();
	}

	public void setCompanykey(Long companykey) {
		this.companykey = companykey;
	}

	public void setSitekey(Long sitekey) {
		this.sitekey = sitekey;
	}

}
