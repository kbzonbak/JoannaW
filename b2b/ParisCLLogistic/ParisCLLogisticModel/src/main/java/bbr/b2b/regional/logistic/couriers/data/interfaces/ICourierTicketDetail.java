package bbr.b2b.regional.logistic.couriers.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IIdentifiable;



public interface ICourierTicketDetail extends IIdentifiable {

	public Boolean getProcessed();
	public void setProcessed(Boolean processed);
	public Date getStartdate();
	public void setStartdate(Date startdate);
	public Boolean getSuccess();
	public void setSuccess(Boolean success);
	public String getResult();
	public void setResult(String result);
	
}
