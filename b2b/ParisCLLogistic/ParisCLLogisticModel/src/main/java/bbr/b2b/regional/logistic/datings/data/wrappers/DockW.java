package bbr.b2b.regional.logistic.datings.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.datings.data.interfaces.IDock;

public class DockW extends ElementDTO implements IDock {

	private String code;
	private Boolean active;
	private Integer visualorder;
	private Long docktypeid;
	private Long locationid;
	
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
	public Long getDocktypeid() {
		return docktypeid;
	}
	public void setDocktypeid(Long docktypeid) {
		this.docktypeid = docktypeid;
	}
	public Long getLocationid() {
		return locationid;
	}
	public void setLocationid(Long locationid) {
		this.locationid = locationid;
	}
}
