package bbr.b2b.logistic.ddcdeliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class DdcDeliveryStateTypeDataDTO extends BaseResultDTO  {

	private Long id;
	private String code;
	private String name;
	private Boolean closed;
	private Boolean showable;
	private Boolean selectable;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getClosed() {
		return closed;
	}
	public void setClosed(Boolean closed) {
		this.closed = closed;
	}
	public Boolean getShowable() {
		return showable;
	}
	public void setShowable(Boolean showable) {
		this.showable = showable;
	}
	public Boolean getSelectable() {
		return selectable;
	}
	public void setSelectable(Boolean selectable) {
		this.selectable = selectable;
	}
		
}
