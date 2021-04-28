package bbr.esb.events.interfaces;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.adtclasses.interfaces.IElementServer;
import bbr.esb.events.data.classes.TicketStateDTO;
import bbr.esb.events.entities.TicketState;

public interface ITicketStateServer extends IElementServer<TicketState, TicketStateDTO> {

	TicketStateDTO addTicketState(TicketStateDTO ticketstate) throws AccessDeniedException, OperationFailedException, NotFoundException;

	TicketStateDTO[] getTicketStates() throws AccessDeniedException, OperationFailedException, NotFoundException;

	TicketStateDTO updateTicketState(TicketStateDTO ticketstate) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
