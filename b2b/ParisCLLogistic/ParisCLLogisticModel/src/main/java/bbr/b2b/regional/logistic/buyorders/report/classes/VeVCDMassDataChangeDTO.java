package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class VeVCDMassDataChangeDTO implements Serializable{

	private Long ordernumber;
	private String orderstatetypecode;
	private String date;
	private int filtertype;
	
	public Long getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}
	public String getOrderstatetypecode() {
		return orderstatetypecode;
	}
	public void setOrderstatetypecode(String orderstatetypecode) {
		this.orderstatetypecode = orderstatetypecode;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getFiltertype() {
		return filtertype;
	}
	public void setFiltertype(int filtertype) {
		this.filtertype = filtertype;
	}
	
}
