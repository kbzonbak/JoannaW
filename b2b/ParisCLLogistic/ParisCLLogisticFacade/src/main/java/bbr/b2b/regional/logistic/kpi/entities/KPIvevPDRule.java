package bbr.b2b.regional.logistic.kpi.entities;

import bbr.b2b.regional.logistic.deliveries.entities.DODeliveryStateType;
import bbr.b2b.regional.logistic.directorders.entities.DirectOrderStateType;
import bbr.b2b.regional.logistic.kpi.data.interfaces.IKPIvevPDRule;

public class KPIvevPDRule implements IKPIvevPDRule {

	private Long id;
	private DirectOrderStateType directorderstatetype;
	private DODeliveryStateType currentdodeliverystatetype;
	private KPIvevPDType intimekpivevpdtype;
	private KPIvevPDType ontimekpivevpdtype;
	private KPIvevPDType delayedkpivevpdtype;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public DirectOrderStateType getDirectorderstatetype() {
		return directorderstatetype;
	}
	public void setDirectorderstatetype(DirectOrderStateType directorderstatetype) {
		this.directorderstatetype = directorderstatetype;
	}
	public DODeliveryStateType getCurrentdodeliverystatetype() {
		return currentdodeliverystatetype;
	}
	public void setCurrentdodeliverystatetype(DODeliveryStateType currentdodeliverystatetype) {
		this.currentdodeliverystatetype = currentdodeliverystatetype;
	}
	public KPIvevPDType getIntimekpivevpdtype() {
		return intimekpivevpdtype;
	}
	public void setIntimekpivevpdtype(KPIvevPDType intimekpivevpdtype) {
		this.intimekpivevpdtype = intimekpivevpdtype;
	}
	public KPIvevPDType getOntimekpivevpdtype() {
		return ontimekpivevpdtype;
	}
	public void setOntimekpivevpdtype(KPIvevPDType ontimekpivevpdtype) {
		this.ontimekpivevpdtype = ontimekpivevpdtype;
	}
	public KPIvevPDType getDelayedkpivevpdtype() {
		return delayedkpivevpdtype;
	}
	public void setDelayedkpivevpdtype(KPIvevPDType delayedkpivevpdtype) {
		this.delayedkpivevpdtype = delayedkpivevpdtype;
	}
}
