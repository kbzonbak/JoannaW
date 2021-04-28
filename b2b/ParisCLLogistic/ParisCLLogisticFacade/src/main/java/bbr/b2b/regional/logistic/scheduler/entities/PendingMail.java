package bbr.b2b.regional.logistic.scheduler.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.scheduler.data.interfaces.IPendingMail;

public class PendingMail implements IPendingMail {

	private Long id;
	private Date when;
	private String code;
	private String mailsession;
	private String subject;
	private String frommail;
	private String tomail;
	private String ccmail;
	private String ccomail; 
	private String content;
	private Boolean html;
	private Integer attempts;
	private Date lastattempt;
	private Integer status;
	private Date datetosend;
	private PendingMailType pendingmailtype;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getWhen() {
		return when;
	}
	public void setWhen(Date when) {
		this.when = when;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMailsession() {
		return mailsession;
	}
	public void setMailsession(String mailsession) {
		this.mailsession = mailsession;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getFrommail() {
		return frommail;
	}
	public void setFrommail(String frommail) {
		this.frommail = frommail;
	}
	public String getTomail() {
		return tomail;
	}
	public void setTomail(String tomail) {
		this.tomail = tomail;
	}
	public String getCcmail() {
		return ccmail;
	}
	public void setCcmail(String ccmail) {
		this.ccmail = ccmail;
	}
	public String getCcomail() {
		return ccomail;
	}
	public void setCcomail(String ccomail) {
		this.ccomail = ccomail;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Boolean getHtml() {
		return html;
	}
	public void setHtml(Boolean html) {
		this.html = html;
	}
	public Integer getAttempts() {
		return attempts;
	}
	public void setAttempts(Integer attempts) {
		this.attempts = attempts;
	}
	public Date getLastattempt() {
		return lastattempt;
	}
	public void setLastattempt(Date lastattempt) {
		this.lastattempt = lastattempt;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getDatetosend() {
		return datetosend;
	}
	public void setDatetosend(Date datetosend) {
		this.datetosend = datetosend;
	}
	public PendingMailType getPendingmailtype() {
		return pendingmailtype;
	}
	public void setPendingmailtype(PendingMailType pendingmailtype) {
		this.pendingmailtype = pendingmailtype;
	}
		
}
