package bbr.esb.services.data.interfaces;

import java.util.Date;

import bbr.common.adtclasses.interfaces.IElement;

public interface ISite extends IElement {

	public String getCode();

	public Date getCreated();

	public String getName();

	public void setCode(String code);

	public void setCreated(Date created);

	public void setName(String name);

	public String getRetailname();

	public void setRetailname(String retailname);
}
