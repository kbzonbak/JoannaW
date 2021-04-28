package bbr.b2b.logistic.dvrorders.report.classes;

import java.io.Serializable;

public class DiscountByOrderDataDTO implements Serializable {

	private Integer visualorder;
	private Boolean percentage;
	private String description;
	private Double value;

	public Integer getVisualorder() {
		return visualorder;
	}

	public void setVisualorder(Integer visualorder) {
		this.visualorder = visualorder;
	}

	public Boolean getPercentage() {
		return percentage;
	}

	public void setPercentage(Boolean percentage) {
		this.percentage = percentage;
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
