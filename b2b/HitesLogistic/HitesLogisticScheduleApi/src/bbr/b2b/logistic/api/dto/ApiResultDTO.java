package bbr.b2b.logistic.api.dto;

public class ApiResultDTO {
	
	private String statuscode;
	private String statusmessage;
	
	public ApiResultDTO(String statuscode, String statusmessage) {
		super();
		this.statuscode = statuscode;
		this.statusmessage = statusmessage;
	}
	public String getStatuscode() {
		return statuscode;
	}
	public void setStatuscode(String statuscode) {
		this.statuscode = statuscode;
	}
	public String getStatusmessage() {
		return statusmessage;
	}
	public void setStatusmessage(String statusmessage) {
		this.statusmessage = statusmessage;
	}
}	
