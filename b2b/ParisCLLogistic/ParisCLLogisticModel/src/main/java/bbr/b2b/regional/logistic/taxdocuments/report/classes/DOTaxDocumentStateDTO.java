package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import java.io.Serializable;

public class DOTaxDocumentStateDTO implements Serializable {
	
	private Long vendorid;
	private String vendorname;
	private Long directorderid;
	private Long directordernumber;
	private Long dotaxdocumentid;
	private Long dotaxdocumentnumber;
	private String dotaxdocumentstatewhen;
	private Long dotaxdocumentstatetypeid;
	private String dotaxdocumentstatetypename;
	private String dotaxdocumentstateusername;
	private String dotaxdocumentstateusertype;
	
	public Long getVendorid() {
		return vendorid;
	}
	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
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
	public String getDotaxdocumentstatewhen() {
		return dotaxdocumentstatewhen;
	}
	public void setDotaxdocumentstatewhen(String dotaxdocumentstatewhen) {
		this.dotaxdocumentstatewhen = dotaxdocumentstatewhen;
	}
	public Long getDotaxdocumentstatetypeid() {
		return dotaxdocumentstatetypeid;
	}
	public void setDotaxdocumentstatetypeid(Long dotaxdocumentstatetypeid) {
		this.dotaxdocumentstatetypeid = dotaxdocumentstatetypeid;
	}
	public String getDotaxdocumentstatetypename() {
		return dotaxdocumentstatetypename;
	}
	public void setDotaxdocumentstatetypename(String dotaxdocumentstatetypename) {
		this.dotaxdocumentstatetypename = dotaxdocumentstatetypename;
	}
	public String getDotaxdocumentstateusername() {
		return dotaxdocumentstateusername;
	}
	public void setDotaxdocumentstateusername(String dotaxdocumentstateusername) {
		this.dotaxdocumentstateusername = dotaxdocumentstateusername;
	}
	public String getDotaxdocumentstateusertype() {
		return dotaxdocumentstateusertype;
	}
	public void setDotaxdocumentstateusertype(String dotaxdocumentstateusertype) {
		this.dotaxdocumentstateusertype = dotaxdocumentstateusertype;
	}
		
}
