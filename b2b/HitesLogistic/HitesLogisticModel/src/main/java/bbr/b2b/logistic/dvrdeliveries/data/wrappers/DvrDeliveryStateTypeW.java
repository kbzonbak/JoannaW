package bbr.b2b.logistic.dvrdeliveries.data.wrappers;

import bbr.b2b.logistic.dvrdeliveries.data.interfaces.IDvrDeliveryStateType;
import bbr.b2b.common.adtclasses.classes.ElementDTO;

public class DvrDeliveryStateTypeW extends ElementDTO implements IDvrDeliveryStateType {

	private String code;
	private String description;
	private Boolean valid;
	private Boolean visible;
	private String nextaction;

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
