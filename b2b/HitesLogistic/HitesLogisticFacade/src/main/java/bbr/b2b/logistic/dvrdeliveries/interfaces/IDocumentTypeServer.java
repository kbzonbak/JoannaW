package bbr.b2b.logistic.dvrdeliveries.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DocumentTypeW;
import bbr.b2b.logistic.dvrdeliveries.entities.DocumentType;

public interface IDocumentTypeServer extends IElementServer<DocumentType, DocumentTypeW> {

	DocumentTypeW addDocumentType(DocumentTypeW documenttype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DocumentTypeW[] getDocumentTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DocumentTypeW updateDocumentType(DocumentTypeW documenttype) throws AccessDeniedException, OperationFailedException, NotFoundException;
}
