package bbr.b2b.regional.logistic.deliveries.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDODeliveryImage extends IElement {

	public Date getCurrenttimestamp();
	public void setCurrenttimestamp(Date currenttimestamp);
	public String getReceivername();
	public void setReceivername(String receivername);
	public String getReceiverrut();
	public void setReceiverrut(String receiverrut);
	public Date getReceptiondate();
	public void setReceptiondate(Date receptiondate);
	
}
