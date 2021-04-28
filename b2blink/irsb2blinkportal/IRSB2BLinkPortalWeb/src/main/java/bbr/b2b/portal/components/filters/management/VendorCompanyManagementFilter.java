//package bbr.b2b.portal.components.filters.management;
//
//import com.vaadin.data.Property.ValueChangeEvent;
//import com.vaadin.data.Property.ValueChangeListener;
//import com.vaadin.event.ShortcutAction.KeyCode;
//import com.vaadin.server.FontAwesome;
//import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.FormLayout;
//import com.vaadin.ui.VerticalLayout;
//
//import bbr.b2b.finances.report.classes.CompanyArrayResultDTO;
//import bbr.b2b.finances.report.classes.CompanyDTO;
//import bbr.b2b.finances.report.classes.VendorCompanyReportInitParamDTO;
//import bbr.b2b.portal.classes.beans.SessionBean;
//import bbr.b2b.portal.classes.constants.BbrAppConstants;
//import bbr.b2b.portal.constants.BbrUtilsResources;
//import bbr.b2b.portal.classes.constants.EnumUserType;
//import bbr.b2b.portal.classes.factory.EJBFactory;
//import bbr.b2b.portal.classes.i18n.I18NManager;
//import bbr.b2b.portal.classes.utils.app.AppUtils;
//import bbr.b2b.portal.classes.utils.app.SearchFilterUtils;
//import bbr.b2b.portal.components.basics.BbrSystemException;
//import bbr.b2b.portal.components.basics.BbrUserException;
//import cl.bbr.core.classes.basics.SearchCriterion;
//import cl.bbr.core.classes.events.BbrFilterEvent;
//import cl.bbr.core.components.basics.BbrComboBox;
//import cl.bbr.core.components.basics.BbrFieldContainer;
//import cl.bbr.core.components.widgets.bbrfilter.BbrFilterContainer;
//import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;
//import cl.bbr.core.components.widgets.bbrtextfield.client.RestrictRange;
//public class VendorCompanyManagementFilter extends BbrFilterContainer implements Button.ClickListener
//{
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PROPERTIES
//	// ****************************************************************************************
//	private static final long serialVersionUID = 5712042403953778345L;
//
//	private BbrComboBox<SearchCriterion> cbx_FilterType = null ;
//	private Button btn_FilterSearch = null;
//	private BbrFieldContainer<?> cmp_FilterFields = null;
//	private FormLayout form = null;
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PROPERTIES
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			CONSTRUCTORS
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			CONSTRUCTORS
//	// ****************************************************************************************
//	public VendorCompanyManagementFilter() 
//	{
//	}
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			OVERRIDDEN METHODS
//	// ****************************************************************************************
//	@Override
//	public void buttonClick(ClickEvent event) 
//	{
//		String errorMsg = this.validateData();
//		if(errorMsg != null && errorMsg.length() == 0)
//		{
//			BbrFilterEvent bbrFilter = this.getBbrFilterEventObject();
//
//			VendorCompanyReportInitParamDTO initParams = this.getInitParams();
//
//			bbrFilter.setResultObject(initParams);
//
//			dispatchBbrFilterEvent(bbrFilter);
//		}
//		else
//		{
//			//Faltan datos en el filtro seleccionado
//			VendorCompanyManagementFilter.this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
//		}
//
//
//
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 8		---->			OVERRIDDEN METHODS
//	// ****************************************************************************************
//
//
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PUBLIC METHODS
//	// ****************************************************************************************
//	public void initializeView() 
//	{
//		cbx_FilterType = this.getSearchCriterionComboBox();
//
//		VerticalLayout mainLayout = new VerticalLayout();
//		mainLayout.setSizeFull();
//
//		this.addStyleName("bbr-filter");
//		this.setWidth("350px");
//		this.setHeight("140px");
//
//		cmp_FilterFields = this.getFieldsComponents();
//		form = new FormLayout();
//		form.setSizeFull();
//		form.addStyleName("filter-form");
//
//		form.addComponent(cbx_FilterType);
//		form.addComponent(cmp_FilterFields);
//
//		btn_FilterSearch = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "generate_report"), this);
//		btn_FilterSearch.setIcon(FontAwesome.CHECK_CIRCLE_O);
//		btn_FilterSearch.setStyleName("btn-filter-search");
//		btn_FilterSearch.setClickShortcut(KeyCode.ENTER);
//
//		mainLayout.addComponent(form);
//		mainLayout.addComponent(btn_FilterSearch);
//
//		mainLayout.setExpandRatio(form, 1F);
//		mainLayout.setComponentAlignment(btn_FilterSearch, Alignment.BOTTOM_RIGHT);
//		this.addComponent(mainLayout);
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PUBLIC METHODS
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PRIVATE METHODS
//	// ****************************************************************************************
//	private VendorCompanyReportInitParamDTO getInitParams() 
//	{
//		VendorCompanyReportInitParamDTO result = new VendorCompanyReportInitParamDTO();
//		SearchCriterion selectedValue = cbx_FilterType.getSelectedValue();
//		if(selectedValue != null)
//		{
//			result.setFilterType(selectedValue.getId());
//			switch (selectedValue.getId()) 
//			{
//			case 0: //Social Reason
//				result.setVendorCompanySocialReason((String) cmp_FilterFields.getSourceValue());
//				break;
//			case 1: //RUT
//				result.setVendorCompanyRut((String) cmp_FilterFields.getSourceValue());
//				break;
//			case 2: //User enterprise
//				result.setPayerCompanyId(((CompanyDTO) cmp_FilterFields.getSourceValue()).getId());
//				break;
//			default:
//				break;
//			}
//		}
//		
//		return result;
//	}
//
//	private BbrFilterEvent getBbrFilterEventObject() 
//	{
//		BbrFilterEvent result = null ;
//		SearchCriterion selectedValue = cbx_FilterType.getSelectedValue();
//		if(selectedValue != null)
//		{
//			result = new BbrFilterEvent(BbrFilterEvent.FILTER_APPLIED);
//			result.setCaption(selectedValue.getDescription()) ;
//			String secondaryHeaderTitle = "";
//			switch (selectedValue.getId()) 
//			{
//			
//			case 0: //Social Reason
//				String searchValue_SocialReason	= (String) cmp_FilterFields.getSourceValue();
//				secondaryHeaderTitle 		= (searchValue_SocialReason != null) ? searchValue_SocialReason : "";
//				break;
//			case 1: //RUT
//				String searchValue_Code	= (String) cmp_FilterFields.getSourceValue();
//				secondaryHeaderTitle 		= (searchValue_Code != null) ? searchValue_Code : "";
//				break;
//			case 2: //Payer Company
//				CompanyDTO searchValue_Company = (CompanyDTO) cmp_FilterFields.getSourceValue(); 
//				secondaryHeaderTitle = (searchValue_Company != null) ? searchValue_Company.getName() : "";
//				break;
//			default:
//				break;
//			}
//
//			result.setSecondaryCaption("- " + secondaryHeaderTitle);
//			result.setResultObject(selectedValue);
//
//		}
//
//		return result ;
//	}
//
//	private BbrComboBox<SearchCriterion> getSearchCriterionComboBox() 
//	{
//		BbrComboBox<SearchCriterion> result = null;
//
//		SessionBean userBean = (SessionBean) this.getBbrUIParent().getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
//		if(userBean != null)
//		{
//			SearchCriterion[] searcCriterions = SearchFilterUtils.getInstance().getVendorCompanyFilterCriteria();
//
//			result = new BbrComboBox<SearchCriterion>(searcCriterions);
//
//			result.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "criteria"));
//			result.setItemCaptionMode(ItemCaptionMode.PROPERTY);
//			result.setItemCaptionPropertyId("description");
//			result.setTextInputAllowed(false);
//			result.setNullSelectionAllowed(false);
//			result.selectFirstItem();
//			result.addValueChangeListener(new ValueChangeListener() 
//			{
//				private static final long serialVersionUID = -885062614408629961L;
//
//				@Override
//				public void valueChange(ValueChangeEvent event) 
//				{
//					if(cbx_FilterType.getSelectedValue() != null)
//					{
//						form.removeComponent(cmp_FilterFields);
//
//						cmp_FilterFields = getFieldsComponents();
//						cmp_FilterFields.markAsDirty();
//
//						form.addComponent(cmp_FilterFields);
//					}
//				}
//			});
//			result.setWidth(100F, Unit.PERCENTAGE);
//		}
//
//
//		return result;
//	}
//
//	private CompanyDTO[] companies = null;
//	private BbrComboBox<CompanyDTO> getPayersCompaniesComboBox() 
//	{
//		BbrComboBox<CompanyDTO> result = null;
//
//		try 
//		{
//			EnumUserType userType = EnumUserType.getEnumUserType(this.getBbrUIParent().getUser().getTypeDescription());
//			
//			if(companies == null || companies.length == 0)
//			{
//				CompanyArrayResultDTO companiesResult = null;
//				if(userType.equals(EnumUserType.SOLVENTA))
//				{
//					companiesResult = EJBFactory.getFinancesEJBFactory().getFinancesReportManagerLocal().getCompaniesByType(EnumUserType.PAYER.getId());	
//				}
//				else if(userType.equals(EnumUserType.PAYER))
//				{
//					companiesResult = EJBFactory.getFinancesEJBFactory().getFinancesReportManagerLocal().getCompanyById(this.getBbrUIParent().getUser().getEnterpriseId());
//				}
//				
//				if(companiesResult != null)
//				{
//					if(!this.getBbrUIParent().getUser().getTypeID().equals(EnumUserType.PAYER))
//					{
//						CompanyDTO allCompany = new CompanyDTO();
//						allCompany.setId(-1L);
//						allCompany.setName(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FINANCES, "all"));
//						
//						companies = new CompanyDTO[companiesResult.getCompanyDTOs().length+1];
//						System.arraycopy(companiesResult.getCompanyDTOs(), 0, companies, 1, companiesResult.getCompanyDTOs().length);
//						companies[0] = allCompany;	
//					}
//					else
//					{
//						companies = companiesResult.getCompanyDTOs();
//					}
//					
//				}
//				 
//			}
//
//			result = new BbrComboBox<CompanyDTO>(companies);
//
//			result.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "value"));
//			result.setItemCaptionMode(ItemCaptionMode.PROPERTY);
//			result.setItemCaptionPropertyId("name");
//			result.setTextInputAllowed(false);
//			result.setNullSelectionAllowed(false);
//			result.selectFirstItem();
//
//			result.setWidth(100F, Unit.PERCENTAGE);
//		} 
//		catch (BbrUserException e) 
//		{
//			AppUtils.getInstance().doLogOut(e.getMessage());
//		} 
//		catch (BbrSystemException e) 
//		{
//			e.printStackTrace();
//		}
//
//		return result;
//	}
//
//	private BbrFieldContainer<?> getFieldsComponents()
//	{
//		BbrFieldContainer<?> result = null;
//		SearchCriterion criterion = cbx_FilterType.getSelectedValue();
//		if(criterion != null)
//		{
//			switch (criterion.getId()) 
//			{
//				case 0: //Social Reason
//					BbrTextField txt_SocialReason = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "value"));
//					txt_SocialReason.addStyleName("bbr-filter-fields");
//					txt_SocialReason.setMaxLength(20);
//					result = new BbrFieldContainer<String>(txt_SocialReason, "value");
//					break;
//				case 1: //RUT
//					BbrTextField txt_VendorCode = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "value"));
//					txt_VendorCode.setRestrict(RestrictRange.NUMBERS);
//					txt_VendorCode.addStyleName("bbr-filter-fields");
//					txt_VendorCode.setMaxLength(10);
//					result = new BbrFieldContainer<String>(txt_VendorCode, "value");
//					break;
//				case 2: //Payers Companies 
//					BbrComboBox<CompanyDTO> cbx_PayersCompanies = this.getPayersCompaniesComboBox();
//					result = new BbrFieldContainer<String>(cbx_PayersCompanies, "value");
//					break;
//				default:
//					break;
//			}
//		}
//
//		return result;
//	}
//
//	private String validateData()
//	{
//		String result = "" ;
//
//		SearchCriterion criterion = cbx_FilterType.getSelectedValue();
//		if(criterion != null)
//		{
//			switch (criterion.getId()) 
//			{
//			case 0: //Social Reason
//				String socialReason = (String) this.cmp_FilterFields.getSourceValue();
//				result = (socialReason == null || socialReason.length() < 3)?I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "field_required"):"";
//				break;
//			case 1: //RUT
//				String code = (String) this.cmp_FilterFields.getSourceValue();
//				result = (code == null || code.length() < 3)?I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "field_required"):"";
//				break;
//			case 2: //Payer Company
//				CompanyDTO company = (CompanyDTO) this.cmp_FilterFields.getSourceValue();
//				result = (company == null)?I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "msg_missing_fields"):"";
//				break;
//			default:
//				break;
//			}
//		}
//
//		return result ;
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PRIVATE METHODS
//	// ****************************************************************************************
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
//}
