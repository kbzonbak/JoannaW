package bbr.b2b.logistic.dvrdeliveries.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IIdentifiable;

public interface IDvrOrderDelivery extends IIdentifiable {

	public Boolean getClosed();
	public Double getEstimatedunits();
	public LocalDateTime getReceptiondate();
	public void setClosed(Boolean closed);
	public void setEstimatedunits(Double estimatedunits);
	public void setReceptiondate(LocalDateTime receptiondate);
}
