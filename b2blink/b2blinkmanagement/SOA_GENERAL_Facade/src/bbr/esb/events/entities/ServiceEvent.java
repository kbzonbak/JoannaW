package bbr.esb.events.entities;

import java.util.Date;

import bbr.esb.events.data.interfaces.IServiceEvent;
import bbr.esb.services.entities.Service;
import bbr.esb.services.entities.Site;
import bbr.esb.users.entities.Company;

public class ServiceEvent implements IServiceEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3567025164789718840L;

	private Company company;

	private Long id;

	private Date datecreated;

	private Date dateprocessed;

	private boolean processed;

	private Service service;

	private Site site;
	
	private String resenddata;		
	
	private String custom;
	
	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}
	public String getResenddata() {
		return resenddata;
	}

	public void setResenddata(String resenddata) {
		this.resenddata = resenddata;
	}

	public Company getCompany() {
		return company;
	}

	public Date getDatecreated() {
		return datecreated;
	}

	public Date getDateprocessed() {
		return dateprocessed;
	}

	public Long getId() {
		return id;
	}

	public boolean getProcessed() {
		return processed;
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

	public void setDateprocessed(Date dateprocessed) {
		this.dateprocessed = dateprocessed;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public void setSite(Site site) {
		this.site = site;
	}

}
