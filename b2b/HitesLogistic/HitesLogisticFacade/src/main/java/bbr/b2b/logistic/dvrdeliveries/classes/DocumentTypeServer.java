package bbr.b2b.logistic.dvrdeliveries.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DocumentTypeW;
import bbr.b2b.logistic.dvrdeliveries.entities.DocumentType;

@Stateless(name = "servers/dvrdeliveries/DocumentTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DocumentTypeServer extends LogisticElementServer<DocumentType, DocumentTypeW> implements DocumentTypeServerLocal {

	public DocumentTypeW addDocumentType(DocumentTypeW documenttype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DocumentTypeW) addElement(documenttype);	
	}
	
	public DocumentTypeW[] getDocumentTypes() throws AccessDeniedException, OperationFailedException, NotFoundException{
		return (DocumentTypeW[]) getIdentifiables();
	}
	
	public DocumentTypeW updateDocumentType(DocumentTypeW documenttype) throws AccessDeniedException, OperationFailedException, NotFoundException{
		return (DocumentTypeW) updateElement(documenttype);
	}
	
	@Override
	protected void copyRelationsEntityToWrapper(DocumentType entity, DocumentTypeW wrapper) {

	}

	@Override
	protected void copyRelationsWrapperToEntity(DocumentType entity, DocumentTypeW wrapper) throws OperationFailedException, NotFoundException {

	}
	
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "DocumentType";
	}
	@Override
	protected void setEntityname() {
		entityname = "DocumentType";
	}
	
}

