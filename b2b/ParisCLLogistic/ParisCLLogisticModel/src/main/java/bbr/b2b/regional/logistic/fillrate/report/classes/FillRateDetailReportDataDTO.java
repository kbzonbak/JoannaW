package bbr.b2b.regional.logistic.fillrate.report.classes;

import java.io.Serializable;

public class FillRateDetailReportDataDTO implements Serializable{

	private String itemsku;
	private double neededunits;
	private double receivedunits;
	private double unitsfillrate;
	
	public String getItemsku() {
		return itemsku;
	}
	public void setItemsku(String itemsku) {
		this.itemsku = itemsku;
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
	public double getUnitsfillrate() {
		return unitsfillrate;
	}
	public void setUnitsfillrate(double unitsfillrate) {
		this.unitsfillrate = unitsfillrate;
	}
		
}
