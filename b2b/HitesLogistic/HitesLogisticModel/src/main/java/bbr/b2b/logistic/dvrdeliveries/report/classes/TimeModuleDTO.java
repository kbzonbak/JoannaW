package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;

public class TimeModuleDTO implements Serializable {

	private Long id;
	private String description;
	private Integer visualorder;

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

	public Integer getVisualorder() {
		return visualorder;
	}

	public void setVisualorder(Integer visualorder) {
		this.visualorder = visualorder;
	}
}
