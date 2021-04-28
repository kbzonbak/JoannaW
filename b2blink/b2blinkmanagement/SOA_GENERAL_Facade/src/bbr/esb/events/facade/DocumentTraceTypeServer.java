package bbr.esb.events.facade;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.base.facade.ElementServer;
import bbr.esb.events.data.classes.DocumentTraceTypeDTO;
import bbr.esb.events.entities.DocumentTraceType;

@Stateless(name = "servers/events/DocumentTraceTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DocumentTraceTypeServer extends ElementServer<DocumentTraceType, DocumentTraceTypeDTO> implements DocumentTraceTypeServerLocal {

//	@EJB
//	CompanyServerLocal companyserver;	

	public DocumentTraceTypeDTO addDocumentTraceType(DocumentTraceTypeDTO documenttracetype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return addElement(documenttracetype);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DocumentTraceType entity, DocumentTraceTypeDTO wrapper) throws OperationFailedException, NotFoundException {
//		copyRelationsEntityToWrapper(entity, wrapper);
	}

	@Override
	protected void copyRelationsWrapperToEntity(DocumentTraceType entity, DocumentTraceTypeDTO wrapper) throws OperationFailedException, NotFoundException {
//		copyRelationsWrapperToEntity(entity, wrapper);
	}

	public void deleteDocumentTraceType(Long documenttracetypeid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		deleteElement(documenttracetypeid);
	}

	public DocumentTraceTypeDTO getDocumentTraceTypeByPK(Long documenttracetypeid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return getById(documenttracetypeid);
	}


	@Override
	protected void setEntitylabel() {
		entitylabel = "Detalle de traza de documentos";

	}

	@Override
	protected void setEntityname() {
		entityname = "DocumentTraceType";
	}

	public DocumentTraceTypeDTO updateDocumentTraceType(DocumentTraceTypeDTO documenttracetype) throws OperationFailedException, NotFoundException {
		return updateElement(documenttracetype);
	}

}
