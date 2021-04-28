package bbr.b2b.regional.logistic.deliveries.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.deliveries.entities.OutDocument;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OutDocumentW;

public interface IOutDocumentServer extends IElementServer<OutDocument, OutDocumentW> {

	OutDocumentW addOutDocument(OutDocumentW outdocument) throws AccessDeniedException, OperationFailedException, NotFoundException;
	OutDocumentW[] getOutDocuments() throws AccessDeniedException, OperationFailedException, NotFoundException;
	OutDocumentW updateOutDocument(OutDocumentW outdocument) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
