package bbr.b2b.portal.modules.customer;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import javax.xml.datatype.XMLGregorianCalendar;

import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.SortEvent;
import com.vaadin.event.SortEvent.SortListener;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.HeaderCell;
import com.vaadin.ui.components.grid.HeaderRow;

import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.DateFormats;
import bbr.b2b.portal.classes.wrappers.customer.RequestClientReportFilterSectionInfo;
import bbr.b2b.portal.classes.wrappers.customer.RequestReportFilterSectionInfo;
import bbr.b2b.portal.classes.wrappers.customer.RequestReportPeriodFilterSectionInfo;
import bbr.b2b.portal.classes.wrappers.customer.RequestReportSelectedInfo;
import bbr.b2b.portal.components.filters.customer_service.RequestReportFilter;
import bbr.b2b.portal.components.modules.customer_service.RequestReportDetails;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.CustomerConstants;
import bbr.b2b.portal.constants.customer.CustomerServiceConstants;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import bbr.esb.services.webservices.facade.OrderCriteriaDTO;
import bbr.esb.services.webservices.facade.PageInfoDTO;
import bbr.esb.services.webservices.facade.ScoreCardManagerServer;
import bbr.esb.services.webservices.facade.ScoreCardManagerServerService;
import bbr.esb.services.webservices.facade.TicketReportDataResultDTO;
import bbr.esb.services.webservices.facade.TicketReportInitParamDTO;
import bbr.esb.services.webservices.facade.TicketReportResultDTO;
import cl.bbr.core.classes.basics.BbrPage;
import cl.bbr.core.classes.basics.BbrUser;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.constants.CoreConstants;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.utilities.BbrDateUtils;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.paging.BbrPagingManager;

