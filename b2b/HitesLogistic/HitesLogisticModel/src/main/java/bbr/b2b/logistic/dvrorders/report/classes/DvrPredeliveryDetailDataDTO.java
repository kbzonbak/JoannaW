package bbr.b2b.logistic.dvrorders.report.classes;

import java.io.Serializable;

public class DvrPredeliveryDetailDataDTO implements Serializable {

	private String sku;
	private String department;
	private String color;
	private String itemdescription;
	private String destlocationcode;
	private String destlocationname;
	private Double totalunits;
	private Double todeliveryunits;
	private Double receivedunits;
	private Double pendingunits;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getItemdescription() {
		return itemdescription;
	}

	public void setItemdescription(String itemdescription) {
		this.itemdescription = itemdescription;
	}

	public String getDestlocationcode() {
		return destlocationcode;
	}

	public void setDestlocationcode(String destlocationcode) {
		this.destlocationcode = destlocationcode;
	}

	public String getDestlocationname() {
		return destlocationname;
	}

	public void setDestlocationname(String destlocationname) {
		this.destlocationname = destlocationname;
	}

	public Double getTotalunits() {
		return totalunits;
	}

	public void setTotalunits(Double totalunits) {
		this.totalunits = totalunits;
	}

	public Double getTodeliveryunits() {
		return todeliveryunits;
	}

	public void setTodeliveryunits(Double todeliveryunits) {
		this.todeliveryunits = todeliveryunits;
	}

	public Double getReceivedunits() {
		return receivedunits;
	}

	public void setReceivedunits(Double receivedunits) {
		this.receivedunits = receivedunits;
	}

	public Double getPendingunits() {
		return pendingunits;
	}

	public void setPendingunits(Double pendingunits) {
		this.pendingunits = pendingunits;
	}

}
