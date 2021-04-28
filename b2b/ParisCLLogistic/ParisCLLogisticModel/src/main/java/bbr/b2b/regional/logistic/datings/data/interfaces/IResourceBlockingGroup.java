package bbr.b2b.regional.logistic.datings.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IResourceBlockingGroup extends IElement {

	public String getComment();
	public Date getCreated();
	public String getReason();
	public Integer getRecurrence();
	public Date getSince();
	public Date getUntil();
	public String getWho();
	public void setComment(String comment);
	public void setCreated(Date created);
	public void setReason(String reason);
	public void setRecurrence(Integer recurrence);
	public void setSince(Date since);
	public void setUntil(Date until);
	public void setWho(String who);
}
