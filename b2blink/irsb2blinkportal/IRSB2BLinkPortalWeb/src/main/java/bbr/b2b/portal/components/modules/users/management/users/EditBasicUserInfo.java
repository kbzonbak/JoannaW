package bbr.b2b.portal.components.modules.users.management.users;

import java.io.Serializable;
import java.util.Optional;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox.NewItemProvider;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.portal.classes.beans.SessionBean;
import bbr.b2b.portal.classes.constants.EnumUserType;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.users.UserManagerUtils;
import bbr.b2b.portal.classes.wrappers.management.EditUserInfo;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.utils.generic.FindProvider;
import bbr.b2b.portal.constants.BbrUtilsConstants;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyDTO;
import bbr.b2b.users.report.classes.CompanyResultDTO;
import bbr.b2b.users.report.classes.PositionDTO;
import bbr.b2b.users.report.classes.PositionResultDTO;
import bbr.b2b.users.report.classes.UserDTO;
import bbr.b2b.users.report.classes.UserInitParamDTO;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrItemSelectField;
import cl.bbr.core.components.basics.BbrMessageBox;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;
import cl.bbr.core.components.widgets.bbrtextfield.client.RestrictRange;
import cl.bbr.core.interfaces.EntityEditor;

public class EditBasicUserInfo extends BbrWindow implements EntityEditor<UserDTO>
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long				serialVersionUID	= 3247626779164695209L;

	private BbrTextField					txt_UserName		= null;
	private BbrTextField					txt_UserLastName	= null;
	private BbrTextField					txt_UserCode		= null;
	private BbrItemSelectField<CompanyDTO>	slc_Enterprise		= null;
	private BbrComboBox<PositionDTO>		cbx_Positions		= null;

	private BbrTextField					txt_Email			= null;
	private BbrTextField					txt_Phone			= null;

	private UserDTO							item				= null;

	private CompanyDTO						originalCompany		= null;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public EditBasicUserInfo(BbrUI parent, UserDTO user)
	{
		super(parent);
		this.setItem(user);
	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	public void initializeView()
	{
		if (this.getItem() != null)
		{
			// User Code Field
			this.txt_UserCode = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_person_id"));
			this.txt_UserCode.setValue((item.getPersonalid() != null) ? item.getPersonalid().trim() : "");
			this.txt_UserCode.addStyleName("bbr-text-field-read-only");
			this.txt_UserCode.setReadOnly(true);

			// User Email
			this.txt_Email = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_email"));
			this.txt_Email.setValue((item.getEmail() != null) ? item.getEmail().trim() : "");
			this.txt_Email.addStyleName("bbr-text-field-read-only");
			this.txt_Email.setReadOnly(true);

			// User Name Field
			this.txt_UserName = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_name"));
			this.txt_UserName.setValue((item.getName() != null) ? item.getName().trim() : "");
			this.txt_UserName.setMaxLength(50);
			this.txt_UserName.addStyleName("bbr-fields");

			// User Last Name Field
			this.txt_UserLastName = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_lastname"));
			this.txt_UserLastName.setValue((item.getLastname() != null) ? item.getLastname().trim() : "");
			this.txt_UserLastName.setMaxLength(50);
			this.txt_UserLastName.addStyleName("bbr-fields");

			// User Enterprise
			this.slc_Enterprise = new BbrItemSelectField<CompanyDTO>(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_enterprise"));
			this.slc_Enterprise.setFieldName("name");
			this.slc_Enterprise.setDescriptionName("name");
			this.slc_Enterprise.addStyleName("bbr-fields");
			this.slc_Enterprise.setReadOnly(true);
			this.slc_Enterprise.addStyleName("bbr-text-field-read-only");

			CompanyResultDTO companyResult;
			companyResult = EJBFactory.getUserOpenEJBFactory().findCompanyById(getItem().getEmkey());
			if (companyResult != null)
			{
				this.originalCompany = companyResult.getCompanyDTO();
				this.slc_Enterprise.setValue(this.originalCompany);
				this.slc_Enterprise.addBbrEventListener((BbrEventListener & Serializable) e -> userEnterprise_handler(e));
			}

			// User Phone
			this.txt_Phone = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_phone"));
			this.txt_Phone.setValue((item.getPhone() != null) ? item.getPhone().trim() : "");
			this.txt_Phone.addStyleName("bbr-fields");
			this.txt_Phone.setRestrict(RestrictRange.PHONE);
			this.txt_Phone.setMaxLength(20);

			// User Position
			this.initializePositionsComboBox(this.getItem().getPosition());
			this.cbx_Positions.addStyleName("bbr-fields");
			this.cbx_Positions.setWidth("100%");
			this.cbx_Positions.setPlaceholder(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "define_position"));

			Boolean isProvider = this.item.getCompanytype().equals(EnumUserType.PROVIDER.getValue());
			if (isProvider || (this.item.getCompanytype().equals(EnumUserType.RETAILER.getValue()) && !UserManagerUtils.getIsExternalRetail()))
			{
				this.txt_UserName.setReadOnly(false);
				this.txt_UserName.focus();
				this.txt_UserLastName.setReadOnly(false);
				this.cbx_Positions.setReadOnly(false);
			}
			else
			{
				this.txt_UserName.setReadOnly(true);
				this.txt_UserName.addStyleName("bbr-text-field-read-only");
				this.txt_UserLastName.setReadOnly(true);
				this.txt_UserLastName.addStyleName("bbr-text-field-read-only");
				this.cbx_Positions.setReadOnly(true);
				this.cbx_Positions.addStyleName("bbr-text-field-read-only");
				this.cbx_Positions.addStyleName("background");
				this.cbx_Positions.addStyleName("bbr-text-field-read-only");
			}

			// Update User Button
			Button btn_UpdateUser = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "save"));
			btn_UpdateUser.setStyleName("primary");
			btn_UpdateUser.addStyleName("btn-generic");
			btn_UpdateUser.setWidth("100%");
			btn_UpdateUser.addClickListener((ClickListener & Serializable) e -> btnUpdate_clickHandler(e));
			btn_UpdateUser.setClickShortcut(KeyCode.ENTER);

			// Cancel Button
			Button btn_Cancel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"));
			btn_Cancel.setStyleName("primary");
			btn_Cancel.addStyleName("btn-generic");
			btn_Cancel.setWidth("100%");
			btn_Cancel.addClickListener((ClickListener & Serializable) e -> btnClose_clickHandler(e));

			HorizontalLayout buttonsPanel = new HorizontalLayout(btn_UpdateUser, btn_Cancel);
			buttonsPanel.addStyleName("bbr-buttons-panel");

			buttonsPanel.setWidth("300px");
			buttonsPanel.setSpacing(true);

			// FormLayout as Panel Content
			FormLayout frmChangeUser = new FormLayout();
			frmChangeUser.addStyleName("generic-form");
			frmChangeUser.setWidth("450px");
			frmChangeUser.setHeight("100%");
			frmChangeUser.addComponents(this.txt_UserCode, this.txt_Email, this.txt_UserName, this.txt_UserLastName, this.txt_Phone, this.cbx_Positions, this.slc_Enterprise);

			// Main Layout
			VerticalLayout mainLayout = new VerticalLayout();
			mainLayout.setSizeFull();
			mainLayout.addComponents(frmChangeUser, buttonsPanel);
			mainLayout.setComponentAlignment(frmChangeUser, Alignment.MIDDLE_CENTER);
			mainLayout.setComponentAlignment(buttonsPanel, Alignment.BOTTOM_CENTER);
			mainLayout.setExpandRatio(frmChangeUser, 1F);
			mainLayout.setExpandRatio(buttonsPanel, 0.2F);
			mainLayout.setMargin(false);
			// Main Windows
			this.setWidth("500px");
			this.setHeight("370px");
			this.setResizable(false);
			this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "win_title_edit_user_basic_info"));
			this.setContent(mainLayout);
			this.addStyleName("win-details");

		}
	}

	@Override
	public void setItem(UserDTO item)
	{
		this.item = item;
	}

	@Override
	public UserDTO getItem()
	{
		return this.item;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************
	private void btnUpdate_clickHandler(ClickEvent event)
	{
		if (this.slc_Enterprise.getValue() != null && this.cbx_Positions.getSelectedValue() != null)
		{
			EditUserInfo userInfo = new EditUserInfo(this.getItem(), this.txt_UserName.getValue(), this.txt_UserLastName.getValue(), this.slc_Enterprise.getValue(), this.txt_Email.getValue(), this.txt_Phone.getValue(),
					this.cbx_Positions.getSelectedValue().getName(), this.item.getAllenterprises(), this.item.getAlllocals());
			SessionBean sessionBean = (SessionBean) this.getBbrUIParent().getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
			this.doUpdateBasicUser(userInfo, sessionBean.getAccessToken());

		}
		else // -> Falta empresa o Cargo
		{
			String message = I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "msg_missing_fields");

			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), message);
			validateData();
		}

	}

	private void btnClose_clickHandler(ClickEvent event)
	{
		this.close();
	}

	private void userEnterprise_handler(BbrEvent event)
	{
		FindProvider winFindCompany = new FindProvider(this.getBbrUIParent(), false);
		winFindCompany.initializeView();
		winFindCompany.addBbrEventListener((BbrEventListener & Serializable) e -> companySelected_handler(e));
		winFindCompany.open(true);
	}

	private void companySelected_handler(BbrEvent event)
	{
		CompanyDTO company = (CompanyDTO) event.getResultObject();
		if (company != null)
		{
			if (!company.getId().equals(originalCompany.getId()))
			{
				String msg = "";
				if (originalCompany.getTekey().equals(EnumUserType.PROVIDER.getId()))
				{
					msg = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "msg_provider_update");
				}
				else
				{
					msg = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "msg_not_provider_update");
				}
				BbrMessageBox
						.createWarning(EditBasicUserInfo.this.getBbrUIParent())
						.withNoButton()
						.withYesButton(new Runnable()
						{
							@Override
							public void run()
							{
								updateCompanyField(company);
							}
						})
						.withCaption(I18NManager.getI18NString(EditBasicUserInfo.this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_warning"))
						.withHtmlMessage(msg)
						.withWidthForAllButtons("100px")
						.open();
			}
			else
			{
				updateCompanyField(company);
			}
		}
	}

	private void updateCompanyField(CompanyDTO company)
	{
		if (company != null)
		{
			slc_Enterprise.setValue(company);
		}
	}

	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private void doUpdateBasicUser(EditUserInfo editUserInfo, String accessToken)
	{
		String message = "";
		BaseResultDTO userResult = new BaseResultDTO();
		try
		{
			if (editUserInfo != null)
			{
				message = editUserInfo.validateData();
				if (message == null || message.length() == 0)
				{
					if (!editUserInfo.getCompany().getId().equals(originalCompany.getId()))
					{
						// Eliminar perfiles, locales y proveedores si cambi� la
						// empresa
						EJBFactory.getUserEJBFactory().getUserReportManagerLocal().deleteAllUserProfileRelations(this.getItem().getId(), this.getUser().getId());
						EJBFactory.getUserEJBFactory().getUserReportManagerLocal().deleteAllUserCompanyRelations(this.getItem().getId(), this.getUser().getId());
						EJBFactory.getUserEJBFactory().getUserReportManagerLocal().deleteAllUserLocalRelations(this.getItem().getId(), this.getUser().getId());
					}

					this.setItem(editUserInfo.getUpdatedUser());

					UserInitParamDTO userInitParam = new UserInitParamDTO();
					userInitParam.setUser(this.getItem());
					userInitParam.setAccessToken(accessToken);
					userInitParam.setUseradmin(this.getUser().getId());
					userInitParam.setExternalRetail(UserManagerUtils.getIsExternalRetail());
					
					userResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal(this.getBbrUIParent()).updateUserBasicInfo(userInitParam);
					BaseResultDTO reportResult = (BaseResultDTO) userResult;

					BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), !this.getBbrUIParent().hasAlertWindowOpen());

					if (error.hasError())
					{
						message = BbrUtils.getInstance().substitute("{0} - {1} - Internal Error", error.getErrorCode(), error.getErrorMessage());
					}
				}
				else
				{
					this.validateData();
				}
			}

		}
		catch (Exception e) // Error no controlado
		{
			message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
		}

		if (message != null && message.length() > 0)
		{
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), message);
		}
		else
		{
			String userFullText = UserManagerUtils.getValidFullnameOrEmailByUser(this.getItem());

			message = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_successfully_edited",
					userFullText,
					"");
			this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"), message);

			BbrEvent updateEvent = new BbrEvent(BbrEvent.USER_UPDATE);
			updateEvent.setResultObject((userResult != null) ? this.getItem() : null);
			this.dispatchBbrEvent(updateEvent);
			this.close();
		}
	}

	private void validateData()
	{
		txt_UserName.setComponentError(null);
		txt_UserLastName.setComponentError(null);
		txt_Email.setComponentError(null);
		txt_Phone.setComponentError(null);
		cbx_Positions.setComponentError(null);
		slc_Enterprise.setComponentError(null);

		String userName = txt_UserName.getValue().trim();
		String userLastName = txt_UserLastName.getValue().trim();
		String userEmail = txt_Email.getValue().trim();
		String userPhone = txt_Phone.getValue().trim();
		PositionDTO position = cbx_Positions.getSelectedValue();
		CompanyDTO company = slc_Enterprise.getValue();

		if (userName == null || userName.length() <= 0 || userName.length() > 50) // ->
																					// Nombre
		{
			txt_UserName.setComponentError(new UserError(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_name")));
		}
		if (userLastName == null || userLastName.length() <= 0 || userLastName.length() > 50) // ->
																								// Apellido
		{
			txt_UserLastName.setComponentError(new UserError(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_last_name")));
		}
		if (!BbrUtils.getInstance().checkEmailAddress(userEmail))// -> Email
		{
			txt_Email.setComponentError(new UserError(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_mail")));
		}
		if (userPhone == null || userPhone.length() > 0 && !BbrUtils.getInstance().isPhoneRegEx(userPhone))// ->
																											// Tel�fono
		{
			txt_Phone.setComponentError(new UserError(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_phone")));
		}
		if (position == null || (position.getName().length() > 20 && !BbrUtils.getInstance().isAlphaNumericWithSpecialCharsRegEx(position.getName())))// ->
																																						// Cargo
		{
			cbx_Positions.setComponentError(new UserError(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_position")));
		}
		if (company == null)// -> Empresa
		{
			slc_Enterprise.setComponentError(new UserError(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_company")));
		}

	}

	private void initializePositionsComboBox(String position)
	{
		try
		{
			PositionResultDTO positionsResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().getAllPosition();
			PositionDTO[] positions = positionsResult.getPositionDTOs();

			cbx_Positions = new BbrComboBox<PositionDTO>(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "user_position"));
			cbx_Positions.setItems(positions);
			cbx_Positions.setItemCaptionGenerator(PositionDTO::getName);
			cbx_Positions.setTextInputAllowed(true);
			cbx_Positions.setEmptySelectionAllowed(false);
			cbx_Positions.setPlaceholder(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "define"));
			cbx_Positions.setNewItemProvider((NewItemProvider<PositionDTO> & Serializable) e -> this.newItemHandler(e));

			if (positions != null)
			{
				for (PositionDTO item : positions)
				{
					if (item.getName().equalsIgnoreCase(position))
					{
						cbx_Positions.setSelectedItem(item);
						break;
					}
				}
			}
			cbx_Positions.setWidth(100F, Unit.PERCENTAGE);
		}
		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), this.getBbrUIParent());
		}
		catch (BbrSystemException e)
		{
			e.printStackTrace();
		}
	}

	private Optional<PositionDTO> newItemHandler(Object e)
	{
		String name = (String) e;

		PositionDTO newPosition = new PositionDTO();
		newPosition.setName(name);
		newPosition.setId(-1L);

		cbx_Positions.addItem(newPosition);
		cbx_Positions.setSelectedItem(newPosition);
		cbx_Positions.markAsDirtyRecursive();

		return Optional.of(newPosition);
	}

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
