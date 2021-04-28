package bbr.b2b.regional.logistic.kpi.entities;

import java.util.Date;

import bbr.b2b.regional.logistic.kpi.data.interfaces.IKPIvevExecutionState;

public class KPIvevExecutionState implements IKPIvevExecutionState{

	private Long id;
	private Date when1;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
