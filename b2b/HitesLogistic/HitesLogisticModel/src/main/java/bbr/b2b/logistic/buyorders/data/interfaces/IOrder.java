package bbr.b2b.logistic.buyorders.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IOrder extends IElement {

	public Long getNumber();
	public LocalDateTime getEmitted();
	public LocalDateTime getCreationdate();
	public void setNumber(Long number);
	public void setEmitted(LocalDateTime emitted);
	public void setCreationdate(LocalDateTime creationdate);
}
