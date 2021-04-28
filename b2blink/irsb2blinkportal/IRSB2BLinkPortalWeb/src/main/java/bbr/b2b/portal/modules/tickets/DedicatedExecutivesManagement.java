//package bbr.b2b.portal.modules.tickets;
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
//import com.vaadin.ui.VerticalLayout;
//
//import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
//import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
//import bbr.b2b.portal.constants.BbrUtilsResources;
//import bbr.b2b.portal.classes.constants.EnumUserType;
//import bbr.b2b.portal.classes.factory.EJBFactory;
//import bbr.b2b.portal.classes.i18n.I18NManager;
//import bbr.b2b.portal.classes.utils.app.AppUtils;
//import bbr.b2b.portal.components.basics.BbrSystemException;
//import bbr.b2b.portal.components.basics.BbrUserException;
//import bbr.b2b.portal.components.modules.tickets.AddDedicatedExecutive;
//import bbr.b2b.portal.components.renderers.grid_details.TicketCompanyRutRenderer;
//import bbr.b2b.portal.components.renderers.grid_details.TicketExecutiveRutRenderer;
//import bbr.b2b.trac.report.classes.DedicatedAnalystDataDTO;
//import bbr.b2b.trac.report.classes.DedicatedAnalystInitParamDTO;
//import bbr.b2b.trac.report.classes.DedicatedAnalystReportResultDTO;
//import cl.bbr.core.classes.basics.BbrPage;
//import cl.bbr.core.classes.basics.BbrUser;
//import cl.bbr.core.classes.constants.CoreConstants;
//import cl.bbr.core.classes.constants.TrackingConstants;
//import cl.bbr.core.classes.errors.BbrError;
//import cl.bbr.core.classes.errors.ErrorsMngr;
//import cl.bbr.core.classes.events.BbrEvent;
//import cl.bbr.core.classes.events.BbrEventListener;
//import cl.bbr.core.classes.events.BbrPagingEvent;
//import cl.bbr.core.classes.events.BbrPagingEventListener;
//import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
//import cl.bbr.core.classes.utilities.BbrUtils;
//import cl.bbr.core.components.basics.BbrMessageBox;
//import cl.bbr.core.components.basics.BbrModule;
//import cl.bbr.core.components.basics.BbrUI;
//import cl.bbr.core.components.grid.BbrAdvancedGrid;
//import cl.bbr.core.components.grid.BbrColumn;
//import cl.bbr.core.components.paging.BbrPagingManager;
//
//public class DedicatedExecutivesManagement extends BbrModule 
//{
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PROPERTIES
//	// ****************************************************************************************
//	private static final long serialVersionUID = 7027502352762624910L;
//
//	private BbrAdvancedGrid<DedicatedAnalystDataDTO> dgd_Executives ;
//	private VerticalLayout mainLayout ;
//
//	private final String VENDOR_RUT_FIELD 				= "vendorrut" ;
//	private final String VENDOR_SOCIAL_REASON_FIELD		= "vendorname" ;
//	private final String EXECUTIVE_RUT_FIELD 			= "userrut" ;
//	private final String EXECUTIVE_NAME_FIELD 			= "username" ;
//	private final String EXECUTIVE_LAST_NAME_FIELD 		= "userlastname" ;
//	private final String EXECUTIVE_EMAIL_FIELD 			= "useremail" ;
//
//
//	private final int DEFAULT_PAGE_NUMBER 	= 1;
//	private final int MAX_ROWS_NUMBER 		= 20;
//
//	private BbrPagingManager pagingManager ;
//
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PROPERTIES
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			CONSTRUCTORS
//	// ****************************************************************************************
//	public DedicatedExecutivesManagement(BbrUI bbrUIParent) 
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
//		if(user != null && user.getTypeDescription().equals(EnumUserType.SOLVENTA.getValue()))
//		{
//			//Reporte
//			//Barra de Herramientas
//
//			Button btn_AddExecutive 	= new Button("", BbrThemeResourcesUtils.getInstance().getResource(CoreConstants.PATH_BASE_IMAGES_BUTTONS+"icon_AddUser.png"));
//			btn_AddExecutive.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "add_executive"));
//			btn_AddExecutive.addClickListener((ClickListener & Serializable)e -> addExecutive_clickHandler(e));
//			btn_AddExecutive.addStyleName("toolbar-button");
//
//			//
//			//			Button btn_EditExecutive 		= new Button("",BbrThemeResourcesUtils.getInstance().getResource(CoreConstants.PATH_BASE_IMAGES_BUTTONS+"icon_EditUser.png"));
//			//			btn_EditExecutive.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "edit_executive"));
//			//			//btn_EditUser.addClickListener((ClickListener & Serializable)e -> editUser_clickHandler(e));
//			//			btn_EditExecutive.addStyleName("toolbar-button");
//			//
//			//
//			Button btn_DeleteExecutive		= new Button("", BbrThemeResourcesUtils.getInstance().getResource(CoreConstants.PATH_BASE_IMAGES_BUTTONS+"icon_DeactivateUser.png"));
//			btn_DeleteExecutive.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "delete_executive"));
//			btn_DeleteExecutive.addClickListener((ClickListener & Serializable)e -> deleteExecutive_clickHandler(e));
//			btn_DeleteExecutive.addStyleName("toolbar-button");
//
//			Button btn_Refresh 		= new Button("", BbrThemeResourcesUtils.getInstance().getResource(CoreConstants.PATH_BASE_IMAGES_BUTTONS+"icon_Refresh.png"));
//			btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
//			btn_Refresh.addClickListener((ClickListener & Serializable)e -> refreshReport_clickHandler(e));
//			btn_Refresh.addStyleName("toolbar-button");
//
//			HorizontalLayout leftButtonsBar = new HorizontalLayout();
//			leftButtonsBar.setSpacing(true);
//			leftButtonsBar.setMargin(false);
//			leftButtonsBar.setHeight("30px");
//			leftButtonsBar.addStyleName("toolbar-layout");
//
//			leftButtonsBar.addComponents(btn_AddExecutive, btn_DeleteExecutive);
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
//			dgd_Executives = new BbrAdvancedGrid<>(DedicatedAnalystDataDTO.class, this.getUser().getLocale());
//
//			dgd_Executives.setBbrColumns(this.getReportColumns(),DedicatedAnalystDataDTO.class);
//			dgd_Executives.addStyleName("report-grid");
//			dgd_Executives.setSizeFull();
//			dgd_Executives.setLocale(this.getUser().getLocale());
//			dgd_Executives.setSelectionMode(SelectionMode.MULTI);
//			dgd_Executives.setPagingManager(pagingManager, this.VENDOR_RUT_FIELD);
//
//			mainLayout = new VerticalLayout();
//			mainLayout.addStyleName("report-layout-no-filter");
//			mainLayout.setSizeFull();
//			mainLayout.addComponents(toolBar,dgd_Executives, pagingManager);
//			mainLayout.setExpandRatio(dgd_Executives, 1F);
//
//			this.updateReport(true, true, DEFAULT_PAGE_NUMBER);
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
//
//
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
//	private void pagingChange_Listener(BbrPagingEvent e) 
//	{
//		if(e != null && e.getEventType().equals(BbrPagingEvent.PAGE_CHANGED) && e.getResultObject() != null)
//		{
//			this.updateReport(false, false, e.getResultObject().getCurrentPage());
//		}
//	}
//
//	private void refreshReport_clickHandler(ClickEvent event) 
//	{
//		this.updateReport(true, true, DEFAULT_PAGE_NUMBER);
//	}
//
//
//	private void addExecutive_clickHandler(ClickEvent e) 
//	{
//		AddDedicatedExecutive winAddDedicatedExecutive = new AddDedicatedExecutive();
//		winAddDedicatedExecutive.initializeView();
//		winAddDedicatedExecutive.addBbrEventListener((BbrEventListener & Serializable) event -> executiveCreated_handler(event));		
//		winAddDedicatedExecutive.open(getBbrUIParent(), true);
//	}
//	private void executiveCreated_handler(BbrEvent e) 
//	{
//		if(e.getEventType().equals(BbrEvent.ITEM_CREATED))
//		{
//			this.updateReport(false, true, DEFAULT_PAGE_NUMBER);
//		}
//	}
//
//	private void deleteExecutive_clickHandler(ClickEvent e) 
//	{
//		ArrayList<DedicatedAnalystDataDTO> items = dgd_Executives.getSelectedsItems();
//		if(items != null && items.size() > 0)
//		{
//			String alertMessage = (items.size() == 1)?I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "sure_delete_executive"):I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "sure_delete_executives");
//
//			BbrMessageBox
//			.createQuestion()
//			.withYesButton((Runnable & Serializable)() -> deleteExecutiveAction(items))
//			.withCancelButton()
//			.withCaption(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"))
//			.withHtmlMessage(alertMessage)
//			.open();
//		}
//		else
//		{
//			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "select_at_least_one"));
//		}
//	}
//
//	private void deleteExecutiveAction(ArrayList<DedicatedAnalystDataDTO> items) 
//	{
//		String message = "";
//		BaseResultDTO 	executiveResult = null ;
//		try 
//		{
//			if(items != null && items.size() > 0)
//			{
//				DedicatedAnalystInitParamDTO[] deleteInitParams = this.getDeleteInitParams(items);
//
//				executiveResult 	= EJBFactory.getTrackingEJBFactory().getTracReportManagerLocal().doDeleteDedicatedAnalyst(deleteInitParams);
//				if(executiveResult != null)
//				{
//					message = I18NManager.getErrorMessageBaseResult(executiveResult, executiveResult.getParams()); // <-- Obtiene el mensaje de error. "" si no hay errores.
//				}
//				else
//				{
//					// -> Error executiveResult = null
//					message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "T1");
//				}
//
//			}
//
//		} 
//		catch (Exception e) //Error no controlado
//		{
//			message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "T1");
//		}
//
//		if(message.length() > 0)
//		{
//			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), message);
//		}
//		else
//		{
//			this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"), I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "successful_operation"));
//			this.updateReport(false, true, pagingManager.getCurrentPage());
//		}
//	}
//
//	private DedicatedAnalystInitParamDTO[] getDeleteInitParams(ArrayList<DedicatedAnalystDataDTO> items) 
//	{
//		DedicatedAnalystInitParamDTO[] result = null;
//
//		if(items != null && items.size() > 0)
//		{
//			result = new DedicatedAnalystInitParamDTO[items.size()];
//			for (int i = 0; i < items.size(); i++) 
//			{
//				result[i] = new DedicatedAnalystInitParamDTO();
//				result[i].setEmkey(items.get(i).getEmkey());
//				result[i].setUskey(items.get(i).getUskey());
//			}
//		}
//
//		return result;
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			EVENTS HANDLERS
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PRIVATE METHODS
//	// ****************************************************************************************
//	private ArrayList<BbrColumn<?>> getReportColumns()
//	{
//		ArrayList<BbrColumn<?>> result = new ArrayList<BbrColumn<?>>();
//
//		BbrColumn<String> col_VendorRut 			= new BbrColumn<>(this.VENDOR_RUT_FIELD, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "vendor_rut"));
//		BbrColumn<String> col_VendorName 			= new BbrColumn<>(this.VENDOR_SOCIAL_REASON_FIELD, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "vendor_social_reason"));
//		BbrColumn<String> col_ExecutiveRut			= new BbrColumn<>(this.EXECUTIVE_RUT_FIELD, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "executive_rut"));
//		BbrColumn<String> col_ExecutiveName			= new BbrColumn<>(this.EXECUTIVE_NAME_FIELD, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "executive_name"));
//		BbrColumn<String> col_ExecutiveLastName		= new BbrColumn<>(this.EXECUTIVE_LAST_NAME_FIELD, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "executive_last_name"));
//		BbrColumn<String> col_ExecutiveEmail		= new BbrColumn<>(this.EXECUTIVE_EMAIL_FIELD, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "executive_email"));
//
//		col_VendorRut.setCustomRenderer(new TicketCompanyRutRenderer());
//		col_ExecutiveRut.setCustomRenderer(new TicketExecutiveRutRenderer());
//
//		result.add(col_VendorRut);
//		result.add(col_VendorName);
//		result.add(col_ExecutiveRut);
//		result.add(col_ExecutiveName);
//		result.add(col_ExecutiveLastName);
//		result.add(col_ExecutiveEmail);
//
//		return result;
//	}
//
//	private void updateReport(Boolean trackReport, boolean resetPageInfo, int pageNumber) 
//	{
//		String errordescription = "";
//
//		try 
//		{
//			//Start Tracking
//			this.getTimingMngr().startTimer();
//
//			DedicatedAnalystReportResultDTO reportResult = EJBFactory.getTrackingEJBFactory().getTracReportManagerLocal().getDedicatedAnalystReport(resetPageInfo, this.MAX_ROWS_NUMBER, pageNumber);
//			if(reportResult != null)
//			{
//				BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), true);
//				if(!error.hasError())
//				{
//					dgd_Executives.setRows(Arrays.asList(reportResult.getReport()),resetPageInfo);
//					
//					if(resetPageInfo)
//					{
//						PageInfoDTO pageInfo = reportResult.getPageInfo();
//						BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
//						pagingManager.setPages(bbrPage, pageInfo.getTotalrows(), resetPageInfo);
//					}
//		
//					//End Tracking
//					if(trackReport == true)
//					{
//						this.trackEvent(TrackingConstants.REPORT_VIEW, this.getBbrFilterDescription());	
//					}
//				}
//				else
//				{
//					errordescription = BbrUtils.getInstance().substitute("{0} - {1} - Internal Error",error.getErrorCode(), error.getErrorMessage());
//				}
//			}
//
//		} 
//		catch (BbrUserException e) 
//		{
//			AppUtils.getInstance().doLogOut(e.getMessage());
//		} 
//		catch (BbrSystemException e) 
//		{
//			e.printStackTrace();
//		}
//		catch (Exception e) 
//		{
//			errordescription = BbrUtils.getInstance().substitute("{0}",e.getMessage());
//			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P200"));
//		}
//
//		if(errordescription.length() > 0 && trackReport == true)
//		{
//			//Track Error
//			this.trackError(TrackingConstants.REPORT_VIEW, this.getBbrFilterDescription(), errordescription, null, this);	
//		}
//	}
//
//
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PRIVATE METHODS
//	// ****************************************************************************************
//
//
//}
