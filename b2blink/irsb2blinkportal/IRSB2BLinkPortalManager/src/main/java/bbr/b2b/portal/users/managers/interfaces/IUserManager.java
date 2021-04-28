package bbr.b2b.portal.users.managers.interfaces;

import java.util.Locale;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.portal.managers.interfaces.IGenericEJBInterface;
import bbr.b2b.users.report.classes.AddFaqInitParamDTO;
import bbr.b2b.users.report.classes.AssignedCompaniesArrayResultDTO;
import bbr.b2b.users.report.classes.AssignedEventsArrayResultDTO;
import bbr.b2b.users.report.classes.AssignedProfilesArrayResultDTO;
import bbr.b2b.users.report.classes.AssignedRelationshipsInitParamDTO;
import bbr.b2b.users.report.classes.AssignedUsersArrayResultDTO;
import bbr.b2b.users.report.classes.ChangeLogIdInitParamDTO;
import bbr.b2b.users.report.classes.CompanyArrayResultDTO;
import bbr.b2b.users.report.classes.CompanyDTO;
import bbr.b2b.users.report.classes.CompanyDataArrayResultDTO;
import bbr.b2b.users.report.classes.CompanyDataResultDTO;
import bbr.b2b.users.report.classes.CompanyReportInitParamDTO;
import bbr.b2b.users.report.classes.CompanyReportResultDTO;
import bbr.b2b.users.report.classes.CompanyResultDTO;
import bbr.b2b.users.report.classes.ContactUsDTO;
import bbr.b2b.users.report.classes.EventArrayResultDTO;
import bbr.b2b.users.report.classes.FaqReportArrayResultDTO;
import bbr.b2b.users.report.classes.FaqReportInitParamDTO;
import bbr.b2b.users.report.classes.FaqsResultDTO;
import bbr.b2b.users.report.classes.LabelCommentValueArrayResultDTO;
import bbr.b2b.users.report.classes.LocationArrayResultDTO;
import bbr.b2b.users.report.classes.PositionResultDTO;
import bbr.b2b.users.report.classes.ProfileArrayResultDTO;
import bbr.b2b.users.report.classes.SelfManagementUpdateUserInfoDTO;
import bbr.b2b.users.report.classes.UserActivateInitParamDTO;
import bbr.b2b.users.report.classes.UserAdminRetailFilterInitParam;
import bbr.b2b.users.report.classes.UserAuditInitParamDTO;
import bbr.b2b.users.report.classes.UserAuditMinDateResultDTO;
import bbr.b2b.users.report.classes.UserDTO;
import bbr.b2b.users.report.classes.UserDataInfoDTO;
import bbr.b2b.users.report.classes.UserForNewsResultDTO;
import bbr.b2b.users.report.classes.UserInitParamDTO;
import bbr.b2b.users.report.classes.UserLikeInitParamDTO;
import bbr.b2b.users.report.classes.UserMergeInitParamDTO;
import bbr.b2b.users.report.classes.UserReportDataDTO;
import bbr.b2b.users.report.classes.UserReportInitParamDTO;
import bbr.b2b.users.report.classes.UserResultDTO;
import bbr.b2b.users.report.classes.UsersResultDTO;


public interface IUserManager extends IGenericEJBInterface {
	
	public Long[] getProviderIdsByUserId(Long userid);
	public void saveCompanySelectedAndAddCountUserProvider(Long uskey, String pvcode);
	
	//filtro proveedores (20 mas usados y ultimo seleccionado)(finanzas y logistica)
	public CompanyDataResultDTO getMostUsedProvidersByUserId(Long userid);
	//filtro proveedores (20 mas usados y ultimo seleccionado)(comercial)
	
	//buscador de proveedores del filtro
	public CompanyDataArrayResultDTO findCompanyOfUserByCode(Long userid,String code, boolean allenterprises);
	public CompanyDataArrayResultDTO findCompanyOfUserByName(Long userid,String name, boolean allenterprises);
	public CompanyDataArrayResultDTO findCompanyOfUserByCodeAndType(Long userid,String code, boolean allenterprises , String type);
	public CompanyDataArrayResultDTO findCompanyOfUserByNameAndType(Long userid,String name, boolean allenterprises , String type);
	//buscador de proveedores del filtro(comercial)
	
	public CompanyResultDTO findCompanyById(Long emkey);
	
	public CompanyArrayResultDTO findCompanyLikeName(String name);
	public CompanyArrayResultDTO findCompanyLikeCode(String code);

