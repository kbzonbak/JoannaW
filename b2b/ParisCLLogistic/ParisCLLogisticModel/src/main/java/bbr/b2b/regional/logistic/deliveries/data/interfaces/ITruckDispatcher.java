package bbr.b2b.regional.logistic.deliveries.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface ITruckDispatcher extends IElement {

	public String getName();
	public void setName(String name);
	public String getEmail();
	public void setEmail(String email);
	public String getPhone();
	public void setPhone(String phone);
	public String getValidationcode();
	public void setValidationcode(String validationcode);
	public Date getGenerationcodedate();
	public void setGenerationcodedate(Date generationcodedate);
	public Date getUsercreationdate();
	public void setUsercreationdate(Date usercreationdate);
	public Boolean getActive();
	public void setActive(Boolean active);
	public Date getActivationdate();
	public void setActivationdate(Date activationdate);

}
