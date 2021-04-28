package bbr.esb.users.facade;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.base.facade.ElementServer;
import bbr.esb.users.data.classes.MessageFolderTypeDTO;
import bbr.esb.users.entities.MessageFolderType;

@Stateless(name = "servers/users/MessageFolderTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class MessageFolderTypeServer extends ElementServer<MessageFolderType, MessageFolderTypeDTO> implements MessageFolderTypeServerLocal {

	public MessageFolderTypeDTO addMessageFolderType(MessageFolderTypeDTO messageFolderType) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return addElement(messageFolderType);
	}

	@Override
	protected void copyRelationsEntityToWrapper(MessageFolderType entity, MessageFolderTypeDTO wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void copyRelationsWrapperToEntity(MessageFolderType entity, MessageFolderTypeDTO wrapper) throws OperationFailedException, NotFoundException {
	}

	public void deleteMessageFolderType(Long userid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		deleteElement(userid);
	}

	public MessageFolderTypeDTO getMessageFolderTypeByPK(Long userid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return getById(userid);
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Tipo de MessageFolder";

	}

	@Override
	protected void setEntityname() {
		entityname = "MessageFolderType";
	}

	public MessageFolderTypeDTO updateMessageFolderType(MessageFolderTypeDTO user) throws OperationFailedException, NotFoundException {
		return updateElement(user);
	}
}
