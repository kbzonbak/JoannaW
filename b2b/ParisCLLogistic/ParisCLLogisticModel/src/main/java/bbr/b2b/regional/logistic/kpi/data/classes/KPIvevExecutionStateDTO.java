package bbr.b2b.regional.logistic.kpi.data.classes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class KPIvevExecutionStateDTO implements Serializable{

	private Long id;
	private Date when1;
	private LocalDateTime when1ldt;
	private String type;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getWhen1() {
		return when1;
	}
	public void setWhen1(Date when1) {
		this.when1 = when1;
	}
	public LocalDateTime getWhen1ldt() {
		return when1ldt;
	}
	public void setWhen1ldt(LocalDateTime when1ldt) {
		this.when1ldt = when1ldt;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
		

		
}
