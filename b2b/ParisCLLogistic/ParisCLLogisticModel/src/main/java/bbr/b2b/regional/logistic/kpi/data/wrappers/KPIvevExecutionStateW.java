package bbr.b2b.regional.logistic.kpi.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.kpi.data.interfaces.IKPIvevExecutionState;

public class KPIvevExecutionStateW extends ElementDTO implements IKPIvevExecutionState{

	private Date when1;
	private String type;
		
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
