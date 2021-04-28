package bbr.b2b.logistic.customer.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IIdentifiable;

public interface IPredistribution extends IIdentifiable {

	public Integer getQuantity();
	public void setQuantity(Integer quantity);
	public void setShipping_quantity(String shipping_quantity);
	public String getReceived_quantity();
	public void setReceived_quantity(String received_quantity);
	public String getPending_quantity();
	public void setPending_quantity(String pending_quantity);
}
