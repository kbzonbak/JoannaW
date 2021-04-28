package bbr.esb.users.interfaces;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.adtclasses.interfaces.IElementServer;
import bbr.esb.users.data.classes.MessageFolderTypeDTO;
import bbr.esb.users.entities.MessageFolderType;

public interface IMessageFolderTypeServer extends IElementServer<MessageFolderType, MessageFolderTypeDTO> {

	public MessageFolderTypeDTO addMessageFolderType(MessageFolderTypeDTO messageFolderType) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void deleteMessageFolderType(Long messageFolderTypeid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public MessageFolderTypeDTO getMessageFolderTypeByPK(Long messageFolderTypeid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public MessageFolderTypeDTO updateMessageFolderType(MessageFolderTypeDTO messageFolderType) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
}
