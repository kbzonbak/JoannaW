package bbr.esb.web.beans;

import java.util.Date;

public class SessionBean {

	private String username;

	private Date loginDate;

	private Date lastAccessDate;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public Date getLastAccessDate() {
		return lastAccessDate;
	}

	public void setLastAccessDate(Date lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("[username = " + username + ", loginDate = " + loginDate + "]");

		String result = builder.toString();
		return result;
	}

}
