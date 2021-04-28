package bbr.esb.events.entities;

import java.util.Date;

import bbr.esb.events.data.interfaces.ITicketEvent;
import bbr.esb.services.entities.Service;
import bbr.esb.services.entities.Site;
import bbr.esb.users.entities.Company;
import bbr.esb.users.entities.User;

public class TicketEvent implements ITicketEvent {

	private static final long serialVersionUID = -3647321007948100151L;

	Long id;

	Long ticketnumber;

	String messageid;

	Date datecreated;

	Date currentstatetypedate;

	Site site;

	Service service;

	Company company;

	TicketStateType currentstatetype;
	
	String adjunto;
	
	String referencia;
	
	User user;
	

	public String getAdjunto() {
		return adjunto;
	}

	public void setAdjunto(String adjunto) {
		this.adjunto = adjunto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTicketnumber() {
		return ticketnumber;
	}

	public void setTicketnumber(Long ticketnumber) {
		this.ticketnumber = ticketnumber;
	}

	public String getMessageid() {
		return messageid;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

	public Date getDatecreated() {
		return datecreated;
	}

	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}

	public Date getCurrentstatetypedate() {
		return currentstatetypedate;
	}

	public void setCurrentstatetypedate(Date currentstatetypedate) {
		this.currentstatetypedate = currentstatetypedate;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public TicketStateType getCurrentstatetype() {
		return currentstatetype;
	}

	public void setCurrentstatetype(TicketStateType currentstatetype) {
		this.currentstatetype = currentstatetype;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


}
