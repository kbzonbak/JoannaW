package bbr.esb.services.interfaces;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.adtclasses.interfaces.IElementServer;
import bbr.esb.services.data.classes.ContractedServiceForCustomDTO;
import bbr.esb.services.data.classes.ServiceDTO;
import bbr.esb.services.entities.Service;

public interface IServiceServer extends IElementServer<Service, ServiceDTO> {

	public ServiceDTO addService(ServiceDTO service) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void deleteService(Long serviceid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ServiceDTO getServiceByPK(Long serviceid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ServiceDTO updateService(ServiceDTO service) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public ContractedServiceForCustomDTO[] getContractedServicesForCustom(String companyrut, String servicecode, String sitename) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
}
