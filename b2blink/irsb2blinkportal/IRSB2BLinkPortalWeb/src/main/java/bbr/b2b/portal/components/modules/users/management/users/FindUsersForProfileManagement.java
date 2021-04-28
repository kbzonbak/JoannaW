package bbr.b2b.portal.components.modules.users.management.users;

import java.io.Serializable;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.SearchFilterUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.UserAdminRetailFilterInitParam;
import bbr.b2b.users.report.classes.UserDTO;
import bbr.b2b.users.report.classes.UsersResultDTO;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.grid.renderer.ShortDateRenderer;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class FindUsersForProfileManagement extends BbrWindow 
{
	// =====================================================================================================================================
	// BEGINNING SECTION 	---->			PROPERTIES
	// =====================================================================================================================================

	private static final long serialVersionUID = 3247626779164695209L;

	private BbrComboBox<SearchCriterion> cbx_Criteria = null ;
	private BbrTextField txt_Value ;
	private FormLayout searchForm ;

	private BbrAdvancedGrid<UserDTO> dgd_Users;

	private Button btn_Select;

	// =====================================================================================================================================
	// ENDING SECTION 		---->			PROPERTIES
	// =====================================================================================================================================

	
	
	// =====================================================================================================================================
	// BEGINNING SECTION 	---->			CONSTRUCTORS
	// =====================================================================================================================================
	
	public FindUsersForProfileManagement(BbrUI parent) 
	{
		super(parent);
	}
	
	// =====================================================================================================================================
	// ENDING SECTION 		---->			CONSTRUCTORS
	// =====================================================================================================================================

	
	
	// =====================================================================================================================================
	// BEGINNING SECTION 	---->			SUPERCLASS IMPLEMENTATIONS
	// =====================================================================================================================================
	
	public void initializeView() 
	{
		cbx_Criteria = this.getSearchCriterionComboBox();

		txt_Value = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "value"));
		txt_Value.setWidth(100,Unit.PERCENTAGE);
		txt_Value.addStyleName("bbr-filter-fields");

		Button btn_Search = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "search"), VaadinIcons.SEARCH);
		btn_Search.setStyleName("btn-filter-search");
		btn_Search.setClickShortcut(KeyCode.ENTER);
		btn_Search.addClickListener((ClickListener & Serializable)e -> search_clickHandler(e));

		//Form Component
		searchForm = new FormLayout();
		searchForm.addComponents(cbx_Criteria, txt_Value, btn_Search);

		dgd_Users = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.initializeGridColumns();
		dgd_Users.addStyleName("report-grid");
		dgd_Users.setSortable(false);
		dgd_Users.setSelectionMode(SelectionMode.MULTI);
		dgd_Users.setWidth("100%");
		dgd_Users.addSelectionListener(e -> toggleSelectBtn());

		//Buttons Layout
		this.btn_Select = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "select"));
		this.btn_Select.addClickListener((ClickListener & Serializable)e -> select_clickHandler(e));
		this.btn_Select.setStyleName("primary");
		this.btn_Select.addStyleName("btn-generic");
		this.btn_Select.setWidth("140px");
		this.btn_Select.setEnabled(false);
		
		Button btn_Cancel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"));
		btn_Cancel.addClickListener(e->close());
		btn_Cancel.setStyleName("primary");
		btn_Cancel.addStyleName("btn-generic");
		btn_Cancel.setWidth("140px");
		
		HorizontalLayout buttonsLayout = new HorizontalLayout(btn_Select, btn_Cancel);
		buttonsLayout.setWidth(100,Unit.PERCENTAGE);
		buttonsLayout.setComponentAlignment(btn_Select, Alignment.MIDDLE_LEFT);
		buttonsLayout.setComponentAlignment(btn_Cancel, Alignment.MIDDLE_RIGHT);
		buttonsLayout.addStyleName("buttons-layout");

		VerticalLayout gridLayout = new VerticalLayout();
		gridLayout.addComponents(dgd_Users, buttonsLayout);
		gridLayout.setExpandRatio(dgd_Users, 1F);
		gridLayout.setSpacing(true);
		gridLayout.setSizeFull();
		gridLayout.setMargin(false);

		//Vertical Layout for Components
		VerticalLayout mainLayout = new VerticalLayout(searchForm,gridLayout);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(gridLayout, 1F);
		mainLayout.addStyleName("form-vertical-container");
		mainLayout.setMargin(false);
		
		//Main Windows
		this.setWidth(650,Unit.PIXELS);
		this.setHeight(480,Unit.PIXELS);
		this.setResizable(false);
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "title_search_users"));
		this.setContent(mainLayout);
		
		txt_Value.focus();
	}

	// =====================================================================================================================================
	// ENDING SECTION 		---->			SUPERCLASS IMPLEMENTATIONS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION 	---->			EVENTS HANDLERS
	// =====================================================================================================================================
	
	private void toggleSelectBtn()
	{
		boolean isSelectEnabled = (this.dgd_Users.getSelectedItems() != null && this.dgd_Users.getSelectedItems().size() > 0); 

		this.btn_Select.setEnabled(isSelectEnabled);
	}

	
	private void select_clickHandler(ClickEvent e) 
	{
		if(dgd_Users.getSelectedItems() != null && dgd_Users.getSelectedItems().size() > 0)
		{
			UserDTO[] selectedUsers = (UserDTO[]) dgd_Users.getSelectedItems().toArray(new UserDTO[0]);
			this.selectUsers(selectedUsers);
		}
		
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "user_required"));
		}
	}
	
	
	private void search_clickHandler(ClickEvent e) 
	{
		String errorMessage = validateData(); 
		if(errorMessage == null || errorMessage.length() <= 0)
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
				
				if(usersResult != null && usersResult.getUserDTOs() != null && usersResult.getUserDTOs().length > 0)
				{
					dgd_Users.setItems(usersResult.getUserDTOs());
				}
				
				else
				{
					dgd_Users.setItems(new UserDTO[0]);

					this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"), I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "no_result"));
				}
			}
			
			catch (BbrUserException ex) 
			{
				AppUtils.getInstance().doLogOut(ex.getMessage(),this.getBbrUIParent());
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
	// =====================================================================================================================================
	// ENDING SECTION 		---->			EVENTS HANDLERS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION 	---->			PRIVATE METHODS
	// =====================================================================================================================================
	
	private void selectUsers(UserDTO[] users) 
	{
		if(users != null)
		{
			BbrEvent event = new BbrEvent(BbrEvent.ITEM_SELECTED);
			event.setResultObject(users);
			dispatchBbrEvent(event);
			this.close();
		}
		
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "company_required"));
		}	
	}


	private void initializeGridColumns()
	{
		this.dgd_Users.addCustomColumn(UserDTO::getName, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_names"), true)
		.setWidthUndefined();
		
		this.dgd_Users.addCustomColumn(UserDTO::getLastname, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_names"), true)
		.setWidthUndefined();
		
		this.dgd_Users.addCustomColumn(UserDTO::getEmail, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "email"), true)
		.setWidth(150D);
		
		this.dgd_Users.addCustomColumn(UserDTO::getLogid, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "log_id"), true)
		.setWidth(120D);
		
		this.dgd_Users.addCustomColumn(u -> stateDescriptionFunction(u), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "state"), true)
		.setWidth(120D);
		
		this.dgd_Users.addCustomColumn(UserDTO::getPosition, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "position"), true)
		.setWidth(120D);
		
		this.dgd_Users.addCustomColumn(UserDTO::getLastlogin, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_login_date"), true)
		.setRenderer(new ShortDateRenderer()).setWidth(150D);
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
		result.addStyleName("bbr-filter-fields");
		
		result.addValueChangeListener(new ValueChangeListener<SearchCriterion>() 
		{
			private static final long serialVersionUID = -885062614408629961L;

			@Override
			public void valueChange(ValueChangeEvent<SearchCriterion> event) 
			{
				if(cbx_Criteria.getSelectedValue() != null)
				{
					txt_Value.setValue("");
					txt_Value.focus();
				}
			}
		});
		result.setWidth(100F, Unit.PERCENTAGE);

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
		String result = "" ;
		String value = txt_Value.getValue().trim();
		if(value.length()<3)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "field_required");
		}

		return result ;
	}
	
	// =====================================================================================================================================
	// ENDING SECTION 		---->			PRIVATE METHODS
	// =====================================================================================================================================

}
