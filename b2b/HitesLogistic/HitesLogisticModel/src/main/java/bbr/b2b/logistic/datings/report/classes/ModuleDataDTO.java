package bbr.b2b.logistic.datings.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ModuleDataDTO implements Serializable {

	private Long id;
	private String name;
	private LocalDateTime starts;
	private LocalDateTime ends;
	private Integer visualorder;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getStarts() {
		return starts;
	}

	public void setStarts(LocalDateTime starts) {
		this.starts = starts;
	}

	public LocalDateTime getEnds() {
		return ends;
	}

	public void setEnds(LocalDateTime ends) {
		this.ends = ends;
	}

	public Integer getVisualorder() {
		return visualorder;
	}

	public void setVisualorder(Integer visualorder) {
		this.visualorder = visualorder;
	}

}
