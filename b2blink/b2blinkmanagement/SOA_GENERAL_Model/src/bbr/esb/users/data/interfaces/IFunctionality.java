package bbr.esb.users.data.interfaces;

import bbr.common.adtclasses.interfaces.IElement;

public interface IFunctionality extends IElement {

	public String getDescription();

	public String getName();

	public void setDescription(String description);

	public void setName(String name);

}