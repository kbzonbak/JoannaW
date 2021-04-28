package bbr.esb.users.data.interfaces;

import bbr.common.adtclasses.interfaces.IIdentifiable;

public interface IAccess extends IIdentifiable {

	public String getCode();
	
	public String getName();	

	public void setCode(String code);
	
	public void setName(String name);	
}
