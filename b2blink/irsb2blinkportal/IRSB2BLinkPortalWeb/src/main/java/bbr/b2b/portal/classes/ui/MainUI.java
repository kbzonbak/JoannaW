package bbr.b2b.portal.classes.ui;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.representations.AccessToken;

import com.vaadin.addon.responsive.Responsive;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServletService;
import com.vaadin.server.WrappedSession;
import com.vaadin.shared.Registration;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.beans.SessionBean;
import bbr.b2b.portal.classes.constants.BbrAppConstants;
import bbr.b2b.portal.classes.constants.BbrPublishingConstants;
import bbr.b2b.portal.classes.constants.FunctionalitiesCodes;
import bbr.b2b.portal.classes.constants.ModulesCodes;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.managers.FunctionalityMngr;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.RoleTypes;
import bbr.b2b.portal.classes.utils.users.UserManagerUtils;
import bbr.b2b.portal.classes.wrappers.customer.ControlPanelCardInfo;
import bbr.b2b.portal.classes.wrappers.management.ProviderContactOperationInfo;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.main.FeedbackWindow;
import bbr.b2b.portal.components.popups.WinPopup;
import bbr.b2b.portal.constants.BbrUtilsConstants;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.PortalConstants;
import bbr.b2b.portal.modules.walldiary.WallDiary;
import bbr.b2b.portal.utils.B2BSystemPropertiesUtil;
import bbr.b2b.users.report.classes.CompanyDTO;
import bbr.b2b.users.report.classes.CompanyResultDTO;
import bbr.b2b.users.report.classes.CompanyTypeResultDTO;
import bbr.b2b.users.report.classes.PopUpReportDataDTO;
import bbr.b2b.users.report.classes.PopUpReportResultDTO;
import bbr.b2b.users.report.classes.ProfileArrayResultDTO;
import bbr.b2b.users.report.classes.UserDTO;
import bbr.b2b.users.report.classes.UserInitParamDTO;
import bbr.b2b.users.report.classes.UserMergeInitParamDTO;
import bbr.b2b.users.report.classes.UserResultDTO;
import bbr.b2b.users.report.classes.UserTreeFunctionalityArrayResultDTO;
import bbr.b2b.users.report.classes.UserTreeFunctionalityDTO;
import cl.bbr.core.classes.basics.BbrButtonOption;
import cl.bbr.core.classes.messaging.BbrMessage;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.utilities.NumericManager;
import cl.bbr.core.components.basics.BbrMessageBox;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrTabContent;
import cl.bbr.core.components.basics.BbrTabSheet;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.widgets.bbrfailover.BBRFailoverReconnectExtension;
import cl.bbr.core.interfaces.BroadcastReceiver;

/**
 * @author DSU 2016-03
 */
/**
 * @author David
 *
 */
