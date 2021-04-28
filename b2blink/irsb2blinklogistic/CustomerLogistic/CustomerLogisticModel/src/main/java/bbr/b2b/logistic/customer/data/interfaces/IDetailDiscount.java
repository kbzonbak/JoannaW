package bbr.b2b.logistic.customer.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IIdentifiable;

public interface IDetailDiscount extends IIdentifiable {

	public String getDescription();
	public Boolean getProcentaje();
	public Double getValue();
	public String getCode();
	public void setDescription(String description);
	public void setProcentaje(Boolean procentaje);
	public void setValue(Double value);
	public void setCode(String code);
}
