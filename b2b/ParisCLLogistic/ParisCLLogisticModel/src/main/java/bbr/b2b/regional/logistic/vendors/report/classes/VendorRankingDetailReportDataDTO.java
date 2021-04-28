package bbr.b2b.regional.logistic.vendors.report.classes;

import java.io.Serializable;

public class VendorRankingDetailReportDataDTO implements Serializable{

	private Long datingnumber;
	private String evaluationtype;
	private Long ordernumber;
	private String datingdate;
	private String datingmonth;
	private double totalavailableunits;
	private double totalreceivedunits;
	private double deliveryscore;
	private double levelservicepercent;
	private double qualitypercent;
	
	public Long getDatingnumber() {
		return datingnumber;
	}
	public void setDatingnumber(Long datingnumber) {
		this.datingnumber = datingnumber;
	}
	public String getEvaluationtype() {
		return evaluationtype;
	}
	public void setEvaluationtype(String evaluationtype) {
		this.evaluationtype = evaluationtype;
	}
	public Long getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}
	public String getDatingdate() {
		return datingdate;
	}
	public void setDatingdate(String datingdate) {
		this.datingdate = datingdate;
	}
	public String getDatingmonth() {
		return datingmonth;
	}
	public void setDatingmonth(String datingmonth) {
		this.datingmonth = datingmonth;
	}
	public double getTotalavailableunits() {
		return totalavailableunits;
	}
	public void setTotalavailableunits(double totalavailableunits) {
		this.totalavailableunits = totalavailableunits;
	}
	public double getTotalreceivedunits() {
		return totalreceivedunits;
	}
	public void setTotalreceivedunits(double totalreceivedunits) {
		this.totalreceivedunits = totalreceivedunits;
	}
	public double getDeliveryscore() {
		return deliveryscore;
	}
	public void setDeliveryscore(double deliveryscore) {
		this.deliveryscore = deliveryscore;
	}
	public double getLevelservicepercent() {
		return levelservicepercent;
	}
	public void setLevelservicepercent(double levelservicepercent) {
		this.levelservicepercent = levelservicepercent;
	}
	public double getQualitypercent() {
		return qualitypercent;
	}
	public void setQualitypercent(double qualitypercent) {
		this.qualitypercent = qualitypercent;
	}
	
}
