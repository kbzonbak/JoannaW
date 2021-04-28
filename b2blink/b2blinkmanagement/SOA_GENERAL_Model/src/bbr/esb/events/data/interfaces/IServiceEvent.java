package bbr.esb.events.data.interfaces;

import java.util.Date;

import bbr.common.adtclasses.interfaces.IElement;

public interface IServiceEvent extends IElement {

	public Date getDatecreated();

	public Date getDateprocessed();

	public boolean getProcessed();

	public void setDatecreated(Date datecreated);

	public void setDateprocessed(Date dateprocessed);

	public void setProcessed(boolean processed);
	
	public String getResenddata();

	public void setResenddata(String resenddata);
	
	public String getCustom();

	public void setCustom(String custom);
}
