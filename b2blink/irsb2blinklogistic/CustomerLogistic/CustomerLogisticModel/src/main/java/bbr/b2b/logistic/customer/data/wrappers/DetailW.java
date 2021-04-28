package bbr.b2b.logistic.customer.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.logistic.customer.data.interfaces.IDetail;

public class DetailW extends ElementDTO implements IDetail {

	private Integer position;
	private String skubuyer;
	private String skuvendor;
	private String ean13;
	private String ean13_buyer;
	private String product_description;
	private String code_umc;
	private String description_umc;
	private String code_umb;
	private String description_umb;
	private Integer umb_x_umc;
	private Integer quantity_umc;
	private Double list_cost;
	private Double final_cost;
	private Double list_price;
	private String tolerance;
	private Date product_delivery_date;
	private String observation;
	private Double freight_cost;
	private Double freight_weight;
	private Long orderid;
	private String item;
	private String ean1;
	private String ean2;
	private String ean3;
	private String stylecode;
	private String styledescription;
	private String stylebrand;
	private String numref1;
	private String numref2;
	private Double costaftertaxes;
	private Long innerpack;
	
	public Integer getPosition(){ 
		return this.position;
	}
	public String getSkuvendor(){ 
		return this.skuvendor;
	}
	public String getEan13(){ 
		return this.ean13;
	}
	public String getEan13_buyer(){ 
		return this.ean13_buyer;
	}
	public String getProduct_description(){ 
		return this.product_description;
	}
	public String getCode_umc(){ 
		return this.code_umc;
	}
	public String getDescription_umc(){ 
		return this.description_umc;
	}
	public String getCode_umb(){ 
		return this.code_umb;
	}
	public String getDescription_umb(){ 
		return this.description_umb;
	}
	public Integer getUmb_x_umc(){ 
		return this.umb_x_umc;
	}
	public Integer getQuantity_umc(){ 
		return this.quantity_umc;
	}
	public Double getList_cost(){ 
		return this.list_cost;
	}
	public Double getFinal_cost(){ 
		return this.final_cost;
	}
	public Double getList_price(){ 
		return this.list_price;
	}
	public String getTolerance(){ 
		return this.tolerance;
	}
	public Date getProduct_delivery_date(){ 
		return this.product_delivery_date;
	}
	public String getObservation(){ 
		return this.observation;
	}
	public Double getFreight_cost(){ 
		return this.freight_cost;
	}
	public Double getFreight_weight(){ 
		return this.freight_weight;
	}
	public Long getOrderid(){ 
		return this.orderid;
	}
	public String getSkubuyer(){ 
		return this.skubuyer;
	}
	public void setPosition(Integer position){ 
		this.position = position;
	}
	public void setSkuvendor(String skuvendor){ 
		this.skuvendor = skuvendor;
	}
	public void setEan13(String ean13){ 
		this.ean13 = ean13;
	}
	public void setEan13_buyer(String ean13_buyer){ 
		this.ean13_buyer = ean13_buyer;
	}
	public void setProduct_description(String product_description){ 
		this.product_description = product_description;
	}
	public void setCode_umc(String code_umc){ 
		this.code_umc = code_umc;
	}
	public void setDescription_umc(String description_umc){ 
		this.description_umc = description_umc;
	}
	public void setCode_umb(String code_umb){ 
		this.code_umb = code_umb;
	}
	public void setDescription_umb(String description_umb){ 
		this.description_umb = description_umb;
	}
	public void setUmb_x_umc(Integer umb_x_umc){ 
		this.umb_x_umc = umb_x_umc;
	}
	public void setQuantity_umc(Integer quantity_umc){ 
		this.quantity_umc = quantity_umc;
	}
	public void setList_cost(Double list_cost){ 
		this.list_cost = list_cost;
	}
	public void setFinal_cost(Double final_cost){ 
		this.final_cost = final_cost;
	}
	public void setList_price(Double list_price){ 
		this.list_price = list_price;
	}
	public void setTolerance(String tolerance){ 
		this.tolerance = tolerance;
	}
	public void setProduct_delivery_date(Date product_delivery_date){ 
		this.product_delivery_date = product_delivery_date;
	}
	public void setObservation(String observation){ 
		this.observation = observation;
	}
	public void setFreight_cost(Double freight_cost){ 
		this.freight_cost = freight_cost;
	}
	public void setFreight_weight(Double freight_weight){ 
		this.freight_weight = freight_weight;
	}
	public void setOrderid(Long orderid){ 
		this.orderid = orderid;
	}
	public void setSkubuyer(String skubuyer){ 
		this.skubuyer = skubuyer;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getEan1() {
		return ean1;
	}
	public void setEan1(String ean1) {
		this.ean1 = ean1;
	}
	public String getEan2() {
		return ean2;
	}
	public void setEan2(String ean2) {
		this.ean2 = ean2;
	}
	public String getEan3() {
		return ean3;
	}
	public void setEan3(String ean3) {
		this.ean3 = ean3;
	}
	public String getStylecode() {
		return stylecode;
	}
	public void setStylecode(String stylecode) {
		this.stylecode = stylecode;
	}
	public String getStyledescription() {
		return styledescription;
	}
	public void setStyledescription(String styledescription) {
		this.styledescription = styledescription;
	}
	public String getStylebrand() {
		return stylebrand;
	}
	public void setStylebrand(String stylebrand) {
		this.stylebrand = stylebrand;
	}
	public String getNumref1() {
		return numref1;
	}
	public void setNumref1(String numref1) {
		this.numref1 = numref1;
	}
	public String getNumref2() {
		return numref2;
	}
	public void setNumref2(String numref2) {
		this.numref2 = numref2;
	}
	public Double getCostaftertaxes() {
		return costaftertaxes;
	}
	public void setCostaftertaxes(Double costaftertaxes) {
		this.costaftertaxes = costaftertaxes;
	}
	public Long getInnerpack() {
		return innerpack;
	}
	public void setInnerpack(Long innerpack) {
		this.innerpack = innerpack;
	}
}
