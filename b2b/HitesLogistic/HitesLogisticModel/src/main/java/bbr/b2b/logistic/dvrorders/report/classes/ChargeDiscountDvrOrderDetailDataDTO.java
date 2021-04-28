package bbr.b2b.logistic.dvrorders.report.classes;

import java.io.Serializable;

public class ChargeDiscountDvrOrderDetailDataDTO implements Serializable {

	private Boolean charge;
	private String description;
	private Double value;

	public Boolean getCharge() {
		return charge;
	}

	public void setCharge(Boolean charge) {
		this.charge = charge;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
}
