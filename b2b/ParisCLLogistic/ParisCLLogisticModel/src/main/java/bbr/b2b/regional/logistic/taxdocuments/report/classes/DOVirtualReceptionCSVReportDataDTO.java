package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DOVirtualReceptionCSVReportDataDTO implements Serializable{

	private Long dodeliverynumber;
	private String dodeliverycurrentst;
	private LocalDateTime dodeliverycurrentstdate;
	private Long donumber;
	private String dorequestnumber;
	private String comment;
	private String doclientname;
	private String doclientrut;
	private String doclientaddress;
	private String doclientcommune;
	private String doclientcity;
	private String doclientphone;
	private String iteminternalcode;
	private Double itemavailableunits;
	private Double itemreceivedunits;
	private Long dotaxdocumentnumber;
	private Boolean isvendorcourier;
	
	public Long getDodeliverynumber() {
		return dodeliverynumber;
	}
	public void setDodeliverynumber(Long dodeliverynumber) {
		this.dodeliverynumber = dodeliverynumber;
	}
	public String getDodeliverycurrentst() {
		return dodeliverycurrentst;
	}
	public void setDodeliverycurrentst(String dodeliverycurrentst) {
		this.dodeliverycurrentst = dodeliverycurrentst;
	}
	public LocalDateTime getDodeliverycurrentstdate() {
		return dodeliverycurrentstdate;
	}
	public void setDodeliverycurrentstdate(LocalDateTime dodeliverycurrentstdate) {
		this.dodeliverycurrentstdate = dodeliverycurrentstdate;
	}
	public Long getDonumber() {
		return donumber;
	}
	public void setDonumber(Long donumber) {
		this.donumber = donumber;
	}
	public String getDorequestnumber() {
		return dorequestnumber;
	}
	public void setDorequestnumber(String dorequestnumber) {
		this.dorequestnumber = dorequestnumber;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getDoclientname() {
		return doclientname;
	}
	public void setDoclientname(String doclientname) {
		this.doclientname = doclientname;
	}
	public String getDoclientrut() {
		return doclientrut;
	}
	public void setDoclientrut(String doclientrut) {
		this.doclientrut = doclientrut;
	}
	public String getDoclientaddress() {
		return doclientaddress;
	}
	public void setDoclientaddress(String doclientaddress) {
		this.doclientaddress = doclientaddress;
	}
	public String getDoclientcommune() {
		return doclientcommune;
	}
	public void setDoclientcommune(String doclientcommune) {
		this.doclientcommune = doclientcommune;
	}
	public String getDoclientcity() {
		return doclientcity;
	}
	public void setDoclientcity(String doclientcity) {
		this.doclientcity = doclientcity;
	}
	public String getDoclientphone() {
		return doclientphone;
	}
	public void setDoclientphone(String doclientphone) {
		this.doclientphone = doclientphone;
	}
	public String getIteminternalcode() {
		return iteminternalcode;
	}
	public void setIteminternalcode(String iteminternalcode) {
		this.iteminternalcode = iteminternalcode;
	}
	public Double getItemavailableunits() {
		return itemavailableunits;
	}
	public void setItemavailableunits(Double itemavailableunits) {
		this.itemavailableunits = itemavailableunits;
	}
	public Double getItemreceivedunits() {
		return itemreceivedunits;
	}
	public void setItemreceivedunits(Double itemreceivedunits) {
		this.itemreceivedunits = itemreceivedunits;
	}
	public Long getDotaxdocumentnumber() {
		return dotaxdocumentnumber;
	}
	public void setDotaxdocumentnumber(Long dotaxdocumentnumber) {
		this.dotaxdocumentnumber = dotaxdocumentnumber;
	}
	public Boolean getIsvendorcourier() {
		return isvendorcourier;
	}
	public void setIsvendorcourier(Boolean isvendorcourier) {
		this.isvendorcourier = isvendorcourier;
	}
	
	
}
