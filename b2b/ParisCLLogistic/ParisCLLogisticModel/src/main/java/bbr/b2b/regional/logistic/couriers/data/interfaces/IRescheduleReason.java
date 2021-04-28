package bbr.b2b.regional.logistic.couriers.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IRescheduleReason extends IElement {

	public String getCode();
	public void setCode(String code);
	public String getDescription();
	public void setDescription(String description);
	public Integer getVisualorder();
	public void setVisualorder(Integer visualorder);
	public Boolean getShowable();
	public void setShowable(Boolean showable);
	public String getResponsibility();
	public void setResponsibility(String responsibility);
	
}
