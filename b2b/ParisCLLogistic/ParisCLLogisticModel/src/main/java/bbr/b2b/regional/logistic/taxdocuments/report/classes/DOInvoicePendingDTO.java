package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DOInvoicePendingDTO implements Serializable {

	private Long vendorid;
	private String vendorcode;
	private String vendorname;
	private Long directorderid;
	private Long directordernumber;
	private String directorderrequestnumber;
	private LocalDateTime directorderemitted;
	private String directorderemittedstr;
	private LocalDateTime directorderstatedate;
	private String directorderstatedatestr;
	private Double directordertotalreceived;
	private Long dodeliveryid;
	private Long dodeliverynumber;
	private LocalDateTime dodeliverystatedate;
	private String dodeliverystatedatestr;
	private Long dotaxdocumentid;
	private Long dotaxdocumentnumber;
	private LocalDateTime dotaxdocumentemitted;
	private String dotaxdocumentemittedstr;	
	private Long dotaxdocumentstateid;
	private String dotaxdocumentstatecode;
	private String dotaxdocumentstatename;
	private LocalDateTime dotaxdocumentstatedate;
	private String dotaxdocumentstatedatestr;
	private String dotaxdocumentstateusername;
	private String dotaxdocumentstateaction;
	
	public Long getVendorid() {
		return vendorid;
	}
	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getVendorname() {
		return vendorname;
	}
	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}
	public Long getDirectorderid() {
		return directorderid;
	}
	public void setDirectorderid(Long directorderid) {
		this.directorderid = directorderid;
	}
	public Long getDirectordernumber() {
		return directordernumber;
	}
	public void setDirectordernumber(Long directordernumber) {
		this.directordernumber = directordernumber;
	}
	public String getDirectorderrequestnumber() {
		return directorderrequestnumber;
	}
	public void setDirectorderrequestnumber(String directorderrequestnumber) {
		this.directorderrequestnumber = directorderrequestnumber;
	}
	public LocalDateTime getDirectorderemitted() {
		return directorderemitted;
	}
	public void setDirectorderemitted(LocalDateTime directorderemitted) {
		this.directorderemitted = directorderemitted;
	}
	public String getDirectorderemittedstr() {
		return directorderemittedstr;
	}
	public void setDirectorderemittedstr(String directorderemittedstr) {
		this.directorderemittedstr = directorderemittedstr;
	}
	public LocalDateTime getDirectorderstatedate() {
		return directorderstatedate;
	}
	public void setDirectorderstatedate(LocalDateTime directorderstatedate) {
		this.directorderstatedate = directorderstatedate;
	}
	public String getDirectorderstatedatestr() {
		return directorderstatedatestr;
	}
	public void setDirectorderstatedatestr(String directorderstatedatestr) {
		this.directorderstatedatestr = directorderstatedatestr;
	}
	public Double getDirectordertotalreceived() {
		return directordertotalreceived;
	}
	public void setDirectordertotalreceived(Double directordertotalreceived) {
		this.directordertotalreceived = directordertotalreceived;
	}
	public Long getDodeliveryid() {
		return dodeliveryid;
	}
	public void setDodeliveryid(Long dodeliveryid) {
		this.dodeliveryid = dodeliveryid;
	}
	public Long getDodeliverynumber() {
		return dodeliverynumber;
	}
	public void setDodeliverynumber(Long dodeliverynumber) {
		this.dodeliverynumber = dodeliverynumber;
	}
	public LocalDateTime getDodeliverystatedate() {
		return dodeliverystatedate;
	}
	public void setDodeliverystatedate(LocalDateTime dodeliverystatedate) {
		this.dodeliverystatedate = dodeliverystatedate;
	}
	public String getDodeliverystatedatestr() {
		return dodeliverystatedatestr;
	}
	public void setDodeliverystatedatestr(String dodeliverystatedatestr) {
		this.dodeliverystatedatestr = dodeliverystatedatestr;
	}
	public Long getDotaxdocumentid() {
		return dotaxdocumentid;
	}
	public void setDotaxdocumentid(Long dotaxdocumentid) {
		this.dotaxdocumentid = dotaxdocumentid;
	}
	public Long getDotaxdocumentnumber() {
		return dotaxdocumentnumber;
	}
	public void setDotaxdocumentnumber(Long dotaxdocumentnumber) {
		this.dotaxdocumentnumber = dotaxdocumentnumber;
	}
	public LocalDateTime getDotaxdocumentemitted() {
		return dotaxdocumentemitted;
	}
	public void setDotaxdocumentemitted(LocalDateTime dotaxdocumentemitted) {
		this.dotaxdocumentemitted = dotaxdocumentemitted;
	}
	public String getDotaxdocumentemittedstr() {
		return dotaxdocumentemittedstr;
	}
	public void setDotaxdocumentemittedstr(String dotaxdocumentemittedstr) {
		this.dotaxdocumentemittedstr = dotaxdocumentemittedstr;
	}
	public Long getDotaxdocumentstateid() {
		return dotaxdocumentstateid;
	}
	public void setDotaxdocumentstateid(Long dotaxdocumentstateid) {
		this.dotaxdocumentstateid = dotaxdocumentstateid;
	}
	public String getDotaxdocumentstatecode() {
		return dotaxdocumentstatecode;
	}
	public void setDotaxdocumentstatecode(String dotaxdocumentstatecode) {
		this.dotaxdocumentstatecode = dotaxdocumentstatecode;
	}
	public String getDotaxdocumentstatename() {
		return dotaxdocumentstatename;
	}
	public void setDotaxdocumentstatename(String dotaxdocumentstatename) {
		this.dotaxdocumentstatename = dotaxdocumentstatename;
	}
	public LocalDateTime getDotaxdocumentstatedate() {
		return dotaxdocumentstatedate;
	}
	public void setDotaxdocumentstatedate(LocalDateTime dotaxdocumentstatedate) {
		this.dotaxdocumentstatedate = dotaxdocumentstatedate;
	}
	public String getDotaxdocumentstatedatestr() {
		return dotaxdocumentstatedatestr;
	}
	public void setDotaxdocumentstatedatestr(String dotaxdocumentstatedatestr) {
		this.dotaxdocumentstatedatestr = dotaxdocumentstatedatestr;
	}
	public String getDotaxdocumentstateusername() {
		return dotaxdocumentstateusername;
	}
	public void setDotaxdocumentstateusername(String dotaxdocumentstateusername) {
		this.dotaxdocumentstateusername = dotaxdocumentstateusername;
	}
	public String getDotaxdocumentstateaction() {
		return dotaxdocumentstateaction;
	}
	public void setDotaxdocumentstateaction(String dotaxdocumentstateaction) {
		this.dotaxdocumentstateaction = dotaxdocumentstateaction;
	}
}
