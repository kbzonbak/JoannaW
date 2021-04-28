package bbr.b2b.logistic.scheduler.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PendingMessageDTO implements Serializable {

	private Long id;
	private LocalDateTime when;
	private String code;
	private String factory;
	private String queue;
	private String charset;
	private String content;
	private Integer attempts;
	private LocalDateTime lastattempt;
	private Integer status;
	private LocalDateTime datatosend;
	private String headervalues;
	private Long pendingmessagetypeid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getWhen() {
		return when;
	}

	public void setWhen(LocalDateTime when) {
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

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getAttempts() {
		return attempts;
	}

	public void setAttempts(Integer attempts) {
		this.attempts = attempts;
	}

	public LocalDateTime getLastattempt() {
		return lastattempt;
	}

	public void setLastattempt(LocalDateTime lastattempt) {
		this.lastattempt = lastattempt;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public LocalDateTime getDatatosend() {
		return datatosend;
	}

	public void setDatatosend(LocalDateTime datatosend) {
		this.datatosend = datatosend;
	}

	public String getHeadervalues() {
		return headervalues;
	}

	public void setHeadervalues(String headervalues) {
		this.headervalues = headervalues;
	}

	public Long getPendingmessagetypeid() {
		return pendingmessagetypeid;
	}

	public void setPendingmessagetypeid(Long pendingmessagetypeid) {
		this.pendingmessagetypeid = pendingmessagetypeid;
	}

}
