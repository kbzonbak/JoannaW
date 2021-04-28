package bbr.b2b.regional.logistic.kpi.entities;

import bbr.b2b.regional.logistic.buyorders.entities.OrderStateType;
import bbr.b2b.regional.logistic.kpi.data.interfaces.IKPIvevCDRule;

public class KPIvevCDRule implements IKPIvevCDRule {

	private Long id;
	private OrderStateType orderstatetype;
	private KPIvevCDType intimekpivevcdtype;
	private KPIvevCDType ontimekpivevcdtype;
	private KPIvevCDType delayedkpivevcdtype;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public OrderStateType getOrderstatetype() {
		return orderstatetype;
	}
	public void setOrderstatetype(OrderStateType orderstatetype) {
		this.orderstatetype = orderstatetype;
	}
	public KPIvevCDType getIntimekpivevcdtype() {
		return intimekpivevcdtype;
	}
	public void setIntimekpivevcdtype(KPIvevCDType intimekpivevcdtype) {
		this.intimekpivevcdtype = intimekpivevcdtype;
	}
	public KPIvevCDType getOntimekpivevcdtype() {
		return ontimekpivevcdtype;
	}
	public void setOntimekpivevcdtype(KPIvevCDType ontimekpivevcdtype) {
		this.ontimekpivevcdtype = ontimekpivevcdtype;
	}
	public KPIvevCDType getDelayedkpivevcdtype() {
		return delayedkpivevcdtype;
	}
	public void setDelayedkpivevcdtype(KPIvevCDType delayedkpivevcdtype) {
		this.delayedkpivevcdtype = delayedkpivevcdtype;
	}
}
