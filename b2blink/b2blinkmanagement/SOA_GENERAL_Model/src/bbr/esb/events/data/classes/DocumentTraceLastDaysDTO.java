package bbr.esb.events.data.classes;

import java.util.Date;

import bbr.common.adtclasses.classes.ElementDTO;
import bbr.esb.events.data.interfaces.IDocumentTraceLastDays;

public class DocumentTraceLastDaysDTO extends ElementDTO implements IDocumentTraceLastDays {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3567025164789718840L;	
	
	private Long numDoc;
	
	private String serviceCode;
	
	private String siteCode;
	
	private String access;
	
	private String companyRut;	

	private Date timestamp;
	
	private Long documenttracetypekey;
	
	private String description;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getNumDoc() {
		return numDoc;
	}

	public void setNumDoc(Long numDoc) {
		this.numDoc = numDoc;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getCompanyRut() {
		return companyRut;
	}

	public void setCompanyRut(String companyRut) {
		this.companyRut = companyRut;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Long getDocumenttracetypekey() {
		return documenttracetypekey;
	}

	public void setDocumenttracetypekey(Long documenttracetypekey) {
		this.documenttracetypekey = documenttracetypekey;
	}

	

	

	

}
