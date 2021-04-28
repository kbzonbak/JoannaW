package bbr.b2b.regional.logistic.datings.data.wrappers;

import java.util.Date;

import bbr.b2b.regional.logistic.datings.data.interfaces.IDating;

public class DatingW extends ReserveW implements IDating {

	private Long number;
	private Boolean confirmed;
	private Date confirmationdate;
	private String comment; 
	private Long vendorid;
	private Long deliveryid;

	public Long getNumber(){ 
		return this.number;
	}
	public Boolean getConfirmed(){ 
		return this.confirmed;
	}
	public Date getConfirmationdate(){ 
		return this.confirmationdate;
	}
	public Long getVendorid(){ 
		return this.vendorid;
	}
	public Long getDeliveryid(){ 
		return this.deliveryid;
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
	public void setVendorid(Long vendorid){ 
		this.vendorid = vendorid;
	}
	public void setDeliveryid(Long deliveryid){ 
		this.deliveryid = deliveryid;
	}
}
