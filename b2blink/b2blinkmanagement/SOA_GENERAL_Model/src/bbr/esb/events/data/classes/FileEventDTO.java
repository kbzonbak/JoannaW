package bbr.esb.events.data.classes;

import java.util.Date;

import bbr.common.adtclasses.classes.ElementDTO;
import bbr.esb.events.data.interfaces.IFileEvent;

public class FileEventDTO extends ElementDTO implements IFileEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3486632534961068187L;

	private Date datecreated;

	private Date datereceived;

	private boolean received;

	private boolean informed;

	private boolean ok;

	private String documentid;

	private String filename;

	private Long sitekey;

	private Long servicekey;

	private Long companykey;

	public Long getCompanykey() {
		return companykey;
	}

	public Date getDatecreated() {
		return datecreated;
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

	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}

	public void setServicekey(Long servicekey) {
		this.servicekey = servicekey;
	}

	public void setSitekey(Long sitekey) {
		this.sitekey = sitekey;
	}

	public Date getDatereceived() {
		return datereceived;
	}

	public void setDatereceived(Date datereceived) {
		this.datereceived = datereceived;
	}

	public boolean getReceived() {
		return received;
	}

	public void setReceived(boolean received) {
		this.received = received;
	}

	public boolean getOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public String getDocumentid() {
		return documentid;
	}

	public void setDocumentid(String documentid) {
		this.documentid = documentid;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public boolean getInformed() {
		return informed;
	}

	public void setInformed(boolean informed) {
		this.informed = informed;
	}

}
