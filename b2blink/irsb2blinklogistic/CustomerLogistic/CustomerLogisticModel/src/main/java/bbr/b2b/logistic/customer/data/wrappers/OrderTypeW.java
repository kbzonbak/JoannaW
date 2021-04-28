package bbr.b2b.logistic.customer.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.customer.data.interfaces.IOrderType;

public class OrderTypeW extends ElementDTO implements IOrderType {

	private String code;
	private String name;
	private Long buyerid;
	private Long ordertypeb2bid;

	public String getCode(){ 
		return this.code;
	}
	public String getName(){ 
		return this.name;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setName(String name){ 
		this.name = name;
	}
	public Long getBuyerid() {
		return buyerid;
	}
	public void setBuyerid(Long buyerid) {
		this.buyerid = buyerid;
	}
	public Long getOrdertypeb2bid() {
		return ordertypeb2bid;
	}
	public void setOrdertypeb2bid(Long ordertypeb2bid) {
		this.ordertypeb2bid = ordertypeb2bid;
	}
}
