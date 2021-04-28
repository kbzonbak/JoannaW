package bbr.b2b.regional.logistic.scheduler.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.scheduler.data.interfaces.IPendingMessage;

public class PendingMessage implements IPendingMessage {

	private Long id;

	private Date when;

	private String code;
	
	private String content;
	
	private String factory;

	private String queue;

	private Integer attempts;

	private Date lastattempt;

	private Integer status;

	private PendingMessageType pendingmessagetype;

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

	public PendingMessageType getPendingmessagetype() {
		return pendingmessagetype;
	}

	public void setPendingmessagetype(PendingMessageType pendingmessagetype) {
		this.pendingmessagetype = pendingmessagetype;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
