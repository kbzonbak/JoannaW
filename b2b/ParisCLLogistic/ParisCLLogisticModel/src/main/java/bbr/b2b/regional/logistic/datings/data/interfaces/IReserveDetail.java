package bbr.b2b.regional.logistic.datings.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IIdentifiable;

public interface IReserveDetail extends IIdentifiable {

	public Date getWhen();
	public void setWhen(Date when);
}
