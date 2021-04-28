package bbr.b2b.logistic.customer.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IOrderDiscount extends IElement {

	public String getCode();
	public void setCode(String code);
	public String getDescription();
	public Boolean getProcentaje();
	public Double getValue();
	public void setDescription(String description);
	public void setProcentaje(Boolean procentaje);
	public void setValue(Double value);
}
