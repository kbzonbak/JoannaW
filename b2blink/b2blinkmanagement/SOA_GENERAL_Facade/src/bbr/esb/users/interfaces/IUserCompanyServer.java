package bbr.esb.users.interfaces;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.adtclasses.interfaces.IGenericServer;
import bbr.esb.users.data.classes.UserCompanyDTO;
import bbr.esb.users.entities.UserCompany;
import bbr.esb.users.entities.UserCompanyKey;

public interface IUserCompanyServer extends IGenericServer<UserCompany, UserCompanyKey, UserCompanyDTO> {

	public UserCompanyDTO addUserCompany(UserCompanyDTO usercompany) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void deleteUserCompany(Long userkey, Long companykey) throws OperationFailedException, NotFoundException;

}
