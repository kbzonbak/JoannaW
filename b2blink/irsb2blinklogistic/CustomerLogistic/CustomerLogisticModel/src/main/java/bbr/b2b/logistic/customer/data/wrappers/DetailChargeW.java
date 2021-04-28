package bbr.b2b.logistic.customer.data.wrappers;

import bbr.b2b.logistic.customer.data.interfaces.IDetailCharge;
import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.customer.data.interfaces.IDetailchargePK;

public class DetailChargeW implements IDetailCharge, IDetailchargePK {

	private String code;
	private String description;
	private Boolean procentaje;
	private Double value;
	private Long orderid;
	private String skubuyer;
	private Integer position;

	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = orderid.longValue() - ((IDetailchargePK) arg0).getOrderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = skubuyer.equals(((IDetailchargePK) arg0).getSkubuyer()) ? 0 : -1;
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = position.equals(((IDetailchargePK) arg0).getPosition()) ? 0 : -1;
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
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
	public Long getOrderid(){ 
		return this.orderid;
	}
	public String getSkubuyer(){ 
		return this.skubuyer;
	}
	public String getCode() {
		return code;
	}
	public Integer getPosition(){ 
		return this.position;
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
	public void setSkubuyer(String skubuyer){ 
		this.skubuyer = skubuyer;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
}
