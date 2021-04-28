package bbr.b2b.logistic.datings.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IIdentifiable;

public interface IDatingResource extends IIdentifiable {

	public Boolean getActive();
	public Integer getVisualorder();
	public void setActive(Boolean active);
	public void setVisualorder(Integer visualorder);
}
