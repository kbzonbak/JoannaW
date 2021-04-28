package bbr.esb.events.interfaces;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.adtclasses.interfaces.IElementServer;
import bbr.esb.events.data.classes.DocumentTraceDTO;
import bbr.esb.events.entities.DocumentTrace;

public interface IDocumentTraceServer extends IElementServer<DocumentTrace, DocumentTraceDTO> {

	public DocumentTraceDTO addDocumentTrace(DocumentTraceDTO event) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void deleteDocumentTrace(Long documenttraceid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public DocumentTraceDTO getDocumentTraceByPK(Long documenttraceid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public DocumentTraceDTO updateDocumentTrace(DocumentTraceDTO event) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
}
