package bbr.esb.users.facade;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.base.facade.ElementServer;
import bbr.esb.services.data.classes.ServiceDTO;
import bbr.esb.users.data.classes.FTPMessageFolderDTO;
import bbr.esb.users.entities.Company;
import bbr.esb.users.entities.FTPMessageFolder;
import bbr.esb.users.entities.MessageFolderType;

@Stateless(name = "servers/FTPMessageFolderServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FTPMessageFolderServer extends ElementServer<FTPMessageFolder, FTPMessageFolderDTO> implements FTPMessageFolderServerLocal {

	@EJB
	CompanyServerLocal companyserver;

	@EJB
	MessageFolderTypeServerLocal messagefoldertypeserver;
	
	public FTPMessageFolderDTO addFTPMessageFolder(FTPMessageFolderDTO ftpMessageFolder) throws OperationFailedException, NotFoundException {
			return (FTPMessageFolderDTO) addElement(ftpMessageFolder);
	}

	@Override
	protected void copyRelationsEntityToWrapper(FTPMessageFolder entity, FTPMessageFolderDTO wrapper) throws OperationFailedException, NotFoundException {			
	}

	@Override
	protected void copyRelationsWrapperToEntity(FTPMessageFolder entity, FTPMessageFolderDTO wrapper) throws OperationFailedException, NotFoundException {		
//		copiado de MessageFolderServer
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

	public FTPMessageFolderDTO[] getFTPMessageFolders() throws OperationFailedException, NotFoundException {
		return (FTPMessageFolderDTO[]) getIdentifiables();
	}
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "FTP MessageFolder";
	}

	@Override
	protected void setEntityname() {
		entityname = "FTPMessageFolder";
	}
	
	public FTPMessageFolderDTO getFTPMessageFolderByPK(Long FTPMessageFolderId) throws OperationFailedException, NotFoundException{
		return getById(FTPMessageFolderId); 
	}
	
	public void deleteFTPMessageFolder(Long FTPMessageFolderId) throws OperationFailedException, NotFoundException{
		deleteElement(FTPMessageFolderId);
	}
	
	public FTPMessageFolderDTO updateFTPMessageFolder(FTPMessageFolderDTO ftpMessageFolder) throws OperationFailedException, NotFoundException {
		return (FTPMessageFolderDTO) updateElement(ftpMessageFolder);
	}		
}
