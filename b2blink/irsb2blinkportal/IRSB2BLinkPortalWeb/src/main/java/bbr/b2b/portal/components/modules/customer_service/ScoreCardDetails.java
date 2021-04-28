package bbr.b2b.portal.components.modules.customer_service;

import static bbr.b2b.portal.classes.utils.customer.CustomerSearchCriteriaFilterUtils.CLIENT;
import static bbr.b2b.portal.classes.utils.customer.CustomerSearchCriteriaFilterUtils.ORDER_NUMBER;
import static bbr.b2b.portal.classes.utils.customer.CustomerSearchCriteriaFilterUtils.ORDER_STATE;
import static bbr.b2b.portal.classes.utils.customer.CustomerSearchCriteriaFilterUtils.SHIPPING_DATE;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.SortEvent;
import com.vaadin.event.selection.MultiSelectionEvent;
import com.vaadin.event.selection.MultiSelectionListener;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.customer.report.classes.ScoreCardTableBbrDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.HasI18n;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.BbrMessagesBoxUtils;
import bbr.b2b.portal.classes.utils.app.DateFormats;
import bbr.b2b.portal.classes.wrappers.customer.CustomerOrdersDetails;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.filters.customer_service.ScoreCardDetailsFilter;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.PortalConstants;
import bbr.b2b.portal.constants.customer.CustomerServiceConstants;
import bbr.esb.services.webservices.facade.BaseResultDTO;
import bbr.esb.services.webservices.facade.DocumentToResendInitParamDTO;
import bbr.esb.services.webservices.facade.DocumentsToResendInitParamDTO;
import bbr.esb.services.webservices.facade.OrderCriteriaDTO;
import bbr.esb.services.webservices.facade.PageInfoDTO;
import bbr.esb.services.webservices.facade.ScoreCardManagerServer;
import bbr.esb.services.webservices.facade.ScoreCardManagerServerService;
import bbr.esb.services.webservices.facade.ScoreCardTableDTO;
import bbr.esb.services.webservices.facade.ScoreCardTableOfCompanyInitParamDTO;
import bbr.esb.services.webservices.facade.ScoreCardTableOfCompanyReturnDTO;
import cl.bbr.core.classes.basics.BbrPage;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.paging.BbrPagingManager;

