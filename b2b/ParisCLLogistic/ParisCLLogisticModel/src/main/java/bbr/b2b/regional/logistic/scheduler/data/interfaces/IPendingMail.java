package bbr.b2b.regional.logistic.scheduler.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IPendingMail extends IElement {

	public Date getWhen();
	public void setWhen(Date when);
	public String getCode();
	public void setCode(String code);
	public String getMailsession();
	public void setMailsession(String mailsession);
	public String getSubject();
	public void setSubject(String subject);
	public String getFrommail();
	public void setFrommail(String frommail);
	public String getTomail();
	public void setTomail(String tomail);
	public String getCcmail();
	public void setCcmail(String ccmail);
	public String getCcomail();
	public void setCcomail(String ccomail);
	public String getContent();
	public void setContent(String content);
	public Boolean getHtml();
	public void setHtml(Boolean html);
	public Integer getAttempts();
	public void setAttempts(Integer attempts);
	public Date getLastattempt();
	public void setLastattempt(Date lastattempt);
	public Integer getStatus();
	public void setStatus(Integer status);
	public Date getDatetosend();
	public void setDatetosend(Date datetosend);

}
