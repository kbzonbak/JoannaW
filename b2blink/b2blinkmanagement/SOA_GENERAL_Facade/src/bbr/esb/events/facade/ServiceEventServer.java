package bbr.esb.events.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.factories.LowerCaseResultTransformer;
import bbr.esb.base.facade.ElementServer;
import bbr.esb.events.data.classes.ServiceEventDTO;
import bbr.esb.events.data.classes.ServiceEventDataDTO;
import bbr.esb.events.entities.ServiceEvent;
import bbr.esb.services.data.classes.ContractedServiceDTO;
import bbr.esb.services.entities.Service;
import bbr.esb.services.entities.Site;
import bbr.esb.services.facade.ServiceServerLocal;
import bbr.esb.services.facade.SiteServerLocal;
import bbr.esb.users.entities.Company;
import bbr.esb.users.facade.CompanyServerLocal;

@Stateless(name = "servers/events/ServiceEventServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ServiceEventServer extends ElementServer<ServiceEvent, ServiceEventDTO> implements ServiceEventServerLocal {

	@EJB
	CompanyServerLocal companyserver;

	@EJB
	ServiceServerLocal serviceserver;

	@EJB
	SiteServerLocal siteserver;

	public ServiceEventDTO addServiceEvent(ServiceEventDTO serviceevent) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return addElement(serviceevent);
	}

	@Override
	protected void copyRelationsEntityToWrapper(ServiceEvent entity, ServiceEventDTO wrapper) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		wrapper.setSitekey(entity.getSite() != null ? new Long(entity.getSite().getId()) : new Long(0));
		wrapper.setServicekey(entity.getService() != null ? new Long(entity.getService().getId()) : new Long(0));
		wrapper.setCompanykey(entity.getCompany() != null ? new Long(entity.getCompany().getId()) : new Long(0));
		
	}

	@Override
	protected void copyRelationsWrapperToEntity(ServiceEvent entity, ServiceEventDTO wrapper) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		if (wrapper.getSitekey() != null && wrapper.getSitekey() > 0) {
			Site site = siteserver.getReferenceById(wrapper.getSitekey());
			if (site != null) {
				entity.setSite(site);
			}
		}
		if (wrapper.getServicekey() != null && wrapper.getServicekey() > 0) {
			Service service = serviceserver.getReferenceById(wrapper.getServicekey());
			if (service != null) {
				entity.setService(service);
			}
		}
		if (wrapper.getCompanykey() != null && wrapper.getCompanykey() > 0) {
			Company company = companyserver.getReferenceById(wrapper.getCompanykey());
			if (company != null) {
				entity.setCompany(company);
			}
		}
	}

	public void deleteServiceEvent(Long serviceeventid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		deleteElement(serviceeventid);
	}

	public ServiceEventDTO getServiceEventByPK(Long serviceeventid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return getById(serviceeventid);
	}

	public ServiceEventDataDTO[] getUnprocessedServiceEvents() throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
//		loguear solicitud de eventos sin procesar solo si la consulta trae algo
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.esb.events.entities.ServiceEvent.getUnprocessedServiceEvents");
		query.setResultTransformer(new LowerCaseResultTransformer(ServiceEventDataDTO.class));
		List list = query.list();
		ServiceEventDataDTO[] result = (ServiceEventDataDTO[]) list.toArray(new ServiceEventDataDTO[list.size()]);
		return result;
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Evento de Servicio";

	}

	@Override
	protected void setEntityname() {
		entityname = "ServiceEvent";
	}

	public ServiceEventDTO updateServiceEvent(ServiceEventDTO serviceevent) throws OperationFailedException, NotFoundException {
		return updateElement(serviceevent);
	}
	
	public ServiceEventDTO[] getServiceEventOfContract(ContractedServiceDTO contractedServiceDTO) throws OperationFailedException, NotFoundException {		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.esb.events.entities.ServiceEvent.getServiceEventsOfContract");
		query.setResultTransformer(new LowerCaseResultTransformer(ServiceEventDTO.class));
		query.setLong("companyid", contractedServiceDTO.getCompanykey());
		query.setLong("serviceid", contractedServiceDTO.getServicekey());
		query.setLong("siteid", contractedServiceDTO.getSitekey());
		List list = query.list();
		ServiceEventDTO[] result = (ServiceEventDTO[]) list.toArray(new ServiceEventDTO[list.size()]);
		return result;
	}

}
