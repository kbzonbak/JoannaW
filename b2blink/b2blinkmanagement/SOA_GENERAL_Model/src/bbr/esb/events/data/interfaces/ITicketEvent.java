package bbr.esb.events.data.interfaces;

import java.util.Date;

import bbr.common.adtclasses.interfaces.IElement;

public interface ITicketEvent extends IElement {

	public Date getCurrentstatetypedate();

	public Date getDatecreated();

	public String getMessageid();

	public Long getTicketnumber();

	public void setCurrentstatetypedate(Date currentstatetypedate);

	public void setDatecreated(Date datecreated);

	public void setMessageid(String messageid);

	public void setTicketnumber(Long ticketnumber);
	
	public String getAdjunto();

	public void setAdjunto(String adjunto);
	
	public String getReferencia();

	public void setReferencia(String reference);
	
}
