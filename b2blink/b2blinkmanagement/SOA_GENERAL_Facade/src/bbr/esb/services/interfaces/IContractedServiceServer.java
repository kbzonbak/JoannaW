package bbr.esb.services.interfaces;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.adtclasses.interfaces.IGenericServer;
import bbr.esb.services.data.classes.CompaniesServiceStatusDataDTO;
import bbr.esb.services.data.classes.ContractedServiceDTO;
import bbr.esb.services.data.classes.ContractedServiceDataDTO;
import bbr.esb.services.data.classes.ContractedServiceOfRutDTO;
import bbr.esb.services.data.classes.SiteDTO;
import bbr.esb.services.entities.ContractedService;
import bbr.esb.services.entities.ContractedServiceKey;

public interface IContractedServiceServer extends IGenericServer<ContractedService, ContractedServiceKey, ContractedServiceDTO> {

	public ContractedServiceDTO addContractedService(ContractedServiceDTO contractedservice) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ContractedServiceDataDTO[] getContractedServicesofCompany(Long companyid) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public SiteDTO[] getContractedServicesofCompanyRut(String companyrut, String servicename) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ContractedServiceDataDTO[] getAllContractedServices() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public CompaniesServiceStatusDataDTO[] getAllContractedServicesBySiteService(Long sitekey, Long servicekey) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public ContractedServiceDTO[] getMonitoredContractedServices() throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public ContractedService updateContractedService(ContractedServiceDTO contractedservice, boolean alert) throws OperationFailedException, NotFoundException;
	
	public ContractedServiceOfRutDTO[] getContractedServicesofCompany(String companyrut) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
