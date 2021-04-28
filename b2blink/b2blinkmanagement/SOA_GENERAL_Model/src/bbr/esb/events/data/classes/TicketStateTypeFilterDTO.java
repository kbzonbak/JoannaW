package bbr.esb.events.data.classes;

import bbr.common.adtclasses.classes.BaseResultDTO;
import bbr.common.adtclasses.classes.ElementDTO;
import bbr.esb.events.data.interfaces.ITicketStateType;

public class TicketStateTypeFilterDTO extends BaseResultDTO {

	private static final long serialVersionUID = -7380338910614198185L;

	TicketStateTypeDTO[] ticketstatetype;

	public TicketStateTypeDTO[] getTicketstatetype() {
		return ticketstatetype;
	}

	public void setTicketstatetype(TicketStateTypeDTO[] ticketstatetype) {
		this.ticketstatetype = ticketstatetype;
	}
	
	
}
