package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;

public class OrdersAndFileDownloadInfoResultDTO extends BaseResultDTO {

	private FileDownloadInfoResultDTO fileinfo = null;
	private OrderReportDataDTO[] orders = null;

	public FileDownloadInfoResultDTO getFileinfo() {
		return fileinfo;
	}

	public void setFileinfo(FileDownloadInfoResultDTO fileinfo) {
		this.fileinfo = fileinfo;
	}

	public OrderReportDataDTO[] getOrders() {
		return orders;
	}

	public void setOrders(OrderReportDataDTO[] orders) {
		this.orders = orders;
	}	
}
