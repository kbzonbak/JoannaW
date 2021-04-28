package bbr.b2b.logistic.ddcorders.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DdcOrderReportDataDTO implements Serializable {

	private Long ddcorderid;
	private Long ddcordernumber;
	private String ddcorderreferencenumber;
	private Long ddcorderstatetypeid;
	private String ddcorderstatetypecode;
	private String ddcorderstatetypename;
	private Long currentddcdeliveryid;
	private Long currentddcdeliverystatetypeid;
	private String currentddcdeliverystatetypecode;
	private String currentddcdeliverystatetypename;
	private LocalDateTime currentddcdeliverystatetypedate;
	private LocalDateTime emitteddate;
	private LocalDateTime originaldeliverydate;
	private LocalDateTime currentdeliverydate;
	private LocalDateTime expirationdate;
	private Integer reschedulingcounter;
	private String ddcordercomment;
	private Long clientid;
	private String clientname;
	private String clientdni;
	private String clientaddress;
	private String clientcommune;
	private String clientcity;
	private Double needunits;
	private Double totalneed;
	private Long ddcfileid;
	private String ddcfilename;
	private Boolean warningoc;
	private Double pendingunits;

	public Long getDdcorderid() {
		return ddcorderid;
	}

	public void setDdcorderid(Long ddcorderid) {
		this.ddcorderid = ddcorderid;
	}

	public Long getDdcordernumber() {
		return ddcordernumber;
	}

	public void setDdcordernumber(Long ddcordernumber) {
		this.ddcordernumber = ddcordernumber;
	}

	public String getDdcorderreferencenumber() {
		return ddcorderreferencenumber;
	}

	public void setDdcorderreferencenumber(String ddcorderreferencenumber) {
		this.ddcorderreferencenumber = ddcorderreferencenumber;
	}

	public Long getDdcorderstatetypeid() {
		return ddcorderstatetypeid;
	}

	public void setDdcorderstatetypeid(Long ddcorderstatetypeid) {
		this.ddcorderstatetypeid = ddcorderstatetypeid;
	}

	public String getDdcorderstatetypecode() {
		return ddcorderstatetypecode;
	}

	public void setDdcorderstatetypecode(String ddcorderstatetypecode) {
		this.ddcorderstatetypecode = ddcorderstatetypecode;
	}

	public String getDdcorderstatetypename() {
		return ddcorderstatetypename;
	}

	public void setDdcorderstatetypename(String ddcorderstatetypename) {
		this.ddcorderstatetypename = ddcorderstatetypename;
	}

	public Long getCurrentddcdeliveryid() {
		return currentddcdeliveryid;
	}

	public void setCurrentddcdeliveryid(Long currentddcdeliveryid) {
		this.currentddcdeliveryid = currentddcdeliveryid;
	}

	public Long getCurrentddcdeliverystatetypeid() {
		return currentddcdeliverystatetypeid;
	}

	public void setCurrentddcdeliverystatetypeid(Long currentddcdeliverystatetypeid) {
		this.currentddcdeliverystatetypeid = currentddcdeliverystatetypeid;
	}

	public String getCurrentddcdeliverystatetypecode() {
		return currentddcdeliverystatetypecode;
	}

	public void setCurrentddcdeliverystatetypecode(String currentddcdeliverystatetypecode) {
		this.currentddcdeliverystatetypecode = currentddcdeliverystatetypecode;
	}

	public String getCurrentddcdeliverystatetypename() {
		return currentddcdeliverystatetypename;
	}

	public void setCurrentddcdeliverystatetypename(String currentddcdeliverystatetypename) {
		this.currentddcdeliverystatetypename = currentddcdeliverystatetypename;
	}

	public LocalDateTime getCurrentddcdeliverystatetypedate() {
		return currentddcdeliverystatetypedate;
	}

	public void setCurrentddcdeliverystatetypedate(LocalDateTime currentddcdeliverystatetypedate) {
		this.currentddcdeliverystatetypedate = currentddcdeliverystatetypedate;
	}

	public LocalDateTime getEmitteddate() {
		return emitteddate;
	}

	public void setEmitteddate(LocalDateTime emitteddate) {
		this.emitteddate = emitteddate;
	}

	public LocalDateTime getOriginaldeliverydate() {
		return originaldeliverydate;
	}

	public void setOriginaldeliverydate(LocalDateTime originaldeliverydate) {
		this.originaldeliverydate = originaldeliverydate;
	}

	public LocalDateTime getCurrentdeliverydate() {
		return currentdeliverydate;
	}

	public void setCurrentdeliverydate(LocalDateTime currentdeliverydate) {
		this.currentdeliverydate = currentdeliverydate;
	}

	public LocalDateTime getExpirationdate() {
		return expirationdate;
	}

	public void setExpirationdate(LocalDateTime expirationdate) {
		this.expirationdate = expirationdate;
	}

	public Integer getReschedulingcounter() {
		return reschedulingcounter;
	}

	public void setReschedulingcounter(Integer reschedulingcounter) {
		this.reschedulingcounter = reschedulingcounter;
	}

	public String getDdcordercomment() {
		return ddcordercomment;
	}

	public void setDdcordercomment(String ddcordercomment) {
		this.ddcordercomment = ddcordercomment;
	}

	public Long getClientid() {
		return clientid;
	}

	public void setClientid(Long clientid) {
		this.clientid = clientid;
	}

	public String getClientname() {
		return clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}

	public String getClientdni() {
		return clientdni;
	}

	public void setClientdni(String clientdni) {
		this.clientdni = clientdni;
	}

	public String getClientaddress() {
		return clientaddress;
	}

	public void setClientaddress(String clientaddress) {
		this.clientaddress = clientaddress;
	}

	public String getClientcommune() {
		return clientcommune;
	}

	public void setClientcommune(String clientcommune) {
		this.clientcommune = clientcommune;
	}

	public String getClientcity() {
		return clientcity;
	}

	public void setClientcity(String clientcity) {
		this.clientcity = clientcity;
	}

	public Double getNeedunits() {
		return needunits;
	}

	public void setNeedunits(Double needunits) {
		this.needunits = needunits;
	}

	public Double getTotalneed() {
		return totalneed;
	}

	public void setTotalneed(Double totalneed) {
		this.totalneed = totalneed;
	}

	public Long getDdcfileid() {
		return ddcfileid;
	}

	public void setDdcfileid(Long ddcfileid) {
		this.ddcfileid = ddcfileid;
	}

	public String getDdcfilename() {
		return ddcfilename;
	}

	public void setDdcfilename(String ddcfilename) {
		this.ddcfilename = ddcfilename;
	}

	public Boolean getWarningoc() {
		return warningoc;
	}

	public void setWarningoc(Boolean warningoc) {
		this.warningoc = warningoc;
	}

	public Double getPendingunits() {
		return pendingunits;
	}

	public void setPendingunits(Double pendingunits) {
		this.pendingunits = pendingunits;
	}

}
