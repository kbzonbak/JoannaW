package bbr.esb.users.data.classes;

import bbr.common.adtclasses.interfaces.IPrimaryKey;
import bbr.esb.users.data.interfaces.IUserCompany;
import bbr.esb.users.data.interfaces.IUserCompanyPK;

public class UserCompanyDTO implements IUserCompany, IUserCompanyPK {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1260981217173104245L;

	private boolean active;

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

	public boolean getActive() {
		return active;
	}

	public Long getCompanykey() {
		return companykey;
	}

	public Long getUserkey() {
		return userkey;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setCompanykey(Long companykey) {
		this.companykey = companykey;
	}

	public void setUserkey(Long userkey) {
		this.userkey = userkey;
	}

}
