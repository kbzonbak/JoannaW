package bbr.esb.events.data.interfaces;

import java.util.Date;

import bbr.common.adtclasses.interfaces.IElement;

public interface IDocumentTrace extends IElement {
	

	public Long getNumDoc();
	
	public void setNumDoc(Long numDoc);
	
	public String getServiceCode();
	
	public void setServiceCode(String serviceCode);
	
	public String getSiteCode();
	
	public void setSiteCode(String siteCode);
	
	public String getAccess();
	
	public void setAccess(String access);
	
	public String getCompanyRut();
	
	public void setCompanyRut(String companyRut);
	
	public Date getTimestamp();
	
	public void setTimestamp(Date timestamp);	


	public String getDescription();

	public void setDescription(String description);

}