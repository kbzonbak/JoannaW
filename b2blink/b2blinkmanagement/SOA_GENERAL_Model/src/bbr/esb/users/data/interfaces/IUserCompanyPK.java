package bbr.esb.users.data.interfaces;

import bbr.common.adtclasses.interfaces.IPrimaryKey;

public interface IUserCompanyPK extends IPrimaryKey {

	Long getCompanykey();

	Long getUserkey();

	void setCompanykey(Long companykey);

	void setUserkey(Long userkey);

}
