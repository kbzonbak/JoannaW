package bbr.b2b.logistic.customer.entities;

import bbr.b2b.logistic.customer.entities.Detail;
import bbr.b2b.logistic.customer.data.interfaces.IDetailDiscount;

public class DetailDiscount implements IDetailDiscount {

	private DetaildiscountId id;
	private String description;
	private String code;
	private Boolean procentaje;
	private Double value;
	private Detail detail;

	public DetaildiscountId getId(){ 
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
	public Detail getDetail(){ 
		return this.detail;
	}
	public void setId(DetaildiscountId id){ 
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
	public void setDetail(Detail detail){ 
		this.detail = detail;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
