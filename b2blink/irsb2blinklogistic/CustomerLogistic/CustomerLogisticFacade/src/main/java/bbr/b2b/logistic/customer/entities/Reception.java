package bbr.b2b.logistic.customer.entities;

import java.util.Date;
import bbr.b2b.logistic.customer.entities.OrderType;
import bbr.b2b.logistic.customer.entities.SoaStateType;
import bbr.b2b.logistic.customer.entities.Buyer;
import bbr.b2b.logistic.customer.entities.Vendor;
import bbr.b2b.logistic.customer.entities.Location;
import bbr.b2b.logistic.customer.data.interfaces.IReception;

public class Reception implements IReception {

	private Long id;
	private Long receptionnumber;
	private Long ordernumber;
	private String ordertypename;
	private boolean complete;
	private Date receptiondate;
	private Double total;
	private String paymentcondition;
	private String observation;
	private String responsible;
	private OrderType ordertype;
	private SoaStateType soastatetype;
	private Buyer buyer;
	private Vendor vendor;
	private Location deliveryplace;

	public Long getId(){ 
		return this.id;
	}
	public Long getReceptionnumber(){ 
		return this.receptionnumber;
	}
	public Long getOrdernumber(){ 
		return this.ordernumber;
	}
	public String getOrdertypename(){ 
		return this.ordertypename;
	}
	public boolean getComplete(){ 
		return this.complete;
	}
	public Date getReceptiondate(){ 
		return this.receptiondate;
	}
	public Double getTotal(){ 
		return this.total;
	}
	public String getPaymentcondition(){ 
		return this.paymentcondition;
	}
	public String getObservation(){ 
		return this.observation;
	}
	public String getResponsible(){ 
		return this.responsible;
	}
	public OrderType getOrdertype(){ 
		return this.ordertype;
	}
	public SoaStateType getSoastatetype(){ 
		return this.soastatetype;
	}
	public Buyer getBuyer(){ 
		return this.buyer;
	}
	public Vendor getVendor(){ 
		return this.vendor;
	}
	public Location getDeliveryplace(){ 
		return this.deliveryplace;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setReceptionnumber(Long receptionnumber){ 
		this.receptionnumber = receptionnumber;
	}
	public void setOrdernumber(Long ordernumber){ 
		this.ordernumber = ordernumber;
	}
	public void setOrdertypename(String ordertypename){ 
		this.ordertypename = ordertypename;
	}
	public void setComplete(boolean complete){ 
		this.complete = complete;
	}
	public void setReceptiondate(Date receptiondate){ 
		this.receptiondate = receptiondate;
	}
	public void setTotal(Double total){ 
		this.total = total;
	}
	public void setPaymentcondition(String paymentcondition){ 
		this.paymentcondition = paymentcondition;
	}
	public void setObservation(String observation){ 
		this.observation = observation;
	}
	public void setResponsible(String responsible){ 
		this.responsible = responsible;
	}
	public void setOrdertype(OrderType ordertype){ 
		this.ordertype = ordertype;
	}
	public void setSoastatetype(SoaStateType soastatetype){ 
		this.soastatetype = soastatetype;
	}
	public void setBuyer(Buyer buyer){ 
		this.buyer = buyer;
	}
	public void setVendor(Vendor vendor){ 
		this.vendor = vendor;
	}
	public void setDeliveryplace(Location deliveryplace){ 
		this.deliveryplace = deliveryplace;
	}
}
