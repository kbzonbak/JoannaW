package bbr.b2b.regional.logistic.locations.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IPropertyLocation extends IElement {

	public String getCode();
	public void setCode(String code);
	public String getDescription();
	public void setDescription(String description);
	public String getValue();
	public void setValue(String value);
	public Date getCreated();
	public void setCreated(Date created);
	public String getCreator();
	public void setCreator(String creator);
	public Date getLastmodified();
	public void setLastmodified(Date lastmodified);
	public String getModifier();
	public void setModifier(String modifier);
	
}
