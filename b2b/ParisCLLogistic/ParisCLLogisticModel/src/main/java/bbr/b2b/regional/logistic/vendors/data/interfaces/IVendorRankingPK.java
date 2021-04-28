package bbr.b2b.regional.logistic.vendors.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;

public interface IVendorRankingPK extends IPrimaryKey {

	public Long getVendorid();
	public Long getRankingid();
	public void setVendorid(Long vendorid);
	public void setRankingid(Long rankingid);
}
