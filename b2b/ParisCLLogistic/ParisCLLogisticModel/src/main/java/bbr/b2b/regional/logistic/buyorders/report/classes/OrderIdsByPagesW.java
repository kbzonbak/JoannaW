package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;


public class OrderIdsByPagesW extends BaseResultDTO{

	private Long[] orderIds;
	private int totalRows;
	
	public Long[] getOrderIds() {
		return orderIds;
	}
	public void setOrderIds(Long[] orderIds) {
		this.orderIds = orderIds;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}	
}
