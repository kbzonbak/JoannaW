package bbr.b2b.logistic.parameters.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IParameterChange extends IElement {

	public LocalDateTime getWhen();
	public String getUser();
	public String getUsertype();
	public String getComment();
	public void setWhen(LocalDateTime when);
	public void setUser(String user);
	public void setUsertype(String usertype);
	public void setComment(String comment);
}
