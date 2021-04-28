package bbr.b2b.regional.logistic.report.classes;

import java.time.LocalDateTime;
import java.util.Date;

public class PendingMessageDTO {

	private Long id;
	
	private Date when;

	private LocalDateTime whenldt;

	private String code;

	private String content;
	
	private String factory;

	private String queue;

	private Integer attempts;

	private Date lastattempt;
	
	private LocalDateTime lastattemptldt;

	private Integer status;

	private Long pendingmessagetypeid;

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

	public LocalDateTime getWhenldt() {
		return whenldt;
	}

	public void setWhenldt(LocalDateTime whenldt) {
		this.whenldt = whenldt;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public LocalDateTime getLastattemptldt() {
		return lastattemptldt;
	}

	public void setLastattemptldt(LocalDateTime lastattemptldt) {
		this.lastattemptldt = lastattemptldt;
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

	


}
