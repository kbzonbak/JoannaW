package bbr.esb.events.interfaces;

import java.text.ParseException;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.adtclasses.interfaces.IElementServer;
import bbr.esb.events.data.classes.TicketEventDTO;
import bbr.esb.events.data.classes.TicketReportInitParamDTO;
import bbr.esb.events.data.classes.TicketReportResultDTO;
import bbr.esb.events.entities.TicketEvent;

public interface ITicketEventServer extends IElementServer<TicketEvent, TicketEventDTO> {

	public TicketEventDTO addTicketEvent(TicketEventDTO event) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void deleteTicketEvent(Long ticketEventid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public TicketEventDTO getTicketEventByPK(Long ticketEventid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public TicketEventDTO updateTicketEvent(TicketEventDTO event) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public int getCountTicketReport(TicketReportInitParamDTO initparam) throws ParseException;
	
	public TicketReportResultDTO getTicketReport(TicketReportInitParamDTO initparam) throws ParseException ;
}
