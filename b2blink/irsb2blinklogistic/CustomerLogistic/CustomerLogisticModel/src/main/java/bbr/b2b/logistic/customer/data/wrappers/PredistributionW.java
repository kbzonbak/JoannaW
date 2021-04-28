package bbr.b2b.logistic.customer.data.wrappers;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.logistic.customer.data.interfaces.IPredistribution;
import bbr.b2b.logistic.customer.data.interfaces.IPredistributionPK;

public class PredistributionW implements IPredistribution, IPredistributionPK {

	private Integer quantity;
	private Long orderid;
	private String skubuyer;
	private Long localid;
	private String shipping_quantity;
	private String received_quantity;
	private String pending_quantity;
	private Integer position;

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
	public Integer getQuantity(){ 
		return this.quantity;
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
	public void setQuantity(Integer quantity){ 
		this.quantity = quantity;
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
	public String getShipping_quantity() {
		return shipping_quantity;
	}
	public void setShipping_quantity(String shipping_quantity) {
		this.shipping_quantity = shipping_quantity;
	}
	public String getReceived_quantity() {
		return received_quantity;
	}
	public void setReceived_quantity(String received_quantity) {
		this.received_quantity = received_quantity;
	}
	public String getPending_quantity() {
		return pending_quantity;
	}
	public void setPending_quantity(String pending_quantity) {
		this.pending_quantity = pending_quantity;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	
}
