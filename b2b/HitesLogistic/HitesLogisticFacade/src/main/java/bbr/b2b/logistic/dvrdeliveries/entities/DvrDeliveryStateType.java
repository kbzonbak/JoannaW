package bbr.b2b.logistic.dvrdeliveries.entities;

import bbr.b2b.logistic.dvrdeliveries.data.interfaces.IDvrDeliveryStateType;

public class DvrDeliveryStateType implements IDvrDeliveryStateType {

	private Long id;
	private String code;
	private String description;
	private Boolean valid;
	private Boolean visible;
	private String nextaction;

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
	public String getNextaction(){ 
		return this.nextaction;
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
	public void setNextaction(String nextaction){ 
		this.nextaction = nextaction;
	}
}
