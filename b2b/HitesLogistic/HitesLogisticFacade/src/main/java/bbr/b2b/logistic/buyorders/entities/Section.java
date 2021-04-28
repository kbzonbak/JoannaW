package bbr.b2b.logistic.buyorders.entities;

import bbr.b2b.logistic.buyorders.data.interfaces.ISection;

public class Section implements ISection {

	private Long id;
	private String name;
	private String code;

	public Long getId(){ 
		return this.id;
	}
	public String getName(){ 
		return this.name;
	}
	public String getCode(){ 
		return this.code;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setName(String name){ 
		this.name = name;
	}
	public void setCode(String code){ 
		this.code = code;
	}
}
