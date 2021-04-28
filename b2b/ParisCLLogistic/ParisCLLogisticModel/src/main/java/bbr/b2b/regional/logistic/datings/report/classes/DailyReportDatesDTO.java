package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;

public class DailyReportDatesDTO implements Serializable{
	private String datingdate;
	private String number;
	private String rut;
	private String buynumber;
	private String code;
	private String internalcode;
	private double availableunits;
	private double amount;
	private String name;
	private String batchname;
	

	public String getDatingdate() {
		return datingdate;
	}
	public void setDatingdate(String datingdate) {
		this.datingdate = datingdate;
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getRut() {
		return rut;
	}
	public void setRut(String rut) {
		this.rut = rut;
	}
	public String getBuynumber() {
		return buynumber;
	}
	public void setBuynumber(String buynumber) {
		this.buynumber = buynumber;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getInternalcode() {
		return internalcode;
	}
	public void setInternalcode(String internalcode) {
		this.internalcode = internalcode;
	}
	public double getAvailableunits() {
		return availableunits;
	}
	public void setAvailableunits(double availableunits) {
		this.availableunits = availableunits;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBatchname() {
		return batchname;
	}
	public void setBatchname(String batchname) {
		this.batchname = batchname;
	}
	

}
