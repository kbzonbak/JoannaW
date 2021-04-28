package bbr.esb.events.data.classes;

import bbr.common.adtclasses.classes.PageInitParamDTO;

public class ScoreCardTableOfCompanyInitParamDTO extends PageInitParamDTO {

	String companyrut;

	String sitename;

	String servicename;

	String status;

	Long numdoc;

	String since;

	String until;

	Integer filterType;

	Boolean queryToCount;
	
	String retailname;	
	

	public String getRetailname() {
		return retailname;
	}

	public void setRetailname(String retailname) {
		this.retailname = retailname;
	}

	public Boolean isQueryToCount() {
		return queryToCount;
	}

	public void setQueryToCount(Boolean queryToCount) {
		this.queryToCount = queryToCount;
	}

	public String getCompanyrut() {
		return companyrut;
	}

	public void setCompanyrut(String companyrut) {
		this.companyrut = companyrut;
	}

	public String getSitename() {
		return sitename;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}

	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

}
