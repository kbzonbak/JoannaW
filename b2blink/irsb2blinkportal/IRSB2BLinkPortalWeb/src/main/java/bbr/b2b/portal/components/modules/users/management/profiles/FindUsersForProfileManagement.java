package bbr.b2b.portal.components.modules.users.management.profiles;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.SearchFilterUtils;
import bbr.b2b.portal.components.basics.BbrButtonGroup;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.UserAdminRetailFilterInitParam;
import bbr.b2b.users.report.classes.UserDTO;
import bbr.b2b.users.report.classes.UsersResultDTO;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.grid.renderer.DateRenderer;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class FindUsersForProfileManagement extends BbrWindow
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long				serialVersionUID	= 3247626779164695209L;

	private BbrComboBox<SearchCriterion>	cbx_Criteria		= null;
	private BbrTextField					txt_Value			= null;
	private FormLayout						searchForm			= null;
	private BbrAdvancedGrid<UserDTO>		dgd_Users			= null;
	private Button							btn_Select			= null;
	private List<UserDTO>					listOfAdmins		= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public FindUsersForProfileManagement(BbrUI parent, List<UserDTO> listOfAdmins)
	{
		super(parent);
		this.listOfAdmins = listOfAdmins;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> SUPERCLASS IMPLEMENTATIONS
	// =====================================================================================================================================

	public void initializeView()
	{
		cbx_Criteria = this.getSearchCriterionComboBox();

		txt_Value = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "value"));
		txt_Value.setWidth(100, Unit.PERCENTAGE);
		txt_Value.addStyleName("bbr-fields");

		Button btn_Search = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "search"), VaadinIcons.SEARCH);
		btn_Search.setStyleName("btn-filter-search");
		btn_Search.setClickShortcut(KeyCode.ENTER);
		btn_Search.addClickListener((ClickListener & Serializable) e -> search_clickHandler(e));

		// Form Component
		searchForm = new FormLayout();
		searchForm.addComponents(cbx_Criteria, txt_Value, btn_Search);

		dgd_Users = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.initializeGridColumns();
		dgd_Users.addStyleName("report-grid");
		dgd_Users.setSortable(false);
		dgd_Users.setSelectionMode(SelectionMode.MULTI);
		dgd_Users.setWidth("100%");
		dgd_Users.addSelectionListener(e -> toggleSelectBtn());

		// Buttons Layout
		BbrButtonGroup bbrGroupButton = new BbrButtonGroup.BbrGroupButtonBuilder(this.getBbrUIParent())
				.withPrimaryButtonLabel(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "select"))
				.withPrimaryButtonListener((ClickListener & Serializable) (e) -> this.select_clickHandler(e))
				.withCancelButtonLabel(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"))
				.withCancelButtonListener((ClickListener & Serializable) (e) -> this.close()).build();
		bbrGroupButton.setStyleName("bbr-buttons-panel");
		this.btn_Select = bbrGroupButton.getPrimaryButton();
		this.btn_Select.setClickShortcut(KeyCode.ENTER);
		this.btn_Select.setEnabled(false);

		VerticalLayout gridLayout = new VerticalLayout();
		gridLayout.addComponents(dgd_Users, bbrGroupButton);
		gridLayout.setExpandRatio(dgd_Users, 1F);
		gridLayout.setSpacing(true);
		gridLayout.setSizeFull();
		gridLayout.setMargin(false);

		// Vertical Layout for Components
		VerticalLayout mainLayout = new VerticalLayout(searchForm, gridLayout);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(gridLayout, 1F);
		mainLayout.addStyleName("form-vertical-container");
		mainLayout.setMargin(false);

		// Main Windows
		this.setWidth(650, Unit.PIXELS);
		this.setHeight(480, Unit.PIXELS);
		this.setResizable(false);
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "title_search_users"));
		this.setContent(mainLayout);

		txt_Value.focus();
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> SUPERCLASS IMPLEMENTATIONS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	private void toggleSelectBtn()
	{
		boolean isSelectEnabled = (this.dgd_Users.getSelectedItems() != null && this.dgd_Users.getSelectedItems().size() > 0);

		this.btn_Select.setEnabled(isSelectEnabled);
	}

	private void select_clickHandler(ClickEvent e)
	{
		if (dgd_Users.getSelectedItems() != null && dgd_Users.getSelectedItems().size() > 0)
		{
			UserDTO[] selectedUsers = (UserDTO[]) dgd_Users.getSelectedItems().toArray(new UserDTO[0]);
			this.selectUsers(selectedUsers);
		}

		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
					I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "user_required"));
		}
	}

	private void search_clickHandler(ClickEvent e)
	{
		String errorMessage = validateData();
		if (errorMessage == null || errorMessage.length() <= 0)
		{
			try
			{
				String value = txt_Value.getValue().trim();
				SearchCriterion criterion = cbx_Criteria.getSelectedValue();
				UsersResultDTO usersResult = null;
				UserAdminRetailFilterInitParam initParam = new UserAdminRetailFilterInitParam();

				initParam.setFilterype(criterion.getId());
				initParam.setValue(value);

				usersResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().findUserForAdminRetailFilter(initParam);

				if (usersResult != null && usersResult.getUserDTOs() != null && usersResult.getUserDTOs().length > 0)
				{
					List<UserDTO> validUsers = this.filterExistingUsers(this.listOfAdmins, usersResult.getUserDTOs());
					dgd_Users.setItems(validUsers);
				}

				else
				{
					dgd_Users.setItems(new UserDTO[0]);

					this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"),
							I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "no_result"));
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
		}

		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMessage);
		}
	}

	private List<UserDTO> filterExistingUsers(List<UserDTO> listOfAdmins, UserDTO[] userDTOs)
	{
		List<UserDTO> resultList = new ArrayList<>();
		if (listOfAdmins.size() > 0)
		{
			for (UserDTO userAdmin : userDTOs)
			{
				boolean userAdminExists = false;
				for (UserDTO userDTO : listOfAdmins)
				{
					if(userAdmin.getId().equals(userDTO.getId())){
						userAdminExists = true;
						break;
					}
				}
				if(!userAdminExists){
					resultList.add(userAdmin);
				}
			}
		}
		else
		{
			resultList.addAll(Arrays.asList(userDTOs));
		}
		return resultList;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private void selectUsers(UserDTO[] users)
	{
		if (users != null)
		{
			BbrEvent event = new BbrEvent(BbrEvent.ITEM_SELECTED);
			event.setResultObject(users);
			dispatchBbrEvent(event);
			this.close();
		}

		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
					I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "company_required"));
		}
	}

	private void initializeGridColumns()
	{
		this.dgd_Users.addCustomColumn(UserDTO::getName, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_name"), true)
				.setWidthUndefined();

		this.dgd_Users.addCustomColumn(UserDTO::getLastname, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_lastname"), true)
				.setWidthUndefined();

		this.dgd_Users.addCustomColumn(UserDTO::getEmail, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "email"), true)
				.setWidth(150D);

		this.dgd_Users.addCustomColumn(UserDTO::getPersonalid, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_person_id"), true)
				.setWidth(120D);

		this.dgd_Users.addCustomColumn(u -> stateDescriptionFunction(u), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "state"), true)
				.setStyleGenerator(i -> BbrAlignment.CENTER.getValue())
				.setWidth(120D);

		this.dgd_Users.addCustomColumn(UserDTO::getPosition, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "position"), true)
				.setWidth(120D);

		this.dgd_Users.addCustomColumn(UserDTO::getLastlogin, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_login_date"), true)
				.setStyleGenerator(i -> BbrAlignment.CENTER.getValue())
				.setDescriptionGenerator(i -> (i.getLastlogin() != null) ? DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss").format(i.getLastlogin()) : "")
				.setRenderer(new DateRenderer())
				.setWidth(150D);
	}

	private BbrComboBox<SearchCriterion> getSearchCriterionComboBox()
	{
		BbrComboBox<SearchCriterion> result = null;
		SearchCriterion[] searcCriterions = SearchFilterUtils.getInstance().getUserProfileManagementFilterCriteria();

		result = new BbrComboBox<SearchCriterion>(searcCriterions);

		result.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "criteria"));
		result.setItemCaptionGenerator(SearchCriterion::getDescription);
		result.setTextInputAllowed(false);
		result.setEmptySelectionAllowed(false);
		result.selectFirstItem();
		result.addStyleName("bbr-fields");

		result.addValueChangeListener(new ValueChangeListener<SearchCriterion>()
		{
			private static final long serialVersionUID = -885062614408629961L;

			@Override
			public void valueChange(ValueChangeEvent<SearchCriterion> event)
			{
				if (cbx_Criteria.getSelectedValue() != null)
				{
					txt_Value.setValue("");
					txt_Value.focus();
				}
			}
		});
		result.setWidth("100%");

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

	private String validateData()
	{
		String result = "";
		String value = txt_Value.getValue().trim();
		if (value.length() < 3)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "field_required");
		}

		return result;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
