package bbr.b2b.regional.logistic.evaluations.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;


public class EvaluationDetailReportInitParamDTO implements Serializable {

	private LocalDateTime since;
	private LocalDateTime until;
	private String vendorcode;
	private int pagenumber;
	private int rows;	
	private OrderCriteriaDTO[] orderby;
	
	public LocalDateTime getSince() {
		return since;
	}
	public void setSince(LocalDateTime since) {
		this.since = since;
	}
	public LocalDateTime getUntil() {
		return until;
	}
	public void setUntil(LocalDateTime until) {
		this.until = until;
	}	
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public int getPagenumber() {
		return pagenumber;
	}
	public void setPagenumber(int pagenumber) {
		this.pagenumber = pagenumber;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public OrderCriteriaDTO[] getOrderby() {
		return orderby;
	}
	public void setOrderby(OrderCriteriaDTO[] orderby) {
		this.orderby = orderby;
	}	
}
