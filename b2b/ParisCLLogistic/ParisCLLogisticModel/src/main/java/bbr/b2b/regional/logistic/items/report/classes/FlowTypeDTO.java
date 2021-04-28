package bbr.b2b.regional.logistic.items.report.classes;

import java.io.Serializable;

public class FlowTypeDTO implements Serializable{

	private Long id;
	private String code;
	private String name;
	private Long estimatedtime;

	public Long getEstimatedtime() {
		return estimatedtime;
	}
	public void setEstimatedtime(Long estimatedtime) {
		this.estimatedtime = estimatedtime;
	}
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
	public void setCode(String code){ 
		this.code = code;
	}
	public void setName(String name){ 
		this.name = name;
	}
}
