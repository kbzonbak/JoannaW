package bbr.esb.events.facade;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.base.facade.ElementServer;
import bbr.esb.events.data.classes.DocumentTraceDTO;
import bbr.esb.events.entities.DocumentTrace;
import bbr.esb.events.entities.DocumentTraceType;

@Stateless(name = "servers/events/DocumentTraceServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DocumentTraceServer extends ElementServer<DocumentTrace, DocumentTraceDTO> implements DocumentTraceServerLocal {

	@EJB
	DocumentTraceTypeServerLocal documenttracetypeserver;	

	public DocumentTraceDTO addDocumentTrace(DocumentTraceDTO documenttrace) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return addElement(documenttrace);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DocumentTrace entity, DocumentTraceDTO wrapper) throws OperationFailedException, NotFoundException {
		
		wrapper.setDocumenttracetypekey((entity.getDocumenttracetype() != null ?  new Long (entity.getDocumenttracetype().getId()) : new Long(0)));
		
	}

	@Override
	protected void copyRelationsWrapperToEntity(DocumentTrace entity, DocumentTraceDTO wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDocumenttracetypekey() != null && wrapper.getDocumenttracetypekey() > 0) {
			DocumentTraceType documenttracetype = documenttracetypeserver.getReferenceById(wrapper.getDocumenttracetypekey());
			if (documenttracetype != null) {
				entity.setDocumenttracetype(documenttracetype);
			}
		}		
	}

	public void deleteDocumentTrace(Long documenttraceid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		deleteElement(documenttraceid);
	}

	public DocumentTraceDTO getDocumentTraceByPK(Long documenttraceid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return getById(documenttraceid);
	}


	@Override
	protected void setEntitylabel() {
		entitylabel = "Traza de documento";

	}

	@Override
	protected void setEntityname() {
		entityname = "DocumentTrace";
	}

	public DocumentTraceDTO updateDocumentTrace(DocumentTraceDTO documenttrace) throws OperationFailedException, NotFoundException {
		return updateElement(documenttrace);
	}

}
