package bbr.b2b.regional.logistic.datings.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.datings.data.interfaces.IDatingRequest;

public class DatingRequestW extends ElementDTO implements IDatingRequest {

	private Long number;
	private Date when;
	private Date requesteddate;
	private Integer estimatedboxes;
	private Integer estimatedpallets;
	private Integer trucks;
	private String requester;
	private String email;
	private String phone;
	private String comment;
	private Long deliveryid;

	public Long getNumber(){ 
		return this.number;
	}
	public Date getWhen(){ 
		return this.when;
	}
	public Date getRequesteddate(){ 
		return this.requesteddate;
	}
	public Integer getEstimatedboxes(){ 
		return this.estimatedboxes;
	}
	public Integer getEstimatedpallets(){ 
		return this.estimatedpallets;
	}
	public Integer getTrucks(){ 
		return this.trucks;
	}
	public String getRequester(){ 
		return this.requester;
	}
	public String getEmail(){ 
		return this.email;
	}
	public String getPhone(){ 
		return this.phone;
	}
	public String getComment(){ 
		return this.comment;
	}
	public Long getDeliveryid(){ 
		return this.deliveryid;
	}
	public void setNumber(Long number){ 
		this.number = number;
	}
	public void setWhen(Date when){ 
		this.when = when;
	}
	public void setRequesteddate(Date requesteddate){ 
		this.requesteddate = requesteddate;
	}
	public void setEstimatedboxes(Integer estimatedboxes){ 
		this.estimatedboxes = estimatedboxes;
	}
	public void setEstimatedpallets(Integer estimatedpallets){ 
		this.estimatedpallets = estimatedpallets;
	}
	public void setTrucks(Integer trucks){ 
		this.trucks = trucks;
	}
	public void setRequester(String requester){ 
		this.requester = requester;
	}
	public void setEmail(String email){ 
		this.email = email;
	}
	public void setPhone(String phone){ 
		this.phone = phone;
	}
	public void setComment(String comment){ 
		this.comment = comment;
	}
	public void setDeliveryid(Long deliveryid){ 
		this.deliveryid = deliveryid;
	}
}
