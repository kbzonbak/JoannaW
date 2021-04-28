package bbr.b2b.regional.logistic.datings.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.datings.data.interfaces.IModule;

public class Module implements IModule {

	private Long id;
	private String name;
	private Date starts;
	private Date ends;
	private Integer visualorder;

	public Long getId(){ 
		return this.id;
	}
	public String getName(){ 
		return this.name;
	}
	public Date getStarts(){ 
		return this.starts;
	}
	public Date getEnds(){ 
		return this.ends;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setName(String name){ 
		this.name = name;
	}
	public void setStarts(Date starts){ 
		this.starts = starts;
	}
	public void setEnds(Date ends){ 
		this.ends = ends;
	}
	public Integer getVisualorder() {
		return visualorder;
	}
	public void setVisualorder(Integer visualorder) {
		this.visualorder = visualorder;
	}
	
}
