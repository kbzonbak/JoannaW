package bbr.b2b.portal.components.walldiary;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.basics.OptionalCommentAlertDTO;
import bbr.b2b.portal.classes.beans.SessionBean;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.users.UserManagerUtils;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.basics.OptionalCommentAlert;
import bbr.b2b.portal.components.modules.users.management.user_request.CreateEditUserRequest;
import bbr.b2b.portal.constants.BbrUtilsConstants;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.AddRequestAdminPVInitParamDTO;
import bbr.b2b.users.report.classes.AddRequestResultDTO;
import bbr.b2b.users.report.classes.UserDTO;
import bbr.b2b.users.report.classes.UserRequestDTO;
import bbr.b2b.users.report.classes.UsersResultDTO;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.utilities.BbrDateUtils;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;

public class CompanyUsersPanel extends VerticalLayout
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long			serialVersionUID		= 4475805477573182647L;
	private BbrAdvancedGrid<UserDTO>	dgd_CompanyUsers		= null;
	private BbrUI						bbrUI					= null;
	private Boolean						resetPageInfo			= true;
	private Button						btn_EditUserRequest		= null;
	private Button						btn_AddUserRequest		= null;
	private Button						btn_DeleteUserRequest	= null;
	private UserDTO						selectedUser			= null;
	private UserDTO						itemLast				= null;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================
	public CompanyUsersPanel(BbrUI bbrUI)
	{
		this.bbrUI = bbrUI;
		initializeControl();
	}

	private BbrUI getBbrUIParent()
	{
		return this.bbrUI;
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	private void initializeControl()
	{
		this.setMargin(false);
		SessionBean sessionBean = (SessionBean) this.getBbrUIParent().getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
		if (sessionBean != null)
		{
			try
			{
				HorizontalLayout pnlAdminButtons = new HorizontalLayout();
				HorizontalLayout pnlAdminLeftButtons = new HorizontalLayout();
				boolean isAdminCompanyProfile = false;
				// SE VALIDA QUE EL USUARIO TENGA EL PERFIL "ADM_USER_PROVEEDOR"
				isAdminCompanyProfile = sessionBean.getUser().getIsadmincompany();
				if (isAdminCompanyProfile) // Crear
				{
					this.btn_AddUserRequest = new Button("", EnumToolbarButton.ADD_ALTERNATIVE.getResource());
					this.btn_AddUserRequest.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "add_user"));
					this.btn_AddUserRequest.addClickListener((ClickListener & Serializable) e -> this.btn_AddNewUserRequest_clickHandler(e));
					this.btn_AddUserRequest.addStyleName("toolbar-button");
					pnlAdminLeftButtons.addComponent(this.btn_AddUserRequest);
					this.btn_EditUserRequest = new Button("", EnumToolbarButton.EDIT_ALTERNATIVE.getResource());
					this.btn_EditUserRequest.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "edit_user"));
					this.btn_EditUserRequest.addClickListener((ClickListener & Serializable) e -> this.btn_AddEditUserRequest_clickHandler(e));
					this.btn_EditUserRequest.addStyleName("toolbar-button");
					this.btn_EditUserRequest.setEnabled(false);
					pnlAdminLeftButtons.addComponent(this.btn_EditUserRequest);
					this.btn_DeleteUserRequest = new Button("", EnumToolbarButton.DELETE.getResource());
					this.btn_DeleteUserRequest.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "desactivate_user"));
					this.btn_DeleteUserRequest.addClickListener((ClickListener & Serializable) e -> this.btn_DeleteUserRequest_clickHandler());
					this.btn_DeleteUserRequest.addStyleName("toolbar-button");
					this.btn_DeleteUserRequest.setEnabled(false);
					pnlAdminLeftButtons.addComponent(this.btn_DeleteUserRequest);
					pnlAdminButtons.setHeight("25px");
					pnlAdminButtons.setWidth("100%");
					pnlAdminButtons.setMargin(false);
					pnlAdminButtons.addComponents(pnlAdminLeftButtons);
					pnlAdminButtons.setExpandRatio(pnlAdminLeftButtons, 1);
					pnlAdminButtons.setComponentAlignment(pnlAdminLeftButtons, Alignment.MIDDLE_LEFT);
					this.addComponents(pnlAdminButtons);
				}
				// Grilla
				this.dgd_CompanyUsers = new BbrAdvancedGrid<>(this.getBbrUIParent().getUser().getLocale());
				this.dgd_CompanyUsers.setHeaderVisible(false);
				this.dgd_CompanyUsers.removeHeaderRow(0);
				this.dgd_CompanyUsers.addSelectionListener((SelectionListener<UserDTO> & Serializable) e -> this.selection_gridHandler(e));
				this.initializeGridColumns();

				UsersResultDTO result = EJBFactory.getUserEJBFactory().getUserReportManagerLocal(this.getBbrUIParent()).getUsersByCompany(this.getBbrUIParent().getUser().getEnterpriseId());
				UsersResultDTO userReportDataResult = (UsersResultDTO) result;

				BbrError error = ErrorsMngr.getInstance().getError(userReportDataResult, this.getBbrUIParent(), !this.getBbrUIParent().hasAlertWindowOpen());
				if (!error.hasError())
				{
					UserDTO[] users = userReportDataResult.getUserDTOs();
					Arrays.sort(users, new SortUsersByLastlogin());

					ListDataProvider<UserDTO> dataprovider = new ListDataProvider<>(Arrays.asList(users));
					this.dgd_CompanyUsers.setDataProvider(dataprovider, this.resetPageInfo);
				}

				else
				{
					this.dgd_CompanyUsers.setEnabled(false);
				}
				this.dgd_CompanyUsers.setSizeFull();
				this.addComponent(this.dgd_CompanyUsers);
				this.setExpandRatio(this.dgd_CompanyUsers, 1F);
				this.setSizeFull();

			}
			catch (BbrUserException e)
			{
				AppUtils.getInstance().doLogOut(e.getMessage(), this.getBbrUIParent());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		}

	}
	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================
	private void btn_AddNewUserRequest_clickHandler(ClickEvent e)
	{
		UserDTO user = new UserDTO();
		user.setId(-1L);
		this.viewAddUserRequest(user);
	}

	private void btn_AddEditUserRequest_clickHandler(ClickEvent e)
	{
		UserDTO user = new UserDTO();
		user = this.selectedUser;
		this.viewAddUserRequest(user);
	}

	private void btn_DeleteUserRequest_clickHandler()
	{
		this.viewDeleteUserRequest();
	}

	private void selectableRow_handler(UserDTO item)
	{
		if (item != null)
		{
			if (this.itemLast == null)
			{
				this.itemLast = new UserDTO();
				this.itemLast.setId(-1L);
			}
			if (item.getId().equals(this.itemLast.getId()))
			{
				this.dgd_CompanyUsers.deselect(item);
				this.itemLast = new UserDTO();
				this.itemLast.setId(-1L);
			}
			else
			{
				this.itemLast = item;
				this.dgd_CompanyUsers.select(item);
			}

		}
	}

	private void selection_gridHandler(SelectionEvent<UserDTO> event)
	{
		try
		{
			this.selectedUser = new UserDTO();
			if (event.getAllSelectedItems().size() > 0)
			{
				this.selectedUser = event.getAllSelectedItems().iterator().next();
			}
			else
			{
				this.selectedUser.setId(-1L);
			}
			this.updateButtons(true);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private void viewDeleteUserRequest()
	{
		String winCaption = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_request_delete", selectedUser.getName(), selectedUser.getLastname());

		OptionalCommentAlert deleteUserRequestCtrl = new OptionalCommentAlert(this.getBbrUIParent(),
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "win_title_info"),
				winCaption,
				this.selectedUser.getId(),
				false);

		deleteUserRequestCtrl.addBbrEventListener((BbrEventListener & Serializable) e -> this.getOptionalCommentAlertResponseDelete(e));
		deleteUserRequestCtrl.initializeView();
		deleteUserRequestCtrl.open(true);
	}

	private void viewAddUserRequest(UserDTO user)
	{
		CreateEditUserRequest addUserRequestCtrl = new CreateEditUserRequest(this.getBbrUIParent(), user);
		addUserRequestCtrl.initializeView();
		addUserRequestCtrl.open(true);
	}

	private void initializeGridColumns()
	{
		this.dgd_CompanyUsers.addStyleName("v-grid-card");//
		this.dgd_CompanyUsers.addComponentColumn(p -> this.setCardViewColumn(p))
				.setStyleGenerator(item -> BbrAlignment.CENTER.getValue());
	}

	private VerticalLayout setCardViewColumn(UserDTO item)
	{
		VerticalLayout cardview = new VerticalLayout();
		String admin = "";
		if (item.getIsadmincompany())
		{
			admin = " " + I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "enterprise_admin");
		}
		
		String userFullText = UserManagerUtils.getValidFullnameOrEmailByUser(item);
		
		Label labelHeader = new Label(userFullText + admin);

		labelHeader.setHeight("20px");
		labelHeader.setStyleName("v-grid-card-title");
		cardview.addComponent(labelHeader);
		Label labelPosition = new Label(item.getPosition());
		labelPosition.setHeight("20px");
		cardview.addComponent(labelPosition);
		Label labelLastLogin = new Label(item.getLastlogin() != null ? BbrDateUtils.getInstance().toShortDateTime(item.getLastlogin()) : "");
		labelLastLogin.setHeight("20px");
		cardview.addComponent(labelLastLogin);
		cardview.setMargin(false);
		cardview.setSpacing(false);
		cardview.setHeight("60px");
		cardview.addLayoutClickListener((LayoutClickListener & Serializable) i -> this.selectableRow_handler(item));
		return cardview;
	}

	private void updateButtons(boolean isEnable)
	{
		try
		{
			if (this.btn_EditUserRequest != null && this.btn_DeleteUserRequest != null)
			{
				if (this.dgd_CompanyUsers.getSelectedItems().size() > 0)
				{

					this.btn_EditUserRequest.setEnabled(isEnable);
					this.btn_DeleteUserRequest.setEnabled(isEnable);

				}
				else
				{
					this.btn_EditUserRequest.setEnabled(!isEnable);
					this.btn_DeleteUserRequest.setEnabled(!isEnable);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void getOptionalCommentAlertResponseDelete(BbrEvent result)
	{
		OptionalCommentAlertDTO resultDTO = (OptionalCommentAlertDTO) result.getResultObject();
		this.doDeleteUserRequest(resultDTO);
	}

	private AddRequestAdminPVInitParamDTO getAddRequestAdminPVInitParam()
	{
		AddRequestAdminPVInitParamDTO addRequestAdminPVInitParam = new AddRequestAdminPVInitParamDTO();
		addRequestAdminPVInitParam.setExternalRetail(UserManagerUtils.getIsExternalRetail());
		addRequestAdminPVInitParam.setAdminid(this.getBbrUIParent().getUser().getId());
		UserRequestDTO userRequest = new UserRequestDTO();
		userRequest.setPersonalId(this.selectedUser.getPersonalid());
		userRequest.setEmail(this.selectedUser.getEmail());
		userRequest.setName(this.selectedUser.getName());
		userRequest.setLastName(this.selectedUser.getLastname());
		userRequest.setEmkey(this.selectedUser.getEmkey());
		userRequest.setPosition(this.selectedUser.getPosition());
		userRequest.setPhone(this.selectedUser.getPhone());

		addRequestAdminPVInitParam.setUserRequest(userRequest);

		SessionBean sessionBean = (SessionBean) this.getBbrUIParent().getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
		addRequestAdminPVInitParam.setAccessToken(sessionBean.getAccessToken());
		addRequestAdminPVInitParam.setUserRequest(userRequest);
		return addRequestAdminPVInitParam;
	}

	private void doDeleteUserRequest(OptionalCommentAlertDTO resultDTO)
	{
		String errorDescription = "";
		try
		{
			if (resultDTO != null)
			{
				AddRequestAdminPVInitParamDTO addRequestAdminPVInitParam = new AddRequestAdminPVInitParamDTO();
				addRequestAdminPVInitParam = this.getAddRequestAdminPVInitParam();
				addRequestAdminPVInitParam.setComment(resultDTO.getComment());
				AddRequestResultDTO result = EJBFactory.getUserEJBFactory().getRequestManagerLocal().addRequestDeleteAdminPV(addRequestAdminPVInitParam);
				AddRequestResultDTO reportResult = (AddRequestResultDTO) result;

				BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), !this.getBbrUIParent().hasAlertWindowOpen());

				if (!error.hasError())
				{
					errorDescription += I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_request_delete",
							selectedUser.getName(),
							selectedUser.getLastname());
					this.getBbrUIParent().showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"),
							errorDescription);
				}
				else
				{
					errorDescription = BbrUtils.getInstance().substitute("{0} - {1} - Internal Error", error.getErrorCode(), error.getErrorMessage());
				}
			}

		}
		catch (Exception e) // Error no controlado
		{
			errorDescription = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
	public class SortUsersByLastlogin implements Comparator<UserDTO>
	{
		@Override
		public int compare(final UserDTO entry1, final UserDTO entry2)
		{
			final LocalDateTime field1 = entry1.getLastlogin();
			final LocalDateTime field2 = entry2.getLastlogin();

			if (field1 != null && field2 != null)
			{
				return field2.compareTo(field1);
			}
			else if (field1 == null && field2 == null)
			{
				return 0;
			}
			else if (field1 == null)
			{
				return 1;
			}
			else
			{
				return -1;
			}

		}
	}
}
