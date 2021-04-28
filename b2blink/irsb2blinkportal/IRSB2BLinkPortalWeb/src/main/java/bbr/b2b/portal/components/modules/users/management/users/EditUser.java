package bbr.b2b.portal.components.modules.users.management.users;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.Function;

import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.portal.classes.beans.SessionBean;
import bbr.b2b.portal.classes.constants.EnumUserType;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.BbrMessagesBoxUtils;
import bbr.b2b.portal.classes.utils.users.UserManagerUtils;
import bbr.b2b.portal.components.basics.BbrButtonGroup;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsConstants;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyArrayResultDTO;
import bbr.b2b.users.report.classes.CompanyDTO;
import bbr.b2b.users.report.classes.CompanyResultDTO;
import bbr.b2b.users.report.classes.LocationArrayResultDTO;
import bbr.b2b.users.report.classes.LocationDTO;
import bbr.b2b.users.report.classes.ProfileArrayResultDTO;
import bbr.b2b.users.report.classes.ProfileDTO;
import bbr.b2b.users.report.classes.UserActivateInitParamDTO;
import bbr.b2b.users.report.classes.UserDTO;
import bbr.b2b.users.report.classes.UsersResultDTO;
import cl.bbr.core.classes.basics.BbrPage;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrItemSelectField;
import cl.bbr.core.components.basics.BbrTabContent;
import cl.bbr.core.components.basics.BbrTabSheet;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.paging.BbrPagingManager;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;
import cl.bbr.core.interfaces.EntityEditor;

