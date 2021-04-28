package bbr.esb.users.data.classes;

import bbr.common.adtclasses.classes.ElementDTO;
import bbr.esb.services.data.interfaces.IWSEndpoint;

public class WSEndpointDTO extends ElementDTO implements IWSEndpoint {

	private static final long serialVersionUID = -5512688949624065965L;
	
	private String protocol;
	private String host;
	private String port;
	private String path;
	private String description;
	private String fullpath;
	private Long servicekey;

	public Long getServicekey() {
		return servicekey;
	}

	public void setServicekey(Long servicekey) {
		this.servicekey = servicekey;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFullpath() {
		return fullpath;
	}

	public void setFullpath(String fullpath) {
		this.fullpath = fullpath;
	}
}
