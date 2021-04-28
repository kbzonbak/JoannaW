package bbr.esb.services.data.interfaces;

import bbr.common.adtclasses.interfaces.IPrimaryKey;

public interface IContractedServicePK extends IPrimaryKey {

	Long getCompanykey();

	Long getServicekey();

	Long getSitekey();

	void setCompanykey(Long companykey);

	void setServicekey(Long servicekey);

	void setSitekey(Long sitekey);

}
