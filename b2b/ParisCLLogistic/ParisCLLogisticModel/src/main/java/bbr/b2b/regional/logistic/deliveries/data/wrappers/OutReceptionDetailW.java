package bbr.b2b.regional.logistic.deliveries.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IOutReceptionDetail;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IOutReceptionDetailPK;

public class OutReceptionDetailW implements IOutReceptionDetail, IOutReceptionDetailPK {

	private Double receivedunits = 0D;
	private Long outreceptionid;
	private Long orderid;
	private Long itemid;
	private Long locationid;

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = outreceptionid.longValue() - ((IOutReceptionDetailPK) arg0).getOutreceptionid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = orderid.longValue() - ((IOutReceptionDetailPK) arg0).getOrderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IOutReceptionDetailPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = locationid.longValue() - ((IOutReceptionDetailPK) arg0).getLocationid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Double getReceivedunits(){ 
		return this.receivedunits;
	}
	public Long getOutreceptionid(){ 
		return this.outreceptionid;
	}
	public Long getOrderid(){ 
		return this.orderid;
	}
	public Long getItemid(){ 
		return this.itemid;
	}
	public Long getLocationid(){ 
		return this.locationid;
	}
	public void setReceivedunits(Double receivedunits){ 
		this.receivedunits = receivedunits;
	}
	public void setOutreceptionid(Long outreceptionid){ 
		this.outreceptionid = outreceptionid;
	}
	public void setOrderid(Long orderid){ 
		this.orderid = orderid;
	}
	public void setItemid(Long itemid){ 
		this.itemid = itemid;
	}
	public void setLocationid(Long locationid){ 
		this.locationid = locationid;
	}
}
