package bbr.b2b.logistic.scheduler.entities;

import java.time.LocalDateTime;

import bbr.b2b.logistic.scheduler.data.interfaces.IPendingMail;

public class PendingMail implements IPendingMail {

	private Long id;
	private LocalDateTime when;
	private String code;
	private String mailsession;
	private String subject;
	private String frommail;
	private String tomail;
	private String ccmail;
	private String ccomail;
	private String content;
	private Integer attempts;
	private LocalDateTime lastattempt;
	private Integer status;
	private LocalDateTime datetosend;
	private PendingMailType pendingmailtype;

	public Long getId(){ 
		return this.id;
	}
	public LocalDateTime getWhen(){ 
		return this.when;
	}
	public String getCode(){ 
		return this.code;
	}
	public String getMailsession(){ 
		return this.mailsession;
	}
	public String getSubject(){ 
		return this.subject;
	}
	public String getFrommail(){ 
		return this.frommail;
	}
	public String getTomail(){ 
		return this.tomail;
	}
	public String getCcmail(){ 
		return this.ccmail;
	}
	public String getCcomail(){ 
		return this.ccomail;
	}
	public String getContent(){ 
		return this.content;
	}
	public Integer getAttempts(){ 
		return this.attempts;
	}
	public LocalDateTime getLastattempt(){ 
		return this.lastattempt;
	}
	public Integer getStatus(){ 
		return this.status;
	}
	public LocalDateTime getDatetosend(){ 
		return this.datetosend;
	}
	public PendingMailType getPendingmailtype(){ 
		return this.pendingmailtype;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setWhen(LocalDateTime when){ 
		this.when = when;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setMailsession(String mailsession){ 
		this.mailsession = mailsession;
	}
	public void setSubject(String subject){ 
		this.subject = subject;
	}
	public void setFrommail(String frommail){ 
		this.frommail = frommail;
	}
	public void setTomail(String tomail){ 
		this.tomail = tomail;
	}
	public void setCcmail(String ccmail){ 
		this.ccmail = ccmail;
	}
	public void setCcomail(String ccomail){ 
		this.ccomail = ccomail;
	}
	public void setContent(String content){ 
		this.content = content;
	}
	public void setAttempts(Integer attempts){ 
		this.attempts = attempts;
	}
	public void setLastattempt(LocalDateTime lastattempt){ 
		this.lastattempt = lastattempt;
	}
	public void setStatus(Integer status){ 
		this.status = status;
	}
	public void setDatetosend(LocalDateTime datetosend){ 
		this.datetosend = datetosend;
	}
	public void setPendingmailtype(PendingMailType pendingmailtype){ 
		this.pendingmailtype = pendingmailtype;
	}
}
