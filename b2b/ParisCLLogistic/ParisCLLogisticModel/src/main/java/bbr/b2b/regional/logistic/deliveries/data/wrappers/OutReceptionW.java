package bbr.b2b.regional.logistic.deliveries.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IOutReception;

public class OutReceptionW extends ElementDTO implements IOutReception {

	private Long number;
	private Long outdelivery;
	private Date receptiondate;
	private Boolean inprocess;
	private Long vendorid;
	private Long locationid;

	public Long getNumber(){ 
		return this.number;
	}
	public Long getOutdelivery(){ 
		return this.outdelivery;
	}
	public Date getReceptiondate(){ 
		return this.receptiondate;
	}
	public Boolean getInprocess(){ 
		return this.inprocess;
	}
	public Long getVendorid(){ 
		return this.vendorid;
	}
	public Long getLocationid(){ 
		return this.locationid;
	}
	public void setNumber(Long number){ 
		this.number = number;
	}
	public void setOutdelivery(Long outdelivery){ 
		this.outdelivery = outdelivery;
	}
	public void setReceptiondate(Date receptiondate){ 
		this.receptiondate = receptiondate;
	}
	public void setInprocess(Boolean inprocess){ 
		this.inprocess = inprocess;
	}
	public void setVendorid(Long vendorid){ 
		this.vendorid = vendorid;
	}
	public void setLocationid(Long locationid){ 
		this.locationid = locationid;
	}
}
