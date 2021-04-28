package bbr.b2b.portal.classes.factory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.rmi.PortableRemoteObject;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.portal.classes.beans.SessionBean;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.jms.facade.interfaces.ValidateUserSessionCacheDAOLocal;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsConstants;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.PortalConstants;
import bbr.b2b.portal.customer.managers.classes.CustomerReportManagerLocal;
import bbr.b2b.portal.managers.interfaces.IGenericEJBInterface;
import bbr.b2b.portal.users.managers.classes.UserReportManagerLocal;
import bbr.b2b.portal.utils.B2BSystemPropertiesUtil;
import bbr.b2b.users.report.classes.CompanyDataArrayResultDTO;
import bbr.b2b.users.report.classes.UserDTO;
import cl.bbr.core.components.basics.BbrUI;

public class CustomerEJBFactory {

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static CustomerEJBFactory _instance = new CustomerEJBFactory();
	private static final ExecutorService threadpool = Executors.newCachedThreadPool();

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	private CustomerEJBFactory() {
	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************
	public CustomerReportManagerLocal getCustomerManagerLocal(BbrUI bbrUI) throws BbrUserException, BbrSystemException {
		return getCustomerManagerLocal(bbrUI, null);
	}
	
	public CustomerReportManagerLocal getCustomerManagerLocal(BbrUI bbrUI, String companycode) throws BbrUserException, BbrSystemException {
		UserDTO userDTO = this.validateUserSystem(bbrUI);
		this.isCompanyAssignedToUser(userDTO.getId(), companycode, userDTO.getAllenterprises());

		CustomerReportManagerLocal manager = (CustomerReportManagerLocal) getEJBHomes("CustomerReportManagerLocal");
		return manager;
	}
	

	// ****************************************************************************************
	// ENDING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	protected static CustomerEJBFactory getFactory() {
		return _instance;
	}

	private IGenericEJBInterface getEJBHomes(String interfaceStr) {
		IGenericEJBInterface genericInterface = null;
		try {
			// Raíz de búsqueda
			Object umreference = getObjectReference(PortalConstants.MANAGER_CLASSPATH, interfaceStr);
			genericInterface = (IGenericEJBInterface) PortableRemoteObject.narrow(umreference, umreference.getClass());
		} catch (Exception e) {
			throw new RuntimeException("Error instanciado session beans", e);
		}

		return genericInterface;
	}

	private Object getObjectReference(String searchParam, String intName) {
		Object result = null;
		try {
			Context ctx = (Context) new InitialContext().lookup(searchParam);
			NamingEnumeration<NameClassPair> names = ctx.list("");
			while (names.hasMore()) {
				result = (new InitialContext()).lookup(searchParam + "/" + names.next().getName());

				if (result.getClass().getSimpleName().split("\\$")[0].equals(intName)) {
					break;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private void isCompanyAssignedToUser(long userID, String companyCode, boolean allenterprises) throws BbrUserException
	{

		if (companyCode != null && !allenterprises)
		{
			UserReportManagerLocal manager = (UserReportManagerLocal) getEJBHomes("UserReportManagerLocal");
			CompanyDataArrayResultDTO companyData = manager.findCompanyOfUserByCode(userID, companyCode, allenterprises);
			if (companyData.getCompanyDataDTOs().length == 0)
			{
				throw new BbrUserException(BbrUserException.UNAUTHORIZED_OPERATION, I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "US1200"));
			}
		}
	}

	@SuppressWarnings("unchecked")
	private UserDTO validateUserSystem(BbrUI bbrUI) throws BbrUserException
	{
		UserDTO userData = null;

		if (bbrUI != null && bbrUI.getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME) != null)
		{
			SessionBean sessionBean = (SessionBean) bbrUI.getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);

			UserReportManagerLocal manager = (UserReportManagerLocal) getEJBHomes("UserReportManagerLocal");

			userData = manager.getUserById(sessionBean.getUserId()).getUser();

			ValidateUserSessionCacheDAOLocal validateUserSessionDAO = (ValidateUserSessionCacheDAOLocal) getEJBHomes(PortalConstants.WEB_CLASSPATH_DAO, "ValidateUserSessionCacheDAOLocal");
			if (!userData.getSession().equals(sessionBean.getSessionId()))
			{
				validateUserSessionDAO.remove(sessionBean.getSessionId());
				throw new BbrUserException(BbrUserException.LOGIN_SAME_ACCOUNT, I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_SYSTEM, "US1100"));
			}
			validateActiveSessions(validateUserSessionDAO, manager, userData, bbrUI);
			// ENVIA AL KEYCLOAK UNA ACTUALIZACION DEL ULTIMO ACCESO SI EL
			// USUARIO LLAMÓ UN REPORTE
			// SE HACE DE MANERA ASYNCRONA PARA NO AFECTAR LA PERFORMANCE DEL
			// SITIO
			UpdateUserToken task = new UpdateUserToken(manager, sessionBean.getRefreshToken());
			threadpool.submit(task);
		}
		else
		{
			AppUtils.getInstance().doLogOut(I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "US1000"), bbrUI);
			throw new BbrUserException(BbrUserException.INVALID_SESSION, I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_SYSTEM, "US1000"));
		}

		return userData;
	}

	private void validateActiveSessions(ValidateUserSessionCacheDAOLocal validateUserSessionDAO,
			UserReportManagerLocal manager, UserDTO userData, BbrUI bbrUI) throws BbrUserException {

		LocalDateTime lastValidatedCache = validateUserSessionDAO.get(userData.getSession());
		if (lastValidatedCache == null) {
			BaseResultDTO resultdto = manager.validateActiveSessions(userData, "");
			if (!resultdto.getStatuscode().equals("0")) {
				throw new BbrUserException(BbrUserException.INVALID_SESSION,
						I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_SYSTEM, "US1000"));
			}

			validateUserSessionDAO.put(userData.getSession(), LocalDateTime.now());
		} else {
			LocalDateTime now = LocalDateTime.now();
			Duration duration = Duration.between(lastValidatedCache, now);
			long minutesPassed = duration.toMinutes();
			Long validateSessionTime = Long.valueOf(B2BSystemPropertiesUtil.getProperty("valdiateActiveSessionMin"));
			if (minutesPassed >= validateSessionTime) {
				BaseResultDTO resultdto = manager.validateActiveSessions(userData, "");
				if (!resultdto.getStatuscode().equals("0")) {
					throw new BbrUserException(BbrUserException.INVALID_SESSION,
							I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_SYSTEM, "US1000"));
				}

				validateUserSessionDAO.put(userData.getSession(), now);
			}
		}
	}

	private IGenericEJBInterface getEJBHomes(String searchParam, String interfaceStr) {
		IGenericEJBInterface genericInterface = null;
		try {
			// Raíz de búsqueda
			Object umreference = getObjectReference(searchParam, interfaceStr);
			genericInterface = (IGenericEJBInterface) PortableRemoteObject.narrow(umreference, umreference.getClass());
		} catch (Exception e) {
			throw new RuntimeException("Error instanciado session beans", e);
		}

		return genericInterface;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

	@SuppressWarnings("rawtypes")
	private static class UpdateUserToken implements Callable {

		private UserReportManagerLocal manager;
		private String refreshToken;

		public UpdateUserToken(UserReportManagerLocal manager, String refreshToken) {
			this.manager = manager;
			this.refreshToken = refreshToken;
		}

		@Override
		public BaseResultDTO call() {
			return manager.doUpdateLastAccessOfUser(refreshToken);
		}
	}
}
