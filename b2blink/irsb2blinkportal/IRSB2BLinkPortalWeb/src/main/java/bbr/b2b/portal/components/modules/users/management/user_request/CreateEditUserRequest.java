package bbr.b2b.portal.components.modules.users.management.user_request;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox.NewItemProvider;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.basics.OptionalCommentAlertDTO;
import bbr.b2b.portal.classes.beans.SessionBean;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.users.UserManagerUtils;
import bbr.b2b.portal.classes.utils.users.user_request.UserRequestResultUtilsDTO;
import bbr.b2b.portal.components.basics.BbrButtonGroup;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.basics.OptionalCommentAlert;
import bbr.b2b.portal.constants.BbrUtilsConstants;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.AddRequestAdminPVInitParamDTO;
import bbr.b2b.users.report.classes.AddRequestResultDTO;
import bbr.b2b.users.report.classes.PositionDTO;
import bbr.b2b.users.report.classes.PositionResultDTO;
import bbr.b2b.users.report.classes.UserCompanyArrayResultDTO;
import bbr.b2b.users.report.classes.UserCompanyDTO;
import bbr.b2b.users.report.classes.UserDTO;
import bbr.b2b.users.report.classes.UserProfileArrayResultDTO;
import bbr.b2b.users.report.classes.UserProfileDTO;
import bbr.b2b.users.report.classes.UserRequestDTO;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrTabContent;
import cl.bbr.core.components.basics.BbrTabSheet;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;
import cl.bbr.core.components.widgets.bbrtextfield.client.RestrictRange;

