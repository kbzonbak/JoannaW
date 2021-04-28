package bbr.b2b.logistic.parameters.entities;

import java.time.LocalDateTime;

import bbr.b2b.logistic.parameters.data.interfaces.IParameter;

public class Parameter implements IParameter {

	private Long id;
	private String code;
	private String description;
	private String value;
	private String unit;
	private Boolean visible;
	private Boolean active;
	private LocalDateTime creationdate;
	private LocalDateTime modificationdate;

	public Long getId(){ 
		return this.id;
	}
	public String getCode(){ 
		return this.code;
	}
	public String getDescription(){ 
		return this.description;
	}
	public String getValue(){ 
		return this.value;
	}
	public String getUnit(){ 
		return this.unit;
	}
	public Boolean getVisible(){ 
		return this.visible;
	}
	public Boolean getActive(){ 
		return this.active;
	}
	public LocalDateTime getCreationdate(){ 
		return this.creationdate;
	}
	public LocalDateTime getModificationdate(){ 
		return this.modificationdate;
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
	public void setValue(String value){ 
		this.value = value;
	}
	public void setUnit(String unit){ 
		this.unit = unit;
	}
	public void setVisible(Boolean visible){ 
		this.visible = visible;
	}
	public void setActive(Boolean active){ 
		this.active = active;
	}
	public void setCreationdate(LocalDateTime creationdate){ 
		this.creationdate = creationdate;
	}
	public void setModificationdate(LocalDateTime modificationdate){ 
		this.modificationdate = modificationdate;
	}
}
