package bbr.b2b.regional.logistic.datings.entities;

import java.util.Date;
import bbr.b2b.regional.logistic.deliveries.entities.Delivery;
import bbr.b2b.regional.logistic.datings.data.interfaces.IDatingRequest;

public class DatingRequest implements IDatingRequest {

	private Long id;
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
	private Delivery delivery;

	public Long getId(){ 
		return this.id;
	}
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
	public Delivery getDelivery(){ 
		return this.delivery;
	}
	public void setId(Long id){ 
		this.id = id;
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
	public void setDelivery(Delivery delivery){ 
		this.delivery = delivery;
	}
}
