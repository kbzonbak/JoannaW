package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class OrderDiscountDTO implements Serializable {

	private Long id;
	private Long orderid;
	private String code;
	private Double value;
	private Boolean percentage;
	private String comment;

	public String getCode(){ 
		return this.code;
	}
	public Double getValue(){ 
		return this.value;
	}
	public Boolean getPercentage(){ 
		return this.percentage;
	}
	public String getComment(){ 
		return this.comment;
	}
	public void setCode(String code){ 
		this.code = code;
	}
	public void setValue(Double value){ 
		this.value = value;
	}
	public void setPercentage(Boolean percentage){ 
		this.percentage = percentage;
	}
	public void setComment(String comment){ 
		this.comment = comment;
	}
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

}
