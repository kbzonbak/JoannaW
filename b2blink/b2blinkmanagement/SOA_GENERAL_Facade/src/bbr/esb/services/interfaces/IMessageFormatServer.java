package bbr.esb.services.interfaces;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.adtclasses.interfaces.IElementServer;
import bbr.esb.services.entities.MessageFormat;
import bbr.esb.users.data.classes.MessageFormatDTO;

public interface IMessageFormatServer extends IElementServer<MessageFormat, MessageFormatDTO> {

	public MessageFormatDTO addMessageFormat(MessageFormatDTO format) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void deleteMessageFormat(Long formatid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public MessageFormatDTO getMessageFormatByPK(Long formatid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public MessageFormatDTO updateMessageFormat(MessageFormatDTO format) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
