package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class CSVOrderVeVReportInitParamDTO implements Serializable {

	private String vendorcode;  //rut del proveedor
	private String locationcode;	
	private Long[] salestoreid;
	private Long orderstatetypeid;
	private String since;
	private String until;
	private int filtertype;
	

	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getLocationcode() {
		return locationcode;
	}
	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}
	public Long getOrderstatetypeid() {
		return orderstatetypeid;
	}
	public void setOrderstatetypeid(Long orderstatetypeid) {
		this.orderstatetypeid = orderstatetypeid;
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
	public int getFiltertype() {
		return filtertype;
	}
	public void setFiltertype(int filtertype) {
		this.filtertype = filtertype;
	}
	public Long[] getSalestoreid() {
		return salestoreid;
	}
	public void setSalestoreid(Long[] salestoreid) {
		this.salestoreid = salestoreid;
	}
	
}