public class RequestReport extends BbrModule implements BbrWorkExecutor
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants

	private static final long							serialVersionUID								= 1442791796539290664L;

	private static final String							ADJUNTO											= "adjunto";
	private static final String							ADJUNTO2										= "adjunto2";
	private static final String							CLIENTE											= "cliente";
	private static final String							ESTADO											= "estado";
	private static final String							FECHA_PROCESO									= "fechaproceso";
	private static final String							FECHA_PROCESO_MANUAL							= "fechaprocesomanual";
	private static final String							FECHA_SOLICITUD									= "fechasolicitud";
	private static final String							NRO_SOLICITUD									= "nrosolicitud";
	private static final String							REFERENCIA										= "referencia";
	private static final String							TIPO_SOLICITUD									= "tiposolicitud";
	private static final String							SPL												= "SPL";

	private final int									DEFAULT_PAGE_NUMBER								= 1;
	private final int									MAX_ROWS_NUMBER									= 100;
	private final String								DEFAULT_SORT_FIELD								= FECHA_SOLICITUD;

	private VerticalLayout								mainLayout										= null;
	private BbrAdvancedGrid<TicketReportDataResultDTO>	datGrid_Request									= null;
	private BbrPagingManager							pagingManager									= null;
	private Button										btn_Refresh										= null;
	// private Button btn_Details = null;
	private BbrWork<TicketReportResultDTO>				reportWork										= null;
	private Boolean										resetPageInfo									= true;
	private OrderCriteriaDTO[]							orderCriteria									= null;
	private TicketReportInitParamDTO					initParam										= null;
	private RequestReportSelectedInfo					selectedRequestReportSelectedInfo				= null;
	private CompanyDataDTO								selectedCompanyData								= null;
	private RequestReportPeriodFilterSectionInfo		selectedRequestReportPeriodFilterSectionInfo	= null;
	private RequestReportFilterSectionInfo				selectedRequestReportFilterSectionInfo			= null;
	private RequestClientReportFilterSectionInfo		selectedRequestClientReportFilterSectionInfo	= null;
	// private TicketReportDataResultDTO selectesTicket = null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public RequestReport(BbrUI bbrUIParent)
	{
		super(bbrUIParent);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================
	@Override
	public void filterApplied_handler(BbrFilterEvent event)
	{
		if ((event != null) && (event.getResultObject() != null))
		{
			this.selectedRequestReportSelectedInfo = (RequestReportSelectedInfo) event.getResultObject();
			this.selectedCompanyData = this.selectedRequestReportSelectedInfo.getSelectedProviderFilterSection();
			this.selectedRequestReportPeriodFilterSectionInfo = this.selectedRequestReportSelectedInfo.getSelectedRequestReportPeriodFilterSection();
			this.selectedRequestReportFilterSectionInfo = this.selectedRequestReportSelectedInfo.getSelectedRequestReportFilterSection();
			this.selectedRequestClientReportFilterSectionInfo = this.selectedRequestReportSelectedInfo.getSelectedRequestClientReportFilterSectionInfo();

			this.executeReportWork();

		}
	}

	private void executeReportWork()
	{
		this.startWaiting();
		this.resetPageInfo = true;
		this.executeBbrWork(reportWork);

	}

	@Override
	public void initializeView()
	{
		BbrUser user = this.getUser();

		if (user != null)
		{
			// Filtro
			RequestReportFilter filterLayout = new RequestReportFilter(this);
			filterLayout.initializeView();
			this.setBbrFilterContainer(filterLayout);

			// Paging Manager
			this.pagingManager = new BbrPagingManager();
			this.pagingManager.setLocale(this.getUser().getLocale());
			this.pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> this.pagingChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);

			// Barra de herramientas superior izq
			HorizontalLayout leftButtonsBar = new HorizontalLayout();
			leftButtonsBar.setSpacing(true);
			leftButtonsBar.setMargin(false);
			leftButtonsBar.setHeight("30px");
			leftButtonsBar.addStyleName("toolbar-layout");

			// this.btn_Details = new Button("", EnumToolbarButton.SEARCH_PRIMARY.getResource());
			// this.btn_Details.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "show_details"));
			// this.btn_Details.addClickListener((ClickListener & Serializable) e -> this.btn_AvailableStockDetails_clickHandler(e));
			// this.btn_Details.addStyleName("toolbar-button");
			//
			// leftButtonsBar.addComponents(this.btn_Details);

			this.btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
			this.btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
			this.btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler(e));
			this.btn_Refresh.addStyleName("toolbar-button");

			// Barra superior dercha
			HorizontalLayout rightButtonsBar = new HorizontalLayout();
			rightButtonsBar.addComponents(this.btn_Refresh);
			rightButtonsBar.setSpacing(true);
			rightButtonsBar.setMargin(false);
			rightButtonsBar.setHeight("30px");
			rightButtonsBar.addStyleName("toolbar-layout");

			// Barra superior
			HorizontalLayout toolBar = new HorizontalLayout();
			toolBar.setWidth("100%");
			toolBar.addComponents(leftButtonsBar, rightButtonsBar);
			toolBar.addStyleName("filter-toolbar");
			toolBar.setExpandRatio(leftButtonsBar, 1F);
			toolBar.setExpandRatio(rightButtonsBar, 1F);

			toolBar.setComponentAlignment(leftButtonsBar, Alignment.MIDDLE_LEFT);
			toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);
			// Reporte: Grilla
			this.datGrid_Request = new BbrAdvancedGrid<>(this.getUser().getLocale());
			this.datGrid_Request.setSortable(false);

			this.initializeDataGridColumns();

			this.datGrid_Request.addStyleName("report-grid");
			this.datGrid_Request.setSizeFull();
			this.datGrid_Request.setPagingManager(pagingManager, FECHA_SOLICITUD);
			this.datGrid_Request.addSortListener((SortListener<GridSortOrder<TicketReportDataResultDTO>> & Serializable) e -> dataGrid_sortHandler(e));
			// this.datGrid_Request.addItemClickListener((ItemClickListener<TicketReportDataResultDTO> & Serializable) e -> dgdItem_clickHandler(e));

			VerticalLayout gridLayout = new VerticalLayout();
			gridLayout.setSizeFull();
			gridLayout.setMargin(false);
			gridLayout.addComponents(this.datGrid_Request, pagingManager);
			gridLayout.setExpandRatio(this.datGrid_Request, 1F);

			HorizontalLayout dataLayout = new HorizontalLayout();
			dataLayout.setSizeFull();
			dataLayout.setMargin(false);
			dataLayout.addComponents(gridLayout);

			this.mainLayout = new VerticalLayout();
			mainLayout.addStyleName("report-layout");
			mainLayout.setSizeFull();
			mainLayout.setMargin(false);
			mainLayout.addComponents(toolBar, dataLayout);
			mainLayout.setExpandRatio(dataLayout, 1F);

			this.addComponents(mainLayout);

			this.updateButtons(false);

			this.updateSortOrderCriteria(null);

			reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
			reportWork.addFunction(new Function<Object, TicketReportResultDTO>()
			{
				@Override
				public TicketReportResultDTO apply(Object t)
				{
					return executeService(RequestReport.this.getBbrUIParent());
				}
			});

		}
		else
		{
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P0016"));
		}
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		RequestReport bbrSender = (RequestReport) sender;
		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				bbrSender.doUpdateReport(result, sender);
			}
		}
		else
		{
			bbrSender.showErrorMessage(I18NManager.getI18NString(bbrSender.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					I18NManager.getI18NString(bbrSender.getBbrUIParent(),
							BbrUtilsResources.RES_GENERIC,
							"unsuccessful_operation"));
		}
		bbrSender.stopWaiting();
	}
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{
		RequestReport senderReport = (RequestReport) sender;

		if (result != null)
		{
			TicketReportResultDTO reportResult = (TicketReportResultDTO) result;
			List<TicketReportDataResultDTO> listTicketReportData = reportResult.getTickets();
			if (reportResult.getStatuscode().equals("0"))
			{
				if (!listTicketReportData.isEmpty())
				{
					ListDataProvider<TicketReportDataResultDTO> dataprovider = new ListDataProvider<>(listTicketReportData);
					senderReport.datGrid_Request.setDataProvider(dataprovider, senderReport.resetPageInfo);

					if (senderReport.resetPageInfo)
					{
						PageInfoDTO pageInfo = reportResult.getPageInfoDTO();

						BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
						senderReport.pagingManager.setPages(bbrPage, senderReport.resetPageInfo);

					}
				}
				else
				{
					ListDataProvider<TicketReportDataResultDTO> dataprovider = new ListDataProvider<>(listTicketReportData);
					senderReport.datGrid_Request.setDataProvider(dataprovider, senderReport.resetPageInfo);
				}
				if (reportResult.getTickets().size() < 1)
				{
					senderReport.askToOpenFilter(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
							I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_FILTERS, "empty_filter_question"));
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

				ListDataProvider<TicketReportDataResultDTO> dataprovider = new ListDataProvider<>(listTicketReportData);
				senderReport.datGrid_Request.setDataProvider(dataprovider, senderReport.resetPageInfo);
			}

		}
		this.updateButtons(false);

		senderReport.stopWaiting(true);

	}

	private TicketReportResultDTO executeService(BbrUI bbrUI)
	{
		ScoreCardManagerServer loginClientPort;
		TicketReportResultDTO result = null;
		this.initParam = this.getTicketReportInitParamDTO();
		if (this.initParam != null)
		{

			Integer requestedPage = (!this.resetPageInfo && this.pagingManager.getCurrentPage() > 0) ? (Integer) this.pagingManager.getCurrentPage() : this.DEFAULT_PAGE_NUMBER;

			try
			{

				// Start Tracking
				this.getTimingMngr().startTimer();
				this.initParam.setPageNumber(requestedPage);
				this.initParam.setNeedPageInfo(this.resetPageInfo);
				this.initParam.setQueryToCount(false);
				this.initParam.setRows(this.MAX_ROWS_NUMBER);

				URL url = new URL(CustomerServiceConstants.getInstance().getScoreCardWebServiceEndpointPath());
				ScoreCardManagerServerService loginClient = new ScoreCardManagerServerService(url);
				loginClientPort = loginClient.getScoreCardManagerServerPort();
				result = loginClientPort.getTicketReport(this.initParam);
				EJBFactory.getCustomerEJBFactory().getCustomerManagerLocal(this.getBbrUIParent()).saveCompanySelectedAndAddCountUserProvider(this.getBbrUIParent().getUser().getId(), this.selectedCompanyData.getRut());
			}
			catch (MalformedURLException e)
			{
				e.printStackTrace();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				this.showErrorMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "error_webservice"));
			}
		}

		return result;
	}

	private TicketReportInitParamDTO getTicketReportInitParamDTO()
	{
		TicketReportInitParamDTO result = new TicketReportInitParamDTO();

		// Proveedor
		result.setCompanyrut(this.selectedCompanyData.getRut());

		// cliente
		result.setSiteid(selectedRequestClientReportFilterSectionInfo.getSitie().getId());

		// Solicitud
		result.setFilterTypeSol(this.selectedRequestReportFilterSectionInfo.getFilterType().getId());
		if (this.selectedRequestReportFilterSectionInfo.getFilterType().getId() == CustomerConstants.TYPE_REQUEST_FILTER_VALUE)
		{
			result.setServiceid(this.selectedRequestReportFilterSectionInfo.getService().getIds().toString());
		}
		else if (this.selectedRequestReportFilterSectionInfo.getFilterType().getId() == CustomerConstants.STATE_REQUEST_FILTER_VALUE)
		{
			result.setStatusid(this.selectedRequestReportFilterSectionInfo.getService().getIds().toString());
		}

		// Filtro
		result.setFilterType(this.selectedRequestReportPeriodFilterSectionInfo.getDateOption().getId());
		if (this.selectedRequestReportPeriodFilterSectionInfo.getDateOption().getId() == CustomerConstants.CREATION_DATE_FILTER_VALUE)
		{
			LocalDateTime startDate = this.selectedRequestReportPeriodFilterSectionInfo.getSelectedStartDate();
			LocalDateTime untilDate = this.selectedRequestReportPeriodFilterSectionInfo.getSelectedEndDate();

			result.setSince(startDate != null ? DateFormats.formatDateWs(startDate).toString() : "");
			result.setUntil(untilDate != null ? DateFormats.formatDateWs(untilDate).toString() : "");
		}
		else if (this.selectedRequestReportPeriodFilterSectionInfo.getDateOption().getId() == CustomerConstants.REQUEST_NUMBER_FILTER_VALUE)
		{
			result.setNumdoc(Long.valueOf(this.selectedRequestReportPeriodFilterSectionInfo.getSelectedRequestNumber().getValue()));
		}
		else if (this.selectedRequestReportPeriodFilterSectionInfo.getDateOption().getId() == CustomerConstants.REFERENCE_NUMBER_FILTER_VALUE)
		{
			result.setReference(this.selectedRequestReportPeriodFilterSectionInfo.getSelectedRefrencenumeber().getValue());
		}

		List<OrderCriteriaDTO> orders = result.getOrderBy();
		if (this.orderCriteria != null)
		{
			orders.addAll(Arrays.asList(this.orderCriteria));
		}
		return result;
	}

	private void updateSortOrderCriteria(List<GridSortOrder<TicketReportDataResultDTO>> sortOrderList)
	{
		if (sortOrderList != null && !sortOrderList.isEmpty())
		{
			ArrayList<OrderCriteriaDTO> resultList = new ArrayList<>();
			for (GridSortOrder<TicketReportDataResultDTO> sorOrder : sortOrderList)
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
			order.setAscending(true);
			this.orderCriteria = new OrderCriteriaDTO[] { order };

			GridSortOrder<TicketReportDataResultDTO> sortOrder = new GridSortOrder<>(datGrid_Request.getColumn(DEFAULT_SORT_FIELD), SortDirection.ASCENDING);
			sortOrderList = new ArrayList<GridSortOrder<TicketReportDataResultDTO>>();
			sortOrderList.add(sortOrder);

			this.datGrid_Request.setSortOrder(sortOrderList);
		}
	}

	private void dataGrid_sortHandler(SortEvent<GridSortOrder<TicketReportDataResultDTO>> e)
	{
		if (e.isUserOriginated() && pagingManager.getTotalsPages() > 1)
		{
			this.startWaiting();

			this.updateSortOrderCriteria(e.getSortOrder());
			this.resetPageInfo = true;
			this.executeBbrWork(reportWork);
		}
	}

	private void refreshReport_clickHandler(ClickEvent event)
	{
		this.startWaiting();
		this.resetPageInfo = false;
		this.executeBbrWork(reportWork);
	}

	private void pagingChange_Listener(BbrPagingEvent e)
	{
		if ((e != null) && e.getEventType().equals(BbrPagingEvent.PAGE_CHANGED) && (e.getResultObject() != null))
		{
			this.startWaiting();
			this.resetPageInfo = false;
			this.executeBbrWork(reportWork);
		}
	}

	private void initializeDataGridColumns()
	{
		this.datGrid_Request.addCustomColumn(TicketReportDataResultDTO::getNrosolicitud, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_request_number"), true)
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(200)
				.setId(NRO_SOLICITUD);

		this.datGrid_Request.addCustomColumn(TicketReportDataResultDTO::getTiposolicitud, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_request_type_number"), true)
				.setId(TIPO_SOLICITUD);

		this.datGrid_Request.addCustomColumn(TicketReportDataResultDTO::getCliente, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_request_client"), true)
				.setId(CLIENTE);

		this.datGrid_Request.addCustomColumn(i -> this.transformDate(i.getFechasolicitud()), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_request_until_date"), true)
				.setWidth(160)
				.setComparator((item1, item2) -> compareDate(item1.getFechasolicitud(), item2.getFechasolicitud()))
				.setId(FECHA_SOLICITUD);

		this.datGrid_Request.addCustomComponentColumn(i -> ((i.getReferencia() != null) && (i.getReferencia().contains(SPL))) ? this.getGridLinkButton(i, REFERENCIA) : this.getGridLabel(i, REFERENCIA),
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_request_reference_number"), true)
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue())
				.setId(REFERENCIA);
		
		this.datGrid_Request.addCustomColumn(TicketReportDataResultDTO::getEstado, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_request_state_request"), true)
		.setWidth(160)
		.setId(ESTADO);

		// carga automatica

		this.datGrid_Request.addCustomColumn(i -> this.transformDate(i.getFechaproceso()), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_date"), true)
				.setWidth(160)
				.setComparator((item1, item2) -> compareDate(item1.getFechaproceso(), item2.getFechaproceso()))
				.setStyleGenerator(item -> BbrAlignment.CENTER.getValue())
				.setId(FECHA_PROCESO);


		this.datGrid_Request.addCustomComponentColumn((item -> this.getGridLinkButton(item, ADJUNTO)),
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_request_attached"), true)
				.setStyleGenerator(item -> BbrAlignment.CENTER.getValue())
				.setWidth(100)
				.setId(ADJUNTO);

		// carga manual
		
		this.datGrid_Request.addCustomColumn(i -> this.transformDate(i.getFechaadjunto()), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_date"), true)
				.setWidth(160)
				.setComparator((item1, item2) -> compareDate(item1.getFechaadjunto(), item2.getFechaadjunto()))
				.setStyleGenerator(item -> BbrAlignment.CENTER.getValue())
				.setId(FECHA_PROCESO_MANUAL);

		this.datGrid_Request.addCustomComponentColumn((item -> this.getGridLinkButton(item, ADJUNTO2)),
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_request_attached"), true)
				.setStyleGenerator(item -> BbrAlignment.CENTER.getValue())
				.setWidth(100)
				.setId(ADJUNTO2);

		// Header Sotck
		HeaderRow headerRow = this.datGrid_Request.prependHeaderRow();
		
		HeaderCell othersCell = headerRow.join(NRO_SOLICITUD,TIPO_SOLICITUD,CLIENTE,FECHA_SOLICITUD,REFERENCIA, ESTADO);
		
		HeaderCell automaticCell = headerRow.join(FECHA_PROCESO, ADJUNTO);
		automaticCell
				.setText(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "automatic_upload"));

		HeaderCell manualCell = headerRow.join(FECHA_PROCESO_MANUAL, ADJUNTO2);
		manualCell.setText(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "manual_upload"));
		othersCell.setText("");
	}

	public String transformDate(XMLGregorianCalendar xmlGregorianCalendar)
	{
		if(xmlGregorianCalendar == null){
			return "";
		}
		LocalDateTime dateTime = LocalDateTime.of(xmlGregorianCalendar.getYear(), xmlGregorianCalendar.getMonth(), xmlGregorianCalendar.getDay(),
				xmlGregorianCalendar.getHour(), xmlGregorianCalendar.getMinute());
		String result = BbrDateUtils.getInstance().toShortDateTime(dateTime);
		return result;
	}

	public int compareDate(XMLGregorianCalendar xmlGregorianCalendar1, XMLGregorianCalendar xmlGregorianCalendar2)
	{
		int result = 1;

		if (xmlGregorianCalendar1 != null && xmlGregorianCalendar2 != null)
		{
			LocalDateTime dateTime1 = LocalDateTime.of(xmlGregorianCalendar1.getYear(), xmlGregorianCalendar1.getMonth(),
					xmlGregorianCalendar1.getDay(), xmlGregorianCalendar1.getHour(), xmlGregorianCalendar1.getMinute());
			LocalDateTime dateTime2 = LocalDateTime.of(xmlGregorianCalendar2.getYear(), xmlGregorianCalendar2.getMonth(),
					xmlGregorianCalendar2.getDay(), xmlGregorianCalendar2.getHour(), xmlGregorianCalendar2.getMinute());

			result = dateTime1.compareTo(dateTime2);

		}
		return result;
	}

	private Label getGridLabel(TicketReportDataResultDTO itemData, String columnID)
	{
		Label result = null;

		if (columnID.equals(REFERENCIA))
		{
			Field field = BbrUtils.getInstance().objectGetField(itemData, columnID);

			field.setAccessible(true);

			String value = "";
			try
			{
				value = (String) field.get(itemData);
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				e.printStackTrace();
			}

			if ((value != null) && (!value.isEmpty()))
			{

				result = new Label(value);
			}
		}

		return result;

	}

	private Button getGridLinkButton(TicketReportDataResultDTO itemData, String columnID)
	{
		Button result = null;

		if (itemData != null)
		{
			if (columnID.equals(ADJUNTO))
			{
				Field field = BbrUtils.getInstance().objectGetField(itemData, columnID);

				field.setAccessible(true);

				String value = "";
				try
				{
					value = (String) field.get(itemData);
				}
				catch (IllegalArgumentException | IllegalAccessException e)
				{
					e.printStackTrace();
				}

				if ((value != null) && (!value.isEmpty()))
				{
					result = new Button("", BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_DownloadAlternativeByGrilla.png"));
					result.addClickListener((ClickListener & Serializable) e -> this.linkButton_clickHandler(e));
					result.addStyleName("toolbar-button");

					result.setData(itemData);
				}
			}
			else if (columnID.equals(REFERENCIA))
			{
				Field field = BbrUtils.getInstance().objectGetField(itemData, columnID);

				field.setAccessible(true);

				String value = "";
				try
				{
					value = (String) field.get(itemData);
				}
				catch (IllegalArgumentException | IllegalAccessException e)
				{
					e.printStackTrace();
				}

				if ((value != null) && (!value.isEmpty()))
				{

					result = new Button(value);
					result.addClickListener((ClickListener & Serializable) e -> this.linkButtonReference_clickHandler(e));
					result.addStyleName("grid-link-button-blue");

					result.setData(itemData);
				}
			}else if (columnID.equals(ADJUNTO2)){
				Field field = BbrUtils.getInstance().objectGetField(itemData, columnID);

				field.setAccessible(true);

				String value = "";
				try
				{
					value = (String) field.get(itemData);
				}
				catch (IllegalArgumentException | IllegalAccessException e)
				{
					e.printStackTrace();
				}

				if ((value != null) && (!value.isEmpty()))
				{
					result = new Button("", BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_DownloadAlternativeByGrilla.png"));
					result.addClickListener((ClickListener & Serializable) e -> this.linkButtonAttachment_clickHandler(e));
					result.addStyleName("toolbar-button");

					result.setData(itemData);
				}
			}

		}

		return result;
	}

	private void linkButton_clickHandler(ClickEvent event)
	{
		TicketReportDataResultDTO itemData = ((event.getButton() != null) && (event.getButton().getData() instanceof TicketReportDataResultDTO)) ? (TicketReportDataResultDTO) event.getButton().getData() : null;
		String url = itemData.getAdjunto();
		UI.getCurrent().getPage().open(url, "_blank", true);
	}
	

	private void linkButtonAttachment_clickHandler(ClickEvent event)
	{
		TicketReportDataResultDTO itemData = ((event.getButton() != null) && (event.getButton().getData() instanceof TicketReportDataResultDTO)) ? (TicketReportDataResultDTO) event.getButton().getData() : null;
		String url = itemData.getAdjunto2();
		UI.getCurrent().getPage().open(url, "_blank", true);
	}
	

	private void updateButtons(Boolean isSelectionEvent)
	{
		this.btn_Refresh.setEnabled(initParam != null);
	}

	private void linkButtonReference_clickHandler(ClickEvent event)
	{
		if ((event.getButton() != null) && (event.getButton().getData() instanceof TicketReportDataResultDTO))
		{
			TicketReportDataResultDTO itemData = (TicketReportDataResultDTO) event.getButton().getData();
			this.viewRequestReportDetails(itemData);
		}

	}

	// private void btn_AvailableStockDetails_clickHandler(ClickEvent event)
	// {
	// if (this.selectesTicket != null)
	// {
	// this.viewRequestReportDetails(this.selectesTicket);
	// }
	// }
	//
	// private void dgdItem_clickHandler(ItemClick<TicketReportDataResultDTO> e)
	// {
	// if (e.getMouseEventDetails().isDoubleClick() && e.getItem() != null)
	// {
	// this.viewRequestReportDetails(e.getItem());
	// }
	// }

	private void viewRequestReportDetails(TicketReportDataResultDTO selectedItem)
	{
		if (selectedItem != null)
		{
			RequestReportDetails requestReportDetailsCtrl = new RequestReportDetails(this.getBbrUIParent(), selectedItem, this.selectedCompanyData);
			requestReportDetailsCtrl.open(true, true, this);
			requestReportDetailsCtrl.addBbrEventListener((BbrEventListener & Serializable) event -> this.refreshRequestReport());
		}
	}

	private void refreshRequestReport()
	{
		this.startWaiting();
		this.executeReportWork();
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
