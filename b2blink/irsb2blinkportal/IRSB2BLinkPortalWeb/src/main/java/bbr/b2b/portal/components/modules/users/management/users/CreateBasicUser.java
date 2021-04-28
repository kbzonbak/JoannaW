package bbr.b2b.portal.components.modules.users.management.users;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Function;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.server.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox.NewItemProvider;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.beans.SessionBean;
import bbr.b2b.portal.classes.constants.EnumUserType;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.users.UserManagerUtils;
import bbr.b2b.portal.classes.wrappers.management.NewUserInfo;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.utils.generic.FindProvider;
import bbr.b2b.portal.constants.BbrUtilsConstants;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.PortalConstants;
import bbr.b2b.users.report.classes.CompanyDTO;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import bbr.b2b.users.report.classes.CompanyResultDTO;
import bbr.b2b.users.report.classes.PositionDTO;
import bbr.b2b.users.report.classes.PositionResultDTO;
import bbr.b2b.users.report.classes.UserDTO;
import bbr.b2b.users.report.classes.UserDataDTO;
import bbr.b2b.users.report.classes.UserInitParamDTO;
import bbr.b2b.users.report.classes.UserLikeInitParamDTO;
import bbr.b2b.users.report.classes.UserResultDTO;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrItemSelectField;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class CreateBasicUser extends BbrWindow implements BbrWorkExecutor
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	// Constants
	private static final long				serialVersionUID			= 3247626779164695209L;
	// Components
	private BbrTextField					txt_UserName				= null;
	private BbrTextField					txt_UserLastName			= null;
	private BbrTextField					txt_UserCode				= null;
	private BbrItemSelectField<CompanyDTO>	slc_Enterprise				= null;
	private BbrComboBox<PositionDTO>		cbx_Positions				= null;
	private BbrTextField					txt_Email					= null;
	private BbrTextField					txt_Phone					= null;
	// Variables
	private String							userType					= null;
	private BbrWork<UserResultDTO>			createUserWork				= null;
	private BbrWork<UserResultDTO>			createNotProviderUserWork	= null;
	private UserDataDTO						baseUser					= null;
	private boolean							isCreateUserLike			= false;
	private BbrWork<UserResultDTO>			createUserLikeWork			= null;
	private CompanyResultDTO				companyResult				= null;
	private boolean							isExternalRetail			= true;

	public void setUserTypeSelected(String userType)
	{
		this.userType = userType;
	}

	private boolean isRetailOrBbrType()
	{
		boolean isProviderType = this.userType != null && (userType.equalsIgnoreCase(EnumUserType.RETAILER.getValue()) || userType.equalsIgnoreCase(EnumUserType.BBR.getValue()));
		return isProviderType;
	}

	private boolean isRetailType()
	{
		boolean isProviderType = this.userType != null && (userType.equalsIgnoreCase(EnumUserType.RETAILER.getValue()));
		return isProviderType;
	}

	private boolean isBbrType()
	{
		boolean isProviderType = this.userType != null && (userType.equalsIgnoreCase(EnumUserType.BBR.getValue()));
		return isProviderType;
	}

	public void setCreateUserLike(UserDataDTO baseUser)
	{
		// se define el usuario base caracteristicas ex:empresa,usertype
		this.isCreateUserLike = true;
		this.baseUser = baseUser;
		this.setUserTypeSelected(baseUser.getUsertype());
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public CreateBasicUser(BbrUI parent)
	{
		super(parent);
	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	public void initializeView()
	{
		this.isExternalRetail = UserManagerUtils.getIsExternalRetail();

		// User Code Field
		this.txt_UserCode = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_person_id"));
		this.txt_UserCode.setMaxLength(50);
		this.txt_UserCode.setRequiredIndicatorVisible(true);
		this.txt_UserCode.addStyleName("bbr-fields");
		this.txt_UserCode.focus();

		// User Name Field
		this.txt_UserName = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_name"));
		this.txt_UserName.setMaxLength(50);
		this.txt_UserName.setRequiredIndicatorVisible(true);
		this.txt_UserName.addStyleName("bbr-fields");

		// User Last Name Field
		this.txt_UserLastName = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_lastname"));
		this.txt_UserLastName.setMaxLength(50);
		this.txt_UserLastName.setRequiredIndicatorVisible(true);
		this.txt_UserLastName.addStyleName("bbr-fields");

		// User Enterprise
		this.slc_Enterprise = new BbrItemSelectField<CompanyDTO>(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_enterprise"));
		this.slc_Enterprise.setFieldName("name");
		this.slc_Enterprise.setDescriptionName("name");
		this.slc_Enterprise.addStyleName("bbr-fields");
		this.slc_Enterprise.addStyleName("content-required");
		this.slc_Enterprise.setRequiredIndicatorVisible(true);
		this.slc_Enterprise.addBbrEventListener((BbrEventListener & Serializable) e -> userEnterprise_handler(e));

		Label lbl_EnterpriseCode = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_enterprise"));
		lbl_EnterpriseCode.setWidth("140px");

		HorizontalLayout enterpriseCodeLayout = new HorizontalLayout(lbl_EnterpriseCode, slc_Enterprise);
		enterpriseCodeLayout.setWidth("100%");
		enterpriseCodeLayout.setExpandRatio(slc_Enterprise, 1F);

		// User Position
		initializePositionsComboBox();
		this.cbx_Positions.addStyleName("bbr-fields");
		this.cbx_Positions.addValueChangeListener((ValueChangeListener<PositionDTO> & Serializable) e -> this.positions_ValueChangeHandler(e));

		// User Email
		this.txt_Email = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_email"));
		this.txt_Email.setRequiredIndicatorVisible(true);
		this.txt_Email.addStyleName("bbr-fields");

		// User Phone
		this.txt_Phone = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_phone"));
		this.txt_Phone.setMaxLength(20);
		// this.txt_Phone.setRestrict(RestrictRange.PHONE);
		this.txt_Phone.addStyleName("bbr-fields");

		// Register User Button
		Button btn_RegisterUser = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "accept"));
		btn_RegisterUser.setStyleName("primary");
		btn_RegisterUser.addStyleName("btn-generic");
		btn_RegisterUser.setWidth("100%");
		btn_RegisterUser.addClickListener((ClickListener & Serializable) e -> this.btnCreate_clickHandler(this.isRetailOrBbrType()));

		// Cancel Button
		Button btn_Cancel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"));
		btn_Cancel.setStyleName("primary");
		btn_Cancel.addStyleName("btn-generic");
		btn_Cancel.setWidth("100%");
		btn_Cancel.addClickListener((ClickListener & Serializable) e -> this.close());

		HorizontalLayout buttonsPanel = new HorizontalLayout(btn_RegisterUser, btn_Cancel);
		buttonsPanel.addStyleName("bbr-buttons-panel");

		buttonsPanel.setWidth("300px");
		buttonsPanel.setSpacing(true);

		// FormLayout as Panel Content
		FormLayout frmEditUser = new FormLayout();
		frmEditUser.addStyleName("generic-form");
		frmEditUser.setWidth("450px");
		frmEditUser.setHeight("100%");

		if (this.isCreateUserLike)
		{
			this.companyResult = EJBFactory.getUserOpenEJBFactory().findCompanyById(this.baseUser.getCompanyid());
		}

		if (this.isRetailOrBbrType())
		{
			if (this.isRetailType() && !this.isExternalRetail)
			{
				frmEditUser.addComponents(this.txt_UserCode, this.txt_Email, this.txt_UserName, this.txt_UserLastName, this.txt_Phone, this.cbx_Positions);
				this.setHeight("330px");
			}
			else
			{
				frmEditUser.addComponents(this.txt_Email, this.txt_Phone);
				this.setHeight("180px");
			}
		}
		else
		{
			frmEditUser.addComponents(this.txt_UserCode, this.txt_Email, this.txt_UserName, this.txt_UserLastName, this.txt_Phone, this.cbx_Positions, this.slc_Enterprise);
			this.setHeight("370px");

			if (this.companyResult != null)
			{
				this.slc_Enterprise.setValue(companyResult.getCompanyDTO());
				this.slc_Enterprise.setReadOnly(true);
				this.slc_Enterprise.addStyleName("bbr-text-field-read-only");
			}
		}

		// Main Layout
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.addComponents(frmEditUser, buttonsPanel);
		mainLayout.setComponentAlignment(frmEditUser, Alignment.MIDDLE_CENTER);
		mainLayout.setComponentAlignment(buttonsPanel, Alignment.BOTTOM_CENTER);
		mainLayout.setExpandRatio(frmEditUser, 1F);

		if (this.isRetailOrBbrType())
		{
			mainLayout.setExpandRatio(buttonsPanel, 1F);
		}
		else
		{
			mainLayout.setExpandRatio(buttonsPanel, 0.2F);
		}
		mainLayout.setMargin(false);

		// Main Windows
		this.setWidth("500px");
		this.setResizable(false);
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "add_user"));
		this.setContent(mainLayout);
		this.addStyleName("win-details");
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		CreateBasicUser senderReport = (CreateBasicUser) sender;
		if (result != null)
		{
			// USUARIO CREADO NORMALMENTE
			if (result instanceof UserResultDTO)
			{
				UserResultDTO addResult = (UserResultDTO) result;
				this.doUpdateReport(senderReport, addResult);
			}
			// USUARIO CREADO COMO
		}
		else
		{
			if (!senderReport.getBbrUIParent().hasAlertWindowOpen())
				senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));

			senderReport.stopWaiting();
		}
	}

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	private void doUpdateReport(CreateBasicUser senderReport, UserResultDTO result)
	{
		if (result != null)
		{
			BbrError error = ErrorsMngr.getInstance().getError(result, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());
			if (!error.hasError())
			{
				String userFullName = UserManagerUtils.getValidFullnameOrEmailByUser(result.getUser());
				this.close();
				String resultMessage;
				if (this.isCreateUserLike)
				{
					String baseUserFullName = UserManagerUtils.getValidFullnameOrEmailByUser(this.baseUser);
					resultMessage = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_successfully_created_like", userFullName, baseUserFullName);
				}
				else
				{
					resultMessage = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_successfully_created", userFullName, "");
				}

				this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"),
						resultMessage,
						"",
						() -> this.dispatchUserAddedBbrEvent(result));
			}
			else
			{
				senderReport.stopWaiting();
				if (!senderReport.getBbrUIParent().hasAlertWindowOpen())
				{
					senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
							I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));
				}

			}
		}
		else
		{
			senderReport.stopWaiting();
			senderReport.close();
		}
	}

	private void dispatchUserAddedBbrEvent(UserResultDTO result)
	{
		BbrEvent createEvent = new BbrEvent(!this.isCreateUserLike ? BbrEvent.USER_ADDED : BbrEvent.ITEM_CREATED);
		createEvent.setResultObject((result != null) ? result.getUser() : null);
		this.dispatchBbrEvent(createEvent);
	}

	private void positions_ValueChangeHandler(ValueChangeEvent<PositionDTO> e)
	{
		PositionDTO positionDTO = new PositionDTO();
		positionDTO = e.getValue();
		if (positionDTO != null)
		{
			positionDTO.setSelected(true);
		}
	}

	private void btnCreate_clickHandler(Boolean isRetailOrBbrType)
	{
		if (this.isCreateUserLike)
		{
			// desde acá se ejecuta la creación como de usuario
			this.createUserLike(isRetailOrBbrType);
		}
		else
		{
			// desde acá se ejecuta la creación basica de usuario
			this.createUser(isRetailOrBbrType);
		}
	}

	private void createUserLike(Boolean isRetailOrBbrType)
	{
		if (isRetailOrBbrType)
		{
			NewUserInfo userInfo = this.initializeNewUserInfo(isRetailOrBbrType);
			String message = userInfo.validateData(isRetailOrBbrType);
			if (message != null && message.length() > 0)
			{
				this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), message);
				this.validateEmailData();
			}
			else
			{
				this.initializeExecuteCreateUserLikeService(userInfo.toUserDTO(), this.baseUser.getId());
			}
		}
		else
		{
			NewUserInfo userInfo = this.initializeNewUserInfo(isRetailOrBbrType);
			String message = userInfo.validateData(isRetailOrBbrType);
			if (message != null && message.length() > 0)
			{
				this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), message);
				this.validateData();
			}
			else
			{
				this.initializeExecuteCreateUserLikeService(userInfo.toUserDTO(), this.baseUser.getId());
			}
		}
	}

	private void initializeExecuteCreateUserLikeService(UserDTO newUser, Long userBaseId)
	{
		this.createUserLikeWork = new BbrWork<>(this, CreateBasicUser.this.getBbrUIParent(), BbrEvent.ITEM_CREATED);
		this.createUserLikeWork.addFunction(new Function<Object, UserResultDTO>()
		{
			@Override
			public UserResultDTO apply(Object t)
			{
				return executeServiceCreateUserLike(CreateBasicUser.this.getBbrUIParent(), newUser, userBaseId);
			}

		});

		this.startWaiting();
		this.executeBbrWork(this.createUserLikeWork);
	}

	private void createUser(Boolean isRetailOrBbrType)
	{
		if (isRetailOrBbrType)
		{
			NewUserInfo userInfo = this.initializeNewUserInfo(isRetailOrBbrType);
			String message = userInfo.validateData(isRetailOrBbrType);
			if (message != null && message.length() > 0)
			{
				this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), message);
				if (this.isRetailType() && !this.isExternalRetail)
				{
					this.validateInternalRetailData();
				}
				else
				{
					this.validateEmailData();
				}
			}
			else
			{
				if (this.isRetailType() && !this.isExternalRetail)
				{
					this.initializeExecuteCreateBasicUserService(userInfo);
				}
				else
				{
					this.initializeExecuteCreateNotProviderService(userInfo);
				}
			}
		}
		else
		{
			NewUserInfo userInfo = this.initializeNewUserInfo(isRetailOrBbrType);
			String message = userInfo.validateData(isRetailOrBbrType);
			if (message != null && message.length() > 0)
			{
				this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), message);
				this.validateData();
			}
			else
			{
				this.initializeExecuteCreateBasicUserService(userInfo);
			}
		}
	}

	private void initializeExecuteCreateBasicUserService(NewUserInfo userInfo)
	{
		createUserWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		createUserWork.addFunction(new Function<Object, UserResultDTO>()
		{
			@Override
			public UserResultDTO apply(Object t)
			{
				return executeCreateBasicUserService(CreateBasicUser.this.getBbrUIParent(), userInfo);
			}
		});
		this.startWaiting();
		this.executeBbrWork(createUserWork);
	}

	private void initializeExecuteCreateNotProviderService(NewUserInfo userInfo)
	{
		createNotProviderUserWork = new BbrWork<>(this, this.getBbrUIParent(), BbrEvent.ITEM_CREATED);
		createNotProviderUserWork.addFunction(new Function<Object, UserResultDTO>()
		{
			@Override
			public UserResultDTO apply(Object t)
			{
				return executeCreateNotProviderService(CreateBasicUser.this.getBbrUIParent(), userInfo);
			}
		});
		this.startWaiting();
		this.executeBbrWork(createNotProviderUserWork);
	}

	private NewUserInfo initializeNewUserInfo(Boolean isRetailOrBbrType)
	{
		if (isRetailOrBbrType)
		{
			if (this.isRetailType() && !this.isExternalRetail)
			{
				// Cuando los usuarios son retail interno la empresa es ID=1
				NewUserInfo userInfo = new NewUserInfo.NewUserInfoBuilder()
						.withUsername(this.txt_UserName.getValue())
						.withUserLastname(this.txt_UserLastName.getValue())
						.withEnterpriseKey(1L)
						.withUserCode(this.txt_UserCode.getValue())
						.withPosition(this.cbx_Positions.getSelectedValue() != null ? this.cbx_Positions.getSelectedValue().getName() : null)
						.withMail(this.txt_Email.getValue())
						.withPhone(this.txt_Phone.getValue())
						.withIsExternalRetail()
						.withIsRetailType(this.isRetailType())
						.build();
				return userInfo;
			}
			else
			{
				NewUserInfo userInfo = new NewUserInfo.NewUserInfoBuilder()
						.withMail(this.txt_Email.getValue())
						.withPhone(this.txt_Phone.getValue())
						.build();
				return userInfo;
			}
		}
		else
		{
			NewUserInfo userInfo = new NewUserInfo.NewUserInfoBuilder()
					.withUsername(this.txt_UserName.getValue())
					.withUserLastname(this.txt_UserLastName.getValue())
					.withEnterpriseCode(this.slc_Enterprise.getValue() != null ? this.slc_Enterprise.getValue().getRut() : null)
					.withEnterpriseKey(this.slc_Enterprise.getValue() != null ? this.slc_Enterprise.getValue().getId() : null)
					.withUserCode(this.txt_UserCode.getValue())
					.withPosition(this.cbx_Positions.getSelectedValue() != null ? this.cbx_Positions.getSelectedValue().getName() : null)
					.withMail(this.txt_Email.getValue())
					.withPhone(this.txt_Phone.getValue())
					.build();
			return userInfo;
		}
	}

	private UserResultDTO executeCreateBasicUserService(BbrUI bbrUIParent, NewUserInfo userInfo)
	{
		UserResultDTO result = null;
		try
		{
			UserDTO user = userInfo.toUserDTO();
			SessionBean sessionBean = (SessionBean) this.getBbrUIParent().getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
			UserInitParamDTO userInitParamDTO = new UserInitParamDTO();
			userInitParamDTO.setUser(user);
			userInitParamDTO.setAccessToken(sessionBean.getAccessToken());
			userInitParamDTO.setUseradmin(this.getUser().getId());
			userInitParamDTO.setExternalRetail(this.isExternalRetail);
			userInitParamDTO.setLocale(this.getBbrUIParent().getLocale());
			result = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().addBasicUser(userInitParamDTO);
		}
		catch (BbrUserException ex)
		{
			AppUtils.getInstance().doLogOut(ex.getMessage(), bbrUIParent);
		}
		catch (BbrSystemException ex)
		{
			ex.printStackTrace();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return result;
	}

	private UserResultDTO executeCreateNotProviderService(BbrUI bbrUIParent, NewUserInfo userInfo)
	{
		UserResultDTO result = null;
		try
		{
			Long id = PortalConstants.ID_RETAIL;
			if (this.isRetailType())
			{
				id = PortalConstants.ID_RETAIL;
			}
			else if (this.isBbrType())
			{
				id = PortalConstants.ID_BBR;
			}
			result = EJBFactory.getUserEJBFactory().getUserReportManagerLocal(bbrUIParent).addNoProviderBasicUserByAdministration(userInfo.getMail(), userInfo.getPhone(), id, this.getUser().getId());
		}
		catch (BbrUserException ex)
		{
			AppUtils.getInstance().doLogOut(ex.getMessage(), bbrUIParent);
		}
		catch (BbrSystemException ex)
		{
			ex.printStackTrace();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return result;
	}

	private void userEnterprise_handler(BbrEvent event)
	{
		FindProvider winFindCompany = new FindProvider(this.getBbrUIParent(), true);
		winFindCompany.initializeView();
		winFindCompany.addBbrEventListener((BbrEventListener & Serializable) e -> companySelected_handler(e));
		winFindCompany.open(true);
	}

	private void companySelected_handler(BbrEvent event)
	{
		CompanyDataDTO companyData = (CompanyDataDTO) event.getResultObject();
		if (companyData != null)
		{
			CompanyDTO company = new CompanyDTO();
			company.setId(companyData.getId());
			company.setName(companyData.getName());
			company.setRut(companyData.getRut());
			slc_Enterprise.setValue(company);

		}
	}

	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

	private void validateData()
	{
		txt_UserCode.setComponentError(null);
		txt_UserName.setComponentError(null);
		txt_UserLastName.setComponentError(null);
		txt_Email.setComponentError(null);
		// txt_Phone.setComponentError(null);

		cbx_Positions.setComponentError(null);
		slc_Enterprise.setComponentError(null);

		String userCode = txt_UserCode.getValue().trim();
		String userName = txt_UserName.getValue().trim();
		String userLastName = txt_UserLastName.getValue().trim();
		String userEmail = txt_Email.getValue().trim();
		// String userPhone = txt_Phone.getValue().trim();

		PositionDTO position = cbx_Positions.getSelectedValue();
		CompanyDTO company = slc_Enterprise.getValue();

		if (userCode == null || userCode.length() <= 0) // -> Código deUsuario
		{
			txt_UserCode.setComponentError(new UserError(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_user_code")));
		}
		if (userName == null || userName.length() <= 0 || userName.length() > 50) // ->Nombre
		{
			txt_UserName.setComponentError(new UserError(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_name")));
		}
		if (userLastName == null || userLastName.length() <= 0 || userLastName.length() > 50) // ->Apellido
		{
			txt_UserLastName.setComponentError(new UserError(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_last_name")));
		}
		if (!BbrUtils.getInstance().checkEmailAddress(userEmail))// ->Email
		{
			txt_Email.setComponentError(new UserError(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_mail")));
		}
		// if (userPhone.length() > 20 || userPhone.length() > 0 &&
		// !BbrUtils.getInstance().isPhoneRegEx(userPhone))// ->Teléfono
		// {
		// txt_Phone.setComponentError(new
		// UserError(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN,
		// "valid_phone")));
		// }
		if (position == null)// -> Cargo
		{
			cbx_Positions.setComponentError(new UserError(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_position")));
		}
		if (company == null)// -> Empresa
		{
			slc_Enterprise.setComponentError(new UserError(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_company")));
		}
	}

	private void validateInternalRetailData()
	{
		txt_UserCode.setComponentError(null);
		txt_UserName.setComponentError(null);
		txt_UserLastName.setComponentError(null);
		txt_Email.setComponentError(null);

		cbx_Positions.setComponentError(null);

		String userCode = txt_UserCode.getValue().trim();
		String userName = txt_UserName.getValue().trim();
		String userLastName = txt_UserLastName.getValue().trim();
		String userEmail = txt_Email.getValue().trim();

		PositionDTO position = cbx_Positions.getSelectedValue();

		if (userCode == null || userCode.length() <= 0) // -> Código deUsuario
		{
			txt_UserCode.setComponentError(new UserError(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_user_code")));
		}
		if (userName == null || userName.length() <= 0 || userName.length() > 50) // ->Nombre
		{
			txt_UserName.setComponentError(new UserError(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_name")));
		}
		if (userLastName == null || userLastName.length() <= 0 || userLastName.length() > 50) // ->Apellido
		{
			txt_UserLastName.setComponentError(new UserError(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_last_name")));
		}
		if (!BbrUtils.getInstance().checkEmailAddress(userEmail))// ->Email
		{
			txt_Email.setComponentError(new UserError(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_mail")));
		}
		if (position == null)// -> Cargo
		{
			cbx_Positions.setComponentError(new UserError(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_position")));
		}
	}

	private void validateEmailData()
	{
		txt_Email.setComponentError(null);

		String userEmail = txt_Email.getValue().trim();

		if (!BbrUtils.getInstance().checkEmailAddress(userEmail))// ->Email
		{
			String userType = this.isBbrType() ? EnumUserType.BBR.getValue() : this.isRetailType() ? EnumUserType.RETAILER.getValue() : "";
			txt_Email.setComponentError(new UserError(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_mail_by_usertype", userType.toUpperCase())));
		}
	}

	private void initializePositionsComboBox()
	{
		try
		{
			PositionResultDTO positionsResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().getAllPosition();
			PositionDTO[] positions = positionsResult.getPositionDTOs();

			this.cbx_Positions = new BbrComboBox<PositionDTO>(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_position"));
			this.cbx_Positions.setItems(positions);
			this.cbx_Positions.setItemCaptionGenerator(PositionDTO::getName);
			this.cbx_Positions.setTextInputAllowed(true);
			this.cbx_Positions.setEmptySelectionAllowed(false);
			this.cbx_Positions.setPlaceholder(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "define_position"));
			this.cbx_Positions.setNewItemProvider((NewItemProvider<PositionDTO> & Serializable) e -> this.newItemHandler(e));
			this.cbx_Positions.setRequiredIndicatorVisible(true);
			this.cbx_Positions.setWidth("100%");
		}
		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), this.getBbrUIParent());
		}
		catch (BbrSystemException e)
		{
			e.printStackTrace();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private Optional<PositionDTO> newItemHandler(Object e)
	{
		String name = (String) e;
		PositionDTO newPosition = new PositionDTO();
		if (name != null)
		{
			newPosition.setName(name);
			newPosition.setId(-1L);

			cbx_Positions.addItem(newPosition);
			cbx_Positions.setSelectedItem(newPosition);
			cbx_Positions.markAsDirtyRecursive();
		}
		return Optional.of(newPosition);
	}

	private UserResultDTO executeServiceCreateUserLike(BbrUI bbrUI, UserDTO newUser, Long userBaseId)
	{
		UserResultDTO result = null;
		try
		{
			if (this.isCreateUserLike)
			{
				if (newUser.getPosition() == null)
				{
					newUser.setPosition(PortalConstants.DEFAULT_POSITION);
				}
				if (newUser.getPersonalid() == null)
				{
					newUser.setPersonalid("");
				}
				if (newUser.getEmkey() == null)
				{
					if (!this.isExternalRetail)
					{
						// Cuando los usuarios son retail interno empresa es ID=1L
						newUser.setEmkey(1L);
					}
					else
					{
						newUser.setEmkey(this.companyResult.getCompanyDTO().getId());
					}
				}
			}

			SessionBean sessionBean = (SessionBean) this.getBbrUIParent().getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);

			UserLikeInitParamDTO userLikeInitParam = new UserLikeInitParamDTO();
			userLikeInitParam.setUser(newUser);
			userLikeInitParam.setUseridbase(userBaseId);
			userLikeInitParam.setAccessToken(sessionBean.getAccessToken());
			userLikeInitParam.setUseradmin(this.getUser().getId());
			userLikeInitParam.setExternalRetail(this.isExternalRetail);
			userLikeInitParam.setLocale(this.getBbrUIParent().getLocale());

			result = EJBFactory.getUserEJBFactory().getUserReportManagerLocal(bbrUI).createUserLike(userLikeInitParam);
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
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
