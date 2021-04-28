package bbr.b2b.regional.logistic.couriers.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IIdentifiable;



public interface IHiredCourier extends IIdentifiable {

	public String getUser();

	public void setUser(String user);

	public String getPassword();

	public void setPassword(String password);

	public String getClientcode();

	public void setClientcode(String clientcode);
	

	public Date getCreationdate();
	
	public void setCreationdate(Date creationdate);
	
	public Date getUpdatedate();
	
	public void setUpdatedate(Date updatedate);
	
}
