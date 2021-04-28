package bbr.b2b.regional.logistic.scheduler.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.scheduler.data.interfaces.IPendingMessage;

public class PendingMessageW extends ElementDTO implements IPendingMessage {

	private Date when;

	private String code;

	private String content;
	
	private String factory;

	private String queue;

	private Integer attempts;

	private Date lastattempt;

	private Integer status;

	private Long pendingmessagetypeid;

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

	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
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

	public Long getPendingmessagetypeid() {
		return pendingmessagetypeid;
	}

	public void setPendingmessagetypeid(Long pendingmessagetypeid) {
		this.pendingmessagetypeid = pendingmessagetypeid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
