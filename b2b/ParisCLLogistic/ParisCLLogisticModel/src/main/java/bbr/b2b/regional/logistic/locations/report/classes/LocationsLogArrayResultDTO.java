package bbr.b2b.regional.logistic.locations.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class LocationsLogArrayResultDTO extends BaseResultDTO {

	private LocationLogDTO[] locations = null;
	private Integer total;
	private PageInfoDTO pageInfo;

	public LocationLogDTO[] getLocations() {
		return locations;
	}

	public void setLocations(LocationLogDTO[] locations) {
		this.locations = locations;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}
}
