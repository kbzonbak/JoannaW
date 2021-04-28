package bbr.b2b.regional.logistic.kpi.entities;

import bbr.b2b.regional.logistic.kpi.data.interfaces.IKPIvevPDType;

public class KPIvevPDType implements IKPIvevPDType {

	private Long id;
	private String code;
	private String description;
	private Boolean fine;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getFine() {
		return fine;
	}
	public void setFine(Boolean fine) {
		this.fine = fine;
	}
}