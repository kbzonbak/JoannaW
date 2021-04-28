package bbr.esb.users.data.classes;

import bbr.common.adtclasses.interfaces.IPrimaryKey;
import bbr.esb.users.data.interfaces.IAccessPK;

public class AccessPK implements IAccessPK {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5935814994524634231L;

	private Long companykey;

	private Long sitekey;

	public int compareTo(IPrimaryKey arg0) {
		// TODO DVI TEST
		long result = sitekey.longValue() - ((IAccessPK) arg0).getSitekey().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = companykey.longValue() - ((IAccessPK) arg0).getCompanykey().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Long getCompanykey() {
		return companykey;
	}

	public Long getSitekey() {
		return sitekey;
	}

	public void setCompanykey(Long companykey) {
		this.companykey = companykey;
	}

	public void setSitekey(Long sitekey) {
		this.sitekey = sitekey;
	}

}
