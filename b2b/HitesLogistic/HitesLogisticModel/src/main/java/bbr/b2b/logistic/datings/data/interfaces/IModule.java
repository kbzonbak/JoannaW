package bbr.b2b.logistic.datings.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IModule extends IElement {

	public String getName();
	public LocalDateTime getStarts();
	public LocalDateTime getEnds();
	public Integer getVisualorder();
	public void setName(String name);
	public void setStarts(LocalDateTime starts);
	public void setEnds(LocalDateTime ends);
	public void setVisualorder(Integer visualorder);
}
