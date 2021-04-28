package bbr.b2b.logistic.ddcdeliveries.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDdcDeliveryStateType extends IElement {

	public String getCode();
	public void setCode(String code);
	public String getName();
	public void setName(String name);
	public Boolean getClosed();
	public void setClosed(Boolean closed);
	public Boolean getShowable();
	public void setShowable(Boolean showable);
	public Boolean getSelectable();
	public void setSelectable(Boolean selectable);
	public String getCodews();
	public void setCodews(String codews);
}
