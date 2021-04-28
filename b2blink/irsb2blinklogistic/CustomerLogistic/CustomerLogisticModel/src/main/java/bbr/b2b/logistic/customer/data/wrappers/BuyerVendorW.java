package bbr.b2b.logistic.customer.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.customer.data.interfaces.IBuyerVendor;
import bbr.b2b.logistic.customer.data.interfaces.IBuyerVendorPK;

public class BuyerVendorW implements IBuyerVendor, IBuyerVendorPK {
	private Long buyerid;
	private Long vendorid;
	private String code;

	public int compareTo(IPrimaryKey arg0) {
		long result = 0L;
		result = this.buyerid.longValue() - ((IBuyerVendorPK) arg0).getBuyerid().longValue();
		if (result != 0L) {
			return (result > 0) ? 1 : -1;
		}
		result = this.vendorid.longValue() - ((IBuyerVendorPK) arg0).getVendorid().longValue();
		if (result != 0L) {
			return (result > 0) ? 1 : -1;
		}
		result = (this.code.equals(((IBuyerVendorPK) arg0).getCode()) ? 0 : -1);
		if (result != 0L) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Long getBuyerid() {
		return this.buyerid;
	}

	public void setBuyerid(Long buyerid) {
		this.buyerid = buyerid;
	}

	public Long getVendorid() {
		return this.vendorid;
	}

	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
