package bbr.b2b.regional.logistic.datings.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.datings.data.interfaces.IResourceBlockingGroup;

public class ResourceBlockingGroupW extends ElementDTO implements IResourceBlockingGroup {

	private String comment;
	private Date created;
	private String reason;
	private Integer recurrence;
	private Date since;
	private Date until;
	private String who;
	private Long locationid;

	public String getComment(){ 
		return this.comment;
	}
	public Date getCreated(){ 
		return this.created;
	}
	public String getReason(){ 
		return this.reason;
	}
	public Integer getRecurrence(){ 
		return this.recurrence;
	}
	public Date getSince(){ 
		return this.since;
	}
	public Date getUntil(){ 
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
	public void setCreated(Date created){ 
		this.created = created;
	}
	public void setReason(String reason){ 
		this.reason = reason;
	}
	public void setRecurrence(Integer recurrence){ 
		this.recurrence = recurrence;
	}
	public void setSince(Date since){ 
		this.since = since;
	}
	public void setUntil(Date until){ 
		this.until = until;
	}
	public void setWho(String who){ 
		this.who = who;
	}
	public void setLocationid(Long locationid){ 
		this.locationid = locationid;
	}
}
