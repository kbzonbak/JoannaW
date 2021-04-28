package bbr.b2b.logistic.customer.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.customer.data.interfaces.IDetailPK;

public class DetailPK implements IDetailPK {

	private Long orderid;
	private String skubuyer;
	private Integer position;

	public DetailPK(){
	}
	public DetailPK(Long orderid, String skubuyer,Integer position){
		this.orderid = orderid;
		this.skubuyer = skubuyer;
		this.position = position;
	}

	public boolean equals(Object o){
		if (o != null && o instanceof DetailPK){
			DetailPK that = (DetailPK) o;
			return this.orderid.equals(that.orderid) && this.skubuyer.equals(that.skubuyer) && this.position.equals(that.position);
		}else{
			return false;
		}
	}
	public int hashCode() {
		return orderid.hashCode() + skubuyer.hashCode() + position.hashCode();
	}
	public int compareTo(IPrimaryKey arg0){
		long result = 0;
		result = orderid.longValue() - ((IDetailPK) arg0).getOrderid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = skubuyer.equals("") ? 0 : -1; //(IDetailPK) arg0)
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = position.equals("") ? 0 : -1; 
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
