package bbr.b2b.regional.logistic.deliveries.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IDelivery;

public class DeliveryW extends ElementDTO implements IDelivery {

	private Long number;
	private Date created;
	private Date currentstatetypedate;
	private Long receptionnumber;
	private Date receptiondate;
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
	public Date getCreated(){ 
		return this.created;
	}
	public Date getCurrentstatetypedate(){ 
		return this.currentstatetypedate;
	}
	public Long getReceptionnumber(){ 
		return this.receptionnumber;
	}
	public Date getReceptiondate(){ 
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
	public void setCreated(Date created){ 
		this.created = created;
	}
	public void setCurrentstatetypedate(Date currentstatetypedate){ 
		this.currentstatetypedate = currentstatetypedate;
	}
	public void setReceptionnumber(Long receptionnumber){ 
		this.receptionnumber = receptionnumber;
	}
	public void setReceptiondate(Date receptiondate){ 
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
