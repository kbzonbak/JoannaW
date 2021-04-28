package bbr.esb.users.data.classes;

import bbr.common.adtclasses.interfaces.IIdentifiable;

public class MessageFolderDataDTO implements IIdentifiable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3223708010434738115L;

	private String type;
	private String path;
	private String host;
	private String username;
	private String password;
	private String protocol;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
}
