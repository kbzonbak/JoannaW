package bbr.esb.users.facade;

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
import bbr.esb.users.data.classes.CompanyDTO;
import bbr.esb.users.entities.Company;

@Stateless(name = "servers/users/CompanyServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CompanyServer extends ElementServer<Company, CompanyDTO> implements CompanyServerLocal {

	public CompanyDTO addCompany(CompanyDTO company) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return addElement(company);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Company entity, CompanyDTO wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void copyRelationsWrapperToEntity(Company entity, CompanyDTO wrapper) throws OperationFailedException, NotFoundException {
	}

	public void deleteCompany(Long userid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		deleteElement(userid);
	}

	public CompanyDTO[] getCompaniesofUser(Long userid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.esb.users.entities.Company.getCompaniesofUser");
		query.setLong("userid", userid);
		query.setResultTransformer(new LowerCaseResultTransformer(CompanyDTO.class));
		List list = query.list();
		CompanyDTO[] result = (CompanyDTO[]) list.toArray(new CompanyDTO[list.size()]);
		return result;
	}

	public CompanyDTO[] getCompanyByAccessAndSitename(String sitename, String accesscode) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.esb.users.entities.Company.getCompanyrutByAccessAndSitename");
		query.setString("sitename", sitename);
		query.setString("accesscode", accesscode);
		query.setResultTransformer(new LowerCaseResultTransformer(CompanyDTO.class));
		List list = query.list();
		CompanyDTO[] result = (CompanyDTO[]) list.toArray(new CompanyDTO[list.size()]);
		return result;
	}
	
	public CompanyDTO getCompanyByPK(Long userid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return getById(userid);
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Compañia";

	}

	@Override
	protected void setEntityname() {
		entityname = "Company";
	}

	public CompanyDTO updateCompany(CompanyDTO user) throws OperationFailedException, NotFoundException {
		return updateElement(user);
	}

}
