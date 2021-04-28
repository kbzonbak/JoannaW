package bbr.esb.services.interfaces;

import java.io.File;

import bbr.common.adtclasses.classes.BaseResultDTO;
import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.events.data.classes.DocumentsToResendInitParamDTO;
import bbr.esb.events.data.classes.FileEventDTO;
import bbr.esb.events.data.classes.FileEventDataDTO;
import bbr.esb.events.data.classes.ServiceEventDTO;
import bbr.esb.events.data.classes.ServiceEventDataDTO;
import bbr.esb.events.data.classes.TicketEventDTO;
import bbr.esb.events.data.classes.TicketEventResultDTO;
import bbr.esb.events.data.classes.TicketEventStatusDataDTO;
import bbr.esb.services.data.classes.CompaniesServiceStatusReportDTO;
import bbr.esb.services.data.classes.ContractedServiceDTO;
import bbr.esb.services.data.classes.ContractedServiceDataDTO;
import bbr.esb.services.data.classes.ContractedServiceMonitorDataDTO;
import bbr.esb.services.data.classes.InitParamCSDTO;
import bbr.esb.services.data.classes.ServiceDTO;
import bbr.esb.services.data.classes.SiteDTO;
import bbr.esb.services.data.classes.SiteServiceDTO;
import bbr.esb.services.data.classes.SiteServiceReportDTO;
import bbr.esb.users.data.classes.AccessDTO;
import bbr.esb.users.data.classes.AccessDataDTO;
import bbr.esb.users.data.classes.CompanyDTO;
import bbr.esb.users.data.classes.FTPMessageFolderDTO;
import bbr.esb.users.data.classes.MessageDataDTO;
import bbr.esb.users.data.classes.MessageFolderDTO;
import bbr.esb.users.data.classes.MessageFolderDataDTO;
import bbr.esb.users.data.classes.MessageFolderTypeDTO;
import bbr.esb.users.data.classes.MessageFormatDTO;

public interface IServiceManager {
	public FTPMessageFolderDTO addFTPMessageFolder(FTPMessageFolderDTO folder) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public MessageFolderTypeDTO[] getAllMessageFolderType() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public FTPMessageFolderDTO getFtpMessageFolderbyId(Long folderid) throws AccessDeniedException, OperationFailedException, NotFoundException ;

	public AccessDTO addAccess(AccessDTO access) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public MessageFolderDTO addMessageFolder(MessageFolderDTO folder) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ServiceDTO addService(ServiceDTO service) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ServiceEventDTO addServiceEvent(ServiceEventDTO user) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public SiteDTO addSite(SiteDTO site) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public TicketEventResultDTO checkUploadStatus(String sitename, String servicename, String accesscode, String ticketnumber, String userlogid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public int countFileEventsByContractedService(Long sitekey, Long servicekey, Long companykey) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void deleteMessageFolder(Long folderid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void deleteService(Long serviceid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void deleteSite(Long siteid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public SiteServiceDTO doActivateInactivateSiteService(Long siteid, Long serviceid, boolean enabled) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public FileEventDTO doAddFileEvent(String sitename, String servicename, String accesscode, String documentid, String filename) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ServiceEventDTO[] doAddGenericServiceEvent(String sitename, String servicename) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ServiceEventDTO doAddServiceEventByContracted(ContractedServiceDataDTO contractedService, String resendingdata) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ServiceEventDTO doAddServiceEvent(String sitename, String servicename, String accesscode, String numorder) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ContractedServiceDTO doContractSiteService(Long sitekey, Long servicekey, Long companykey, Long folderkey, Long formatkey,boolean compresseddocument) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void doRevokeSiteService(Long siteid, Long serviceid, Long companyid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ContractedServiceDTO doUpdateContractedService(Long siteid, Long serviceid, Long companyid, Long folderkey, Long formatkey, boolean monitor, boolean compresseddocument) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public FileEventDTO doUpdateFileEventStatus(String as2id, String filename, String status, String datereceived) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public AccessDataDTO[] getAccessDataofCompany(Long companyid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public AccessDTO[] getAccessesofCompany(Long companyid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ContractedServiceDataDTO[] getAllContractedServices() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public File getAllContractedServicesXLS() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public CompanyDTO[] getCompanies() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public CompaniesServiceStatusReportDTO[] getCompaniesServiceStatusReport() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ContractedServiceDataDTO[] getContractedServicesofCompany(Long companyid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public FileEventDataDTO[] getFileEventsByContractedService(Long sitekey, Long servicekey, Long companykey) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public FileEventDataDTO[] getFileEventsByContractedService(Long sitekey, Long servicekey, Long companykey, boolean ispaginated, int pagenumber, int rows) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public MessageDataDTO getMessageData(String sitename, String servicename, String accesscode) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public MessageFolderDataDTO getMessageFolder(String sitename, String servicename, String accesscode) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public MessageFolderDTO[] getMessageFoldersofCompany(Long companyid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public MessageFormatDTO[] getMessageFormatsofService(Long serviceid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ServiceDTO getServiceByPK(Long serviceid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ServiceDTO[] getServices() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public SiteDTO getSiteByPK(Long siteid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public SiteDTO[] getSites() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public SiteServiceReportDTO[] getSiteServiceReport() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public SiteServiceDTO[] getSiteServices() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public FileEventDataDTO[] getUninformedFileEvents() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ServiceEventDataDTO[] getUnprocessedServiceEvents() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ContractedServiceDTO switchSiteService(Long siteid, Long serviceid, Long companyid, boolean active) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public AccessDTO updateAccess(AccessDTO access) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void updateContractedServiceMonitorData(ContractedServiceMonitorDataDTO[] monitordata) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public MessageFolderDTO updateMessageFolder(MessageFolderDTO folder) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ServiceDTO updateService(ServiceDTO service) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public SiteDTO updateSite(SiteDTO site) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public FileEventDTO doUpdateFileEventStatusofPathandUser(String path, String userlogid, String filename, String datereceived) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public MessageDataDTO getMessageDataofUser(String sitename, String servicename, String accesscode, String userlogid) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public MessageFolderDataDTO getMessageFolderofUser(String path, String userlogid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public InitParamCSDTO[] getCredentialsBySiteService(String sitecode, String servicecode) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public InitParamCSDTO getCredentialsBySiteServiceAndCompany(String sitecode, String servicecode, String accesscode) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public 	BaseResultDTO doAddServiceEventByContracted(DocumentsToResendInitParamDTO documentstoresend);
	
	public void addlogById(String description, Integer siteid, Integer serviceid, String accesscode, String numorder, String comentcode, Boolean iscontracted);
	
	public void addlog(String description, String sitename, String servicename, String accesscode, String numorder, String comentcode, Boolean iscontracted);
	
	public void addlogByCompanyRut(String description, String sitename, String servicename, String companyrut, String numorder, String comentcode, Boolean iscontracted);
	
	public void addlogByCompanyAcess(String description, String sitename, String servicename, String access, String numorder, String comentcode, Boolean iscontracted);

	public void doAddServiceEventBySericeProvider(String companyrut, String servicecode, String sitename);
	
	public void doMonitor();
	
	public TicketEventDTO doUpdateTicketEventStatus(Long ticketnumber, String refnumber, String sitename, String servicename, String accesscode, String ticketstate, String url, String userlogid, TicketEventStatusDataDTO[] details) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public TicketEventDTO doAddTicketEventStatus(Long ticketnumber, String sitename, String servicename, String accesscode, String ticketstate, String userlogid ) throws AccessDeniedException, OperationFailedException, NotFoundException;
}