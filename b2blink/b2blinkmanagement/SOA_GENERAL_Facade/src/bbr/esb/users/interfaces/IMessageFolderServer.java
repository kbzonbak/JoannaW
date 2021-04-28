package bbr.esb.users.interfaces;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.adtclasses.interfaces.IElementServer;
import bbr.esb.users.data.classes.MessageFolderDTO;
import bbr.esb.users.entities.MessageFolder;

public interface IMessageFolderServer extends IElementServer<MessageFolder, MessageFolderDTO> {

	public MessageFolderDTO addMessageFolder(MessageFolderDTO folder) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void deleteMessageFolder(Long folderid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public MessageFolderDTO getMessageFolderByPK(Long folderid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public MessageFolderDTO updateMessageFolder(MessageFolderDTO folder) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public MessageFolderDTO[] getMessageFoldersByPathAndUser(String path, Long uskey) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
