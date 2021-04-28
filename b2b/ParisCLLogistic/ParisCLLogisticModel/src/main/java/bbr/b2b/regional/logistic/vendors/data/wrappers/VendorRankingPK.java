package bbr.b2b.regional.logistic.vendors.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.vendors.data.interfaces.IVendorRankingPK;

public class VendorRankingPK implements IVendorRankingPK {

	private Long vendorid;
	private Long rankingid;

	public VendorRankingPK(){
	}
	public VendorRankingPK(Long vendorid, Long rankingid){
		this.vendorid = vendorid;
		this.rankingid = rankingid;
	}

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = vendorid.longValue() - ((IVendorRankingPK) arg0).getVendorid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = rankingid.longValue() - ((IVendorRankingPK) arg0).getRankingid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}
	public boolean equals(Object o){
		if (o != null && o instanceof VendorRankingPK){
			VendorRankingPK that = (VendorRankingPK) o;
			return this.vendorid.equals(that.vendorid) && this.rankingid.equals(that.rankingid);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return vendorid.hashCode() + rankingid.hashCode();
	}

	public Long getVendorid(){ 
		return this.vendorid;
	}
	public Long getRankingid(){ 
		return this.rankingid;
	}
	public void setVendorid(Long vendorid){ 
		this.vendorid = vendorid;
	}
	public void setRankingid(Long rankingid){ 
		this.rankingid = rankingid;
	}
}
