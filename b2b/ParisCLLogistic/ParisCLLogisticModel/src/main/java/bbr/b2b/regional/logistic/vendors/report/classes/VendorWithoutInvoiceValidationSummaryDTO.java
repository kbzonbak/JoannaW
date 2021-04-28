package bbr.b2b.regional.logistic.vendors.report.classes;

import java.io.Serializable;

public class VendorWithoutInvoiceValidationSummaryDTO implements Serializable {

	private Integer totalreg;
	private String modifier;
	private String lastmodified;
		
	public Integer getTotalreg() {
		return totalreg;
	}
	public void setTotalreg(Integer totalreg) {
		this.totalreg = totalreg;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public String getLastmodified() {
		return lastmodified;
	}
	public void setLastmodified(String lastmodified) {
		this.lastmodified = lastmodified;
	}
}
