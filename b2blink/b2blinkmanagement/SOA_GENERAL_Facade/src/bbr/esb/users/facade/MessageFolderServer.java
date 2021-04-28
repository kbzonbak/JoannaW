package bbr.esb.users.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.factories.LowerCaseResultTransformer;
import bbr.esb.base.facade.ElementServer;
import bbr.esb.users.data.classes.MessageFolderDTO;
import bbr.esb.users.entities.Company;
import bbr.esb.users.entities.MessageFolder;
import bbr.esb.users.entities.MessageFolderType;

@Stateless(name = "servers/users/MessageFolderServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class MessageFolderServer extends ElementServer<MessageFolder, MessageFolderDTO> implements MessageFolderServerLocal {

	@EJB
	CompanyServerLocal companyserver;

	@EJB
	MessageFolderTypeServerLocal messagefoldertypeserver;

	public MessageFolderDTO addMessageFolder(MessageFolderDTO folder) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return addElement(folder);
	}

	@Override
	protected void copyRelationsEntityToWrapper(MessageFolder entity, MessageFolderDTO wrapper) throws OperationFailedException, NotFoundException {
		wrapper.setCompanykey(entity.getCompany().getId() != null ? new Long(entity.getCompany().getId()) : new Long(0));
		wrapper.setMessagefoldertypekey(entity.getMessagefoldertype().getId() != null ? new Long(entity.getMessagefoldertype().getId()) : new Long(0));
	}

	@Override
	protected void copyRelationsWrapperToEntity(MessageFolder entity, MessageFolderDTO wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getCompanykey() != null && wrapper.getCompanykey() > 0) {
			Company service = companyserver.getReferenceById(wrapper.getCompanykey());
			if (service != null) {
				entity.setCompany(service);
			}
		}
		
		if (wrapper.getMessagefoldertypekey()!= null && wrapper.getMessagefoldertypekey() > 0) {
			MessageFolderType messagefoldertype = messagefoldertypeserver.getReferenceById(wrapper.getMessagefoldertypekey());
			if (messagefoldertype != null) {
				entity.setMessagefoldertype(messagefoldertype);
			}
		}
	}

	public void deleteMessageFolder(Long userid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		deleteElement(userid);
	}

	public MessageFolderDTO getMessageFolderByPK(Long userid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return getById(userid);
	}
	
	public MessageFolderDTO[] getMessageFoldersByPathAndUser(String path, Long uskey) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.esb.users.entities.MessageFolder.getMessageFoldersByPathAndUser");
		query.setString("path", path);
		query.setLong("uskey", uskey);
		query.setResultTransformer(new LowerCaseResultTransformer(MessageFolderDTO.class));
		List<?> list = query.list();
		MessageFolderDTO[] result = (MessageFolderDTO[]) list.toArray(new MessageFolderDTO[list.size()]);
		return result;
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Casilla";

	}

	@Override
	protected void setEntityname() {
		entityname = "MessageFolder";
	}

	public MessageFolderDTO updateMessageFolder(MessageFolderDTO user) throws OperationFailedException, NotFoundException {
		return updateElement(user);
	}

}
