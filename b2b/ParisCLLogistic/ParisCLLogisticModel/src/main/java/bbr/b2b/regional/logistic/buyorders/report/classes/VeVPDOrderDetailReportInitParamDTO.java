package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;
import java.util.List;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class VeVPDOrderDetailReportInitParamDTO implements Serializable {

	private Long orderid;

	private List<Long> ordersId;

	private String vendorcode;

	private int pageNumber;

	private int rows;

	private OrderCriteriaDTO[] orderby;

	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	public String getVendorcode() {
		return vendorcode;
	}

	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
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

	public List<Long> getOrdersId() {
		return ordersId;
	}

	public void setOrdersId(List<Long> ordersId) {
		this.ordersId = ordersId;
	}

}
