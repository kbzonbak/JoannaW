package bbr.b2b.logistic.notifications.report.classes;

import java.io.Serializable;

public class NotificationTypeDTO implements Serializable {

	private Long id;
	private String code;
	private String description;
	private Boolean visible;
	private Long visualorder;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Long getVisualorder() {
		return visualorder;
	}

	public void setVisualorder(Long visualorder) {
		this.visualorder = visualorder;
	}

}
