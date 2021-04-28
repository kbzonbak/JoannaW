package bbr.b2b.logistic.customer.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.customer.data.interfaces.IPredistributionPK;

public class PredistributioPK implements IPredistributionPK {

	private Long orderid;
	private String skubuyer;
	private Long localid;
	private Integer position;

	public PredistributioPK(){
	}
	public PredistributioPK(Long orderid, String skubuyer, Long localid , Integer position){
		this.orderid = orderid;
		this.skubuyer = skubuyer;
		this.localid = localid;
		this.position = position;
	}

	public boolean equals(Object o){
		if (o != null && o instanceof PredistributioPK){
			PredistributioPK that = (PredistributioPK) o;
			return this.orderid.equals(that.orderid) && this.skubuyer.equals(that.skubuyer) && this.localid.equals(that.localid) && this.position.equals(that.position);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return orderid.hashCode() + skubuyer.hashCode() + localid.hashCode()+ position.hashCode();
	}
	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = orderid.longValue() - ((IPredistributionPK) arg0).getOrderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = skubuyer.equals(((IPredistributionPK) arg0).getSkubuyer()) ? 0 : -1;
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = localid.longValue() - ((IPredistributionPK) arg0).getLocalid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = position.longValue() - ((IPredistributionPK) arg0).getPosition().longValue();
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
	public Long getLocalid(){ 
		return this.localid;
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
	public void setLocalid(Long localid){ 
		this.localid = localid;
	}
	public void setPosition(Integer position){ 
		this.position = position;
	}
}
