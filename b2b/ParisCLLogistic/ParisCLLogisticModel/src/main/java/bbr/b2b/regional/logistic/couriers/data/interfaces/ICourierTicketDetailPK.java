package bbr.b2b.regional.logistic.couriers.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface ICourierTicketDetailPK extends IPrimaryKey {

	public Long getCourierticketid();

	public void setCourierticketid(Long courierticketid);

	public Long getDodeliveryid();

	public void setDodeliveryid(Long dodeliveryid);
	
}
