package bbr.b2b.regional.logistic.vendors.report.classes;

import java.io.Serializable;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class VendorWithoutInvoiceValidationInitParamDTO implements Serializable {

	private OrderCriteriaDTO[] orderby;

	public OrderCriteriaDTO[] getOrderby() {
		return orderby;
	}

	public void setOrderby(OrderCriteriaDTO[] orderby) {
		this.orderby = orderby;
	}
}
