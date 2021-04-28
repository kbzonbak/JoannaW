package bbr.b2b.logistic.buyorders.data.dto;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class OrderReportResultDTO extends BaseResultDTO {

	private OrderReportDataDTO[] orders;
	private Integer totalreg;
	private PageInfoDTO pageInfo;

	public OrderReportDataDTO[] getOrders() {
		return orders;
	}

	public void setOrders(OrderReportDataDTO[] orders) {
		this.orders = orders;
	}

	public Integer getTotalreg() {
		return totalreg;
	}

	public void setTotalreg(Integer totalreg) {
		this.totalreg = totalreg;
	}

	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}

}
