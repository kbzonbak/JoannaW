package bbr.b2b.logistic.dvrorders.entities;

import bbr.b2b.logistic.dvrorders.data.interfaces.IDvrOrderStateType;

public class DvrOrderStateType implements IDvrOrderStateType {

	private Long id;
	private String code;
	private String description;
	private Boolean valid;
	private Boolean visible;

	public Long getId(){ 
		return this.id;
	}
	public String getCode(){ 
		return this.code;
	}
	public String getDescription(){ 
		return this.description;
	}
	public Boolean getValid(){ 
		return this.valid;
	}
	public Boolean getVisible(){ 
		return this.visible;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setDescription(String description){ 
		this.description = description;
	}
	public void setValid(Boolean valid){ 
		this.valid = valid;
	}
	public void setVisible(Boolean visible){ 
		this.visible = visible;
	}
}
