package bbr.esb.events.interfaces;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.adtclasses.interfaces.IElementServer;
import bbr.esb.events.data.classes.DocumentTraceTypeDTO;
import bbr.esb.events.entities.DocumentTraceType;

public interface IDocumentTraceTypeServer extends IElementServer<DocumentTraceType, DocumentTraceTypeDTO> {

	public DocumentTraceTypeDTO addDocumentTraceType(DocumentTraceTypeDTO event) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void deleteDocumentTraceType(Long documenttracetypeid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public DocumentTraceTypeDTO getDocumentTraceTypeByPK(Long documenttracetypeid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public DocumentTraceTypeDTO updateDocumentTraceType(DocumentTraceTypeDTO event) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
}
