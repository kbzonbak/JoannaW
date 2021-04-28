package bbr.b2b.logistic.datings.data.wrappers;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.datings.data.interfaces.IModule;

public class ModuleW extends ElementDTO implements IModule {

	private String name;
	private LocalDateTime starts;
	private LocalDateTime ends;
	private Integer visualorder;

	public String getName(){ 
		return this.name;
	}
	public LocalDateTime getStarts(){ 
		return this.starts;
	}
	public LocalDateTime getEnds(){ 
		return this.ends;
	}
	public Integer getVisualorder(){ 
		return this.visualorder;
	}
	public void setName(String name){ 
		this.name = name;
	}
	public void setStarts(LocalDateTime starts){ 
		this.starts = starts;
	}
	public void setEnds(LocalDateTime ends){ 
		this.ends = ends;
	}
	public void setVisualorder(Integer visualorder){ 
		this.visualorder = visualorder;
	}
}
