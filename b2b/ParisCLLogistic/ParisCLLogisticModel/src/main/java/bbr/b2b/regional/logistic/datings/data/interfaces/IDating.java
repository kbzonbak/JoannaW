package bbr.b2b.regional.logistic.datings.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDating extends IElement {

	public Long getNumber();
	public Boolean getConfirmed();
	public Date getConfirmationdate();
	public void setNumber(Long number);
	public void setConfirmed(Boolean confirmed);
	public void setConfirmationdate(Date confirmationdate);
	public String getComment(); 
	public void setComment(String comment);
}
