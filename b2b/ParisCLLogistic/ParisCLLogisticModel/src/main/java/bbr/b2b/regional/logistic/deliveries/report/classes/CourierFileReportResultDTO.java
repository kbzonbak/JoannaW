package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.CourierFileDTO;

public class CourierFileReportResultDTO extends BaseResultDTO {

	private CourierFileDTO[] courierfilereport;
	private PageInfoDTO pageInfo;		
	
	public CourierFileDTO[] getCourierfilereport() {
		return courierfilereport;
	}
	public void setCourierfilereport(CourierFileDTO[] courierfilereport) {
		this.courierfilereport = courierfilereport;
	}
	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}	
}
