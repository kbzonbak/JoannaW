package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import java.io.Serializable;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class ReceptionDetailInitParamDTO implements Serializable {

	private String vendorcode;
	private Long taxdocumentid;
	private int pagenumber;
	private int rows;	
	private OrderCriteriaDTO[] orderby;
	
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public Long getTaxdocumentid() {
		return taxdocumentid;
	}
	public void setTaxdocumentid(Long taxdocumentid) {
		this.taxdocumentid = taxdocumentid;
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
