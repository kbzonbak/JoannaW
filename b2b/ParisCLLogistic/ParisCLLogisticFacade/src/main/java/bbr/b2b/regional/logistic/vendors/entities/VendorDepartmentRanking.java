package bbr.b2b.regional.logistic.vendors.entities;

import bbr.b2b.regional.logistic.vendors.entities.Vendor;
import bbr.b2b.regional.logistic.vendors.entities.VendorRanking;
import bbr.b2b.regional.logistic.vendors.data.interfaces.IVendorDepartmentRanking;

public class VendorDepartmentRanking implements IVendorDepartmentRanking {

	private VendorDepartmentRankingId id;
	private Double totalreceivedunits;
	private Double totalavailableunits;
	private Double sumscoreweighed;
	private Integer rejectedcount;
	private Integer nonassistancecount;
	private Integer approvedcount;
	private Vendor department;
	private VendorRanking vendorranking;

	public VendorDepartmentRankingId getId(){ 
		return this.id;
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
	public Vendor getDepartment(){ 
		return this.department;
	}
	public VendorRanking getVendorranking(){ 
		return this.vendorranking;
	}
	public void setId(VendorDepartmentRankingId id){ 
		this.id = id;
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
	public void setDepartment(Vendor department){ 
		this.department = department;
	}
	public void setVendorranking(VendorRanking vendorranking){ 
		this.vendorranking = vendorranking;
	}
}
