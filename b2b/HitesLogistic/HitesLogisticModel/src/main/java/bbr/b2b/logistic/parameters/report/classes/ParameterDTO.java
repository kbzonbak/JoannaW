package bbr.b2b.logistic.parameters.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ParameterDTO implements Serializable {

	private Long id;
	private String code;
	private String description;
	private String value;
	private String unit;
	private Boolean visible;
	private Boolean active;
	private LocalDateTime creationdate;
	private LocalDateTime modificationdate;

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public String getValue() {
		return value;
	}

	public String getUnit() {
		return unit;
	}

	public Boolean getVisible() {
		return visible;
	}

	public Boolean getActive() {
		return active;
	}

	public LocalDateTime getCreationdate() {
		return creationdate;
	}

	public LocalDateTime getModificationdate() {
		return modificationdate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void setCreationdate(LocalDateTime creationdate) {
		this.creationdate = creationdate;
	}

	public void setModificationdate(LocalDateTime modificationdate) {
		this.modificationdate = modificationdate;
	}

}