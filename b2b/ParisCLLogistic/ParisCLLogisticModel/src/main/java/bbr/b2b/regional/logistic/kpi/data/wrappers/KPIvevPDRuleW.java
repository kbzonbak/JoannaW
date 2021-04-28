package bbr.b2b.regional.logistic.kpi.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.kpi.data.interfaces.IKPIvevPDRule;

public class KPIvevPDRuleW extends ElementDTO implements IKPIvevPDRule {

	private Long directorderstatetypeid;
	private Long currentdodeliverystatetypeid;
	private Long intimekpivevpdtypeid;
	private Long ontimekpivevpdtypeid;
	private Long delayedkpivevpdtypeid;
	
	public Long getDirectorderstatetypeid() {
		return directorderstatetypeid;
	}
	public void setDirectorderstatetypeid(Long directorderstatetypeid) {
		this.directorderstatetypeid = directorderstatetypeid;
	}
	public Long getCurrentdodeliverystatetypeid() {
		return currentdodeliverystatetypeid;
	}
	public void setCurrentdodeliverystatetypeid(Long currentdodeliverystatetypeid) {
		this.currentdodeliverystatetypeid = currentdodeliverystatetypeid;
	}
	public Long getIntimekpivevpdtypeid() {
		return intimekpivevpdtypeid;
	}
	public void setIntimekpivevpdtypeid(Long intimekpivevpdtypeid) {
		this.intimekpivevpdtypeid = intimekpivevpdtypeid;
	}
	public Long getOntimekpivevpdtypeid() {
		return ontimekpivevpdtypeid;
	}
	public void setOntimekpivevpdtypeid(Long ontimekpivevpdtypeid) {
		this.ontimekpivevpdtypeid = ontimekpivevpdtypeid;
	}
	public Long getDelayedkpivevpdtypeid() {
		return delayedkpivevpdtypeid;
	}
	public void setDelayedkpivevpdtypeid(Long delayedkpivevpdtypeid) {
		this.delayedkpivevpdtypeid = delayedkpivevpdtypeid;
	}
}
