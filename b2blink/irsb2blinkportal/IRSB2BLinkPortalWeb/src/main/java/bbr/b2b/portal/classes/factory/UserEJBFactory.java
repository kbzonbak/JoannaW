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

import com.vaadin.ui.UI;

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
import bbr.b2b.portal.managers.interfaces.IGenericEJBInterface;
import bbr.b2b.portal.users.managers.classes.CompanyTypeReportManagerLocal;
import bbr.b2b.portal.users.managers.classes.ContactB2BReportManagerLocal;
import bbr.b2b.portal.users.managers.classes.FeedbackManagerLocal;
import bbr.b2b.portal.users.managers.classes.FunctionalityManagerLocal;
import bbr.b2b.portal.users.managers.classes.PublishingManagerLocal;
import bbr.b2b.portal.users.managers.classes.RequestReportManagerLocal;
import bbr.b2b.portal.users.managers.classes.UserReportManagerLocal;
import bbr.b2b.portal.utils.B2BSystemPropertiesUtil;
import bbr.b2b.users.report.classes.UserDTO;
import cl.bbr.core.components.basics.BbrUI;

public class UserEJBFactory {

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static UserEJBFactory _instance = new UserEJBFactory();
	private static final ExecutorService threadpool = Executors.newCachedThreadPool();

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	private UserEJBFactory() {
	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************
	public FeedbackManagerLocal getFeedbackManagerLocal() throws BbrUserException, BbrSystemException {
		this.validateUserSystem();

		FeedbackManagerLocal manager = (FeedbackManagerLocal) getEJBHomes("FeedbackManagerLocal");
		return manager;
	}
	
	public FeedbackManagerLocal getFeedbackManagerLocal(BbrUI bbrUI) throws BbrUserException, BbrSystemException {
		this.validateUserSystem(bbrUI);

		FeedbackManagerLocal manager = (FeedbackManagerLocal) getEJBHomes("FeedbackManagerLocal");
		return manager;
	}

	public FunctionalityManagerLocal getFunctionalityManagerLocal() throws BbrUserException, BbrSystemException {
		this.validateUserSystem();

		FunctionalityManagerLocal manager = (FunctionalityManagerLocal) getEJBHomes("FunctionalityManagerLocal");
		return manager;
	}

	public FunctionalityManagerLocal getFunctionalityManagerLocal(BbrUI bbrUI)
			throws BbrUserException, BbrSystemException {
		this.validateUserSystem(bbrUI);

		FunctionalityManagerLocal manager = (FunctionalityManagerLocal) getEJBHomes("FunctionalityManagerLocal");
		return manager;
	}

	public PublishingManagerLocal getPublishingManagerLocal(BbrUI bbrUI) throws BbrUserException, BbrSystemException {
		this.validateUserSystem(bbrUI);

		PublishingManagerLocal manager = (PublishingManagerLocal) getEJBHomes("PublishingManagerLocal");
		return manager;
	}

	public UserReportManagerLocal getUserReportManagerLocal() throws BbrUserException, BbrSystemException {
		return getUserReportManagerLocal((BbrUI) UI.getCurrent());
	}

	public UserReportManagerLocal getUserReportManagerLocal(BbrUI bbrUI) throws BbrUserException, BbrSystemException {
		this.validateUserSystem(bbrUI);

		UserReportManagerLocal manager = (UserReportManagerLocal) getEJBHomes("UserReportManagerLocal");
		return manager;
	}

	public RequestReportManagerLocal getRequestManagerLocal() throws BbrUserException, BbrSystemException {
		return getRequestManagerLocal((BbrUI) UI.getCurrent());
	}

	public RequestReportManagerLocal getRequestManagerLocal(BbrUI bbrUI) throws BbrUserException, BbrSystemException {
		this.validateUserSystem(bbrUI);

		RequestReportManagerLocal manager = (RequestReportManagerLocal) getEJBHomes("RequestReportManagerLocal");
		return manager;
	}

	public CompanyTypeReportManagerLocal getCompanyTypeReportManagerLocal(BbrUI bbrUI)
			throws BbrUserException, BbrSystemException {
		this.validateUserSystem(bbrUI);

		CompanyTypeReportManagerLocal manager = (CompanyTypeReportManagerLocal) getEJBHomes(
				"CompanyTypeReportManagerLocal");
		return manager;
	}

	public ContactB2BReportManagerLocal getContactB2BReportManagerLocal(BbrUI bbrUI)
			throws BbrUserException, BbrSystemException {
		this.validateUserSystem(bbrUI);

		ContactB2BReportManagerLocal manager = (ContactB2BReportManagerLocal) getEJBHomes(
				"ContactB2BReportManagerLocal");
		return manager;
	}

	// ****************************************************************************************
	// ENDING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	protected static UserEJBFactory getFactory() {
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

	private Long validateUserSystem() throws BbrUserException {

		Long result = null;

		if (UI.getCurrent() != null && UI.getCurrent() instanceof BbrUI
				&& ((BbrUI) UI.getCurrent()).getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME) != null) {
			result = this.validateUserSystem((BbrUI) UI.getCurrent());
		} else {
			if (UI.getCurrent() != null && UI.getCurrent() instanceof BbrUI) {
				AppUtils.getInstance().doLogOut(I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "US1000"),
						(BbrUI) UI.getCurrent());
			}
			throw new BbrUserException(BbrUserException.INVALID_SESSION,
					I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "US1000"));
		}

		return result;

	}

	@SuppressWarnings("unchecked")
	private Long validateUserSystem(BbrUI bbrUI) throws BbrUserException {

		Long result = null;

		if (bbrUI != null && bbrUI.getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME) != null) {
			SessionBean sessionBean = (SessionBean) bbrUI.getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);

			UserReportManagerLocal manager = (UserReportManagerLocal) getEJBHomes("UserReportManagerLocal");

			UserDTO userData = manager.getUserById(sessionBean.getUserId()).getUser();

			result = userData.getId();

			ValidateUserSessionCacheDAOLocal validateUserSessionDAO = (ValidateUserSessionCacheDAOLocal) getEJBHomes(
					PortalConstants.WEB_CLASSPATH_DAO, "ValidateUserSessionCacheDAOLocal");
			if (!userData.getSession().equals(sessionBean.getSessionId())) {
				validateUserSessionDAO.remove(sessionBean.getSessionId());
				throw new BbrUserException(BbrUserException.LOGIN_SAME_ACCOUNT,
						I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_SYSTEM, "US1100"));
			}
			validateActiveSessions(validateUserSessionDAO, manager, userData, bbrUI);
			// ENVIA AL KEYCLOAK UNA ACTUALIZACION DEL ULTIMO ACCESO SI EL USUARIO LLAMÓ UN REPORTE
			// SE HACE DE MANERA ASYNCRONA PARA NO AFECTAR LA PERFORMANCE DEL SITIO
			UpdateUserToken task = new UpdateUserToken(manager, sessionBean.getRefreshToken());

			threadpool.submit(task);

		} else {
			AppUtils.getInstance().doLogOut(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_SYSTEM, "US1000"),
					bbrUI);
			throw new BbrUserException(BbrUserException.INVALID_SESSION,
					I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_SYSTEM, "US1000"));
		}

		return result;

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
