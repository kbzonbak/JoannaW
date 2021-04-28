package bbr.esb.services.data.interfaces;

import java.util.Date;

import bbr.common.adtclasses.interfaces.IIdentifiable;

public interface IContractedService extends IIdentifiable {

	public boolean getActive();

	public void setActive(boolean active);

	public Date getLastsentmessage();

	public void setLastsentmessage(Date lastsentmessage);

	public Date getActivation();

	public void setActivation(Date activation);

	public boolean getMonitor();

	public void setMonitor(boolean monitor);

	public Date getLastmonitored();

	public void setLastmonitored(Date lastmonitored);

	public Integer getPendingmessages();

	public void setPendingmessages(Integer pendingmessages);
	
	public Integer getCustommaxdelayendflow();

	public void setCustommaxdelayendflow(Integer custommaxdelayendflow);
	
	public String getMonitoringannotation() ;
	
	public void setMonitoringannotation(String monitoringannotation);
	
	public boolean getCompresseddocument();

	public void setCompresseddocument(boolean compresseddocument);	

	public String getExtratorcredentials();

	public void setExtratorcredentials(String extratorcredentials);
	
	public String getCustom();

	public void setCustom(String custom);
	
	public String getDetail();

	public void setDetail(String detail);
}
