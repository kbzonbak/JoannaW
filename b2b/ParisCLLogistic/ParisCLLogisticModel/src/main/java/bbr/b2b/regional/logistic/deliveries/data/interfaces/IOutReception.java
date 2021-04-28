package bbr.b2b.regional.logistic.deliveries.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IOutReception extends IElement {

	public Long getNumber();
	public Long getOutdelivery();
	public Date getReceptiondate();
	public Boolean getInprocess();
	public void setNumber(Long number);
	public void setOutdelivery(Long outdelivery);
	public void setReceptiondate(Date receptiondate);
	public void setInprocess(Boolean inprocess);
}
