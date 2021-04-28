package bbr.b2b.portal.users.managers.classes;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.portal.utils.PortalStatusCodeUtils;
import bbr.b2b.users.managers.interfaces.ContextUtil;
import bbr.b2b.users.managers.interfaces.IFunctionalityManager;
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

@Stateless(name = "managers/FunctionalityManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FunctionalityManager implements FunctionalityManagerLocal {

	private IFunctionalityManager functionalityManager = null;
		
	@PostConstruct
	public void getRemote() {
		try {
			functionalityManager = ContextUtil.getInstance().getIFunctionalityManager();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public UserTreeFunctionalityArrayResultDTO getTreeFunctionalityByAppAndUser(String nameApp, Long userId){
		try {
			return functionalityManager.getTreeFunctionalityByAppAndUser(nameApp, userId);
		
		} catch (Exception e) {
			UserTreeFunctionalityArrayResultDTO resultW = new UserTreeFunctionalityArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}
	}
	
	public ProfileResultDTO doAddProfile(ProfileFunctionalityInitParamDTO initParamW) {
		try{
			return functionalityManager.doAddProfile(initParamW);
		}catch (Exception e) {
			ProfileResultDTO resultW = new ProfileResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}		
	}
	
	public UserTreeFunctionalityArrayResultDTO getTreeFunctionalitiesByProfile(Long profileId) {
		try{
			return functionalityManager.getTreeFunctionalitiesByProfile(profileId);
		}catch (Exception e) {
			UserTreeFunctionalityArrayResultDTO resultW = new UserTreeFunctionalityArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}
	}
	
	public UserTreeFunctionalityArrayResultDTO getTreeFunctionalitiesByProfileCode(String profileCode) {
		try{
			return functionalityManager.getTreeFunctionalitiesByProfileCode(profileCode);
		}catch (Exception e) {
			UserTreeFunctionalityArrayResultDTO resultW = new UserTreeFunctionalityArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}
	}
	
	public ProfileResultDTO doUpdateProfile(ProfileDTO profile, Long[] funcsKeyDelete, Long[] funcsKeyAdd) {
		try{
			return functionalityManager.doUpdateProfile(profile, funcsKeyDelete, funcsKeyAdd);
		}catch (Exception e) {
			ProfileResultDTO resultW =  new ProfileResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}		
	}
	public TreeFunctionalityResultDTO addFunctionality(TreeFunctionalityDTO treeFunctionality){
		try{
			return functionalityManager.addFunctionality(treeFunctionality);
		}catch (Exception e) {
			TreeFunctionalityResultDTO resultW =  new TreeFunctionalityResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}			
	}
	
	public ProfileResultDTO deleteProfile(Long profileId) {
		try{
			return functionalityManager.deleteProfile(profileId);
		}catch (Exception e) {
			ProfileResultDTO resultW =  new ProfileResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}	
	}
	
	public TreeFunctionalityResultDTO deleteFunctionality(Long funcKey){
		try{
			return functionalityManager.deleteFunctionality(funcKey);
		}catch (Exception e) {
			TreeFunctionalityResultDTO resultW =  new TreeFunctionalityResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}		
	}
	
	public UserTreeFunctionalityArrayResultDTO getTreeFunctionalityByApp(String nameApp){
		try{
			return functionalityManager.getTreeFunctionalityByApp(nameApp);
		}catch (Exception e) {
			UserTreeFunctionalityArrayResultDTO resultW =  new UserTreeFunctionalityArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}		
	}
	
	public ProfileArrayResultDTO getProfiles() {
		try{
			return functionalityManager.getProfilesByVisibility();
		}catch (Exception e) {
			ProfileArrayResultDTO resultW =  new ProfileArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}			
	}
	
	/*public ProfileArrayResultDTO getProfilesByUserId(Long userPK,int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order) {
		try{
			return functionalityManager.getProfilesByUserId(userPK,pagenumber,rows,getInfoPage,isPaginated,order);
		}catch (Exception e) {
			ProfileArrayResultDTO resultW =  new ProfileArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}		
	}*/
	
	

	public TreeFunctionalityArrayResultDTO doLoadTreeFunctionalities(TreeFunctionalityDTO[] functionalities) {
		try{
			return functionalityManager.doLoadTreeFunctionalities(functionalities);
		}catch (Exception e) {
			TreeFunctionalityArrayResultDTO resultW =  new TreeFunctionalityArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}	
	}

//	public ProfileArrayResultDTO findProfilesLikeCodeNotAssigned(Long userid, String code, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order) {
//		try {
//			return functionalityManager.findProfilesLikeCodeNotAssigned(userid, code, pagenumber, rows, getInfoPage, isPaginated, order);
//		} catch (Exception e) {
//			ProfileArrayResultDTO resultDTO = new ProfileArrayResultDTO();
//			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
//		}
//	}
//
//	public ProfileArrayResultDTO findProfilesLikeNameNotAssigned(Long userid, String name, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order) {
//		try {
//			return functionalityManager.findProfilesLikeNameNotAssigned(userid, name, pagenumber, rows, getInfoPage, isPaginated, order);
//		} catch (Exception e) {
//			ProfileArrayResultDTO resultDTO = new ProfileArrayResultDTO();
//			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
//		}
//	}
//
//	public ProfileArrayResultDTO findProfilesLikeCodeAssigned(Long userid, String code, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order) {
//		try {
//			return functionalityManager.findProfilesLikeCodeAssigned(userid, code, pagenumber, rows, getInfoPage, isPaginated, order);
//		} catch (Exception e) {
//			ProfileArrayResultDTO resultDTO = new ProfileArrayResultDTO();
//			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
//		}
//	}

	public BaseResultDTO updateUserProfileRelations(Long userid, Long[] profilesKeyAdd) {
		try {
			return functionalityManager.updateUserProfileRelations(userid,profilesKeyAdd);
		} catch (Exception e) {
			BaseResultDTO resultDTO = new BaseResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	public ProfileArrayResultDTO findProfilesLikeCode(Long userid, String code) {
		try {
			return functionalityManager.findProfilesLikeCode(userid, code);
		} catch (Exception e) {
			ProfileArrayResultDTO resultDTO = new ProfileArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	public LocationArrayResultDTO findLocalsLikeNameAssigned(Long userid, String name, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order) {
		try {
			return functionalityManager.findLocationsLikeNameAssigned(userid, name, pagenumber, rows, getInfoPage, isPaginated, order);
		} catch (Exception e) {
			LocationArrayResultDTO resultDTO = new LocationArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	public LocationArrayResultDTO findLocalsLikeCodeAssigned(Long userid, String code, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order) {
		try {
			return functionalityManager.findLocationsLikeCodeAssigned(userid, code, pagenumber, rows, getInfoPage, isPaginated, order);
		} catch (Exception e) {
			LocationArrayResultDTO resultDTO = new LocationArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	public LocationArrayResultDTO findLocalsLikeNameNotAssigned(Long userid, String name, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order) {
		try {
			return functionalityManager.findLocationsLikeNameNotAssigned(userid, name, pagenumber, rows, getInfoPage, isPaginated, order);
		} catch (Exception e) {
			LocationArrayResultDTO resultDTO = new LocationArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	public LocationArrayResultDTO findLocalsLikeCodeNotAssigned(Long userid, String code, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order) {
		try {
			return functionalityManager.findLocationsLikeCodeNotAssigned(userid, code, pagenumber, rows, getInfoPage, isPaginated, order);
		} catch (Exception e) {
			LocationArrayResultDTO resultDTO = new LocationArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public LocationArrayResultDTO findAllLocalsNotAssigned(Long userid, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order) {
		try {
			return functionalityManager.findAllLocationsNotAssigned(userid, pagenumber, rows, getInfoPage, isPaginated, order);
		} catch (Exception e) {
			LocationArrayResultDTO resultDTO = new LocationArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	public LocationArrayResultDTO findAllLocalsAssigned(Long userid, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order) {
		try {
			return functionalityManager.findAllLocationsAssigned(userid, pagenumber, rows, getInfoPage, isPaginated, order);
		} catch (Exception e) {
			LocationArrayResultDTO resultDTO = new LocationArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	public CompanyDataArrayResultDTO findCompaniesLikeCodeNotAssigned(Long userid, String code, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order) {
		try {
			return functionalityManager.findCompaniesLikeCodeNotAssigned(userid, code, pagenumber, rows, getInfoPage, isPaginated, order);
		} catch (Exception e) {
			CompanyDataArrayResultDTO resultDTO = new CompanyDataArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public CompanyDataArrayResultDTO findCompaniesLikeNameNotAssigned(Long userid, String name, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order) {
		try {
			return functionalityManager.findCompaniesLikeNameNotAssigned(userid, name, pagenumber, rows, getInfoPage, isPaginated, order);
		} catch (Exception e) {
			CompanyDataArrayResultDTO resultDTO = new CompanyDataArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	
	public CompanyDataArrayResultDTO findCompaniesLikeCodeAssigned(Long userid, String code, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order) {
		try {
			return functionalityManager.findCompaniesLikeCodeAssigned(userid, code, pagenumber, rows, getInfoPage, isPaginated, order);
		} catch (Exception e) {
			CompanyDataArrayResultDTO resultDTO = new CompanyDataArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	
	public CompanyDataArrayResultDTO findCompaniesLikeNameAssigned(Long userid, String name, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order) {
		try {
			return functionalityManager.findCompaniesLikeNameAssigned(userid, name, pagenumber, rows, getInfoPage, isPaginated, order);
		} catch (Exception e) {
			CompanyDataArrayResultDTO resultDTO = new CompanyDataArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public CompanyDataArrayResultDTO findAllCompaniesAssigned(Long userid,int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order) {
		try {
			return functionalityManager.findAllCompaniesAssigned(userid, pagenumber, rows, getInfoPage, isPaginated, order);
		} catch (Exception e) {
			CompanyDataArrayResultDTO resultDTO = new CompanyDataArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	
	public CompanyDataArrayResultDTO findAllCompaniesNotAssigned(Long userid, int pagenumber, int rows, boolean getInfoPage, boolean isPaginated, OrderCriteriaDTO[] order) {
		try {
			return functionalityManager.findAllCompaniesNotAssigned(userid, pagenumber, rows, getInfoPage, isPaginated, order);
		} catch (Exception e) {
			CompanyDataArrayResultDTO resultDTO = new CompanyDataArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	public ProfileArrayResultDTO getProfilesOfUserForFilter(Long userid) {
		try {
			return functionalityManager.getProfilesOfUserForFilter(userid);
		} catch (Exception e) {
			ProfileArrayResultDTO resultDTO = new ProfileArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}	
	
	public ProfileArrayResultDTO getProfilesForProviders() {
		try{
			return functionalityManager.getProfilesForProviders();
		}catch (Exception e) {
			ProfileArrayResultDTO resultW = new ProfileArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}		
	}
	
	public ProfileArrayResultDTO getAllProfileAssigedToUser(Long userid) {
		try{
			return functionalityManager.getAllProfileAssigedToUser(userid);
		}catch (Exception e) {
			ProfileArrayResultDTO resultW = new ProfileArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}		
	}
	
	public UserCompanyArrayResultDTO getVendorForUserRequest(Long userid, Long adminpvid) {
		try{
			return functionalityManager.getVendorForUserRequest(userid, adminpvid);
		}catch (Exception e) {
			UserCompanyArrayResultDTO resultW = new UserCompanyArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}		
	}
	
	public UserProfileArrayResultDTO getAllProfilesAdminCompany(Long userid, Long adminid) {
		try{
			return functionalityManager.getAllProfilesAdminCompany(userid, adminid);
		}catch (Exception e) {
			UserProfileArrayResultDTO resultW = new UserProfileArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo usuarios no disp.
		}		
	}
}
