package bbr.b2b.regional.logistic.scheduler.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IPendingMessage extends IElement {

	public Date getWhen();

	public void setWhen(Date when);

	public String getCode();

	public void setCode(String code);
	
	public String getFactory();

	public void setFactory(String factory);
	
	public String getQueue();

	public void setQueue(String queue);

	public Integer getAttempts();

	public void setAttempts(Integer attempts);

	public Date getLastattempt();

	public void setLastattempt(Date lastattempt);

	public Integer getStatus();

	public void setStatus(Integer status);

	public String getContent();

	public void setContent(String content);

}
