package bbr.b2b.logistic.customer.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.customer.data.interfaces.IAction;

public class ActionW extends ElementDTO implements IAction {

	private String code;
	private String name;
	private Long buyerid;

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
}
