package bbr.esb.events.data.classes;

import bbr.common.adtclasses.interfaces.IIdentifiable;

public class ServiceEventDataDTO implements IIdentifiable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5934741479863424872L;

	private Long id;

	private Long sitekey;

	private Long servicekey;

	private Long companykey;

	private Long formatkey;

	private String sitename;

	private String servicename;

	private String formatname;

	private String accesscode;
	
	private String sitecode;

	private String servicecode;
	
	private String resenddata;
	
	private String[] orders;
	
	private String statuscode;	
	
	private String activation;
	
	private String custom;
	
	
	
	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	public String getActivation() {
		return activation;
	}

	public void setActivation(String activation) {
		this.activation = activation;
	}

	public String[] getOrders() {
		return orders;
	}

	public void setOrders(String[] orders) {
		this.orders = orders;
	}


	public String getResenddata() {
		return resenddata;
	}

	public void setResenddata(String resenddata) {
		this.resenddata = resenddata;
	}

	public String getStatuscode() {
		return statuscode;
	}

	public void setStatuscode(String statuscode) {
		this.statuscode = statuscode;
	}

	public String getAccesscode() {
		return accesscode;
	}

	public Long getCompanykey() {
		return companykey;
	}

	public Long getFormatkey() {
		return formatkey;
	}

	public String getFormatname() {
		return formatname;
	}

	public Long getId() {
		return id;
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

	public void setCompanykey(Long companykey) {
		this.companykey = companykey;
	}

	public void setFormatkey(Long formatkey) {
		this.formatkey = formatkey;
	}

	public void setFormatname(String formatname) {
		this.formatname = formatname;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getSitecode() {
		return sitecode;
	}

	public void setSitecode(String sitecode) {
		this.sitecode = sitecode;
	}

	public String getServicecode() {
		return servicecode;
	}

	public void setServicecode(String servicecode) {
		this.servicecode = servicecode;
	}
}
