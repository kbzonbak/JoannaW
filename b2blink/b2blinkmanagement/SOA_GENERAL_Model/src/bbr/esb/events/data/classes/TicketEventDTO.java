package bbr.esb.events.data.classes;

import java.util.Date;

import bbr.common.adtclasses.classes.ElementDTO;
import bbr.esb.events.data.interfaces.ITicketEvent;

public class TicketEventDTO extends ElementDTO implements ITicketEvent {

	private static final long serialVersionUID = 4570152441500864216L;

	private Long ticketnumber;

	private String messageid;

	private Date datecreated;

	private Date currentstatetypedate;

	private Long sitekey;

	private Long servicekey;

	private Long companykey;

	private Long currentstatetypekey;
	
	private String adjunto;
	
	private String referencia;
	
	private Long userid;
	

	public String getAdjunto() {
		return adjunto;
	}

	public void setAdjunto(String adjunto) {
		this.adjunto = adjunto;
	}

	public Long getCompanykey() {
		return companykey;
	}

	public Date getCurrentstatetypedate() {
		return currentstatetypedate;
	}

	public Long getCurrentstatetypekey() {
		return currentstatetypekey;
	}

	public Date getDatecreated() {
		return datecreated;
	}

	public String getMessageid() {
		return messageid;
	}

	public Long getServicekey() {
		return servicekey;
	}

	public Long getSitekey() {
		return sitekey;
	}

	public Long getTicketnumber() {
		return ticketnumber;
	}

	public void setCompanykey(Long companykey) {
		this.companykey = companykey;
	}

	public void setCurrentstatetypedate(Date currentstatetypedate) {
		this.currentstatetypedate = currentstatetypedate;
	}

	public void setCurrentstatetypekey(Long currentstatetypekey) {
		this.currentstatetypekey = currentstatetypekey;
	}

	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

	public void setServicekey(Long servicekey) {
		this.servicekey = servicekey;
	}

	public void setSitekey(Long sitekey) {
		this.sitekey = sitekey;
	}

	public void setTicketnumber(Long ticketnumber) {
		this.ticketnumber = ticketnumber;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}
	
	
	
}
