package bbr.b2b.portal.modules.users.management;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.ItemClickListener;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.modules.users.management.profiles.FindUsersForProfileManagement;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.ProfileArrayResultDTO;
import bbr.b2b.users.report.classes.ProfileDTO;
import bbr.b2b.users.report.classes.SelfManagementProfileResultDTO;
import bbr.b2b.users.report.classes.SelfManagementUpdateUserInfoDTO;
import bbr.b2b.users.report.classes.UserDTO;
import bbr.b2b.users.report.classes.UsersResultDTO;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.constants.TrackingConstants;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrHSeparator;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.grid.renderer.DateRenderer;

public class ProfilesUsersManagement extends BbrModule implements BbrWorkExecutor
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants
	private static final long						serialVersionUID	= 4982315246309578782L;
	private static final String						FIRST_NAME			= "name";
	private static final String						LAST_NAME			= "lastname";
	private static final String						MAIL				= "email";
	private static final String						LOG_ID				= "logid";
	private static final String						STATE				= "active";
	private static final String						POSITION			= "position";
	private static final String						LAST_LOGIN			= "lastlogin";
	// Components
	private VerticalLayout							mainLayout			= null;
	private Button									btn_AddAdmins		= null;
	private Button									btn_RemoveAdmins	= null;
	private Label									lbl_LastUpdate		= null;
	private BbrAdvancedGrid<ProfileDTO>				dgd_Profiles		= null;
	private BbrAdvancedGrid<UserDTO>				dgd_Admins			= null;
	// Variables
	private BbrWork<ProfileArrayResultDTO>			profileReportWork	= null;
	private BbrWork<UsersResultDTO>					adminsReportWork	= null;
	private BbrWork<BaseResultDTO>					addAdminsWork		= null;
	private BbrWork<BaseResultDTO>					removeAdminsWork	= null;
	private BbrWork<SelfManagementProfileResultDTO>	getUpdateInfoWork	= null;
	private Boolean									trackReport			= true;
	private ProfileDTO								selectedProfile		= null;
	private List<UserDTO>							listOfAdmins		= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ProfilesUsersManagement(BbrUI bbrUIParent)
	{
		super(bbrUIParent);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	@Override
	public void initializeView()
	{
		// Encabezado Titulo

		Label lbl_ReportTitle = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "registered_admins_in_system"));
		lbl_ReportTitle.addStyleName("header_style");

		HorizontalLayout titleBar = new HorizontalLayout();
		titleBar.setWidth("100%");
		titleBar.addComponent(lbl_ReportTitle);
		titleBar.setComponentAlignment(lbl_ReportTitle, Alignment.MIDDLE_LEFT);
		titleBar.addStyleName("filter-toolbar");
		titleBar.setExpandRatio(lbl_ReportTitle, 1F);

		// Grilla Perfiles

		Label lbl_ProfilesGridTitle = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "provider_type_profiles"));
		lbl_ProfilesGridTitle.addStyleNames("bold_style");

		HorizontalLayout profilesTitleBar = new HorizontalLayout();
		profilesTitleBar.setWidth("100%");
		profilesTitleBar.setHeight("30px");
		profilesTitleBar.addComponent(lbl_ProfilesGridTitle);
		profilesTitleBar.setComponentAlignment(lbl_ProfilesGridTitle, Alignment.MIDDLE_CENTER);
		profilesTitleBar.setExpandRatio(lbl_ProfilesGridTitle, 1F);

		this.dgd_Profiles = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.dgd_Profiles.setSortable(false);
		this.initializeProfilesGridColumns();

		this.dgd_Profiles.addStyleName("report-grid");
		this.dgd_Profiles.setWidth("370px");
		this.dgd_Profiles.addItemClickListener((ItemClickListener<ProfileDTO> & Serializable) e -> this.profileRow_SelectedHandler(e));

		VerticalLayout profilesSpace = new VerticalLayout();
		profilesSpace.addComponents(profilesTitleBar, this.dgd_Profiles);
		profilesSpace.setExpandRatio(this.dgd_Profiles, 1F);
		profilesSpace.setWidth("400px");
		profilesSpace.setHeight("100%");
		profilesSpace.setMargin(false);
		profilesSpace.setSpacing(true);

		// Grilla Administradores

		this.dgd_Admins = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.dgd_Admins.setSortable(false);
		this.initializeAdminsGridColumns();

		this.dgd_Admins.addStyleName("report-grid");
		this.dgd_Admins.setSelectionMode(SelectionMode.MULTI);
		this.dgd_Admins.addSelectionListener((SelectionListener<UserDTO> & Serializable) e -> this.adminRow_SelectedHandler(e));
		this.dgd_Admins.setSizeFull();

		this.btn_AddAdmins = new Button("", EnumToolbarButton.ADD_PRIMARY.getResource());
		this.btn_AddAdmins.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "add_admin"));
		this.btn_AddAdmins.addClickListener((ClickListener & Serializable) e -> this.btn_AddAdmins_clickHandler(e));
		this.btn_AddAdmins.addStyleName("toolbar-button");

		this.btn_RemoveAdmins = new Button("", EnumToolbarButton.DEACTIVATE_ALTERNATIVE.getResource());
		this.btn_RemoveAdmins.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "remove_admin"));
		this.btn_RemoveAdmins.addClickListener((ClickListener & Serializable) e -> this.btn_RemoveAdmins_clickHandler(e));
		this.btn_RemoveAdmins.addStyleName("toolbar-button");

		Label lbl_AdminsGridTitle = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "profile_admins"));
		lbl_AdminsGridTitle.addStyleNames("bold_style");

		HorizontalLayout adminsTitleBar = new HorizontalLayout();
		adminsTitleBar.setWidth("100%");
		adminsTitleBar.addComponent(lbl_AdminsGridTitle);
		adminsTitleBar.setComponentAlignment(lbl_AdminsGridTitle, Alignment.MIDDLE_CENTER);
		adminsTitleBar.setExpandRatio(lbl_AdminsGridTitle, 1F);

		HorizontalLayout adminsButtons = new HorizontalLayout();
		adminsButtons.addStyleName("toolbar-layout");
		adminsButtons.setHeight("30px");
		adminsButtons.addComponents(this.btn_AddAdmins, this.btn_RemoveAdmins);
		adminsButtons.setSpacing(true);

		HorizontalLayout adminsToolBar = new HorizontalLayout();
		adminsToolBar.setWidth("100%");
		adminsToolBar.addComponents(adminsButtons, adminsTitleBar);
		adminsToolBar.setComponentAlignment(adminsButtons, Alignment.MIDDLE_LEFT);
		adminsToolBar.setExpandRatio(adminsTitleBar, 1F);
		adminsToolBar.setMargin(false);

		VerticalLayout adminsSpace = new VerticalLayout();
		adminsSpace.addComponents(adminsToolBar, this.dgd_Admins);
		adminsSpace.setExpandRatio(this.dgd_Admins, 1F);
		adminsSpace.setSizeFull();
		adminsSpace.setMargin(false);
		adminsSpace.setSpacing(true);

		HorizontalLayout dataGridsSpace = new HorizontalLayout();
		dataGridsSpace.addComponents(profilesSpace, new BbrHSeparator("5px"), adminsSpace);
		dataGridsSpace.setExpandRatio(adminsSpace, 1F);
		dataGridsSpace.setSizeFull();
		dataGridsSpace.setMargin(false);
		dataGridsSpace.setSpacing(false);

		// Texto Ultima Actualizacion

		this.lbl_LastUpdate = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_update_date") + ": ");

		HorizontalLayout labelSpace = new HorizontalLayout();
		labelSpace.addComponents(new BbrHSeparator("100%"), this.lbl_LastUpdate);
		labelSpace.setWidth("100%");
		labelSpace.setHeight("30px");
		labelSpace.setComponentAlignment(this.lbl_LastUpdate, Alignment.MIDDLE_RIGHT);

		// Main Layout

		this.mainLayout = new VerticalLayout();
		this.mainLayout.addStyleName("report-layout-no-filter");
		this.mainLayout.setSizeFull();
		this.mainLayout.setMargin(false);
		this.mainLayout.addComponents(titleBar, dataGridsSpace, labelSpace);
		this.mainLayout.setExpandRatio(dataGridsSpace, 1F);

		this.addComponent(this.mainLayout);

		this.updateAdminActionButtons();

		this.profileReportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		this.profileReportWork.addFunction(new Function<Object, ProfileArrayResultDTO>()
		{
			@Override
			public ProfileArrayResultDTO apply(Object t)
			{
				return executeService(ProfilesUsersManagement.this.getBbrUIParent());
			}
		});
		this.startWaiting();
		this.executeBbrWork(this.profileReportWork);
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				this.doUpdateProfileReport(result, sender);
				this.refreshLastUpdateInfo();
			}

			else if (triggerObject == this.dgd_Profiles)
			{
				this.doUpdateAdminsReport(result, sender);
			}

			else if ((triggerObject == this.btn_AddAdmins) || (triggerObject == this.btn_RemoveAdmins))
			{
				this.updateAdminsReportAfterAction(result, sender);
				this.refreshLastUpdateInfo();
			}

			else if (triggerObject == this.lbl_LastUpdate)
			{
				this.updateInfoLabel(result, sender);
			}
		}

		else
		{
			ProfilesUsersManagement senderReport = (ProfilesUsersManagement) sender;

			if (!senderReport.getBbrUIParent().hasAlertWindowOpen())
				senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));

			senderReport.stopWaiting();
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	private void btn_AddAdmins_clickHandler(ClickEvent event)
	{
		FindUsersForProfileManagement winFindUsers = new FindUsersForProfileManagement(this.getBbrUIParent(),this.listOfAdmins);
		winFindUsers.addBbrEventListener((BbrEventListener & Serializable) bbrEvent -> this.usersSelectedHandler(bbrEvent));
		winFindUsers.open(true, true, this);
	}

	private void btn_RemoveAdmins_clickHandler(ClickEvent event)
	{
		if ((this.dgd_Admins.getSelectedItems() != null) && (this.dgd_Admins.getSelectedItems().size() > 0))
		{
			UserDTO[] selectedUsers = (UserDTO[]) this.dgd_Admins.getSelectedItems().toArray(new UserDTO[0]);

			this.removeAdminsWork = new BbrWork<>(this, this.getBbrUIParent(), this.btn_RemoveAdmins);
			this.removeAdminsWork.addFunction(new Function<Object, BaseResultDTO>()
			{
				@Override
				public BaseResultDTO apply(Object t)
				{
					return executeRemoveAdminsService(ProfilesUsersManagement.this.getBbrUIParent(), selectedUsers);
				}
			});
			this.startWaiting();
			this.executeBbrWork(this.removeAdminsWork);
		}

		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
					I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "user_required"));
		}
	}

	private void profileRow_SelectedHandler(ItemClick<ProfileDTO> e)
	{
		if (e.getItem() != null)
		{
			// Usar item seleccionado para llamar al reporte de admins
			// por perfiles

			this.selectedProfile = e.getItem();

			this.adminsReportWork = new BbrWork<>(this, this.getBbrUIParent(), this.dgd_Profiles);
			this.adminsReportWork.addFunction(new Function<Object, UsersResultDTO>()
			{
				@Override
				public UsersResultDTO apply(Object t)
				{
					return executeAdminsService(ProfilesUsersManagement.this.getBbrUIParent(), ProfilesUsersManagement.this.selectedProfile);
				}
			});
			this.startWaiting();
			this.executeBbrWork(this.adminsReportWork);
		}
	}

	private void adminRow_SelectedHandler(SelectionEvent<UserDTO> e)
	{
		this.updateAdminActionButtons();
	}

	private void usersSelectedHandler(BbrEvent event)
	{
		if ((event != null) && (event.getResultObject() != null))
		{
			UserDTO[] users = (UserDTO[]) event.getResultObject();

			this.addAdminsWork = new BbrWork<>(this, this.getBbrUIParent(), this.btn_AddAdmins);
			this.addAdminsWork.addFunction(new Function<Object, BaseResultDTO>()
			{
				@Override
				public BaseResultDTO apply(Object t)
				{
					return executeAddAdminsService(ProfilesUsersManagement.this.getBbrUIParent(), users);
				}
			});
			this.startWaiting();
			this.executeBbrWork(this.addAdminsWork);

		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private ProfileArrayResultDTO executeService(BbrUI bbrUI)
	{
		ProfileArrayResultDTO result = null;

		try
		{
			// Start Tracking

			this.getTimingMngr().startTimer();
			result = EJBFactory.getUserEJBFactory().getFunctionalityManagerLocal(bbrUI).getProfilesForProviders();
		}

		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), this.getBbrUIParent());
		}

		catch (BbrSystemException e)
		{
			e.printStackTrace();
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

	private UsersResultDTO executeAdminsService(BbrUI bbrUI, ProfileDTO selectedProfile)
	{
		UsersResultDTO result = null;

		try
		{
			// Start Tracking

			this.getTimingMngr().startTimer();
			result = EJBFactory.getUserEJBFactory().getUserReportManagerLocal(bbrUI).getAdminUserRTByProfile(selectedProfile.getId());
		}

		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), this.getBbrUIParent());
		}

		catch (BbrSystemException e)
		{
			e.printStackTrace();
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

	private BaseResultDTO executeAddAdminsService(BbrUI bbrUI, UserDTO[] users)
	{
		BaseResultDTO result = null;

		Long[] usersIDs = (Long[]) Arrays.stream(users).map(UserDTO::getId).collect(Collectors.toList()).toArray(new Long[0]);

		SelfManagementUpdateUserInfoDTO updateInfo = new SelfManagementUpdateUserInfoDTO();
		updateInfo.setUsername(this.getUser().getName());
		updateInfo.setUserlastname(this.getUser().getLastName());
		updateInfo.setWhen(LocalDateTime.now());

		try
		{
			// Start Tracking

			this.getTimingMngr().startTimer();
			result = EJBFactory.getUserEJBFactory().getUserReportManagerLocal(this.getBbrUIParent()).doAddAdminRetail(usersIDs, this.selectedProfile.getId(), updateInfo);
		}

		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), this.getBbrUIParent());
		}

		catch (BbrSystemException e)
		{
			e.printStackTrace();
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

	private BaseResultDTO executeRemoveAdminsService(BbrUI bbrUI, UserDTO[] users)
	{
		BaseResultDTO result = null;

		Long[] usersIDs = (Long[]) Arrays.stream(users).map(UserDTO::getId).collect(Collectors.toList()).toArray(new Long[0]);

		SelfManagementUpdateUserInfoDTO updateInfo = new SelfManagementUpdateUserInfoDTO();
		updateInfo.setUsername(bbrUI.getUser().getName());
		updateInfo.setUserlastname(bbrUI.getUser().getLastName());
		updateInfo.setWhen(LocalDateTime.now());

		try
		{
			// Start Tracking

			this.getTimingMngr().startTimer();
			result = EJBFactory.getUserEJBFactory().getUserReportManagerLocal(bbrUI).deleteAdminRetailFromProfile(this.selectedProfile.getId(), usersIDs, updateInfo);
		}

		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), bbrUI);
		}

		catch (BbrSystemException e)
		{
			e.printStackTrace();
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

	private void doUpdateProfileReport(Object result, BbrWorkExecutor sender)
	{
		String errordescription = "";
		ProfilesUsersManagement senderReport = (ProfilesUsersManagement) sender;

		if (result != null)
		{
			ProfileArrayResultDTO reportResult = (ProfileArrayResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), !this.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				ProfileDTO[] profiles = reportResult.getProfiles();
				ListDataProvider<ProfileDTO> dataprovider = new ListDataProvider<>(Arrays.asList(profiles));

				this.dgd_Profiles.setDataProvider(dataprovider, false);

				// End Tracking

				if (senderReport.trackReport == true)
				{
					senderReport.trackEvent(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription());
				}
			}

			else
			{
				errordescription = BbrUtils.getInstance().substitute("{0} - {1} - Internal Error", error.getErrorCode(), error.getErrorMessage());
			}
		}

		if (errordescription.length() > 0 && senderReport.trackReport == true)
		{
			// Track Error
			senderReport.trackError(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription(), errordescription, null, this);
		}

		this.stopWaiting();
	}

	private void doUpdateAdminsReport(Object result, BbrWorkExecutor sender)
	{
		String errordescription = "";
		ProfilesUsersManagement senderReport = (ProfilesUsersManagement) sender;

		if (result != null)
		{
			UsersResultDTO reportResult = (UsersResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				UserDTO[] users = reportResult.getUserDTOs();
				ListDataProvider<UserDTO> dataprovider = new ListDataProvider<>(Arrays.asList(users));
				this.listOfAdmins = Arrays.asList(users);
				this.dgd_Admins.setDataProvider(dataprovider, false);

				this.updateAdminActionButtons();

				// End Tracking

				if (senderReport.trackReport == true)
				{
					senderReport.trackEvent(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription());
				}
			}

			else
			{
				errordescription = BbrUtils.getInstance().substitute("{0} - {1} - Internal Error", error.getErrorCode(), error.getErrorMessage());
			}
		}

		if (errordescription.length() > 0 && senderReport.trackReport == true)
		{
			// Track Error

			senderReport.trackError(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription(), errordescription, null, this);
		}

		this.stopWaiting();
	}

	private void updateAdminsReportAfterAction(Object result, BbrWorkExecutor sender)
	{
		String errordescription = "";
		ProfilesUsersManagement senderReport = (ProfilesUsersManagement) sender;

		if (result != null)
		{
			BaseResultDTO actionResult = (BaseResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(actionResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				// End Tracking

				if (senderReport.trackReport == true)
				{
					senderReport.trackEvent(TrackingConstants.ITEM_GENERIC_ACTION, senderReport.getBbrFilterDescription());
				}

				this.adminsReportWork = new BbrWork<>(this, this.getBbrUIParent(), this.dgd_Profiles);
				this.adminsReportWork.addFunction(new Function<Object, UsersResultDTO>()
				{
					@Override
					public UsersResultDTO apply(Object t)
					{
						return executeAdminsService(ProfilesUsersManagement.this.getBbrUIParent(), ProfilesUsersManagement.this.selectedProfile);
					}
				});
				this.executeBbrWork(this.adminsReportWork);
			}

			else
			{
				errordescription = BbrUtils.getInstance().substitute("{0} - {1} - Internal Error", error.getErrorCode(), error.getErrorMessage());
				this.stopWaiting();
			}
		}

		if (errordescription.length() > 0 && senderReport.trackReport == true)
		{
			// Track Error

			senderReport.trackError(TrackingConstants.ITEM_GENERIC_ACTION, senderReport.getBbrFilterDescription(), errordescription, null, this);
		}
	}

	private void initializeProfilesGridColumns()
	{
		this.dgd_Profiles.addColumn(ProfileDTO::getName)
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "profile"))
				.setDescriptionGenerator(p -> p.getDescription())
				.setWidthUndefined();

		this.dgd_Profiles
				.addCustomComponentColumn(p -> this.getSelfAssignableCheckBox(p), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_self_assignable"))
				.setStyleGenerator(p -> BbrAlignment.CENTER.getValue())
				.setWidth(100D);
	}

	private void initializeAdminsGridColumns()
	{
		this.dgd_Admins.addCustomColumn(UserDTO::getName, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_name"), true)
				.setId(FIRST_NAME)
				.setDescriptionGenerator(UserDTO::getName);

		this.dgd_Admins.addCustomColumn(UserDTO::getLastname, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_lastname"), true)
				.setId(LAST_NAME)
				.setDescriptionGenerator(UserDTO::getLastname);

		this.dgd_Admins.addCustomColumn(UserDTO::getEmail, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "email"), true)
				.setId(MAIL)
				.setDescriptionGenerator(UserDTO::getEmail)
				.setWidth(150D);

		this.dgd_Admins.addCustomColumn(UserDTO::getPersonalid, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_person_id"), true)
				.setId(LOG_ID)
				.setDescriptionGenerator(UserDTO::getPersonalid)
				.setWidth(120D);

		this.dgd_Admins.addCustomColumn(u -> stateDescriptionFunction(u), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "state"), true)
				.setId(STATE)
				.setDescriptionGenerator(u -> stateDescriptionFunction(u))
				.setStyleGenerator(i -> BbrAlignment.CENTER.getValue())
				.setWidth(120D);

		this.dgd_Admins.addCustomColumn(UserDTO::getPosition, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "position"), true)
				.setDescriptionGenerator(UserDTO::getPosition)
				.setId(POSITION)
				.setWidth(120D);

		this.dgd_Admins.addCustomColumn(UserDTO::getLastlogin, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_login_date"), true)
				.setRenderer(new DateRenderer())
				.setDescriptionGenerator(u -> u.getLastlogin() != null ? DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss").format(u.getLastlogin()) : "")
				.setStyleGenerator(i -> BbrAlignment.CENTER.getValue())
				.setId(LAST_LOGIN).setWidth(150D);
	}

	private CheckBox getSelfAssignableCheckBox(ProfileDTO profileItem)
	{
		CheckBox result = null;

		if (profileItem.getAutoasignable())
		{
			result = new CheckBox();
			result.setValue(true);
			result.setReadOnly(true);
			result.setEnabled(false);
		}

		return result;
	}

	private String stateDescriptionFunction(UserDTO user)
	{
		String result = "";

		if (user != null)
		{
			result = (user.getActive()) ? I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "active")
					: I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "inactive");
		}

		return result;
	}

	private void refreshLastUpdateInfo()
	{
		this.getUpdateInfoWork = new BbrWork<>(this, this.getBbrUIParent(), this.lbl_LastUpdate);
		this.getUpdateInfoWork.addFunction(new Function<Object, SelfManagementProfileResultDTO>()
		{
			@Override
			public SelfManagementProfileResultDTO apply(Object t)
			{
				return executeLastUpdateInfoService(ProfilesUsersManagement.this.getBbrUIParent());
			}
		});
		this.startWaiting();
		this.executeBbrWork(this.getUpdateInfoWork);
	}

	SelfManagementProfileResultDTO executeLastUpdateInfoService(BbrUI bbrUI)
	{
		SelfManagementProfileResultDTO result = null;

		try
		{
			result = EJBFactory.getUserEJBFactory().getRequestManagerLocal(bbrUI).getLastSelfManagementProfileUpdate();
		}

		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), bbrUI);
		}

		catch (BbrSystemException e)
		{
			e.printStackTrace();
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

	private void updateInfoLabel(Object result, BbrWorkExecutor sender)
	{
		ProfilesUsersManagement senderReport = (ProfilesUsersManagement) sender;
		if (result != null)
		{
			SelfManagementProfileResultDTO callResult = (SelfManagementProfileResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(callResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				if ((callResult != null) && (callResult.getSelfManagementUpdateUserInfoDTO() != null))
				{
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm");

					String newInfoLabel = I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_update_date")
							+ ": "
							+ callResult.getSelfManagementUpdateUserInfoDTO().getUsername()
							+ " "
							+ callResult.getSelfManagementUpdateUserInfoDTO().getUserlastname()
							+ " ("
							+ dtf.format(callResult.getSelfManagementUpdateUserInfoDTO().getWhen())
							+ ")";

					this.lbl_LastUpdate.setValue(newInfoLabel);
				}
			}
		}

		this.stopWaiting();
	}

	private void updateAdminActionButtons()
	{
		if (this.dgd_Admins != null)
		{
			Boolean areAdminsSelected = (!this.dgd_Admins.isEmpty())
					? ((this.dgd_Admins.getSelectedItems() != null) && !this.dgd_Admins.getSelectedItems().isEmpty()) : false;
			Boolean areProfilesSelected = (!this.dgd_Profiles.isEmpty())
					? ((this.dgd_Profiles.getSelectedItems() != null) && !this.dgd_Profiles.getSelectedItems().isEmpty()) : false;

			this.btn_RemoveAdmins.setEnabled(areAdminsSelected);
			this.btn_AddAdmins.setEnabled(areProfilesSelected && (this.selectedProfile != null) && !this.selectedProfile.getAutoasignable());
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
