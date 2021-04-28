package bbr.b2b.regional.logistic.deliveries.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;


public class CourierFileReportInitParamDTO implements Serializable {

	private LocalDateTime since;
	private LocalDateTime until;
	private int pageNumber;
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
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
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
