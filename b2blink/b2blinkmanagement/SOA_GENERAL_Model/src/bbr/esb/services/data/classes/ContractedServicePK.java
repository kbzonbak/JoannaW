package bbr.esb.services.data.classes;

import bbr.common.adtclasses.interfaces.IPrimaryKey;
import bbr.esb.services.data.interfaces.IContractedServicePK;

public class ContractedServicePK implements IContractedServicePK {

	/**
	 * 
	 */
	private static final long serialVersionUID = 58868737980613231L;

	private Long companykey;

	private Long servicekey;

	private Long sitekey;

	public int compareTo(IPrimaryKey arg0) {
		// TODO DVI TEST
		long result = sitekey.longValue() - ((IContractedServicePK) arg0).getSitekey().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = servicekey.longValue() - ((IContractedServicePK) arg0).getServicekey().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = companykey.longValue() - ((IContractedServicePK) arg0).getCompanykey().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Long getCompanykey() {
		return companykey;
	}

	public Long getServicekey() {
		return servicekey;
	}

	public Long getSitekey() {
		return sitekey;
	}

	public void setCompanykey(Long companykey) {
		this.companykey = companykey;
	}

	public void setServicekey(Long servicekey) {
		this.servicekey = servicekey;
	}

	public void setSitekey(Long sitekey) {
		this.sitekey = sitekey;
	}

}
