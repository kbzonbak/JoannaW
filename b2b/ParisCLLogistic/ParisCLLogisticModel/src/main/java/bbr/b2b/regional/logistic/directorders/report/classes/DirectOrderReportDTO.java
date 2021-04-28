package bbr.b2b.regional.logistic.directorders.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DirectOrderReportDTO implements Serializable{
	
	private Long id;
	private Long number;
	private String requestnumber;
	private boolean active;
	private String clientaddress;
	private String clientcity;
	private String clientcommune;
	private String clientname;
	private String clientphone;
	private String clientrut;
	private String comment;
	private String currentstatetypecomment;
	private String currentstatetypewho;
	private String deliverycurrentstcomment;
	private String deliverycurrentstwho;
	private String paymenttype;
	private String season;	
	private LocalDateTime currentdeliverydate;
	private LocalDateTime currentstatetypedate;
	private LocalDateTime deliverycommiteddate;	
	private LocalDateTime deliverycurrentstdate;
	private LocalDateTime deliveryoriginaldate;
	private LocalDateTime emitted;
	private LocalDateTime originaldeliverydate;
	private LocalDateTime paymentdate;	
	private Double pendingunits;
	private Double receivedunits;
	private Double needunits;
	private Double todeliveryunits;
	private Double totalneed;
	private Double totalpending;
	private Double totalreceived;
	private Double totaltodelivery;	
	private Long deliverycurrentstatetypeid;
	private Long currentstatetypeid;
	private Long currentdeliveryid;
	private Long responsableid;
	private Long vendorid;
	private Long clientid;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public String getRequestnumber() {
		return requestnumber;
	}
	public void setRequestnumber(String requestnumber) {
		this.requestnumber = requestnumber;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
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
	public String getClientname() {
		return clientname;
	}
	public void setClientname(String clientname) {
		this.clientname = clientname;
	}
	public String getClientphone() {
		return clientphone;
	}
	public void setClientphone(String clientphone) {
		this.clientphone = clientphone;
	}
	public String getClientrut() {
		return clientrut;
	}
	public void setClientrut(String clientrut) {
		this.clientrut = clientrut;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
	public String getDeliverycurrentstcomment() {
		return deliverycurrentstcomment;
	}
	public void setDeliverycurrentstcomment(String deliverycurrentstcomment) {
		this.deliverycurrentstcomment = deliverycurrentstcomment;
	}
	public String getDeliverycurrentstwho() {
		return deliverycurrentstwho;
	}
	public void setDeliverycurrentstwho(String deliverycurrentstwho) {
		this.deliverycurrentstwho = deliverycurrentstwho;
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
	public LocalDateTime getCurrentdeliverydate() {
		return currentdeliverydate;
	}
	public void setCurrentdeliverydate(LocalDateTime currentdeliverydate) {
		this.currentdeliverydate = currentdeliverydate;
	}
	public LocalDateTime getCurrentstatetypedate() {
		return currentstatetypedate;
	}
	public void setCurrentstatetypedate(LocalDateTime currentstatetypedate) {
		this.currentstatetypedate = currentstatetypedate;
	}
	public LocalDateTime getDeliverycommiteddate() {
		return deliverycommiteddate;
	}
	public void setDeliverycommiteddate(LocalDateTime deliverycommiteddate) {
		this.deliverycommiteddate = deliverycommiteddate;
	}
	public LocalDateTime getDeliverycurrentstdate() {
		return deliverycurrentstdate;
	}
	public void setDeliverycurrentstdate(LocalDateTime deliverycurrentstdate) {
		this.deliverycurrentstdate = deliverycurrentstdate;
	}
	public LocalDateTime getDeliveryoriginaldate() {
		return deliveryoriginaldate;
	}
	public void setDeliveryoriginaldate(LocalDateTime deliveryoriginaldate) {
		this.deliveryoriginaldate = deliveryoriginaldate;
	}
	public LocalDateTime getEmitted() {
		return emitted;
	}
	public void setEmitted(LocalDateTime emitted) {
		this.emitted = emitted;
	}
	public LocalDateTime getOriginaldeliverydate() {
		return originaldeliverydate;
	}
	public void setOriginaldeliverydate(LocalDateTime originaldeliverydate) {
		this.originaldeliverydate = originaldeliverydate;
	}
	public LocalDateTime getPaymentdate() {
		return paymentdate;
	}
	public void setPaymentdate(LocalDateTime paymentdate) {
		this.paymentdate = paymentdate;
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
	public Double getNeedunits() {
		return needunits;
	}
	public void setNeedunits(Double needunits) {
		this.needunits = needunits;
	}
	public Double getTodeliveryunits() {
		return todeliveryunits;
	}
	public void setTodeliveryunits(Double todeliveryunits) {
		this.todeliveryunits = todeliveryunits;
	}
	public Double getTotalneed() {
		return totalneed;
	}
	public void setTotalneed(Double totalneed) {
		this.totalneed = totalneed;
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
	public Long getDeliverycurrentstatetypeid() {
		return deliverycurrentstatetypeid;
	}
	public void setDeliverycurrentstatetypeid(Long deliverycurrentstatetypeid) {
		this.deliverycurrentstatetypeid = deliverycurrentstatetypeid;
	}
	public Long getCurrentstatetypeid() {
		return currentstatetypeid;
	}
	public void setCurrentstatetypeid(Long currentstatetypeid) {
		this.currentstatetypeid = currentstatetypeid;
	}
	public Long getCurrentdeliveryid() {
		return currentdeliveryid;
	}
	public void setCurrentdeliveryid(Long currentdeliveryid) {
		this.currentdeliveryid = currentdeliveryid;
	}
	public Long getResponsableid() {
		return responsableid;
	}
	public void setResponsableid(Long responsableid) {
		this.responsableid = responsableid;
	}
	public Long getVendorid() {
		return vendorid;
	}
	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}
	public Long getClientid() {
		return clientid;
	}
	public void setClientid(Long clientid) {
		this.clientid = clientid;
	}	
}
