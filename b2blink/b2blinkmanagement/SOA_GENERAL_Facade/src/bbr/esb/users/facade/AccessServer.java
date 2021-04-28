package bbr.esb.users.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.common.adtclasses.classes.PropertyInfoDTO;
import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.factories.LowerCaseResultTransformer;
import bbr.esb.base.facade.BaseEJB3Server;
import bbr.esb.services.entities.Site;
import bbr.esb.services.facade.SiteServerLocal;
import bbr.esb.users.data.classes.AccessDTO;
import bbr.esb.users.data.classes.AccessDataDTO;
import bbr.esb.users.entities.Access;
import bbr.esb.users.entities.AccessKey;
import bbr.esb.users.entities.Company;

@Stateless(name = "servers/users/AccessServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AccessServer extends BaseEJB3Server<Access, AccessKey, AccessDTO> implements AccessServerLocal {

	@EJB
	CompanyServerLocal companyserver;

	@EJB
	SiteServerLocal siteserver;

	public AccessDTO addAccess(AccessDTO access) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return addIdentifiable(access);
	}

	public void deleteSiteUser(Long sitekey, Long companykey) throws OperationFailedException, NotFoundException {
		AccessKey key = new AccessKey(companykey, sitekey);
		deleteIdentifiable(key);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Access entity, AccessDTO wrapper) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		wrapper.setCompanykey(entity.getId().getCompanykey() != null ? new Long(entity.getId().getCompanykey()) : new Long(0));
		wrapper.setSitekey(entity.getId().getSitekey() != null ? new Long(entity.getId().getSitekey()) : new Long(0));
	}

	@Override
	protected void copyRelationsWrapperToEntity(Access entity, AccessDTO wrapper) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		if (wrapper.getCompanykey() != null && wrapper.getCompanykey() > 0) {
			Company company = companyserver.getReferenceById(wrapper.getCompanykey());
			if (company != null) {
				entity.setCompany(company);
			}
		}
		if (wrapper.getSitekey() != null && wrapper.getSitekey() > 0) {
			Site site = siteserver.getReferenceById(wrapper.getSitekey());
			if (site != null) {
				entity.setSite(site);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Acceso";

	}

	@Override
	protected void setEntityname() {
		entityname = "Access";
	}

	public AccessDTO[] getAccessesBySiteAndCode(Long sitekey, String accesscode) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		PropertyInfoDTO[] info = new PropertyInfoDTO[2];
		PropertyInfoDTO site = new PropertyInfoDTO("site.id", "sitekey", sitekey);
		info[0] = site;
		PropertyInfoDTO code = new PropertyInfoDTO("code", "code", accesscode);
		info[1] = code;
		List<AccessDTO> accessList = getByProperties(info);
		return getWrapperArrayByCollection(accessList);
	}

	@Override
	public AccessDataDTO[] getAccessDataofCompany(Long companyid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.esb.users.entities.Access.getAccessDataofCompany");
		query.setLong("companyid", companyid);
		query.setResultTransformer(new LowerCaseResultTransformer(AccessDataDTO.class));
		List list = query.list();
		AccessDataDTO[] result = (AccessDataDTO[]) list.toArray(new AccessDataDTO[list.size()]);
		return result;
	}

	public String getAccessByCompanyRutAndSiteName(String companyrut, String sitename) throws AccessDeniedException{
		String result="";
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.esb.users.entities.Access.getAccessbycompanyrutAndsitename");
		query.setString("companyrut", companyrut);
		query.setString("sitename", sitename);		
		//query.setResultTransformer(new LowerCaseResultTransformer(String.class));
		List list = query.list();
		if(list != null && list.size()>0){
			result = (String) list.get(0);
		}else{
			throw new AccessDeniedException("No existe codigo para el sitio y proveedor indicado "+ sitename + " - " + companyrut);
		}
		
		return result;
	}
}
