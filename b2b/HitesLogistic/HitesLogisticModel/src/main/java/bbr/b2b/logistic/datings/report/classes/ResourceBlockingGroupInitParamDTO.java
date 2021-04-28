package bbr.b2b.logistic.datings.report.classes;

import java.io.Serializable;

public class ResourceBlockingGroupInitParamDTO implements Serializable {

	private ResourceBlockingGroupResultDTO blockingroup;
	private ReserveDetailDTO[] details;

	public ResourceBlockingGroupResultDTO getBlockingroup() {
		return blockingroup;
	}

	public void setBlockingroup(ResourceBlockingGroupResultDTO blockingroup) {
		this.blockingroup = blockingroup;
	}

	public ReserveDetailDTO[] getDetails() {
		return details;
	}

	public void setDetails(ReserveDetailDTO[] details) {
		this.details = details;
	}

}
