package bbr.b2b.regional.logistic.deliveries.entities;

import bbr.b2b.regional.logistic.deliveries.entities.DOReceptionType;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IDODeliveryStateType;

public class DODeliveryStateType implements IDODeliveryStateType {

	private Long id;
	private String code;
	private String codews;
	private String name;
	private Boolean closed;
	private Boolean showable;
	private Boolean modifiable;
	private DOReceptionType receptiontype;

	public Long getId(){ 
		return this.id;
	}
	public String getCode(){ 
		return this.code;
	}
	public String getName(){ 
		return this.name;
	}
	public Boolean getClosed(){ 
		return this.closed;
	}
	public Boolean getShowable(){ 
		return this.showable;
	}
	public DOReceptionType getReceptiontype(){ 
		return this.receptiontype;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setName(String name){ 
		this.name = name;
	}
	public void setClosed(Boolean closed){ 
		this.closed = closed;
	}
	public void setShowable(Boolean showable){ 
		this.showable = showable;
	}
	public void setReceptiontype(DOReceptionType receptiontype){ 
		this.receptiontype = receptiontype;
	}
	public Boolean getModifiable() {
		return modifiable;
	}
	public void setModifiable(Boolean modifiable) {
		this.modifiable = modifiable;
	}
	public String getCodews() {
		return codews;
	}
	public void setCodews(String codews) {
		this.codews = codews;
	}
}
