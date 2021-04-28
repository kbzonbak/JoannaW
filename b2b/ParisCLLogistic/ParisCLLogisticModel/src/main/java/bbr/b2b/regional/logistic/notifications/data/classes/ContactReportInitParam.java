package bbr.b2b.regional.logistic.notifications.data.classes;

import java.io.Serializable;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class ContactReportInitParam implements Serializable{

	private String vendorcode;
	private OrderCriteriaDTO[] order;
	private Integer pagenumber;
	private Integer pagerows;
	private Boolean isByFilter;

	public Boolean getIsByFilter() {
		return isByFilter;
	}

	public void setIsByFilter(Boolean isByFilter) {
		this.isByFilter = isByFilter;
	}

	public String getVendorcode() {
		return vendorcode;
	}

	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}

	public OrderCriteriaDTO[] getOrder() {
		return order;
	}

	public void setOrder(OrderCriteriaDTO[] order) {
		this.order = order;
	}

	public Integer getPagenumber() {
		return pagenumber;
	}

	public void setPagenumber(Integer pagenumber) {
		this.pagenumber = pagenumber;
	}

	public Integer getPagerows() {
		return pagerows;
	}

	public void setPagerows(Integer pagerows) {
		this.pagerows = pagerows;
	}

}
