package bbr.b2b.logistic.dvrdeliveries.data.wrappers;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.dvrdeliveries.data.interfaces.IDvrOrderDelivery;
import bbr.b2b.logistic.dvrdeliveries.data.interfaces.IDvrOrderDeliveryPK;

public class DvrOrderDeliveryW implements IDvrOrderDelivery, IDvrOrderDeliveryPK {

	private Boolean closed;
	private Double estimatedunits;
	private LocalDateTime receptiondate;
	private Long dvrdeliveryid;
	private Long dvrorderid;

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = dvrorderid.longValue() - ((IDvrOrderDeliveryPK) arg0).getDvrorderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = dvrdeliveryid.longValue() - ((IDvrOrderDeliveryPK) arg0).getDvrdeliveryid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Boolean getClosed(){ 
		return this.closed;
	}
	public Double getEstimatedunits(){ 
		return this.estimatedunits;
	}
	public LocalDateTime getReceptiondate(){ 
		return this.receptiondate;
	}
	public Long getDvrdeliveryid(){ 
		return this.dvrdeliveryid;
	}
	public Long getDvrorderid(){ 
		return this.dvrorderid;
	}
	public void setClosed(Boolean closed){ 
		this.closed = closed;
	}
	public void setEstimatedunits(Double estimatedunits){ 
		this.estimatedunits = estimatedunits;
	}
	public void setReceptiondate(LocalDateTime receptiondate){ 
		this.receptiondate = receptiondate;
	}
	public void setDvrdeliveryid(Long dvrdeliveryid){ 
		this.dvrdeliveryid = dvrdeliveryid;
	}
	public void setDvrorderid(Long dvrorderid){ 
		this.dvrorderid = dvrorderid;
	}
}
