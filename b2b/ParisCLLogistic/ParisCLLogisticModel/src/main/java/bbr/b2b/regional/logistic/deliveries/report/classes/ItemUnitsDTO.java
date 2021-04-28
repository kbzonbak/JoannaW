package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class ItemUnitsDTO implements Serializable {

	private Long itemid;
	private Double units;
		
	public Long getItemid() {
		return itemid;
	}
	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}
	public Double getUnits() {
		return units;
	}
	public void setUnits(Double units) {
		this.units = units;
	}	
}
