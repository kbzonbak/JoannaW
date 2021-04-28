package bbr.b2b.regional.logistic.couriers.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface ICourierState extends IElement {
	public String getDescription();
	public void setDescription(String description);
	public Date getUploaddate();
	public void setUploaddate(Date uploaddate);
	public Date getStartdate();
	public void setStartdate(Date startdate);
}
