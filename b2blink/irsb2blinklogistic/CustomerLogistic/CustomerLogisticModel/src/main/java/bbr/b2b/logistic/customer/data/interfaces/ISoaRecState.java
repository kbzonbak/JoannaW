package bbr.b2b.logistic.customer.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface ISoaRecState extends IElement {

	public Date getWhen();
	public String getComment();
	public void setWhen(Date when);
	public void setComment(String comment);
}
