package bbr.esb.events.data.classes;

import bbr.common.adtclasses.classes.PageInitParamDTO;

public class TicketReportInitParamDTO extends PageInitParamDTO {

	String companyrut;

	String serviceid;

	String statusid;

	Long numdoc;

	String since;

	String until;
	
	String reference;

	Integer filterType;
	
	Integer filterTypeSol;

	Boolean queryToCount;
	
	Long siteid;
	

	public Integer getFilterTypeSol() {
		return filterTypeSol;
	}

	public void setFilterTypeSol(Integer filterTypeSol) {
		this.filterTypeSol = filterTypeSol;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getCompanyrut() {
		return companyrut;
	}

	public void setCompanyrut(String companyrut) {
		this.companyrut = companyrut;
	}

	public String getServiceid() {
		return serviceid;
	}

	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}

	public String getStatusid() {
		return statusid;
	}

	public void setStatusid(String statusid) {
		this.statusid = statusid;
	}

	public Long getNumdoc() {
		return numdoc;
	}

	public void setNumdoc(Long numdoc) {
		this.numdoc = numdoc;
	}

	public String getSince() {
		return since;
	}

	public void setSince(String since) {
		this.since = since;
	}

	public String getUntil() {
		return until;
	}

	public void setUntil(String until) {
		this.until = until;
	}

	public Integer getFilterType() {
		return filterType;
	}

	public void setFilterType(Integer filterType) {
		this.filterType = filterType;
	}

	public Boolean isQueryToCount() {
		return queryToCount;
	}

	public void setQueryToCount(Boolean queryToCount) {
		this.queryToCount = queryToCount;
	}

	public Long getSiteid() {
		return siteid;
	}

	public void setSiteid(Long siteid) {
		this.siteid = siteid;
	}

}
