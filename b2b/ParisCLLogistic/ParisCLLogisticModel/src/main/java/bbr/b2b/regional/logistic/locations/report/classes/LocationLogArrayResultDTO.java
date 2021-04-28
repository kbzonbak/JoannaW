package bbr.b2b.regional.logistic.locations.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class LocationLogArrayResultDTO extends BaseResultDTO {

	private LocationLogDTO[] locations = null;

	public LocationLogDTO[] getLocations() {
		return locations;
	}

	public void setLocations(LocationLogDTO[] locations) {
		this.locations = locations;
	}	
}
