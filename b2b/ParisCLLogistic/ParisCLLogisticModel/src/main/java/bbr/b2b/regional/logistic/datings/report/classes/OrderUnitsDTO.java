package bbr.b2b.regional.logistic.datings.report.classes;

import java.io.Serializable;

public class OrderUnitsDTO implements Serializable {

	private Long orderid;
	private Long ocnumber;
	private Double estimatedunits; // OrderDelivery
	private Double pendingunits; // Order (Sin Cita)
	private Double todeliveryunits; // Order (Con cita solicitada)
	private String expirationdate;
	private String validdate;
	private Long originalvendorid;
	private String originalvendorcode; 
	private String originalvendorname;
	
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Long getOcnumber() {
		return ocnumber;
	}
	public void setOcnumber(Long ocnumber) {
		this.ocnumber = ocnumber;
	}
	public Double getEstimatedunits() {
		return estimatedunits;
	}
	public void setEstimatedunits(Double estimatedunits) {
		this.estimatedunits = estimatedunits;
	}
	public Double getPendingunits() {
		return pendingunits;
	}
	public void setPendingunits(Double pendingunits) {
		this.pendingunits = pendingunits;
	}
	public Double getTodeliveryunits() {
		return todeliveryunits;
	}
	public void setTodeliveryunits(Double todeliveryunits) {
		this.todeliveryunits = todeliveryunits;
	}
	public String getExpirationdate() {
		return expirationdate;
	}
	public void setExpirationdate(String expirationdate) {
		this.expirationdate = expirationdate;
	}
	public String getValiddate() {
		return validdate;
	}
	public void setValiddate(String validdate) {
		this.validdate = validdate;
	}
	public Long getOriginalvendorid() {
		return originalvendorid;
	}
	public void setOriginalvendorid(Long originalvendorid) {
		this.originalvendorid = originalvendorid;
	}
	public String getOriginalvendorcode() {
		return originalvendorcode;
	}
	public void setOriginalvendorcode(String originalvendorcode) {
		this.originalvendorcode = originalvendorcode;
	}
	public String getOriginalvendorname() {
		return originalvendorname;
	}
	public void setOriginalvendorname(String originalvendorname) {
		this.originalvendorname = originalvendorname;
	}
	
}
