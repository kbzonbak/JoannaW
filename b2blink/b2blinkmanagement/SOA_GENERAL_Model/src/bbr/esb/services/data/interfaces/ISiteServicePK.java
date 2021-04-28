package bbr.esb.services.data.interfaces;

import bbr.common.adtclasses.interfaces.IPrimaryKey;

public interface ISiteServicePK extends IPrimaryKey {

	Long getServicekey();

	Long getSitekey();

	void setServicekey(Long servicekey);

	void setSitekey(Long sitekey);

}
