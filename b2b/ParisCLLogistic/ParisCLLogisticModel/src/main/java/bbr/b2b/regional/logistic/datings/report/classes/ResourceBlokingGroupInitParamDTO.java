package bbr.b2b.regional.logistic.datings.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class ResourceBlokingGroupInitParamDTO extends BaseResultDTO {

	private ResourceBlockingGroupDTO blockingroup;
	private ReserveDetailDTO[] details;

	public ResourceBlockingGroupDTO getBlockingroup() {
		return blockingroup;
	}

	public void setBlockingroup(ResourceBlockingGroupDTO blockingroup) {
		this.blockingroup = blockingroup;
	}

	public ReserveDetailDTO[] getDetails() {
		return details;
	}

	public void setDetails(ReserveDetailDTO[] details) {
		this.details = details;
	}

}
