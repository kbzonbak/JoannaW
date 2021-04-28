package bbr.esb.services.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.factories.LowerCaseResultTransformer;
import bbr.esb.base.facade.ElementServer;
import bbr.esb.services.data.classes.ContractedServiceForCustomDTO;
import bbr.esb.services.data.classes.ServiceDTO;
import bbr.esb.services.data.classes.SiteDTO;
import bbr.esb.services.entities.Service;

@Stateless(name = "servers/services/ServiceServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ServiceServer extends ElementServer<Service, ServiceDTO> implements ServiceServerLocal {

	public ServiceDTO addService(ServiceDTO service) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return addElement(service);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Service entity, ServiceDTO wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void copyRelationsWrapperToEntity(Service entity, ServiceDTO wrapper) throws OperationFailedException, NotFoundException {
	}

	public void deleteService(Long serviceid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		deleteElement(serviceid);
	}

	public ServiceDTO getServiceByPK(Long serviceid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return getById(serviceid);
	}

	public ServiceDTO[] getServices() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return null;
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Servicio";

	}

	@Override
	protected void setEntityname() {
		entityname = "Service";
	}

	public ServiceDTO updateService(ServiceDTO service) throws OperationFailedException, NotFoundException {
		return updateElement(service);
	}
	
	public ContractedServiceForCustomDTO[] getContractedServicesForCustom(String companyrut, String servicecode, String sitename) throws AccessDeniedException, OperationFailedException, NotFoundException {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.esb.services.entities.ContractedService.getContractedServicesforCustom");
		query.setString("companyrut", companyrut);
		query.setString("servicecode", servicecode);
		query.setString("sitename", sitename);		
		query.setResultTransformer(new LowerCaseResultTransformer(ContractedServiceForCustomDTO.class));
		List list = query.list();
		ContractedServiceForCustomDTO[] result = (ContractedServiceForCustomDTO[]) list.toArray(new ContractedServiceForCustomDTO[list.size()]);
		return result;
	}

}
