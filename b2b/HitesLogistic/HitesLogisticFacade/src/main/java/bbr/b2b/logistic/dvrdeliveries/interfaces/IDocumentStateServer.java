package bbr.b2b.logistic.dvrdeliveries.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.dvrdeliveries.entities.DocumentState;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DocumentStateW;

public interface IDocumentStateServer extends IElementServer<DocumentState, DocumentStateW> {

	DocumentStateW addDocumentState(DocumentStateW documentstate) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DocumentStateW[] getDocumentStates() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DocumentStateW updateDocumentState(DocumentStateW documentstate) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
