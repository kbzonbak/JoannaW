package bbr.esb.users.interfaces;

import bbr.common.adtclasses.classes.BaseResultDTO;
import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.services.data.classes.LogonResultDTO;
import bbr.esb.services.data.classes.QuestionChallengeResultDTO;
import bbr.esb.users.data.classes.CompanyDTO;
import bbr.esb.users.data.classes.FunctionalityArrayResultDTO;
import bbr.esb.users.data.classes.UserCompanyDTO;
import bbr.esb.users.data.classes.UserDTO;
import bbr.esb.users.data.classes.UserTypeDTO;

public interface IUserManager {

	public CompanyDTO addCompany(CompanyDTO company) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public UserDTO addUser(UserDTO user) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public UserCompanyDTO addUserCompany(UserCompanyDTO usercompany) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void deleteUser(Long userid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void deleteUserCompany(Long userkey, Long companykey) throws OperationFailedException, NotFoundException;

	public UserDTO doAddUser(UserDTO user, Long[] companies) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public BaseResultDTO doChangePassword(String logid, String oldpassword, String newpassword);

	public BaseResultDTO doChangeQuestionAndAnswerChallenge(Integer userid, String password, String newquestion, String newanswer);

	public void doDeleteUser(Long userid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public LogonResultDTO doLogonUser(String logid, String password);

	public UserDTO doUpdateUser(UserDTO user, Long[] companies) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public BaseResultDTO doValidateQuestionChallengeAnswer(String logid, String answer, boolean newpassword);

	public UserDTO doValidateUserSession(String logid, String sessionid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public CompanyDTO[] getCompanies() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public CompanyDTO[] getCompaniesByRut(String rut) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public CompanyDTO[] getCompaniesLikeName(String name) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public CompanyDTO[] getCompaniesofUser(Long userid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public FunctionalityArrayResultDTO getFunctionalitiesByUserType(Long usertypeid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public QuestionChallengeResultDTO getQuestionChallengeByUser(String logid);

	public UserDTO getUserByPK(Long userid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public LogonResultDTO getUserBySession(String sessionid);

	public UserDTO[] getUsers() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public UserTypeDTO[] getUserTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;

	public CompanyDTO updateCompany(CompanyDTO company) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public UserDTO updateUser(UserDTO user) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
