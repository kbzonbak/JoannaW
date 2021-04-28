package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class VeVCDUnitaryDataChangeInitParamDTO implements Serializable{

	private Long ordernumber;
	private Long orderstatetypeid;
	private String date;
	private int filtertype;
	
	public Long getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(Long ordernumber) {
		this.ordernumber = ordernumber;
	}
	public Long getOrderstatetypeid() {
		return orderstatetypeid;
	}
	public void setOrderstatetypeid(Long orderstatetypeid) {
		this.orderstatetypeid = orderstatetypeid;
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
