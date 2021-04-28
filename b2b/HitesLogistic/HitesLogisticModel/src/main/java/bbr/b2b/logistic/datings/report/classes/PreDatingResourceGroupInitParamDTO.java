package bbr.b2b.logistic.datings.report.classes;

import java.io.Serializable;

public class PreDatingResourceGroupInitParamDTO implements Serializable {

	private PreDatingResourceGroupResultDTO predatingresource;
	private ReserveDetailDTO[] details;

	public PreDatingResourceGroupResultDTO getPredatingresource() {
		return predatingresource;
	}

	public void setPredatingresource(PreDatingResourceGroupResultDTO predatingresource) {
		this.predatingresource = predatingresource;
	}

	public ReserveDetailDTO[] getDetails() {
		return details;
	}

	public void setDetails(ReserveDetailDTO[] details) {
		this.details = details;
	}

}