	public CompanyArrayResultDTO findCompanyLikeNameAndType(String name, String type);
	public CompanyArrayResultDTO findCompanyLikeCodeAndType(String code, String type);
	
	public UserResultDTO getUserById(Long id);
	public UsersResultDTO getUsersByCompany(Long company);
	//public GeneratePasswordDTO generatePasswordWithoutPIN(Long userPK);
	
	//CU 500
	//public UserResultDTO doLoginUser(String logid, String password);
	
	public BaseResultDTO updateUser (UserDTO user);
	public BaseResultDTO updateUserLastAccess (UserDTO user);

	//CU 501
	public UserResultDTO getUserByLogId(String logid);
	//CU 502
	//public UserResultDTO changePasswordByUser(Long userid, String password, String newpassword, String confirmnewpassword);
	
	public UserReportDataDTO getUserReport(UserReportInitParamDTO initParamW, Long userTypeId, Long companyId, boolean isPaginated, OrderCriteriaDTO[] order);
	
	public FileDownloadInfoResultDTO getExcelUserReport(UserReportInitParamDTO initParamW, Long userKey, String fileType, boolean isForZip);
	
	public UserResultDTO doChangeLogId (ChangeLogIdInitParamDTO initParamW);
	
	public BaseResultDTO deleteUser(Long[] userPKs, String accessToken,Long useradmin);
	
	public UsersResultDTO inactivateUser(Long[] userPKs);
	
	public UsersResultDTO activateUser(UserActivateInitParamDTO userActivateInitParam);

	
	//para diario mural
	public UserForNewsResultDTO getUsersForNews(long uskey);
	
	public UserDataInfoDTO getUserInfo(long uskey);
	
	public BaseResultDTO sendContactMail(ContactUsDTO contactUsW);
	
	public LabelCommentValueArrayResultDTO getCommentTypes();
	
	//validación autorización usuario
	public BaseResultDTO validateAuthorization(Long userid, String serviceName);
	
	//FAQ
	// Diario
	public FaqsResultDTO getFaqQuestionsForUserProfiles(long uskey);
	
	public FaqsResultDTO getFaqAnswerByQuestion(long fakey);
	
	// Administracion
	public FaqReportArrayResultDTO getFaqReportByProfile(FaqReportInitParamDTO initParamW, boolean isByFilter);
	
	public ProfileArrayResultDTO getProfilesByFaq(Long fakey);
	
	public BaseResultDTO doAddOrUpdateFaq(AddFaqInitParamDTO initParamW, boolean isAddFaq);
	
	public BaseResultDTO doDeleteFaqs(Long[] faKeys);
	
	public UserResultDTO addBasicUser(UserInitParamDTO userInitParam);
	
	public UserResultDTO addNoProviderBasicUserByAdministration(String email, String phone, Long emKey, Long useradmin);
	
	//Crear Como (Create Like)
	public UserResultDTO createUserLike (UserLikeInitParamDTO userLikeInitParam);
	
	//nuevos servicios actualizacion
	//nuevos servicios actualizacion
	public BaseResultDTO updateUserBasicInfo(UserInitParamDTO userInitParam);
	
	public BaseResultDTO addUserProfileRelations(Long userid, Long[] profilesKeyAdd, Long useradmin);
	public BaseResultDTO addUserCompanyRelations(Long userid, Long[] providersKeyAdd, Long useradmin);
	public BaseResultDTO addUserLocalRelations(Long userid, Long[] localsKeyAdd, Long useradmin);

	public BaseResultDTO deleteUserProfileRelations(Long userid, Long[] profilesKeyDelete, Long useradmin);
	public BaseResultDTO deleteUserCompanyRelations(Long userid, Long[] providersKeyDelete, Long useradmin);
	public BaseResultDTO deleteUserLocalRelations(Long userid, Long[] localsKeyDelete, Long useradmin);

	public BaseResultDTO deleteAllUserProfileRelations(Long userid, Long useradmin);
	public BaseResultDTO deleteAllUserCompanyRelations(Long userid, Long useradmin);
	public BaseResultDTO deleteAllUserLocalRelations(Long userid, Long useradmin);
	
	public BaseResultDTO addAllUserProfileRelations(Long userid, Long useradmin);
	public BaseResultDTO addAllUserCompanyRelations(Long userid);
	public BaseResultDTO addAllUserLocalRelations(Long userid);
	
