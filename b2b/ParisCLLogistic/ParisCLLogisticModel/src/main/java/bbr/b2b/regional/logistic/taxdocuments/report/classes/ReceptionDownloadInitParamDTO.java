package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import java.io.Serializable;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class ReceptionDownloadInitParamDTO implements Serializable {

	private String vendorcode;
	private String vendortradename;
	private Long[] taxdocumentids;
	private OrderCriteriaDTO[] orderby;
	
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public String getVendortradename() {
		return vendortradename;
	}
	public void setVendortradename(String vendortradename) {
		this.vendortradename = vendortradename;
	}
	public Long[] getTaxdocumentids() {
		return taxdocumentids;
	}
	public void setTaxdocumentids(Long[] taxdocumentids) {
		this.taxdocumentids = taxdocumentids;
	}
	public OrderCriteriaDTO[] getOrderby() {
		return orderby;
	}
	public void setOrderby(OrderCriteriaDTO[] orderby) {
		this.orderby = orderby;
	}
}
