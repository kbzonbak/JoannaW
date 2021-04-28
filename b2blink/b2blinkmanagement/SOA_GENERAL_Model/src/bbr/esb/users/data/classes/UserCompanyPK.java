package bbr.esb.users.data.classes;

import bbr.common.adtclasses.interfaces.IPrimaryKey;
import bbr.esb.users.data.interfaces.IUserCompanyPK;

public class UserCompanyPK implements IUserCompanyPK {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5928414925054378698L;

	private Long companykey;

	private Long userkey;

	public int compareTo(IPrimaryKey arg0) {
		// TODO DVI TEST
		long result = companykey.longValue() - ((IUserCompanyPK) arg0).getCompanykey().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = userkey.longValue() - ((IUserCompanyPK) arg0).getUserkey().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Long getCompanykey() {
		return companykey;
	}

	public Long getUserkey() {
		return userkey;
	}

	public void setCompanykey(Long companykey) {
		this.companykey = companykey;
	}

	public void setUserkey(Long userkey) {
		this.userkey = userkey;
	}

}
