package bbr.esb.users.facade;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.base.facade.BaseEJB3Server;
import bbr.esb.users.data.classes.UserCompanyDTO;
import bbr.esb.users.entities.Company;
import bbr.esb.users.entities.User;
import bbr.esb.users.entities.UserCompany;
import bbr.esb.users.entities.UserCompanyKey;

@Stateless(name = "servers/users/UserCompanyServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserCompanyServer extends BaseEJB3Server<UserCompany, UserCompanyKey, UserCompanyDTO> implements UserCompanyServerLocal {

	@EJB
	CompanyServerLocal companyserver;

	@EJB
	UserServerLocal userserver;

	public UserCompanyDTO addUserCompany(UserCompanyDTO userCompany) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return addIdentifiable(userCompany);
	}

	@Override
	protected void copyRelationsEntityToWrapper(UserCompany entity, UserCompanyDTO wrapper) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		wrapper.setUserkey(entity.getId().getUserkey() != null ? new Long(entity.getId().getUserkey()) : new Long(0));
		wrapper.setCompanykey(entity.getId().getCompanykey() != null ? new Long(entity.getId().getCompanykey()) : new Long(0));
	}

	@Override
	protected void copyRelationsWrapperToEntity(UserCompany entity, UserCompanyDTO wrapper) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		if (wrapper.getUserkey() != null && wrapper.getUserkey() > 0) {
			User user = userserver.getReferenceById(wrapper.getUserkey());
			if (user != null) {
				entity.setUser(user);
			}
		}
		if (wrapper.getCompanykey() != null && wrapper.getCompanykey() > 0) {
			Company company = companyserver.getReferenceById(wrapper.getCompanykey());
			if (company != null) {
				entity.setCompany(company);
			}
		}
	}

	public void deleteUserCompany(Long userkey, Long companykey) throws OperationFailedException, NotFoundException {
		UserCompanyKey key = new UserCompanyKey();
		key.setUserkey(userkey);
		key.setCompanykey(companykey);
		deleteIdentifiable(key);
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Usuario Compañia";
	}

	@Override
	protected void setEntityname() {
		entityname = "UserCompany";
	}

}
