package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;

public class DvrDeliveryStateTypeDataDTO implements Serializable {

	private Long id;
	private String code;
	private String description;
	private Boolean valid;
	private Boolean visible;
	private String nextaction;

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

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public String getNextaction() {
		return nextaction;
	}

	public void setNextaction(String nextaction) {
		this.nextaction = nextaction;
	}
}
