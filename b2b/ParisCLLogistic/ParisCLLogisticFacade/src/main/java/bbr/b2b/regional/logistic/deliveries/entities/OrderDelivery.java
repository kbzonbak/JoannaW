package bbr.b2b.regional.logistic.deliveries.entities;

import bbr.b2b.regional.logistic.deliveries.entities.Delivery;
import bbr.b2b.regional.logistic.buyorders.entities.Order;
import bbr.b2b.regional.logistic.vendors.entities.Department;
import bbr.b2b.regional.logistic.items.entities.FlowType;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IOrderDelivery;

public class OrderDelivery implements IOrderDelivery {

	private OrderDeliveryId id;
	private Boolean closed;
	private Long asnimp;
	private Long refdocument;
	private String imp;
	private Integer deliveryindex;
	private Double estimatedunits;
	private Double originalestimunits;
	private Integer realbulkcount;
	private Integer realpalletcount;
	private Double realpackedunits;
	private Delivery delivery;
	private Order order;
	private Department department;
	private FlowType flowtype;

	public Long getAsnimp() {
		return asnimp;
	}
	public void setAsnimp(Long asnimp) {
		this.asnimp = asnimp;
	}
	public Long getRefdocument() {
		return refdocument;
	}
	public void setRefdocument(Long refdocument) {
		this.refdocument = refdocument;
	}
	public String getImp() {
		return imp;
	}
	public void setImp(String imp) {
		this.imp = imp;
	}
	public OrderDeliveryId getId(){ 
		return this.id;
	}
	public Boolean getClosed(){ 
		return this.closed;
	}
	public Integer getDeliveryindex(){ 
		return this.deliveryindex;
	}
	public Double getEstimatedunits(){ 
		return this.estimatedunits;
	}
	public Double getOriginalestimunits(){ 
		return this.originalestimunits;
	}
	public Integer getRealbulkcount(){ 
		return this.realbulkcount;
	}
	public Integer getRealpalletcount(){ 
		return this.realpalletcount;
	}
	public Double getRealpackedunits(){ 
		return this.realpackedunits;
	}
	public Delivery getDelivery(){ 
		return this.delivery;
	}
	public Order getOrder(){ 
		return this.order;
	}
	public Department getDepartment(){ 
		return this.department;
	}
	public FlowType getFlowtype(){ 
		return this.flowtype;
	}
	public void setId(OrderDeliveryId id){ 
		this.id = id;
	}
	public void setClosed(Boolean closed){ 
		this.closed = closed;
	}
	public void setDeliveryindex(Integer deliveryindex){ 
		this.deliveryindex = deliveryindex;
	}
	public void setEstimatedunits(Double estimatedunits){ 
		this.estimatedunits = estimatedunits;
	}
	public void setOriginalestimunits(Double originalestimunits){ 
		this.originalestimunits = originalestimunits;
	}
	public void setRealbulkcount(Integer realbulkcount){ 
		this.realbulkcount = realbulkcount;
	}
	public void setRealpalletcount(Integer realpalletcount){ 
		this.realpalletcount = realpalletcount;
	}
	public void setRealpackedunits(Double realpackedunits){ 
		this.realpackedunits = realpackedunits;
	}
	public void setDelivery(Delivery delivery){ 
		this.delivery = delivery;
	}
	public void setOrder(Order order){ 
		this.order = order;
	}
	public void setDepartment(Department department){ 
		this.department = department;
	}
	public void setFlowtype(FlowType flowtype){ 
		this.flowtype = flowtype;
	}
}
