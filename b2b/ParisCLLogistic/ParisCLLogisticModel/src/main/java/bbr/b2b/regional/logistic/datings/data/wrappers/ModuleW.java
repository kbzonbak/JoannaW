package bbr.b2b.regional.logistic.datings.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.datings.data.interfaces.IModule;

public class ModuleW extends ElementDTO implements IModule {

	private String name;
	private Date starts;
	private Date ends;
	private Integer visualorder;

	public String getName(){ 
		return this.name;
	}
	public Date getStarts(){ 
		return this.starts;
	}
	public Date getEnds(){ 
		return this.ends;
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