public class EditUser extends BbrWindow implements EntityEditor<UserDTO>, BbrWorkExecutor
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	// Constants
	private static final long				serialVersionUID		= 3247626779164695209L;
	// Components
	private BbrTextField					txt_UserName			= null;
	private BbrTextField					txt_UserLastName		= null;
	private BbrTextField					txt_UserCode			= null;
	private BbrTextField					txt_Position			= null;
	private BbrTextField					txt_UserType			= null;
	private BbrTextField					txt_Email				= null;
	private BbrTextField					txt_Phone				= null;
	private BbrItemSelectField<CompanyDTO>	slc_Enterprise			= null;
	private BbrAdvancedGrid<ProfileDTO>		dgd_Profiles			= null;
	private BbrAdvancedGrid<CompanyDTO>		dgd_Company				= null;
	private BbrAdvancedGrid<LocationDTO>	dgd_Locals;
	private BbrPagingManager				pagingManagerCompany	= null;
	private BbrPagingManager				pagingManagerLocals		= null;
	// Variables
	private UserDTO							item					= null;
	private ProfileDTO[]					assignedProfiles		= null;
	private CompanyDTO[]					assignedCompanies		= null;
	private LocationDTO[]					assignedLocals			= null;
	private final String					CMP_CODE_FIELD			= "rut";
	private final String					LOC_CODE_FIELD			= "code";
	private final int						DEFAULT_PAGE_NUMBER		= 1;
	private final int						MAX_ROWS_NUMBER			= 20;

	private BbrTabSheet						tabUserEditor			= null;

	private BbrTabContent					tabContentCompanies		= null;
	private BbrTabContent					tabContentLocals		= null;
	private Boolean							isCreate				= false;
	private CheckBox						chk_AllCompanies		= null;
	private CheckBox						chk_AllLocals			= null;
	private HorizontalLayout				pnlCompaniesLinks		= null;
	private HorizontalLayout				pnlLocalsLinks			= null;
	private boolean							allCompanies			= false;
	private boolean							allLocals				= false;
	private BbrWork<BaseResultDTO>			allCompaniesWork		= null;
	private BbrWork<BaseResultDTO>			allLocalsWork			= null;
	private BbrWork<BaseResultDTO>			activateUserWork		= null;
	private boolean							byUser					= false;
	private boolean							isCompaniesEdited		= false;

	@Override
	public void setItem(UserDTO item)
	{
		this.item = item;
	}

	@Override
	public UserDTO getItem()
	{
		return item;
	}

	public void setCreated(Boolean isCreate)
	{
		this.isCreate = isCreate;
	}

	public Boolean getCreated()
	{
		return isCreate;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public EditUser(BbrUI parent, UserDTO selectedUser)
	{
		super(parent);
		this.setItem(selectedUser);
	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	public void initializeView()
	{
		Button btn_EditUserInfo = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "edit"));
		btn_EditUserInfo.addStyleName("link-button");
		btn_EditUserInfo.addClickListener((ClickListener & Serializable) e -> userBasicInfo_clickHandler(e));

		// User Code Field
		this.txt_UserCode = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_person_id"));
		this.txt_UserCode.addStyleName("bbr-text-field-read-only");
		this.txt_UserCode.setWidth("100%");

		// User Name Field
		this.txt_UserName = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_name"));
		this.txt_UserName.addStyleName("bbr-text-field-read-only");
		this.txt_UserName.setWidth("100%");

		// User Last Name Field
		this.txt_UserLastName = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_lastname"));
		this.txt_UserLastName.addStyleName("bbr-text-field-read-only");
		this.txt_UserLastName.setWidth("100%");

		// User Enterprise
		this.slc_Enterprise = new BbrItemSelectField<CompanyDTO>(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_enterprise"));
		this.slc_Enterprise.setFieldName("name");
		this.slc_Enterprise.setDescriptionName("name");
		this.slc_Enterprise.setWidth("100%");

		// User Email
		this.txt_Email = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_email"));
		this.txt_Email.addStyleName("bbr-text-field-read-only");
		this.txt_Email.setWidth("100%");

		// User Phone
		this.txt_Phone = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_phone"));
		this.txt_Phone.addStyleName("bbr-text-field-read-only");
		this.txt_Phone.setWidth("100%");

		// User Type
		this.txt_UserType = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_type"));
		this.txt_UserType.addStyleName("bbr-text-field-read-only");
		this.txt_UserType.setWidth("100%");

		// User Position
		this.txt_Position = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_position"));
		this.txt_Position.addStyleName("bbr-text-field-read-only");
		this.txt_Position.setWidth("100%");

		// Close Button
		BbrButtonGroup bbrGroupButton = new BbrButtonGroup.BbrGroupButtonBuilder(this.getBbrUIParent())
				.withPrimaryButtonStyle("primary btn-login")
				.withPrimaryButtonLabel(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "accept"))
				.withPrimaryButtonListener((ClickListener & Serializable) (e) -> this.btnAccept_clickHandler())
				.withCancelButtonLabel(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "close"))
				.withCancelButtonListener((ClickListener & Serializable) (e) -> this.btnCancel_ClickHandler())
				.build();
		bbrGroupButton.getPrimaryButton().setClickShortcut(KeyCode.ENTER);

		// FormLayout as Panel Content
		FormLayout frmLeft = new FormLayout();
		frmLeft.setMargin(true);
		frmLeft.setSizeFull();
		frmLeft.addStyleName("generic-form");
		frmLeft.addComponents(this.txt_UserCode, this.txt_UserName, this.txt_UserLastName, this.slc_Enterprise);

		// FormLayout as Panel Content
		FormLayout frmRigth = new FormLayout();
		frmRigth.setMargin(true);
		frmRigth.setSizeFull();
		frmRigth.addStyleName("generic-form");
		frmRigth.addComponents(this.txt_Email, this.txt_Phone, this.txt_UserType, this.txt_Position);

		HorizontalLayout formLayout = new HorizontalLayout(frmLeft, frmRigth);
		formLayout.setWidth("100%");
		formLayout.setSpacing(false);

		this.updateUserBasicInfoForm();

		this.tabUserEditor = this.getTabs();

		// Main Layout
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.addComponents(btn_EditUserInfo, formLayout, this.tabUserEditor, bbrGroupButton);
		mainLayout.setComponentAlignment(btn_EditUserInfo, Alignment.TOP_RIGHT);
		mainLayout.setComponentAlignment(formLayout, Alignment.TOP_CENTER);
		mainLayout.setComponentAlignment(bbrGroupButton, Alignment.BOTTOM_CENTER);
		mainLayout.setExpandRatio(this.tabUserEditor, 1F);
		mainLayout.setSpacing(false);
		mainLayout.addStyleName("bbr-margin-windows-zero-top");

		// Main Windows
		this.setWidth("750px");
		this.setHeight("550px");
		this.setResizable(false);
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "edit_user"));
		this.setContent(mainLayout);
		this.setClosable(false);
	}

	private void btnCancel_ClickHandler()
	{
		String validationMessage = this.validateDataCancel();
		if (!validationMessage.isEmpty())
		{
			this.showWarningMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
					validationMessage,
					null, () -> this.close());
		}
		else
		{
			this.close();
		}
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		if (result != null)
		{
			if (triggerObject == this.chk_AllCompanies)
			{
				this.doAddAllCompaniesToUser(result, sender);
			}
			else if (triggerObject == this.chk_AllLocals)
			{
				this.doAddAllLocalsToUser(result, sender);
			}
			else if (result instanceof UsersResultDTO)
			{
				this.doUpdateActivateUser(result, sender);
			}
		}
		else
		{
			EditUser senderReport = (EditUser) sender;

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
	private String validateData()
	{
		String result = "";

		if ((this.assignedProfiles == null || this.assignedProfiles.length < 1))
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_profile");
		}
		else if (this.chk_AllCompanies != null && this.chk_AllCompanies.getValue().equals(false))
		{
			result = validateAssignedCompanies(result);
		}
		else if (this.chk_AllCompanies == null)
		{
			result = validateAssignedCompanies(result);
		}

		return result;
	}

	private String validateDataCancel()
	{
		String result = "";

		if ((this.assignedProfiles == null || this.assignedProfiles.length < 1)
				&& ((this.chk_AllCompanies != null && this.chk_AllCompanies.getValue().equals(false) || this.chk_AllCompanies == null) && (this.assignedCompanies == null || this.assignedCompanies.length < 1)))
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_user_profiles_and_providers_assigned");
		}
		else if ((this.assignedProfiles == null || this.assignedProfiles.length < 1))
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_user_profiles_assigned");
		}
		else if (this.chk_AllCompanies != null && this.chk_AllCompanies.getValue().equals(false))
		{
			result = validateAssignedCompanies(result);
		}
		else if (this.chk_AllCompanies == null)
		{
			result = validateAssignedCompanies(result);
		}

		return result;
	}

	private void btnAccept_clickHandler()
	{
		String result = "";
		this.updateProfiles();
		result = this.validateData();
		if ((result == null) || (result.length() == 0))
		{
			if (this.isCreate && !this.item.getActive())
			{
				this.activateUser_clickHandler(this.item);
			}
			BbrEvent updateEvent = new BbrEvent(BbrEvent.USER_UPDATE);
			this.dispatchBbrEvent(updateEvent);
			this.close();
		}
		else
		{
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), result);
		}
	}

	private String validateAssignedCompanies(String result)
	{
		if (this.assignedCompanies == null || this.assignedCompanies.length < 1)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_user_providers_assigned");
		}
		return result;
	}

	private void activateUser_clickHandler(UserDTO item)
	{
		if (item != null)
		{
			String alertMessage = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "sure_enable_user");

			BbrMessagesBoxUtils.showConfirmationMessage(this.getBbrUIParent(),
					I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_question"),
					alertMessage,
					(Runnable & Serializable) () -> this.doActivateUser(item),
					(Runnable & Serializable) () -> this.doCancel());

		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "select_at_least_one"));
		}
	}

	private void editProfile_clickHandler(ClickEvent e)
	{
		ProfilesEditor winProfileEditor = new ProfilesEditor(this.getBbrUIParent(), this.getItem());
		winProfileEditor.initializeView();
		winProfileEditor.addBbrEventListener((BbrEventListener & Serializable) evt -> profilesEdited_handler(evt));
		winProfileEditor.open(true);
	}

	private void profilesEdited_handler(BbrEvent e)
	{
		this.updateProfiles();
	}

	private void addCompanies_clickHandler(ClickEvent e)
	{
		AddCompanies winAddCompaniesEditor = new AddCompanies(this.getBbrUIParent(), this.getItem());
		winAddCompaniesEditor.initializeView();
		winAddCompaniesEditor.addBbrEventListener((BbrEventListener & Serializable) evt -> companiesEdited_handler(evt));
		winAddCompaniesEditor.open(true);
	}

	private void addLocals_clickHandler(ClickEvent e)
	{
		AddLocals winAddLocalsEditor = new AddLocals(this.getBbrUIParent(), this.getItem());
		winAddLocalsEditor.initializeView();
		winAddLocalsEditor.addBbrEventListener((BbrEventListener & Serializable) evt -> localsEdited_handler(evt));
		winAddLocalsEditor.open(true);
	}

	private void removeCompanies_clickHandler(ClickEvent e)
	{
		RemoveCompanies winRemoveCompaniesEditor = new RemoveCompanies(this.getBbrUIParent(), this.getItem());
		winRemoveCompaniesEditor.initializeView();
		winRemoveCompaniesEditor.addBbrEventListener((BbrEventListener & Serializable) evt -> companiesEdited_handler(evt));
		winRemoveCompaniesEditor.open(true);
	}

	private void removeLocals_clickHandler(ClickEvent e)
	{
		RemoveLocals winRemoveLocalEditor = new RemoveLocals(this.getBbrUIParent(), this.getItem());
		winRemoveLocalEditor.initializeView();
		winRemoveLocalEditor.addBbrEventListener((BbrEventListener & Serializable) evt -> localsEdited_handler(evt));
		winRemoveLocalEditor.open(true);
	}

	private void removeAllCompanies_clickHandler(ClickEvent e)
	{
		String message = "";
		try
		{
			if (this.assignedCompanies != null && this.assignedCompanies.length > 0)
			{
				BaseResultDTO addResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().deleteAllUserCompanyRelations(this.item.getId(), this.getUser().getId());
				if (addResult != null)
				{
					message = I18NManager.getErrorMessageBaseResult(addResult); // <--
					// Obtiene
					// el
					// mensaje
					// de
					// error.
					// ""
					// si
					// no
					// hay
					// errores.
				}
				else
				{
					// -> Error userResult = null
					message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
				}
			}
			else
			{
				message = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "no_companies_assigned");
			}
		}
		catch (Exception ex) // Error no controlado
		{
			message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
		}

		if (message.length() > 0)
		{
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), message);
		}
		else
		{
			this.updateCompaniesTab();
			message = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "successful_operation");
			this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"), message);
		}

	}

	private void removeAllLocals_clickHandler(ClickEvent e)
	{
		String message = "";
		try
		{
			if (this.assignedLocals != null && this.assignedLocals.length > 0)
			{
				BaseResultDTO addResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().deleteAllUserLocalRelations(this.item.getId(), this.getUser().getId());
				if (addResult != null)
				{
					message = I18NManager.getErrorMessageBaseResult(addResult);
				}
				else
				{
					// -> Error userResult = null
					message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
				}
			}
			else
			{
				message = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "no_locals_assigned");
			}
		}
		catch (Exception ex) // Error no controlado
		{
			message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
		}

		if (message.length() > 0)
		{
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), message);
		}
		else
		{
			this.updateLocalsTab();
			message = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "successful_operation");
			this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"), message);
		}

	}

	private void companiesEdited_handler(BbrEvent e)
	{
		this.isCompaniesEdited = true;
		this.updateCompaniesTab();
	}

	private void localsEdited_handler(BbrEvent e)
	{
		this.updateLocalsTab();
	}

	private void userBasicInfo_clickHandler(ClickEvent e)
	{
		UserDTO user = this.getItem();
		user.setAllenterprises(this.chk_AllCompanies != null ? this.chk_AllCompanies.getValue() : false);
		user.setAlllocals(this.chk_AllLocals != null ? this.chk_AllLocals.getValue() : false);
		EditBasicUserInfo winBasiUserInfoEditor = new EditBasicUserInfo(this.getBbrUIParent(), user);
		winBasiUserInfoEditor.initializeView();
		winBasiUserInfoEditor.addBbrEventListener((BbrEventListener & Serializable) evt -> userBasicInfo_handler(evt));
		winBasiUserInfoEditor.open(true);
	}

	private void userBasicInfo_handler(BbrEvent e)
	{
		if (e.getResultObject() != null)
		{
			this.item = (UserDTO) e.getResultObject();
			this.updateUserBasicInfoForm();
			this.updateProfiles();
			this.updateCompaniesTab();
			this.updateLocalsTab();
		}
	}

	private void pagingCompanyChange_Listener(BbrPagingEvent e)
	{
		if (e != null && e.getEventType().equals(BbrPagingEvent.PAGE_CHANGED) && e.getResultObject() != null)
		{
			this.updateCompanies(e.getResultObject().getCurrentPage(), false, true);
		}
	}

	private void pagingLocalsChange_Listener(BbrPagingEvent e)
	{
		if (e != null && e.getEventType().equals(BbrPagingEvent.PAGE_CHANGED) && e.getResultObject() != null)
		{
			this.updateLocals(e.getResultObject().getCurrentPage(), false, true);
		}
	}

	private void updateCompanies_ValueChangeHandler()
	{
		if (this.chk_AllCompanies.getValue())
		{
			String userFullText = UserManagerUtils.getValidFullnameOrEmailByUser(this.item);

			BbrMessagesBoxUtils.showConfirmationMessage(this.getBbrUIParent(),
					I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_question"),
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "all_companies_active",
							userFullText,
							""),
					(Runnable & Serializable) () -> this.setAllCompanies(),
					(Runnable & Serializable) () -> this.closeAllCompanies_Handler());

		}
		else
		{
			this.setAllCompanies();
			this.updateCompaniesComponents();
		}
	}

	private void updateLocals_ValueChangeHandler(Boolean byUser)
	{
		if (this.chk_AllLocals.getValue())
		{
			String userFullText = UserManagerUtils.getValidFullnameOrEmailByUser(this.item);

			BbrMessagesBoxUtils.showConfirmationMessage(this.getBbrUIParent(),
					I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_question"),
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "all_locals_active",
							userFullText,
							""),
					(Runnable & Serializable) () -> this.setAllLocals(),
					(Runnable & Serializable) () -> this.closeAllLocals_Handler());
		}
		else
		{
			this.setAllLocals();
			this.updateLocalsComponents();
		}
	}

	private void closeAllCompanies_Handler()
	{
		this.chk_AllCompanies.setValue(false);
	}

	private void closeAllLocals_Handler()
	{
		this.chk_AllLocals.setValue(false);
	}

	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************
	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

	private void updateCompaniesComponents()
	{
		if (this.chk_AllCompanies != null)
		{
			this.pnlCompaniesLinks.setEnabled(!this.chk_AllCompanies.getValue());
			this.dgd_Company.setEnabled(!this.chk_AllCompanies.getValue());
			this.pagingManagerCompany.setEnabled(!this.chk_AllCompanies.getValue());
			this.updateCompanies(this.DEFAULT_PAGE_NUMBER, true, true);
		}
	}

	private void updateLocalsComponents()
	{
		if (this.chk_AllLocals != null)
		{
			if (this.pagingManagerLocals != null)
			{
				this.pagingManagerLocals.setEnabled(!this.chk_AllLocals.getValue());
			}
			if (this.pnlLocalsLinks != null)
			{
				this.pnlLocalsLinks.setEnabled(!this.chk_AllLocals.getValue());
			}
			if (this.dgd_Locals != null)
			{
				this.dgd_Locals.setEnabled(!this.chk_AllLocals.getValue());
			}
		}
	}

	private void doCancel()
	{
		String userFullText = UserManagerUtils.getValidFullnameOrEmailByUser(this.item);

		this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_cancel_activation",
						userFullText,
						""));
	}

	private void doActivateUser(UserDTO item)
	{
		this.activateUserWork = new BbrWork<>(this, this.getBbrUIParent(), true);
		this.activateUserWork.addFunction(new Function<Object, BaseResultDTO>()
		{
			@Override
			public BaseResultDTO apply(Object t)
			{
				return executeActivateUserWorkService(EditUser.this.getBbrUIParent(), item);
			}
		});
		this.startWaiting();
		this.executeBbrWork(activateUserWork);
	}

	private BaseResultDTO executeActivateUserWorkService(BbrUI parentUI, UserDTO item)
	{
		BaseResultDTO result = null;
		try
		{
			UserActivateInitParamDTO userActivateInitParam = new UserActivateInitParamDTO();
			userActivateInitParam.setUserPKs(new Long[] { item.getId() });
			userActivateInitParam.setExternalRetail(UserManagerUtils.getIsExternalRetail());
			userActivateInitParam.setLocale(this.getBbrUIParent().getLocale());
			result = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().activateUser(userActivateInitParam);
		}
		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), parentUI);
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

	private void doUpdateActivateUser(Object result, BbrWorkExecutor sender)
	{
		EditUser senderResult = (EditUser) sender;
		UsersResultDTO reportResult = (UsersResultDTO) result;
		BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderResult.getBbrUIParent(), !senderResult.getBbrUIParent().hasAlertWindowOpen());

		if (!error.hasError())
		{
			String userFullText = UserManagerUtils.getValidFullnameOrEmailByUser(senderResult.item);
			// Operacion Exitosa.
			String resultMessage = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_successfully_activated",
					userFullText,
					"");

			senderResult.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"),
					resultMessage,
					"100px",
					() -> senderResult.doDispatchUpdateEvent());
		}
	}

	private void doDispatchUpdateEvent()
	{
		BbrEvent updateEvent = new BbrEvent(BbrEvent.USER_UPDATE);
		this.dispatchBbrEvent(updateEvent);
	}

	private BbrTabSheet getTabs()
	{
		this.tabUserEditor = new BbrTabSheet();
		this.tabUserEditor.setSizeFull();

		// Profile
		BbrTabContent tabContentProfiles = this.getProfileTab();
		this.tabUserEditor.addBbrTab(tabContentProfiles, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "profiles"), false);

		// Companies
		this.updateCompaniesTab();

		// Locals
		this.updateLocalsTab();

		return tabUserEditor;
	}

	private BbrTabContent getProfileTab()
	{
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.setWidth("100%");
		buttons.setMargin(false);

		Button btn_EditProfileInfo = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "edit_profiles"));
		btn_EditProfileInfo.addStyleName("link-button");
		btn_EditProfileInfo.addClickListener((ClickListener & Serializable) e -> this.editProfile_clickHandler(e));

		buttons.addComponent(btn_EditProfileInfo);
		buttons.setComponentAlignment(btn_EditProfileInfo, Alignment.BOTTOM_RIGHT);

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.addStyleName("bbr-margin-windows-zero-top");
		mainLayout.setSpacing(false);

		BbrTabContent result = new BbrTabContent();
		result.setSizeFull();
		SessionBean sessionBean = (SessionBean) (this.getBbrUIParent()).getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
		if (sessionBean != null)
		{
			this.dgd_Profiles = new BbrAdvancedGrid<>(this.getUser().getLocale());
			this.initializeProfilesGridColumns();
			this.dgd_Profiles.setSortable(false);
			this.dgd_Profiles.addStyleName("report-grid");
			this.dgd_Profiles.setSizeFull();
			this.dgd_Profiles.setResponsive(true);

			this.updateProfiles();

			mainLayout.addComponents(buttons, this.dgd_Profiles);
			mainLayout.setExpandRatio(this.dgd_Profiles, 1F);
			result.addComponent(mainLayout);
		}

		return result;
	}

	private void initializeProfilesGridColumns()
	{
		this.dgd_Profiles.addColumn(ProfileDTO::getName)
				.setDescriptionGenerator(ProfileDTO::getName)
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "name"));
		this.dgd_Profiles.addColumn(ProfileDTO::getDescription)
				.setDescriptionGenerator(ProfileDTO::getDescription)
				.setWidth(450D)
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "description"));
	}

	private void initializeCompaniesGridColumns()
	{
		this.dgd_Company.addColumn(CompanyDTO::getRut)
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "code"));
		this.dgd_Company.addColumn(CompanyDTO::getName)
				.setWidth(450D)
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "description"));
	}

	private void updateCompaniesTab()
	{
		if (this.tabContentCompanies == null)
		{
			this.tabContentCompanies = new BbrTabContent();
			this.tabContentCompanies.setSizeFull();
			this.tabUserEditor.addBbrTab(this.tabContentCompanies, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "vendors"), false);
		}
		else
		{
			this.tabContentCompanies.removeAllComponents();
		}

		Button btn_AddCompanies = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "add_companies"));
		btn_AddCompanies.addStyleName("link-button");
		btn_AddCompanies.addClickListener((ClickListener & Serializable) e -> addCompanies_clickHandler(e));

		Button btn_RemoveCompanies = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "remove_companies"));
		btn_RemoveCompanies.addStyleName("link-button");
		btn_RemoveCompanies.addClickListener((ClickListener & Serializable) e -> removeCompanies_clickHandler(e));

		Button btn_RemoveAllCompanies = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "remove_all_companies"));
		btn_RemoveAllCompanies.addStyleName("link-button");
		btn_RemoveAllCompanies.addClickListener((ClickListener & Serializable) e -> removeAllCompanies_clickHandler(e));

		this.pnlCompaniesLinks = new HorizontalLayout(btn_AddCompanies, btn_RemoveCompanies, btn_RemoveAllCompanies);
		HorizontalLayout pnlHeader = new HorizontalLayout();
		pnlHeader.setWidth("100%");

		// All Companies
		this.allCompanies = false;
		Boolean isProvider = this.item.getCompanytype().equals(EnumUserType.PROVIDER.getValue());
		if (!isProvider)
		{
			if (this.item.getAllenterprises() != null)
			{
				this.chk_AllCompanies = new CheckBox(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "all_companies"), this.allCompanies);
				this.chk_AllCompanies.addStyleName("generic-text-field");
				this.chk_AllCompanies.setWidth("100%");
				pnlHeader.addComponent(this.chk_AllCompanies);
				pnlHeader.setComponentAlignment(this.chk_AllCompanies, Alignment.BOTTOM_LEFT);
				if (!this.isCompaniesEdited)
				{
					this.allCompanies = this.item.getAllenterprises();
					this.chk_AllCompanies.setValue(this.allCompanies);
				}
				this.chk_AllCompanies.addValueChangeListener((ValueChangeListener<Boolean> & Serializable) e -> this.updateCompanies_ValueChangeHandler());
			}
		}

		pnlHeader.addComponent(this.pnlCompaniesLinks);
		pnlHeader.setComponentAlignment(this.pnlCompaniesLinks, Alignment.BOTTOM_RIGHT);

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.addStyleName("bbr-margin-windows-zero-top");
		mainLayout.setSpacing(false);

		SessionBean sessionBean = (SessionBean) this.getBbrUIParent().getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
		if (sessionBean != null)
		{
			// Paging Manager
			this.pagingManagerCompany = new BbrPagingManager();
			this.pagingManagerCompany.setLocale(this.getUser().getLocale());
			this.pagingManagerCompany.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> pagingCompanyChange_Listener(e),
					BbrPagingEvent.PAGE_CHANGED);

			this.dgd_Company = new BbrAdvancedGrid<>(this.getUser().getLocale());
			this.initializeCompaniesGridColumns();
			this.dgd_Company.setSortable(false);
			this.dgd_Company.addStyleName("report-grid");
			this.dgd_Company.setSizeFull();
			this.dgd_Company.setPagingManager(this.pagingManagerCompany, this.CMP_CODE_FIELD);
			this.updateCompanies(this.DEFAULT_PAGE_NUMBER, true, true);

			mainLayout.addComponents(pnlHeader, this.dgd_Company, this.pagingManagerCompany);
			mainLayout.setExpandRatio(this.dgd_Company, 1F);
			mainLayout.setComponentAlignment(pnlHeader, Alignment.TOP_RIGHT);
			this.tabContentCompanies.addComponent(mainLayout);
		}
		this.updateCompaniesComponents();
	}

	private void updateLocalsTab()
	{
		if (!this.item.getCompanytype().equals(EnumUserType.PROVIDER.getValue()))
		{
			if (this.tabContentLocals == null)
			{
				this.tabContentLocals = new BbrTabContent();
				this.tabContentLocals.setSizeFull();
				this.tabUserEditor.addBbrTab(this.tabContentLocals, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "locals"), false);
			}
			else
			{
				this.tabContentLocals.removeAllComponents();
			}

			Button btn_AddLocals = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "add_locals"));
			btn_AddLocals.addStyleName("link-button");
			btn_AddLocals.addClickListener((ClickListener & Serializable) e -> addLocals_clickHandler(e));

			Button btn_RemoveLocals = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "remove_locals"));
			btn_RemoveLocals.addStyleName("link-button");
			btn_RemoveLocals.addClickListener((ClickListener & Serializable) e -> removeLocals_clickHandler(e));

			Button btn_RemoveAllLocals = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "remove_all_locals"));
			btn_RemoveAllLocals.addStyleName("link-button");
			btn_RemoveAllLocals.addClickListener((ClickListener & Serializable) e -> removeAllLocals_clickHandler(e));

			this.pnlLocalsLinks = new HorizontalLayout(btn_AddLocals, btn_RemoveLocals, btn_RemoveAllLocals);

			HorizontalLayout pnlHeader = new HorizontalLayout();
			pnlHeader.setWidth("100%");
			// All Locals
			this.chk_AllLocals = new CheckBox(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "all_locals"),
					(this.item.getAllenterprises() != null && this.item.getAllenterprises() == true));
			this.chk_AllLocals.addStyleName("generic-text-field");
			this.chk_AllLocals.setWidth("100%");
			this.byUser = true;
			this.allLocals = false;
			Boolean isProvider = this.item.getCompanytype().equals(EnumUserType.PROVIDER.getValue());
			if (!isProvider)
			{
				pnlHeader.addComponent(this.chk_AllLocals);
				pnlHeader.setComponentAlignment(this.chk_AllLocals, Alignment.BOTTOM_LEFT);
				this.allLocals = this.item.getAlllocals();
				this.chk_AllLocals.setValue(this.allLocals);
			}
			this.chk_AllLocals.addValueChangeListener((ValueChangeListener<Boolean> & Serializable) e -> this.updateLocals_ValueChangeHandler(this.byUser));

			pnlHeader.addComponent(this.pnlLocalsLinks);
			pnlHeader.setComponentAlignment(this.pnlLocalsLinks, Alignment.BOTTOM_RIGHT);
			VerticalLayout mainLayout = new VerticalLayout();
			mainLayout.setSizeFull();
			mainLayout.addStyleName("bbr-margin-windows-zero-top");
			mainLayout.setSpacing(false);

			SessionBean sessionBean = (SessionBean) this.getBbrUIParent().getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
			if (sessionBean != null)
			{
				// Paging Manager
				this.pagingManagerLocals = new BbrPagingManager();
				this.pagingManagerLocals.setLocale(this.getUser().getLocale());
				this.pagingManagerLocals.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> pagingLocalsChange_Listener(e),
						BbrPagingEvent.PAGE_CHANGED);

				this.dgd_Locals = new BbrAdvancedGrid<>(this.getUser().getLocale());
				this.dgd_Locals.addColumn(LocationDTO::getCode).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "code"));
				this.dgd_Locals.addColumn(LocationDTO::getName).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "description"));
				this.dgd_Locals.setSortable(false);
				this.dgd_Locals.addStyleName("report-grid");
				this.dgd_Locals.setSizeFull();
				this.dgd_Locals.setPagingManager(this.pagingManagerLocals, this.LOC_CODE_FIELD);

				this.updateLocals(this.DEFAULT_PAGE_NUMBER, true, true);

				mainLayout.addComponents(pnlHeader, this.dgd_Locals, this.pagingManagerLocals);
				mainLayout.setExpandRatio(this.dgd_Locals, 1F);
				mainLayout.setComponentAlignment(pnlHeader, Alignment.TOP_RIGHT);
				this.tabContentLocals.addComponent(mainLayout);
			}
			if (this.chk_AllLocals != null)
			{
				this.updateLocalsComponents();
			}
		}
		else
		{
			if (this.tabContentLocals != null && this.tabUserEditor.getTab(this.tabContentLocals) != null)
			{
				this.tabUserEditor.removeTab(this.tabContentLocals);
				this.tabContentLocals = null;
			}
		}

	}

	private void setAllCompanies()
	{
		this.allCompaniesWork = new BbrWork<>(this, this.getBbrUIParent(), this.chk_AllCompanies);
		this.allCompaniesWork.addFunction(new Function<Object, BaseResultDTO>()
		{
			@Override
			public BaseResultDTO apply(Object t)
			{
				return executeServiceAllCompanies(EditUser.this.getBbrUIParent());
			}
		});
		this.executeBbrWork(allCompaniesWork);
		this.startWaiting();
	}

	private void setAllLocals()
	{
		this.allLocalsWork = new BbrWork<>(this, this.getBbrUIParent(), this.chk_AllLocals);
		this.allLocalsWork.addFunction(new Function<Object, BaseResultDTO>()
		{
			@Override
			public BaseResultDTO apply(Object t)
			{
				return executeServiceAllLocals(EditUser.this.getBbrUIParent());
			}
		});
		this.executeBbrWork(allLocalsWork);
		this.startWaiting();
	}

	private BaseResultDTO executeServiceAllCompanies(BbrUI parentUI)
	{
		BaseResultDTO result = null;
		try
		{
			result = EJBFactory.getUserEJBFactory().getUserReportManagerLocal(this.getBbrUIParent()).addAllCompaniesToUser(this.item.getId(),
					this.chk_AllCompanies.getValue(), this.getUser().getId());
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

	private BaseResultDTO executeServiceAllLocals(BbrUI parentUI)
	{
		BaseResultDTO result = null;

		try
		{
			result = EJBFactory.getUserEJBFactory().getUserReportManagerLocal(this.getBbrUIParent()).addAllLocalsToUser(this.item.getId(), this.chk_AllLocals.getValue(), this.getUser().getId());
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

	private void updateProfiles()
	{
		try
		{
			ProfileArrayResultDTO profilesResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal(this.getBbrUIParent())
					.getAssignedProfilesOfUser(this.getItem().getId(), 0, 0,
							false, false, null);

			if (profilesResult != null && profilesResult.getProfiles() != null)
			{
				this.assignedProfiles = profilesResult.getProfiles();

				ListDataProvider<ProfileDTO> dataprovider = new ListDataProvider<>(Arrays.asList(this.assignedProfiles));
				this.dgd_Profiles.setDataProvider(dataprovider);
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
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private void updateCompanies(int pageNumber, boolean resetPageInfo, boolean isPaginated)
	{
		try
		{
			CompanyArrayResultDTO companiesResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal(this.getBbrUIParent())
					.getAssignedCompaniesOfUser(this.getItem().getId(), pageNumber, this.MAX_ROWS_NUMBER, resetPageInfo, isPaginated, null);
			if (companiesResult != null && companiesResult.getCompanyDTOs() != null)
			{
				this.assignedCompanies = companiesResult.getCompanyDTOs();

				ListDataProvider<CompanyDTO> dataprovider = new ListDataProvider<>(Arrays.asList(this.assignedCompanies));
				this.dgd_Company.setDataProvider(dataprovider);

				if (resetPageInfo)
				{
					PageInfoDTO pageInfo = companiesResult.getPageInfo();

					BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
					this.pagingManagerCompany.setPages(bbrPage, resetPageInfo);
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
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private void updateLocals(int pageNumber, boolean resetPageInfo, boolean isPaginated)
	{
		try
		{
			LocationArrayResultDTO localsResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal(this.getBbrUIParent())
					.getAssignedLocationsOfUser(this.getItem().getId(),
							pageNumber, this.MAX_ROWS_NUMBER, resetPageInfo, isPaginated, null);

			if (localsResult != null && localsResult.getLocationDTOs() != null)
			{
				this.assignedLocals = localsResult.getLocationDTOs();

				ListDataProvider<LocationDTO> dataprovider = new ListDataProvider<>(Arrays.asList(this.assignedLocals));
				this.dgd_Locals.setDataProvider(dataprovider);

				if (resetPageInfo)
				{
					PageInfoDTO pageInfo = localsResult.getPageInfoDTO();

					BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
					this.pagingManagerLocals.setPages(bbrPage, resetPageInfo);
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
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private void updateUserBasicInfoForm()
	{
		if (this.item != null)
		{
			// User Code Field
			this.txt_UserCode.setReadOnly(false);
			this.txt_UserCode.setValue((item.getPersonalid() != null) ? item.getPersonalid().trim() : "");
			this.txt_UserCode.setReadOnly(true);

			// User Name Field
			this.txt_UserName.setReadOnly(false);
			this.txt_UserName.setValue((item.getName() != null) ? item.getName().trim() : "");
			this.txt_UserName.setReadOnly(true);

			// User Last Name Field
			this.txt_UserLastName.setReadOnly(false);
			this.txt_UserLastName.setValue((item.getLastname() != null) ? item.getLastname().trim() : "");
			this.txt_UserLastName.setReadOnly(true);

			// User Enterprise
			this.slc_Enterprise.setReadOnly(false);
			CompanyResultDTO companyResult = EJBFactory.getUserOpenEJBFactory().findCompanyById(getItem().getEmkey());
			if (companyResult != null)
			{
				this.slc_Enterprise.setValue(companyResult.getCompanyDTO());
				this.slc_Enterprise.setReadOnly(true);
				this.slc_Enterprise.addStyleName("bbr-text-field-read-only");
			}

			// User Email
			this.txt_Email.setReadOnly(false);
			this.txt_Email.setValue((this.item.getEmail() != null) ? this.item.getEmail().trim() : "");
			this.txt_Email.setReadOnly(true);

			// User Phone
			this.txt_Phone.setReadOnly(false);
			this.txt_Phone.setValue((this.item.getPhone() != null) ? this.item.getPhone().trim() : "");
			this.txt_Phone.setReadOnly(true);

			// User Type
			this.txt_UserType.setReadOnly(false);
			this.txt_UserType.setValue((this.item.getCompanytype() != null) ? EnumUserType.getEnumUserType(this.item.getCompanytype().trim()).getDescription() : "");
			this.txt_UserType.setReadOnly(true);

			// User Position
			this.txt_Position.setReadOnly(false);
			this.txt_Position.setValue((this.item.getPosition() != null) ? this.item.getPosition().trim() : "");
			this.txt_Position.setReadOnly(true);
		}
	}

	private void doAddAllCompaniesToUser(Object result, BbrWorkExecutor sender)
	{
		try
		{
			EditUser senderResult = (EditUser) sender;
			BaseResultDTO reportResult = (BaseResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderResult.getBbrUIParent(), !senderResult.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				// Operación Exitosa.
				this.updateCompaniesComponents();
				this.stopWaiting();
				String resultMessage = "";
				if (this.chk_AllCompanies.getValue())
				{
					resultMessage = I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "all_companies_assigned");
					ListDataProvider<CompanyDTO> dataprovider = new ListDataProvider<>(this.dgd_Company.getAllSelectedsItems());
					this.dgd_Company.setDataProvider(dataprovider);
				}
				else
				{
					resultMessage = I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "all_companies_not_assigned");
				}
				this.showInfoMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"), resultMessage);
			}
			else
			{
				// errordescription =
				// BbrUtils.getInstance().substitute("{0} - {1} -
				// Internal Error", error.getErrorCode(),
				// error.getErrorMessage());
			}
		}
		catch (Exception ex) // Error no controlado
		{
			// errordescription =
			// I18NManager.getI18NString(this.getBbrUIParent(),BbrUtilsResources.RES_SYSTEM,
			// "U1");
		}

	}

	private void doAddAllLocalsToUser(Object result, BbrWorkExecutor sender)
	{
		try
		{
			EditUser senderResult = (EditUser) sender;
			BaseResultDTO reportResult = (BaseResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderResult.getBbrUIParent(), !senderResult.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				// Operación Exitosa.
				this.updateLocalsComponents();
				this.stopWaiting();
				String resultMessage = "";
				if (this.chk_AllLocals.getValue())
				{
					resultMessage = I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "all_locals_assigned");
					ListDataProvider<LocationDTO> dataprovider = new ListDataProvider<>(this.dgd_Locals.getAllSelectedsItems());
					this.dgd_Locals.setDataProvider(dataprovider);
				}
				else
				{
					resultMessage = I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "all_locals_not_assigned");
				}
				this.showInfoMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"), resultMessage);
			}
			else
			{
				// errordescription =
				// BbrUtils.getInstance().substitute("{0} - {1} -
				// Internal Error", error.getErrorCode(),
				// error.getErrorMessage());
			}
		}
		catch (Exception ex) // Error no controlado
		{
			// errordescription =
			// I18NManager.getI18NString(this.getBbrUIParent(),BbrUtilsResources.RES_SYSTEM,
			// "U1");
		}

	}
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
