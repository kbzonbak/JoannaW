package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;


public class VeVPDOrderReportDataDTO implements Serializable {
	
	private Long id;   
	private Long docnumber;   
	private String requestnumber;   
	private String dorderestatetypecode;  
	private String dorderestatetypename;  
	private String dodeliverystatatype;  
	private String currentstatetypedate;   
	private String emitted;   
	private String valid;  
	private String reprogrammingdate;   
	private String clientrut;   
	private String clientname;  
	private String clientcommune;  
	private String clientaddress;  
	private String clientcity;
	private String clientphone;  
	private String comment;  
	private Double needunits;  
	private Double totalneed;  					
	private Double todeliveryunits;   
	private Double receivedunits;   
	private Double pendingunits;   
	private Double totalreceived;   
	private Double totaltodelivery;   
	private Double totalpending;
	private String dispatcheremail;
	private String salestore;
	private String sendnumber;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDocnumber() {
		return docnumber;
	}
	public void setDocnumber(Long docnumber) {
		this.docnumber = docnumber;
	}
	public String getRequestnumber() {
		return requestnumber;
	}
	public void setRequestnumber(String requestnumber) {
		this.requestnumber = requestnumber;
	}
	public String getDorderestatetypecode() {
		return dorderestatetypecode;
	}
	public void setDorderestatetypecode(String dorderestatetypecode) {
		this.dorderestatetypecode = dorderestatetypecode;
	}
	public String getDorderestatetypename() {
		return dorderestatetypename;
	}
	public void setDorderestatetypename(String dorderestatetypename) {
		this.dorderestatetypename = dorderestatetypename;
	}
	public String getDodeliverystatatype() {
		return dodeliverystatatype;
	}
	public void setDodeliverystatatype(String dodeliverystatatype) {
		this.dodeliverystatatype = dodeliverystatatype;
	}
	public String getCurrentstatetypedate() {
		return currentstatetypedate;
	}
	public void setCurrentstatetypedate(String currentstatetypedate) {
		this.currentstatetypedate = currentstatetypedate;
	}
	public String getEmitted() {
		return emitted;
	}
	public void setEmitted(String emitted) {
		this.emitted = emitted;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public String getReprogrammingdate() {
		return reprogrammingdate;
	}
	public void setReprogrammingdate(String reprogrammingdate) {
		this.reprogrammingdate = reprogrammingdate;
	}
	public String getClientrut() {
		return clientrut;
	}
	public void setClientrut(String clientrut) {
		this.clientrut = clientrut;
	}
	public String getClientname() {
		return clientname;
	}
	public void setClientname(String clientname) {
		this.clientname = clientname;
	}
	public String getClientcommune() {
		return clientcommune;
	}
	public void setClientcommune(String clientcommune) {
		this.clientcommune = clientcommune;
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
	public Double getTodeliveryunits() {
		return todeliveryunits;
	}
	public void setTodeliveryunits(Double todeliveryunits) {
		this.todeliveryunits = todeliveryunits;
	}
	public Double getReceivedunits() {
		return receivedunits;
	}
	public void setReceivedunits(Double receivedunits) {
		this.receivedunits = receivedunits;
	}
	public Double getPendingunits() {
		return pendingunits;
	}
	public void setPendingunits(Double pendingunits) {
		this.pendingunits = pendingunits;
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
	public Double getTotalpending() {
		return totalpending;
	}
	public void setTotalpending(Double totalpending) {
		this.totalpending = totalpending;
	}
	public String getDispatcheremail() {
		return dispatcheremail;
	}
	public void setDispatcheremail(String dispatcheremail) {
		this.dispatcheremail = dispatcheremail;
	}
	public String getClientphone() {
		return clientphone;
	}
	public void setClientphone(String clientphone) {
		this.clientphone = clientphone;
	}
	public String getSalestore() {
		return salestore;
	}
	public void setSalestore(String salestore) {
		this.salestore = salestore;
	}
	public String getSendnumber() {
		return sendnumber;
	}
	public void setSendnumber(String sendnumber) {
		this.sendnumber = sendnumber;
	}
}
