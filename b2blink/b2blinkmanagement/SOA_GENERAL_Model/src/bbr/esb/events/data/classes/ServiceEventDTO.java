package bbr.esb.events.data.classes;

import java.util.Date;

import bbr.common.adtclasses.classes.ElementDTO;
import bbr.esb.events.data.interfaces.IServiceEvent;

public class ServiceEventDTO extends ElementDTO implements IServiceEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8300738006738017296L;

	private Date datecreated;

	private Date dateprocessed;

	private boolean processed;

	private Long sitekey;

	private Long servicekey;

	private Long companykey;
	
	private String resenddata;
	
	private String custom;
	
	
	
	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	public String getResenddata() {
		return resenddata;
	}

	public void setResenddata(String resenddata) {
		this.resenddata = resenddata;
	}

	public Long getCompanykey() {
		return companykey;
	}

	public Date getDatecreated() {
		return datecreated;
	}

	public Date getDateprocessed() {
		return dateprocessed;
	}

	public boolean getProcessed() {
		return processed;
	}

	public Long getServicekey() {
		return servicekey;
	}

	public Long getSitekey() {
		return sitekey;
	}

	public void setCompanykey(Long companykey) {
		this.companykey = companykey;
	}

	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}

	public void setDateprocessed(Date dateprocessed) {
		this.dateprocessed = dateprocessed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public void setServicekey(Long servicekey) {
		this.servicekey = servicekey;
	}

	public void setSitekey(Long sitekey) {
		this.sitekey = sitekey;
	}

}
