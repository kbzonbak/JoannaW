package bbr.b2b.regional.logistic.taxdocuments.interfaces;

import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.taxdocuments.data.wrappers.DOTaxDocumentTicketW;
import bbr.b2b.regional.logistic.taxdocuments.entities.DOTaxDocumentTicket;

public interface IDOTaxDocumentTicketServer extends IElementServer<DOTaxDocumentTicket, DOTaxDocumentTicketW> {

	public DOTaxDocumentTicketW addDOTaxDocumentTicket(DOTaxDocumentTicketW dotaxdocumentstatetype) throws OperationFailedException, NotFoundException;
	public DOTaxDocumentTicketW[] getDOTaxDocumentTicket() throws OperationFailedException, NotFoundException;
	public DOTaxDocumentTicketW updateDOTaxDocumentTicket(DOTaxDocumentTicketW dotaxdocumentstatetype) throws OperationFailedException, NotFoundException;
	public DOTaxDocumentTicketW[] getPendingTickets();
}
