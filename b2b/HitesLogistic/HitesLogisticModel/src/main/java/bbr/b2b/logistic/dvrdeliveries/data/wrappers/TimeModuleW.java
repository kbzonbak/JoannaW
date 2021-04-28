package bbr.b2b.logistic.dvrdeliveries.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.dvrdeliveries.data.interfaces.ITimeModule;

public class TimeModuleW extends ElementDTO implements ITimeModule {

	private String description;
	private Integer visualorder;

	public String getDescription(){ 
		return this.description;
	}
	public Integer getVisualorder(){ 
		return this.visualorder;
	}
	public void setDescription(String description){ 
		this.description = description;
	}
	public void setVisualorder(Integer visualorder){ 
		this.visualorder = visualorder;
	}
}
