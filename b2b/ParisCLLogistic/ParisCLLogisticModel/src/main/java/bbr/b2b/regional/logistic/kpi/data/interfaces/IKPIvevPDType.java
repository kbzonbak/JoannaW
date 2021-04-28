package bbr.b2b.regional.logistic.kpi.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IKPIvevPDType extends IElement{

	public String getCode();
	public void setCode(String code);
	public String getDescription();
	public void setDescription(String description);
	public Boolean getFine();
	public void setFine(Boolean fine);
}