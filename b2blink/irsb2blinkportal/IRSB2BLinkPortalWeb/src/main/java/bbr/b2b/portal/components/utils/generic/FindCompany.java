package bbr.b2b.portal.components.utils.generic;

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
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.SearchFilterUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyArrayResultDTO;
import bbr.b2b.users.report.classes.CompanyDTO;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class FindCompany extends BbrWindow 
{
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PROPERTIES
	// ****************************************************************************************
	private static final long serialVersionUID = 3247626779164695209L;

	private BbrComboBox<SearchCriterion> cbx_Criteria = null ;
	private BbrTextField txt_Value ;
	private FormLayout companyForm ;

	private BbrAdvancedGrid<CompanyDTO> dgd_Company ;
	private String enterprise = "";
	// ****************************************************************************************
	// ENDING SECTION 		---->			PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			CONSTRUCTORS
	// ****************************************************************************************
	public FindCompany(BbrUI parent,String enterprise) 
	{
		super(parent);
		if(enterprise != null)
		{
			this.enterprise = enterprise;
		}
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			OVERRIDDEN METHODS
	// ****************************************************************************************
	public void initializeView() 
	{
		cbx_Criteria = this.getSearchCriterionComboBox();

		txt_Value = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "value"));
		txt_Value.setWidth(100,Unit.PERCENTAGE);
		txt_Value.addStyleName("bbr-filter-fields");
		txt_Value.setValue(enterprise);
		
		Button btn_Search = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "search"), VaadinIcons.SEARCH);
		btn_Search.setStyleName("btn-filter-search");
		btn_Search.setClickShortcut(KeyCode.ENTER);
		btn_Search.addClickListener((ClickListener & Serializable)e -> search_clickHandler(e));

		//Form Component
		companyForm = new FormLayout();
		companyForm.addComponents(cbx_Criteria, txt_Value, btn_Search);

		dgd_Company = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.initializeGridColumns();
		dgd_Company.addStyleName("report-grid");
		dgd_Company.setWidth("100%");
		

		//Buttons Layout
		Button btn_Select = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "select"), VaadinIcons.CHECK);
		btn_Select.addClickListener((ClickListener & Serializable)e -> select_clickHandler(e));
		
		Button btn_Cancel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"), VaadinIcons.CLOSE_CIRCLE);
		btn_Cancel.addClickListener(e->close());
		
		HorizontalLayout buttonsLayout = new HorizontalLayout(btn_Select, btn_Cancel);
		buttonsLayout.setWidth(100,Unit.PERCENTAGE);
		buttonsLayout.setComponentAlignment(btn_Select, Alignment.MIDDLE_LEFT);
		buttonsLayout.setComponentAlignment(btn_Cancel, Alignment.MIDDLE_RIGHT);
		buttonsLayout.addStyleName("buttons-layout");

		VerticalLayout gridLayout = new VerticalLayout();
		gridLayout.addComponents(dgd_Company, buttonsLayout);
		gridLayout.setExpandRatio(dgd_Company, 1F);
		gridLayout.setSpacing(true);
		gridLayout.setSizeFull();
		gridLayout.setMargin(false);

		//Vertical Layout for Components
		VerticalLayout mainLayout = new VerticalLayout(companyForm,gridLayout);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(gridLayout, 1F);
		mainLayout.addStyleName("form-vertical-container");
		mainLayout.setMargin(false);
		//Main Windows
		this.setWidth(450,Unit.PIXELS);
		this.setHeight(480,Unit.PIXELS);
		this.setResizable(false);
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "title_search_enterprise"));
		this.setContent(mainLayout);
		
		txt_Value.focus();
	}

	private void select_clickHandler(ClickEvent e) 
	{
		if(dgd_Company.getSelectedItems() != null && dgd_Company.getSelectedItems().size() > 0)
		{
			CompanyDTO company = (CompanyDTO) dgd_Company.getSelectedItems().iterator().next();
			this.selectCompany(company);	
		}
	}
	
	private void selectCompany(CompanyDTO company) 
	{
		if(company != null)
		{
			BbrEvent event = new BbrEvent(BbrEvent.ITEM_SELECTED);
			event.setResultObject(company);
			dispatchBbrEvent(event);
			this.close();
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "company_required"));
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
				CompanyArrayResultDTO companyResult = null;
				if(criterion.getId()==0)
				{
					companyResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().findCompanyLikeName(value);
				}
				else if(criterion.getId() == 1)
				{
					companyResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().findCompanyLikeCode(value);
				}
				
				if(companyResult != null && companyResult.getCompanyDTOs() != null && companyResult.getCompanyDTOs().length > 0)
				{
					dgd_Company.setItems(companyResult.getCompanyDTOs());
				}
				else
				{
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

	private void initializeGridColumns()
	{
		dgd_Company.addColumn(CompanyDTO::getRut).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "cmp_enterprise_code"));
		dgd_Company.addColumn(CompanyDTO::getName).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "cmp_social_reason"));
	}

	// ****************************************************************************************
	// ENDING SECTION 		---->			OVERRIDDEN METHODS
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION 	---->			EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// ENDING SECTION 		---->			EVENTS HANDLERS
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PRIVATE METHODS
	// ****************************************************************************************
	private BbrComboBox<SearchCriterion> getSearchCriterionComboBox() 
	{
		BbrComboBox<SearchCriterion> result = null;
		SearchCriterion[] searcCriterions = SearchFilterUtils.getInstance().getSearchCompanyCriteria();

		result = new BbrComboBox<SearchCriterion>(searcCriterions);

		result.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "criteria"));
		result.setItemCaptionGenerator(SearchCriterion::getDescription);
		result.setTextInputAllowed(false);
		result.setEmptySelectionAllowed(false);
		result.selectFirstItem();

		result.addStyleName("bbr-filter-fields");
		
		result.setTextInputAllowed(false);
		
		
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

	private String validateData()
	{
		String result = "" ;
		String value = txt_Value.getValue().trim();
		if(value.length()<3)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "field_required");
		}
		else
		{
			SearchCriterion criterion = cbx_Criteria.getSelectedValue();
			if(criterion.getId()==0)
			{
				result = (!validateExtendedTextField(value, 50))?I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "usr_invalid_search"):"";
			}
			else if(criterion.getId() == 1)
			{
				result = (!validateRutTextField(value, 9))?I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "usr_invalid_search"):"";
			}
		}


		return result ;
	}
	
	private Boolean validateExtendedTextField(String fieldValue, int maxLength)
	{
		Boolean result = false;
		if(fieldValue != null && fieldValue.length()<=maxLength)
		{
			result = BbrUtils.getInstance().isAlphaNumericWithSpecialCharsRegEx(fieldValue);
		}
		return result;
	}

	private Boolean validateRutTextField(String fieldValue, int maxLength)
	{
		Boolean result = false;
		if(fieldValue != null && fieldValue.length()<=maxLength)
		{
			result = BbrUtils.getInstance().isRUTRegEx(fieldValue);
		}
		return result;
	}
	// ****************************************************************************************
	// ENDING SECTION 		---->			PRIVATE METHODS
	// ****************************************************************************************



}
