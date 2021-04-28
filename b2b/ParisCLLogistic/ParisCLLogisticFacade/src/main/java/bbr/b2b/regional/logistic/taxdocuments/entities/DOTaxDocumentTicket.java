package bbr.b2b.regional.logistic.taxdocuments.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.taxdocuments.data.interfaces.IDOTaxDocumentTicket;

public class DOTaxDocumentTicket implements IDOTaxDocumentTicket {

	private Long id;
	private Long ticketnumber;
	private Date creationdate;
	private Date startdate;
	private Date enddate;
	private String userid;
	private String username;
	private String usermail;
	private Boolean processed;
	
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
	public Date getCreationdate() {
		return creationdate;
	}
	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsermail() {
		return usermail;
	}
	public void setUsermail(String usermail) {
		this.usermail = usermail;
	}
	public Boolean getProcessed() {
		return processed;
	}
	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}
	
}