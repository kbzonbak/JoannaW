package bbr.esb.users.entities;

import bbr.common.adtclasses.interfaces.IPrimaryKey;
import bbr.esb.users.data.interfaces.IUserCompanyPK;

public class UserCompanyKey implements IUserCompanyPK {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8569864326129103077L;

	private Long companykey;

	private Long userkey;

	public UserCompanyKey() {
	}

	public UserCompanyKey(Long companykey, Long userkey) {
		this.companykey = companykey;
		this.userkey = userkey;
	}

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

	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof UserCompanyKey) {
			UserCompanyKey that = (UserCompanyKey) o;
			return this.companykey.equals(that.companykey) && this.userkey.equals(that.userkey);
		} else {
			return false;
		}
	}

	public Long getCompanykey() {
		return companykey;
	}

	public Long getUserkey() {
		return userkey;
	}

	@Override
	public int hashCode() {
		return companykey.hashCode() + userkey.hashCode();
	}

	public void setCompanykey(Long companykey) {
		this.companykey = companykey;
	}

	public void setUserkey(Long userkey) {
		this.userkey = userkey;
	}

}
