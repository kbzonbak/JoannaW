package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class DODeliveryStateTypeDTO implements Serializable {

	private Long id;
	private String code;
	private String name;
	private Boolean closed;
	private Boolean showable;
	private Long receptiontypeid;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public void setReceptiontypeid(Long receptiontypeid){ 
		this.receptiontypeid = receptiontypeid;
	}
}
