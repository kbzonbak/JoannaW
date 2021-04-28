package bbr.b2b.regional.logistic.couriers.entities;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.couriers.data.interfaces.ICourierTicketDetailPK;

public class CourierTicketDetailId implements ICourierTicketDetailPK {

	private Long courierticketid;

	private Long dodeliveryid;

	public CourierTicketDetailId() {
	}

	public CourierTicketDetailId(Long courierticketid, Long directorderid) {
		this.courierticketid = courierticketid;
		this.dodeliveryid = directorderid;
	}

	public int compareTo(IPrimaryKey arg0) {
		long result = 0;
		result = courierticketid.longValue() - ((ICourierTicketDetailPK) arg0).getCourierticketid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = dodeliveryid.longValue() - ((ICourierTicketDetailPK) arg0).getDodeliveryid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public boolean equals(Object o) {
		if (o != null && o instanceof CourierTicketDetailId) {
			CourierTicketDetailId that = (CourierTicketDetailId) o;
			return this.courierticketid.equals(that.courierticketid) && this.dodeliveryid.equals(that.dodeliveryid);
		} else {
			return false;
		}
	}

	public int hashCode() {
//		return vendorid.hashCode() + courierid.hashCode();
		return courierticketid == null ? 0 : courierticketid.hashCode() + (dodeliveryid == null ? 0 : dodeliveryid.hashCode());
	}

	public Long getCourierticketid() {
		return courierticketid;
	}

	public void setCourierticketid(Long courierticketid) {
		this.courierticketid = courierticketid;
	}

	public Long getDodeliveryid() {
		return dodeliveryid;
	}

	public void setDodeliveryid(Long directorderid) {
		this.dodeliveryid = directorderid;
	}

}
