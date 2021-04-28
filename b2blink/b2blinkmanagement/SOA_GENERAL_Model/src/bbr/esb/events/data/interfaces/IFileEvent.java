package bbr.esb.events.data.interfaces;

import java.util.Date;

import bbr.common.adtclasses.interfaces.IElement;

public interface IFileEvent extends IElement {

	public Date getDatecreated();

	public Date getDatereceived();

	public String getDocumentid();

	public String getFilename();

	public boolean getInformed();

	public boolean getOk();

	public boolean getReceived();

	public void setDatecreated(Date datecreated);

	public void setDatereceived(Date datereceived);

	public void setDocumentid(String documentid);

	public void setFilename(String filename);

	public void setInformed(boolean informed);

	public void setOk(boolean ok);

	public void setReceived(boolean received);

}