@Theme("irsb2blinkportalweb")
@Title("| Portal B2B Link |")
// @Push(transport=Transport.WEBSOCKET, value=PushMode.AUTOMATIC)
public class MainUI extends BbrUI implements BroadcastReceiver
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	// Constants
	private static final long							serialVersionUID				= -875588952092495723L;
	private static final String							KEYCLOAK_BBR_ROLE				= "bbr";
	private static final String							KEYCLOAK_RETAIL_ROLE			= "retail";
	// Components
	private MenuBar										menuBar							= null;
	private MenuBar										menuSystemUser					= null;
	private BbrTabSheet									bbrTabSheet						= null;
	private BbrTabContent								bbrTab_Module					= null;
	// Variables
	private HashMap<Integer, UserTreeFunctionalityDTO>	mapfunctionalities				= new HashMap<Integer, UserTreeFunctionalityDTO>();
	private UserTreeFunctionalityDTO[]					functionalities					= null;
	private Map<String, Runnable>						modulesToAttachOnSelected		= new LinkedHashMap<>();
	private Registration								registrationSelectedTabChange	= null;
	private FunctionalityMngr							functionalityMngr				= null;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> INTERFACE IMPLEMENTATIONS
	// ****************************************************************************************
	@Override
	public void receiveBroadcast(BbrMessage message)
	{
		access(new Runnable()
		{
			@Override
			public void run()
			{
				getMessagingEventMngr().processBbrMessage(message);
			}
		});
	}
	// ****************************************************************************************
	// ENDING SECTION ----> INTERFACE IMPLEMENTATIONS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	@Override
	protected void init(VaadinRequest request)
	{

		boolean islogged = false;
		try
		{
			islogged = this.initializeSessionBean(request);
		}
		catch (BbrUserException | BbrSystemException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		if (islogged)
		{
			// Inicializa Manager de funcionalidades
			functionalityMngr = new FunctionalityMngr();

			new Responsive(this);
			SessionBean sessionBean = (SessionBean) this.getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);

			Locale userLocale = new Locale("es", "ES");
			sessionBean.setLocale(userLocale);

			this.setSiteCode(this.getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "site_code"));
			this.setSiteVersion(this.getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "site_version"));
			this.setSessionUserBean(sessionBean, BbrUtilsConstants.SESSION_BEAN_NAME);
			this.setTrackingURL(B2BSystemPropertiesUtil.getProperty("tracking_url"));

			if (sessionBean != null && sessionBean.getUser() != null)
			{
				NumericManager.setGroupingSeparatorChar('.');
				NumericManager.setDecimalSeparatorChar(',');

				this.getPage().setTitle(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "main_title"));

				// Info para el Audita
				this.setSiteCode(this.getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "site_code"));
				this.setSiteVersion(this.getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "site_version"));
				this.setUser(AppUtils.getInstance().getBbrUserFromUserBean(sessionBean));

				// Header Layout
				final HorizontalLayout pnlHeader = new HorizontalLayout();
				pnlHeader.setWidth("100%");
				pnlHeader.setHeight("56px");
				pnlHeader.setStyleName("pnlHeader");
				String res_logo = this.getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "logo_header");
				Resource res = new ThemeResource(res_logo);
				Image image = new Image(null, res);
				image.setStyleName("logoHeader");
				String userName = (sessionBean != null) ? sessionBean.getUserFullName() : "N/A";
				Label lbl_UserName = new Label();
				lbl_UserName.setCaption(userName);
				lbl_UserName.setId("lblusername");

				boolean isPendingContacts = false;
				ProviderContactOperationInfo providerContactOperationInfo = new ProviderContactOperationInfo(this);
				isPendingContacts = providerContactOperationInfo
						.showPendingContactsPositions(true);

				// RECORDAR QUE ES SOLO isPendingContacts
				if (isPendingContacts)
				{
					this.menuBar = this.getApplicationMenu(FunctionalitiesCodes.USR_CPV_RESTRICT);
				}
				else
				{
					this.menuBar = this.getApplicationMenu(null);
				}
				if (this.menuBar != null)
				{
					this.menuBar.setWidth("99%");
					this.menuBar.setResponsive(true);
					this.menuBar.setStyleName("mainMenuBar");

					menuSystemUser = this.getSystemUserMenu();
					menuSystemUser.setStyleName("mainMenuBar");

					pnlHeader.addComponents(image, this.menuBar, menuSystemUser);
					pnlHeader.setExpandRatio(this.menuBar, 1F);

					// Working Area Layout
					final HorizontalLayout pnlWorkingArea = new HorizontalLayout();
					pnlWorkingArea.setSizeFull();
					pnlWorkingArea.setHeight(100, Unit.PERCENTAGE);

					bbrTabSheet = new BbrTabSheet();
					bbrTabSheet.setSizeFull();

					bbrTab_Module = new BbrTabContent();
					bbrTab_Module.setSizeFull();
					bbrTab_Module.setStyleName("tab-module-style");
					bbrTabSheet.setStyleName("main-tab-sheet");
					bbrTabSheet.addBbrTab(bbrTab_Module, "", true);
					WallDiary wallDiary = new WallDiary(this);
					wallDiary.setId("wallDiaryID");
					wallDiary.setBbrUIParent(this);
					wallDiary.initializeView();
					bbrTab_Module.addBbrModule(wallDiary, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_WALLDIARY, "wall_diary_tab"));
					pnlWorkingArea.addComponent(bbrTabSheet);
					pnlWorkingArea.addStyleName("bbr-working-area");

					// Footer Layout
					final HorizontalLayout pnlFooter = new HorizontalLayout();
					pnlFooter.setId("pnlFooter");
					pnlFooter.setSizeFull();
					pnlFooter.setWidth("100%");
					pnlFooter.setHeight("25px");
					pnlFooter.setStyleName("pnlFooter");
					Label lblFooter = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "app_footer"));
					lblFooter.setWidthUndefined();
					pnlFooter.addComponent(lblFooter);
					pnlFooter.setComponentAlignment(lblFooter, Alignment.MIDDLE_CENTER);

					// Main Layout
					final VerticalLayout pnlMain = new VerticalLayout();
					pnlMain.setMargin(false);
					pnlMain.setSizeFull();
					pnlMain.addComponents(pnlHeader, pnlWorkingArea, pnlFooter);
					pnlMain.setExpandRatio(pnlWorkingArea, 1F);

					// Your Opinion
					Button btn_YourOpinionLeft = new Button(I18NManager.getI18NString(this, BbrUtilsResources.RES_GENERIC, "your_opinion"));
					btn_YourOpinionLeft.setDescription(I18NManager.getI18NString(this, BbrUtilsResources.RES_GENERIC, "your_opinion"));
					btn_YourOpinionLeft.addStyleName(FeedbackWindow.LEFT_FIXED_BUTTON_STYLE);
					btn_YourOpinionLeft.setWidth("80px");
					btn_YourOpinionLeft.setHeight("30px");
					btn_YourOpinionLeft.addClickListener((e) -> new FeedbackWindow(this));

					AbsoluteLayout pnlMainFull = new AbsoluteLayout();
					pnlMainFull.addComponents(btn_YourOpinionLeft, pnlMain);

					// This BbrUI Content
					this.setContent(pnlMainFull);

					this.setCurrentSessionFileBeanName(BbrUtilsConstants.FILES_SESSION_BEAN_NAME);
					this.setBaseDownloadPath(AppUtils.getInstance().getFullServerContextPath(this) + BbrAppConstants.DOWNLOAD_PATH);

					this.showApplicationPopups();

					this.getReconnectDialogConfiguration().setDialogText(I18NManager.getI18NString(this, BbrUtilsResources.RES_SYSTEM, "US1300"));
					this.getReconnectDialogConfiguration().setDialogModal(true);

					this.getReconnectDialogConfiguration().setDialogTextGaveUp(I18NManager.getI18NString(this, BbrUtilsResources.RES_SYSTEM, "US1400"));

					this.getReconnectDialogConfiguration().setDialogGracePeriod(400);
					this.getReconnectDialogConfiguration().setReconnectAttempts(10);
					this.getReconnectDialogConfiguration().setReconnectInterval(5000);

					final BBRFailoverReconnectExtension failoverExtension = BBRFailoverReconnectExtension.addTo(this);
					failoverExtension.setTrySpareServersButtonCaption(I18NManager.getI18NString(this, BbrUtilsResources.RES_GENERIC, "module_refresh"));
					if (isPendingContacts)
					{
						this.redirectToContactsProvider_ClickHandler();
					}
				}

			}
			else
			{
				String loginUrl = this.getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "login");
				this.goToURL(loginUrl);
			}
		}

	}

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************
	void menuClick_handler(int selectedItem)
	{
		if (this != null)
		{
			openModuleTabByMap(selectedItem);
		}
	}

	private void redirectToContactsProvider_ClickHandler()
	{
		this.showModule(ModulesCodes.USR_CPV);
	}

	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// ENDING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private MenuBar getSystemUserMenu()
	{
		MenuBar result = null;
		SessionBean sessionBean = (SessionBean) this.getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
		if (sessionBean != null)
		{
			result = new MenuBar();
			MenuItem mainItem = result.addItem(sessionBean.getUserFullName(), VaadinIcons.USER, null);

			mainItem.addItem(I18NManager.getI18NString(BbrUtilsResources.RES_MENU, "close_session"), VaadinIcons.CLOSE,
					(Command & Serializable) e -> invalidateSession(sessionBean.getRefreshToken()));
		}

		return result;
	}

	private MenuBar getApplicationMenu(String code)
	{
		MenuBar bar = null;
		SessionBean sessionBean = (SessionBean) this.getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
		if (sessionBean != null)
		{

			UserTreeFunctionalityArrayResultDTO result;
			try
			{
				if (code != null)
				{
					// Funcionalidad de perfil contacto proveedor
					result = EJBFactory.getUserEJBFactory().getFunctionalityManagerLocal().getTreeFunctionalitiesByProfileCode(code);
				}
				else
				{
					// Funcionalidades del usuario
					result = EJBFactory.getUserEJBFactory().getFunctionalityManagerLocal().getTreeFunctionalityByAppAndUser(BbrAppConstants.B2B_PORTAL,
							sessionBean.getUserId());
				}

				functionalities = result.getFunctionalityDTOs();
				functionalityMngr.initializeFunctionalitiesMap(functionalities);
				UserTreeFunctionalityDTO root = null;
				for (UserTreeFunctionalityDTO item : functionalities)
				{
					if (item.getParentkey() == 0)
					{
						root = item;
						break;
					}
				}

				bar = new MenuBar();
				root.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MENU, root.getName()));
				functionalityMngr.setRootFunctionality(root);

				populateMenuBar(bar, null, root.getId());

				ProfileArrayResultDTO resultProfile = EJBFactory.getUserEJBFactory().getFunctionalityManagerLocal().getAllProfileAssigedToUser(sessionBean.getUserId());
				if (resultProfile.getProfiles().length == 0)
				{
					this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
							I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "user_whitout_profile"),
							null,
							() -> invalidateSession(sessionBean.getRefreshToken()));
				}

			}
			catch (BbrUserException e)
			{
				AppUtils.getInstance().doLogOut(e.getMessage(), this);
			}
			catch (BbrSystemException e)
			{
				e.printStackTrace();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return bar;
	}

	/**
	 * Método recursivo para obtener todos los elementos de la bara de menú.
	 * 
	 * @param bar
	 * @param item
	 * @param parentId
	 */
	private void populateMenuBar(MenuBar bar, MenuItem item, long parentId)
	{
		UserTreeFunctionalityDTO[] functionalities = functionalityMngr.getFuntionalitiesByParenId(parentId);
		if (functionalities != null)
		{
			for (UserTreeFunctionalityDTO node : functionalities)
			{
				if (node.getParentkey().equals(parentId))
				{
					String description = I18NManager.getI18NString(BbrUtilsResources.RES_MENU, node.getName());

					node.setDescription(description);

					if (item == null)
					{
						MenuItem itemnode = bar.addItem(node.getDescription(), null);

						mapfunctionalities.put(itemnode.getId(), node);
						populateMenuBar(bar, itemnode, node.getId());
					}
					else
					{
						MenuItem itemnode = item.addItem(node.getDescription(), null);

						mapfunctionalities.put(itemnode.getId(), node);
						if (node.isTerminal() || node.isHasfunctionalities())
						{
							if (node.isEnabled())
							{
								itemnode.setCommand(new Command()
								{
									private static final long serialVersionUID = -6517556624741148864L;

									@Override
									public void menuSelected(MenuItem selectedItem)
									{
										menuClick_handler(selectedItem.getId());
										selectedItem.setStyleName("menuItem");
									}
								});
							}
							else
							{
								itemnode.setEnabled(false);
							}

						}
						else
						{
							populateMenuBar(bar, itemnode, node.getId());
						}
					}
				}
			}
		}
	}

	private PopUpReportDataDTO[] getPopUpsById(Long moduleID)
	{
		PopUpReportDataDTO[] result = null;
		SessionBean sessionBean = (SessionBean) this.getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
		if (sessionBean != null)
		{
			try
			{
				PopUpReportResultDTO popups = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this)
						.getActivePopUpByUserAndFunctionality(sessionBean.getUserId(), moduleID);
				result = popups.getPopupdata();
			}
			catch (BbrUserException e)
			{
				AppUtils.getInstance().doLogOut(e.getMessage(), this);
			}
			catch (BbrSystemException e)
			{
				e.printStackTrace();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return result;
	}

	private void openModuleTabByMap(int selectedItem)
	{
		String code = mapfunctionalities.get(selectedItem).getName();
		Long id = mapfunctionalities.get(selectedItem).getId();
		openModulePopupsById(code, id);
	}

	private void openModulePopupsById(String code, Long id)
	{
		String currentCode = null;
		boolean isModuleBloqued = false;
		PopUpReportDataDTO[] popups = this.getPopUpsById(id);

		isModuleBloqued = Arrays.stream(popups).filter(currentPopup -> currentPopup.getCpcode().equalsIgnoreCase(BbrPublishingConstants.POPUP_MODE_LINK)
				|| currentPopup.getCpcode().equalsIgnoreCase(BbrPublishingConstants.POPUP_MODE_BLOQUEA)
				|| currentPopup.getCpcode().equalsIgnoreCase(BbrPublishingConstants.POPUP_MODE_MODULE)).count() > 0;

		if (popups != null && popups.length > 0)
		{
			for (int i = 0; i < popups.length; i++)
			{
				PopUpReportDataDTO currentPopup = popups[i];
				if (currentPopup.getCpcode().equalsIgnoreCase(BbrPublishingConstants.POPUP_MODE_LINK))
				{
					this.goToNewURL(currentPopup.getLink());
				}
				else if (currentPopup.getCpcode().equalsIgnoreCase(BbrPublishingConstants.POPUP_MODE_BLOQUEA))
				{
					// (AUDITORIA) AL ACEPTAR ABRE EL MODULO Y AL CERRAR LOGOUT
					openModulesPopup(currentPopup, code, isModuleBloqued);
				}
				else if (currentPopup.getCpcode().equalsIgnoreCase(BbrPublishingConstants.POPUP_MODE_MODULE))
				{
					openModulesPopup(currentPopup);
				}
				else if (currentPopup.getCpcode().equalsIgnoreCase(BbrPublishingConstants.POPUP_MODE_NORMAL) ||
						currentPopup.getCpcode().equalsIgnoreCase(BbrPublishingConstants.POPUP_MODE_PERMITE))
				{
					if (!code.equals(BbrAppConstants.B2B_PORTAL) && !code.equals(currentCode) && !isModuleBloqued)
					{
						this.showModule(code);
						currentCode = code;
					}
					openModulesPopup(currentPopup);
				}
			}
		}
		else
		{
			if (!code.equals(BbrAppConstants.B2B_PORTAL))
			{
				this.showModule(code);
			}
		}
	}

	private void openModulesPopup(PopUpReportDataDTO popups)
	{
		WinPopup winPopup = new WinPopup(this, popups);
		winPopup.initializeView();
		winPopup.open(true);
	}

	private void openModulesPopup(PopUpReportDataDTO popups, String code, boolean isModuleBloqued)
	{
		WinPopup winPopup = new WinPopup(this, popups);
		winPopup.setCodeModuleSelected(code);
		winPopup.initializeView();
		winPopup.open(true);
	}

	@Override
	public void showModule(String code)
	{
		if (code != null)
		{
			BbrModule module = functionalityMngr.getModuleByCode(code, this);

			if (module != null)
			{
				bbrTab_Module = new BbrTabContent();
				bbrTab_Module.setSizeFull();
				bbrTab_Module.setStyleName("tab-module-style");
				bbrTabSheet.addBbrTab(bbrTab_Module, "", true);
				bbrTab_Module.addBbrModule(module, module.getModuleName(), true);
				bbrTabSheet.setBbrSelectedTab(bbrTab_Module);
			}
			else
			{
				Notification.show(code);
			}
		}
	}

	public void showModuleFromCardInfo(String code, ControlPanelCardInfo cardInfo)
	{
		BbrModule module = FunctionalityMngr.getInstance().getModuleByCodeAndCardInfo(code, this, cardInfo);
		this.getNewTabByModule(module);
	}

	public void showModuleFromScoreCard(String code, String state)
	{
		BbrModule module = FunctionalityMngr.getInstance().getModuleByCodeAndState(code, this, state);
		this.getNewTabByModule(module);
	}

	public void showModuleFromAvailableStock(String code, String codeState)
	{
		BbrModule module = FunctionalityMngr.getInstance().getModuleByAvailableStockCodeState(code, this, codeState);
		this.getNewTabByModule(module);
	}

	public void showModuleFromAlertReport(String code, String codeState)
	{
		BbrModule module = FunctionalityMngr.getInstance().getModuleByAlertReportCodeState(code, this, codeState);
		this.getNewTabByModule(module);
	}

	public void showModuleFromProductWithoutHomologation(String code, String codeState)
	{
		BbrModule module = FunctionalityMngr.getInstance().getModuleByProductWithoutHomologation(code, this, codeState);
		this.getNewTabByModule(module);
	}
	
	public void showModuleFromAvailabilityReport(String code, String clientName)
	{
		BbrModule module = FunctionalityMngr.getInstance().getModuleByAvailabilityReport(code, this, clientName);
		this.getNewTabByModule(module);
	}

	private void getNewTabByModule(BbrModule module)
	{
		bbrTab_Module = new BbrTabContent();
		bbrTab_Module.setSizeFull();
		bbrTab_Module.setStyleName("tab-module-style");
		bbrTabSheet.addBbrTab(bbrTab_Module, "", true);
		bbrTab_Module.addBbrModule(module, module.getModuleName(), true);
		bbrTabSheet.setBbrSelectedTab(bbrTab_Module);
	}

	private void showApplicationPopups()
	{
		SessionBean sessionBean = (SessionBean) this.getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
		if (sessionBean != null)
		{
			try
			{
				openModulePopupsById(functionalityMngr.getRootFunctionality().getName(), functionalityMngr.getRootFunctionality().getId());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private boolean initializeSessionBean(VaadinRequest request) throws BbrUserException, BbrSystemException
	{

		// SE DEBE ANALIZAR EL REQUEST, PARA VER SI TIENE UNA SESIÓN
		// KEYCLOAK VALIDA
		// SI ES PRIMERA VEZ QUE VIENE UN REQUEST SE DEBE CREAR UN SESSION
		// BEAN
		boolean isLogged = false;
		UserDTO user = null;
		WrappedSession session = null;
		String clienId = System.getProperty("keycloak.clientid");

		KeycloakPrincipal<?> principal = (KeycloakPrincipal<?>) request.getUserPrincipal();

		RefreshableKeycloakSecurityContext securityContext = (RefreshableKeycloakSecurityContext) principal.getKeycloakSecurityContext();

		if (principal != null)
		{
			try
			{
				if (principal.getKeycloakSecurityContext().getToken().isActive())
				{
					String email = principal.getKeycloakSecurityContext().getToken().getEmail();

					// ALMACENA REFRESHTOKEN
					session = request.getWrappedSession(false);

					session.setAttribute(BbrAppConstants.REFRESH_TOKEN, securityContext.getRefreshToken());

					// LOGOUT
					// SE ELIMINA LA SESION ACTUAL DE KEYCLOAK POR DUPLICACION Y
					// SE REDIRIGE AL LOGIN
					if (request.getParameter("logout") != null && Boolean.valueOf(request.getParameter("logout")))
					{

						if (!this.hasAlertWindowOpen())
						{
							this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"),
									I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "US1100"), null,
									() -> invalidateSession(securityContext.getRefreshToken()));

						}
						isLogged = false;
					}
					else
					{

						// TRAE USUARIO POR LOGID QUE CORRESPONDE AL CORREO
						UserResultDTO userResult = EJBFactory.getUserOpenEJBFactory().getUserByLogId(email);
						if (userResult != null)
						{
							// En dependencia de si vienen parametros, es que se
							// escogen mensajes personalizados o no
							if (userResult.getUser() != null)
							{
								// USUARIO EXISTE EN BD INTERNA
								user = userResult.getUser();

								// VALIDA QUE SUS DATOS NO HAYAN CAMBIADO SI
								// CAMBIARON, SE ACTUALIZA LA BDUI
								UserResultDTO mergeResult = doMergeUserAttributes(principal, user, clienId);

								if (mergeResult.getStatuscode().equals("0"))
								{

									user = mergeResult.getUser();

									// SI LA SESION ES NUEVA, NO EXISTE O BIEN
									// NO TIENE ASOCIADO EL SESSIONBEAN SE CREA
									// UNA NUEVA Y SE LE ASIGNA EL SESSIONBEAN
									if (session == null || session.isNew() || session.getAttribute(BbrUtilsConstants.SESSION_BEAN_NAME) == null
											|| ((SessionBean) session.getAttribute(BbrUtilsConstants.SESSION_BEAN_NAME)).getUserId().equals(0L))

									{

										CompanyResultDTO company = EJBFactory.getUserOpenEJBFactory().findCompanyById(user.getEmkey());
										CompanyTypeResultDTO companytype = EJBFactory.getUserOpenEJBFactory()
												.findCompanyTypeById(company.getCompanyDTO().getTekey());

										String enterpriseCode = (company != null && company.getCompanyDTO() != null)
												? company.getCompanyDTO().getRut() : "";

										String enterpriseName = (company != null && company.getCompanyDTO() != null)
												? company.getCompanyDTO().getName() : "";

										Long enterpriseId = (company != null && company.getCompanyDTO() != null)
												? company.getCompanyDTO().getId() : -1L;

										session = request.getWrappedSession(true);
										user.setSession(session.getId());
										user.setLastlogin(LocalDateTime.now());

										Locale userLocale = new Locale("es");
										SessionBean sessionBean = new SessionBean();
										sessionBean.setCompanyType(companytype.getCompanyTypeResultDTO());
										sessionBean.setLogged(true);
										sessionBean.setUser(user);
										sessionBean.setSessionId(session.getId());
										sessionBean.setEmailUser(user.getEmail());
										sessionBean.setUserId(user.getId());
										sessionBean.setUserLastName(user.getLastname());
										sessionBean.setUserName(user.getName());
										sessionBean.setFirstTravelToUser(true);
										sessionBean.setEnterpriseId(enterpriseId);
										sessionBean.setEnterpriseCode(enterpriseCode);
										sessionBean.setEnterpriseName(enterpriseName);

										sessionBean.setLocale(userLocale);
										sessionBean.setAccessToken(principal.getKeycloakSecurityContext().getTokenString());
										sessionBean.setRefreshToken(securityContext.getRefreshToken());

										this.setSessionUserBean(sessionBean, BbrUtilsConstants.SESSION_BEAN_NAME);
										this.doLogInfo(BbrUtils.getInstance().substitute(BbrAppConstants.SESSION_CREATED + " {0} : {1}",
												user.getSession(), sessionBean.getUserFullName()));

										// CONSULTA SI EL USUARIO ESTA ACTIVO EN
										// EL B2B
										if (user.getActive())
										{
											EJBFactory.getUserOpenEJBFactory().updateUser(user);
											isLogged = true;
										}
										else
										{

											// DEBO DESLOGUEARLO
											this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"),
													I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U5026P"), null,
													() -> invalidateSession(securityContext.getRefreshToken()));

											isLogged = false;
										}
									}
									else
									{
										// VALIDA QUE LA SESION SEA LA MISMA
										// ALMACENADA EN/ LA BD
										SessionBean sessionBean = (SessionBean) session.getAttribute(BbrUtilsConstants.SESSION_BEAN_NAME);
										isLogged = true;
										if (!user.getSession().equals(sessionBean.getSessionId()))
										{

											// EL USUARIO NO EXISTE SE ELIMINA
											// SESION DEL KEYCLOAK
											this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"),
													I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U101"), null,
													() -> invalidateSession(securityContext.getRefreshToken()));

											isLogged = false;
										}
									}
								}
							}
							else
							{

								// VALIDA EL TIPO DE USUARIO
								UserResultDTO userResultDTO = null;
								UserDTO newUser = null;
								Set<String> roles = principal.getKeycloakSecurityContext().getToken().getResourceAccess(clienId).getRoles();

								if (roles.contains(RoleTypes.RETAIL.getRole()))
								{
									// SE CREA UN USUARIO RETAIL CENCOSUD
									newUser = getUserDTOFromSession(principal, RoleTypes.RETAIL.getRole());

								}
								else if (roles.contains(RoleTypes.BBR.getRole()))
								{
									// SE CREA UN USUARIO BBR
									newUser = getUserDTOFromSession(principal, RoleTypes.BBR.getRole());
								}

								// REALIZA LA CREACION DE USUARIO Y SESSIONBEAN
								if (roles.contains(RoleTypes.RETAIL.getRole()) || roles.contains(RoleTypes.BBR.getRole()))
								{
									UserInitParamDTO userInitParam = new UserInitParamDTO();
									userInitParam.setUser(newUser);
									userInitParam.setAccessToken(principal.getKeycloakSecurityContext().getTokenString());
									// MRI 25-03-2019: siempre desde el flujo de
									// creación de user por login, el adminuser
									// para el audita se setea en null
									userInitParam.setUseradmin(null);
									userInitParam.setExternalRetail(UserManagerUtils.getIsExternalRetail());
									userInitParam.setLocale(this.getLocale());

									userResultDTO = EJBFactory.getUserOpenEJBFactory().addBasicUser(userInitParam);

									if (userResultDTO.getStatuscode().equals("0"))
									{

										user = userResultDTO.getUser();

										CompanyResultDTO company = EJBFactory.getUserOpenEJBFactory().findCompanyById(user.getEmkey());
										CompanyTypeResultDTO companytype = EJBFactory.getUserOpenEJBFactory()
												.findCompanyTypeById(company.getCompanyDTO().getTekey());

										String enterpriseCode = (company != null && company.getCompanyDTO() != null)
												? company.getCompanyDTO().getRut() : "";

										String enterpriseName = (company != null && company.getCompanyDTO() != null)
												? company.getCompanyDTO().getName() : "";

										Long enterpriseId = (company != null && company.getCompanyDTO() != null)
												? company.getCompanyDTO().getId() : -1L;

										// CREA LA SESION
										session = request.getWrappedSession(true);

										user.setSession(session.getId());

										Locale userLocale = new Locale("es");
										SessionBean sessionBean = new SessionBean();

										sessionBean.setLogged(true);
										sessionBean.setCompanyType(companytype.getCompanyTypeResultDTO());
										sessionBean.setUser(user);
										sessionBean.setSessionId(session.getId());
										sessionBean.setEmailUser(user.getEmail());
										sessionBean.setUserId(user.getId());
										sessionBean.setUserLastName(user.getLastname());
										sessionBean.setUserName(user.getName());
										sessionBean.setFirstTravelToUser(true);
										sessionBean.setEnterpriseId(enterpriseId);
										sessionBean.setEnterpriseCode(enterpriseCode);
										sessionBean.setEnterpriseName(enterpriseName);

										sessionBean.setLocale(userLocale);
										sessionBean.setAccessToken(principal.getKeycloakSecurityContext().getTokenString());
										sessionBean.setRefreshToken(securityContext.getRefreshToken());
										if (!user.getActive())
											user.setActive(true);

										EJBFactory.getUserOpenEJBFactory().updateUser(user);

										this.setSessionUserBean(sessionBean, BbrUtilsConstants.SESSION_BEAN_NAME);
										this.doLogInfo(BbrUtils.getInstance().substitute(BbrAppConstants.SESSION_CREATED + " {0} : {1}",
												user.getSession(), sessionBean.getUserFullName()));

										isLogged = true;
									}
									else
									{
										this.errorMessageBox(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"),
												I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, userResultDTO.getStatuscode()),
												securityContext.getRefreshToken());

										isLogged = false;
									}
								}
								else
								{
									// EL USUARIO NO EXISTE Y DE TIPO PROVEEDOR
									// SE ELIMINA SESION DEL KEYCLOAK
									this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"),
											I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, userResult.getStatuscode()), null,
											() -> invalidateSession(securityContext.getRefreshToken()));

									isLogged = false;
								}
							}
						}
					}

				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U101"), null, () -> invalidateSession(securityContext.getRefreshToken()));

				isLogged = false;
			}
		}

		return isLogged;
	}

	private UserResultDTO doMergeUserAttributes(KeycloakPrincipal<?> principal, UserDTO user, String clientid) throws BbrUserException, BbrSystemException
	{
		Set<String> roles = principal.getKeycloakSecurityContext().getToken().getResourceAccess(clientid).getRoles();
		String role = "";
		if (roles.contains(RoleTypes.RETAIL.getRole()))
			role = RoleTypes.RETAIL.getRole();

		else if (roles.contains(RoleTypes.BBR.getRole()))
			role = RoleTypes.BBR.getRole();
		UserDTO userDTO = getUserDTOFromSession(principal, role);
		userDTO.setId(user.getId());

		UserMergeInitParamDTO userMergeInitParam = new UserMergeInitParamDTO();
		userMergeInitParam.setUser(userDTO);
		userMergeInitParam.setExternalRetail(UserManagerUtils.getIsExternalRetail());
		UserResultDTO mergeResult = EJBFactory.getUserOpenEJBFactory().doMergeUserAttributes(userMergeInitParam);

		return mergeResult;
	}

	private UserDTO getUserDTOFromSession(KeycloakPrincipal<?> principal, String role) throws BbrUserException, BbrSystemException
	{
		UserDTO user = new UserDTO();
		LocalDateTime now = LocalDateTime.now();
		CompanyResultDTO companyRsult = null;
		CompanyDTO company = null;

		AccessToken token = principal.getKeycloakSecurityContext().getToken();

		user.setActive(false);
		user.setCreation(now);
		user.setEmail(token.getEmail());
		user.setId(-1L);
		user.setLastaccess(now);
		user.setLastlogin(now);
		user.setLastname(token.getFamilyName());
		user.setLastupdate(now);
		user.setLogid(token.getEmail());
		user.setName(token.getGivenName());
		user.setPhone(token.getPhoneNumber());
		// Campos Custom - Cargo y DNI
		String dni = " ";
		String position = "Sin Cargo";
		Map<String, Object> otherClaims = token.getOtherClaims();

		switch (role)
		{
			case KEYCLOAK_BBR_ROLE:
				companyRsult = EJBFactory.getUserOpenEJBFactory().findCompanyById(PortalConstants.ID_BBR);
				if (companyRsult != null)
				{
					company = companyRsult.getCompanyDTO();
					if (otherClaims.containsKey("employeeNumber"))
					{
						dni = String.valueOf(otherClaims.get("employeeNumber"));
					}
					if (otherClaims.containsKey("title"))
					{
						position = String.valueOf(otherClaims.get("title"));
					}
				}
				break;
			// revisa caso retail
			case KEYCLOAK_RETAIL_ROLE:
				companyRsult = EJBFactory.getUserOpenEJBFactory().findCompanyById(PortalConstants.ID_RETAIL);
				if (companyRsult != null)
				{
					company = companyRsult.getCompanyDTO();
					if (otherClaims.containsKey("dni"))
					{
						dni = String.valueOf(otherClaims.get("dni"));
					}
					if (otherClaims.containsKey("groupsid"))
					{
						position = String.valueOf(otherClaims.get("groupsid"));
					}
				}

				break;
			default:
				break;
		}

		user.setEmkey(company == null ? null : company.getId());
		user.setPersonalid(dni);
		user.setPosition(position);

		user.setSession("");

		return user;
	}

	public void invalidateSession(String regreshToken)
	{
		Object sessionBeanObj = this.getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
		SessionBean sessionBean = (sessionBeanObj == null) ? new SessionBean() : (SessionBean) this.getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
		this.doLogInfo(BbrUtils.getInstance().substitute(BbrAppConstants.SESSION_DESTROYED + " {0} : {1}", sessionBean.getSessionId(), sessionBean.getUserFullName()));

		String loginUrl = this.getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "main");
		this.goToURL(loginUrl);

		UI.getCurrent().getSession().close();
		UI.getCurrent().getSession().getSession().invalidate();
		try
		{
			VaadinServletService.getCurrentServletRequest().logout();
		}
		catch (ServletException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void errorMessageBox(String caption, String message, String regreshToken)
	{
		BbrMessageBox.createError(this)
				.withCloseButton(() -> invalidateSession(regreshToken), BbrButtonOption.CloseOnClick.closeOnClick(true))
				.withCaption(caption)
				.withHtmlMessage(message)
				.withWidthForAllButtons("100px")
				.open();
	}

	public void onChangeSelectedTab(String bbrTabCaption, Runnable runnable)
	{
		modulesToAttachOnSelected.put(bbrTabCaption, runnable);
		if (registrationSelectedTabChange != null)
		{
			registrationSelectedTabChange.remove();
		}
		registrationSelectedTabChange = bbrTabSheet.addSelectedTabChangeListener((SelectedTabChangeListener & Serializable) e ->
		{
			String keySelected = e.getTabSheet().getSelectedTab().getCaption();

			if (e.isUserOriginated() && modulesToAttachOnSelected.containsKey(keySelected))
			{
				modulesToAttachOnSelected.get(keySelected).run();
			}

		});
	}

	public String getSelectedFuncionalityNameFromUI()
	{
		return !this.bbrTabSheet.getSelectedBbrTabContent().getBbrModule().getModuleName().isEmpty() ? this.bbrTabSheet.getSelectedBbrTabContent().getBbrModule().getModuleName()
				: I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_WALLDIARY, "wall_diary_tab");
	}

	public void showSelectedFuncionalityCodeFromUI(String code)
	{
		if (code != null)
		{
			this.showModule(code);
		}
	}

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
