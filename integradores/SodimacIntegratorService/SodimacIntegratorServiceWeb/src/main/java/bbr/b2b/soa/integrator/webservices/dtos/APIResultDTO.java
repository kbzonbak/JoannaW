package bbr.b2b.soa.integrator.webservices.dtos;

public class APIResultDTO {
	
	private String statuscode;
	private String statusmessage;
	
	public APIResultDTO(String statuscode, String statusmessage) {
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
