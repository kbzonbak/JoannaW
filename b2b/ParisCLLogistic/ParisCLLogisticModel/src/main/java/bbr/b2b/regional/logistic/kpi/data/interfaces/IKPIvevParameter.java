package bbr.b2b.regional.logistic.kpi.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IKPIvevParameter extends IElement{

	public String getCode();
	public void setCode(String code);
	public String getDescription();
	public void setDescription(String description);
	public Double getValue();
	public void setValue(Double value);
	public String getUm();
	public void setUm(String um);
	public Boolean getVisible();
	public void setVisible(Boolean visible);
	
}