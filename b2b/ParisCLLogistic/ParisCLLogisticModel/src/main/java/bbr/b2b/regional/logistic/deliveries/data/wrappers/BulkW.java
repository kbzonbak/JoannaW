package bbr.b2b.regional.logistic.deliveries.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.regional.logistic.deliveries.data.interfaces.IBulk;

public class BulkW extends ElementDTO implements IBulk {

	private String code;
	private Long number;
	private Long orderid;
	private Long deliveryid;
	private Long departmentid;
	private Long flowtypeid;
	private Long locationid;

	public String getCode(){ 
		return this.code;
	}
	public Long getNumber(){ 
		return this.number;
	}
	public Long getOrderid(){ 
		return this.orderid;
	}
	public Long getDeliveryid(){ 
		return this.deliveryid;
	}
	public Long getDepartmentid(){ 
		return this.departmentid;
	}
	public Long getFlowtypeid(){ 
		return this.flowtypeid;
	}
	public Long getLocationid(){ 
		return this.locationid;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setNumber(Long number){ 
		this.number = number;
	}
	public void setOrderid(Long orderid){ 
		this.orderid = orderid;
	}
	public void setDeliveryid(Long deliveryid){ 
		this.deliveryid = deliveryid;
	}
	public void setDepartmentid(Long departmentid){ 
		this.departmentid = departmentid;
	}
	public void setFlowtypeid(Long flowtypeid){ 
		this.flowtypeid = flowtypeid;
	}
	public void setLocationid(Long locationid){ 
		this.locationid = locationid;
	}
}
