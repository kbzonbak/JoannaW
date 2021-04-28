package bbr.b2b.logistic.dvrdeliveries.entities;

import bbr.b2b.logistic.dvrdeliveries.data.interfaces.ITimeModule;

public class TimeModule implements ITimeModule {

	private Long id;
	private String description;
	private Integer visualorder;

	public Long getId(){ 
		return this.id;
	}
	public String getDescription(){ 
		return this.description;
	}
	public Integer getVisualorder(){ 
		return this.visualorder;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setDescription(String description){ 
		this.description = description;
	}
	public void setVisualorder(Integer visualorder){ 
		this.visualorder = visualorder;
	}
}
