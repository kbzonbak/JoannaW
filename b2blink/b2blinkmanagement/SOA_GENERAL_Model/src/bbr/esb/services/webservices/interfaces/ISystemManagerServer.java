package bbr.esb.services.webservices.interfaces;

import java.io.File;

import bbr.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.services.data.classes.ContractedServiceDTO;
import bbr.esb.services.data.classes.ContractedServiceDataDTO;
import bbr.esb.services.data.classes.ServiceDTO;
import bbr.esb.services.data.classes.SiteDTO;
import bbr.esb.services.data.classes.SiteServiceDTO;
import bbr.esb.users.data.classes.AccessDTO;
import bbr.esb.users.data.classes.CompanyDTO;
import bbr.esb.users.data.classes.MessageFolderDTO;
import bbr.esb.users.data.classes.MessageFolderDataDTO;
import bbr.esb.users.data.classes.MessageFormatDTO;
import bbr.esb.users.data.classes.UserDTO;
import bbr.esb.users.data.classes.UserTypeDTO;

public interface ISystemManagerServer {

	public AccessDTO addAccess(AccessDTO access) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public CompanyDTO addCompany(CompanyDTO company) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public MessageFolderDTO addMessageFolder(MessageFolderDTO folder) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ServiceDTO addService(ServiceDTO service) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public SiteDTO addSite(SiteDTO site) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void deleteMessageFolder(Long folderid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void deleteService(Long serviceid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void deleteSite(Long siteid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public SiteServiceDTO doActivateInactivateSiteService(Long siteid, Long serviceid, boolean enabled) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public UserDTO doAddUser(UserDTO user, Long[] companies) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ContractedServiceDTO doContractSiteService(Long siteid, Long serviceid, Long companyid, Long folderkey, Long formatkey) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void doDeleteUser(Long userid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void doRevokeSiteService(Long siteid, Long serviceid, Long companyid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ContractedServiceDTO doUpdateContractedService(Long siteid, Long serviceid, Long companyid, Long folderkey, Long formatkey, boolean monitor) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public UserDTO doUpdateUser(UserDTO user, Long[] companies) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public AccessDTO[] getAccessesofCompany(Long companyid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ContractedServiceDataDTO[] getAllContractedServices() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public File getAllContractedServicesXLS() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public CompanyDTO[] getCompanies() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public CompanyDTO[] getCompaniesByRut(String rut) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public CompanyDTO[] getCompaniesLikeName(String name) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public CompanyDTO[] getCompaniesofUser(Long userid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ContractedServiceDataDTO[] getContractedServicesofCompany(Long companyid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public MessageFolderDataDTO getMessageFolder(String sitename, String servicename, String accesscode) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public MessageFolderDTO[] getMessageFoldersofCompany(Long companyid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public MessageFormatDTO[] getMessageFormatsofService(Long serviceid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ServiceDTO getServiceByPK(Long serviceid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ServiceDTO[] getServices() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public SiteDTO getSiteByPK(Long siteid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public SiteDTO[] getSites() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public SiteServiceDTO[] getSiteServices() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public UserDTO[] getUsers() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public UserTypeDTO[] getUserTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ContractedServiceDTO switchSiteService(Long siteid, Long serviceid, Long companyid, boolean active) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public AccessDTO updateAccess(AccessDTO access) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public CompanyDTO updateCompany(CompanyDTO company) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public MessageFolderDTO updateMessageFolder(MessageFolderDTO folder) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ServiceDTO updateService(ServiceDTO service) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public SiteDTO updateSite(SiteDTO site) throws AccessDeniedException, OperationFailedException, NotFoundException;

}