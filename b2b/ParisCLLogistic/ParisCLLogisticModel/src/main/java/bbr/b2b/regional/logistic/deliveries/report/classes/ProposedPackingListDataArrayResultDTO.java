package bbr.b2b.regional.logistic.deliveries.report.classes;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;

public class ProposedPackingListDataArrayResultDTO extends BaseResultDTO{

	private ProposedPackingListDataDTO[] packinglistdatas;
	private ProposedPackingListTotalDataDTO totals;
	private Boolean domesticvendor;
	private PageInfoDTO pageInfo;
		
	public ProposedPackingListDataDTO[] getPackinglistdatas() {
		return packinglistdatas;
	}

	public void setPackinglistdatas(ProposedPackingListDataDTO[] packinglistdatas) {
		this.packinglistdatas = packinglistdatas;
	}

	public ProposedPackingListTotalDataDTO getTotals() {
		return totals;
	}

	public void setTotals(ProposedPackingListTotalDataDTO totals) {
		this.totals = totals;
	}
	
	public Boolean getDomesticvendor() {
		return domesticvendor;
	}

	public void setDomesticvendor(Boolean domesticvendor) {
		this.domesticvendor = domesticvendor;
	}

	public PageInfoDTO getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfoDTO pageInfo) {
		this.pageInfo = pageInfo;
	}
}
