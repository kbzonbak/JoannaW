package bbr.esb.events.data.interfaces;

import java.util.Date;

import bbr.common.adtclasses.interfaces.IElement;

public interface ITicketState extends IElement {

	public String getCode();

	public String getDescription();

	public String getState();

	public String getType();

	public Date getWhen();

	public void setCode(String code);

	public void setDescription(String description);

	public void setState(String state);

	public void setType(String type);

	public void setWhen(Date when);

}
