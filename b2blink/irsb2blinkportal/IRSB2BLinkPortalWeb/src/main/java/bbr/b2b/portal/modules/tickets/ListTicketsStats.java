//package bbr.b2b.portal.modules.tickets;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import com.vaadin.ui.Grid.SelectionMode;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Label;
//import com.vaadin.ui.VerticalLayout;
//
//import bbr.b2b.portal.constants.BbrUtilsResources;
//import bbr.b2b.portal.classes.factory.EJBFactory;
//import bbr.b2b.portal.classes.i18n.I18NManager;
//import bbr.b2b.portal.classes.utils.app.AppUtils;
//import bbr.b2b.portal.components.basics.BbrSystemException;
//import bbr.b2b.portal.components.basics.BbrUserException;
//import bbr.b2b.trac.report.classes.AnalystSummaryReportDataDTO;
//import bbr.b2b.trac.report.classes.AnalystSummaryReportResultDTO;
//import bbr.b2b.trac.report.classes.CompanySummaryReportDataDTO;
//import bbr.b2b.trac.report.classes.CompanySummaryReportResultDTO;
//import bbr.b2b.trac.report.classes.TicketTypeSummaryReportDataDTO;
//import bbr.b2b.trac.report.classes.TicketTypeSummaryReportResultDTO;
//import cl.bbr.core.classes.constants.BbrAlignment;
//import cl.bbr.core.classes.constants.TrackingConstants;
//import cl.bbr.core.classes.errors.BbrError;
//import cl.bbr.core.classes.errors.ErrorsMngr;
//import cl.bbr.core.classes.utilities.BbrUtils;
//import cl.bbr.core.components.basics.BbrModule;
//import cl.bbr.core.components.basics.BbrUI;
//import cl.bbr.core.components.grid.BbrAdvancedGrid;
//import cl.bbr.core.components.grid.BbrColumn;
//
//public class ListTicketsStats extends BbrModule 
//{
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PROPERTIES
//	// ****************************************************************************************
//	private static final long serialVersionUID = 7027502352762624910L;
//
//	private BbrAdvancedGrid<AnalystSummaryReportDataDTO> dgd_AnalystTickets ;
//	private BbrAdvancedGrid<CompanySummaryReportDataDTO> dgd_CompanyTickets ;
//	private BbrAdvancedGrid<TicketTypeSummaryReportDataDTO> dgd_TicketType ;
//	private VerticalLayout mainLayout ;
//
//	private final String ANALYST_ROW_NUMBER_FIELD 		= "rownumber" ;
//	private final String ANALYST_NAME_FIELD 			= "analystname" ;
//	private final String ANALYST_COMPANY_COUNT_FIELD 	= "companycount" ;
//	private final String ANALYST_INACTIVE_COUNT_FIELD 	= "inactivecount" ;
//	private final String ANALYST_ACTIVE_COUNT_FIELD 	= "activecount" ;
//
//	private final String COMPANY_ROW_NUMBER_FIELD 		= "rownumber" ;
//	private final String COMPANY_CODE_FIELD 			= "companycode" ;
//	private final String COMPANY_NAME_FIELD 			= "companyname" ;
//	private final String COMPANY_INACTIVE_COUNT_FIELD 	= "inactivecount" ;
//	private final String COMPANY_ACTIVE_COUNT_FIELD 	= "activecount" ;
//
//	private final String TICKET_TYPE_ROW_NUMBER_FIELD 		= "rownumber" ;
//	private final String TICKET_TYPE_DESCRIPTION_FIELD 		= "tickettypedescription" ;
//	private final String TICKET_TYPE_INACTIVE_COUNT_FIELD 	= "inactivecount" ;
//	private final String TICKET_TYPE_ACTIVE_COUNT_FIELD 	= "activecount" ;
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PROPERTIES
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			CONSTRUCTORS
//	// ****************************************************************************************
//	public ListTicketsStats(BbrUI bbrUIParent) 
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
//		//Reporte
//		//Grilla Resumen Analista
//		dgd_AnalystTickets = new BbrAdvancedGrid<>(AnalystSummaryReportDataDTO.class,this.getUser().getLocale());
//		dgd_AnalystTickets.setSortable(false);
//		dgd_AnalystTickets.setBbrColumns(this.getAnalystTicketsReportColumns(),AnalystSummaryReportDataDTO.class);
//		dgd_AnalystTickets.getDefaultHeaderRow().setStyleName("bbr-grid-header");
//		dgd_AnalystTickets.addStyleName("report-grid");
//		dgd_AnalystTickets.addStyleName("bbr-grid-multi-header");
//		dgd_AnalystTickets.setSizeFull();
//		dgd_AnalystTickets.setSelectionMode(SelectionMode.SINGLE);
//
//		//Grilla Resumen Empresa
//		dgd_CompanyTickets = new BbrAdvancedGrid<>(CompanySummaryReportDataDTO.class,this.getUser().getLocale());
//		dgd_CompanyTickets.setBbrColumns(this.getCompanyTicketsReportColumns(),CompanySummaryReportDataDTO.class);
//		dgd_CompanyTickets.addStyleName("report-grid");
//		dgd_CompanyTickets.addStyleName("bbr-grid-multi-header");
//		dgd_CompanyTickets.getDefaultHeaderRow().setStyleName("bbr-grid-header");
//		dgd_CompanyTickets.setSizeFull();
//		dgd_CompanyTickets.setSelectionMode(SelectionMode.SINGLE);
//
//		//Grilla Resumen Analista
//		dgd_TicketType = new BbrAdvancedGrid<>(TicketTypeSummaryReportDataDTO.class,this.getUser().getLocale());
//		dgd_TicketType.setBbrColumns(this.getTypeCaseTicketsReportColumns(),TicketTypeSummaryReportDataDTO.class);
//		dgd_TicketType.addStyleName("report-grid");
//		dgd_TicketType.addStyleName("bbr-grid-multi-header");
//		dgd_TicketType.getDefaultHeaderRow().setStyleName("bbr-grid-header");
//		dgd_TicketType.setSizeFull();
//		dgd_TicketType.setSelectionMode(SelectionMode.SINGLE);
//
//		//Label Analistas
//		Label lbl_Analist = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "ticket_analyst_header"));
//		lbl_Analist.addStyleName("bbr-bold-label");
//		
//		//Label Empresas
//		Label lbl_Company = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "ticket_company_header"));
//		lbl_Company.addStyleName("bbr-bold-label");
//		//Label Casos Tipo
//		Label lbl_TypeCase = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "ticket_type_header"));
//		lbl_TypeCase.addStyleName("bbr-bold-label");
//
//		VerticalLayout mainLeftLayout = new VerticalLayout();
//		mainLeftLayout.setSizeFull();
//		mainLeftLayout.addComponents(lbl_Analist,dgd_AnalystTickets);
//		mainLeftLayout.setExpandRatio(dgd_AnalystTickets, 1F);
//		mainLeftLayout.setSpacing(true);
//
//		VerticalLayout rightTopLayout = new VerticalLayout();
//		rightTopLayout.setSizeFull();
//		rightTopLayout.addComponents(lbl_Company,dgd_CompanyTickets);
//		rightTopLayout.setExpandRatio(dgd_CompanyTickets, 1F);
//		rightTopLayout.setSpacing(true);
//
//		VerticalLayout rightBottomLayout = new VerticalLayout();
//		rightBottomLayout.setSizeFull();
//		rightBottomLayout.addComponents(lbl_TypeCase,dgd_TicketType);
//		rightBottomLayout.setExpandRatio(dgd_TicketType, 1F);
//		rightBottomLayout.setSpacing(true);
//
//		VerticalLayout mainRightLayout = new VerticalLayout();
//		mainRightLayout.setSizeFull();
//		mainRightLayout.addComponents(rightTopLayout,rightBottomLayout);
//
//		HorizontalLayout mainContentLayout = new HorizontalLayout();
//		mainContentLayout.setSizeFull();
//		mainContentLayout.addComponents(mainLeftLayout, mainRightLayout);
//		mainContentLayout.setSpacing(true);
//
//		mainLayout = new VerticalLayout();
//		mainLayout.addStyleName("report-layout-no-filter");
//		mainLayout.addStyleName("bbr-win-container");
//		mainLayout.setSizeFull();
//		mainLayout.addComponents(mainContentLayout);
//		mainLayout.setExpandRatio(mainContentLayout, 1F);
//
//		this.addComponents(mainLayout);
//		
//		this.updateReports();
//
//	}
//
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			OVERRIDDEN METHODS
//	// ****************************************************************************************
//
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			EVENTS HANDLERS
//	// ****************************************************************************************
//	private void updateReports()
//	{
//		this.updateAnalystReport(true);
//		this.updateCompanyReport(true);
//		this.updateTicketTypesReport(true);
//	}
//
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			EVENTS HANDLERS
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION 	---->			PRIVATE METHODS
//	// ****************************************************************************************
//
//	private ArrayList<BbrColumn<?>> getAnalystTicketsReportColumns()
//	{
//		ArrayList<BbrColumn<?>> result = new ArrayList<BbrColumn<?>>();
//
//		BbrColumn<Long> col_RowNumber 		= new BbrColumn<>(this.ANALYST_ROW_NUMBER_FIELD, Long.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "analyst_row_number"));
//		BbrColumn<String> col_Name 			= new BbrColumn<>(this.ANALYST_NAME_FIELD, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "analyst_name"));
//		BbrColumn<Long> col_ActiveCount 	= new BbrColumn<>(this.ANALYST_ACTIVE_COUNT_FIELD, Long.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "analyst_active_count"));
//		BbrColumn<Long> col_InactiveCount 	= new BbrColumn<>(this.ANALYST_INACTIVE_COUNT_FIELD, Long.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "analyst_inactive_count"));
//		BbrColumn<Long> col_CompanyCount 	= new BbrColumn<>(this.ANALYST_COMPANY_COUNT_FIELD, Long.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "analyst_company_count"));
//		
//		col_Name.setHtmlHeaderCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "analyst_name"));
//		col_ActiveCount.setHtmlHeaderCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "analyst_active_count"));
//		col_InactiveCount.setHtmlHeaderCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "analyst_inactive_count"));
//		col_CompanyCount.setHtmlHeaderCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "analyst_company_count"));
//
//		col_RowNumber.setAlignment(BbrAlignment.RIGHT);
//		col_ActiveCount.setAlignment(BbrAlignment.RIGHT);
//		col_InactiveCount.setAlignment(BbrAlignment.RIGHT);
//		col_CompanyCount.setAlignment(BbrAlignment.RIGHT);
//
//		result.add(col_RowNumber);
//		result.add(col_Name);
//		result.add(col_ActiveCount);
//		result.add(col_InactiveCount);
//		result.add(col_CompanyCount);
//
//		return result;
//	}
//
//	private ArrayList<BbrColumn<?>> getCompanyTicketsReportColumns()
//	{
//		ArrayList<BbrColumn<?>> result = new ArrayList<BbrColumn<?>>();
//
//		BbrColumn<Long> col_RowNumber 		= new BbrColumn<>(this.COMPANY_ROW_NUMBER_FIELD, Long.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "company_row_number"));
//		BbrColumn<String> col_Code 			= new BbrColumn<>(this.COMPANY_CODE_FIELD, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "company_code"));
//		BbrColumn<String> col_SocialReason 	= new BbrColumn<>(this.COMPANY_NAME_FIELD, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "company_name"));
//		BbrColumn<Long> col_ActiveCount 	= new BbrColumn<>(this.COMPANY_ACTIVE_COUNT_FIELD, Long.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "company_active_count"));
//		BbrColumn<Long> col_InactiveCount 	= new BbrColumn<>(this.COMPANY_INACTIVE_COUNT_FIELD, Long.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "company_inactive_count"));
//
//		col_Code.setHtmlHeaderCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "company_code"));
//		col_SocialReason.setHtmlHeaderCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "company_name"));
//		col_ActiveCount.setHtmlHeaderCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "company_active_count"));
//		col_InactiveCount.setHtmlHeaderCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "company_inactive_count"));
//		
//		
//		col_RowNumber.setAlignment(BbrAlignment.RIGHT);
//		col_ActiveCount.setAlignment(BbrAlignment.RIGHT);
//		col_InactiveCount.setAlignment(BbrAlignment.RIGHT);
//
//		result.add(col_RowNumber);
//		result.add(col_Code);
//		result.add(col_SocialReason);
//		result.add(col_ActiveCount);
//		result.add(col_InactiveCount);
//
//		return result;
//	}
//
//	private ArrayList<BbrColumn<?>> getTypeCaseTicketsReportColumns()
//	{
//		ArrayList<BbrColumn<?>> result = new ArrayList<BbrColumn<?>>();
//
//		BbrColumn<Long> col_RowNumber 		= new BbrColumn<>(this.TICKET_TYPE_ROW_NUMBER_FIELD, Long.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "ticket_type_row_number"));
//		BbrColumn<String> col_TicketType 	= new BbrColumn<>(this.TICKET_TYPE_DESCRIPTION_FIELD, String.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "ticket_type_description"));
//		BbrColumn<Long> col_ActiveCount 	= new BbrColumn<>(this.TICKET_TYPE_ACTIVE_COUNT_FIELD, Long.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "ticket_type_active_count"));
//		BbrColumn<Long> col_InactiveCount 	= new BbrColumn<>(this.TICKET_TYPE_INACTIVE_COUNT_FIELD, Long.class, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "ticket_type_inactive_count"));
//
//		col_ActiveCount.setHtmlHeaderCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "ticket_type_active_count"));
//		col_InactiveCount.setHtmlHeaderCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_TICKETS, "ticket_type_inactive_count"));
//		
//		col_RowNumber.setAlignment(BbrAlignment.RIGHT);
//		col_ActiveCount.setAlignment(BbrAlignment.RIGHT);
//		col_InactiveCount.setAlignment(BbrAlignment.RIGHT);
//
//		result.add(col_RowNumber);
//		result.add(col_TicketType);
//		result.add(col_ActiveCount);
//		result.add(col_InactiveCount);
//
//		return result;
//
//	}
//
//	private void updateAnalystReport(Boolean trackReport) 
//	{
//
//		String errordescription = "";
//
//		try 
//		{
//			//Start Tracking
//			this.getTimingMngr().startTimer();
//
//			AnalystSummaryReportResultDTO reportResult = EJBFactory.getTicketsEJBFactory().getTicketReportManagerLocal().getAnalystSummaryReport();
//			if(reportResult != null)
//			{
//				BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), true);
//				if(!error.hasError())
//				{
//					dgd_AnalystTickets.setRows(Arrays.asList(reportResult.getReport()));
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
//			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P300"));
//		}
//
//		if(errordescription.length() > 0 && trackReport == true)
//		{
//			//Track Error
//			this.trackError(TrackingConstants.REPORT_VIEW, this.getBbrFilterDescription(), errordescription, null, this);	
//		}
//
//	}
//	
//	private void updateCompanyReport(Boolean trackReport) 
//	{
//		
//		String errordescription = "";
//		
//		try 
//		{
//			//Start Tracking
//			this.getTimingMngr().startTimer();
//			
//			CompanySummaryReportResultDTO reportResult = EJBFactory.getTicketsEJBFactory().getTicketReportManagerLocal().getCompanySummaryReport();
//			if(reportResult != null)
//			{
//				BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), true);
//				if(!error.hasError())
//				{
//					dgd_CompanyTickets.setRows(Arrays.asList(reportResult.getReport()));
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
//			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P300"));
//		}
//		
//		if(errordescription.length() > 0 && trackReport == true)
//		{
//			//Track Error
//			this.trackError(TrackingConstants.REPORT_VIEW, this.getBbrFilterDescription(), errordescription, null, this);	
//		}
//		
//	}
//
//	private void updateTicketTypesReport(Boolean trackReport) 
//	{
//		
//		String errordescription = "";
//		
//		try 
//		{
//			//Start Tracking
//			this.getTimingMngr().startTimer();
//			
//			TicketTypeSummaryReportResultDTO reportResult = EJBFactory.getTicketsEJBFactory().getTicketReportManagerLocal().getTicketTypeSummaryReport();
//			if(reportResult != null)
//			{
//				BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), true);
//				if(!error.hasError())
//				{
//					dgd_TicketType.setRows(Arrays.asList(reportResult.getReport()));
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
//			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P300"));
//		}
//		
//		if(errordescription.length() > 0 && trackReport == true)
//		{
//			//Track Error
//			this.trackError(TrackingConstants.REPORT_VIEW, this.getBbrFilterDescription(), errordescription, null, this);	
//		}
//		
//	}
//	// ****************************************************************************************
//	// ENDING SECTION 		---->			PRIVATE METHODS
//	// ****************************************************************************************
//
//}
