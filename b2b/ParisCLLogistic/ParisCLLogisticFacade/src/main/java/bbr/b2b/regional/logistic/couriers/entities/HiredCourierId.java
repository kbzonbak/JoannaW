package bbr.b2b.regional.logistic.couriers.entities;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.couriers.data.interfaces.IHiredCourierPK;

public class HiredCourierId implements IHiredCourierPK {

	private Long vendorid;
	private Long courierid;

	public HiredCourierId() {
	}

	public HiredCourierId(Long vendorid, Long courierid) {
		this.vendorid = vendorid;
		this.courierid = courierid;
	}

	public int compareTo(IPrimaryKey arg0) {
		long result = 0;
		result = vendorid.longValue() - ((IHiredCourierPK) arg0).getVendorid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = courierid.longValue() - ((IHiredCourierPK) arg0).getCourierid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public boolean equals(Object o) {
		if (o != null && o instanceof HiredCourierId) {
			HiredCourierId that = (HiredCourierId) o;
			return this.vendorid.equals(that.vendorid) && this.courierid.equals(that.courierid);
		} else {
			return false;
		}
	}

	public int hashCode() {
		return (vendorid == null ? 0 : vendorid.hashCode()) + (courierid == null ? 0 : courierid.hashCode());
	}

	public Long getVendorid() {
		return vendorid;
	}

	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}

	public Long getCourierid() {
		return courierid;
	}

	public void setCourierid(Long courierid) {
		this.courierid = courierid;
	}

}
