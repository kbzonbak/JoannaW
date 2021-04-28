package bbr.b2b.regional.logistic.fillrate.entities;

import bbr.b2b.regional.logistic.vendors.entities.Vendor;
import bbr.b2b.regional.logistic.vendors.entities.Department;
import bbr.b2b.regional.logistic.fillrate.entities.FillRatePeriod;
import bbr.b2b.regional.logistic.fillrate.data.interfaces.IFillRate;

public class FillRate implements IFillRate {

	private Long id;
	private Double totalunits;
	private Double receivedunits;
	private Double totalneed;
	private Double totalreceived;
	private Integer countorders;
	private Vendor vendor;
	private Department department;
	private FillRatePeriod fillrateperiod;

	public Long getId(){ 
		return this.id;
	}
	public Double getTotalunits(){ 
		return this.totalunits;
	}
	public Double getReceivedunits(){ 
		return this.receivedunits;
	}
	public Double getTotalneed(){ 
		return this.totalneed;
	}
	public Double getTotalreceived(){ 
		return this.totalreceived;
	}
	public Integer getCountorders(){ 
		return this.countorders;
	}
	public Vendor getVendor(){ 
		return this.vendor;
	}
	public Department getDepartment(){ 
		return this.department;
	}
	public FillRatePeriod getFillrateperiod(){ 
		return this.fillrateperiod;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setTotalunits(Double totalunits){ 
		this.totalunits = totalunits;
	}
	public void setReceivedunits(Double receivedunits){ 
		this.receivedunits = receivedunits;
	}
	public void setTotalneed(Double totalneed){ 
		this.totalneed = totalneed;
	}
	public void setTotalreceived(Double totalreceived){ 
		this.totalreceived = totalreceived;
	}
	public void setCountorders(Integer countorders){ 
		this.countorders = countorders;
	}
	public void setVendor(Vendor vendor){ 
		this.vendor = vendor;
	}
	public void setDepartment(Department department){ 
		this.department = department;
	}
	public void setFillrateperiod(FillRatePeriod fillrateperiod){ 
		this.fillrateperiod = fillrateperiod;
	}
}
