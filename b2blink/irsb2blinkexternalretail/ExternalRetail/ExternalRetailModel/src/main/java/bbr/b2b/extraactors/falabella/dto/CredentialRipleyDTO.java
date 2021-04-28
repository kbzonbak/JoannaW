package bbr.b2b.extraactors.falabella.dto;

public class CredentialRipleyDTO {
	
	private String user;
	private String password;
	private boolean downloadVeV;
	private boolean downloadSupply;
	
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
	public boolean isDownloadVeV() {
		return downloadVeV;
	}
	public void setDownloadVeV(boolean downloadVeV) {
		this.downloadVeV = downloadVeV;
	}
	public boolean isDownloadSupply() {
		return downloadSupply;
	}
	public void setDownloadSupply(boolean downloadSupply) {
		this.downloadSupply = downloadSupply;
	}
		

}
