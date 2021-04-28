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
import bbr.esb.services.data.classes.SiteServiceDTO;
import bbr.esb.services.data.classes.SiteServiceReportDTO;
import bbr.esb.services.entities.Service;
import bbr.esb.services.entities.Site;
import bbr.esb.services.entities.SiteService;
import bbr.esb.services.entities.SiteServiceKey;

@Stateless(name = "servers/services/SiteServiceServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SiteServiceServer extends BaseEJB3Server<SiteService, SiteServiceKey, SiteServiceDTO> implements SiteServiceServerLocal {

	@EJB
	SiteServerLocal siteserver;

	@EJB
	ServiceServerLocal serviceserver;

	public SiteServiceDTO addSiteService(SiteServiceDTO siteService) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return addIdentifiable(siteService);
	}

	@Override
	protected void copyRelationsEntityToWrapper(SiteService entity, SiteServiceDTO wrapper) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		wrapper.setSitekey(entity.getId().getSitekey() != null ? new Long(entity.getId().getSitekey()) : new Long(0));
		wrapper.setServicekey(entity.getId().getServicekey() != null ? new Long(entity.getId().getServicekey()) : new Long(0));
	}

	@Override
	protected void copyRelationsWrapperToEntity(SiteService entity, SiteServiceDTO wrapper) throws OperationFailedException, NotFoundException {
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
	}

	public void deleteSiteService(Long sitekey, Long servicekey) throws OperationFailedException, NotFoundException {
		SiteServiceKey siteServiceKey = new SiteServiceKey(sitekey, servicekey);
		deleteIdentifiable(siteServiceKey);
	}

	public SiteServiceDTO[] getSiteServices() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return getAllAsArray();
	}

	public SiteServiceReportDTO[] getSiteServiceReport() throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.esb.services.entities.SiteService.getSiteServiceReport");
		query.setResultTransformer(new LowerCaseResultTransformer(SiteServiceReportDTO.class));
		List list = query.list();
		SiteServiceReportDTO[] result = (SiteServiceReportDTO[]) list.toArray(new SiteServiceReportDTO[list.size()]);
		return result;
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Servicio de Sitio";

	}

	@Override
	protected void setEntityname() {
		entityname = "SiteService";
	}

}
