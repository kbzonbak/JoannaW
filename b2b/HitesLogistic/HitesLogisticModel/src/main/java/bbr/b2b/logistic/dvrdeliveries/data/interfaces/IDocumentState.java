package bbr.b2b.logistic.dvrdeliveries.data.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IDocumentState extends IElement {

	public LocalDateTime getWhen();
	public Boolean getStatus();
	public String getDocumentresponsemessage();
	public String getDocumentresponsecode();
	public String getUser();
	public String getUsertype();
	public void setWhen(LocalDateTime when);
	public void setStatus(Boolean status);
	public void setDocumentresponsemessage(String documentresponsemessage);
	public void setDocumentresponsecode(String documentresponsecode);
	public void setUser(String user);
	public void setUsertype(String usertype);
}
