package bbr.b2b.regional.logistic.datings.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IModule extends IElement {

	public String getName();
	public Date getStarts();
	public Date getEnds();
	public void setName(String name);
	public void setStarts(Date starts);
	public void setEnds(Date ends);
	public Integer getVisualorder();
	public void setVisualorder(Integer visualorder);
}
