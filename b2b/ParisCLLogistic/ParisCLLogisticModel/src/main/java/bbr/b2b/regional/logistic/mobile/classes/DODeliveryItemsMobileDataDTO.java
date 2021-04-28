package bbr.b2b.regional.logistic.mobile.classes;

import java.io.Serializable;

public class DODeliveryItemsMobileDataDTO implements Serializable {

	private String itemsku;
	private String itemdescription;
	private Double units;

	public String getItemsku() {
		return itemsku;
	}
	public void setItemsku(String itemsku) {
		this.itemsku = itemsku;
	}
	public String getItemdescription() {
		return itemdescription;
	}
	public void setItemdescription(String itemdescription) {
		this.itemdescription = itemdescription;
	}
	public Double getUnits() {
		return units;
	}
	public void setUnits(Double units) {
		this.units = units;
	}
}
