package bbr.b2b.regional.logistic.items.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.items.data.interfaces.IFlowType;

public class FlowTypeW extends ElementDTO implements IFlowType {

	private String code;
	private String name;
	private Long estimatedtime;

	public String getCode(){ 
		return this.code;
	}
	public String getName(){ 
		return this.name;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setName(String name){ 
		this.name = name;
	}
	public Long getEstimatedtime() {
		return estimatedtime;
	}
	public void setEstimatedtime(Long estimatedtime) {
		this.estimatedtime = estimatedtime;
	}
}
