package bbr.esb.events.entities;

import java.util.Date;

import bbr.esb.events.data.interfaces.IFileEvent;
import bbr.esb.services.entities.Service;
import bbr.esb.services.entities.Site;
import bbr.esb.users.entities.Company;

public class FileEvent implements IFileEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1508896375807407258L;

	private Company company;

	private Long id;

	private Date datecreated;

	private Date datereceived;

	private boolean received;

	private boolean informed;

	private boolean ok;

	private Service service;

	private Site site;

	private String documentid;

	private String filename;

	public Company getCompany() {
		return company;
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

	public boolean getInformed() {
		return informed;
	}

	public Long getId() {
		return id;
	}

	public boolean getOk() {
		return ok;
	}

	public boolean getReceived() {
		return received;
	}

	public Service getService() {
		return service;
	}

	public Site getSite() {
		return site;
	}

	public void setCompany(Company company) {
		this.company = company;
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

	public void setInformed(boolean informed) {
		this.informed = informed;
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

	public void setService(Service service) {
		this.service = service;
	}

	public void setSite(Site site) {
		this.site = site;
	}

}
