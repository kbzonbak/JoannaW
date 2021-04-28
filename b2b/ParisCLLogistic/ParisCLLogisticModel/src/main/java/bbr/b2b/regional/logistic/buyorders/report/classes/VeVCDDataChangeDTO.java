package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class VeVCDDataChangeDTO implements Serializable{

	private Long orderid;
	private Long ordernumber;
	private Long neworderstatetypeid;
	private String newdate;
		
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
	public Long getNeworderstatetypeid() {
		return neworderstatetypeid;
	}
	public void setNeworderstatetypeid(Long neworderstatetypeid) {
		this.neworderstatetypeid = neworderstatetypeid;
	}
	public String getNewdate() {
		return newdate;
	}
	public void setNewdate(String newdate) {
		this.newdate = newdate;
	}
			
}
