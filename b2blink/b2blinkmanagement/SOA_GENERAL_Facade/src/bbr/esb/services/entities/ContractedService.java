package bbr.esb.services.entities;

import java.util.Date;

import bbr.esb.services.data.interfaces.IContractedService;
import bbr.esb.users.entities.Company;
import bbr.esb.users.entities.MessageFolder;

public class ContractedService implements IContractedService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2543043192092067355L;

	private boolean active;

	private ContractedServiceKey id;

	private Company company;

	private Service service;

	private Site site;

	private MessageFolder folder;

	private MessageFormat format;
	
	private WSEndpoint wsendpoint;

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

	public boolean getCompresseddocument() {
		return compresseddocument;
	}

	public void setCompresseddocument(boolean compresseddocument) {
		this.compresseddocument = compresseddocument;
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

	public Date getActivation() {
		return activation;
	}

	public boolean getActive() {
		return active;
	}

	public Company getCompany() {
		return company;
	}

	public MessageFolder getFolder() {
		return folder;
	}

	public MessageFormat getFormat() {
		return format;
	}

	public ContractedServiceKey getId() {
		return id;
	}

	public Date getLastmonitored() {
		return lastmonitored;
	}

	public Date getLastsentmessage() {
		return lastsentmessage;
	}

	public boolean getMonitor() {
		return monitor;
	}

	public Integer getPendingmessages() {
		return pendingmessages;
	}

	public Service getService() {
		return service;
	}

	public Site getSite() {
		return site;
	}

	public void setActivation(Date activation) {
		this.activation = activation;
	}

	public WSEndpoint getWsendpoint() {
		return wsendpoint;
	}

	public void setWsendpoint(WSEndpoint wsendpoint) {
		this.wsendpoint = wsendpoint;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setFolder(MessageFolder folder) {
		this.folder = folder;
	}

	public void setFormat(MessageFormat format) {
		this.format = format;
	}

	public void setId(ContractedServiceKey id) {
		this.id = id;
	}

	public void setLastmonitored(Date lastmonitored) {
		this.lastmonitored = lastmonitored;
	}

	public void setLastsentmessage(Date lastsentmessage) {
		this.lastsentmessage = lastsentmessage;
	}

	public void setMonitor(boolean monitor) {
		this.monitor = monitor;
	}

	public void setPendingmessages(Integer pendingmessages) {
		this.pendingmessages = pendingmessages;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
}
