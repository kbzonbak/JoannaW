package bbr.b2b.regional.logistic.datings.entities;

import bbr.b2b.regional.logistic.datings.entities.Reserve;
import java.util.Date;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;
import bbr.b2b.regional.logistic.deliveries.entities.Delivery;
import bbr.b2b.regional.logistic.datings.data.interfaces.IDating;

public class Dating extends Reserve implements IDating {

	private Long number;
	private Boolean confirmed;
	private Date confirmationdate;
	private String comment; 
	private Vendor vendor;
	private Delivery delivery;

	public Long getNumber(){ 
		return this.number;
	}
	public Boolean getConfirmed(){ 
		return this.confirmed;
	}
	public Date getConfirmationdate(){ 
		return this.confirmationdate;
	}
	public Vendor getVendor(){ 
		return this.vendor;
	}
	public Delivery getDelivery(){ 
		return this.delivery;
	}
	public void setNumber(Long number){ 
		this.number = number;
	}
	public void setConfirmed(Boolean confirmed){ 
		this.confirmed = confirmed;
	}
	public void setConfirmationdate(Date confirmationdate){ 
		this.confirmationdate = confirmationdate;
	}	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setVendor(Vendor vendor){ 
		this.vendor = vendor;
	}
	public void setDelivery(Delivery delivery){ 
		this.delivery = delivery;
	}
}
