package bbr.b2b.regional.logistic.mobile.classes;

import java.io.Serializable;

public class DODeliveryTokenCreationInitParamDTO implements Serializable {

	private String email;
	private String validationcode;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getValidationcode() {
		return validationcode;
	}
	public void setValidationcode(String validationcode) {
		this.validationcode = validationcode;
	}
}
