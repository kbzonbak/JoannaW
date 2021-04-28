package bbr.b2b.logistic.dvrdeliveries.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDvrDelivery extends IElement {

	public Long getNumber();
	public LocalDateTime getCreated();
	public LocalDateTime getCurrentstatetypedate();
	public LocalDateTime getPluploaddate();
	public void setNumber(Long number);
	public void setCreated(LocalDateTime created);
	public void setCurrentstatetypedate(LocalDateTime currentstatetypedate);
	public void setPluploaddate(LocalDateTime pluploaddate);
}
