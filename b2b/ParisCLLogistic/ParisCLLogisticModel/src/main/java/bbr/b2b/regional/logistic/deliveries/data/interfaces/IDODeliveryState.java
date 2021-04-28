package bbr.b2b.regional.logistic.deliveries.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDODeliveryState extends IElement {

	public Date getWhen();
	public Date getDeliverystatedate();
	public String getWho();
	public String getComment();
	public void setWhen(Date when);
	public void setDeliverystatedate(Date deliverystatedate);
	public void setWho(String who);
	public void setComment(String comment);
}
