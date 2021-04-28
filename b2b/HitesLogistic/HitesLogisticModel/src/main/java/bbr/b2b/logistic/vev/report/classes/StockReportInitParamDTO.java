package bbr.b2b.logistic.vev.report.classes;

import java.io.Serializable;

public class StockReportInitParamDTO implements Serializable {

	private String vendorrut;
		
	public String getVendorrut() {
		return vendorrut;
	}
	public void setVendorrut(String vendorrut) {
		this.vendorrut = vendorrut;
	}
}
