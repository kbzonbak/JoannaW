package bbr.esb.services.data.interfaces;

import bbr.common.adtclasses.interfaces.IElement;

public interface IService extends IElement {

	public String getCode();

	public String getName();

	public void setCode(String code);

	public void setName(String name);

}
