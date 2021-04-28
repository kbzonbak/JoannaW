package bbr.esb.events.interfaces;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.adtclasses.interfaces.IElementServer;
import bbr.esb.events.data.classes.ServiceEventDataDTO;
import bbr.esb.events.data.classes.ServiceEventDTO;
import bbr.esb.events.entities.ServiceEvent;
import bbr.esb.services.data.classes.ContractedServiceDTO;

public interface IServiceEventServer extends IElementServer<ServiceEvent, ServiceEventDTO> {

	public ServiceEventDTO addServiceEvent(ServiceEventDTO event) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void deleteServiceEvent(Long serviceeventid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ServiceEventDTO getServiceEventByPK(Long serviceeventid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ServiceEventDataDTO[] getUnprocessedServiceEvents() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public ServiceEventDTO updateServiceEvent(ServiceEventDTO event) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public ServiceEventDTO[] getServiceEventOfContract(ContractedServiceDTO contractedServiceDTO) throws OperationFailedException, NotFoundException;
}