public class ScoreCardDetails extends BbrModule implements BbrWorkExecutor, HasI18n
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants
	private static final long							serialVersionUID	= -8944881282071997640L;
	private static final String							STATUS				= "status";
	private static final String							FECHA				= "fecha";
	private static final String							SITIO				= "sitio";
	private static final String							NUMDOC				= "numdoc";
	// Components
	private BbrPagingManager							pagingManager		= null;
	private BbrAdvancedGrid<ScoreCardTableDTO>			dgd_ScoreCardTable	= null;
	private OrderCriteriaDTO[]							orderCriteria		= null;
	// Variables
	private Boolean										resetPageInfo		= false;
	private final int									MAX_ROWS_NUMBER		= 100;
	private final String								DEFAULT_SORT_FIELD	= FECHA;
	private BbrWork<ScoreCardTableOfCompanyReturnDTO>	reportWork			= null;
	private BbrWork<BaseResultDTO>						resendWork			= null;
	private BbrWork<FileDownloadInfoResultDTO>			downloadsWork			= null;
	private CustomerOrdersDetails						selectedFilter		= null;
	private String										stateSelected		= null;
	private ScoreCardDetailsFilter						filterLayout		= null;
	private List<DocumentToResendInitParamDTO>			documentsSelected	= null;
	//private Button										btn_Resend			= null;
	private Button										btn_DownloadExcel	= null;

	
	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ScoreCardDetails(BbrUI parent)
	{
		super(parent);
	}

	public ScoreCardDetails(BbrUI parent, String stateSelected)
	{
		super(parent);
		this.stateSelected = stateSelected; // default selected from
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	@Override
	public void initializeView()
	{
		// Filtro
		filterLayout = new ScoreCardDetailsFilter(this, this.stateSelected);
		filterLayout.initializeView();
		this.setBbrFilterContainer(filterLayout);

		// Paging Manager
		this.pagingManager = new BbrPagingManager();
		this.pagingManager.setLocale(this.getUser().getLocale());
		this.pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> pagingChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);

		this.dgd_ScoreCardTable = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.dgd_ScoreCardTable.addSortListener(this::dataGrid_sortHandler);
		this.initializeGridColumns();

		this.dgd_ScoreCardTable.addStyleName("report-grid");
		this.dgd_ScoreCardTable.addStyleName("bbr-multi-grid");
		this.dgd_ScoreCardTable.setSizeFull();
		this.dgd_ScoreCardTable.setSelectionMode(SelectionMode.MULTI);
		this.dgd_ScoreCardTable.setPagingManager(this.pagingManager, this.DEFAULT_SORT_FIELD);
		this.dgd_ScoreCardTable.asMultiSelect().addSelectionListener((MultiSelectionListener<ScoreCardTableDTO> & Serializable) e -> this.updateToolBarButtons_SelectionHandler(e));
		this.updateSortOrderCriteria(null);

		//MVE: 17-02-21 Se quita boton temporalmente
//		this.btn_Resend = new Button("", EnumToolbarButton.ACTION_RIGHT_PRIMARY.getResource());
//		this.btn_Resend.setDescription(this.getI18n("resend"));
//		this.btn_Resend.addClickListener((ClickListener & Serializable) e -> this.resend_clickHandler(e));
//		this.btn_Resend.addStyleName("toolbar-button");
//		this.btn_Resend.setEnabled(false);

		
		this.btn_DownloadExcel = new Button(EnumToolbarButton.DOWNLOAD_PRIMARY.getResource());
		this.btn_DownloadExcel.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "download_excel_file"));
		this.btn_DownloadExcel.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadFile_downloadHandler(e));
		this.btn_DownloadExcel.addStyleName("toolbar-button");
		
		
		Button btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
		btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
		btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler(e));
		btn_Refresh.addStyleName("toolbar-button");

		HorizontalLayout rightButtonsBar = new HorizontalLayout();
		//rightButtonsBar.addComponents(btn_Resend);
		rightButtonsBar.addComponents(btn_DownloadExcel, btn_Refresh);
		//rightButtonsBar.setWidth("100%");
		//rightButtonsBar.setComponentAlignment(btn_Resend, Alignment.MIDDLE_LEFT);
		rightButtonsBar.setComponentAlignment(this.btn_DownloadExcel, Alignment.MIDDLE_CENTER);
		rightButtonsBar.setComponentAlignment(btn_Refresh, Alignment.MIDDLE_RIGHT);
		rightButtonsBar.setSpacing(true);
		rightButtonsBar.setMargin(false);
		rightButtonsBar.setHeight("30px");
		rightButtonsBar.addStyleName("toolbar-layout");

		HorizontalLayout toolBar = new HorizontalLayout();
		toolBar.setWidth("100%");
		toolBar.addComponents(rightButtonsBar);
		toolBar.addStyleName("filter-toolbar");
		toolBar.setExpandRatio(rightButtonsBar, 1F);
		toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);

		// Reporte: Contenido

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addStyleName("report-layout");
		mainLayout.setSizeFull();
		mainLayout.setMargin(false);
		mainLayout.addComponents(toolBar);
		mainLayout.addComponents(this.dgd_ScoreCardTable, this.pagingManager);
		mainLayout.setExpandRatio(this.dgd_ScoreCardTable, 1F);

		this.addComponents(mainLayout);

		reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		reportWork.addFunction(new Function<Object, ScoreCardTableOfCompanyReturnDTO>()
		{
			@Override
			public ScoreCardTableOfCompanyReturnDTO apply(Object t)
			{
				return executeService(ScoreCardDetails.this.getBbrUIParent());
			}
		});

		this.generateFilterClick();
	}

	@Override
	public void filterApplied_handler(BbrFilterEvent event)
	{
		if ((event != null) && (event.getResultObject() != null))
		{
			this.selectedFilter = (CustomerOrdersDetails) event.getResultObject();
			this.resetPageInfo = true;
			this.executeReportWork();

		}
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		ScoreCardDetails senderReport = (ScoreCardDetails) sender;

		if (result != null)
		{
			if (result instanceof ScoreCardTableOfCompanyReturnDTO)
			{
				senderReport.doUpdateReport(result, senderReport);
			}
			else if (result instanceof BaseResultDTO)
			{
				senderReport.doUpdateReportResend(result, senderReport);
			}
			else if(result instanceof FileDownloadInfoResultDTO) 
			{
				senderReport.doUpdateDownloadFile((FileDownloadInfoResultDTO) result, senderReport);
			}

		}
		else
		{
			if (!senderReport.getBbrUIParent().hasAlertWindowOpen())
			{
				senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));
			}

			senderReport.stopWaiting();
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================
	private void generateFilterClick()
	{
		ClickEvent e = new ClickEvent(filterLayout);
		filterLayout.buttonClick(e);
	}

	private void refreshReport_clickHandler(ClickEvent event)
	{
		this.executeReportWork();
	}

	private void resend_clickHandler(ClickEvent event)
	{
		String count = String.valueOf(this.dgd_ScoreCardTable.getAllSelectedsItems().size());
		BbrMessagesBoxUtils.showConfirmationMessage(this.getBbrUIParent(),
				this.getI18nGeneric("windows_title_info"), this.getI18n("resend_count_selected", count),
				() -> this.initializeResendServiceWork());
	}

	private void initializeResendServiceWork()
	{
		resendWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		resendWork.addFunction(new Function<Object, BaseResultDTO>()
		{
			@Override
			public BaseResultDTO apply(Object t)
			{
				return executeResendService(ScoreCardDetails.this.getBbrUIParent());
			}
		});

		this.startWaiting();
		this.executeBbrWork(resendWork);
	}

	private void executeReportWork()
	{
		this.startWaiting();
		this.executeBbrWork(reportWork);

	}

	private void pagingChange_Listener(BbrPagingEvent e)
	{
		if ((e != null) && e.getEventType().equals(BbrPagingEvent.PAGE_CHANGED) && (e.getResultObject() != null))
		{
			this.startWaiting();

			// this.currentPage = e.getResultObject().getCurrentPage();
			// initParam.setPageNumber(e.getResultObject().getCurrentPage());
			//
			// this.trackReport = false;
			this.resetPageInfo = false;
			this.executeBbrWork(reportWork);
		}
	}
	
	

	private void btn_DownloadFile_downloadHandler(ClickEvent event)
	{
		downloadsWork = new BbrWork<>(this, this.getBbrUIParent(), btn_DownloadTriggerButton);
		downloadsWork.addFunction(new Function<Object, FileDownloadInfoResultDTO>()
		{
			@Override
			public FileDownloadInfoResultDTO apply(Object t)
			{
				return executeDownloadService(ScoreCardDetails.this.getBbrUIParent());
			}
		});

		this.startWaiting();
		this.executeBbrWork(downloadsWork);
	}
	
	
	
	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private void updateToolBarButtons_SelectionHandler(MultiSelectionEvent<ScoreCardTableDTO> event)
	{
		boolean canResend = false;

		if (event != null && event.getAllSelectedItems() != null && event.getAllSelectedItems().size() > 0)
		{
			canResend = true;

			if (event.getAllSelectedItems().size() == 1)
			{
				canResend = true;
			}
		}

//		if (this.btn_Resend != null)
//		{
//			this.btn_Resend.setEnabled(canResend);
//		}
	}

	private void initializeGridColumns()
	{
		this.dgd_ScoreCardTable.addColumn(ScoreCardTableDTO::getNumdoc)
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, NUMDOC))
				.setId(NUMDOC);
		this.dgd_ScoreCardTable.addColumn(ScoreCardTableDTO::getRetail)
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, SITIO))
				.setId(SITIO);
		this.dgd_ScoreCardTable.addColumn(i -> this.transformDate(i.getFecha()))
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER,
						"shipping_date"))
				.setStyleGenerator(i -> BbrAlignment.CENTER.getValue())
				.setId(FECHA);
		this.dgd_ScoreCardTable.addColumn(i -> this.getVisualStatus(i.getEstado()))
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, STATUS))
				.setId(STATUS);

	}

	public String transformDate(XMLGregorianCalendar xmlGregorianCalendar)
	{
		LocalDate localDate = LocalDate.of(
				xmlGregorianCalendar.getYear(),
				xmlGregorianCalendar.getMonth(),
				xmlGregorianCalendar.getDay());
		LocalTime localTime = LocalTime.of(xmlGregorianCalendar.getHour(), xmlGregorianCalendar.getMinute());
		LocalDateTime dateTime = LocalDateTime.of(localDate, localTime);
		String result = dateTime.format(DateFormats.getDTFGrid());
		return result;
	}

	public XMLGregorianCalendar transformGregorianDate(String datetime)
	{
		XMLGregorianCalendar result = null;
		try
		{
			result = DatatypeFactory.newInstance().newXMLGregorianCalendar(datetime);
		}
		catch (DatatypeConfigurationException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	private void dataGrid_sortHandler(SortEvent<GridSortOrder<ScoreCardTableDTO>> e)
	{
		if (e.isUserOriginated() && pagingManager.getTotalsPages() > 1)
		{
			this.updateSortOrderCriteria(e.getSortOrder());

			// this.trackReport = false;

			this.resetPageInfo = true;
			this.startWaiting();
			this.executeBbrWork(this.reportWork);
		}
	}

	private void updateSortOrderCriteria(List<GridSortOrder<ScoreCardTableDTO>> sortOrderList)
	{
		if (sortOrderList != null && !sortOrderList.isEmpty())
		{
			ArrayList<OrderCriteriaDTO> resultList = new ArrayList<>();
			for (GridSortOrder<ScoreCardTableDTO> sorOrder : sortOrderList)
			{
				OrderCriteriaDTO order = new OrderCriteriaDTO();
				order.setPropertyname(sorOrder.getSorted().getId().toUpperCase());
				order.setAscending(sorOrder.getDirection() == SortDirection.ASCENDING);
				resultList.add(order);
			}
			this.orderCriteria = resultList.toArray(new OrderCriteriaDTO[resultList.size()]);
		}
		else
		{
			OrderCriteriaDTO order = new OrderCriteriaDTO();
			order.setPropertyname(DEFAULT_SORT_FIELD.toUpperCase());
			order.setAscending(false);
			this.orderCriteria = new OrderCriteriaDTO[] { order };

			GridSortOrder<ScoreCardTableDTO> sortOrder = new GridSortOrder<>(this.dgd_ScoreCardTable.getColumn(DEFAULT_SORT_FIELD), SortDirection.DESCENDING);
			sortOrderList = new ArrayList<GridSortOrder<ScoreCardTableDTO>>();
			sortOrderList.add(sortOrder);

			this.dgd_ScoreCardTable.setSortOrder(sortOrderList);
		}
	}

	private ScoreCardTableOfCompanyReturnDTO executeService(BbrUI bbrUIParent)
	{
		ScoreCardTableOfCompanyReturnDTO result = new ScoreCardTableOfCompanyReturnDTO();

		try
		{
			// del detalle
			result = this.getScoreCardDTOWS();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

	private BaseResultDTO executeResendService(BbrUI bbrUIParent)
	{
		BaseResultDTO result = new BaseResultDTO();

		try
		{
			// reenvio
			result = this.getScoreDetailsResendDTOWS();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

	private BaseResultDTO getScoreDetailsResendDTOWS()
	{

		ScoreCardManagerServer loginClient;
		BaseResultDTO item = null;
		try
		{
			loginClient = new ScoreCardManagerServerService(new URL(CustomerServiceConstants.getInstance().getScoreCardWebServiceEndpointPath())).getScoreCardManagerServerPort();
			DocumentsToResendInitParamDTO initParamResend = this.getInitParamResend();
			item = loginClient.doAddServiceEventByContracted(initParamResend);
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			if (!this.getBbrUIParent().hasAlertWindowOpen())
			{
				this.showErrorMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "error_webservice"));
			}
			e.printStackTrace();
		}
		return item;
	}

	private ScoreCardTableOfCompanyReturnDTO getScoreCardDTOWS()
	{

		ScoreCardManagerServer loginClient;
		ScoreCardTableOfCompanyReturnDTO item = null;
		try
		{
			loginClient = new ScoreCardManagerServerService(new URL(CustomerServiceConstants.getInstance().getScoreCardWebServiceEndpointPath())).getScoreCardManagerServerPort();
			ScoreCardTableOfCompanyInitParamDTO initParam = this.getInitParam();
			item = loginClient.getScoreCardTableOfCompany(initParam);
			EJBFactory.getCustomerEJBFactory().getCustomerManagerLocal(this.getBbrUIParent()).saveCompanySelectedAndAddCountUserProvider(this.getBbrUIParent().getUser().getId(), initParam.getCompanyrut());
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			if (!this.getBbrUIParent().hasAlertWindowOpen())
			{
				this.showErrorMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "error_webservice"));
			}
			e.printStackTrace();
		}
		return item;
	}
	
	//TODO excel
	private ScoreCardTableOfCompanyReturnDTO getExcelScoreCardDTOWS()
	{

		ScoreCardManagerServer loginClient;
		ScoreCardTableOfCompanyReturnDTO item = null;
		try
		{
			loginClient = new ScoreCardManagerServerService(new URL(CustomerServiceConstants.getInstance().getScoreCardWebServiceEndpointPath())).getScoreCardManagerServerPort();
			ScoreCardTableOfCompanyInitParamDTO initParam = this.getExcelInitParam();
			item = loginClient.getScoreCardTableOfCompany(initParam);
			EJBFactory.getCustomerEJBFactory().getCustomerManagerLocal(this.getBbrUIParent()).saveCompanySelectedAndAddCountUserProvider(this.getBbrUIParent().getUser().getId(), initParam.getCompanyrut());
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			if (!this.getBbrUIParent().hasAlertWindowOpen())
			{
				this.showErrorMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "error_webservice"));
			}
			e.printStackTrace();
		}
		return item;
	}

	private DocumentsToResendInitParamDTO getInitParamResend()
	{
		DocumentsToResendInitParamDTO initParam = new DocumentsToResendInitParamDTO();
		List<DocumentToResendInitParamDTO> documents = initParam.getDocuments();
		ArrayList<ScoreCardTableDTO> selected = this.dgd_ScoreCardTable.getAllSelectedsItems();
		if (selected != null)
		{
			this.documentsSelected = new ArrayList<>();
			for (ScoreCardTableDTO scoreCardTableDTO : selected)
			{
				DocumentToResendInitParamDTO doc = new DocumentToResendInitParamDTO();
				doc.setCompanyid(this.isNotNull(scoreCardTableDTO.getCompanyid()));
				doc.setServiceid(this.isNotNull(scoreCardTableDTO.getServiceid()));
				doc.setSiteid(this.isNotNull(scoreCardTableDTO.getSiteid()));
				doc.setDocument(this.isNotNull(scoreCardTableDTO.getNumdoc()));
				this.documentsSelected.add(doc);
			}
		}

		if (this.documentsSelected != null)
		{
			documents.addAll(this.documentsSelected);
		}
		return initParam;
	}

	private String isNotNull(Long value)
	{
		String result = value != null ? value.toString() : "";
		return result;
	}

	//TODO initparam
	private ScoreCardTableOfCompanyInitParamDTO getInitParam()
	{
		Integer requestedPage = (!resetPageInfo && this.pagingManager.getCurrentPage() > 0) ? (Integer) this.pagingManager.getCurrentPage() : PortalConstants.DEFAULT_PAGE_NUMBER;
		String pvcode = this.selectedFilter.getCompany() != null ? this.selectedFilter.getCompany().getRut() : null;

		ScoreCardTableOfCompanyInitParamDTO initParam = new ScoreCardTableOfCompanyInitParamDTO();
		initParam.setCompanyrut(pvcode);
		initParam.setQueryToCount(false);
		initParam.setRows(this.MAX_ROWS_NUMBER);
		initParam.setNeedPageInfo(this.resetPageInfo);
		initParam.setPageNumber(requestedPage);
		initParam.setServicename(CustomerServiceConstants.REPORT_OPTION);
		initParam.setFilterType(this.selectedFilter.getCriterion().getTypeSearch().getId());
		initParam.setNumdoc(0L);
		initParam.setSitename("");
		initParam.setStatus("");
		initParam.setRetailname("");

		LocalDateTime startDate = LocalDateTime.now();
		LocalDateTime untilDate = LocalDateTime.now();

		initParam.setSince(startDate != null ? DateFormats.formatDateWs(startDate).toString() : "");
		initParam.setUntil(untilDate != null ? DateFormats.formatDateWs(untilDate).toString() : "");

		switch (this.selectedFilter.getCriterion().getTypeSearch().getId())
		{
			case CLIENT:
				initParam.setSitename(this.selectedFilter.getCriterion().getSite().getName());
				break;
			case SHIPPING_DATE:
				startDate = this.selectedFilter.getCriterion().getStartDateTime();
				untilDate = this.selectedFilter.getCriterion().getEndDateTime();
				initParam.setSince(startDate != null ? DateFormats.formatDateWs(startDate).toString() : "");
				initParam.setUntil(untilDate != null ? DateFormats.formatDateWs(untilDate).toString() : "");
				break;
			case ORDER_NUMBER:
				initParam.setNumdoc(this.selectedFilter.getCriterion().getOrderNumber());
				break;
			case ORDER_STATE:
				initParam.setStatus(this.getRealStatus(this.selectedFilter.getCriterion().getOrderState().getDescription()));
				break;
		}


		List<OrderCriteriaDTO> orders = initParam.getOrderBy();
		if (this.orderCriteria != null)
		{
			orders.addAll(Arrays.asList(this.orderCriteria));
		}
		return initParam;
	}
	
	private ScoreCardTableOfCompanyInitParamDTO getExcelInitParam()
	{
		Integer requestedPage = (!resetPageInfo && this.pagingManager.getCurrentPage() > 0) ? (Integer) this.pagingManager.getCurrentPage() : PortalConstants.DEFAULT_PAGE_NUMBER;
		String pvcode = this.selectedFilter.getCompany() != null ? this.selectedFilter.getCompany().getRut() : null;

		ScoreCardTableOfCompanyInitParamDTO initParam = new ScoreCardTableOfCompanyInitParamDTO();
		initParam.setCompanyrut(pvcode);
		initParam.setQueryToCount(false);
		initParam.setNeedPageInfo(false);
		//initParam.setRows(this.MAX_ROWS_NUMBER);
		//initParam.setNeedPageInfo(this.resetPageInfo);
		//initParam.setPageNumber(requestedPage);
		initParam.setServicename(CustomerServiceConstants.REPORT_OPTION);
		initParam.setFilterType(this.selectedFilter.getCriterion().getTypeSearch().getId());
		initParam.setNumdoc(0L);
		initParam.setSitename("");
		initParam.setStatus("");
		initParam.setRetailname("");

		LocalDateTime startDate = LocalDateTime.now();
		LocalDateTime untilDate = LocalDateTime.now();

		initParam.setSince(startDate != null ? DateFormats.formatDateWs(startDate).toString() : "");
		initParam.setUntil(untilDate != null ? DateFormats.formatDateWs(untilDate).toString() : "");

		switch (this.selectedFilter.getCriterion().getTypeSearch().getId())
		{
			case CLIENT:
				initParam.setSitename(this.selectedFilter.getCriterion().getSite().getName());
				break;
			case SHIPPING_DATE:
				startDate = this.selectedFilter.getCriterion().getStartDateTime();
				untilDate = this.selectedFilter.getCriterion().getEndDateTime();
				initParam.setSince(startDate != null ? DateFormats.formatDateWs(startDate).toString() : "");
				initParam.setUntil(untilDate != null ? DateFormats.formatDateWs(untilDate).toString() : "");
				break;
			case ORDER_NUMBER:
				initParam.setNumdoc(this.selectedFilter.getCriterion().getOrderNumber());
				break;
			case ORDER_STATE:
				initParam.setStatus(this.getRealStatus(this.selectedFilter.getCriterion().getOrderState().getDescription()));
				break;
		}


		List<OrderCriteriaDTO> orders = initParam.getOrderBy();
		if (this.orderCriteria != null)
		{
			orders.addAll(Arrays.asList(this.orderCriteria));
		}
		return initParam;
	}

	private String getRealStatus(String visualStatus)
	{
		if (visualStatus.equals(this.getI18n("total_error")))
		{
			return this.getI18n("error");
		}
		if (visualStatus.equals(this.getI18n("total_success")))
		{
			return this.getI18n("received");
		}
		if (visualStatus.equals(this.getI18n("in_process")))
		{
			return this.getI18n("in_process");
		}
		return "";
	}

	private String getVisualStatus(String visualStatus)
	{
		if (visualStatus.equals(this.getI18n("error")))
		{
			return this.getI18n("total_error");
		}
		if (visualStatus.equals(this.getI18n("received")))
		{
			return this.getI18n("total_success");
		}
		if (visualStatus.equals(this.getI18n("in_process")))
		{
			return this.getI18n("in_process");
		}
		return "";
	}

	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{

		ScoreCardDetails senderReport = (ScoreCardDetails) sender;

		if (result != null)
		{
			ScoreCardTableOfCompanyReturnDTO reportResult = (ScoreCardTableOfCompanyReturnDTO) result;
			List<ScoreCardTableDTO> scores = reportResult.getScorecards();
			if (reportResult.getStatuscode().equals("0"))
			{
				if (!scores.isEmpty())
				{
					ListDataProvider<ScoreCardTableDTO> dataprovider = new ListDataProvider<>(scores);
					senderReport.dgd_ScoreCardTable.setDataProvider(dataprovider, senderReport.resetPageInfo);

					if (senderReport.resetPageInfo)
					{
						PageInfoDTO pageInfo = reportResult.getPageInfoDTO();

						BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
						senderReport.pagingManager.setPages(bbrPage, senderReport.resetPageInfo);

					}
				}
				else
				{
					ListDataProvider<ScoreCardTableDTO> dataprovider = new ListDataProvider<>(scores);
					senderReport.dgd_ScoreCardTable.setDataProvider(dataprovider, senderReport.resetPageInfo);
				}
			}
			else
			{
				if (!senderReport.getBbrUIParent().hasAlertWindowOpen())
				{
					senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(),
							BbrUtilsResources.RES_GENERIC, "windows_title_error"),
							reportResult.getStatusmessage());
				}

				ListDataProvider<ScoreCardTableDTO> dataprovider = new ListDataProvider<>(scores);
				senderReport.dgd_ScoreCardTable.setDataProvider(dataprovider, senderReport.resetPageInfo);
			}

		}
		senderReport.stopWaiting();

	}

	private void doUpdateReportResend(Object result, BbrWorkExecutor sender)
	{

		ScoreCardDetails senderReport = (ScoreCardDetails) sender;

		if (result != null)
		{
			BaseResultDTO reportResult = (BaseResultDTO) result;
			if (reportResult.getStatuscode().equals("0"))
			{
				senderReport.showInfoMessage(this.getI18nGeneric("windows_title_info"),
						this.getI18n("processed_successfully"), null,
						() -> senderReport.executeReportWork());

			}
			else
			{
				if (!senderReport.getBbrUIParent().hasAlertWindowOpen())
				{
					senderReport.showErrorMessage(this.getI18nGeneric("windows_title_error"),
							reportResult.getStatusmessage(), null,
							() -> senderReport.refreshReport_clickHandler(null));
				}

			}

		}
		senderReport.stopWaiting();

	}

	private void doUpdateDownloadFile(FileDownloadInfoResultDTO result, BbrWorkExecutor sender)
	{
		ScoreCardDetails senderReport = (ScoreCardDetails) sender;
		if (senderReport != null && result != null)
		{
			BbrError error = ErrorsMngr.getInstance().getError(result, this.getBbrUIParent(), true);

			if (!error.hasError())
			{
				senderReport.downloadLinkFile(result);
			}
		}

		senderReport.stopWaiting();
	}

	
	@Override
	public String getI18n(String resource)
	{
		return I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_CUSTOMER, resource);
	}

	public String getI18n(String resource, String... params)
	{
		return I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_CUSTOMER, resource, params);
	}

	public String getI18nGeneric(String resource)
	{
		return I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, resource);
	}

	
	private FileDownloadInfoResultDTO executeDownloadService(BbrUI bbrUIParent)
	{
		FileDownloadInfoResultDTO fileResult = null;

		try
		{
			// Llama a Ws para obtener datos
			 ScoreCardTableOfCompanyReturnDTO wsDataResult = getExcelScoreCardDTOWS();
			 List<ScoreCardTableBbrDTO> scoreCardDataList = new ArrayList<ScoreCardTableBbrDTO>();
			 
			 if(wsDataResult.getStatuscode().equals("0")) {				 
				 for(ScoreCardTableDTO wsdata : wsDataResult.getScorecards()) {
					 ScoreCardTableBbrDTO scoreCardData = new ScoreCardTableBbrDTO();
					 scoreCardData.setCompanyid(wsdata.getCompanyid());
					 scoreCardData.setEstado(wsdata.getEstado());
					 scoreCardData.setNumdoc(wsdata.getNumdoc());
					 scoreCardData.setRetail(wsdata.getRetail());
					 scoreCardData.setServiceid(wsdata.getServiceid());
					 scoreCardData.setSiteid(wsdata.getSiteid());
					 scoreCardData.setSitio(wsdata.getSitio());
					 
					 // Transforma fecha
					 XMLGregorianCalendar gregorianDate = wsdata.getFecha();
					 LocalDateTime date = gregorianDate.toGregorianCalendar().toZonedDateTime().withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
					 scoreCardData.setFecha(date);

					 scoreCardDataList.add(scoreCardData);
				 }

				 ScoreCardTableBbrDTO[] scoreCardArr = (ScoreCardTableBbrDTO[]) scoreCardDataList.toArray(new ScoreCardTableBbrDTO[scoreCardDataList.size()]);
				 		 
				 fileResult = EJBFactory.getCustomerEJBFactory().getCustomerManagerLocal(bbrUIParent).downloadScoreCardDetailReport(scoreCardArr, this.getBbrUIParent().getUser().getId());
				 
			 }
		}

		catch (BbrUserException ex)
		{
			AppUtils.getInstance().doLogOut(ex.getMessage(), this.getBbrUIParent());
		}
		catch (BbrSystemException ex)
		{
			ex.printStackTrace();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return fileResult;

	}

	
	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
