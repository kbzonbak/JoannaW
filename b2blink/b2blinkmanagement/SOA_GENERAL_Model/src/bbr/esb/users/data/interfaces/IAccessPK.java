package bbr.esb.users.data.interfaces;

import bbr.common.adtclasses.interfaces.IPrimaryKey;

public interface IAccessPK extends IPrimaryKey {

	Long getCompanykey();

	Long getSitekey();

	void setCompanykey(Long companykey);

	void setSitekey(Long sitekey);

}
