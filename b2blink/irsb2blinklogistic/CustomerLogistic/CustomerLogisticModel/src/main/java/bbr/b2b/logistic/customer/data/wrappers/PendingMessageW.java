package bbr.b2b.logistic.customer.data.wrappers;

import bbr.b2b.logistic.customer.data.interfaces.IPendingMessage;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;

public class PendingMessageW extends ElementDTO implements IPendingMessage {

	private Date when;
	private String code;
	private String factory;
	private String queue;
	private String charset;
	private String content;
	private Integer attempts;
	private Date lastattempt;
	private Integer status;
	private Long pendingmessagetypeid;

	public Date getWhen(){ 
		return this.when;
	}
	public String getCode(){ 
		return this.code;
	}
	public String getFactory(){ 
		return this.factory;
	}
	public String getQueue(){ 
		return this.queue;
	}
	public String getCharset(){ 
		return this.charset;
	}
	public String getContent(){ 
		return this.content;
	}
	public Integer getAttempts(){ 
		return this.attempts;
	}
	public Date getLastattempt(){ 
		return this.lastattempt;
	}
	public Integer getStatus(){ 
		return this.status;
	}
	public Long getPendingmessagetypeid(){ 
		return this.pendingmessagetypeid;
	}
	public void setWhen(Date when){ 
		this.when = when;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setFactory(String factory){ 
		this.factory = factory;
	}
	public void setQueue(String queue){ 
		this.queue = queue;
	}
	public void setCharset(String charset){ 
		this.charset = charset;
	}
	public void setContent(String content){ 
		this.content = content;
	}
	public void setAttempts(Integer attempts){ 
		this.attempts = attempts;
	}
	public void setLastattempt(Date lastattempt){ 
		this.lastattempt = lastattempt;
	}
	public void setStatus(Integer status){ 
		this.status = status;
	}
	public void setPendingmessagetypeid(Long pendingmessagetypeid){ 
		this.pendingmessagetypeid = pendingmessagetypeid;
	}
}
