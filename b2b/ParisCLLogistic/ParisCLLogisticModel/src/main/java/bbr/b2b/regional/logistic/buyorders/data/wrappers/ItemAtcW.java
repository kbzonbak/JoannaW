package bbr.b2b.regional.logistic.buyorders.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.buyorders.data.interfaces.IItemAtc;
import bbr.b2b.regional.logistic.buyorders.data.interfaces.IItemAtcPK;

public class ItemAtcW implements IItemAtc, IItemAtcPK {

	private Long curve;
	private Long itemid;
	private Long atcid;

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = atcid.longValue() - ((IItemAtcPK) arg0).getAtcid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = itemid.longValue() - ((IItemAtcPK) arg0).getItemid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Long getCurve() {
		return curve;
	}

	public void setCurve(Long curve) {
		this.curve = curve;
	}

	public Long getItemid() {
		return itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	public Long getAtcid() {
		return atcid;
	}

	public void setAtcid(Long atcid) {
		this.atcid = atcid;
	}

}
