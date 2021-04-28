package bbr.b2b.regional.logistic.fillrate.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.fillrate.data.interfaces.IFillRateDetail;
import bbr.b2b.regional.logistic.fillrate.data.interfaces.IFillRateDetailPK;

public class FillRateDetailW implements IFillRateDetail, IFillRateDetailPK {

	private Double totalunits;
	private Double receivedunits;
	private Long fillrateid;
	private Long itemid;

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = fillrateid.longValue() - ((IFillRateDetailPK) arg0).getFillrateid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IFillRateDetailPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Double getTotalunits(){ 
		return this.totalunits;
	}
	public Double getReceivedunits(){ 
		return this.receivedunits;
	}
	public Long getFillrateid(){ 
		return this.fillrateid;
	}
	public Long getItemid(){ 
		return this.itemid;
	}
	public void setTotalunits(Double totalunits){ 
		this.totalunits = totalunits;
	}
	public void setReceivedunits(Double receivedunits){ 
		this.receivedunits = receivedunits;
	}
	public void setFillrateid(Long fillrateid){ 
		this.fillrateid = fillrateid;
	}
	public void setItemid(Long itemid){ 
		this.itemid = itemid;
	}
}
