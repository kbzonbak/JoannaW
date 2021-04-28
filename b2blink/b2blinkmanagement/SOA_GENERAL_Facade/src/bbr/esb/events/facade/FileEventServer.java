package bbr.esb.events.facade;

import java.math.BigInteger;
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
import bbr.esb.events.data.classes.FileEventDTO;
import bbr.esb.events.data.classes.FileEventDataDTO;
import bbr.esb.events.data.classes.ScoreCardDTO;
import bbr.esb.events.data.classes.ServiceEventDTO;
import bbr.esb.events.entities.FileEvent;
import bbr.esb.services.data.classes.ContractedServiceDTO;
import bbr.esb.services.entities.Service;
import bbr.esb.services.entities.Site;
import bbr.esb.services.facade.ServiceServerLocal;
import bbr.esb.services.facade.SiteServerLocal;
import bbr.esb.users.entities.Company;
import bbr.esb.users.facade.CompanyServerLocal;

@Stateless(name = "servers/events/FileEventServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FileEventServer extends ElementServer<FileEvent, FileEventDTO> implements FileEventServerLocal {

	@EJB
	CompanyServerLocal companyserver;

	@EJB
	ServiceServerLocal serviceserver;

	@EJB
	SiteServerLocal siteserver;

	public FileEventDTO addFileEvent(FileEventDTO serviceevent) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return addElement(serviceevent);
	}

	@Override
	protected void copyRelationsEntityToWrapper(FileEvent entity, FileEventDTO wrapper) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		wrapper.setSitekey(entity.getSite() != null ? new Long(entity.getSite().getId()) : new Long(0));
		wrapper.setServicekey(entity.getService() != null ? new Long(entity.getService().getId()) : new Long(0));
		wrapper.setCompanykey(entity.getCompany() != null ? new Long(entity.getCompany().getId()) : new Long(0));
	}

	@Override
	protected void copyRelationsWrapperToEntity(FileEvent entity, FileEventDTO wrapper) throws OperationFailedException, NotFoundException {
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

	@Override
	public void deleteFileEvent(Long serviceeventid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		deleteElement(serviceeventid);
	}

	@Override
	public FileEventDTO getFileEventByPK(Long serviceeventid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return getById(serviceeventid);
	}
	
	@Override
	public FileEventDataDTO[] getFileEventsByContractedService(Long sitekey, Long servicekey, Long companykey) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.esb.events.entities.FileEvent.getFileEventsByContractedService");
		query.setResultTransformer(new LowerCaseResultTransformer(FileEventDataDTO.class));
		query.setLong("sitekey", sitekey);
		query.setLong("servicekey", servicekey);
		query.setLong("companykey", companykey);
		List list = query.list();
		FileEventDataDTO[] result = (FileEventDataDTO[]) list.toArray(new FileEventDataDTO[list.size()]);
		return result;
	}

	@Override
	public FileEventDataDTO[] getFileEventsByContractedService(Long sitekey, Long servicekey, Long companykey, boolean ispaginated, int pagenumber, int rows) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.esb.events.entities.FileEvent.getFileEventsByContractedService");
		query.setResultTransformer(new LowerCaseResultTransformer(FileEventDataDTO.class));
		query.setLong("sitekey", sitekey);
		query.setLong("servicekey", servicekey);
		query.setLong("companykey", companykey);

		if (ispaginated) {
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}

		List list = query.list();
		FileEventDataDTO[] result = (FileEventDataDTO[]) list.toArray(new FileEventDataDTO[list.size()]);
		return result;
	}

	@Override
	public int countFileEventsByContractedService(Long sitekey, Long servicekey, Long companykey) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.esb.events.entities.FileEvent.countFileEventsByContractedService");
		query.setLong("sitekey", sitekey);
		query.setLong("servicekey", servicekey);
		query.setLong("companykey", companykey);
		int total = ((BigInteger) query.list().get(0)).intValue();
		return total;
	}

	@Override
	public FileEventDataDTO[] getUninformedFileEvents() throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.esb.events.entities.FileEvent.getUninformedFileEvents");
		query.setResultTransformer(new LowerCaseResultTransformer(FileEventDataDTO.class));
		List list = query.list();
		FileEventDataDTO[] result = (FileEventDataDTO[]) list.toArray(new FileEventDataDTO[list.size()]);
		return result;
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Evento de Archivo";
	}

	@Override
	protected void setEntityname() {
		entityname = "FileEvent";
	}

	public FileEventDTO updateFileEvent(FileEventDTO serviceevent) throws OperationFailedException, NotFoundException {
		return updateElement(serviceevent);
	}
	
	public FileEventDTO[] FileEventOfContract(ServiceEventDTO serviceEventDTO, Integer delay) throws OperationFailedException, NotFoundException {		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.esb.events.entities.FileEvent.FileEventsOfAServiceEvent");
		query.setResultTransformer(new LowerCaseResultTransformer(FileEventDTO.class));
		query.setLong("companyid", serviceEventDTO.getCompanykey());
		query.setLong("serviceid", serviceEventDTO.getServicekey());
		query.setLong("siteid", serviceEventDTO.getSitekey());
		query.setDate("datecreated", serviceEventDTO.getDatecreated());
		query.setInteger("delay",delay);
		List list = query.list();
		FileEventDTO[] result = (FileEventDTO[]) list.toArray(new FileEventDTO[list.size()]);
		return result;
	}

}
