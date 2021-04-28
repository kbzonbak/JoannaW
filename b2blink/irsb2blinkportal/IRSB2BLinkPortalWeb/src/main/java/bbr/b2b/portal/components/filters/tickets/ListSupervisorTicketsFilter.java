//package bbr.b2b.portal.components.filters.tickets;
//
//import com.vaadin.data.Property.ValueChangeEvent;
//import com.vaadin.data.Property.ValueChangeListener;
//import com.vaadin.event.ShortcutAction.KeyCode;
//import com.vaadin.server.FontAwesome;
//import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Label;
//import com.vaadin.ui.VerticalLayout;
//
//import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
//import bbr.b2b.common.factories.BeanExtenderFactory;
//import bbr.b2b.finances.report.classes.CompanyArrayResultDTO;
//import bbr.b2b.finances.report.classes.CompanyDTO;
//import bbr.b2b.portal.classes.basics.CustomUserDTO;
//import bbr.b2b.portal.classes.beans.SessionBean;
//import bbr.b2b.portal.classes.constants.BbrAppConstants;
//import bbr.b2b.portal.constants.BbrUtilsResources;
//import bbr.b2b.portal.classes.constants.EnumUserType;
//import bbr.b2b.portal.classes.factory.EJBFactory;
//import bbr.b2b.portal.classes.i18n.I18NManager;
//import bbr.b2b.portal.classes.utils.app.AppUtils;
//import bbr.b2b.portal.classes.utils.app.SearchFilterUtils;
//import bbr.b2b.portal.classes.wrappers.tickets.ListSupervisorTicketsInfo;
//import bbr.b2b.portal.components.basics.BbrSystemException;
//import bbr.b2b.portal.components.basics.BbrUserException;
//import bbr.b2b.users.report.classes.UserDTO;
//import bbr.b2b.users.report.classes.UsersResultDTO;
//import cl.bbr.core.classes.basics.BbrUser;
//import cl.bbr.core.classes.basics.SearchCriterion;
//import cl.bbr.core.classes.events.BbrFilterEvent;
//import cl.bbr.core.components.basics.BbrComboBox;
//import cl.bbr.core.components.widgets.bbrfilter.BbrFilterContainer;
//import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;
//import cl.bbr.core.components.widgets.bbrtextfield.client.RestrictRange;
//
//public class ListSupervisorTicketsFilter extends BbrFilterContainer implements Button.ClickListener
//{
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PROPERTIES
//	// ****************************************************************************************
//	private static final long serialVersionUID = 5712042403953778345L;
//
//	private BbrComboBox<CompanyDTO> 		cbx_Providers 	= null;
//	private BbrComboBox<SearchCriterion> 	cbx_FilterType 	= null ;
//
//	private Button 					btn_FilterSearch 	= null;
//	private VerticalLayout 			pnlFilterValue 		= null;
//	private BbrTextField 			txtNumber 			= null;
//	private BbrComboBox<CustomUserDTO> 	cbx_Users 			= null;
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
//	public ListSupervisorTicketsFilter() 
//	{
//	}
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			OVERRIDDEN METHODS
//	// ****************************************************************************************
//	@Override
//	public void buttonClick(ClickEvent event) 
//	{
//
//		ListSupervisorTicketsInfo filterInfo = new ListSupervisorTicketsInfo(cbx_FilterType.getSelectedValue().getId(), cbx_Providers.getSelectedValue(), 
//				txtNumber.getValue(), cbx_Users.getSelectedValue(),this.getBbrUIParent().getUser());
//		String errorMsg = filterInfo.validateData();
//		if(errorMsg == null)
//		{
//			BbrFilterEvent bbrFilter = this.getBbrFilterEventObject();
//
//			bbrFilter.setResultObject(filterInfo.toTicketReportInitParamDTO());
//
//			dispatchBbrFilterEvent(bbrFilter);
//		}
//		else
//		{
//			//Faltan datos en el filtro seleccionado
//			ListSupervisorTicketsFilter.this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
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
//
//	public void initializeView() 
//	{
//		//Enterprise Panel
//		Label lblEnterpriseHeader = new Label("Empresas");
//		lblEnterpriseHeader.setWidth("100%");
//		lblEnterpriseHeader.addStyleName("bbr-filter-label-header");
//		lblEnterpriseHeader.addStyleName("bbr-panel-space");
//
//		Label lblProvider = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FINANCES, "vendor"));
//		lblProvider.setWidth("145px");
//		lblProvider.addStyleName("bbr-filter-label");
//		cbx_Providers = this.getVendorCompaniesComboBox();
//		HorizontalLayout pnlProvider = new HorizontalLayout();
//		pnlProvider.setWidth("100%");
//		pnlProvider.addComponents(lblProvider,cbx_Providers);
//		pnlProvider.setExpandRatio(cbx_Providers, 1F);
//		pnlProvider.addStyleName("bbr-panel-space");
//
//		Label lblPaymentCompany = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FINANCES, "payer_company"));
//		lblPaymentCompany.setWidth("120px");
//
//		VerticalLayout pnlEnterprise = new VerticalLayout();
//		pnlEnterprise.setWidth("400px");
//		pnlEnterprise.addStyleName("bbr-filter-sections");
//		pnlEnterprise.addStyleName("bbr-panel-space");
//
//		pnlEnterprise.setSpacing(false);
//
//		pnlEnterprise.addComponents(lblEnterpriseHeader,pnlProvider);
//
//		VerticalLayout pnlAllCriteria = new VerticalLayout();
//		pnlAllCriteria.setWidth("400px");
//		pnlAllCriteria.addStyleName("bbr-filter-sections");
//		pnlAllCriteria.addStyleName("bbr-panel-space");
//		pnlAllCriteria.setSpacing(false);
//
//		Label lblCriteria = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "criteria"));
//		lblCriteria.addStyleName("bbr-filter-label");
//		lblCriteria.setWidth("145px");
//		cbx_FilterType = this.getSearchCriterionComboBox();
//		HorizontalLayout pnlCriteria = new HorizontalLayout();
//		pnlCriteria.setWidth("400px");
//		pnlCriteria.setHeight("35px");
//		pnlCriteria.addComponents(lblCriteria,cbx_FilterType);
//		pnlCriteria.setExpandRatio(cbx_FilterType, 1F);
//		pnlCriteria.setComponentAlignment(lblCriteria, Alignment.MIDDLE_LEFT);
//
//		this.resetFilterValuesPanel(cbx_FilterType.getSelectedValue());
//
//		pnlAllCriteria.addComponents(pnlCriteria, pnlFilterValue);
//
//		VerticalLayout mainLayout = new VerticalLayout();
//		mainLayout.setSizeFull();
//		mainLayout.setSpacing(false);
//		this.addStyleName("bbr-filter");
//		this.setWidth("400px");
//		this.setHeight("205px");
//
//		btn_FilterSearch = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "generate_report"), this);
//		btn_FilterSearch.setIcon(FontAwesome.CHECK_CIRCLE_O);
//		btn_FilterSearch.setStyleName("btn-filter-search");
//		btn_FilterSearch.setClickShortcut(KeyCode.ENTER);
//
//		mainLayout.addComponents(pnlEnterprise, pnlAllCriteria);
//		mainLayout.addComponent(btn_FilterSearch);
//		mainLayout.setExpandRatio(pnlAllCriteria, 1F);
//		mainLayout.setComponentAlignment(btn_FilterSearch, Alignment.BOTTOM_RIGHT);
//		this.addComponent(mainLayout);
//	}
//
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PUBLIC METHODS
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PRIVATE METHODS
//	// ****************************************************************************************
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
//			case 2: //Número de Operación
//				secondaryHeaderTitle = txtNumber.getValue();
//				result.setSecondaryCaption("- " + secondaryHeaderTitle);
//				break;
//			case 3: //Por Ejecutivo
//				secondaryHeaderTitle = cbx_Users.getSelectedValue().getCaption();
//				result.setSecondaryCaption("- " + secondaryHeaderTitle);
//				break;
//			}
//
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
//			SearchCriterion[] searcCriterions = SearchFilterUtils.getInstance().getListSupervisorTicketsFilterCriteria();
//
//			result = new BbrComboBox<SearchCriterion>(searcCriterions);
//
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
//						resetFilterValuesPanel(cbx_FilterType.getSelectedValue());
//					}
//				}
//			});
//			result.setWidth(240F, Unit.PIXELS);
//		}
//
//
//		return result;
//	}
//
//	private void resetFilterValuesPanel(SearchCriterion searchCriterion)
//	{
//		if(pnlFilterValue == null)
//		{
//			pnlFilterValue = new VerticalLayout();
//		}
//		else
//		{
//			pnlFilterValue.removeAllComponents();
//		}
//
//		txtNumber = new BbrTextField();
//		cbx_Users = new BbrComboBox<>();
//
//		if(searchCriterion.getId().equals(2))
//		{
//			Label lblValue = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "value"));
//			lblValue.addStyleName("bbr-filter-label");
//			lblValue.setWidth("145px");
//
//			txtNumber.setMaxLength(30);
//			txtNumber.setRestrict(RestrictRange.NUMBERS);
//			txtNumber.addStyleName("bbr-filter-fields");
//			txtNumber.setWidth("240px");
//
//			HorizontalLayout pnlNumber = new HorizontalLayout();
//			pnlNumber.setWidth("400px");
//			pnlNumber.setHeight("40px");
//			pnlNumber.addComponents(lblValue, txtNumber);
//			pnlNumber.setExpandRatio(txtNumber, 1F);
//
//			VerticalLayout pnlPayment = new VerticalLayout();
//			pnlPayment.setWidth("400px");
//			pnlPayment.setSpacing(false);
//			pnlPayment.addComponents(pnlNumber);
//
//			pnlFilterValue.addComponents(pnlPayment);
//			pnlFilterValue.addStyleName("bbr-panel-space");
//
//		}
//		else if(searchCriterion.getId().equals(3))
//		{
//			Label lblValue = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "value"));
//			lblValue.addStyleName("bbr-filter-label");
//			lblValue.setWidth("145px");
//
//			cbx_Users = this.getUserComboBox();
//
//			HorizontalLayout pnlValue = new HorizontalLayout();
//			pnlValue.setWidth("400px");
//			pnlValue.setHeight("40px");
//			pnlValue.addComponents(lblValue, cbx_Users);
//			pnlValue.setExpandRatio(cbx_Users, 1F);
//
//			VerticalLayout pnlValues = new VerticalLayout();
//			pnlValues.setWidth("400px");
//			pnlValues.setSpacing(false);
//			pnlValues.addComponents(pnlValue);
//
//			pnlFilterValue.addComponents(pnlValues);
//			pnlFilterValue.addStyleName("bbr-panel-space");	
//		}
//	}
//
//	private CompanyDTO[] vendorCompanies = null;
//	private BbrComboBox<CompanyDTO> getVendorCompaniesComboBox() 
//	{
//		BbrComboBox<CompanyDTO> result = null;
//
//		try 
//		{
//			if(vendorCompanies == null || vendorCompanies.length == 0)
//			{
//				BbrUser user = this.getBbrUIParent().getUser();
//				if(user != null)
//				{
//					if(user.getTypeID().equals(EnumUserType.PROVIDER.getId()))
//					{
//						//Es un proveedor, se trae solo su empresa.
//						CompanyArrayResultDTO companiesResult = EJBFactory.getFinancesEJBFactory().getFinancesReportManagerLocal().getCompanyById(user.getEnterpriseId());
//						vendorCompanies = companiesResult.getCompanyDTOs();
//					}
//					else
//					{
//						CompanyArrayResultDTO companiesResult ;
//						if(user.getTypeID().equals(EnumUserType.SOLVENTA.getId()))
//						{
//							companiesResult = EJBFactory.getFinancesEJBFactory().getFinancesReportManagerLocal().getCompaniesByType(EnumUserType.PROVIDER.getId());
//						}
//						else
//						{
//							companiesResult = EJBFactory.getFinancesEJBFactory().getFinancesReportManagerLocal().getVendorCompaniesByPayer(user.getEnterpriseId());
//						}
//
//						if(companiesResult.getCompanyDTOs() != null)
//						{
//							CompanyDTO companyAll = new CompanyDTO();
//							companyAll.setId(-1L);
//							companyAll.setName("Todos");
//
//							vendorCompanies = new CompanyDTO[companiesResult.getCompanyDTOs().length+1];
//							System.arraycopy(companiesResult.getCompanyDTOs(), 0, vendorCompanies, 1, companiesResult.getCompanyDTOs().length);
//							vendorCompanies[0] = companyAll;
//						}	
//					}
//
//				}
//			}
//
//
//			result = new BbrComboBox<CompanyDTO>(vendorCompanies);
//			result.setItemCaptionMode(ItemCaptionMode.PROPERTY);
//			result.setItemCaptionPropertyId("name");
//			result.setTextInputAllowed(false);
//			result.setPageLength(0);
//			result.setNullSelectionAllowed(false);
//			result.selectFirstItem();
//
//			result.setWidth(240F, Unit.PIXELS);
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
//	private CustomUserDTO[] users = null;
//	private BbrComboBox<CustomUserDTO> getUserComboBox() 
//	{
//		BbrComboBox<CustomUserDTO> result = null;
//
//		try 
//		{
//			if(users == null || users.length == 0)
//			{
//				UsersResultDTO usersResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().getUsersByProfile("SOLV_TICKET");
//				UserDTO[] usersdtos = usersResult.getUserDTOs();
//				if(usersdtos != null && usersdtos.length>0)
//				{
//					users = new CustomUserDTO[usersdtos.length];
//					for (int i = 0; i < usersdtos.length; i++) 
//					{
//						CustomUserDTO user = new CustomUserDTO();
//						try 
//						{
//							BeanExtenderFactory.copyProperties(usersdtos[i],user);
//							user.setCaption(usersdtos[i].getName() + " " + usersdtos[i].getLastname());
//						} 
//						catch (OperationFailedException e) 
//						{
//							e.printStackTrace();
//						}
//
//						users[i] = user;
//					}
//
//					
//					CustomUserDTO analystAll = new CustomUserDTO();
//					analystAll.setId(-1L);
//					analystAll.setCaption("Todos");
//					
//					CustomUserDTO[] tmpusers = new CustomUserDTO[usersResult.getUserDTOs().length+1];
//
//					System.arraycopy(users, 0, tmpusers, 1, users.length);
//					tmpusers[0] = analystAll;
//					
//					users = tmpusers;
//				
//				}
//			}
//
//			result = new BbrComboBox<CustomUserDTO>((CustomUserDTO[]) users);
//			result.setItemCaptionMode(ItemCaptionMode.PROPERTY);
//			result.setItemCaptionPropertyId("caption");
//			result.setTextInputAllowed(false);
//			result.setPageLength(0);
//			result.setNullSelectionAllowed(false);
//			result.selectFirstItem();
//
//			result.setWidth(240F, Unit.PIXELS);
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
