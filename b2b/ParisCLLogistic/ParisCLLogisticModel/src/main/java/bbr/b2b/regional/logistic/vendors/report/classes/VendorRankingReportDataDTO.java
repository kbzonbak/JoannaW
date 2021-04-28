package bbr.b2b.regional.logistic.vendors.report.classes;

import java.io.Serializable;

public class VendorRankingReportDataDTO implements Serializable{

	private Long vendorid;
	private String vendorrut;
	private String vendorname;
	private int approvedcount;
	private int nonassistancecount;
	private int rejectedcount;
	private Long rankingid;
	private double ranking;
	private String vendorclassification;
	
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
	public String getVendorname() {
		return vendorname;
	}
	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}
	public int getApprovedcount() {
		return approvedcount;
	}
	public void setApprovedcount(int approvedcount) {
		this.approvedcount = approvedcount;
	}
	public int getNonassistancecount() {
		return nonassistancecount;
	}
	public void setNonassistancecount(int nonassistancecount) {
		this.nonassistancecount = nonassistancecount;
	}
	public int getRejectedcount() {
		return rejectedcount;
	}
	public void setRejectedcount(int rejectedcount) {
		this.rejectedcount = rejectedcount;
	}
	public Long getRankingid() {
		return rankingid;
	}
	public void setRankingid(Long rankingid) {
		this.rankingid = rankingid;
	}
	public double getRanking() {
		return ranking;
	}
	public void setRanking(double ranking) {
		this.ranking = ranking;
	}
	public String getVendorclassification() {
		return vendorclassification;
	}
	public void setVendorclassification(String vendorclassification) {
		this.vendorclassification = vendorclassification;
	}
}
