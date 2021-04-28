package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class ProposedPackingListResultDTO extends BaseResultDTO {

	private ProposedPackingListDataDTO[] proposedpackinglist = null;

	public ProposedPackingListDataDTO[] getProposedpackinglist() {
		return proposedpackinglist;
	}

	public void setProposedpackinglist(ProposedPackingListDataDTO[] proposedpackinglist) {
		this.proposedpackinglist = proposedpackinglist;
	}		
}
