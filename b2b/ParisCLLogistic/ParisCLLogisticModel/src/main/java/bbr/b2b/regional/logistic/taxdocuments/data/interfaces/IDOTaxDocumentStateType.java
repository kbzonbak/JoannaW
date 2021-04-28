package bbr.b2b.regional.logistic.taxdocuments.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDOTaxDocumentStateType extends IElement {

	public String getCode();
	public void setCode(String code);
	public String getName();
	public void setName(String name);
	public String getAction();
	public void setAction(String action);
	public Boolean getClosed();
	public void setClosed(Boolean closed);
	public Boolean getShowable();
	public void setShowable(Boolean showable);
	
}