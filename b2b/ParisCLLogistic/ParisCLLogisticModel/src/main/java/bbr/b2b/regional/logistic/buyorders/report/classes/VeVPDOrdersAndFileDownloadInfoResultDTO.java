package bbr.b2b.regional.logistic.buyorders.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;

public class VeVPDOrdersAndFileDownloadInfoResultDTO extends BaseResultDTO {

	private FileDownloadInfoResultDTO fileinfo = null;
	private VeVPDOrderReportDataDTO[] orders = null;

	public FileDownloadInfoResultDTO getFileinfo() {
		return fileinfo;
	}

	public void setFileinfo(FileDownloadInfoResultDTO fileinfo) {
		this.fileinfo = fileinfo;
	}

	public VeVPDOrderReportDataDTO[] getOrders() {
		return orders;
	}

	public void setOrders(VeVPDOrderReportDataDTO[] orders) {
		this.orders = orders;
	}	
}
