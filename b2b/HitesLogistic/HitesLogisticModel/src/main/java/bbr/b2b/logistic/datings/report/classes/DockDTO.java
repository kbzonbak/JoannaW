package bbr.b2b.logistic.datings.report.classes;

import java.io.Serializable;

public class DockDTO implements Serializable {

	private Long id;
	private String code;
	private Integer visualorder;
	private Boolean active;
	private Long locationid;

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

	public Integer getVisualorder() {
		return visualorder;
	}

	public void setVisualorder(Integer visualorder) {
		this.visualorder = visualorder;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Long getLocationid() {
		return locationid;
	}

	public void setLocationid(Long locationid) {
		this.locationid = locationid;
	}

}
