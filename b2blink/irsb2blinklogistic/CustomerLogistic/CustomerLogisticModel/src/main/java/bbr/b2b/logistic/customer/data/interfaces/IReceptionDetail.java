package bbr.b2b.logistic.customer.data.interfaces;

import bbr.b2b.common.adtclasses.interfaces.IElement;

public interface IReceptionDetail extends IElement {

	public Integer getPosition();
	public String getSkubuyer();
	public String getSkuvendor();
	public String getEan13();
	public String getProductdescription();
	public String getCodeumc();
	public String getDescriptionumc();
	public String getDescriptionumb();
	public Integer getQuantityumc();
	public Double getReceivedquantity();
	public Double getListcost();
	public Double getFinalcost();
	public void setPosition(Integer position);
	public void setSkubuyer(String skubuyer);
	public void setSkuvendor(String skuvendor);
	public void setEan13(String ean13);
	public void setProductdescription(String productdescription);
	public void setCodeumc(String codeumc);
	public void setDescriptionumc(String descriptionumc);
	public void setDescriptionumb(String descriptionumb);
	public void setQuantityumc(Integer quantityumc);
	public void setReceivedquantity(Double receivedquantity);
	public void setListcost(Double listcost);
	public void setFinalcost(Double finalcost);
}
