package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class ProposedPackingListTotalDataDTO implements Serializable {
	
	private Integer totalreg;
	private Double units;
	
	public Integer getTotalreg() {
		return totalreg;
	}
	public void setTotalreg(Integer totalreg) {
		this.totalreg = totalreg;
	}
	public Double getUnits() {
		return units;
	}
	public void setUnits(Double units) {
		this.units = units;
	}		
}
