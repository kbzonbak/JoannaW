package bbr.b2b.logistic.datings.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IReserve extends IElement {

	public LocalDateTime getWhen();
	public void setWhen(LocalDateTime when);
}
