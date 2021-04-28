package bbr.esb.services.facade;

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
import bbr.esb.base.facade.BaseEJB3Server;
import bbr.esb.services.data.classes.CompaniesServiceStatusDataDTO;
import bbr.esb.services.data.classes.ContractedServiceDTO;
import bbr.esb.services.data.classes.ContractedServiceDataDTO;
import bbr.esb.services.data.classes.ContractedServiceOfRutDTO;
import bbr.esb.services.data.classes.SiteDTO;
import bbr.esb.services.entities.ContractedService;
import bbr.esb.services.entities.ContractedServiceKey;
import bbr.esb.services.entities.MessageFormat;
import bbr.esb.services.entities.Service;
import bbr.esb.services.entities.Site;
import bbr.esb.services.entities.WSEndpoint;
import bbr.esb.users.entities.Company;
import bbr.esb.users.entities.MessageFolder;
import bbr.esb.users.facade.CompanyServerLocal;
import bbr.esb.users.facade.MessageFolderServerLocal;

@Stateless(name = "servers/services/ContractedServiceServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ContractedServiceServer extends BaseEJB3Server<ContractedService, ContractedServiceKey, ContractedServiceDTO> implements ContractedServiceServerLocal {

	@EJB
	CompanyServerLocal companyserver;

	@EJB
	SiteServerLocal siteserver;

	@EJB
	ServiceServerLocal serviceserver;

	@EJB
	MessageFolderServerLocal folderserver;

	@EJB
	MessageFormatServerLocal formatserver;

	@EJB
	WSEndpointServerLocal wsserver;
	
	public ContractedServiceDTO addContractedService(ContractedServiceDTO siteService) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return addIdentifiable(siteService);
	}

	@Override
	protected void copyRelationsEntityToWrapper(ContractedService entity, ContractedServiceDTO wrapper) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		wrapper.setCompanykey(entity.getId().getCompanykey() != null ? new Long(entity.getId().getCompanykey()) : new Long(0));
		wrapper.setSitekey(entity.getId().getSitekey() != null ? new Long(entity.getId().getSitekey()) : new Long(0));
		wrapper.setServicekey(entity.getId().getServicekey() != null ? new Long(entity.getId().getServicekey()) : new Long(0));
		wrapper.setFolderkey(entity.getFolder().getId() != null ? new Long(entity.getFolder().getId()) : new Long(0));
		wrapper.setFormatkey(entity.getFormat().getId() != null ? new Long(entity.getFormat().getId()) : new Long(0));
		wrapper.setWsendpointkey(entity.getWsendpoint() != null ? new Long(entity.getWsendpoint().getId()) : new Long(0));
	}

	@Override
	protected void copyRelationsWrapperToEntity(ContractedService entity, ContractedServiceDTO wrapper) throws OperationFailedException, NotFoundException {
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
		if (wrapper.getFolderkey() != null && wrapper.getFolderkey() > 0) {
			MessageFolder folder = folderserver.getReferenceById(wrapper.getFolderkey());
			if (folder != null) {
				entity.setFolder(folder);
			}
		}
		if (wrapper.getFormatkey() != null && wrapper.getFormatkey() > 0) {
			MessageFormat format = formatserver.getReferenceById(wrapper.getFormatkey());
			if (format != null) {
				entity.setFormat(format);
			}
		}
		if (wrapper.getWsendpointkey() != null && wrapper.getWsendpointkey() > 0) {
			WSEndpoint wsendpoint = wsserver.getReferenceById(wrapper.getWsendpointkey());
			if (wsendpoint != null) {
				entity.setWsendpoint(wsendpoint);
			}
		}	
	}

	public void deleteContractedService(Long sitekey, Long servicekey, Long companykey) throws OperationFailedException, NotFoundException {
		ContractedServiceKey siteServiceKey = new ContractedServiceKey(sitekey, servicekey, companykey);
		deleteIdentifiable(siteServiceKey);
	}

	public ContractedServiceDataDTO[] getAllContractedServices() throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.esb.services.entities.ContractedService.getAllContractedServices");
		query.setResultTransformer(new LowerCaseResultTransformer(ContractedServiceDataDTO.class));
		List list = query.list();
		ContractedServiceDataDTO[] result = (ContractedServiceDataDTO[]) list.toArray(new ContractedServiceDataDTO[list.size()]);
		return result;
	}

	public CompaniesServiceStatusDataDTO[] getAllContractedServicesBySiteService(Long sitekey, Long servicekey) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.esb.services.entities.ContractedService.getAllContractedServicesBySiteService");
		query.setLong("siteid", sitekey);
		query.setLong("serviceid", servicekey);
		query.setResultTransformer(new LowerCaseResultTransformer(CompaniesServiceStatusDataDTO.class));
		List list = query.list();
		CompaniesServiceStatusDataDTO[] result = (CompaniesServiceStatusDataDTO[]) list.toArray(new CompaniesServiceStatusDataDTO[list.size()]);
		return result;
	}

	public ContractedServiceDTO[] getContractedServices() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return getAllAsArray();
	}

	public ContractedServiceDataDTO[] getContractedServicesofCompany(Long companyid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.esb.services.entities.ContractedService.getContractedServicesofCompany");
		query.setLong("companyid", companyid);
		query.setResultTransformer(new LowerCaseResultTransformer(ContractedServiceDataDTO.class));
		List list = query.list();
		ContractedServiceDataDTO[] result = (ContractedServiceDataDTO[]) list.toArray(new ContractedServiceDataDTO[list.size()]);
		return result;
	}
	
	public ContractedServiceOfRutDTO[] getContractedServicesofCompany(String companyrut) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.esb.services.entities.ContractedService.getContractedServicesofCompanyByRut");
		query.setString("companyrut", companyrut);
		query.setResultTransformer(new LowerCaseResultTransformer(ContractedServiceOfRutDTO.class));
		List list = query.list();
		ContractedServiceOfRutDTO[] result = (ContractedServiceOfRutDTO[]) list.toArray(new ContractedServiceOfRutDTO[list.size()]);
		return result;
	}
	
	public SiteDTO[] getContractedServicesofCompanyRut(String companyrut, String servicename) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.esb.services.entities.ContractedService.getContractedServicesofCompanyRut");
		query.setString("companyrut", companyrut);
		query.setString("servicename", servicename);
		query.setResultTransformer(new LowerCaseResultTransformer(SiteDTO.class));
		List list = query.list();
		SiteDTO[] result = (SiteDTO[]) list.toArray(new SiteDTO[list.size()]);
		return result;
	}

	public ContractedServiceDTO[] getMonitoredContractedServices() throws AccessDeniedException, OperationFailedException, NotFoundException {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.esb.services.entities.ContractedService.getMonitoredContractedServices");		
		query.setResultTransformer(new LowerCaseResultTransformer(ContractedServiceDTO.class));
		List list = query.list();
		ContractedServiceDTO[] result = (ContractedServiceDTO[]) list.toArray(new ContractedServiceDTO[list.size()]);
		return result;
	}
	
	public ContractedService updateContractedService(ContractedServiceDTO contractedservice, boolean alert) throws OperationFailedException, NotFoundException {
		
		SQLQuery query;
		if(alert){
			query = (SQLQuery) getSession().getNamedQuery("bbr.esb.services.entities.ContractedService.UpdateMonitorDateAndPendingMessage");
		}else{
			query = (SQLQuery) getSession().getNamedQuery("bbr.esb.services.entities.ContractedService.UpdateMonitorDate");
		}
		query.setLong("sitekey", contractedservice.getSitekey());
		query.setLong("servicekey", contractedservice.getServicekey());
		query.setLong("companykey", contractedservice.getCompanykey());
		query.executeUpdate();
		
		return null;
	}
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "Servicio Contratado";

	}

	@Override
	protected void setEntityname() {
		entityname = "ContractedService";
	}
}
