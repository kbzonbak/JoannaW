package bbr.b2b.regional.logistic.fillrate.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.fillrate.data.interfaces.IFillRate;

public class FillRateW extends ElementDTO implements IFillRate {

	private Double totalunits;
	private Double receivedunits;
	private Double totalneed;
	private Double totalreceived;
	private Integer countorders;
	private Long vendorid;
	private Long departmentid;
	private Long fillrateperiodid;

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
	public Long getVendorid(){ 
		return this.vendorid;
	}
	public Long getDepartmentid(){ 
		return this.departmentid;
	}
	public Long getFillrateperiodid(){ 
		return this.fillrateperiodid;
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
	public void setVendorid(Long vendorid){ 
		this.vendorid = vendorid;
	}
	public void setDepartmentid(Long departmentid){ 
		this.departmentid = departmentid;
	}
	public void setFillrateperiodid(Long fillrateperiodid){ 
		this.fillrateperiodid = fillrateperiodid;
	}
}
