package bbr.b2b.logistic.customer.ripley.dto;

public class LoginResponseDTO {
	
	private int status_code;
	private String access_token;
	
	public int getStatus_code() {
		return status_code;
	}
	public void setStatus_code(int status_code) {
		this.status_code = status_code;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	

}
