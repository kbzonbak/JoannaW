package bbr.b2b.logistic.datings.entities;

import java.time.LocalDateTime;

import bbr.b2b.logistic.datings.data.interfaces.IModule;

public class Module implements IModule {

	private Long id;
	private String name;
	private LocalDateTime starts;
	private LocalDateTime ends;
	private Integer visualorder;

	public Long getId(){ 
		return this.id;
	}
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
	public void setId(Long id){ 
		this.id = id;
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
