package bbr.esb.users.entities;

import bbr.esb.users.data.interfaces.IFTPMessageFolder;

public class FTPMessageFolder extends MessageFolder implements IFTPMessageFolder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String host;
	private String username;
	private String password;
	private String protocol;
	
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