package bbr.b2b.logistic.buyorders.data.dto;

public class HistoryHeaderDTO {
	
	private Long orderid;
	private String ocnumber;
	private String octype;
	private Integer quantity;
	private Double ammount;
	private String client;
	private String emitted;
	private String receptiondate;
	private boolean valid;
	
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
	public String getOctype() {
		return octype;
	}
	public void setOctype(String octype) {
		this.octype = octype;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getAmmount() {
		return ammount;
	}
	public void setAmmount(Double ammount) {
		this.ammount = ammount;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
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
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	
}
