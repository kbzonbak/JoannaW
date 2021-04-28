package bbr.b2b.portal.users.managers.interfaces;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.portal.managers.interfaces.IGenericEJBInterface;
import bbr.b2b.users.report.classes.CompanyDataArrayResultDTO;
import bbr.b2b.users.report.classes.LocationArrayResultDTO;
import bbr.b2b.users.report.classes.ProfileArrayResultDTO;
import bbr.b2b.users.report.classes.ProfileDTO;
import bbr.b2b.users.report.classes.ProfileFunctionalityInitParamDTO;
import bbr.b2b.users.report.classes.ProfileResultDTO;
import bbr.b2b.users.report.classes.TreeFunctionalityArrayResultDTO;
import bbr.b2b.users.report.classes.TreeFunctionalityDTO;
import bbr.b2b.users.report.classes.TreeFunctionalityResultDTO;
import bbr.b2b.users.report.classes.UserCompanyArrayResultDTO;
import bbr.b2b.users.report.classes.UserProfileArrayResultDTO;
import bbr.b2b.users.report.classes.UserTreeFunctionalityArrayResultDTO;


public interface IFunctionalityManager extends IGenericEJBInterface{

	public ProfileArrayResultDTO getProfiles();

	public ProfileResultDTO doAddProfile(ProfileFunctionalityInitParamDTO initParamW);

	public ProfileResultDTO deleteProfile(Long profileId);

	public ProfileResultDTO doUpdateProfile(ProfileDTO profile, Long[] funcsKeyDelete, Long[] funcsKeyAdd);	

	public UserTreeFunctionalityArrayResultDTO getTreeFunctionalityByApp(String nameApp);	

	public UserTreeFunctionalityArrayResultDTO getTreeFunctionalityByAppAndUser(String nameApp, Long userId);

	public UserTreeFunctionalityArrayResultDTO getTreeFunctionalitiesByProfile(Long profileId);

	public TreeFunctionalityResultDTO addFunctionality(TreeFunctionalityDTO treeFunctionality);	

	public TreeFunctionalityResultDTO deleteFunctionality(Long funcKey);

	public TreeFunctionalityArrayResultDTO doLoadTreeFunctionalities(TreeFunctionalityDTO[] functionalities);
	
	// Manejo perfiles app de usuario
	public BaseResultDTO updateUserProfileRelations(Long userid, Long[] profilesKeyAdd);	
	
	public ProfileArrayResultDTO findProfilesLikeCode(Long userid, String code);
	
	public CompanyDataArrayResultDTO findCompaniesLikeCodeNotAssigned(Long userid, String code, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order);
	public CompanyDataArrayResultDTO findCompaniesLikeNameNotAssigned(Long userid, String name, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order);
	public CompanyDataArrayResultDTO findCompaniesLikeCodeAssigned(Long userid, String code, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order);
	public CompanyDataArrayResultDTO findCompaniesLikeNameAssigned(Long userid, String name, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order);
	
	public CompanyDataArrayResultDTO findAllCompaniesNotAssigned(Long userid, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order);
	public CompanyDataArrayResultDTO findAllCompaniesAssigned(Long userid, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order);
	
	public LocationArrayResultDTO findLocalsLikeNameAssigned(Long userid, String name, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order);
	public LocationArrayResultDTO findLocalsLikeCodeAssigned(Long userid, String code, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order);
	public LocationArrayResultDTO findLocalsLikeNameNotAssigned(Long userid, String name, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order);
	public LocationArrayResultDTO findLocalsLikeCodeNotAssigned(Long userid, String code, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order);
	
	public LocationArrayResultDTO findAllLocalsNotAssigned(Long userid, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order);
	public LocationArrayResultDTO findAllLocalsAssigned(Long userid, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order);
	
	// Relaciones de un usuario
	public ProfileArrayResultDTO getProfilesOfUserForFilter(Long userid);	
	
	//Autogestion
	public ProfileArrayResultDTO getProfilesForProviders();
	public ProfileArrayResultDTO getAllProfileAssigedToUser(Long userid);
	public UserCompanyArrayResultDTO getVendorForUserRequest(Long userid, Long adminpvid);
	public UserProfileArrayResultDTO getAllProfilesAdminCompany(Long userid, Long adminid);
	
	public UserTreeFunctionalityArrayResultDTO getTreeFunctionalitiesByProfileCode(String profileCode);
}
