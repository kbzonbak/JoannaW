package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class DetailDiscountReportDataDTO implements Serializable{

	private String discounttype;
	private String description;
	private double discountpercentage;
	private double discountvalue;
	
	public String getDiscounttype() {
		return discounttype;
	}
	public void setDiscounttype(String discounttype) {
		this.discounttype = discounttype;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getDiscountpercentage() {
		return discountpercentage;
	}
	public void setDiscountpercentage(double discountpercentage) {
		this.discountpercentage = discountpercentage;
	}
	public double getDiscountvalue() {
		return discountvalue;
	}
	public void setDiscountvalue(double discountvalue) {
		this.discountvalue = discountvalue;
	}
	
}