public class CreateEditUserRequest extends BbrWindow implements BbrWorkExecutor
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants
	private static final long					serialVersionUID			= -8695609407959023617L;
	private static final String					PROFILE_CODE				= "profilename";
	private static final String					PROFILE_NAME				= "profiledesc";
	private static final String					COMPANY_CODE				= "company_code";
	private static final String					COMPANY_NAME				= "company_name";
	private static final Long					NEW_USER_ID					= -1L;
	private static final Integer				ASSIGNED_CODE				= 1;
	boolean										isNewUser					= false;
	// Components
	private BbrTextField						txt_PersonalId				= null;
	private BbrTextField						txt_Email					= null;
	private BbrTextField						txt_Name					= null;
	private BbrTextField						txt_LastName				= null;
	private BbrTextField						txt_Company					= null;
	private BbrTextField						txt_Phone					= null;
	private BbrTabSheet							tabNav_UserRequest			= null;
	private BbrTabContent						tabCont_UserRequestProfiles	= null;
	private BbrTabContent						tabCont_UserRequestVendors	= null;
	private BbrAdvancedGrid<UserProfileDTO>		dgd_Profiles				= null;
	private BbrAdvancedGrid<UserCompanyDTO>		dgd_Vendors					= null;
	private BbrComboBox<PositionDTO>			cbx_Position				= null;
	private BbrWork<UserRequestResultUtilsDTO>	positionsWork				= null;
	// Variables
	private UserProfileArrayResultDTO			userProfileArrayResult		= null;
	private UserCompanyArrayResultDTO			userCompanyArrayResult		= null;
	private boolean								resetPageInfo				= false;
	private UserDTO								user						= null;

	// ==============================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================
	public CreateEditUserRequest(BbrUI parentUI, UserDTO user)
	{
		super(parentUI);
		this.user = user;
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
		this.isNewUser = this.user.getId().equals(NEW_USER_ID);

		FormLayout pnlHeader = this.getUserRequestHeader();

		HorizontalLayout pnlTabs = this.getUserRequestTabs();

		String buttonCaption = (this.isNewUser) ? "send" : "save";
		BbrButtonGroup bbrGroupButton = new BbrButtonGroup.BbrGroupButtonBuilder(this.getBbrUIParent())
				.withPrimaryButtonLabel(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, buttonCaption))
				.withPrimaryButtonListener((ClickListener & Serializable) (e) -> this.btn_Save_ClickHandler(e))
				.withCancelButtonLabel(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"))
				.withCancelButtonListener((ClickListener & Serializable) e -> this.close())
				.build();
		bbrGroupButton.setStyleName("bbr-buttons-panel");

		// Main Layout
		VerticalLayout mainLayout = new VerticalLayout(pnlHeader, pnlTabs, bbrGroupButton);
		mainLayout.setSizeFull();
		mainLayout.setSpacing(true);
		mainLayout.addStyleName("bbr-margin-windows-zero-top-bottom");
		mainLayout.setComponentAlignment(bbrGroupButton, Alignment.MIDDLE_CENTER);
		mainLayout.setExpandRatio(pnlTabs, 1F);

		// Ventana
		this.setWidth("40%");
		this.setHeight("600px");
		this.setResizable(true);
		this.setResponsive(true);
		String winCaption = (this.isNewUser) ? "win_title_add_user_request_window" : "win_title_edit_user_request_window";
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, winCaption));
		this.setContent(mainLayout);

		this.positionsWork = new BbrWork<UserRequestResultUtilsDTO>(this, this.getBbrUIParent(), this);
		this.positionsWork.addFunction(new Function<Object, UserRequestResultUtilsDTO>()
		{
			@Override
			public UserRequestResultUtilsDTO apply(Object t)
			{
				return executeService(CreateEditUserRequest.this.getBbrUIParent());
			}
		});

		this.startWaiting();
		this.resetPageInfo = true;
		this.executeBbrWork(this.positionsWork);

	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		if (result != null)
		{
			this.updateComboBox(result);
		}
		else
		{
			CreateEditUserRequest senderReport = (CreateEditUserRequest) sender;

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
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	private void btn_Save_ClickHandler(ClickEvent e)
	{
		String errorMsg = this.validateData();
		if ((errorMsg == null) || (errorMsg.length() == 0))
		{
			this.viewConfirmationUserRequest();
		}
		else
		{
			CreateEditUserRequest.this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), errorMsg);
		}
	}

	private Optional<PositionDTO> btn_ChangeText_BlurHandler(Object e)
	{
		String name = (String) e;
		PositionDTO newPosition = new PositionDTO();
		if (name != null)
		{
			newPosition.setName(name);
			newPosition.setId(-1L);

			cbx_Position.addItem(newPosition);
			cbx_Position.setSelectedItem(newPosition);
			cbx_Position.markAsDirtyRecursive();
		}
		return Optional.of(newPosition);
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
	private void viewConfirmationUserRequest()
	{
		this.close();// tratar de no cerrar aun

		OptionalCommentAlert addUserRequestCtrl = new OptionalCommentAlert(this.getBbrUIParent(),
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "win_title_info"),
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_request_send"),
				NEW_USER_ID,
				false);

		addUserRequestCtrl.addBbrEventListener((BbrEventListener & Serializable) e -> this.getOptionalCommentAlertResponseAdd(e));
		addUserRequestCtrl.initializeView();
		addUserRequestCtrl.open(true);
	}

	private void getOptionalCommentAlertResponseAdd(BbrEvent result)
	{
		try
		{
			OptionalCommentAlertDTO resultDTO = (OptionalCommentAlertDTO) result.getResultObject();
			if (resultDTO != null)
			{
				// De no haber error se llena el initparam
				AddRequestAdminPVInitParamDTO initParam = this.getInitParam();
				initParam.setComment(resultDTO.getComment());
				// Se envia al servidor para almacenar
				this.doCreateEditUserRequest(initParam);
			}
		}
		catch (Exception e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), this.getBbrUIParent());
		}
	}

	private AddRequestAdminPVInitParamDTO getInitParam()
	{
		AddRequestAdminPVInitParamDTO initParam = new AddRequestAdminPVInitParamDTO();
		initParam.setExternalRetail(UserManagerUtils.getIsExternalRetail());
		initParam.setAdminid(this.getBbrUIParent().getUser().getId());

		UserRequestDTO userRequestDTO = new UserRequestDTO();

		userRequestDTO.setPersonalId(this.txt_PersonalId.getValue());
		userRequestDTO.setEmail(this.txt_Email.getValue());
		userRequestDTO.setName(this.txt_Name.getValue());
		userRequestDTO.setLastName(this.txt_LastName.getValue());
		userRequestDTO.setEmkey(this.getBbrUIParent().getUser().getEnterpriseId());
		userRequestDTO.setPosition(this.cbx_Position.getValue().getName());
		userRequestDTO.setPhone(this.txt_Phone.getValue());

		initParam.setUserRequest(userRequestDTO);

		Long[] profilesSelected = this.dgd_Profiles.getSelectedItems().stream().map(UserProfileDTO::getPrkey).toArray(Long[]::new);
		userRequestDTO.setPrkeys(profilesSelected);

		Long[] companiesSelected = this.dgd_Vendors.getSelectedItems().stream().map(UserCompanyDTO::getEmkey).toArray(Long[]::new);
		userRequestDTO.setEmkeys(companiesSelected);

		SessionBean sessionBean = (SessionBean) this.getBbrUIParent().getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
		initParam.setAccessToken(sessionBean.getAccessToken());

		return initParam;
	}

	private String validateData()
	{
		String result = null;

		this.txt_PersonalId.setComponentError(null);
		this.txt_Email.setComponentError(null);
		this.txt_Name.setComponentError(null);
		this.txt_LastName.setComponentError(null);
		this.cbx_Position.setComponentError(null);
		this.txt_Company.setComponentError(null);
		this.txt_Phone.setComponentError(null);
		this.dgd_Profiles.setComponentError(null);
		this.dgd_Vendors.setComponentError(null);

		if ((this.txt_PersonalId.getValue() != null && this.txt_PersonalId.getValue().trim().length() > 0)
				&& (this.txt_Name.getValue() != null && this.txt_Name.getValue().trim().length() > 0)
				&& (this.txt_Email.getValue() != null && this.txt_Email.getValue().trim().length() > 0)
				&& (this.txt_LastName.getValue() != null && this.txt_LastName.getValue().trim().length() > 0) && (this.cbx_Position.getValue() != null)
				&& (this.txt_Company.getValue() != null && this.txt_Company.getValue().trim().length() > 0) && (this.cbx_Position.getValue() != null))
		{
			if (!BbrUtils.getInstance().checkEmailAddress(this.txt_Email.getValue()))// ->
			{
				// -> Email
				this.txt_Email.setComponentError(new UserError(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_mail")));
				result = I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_mail");
			}
			else if ((this.dgd_Profiles.getSelectedItems() == null || this.dgd_Profiles.getSelectedItems().size() < 1))
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_profile");
			}
			else if ((this.dgd_Vendors.getSelectedItems() == null || this.dgd_Vendors.getSelectedItems().size() < 1))
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_vendor");
			}
			else if (this.txt_Phone.getValue() != null && this.txt_Phone.getValue().trim().length() > 0 && !BbrUtils.getInstance().isPhoneRegEx(this.txt_Phone.getValue()))// ->
			{
				// -> Phone
				result = I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "valid_phone");
			}

		}
		else
		{
			// -> Campos vac�os.
			result = I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "msg_missing_fields");
		}
		return result;
	}

	// No se puede agregar work ya que está en la pantalla principal
	private void doCreateEditUserRequest(AddRequestAdminPVInitParamDTO initParam)
	{
		String errorDescription = "";
		AddRequestResultDTO result = null;
		try
		{
			if (initParam != null)
			{

				if (this.isNewUser)
				{
					// aqui le dan la respuesta
					result = EJBFactory.getUserEJBFactory().getRequestManagerLocal(this.getBbrUIParent()).addRequestNewUserAdminPV(initParam);
				}
				else
				{
					result = EJBFactory.getUserEJBFactory().getRequestManagerLocal(this.getBbrUIParent()).addRequestEditAdminPV(initParam);
				}

				if (result != null)
				{
					AddRequestResultDTO reportResult = (AddRequestResultDTO) result;

					BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), !this.getBbrUIParent().hasAlertWindowOpen());

					if (!error.hasError())
					{
						errorDescription += I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_request_send_response",
								result.getRequestId().toString());

						this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"), errorDescription);
						this.close();
					}
					else
					{
						errorDescription = BbrUtils.getInstance().substitute("{0} - {1} - Internal Error", error.getErrorCode(), error.getErrorMessage());
					}
				}
				else
				{
					// -> Error reportResult = null
					errorDescription = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
				}
			}

		}
		catch (Exception e) // Error no controlado
		{
			errorDescription = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
		}
	}

	private HorizontalLayout getUserRequestTabs()
	{
		this.tabNav_UserRequest = new BbrTabSheet();
		this.tabNav_UserRequest.setSizeFull();
		this.tabNav_UserRequest.addStyleName("main-tab-sheet");

		this.dgd_Profiles = new BbrAdvancedGrid<>(this.getBbrUIParent().getLocale());
		this.dgd_Profiles.setSizeFull();
		this.dgd_Profiles.addStyleName("report-grid");
		this.dgd_Profiles.setSelectionMode(SelectionMode.MULTI);
		this.initializeUserProfileDataGridColumns();

		this.dgd_Vendors = new BbrAdvancedGrid<>(this.getBbrUIParent().getLocale());
		this.dgd_Vendors.setSizeFull();
		this.dgd_Vendors.addStyleName("report-grid");
		this.dgd_Vendors.setSelectionMode(SelectionMode.MULTI);
		this.initializeUserCompanyDataGridColumns();

		this.tabCont_UserRequestProfiles = new BbrTabContent();
		this.tabCont_UserRequestProfiles.addComponents(this.dgd_Profiles);
		this.tabCont_UserRequestProfiles.setSizeFull();

		this.tabCont_UserRequestVendors = new BbrTabContent();
		this.tabCont_UserRequestVendors.addComponents(this.dgd_Vendors);
		this.tabCont_UserRequestVendors.setSizeFull();

		this.tabNav_UserRequest.addBbrTab(this.tabCont_UserRequestProfiles, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "profiles"), false);
		this.tabNav_UserRequest.addBbrTab(this.tabCont_UserRequestVendors, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "vendors"), false);

		HorizontalLayout tabs = new HorizontalLayout(this.tabNav_UserRequest);
		tabs.setSizeFull();
		tabs.setSpacing(false);
		return tabs;
	}

	private void initializeUserProfileDataGridColumns()
	{
		this.dgd_Profiles.addCustomColumn(UserProfileDTO::getProfilename, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "name"), true)
				.setDescriptionGenerator(i -> i.getProfilename())
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue()).setId(PROFILE_CODE);

		this.dgd_Profiles.addCustomColumn(UserProfileDTO::getProfiledesc, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "description"), true)
				.setDescriptionGenerator(i -> i.getProfiledesc())
				.setWidth(300D)
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue()).setId(PROFILE_NAME);
	}

	private void initializeUserCompanyDataGridColumns()
	{
		this.dgd_Vendors.addCustomColumn(UserCompanyDTO::getEmrut, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "code"), true)
				.setDescriptionGenerator(i -> i.getEmrut())
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue()).setId(COMPANY_CODE);

		this.dgd_Vendors.addCustomColumn(UserCompanyDTO::getEmdesc, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "description"), true)
				.setDescriptionGenerator(i -> i.getEmdesc())
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue()).setId(COMPANY_NAME);
	}

	private FormLayout getUserRequestHeader()
	{
		this.txt_PersonalId = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_person_id"));
		this.txt_PersonalId.setMaxLength(50);
		this.txt_PersonalId.setRequiredIndicatorVisible(true);
		this.txt_PersonalId.setRestrict(RestrictRange.RUT);

		this.txt_Email = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_email"));
		this.txt_Email.setMaxLength(50);
		this.txt_Email.setRequiredIndicatorVisible(true);

		this.txt_Name = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_name"));
		this.txt_Name.setMaxLength(50);
		this.txt_Name.setRequiredIndicatorVisible(true);
		this.txt_Name.setRestrict(RestrictRange.ALL_LETTERS_SPACE);
		this.txt_Name.addStyleName("bbr-fields");

		this.txt_LastName = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_lastname"));
		this.txt_LastName.setMaxLength(50);
		this.txt_LastName.setRequiredIndicatorVisible(true);
		this.txt_LastName.setRestrict(RestrictRange.ALL_LETTERS_SPACE);
		this.txt_LastName.addStyleName("bbr-fields");

		this.txt_Phone = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_phone"));
		this.txt_Phone.setMaxLength(15);
		this.txt_Phone.setRestrict(RestrictRange.PHONE);
		this.txt_Phone.addStyleName("bbr-fields");

		this.cbx_Position = new BbrComboBox<>(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_position"));
		this.cbx_Position.setItemCaptionGenerator(PositionDTO::getName);
		this.cbx_Position.setRequiredIndicatorVisible(true);
		this.cbx_Position.setTextInputAllowed(true);
		this.cbx_Position.setWidth("100%");
		this.cbx_Position.setEmptySelectionAllowed(false);
		this.cbx_Position.setNewItemProvider((NewItemProvider<PositionDTO> & Serializable) e -> this.btn_ChangeText_BlurHandler(e));

		this.txt_Company = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_enterprise"));
		this.txt_Company.setRequiredIndicatorVisible(true);
		this.txt_Company.setEnabled(false);
		this.txt_Company.setValue(this.getBbrUIParent().getUser().getEnterpriseDescription());
		this.txt_Company.setWidth("100%");
		this.txt_Company.setHeight("30px");

		if (this.isNewUser)
		{
			this.txt_PersonalId.addStyleName("bbr-fields");
			this.txt_PersonalId.focus();
			this.txt_Email.addStyleName("bbr-fields");
		}
		else
		{
			this.txt_PersonalId.addStyleName("bbr-text-field-read-only");
			this.txt_PersonalId.setReadOnly(true);
			this.txt_Email.addStyleName("bbr-text-field-read-only");
			this.txt_Email.setReadOnly(true);

			this.txt_Name.focus();
			this.txt_PersonalId.setValue((this.user.getPersonalid() != null) ? this.user.getPersonalid() : "");
			this.txt_Email.setValue((this.user.getEmail() != null) ? this.user.getEmail() : "");
			this.txt_Name.setValue((this.user.getLastname() != null) ? this.user.getName() : "");
			this.txt_LastName.setValue((this.user.getLastname() != null) ? this.user.getLastname() : "");
			this.txt_Phone.setValue((this.user.getPhone() != null) ? this.user.getPhone() : "");
		}

		FormLayout header = new FormLayout(this.txt_PersonalId, this.txt_Email, this.txt_Name, this.txt_LastName, this.txt_Phone, this.cbx_Position, this.txt_Company);
		header.setSpacing(false);
		return header;
	}

	private void updateComboBox(Object result)
	{

		UserRequestResultUtilsDTO resultDTO = (UserRequestResultUtilsDTO) result;

		BbrError error = ErrorsMngr.getInstance().getError(resultDTO, this.getBbrUIParent(), !this.getBbrUIParent().hasAlertWindowOpen());

		if (!error.hasError())
		{
			if (resultDTO != null)
			{
				BbrError errorPosition = ErrorsMngr.getInstance().getError(resultDTO.getPositionResult(), this.getBbrUIParent(),
						!this.getBbrUIParent().hasAlertWindowOpen());
				BbrError errorUserProfile = ErrorsMngr.getInstance().getError(resultDTO.getUserProfileArrayResult(), this.getBbrUIParent(),
						!this.getBbrUIParent().hasAlertWindowOpen());
				BbrError errorUserCompany = ErrorsMngr.getInstance().getError(resultDTO.getUserCompanyArrayResult(), this.getBbrUIParent(),
						!this.getBbrUIParent().hasAlertWindowOpen());

				if (!errorPosition.hasError())
				{
					if (resultDTO.getPositionResult() != null && resultDTO.getPositionResult().getStatuscode().equals("0")
							&& resultDTO.getPositionResult().getPositionDTOs().length > 0)
					{
						this.cbx_Position.setItems(resultDTO.getPositionResult().getPositionDTOs());
						this.cbx_Position.setItemCaptionGenerator(PositionDTO::getName);
						PositionDTO[] positionDTOs = resultDTO.getPositionResult().getPositionDTOs();
						Optional<PositionDTO> optionalPositionDTO = Arrays.stream(positionDTOs).filter(p -> p.getName().equals(this.user.getPosition()))
								.findFirst();
						if (optionalPositionDTO.isPresent())
						{

							this.cbx_Position.setSelectedItem(optionalPositionDTO.get());
						}
						else
						{
							this.cbx_Position.selectFirstItem();
						}
					}
					else
					{
						this.cbx_Position.setEnabled(false);
					}
				}
				if (!errorUserProfile.hasError())
				{
					if (resultDTO.getUserProfileArrayResult() != null && resultDTO.getUserProfileArrayResult().getStatuscode().equals("0"))
					{
						this.userProfileArrayResult = new UserProfileArrayResultDTO();

						this.userProfileArrayResult.setUserProfiles(
								(resultDTO.getUserProfileArrayResult() != null) ? resultDTO.getUserProfileArrayResult().getUserProfiles() : new UserProfileDTO[0]);
						ListDataProvider<UserProfileDTO> dataproviderUserProfile = new ListDataProvider<>(
								Arrays.asList(this.userProfileArrayResult.getUserProfiles()));
						this.dgd_Profiles.setDataProvider(dataproviderUserProfile, this.resetPageInfo);

						for (int i = 0; i < this.userProfileArrayResult.getUserProfiles().length; i++)
						{
							if (userProfileArrayResult.getUserProfiles()[i].getAssigned() == ASSIGNED_CODE)
							{
								this.dgd_Profiles.select(this.userProfileArrayResult.getUserProfiles()[i]);
							}
						}
					}
					else
					{
						this.dgd_Profiles.setEnabled(false);
					}

				}
				if (!errorUserCompany.hasError())
				{
					if (resultDTO.getUserCompanyArrayResult() != null && resultDTO.getUserCompanyArrayResult().getStatuscode().equals("0"))
					{
						this.userCompanyArrayResult = new UserCompanyArrayResultDTO();

						this.userCompanyArrayResult.setCompanyDTOs((resultDTO.getUserCompanyArrayResult().getCompanyDTOs() != null)
								? resultDTO.getUserCompanyArrayResult().getCompanyDTOs() : new UserCompanyDTO[0]);
						ListDataProvider<UserCompanyDTO> dataproviderUserCompany = new ListDataProvider<>(
								Arrays.asList(this.userCompanyArrayResult.getCompanyDTOs()));
						this.dgd_Vendors.setDataProvider(dataproviderUserCompany, this.resetPageInfo);

						for (int i = 0; i < this.userCompanyArrayResult.getCompanyDTOs().length; i++)
						{
							if (this.userCompanyArrayResult.getCompanyDTOs()[i].getAssigned() == ASSIGNED_CODE)
							{
								this.dgd_Vendors.select(this.userCompanyArrayResult.getCompanyDTOs()[i]);
							}
						}
					}
					else
					{
						this.dgd_Profiles.setEnabled(false);
					}
				}

			}
		}
		this.stopWaiting();

	}

	private UserRequestResultUtilsDTO executeService(BbrUI parentUI)
	{
		UserRequestResultUtilsDTO result = new UserRequestResultUtilsDTO();

		PositionResultDTO positionResult = new PositionResultDTO();
		UserProfileArrayResultDTO userProfileArrayResult = new UserProfileArrayResultDTO();
		UserCompanyArrayResultDTO userCompanyArrayResult = new UserCompanyArrayResultDTO();

		try
		{
			Long adminid = this.getBbrUIParent().getUser().getId();
			positionResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal(parentUI).getAllPosition();
			Arrays.sort(positionResult.getPositionDTOs(), Comparator.comparing(PositionDTO::getName));
			result.setPositionResult(positionResult);

			userProfileArrayResult = EJBFactory.getUserEJBFactory().getFunctionalityManagerLocal(parentUI).getAllProfilesAdminCompany(this.user.getId(), adminid);
			Arrays.sort(userProfileArrayResult.getUserProfiles(), Comparator.comparing(UserProfileDTO::getProfilename));
			result.setUserProfileArrayResult(userProfileArrayResult);

			userCompanyArrayResult = EJBFactory.getUserEJBFactory().getFunctionalityManagerLocal(parentUI).getVendorForUserRequest(this.user.getId(), adminid);
			Arrays.sort(userCompanyArrayResult.getCompanyDTOs(), Comparator.comparing(UserCompanyDTO::getEmdesc));
			result.setUserCompanyArrayResult(userCompanyArrayResult);

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

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
