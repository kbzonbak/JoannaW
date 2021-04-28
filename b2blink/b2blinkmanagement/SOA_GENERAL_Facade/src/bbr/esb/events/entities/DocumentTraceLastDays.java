package bbr.esb.events.entities;

import java.util.Date;

import bbr.esb.events.data.interfaces.IDocumentTraceLastDays;

public class DocumentTraceLastDays implements IDocumentTraceLastDays {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3567025164789718840L;

	private Long id;
	
	private Long numDoc;
	
	private String serviceCode;
	
	private String siteCode;
	
	private String access;
	
	private String companyRut;	

	private Date timestamp;
	
	private DocumentTraceType documenttracetype;
	
	private String description;	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public DocumentTraceType getDocumenttracetype() {
		return documenttracetype;
	}

	public void setDocumenttracetype(DocumentTraceType documenttracetype) {
		this.documenttracetype = documenttracetype;
	}



	


}
