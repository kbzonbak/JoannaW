package bbr.b2b.regional.logistic.deliveries.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.ITruckDispatcher;

public class TruckDispatcherW extends ElementDTO implements ITruckDispatcher {

	private String name;
	private String email;
	private String phone;
	private String validationcode;
	private Date generationcodedate;
	private Date usercreationdate;
	private Boolean active;
	private Date activationdate;
	private String token;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getValidationcode() {
		return validationcode;
	}
	public void setValidationcode(String validationcode) {
		this.validationcode = validationcode;
	}
	public Date getGenerationcodedate() {
		return generationcodedate;
	}
	public void setGenerationcodedate(Date generationcodedate) {
		this.generationcodedate = generationcodedate;
	}
	public Date getUsercreationdate() {
		return usercreationdate;
	}
	public void setUsercreationdate(Date usercreationdate) {
		this.usercreationdate = usercreationdate;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Date getActivationdate() {
		return activationdate;
	}
	public void setActivationdate(Date activationdate) {
		this.activationdate = activationdate;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
