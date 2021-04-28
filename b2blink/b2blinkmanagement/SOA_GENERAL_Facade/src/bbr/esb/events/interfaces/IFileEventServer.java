package bbr.esb.events.interfaces;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.adtclasses.interfaces.IElementServer;
import bbr.esb.events.data.classes.FileEventDTO;
import bbr.esb.events.data.classes.FileEventDataDTO;
import bbr.esb.events.data.classes.ServiceEventDTO;
import bbr.esb.events.entities.FileEvent;

public interface IFileEventServer extends IElementServer<FileEvent, FileEventDTO> {

	public FileEventDTO addFileEvent(FileEventDTO event) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void deleteFileEvent(Long FileEventid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public FileEventDTO getFileEventByPK(Long FileEventid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public FileEventDataDTO[] getFileEventsByContractedService(Long sitekey, Long servicekey, Long companykey) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public FileEventDataDTO[] getFileEventsByContractedService(Long sitekey, Long servicekey, Long companykey, boolean ispaginated, int pagenumber, int rows) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public int countFileEventsByContractedService(Long sitekey, Long servicekey, Long companykey) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public FileEventDataDTO[] getUninformedFileEvents() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public FileEventDTO updateFileEvent(FileEventDTO event) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public FileEventDTO[] FileEventOfContract(ServiceEventDTO serviceEventDTO, Integer delay) throws OperationFailedException, NotFoundException;

}
