package bbr.b2b.regional.logistic.fillrate.report.classes;

import java.io.Serializable;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class FillRateDetailInitParamDTO implements Serializable{

	private String vendorcode;
	private Long departmentid;
	private Long fillrateid;
	private int pageNumber;
	private int rows;
	private OrderCriteriaDTO[] orderby;

	public String getVendorcode() {
		return vendorcode;
	}

	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	
	public Long getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(Long departmentid) {
		this.departmentid = departmentid;
	}

	public Long getFillrateid() {
		return fillrateid;
	}

	public void setFillrateid(Long fillrateid) {
		this.fillrateid = fillrateid;
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
