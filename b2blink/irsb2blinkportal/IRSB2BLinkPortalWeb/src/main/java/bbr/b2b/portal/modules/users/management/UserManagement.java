package bbr.b2b.portal.modules.users.management;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.SortEvent;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.components.grid.ItemClickListener;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.constants.FunctionalitiesCodes;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.jms.constants.MessageTopics;
import bbr.b2b.portal.classes.managers.FunctionalityMngr;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.BbrMessagesBoxUtils;
import bbr.b2b.portal.classes.utils.app.RoleTypes;
import bbr.b2b.portal.classes.utils.users.UserManagerUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.filters.management.UserManagementFilter;
import bbr.b2b.portal.components.modules.users.management.users.AskType;
import bbr.b2b.portal.components.modules.users.management.users.CopyProfiles;
import bbr.b2b.portal.components.modules.users.management.users.CreateBasicUser;
import bbr.b2b.portal.components.modules.users.management.users.EditUser;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyTypeArrayResultDTO;
import bbr.b2b.users.report.classes.CompanyTypeDTO;
import bbr.b2b.users.report.classes.UserActivateInitParamDTO;
import bbr.b2b.users.report.classes.UserDTO;
import bbr.b2b.users.report.classes.UserDataDTO;
import bbr.b2b.users.report.classes.UserReportDataDTO;
import bbr.b2b.users.report.classes.UserReportInitParamDTO;
import bbr.b2b.users.report.classes.UserResultDTO;
import cl.bbr.core.classes.basics.BbrPage;
import cl.bbr.core.classes.basics.BbrUser;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.constants.DownloadFormats;
import cl.bbr.core.classes.constants.TrackingConstants;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.classes.events.BbrMessageEvent;
import cl.bbr.core.classes.events.BbrMessageEventListener;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.grid.renderer.DateRenderer;
import cl.bbr.core.components.paging.BbrPagingManager;
import cl.bbr.core.components.widgets.bbrpopupbutton.BbrPopupButton;

