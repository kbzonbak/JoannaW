package bbr.b2b.regional.logistic.report.classes;

import java.io.Serializable;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;

public class AssignedDataInitParamsDTO implements Serializable {

	private String searchstring;
	private int pagenumber;
	private int rows;
	private boolean getPageInfo;
	private boolean isPaginated;
	private OrderCriteriaDTO[] order;
	
	public String getSearchstring() {
		return searchstring;
	}
	public void setSearchstring(String searchstring) {
		this.searchstring = searchstring;
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
	public boolean isGetPageInfo() {
		return getPageInfo;
	}
	public void setGetPageInfo(boolean getPageInfo) {
		this.getPageInfo = getPageInfo;
	}
	public boolean isPaginated() {
		return isPaginated;
	}
	public void setPaginated(boolean isPaginated) {
		this.isPaginated = isPaginated;
	}
	public OrderCriteriaDTO[] getOrder() {
		return order;
	}
	public void setOrder(OrderCriteriaDTO[] order) {
		this.order = order;
	}	
}