package bbr.b2b.regional.logistic.taxdocuments.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDOTaxDocumentState extends IElement {

	public Date getWhen();
	public void setWhen(Date when);
	public String getWho();
	public void setWho(String who);
	public String getUsertype();
	public void setUsertype(String usertype);
	public String getComment();
	public void setComment(String comment);
	public Long getTicketnumber();
	public void setTicketnumber(Long ticketnumber);
	
}