	public PositionResultDTO getAllPosition();

//	public UserResultDTO recoverPasswordFirstPhase(String logid, String email, String url);
//	public UserResultDTO recoverPasswordSecondPhase(String logid, String verificationCode, String newpassword, String confirmnewpassword);
	public UserResultDTO getUserByVerificationCode(String verificationCode);
	
	//public UserResultDTO doUserRegistrationRequest(UserDTO userdata, String companyCode);	

	//public UserResultDTO updateUserAdvanced(UpdateUserInitParamDTO initParams);
	
	public CompanyResultDTO doAddCompany(CompanyDTO companydata);
	public CompanyReportResultDTO getCompanyReport(CompanyReportInitParamDTO initParams, boolean isPaginated);
	
	public UsersResultDTO getUsersFromCompanyByProfile(Long companyId, String profileCode);
	public UsersResultDTO getUsersByProfile(String profileCode);
	
	public CompanyArrayResultDTO getAllPayerCompanies();
	public CompanyArrayResultDTO getAllVendorCompanies();
	public UsersResultDTO getAllPayerUsers();
	public UsersResultDTO getAllVendorUsers();
	public EventArrayResultDTO getAllEvents();
	public AssignedUsersArrayResultDTO getAssignedUsersToPublishing(AssignedRelationshipsInitParamDTO initParams, boolean ispaginated);
	public AssignedCompaniesArrayResultDTO getAssignedCompaniesToPublishing(AssignedRelationshipsInitParamDTO initParams, boolean ispaginated);
	public AssignedProfilesArrayResultDTO getAssignedProfilesToPublishing(Long publishingId);
	public AssignedEventsArrayResultDTO getAssignedEventsToPublishing(Long publishingId);
	
	public UsersResultDTO getAllUsers(int rows, int pagenumber, boolean isPaginated);
	public CompanyArrayResultDTO getAllCompanies(int rows, int pagenumber, boolean isPaginated);
	
	public UsersResultDTO getUsersByLastNameAndProfile(String lastname, String profileCode);
	public UsersResultDTO getUsersByNameAndProfile(String name, String profileCode);
	public UsersResultDTO getUsersByCodeAndProfile(String code, String profileCode);
	
	public LocationArrayResultDTO getAssignedLocationsOfUser(Long userid,int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order);
	public LocationArrayResultDTO getAvailableLocationsOfUser(Long userid, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order);
	
	public CompanyArrayResultDTO getAssignedCompaniesOfUser(Long userid,int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order);
	public CompanyArrayResultDTO getAvailableCompaniesOfUser(Long userid, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order);
	
	public ProfileArrayResultDTO getAssignedProfilesOfUser(Long userid,int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order);
	public ProfileArrayResultDTO getAvailableProfilesOfUser(Long userid);
	
	public BaseResultDTO addAllCompaniesToUser(Long userid, boolean active, Long useradmin);
	public BaseResultDTO addAllLocalsToUser(Long userid, boolean active, Long useradmin);
	
	public UserResultDTO doMergeUserAttributes(UserMergeInitParamDTO userMergeInitParam);
	
	public BaseResultDTO validateActiveSessions(UserDTO user, String accessToken);
	
	public BaseResultDTO doLogOut(String refreshToken);
	
	public BaseResultDTO addUserProfileLikeUser(Long userid, Long useridbase);
	public FileDownloadInfoResultDTO downloadUserRecords(UserReportInitParamDTO initParamW, Long companyTypeId, Long companyId, OrderCriteriaDTO[] usersOrderCriteria, Long uskey, String fileformat, Locale locale );
	public FileDownloadInfoResultDTO downloadActiveProviderFile(Long userkey, String fomart, Locale locale );
	public BaseResultDTO doUpdateLastAccessOfUser(String refreshToken);
	
	public UsersResultDTO getAdminUserRTByProfile(Long prkey);
	public BaseResultDTO deleteAdminRetailFromProfile(Long profileid, Long[] adminIds, SelfManagementUpdateUserInfoDTO userdata);
	public UsersResultDTO findUserForAdminRetailFilter(UserAdminRetailFilterInitParam initParam);
	public BaseResultDTO doAddAdminRetail(Long[] userid, Long profileid, SelfManagementUpdateUserInfoDTO userdata);
	
	public FileDownloadInfoResultDTO downloadUserAuditReport (UserAuditInitParamDTO initparamDTO, Long uskey);
	public UserAuditMinDateResultDTO getMinAuditDate();
}

