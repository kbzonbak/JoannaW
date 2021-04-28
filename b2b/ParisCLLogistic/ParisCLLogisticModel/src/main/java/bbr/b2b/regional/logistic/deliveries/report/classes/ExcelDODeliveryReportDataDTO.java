package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;


public class ExcelDODeliveryReportDataDTO implements Serializable {
	
	private Long dlid;
	private Long deliverystatetypeid;
	private Long itemid;
	private Long docid;
	private String commiteddatestr;
	private Integer rownumber;
	
	private Long dlnumber;
	private String deliverystatetype;
	private LocalDateTime commiteddate;
	private Long docnumber;
	private String requestnumber;
	private String currentstcomment;
	private String clientname;
	private String clientrut;
	private String clientaddress;
	private String clientcommune;
	private String clientcity;
	private String clientphone;	
	private String itemsku;
	private Double availableunits;
	private Double receivedunits;
	
	public Long getDlid() {
		return dlid;
	}
	public void setDlid(Long dlid) {
		this.dlid = dlid;
	}
	public Long getDeliverystatetypeid() {
		return deliverystatetypeid;
	}
	public void setDeliverystatetypeid(Long deliverystatetypeid) {
		this.deliverystatetypeid = deliverystatetypeid;
	}
	public Long getItemid() {
		return itemid;
	}
	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}
	public Long getDocid() {
		return docid;
	}
	public void setDocid(Long docid) {
		this.docid = docid;
	}
	public String getCommiteddatestr() {
		return commiteddatestr;
	}
	public void setCommiteddatestr(String commiteddatestr) {
		this.commiteddatestr = commiteddatestr;
	}
	public Long getDlnumber() {
		return dlnumber;
	}
	public void setDlnumber(Long dlnumber) {
		this.dlnumber = dlnumber;
	}
	public String getDeliverystatetype() {
		return deliverystatetype;
	}
	public void setDeliverystatetype(String deliverystatetype) {
		this.deliverystatetype = deliverystatetype;
	}
	public LocalDateTime getCommiteddate() {
		return commiteddate;
	}
	public void setCommiteddate(LocalDateTime commiteddate) {
		this.commiteddate = commiteddate;
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
	public String getCurrentstcomment() {
		return currentstcomment;
	}
	public void setCurrentstcomment(String currentstcomment) {
		this.currentstcomment = currentstcomment;
	}
	public String getClientname() {
		return clientname;
	}
	public void setClientname(String clientname) {
		this.clientname = clientname;
	}
	public String getClientrut() {
		return clientrut;
	}
	public void setClientrut(String clientrut) {
		this.clientrut = clientrut;
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
	public String getClientphone() {
		return clientphone;
	}
	public void setClientphone(String clientphone) {
		this.clientphone = clientphone;
	}
	public String getItemsku() {
		return itemsku;
	}
	public void setItemsku(String itemsku) {
		this.itemsku = itemsku;
	}
	public Double getAvailableunits() {
		return availableunits;
	}
	public void setAvailableunits(Double availableunits) {
		this.availableunits = availableunits;
	}
	public Double getReceivedunits() {
		return receivedunits;
	}
	public void setReceivedunits(Double receivedunits) {
		this.receivedunits = receivedunits;
	}
	public Integer getRownumber() {
		return rownumber;
	}
	public void setRownumber(Integer rownumber) {
		this.rownumber = rownumber;
	}	
}
