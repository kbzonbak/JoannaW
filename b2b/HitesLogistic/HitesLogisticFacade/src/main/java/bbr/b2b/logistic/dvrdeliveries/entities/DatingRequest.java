package bbr.b2b.logistic.dvrdeliveries.entities;

import bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery;

import java.time.LocalDateTime;

import bbr.b2b.logistic.dvrdeliveries.data.interfaces.IDatingRequest;

public class DatingRequest implements IDatingRequest {

	private Long id;
	private String requester;
	private String email;
	private String phone;
	private LocalDateTime requestdate;
	private Integer trucks;
	private Integer pallets;
	private Integer estimatedvolume;
	private Integer estimatedtime;
	private Integer estimatedbulks;
	private String needmodule;
	private String comment;
	private LocalDateTime when;
	private DvrDelivery dvrdelivery;

	public Long getId(){ 
		return this.id;
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
	public LocalDateTime getRequestdate(){ 
		return this.requestdate;
	}
	public Integer getTrucks(){ 
		return this.trucks;
	}
	public Integer getPallets(){ 
		return this.pallets;
	}
	public Integer getEstimatedvolume(){ 
		return this.estimatedvolume;
	}
	public Integer getEstimatedtime(){ 
		return this.estimatedtime;
	}
	public Integer getEstimatedbulks(){ 
		return this.estimatedbulks;
	}
	public String getNeedmodule(){ 
		return this.needmodule;
	}
	public String getComment(){ 
		return this.comment;
	}
	public LocalDateTime getWhen(){ 
		return this.when;
	}
	public DvrDelivery getDvrdelivery(){ 
		return this.dvrdelivery;
	}
	public void setId(Long id){ 
		this.id = id;
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
	public void setRequestdate(LocalDateTime requestdate){ 
		this.requestdate = requestdate;
	}
	public void setTrucks(Integer trucks){ 
		this.trucks = trucks;
	}
	public void setPallets(Integer pallets){ 
		this.pallets = pallets;
	}
	public void setEstimatedvolume(Integer estimatedvolume){ 
		this.estimatedvolume = estimatedvolume;
	}
	public void setEstimatedtime(Integer estimatedtime){ 
		this.estimatedtime = estimatedtime;
	}
	public void setEstimatedbulks(Integer estimatedbulks){ 
		this.estimatedbulks = estimatedbulks;
	}
	public void setNeedmodule(String needmodule){ 
		this.needmodule = needmodule;
	}
	public void setComment(String comment){ 
		this.comment = comment;
	}
	public void setWhen(LocalDateTime when){ 
		this.when = when;
	}
	public void setDvrdelivery(DvrDelivery dvrdelivery){ 
		this.dvrdelivery = dvrdelivery;
	}
}
