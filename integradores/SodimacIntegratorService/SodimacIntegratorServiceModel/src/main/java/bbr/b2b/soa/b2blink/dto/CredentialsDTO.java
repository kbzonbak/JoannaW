package bbr.b2b.soa.b2blink.dto;

import lombok.Data;

@Data
public class CredentialsDTO {
	
	private String user;
	private String password;
	private String rut;
	private Boolean downloadVeV;
	private Boolean downloadSupply;
	private String userId;	
	private String urlLogin;
	private String maxTimeWaitDownload;	 
	private String maxNumberOfAttempts;
	private String statusToDownload; 
	private String fileNameToDownload;
	private String extension;	
}
