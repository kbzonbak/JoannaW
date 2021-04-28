package bbr.b2b.regional.logistic.couriers.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface ICourier extends IElement {

	public String getCode();

	public void setCode(String code);

	public String getDescription();

	public void setDescription(String description);
	
}
