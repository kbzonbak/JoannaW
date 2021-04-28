package bbr.b2b.logistic.datings.data.wrappers;

import bbr.b2b.logistic.datings.data.interfaces.IDock;
import bbr.b2b.common.adtclasses.classes.ElementDTO;

public class DockW extends ElementDTO implements IDock {

	private String code;
	private Integer visualorder;
	private Boolean active;
	private Long locationid;

	public String getCode(){ 
		return this.code;
	}
	public Integer getVisualorder(){ 
		return this.visualorder;
	}
	public Boolean getActive(){ 
		return this.active;
	}
	public Long getLocationid(){ 
		return this.locationid;
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
	public void setLocationid(Long locationid){ 
		this.locationid = locationid;
	}
}
