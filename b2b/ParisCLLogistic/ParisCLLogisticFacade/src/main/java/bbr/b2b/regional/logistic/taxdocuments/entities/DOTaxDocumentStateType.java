package bbr.b2b.regional.logistic.taxdocuments.entities;

import bbr.b2b.regional.logistic.taxdocuments.data.interfaces.IDOTaxDocumentStateType;

public class DOTaxDocumentStateType implements IDOTaxDocumentStateType {

	private Long id;
	private String code;
	private String name;
	private String action;
	private Boolean closed;
	private Boolean showable;
	
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