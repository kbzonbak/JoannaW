package bbr.b2b.logistic.buyorders.data.dto;

public class HistoryDetailDTO {
	
	Long orderid;
	String 	ocnumber;
	String emitted;
	String receptiondate;
	Double ammount;
	Integer quantity;
	
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public String getOcnumber() {
		return ocnumber;
	}
	public void setOcnumber(String ocnumber) {
		this.ocnumber = ocnumber;
	}
	public String getEmitted() {
		return emitted;
	}
	public void setEmitted(String emitted) {
		this.emitted = emitted;
	}
	public String getReceptiondate() {
		return receptiondate;
	}
	public void setReceptiondate(String receptiondate) {
		this.receptiondate = receptiondate;
	}
	public Double getAmmount() {
		return ammount;
	}
	public void setAmmount(Double ammount) {
		this.ammount = ammount;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
}
