package bbr.esb.services.data.classes;

import java.util.Date;

import bbr.common.adtclasses.interfaces.IIdentifiable;

public class ContractedServiceDataDTO implements IIdentifiable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5762311556910448623L;

	private Long companykey;

	private Long servicekey;

	private Long sitekey;

	private Long folderkey;

	private Long formatkey;

	private boolean active;

	private String sitename;

	private String servicename;

	private String companyname;

	private Boolean encrypt;

	private String encpwd;

	private String foldername;

	private String formatname;

	private Date lastsentmessage;

	private Boolean monitor;
	
	private Date activation;

	private Date lastmonitored;
	
	private Integer pendingmessages;	
	
	private Long wsendpointkey;
	
	private String wsendpoint;

	private String sitecode;

	private String servicecode;
	
	private Integer custommaxdelayendflow;
	
	private Boolean compresseddocument;
	
	public Integer getCustommaxdelayendflow() {
		return custommaxdelayendflow;
	}

	public void setCustommaxdelayendflow(Integer custommaxdelayendflow) {
		this.custommaxdelayendflow = custommaxdelayendflow;
	}

	public Boolean getCompresseddocument() {
		return compresseddocument;
	}

	public void setCompresseddocument(Boolean compresseddocument) {
		this.compresseddocument = compresseddocument;
	}

	public Date getActivation() {
		return activation;
	}

	public boolean getActive() {
		return active;
	}

	public Long getCompanykey() {
		return companykey;
	}

	public String getCompanyname() {
		return companyname;
	}

	public String getEncpwd() {
		return encpwd;
	}

	public Boolean getEncrypt() {
		return encrypt;
	}

	public Long getFolderkey() {
		return folderkey;
	}

	public String getFoldername() {
		return foldername;
	}

	public Long getFormatkey() {
		return formatkey;
	}

	public String getFormatname() {
		return formatname;
	}

	public Date getLastmonitored() {
		return lastmonitored;
	}

	public Date getLastsentmessage() {
		return lastsentmessage;
	}

	public Boolean getMonitor() {
		return monitor;
	}

	public Integer getPendingmessages() {
		return pendingmessages;
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

	public void setActivation(Date activation) {
		this.activation = activation;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setCompanykey(Long companykey) {
		this.companykey = companykey;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public void setEncpwd(String encpwd) {
		this.encpwd = encpwd;
	}

	public void setEncrypt(Boolean encrypt) {
		this.encrypt = encrypt;
	}

	public void setFolderkey(Long folderkey) {
		this.folderkey = folderkey;
	}

	public void setFoldername(String foldername) {
		this.foldername = foldername;
	}

	public void setFormatkey(Long formatkey) {
		this.formatkey = formatkey;
	}

	public void setFormatname(String formatname) {
		this.formatname = formatname;
	}

	public void setLastmonitored(Date lastmonitored) {
		this.lastmonitored = lastmonitored;
	}

	public void setLastsentmessage(Date lastsentmessage) {
		this.lastsentmessage = lastsentmessage;
	}

	public void setMonitor(Boolean monitor) {
		this.monitor = monitor;
	}

	public void setPendingmessages(Integer pendingmessages) {
		this.pendingmessages = pendingmessages;
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

	public Long getWsendpointkey() {
		return wsendpointkey;
	}

	public void setWsendpointkey(Long wsendpointkey) {
		this.wsendpointkey = wsendpointkey;
	}

	public String getWsendpoint() {
		return wsendpoint;
	}

	public void setWsendpoint(String wsendpoint) {
		this.wsendpoint = wsendpoint;
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
