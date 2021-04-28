package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import java.io.Serializable;

public class TaxDocumentReportDataDTO implements Serializable {

	private Long taxdocumentid;
	private String taxdocumentemitted;
	private Long taxdocumentnumber;
	private Boolean taxdocumentvalidated;
	private Long datingid;
	private Long datingnumber;
	private String datingdate;
	private Long orderid;
	private Long ordernumber;
	private Long vendorid;
	private String vendorcode;
	private String vendortradename;
	
	public Long getTaxdocumentid() {
		return taxdocumentid;
	}
	public void setTaxdocumentid(Long taxdocumentid) {
		this.taxdocumentid = taxdocumentid;
	}
	public String getTaxdocumentemitted() {
		return taxdocumentemitted;
	}
	public void setTaxdocumentemitted(String taxdocumentemitted) {
		this.taxdocumentemitted = taxdocumentemitted;
	}
	public Long getTaxdocumentnumber() {
		return taxdocumentnumber;
	}
	public void setTaxdocumentnumber(Long taxdocumentnumber) {
		this.taxdocumentnumber = taxdocumentnumber;
	}
	public Boolean getTaxdocumentvalidated() {
		return taxdocumentvalidated;
	}
	public void setTaxdocumentvalidated(Boolean taxdocumentvalidated) {
		this.taxdocumentvalidated = taxdocumentvalidated;
	}
	public Long getDatingid() {
		return datingid;
	}
	public void setDatingid(Long datingid) {
		this.datingid = datingid;
	}
	public Long getDatingnumber() {
		return datingnumber;
	}
	public void setDatingnumber(Long datingnumber) {
		this.datingnumber = datingnumber;
	}
	public String getDatingdate() {
		return datingdate;
	}
	public void setDatingdate(String datingdate) {
		this.datingdate = datingdate;
	}
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Long getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}
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
	public String getVendortradename() {
		return vendortradename;
	}
	public void setVendortradename(String vendortradename) {
		this.vendortradename = vendortradename;
	}
	
}
