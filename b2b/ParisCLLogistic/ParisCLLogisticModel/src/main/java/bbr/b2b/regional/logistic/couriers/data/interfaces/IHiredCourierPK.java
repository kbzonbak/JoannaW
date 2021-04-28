package bbr.b2b.regional.logistic.couriers.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IHiredCourierPK extends IPrimaryKey {

	public Long getVendorid();

	public void setVendorid(Long vendorid);

	public Long getCourierid();

	public void setCourierid(Long courierid);
}
