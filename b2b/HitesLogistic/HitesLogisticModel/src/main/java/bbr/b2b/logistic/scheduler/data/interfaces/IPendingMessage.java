package bbr.b2b.logistic.scheduler.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IPendingMessage extends IElement {

	public LocalDateTime getWhen();
	public String getCode();
	public String getFactory();
	public String getQueue();
	public String getCharset();
	public String getContent();
	public Integer getAttempts();
	public LocalDateTime getLastattempt();
	public Integer getStatus();
	public LocalDateTime getDatatosend();
	public String getHeadervalues();
	public void setWhen(LocalDateTime when);
	public void setCode(String code);
	public void setFactory(String factory);
	public void setQueue(String queue);
	public void setCharset(String charset);
	public void setContent(String content);
	public void setAttempts(Integer attempts);
	public void setLastattempt(LocalDateTime lastattempt);
	public void setStatus(Integer status);
	public void setDatatosend(LocalDateTime datatosend);
	public void setHeadervalues(String headervalues);
}
