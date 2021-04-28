package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;

public class DirectOrderReprogDateDTO implements Serializable {

	private Long orderid;
	private Long ordernumber;
	private String reprogdate;
	private String dispatcheremail;
	private Integer rownumber;
	
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
	public String getReprogdate() {
		return reprogdate;
	}
	public void setReprogdate(String reprogdate) {
		this.reprogdate = reprogdate;
	}
	public String getDispatcheremail() {
		return dispatcheremail;
	}
	public void setDispatcheremail(String dispatcheremail) {
		this.dispatcheremail = dispatcheremail;
	}	
	public Integer getRownumber() {
		return rownumber;
	}
	public void setRownumber(Integer rownumber) {
		this.rownumber = rownumber;
	}
}
