package bbr.esb.events.data.classes;

import bbr.common.adtclasses.classes.BaseResultDTO;
import bbr.common.adtclasses.classes.PageInfoDTO;

public class TicketReportResultDTO extends BaseResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6294646087271520715L;

	TicketReportDataResultDTO[] tickets;
	private PageInfoDTO pageInfoDTO;
	
	

	public PageInfoDTO getPageInfoDTO() {
		return pageInfoDTO;
	}

	public void setPageInfoDTO(PageInfoDTO pageInfoDTO) {
		this.pageInfoDTO = pageInfoDTO;
	}

	public TicketReportDataResultDTO[] getTickets() {
		return tickets;
	}

	public void setTickets(TicketReportDataResultDTO[] tickets) {
		this.tickets = tickets;
	}

	
	
}


	
