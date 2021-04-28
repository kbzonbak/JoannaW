package bbr.b2b.regional.logistic.kpi.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.kpi.data.interfaces.IKPIvevCDRule;

public class KPIvevCDRuleW extends ElementDTO implements IKPIvevCDRule {

	private Long orderstatetypeid;
	private Long intimekpivevcdtypeid;
	private Long ontimekpivevcdtypeid;
	private Long delayedkpivevcdtypeid;
	
	public Long getOrderstatetypeid() {
		return orderstatetypeid;
	}
	public void setOrderstatetypeid(Long orderstatetypeid) {
		this.orderstatetypeid = orderstatetypeid;
	}
	public Long getIntimekpivevcdtypeid() {
		return intimekpivevcdtypeid;
	}
	public void setIntimekpivevcdtypeid(Long intimekpivevcdtypeid) {
		this.intimekpivevcdtypeid = intimekpivevcdtypeid;
	}
	public Long getOntimekpivevcdtypeid() {
		return ontimekpivevcdtypeid;
	}
	public void setOntimekpivevcdtypeid(Long ontimekpivevcdtypeid) {
		this.ontimekpivevcdtypeid = ontimekpivevcdtypeid;
	}
	public Long getDelayedkpivevcdtypeid() {
		return delayedkpivevcdtypeid;
	}
	public void setDelayedkpivevcdtypeid(Long delayedkpivevcdtypeid) {
		this.delayedkpivevcdtypeid = delayedkpivevcdtypeid;
	}
}
