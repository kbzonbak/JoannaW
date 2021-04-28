package bbr.b2b.regional.logistic.datings.entities;

import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.datings.data.interfaces.IDock;

public class Dock implements IDock {

	private Long id;
	private String code;
	private Boolean active;
	private Integer visualorder;
	private DockType docktype;
	private Location location;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Integer getVisualorder() {
		return visualorder;
	}
	public void setVisualorder(Integer visualorder) {
		this.visualorder = visualorder;
	}
	public DockType getDocktype() {
		return docktype;
	}
	public void setDocktype(DockType docktype) {
		this.docktype = docktype;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
}
