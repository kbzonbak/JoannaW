package bbr.b2b.regional.logistic.vendors.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.vendors.data.interfaces.IVendorDepartmentRankingPK;

public class VendorDepartmentRankingPK implements IVendorDepartmentRankingPK {

	private Long vendorid;
	private Long rankingid;
	private Long departmentid;

	public VendorDepartmentRankingPK(){
	}
	public VendorDepartmentRankingPK(Long vendorid, Long rankingid, Long departmentid){
		this.vendorid = vendorid;
		this.rankingid = rankingid;
		this.departmentid = departmentid;
	}

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = vendorid.longValue() - ((IVendorDepartmentRankingPK) arg0).getVendorid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = rankingid.longValue() - ((IVendorDepartmentRankingPK) arg0).getRankingid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = departmentid.longValue() - ((IVendorDepartmentRankingPK) arg0).getDepartmentid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}
	public boolean equals(Object o){
		if (o != null && o instanceof VendorDepartmentRankingPK){
			VendorDepartmentRankingPK that = (VendorDepartmentRankingPK) o;
			return this.vendorid.equals(that.vendorid) && this.rankingid.equals(that.rankingid) && this.departmentid.equals(that.departmentid);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return vendorid.hashCode() + rankingid.hashCode() + departmentid.hashCode();
	}

	public Long getVendorid(){ 
		return this.vendorid;
	}
	public Long getRankingid(){ 
		return this.rankingid;
	}
	public Long getDepartmentid(){ 
		return this.departmentid;
	}
	public void setVendorid(Long vendorid){ 
		this.vendorid = vendorid;
	}
	public void setRankingid(Long rankingid){ 
		this.rankingid = rankingid;
	}
	public void setDepartmentid(Long departmentid){ 
		this.departmentid = departmentid;
	}
}
