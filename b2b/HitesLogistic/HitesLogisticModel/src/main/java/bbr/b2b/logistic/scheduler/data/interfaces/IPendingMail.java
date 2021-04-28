package bbr.b2b.logistic.scheduler.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IPendingMail extends IElement {

	public LocalDateTime getWhen();
	public String getCode();
	public String getMailsession();
	public String getSubject();
	public String getFrommail();
	public String getTomail();
	public String getCcmail();
	public String getCcomail();
	public String getContent();
	public Integer getAttempts();
	public LocalDateTime getLastattempt();
	public Integer getStatus();
	public LocalDateTime getDatetosend();
	public void setWhen(LocalDateTime when);
	public void setCode(String code);
	public void setMailsession(String mailsession);
	public void setSubject(String subject);
	public void setFrommail(String frommail);
	public void setTomail(String tomail);
	public void setCcmail(String ccmail);
	public void setCcomail(String ccomail);
	public void setContent(String content);
	public void setAttempts(Integer attempts);
	public void setLastattempt(LocalDateTime lastattempt);
	public void setStatus(Integer status);
	public void setDatetosend(LocalDateTime datetosend);
}
