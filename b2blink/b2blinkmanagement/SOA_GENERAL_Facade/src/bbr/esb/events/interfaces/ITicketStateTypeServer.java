package bbr.esb.events.interfaces;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.adtclasses.interfaces.IElementServer;
import bbr.esb.events.data.classes.TicketStateTypeDTO;
import bbr.esb.events.entities.TicketStateType;

public interface ITicketStateTypeServer extends IElementServer<TicketStateType, TicketStateTypeDTO> {

	TicketStateTypeDTO addTicketStateType(TicketStateTypeDTO ticketstatetype) throws AccessDeniedException, OperationFailedException, NotFoundException;

	TicketStateTypeDTO[] getTicketStateTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;

	TicketStateTypeDTO updateTicketStateType(TicketStateTypeDTO ticketstatetype) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
