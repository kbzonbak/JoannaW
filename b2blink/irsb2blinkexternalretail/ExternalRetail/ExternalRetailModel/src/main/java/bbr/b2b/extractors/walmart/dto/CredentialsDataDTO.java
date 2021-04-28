package bbr.b2b.extractors.walmart.dto;

public class CredentialsDataDTO {
	
	private String userId;
	private String user; 
	private String password;
	private String urlLogin;
	private String maxTimeWaitDownload;	 
	private String maxNumberOfAttempts;
	private String statusToDownload; 
	private String fileNameToDownload;
	private String extension;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUrlLogin() {
		return urlLogin;
	}
	public void setUrlLogin(String urlLogin) {
		this.urlLogin = urlLogin;
	}
	public String getMaxTimeWaitDownload() {
		return maxTimeWaitDownload;
	}
	public void setMaxTimeWaitDownload(String maxTimeWaitDownload) {
		this.maxTimeWaitDownload = maxTimeWaitDownload;
	}
	public String getMaxNumberOfAttempts() {
		return maxNumberOfAttempts;
	}
	public void setMaxNumberOfAttempts(String maxNumberOfAttempts) {
		this.maxNumberOfAttempts = maxNumberOfAttempts;
	}
	public String getStatusToDownload() {
		return statusToDownload;
	}
	public void setStatusToDownload(String statusToDownload) {
		this.statusToDownload = statusToDownload;
	}
	public String getFileNameToDownload() {
		return fileNameToDownload;
	}
	public void setFileNameToDownload(String fileNameToDownload) {
		this.fileNameToDownload = fileNameToDownload;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}	
}
