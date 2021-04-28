package bbr.b2b.logistic.buyorders.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IRetailer extends IElement {

	public String getCode();
	public String getDni();
	public String getDescription();
	public String getGln();
	public void setCode(String code);
	public void setDni(String dni);
	public void setDescription(String description);
	public void setGln(String gln);
}
