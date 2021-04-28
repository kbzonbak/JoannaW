package bbr.b2b.regional.logistic.deliveries.entities;

import bbr.b2b.regional.logistic.deliveries.entities.OrderDelivery;
import bbr.b2b.regional.logistic.vendors.entities.Department;
import bbr.b2b.regional.logistic.items.entities.FlowType;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IBulk;

public class Bulk implements IBulk {

	private Long id;
	private String code;
	private Long number;
	private OrderDelivery orderdelivery;
	private Department department;
	private FlowType flowtype;
	private Location location;

	public Long getId(){ 
		return this.id;
	}
	public String getCode(){ 
		return this.code;
	}
	public Long getNumber(){ 
		return this.number;
	}
	public OrderDelivery getOrderdelivery(){ 
		return this.orderdelivery;
	}
	public Department getDepartment(){ 
		return this.department;
	}
	public FlowType getFlowtype(){ 
		return this.flowtype;
	}
	public Location getLocation(){ 
		return this.location;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setNumber(Long number){ 
		this.number = number;
	}
	public void setOrderdelivery(OrderDelivery orderdelivery){ 
		this.orderdelivery = orderdelivery;
	}
	public void setDepartment(Department department){ 
		this.department = department;
	}
	public void setFlowtype(FlowType flowtype){ 
		this.flowtype = flowtype;
	}
	public void setLocation(Location location){ 
		this.location = location;
	}
}
