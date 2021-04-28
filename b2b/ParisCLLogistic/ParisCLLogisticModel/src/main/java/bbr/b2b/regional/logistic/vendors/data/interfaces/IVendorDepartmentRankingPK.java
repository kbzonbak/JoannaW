package bbr.b2b.regional.logistic.vendors.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IVendorDepartmentRankingPK extends IPrimaryKey {

	public Long getVendorid();
	public Long getRankingid();
	public Long getDepartmentid();
	public void setVendorid(Long vendorid);
	public void setRankingid(Long rankingid);
	public void setDepartmentid(Long departmentid);
}
