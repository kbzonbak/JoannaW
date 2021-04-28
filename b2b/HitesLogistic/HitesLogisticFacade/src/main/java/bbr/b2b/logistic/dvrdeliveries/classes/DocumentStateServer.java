package bbr.b2b.logistic.dvrdeliveries.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DocumentStateW;
import bbr.b2b.logistic.dvrdeliveries.entities.Document;
import bbr.b2b.logistic.dvrdeliveries.entities.DocumentState;

@Stateless(name = "servers/dvrdeliveries/DocumentStateServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DocumentStateServer extends LogisticElementServer<DocumentState, DocumentStateW> implements DocumentStateServerLocal {


	@EJB
	DocumentServerLocal documentserver;

	public DocumentStateW addDocumentState(DocumentStateW documentstate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DocumentStateW) addElement(documentstate);
	}
	public DocumentStateW[] getDocumentStates() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DocumentStateW[]) getIdentifiables();
	}
	public DocumentStateW updateDocumentState(DocumentStateW documentstate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DocumentStateW) updateElement(documentstate);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DocumentState entity, DocumentStateW wrapper) {
		wrapper.setDocumentid(entity.getDocument() != null ? new Long(entity.getDocument().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(DocumentState entity, DocumentStateW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDocumentid() != null && wrapper.getDocumentid() > 0) { 
			Document document = documentserver.getReferenceById(wrapper.getDocumentid());
			if (document != null) { 
				entity.setDocument(document);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DocumentState";
	}
	@Override
	protected void setEntityname() {
		entityname = "DocumentState";
	}
}
