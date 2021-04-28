package bbr.esb.services.data.classes;

import java.util.Date;

import bbr.common.adtclasses.interfaces.IPrimaryKey;
import bbr.esb.services.data.interfaces.IContractedService;
import bbr.esb.services.data.interfaces.IContractedServicePK;

public class ContractedServiceDTO implements IContractedService, IContractedServicePK {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5028166463196391325L;

	private boolean active;

	private Long companykey;

	private Long servicekey;

	private Long sitekey;

	private Long folderkey;

	private Long formatkey;
	
	private Long wsendpointkey;

	private Date lastsentmessage;

	private Date activation;

	private boolean monitor;

	private Date lastmonitored;

	private Integer pendingmessages;
	
	private Integer custommaxdelayendflow;
	
	private String monitoringannotation;
	
	private boolean compresseddocument;
	
	private String extratorcredentials;
	
	private String custom;
	
	private String detail;
	
	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	public String getExtratorcredentials() {
		return extratorcredentials;
	}

	public void setExtratorcredentials(String extratorcredentials) {
		this.extratorcredentials = extratorcredentials;
	}

	public Integer getCustommaxdelayendflow() {
		return custommaxdelayendflow;
	}

	public void setCustommaxdelayendflow(Integer custommaxdelayendflow) {
		this.custommaxdelayendflow = custommaxdelayendflow;
	}

	public String getMonitoringannotation() {
		return monitoringannotation;
	}

	public void setMonitoringannotation(String monitoringannotation) {
		this.monitoringannotation = monitoringannotation;
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

	public boolean getActive() {
		return active;
	}

	public Long getCompanykey() {
		return companykey;
	}

	public Long getFolderkey() {
		return folderkey;
	}

	public Long getFormatkey() {
		return formatkey;
	}

	public Long getServicekey() {
		return servicekey;
	}

	public Long getSitekey() {
		return sitekey;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setCompanykey(Long companykey) {
		this.companykey = companykey;
	}

	public void setFolderkey(Long folderkey) {
		this.folderkey = folderkey;
	}

	public void setFormatkey(Long formatkey) {
		this.formatkey = formatkey;
	}

	public void setServicekey(Long servicekey) {
		this.servicekey = servicekey;
	}

	public void setSitekey(Long sitekey) {
		this.sitekey = sitekey;
	}

	public Date getLastsentmessage() {
		return lastsentmessage;
	}

	public void setLastsentmessage(Date lastsentmessage) {
		this.lastsentmessage = lastsentmessage;
	}

	public Date getActivation() {
		return activation;
	}

	public void setActivation(Date activation) {
		this.activation = activation;
	}

	public boolean getMonitor() {
		return monitor;
	}

	public boolean getCompresseddocument() {
		return compresseddocument;
	}

	public void setCompresseddocument(boolean compresseddocument) {
		this.compresseddocument = compresseddocument;
	}

	public void setMonitor(boolean monitor) {
		this.monitor = monitor;
	}

	public Date getLastmonitored() {
		return lastmonitored;
	}

	public void setLastmonitored(Date lastmonitored) {
		this.lastmonitored = lastmonitored;
	}

	public Integer getPendingmessages() {
		return pendingmessages;
	}

	public void setPendingmessages(Integer pendingmessages) {
		this.pendingmessages = pendingmessages;
	}

	public Long getWsendpointkey() {
		return wsendpointkey;
	}

	public void setWsendpointkey(Long wsendpointkey) {
		this.wsendpointkey = wsendpointkey;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	
	
}
