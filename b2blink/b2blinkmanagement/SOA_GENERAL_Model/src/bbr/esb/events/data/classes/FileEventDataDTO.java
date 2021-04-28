package bbr.esb.events.data.classes;

import java.util.Date;

import bbr.common.adtclasses.interfaces.IIdentifiable;

public class FileEventDataDTO implements IIdentifiable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7750211896869687438L;

	private Long id;

	private Long sitekey;

	private String sitecode;
	
	private String servicecode;	
	
	private Long servicekey;

	private Long companykey;

	private String sitename;

	private String servicename;

	private String accesscode;

	private Date datecreated;

	private Date datereceived;

	private boolean received;

	private boolean ok;

	private String documentid;

	private String filename;

	public String getAccesscode() {
		return accesscode;
	}

	public Long getCompanykey() {
		return companykey;
	}

	public Date getDatecreated() {
		return datecreated;
	}

	public Date getDatereceived() {
		return datereceived;
	}

	public String getDocumentid() {
		return documentid;
	}

	public String getFilename() {
		return filename;
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

	public boolean isOk() {
		return ok;
	}

	public boolean isReceived() {
		return received;
	}

	public void setAccesscode(String accesscode) {
		this.accesscode = accesscode;
	}

	public void setCompanykey(Long companykey) {
		this.companykey = companykey;
	}

	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}

	public void setDatereceived(Date datereceived) {
		this.datereceived = datereceived;
	}

	public void setDocumentid(String documentid) {
		this.documentid = documentid;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public void setReceived(boolean received) {
		this.received = received;
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
