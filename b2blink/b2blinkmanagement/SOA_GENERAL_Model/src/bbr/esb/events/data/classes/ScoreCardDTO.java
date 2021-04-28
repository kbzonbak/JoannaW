package bbr.esb.events.data.classes;

import bbr.common.adtclasses.classes.BaseResultDTO;

public class ScoreCardDTO extends BaseResultDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		
	private Integer totalsucess;
	
	private Integer totalerror;
	
	private Integer totalinprogress;
	
	SiteProgress[] siteprogressarray;

	public Integer getTotalsucess() {
		return totalsucess;
	}

	public void setTotalsucess(Integer totalsucesses) {
		this.totalsucess = totalsucesses;
	}

	public Integer getTotalerror() {
		return totalerror;
	}

	public void setTotalerror(Integer totalerror) {
		this.totalerror = totalerror;
	}

	public Integer getTotalinprogress() {
		return totalinprogress;
	}

	public void setTotalinprogress(Integer totalinprogress) {
		this.totalinprogress = totalinprogress;
	}

	public SiteProgress[] getSiteprogressarray() {
		return siteprogressarray;
	}

	public void setSiteprogressarray(SiteProgress[] siteprogressarray) {
		this.siteprogressarray = siteprogressarray;
	}

	
}
