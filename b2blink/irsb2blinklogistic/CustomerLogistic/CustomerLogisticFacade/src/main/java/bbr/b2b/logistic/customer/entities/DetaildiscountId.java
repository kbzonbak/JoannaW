package bbr.b2b.logistic.customer.entities;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.customer.data.interfaces.IDetaildiscountPK;

public class DetaildiscountId implements IDetaildiscountPK {

	private Long orderid;
	private String skubuyer;
	private Integer position;

	public DetaildiscountId(){
	}
	public DetaildiscountId(Long orderid, String skubuyer,Integer position){
		this.orderid = orderid;
		this.skubuyer = skubuyer;
		this.position = position;
	}

	public boolean equals(Object o){
		if (o != null && o instanceof DetaildiscountId){
			DetaildiscountId that = (DetaildiscountId) o;
			return this.orderid.equals(that.orderid) && this.skubuyer.equals(that.skubuyer) && this.position.equals(that.position);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return orderid.hashCode() + skubuyer.hashCode() + skubuyer.hashCode();
	}
	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = orderid.longValue() - ((IDetaildiscountPK) arg0).getOrderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = skubuyer.equals(((IDetaildiscountPK) arg0).getSkubuyer()) ? 0 : -1;
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = position.equals(((IDetaildiscountPK) arg0).getPosition()) ? 0 : -1;
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

	public Long getOrderid(){ 
		return this.orderid;
	}
	public String getSkubuyer(){ 
		return this.skubuyer;
	}
	public Integer getPosition(){ 
		return this.position;
	}
	public void setOrderid(Long orderid){ 
		this.orderid = orderid;
	}
	public void setSkubuyer(String skubuyer){ 
		this.skubuyer = skubuyer;
	}
	public void setPosition(Integer position){ 
		this.position = position;
	}
}
