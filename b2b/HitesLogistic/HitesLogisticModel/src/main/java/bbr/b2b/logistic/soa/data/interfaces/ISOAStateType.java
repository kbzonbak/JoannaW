package bbr.b2b.logistic.soa.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface ISOAStateType extends IElement {

	public Long getId();
	public void setId(Long id);
	public String getCode(); 
	public void setCode(String code);	
	public String getName(); 
	public void setName(String name);	
}