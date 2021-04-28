package bbr.b2b.logistic.ddcdeliveries.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDdcDelivery extends IElement {

	public Long getNumber();
	public void setNumber(Long number);
	public LocalDateTime getOriginaldate();
	public void setOriginaldate(LocalDateTime originaldate);
	public LocalDateTime getCommitteddate();
	public void setCommitteddate(LocalDateTime committeddate);
	public LocalDateTime getCurrentstatetypedate();
	public void setCurrentstatetypedate(LocalDateTime currentstatetypedate);
	public String getCurrentstatetypewho();
	public void setCurrentstatetypewho(String currentstatetypewho);
	public String getCurrentstatetypecomment();
	public void setCurrentstatetypecomment(String currentstatetypecomment);
}
