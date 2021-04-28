package bbr.b2b.logistic.customer.data.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IIdentifiable;

public interface IDetail extends IIdentifiable {

	public String getSkuvendor();
	public String getEan13();
	public String getEan13_buyer();
	public String getProduct_description();
	public String getCode_umc();
	public String getDescription_umc();
	public String getCode_umb();
	public String getDescription_umb();
	public Integer getUmb_x_umc();
	public Integer getQuantity_umc();
	public Double getList_cost();
	public Double getFinal_cost();
	public Double getList_price();
	public String getTolerance();
	public Date getProduct_delivery_date();
	public String getObservation();
	public Double getFreight_cost();
	public Double getFreight_weight();
	public String getNumref1();
	public String getNumref2();
	public String getItem();
	public String getEan1();
	public String getEan2();
	public String getEan3();
	public String getStylecode();
	public String getStyledescription();
	public String getStylebrand();
	public Long getInnerpack();
	public void setSkuvendor(String sku_vendor);
	public void setEan13(String ean13);
	public void setEan13_buyer(String ean13_buyer);
	public void setProduct_description(String product_description);
	public void setCode_umc(String code_umc);
	public void setNumref1(String numref1);
	public void setNumref2(String numref2);
	public void setDescription_umc(String description_umc);
	public void setCode_umb(String code_umb);
	public void setDescription_umb(String description_umb);
	public void setUmb_x_umc(Integer umb_x_umc);
	public void setQuantity_umc(Integer quantity_umc);
	public void setList_cost(Double list_cost);
	public void setFinal_cost(Double final_cost);
	public void setList_price(Double list_price);
	public void setTolerance(String tolerance);
	public void setProduct_delivery_date(Date product_delivery_date);
	public void setObservation(String observation);
	public void setFreight_cost(Double freight_cost);
	public void setFreight_weight(Double freight_weight);
	public void setItem(String item);
	public void setEan1(String ean1);
	public void setEan2(String ean2);
	public void setEan3(String ean3);
	public void setStylecode(String stylecode);
	public void setStyledescription(String styledescription);
	public void setStylebrand(String stylebrand);
	public Double getCostaftertaxes();
	public void setCostaftertaxes(Double costaftertaxes);
	public void setInnerpack(Long innerpack);
	
}
