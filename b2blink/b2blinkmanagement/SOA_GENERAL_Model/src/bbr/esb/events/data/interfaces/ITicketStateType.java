package bbr.esb.events.data.interfaces;

import bbr.common.adtclasses.interfaces.IElement;

public interface ITicketStateType extends IElement {

	public String getCode();

	public String getName();

	public void setCode(String code);

	public void setName(String name);
}
