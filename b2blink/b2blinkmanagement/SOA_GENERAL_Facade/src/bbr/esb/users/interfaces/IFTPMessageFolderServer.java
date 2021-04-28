package bbr.esb.users.interfaces;

import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.adtclasses.interfaces.IGenericServer;
import bbr.esb.users.data.classes.FTPMessageFolderDTO;
import bbr.esb.users.entities.FTPMessageFolder;

public interface IFTPMessageFolderServer extends IGenericServer<FTPMessageFolder, Long, FTPMessageFolderDTO> {

	public FTPMessageFolderDTO addFTPMessageFolder(FTPMessageFolderDTO ftpMessageFolder) throws OperationFailedException, NotFoundException;
	public FTPMessageFolderDTO[] getFTPMessageFolders() throws OperationFailedException, NotFoundException;
	public void deleteFTPMessageFolder(Long ftpMessageFolderId) throws OperationFailedException, NotFoundException;
	public FTPMessageFolderDTO updateFTPMessageFolder(FTPMessageFolderDTO ftpMessageFolder) throws OperationFailedException, NotFoundException;
	public FTPMessageFolderDTO getFTPMessageFolderByPK(Long FTPMessageFolderId) throws OperationFailedException, NotFoundException;
}
