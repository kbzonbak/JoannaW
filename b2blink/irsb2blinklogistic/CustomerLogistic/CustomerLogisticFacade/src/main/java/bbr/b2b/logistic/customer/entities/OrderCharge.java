package bbr.b2b.logistic.customer.entities;

import bbr.b2b.logistic.customer.entities.Order;
import bbr.b2b.logistic.customer.data.interfaces.IOrderCharge;

public class OrderCharge implements IOrderCharge {

	private Long id;
	private String code;
	private String description;
	private Boolean procentaje;
	private Double value;
	private Order order;

	public Long getId(){ 
		return this.id;
	}
	public String getDescription(){ 
		return this.description;
	}
	public Boolean getProcentaje(){ 
		return this.procentaje;
	}
	public Double getValue(){ 
		return this.value;
	}
	public Order getOrder(){ 
		return this.order;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setDescription(String description){ 
		this.description = description;
	}
	public void setProcentaje(Boolean procentaje){ 
		this.procentaje = procentaje;
	}
	public void setValue(Double value){ 
		this.value = value;
	}
	public void setOrder(Order order){ 
		this.order = order;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
