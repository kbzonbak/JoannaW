package bbr.b2b.regional.logistic.fillrate.report.classes;

import java.io.Serializable;

public class FillRateReportDataDTO implements Serializable{

	private Long vendorid;
	private String vendorname;
	private String vendorrut;
	private Long departmentid;
	private String departmentname;
	private double neededamount;
	private double receivedamount;
	private double neededunits;
	private double receivedunits;
	private Long fillrateid;
	private double amountfillrate;
	private double unitsfillrate;
	
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
	public String getVendorrut() {
		return vendorrut;
	}
	public void setVendorrut(String vendorrut) {
		this.vendorrut = vendorrut;
	}
	public Long getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(Long departmentid) {
		this.departmentid = departmentid;
	}
	public String getDepartmentname() {
		return departmentname;
	}
	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}
	public double getNeededamount() {
		return neededamount;
	}
	public void setNeededamount(double neededamount) {
		this.neededamount = neededamount;
	}
	public double getReceivedamount() {
		return receivedamount;
	}
	public void setReceivedamount(double receivedamount) {
		this.receivedamount = receivedamount;
	}
	public double getNeededunits() {
		return neededunits;
	}
	public void setNeededunits(double neededunits) {
		this.neededunits = neededunits;
	}
	public double getReceivedunits() {
		return receivedunits;
	}
	public void setReceivedunits(double receivedunits) {
		this.receivedunits = receivedunits;
	}
	public Long getFillrateid() {
		return fillrateid;
	}
	public void setFillrateid(Long fillrateid) {
		this.fillrateid = fillrateid;
	}
	public double getAmountfillrate() {
		return amountfillrate;
	}
	public void setAmountfillrate(double amountfillrate) {
		this.amountfillrate = amountfillrate;
	}
	public double getUnitsfillrate() {
		return unitsfillrate;
	}
	public void setUnitsfillrate(double unitsfillrate) {
		this.unitsfillrate = unitsfillrate;
	}
		
}
