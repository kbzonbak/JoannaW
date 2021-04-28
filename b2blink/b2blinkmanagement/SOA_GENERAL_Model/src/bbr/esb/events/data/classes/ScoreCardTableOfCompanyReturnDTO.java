package bbr.esb.events.data.classes;

import bbr.common.adtclasses.classes.BaseResultDTO;
import bbr.common.adtclasses.classes.PageInfoDTO;

public class ScoreCardTableOfCompanyReturnDTO extends BaseResultDTO	 {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ScoreCardTableDTO[] scorecards;
	private PageInfoDTO pageInfoDTO;
	
	public ScoreCardTableDTO[] getScorecards() {
		return scorecards;
	}
	public void setScorecards(ScoreCardTableDTO[] scorecards) {
		this.scorecards = scorecards;
	}
	public PageInfoDTO getPageInfoDTO() {
		return pageInfoDTO;
	}
	public void setPageInfoDTO(PageInfoDTO pageInfoDTO) {
		this.pageInfoDTO = pageInfoDTO;
	}
	
	
}
