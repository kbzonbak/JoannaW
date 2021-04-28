package bbr.b2b.regional.logistic.datings.entities;

import bbr.b2b.regional.logistic.datings.entities.Dock;
import bbr.b2b.regional.logistic.datings.entities.Module;
import bbr.b2b.regional.logistic.datings.data.interfaces.IDatingResource;

public class DatingResource implements IDatingResource {

	private DatingResourceId id;
	private Boolean active;
	private Dock dock;
	private Module module;

	public DatingResourceId getId(){ 
		return this.id;
	}
	public Boolean getActive(){ 
		return this.active;
	}
	public Dock getDock(){ 
		return this.dock;
	}
	public Module getModule(){ 
		return this.module;
	}
	public void setId(DatingResourceId id){ 
		this.id = id;
	}
	public void setActive(Boolean active){ 
		this.active = active;
	}
	public void setDock(Dock dock){ 
		this.dock = dock;
	}
	public void setModule(Module module){ 
		this.module = module;
	}
}
