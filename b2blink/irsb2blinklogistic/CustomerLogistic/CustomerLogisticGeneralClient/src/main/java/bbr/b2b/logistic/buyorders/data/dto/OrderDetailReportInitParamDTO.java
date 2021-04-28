package bbr.b2b.logistic.buyorders.data.dto;

import java.io.Serializable;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class OrderDetailReportInitParamDTO implements Serializable {

	private Long ocnumber;
	private String vendorcode;
	private String clientcode;
	private int pageNumber;
	private int rows;
	private OrderCriteriaDTO[] orderby;
	private boolean byfilter;
	private boolean paginated;
	
	public Long getOcnumber() {
		return ocnumber;
	}
	public void setOcnumber(Long ocnumber) {
		this.ocnumber = ocnumber;
	}
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getClientcode() {
		return clientcode;
	}
	public void setClientcode(String clientcode) {
		this.clientcode = clientcode;
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
	public boolean isByfilter() {
		return byfilter;
	}
	public void setByfilter(boolean byfilter) {
		this.byfilter = byfilter;
	}
	public boolean isPaginated() {
		return paginated;
	}
	public void setPaginated(boolean paginated) {
		this.paginated = paginated;
	}

	
}
