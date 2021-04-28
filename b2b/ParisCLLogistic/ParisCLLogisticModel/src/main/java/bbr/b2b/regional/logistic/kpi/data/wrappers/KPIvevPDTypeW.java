package bbr.b2b.regional.logistic.kpi.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.kpi.data.interfaces.IKPIvevPDType;

public class KPIvevPDTypeW extends ElementDTO implements IKPIvevPDType{

	private String code;
	private String description;	
	private Boolean fine;
	
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