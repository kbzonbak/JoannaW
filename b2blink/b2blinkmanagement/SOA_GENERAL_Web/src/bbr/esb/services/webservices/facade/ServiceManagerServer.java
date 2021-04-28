package bbr.esb.services.webservices.facade;

import javax.ejb.EJB;
import javax.jws.WebService;

import bbr.common.adtclasses.classes.BaseResultDTO;
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
import bbr.esb.services.data.classes.InitParamCSDTO;
import bbr.esb.services.data.classes.LogonResultDTO;
import bbr.esb.services.managers.ServiceManagerLocal;
import bbr.esb.services.managers.UserManagerLocal;
import bbr.esb.services.webservices.interfaces.IServiceManagerServer;
import bbr.esb.users.data.classes.MessageDataDTO;
import bbr.esb.users.data.classes.MessageFolderDataDTO;

@WebService
public class ServiceManagerServer implements IServiceManagerServer {

	@EJB
	private ServiceManagerLocal servicemanagerlocal = null;
	
	@EJB
	private UserManagerLocal usermanagerlocal = null;
	
	
	public ServiceEventDTO[] doAddGenericServiceEvent(String sitename, String servicename) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return servicemanagerlocal.doAddGenericServiceEvent(sitename, servicename);
	}

	public ServiceEventDTO doAddServiceEvent(String sitename, String servicename, String accesscode, String numorder) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return servicemanagerlocal.doAddServiceEvent(sitename, servicename, accesscode, numorder);
	}

	public MessageDataDTO getMessageData(String sitename, String servicename, String accesscode) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return servicemanagerlocal.getMessageData(sitename, servicename, accesscode);
	}

	public MessageFolderDataDTO getMessageFolder(String sitename, String servicename, String accesscode) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return servicemanagerlocal.getMessageFolder(sitename, servicename, accesscode);
	}

	public ServiceEventDataDTO[] getUnprocessedServiceEvents() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return servicemanagerlocal.getUnprocessedServiceEvents();
	}

	public FileEventDTO doAddFileEvent(String sitename, String servicename, String accesscode, String documentid, String filename) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return servicemanagerlocal.doAddFileEvent(sitename, servicename, accesscode, documentid, filename);
	}

	public FileEventDTO doUpdateFileEventStatus(String as2id, String filename, String status, String datereceived) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return servicemanagerlocal.doUpdateFileEventStatus(as2id, filename, status, datereceived);
	}

	public FileEventDataDTO[] getUninformedFileEvents() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return servicemanagerlocal.getUninformedFileEvents();
	}

	public CompaniesServiceStatusReportDTO[] getCompaniesServiceStatusReport() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return servicemanagerlocal.getCompaniesServiceStatusReport();
	}

	public void updateContractedServiceMonitorData(ContractedServiceMonitorDataDTO[] monitordata) throws AccessDeniedException, OperationFailedException, NotFoundException {
		servicemanagerlocal.updateContractedServiceMonitorData(monitordata);
	}
	
	public FileEventDTO doUpdateFileEventStatusofPathandUser(String path, String userlogid, String filename, String datereceived) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return servicemanagerlocal.doUpdateFileEventStatusofPathandUser(path, userlogid, filename, datereceived);
	}
	
	public LogonResultDTO doLogonUser(String logid, String password) {
		return usermanagerlocal.doLogonUser(logid, password);
	}

	public TicketEventResultDTO checkUploadStatus(String sitename, String servicename, String accesscode, String ticketnumber, String userlogid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return servicemanagerlocal.checkUploadStatus(sitename, servicename, accesscode, ticketnumber, userlogid);
	}
	
	public MessageDataDTO getMessageDataofUser(String sitename, String servicename, String accesscode, String userlogid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return servicemanagerlocal.getMessageDataofUser(sitename, servicename, accesscode, userlogid);
	}
	
	public TicketEventDTO doAddTicketEventStatus(Long ticketnumber, String sitename, String servicename, String accesscode, String ticketstate, String userlogid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return servicemanagerlocal.doAddTicketEventStatus(ticketnumber, sitename, servicename, accesscode, ticketstate, userlogid);
	}
	
	public TicketEventDTO doUpdateTicketEventStatus(Long ticketnumber, String refnumber, String sitename, String servicename, String accesscode, String ticketstate, String url, String userlogid, TicketEventStatusDataDTO[] details) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return servicemanagerlocal.doUpdateTicketEventStatus(ticketnumber, refnumber, sitename, servicename, accesscode, ticketstate,url, userlogid, details);
	}
	
	public MessageFolderDataDTO getMessageFolderofUser(String path, String userlogid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return servicemanagerlocal.getMessageFolderofUser(path, userlogid);
	}
	
	public 	InitParamCSDTO[] getCredentialsBySiteService(String sitecode, String servicecode) throws AccessDeniedException, OperationFailedException, NotFoundException{
		return servicemanagerlocal.getCredentialsBySiteService(sitecode, servicecode);
	}
	
	public InitParamCSDTO getCredentialsBySiteServiceAndCompany(String sitecode, String servicecode, String accesscode) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return servicemanagerlocal.getCredentialsBySiteServiceAndCompany(sitecode, servicecode, accesscode);
	}

	public void addlogById(String description, Integer siteid, Integer serviceid, String accesscode, String numorder, String comentcode, Boolean iscontracted){
		servicemanagerlocal.addlogById(description, siteid, serviceid, accesscode, numorder, comentcode, iscontracted);
	}	
	
	public void addlog(String description, String sitename, String servicename, String accesscode, String numorder, String comentcode, Boolean iscontracted){
		servicemanagerlocal.addlog(description, sitename, servicename, accesscode, numorder, comentcode, iscontracted);
	}
	public void addlogByCompanyRut(String description, String sitename, String servicename, String companyrut, String numorder, String comentcode, Boolean iscontracted){
		servicemanagerlocal.addlogByCompanyRut(description, sitename, servicename, companyrut, numorder, comentcode, iscontracted);
	}
	public void addlogByCompanyAccess(String description, String sitename, String servicename, String access, String numorder, String comentcode, Boolean iscontracted){
		servicemanagerlocal.addlogByCompanyAcess(description, sitename, servicename, access, numorder, comentcode, iscontracted);
	}
	public void monitor(){
		servicemanagerlocal.doMonitor();
	}
	

	
	
}
