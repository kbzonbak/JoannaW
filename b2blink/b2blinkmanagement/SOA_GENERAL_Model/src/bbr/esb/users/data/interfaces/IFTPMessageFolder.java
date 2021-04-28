package bbr.esb.users.data.interfaces;

import bbr.common.adtclasses.interfaces.IElement;

public interface IFTPMessageFolder extends IElement {	
	
	public String getHost(); 
	public void setHost(String host); 
	public String getUsername();
	public void setUsername(String username);
	public String getPassword();
	public void setPassword(String password);
	public String getProtocol();
	public void setProtocol(String protocol); 
}