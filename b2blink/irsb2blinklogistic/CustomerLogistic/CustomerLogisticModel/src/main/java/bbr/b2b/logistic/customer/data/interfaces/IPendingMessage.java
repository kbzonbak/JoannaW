package bbr.b2b.logistic.customer.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IPendingMessage extends IElement {

	public Date getWhen();
	public String getCode();
	public String getFactory();
	public String getQueue();
	public String getCharset();
	public String getContent();
	public Integer getAttempts();
	public Date getLastattempt();
	public Integer getStatus();
	public void setWhen(Date when);
	public void setCode(String code);
	public void setFactory(String factory);
	public void setQueue(String queue);
	public void setCharset(String charset);
	public void setContent(String content);
	public void setAttempts(Integer attempts);
	public void setLastattempt(Date lastattempt);
	public void setStatus(Integer status);
}
