package bbr.b2b.logistic.customer.entities;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.customer.data.interfaces.IBuyerVendorPK;

public class BuyerVendorId implements IBuyerVendorPK {
	private Long buyerid;
	private Long vendorid;
	private String code;

	public BuyerVendorId() {
	}

	public BuyerVendorId(Long buyerid, Long vendorid, String code) {
		this.buyerid = buyerid;
		this.vendorid = vendorid;
		this.code = code;
	}

	public boolean equals(Object o) {
		if (o != null && o instanceof BuyerVendorId) {
			BuyerVendorId that = (BuyerVendorId) o;
			return (this.buyerid.equals(that.buyerid) && this.vendorid.equals(that.vendorid)
					&& this.code.equals(that.code));
		}
		return false;
	}

	public int hashCode() {
		return this.buyerid.hashCode() + this.vendorid.hashCode() + this.code.hashCode();
	}

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
