package bbr.b2b.regional.logistic.vendors.report.classes;

import java.io.Serializable;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class VendorRankingDetailInitParamDTO implements Serializable{

	private String vendorcode;
	private Long rankingid;
	private int pageNumber;
	private int rows;
	private OrderCriteriaDTO[] orderby;
	
	public String getVendorcode() {
		return vendorcode;
	}
	public void setVendorcode(String vendorcode) {
		this.vendorcode = vendorcode;
	}
	public Long getRankingid() {
		return rankingid;
	}
	public void setRankingid(Long rankingid) {
		this.rankingid = rankingid;
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
