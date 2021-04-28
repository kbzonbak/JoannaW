package bbr.esb.events.data.classes;

import bbr.common.adtclasses.classes.BaseResultDTO;

public class TicketEventResultDTO extends BaseResultDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6294646087271520715L;

	private TicketEventDataDTO[] ticketresults = null;

	public TicketEventDataDTO[] getTicketresults() {
		return ticketresults;
	}

	public void setTicketresults(TicketEventDataDTO[] ticketresults) {
		this.ticketresults = ticketresults;
	}
}
