package bbr.esb.services.data.interfaces;

import bbr.common.adtclasses.interfaces.IElement;

public interface IWSEndpoint extends IElement {

	public String getProtocol(); 
	public void setProtocol(String protocol); 
	public String getHost();
	public void setHost(String host);
	public String getPort();
	public void setPort(String port); 
	public String getPath();
	public void setPath(String path);
	public String getDescription(); 
	public void setDescription(String description);
	public String getFullpath();
	public void setFullpath(String fullpath);
}
