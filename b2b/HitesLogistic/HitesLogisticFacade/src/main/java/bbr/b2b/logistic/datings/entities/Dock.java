package bbr.b2b.logistic.datings.entities;

import bbr.b2b.logistic.locations.entities.Location;
import bbr.b2b.logistic.datings.data.interfaces.IDock;

public class Dock implements IDock {

	private Long id;
	private String code;
	private Integer visualorder;
	private Boolean active;
	private Location location;

	public Long getId(){ 
		return this.id;
	}
	public String getCode(){ 
		return this.code;
	}
	public Integer getVisualorder(){ 
		return this.visualorder;
	}
	public Boolean getActive(){ 
		return this.active;
	}
	public Location getLocation(){ 
		return this.location;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setVisualorder(Integer visualorder){ 
		this.visualorder = visualorder;
	}
	public void setActive(Boolean active){ 
		this.active = active;
	}
	public void setLocation(Location location){ 
		this.location = location;
	}
}
