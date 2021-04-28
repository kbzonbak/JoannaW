package bbr.b2b.regional.logistic.couriers.report.classes;

import java.io.Serializable;

public class CourierInformationDTO implements Serializable {

	private Long id;
	private String description;
	private String code;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
