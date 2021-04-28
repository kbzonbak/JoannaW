package bbr.b2b.regional.logistic.vendors.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.vendors.data.interfaces.IVendorDepartmentRanking;
import bbr.b2b.regional.logistic.vendors.data.interfaces.IVendorDepartmentRankingPK;

public class VendorDepartmentRankingW implements IVendorDepartmentRankingPK, IVendorDepartmentRanking {

	private Double totalreceivedunits;
	private Double totalavailableunits;
	private Double sumscoreweighed;
	private Integer rejectedcount;
	private Integer nonassistancecount;
	private Integer approvedcount;
	private Long departmentid;
	private Long vendorid;
	private Long rankingid;

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

	public Double getTotalreceivedunits(){ 
		return this.totalreceivedunits;
	}
	public Double getTotalavailableunits(){ 
		return this.totalavailableunits;
	}
	public Double getSumscoreweighed(){ 
		return this.sumscoreweighed;
	}
	public Integer getRejectedcount(){ 
		return this.rejectedcount;
	}
	public Integer getNonassistancecount(){ 
		return this.nonassistancecount;
	}
	public Integer getApprovedcount(){ 
		return this.approvedcount;
	}
	public Long getDepartmentid(){ 
		return this.departmentid;
	}
	public Long getVendorid(){ 
		return this.vendorid;
	}
	public Long getRankingid(){ 
		return this.rankingid;
	}
	public void setTotalreceivedunits(Double totalreceivedunits){ 
		this.totalreceivedunits = totalreceivedunits;
	}
	public void setTotalavailableunits(Double totalavailableunits){ 
		this.totalavailableunits = totalavailableunits;
	}
	public void setSumscoreweighed(Double sumscoreweighed){ 
		this.sumscoreweighed = sumscoreweighed;
	}
	public void setRejectedcount(Integer rejectedcount){ 
		this.rejectedcount = rejectedcount;
	}
	public void setNonassistancecount(Integer nonassistancecount){ 
		this.nonassistancecount = nonassistancecount;
	}
	public void setApprovedcount(Integer approvedcount){ 
		this.approvedcount = approvedcount;
	}
	public void setDepartmentid(Long departmentid){ 
		this.departmentid = departmentid;
	}
	public void setVendorid(Long vendorid){ 
		this.vendorid = vendorid;
	}
	public void setRankingid(Long rankingid){ 
		this.rankingid = rankingid;
	}
}
