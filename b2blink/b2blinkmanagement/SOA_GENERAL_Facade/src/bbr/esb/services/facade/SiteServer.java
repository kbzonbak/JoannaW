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
import bbr.esb.services.data.classes.SiteDTO;
import bbr.esb.services.entities.Site;

@Stateless(name = "servers/services/SiteServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SiteServer extends ElementServer<Site, SiteDTO> implements SiteServerLocal {

	public SiteDTO addSite(SiteDTO site) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return addElement(site);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Site entity, SiteDTO wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void copyRelationsWrapperToEntity(Site entity, SiteDTO wrapper) throws OperationFailedException, NotFoundException {
	}

	public void deleteSite(Long siteid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		deleteElement(siteid);
	}

	public SiteDTO getSiteByPK(Long siteid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return getById(siteid);
	}

	public SiteDTO[] getSites() throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public SiteDTO[] getSitesByCompanyRut(String companyrut) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.esb.services.entities.Site.getSitesByCompanyRut");
		query.setString("companyrut", companyrut);
		query.setResultTransformer(new LowerCaseResultTransformer(SiteDTO.class));
		List list = query.list();
		SiteDTO[] result = (SiteDTO[]) list.toArray(new SiteDTO[list.size()]);
		return result;
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Sitio";

	}

	@Override
	protected void setEntityname() {
		entityname = "Site";
	}

	public SiteDTO updateSite(SiteDTO site) throws OperationFailedException, NotFoundException {
		return updateElement(site);
	}

}
