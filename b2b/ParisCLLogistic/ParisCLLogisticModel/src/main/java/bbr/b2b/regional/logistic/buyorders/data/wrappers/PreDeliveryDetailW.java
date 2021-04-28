package bbr.b2b.regional.logistic.buyorders.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.buyorders.data.interfaces.IPreDeliveryDetail;
import bbr.b2b.regional.logistic.buyorders.data.interfaces.IPreDeliveryDetailPK;

public class PreDeliveryDetailW implements IPreDeliveryDetail, IPreDeliveryDetailPK {

	private Integer sequence;
	private Double units;
	private Double pendingunits = 0D;
	private Double receivedunits = 0D;
	private Double todeliveryunits = 0D;
	private Double outreceivedunits = 0D;
	private Double totalneed = 0D;
	private Double totalpending = 0D;
	private Double totalreceived = 0D;
	private Double totaltodelivery = 0D;
	private Long locationid;
	private Long orderid;
	private Long itemid;

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = orderid.longValue() - ((IPreDeliveryDetailPK) arg0).getOrderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IPreDeliveryDetailPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = locationid.longValue() - ((IPreDeliveryDetailPK) arg0).getLocationid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Integer getSequence(){ 
		return this.sequence;
	}
	public Double getUnits(){ 
		return this.units;
	}
	public Double getPendingunits(){ 
		return this.pendingunits;
	}
	public Double getReceivedunits(){ 
		return this.receivedunits;
	}
	public Double getTodeliveryunits(){ 
		return this.todeliveryunits;
	}
	public Double getOutreceivedunits(){ 
		return this.outreceivedunits;
	}
	public Double getTotalneed(){ 
		return this.totalneed;
	}
	public Double getTotalpending(){ 
		return this.totalpending;
	}
	public Double getTotalreceived(){ 
		return this.totalreceived;
	}
	public Double getTotaltodelivery(){ 
		return this.totaltodelivery;
	}
	public Long getLocationid(){ 
		return this.locationid;
	}
	public Long getOrderid(){ 
		return this.orderid;
	}
	public Long getItemid(){ 
		return this.itemid;
	}
	public void setSequence(Integer sequence){ 
		this.sequence = sequence;
	}
	public void setUnits(Double units){ 
		this.units = units;
	}
	public void setPendingunits(Double pendingunits){ 
		this.pendingunits = pendingunits;
	}
	public void setReceivedunits(Double receivedunits){ 
		this.receivedunits = receivedunits;
	}
	public void setTodeliveryunits(Double todeliveryunits){ 
		this.todeliveryunits = todeliveryunits;
	}
	public void setOutreceivedunits(Double outreceivedunits){ 
		this.outreceivedunits = outreceivedunits;
	}
	public void setTotalneed(Double totalneed){ 
		this.totalneed = totalneed;
	}
	public void setTotalpending(Double totalpending){ 
		this.totalpending = totalpending;
	}
	public void setTotalreceived(Double totalreceived){ 
		this.totalreceived = totalreceived;
	}
	public void setTotaltodelivery(Double totaltodelivery){ 
		this.totaltodelivery = totaltodelivery;
	}
	public void setLocationid(Long locationid){ 
		this.locationid = locationid;
	}
	public void setOrderid(Long orderid){ 
		this.orderid = orderid;
	}
	public void setItemid(Long itemid){ 
		this.itemid = itemid;
	}
}
