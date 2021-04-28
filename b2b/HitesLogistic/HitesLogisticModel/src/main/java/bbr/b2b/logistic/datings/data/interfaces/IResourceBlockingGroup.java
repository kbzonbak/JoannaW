package bbr.b2b.logistic.datings.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IResourceBlockingGroup extends IElement {

	public String getComment();
	public LocalDateTime getCreated();
	public String getReason();
	public Integer getRecurrence();
	public LocalDateTime getSince();
	public LocalDateTime getUntil();
	public String getWho();
	public void setComment(String comment);
	public void setCreated(LocalDateTime created);
	public void setReason(String reason);
	public void setRecurrence(Integer recurrence);
	public void setSince(LocalDateTime since);
	public void setUntil(LocalDateTime until);
	public void setWho(String who);
}
