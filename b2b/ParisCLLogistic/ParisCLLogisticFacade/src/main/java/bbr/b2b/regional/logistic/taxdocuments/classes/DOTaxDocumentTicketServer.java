package bbr.b2b.regional.logistic.taxdocuments.classes;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.taxdocuments.data.wrappers.DOTaxDocumentTicketW;
import bbr.b2b.regional.logistic.taxdocuments.entities.DOTaxDocumentTicket;

@Stateless(name = "servers/taxdocuments/DOTaxDocumentTicketServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DOTaxDocumentTicketServer extends LogisticElementServer<DOTaxDocumentTicket, DOTaxDocumentTicketW> implements DOTaxDocumentTicketServerLocal {

	public DOTaxDocumentTicketW addDOTaxDocumentTicket(DOTaxDocumentTicketW dotaxdocumentticket) throws OperationFailedException, NotFoundException {
		return (DOTaxDocumentTicketW) addElement(dotaxdocumentticket);
	}
	
	public DOTaxDocumentTicketW[] getDOTaxDocumentTicket() throws OperationFailedException, NotFoundException {
		return (DOTaxDocumentTicketW[]) getIdentifiables();
	}
	
	public DOTaxDocumentTicketW updateDOTaxDocumentTicket(DOTaxDocumentTicketW dotaxdocumentticket) throws OperationFailedException, NotFoundException {
		return (DOTaxDocumentTicketW) updateElement(dotaxdocumentticket);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DOTaxDocumentTicket entity, DOTaxDocumentTicketW wrapper) {

	}
	
	@Override
	protected void copyRelationsWrapperToEntity(DOTaxDocumentTicket entity, DOTaxDocumentTicketW wrapper) throws OperationFailedException, NotFoundException {
		
	}
	
	public DOTaxDocumentTicketW[] getPendingTickets(){
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.taxdocuments.entities.DOTaxDocumentTicket.getPendingTickets");
		query.setResultTransformer(new LowerCaseResultTransformer(DOTaxDocumentTicketW.class));
		
		List list = query.list();
		DOTaxDocumentTicketW[] result = (DOTaxDocumentTicketW[]) list.toArray(new DOTaxDocumentTicketW[list.size()]);		
		
		return result;
		
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DOTaxDocumentTicket";
	}
	
	@Override
	protected void setEntityname() {
		entityname = "DOTaxDocumentTicket";
	}
	
}