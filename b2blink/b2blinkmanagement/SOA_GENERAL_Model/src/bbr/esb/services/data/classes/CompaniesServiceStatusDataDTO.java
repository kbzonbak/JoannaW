package bbr.esb.services.data.classes;

import bbr.common.adtclasses.interfaces.IIdentifiable;

public class CompaniesServiceStatusDataDTO implements IIdentifiable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -326477756014077838L;

	private String accesscode;

	private String as2id;

	private Long companykey;

	private Long servicekey;

	private String servicename;

	private Long sitekey;

	private String sitename;

	private Boolean monitor;

	private String activation;
	
	private String servicecode;

	private String sitecode;
	
	private Integer custommaxdelay;

	public String getAccesscode() {
		return accesscode;
	}

	public String getAs2id() {
		return as2id;
	}

	public Long getCompanykey() {
		return companykey;
	}

	public Long getServicekey() {
		return servicekey;
	}

	public String getServicename() {
		return servicename;
	}

	public Long getSitekey() {
		return sitekey;
	}

	public String getSitename() {
		return sitename;
	}

	public void setAccesscode(String accesscode) {
		this.accesscode = accesscode;
	}

	public void setAs2id(String as2id) {
		this.as2id = as2id;
	}

	public void setCompanykey(Long companykey) {
		this.companykey = companykey;
	}

	public void setServicekey(Long servicekey) {
		this.servicekey = servicekey;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	public void setSitekey(Long sitekey) {
		this.sitekey = sitekey;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}

	public Boolean getMonitor() {
		return monitor;
	}

	public void setMonitor(Boolean monitor) {
		this.monitor = monitor;
	}

	public String getActivation() {
		return activation;
	}

	public void setActivation(String activation) {
		this.activation = activation;
	}

	public String getServicecode() {
		return servicecode;
	}

	public void setServicecode(String servicecode) {
		this.servicecode = servicecode;
	}

	public String getSitecode() {
		return sitecode;
	}

	public void setSitecode(String sitecode) {
		this.sitecode = sitecode;
	}

	public Integer getCustommaxdelay() {
		return custommaxdelay;
	}

	public void setCustommaxdelay(Integer custommaxdelay) {
		this.custommaxdelay = custommaxdelay;
	}
	
}
