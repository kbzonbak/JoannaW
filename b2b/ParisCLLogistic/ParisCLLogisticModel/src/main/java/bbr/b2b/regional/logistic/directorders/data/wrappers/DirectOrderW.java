package bbr.b2b.regional.logistic.directorders.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.directorders.data.interfaces.IDirectOrder;

public class DirectOrderW extends ElementDTO implements IDirectOrder {

	private Long number;
	private String distributionordernumber;
	private String requestnumber;
	private Boolean active;
	private Date emitted;
	private Date valid;
	private Date expiration;
	private Date currentdeliverydate;
	private Date originaldeliverydate;
	private Date currentstatetypedate;
	private String currentstatetypecomment;
	private String currentstatetypewho;
	private Date paymentdate;
	private String paymenttype;
	private String season;
	private String clientaddress;
	private String clientcity;
	private String clientcommune;
	private String clientdeptnumber;
	private String clienthousenumber;
	private String clientphone;
	private String clientregion;
	private String clientroadnumber;
	private Double needunits = 0D;
	private Double pendingunits = 0D;
	private Double receivedunits = 0D;
	private Double todeliveryunits = 0D;
	private Double totalpending = 0D;
	private Double totalreceived = 0D;
	private Double totaltodelivery = 0D;
	private Double totalneed = 0D;
	private String comment;
	private Boolean courierreceived;
	private Long clientid;
	private Long vendorid;
	private Long responsableid;
	private Long currentstatetypeid;
	private Long departmentid;
	private Long dodeliveryid;	
	private Long currentsoastatetypeid;	
	private Date currentsoastatetypedate;	
	private Long salestoreid;
	
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public String getDistributionordernumber() {
		return distributionordernumber;
	}
	public void setDistributionordernumber(String distributionordernumber) {
		this.distributionordernumber = distributionordernumber;
	}
	public String getRequestnumber() {
		return requestnumber;
	}
	public void setRequestnumber(String requestnumber) {
		this.requestnumber = requestnumber;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Date getEmitted() {
		return emitted;
	}
	public void setEmitted(Date emitted) {
		this.emitted = emitted;
	}
	public Date getValid() {
		return valid;
	}
	public void setValid(Date valid) {
		this.valid = valid;
	}
	public Date getExpiration() {
		return expiration;
	}
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	public Date getCurrentdeliverydate() {
		return currentdeliverydate;
	}
	public void setCurrentdeliverydate(Date currentdeliverydate) {
		this.currentdeliverydate = currentdeliverydate;
	}
	public Date getOriginaldeliverydate() {
		return originaldeliverydate;
	}
	public void setOriginaldeliverydate(Date originaldeliverydate) {
		this.originaldeliverydate = originaldeliverydate;
	}
	public Date getCurrentstatetypedate() {
		return currentstatetypedate;
	}
	public void setCurrentstatetypedate(Date currentstatetypedate) {
		this.currentstatetypedate = currentstatetypedate;
	}
	public String getCurrentstatetypecomment() {
		return currentstatetypecomment;
	}
	public void setCurrentstatetypecomment(String currentstatetypecomment) {
		this.currentstatetypecomment = currentstatetypecomment;
	}
	public String getCurrentstatetypewho() {
		return currentstatetypewho;
	}
	public void setCurrentstatetypewho(String currentstatetypewho) {
		this.currentstatetypewho = currentstatetypewho;
	}
	public Date getPaymentdate() {
		return paymentdate;
	}
	public void setPaymentdate(Date paymentdate) {
		this.paymentdate = paymentdate;
	}
	public String getPaymenttype() {
		return paymenttype;
	}
	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public String getClientaddress() {
		return clientaddress;
	}
	public void setClientaddress(String clientaddress) {
		this.clientaddress = clientaddress;
	}
	public String getClientcity() {
		return clientcity;
	}
	public void setClientcity(String clientcity) {
		this.clientcity = clientcity;
	}
	public String getClientcommune() {
		return clientcommune;
	}
	public void setClientcommune(String clientcommune) {
		this.clientcommune = clientcommune;
	}
	public String getClientdeptnumber() {
		return clientdeptnumber;
	}
	public void setClientdeptnumber(String clientdeptnumber) {
		this.clientdeptnumber = clientdeptnumber;
	}
	public String getClienthousenumber() {
		return clienthousenumber;
	}
	public void setClienthousenumber(String clienthousenumber) {
		this.clienthousenumber = clienthousenumber;
	}
	public String getClientphone() {
		return clientphone;
	}
	public void setClientphone(String clientphone) {
		this.clientphone = clientphone;
	}
	public String getClientregion() {
		return clientregion;
	}
	public void setClientregion(String clientregion) {
		this.clientregion = clientregion;
	}
	public String getClientroadnumber() {
		return clientroadnumber;
	}
	public void setClientroadnumber(String clientroadnumber) {
		this.clientroadnumber = clientroadnumber;
	}
	public Double getNeedunits() {
		return needunits;
	}
	public void setNeedunits(Double needunits) {
		this.needunits = needunits;
	}
	public Double getPendingunits() {
		return pendingunits;
	}
	public void setPendingunits(Double pendingunits) {
		this.pendingunits = pendingunits;
	}
	public Double getReceivedunits() {
		return receivedunits;
	}
	public void setReceivedunits(Double receivedunits) {
		this.receivedunits = receivedunits;
	}
	public Double getTodeliveryunits() {
		return todeliveryunits;
	}
	public void setTodeliveryunits(Double todeliveryunits) {
		this.todeliveryunits = todeliveryunits;
	}
	public Double getTotalpending() {
		return totalpending;
	}
	public void setTotalpending(Double totalpending) {
		this.totalpending = totalpending;
	}
	public Double getTotalreceived() {
		return totalreceived;
	}
	public void setTotalreceived(Double totalreceived) {
		this.totalreceived = totalreceived;
	}
	public Double getTotaltodelivery() {
		return totaltodelivery;
	}
	public void setTotaltodelivery(Double totaltodelivery) {
		this.totaltodelivery = totaltodelivery;
	}
	public Double getTotalneed() {
		return totalneed;
	}
	public void setTotalneed(Double totalneed) {
		this.totalneed = totalneed;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Boolean getCourierreceived() {
		return courierreceived;
	}
	public void setCourierreceived(Boolean courierreceived) {
		this.courierreceived = courierreceived;
	}
	public Long getClientid() {
		return clientid;
	}
	public void setClientid(Long clientid) {
		this.clientid = clientid;
	}
	public Long getVendorid() {
		return vendorid;
	}
	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}
	public Long getResponsableid() {
		return responsableid;
	}
	public void setResponsableid(Long responsableid) {
		this.responsableid = responsableid;
	}
	public Long getCurrentstatetypeid() {
		return currentstatetypeid;
	}
	public void setCurrentstatetypeid(Long currentstatetypeid) {
		this.currentstatetypeid = currentstatetypeid;
	}
	public Long getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(Long departmentid) {
		this.departmentid = departmentid;
	}
	public Long getDodeliveryid() {
		return dodeliveryid;
	}
	public void setDodeliveryid(Long dodeliveryid) {
		this.dodeliveryid = dodeliveryid;
	}
	public Long getCurrentsoastatetypeid() {
		return currentsoastatetypeid;
	}
	public void setCurrentsoastatetypeid(Long currentsoastatetypeid) {
		this.currentsoastatetypeid = currentsoastatetypeid;
	}
	public Date getCurrentsoastatetypedate() {
		return currentsoastatetypedate;
	}
	public void setCurrentsoastatetypedate(Date currentsoastatetypedate) {
		this.currentsoastatetypedate = currentsoastatetypedate;
	}
	public Long getSalestoreid() {
		return salestoreid;
	}
	public void setSalestoreid(Long salestoreid) {
		this.salestoreid = salestoreid;
	}

}
