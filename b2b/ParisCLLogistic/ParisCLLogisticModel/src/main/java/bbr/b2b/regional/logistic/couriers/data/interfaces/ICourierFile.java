package bbr.b2b.regional.logistic.couriers.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface ICourierFile extends IElement {
	public Date getUploaddate();

	public void setUploaddate(Date uploaddate);

	public String getFilename();

	public void setFilename(String filename);

	public Boolean getDayloaded();

	public void setDayloaded(Boolean dayloaded);
}
