package bbr.b2b.regional.logistic.deliveries.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDeliveryStateType extends IElement {

	public String getCode();
	public String getName();
	public Boolean getClosed();
	public Boolean getShowable();
	public void setCode(String code);
	public void setName(String name);
	public void setClosed(Boolean closed);
	public void setShowable(Boolean showable);
	public String getAction(); 
	public void setAction(String action); 
}
