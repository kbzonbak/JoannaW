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

import bbr.b2b.portal.classes.constants.CompanySearchCriteria;
import bbr.b2b.portal.classes.constants.EnumUserType;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.SearchFilterUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyDataArrayResultDTO;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class FindProvider extends BbrWindow 
{
	// ****************************************************************************************
	// BEGINNING SECTION 	---->			PROPERTIES
	// ****************************************************************************************
	private static final long serialVersionUID = 3247626779164695209L;

	private BbrComboBox<SearchCriterion> cbx_Criteria = null ;
	private BbrTextField txt_Value ;
	private FormLayout companyForm ;

	private BbrAdvancedGrid<CompanyDataDTO> dgd_Company ;

	private Button btn_Select;

	private boolean isAllEnterprises = false;
	private String companyType = EnumUserType.PROVIDER.getValue();

	// ****************************************************************************************
	// ENDING SECTION 		---->			PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION 	---->			CONSTRUCTORS
	// ****************************************************************************************
	public FindProvider(BbrUI parent, boolean isAllEnterprises) 
	{
		super(parent);
		this.isAllEnterprises = isAllEnterprises;
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
		txt_Value.setMaxLength(50);
		txt_Value.setWidth("100%");
		txt_Value.addStyleName("bbr-filter-fields");

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
		dgd_Company.addSelectionListener(e -> toggleSelectBtn());

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
		this.setWidth("450px");
		this.setHeight("480px");
		this.setResizable(false);
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "title_search_providers"));
		this.setContent(mainLayout);
		
		txt_Value.focus();
	}
	
	private void selectCompany(CompanyDataDTO company) 
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

	

	private void initializeGridColumns()
	{
		dgd_Company.addColumn(CompanyDataDTO::getRut).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "cmp_enterprise_code"));
		dgd_Company.addColumn(CompanyDataDTO::getName).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "cmp_social_reason"));
	}

	// ****************************************************************************************
	// ENDING SECTION 		---->			OVERRIDDEN METHODS
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION 	---->			EVENTS HANDLERS
	// ****************************************************************************************
	private void toggleSelectBtn()
	{
		boolean isSelectEnabled = (this.dgd_Company.getSelectedItems() != null && this.dgd_Company.getSelectedItems().size() > 0); 

		this.btn_Select.setEnabled(isSelectEnabled);
	}

	private void select_clickHandler(ClickEvent e) 
	{
		if(dgd_Company.getSelectedItems() != null && dgd_Company.getSelectedItems().size() > 0)
		{
			CompanyDataDTO company = (CompanyDataDTO) dgd_Company.getSelectedItems().iterator().next();
			this.selectCompany(company);
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
				CompanyDataArrayResultDTO companyResult = null;
				if(criterion.getId() == CompanySearchCriteria.DESCRIPTION.getValue())
				{
					companyResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().findCompanyOfUserByNameAndType(this.getBbrUIParent().getUser().getId(),value,this.isAllEnterprises,this.companyType );
				}
				else if(criterion.getId() == CompanySearchCriteria.CODE.getValue())
				{
					companyResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().findCompanyOfUserByCodeAndType(this.getBbrUIParent().getUser().getId(),value,this.isAllEnterprises,this.companyType);
				}
				
				if(companyResult != null && companyResult.getCompanyDataDTOs() != null && companyResult.getCompanyDataDTOs().length > 0)
				{
					dgd_Company.setItems(companyResult.getCompanyDataDTOs());
				}
				else
				{
					dgd_Company.setItems(new CompanyDataDTO[0]);

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
		result.setWidth("100%");

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
		else if (value.length() > 50)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "usr_invalid_search");
		}

		return result ;
	}
	
	
	// ****************************************************************************************
	// ENDING SECTION 		---->			PRIVATE METHODS
	// ****************************************************************************************



}
