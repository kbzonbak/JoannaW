//package bbr.b2b.portal.modules.tickets;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//
//import com.vaadin.event.ItemClickEvent;
//import com.vaadin.event.ItemClickEvent.ItemClickListener;
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Button.ClickListener;
//import com.vaadin.ui.Grid.SelectionMode;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.VerticalLayout;
//
//import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
//import bbr.b2b.portal.constants.BbrUtilsResources;
//import bbr.b2b.portal.classes.factory.EJBFactory;
//import bbr.b2b.portal.classes.i18n.I18NManager;
//import bbr.b2b.portal.classes.utils.app.AppUtils;
//import bbr.b2b.portal.components.basics.BbrSystemException;
//import bbr.b2b.portal.components.basics.BbrUserException;
//import bbr.b2b.portal.components.filters.tickets.ListAnalystTicketsFilter;
//import bbr.b2b.portal.components.modules.tickets.AnalystTicketDetails;
//import bbr.b2b.portal.components.renderers.grid_details.VendorTicketAttachRenderer;
//import bbr.b2b.trac.report.classes.TicketReportDataDTO;
//import bbr.b2b.trac.report.classes.TicketReportInitParamDTO;
//import bbr.b2b.trac.report.classes.TicketReportResultDTO;
//import cl.bbr.core.classes.basics.BbrPage;
//import cl.bbr.core.classes.constants.BbrAlignment;
//import cl.bbr.core.classes.constants.CoreConstants;
//import cl.bbr.core.classes.constants.TrackingConstants;
//import cl.bbr.core.classes.converter.ShortDateConverter;
//import cl.bbr.core.classes.errors.BbrError;
//import cl.bbr.core.classes.errors.ErrorsMngr;
//import cl.bbr.core.classes.events.BbrEvent;
//import cl.bbr.core.classes.events.BbrEventListener;
//import cl.bbr.core.classes.events.BbrFilterEvent;
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
//public class ListAnalystTickets extends BbrModule 
//{
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PROPERTIES
//	// ****************************************************************************************
//	private static final long serialVersionUID = 7027502352762624910L;
//
//	private BbrAdvancedGrid<TicketReportDataDTO> dgd_Tickets ;
//	private VerticalLayout mainLayout ;
//
//	private final String TICKET_NUMBER_FIELD 		= "ticketnumber" ;
//	private final String VENDOR_RUT_FIELD 			= "vendorrut" ;
//	private final String VENDOR_NAME_FIELD 			= "vendorname" ;
//	private final String USER_NAME_FIELD 			= "username" ;
//	private final String TICKET_TYPE_FIELD 			= "tickettype" ;
//	private final String CREATION_DATE_FIELD 		= "creationdate" ;
//	private final String EVENT_DATE_FIELD 			= "eventdate" ;
//	private final String STATUS_FIELD 				= "providerstatus" ;
//	private final String ATTACHED_FIELD 			= "attached" ;
//	
//	
//	private final int DEFAULT_PAGE_NUMBER 	= 1;
//	private final int MAX_ROWS_NUMBER 		= 20;
//
//	private TicketReportInitParamDTO initParams	 	= null;
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
//	public ListAnalystTickets(BbrUI bbrUIParent) 
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
//		//Filtro del reporte
//		ListAnalystTicketsFilter filterLayout = new ListAnalystTicketsFilter();
//		filterLayout.initializeView();
//		this.setBbrFilterContainer(filterLayout);
//
//		//Reporte
//		//Barra de Herramientas
//		HorizontalLayout leftButtonsBar = new HorizontalLayout();
//		HorizontalLayout rightButtonsBar = new HorizontalLayout();
//
//		Button btn_ViewDetails 		= new Button("",BbrThemeResourcesUtils.getInstance().getResource(CoreConstants.PATH_BASE_IMAGES_BUTTONS+"icon_TicketDetail.png"));
//		btn_ViewDetails.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "view_ticket_details"));
//		btn_ViewDetails.addClickListener((ClickListener & Serializable)e -> viewTicketDetails_clickHandler(e));
//		btn_ViewDetails.addStyleName("toolbar-button");
//
//		leftButtonsBar.addComponents(btn_ViewDetails);
//
//
//		leftButtonsBar.setSpacing(true);
//		leftButtonsBar.setMargin(false);
//		leftButtonsBar.setHeight("30px");
//		leftButtonsBar.addStyleName("toolbar-layout");
//
//		Button btn_Refresh 		= new Button("", BbrThemeResourcesUtils.getInstance().getResource(CoreConstants.PATH_BASE_IMAGES_BUTTONS+"icon_Refresh.png"));
//		btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
//		btn_Refresh.addClickListener((ClickListener & Serializable)e -> refreshReport_clickHandler(e));
//		btn_Refresh.addStyleName("toolbar-button");
//
//		rightButtonsBar.addComponents(btn_Refresh);
//		rightButtonsBar.setSpacing(true);
//		rightButtonsBar.setMargin(false);
//		rightButtonsBar.setHeight("30px");
//		rightButtonsBar.addStyleName("toolbar-layout");
//
//		rightButtonsBar.setComponentAlignment(btn_Refresh, Alignment.MIDDLE_RIGHT);
//
//		HorizontalLayout toolBar = new HorizontalLayout();
//		toolBar.setWidth("100%");
//		toolBar.addComponents(leftButtonsBar,rightButtonsBar);
//		toolBar.addStyleName("filter-toolbar");
//		toolBar.setExpandRatio(leftButtonsBar, 1F);
//		toolBar.setExpandRatio(rightButtonsBar, 1F);
//
//		toolBar.setComponentAlignment(leftButtonsBar, Alignment.MIDDLE_LEFT);
//		toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);
//
//		//Paging Manager
//		pagingManager = new BbrPagingManager();
//		pagingManager.setLocale(this.getUser().getLocale());
//		pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable)e -> pagingChange_Listener(e),BbrPagingEvent.PAGE_CHANGED);
//
//		//Grilla
//		dgd_Tickets = new BbrAdvancedGrid<>(TicketReportDataDTO.class,this.getUser().getLocale());
//		dgd_Tickets.setBbrColumns(this.getReportColumns(),TicketReportDataDTO.class);
//		dgd_Tickets.addStyleName("report-grid");
//		dgd_Tickets.setSizeFull();
//		dgd_Tickets.setSelectionMode(SelectionMode.SINGLE);
//		dgd_Tickets.addItemClickListener((ItemClickListener & Serializable)e -> dgdItem_clickHandler(e));
//		dgd_Tickets.setPagingManager(pagingManager, this.TICKET_NUMBER_FIELD);
//
//
//		mainLayout = new VerticalLayout();
//		mainLayout.addStyleName("report-layout");
//		mainLayout.setSizeFull();
//		mainLayout.addComponents(toolBar,dgd_Tickets, pagingManager);
//		mainLayout.setExpandRatio(dgd_Tickets, 1F);
//
//		this.addComponents(mainLayout);
//
//	}
//
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			OVERRIDDEN METHODS
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			EVENTS HANDLERS
//	// ****************************************************************************************
//	private void pagingChange_Listener(BbrPagingEvent e) 
//	{
//		if(e != null && e.getEventType().equals(BbrPagingEvent.PAGE_CHANGED) && e.getResultObject() != null)
//		{
//			initParams.setPageNumber(e.getResultObject().getCurrentPage());
//
//			this.updateReport(initParams, false, false,true);
//		}
//	}
//
//	@Override
//	public void filterApplied_handler(BbrFilterEvent event)
//	{
//		if(event != null && event.getResultObject() != null)
//		{
//			initParams = (TicketReportInitParamDTO) event.getResultObject();
//			initParams = this.updateInitParams();
//
//			updateReport(initParams, true, true,true);
//		}
//	}
//
//	private void ticketUpdated_handler(BbrEvent event) 
//	{
//		if(event.getEventType().equals(BbrEvent.ITEM_UPDATED))
//		{
//			this.updateReport(initParams, false, true,false);
//		}
//	}
//
//	private void viewTicketDetails_clickHandler(ClickEvent event) 
//	{
//		TicketReportDataDTO item = (TicketReportDataDTO) dgd_Tickets.getSelectedRow();
//
//		if(item != null)
//		{
//			this.viewTicketDetails(item);	
//		}
//		else
//		{
//			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "ticket_requieres"));
//		}
//	}
//
//	private void refreshReport_clickHandler(ClickEvent event) 
//	{
//		initParams = this.updateInitParams();
//		this.updateReport(initParams, true, true,true);
//	}
//
//	private void dgdItem_clickHandler(ItemClickEvent event) 
//	{
//		if (event.isDoubleClick()) 
//		{
//			if(event.getItemId() != null)
//			{
//				TicketReportDataDTO selectedTicket = (TicketReportDataDTO) event.getItemId();
//				if(selectedTicket != null)
//				{
//					viewTicketDetails(selectedTicket);
//				}	
//			}
//		}
//	}
//
//	private void viewTicketDetails(TicketReportDataDTO selectedTicket)
//	{
//		AnalystTicketDetails winViewTicketDetails = new AnalystTicketDetails(selectedTicket,this.initParams.getVendorId());
//		winViewTicketDetails.addBbrEventListener((BbrEventListener & Serializable) event -> ticketUpdated_handler(event));		
//		winViewTicketDetails.initializeView();
//		winViewTicketDetails.open(getBbrUIParent(), true);
//	}
//
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			EVENTS HANDLERS
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PRIVATE METHODS
//	// ****************************************************************************************
//	private TicketReportInitParamDTO updateInitParams() 
//	{
//		if(initParams != null)
//		{
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
//		BbrColumn<Long> col_TicketNumber 			= new BbrColumn<>(this.TICKET_NUMBER_FIELD, Long.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "ticket_number"));
//		col_TicketNumber.setAlignment(BbrAlignment.RIGHT);
//
//		BbrColumn<String> col_VendorRut		= new BbrColumn<>(this.VENDOR_RUT_FIELD, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "vendor_rut"));
//		BbrColumn<String> col_SocialReason	= new BbrColumn<>(this.VENDOR_NAME_FIELD, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "social_reason"));
//		BbrColumn<String> col_Name 			= new BbrColumn<>(this.USER_NAME_FIELD, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "ticket_user_name"));
//		BbrColumn<String> col_TicketType	= new BbrColumn<>(this.TICKET_TYPE_FIELD, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "ticket_type"));
//
//		BbrColumn<Date> col_CreationDate	= new BbrColumn<>(this.CREATION_DATE_FIELD, Date.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "ticket_creation_date"));
//		col_CreationDate.setAlignment(BbrAlignment.CENTER);
//		col_CreationDate.setConverter(new ShortDateConverter());
//
//		BbrColumn<Date> col_EventDate	= new BbrColumn<>(this.EVENT_DATE_FIELD, Date.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "ticket_event_date"));
//		col_EventDate.setAlignment(BbrAlignment.CENTER);
//		col_EventDate.setConverter(new ShortDateConverter());
//		
//		BbrColumn<String> col_Status	= new BbrColumn<>(this.STATUS_FIELD, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "ticket_status"));
//		
//		BbrColumn<Boolean> col_Attach			= new BbrColumn<>(this.ATTACHED_FIELD, Boolean.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "ticket_attach"));
//		col_Attach.setAlignment(BbrAlignment.CENTER);
//		col_Attach.setCustomRenderer(new VendorTicketAttachRenderer());
//
//		result.add(col_TicketNumber);
//		result.add(col_VendorRut);
//		result.add(col_SocialReason);
//		result.add(col_Name);
//		result.add(col_TicketType);
//		result.add(col_CreationDate);
//		result.add(col_EventDate);
//		result.add(col_Status);
//		result.add(col_Attach);
//		
//
//		return result;
//
//	}
//
//	private void updateReport(TicketReportInitParamDTO initParams, Boolean trackReport, boolean resetPageInfo, boolean askForFilter) 
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
//				TicketReportResultDTO reportResult = EJBFactory.getTicketsEJBFactory().getTicketReportManagerLocal().getAnalystTicketReportByFilter(initParams, true);
//				if(reportResult != null)
//				{
//					BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), true);
//					if(!error.hasError())
//					{
//						dgd_Tickets.setRows(Arrays.asList(reportResult.getReport()),resetPageInfo);
//						if(resetPageInfo)
//						{
//							PageInfoDTO pageInfo = reportResult.getPageInfo();
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
//						if(reportResult.getReport().length == 0 && askForFilter==true)
//						{
//							ListAnalystTickets.this.askToOpenFilter(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"), I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "empty_filter_question"));
//						}
//						
//						askForFilter = true;
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
//				this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P300"));
//			}
//
//			if(errordescription.length() > 0 && trackReport == true)
//			{
//				//Track Error
//				this.trackError(TrackingConstants.REPORT_VIEW, this.getBbrFilterDescription(), errordescription, null, this);	
//			}
//		}
//
//
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PRIVATE METHODS
//	// ****************************************************************************************
//
//}
