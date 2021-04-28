package bbr.b2b.regional.logistic.taxdocuments.interfaces;

import java.util.List;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.taxdocuments.data.wrappers.DOTaxDocumentStateW;
import bbr.b2b.regional.logistic.taxdocuments.entities.DOTaxDocumentState;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentStateCommentDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentStateDTO;

public interface IDOTaxDocumentStateServer extends IElementServer<DOTaxDocumentState, DOTaxDocumentStateW> {

	DOTaxDocumentStateW addDOTaxDocumentState(DOTaxDocumentStateW dotaxdocumentstate) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DOTaxDocumentStateW[] getDOTaxDocumentStates() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DOTaxDocumentStateW updateDOTaxDocumentState(DOTaxDocumentStateW dotaxdocumentstate) throws AccessDeniedException, OperationFailedException, NotFoundException;
	List<DOTaxDocumentStateCommentDTO> getCommentsByDOTaxDocumentTicket(Long ticketid);
	DOTaxDocumentStateDTO[] getDOTaxDocumentStateReportByDOTaxDocuments(Long[] dotaxdocumentids, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException;
	int countDOTaxDocumentStateReportByDOTaxDocuments(Long[] dotaxdocumentids) throws AccessDeniedException;
}