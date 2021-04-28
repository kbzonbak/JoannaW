package bbr.b2b.logistic.ddcdeliveries.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.ddcdeliveries.data.interfaces.IDdcDeliveryDetail;
import bbr.b2b.logistic.ddcdeliveries.data.interfaces.IDdcDeliveryDetailPK;

public class DdcDeliveryDetailW implements IDdcDeliveryDetail, IDdcDeliveryDetailPK {

	private Double pendingunits;
	private Double availableunits;
	private Double receivedunits;
	private Long ddcdeliveryid;
	private Long itemid;
	private Integer position;

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = ddcdeliveryid.longValue() - ((IDdcDeliveryDetailPK) arg0).getDdcdeliveryid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IDdcDeliveryDetailPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = position.longValue() - ((IDdcDeliveryDetailPK) arg0).getPosition().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Double getPendingunits() {
		return pendingunits;
	}

	public void setPendingunits(Double pendingunits) {
		this.pendingunits = pendingunits;
	}

	public Double getAvailableunits() {
		return availableunits;
	}

	public void setAvailableunits(Double availableunits) {
		this.availableunits = availableunits;
	}

	public Double getReceivedunits() {
		return receivedunits;
	}

	public void setReceivedunits(Double receivedunits) {
		this.receivedunits = receivedunits;
	}

	public Long getDdcdeliveryid() {
		return ddcdeliveryid;
	}

	public void setDdcdeliveryid(Long ddcdeliveryid) {
		this.ddcdeliveryid = ddcdeliveryid;
	}

	public Long getItemid() {
		return itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}
	
}
