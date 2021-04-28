package bbr.b2b.regional.logistic.kpi.data.classes;

import java.io.Serializable;

public class KPIvevPDReportDataDTO implements Serializable{
		
	private String fcm;
	private Long vendorid;
	private String vendorrut;
	private String vendorcode;
	private String vendorsocialreason;
	private Long totaldelivered;
	private Long totalreceiveddelayed;
	private Long totalcourierreceiveddelayed;
	private Long totalproviderdelayed;
	private Long totalcourierdelayed;
	private Long totaldelivering;
	private Long totallosts;
	private Long creditnotes;
	private Long inconsistencies;
	private Long customerresponsabilities;
	private Double compliancerate;
	private Double vendordefaultrate;
	private Double courierdefaultrate;
	private Long totalevaluated;
	private Long total;
	private String meta;
	
	public String getFcm() {
		return fcm;
	}
	public void setFcm(String fcm) {
		this.fcm = fcm;
	}
	public Long getVendorid() {
		return vendorid;
	}
	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}

	public String getVendorrut() {
		return vendorrut;
	}
	public void setVendorrut(String vendorrut) {
		this.vendorrut = vendorrut;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getVendorsocialreason() {
		return vendorsocialreason;
	}
	public void setVendorsocialreason(String vendorsocialreason) {
		this.vendorsocialreason = vendorsocialreason;
	}
	public Long getTotaldelivered() {
		return totaldelivered;
	}
	public void setTotaldelivered(Long totaldelivered) {
		this.totaldelivered = totaldelivered;
	}
	public Long getTotalreceiveddelayed() {
		return totalreceiveddelayed;
	}
	public void setTotalreceiveddelayed(Long totalreceiveddelayed) {
		this.totalreceiveddelayed = totalreceiveddelayed;
	}
	public Long getTotalcourierreceiveddelayed() {
		return totalcourierreceiveddelayed;
	}
	public void setTotalcourierreceiveddelayed(Long totalcourierreceiveddelayed) {
		this.totalcourierreceiveddelayed = totalcourierreceiveddelayed;
	}
	public Long getTotalproviderdelayed() {
		return totalproviderdelayed;
	}
	public void setTotalproviderdelayed(Long totalproviderdelayed) {
		this.totalproviderdelayed = totalproviderdelayed;
	}
	public Long getTotalcourierdelayed() {
		return totalcourierdelayed;
	}
	public void setTotalcourierdelayed(Long totalcourierdelayed) {
		this.totalcourierdelayed = totalcourierdelayed;
	}
	public Long getTotaldelivering() {
		return totaldelivering;
	}
	public void setTotaldelivering(Long totaldelivering) {
		this.totaldelivering = totaldelivering;
	}
	public Long getTotallosts() {
		return totallosts;
	}
	public void setTotallosts(Long totallosts) {
		this.totallosts = totallosts;
	}
	public Long getCreditnotes() {
		return creditnotes;
	}
	public void setCreditnotes(Long creditnotes) {
		this.creditnotes = creditnotes;
	}
	public Long getInconsistencies() {
		return inconsistencies;
	}
	public void setInconsistencies(Long inconsistencies) {
		this.inconsistencies = inconsistencies;
	}
	public Long getCustomerresponsabilities() {
		return customerresponsabilities;
	}
	public void setCustomerresponsabilities(Long customerresponsabilities) {
		this.customerresponsabilities = customerresponsabilities;
	}
	public Double getCompliancerate() {
		return compliancerate;
	}
	public void setCompliancerate(Double compliancerate) {
		this.compliancerate = compliancerate;
	}
	public Double getVendordefaultrate() {
		return vendordefaultrate;
	}
	public void setVendordefaultrate(Double vendordefaultrate) {
		this.vendordefaultrate = vendordefaultrate;
	}
	public Double getCourierdefaultrate() {
		return courierdefaultrate;
	}
	public void setCourierdefaultrate(Double courierdefaultrate) {
		this.courierdefaultrate = courierdefaultrate;
	}
	public Long getTotalevaluated() {
		return totalevaluated;
	}
	public void setTotalevaluated(Long totalevaluated) {
		this.totalevaluated = totalevaluated;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public String getMeta() {
		return meta;
	}
	public void setMeta(String meta) {
		this.meta = meta;
	}
}
