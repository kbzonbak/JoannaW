package bbr.b2b.regional.logistic.deliveries.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IDODeliveryStateType;

public class DODeliveryStateTypeW extends ElementDTO implements IDODeliveryStateType {

	private String code;
	private String name;
	private Boolean closed;
	private Boolean showable;
	private Boolean modifiable;
	private Long receptiontypeid;
	private String codews;

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
	public Boolean getModifiable() {
		return modifiable;
	}
	public Long getReceptiontypeid(){ 
		return this.receptiontypeid;
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
	public void setModifiable(Boolean modifiable) {
		this.modifiable = modifiable;
	}
	public void setReceptiontypeid(Long receptiontypeid){ 
		this.receptiontypeid = receptiontypeid;
	}
	public String getCodews() {
		return codews;
	}
	public void setCodews(String codews) {
		this.codews = codews;
	}
}
