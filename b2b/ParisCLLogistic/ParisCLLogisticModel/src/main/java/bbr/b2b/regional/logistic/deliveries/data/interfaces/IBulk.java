package bbr.b2b.regional.logistic.deliveries.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IBulk extends IElement {

	public String getCode();
	public Long getNumber();
	public void setCode(String code);
	public void setNumber(Long number);
}
