package bbr.b2b.logistic.ddcorders.report.classes;

import java.io.Serializable;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class DdcOrderDetailReportInitParamDTO implements Serializable {

	private Long ddcorderid;
	private String vendorcode;
	private int pageNumber;
	private int rows;
	private OrderCriteriaDTO[] orderby;

	public Long getDdcorderid() {
		return ddcorderid;
	}

	public void setDdcorderid(Long ddcorderid) {
		this.ddcorderid = ddcorderid;
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

}
