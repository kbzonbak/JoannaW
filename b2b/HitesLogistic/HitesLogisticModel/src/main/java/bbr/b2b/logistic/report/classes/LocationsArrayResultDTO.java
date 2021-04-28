package bbr.b2b.logistic.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class LocationsArrayResultDTO extends BaseResultDTO {

	private LocationDTO[] locations = null;
	private Integer total;

	public LocationDTO[] getLocations() {
		return locations;
	}

	public void setLocations(LocationDTO[] locations) {
		this.locations = locations;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

}
