package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;
import java.util.Date;

public class BlockedOrderDTO implements Serializable {

	private Date blocked;
	private String username;
	private String useremail;
	private String usertype;
	private Long ordernumber;
	private String vendorcode;
	private String vendorname;
	private String deliverylocationcode;
	private String deliverylocationname;
	private String destinationlocationcode;
	private String destinationlocationname;
	private String iteminternalcode;
	private String itemname;
	
	public Date getBlocked() {
		return blocked;
	}
	public void setBlocked(Date blocked) {
		this.blocked = blocked;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUseremail() {
		return useremail;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public Long getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getVendorname() {
		return vendorname;
	}
	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}
	public String getDeliverylocationcode() {
		return deliverylocationcode;
	}
	public void setDeliverylocationcode(String deliverylocationcode) {
		this.deliverylocationcode = deliverylocationcode;
	}
	public String getDeliverylocationname() {
		return deliverylocationname;
	}
	public void setDeliverylocationname(String deliverylocationname) {
		this.deliverylocationname = deliverylocationname;
	}
	public String getDestinationlocationcode() {
		return destinationlocationcode;
	}
	public void setDestinationlocationcode(String destinationlocationcode) {
		this.destinationlocationcode = destinationlocationcode;
	}
	public String getDestinationlocationname() {
		return destinationlocationname;
	}
	public void setDestinationlocationname(String destinationlocationname) {
		this.destinationlocationname = destinationlocationname;
	}
	public String getIteminternalcode() {
		return iteminternalcode;
	}
	public void setIteminternalcode(String iteminternalcode) {
		this.iteminternalcode = iteminternalcode;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
		
}
