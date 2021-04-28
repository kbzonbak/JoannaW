package bbr.b2b.logistic.customer.data.wrappers;

import bbr.b2b.logistic.customer.data.interfaces.IOrderCharge;
import bbr.b2b.common.adtclasses.classes.ElementDTO;

public class OrderChargeW extends ElementDTO implements IOrderCharge {

	private String code;
	private String description;
	private Boolean procentaje;
	private Double value;
	private Long orderid;

	public String getDescription(){ 
		return this.description;
	}
	public Boolean getProcentaje(){ 
		return this.procentaje;
	}
	public Double getValue(){ 
		return this.value;
	}
	public Long getOrderid(){ 
		return this.orderid;
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
	public void setOrderid(Long orderid){ 
		this.orderid = orderid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
