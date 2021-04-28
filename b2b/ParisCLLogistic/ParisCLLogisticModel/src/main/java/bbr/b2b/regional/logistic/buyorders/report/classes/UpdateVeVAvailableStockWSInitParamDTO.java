package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class UpdateVeVAvailableStockWSInitParamDTO implements Serializable {

	private String vendorrut;
	private VeVAvailableStockWSInitParamDTO[] details;
	
	public String getVendorrut() {
		return vendorrut;
	}
	public void setVendorrut(String vendorrut) {
		this.vendorrut = vendorrut;
	}
	public VeVAvailableStockWSInitParamDTO[] getDetails() {
		return details;
	}
	public void setDetails(VeVAvailableStockWSInitParamDTO[] details) {
		this.details = details;
	}
	
}
