package bbr.b2b.regional.logistic.taxdocuments.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.taxdocuments.data.interfaces.IDOTaxDocumentStateType;

public class DOTaxDocumentStateTypeW extends ElementDTO implements IDOTaxDocumentStateType {

	private String code;
	private String name;
	private String action;
	private Boolean closed;
	private Boolean showable;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Boolean getClosed() {
		return closed;
	}
	public void setClosed(Boolean closed) {
		this.closed = closed;
	}
	public Boolean getShowable() {
		return showable;
	}
	public void setShowable(Boolean showable) {
		this.showable = showable;
	}
		
}