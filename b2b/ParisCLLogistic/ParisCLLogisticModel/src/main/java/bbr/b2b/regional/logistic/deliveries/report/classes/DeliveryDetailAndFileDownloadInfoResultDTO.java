package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;

public class DeliveryDetailAndFileDownloadInfoResultDTO extends BaseResultDTO{

	private FileDownloadInfoResultDTO fileinfo = null;
	private DeliveryDetailReportDataDTO[] deliverydetail = null;
		
	public FileDownloadInfoResultDTO getFileinfo() {
		return fileinfo;
	}
	public void setFileinfo(FileDownloadInfoResultDTO fileinfo) {
		this.fileinfo = fileinfo;
	}
	public DeliveryDetailReportDataDTO[] getDeliverydetail() {
		return deliverydetail;
	}
	public void setDeliverydetail(DeliveryDetailReportDataDTO[] deliverydetail) {
		this.deliverydetail = deliverydetail;
	}
	
	
}
