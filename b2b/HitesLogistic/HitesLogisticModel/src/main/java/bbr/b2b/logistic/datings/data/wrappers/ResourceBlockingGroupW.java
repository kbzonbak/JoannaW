package bbr.b2b.logistic.datings.data.wrappers;

import bbr.b2b.logistic.datings.data.interfaces.IResourceBlockingGroup;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.ElementDTO;

public class ResourceBlockingGroupW extends ElementDTO implements IResourceBlockingGroup {

	private String comment;
	private LocalDateTime created;
	private String reason;
	private Integer recurrence;
	private LocalDateTime since;
	private LocalDateTime until;
	private String who;
	private Long locationid;

	public String getComment(){ 
		return this.comment;
	}
	public LocalDateTime getCreated(){ 
		return this.created;
	}
	public String getReason(){ 
		return this.reason;
	}
	public Integer getRecurrence(){ 
		return this.recurrence;
	}
	public LocalDateTime getSince(){ 
		return this.since;
	}
	public LocalDateTime getUntil(){ 
		return this.until;
	}
	public String getWho(){ 
		return this.who;
	}
	public Long getLocationid(){ 
		return this.locationid;
	}
	public void setComment(String comment){ 
		this.comment = comment;
	}
	public void setCreated(LocalDateTime created){ 
		this.created = created;
	}
	public void setReason(String reason){ 
		this.reason = reason;
	}
	public void setRecurrence(Integer recurrence){ 
		this.recurrence = recurrence;
	}
	public void setSince(LocalDateTime since){ 
		this.since = since;
	}
	public void setUntil(LocalDateTime until){ 
		this.until = until;
	}
	public void setWho(String who){ 
		this.who = who;
	}
	public void setLocationid(Long locationid){ 
		this.locationid = locationid;
	}
}
