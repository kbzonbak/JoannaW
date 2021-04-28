package bbr.b2b.regional.logistic.vendors.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IIdentifiable;

public interface IVendorRanking extends IIdentifiable {

	public Double getTotalreceivedunits();
	public Double getTotalavailableunits();
	public Double getSumscoreweighed();
	public Integer getRejectedcount();
	public Integer getNonassistancecount();
	public Integer getApprovedcount();
	public void setTotalreceivedunits(Double totalreceivedunits);
	public void setTotalavailableunits(Double totalavailableunits);
	public void setSumscoreweighed(Double sumscoreweighed);
	public void setRejectedcount(Integer rejectedcount);
	public void setNonassistancecount(Integer nonassistancecount);
	public void setApprovedcount(Integer approvedcount);
}
