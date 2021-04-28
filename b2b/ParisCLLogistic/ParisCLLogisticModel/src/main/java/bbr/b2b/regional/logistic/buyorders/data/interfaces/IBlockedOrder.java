package bbr.b2b.regional.logistic.buyorders.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IBlockedOrder extends IElement {

	public Date getWhen();
	public void setWhen(Date when);
	public String getUsername();
	public void setUsername(String username);
	public String getUseremail();
	public void setUseremail(String useremail);
	public String getUsertype();
	public void setUsertype(String usertype);
	public Long getOrdernumber();
	public void setOrdernumber(Long ordernumber);
	public String getVendorcode();
	public void setVendorcode(String vendorcode);
	public String getVendorname();
	public void setVendorname(String vendorname);
	public String getDeliverylocationcode();
	public void setDeliverylocationcode(String deliverylocationcode);
	public String getDeliverylocationname();
	public void setDeliverylocationname(String deliverylocationname);
	public String getDestinationlocationcode();
	public void setDestinationlocationcode(String destinationlocationcode);
	public String getDestinationlocationname();
	public void setDestinationlocationname(String destinationlocationname);
	public String getIteminternalcode();
	public void setIteminternalcode(String iteminternalcode);
	public String getItemname();
	public void setItemname(String itemname);
	
}
