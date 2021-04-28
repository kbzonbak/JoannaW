package bbr.b2b.logistic.soa.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface ISOAState extends IElement {

	public Long getId();
	public void setId(Long id);	
	public LocalDateTime getWhen(); 	
	public void setWhen(LocalDateTime when);
	public String getComment();
	public void setComment(String comment);
}