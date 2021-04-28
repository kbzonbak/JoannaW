package bbr.b2b.logistic.customer.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IReceptionLocatioPK extends IPrimaryKey {

	public Long getReceptionid();
	public Long getLocationid();
	public void setReceptionid(Long receptionid);
	public void setLocationid(Long locationid);
}
