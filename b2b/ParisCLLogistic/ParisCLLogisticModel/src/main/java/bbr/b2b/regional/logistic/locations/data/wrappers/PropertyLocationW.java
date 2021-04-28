package bbr.b2b.regional.logistic.locations.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.locations.data.interfaces.IPropertyLocation;

public class PropertyLocationW extends ElementDTO implements IPropertyLocation {

	private String code;
	private String description;
	private String value;
	private Date created;
	private String creator;
	private Date lastmodified;
	private String modifier;
	private Long locationid;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getLastmodified() {
		return lastmodified;
	}
	public void setLastmodified(Date lastmodified) {
		this.lastmodified = lastmodified;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public Long getLocationid() {
		return locationid;
	}
	public void setLocationid(Long locationid) {
		this.locationid = locationid;
	}
}
