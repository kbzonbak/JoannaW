package bbr.esb.services.data.classes;

import bbr.common.adtclasses.interfaces.IIdentifiable;

public class CompaniesServiceStatusReportDTO implements IIdentifiable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3998480640915440773L;

	private Long servicekey;

	private Long sitekey;

	private String servicename;

	private String sitename;
	
	private String monitoringannotation;
	
	private Integer custommaxdelay;

	private CompaniesServiceStatusDataDTO[] datas;
	
	
	public String getMonitoringannotation() {
		return monitoringannotation;
	}

	public void setMonitoringannotation(String monitoringannotation) {
		this.monitoringannotation = monitoringannotation;
	}

	public Integer getCustommaxdelay() {
		return custommaxdelay;
	}

	public void setCustommaxdelay(Integer custommaxdelay) {
		this.custommaxdelay = custommaxdelay;
	}

	public CompaniesServiceStatusDataDTO[] getDatas() {
		return datas;
	}

	public void setDatas(CompaniesServiceStatusDataDTO[] datas) {
		this.datas = datas;
	}

	public Long getServicekey() {
		return servicekey;
	}

	public void setServicekey(Long servicekey) {
		this.servicekey = servicekey;
	}

	public Long getSitekey() {
		return sitekey;
	}

	public void setSitekey(Long sitekey) {
		this.sitekey = sitekey;
	}

	public String getSitename() {
		return sitename;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}

	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

}
