package bbr.b2b.regional.logistic.deliveries.entities;

import java.util.Date;
import bbr.b2b.regional.logistic.deliveries.entities.DeliveryType;
import bbr.b2b.regional.logistic.deliveries.entities.DeliveryStateType;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.buyorders.entities.OrderType;
import bbr.b2b.regional.logistic.items.entities.FlowType;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IDelivery;

public class Delivery implements IDelivery {

	private Long id;
	private Long number;
	private Date created;
	private Date currentstatetypedate;
	private Long receptionnumber;
	private Date receptiondate;
	private Long refdocument;
	private String container;
	private String imp;
	private Integer complementscount;
	private Boolean hasatc;
	private DeliveryType deliverytype;
	private DeliveryStateType currentstatetype;
	private Location location;
	private OrderType ordertype;
	private FlowType flowtype;
	private Vendor vendor;

	public Long getId(){ 
		return this.id;
	}
	public Long getNumber(){ 
		return this.number;
	}
	public Date getCreated(){ 
		return this.created;
	}
	public Date getCurrentstatetypedate(){ 
		return this.currentstatetypedate;
	}
	public Long getReceptionnumber(){ 
		return this.receptionnumber;
	}
	public Date getReceptiondate(){ 
		return this.receptiondate;
	}
	public Long getRefdocument(){ 
		return this.refdocument;
	}
	public String getContainer(){ 
		return this.container;
	}
	public String getImp(){ 
		return this.imp;
	}
	public Integer getComplementscount(){ 
		return this.complementscount;
	}
	public Boolean getHasatc() {
		return hasatc;
	}
	public DeliveryType getDeliverytype(){ 
		return this.deliverytype;
	}
	public DeliveryStateType getCurrentstatetype(){ 
		return this.currentstatetype;
	}
	public Location getLocation(){ 
		return this.location;
	}
	public OrderType getOrdertype(){ 
		return this.ordertype;
	}
	public FlowType getFlowtype(){ 
		return this.flowtype;
	}
	public Vendor getVendor(){ 
		return this.vendor;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setNumber(Long number){ 
		this.number = number;
	}
	public void setCreated(Date created){ 
		this.created = created;
	}
	public void setCurrentstatetypedate(Date currentstatetypedate){ 
		this.currentstatetypedate = currentstatetypedate;
	}
	public void setReceptionnumber(Long receptionnumber){ 
		this.receptionnumber = receptionnumber;
	}
	public void setReceptiondate(Date receptiondate){ 
		this.receptiondate = receptiondate;
	}
	public void setRefdocument(Long refdocument){ 
		this.refdocument = refdocument;
	}
	public void setContainer(String container){ 
		this.container = container;
	}
	public void setImp(String imp){ 
		this.imp = imp;
	}
	public void setComplementscount(Integer complementscount){ 
		this.complementscount = complementscount;
	}
	public void setHasatc(Boolean hasatc) {
		this.hasatc = hasatc;
	}
	public void setDeliverytype(DeliveryType deliverytype){ 
		this.deliverytype = deliverytype;
	}
	public void setCurrentstatetype(DeliveryStateType currentstatetype){ 
		this.currentstatetype = currentstatetype;
	}
	public void setLocation(Location location){ 
		this.location = location;
	}
	public void setOrdertype(OrderType ordertype){ 
		this.ordertype = ordertype;
	}
	public void setFlowtype(FlowType flowtype){ 
		this.flowtype = flowtype;
	}
	public void setVendor(Vendor vendor){ 
		this.vendor = vendor;
	}
}
