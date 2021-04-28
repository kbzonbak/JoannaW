package bbr.b2b.logistic.customer.data.wrappers;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.customer.data.interfaces.IReceptionDetail;

public class ReceptionDetailW extends ElementDTO implements IReceptionDetail {

	private Integer position;
	private String skubuyer;
	private String skuvendor;
	private String ean13;
	private String productdescription;
	private String codeumc;
	private String descriptionumc;
	private String descriptionumb;
	private Integer quantityumc;
	private Double receivedquantity;
	private Double listcost;
	private Double finalcost;
	private Long receptionid;

	public Integer getPosition(){ 
		return this.position;
	}
	public String getSkubuyer(){ 
		return this.skubuyer;
	}
	public String getSkuvendor(){ 
		return this.skuvendor;
	}
	public String getEan13(){ 
		return this.ean13;
	}
	public String getProductdescription(){ 
		return this.productdescription;
	}
	public String getCodeumc(){ 
		return this.codeumc;
	}
	public String getDescriptionumc(){ 
		return this.descriptionumc;
	}
	public String getDescriptionumb(){ 
		return this.descriptionumb;
	}
	public Integer getQuantityumc(){ 
		return this.quantityumc;
	}
	public Double getReceivedquantity(){ 
		return this.receivedquantity;
	}
	public Double getListcost(){ 
		return this.listcost;
	}
	public Double getFinalcost(){ 
		return this.finalcost;
	}
	public Long getReceptionid(){ 
		return this.receptionid;
	}
	public void setPosition(Integer position){ 
		this.position = position;
	}
	public void setSkubuyer(String skubuyer){ 
		this.skubuyer = skubuyer;
	}
	public void setSkuvendor(String skuvendor){ 
		this.skuvendor = skuvendor;
	}
	public void setEan13(String ean13){ 
		this.ean13 = ean13;
	}
	public void setProductdescription(String productdescription){ 
		this.productdescription = productdescription;
	}
	public void setCodeumc(String codeumc){ 
		this.codeumc = codeumc;
	}
	public void setDescriptionumc(String descriptionumc){ 
		this.descriptionumc = descriptionumc;
	}
	public void setDescriptionumb(String descriptionumb){ 
		this.descriptionumb = descriptionumb;
	}
	public void setQuantityumc(Integer quantityumc){ 
		this.quantityumc = quantityumc;
	}
	public void setReceivedquantity(Double receivedquantity){ 
		this.receivedquantity = receivedquantity;
	}
	public void setListcost(Double listcost){ 
		this.listcost = listcost;
	}
	public void setFinalcost(Double finalcost){ 
		this.finalcost = finalcost;
	}
	public void setReceptionid(Long receptionid){ 
		this.receptionid = receptionid;
	}
}
