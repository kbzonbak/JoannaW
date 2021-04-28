package bbr.b2b.regional.logistic.soa.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDirectOrderSOAState extends IElement {

	public Long getId();
	public void setId(Long id);	
	public Date getWhen(); 	
	public void setWhen(Date when);
	public String getComment();
	public void setComment(String comment);
}