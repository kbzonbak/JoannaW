package bbr.b2b.logistic.customer.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IReception extends IElement {

	public Long getReceptionnumber();
	public Long getOrdernumber();
	public String getOrdertypename();
	public boolean getComplete();
	public Date getReceptiondate();
	public Double getTotal();
	public String getPaymentcondition();
	public String getObservation();
	public String getResponsible();
	public void setReceptionnumber(Long receptionnumber);
	public void setOrdernumber(Long ordernumber);
	public void setOrdertypename(String ordertypename);
	public void setComplete(boolean complete);
	public void setReceptiondate(Date receptiondate);
	public void setTotal(Double total);
	public void setPaymentcondition(String paymentcondition);
	public void setObservation(String observation);
	public void setResponsible(String responsible);
}
