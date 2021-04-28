package bbr.b2b.regional.logistic.notifications.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IContact extends IElement {

	public String getName();
	public String getLastname();
	public String getPosition();
	public String getEmail();
	public void setName(String name);
	public void setLastname(String lastname);
	public void setPosition(String position);
	public void setEmail(String email);
}
