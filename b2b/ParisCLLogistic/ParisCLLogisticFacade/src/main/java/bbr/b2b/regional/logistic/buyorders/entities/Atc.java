package bbr.b2b.regional.logistic.buyorders.entities;

import bbr.b2b.regional.logistic.buyorders.data.interfaces.IAtc;

public class Atc implements IAtc {

	private Long id;
	private String code;

	public Long getId(){ 
		return this.id;
	}
	public String getCode(){ 
		return this.code;
	}
	public void setId(Long id){ 
		this.id = id;
	}
	public void setCode(String code){ 
		this.code = code;
	}
}
