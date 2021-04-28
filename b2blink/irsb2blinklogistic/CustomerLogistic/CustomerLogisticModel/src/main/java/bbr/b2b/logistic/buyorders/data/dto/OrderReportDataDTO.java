package bbr.b2b.logistic.buyorders.data.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class OrderReportDataDTO implements Serializable {

	private Long orderid;
	private Long ordernumber;
	private String ordertype;
	private String soastatetype;
	private String cliente;
	private String emitted;
	private String receptiondate;
	private Double totalamount;
	private Integer totalunits;
	private Boolean valid;
	private Boolean history;
	private Boolean complete;
	
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Long getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}
	public String getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}
	public String getSoastatetype() {
		return soastatetype;
	}
	public void setSoastatetype(String soastatetype) {
		this.soastatetype = soastatetype;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
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
	public Double getTotalamount() {
		return totalamount;
	}
	public void setTotalamount(Double totalamount) {
		this.totalamount = totalamount;
	}
	public Integer getTotalunits() {
		return totalunits;
	}
	public void setTotalunits(Integer totalunits) {
		this.totalunits = totalunits;
	}
	public Boolean getValid() {
		return valid;
	}
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	public Boolean getHistory() {
		return history;
	}
	public void setHistory(Boolean history) {
		this.history = history;
	}
	public Boolean getComplete() {
		return complete;
	}
	public void setComplete(Boolean complete) {
		this.complete = complete;
	}

}
