package bbr.esb.services.entities;

import bbr.common.adtclasses.interfaces.IPrimaryKey;
import bbr.esb.services.data.interfaces.IContractedServicePK;

public class ContractedServiceKey implements IContractedServicePK {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3974536541067866963L;
	private Long companykey;
	private Long servicekey;
	private Long sitekey;

	public ContractedServiceKey() {
	}
	
	public ContractedServiceKey(Long sitekey, Long servicekey, Long companykey) {
		this.servicekey = servicekey;
		this.sitekey = sitekey;
		this.companykey = companykey;
	}
	
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

	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof ContractedServiceKey) {
			ContractedServiceKey that = (ContractedServiceKey) o;
			return this.companykey.equals(that.companykey) && this.servicekey.equals(that.servicekey) && this.sitekey.equals(that.sitekey);
		} else {
			return false;
		}
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

	@Override
	public int hashCode() {
		return sitekey.hashCode() + servicekey.hashCode() + companykey.hashCode();
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
