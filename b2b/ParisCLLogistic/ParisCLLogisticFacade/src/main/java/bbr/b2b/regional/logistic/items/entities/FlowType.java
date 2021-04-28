package bbr.b2b.regional.logistic.items.entities;

import bbr.b2b.regional.logistic.items.data.interfaces.IFlowType;

public class FlowType implements IFlowType {

	private Long id;
	private String code;
	private String name;
	private Long estimatedtime;

	public Long getId(){ 
		return this.id;
	}
	public String getCode(){ 
		return this.code;
	}
	public String getName(){ 
		return this.name;
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
	public Long getEstimatedtime() {
		return estimatedtime;
	}
	public void setEstimatedtime(Long estimatedtime) {
		this.estimatedtime = estimatedtime;
	}
}
