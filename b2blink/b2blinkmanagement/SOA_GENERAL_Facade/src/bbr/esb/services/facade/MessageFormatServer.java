package bbr.esb.services.facade;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.base.facade.ElementServer;
import bbr.esb.services.entities.MessageFormat;
import bbr.esb.services.entities.Service;
import bbr.esb.users.data.classes.MessageFormatDTO;

@Stateless(name = "servers/users/MessageFormatServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class MessageFormatServer extends ElementServer<MessageFormat, MessageFormatDTO> implements MessageFormatServerLocal {

	@EJB
	ServiceServerLocal serviceserver;

	public MessageFormatDTO addMessageFormat(MessageFormatDTO folder) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return addElement(folder);
	}

	@Override
	protected void copyRelationsEntityToWrapper(MessageFormat entity, MessageFormatDTO wrapper) throws OperationFailedException, NotFoundException {
		wrapper.setServicekey(entity.getService().getId() != null ? new Long(entity.getService().getId()) : new Long(0));
	}

	@Override
	protected void copyRelationsWrapperToEntity(MessageFormat entity, MessageFormatDTO wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getServicekey() != null && wrapper.getServicekey() > 0) {
			Service service = serviceserver.getReferenceById(wrapper.getServicekey());
			if (service != null) {
				entity.setService(service);
			}
		}
	}

	public void deleteMessageFormat(Long userid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		deleteElement(userid);
	}

	public MessageFormatDTO getMessageFormatByPK(Long userid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return getById(userid);
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Casilla";

	}

	@Override
	protected void setEntityname() {
		entityname = "MessageFormat";
	}

	public MessageFormatDTO updateMessageFormat(MessageFormatDTO user) throws OperationFailedException, NotFoundException {
		return updateElement(user);
	}

}
