package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;

public class VeVCDOrdersAndFileDownloadInfoResultDTO extends BaseResultDTO {

	private FileDownloadInfoResultDTO fileinfo = null;
	private VeVCDOrderReportDataDTO[] orders = null;

	public FileDownloadInfoResultDTO getFileinfo() {
		return fileinfo;
	}

	public void setFileinfo(FileDownloadInfoResultDTO fileinfo) {
		this.fileinfo = fileinfo;
	}

	public VeVCDOrderReportDataDTO[] getOrders() {
		return orders;
	}

	public void setOrders(VeVCDOrderReportDataDTO[] orders) {
		this.orders = orders;
	}
}
