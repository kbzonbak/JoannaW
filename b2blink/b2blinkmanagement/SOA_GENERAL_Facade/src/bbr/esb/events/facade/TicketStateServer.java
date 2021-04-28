package bbr.esb.events.facade;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.base.facade.ElementServer;
import bbr.esb.events.data.classes.TicketStateDTO;
import bbr.esb.events.entities.TicketEvent;
import bbr.esb.events.entities.TicketState;
import bbr.esb.events.entities.TicketStateType;

@Stateless(name = "servers/events/TicketStateServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TicketStateServer extends ElementServer<TicketState, TicketStateDTO> implements TicketStateServerLocal {

	@EJB
	TicketEventServerLocal ticketserver;

	@EJB
	TicketStateTypeServerLocal ticketstatetypeserver;

	public TicketStateDTO addTicketState(TicketStateDTO ticketstate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (TicketStateDTO) addElement(ticketstate);
	}

	public TicketStateDTO[] getTicketStates() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (TicketStateDTO[]) getIdentifiables();
	}

	public TicketStateDTO updateTicketState(TicketStateDTO ticketstate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (TicketStateDTO) updateElement(ticketstate);
	}

	@Override
	protected void copyRelationsEntityToWrapper(TicketState entity, TicketStateDTO wrapper) {
		wrapper.setTicketkey(entity.getTicketevent() != null ? new Long(entity.getTicketevent().getId()) : new Long(0));
		wrapper.setTicketstatetypekey(entity.getTicketstatetype() != null ? new Long(entity.getTicketstatetype().getId()) : new Long(0));
	}

	@Override
	protected void copyRelationsWrapperToEntity(TicketState entity, TicketStateDTO wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getTicketkey() != null && wrapper.getTicketkey() > 0) {
			TicketEvent ticketevent = ticketserver.getReferenceById(wrapper.getTicketkey());
			if (ticketevent != null) {
				entity.setTicketevent(ticketevent);
			}
		}
		if (wrapper.getTicketstatetypekey() != null && wrapper.getTicketstatetypekey() > 0) {
			TicketStateType ticketstatetype = ticketstatetypeserver.getReferenceById(wrapper.getTicketstatetypekey());
			if (ticketstatetype != null) {
				entity.setTicketstatetype(ticketstatetype);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "TicketState";
	}

	@Override
	protected void setEntityname() {
		entityname = "TicketState";
	}
}
