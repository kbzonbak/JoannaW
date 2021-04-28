package bbr.esb.services.webservices.interfaces;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.events.data.classes.FileEventDTO;
import bbr.esb.events.data.classes.FileEventDataDTO;
import bbr.esb.events.data.classes.ServiceEventDTO;
import bbr.esb.events.data.classes.ServiceEventDataDTO;
import bbr.esb.events.data.classes.TicketEventDTO;
import bbr.esb.events.data.classes.TicketEventResultDTO;
import bbr.esb.events.data.classes.TicketEventStatusDataDTO;
import bbr.esb.services.data.classes.CompaniesServiceStatusReportDTO;
import bbr.esb.services.data.classes.ContractedServiceMonitorDataDTO;
import bbr.esb.services.data.classes.LogonResultDTO;
import bbr.esb.users.data.classes.MessageDataDTO;
import bbr.esb.users.data.classes.MessageFolderDataDTO;

public interface IServiceManagerServer {

	public ServiceEventDTO[] doAddGenericServiceEvent(String sitename, String servicename) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ServiceEventDTO doAddServiceEvent(String sitename, String servicename, String accesscode, String numorder) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public MessageDataDTO getMessageData(String sitename, String servicename, String accesscode) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public MessageFolderDataDTO getMessageFolder(String sitename, String servicename, String accesscode) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ServiceEventDataDTO[] getUnprocessedServiceEvents() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public FileEventDTO doAddFileEvent(String sitename, String servicename, String accesscode, String documentid, String filename) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public FileEventDTO doUpdateFileEventStatus(String as2id, String filename, String status, String datereceived) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public FileEventDataDTO[] getUninformedFileEvents() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public CompaniesServiceStatusReportDTO[] getCompaniesServiceStatusReport() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void updateContractedServiceMonitorData(ContractedServiceMonitorDataDTO[] monitordata) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public LogonResultDTO doLogonUser(String logid, String password);
	
	public FileEventDTO doUpdateFileEventStatusofPathandUser(String path, String userlogid, String filename, String datereceived) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public MessageDataDTO getMessageDataofUser(String sitename, String servicename, String accesscode, String userlogid) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public TicketEventResultDTO checkUploadStatus(String sitename, String servicename, String accesscode, String ticketnumber, String userlogid) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public MessageFolderDataDTO getMessageFolderofUser(String path, String userlogid) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public void addlogByCompanyRut(String description, String sitename, String servicename, String companyrut, String numorder, String comentcode, Boolean iscontracted);
	
	public TicketEventDTO doAddTicketEventStatus(Long ticketnumber, String sitename, String servicename, String accesscode, String ticketstate, String userlogid) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public TicketEventDTO doUpdateTicketEventStatus(Long ticketnumber, String refnumber, String sitename, String servicename, String accesscode, String ticketstate, String url, String userlogid, TicketEventStatusDataDTO[] details) throws AccessDeniedException, OperationFailedException, NotFoundException;
}