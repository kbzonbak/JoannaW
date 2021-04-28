package bbr.b2b.regional.logistic.datings.entities;

import java.util.Date;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.datings.data.interfaces.IResourceBlockingGroup;

public class ResourceBlockingGroup implements IResourceBlockingGroup {

	private Long id;
	private String comment;
	private Date created;
	private String reason;
	private Integer recurrence;
	private Date since;
	private Date until;
	private String who;
	private Location location;

	public Long getId(){ 
		return this.id;
	}
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
	public Location getLocation(){ 
		return this.location;
	}
	public void setId(Long id){ 
		this.id = id;
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
	public void setLocation(Location location){ 
		this.location = location;
	}
}
