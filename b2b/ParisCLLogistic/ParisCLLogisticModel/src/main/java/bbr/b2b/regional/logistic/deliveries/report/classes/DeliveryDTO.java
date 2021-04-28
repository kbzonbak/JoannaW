package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.ElementDTO;

public class DeliveryDTO extends ElementDTO {

	private Long number;
	private LocalDateTime created;
	private LocalDateTime currentstatetypedate;
	private Long receptionnumber;
	private LocalDateTime receptiondate;
	private Long refdocument;
	private String container;
	private String imp;
	private Integer complementscount;
	private Boolean hasatc;
	private Long deliverytypeid;
	private Long currentstatetypeid;
	private Long locationid;
	private Long ordertypeid;
	private Long flowtypeid;
	private Long vendorid;

	public Long getNumber(){ 
		return this.number;
	}
	public LocalDateTime getCreated(){ 
		return this.created;
	}
	public LocalDateTime getCurrentstatetypedate(){ 
		return this.currentstatetypedate;
	}
	public Long getReceptionnumber(){ 
		return this.receptionnumber;
	}
	public LocalDateTime getReceptiondate(){ 
		return this.receptiondate;
	}
	public Long getRefdocument(){ 
		return this.refdocument;
	}
	public String getContainer(){ 
		return this.container;
	}
	public String getImp(){ 
		return this.imp;
	}
	public Integer getComplementscount(){ 
		return this.complementscount;
	}
	public Long getDeliverytypeid(){ 
		return this.deliverytypeid;
	}
	public Long getCurrentstatetypeid(){ 
		return this.currentstatetypeid;
	}
	public Long getLocationid(){ 
		return this.locationid;
	}
	public Long getOrdertypeid(){ 
		return this.ordertypeid;
	}
	public Long getFlowtypeid(){ 
		return this.flowtypeid;
	}
	public Long getVendorid(){ 
		return this.vendorid;
	}
	public void setNumber(Long number){ 
		this.number = number;
	}
	public void setCreated(LocalDateTime created){ 
		this.created = created;
	}
	public void setCurrentstatetypedate(LocalDateTime currentstatetypedate){ 
		this.currentstatetypedate = currentstatetypedate;
	}
	public void setReceptionnumber(Long receptionnumber){ 
		this.receptionnumber = receptionnumber;
	}
	public void setReceptiondate(LocalDateTime receptiondate){ 
		this.receptiondate = receptiondate;
	}
	public void setRefdocument(Long refdocument){ 
		this.refdocument = refdocument;
	}
	public void setContainer(String container){ 
		this.container = container;
	}
	public void setImp(String imp){ 
		this.imp = imp;
	}
	public void setComplementscount(Integer complementscount){ 
		this.complementscount = complementscount;
	}
	public void setDeliverytypeid(Long deliverytypeid){ 
		this.deliverytypeid = deliverytypeid;
	}
	public void setCurrentstatetypeid(Long currentstatetypeid){ 
		this.currentstatetypeid = currentstatetypeid;
	}
	public void setLocationid(Long locationid){ 
		this.locationid = locationid;
	}
	public void setOrdertypeid(Long ordertypeid){ 
		this.ordertypeid = ordertypeid;
	}
	public void setFlowtypeid(Long flowtypeid){ 
		this.flowtypeid = flowtypeid;
	}
	public void setVendorid(Long vendorid){ 
		this.vendorid = vendorid;
	}
	public Boolean getHasatc() {
		return hasatc;
	}
	public void setHasatc(Boolean hasatc) {
		this.hasatc = hasatc;
	}
	
}