public class UserManagement extends BbrModule implements BbrWorkExecutor
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	// Constants
	private static final long	serialVersionUID	= 7027502352762624910L;
	private static final String	USERNAME			= "username";
	private static final String	LASTNAME			= "lastname";
	private static final String	EMAIL				= "email";
	private static final String	PERSON_ID			= "rut";
	private static final String	STATE				= "stateindicator";
	private static final String	USER_TYPE			= "usertype";
	private static final String	COMPANY_CODE		= "companycode";
	private static final String	COMPANY_NAME		= "companyname";
	private static final String	POSITION			= "position";
	private static final String	CREATION_DATE		= "creationdate";
	private static final String	LAST_UPDATE			= "lastupdate";
	private static final String	LAST_LOGIN			= "lastlogin";

	private enum MultipleUserOperation
	{
		ACTIVATE_USERS, DEACTIVATE_USERS, CHANGE_USERS_PASSWORD, REMOVE_USERS;
	}

	private static final int					FILTER_TYPE_SELECTED			= 2;
	private final String						LOG_ID_FIELD					= "logid";
	private final String						DEFAULT_SORT_FIELD				= USERNAME;
	private final int							DEFAULT_PAGE_NUMBER				= 1;
	private final int							MAX_ROWS_NUMBER					= 50;
	// Components
	private BbrAdvancedGrid<UserDataDTO>		dgd_Users						= null;
	private BbrPagingManager					pagingManager					= null;
	private Button								btn_EditUser					= null;
	private Button								btn_CreateUserLike				= null;
	private Button								btn_CopyProfilesAndProviders	= null;
	private Button								btn_ActivateUser				= null;
	private Button								btn_DeactivateUser				= null;
	private Button								btn_DeleteUser					= null;
	private Button								btn_AddUser						= null;
	private Button								btn_DownloadExcel				= null;//new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "download_report"));
	private Button								btn_DownloadActiveProviderZip	= null;
	private BbrPopupButton						btn_Actions						= null;
	private BbrPopupButton						btn_Download					= null;
	// Variables
	private UserReportInitParamDTO				initParams						= null;
	private BbrMessageEventListener				messagingListener				= null;
	private MultipleUserOperation				userOperation					= null;
	private OrderCriteriaDTO[]					usersOrderCriteria				= null;
	private BbrWork<UserReportDataDTO>			reportWork						= null;
	private BbrWork<FileDownloadInfoResultDTO>	downloadsWork					= null;
	private BbrWork<CompanyTypeArrayResultDTO>	companyTypesWork				= null;
	private Boolean								trackReport						= true;
	private Boolean								resetPageInfo					= true;
	private UserDataDTO							userSelected					= null;
	private FunctionalityMngr					functionalityMngr				= null;
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public UserManagement(BbrUI bbrUIParent, FunctionalityMngr functionalityMngr)
	{
		super(bbrUIParent);
		this.functionalityMngr = functionalityMngr;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	public void initializeView()
	{
		// Filtro del reporte
		UserManagementFilter filterLayout = new UserManagementFilter(this);
		filterLayout.initializeView();
		this.setBbrFilterContainer(filterLayout);

		// Paging Manager
		this.pagingManager = new BbrPagingManager();
		this.pagingManager.setLocale(this.getUser().getLocale());
		this.pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> pagingChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);

		// Grilla
		this.dgd_Users = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.dgd_Users.addSortListener(this::dataGrid_sortHandler);
		this.dgd_Users.setHasRowsDetails(true);
		this.initializeGridColumns();

		this.dgd_Users.addStyleName("report-grid");
		this.dgd_Users.addStyleName("bbr-multi-grid");
		this.dgd_Users.setSizeFull();
		this.dgd_Users.setSelectionMode(SelectionMode.MULTI);
		this.dgd_Users.setPagingManager(pagingManager, this.LOG_ID_FIELD);
		this.dgd_Users.addItemClickListener((ItemClickListener<UserDataDTO> & Serializable) e -> this.selectableRow_handler(e));
		this.dgd_Users.addSelectionListener((SelectionListener<UserDataDTO> & Serializable) e -> this.updateToolBarButtons(e));

		// Reporte
		// Barra de Herramientas
		HorizontalLayout leftButtonsBar = new HorizontalLayout();
		HorizontalLayout rightButtonsBar = new HorizontalLayout();

		if (functionalityMngr.hasFunctionalityAssigned(FunctionalitiesCodes.USR_600)) // CrearUsuario
		{
			this.btn_AddUser = new Button("", EnumToolbarButton.ADD_ALTERNATIVE.getResource());
			this.btn_AddUser.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "add_user"));
			this.btn_AddUser.addClickListener((ClickListener & Serializable) e -> this.addUser_clickHandler(e));
			this.btn_AddUser.addStyleName("toolbar-button");

			leftButtonsBar.addComponent(this.btn_AddUser);
		}
		if (functionalityMngr.hasFunctionalityAssigned(FunctionalitiesCodes.USR_500)) // EditarUsuario
		{
			this.btn_EditUser = new Button("", EnumToolbarButton.EDIT_ALTERNATIVE.getResource());
			this.btn_EditUser.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "edit_user"));
			this.btn_EditUser.addClickListener((ClickListener & Serializable) e -> this.editUser_clickHandler());
			this.btn_EditUser.addStyleName("toolbar-button");

			leftButtonsBar.addComponent(this.btn_EditUser);

		}

		if (functionalityMngr.hasFunctionalityAssigned(FunctionalitiesCodes.USR_400)) // EliminarUsuario
		{
			this.btn_DeleteUser = new Button("", EnumToolbarButton.DELETE.getResource());
			this.btn_DeleteUser.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "delete_user"));
			this.btn_DeleteUser.addClickListener((ClickListener & Serializable) e -> this.deleteUser_clickHandler(e));
			this.btn_DeleteUser.addStyleName("toolbar-button");

			leftButtonsBar.addComponent(this.btn_DeleteUser);
		}

		// ACTION BUTTONS SECTION
		VerticalLayout cmp_ActionButtons = new VerticalLayout();
		cmp_ActionButtons.setMargin(new MarginInfo(false, true));
		cmp_ActionButtons.setSpacing(false);

		if (functionalityMngr.hasFunctionalityAssigned(FunctionalitiesCodes.USR_100)) // ActivarUsuario
		{
			this.btn_ActivateUser = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "activate_user"));
			this.btn_ActivateUser.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "activate_user"));
			this.btn_ActivateUser.addClickListener((ClickListener & Serializable) e -> this.activateUser_clickHandler(e));
			this.btn_ActivateUser.addStyleName("action-button");
			cmp_ActionButtons.addComponent(this.btn_ActivateUser);
		}
		if (functionalityMngr.hasFunctionalityAssigned(FunctionalitiesCodes.USR_200)) // DesactivarUsuario
		{
			this.btn_DeactivateUser = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "desactivate_user"));
			this.btn_DeactivateUser.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "desactivate_user"));
			this.btn_DeactivateUser.addClickListener((ClickListener & Serializable) e -> this.deactivateUser_clickHandler(e));
			this.btn_DeactivateUser.addStyleName("action-button");

			cmp_ActionButtons.addComponent(this.btn_DeactivateUser);
		}

		if (functionalityMngr.hasFunctionalityAssigned(FunctionalitiesCodes.USR_700)) // CrearUsuarioComo
		{
			this.btn_CopyProfilesAndProviders = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "copy_profiles"));
			this.btn_CopyProfilesAndProviders.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "copy_profiles_and_providers"));
			this.btn_CopyProfilesAndProviders.addClickListener((ClickListener & Serializable) e -> this.copyProfiles_clickHandler());
			this.btn_CopyProfilesAndProviders.addStyleName("action-button");

			cmp_ActionButtons.addComponent(this.btn_CopyProfilesAndProviders);

		}
		if (functionalityMngr.hasFunctionalityAssigned(FunctionalitiesCodes.USR_701)) // CopiarPerfiles
		{
			this.btn_CreateUserLike = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "create_user_like"));
			this.btn_CreateUserLike.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "create_user_like_description"));
			this.btn_CreateUserLike.addClickListener((ClickListener & Serializable) e -> this.createUserLike_clickHandler(e));
			this.btn_CreateUserLike.addStyleName("action-button");

			cmp_ActionButtons.addComponent(this.btn_CreateUserLike);
		}
		this.btn_Actions = new BbrPopupButton("");
		this.btn_Actions.setIcon(EnumToolbarButton.LIST.getResource());
		this.btn_Actions.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "aditional_actions"));
		this.btn_Actions.addStyleName("toolbar-button");
		this.btn_Actions.setContentLayout(cmp_ActionButtons);
		this.btn_Actions.setClosePopupOnOutsideClick(true);
		leftButtonsBar.addComponent(this.btn_Actions);
		// END ACTION BUTTONS SECTION

		// DOWNLOAD BUTTONS SECTION
		VerticalLayout cmp_DownloadButtons = new VerticalLayout();
		cmp_DownloadButtons.setMargin(new MarginInfo(false, true));
		cmp_DownloadButtons.setSpacing(false);
		
		//se mueve validación en donde se habilita el botón
		if (functionalityMngr.hasFunctionalityAssigned(FunctionalitiesCodes.USR_800)) // DescargarExcel
		{
			this.btn_DownloadExcel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "download_report"));
			this.btn_DownloadExcel.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "download_report"));
			this.btn_DownloadExcel.addClickListener((ClickListener & Serializable) e -> btn_DownloadFile_downloadHandler(e));
			this.btn_DownloadExcel.addStyleName("action-button");
			this.btn_DownloadExcel.setEnabled(false);
			cmp_DownloadButtons.addComponent(this.btn_DownloadExcel);
		}
		if (functionalityMngr.hasFunctionalityAssigned(FunctionalitiesCodes.USR_800)) // DescargarExcel
		{
			this.btn_DownloadActiveProviderZip = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "download_rar_file"));
			this.btn_DownloadActiveProviderZip.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "download_rar_file"));
			this.btn_DownloadActiveProviderZip.addClickListener((ClickListener & Serializable) e -> btn_DownloadFile_downloadHandler(e));
			this.btn_DownloadActiveProviderZip.addStyleName("action-button");

			cmp_DownloadButtons.addComponent(this.btn_DownloadActiveProviderZip);
		}

		this.btn_Download = new BbrPopupButton("");
		this.btn_Download.setIcon(EnumToolbarButton.DOWNLOAD_PRIMARY.getResource());
		this.btn_Download.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "downloads"));
		this.btn_Download.addStyleName("toolbar-button");
		this.btn_Download.setContentLayout(cmp_DownloadButtons);
		this.btn_Download.setClosePopupOnOutsideClick(true);
		rightButtonsBar.addComponent(this.btn_Download);
		// END DOWNLOAD BUTTONS SECTION

		leftButtonsBar.setSpacing(true);
		leftButtonsBar.setMargin(false);
		leftButtonsBar.setHeight("30px");
		leftButtonsBar.addStyleName("toolbar-layout");

		Button btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
		btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
		btn_Refresh.addClickListener((ClickListener & Serializable) e -> refreshReport_clickHandler());
		btn_Refresh.addStyleName("toolbar-button");

		rightButtonsBar.addComponents(btn_Refresh);
		rightButtonsBar.setSpacing(true);
		rightButtonsBar.setMargin(false);
		rightButtonsBar.setHeight("30px");
		rightButtonsBar.addStyleName("toolbar-layout");

		rightButtonsBar.setComponentAlignment(btn_Refresh, Alignment.MIDDLE_RIGHT);

		HorizontalLayout toolBar = new HorizontalLayout();
		toolBar.setWidth("100%");
		toolBar.addComponents(leftButtonsBar, rightButtonsBar);
		toolBar.addStyleName("filter-toolbar");
		toolBar.setExpandRatio(leftButtonsBar, 1F);
		toolBar.setExpandRatio(rightButtonsBar, 1F);

		toolBar.setComponentAlignment(leftButtonsBar, Alignment.MIDDLE_LEFT);
		toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);

		this.updateToolBarButtons(null);

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addStyleName("report-layout");
		mainLayout.setSizeFull();
		mainLayout.addComponents(toolBar, dgd_Users, pagingManager);
		mainLayout.setExpandRatio(dgd_Users, 1F);
		mainLayout.setMargin(false);
		this.addComponents(mainLayout);

		this.updateUserSortOrderCriteria(null);

		reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		reportWork.addFunction(new Function<Object, UserReportDataDTO>()
		{
			@Override
			public UserReportDataDTO apply(Object t)
			{
				return executeService(UserManagement.this.getBbrUIParent());
			}
		});

	}

	private void selectableRow_handler(ItemClick<UserDataDTO> e)
	{
		if (e.getMouseEventDetails().isDoubleClick() && e.getItem() != null)
		{
			if (functionalityMngr.hasFunctionalityAssigned(FunctionalitiesCodes.USR_500)) // EditarUsuario
			{
				this.dgd_Users.select(e.getItem());
				this.editUser_clickHandler();
			}
		}
		else
		{
			UserDataDTO item = new UserDataDTO();
			if (e.getItem() instanceof UserDataDTO)
			{
				item = e.getItem();
			}
			if (item != null)
			{
				if (this.userSelected == null)
				{
					this.userSelected = new UserDataDTO();
				}
				if (item.getId().equals(this.userSelected.getId()))
				{
					if (this.dgd_Users.getSelectedItems().size() > 0)
					{
						this.dgd_Users.deselect(item);
						this.userSelected = new UserDataDTO();
						this.userSelected.setId(-1L);
					}
				}
				else
				{
					this.dgd_Users.deselectAll();
					this.userSelected = item;
					this.dgd_Users.select(item);
				}

			}
		}
	}

	@Override
	public void attach()
	{
		super.attach();
		this.messagingListener = (BbrMessageEventListener & Serializable) e -> bbrMessage_Listener(e);
		((BbrUI) UI.getCurrent()).getMessagingEventMngr().addBbrMessageListener(messagingListener, MessageTopics.FAQS);
	}

	@Override
	public void detach()
	{
		super.detach();
		((BbrUI) UI.getCurrent()).getMessagingEventMngr().removeBbrMessageListener(messagingListener, MessageTopics.FAQS);
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				this.doUpdateReport(result, sender);
			}
			else if (triggerObject == this.btn_DownloadTriggerButton)
			{
				this.doDownloadFile(result, sender, triggerObject);
			}
			else if (triggerObject == this.btn_CopyProfilesAndProviders)
			{
				this.stopWaiting();
				CompanyTypeArrayResultDTO resultDTO = (CompanyTypeArrayResultDTO) result;
				this.initializeCopyProfiles(resultDTO);
			}
			else if (triggerObject == this.btn_CreateUserLike)
			{
				this.stopWaiting();
				UserResultDTO resultDTO = (UserResultDTO) result;
				this.doEditUserLike(sender, resultDTO);
			}
		}
		else
		{
			UserManagement senderReport = (UserManagement) sender;

			if (!senderReport.getBbrUIParent().hasAlertWindowOpen())
				senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));

			senderReport.stopWaiting();
		}
	}

	private void doEditUserLike(BbrWorkExecutor sender, UserResultDTO result)
	{
		UserManagement senderReport = (UserManagement) sender;

		if (result != null)
		{
			BbrError error = ErrorsMngr.getInstance().getError(result, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());
			if (!error.hasError())
			{
				this.editUser(result.getUser().getId(), false);
			}
		}

	}

	private void initializeCopyProfiles(CompanyTypeArrayResultDTO result)
	{
		for (CompanyTypeDTO companyType : result.getCompanyTypeDTOs())
		{
			this.userSelected = this.dgd_Users.getAllSelectedsItems().get(0);
			if (companyType.getName().equals(userSelected.getUsertype()))
			{
				this.initParams.setCompanyTypeId(companyType.getId());
			}
		}
		CopyProfiles winEditUser = new CopyProfiles(UserManagement.this.getBbrUIParent(), userSelected, FILTER_TYPE_SELECTED, this.initParams.getCompanyTypeId());
		winEditUser.addBbrEventListener((BbrEventListener & Serializable) e -> this.userEdited_handler(e));
		winEditUser.addCloseListener((CloseListener & Serializable) e -> this.refreshReport_clickHandler());
		winEditUser.open(true, true, this);
	}
	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************
	private void pagingChange_Listener(BbrPagingEvent e)
	{
		if ((e != null) && e.getEventType().equals(BbrPagingEvent.PAGE_CHANGED) && (e.getResultObject() != null))
		{
			this.startWaiting();

			initParams.setPageNumber(e.getResultObject().getCurrentPage());

			this.trackReport = false;
			this.resetPageInfo = false;
			this.executeBbrWork(reportWork);
		}
	}

	private void updateToolBarButtons(SelectionEvent<UserDataDTO> event)
	{
		boolean canEdit = false;
		boolean canCopyProfiles = false;
		boolean canActivate = false;
		boolean canDesactivate = false;
		boolean canDelete = false;
		boolean canCreateUserAs = false;

		if (event != null && event.getAllSelectedItems() != null && event.getAllSelectedItems().size() > 0)
		{
			canActivate = true;
			canDesactivate = true;
			canDelete = true;

			// Solo aplica cuando seleccionas 1 usuario
			if (event.getAllSelectedItems().size() == 1)
			{
				canEdit = true;
				canCopyProfiles = true;
				// Solo si el usuario seleccionado no es bbr
				Optional<UserDataDTO> uSelected = event.getFirstSelectedItem();
				if (uSelected.isPresent() && !uSelected.get().getUsertype().equalsIgnoreCase(RoleTypes.BBR.getRole()))
				{
					canCreateUserAs = true;
				}
			}
		}

		if (this.btn_EditUser != null)
		{
			this.btn_EditUser.setEnabled(canEdit);
		}
		if (this.btn_ActivateUser != null)
		{
			this.btn_ActivateUser.setEnabled(canActivate);
		}
		if (this.btn_DeactivateUser != null)
		{
			this.btn_DeactivateUser.setEnabled(canDesactivate);
		}
		if (this.btn_DeleteUser != null)
		{
			this.btn_DeleteUser.setEnabled(canDelete);
		}
		if (this.btn_CopyProfilesAndProviders != null)
		{
			this.btn_CopyProfilesAndProviders.setEnabled(canCopyProfiles);
		}
		if (this.btn_CreateUserLike != null)
		{
			this.btn_CreateUserLike.setEnabled(canCreateUserAs);
		}
	}

	@Override
	public void filterApplied_handler(BbrFilterEvent event)
	{
		if ((event != null) && (event.getResultObject() != null))
		{
			initParams = (UserReportInitParamDTO) event.getResultObject();
			initParams = this.updateInitParams();

			this.trackReport = true;
			this.resetPageInfo = true;

			this.startWaiting();
			this.executeBbrWork(reportWork);
		}
	}

	private void editUser_clickHandler()
	{
		if (this.dgd_Users.getSelectedItems() != null && this.dgd_Users.getSelectedItems().size() > 0)
		{
			ArrayList<UserDataDTO> items = new ArrayList<>(this.dgd_Users.getSelectedItems());
			if (items.size() == 1)
			{
				UserDataDTO selectedUser = items.get(0);

				this.editUser(selectedUser.getId(), false);
			}
			else
			{
				this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
						I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "only_one_user_requires"));
			}
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_requieres"));
		}
	}

	private void copyProfiles_clickHandler()
	{
		if (this.dgd_Users.getSelectedItems() != null && this.dgd_Users.getSelectedItems().size() > 0)
		{
			ArrayList<UserDataDTO> items = new ArrayList<>(this.dgd_Users.getSelectedItems());
			if (items.size() == 1)
			{
				UserDataDTO selectedUser = items.get(0);

				this.copyProfiles(selectedUser);
			}
			else
			{
				this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
						I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "only_one_user_requires"));
			}
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_requieres"));
		}
	}

	private void activateUser_clickHandler(ClickEvent event)
	{
		ArrayList<UserDataDTO> items = this.dgd_Users.getAllSelectedsItems();

		if (items != null && items.size() > 0)
		{
			this.userOperation = MultipleUserOperation.ACTIVATE_USERS;
			String alertMessage = (items.size() == 1) ? I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "sure_enable_user")
					: I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "sure_enable_users");

			BbrMessagesBoxUtils.showConfirmationMessage(this.getBbrUIParent(),
					I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_question"),
					alertMessage,
					(Runnable & Serializable) () -> multipleUserAction(items));

		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "select_at_least_one"));
		}
	}

	private void deactivateUser_clickHandler(ClickEvent event)
	{

		if (this.dgd_Users.getSelectedItems() != null && this.dgd_Users.getSelectedItems().size() > 0)
		{
			ArrayList<UserDataDTO> items = new ArrayList<>(this.dgd_Users.getSelectedItems());

			this.userOperation = MultipleUserOperation.DEACTIVATE_USERS;

			String alertMessage = (items.size() == 1) ? I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "sure_disable_user")
					: I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "sure_disable_users");

			BbrMessagesBoxUtils.showConfirmationMessage(this.getBbrUIParent(),
					I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_question"),
					alertMessage,
					(Runnable & Serializable) () -> multipleUserAction(items));

		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "select_at_least_one"));
		}
	}

	private void deleteUser_clickHandler(ClickEvent event)
	{

		if (this.dgd_Users.getSelectedItems() != null && this.dgd_Users.getSelectedItems().size() > 0)
		{
			ArrayList<UserDataDTO> items = new ArrayList<>(this.dgd_Users.getSelectedItems());

			this.userOperation = MultipleUserOperation.REMOVE_USERS;

			String userFullText = UserManagerUtils.getValidFullnameOrEmailByUser(items.get(0));

			String alertMessage = (items.size() == 1) ? I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "sure_delete_the_user",
					userFullText,
					"",
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "delete_app_name")) // #UNIDAD_NEGOCIO
					: I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "sure_delete_users");

			BbrMessagesBoxUtils.showConfirmationMessage(this.getBbrUIParent(),
					I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_question"),
					alertMessage,
					(Runnable & Serializable) () -> multipleUserAction(items));

		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "select_at_least_one"));
		}
	}

	private void createUserLike_clickHandler(ClickEvent e)
	{
		if (this.dgd_Users.getSelectedItems() != null && this.dgd_Users.getSelectedItems().size() > 0)
		{
			ArrayList<UserDataDTO> items = new ArrayList<>(this.dgd_Users.getSelectedItems());
			if (items.size() == 1)
			{
				UserDataDTO selectedUser = items.get(0);
				this.openCreateUserLikeView(selectedUser);
				// this.createUserLike(selectedUser);
			}
			else
			{
				this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
						I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "only_one_user_requires"));
			}
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_requieres"));
		}
	}

	private void refreshReport_clickHandler()
	{
		this.startWaiting();

		initParams = this.updateInitParams();

		this.trackReport = false;
		this.resetPageInfo = false;
		this.executeBbrWork(reportWork);
	}

	private void addUser_clickHandler(ClickEvent event)
	{
		AskType askType = new AskType(this.getBbrUIParent());
		askType.addBbrEventListener((BbrEventListener & Serializable) e -> this.openCreateView(e));
		askType.open(true, true, this);
	}

	private void openCreateView(BbrEvent e)
	{
		CreateBasicUser winCreateBasicUser = new CreateBasicUser(this.getBbrUIParent());
		winCreateBasicUser.setUserTypeSelected(e.getEventType());
		winCreateBasicUser.addBbrEventListener((BbrEventListener & Serializable) ev -> userCreated_handler(ev, true));
		winCreateBasicUser.open(true, true, this);
	}

	private void openCreateUserLikeView(UserDataDTO selectedUser)
	{
		CreateBasicUser winCreateBasicUser = new CreateBasicUser(this.getBbrUIParent());
		winCreateBasicUser.setCreateUserLike(selectedUser);
		winCreateBasicUser.addBbrEventListener((BbrEventListener & Serializable) ev -> userCreatedLike_handler(ev, selectedUser.getId()));
		winCreateBasicUser.open(true, true, this);
	}

	private void userCreated_handler(BbrEvent e, boolean creation)
	{
		// Mostrar la edición del usuario.
		if (e.getEventType().equals(BbrEvent.USER_ADDED))
		{
			this.startWaiting();

			this.trackReport = false;
			this.resetPageInfo = false;
			this.executeBbrWork(reportWork);

			UserDTO user = (UserDTO) e.getResultObject();
			this.editUser(user.getId(), creation);
		}
	}

	private void userCreatedLike_handler(BbrEvent e, Long userBaseId)
	{
		// Mostrar la edición del usuario.
		if (e.getEventType().equals(BbrEvent.ITEM_CREATED))
		{
			// Se recibe la resppuesta del usuario creado como
			this.startWaiting();

			this.trackReport = false;
			this.resetPageInfo = false;
			this.executeBbrWork(reportWork);

			UserDTO user = (UserDTO) e.getResultObject();
			this.editUser(user.getId(), true);
		}
	}

	private void editUser(Long id, boolean creation)
	{
		try
		{
			UserResultDTO userResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().getUserById(id);
			EditUser winEditUser = new EditUser(this.getBbrUIParent(), userResult.getUser());
			winEditUser.setCreated(creation);
			winEditUser.addBbrEventListener((BbrEventListener & Serializable) e -> this.userEdited_handler(e));
			winEditUser.open(true, true, this);
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
	}

	private void copyProfiles(UserDataDTO item)
	{
		this.companyTypesWork = new BbrWork<>(this, UserManagement.this.getBbrUIParent(), this.btn_CopyProfilesAndProviders);
		this.companyTypesWork.addFunction(new Function<Object, CompanyTypeArrayResultDTO>()
		{
			@Override
			public CompanyTypeArrayResultDTO apply(Object t)
			{
				return executeServiceGetCompanyTypes(UserManagement.this.getBbrUIParent());
			}

		});

		this.startWaiting();
		this.executeBbrWork(this.companyTypesWork);
	}

	private CompanyTypeArrayResultDTO executeServiceGetCompanyTypes(BbrUI bbrUI)
	{
		CompanyTypeArrayResultDTO result = null;
		try
		{
			result = EJBFactory.getUserEJBFactory().getCompanyTypeReportManagerLocal(bbrUI).getCompanyTypesOfUserForFilter(bbrUI.getUser().getId());
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

	private void userEdited_handler(BbrEvent e)
	{
		if (e.getEventType().equals(BbrEvent.USER_UPDATE))
		{
			this.startWaiting();

			this.trackReport = false;
			this.resetPageInfo = true;
			this.executeBbrWork(this.reportWork);

		}
	}

	private void bbrMessage_Listener(BbrMessageEvent event)
	{
		if (event != null && event.getMessage() != null && event.getMessage().getSenderBbrUser() != null)
		{
			BbrUser sender = event.getMessage().getSenderBbrUser();

			this.startWaiting();

			this.trackReport = (sender.getId().equals(this.getUser().getId()));
			this.resetPageInfo = true;
			this.executeBbrWork(this.reportWork);
		}
	}

	private void dataGrid_sortHandler(SortEvent<GridSortOrder<UserDataDTO>> e)
	{
		if (e.isUserOriginated() && pagingManager.getTotalsPages() > 1)
		{
			this.updateUserSortOrderCriteria(e.getSortOrder());
			this.initParams.setPageNumber(this.DEFAULT_PAGE_NUMBER);

			this.trackReport = false;
			this.resetPageInfo = false;
			this.executeBbrWork(this.reportWork);
		}
	}

	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private UserReportInitParamDTO updateInitParams()
	{
		if (this.initParams != null)
		{
			this.initParams.setPageNumber(this.DEFAULT_PAGE_NUMBER);
			this.initParams.setRows(this.MAX_ROWS_NUMBER);
		}

		return this.initParams;
	}

	private void initializeGridColumns()
	{
		this.dgd_Users.addColumn(UserDataDTO::getUsername).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_name"))
				.setId(USERNAME);
		this.dgd_Users.addColumn(UserDataDTO::getLastname).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_lastname"))
				.setId(LASTNAME);
		this.dgd_Users.addColumn(UserDataDTO::getEmail).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_email"))
				.setId(EMAIL);
		this.dgd_Users.addColumn(UserDataDTO::getPersonalid).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_person_id"))
				.setId(PERSON_ID);
		this.dgd_Users.addColumn(user -> stateDescription_function(user)).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_state"))
				.setId(STATE);
		this.dgd_Users.addColumn(UserDataDTO::getUsertype).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_type"))
				.setId(USER_TYPE);
		this.dgd_Users.addColumn(UserDataDTO::getCompanycode).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "company_code"))
				.setId(COMPANY_CODE);
		this.dgd_Users.addColumn(UserDataDTO::getCompanyname).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "company_name"))
				.setId(COMPANY_NAME);
		this.dgd_Users.addColumn(UserDataDTO::getPosition).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_position"))
				.setId(POSITION);
		this.dgd_Users.addColumn(UserDataDTO::getCreationdate).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "creation_date"))
				.setStyleGenerator(creationdate -> BbrAlignment.CENTER.getValue())
				.setRenderer(new DateRenderer())
				.setId(CREATION_DATE);
		this.dgd_Users.addColumn(UserDataDTO::getLastupdate).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_update_date"))
				.setStyleGenerator(lastupdate -> BbrAlignment.CENTER.getValue())
				.setRenderer(new DateRenderer())
				.setId(LAST_UPDATE);
		this.dgd_Users.addColumn(UserDataDTO::getLastlogin).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_login_date"))
				.setStyleGenerator(i -> BbrAlignment.CENTER.getValue())
				.setRenderer(new DateRenderer())
				.setWidthUndefined()
				.setId(LAST_LOGIN);

		this.dgd_Users.setFrozenColumnCount(3);
	}

	private String stateDescription_function(UserDataDTO user)
	{
		String result = "";
		if (user != null)
		{
			result = (user.isStateindicator() == true) ? I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "active")
					: I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "inactive");
		}

		return result;
	}

	private void multipleUserAction(ArrayList<UserDataDTO> users)
	{
		String errordescription = "";
		String trackValidMessage = TrackingConstants.ITEM_EDDITED;
		String trackErrorMessage = TrackingConstants.ERROR_ITEM_EDDITED;
		try
		{

			// Start Tracking
			this.getTimingMngr().startTimer();

			ArrayList<Long> ids = new ArrayList<>();
			users.forEach((Consumer<UserDataDTO> & Serializable) user -> ids.add(user.getId()));
			Long[] usersIds = ids.toArray(new Long[ids.size()]);

			BaseResultDTO result;

			String resultMessage = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "successful_operation");
			switch (this.userOperation)
			{
				case ACTIVATE_USERS:
					// URL del cambio de contraseña.
					// String changeUrl = this.getBbrUIParent().getCurrentURL().replace(this.getBbrUIParent().getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "main"),
					// this.getBbrUIParent().getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "change"));
					UserActivateInitParamDTO userActivateInitParam = new UserActivateInitParamDTO();
					userActivateInitParam.setUserPKs(usersIds);
					userActivateInitParam.setExternalRetail(UserManagerUtils.getIsExternalRetail());
					result = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().activateUser(userActivateInitParam);
					if (users.size() == 1)
					{
						String userFullname = UserManagerUtils.getValidFullnameOrEmailByUser(users.get(0));
						resultMessage = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_successfully_activated", userFullname, "");
					}
					break;

				case DEACTIVATE_USERS:
					result = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().inactivateUser(usersIds);
					if (users.size() == 1)
					{
						String userFullname = UserManagerUtils.getValidFullnameOrEmailByUser(users.get(0));
						resultMessage = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_successfully_deactivated", userFullname, "");
					}
					break;

				case REMOVE_USERS:
					result = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().deleteUser(usersIds, "", this.getUser().getId());
					if (users.size() == 1)
					{
						String userFullname = UserManagerUtils.getValidFullnameOrEmailByUser(users.get(0));
						resultMessage = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_successfully_deleted", userFullname, "");
					}
					trackValidMessage = TrackingConstants.ITEM_DELETED;
					trackErrorMessage = TrackingConstants.ERROR_ITEM_DELETED;
					break;

				default:
					result = null;
					resultMessage = "";
					break;
			}

			if (result != null)
			{
				String message = I18NManager.getErrorMessageBaseResult(result, result.getParams()); // <--Obtiene
				// el//mensaje // de // error. // "" // si // no // hay errores.

				if (message.length() == 0)
				{
					// Operación Exitosa.
					this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"), resultMessage);

					this.updateInitParams();

					this.trackReport = false;
					this.resetPageInfo = true;
					this.executeBbrWork(reportWork);

					// End Tracking
					this.trackEvent(trackValidMessage, this.getBbrFilterDescription());
				}
				else
				{
					errordescription = BbrUtils.getInstance().substitute("{0} - {1} - Internal Error", result.getStatuscode(), result.getStatusmessage());
					if (!this.getBbrUIParent().hasAlertWindowOpen())
					{
						this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), message);
					}

				}
			}

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
			errordescription = BbrUtils.getInstance().substitute("{0}", e.getMessage());
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P200"));
		}

		if (errordescription.length() > 0)
		{
			// Track Error
			this.trackError(trackErrorMessage, this.getBbrFilterDescription(), errordescription, null, this);
		}
	}

	private void updateUserSortOrderCriteria(List<GridSortOrder<UserDataDTO>> sortOrderList)
	{
		if (sortOrderList != null && !sortOrderList.isEmpty())
		{
			ArrayList<OrderCriteriaDTO> resultList = new ArrayList<>();
			for (GridSortOrder<UserDataDTO> sorOrder : sortOrderList)
			{
				OrderCriteriaDTO order = new OrderCriteriaDTO();
				order.setPropertyname(sorOrder.getSorted().getId().toUpperCase());
				order.setAscending(sorOrder.getDirection() == SortDirection.ASCENDING);
				resultList.add(order);
			}
			this.usersOrderCriteria = resultList.toArray(new OrderCriteriaDTO[resultList.size()]);
		}
		else
		{
			OrderCriteriaDTO order = new OrderCriteriaDTO();
			order.setPropertyname(DEFAULT_SORT_FIELD.toUpperCase());
			order.setAscending(true);
			this.usersOrderCriteria = new OrderCriteriaDTO[] { order };

			GridSortOrder<UserDataDTO> sortOrder = new GridSortOrder<>(this.dgd_Users.getColumn(DEFAULT_SORT_FIELD), SortDirection.ASCENDING);
			sortOrderList = new ArrayList<GridSortOrder<UserDataDTO>>();
			sortOrderList.add(sortOrder);

			this.dgd_Users.setSortOrder(sortOrderList);
		}
	}

	private UserReportDataDTO executeService(BbrUI bbrUI)
	{
		UserReportDataDTO result = null;
		if (this.initParams != null)
		{
			try
			{
				// Start Tracking
				this.getTimingMngr().startTimer();
				result = EJBFactory.getUserEJBFactory().getUserReportManagerLocal(bbrUI).getUserReport(initParams, bbrUI.getUser().getTypeID(),
						bbrUI.getUser().getEnterpriseId(), true, usersOrderCriteria);
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
		}
		return result;
	}

	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{
		String errordescription = "";

		UserManagement senderReport = (UserManagement) sender;

		if (result != null)
		{
			UserReportDataDTO reportResult = (UserReportDataDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				UserDataDTO[] provider = reportResult.getUserReport();

				senderReport.updateButtonDownload(provider.length > 0);

				ListDataProvider<UserDataDTO> dataprovider = new ListDataProvider<>(Arrays.asList(provider));
				senderReport.dgd_Users.setDataProvider(dataprovider, senderReport.resetPageInfo);

				if (senderReport.resetPageInfo)
				{
					PageInfoDTO pageInfo = reportResult.getPageInfoDTO();

					BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
					senderReport.pagingManager.setPages(bbrPage, senderReport.resetPageInfo);

				}

				// End Tracking
				if (senderReport.trackReport == true)
				{
					senderReport.trackEvent(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription());
				}

				if (reportResult.getUserReport().length == 0)
				{
					senderReport.askToOpenFilter(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
							I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_FILTERS, "empty_filter_question"));
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

		senderReport.stopWaiting();
	}

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

	private void updateButtonDownload(boolean b)
	{
			this.btn_DownloadExcel.setEnabled(b);
	}

	private void btn_DownloadFile_downloadHandler(ClickEvent e)
	{
		this.btn_DownloadTriggerButton = e.getButton();
		this.downloadDataSource();
	}

	private void downloadDataSource()
	{
		downloadsWork = new BbrWork<>(this, this.getBbrUIParent(), btn_DownloadTriggerButton);
		downloadsWork.addFunction(new Function<Object, FileDownloadInfoResultDTO>()
		{
			@Override
			public FileDownloadInfoResultDTO apply(Object t)
			{
				return executeDownloadService(UserManagement.this.getBbrUIParent(), DownloadFormats.XLSX, btn_DownloadTriggerButton, null);
			}
		});

		this.startWaiting();
		this.executeBbrWork(downloadsWork);
	}

	private FileDownloadInfoResultDTO executeDownloadService(BbrUI bbrUIParent, DownloadFormats selectedFormat, Button downloadTriggerButton, Object extraData)
	{
		FileDownloadInfoResultDTO fileResult = null;
		if (selectedFormat != null)
		{
			try
			{
				if (this.btn_DownloadTriggerButton == this.btn_DownloadExcel)
				{
					fileResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal(bbrUIParent).downloadUserRecords(initParams,
							bbrUIParent.getUser().getTypeID(), bbrUIParent.getUser().getEnterpriseId(), usersOrderCriteria, bbrUIParent.getUser().getId(),
							selectedFormat.getValue(), bbrUIParent.getUser().getLocale());
				}
				else if (this.btn_DownloadTriggerButton == this.btn_DownloadActiveProviderZip)
				{
					fileResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal(bbrUIParent).downloadActiveProviderFile(bbrUIParent.getUser().getId(),
							selectedFormat.getValue(), bbrUIParent.getUser().getLocale());
				}
			}
			catch (BbrUserException ex)
			{
				AppUtils.getInstance().doLogOut(ex.getMessage(), this.getBbrUIParent());
			}
			catch (BbrSystemException ex)
			{
				ex.printStackTrace();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}

		return fileResult;
	}

	private void doDownloadFile(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		UserManagement senderReport = (UserManagement) sender;
		if (senderReport != null)
		{
			senderReport.downloadLinkFile(result);
		}
	}

}
