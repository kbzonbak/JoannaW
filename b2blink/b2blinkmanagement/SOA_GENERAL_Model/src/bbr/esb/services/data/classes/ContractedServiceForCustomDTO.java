package bbr.esb.services.data.classes;

import bbr.esb.services.data.interfaces.IContractedServiceForCustomDTO;

public class ContractedServiceForCustomDTO implements IContractedServiceForCustomDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5739839940847868736L;

	

	private Long siteid;

	private Long serviceid;

	private Long companyid;
	
	private String custom;

	public Long getSiteid() {
		return siteid;
	}

	public void setSiteid(Long siteid) {
		this.siteid = siteid;
	}

	public Long getServiceid() {
		return serviceid;
	}

	public void setServiceid(Long serviceid) {
		this.serviceid = serviceid;
	}

	public Long getCompanyid() {
		return companyid;
	}

	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}

	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	

	
	

}
