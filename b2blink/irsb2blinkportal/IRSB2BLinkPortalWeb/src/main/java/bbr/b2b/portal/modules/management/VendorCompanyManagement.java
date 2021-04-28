//package bbr.b2b.portal.modules.management;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Button.ClickListener;
//import com.vaadin.ui.Grid.SelectionMode;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.UI;
//import com.vaadin.ui.VerticalLayout;
//
//import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
//import bbr.b2b.finances.report.classes.VendorCompanyReportDataDTO;
//import bbr.b2b.finances.report.classes.VendorCompanyReportInitParamDTO;
//import bbr.b2b.finances.report.classes.VendorCompanyReportResultDTO;
//import bbr.b2b.portal.classes.beans.SessionBean;
//import bbr.b2b.portal.classes.constants.BbrAppConstants;
//import bbr.b2b.portal.constants.BbrUtilsResources;
//import bbr.b2b.portal.classes.constants.EnumUserType;
//import bbr.b2b.portal.classes.factory.EJBFactory;
//import bbr.b2b.portal.classes.i18n.I18NManager;
//import bbr.b2b.portal.classes.jms.constants.MessageTopics;
//import bbr.b2b.portal.classes.utils.app.AppUtils;
//import bbr.b2b.portal.components.basics.BbrSystemException;
//import bbr.b2b.portal.components.basics.BbrUserException;
//import bbr.b2b.portal.components.filters.management.VendorCompanyManagementFilter;
//import bbr.b2b.portal.components.renderers.grid_details.VendorCompanyDetailsGenerator;
//import bbr.b2b.portal.components.renderers.grid_details.VendorCompanyRutRenderer;
//import cl.bbr.core.classes.basics.BbrPage;
//import cl.bbr.core.classes.basics.BbrUser;
//import cl.bbr.core.classes.constants.CoreConstants;
//import cl.bbr.core.classes.constants.TrackingConstants;
//import cl.bbr.core.classes.errors.BbrError;
//import cl.bbr.core.classes.errors.ErrorsMngr;
//import cl.bbr.core.classes.events.BbrFilterEvent;
//import cl.bbr.core.classes.events.BbrMessageEvent;
//import cl.bbr.core.classes.events.BbrMessageEventListener;
//import cl.bbr.core.classes.events.BbrPagingEvent;
//import cl.bbr.core.classes.events.BbrPagingEventListener;
//import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
//import cl.bbr.core.classes.utilities.BbrUtils;
//import cl.bbr.core.components.basics.BbrModule;
//import cl.bbr.core.components.basics.BbrUI;
//import cl.bbr.core.components.grid.BbrAdvancedGrid;
//import cl.bbr.core.components.grid.BbrColumn;
//import cl.bbr.core.components.paging.BbrPagingManager;
//
//public class VendorCompanyManagement extends BbrModule 
//{
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PROPERTIES
//	// ****************************************************************************************
//	private static final long serialVersionUID = 7027502352762624910L;
//
//	private BbrAdvancedGrid<VendorCompanyReportDataDTO> dgd_Companies ;
//	private VerticalLayout mainLayout ;
//
//	private final String RUT_FIELD 				= "vendorcompanyrut" ;
//	private final String NAME_FIELD 			= "vendorcompanyname" ;
//	private final String PAYMENT_TYPE_FIELD 	= "paymenttype" ;
//	private final String CURRENT_ACOUNT_FIELD	= "currentacount" ;
//	private final String BANK_NAME_FIELD 		= "bankname" ;
//
//	private final int DEFAULT_PAGE_NUMBER 	= 1;
//	private final int MAX_ROWS_NUMBER 		= 20;
//
//	private VendorCompanyReportInitParamDTO initParams	 	= null;
//
//	private BbrMessageEventListener messagingListener = null;
//	private BbrPagingManager pagingManager ;
//
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PROPERTIES
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			CONSTRUCTORS
//	// ****************************************************************************************
//	public VendorCompanyManagement(BbrUI bbrUIParent) 
//	{
//		super(bbrUIParent);
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
//		BbrUser user = this.getUser();
//		if(user != null && (user.getTypeDescription().equals(EnumUserType.SOLVENTA.getValue()) || user.getTypeDescription().equals(EnumUserType.PAYER.getValue())))
//		{
//			//Filtro del reporte
//			VendorCompanyManagementFilter filterLayout = new VendorCompanyManagementFilter();
//			filterLayout.initializeView();
//			this.setBbrFilterContainer(filterLayout);
//			
//			//Reporte
//			//Barra de Herramientas
//			
//			HorizontalLayout leftButtonsBar = new HorizontalLayout();
//			leftButtonsBar.setSpacing(true);
//			leftButtonsBar.setMargin(false);
//			leftButtonsBar.setHeight("30px");
//			leftButtonsBar.addStyleName("toolbar-layout");
//			
//			Button btn_Refresh 		= new Button("", BbrThemeResourcesUtils.getInstance().getResource(CoreConstants.PATH_BASE_IMAGES_BUTTONS+"icon_Refresh.png"));
//			btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
//			btn_Refresh.addClickListener((ClickListener & Serializable)e -> refreshReport_clickHandler(e));
//			btn_Refresh.addStyleName("toolbar-button");
//
//			HorizontalLayout rightButtonsBar = new HorizontalLayout();
//
//			rightButtonsBar.addComponents(btn_Refresh);
//			rightButtonsBar.setSpacing(true);
//			rightButtonsBar.setMargin(false);
//			rightButtonsBar.setHeight("30px");
//			rightButtonsBar.addStyleName("toolbar-layout");
//			
//			rightButtonsBar.setComponentAlignment(btn_Refresh, Alignment.MIDDLE_RIGHT);
//
//			HorizontalLayout toolBar = new HorizontalLayout();
//			toolBar.setWidth("100%");
//			toolBar.addComponents(leftButtonsBar,rightButtonsBar);
//			toolBar.addStyleName("filter-toolbar");
//			toolBar.setExpandRatio(leftButtonsBar, 1F);
//			toolBar.setExpandRatio(rightButtonsBar, 1F);
//
//			toolBar.setComponentAlignment(leftButtonsBar, Alignment.MIDDLE_LEFT);
//			toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);
//
//			//Paging Manager
//			pagingManager = new BbrPagingManager();
//			pagingManager.setLocale(this.getUser().getLocale());
//			pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable)e -> pagingChange_Listener(e),BbrPagingEvent.PAGE_CHANGED);
//
//			//Grilla
//			dgd_Companies = new BbrAdvancedGrid<>(VendorCompanyReportDataDTO.class, this.getUser().getLocale());
//
//			dgd_Companies.setRowsDetailsGenerator(new VendorCompanyDetailsGenerator());
//			dgd_Companies.setHasRowsDetails(true);
//			dgd_Companies.setBbrColumns(this.getReportColumns(),VendorCompanyReportDataDTO.class);
//			dgd_Companies.addStyleName("report-grid");
//			dgd_Companies.setSizeFull();
//			dgd_Companies.setLocale(this.getUser().getLocale());
//			dgd_Companies.setSelectionMode(SelectionMode.SINGLE);
//			dgd_Companies.setPagingManager(pagingManager, this.RUT_FIELD);
//
//
//			mainLayout = new VerticalLayout();
//			mainLayout.addStyleName("report-layout");
//			mainLayout.setSizeFull();
//			mainLayout.addComponents(toolBar,dgd_Companies, pagingManager);
//			mainLayout.setExpandRatio(dgd_Companies, 1F);
//
//			this.updateInitParams();
//			
//			this.addComponents(mainLayout);
//		}
//		else
//		{
//			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P0016"));
//		}
//		
//
//	}
//
//	@Override
//	public void attach() 
//	{
//		super.attach();
//		this.messagingListener = (BbrMessageEventListener & Serializable)e -> bbrMessage_Listener(e);
//		((BbrUI) UI.getCurrent()).getMessagingEventMngr().addBbrMessageListener(messagingListener, MessageTopics.ADM_PAYING_COMPANY);
//	}
//
//	@Override
//	public void detach() 
//	{
//		super.detach();
//		((BbrUI) UI.getCurrent()).getMessagingEventMngr().removeBbrMessageListener(messagingListener, MessageTopics.ADM_PAYING_COMPANY);
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			OVERRIDDEN METHODS
//	// ****************************************************************************************
//
//
//
//
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			EVENTS HANDLERS
//	// ****************************************************************************************
//	@Override
//	public void filterApplied_handler(BbrFilterEvent event)
//	{
//		if(event != null && event.getResultObject() != null)
//		{
//			initParams = (VendorCompanyReportInitParamDTO)event.getResultObject();
//			initParams = this.updateInitParams();
//
//			updateReport(initParams, true, true);
//		}
//	}
//	
//	private void pagingChange_Listener(BbrPagingEvent e) 
//	{
//		if(e != null && e.getEventType().equals(BbrPagingEvent.PAGE_CHANGED) && e.getResultObject() != null)
//		{
//			initParams.setPageNumber(e.getResultObject().getCurrentPage());
//
//			this.updateReport(initParams, false, false);
//		}
//	}
//
//	private void refreshReport_clickHandler(ClickEvent event) 
//	{
//		initParams = this.updateInitParams();
//		this.updateReport(initParams, true, true);
//	}
//
//	private void bbrMessage_Listener(BbrMessageEvent event) 
//	{
//		if(event != null && event.getMessage() != null && event.getMessage().getSenderBbrUser() != null)
//		{
//			BbrUser sender = event.getMessage().getSenderBbrUser();
//			Boolean trackReport = (sender.getId().equals(this.getUser().getId()));
//
//			updateReport(initParams, trackReport, true);	
//
//		}
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			EVENTS HANDLERS
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PRIVATE METHODS
//	// ****************************************************************************************
//	private VendorCompanyReportInitParamDTO updateInitParams() 
//	{
//		if(initParams == null)
//		{
//			initParams = new VendorCompanyReportInitParamDTO();
//		}
//		if(initParams != null)
//		{
//			SessionBean userBean = (SessionBean) this.getBbrUIParent().getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
//			initParams.setCompanyTypeCode(userBean.getCompanyType().getName());
//			initParams.setOwnCompanyId(this.getUser().getEnterpriseId());
//			initParams.setPageNumber(this.DEFAULT_PAGE_NUMBER);
//			initParams.setRows(this.MAX_ROWS_NUMBER);
//		}
//
//		return initParams;
//	}
//
//	private ArrayList<BbrColumn<?>> getReportColumns()
//	{
//		ArrayList<BbrColumn<?>> result = new ArrayList<BbrColumn<?>>();
//
//		BbrColumn<String> col_Rut 				= new BbrColumn<>(this.RUT_FIELD, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "vendor_company_rut"));
//		col_Rut.setCustomRenderer(new VendorCompanyRutRenderer());
//		BbrColumn<String> col_SocialReason 		= new BbrColumn<>(this.NAME_FIELD, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "vendor_social_reason"));
//		BbrColumn<String> col_PaymentType		= new BbrColumn<>(this.PAYMENT_TYPE_FIELD, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "vendor_payment_type"));
//		BbrColumn<String> col_CurrentAccount	= new BbrColumn<>(this.CURRENT_ACOUNT_FIELD, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "vendor_current_account"));
//		BbrColumn<String> col_BanckCode			= new BbrColumn<>(this.BANK_NAME_FIELD, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "vendor_bank_code"));
//		
//		
//		result.add(col_Rut);
//		result.add(col_SocialReason);
//		result.add(col_PaymentType);
//		result.add(col_CurrentAccount);
//		result.add(col_BanckCode);
//		
//		return result;
//	}
//
//	private void updateReport(VendorCompanyReportInitParamDTO initParams, Boolean trackReport, boolean resetPageInfo) 
//	{
//		if(initParams != null)
//		{
//			String errordescription = "";
//
//			try 
//			{
//				//Start Tracking
//				this.getTimingMngr().startTimer();
//
//				VendorCompanyReportResultDTO companyReportResult = EJBFactory.getFinancesEJBFactory().getFinancesReportManagerLocal().getVendorCompanyReport(initParams,true);
//				if(companyReportResult != null)
//				{
//					BbrError error = ErrorsMngr.getInstance().getError(companyReportResult, this.getBbrUIParent(), true);
//					if(!error.hasError())
//					{
//						dgd_Companies.setRows(Arrays.asList(companyReportResult.getReport()),resetPageInfo);
//
//						if(resetPageInfo)
//						{
//							PageInfoDTO pageInfo = companyReportResult.getPageInfo();
//							BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
//							pagingManager.setPages(bbrPage, pageInfo.getTotalrows(), resetPageInfo);	
//						}
//						
//
//						//End Tracking
//						if(trackReport == true)
//						{
//							this.trackEvent(TrackingConstants.REPORT_VIEW, this.getBbrFilterDescription());	
//						}
//
//						if(companyReportResult.getReport().length == 0)
//						{
//							VendorCompanyManagement.this.askToOpenFilter(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"), I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "empty_filter_question"));
//						}
//					}
//					else
//					{
//						errordescription = BbrUtils.getInstance().substitute("{0} - {1} - Internal Error",error.getErrorCode(), error.getErrorMessage());
//					}
//				}
//
//			} 
//			catch (BbrUserException e) 
//			{
//				AppUtils.getInstance().doLogOut(e.getMessage());
//			} 
//			catch (BbrSystemException e) 
//			{
//				e.printStackTrace();
//			}
//			catch (Exception e) 
//			{
//				errordescription = BbrUtils.getInstance().substitute("{0}",e.getMessage());
//				this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P200"));
//			}
//
//			if(errordescription.length() > 0 && trackReport == true)
//			{
//				//Track Error
//				this.trackError(TrackingConstants.REPORT_VIEW, this.getBbrFilterDescription(), errordescription, null, this);	
//			}
//
//
//		}
//
//
//	}
//	
//
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PRIVATE METHODS
//	// ****************************************************************************************
//
//
//}
