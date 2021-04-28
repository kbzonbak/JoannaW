package bbr.b2b.regional.logistic.deliveries.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OutDocumentW;
import bbr.b2b.regional.logistic.deliveries.entities.OutDocument;
import bbr.b2b.regional.logistic.deliveries.entities.OutReception;

@Stateless(name = "servers/deliveries/OutDocumentServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OutDocumentServer extends LogisticElementServer<OutDocument, OutDocumentW> implements OutDocumentServerLocal {


	@EJB
	OutReceptionServerLocal outreceptionserver;

	public OutDocumentW addOutDocument(OutDocumentW outdocument) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OutDocumentW) addElement(outdocument);
	}
	public OutDocumentW[] getOutDocuments() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OutDocumentW[]) getIdentifiables();
	}
	public OutDocumentW updateOutDocument(OutDocumentW outdocument) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OutDocumentW) updateElement(outdocument);
	}

	@Override
	protected void copyRelationsEntityToWrapper(OutDocument entity, OutDocumentW wrapper) {
		wrapper.setOutreceptionid(entity.getOutreception() != null ? new Long(entity.getOutreception().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(OutDocument entity, OutDocumentW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getOutreceptionid() != null && wrapper.getOutreceptionid() > 0) { 
			OutReception outreception = outreceptionserver.getReferenceById(wrapper.getOutreceptionid());
			if (outreception != null) { 
				entity.setOutreception(outreception);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "OutDocument";
	}
	@Override
	protected void setEntityname() {
		entityname = "OutDocument";
	}
}
