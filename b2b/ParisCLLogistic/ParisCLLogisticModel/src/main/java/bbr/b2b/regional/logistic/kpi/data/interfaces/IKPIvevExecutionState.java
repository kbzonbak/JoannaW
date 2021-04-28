package bbr.b2b.regional.logistic.kpi.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IKPIvevExecutionState extends IElement{

	public Date getWhen1();
	public void setWhen1(Date when1);
	public String getType();
	public void setType(String type);
		
}
