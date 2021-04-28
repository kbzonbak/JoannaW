package bbr.b2b.logistic.customer.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IOrderCharge extends IElement {

	public String getCode();
	public String getDescription();
	public Boolean getProcentaje();
	public Double getValue();
	public void setCode(String code);
	public void setDescription(String description);
	public void setProcentaje(Boolean procentaje);
	public void setValue(Double value);
}
