package bbr.b2b.regional.logistic.kpi.data.classes;

import java.io.Serializable;

public class KPIvevCDReportDataDTO implements Serializable{
		
	private String fpi;
	private Long vendorid;
	private String vendorrut;
	private String vendorcode;
	private String vendorsocialreason;
	private Long totalreceivedconformed;
	private Long totalreceiveddelayed;
	private Long totalproviderdelayed;
	private Long creditnotes;
	private Double compliancerate;
	private Double defaultrate;
	private Long totalevaluated;
	private Long total;
	private String meta;
	
	public String getFpi() {
		return fpi;
	}
	public void setFpi(String fpi) {
		this.fpi = fpi;
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
	public Long getTotalreceivedconformed() {
		return totalreceivedconformed;
	}
	public void setTotalreceivedconformed(Long totalreceivedconformed) {
		this.totalreceivedconformed = totalreceivedconformed;
	}
	public Long getTotalreceiveddelayed() {
		return totalreceiveddelayed;
	}
	public void setTotalreceiveddelayed(Long totalreceiveddelayed) {
		this.totalreceiveddelayed = totalreceiveddelayed;
	}
	public Long getTotalproviderdelayed() {
		return totalproviderdelayed;
	}
	public void setTotalproviderdelayed(Long totalproviderdelayed) {
		this.totalproviderdelayed = totalproviderdelayed;
	}
	public Long getCreditnotes() {
		return creditnotes;
	}
	public void setCreditnotes(Long creditnotes) {
		this.creditnotes = creditnotes;
	}
	public Double getCompliancerate() {
		return compliancerate;
	}
	public void setCompliancerate(Double compliancerate) {
		this.compliancerate = compliancerate;
	}
	public Double getDefaultrate() {
		return defaultrate;
	}
	public void setDefaultrate(Double defaultrate) {
		this.defaultrate = defaultrate;
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
