package bbr.b2b.portal.components.utils.generic;
//package bbr.b2b.portal.components.utils;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import com.vaadin.data.Property.ValueChangeEvent;
//import com.vaadin.data.Property.ValueChangeListener;
//import com.vaadin.event.ShortcutAction.KeyCode;
//import com.vaadin.server.FontAwesome;
//import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Button.ClickListener;
//import com.vaadin.ui.FormLayout;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.VerticalLayout;
//
//import bbr.b2b.portal.classes.constants.BbrAppConstants;
//import bbr.b2b.portal.constants.BbrUtilsResources;
//import bbr.b2b.portal.classes.factory.EJBFactory;
//import bbr.b2b.portal.classes.i18n.I18NManager;
//import bbr.b2b.portal.classes.utils.app.AppUtils;
//import bbr.b2b.portal.classes.utils.app.SearchFilterUtils;
//import bbr.b2b.portal.components.basics.BbrSystemException;
//import bbr.b2b.portal.components.basics.BbrUserException;
//import bbr.b2b.users.report.classes.UserDTO;
//import bbr.b2b.users.report.classes.UsersResultDTO;
//import cl.bbr.core.classes.basics.SearchCriterion;
//import cl.bbr.core.classes.events.BbrEvent;
//import cl.bbr.core.classes.utilities.BbrUtils;
//import cl.bbr.core.components.basics.BbrComboBox;
//import cl.bbr.core.components.basics.BbrWindow;
//import cl.bbr.core.components.grid.BbrAdvancedGrid;
//import cl.bbr.core.components.grid.BbrColumn;
//import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;
//import cl.bbr.core.components.widgets.bbrtextfield.client.RestrictRange;
//
//public class FindExecutive extends BbrWindow 
//{
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PROPERTIES
//	// ****************************************************************************************
//	private static final long serialVersionUID = 3247626779164695209L;
//
//	private BbrComboBox<SearchCriterion> cbx_Criteria = null ;
//	private BbrTextField txt_Value ;
//	private FormLayout executiveForm ;
//
//	private BbrAdvancedGrid<UserDTO> dgd_Executives ;
//
//	private final String RUT 		= "rut" ;
//	private final String NAME		= "name" ;
//	private final String LASTNAME	= "lastname" ;
//	private final String EMAIL		= "email" ;
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PROPERTIES
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			CONSTRUCTORS
//	// ****************************************************************************************
//	public FindExecutive() 
//	{
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			CONSTRUCTORS
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			OVERRIDDEN METHODS
//	// ****************************************************************************************
//	public void initializeView() 
//	{
//		cbx_Criteria = this.getSearchCriterionComboBox();
//
//		txt_Value = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "value"));
//		txt_Value.setMaxLength(9);
//		txt_Value.setWidth(100,Unit.PERCENTAGE);
//		txt_Value.setRequired(true);
//		txt_Value.setRestrict(RestrictRange.ALL_LETTERS_SPACE);
//		txt_Value.addStyleName("bbr-filter-fields");
//
//		Button btn_Search = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "search"), FontAwesome.SEARCH);
//		btn_Search.setStyleName("btn-filter-search");
//		btn_Search.setClickShortcut(KeyCode.ENTER);
//		btn_Search.addClickListener((ClickListener & Serializable)e -> search_clickHandler(e));
//
//		//Form Component
//		executiveForm = new FormLayout();
//		executiveForm.addComponents(cbx_Criteria, txt_Value, btn_Search);
//
//		dgd_Executives = new BbrAdvancedGrid<>(UserDTO.class, this.getUser().getLocale());
//		dgd_Executives.addStyleName("report-grid");
//		dgd_Executives.setWidth("100%");
//		dgd_Executives.setBbrColumns(this.getReportColumns(), UserDTO.class);
//
//
//		//Buttons Layout
//		Button btn_Select = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "select"), FontAwesome.CHECK);
//		btn_Select.addClickListener((ClickListener & Serializable)e -> select_clickHandler(e));
//		
//		Button btn_Cancel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"), FontAwesome.REMOVE);
//		btn_Cancel.addClickListener(e->close());
//		
//		HorizontalLayout buttonsLayout = new HorizontalLayout(btn_Select, btn_Cancel);
//		buttonsLayout.setWidth(100,Unit.PERCENTAGE);
//		buttonsLayout.setComponentAlignment(btn_Select, Alignment.MIDDLE_LEFT);
//		buttonsLayout.setComponentAlignment(btn_Cancel, Alignment.MIDDLE_RIGHT);
//		buttonsLayout.addStyleName("buttons-layout");
//
//		VerticalLayout gridLayout = new VerticalLayout();
//		gridLayout.addComponents(dgd_Executives, buttonsLayout);
//		gridLayout.setExpandRatio(dgd_Executives, 1F);
//		gridLayout.setSpacing(true);
//		gridLayout.setSizeFull();
//
//		//Vertical Layout for Components
//		VerticalLayout mainLayout = new VerticalLayout(executiveForm,gridLayout);
//		mainLayout.setSizeFull();
//		mainLayout.setExpandRatio(gridLayout, 1F);
//		mainLayout.addStyleName("form-vertical-container");
//
//		//Main Windows
//		this.setWidth(450,Unit.PIXELS);
//		this.setHeight(480,Unit.PIXELS);
//		this.setResizable(false);
//		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "title_search_executives"));
//		this.setContent(mainLayout);
//		
//		txt_Value.focus();
//	}
//
//	private void select_clickHandler(ClickEvent e) 
//	{
//		UserDTO user = (UserDTO) dgd_Executives.getSelectedRow();
//		this.selectUser(user);
//	}
//	
//	private void selectUser(UserDTO user) 
//	{
//		if(user != null)
//		{
//			BbrEvent event = new BbrEvent(BbrEvent.ITEM_SELECTED);
//			event.setResultObject(user);
//			dispatchBbrEvent(event);
//			this.close();
//		}
//		else
//		{
//			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "company_required"));
//		}	
//	}
//
//	private void search_clickHandler(ClickEvent e) 
//	{
//		String errorMessage = validateData(); 
//		if(errorMessage == null || errorMessage.length() <= 0)
//		{
//			try 
//			{
//				String value = txt_Value.getValue().trim();
//				SearchCriterion criterion = cbx_Criteria.getSelectedValue();
//				UsersResultDTO usersResult = null;
//				if(criterion.getId()==0)
//				{
//					usersResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().getUsersByNameAndProfile(value,BbrAppConstants.SOLVENTA_TICKETS);
//				}
//				else if(criterion.getId() == 1)
//				{
//					usersResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().getUsersByLastNameAndProfile(value,BbrAppConstants.SOLVENTA_TICKETS);
//				}
//				else if(criterion.getId() == 2)
//				{
//					usersResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().getUsersByCodeAndProfile(value,BbrAppConstants.SOLVENTA_TICKETS);
//				}
//				
//				if(usersResult != null && usersResult.getUserDTOs() != null && usersResult.getUserDTOs().length > 0)
//				{
//					dgd_Executives.setRows(Arrays.asList(usersResult.getUserDTOs()));
//				}
//				else
//				{
//					dgd_Executives.setRows(Arrays.asList(new UserDTO[0]));
//					this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"), I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "no_result"));
//				}
//			}
//			catch (BbrUserException ex) 
//			{
//				AppUtils.getInstance().doLogOut(ex.getMessage());
//			} 
//			catch (BbrSystemException ex) 
//			{
//				ex.printStackTrace();
//			}	
//		}
//		else
//		{
//			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMessage);
//		}
//	}
//
//	private ArrayList<BbrColumn<?>> getReportColumns() 
//	{
//		ArrayList<BbrColumn<?>> result = new ArrayList<BbrColumn<?>>();
//
//		BbrColumn<String> col_RUT 		= new BbrColumn<>(this.RUT, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "exe_rut"));
//		BbrColumn<String> col_Name 		= new BbrColumn<>(this.NAME, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "exe_name"));
//		BbrColumn<String> col_LastName 	= new BbrColumn<>(this.LASTNAME, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "exe_last_name"));
//		BbrColumn<String> col_Email 	= new BbrColumn<>(this.EMAIL, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "exe_email"));
//
//		result.add(col_RUT);
//		result.add(col_Name);
//		result.add(col_LastName);
//		result.add(col_Email);
//
//		return result;
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			OVERRIDDEN METHODS
//	// ****************************************************************************************
//
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			EVENTS HANDLERS
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			EVENTS HANDLERS
//	// ****************************************************************************************
//
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PRIVATE METHODS
//	// ****************************************************************************************
//	private BbrComboBox<SearchCriterion> getSearchCriterionComboBox() 
//	{
//		BbrComboBox<SearchCriterion> result = null;
//		SearchCriterion[] searcCriterions = SearchFilterUtils.getInstance().getSearchExecutiveCriteria();
//
//		result = new BbrComboBox<SearchCriterion>(searcCriterions);
//
//		result.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "criteria"));
//		result.setItemCaptionMode(ItemCaptionMode.PROPERTY);
//		result.setItemCaptionPropertyId("description");
//		result.setTextInputAllowed(false);
//		result.setNullSelectionAllowed(false);
//		result.selectFirstItem();
//		result.addStyleName("bbr-filter-fields");
//		result.addValueChangeListener(new ValueChangeListener() 
//		{
//			private static final long serialVersionUID = -885062614408629961L;
//
//			@Override
//			public void valueChange(ValueChangeEvent event) 
//			{
//				if(cbx_Criteria.getSelectedValue() != null)
//				{
//					txt_Value.setValue("");
//					txt_Value.focus();
//					if(cbx_Criteria.getSelectedValue().getId().equals(2)) //By Rut
//					{
//						txt_Value.setMaxLength(9);
//						txt_Value.setRestrict(RestrictRange.NUMBERS);
//					}
//					else
//					{
//						txt_Value.setMaxLength(50);
//						txt_Value.setRestrict(RestrictRange.ALL_LETTERS_SPACE);
//					}
//				}
//			}
//		});
//		result.setWidth(100F, Unit.PERCENTAGE);
//
//		return result;
//	}
//
//	private String validateData()
//	{
//		String result = "" ;
//		String value = txt_Value.getValue().trim();
//		if(value.length()<3)
//		{
//			result = I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "field_required");
//		}
//		else
//		{
//			SearchCriterion criterion = cbx_Criteria.getSelectedValue();
//			if(criterion.getId()==0 ||criterion.getId()==1)
//			{
//				result = (!validateExtendedTextField(value, 50))?I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "usr_invalid_search"):"";
//			}
//			else if(criterion.getId() == 2)
//			{
//				result = (!validateRutTextField(value, 9))?I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "usr_invalid_search"):"";
//			}
//		}
//
//
//		return result ;
//	}
//	
//	private Boolean validateExtendedTextField(String fieldValue, int maxLength)
//	{
//		Boolean result = false;
//		if(fieldValue != null && fieldValue.length()<=maxLength)
//		{
//			result = BbrUtils.getInstance().isAlphaNumericWithSpecialCharsRegEx(fieldValue);
//		}
//		return result;
//	}
//
//	private Boolean validateRutTextField(String fieldValue, int maxLength)
//	{
//		Boolean result = false;
//		if(fieldValue != null && fieldValue.length()<=maxLength)
//		{
//			result = BbrUtils.getInstance().isAlphaNumericNoSpaceRegEx(fieldValue);
//		}
//		return result;
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PRIVATE METHODS
//	// ****************************************************************************************
//
//
//
//}
