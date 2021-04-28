package bbr.esb.users.data.interfaces;

import bbr.common.adtclasses.interfaces.IElement;

public interface IMessageFolderType extends IElement {

	public String getCode(); 
	public void setCode(String code);
	public String getDescription(); 
	public void setDescription(String description);
	
}
