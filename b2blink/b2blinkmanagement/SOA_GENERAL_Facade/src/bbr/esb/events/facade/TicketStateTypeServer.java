package bbr.esb.events.facade;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.base.facade.ElementServer;
import bbr.esb.events.data.classes.TicketStateTypeDTO;
import bbr.esb.events.entities.TicketStateType;

@Stateless(name = "servers/events/TicketStateTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TicketStateTypeServer extends ElementServer<TicketStateType, TicketStateTypeDTO> implements TicketStateTypeServerLocal {

	public TicketStateTypeDTO addTicketStateType(TicketStateTypeDTO ticketstatetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (TicketStateTypeDTO) addElement(ticketstatetype);
	}

	public TicketStateTypeDTO[] getTicketStateTypes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (TicketStateTypeDTO[]) getIdentifiables();
	}

	public TicketStateTypeDTO updateTicketStateType(TicketStateTypeDTO ticketstatetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (TicketStateTypeDTO) updateElement(ticketstatetype);
	}

	@Override
	protected void copyRelationsEntityToWrapper(TicketStateType entity, TicketStateTypeDTO wrapper) {
	}

	@Override
	protected void copyRelationsWrapperToEntity(TicketStateType entity, TicketStateTypeDTO wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "TicketStateType";
	}

	@Override
	protected void setEntityname() {
		entityname = "TicketStateType";
	}
}
