package bbr.esb.users.data.classes;

import bbr.common.adtclasses.interfaces.IPrimaryKey;
import bbr.esb.users.data.interfaces.IAccess;
import bbr.esb.users.data.interfaces.IAccessPK;

public class AccessDTO implements IAccess, IAccessPK {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8675210994866902819L;

	private String code;
	private Long companykey;
	private String name;
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

	public String getCode() {
		return code;
	}

	public Long getCompanykey() {
		return companykey;
	}

	public String getName() {
		return name;
	}

	public Long getSitekey() {
		return sitekey;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCompanykey(Long companykey) {
		this.companykey = companykey;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSitekey(Long sitekey) {
		this.sitekey = sitekey;
	}

}
