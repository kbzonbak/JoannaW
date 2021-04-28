package bbr.b2b.logistic.dvrorders.report.classes;

import java.io.Serializable;

public class ChargeDiscountDataByDvrOrderDetailDTO implements Serializable {

	private Long id;
	private Boolean charge;
	private String description;
	private Integer visualorder;;
	private Boolean percentage;
	private Double value;
	private Long dvrorderid;
	private Long itemid;
	private Integer position;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Long getDvrorderid() {
		return dvrorderid;
	}

	public void setDvrorderid(Long dvrorderid) {
		this.dvrorderid = dvrorderid;
	}

	public Long getItemid() {
		return itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

}
