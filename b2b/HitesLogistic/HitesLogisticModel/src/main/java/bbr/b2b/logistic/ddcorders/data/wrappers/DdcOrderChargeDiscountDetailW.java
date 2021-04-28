package bbr.b2b.logistic.ddcorders.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.ddcorders.data.interfaces.IDdcOrderChargeDiscountDetail;
import bbr.b2b.logistic.ddcorders.data.interfaces.IDdcOrderChargeDiscountDetailPK;

public class DdcOrderChargeDiscountDetailW implements IDdcOrderChargeDiscountDetail, IDdcOrderChargeDiscountDetailPK {

	private Long ddcchargediscountid;
	private Long ddcorderid;
	private Long itemid;
	private Integer position;
	
	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = ddcorderid.longValue() - ((IDdcOrderChargeDiscountDetailPK) arg0).getDdcorderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IDdcOrderChargeDiscountDetailPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = position.longValue() - ((IDdcOrderChargeDiscountDetailPK) arg0).getPosition().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = ddcchargediscountid.longValue() - ((IDdcOrderChargeDiscountDetailPK) arg0).getDdcchargediscountid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Long getDdcchargediscountid() {
		return ddcchargediscountid;
	}

	public void setDdcchargediscountid(Long ddcchargediscountid) {
		this.ddcchargediscountid = ddcchargediscountid;
	}

	public Long getDdcorderid() {
		return ddcorderid;
	}

	public void setDdcorderid(Long ddcorderid) {
		this.ddcorderid = ddcorderid;
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
