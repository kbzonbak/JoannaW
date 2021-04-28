package bbr.b2b.regional.logistic.deliveries.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IIdentifiable;

public interface IOrderDelivery extends IIdentifiable {

	public Boolean getClosed();
	public Long getAsnimp();
	public Long getRefdocument();
	public String getImp();
	public Integer getDeliveryindex();
	public Double getEstimatedunits();
	public Double getOriginalestimunits();
	public Integer getRealbulkcount();
	public Integer getRealpalletcount();
	public Double getRealpackedunits();
	public void setClosed(Boolean closed);
	public void setAsnimp(Long asnimp);
	public void setRefdocument(Long refdocument);
	public void setImp(String imp);
	public void setDeliveryindex(Integer deliveryindex);
	public void setEstimatedunits(Double estimatedunits);
	public void setOriginalestimunits(Double originalestimunits);
	public void setRealbulkcount(Integer realbulkcount);
	public void setRealpalletcount(Integer realpalletcount);
	public void setRealpackedunits(Double realpackedunits);
}
