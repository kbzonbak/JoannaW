package bbr.b2b.regional.logistic.fillrate.report.classes;

import java.io.Serializable;

public class FillRateEvolutionReportDataDTO implements Serializable{

	private int year;
	private String month;
	private double neededamount;
	private double receivedamount;
	private double neededunits;
	private double receivedunits;
	private double amountfillrate;
	private double unitsfillrate;
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
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
