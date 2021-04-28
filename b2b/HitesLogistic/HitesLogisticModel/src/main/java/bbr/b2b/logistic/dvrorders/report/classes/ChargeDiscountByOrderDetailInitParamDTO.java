package bbr.b2b.logistic.dvrorders.report.classes;

import java.io.Serializable;

public class ChargeDiscountByOrderDetailInitParamDTO implements Serializable {

	private Long dvrorderid;
	private Long itemid;
	private Integer position;

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
