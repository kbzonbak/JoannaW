package bbr.b2b.regional.logistic.kpi.data.classes;

import java.io.Serializable;

public class KPIResetInitParamDTO implements Serializable{

	private String vendorcode;
	private String since;
	private String until;
	private boolean courier;
	
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getSince() {
		return since;
	}
	public void setSince(String since) {
		this.since = since;
	}
	public String getUntil() {
		return until;
	}
	public void setUntil(String until) {
		this.until = until;
	}
	public boolean getCourier() {
		return courier;
	}
	public void setCourier(boolean courier) {
		this.courier = courier;
	}
	
}
