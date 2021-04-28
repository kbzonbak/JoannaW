package bbr.b2b.regional.logistic.couriers.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.couriers.data.interfaces.IHiredCourier;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

public class HiredCourier implements IHiredCourier {

	HiredCourierId id;
	String user;
	String password;
	String clientcode;
	Date creationdate;
	Date updatedate;
	Vendor vendor;
	Courier courier;
	
	public HiredCourierId getId() {
		return id;
	}
	public void setId(HiredCourierId id) {
		this.id = id;
	}
	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	public Courier getCourier() {
		return courier;
	}
	public void setCourier(Courier courier) {
		this.courier = courier;
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
	public String getClientcode() {
		return clientcode;
	}
	public void setClientcode(String clientcode) {
		this.clientcode = clientcode;
	}
	public Date getCreationdate() {
		return creationdate;
	}
	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	
}
