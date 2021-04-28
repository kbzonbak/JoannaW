package bbr.b2b.portal.modules.logistic;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.SortEvent;
import com.vaadin.event.SortEvent.SortListener;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.ItemClickListener;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderReportDataDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderReportInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderReportResultDTO;
import bbr.b2b.logistic.buyorders.data.dto.ResendInitParamDTO;
import bbr.b2b.logistic.rest.client.CustomerLogisticRestFulWSClient;
import bbr.b2b.portal.classes.constants.BbrAppConstants;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.managers.FunctionalityMngr;
import bbr.b2b.portal.classes.styles.BbrStyles;
import bbr.b2b.portal.components.filters.logistic.PucharseOrdersFilter;
import bbr.b2b.portal.components.modules.logistic.PucharseOrderDetails;
import bbr.b2b.portal.components.modules.logistic.PucharseOrderHistoricalDetails;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.PortalConstants;
import bbr.b2b.portal.utils.PortalDates;
import cl.bbr.core.classes.basics.BbrPage;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.constants.BbrResources;
import cl.bbr.core.classes.constants.CoreConstants;
import cl.bbr.core.classes.constants.TrackingConstants;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.utilities.BbrDateUtils;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.utilities.NumericManager;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.paging.BbrPagingManager;

public class PucharseOrdersReport extends BbrModule implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// Constants
	private static final long						serialVersionUID		= -998406901515171430L;

	private static final String						ORDER_NUMBER			= "ordernumber";
	private static final String						ORDER_TYPE				= "ordertype";
	private static final String						SOA_STATE_TYPE			= "soastatetype";
	private static final String						CLIENTE					= "cliente";
	private static final String						EMITTED					= "emitted";
	private static final String						RECEPTION_DATE			= "receptiondate";
	private static final String						TOTAL_AMOUNT			= "totalamount";
	private static final String						TOTAL_UNITS				= "totalunits";
	private static final String						HISTORY					= "history";
	private final int								MAX_ROWS_NUMBER			= 100;
	// Components
	private BbrAdvancedGrid<OrderReportDataDTO>		datGrid_Orders			= null;
	private BbrPagingManager						pagingManager			= null;
	private Button									btn_Refresh				= null;
	private Button									btn_OrderDetails		= null;
	private Button									btn_ForwardOC			= null;

	// Variables
	private ArrayList<OrderReportDataDTO>			selectedItems			= null;
	private final String							DEFAULT_SORT_FIELD		= ORDER_NUMBER;
	private OrderReportInitParamDTO					initParam				= null;
	private BbrWork<OrderReportResultDTO>			reportWork				= null;
	private Boolean									trackReport				= true;
	private Boolean									resetPageInfo			= true;
	private Map<Integer, Integer>					selectedItemsMap		= new HashMap<>();
	private OrderCriteriaDTO[]						orderCriteria			= null;

	private BbrWork<BaseResultDTO>					doForwardOCWork	= null;
	private FunctionalityMngr						functionalityMngr		= null;
	private static CustomerLogisticRestFulWSClient	clientCustomerWS		= null;
	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public PucharseOrdersReport(BbrUI bbrUIParent, FunctionalityMngr functionalityMngr)
	{
		super(bbrUIParent);
		this.functionalityMngr = functionalityMngr;
		PucharseOrdersReport.clientCustomerWS = CustomerLogisticRestFulWSClient.getInstance(BbrAppConstants.CUSTOMER_URL);

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
		PucharseOrdersFilter filterLayout = new PucharseOrdersFilter(this);
		filterLayout.initializeView();
		this.setBbrFilterContainer(filterLayout);

		// Paging Manager
		this.pagingManager = new BbrPagingManager();
		this.pagingManager.setLocale(this.getUser().getLocale());
		this.pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> this.pagingChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);
		this.pagingManager.setTotalLabelFunction(new BiFunction<BbrPage, SelectionEvent<?>, String>()
		{
			@Override
			public String apply(BbrPage bbrPage, SelectionEvent<?> t)
			{
				String elementCount = I18NManager.getI18NString(PucharseOrdersReport.this.getBbrUIParent(), BbrResources.RES_PAGING, "totals_records") + " : " + bbrPage.getTotalRows();
				int selectedCount = 0;
				if (PucharseOrdersReport.this.datGrid_Orders != null)
				{
					selectedCount = PucharseOrdersReport.this.datGrid_Orders.getAllSelectedsItems().size();
				}
				if (t == null)
				{
					return elementCount + " (0)";
				}
				else
				{
					return elementCount + " (" + selectedCount + ")";
				}
			}
		});

		// Reporte: Barra de herramientas superior izq
		HorizontalLayout leftButtonsBar = new HorizontalLayout();
		leftButtonsBar.setSpacing(true);
		leftButtonsBar.setMargin(false);
		leftButtonsBar.setHeight("30px");
		leftButtonsBar.addStyleName(BbrStyles.TOOLBAR_LAYOUT);

		this.btn_OrderDetails = new Button("", EnumToolbarButton.SEARCH_PRIMARY.getResource());
		this.btn_OrderDetails.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "view_order_details"));
		this.btn_OrderDetails.addClickListener((ClickListener & Serializable) e -> this.btn_OrderDetails_clickHandler(e));
		this.btn_OrderDetails.addStyleName(BbrStyles.TOOLBAR_BUTTON);
		this.btn_OrderDetails.setEnabled(false);

		this.btn_ForwardOC = new Button("", EnumToolbarButton.ACTION_RIGHT_PRIMARY.getResource());
		this.btn_ForwardOC.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "view_forward_oc"));
		this.btn_ForwardOC.addClickListener((ClickListener & Serializable) e -> this.btn_DatingReport_clickHandler(e));
		this.btn_ForwardOC.addStyleName(BbrStyles.TOOLBAR_BUTTON);
		this.btn_ForwardOC.setEnabled(false);

		// Botones de acción de la izquierda (...)
		VerticalLayout cmp_ActionButtons = new VerticalLayout();
		cmp_ActionButtons.setMargin(new MarginInfo(false, true));
		cmp_ActionButtons.setSpacing(false);

		leftButtonsBar.addComponents(this.btn_OrderDetails, this.btn_ForwardOC);

		// Botones de descarga de la derecha (v)
		VerticalLayout cmp_DownloadButtons = new VerticalLayout();
		cmp_DownloadButtons.setMargin(new MarginInfo(false, true));
		cmp_DownloadButtons.setSpacing(false);

		this.btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
		this.btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
		this.btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler(e));
		this.btn_Refresh.addStyleName(BbrStyles.TOOLBAR_BUTTON);

		Button btn_Help = this.getHelpButton();

		HorizontalLayout rightButtonsBar = new HorizontalLayout();
		rightButtonsBar.addComponents(this.btn_Refresh, btn_Help);
		rightButtonsBar.setSpacing(true);
		rightButtonsBar.setMargin(false);
		rightButtonsBar.setHeight("30px");
		rightButtonsBar.addStyleName(BbrStyles.TOOLBAR_LAYOUT);

		rightButtonsBar.setComponentAlignment(btn_Refresh, Alignment.MIDDLE_RIGHT);

		HorizontalLayout toolBar = new HorizontalLayout();
		toolBar.setWidth("100%");
		toolBar.addComponents(leftButtonsBar, rightButtonsBar);
		toolBar.addStyleName("filter-toolbar");
		toolBar.setExpandRatio(leftButtonsBar, 1F);
		toolBar.setExpandRatio(rightButtonsBar, 1F);

		toolBar.setComponentAlignment(leftButtonsBar, Alignment.MIDDLE_LEFT);
		toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);

		// Reporte: Grilla
		this.datGrid_Orders = new BbrAdvancedGrid<>(this.getUser().getLocale());

		this.initializeDataGridColumns();

		this.datGrid_Orders.addStyleName("report-grid");
		this.datGrid_Orders.setSizeFull();
		this.datGrid_Orders.setSelectionMode(SelectionMode.MULTI);
		this.datGrid_Orders.setPagingManager(pagingManager, this.DEFAULT_SORT_FIELD);
		this.datGrid_Orders.addSelectionListener((SelectionListener<OrderReportDataDTO> & Serializable) e -> this.selection_gridHandler(e));
		this.datGrid_Orders.addSortListener((SortListener<GridSortOrder<OrderReportDataDTO>> & Serializable) e -> dataGrid_sortHandler(e));
		this.datGrid_Orders.addItemClickListener((ItemClickListener<OrderReportDataDTO> & Serializable) e -> this.dgdItem_clickHandler(e));

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addStyleName("report-layout");
		mainLayout.setSizeFull();
		mainLayout.setMargin(false);
		mainLayout.addComponents(toolBar, this.datGrid_Orders, pagingManager);
		mainLayout.setExpandRatio(this.datGrid_Orders, 1F);

		this.addComponents(mainLayout);

		this.updateButtons(false);
		this.updateSortOrderCriteria(null);

		reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		reportWork.addFunction(new Function<Object, OrderReportResultDTO>()
		{
			@Override
			public OrderReportResultDTO apply(Object t)
			{
				return executeService(PucharseOrdersReport.this.getBbrUIParent());
			}
		});

		doForwardOCWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		doForwardOCWork.addFunction(new Function<Object, BaseResultDTO>()
		{
			@Override
			public BaseResultDTO apply(Object t)
			{
				return executeForwardOCSevice(PucharseOrdersReport.this.getBbrUIParent());
			}
		});
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				if (result instanceof OrderReportResultDTO)
				{
					this.doUpdateReport(result, sender);
				}
				else if (result instanceof BaseResultDTO)
				{
					this.doForwardOC(result, sender);
				}
			}
		}
		else
		{
			PucharseOrdersReport senderReport = (PucharseOrdersReport) sender;

			this.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));

			senderReport.stopWaiting();
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	private void btn_OrderDetails_clickHandler(ClickEvent event)
	{
		ArrayList<OrderReportDataDTO> gridData = new ArrayList<>(this.datGrid_Orders.getAllSelectedsItems());
		if (!gridData.isEmpty())
		{
			OrderReportDataDTO selectedOrder = gridData.get(0);
			this.viewOrderDetails(selectedOrder);
		}
	}

	private void btn_DatingReport_clickHandler(ClickEvent event)
	{
		this.startWaiting();
		this.executeBbrWork(doForwardOCWork);
	}

	@Override
	public void filterApplied_handler(BbrFilterEvent event)
	{
		if ((event != null) && (event.getResultObject() != null))
		{
			this.initParam = (OrderReportInitParamDTO) event.getResultObject();
			this.trackReport = true;
			this.resetPageInfo = true;
			this.startWaiting();
			this.executeBbrWork(reportWork);
		}
	}

	private void pagingChange_Listener(BbrPagingEvent e)
	{
		if ((e != null) && e.getEventType().equals(BbrPagingEvent.PAGE_CHANGED) && (e.getResultObject() != null))
		{
			this.startWaiting();

			this.trackReport = false;
			this.resetPageInfo = false;
			this.executeBbrWork(reportWork);
		}
	}

	private void selection_gridHandler(SelectionEvent<OrderReportDataDTO> e)
	{
		// botones
		if (e.isUserOriginated())
		{
			this.selectedItems = this.datGrid_Orders.getAllSelectedsItems();
			this.selectedItemsMap.put(this.pagingManager.getCurrentPage(), selectedItems.size());
			if (e.getAllSelectedItems().isEmpty())
			{
				this.selectedItemsMap.remove(this.pagingManager.getCurrentPage());
			}
		}

		this.updateButtons(true);
	}

	private void dataGrid_sortHandler(SortEvent<GridSortOrder<OrderReportDataDTO>> e)
	{
		if (e.isUserOriginated() && pagingManager.getTotalsPages() > 1)
		{
			this.startWaiting();
			this.updateSortOrderCriteria(e.getSortOrder());
			this.trackReport = false;
			this.resetPageInfo = true;
			this.executeBbrWork(reportWork);
		}
	}

	private void refreshReport_clickHandler(ClickEvent event)
	{
		this.refreshGrid();
	}

	private void dgdItem_clickHandler(ItemClick<OrderReportDataDTO> e)
	{
		if (e.getMouseEventDetails().isDoubleClick() && e.getItem() != null)
		{
			this.viewOrderDetails(e.getItem());
		}
		this.updateButtons(true);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private void refreshGrid()
	{
		this.startWaiting();
		this.trackReport = false;
		this.resetPageInfo = false;
		this.executeBbrWork(reportWork);
	}

	private void viewOrderDetails(OrderReportDataDTO selectedOrder)
	{
		if (selectedOrder != null)
		{
			// Ventana - Detalle de Orden de Compra
			//
			PucharseOrderDetails pucharseOrderDetailsCtrl = new PucharseOrderDetails(this.getBbrUIParent(), selectedOrder, this.initParam, functionalityMngr);
			pucharseOrderDetailsCtrl.addBbrEventListener((BbrEventListener & Serializable) ev -> refreshAcctionForwardOC_updateHandler(ev));
			pucharseOrderDetailsCtrl.open(true, true);
		}
		else
		{
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "validate_selected_oc"));
		}
	}
	
	private void refreshAcctionForwardOC_updateHandler(BbrEvent bbrEvent)
	{
		if (bbrEvent != null && (bbrEvent.getEventType().equals(BbrEvent.ITEM_UPDATED)))
		{
			this.refreshGrid();
		}
	}

	private void initializeDataGridColumns()
	{
		// Número Orden
		this.datGrid_Orders.addCustomColumn(OrderReportDataDTO::getOrdernumber, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_order_number"), true)
				.setDescriptionGenerator(order -> String.valueOf(order.getOrdernumber()), ContentMode.TEXT)
				.setWidth(120)
				.setId(ORDER_NUMBER);

		// Tipo
		this.datGrid_Orders.addCustomColumn(OrderReportDataDTO::getOrdertype, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_type"), true)
				.setDescriptionGenerator(order -> order.getOrdertype(), ContentMode.TEXT)
				.setId(ORDER_TYPE);

		// Estado B2B Link
		this.datGrid_Orders.addCustomColumn(OrderReportDataDTO::getSoastatetype, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_type_B2B_link"), true)
				.setDescriptionGenerator(order -> order.getSoastatetype(), ContentMode.TEXT)
				.setId(SOA_STATE_TYPE);

		// Cliente
		this.datGrid_Orders.addCustomColumn(OrderReportDataDTO::getCliente, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_client"), true)
				.setDescriptionGenerator(order -> order.getCliente(), ContentMode.TEXT)
				.setId(CLIENTE);

		// Fecha Emisión
		this.datGrid_Orders.addCustomColumn(o -> this.transformDate(o.getEmitted()), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_oc_fecha_emision"), true)
				.setComparator((item1, item2) -> compareDate(item1.getEmitted(), item2.getEmitted()))
				.setStyleGenerator(item -> BbrAlignment.CENTER.getValue())
				.setWidth(120)
				.setId(EMITTED);

		// Fecha Recepcionb2b link
		this.datGrid_Orders.addCustomColumn(o -> this.transformDate(o.getReceptiondate()), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_oc_b2b_date"), true)
				.setComparator((item1, item2) -> compareDate(item1.getReceptiondate(), item2.getReceptiondate()))
				.setStyleGenerator(item -> BbrAlignment.CENTER.getValue())
				.setWidth(160)
				.setId(RECEPTION_DATE);

		// Solicitado $
		this.datGrid_Orders.addCustomColumn(OrderReportDataDTO::getTotalamount, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_requested_amount_short"), true)
				.setDescriptionGenerator(order -> String.valueOf(order.getTotalamount()), ContentMode.TEXT)
				.setRenderer(NumericManager.getNumberRenderer(1))
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setId(TOTAL_AMOUNT);

		// Solicitado Q
		this.datGrid_Orders.addCustomColumn(OrderReportDataDTO::getTotalunits, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_requested_units_short"), true)
				.setDescriptionGenerator(order -> String.valueOf(order.getTotalunits()), ContentMode.TEXT)
				.setRenderer(NumericManager.getNumberRenderer(1))
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setId(TOTAL_UNITS);

		// Historico
		this.datGrid_Orders.addCustomComponentColumn((item -> this.getGridLinkButton(item, HISTORY)),
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_historico"), true)
				.setStyleGenerator(item -> BbrAlignment.CENTER.getValue())
				.setWidth(100)
				.setId(HISTORY);
	}

	public String transformDate(String date)
	{
		LocalDateTime dateTime = PortalDates.getLocalDateTimeformatYYYYMMDD(date);
		String result = BbrDateUtils.getInstance().toShortDate(dateTime);
		return result;
	}

	public int compareDate(String date1, String date2)
	{
		int result = 0;

		if (!date1.isEmpty() && !date2.isEmpty())
		{
			LocalDateTime dateTime1 = PortalDates.getLocalDateTimeformatYYYYMMDD(date1);
			LocalDateTime dateTime2 = PortalDates.getLocalDateTimeformatYYYYMMDD(date2);

			result = dateTime1.compareTo(dateTime2);

		}
		return result;
	}

	private Button getGridLinkButton(OrderReportDataDTO itemData, String columnID)
	{
		Button result = null;

		if (itemData != null)
		{
			Field field = BbrUtils.getInstance().objectGetField(itemData, columnID);

			field.setAccessible(true);

			boolean history = false;
			try
			{
				history = (boolean) field.get(itemData);
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				e.printStackTrace();
			}

			if (history)
			{
				result = new Button("", BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_Add.png"));
				result.addClickListener((ClickListener & Serializable) e -> this.linkButton_clickHandler(e));
				result.addStyleName("toolbar-button");

				result.setData(itemData);
			}
		}

		return result;
	}

	private void linkButton_clickHandler(ClickEvent event)
	{
		OrderReportDataDTO itemData = ((event.getButton() != null) && (event.getButton().getData() instanceof OrderReportDataDTO)) ? (OrderReportDataDTO) event.getButton().getData() : null;

		if (itemData != null)
		{
			PucharseOrderHistoricalDetails pucharseHistoricalOrderDetailsCtrl = new PucharseOrderHistoricalDetails(this.getBbrUIParent(), itemData, this.initParam, functionalityMngr);
			pucharseHistoricalOrderDetailsCtrl.open(true, true);
		}
		else
		{
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "validate_selected_oc"));
		}
	}

	private void updateButtons(Boolean isSelectionEvent)
	{
		if (this.datGrid_Orders != null)
		{
			boolean isEnable = ((this.selectedItems != null) && (this.selectedItems.size() > 0));
			this.btn_OrderDetails.setEnabled(isEnable);
			this.btn_ForwardOC.setEnabled(isEnable);

			this.btn_Refresh.setEnabled(initParam != null);
		}
	}

	private void updateSortOrderCriteria(List<GridSortOrder<OrderReportDataDTO>> sortOrderList)
	{
		if (sortOrderList != null && !sortOrderList.isEmpty())
		{
			ArrayList<OrderCriteriaDTO> resultList = new ArrayList<>();

			for (GridSortOrder<OrderReportDataDTO> sortOrder : sortOrderList)
			{
				OrderCriteriaDTO order = new OrderCriteriaDTO();
				order.setPropertyname(sortOrder.getSorted().getId().toUpperCase());
				order.setAscending(sortOrder.getDirection() == SortDirection.ASCENDING);
				resultList.add(order);
			}
			this.orderCriteria = resultList.toArray(new OrderCriteriaDTO[resultList.size()]);
		}
		else
		{
			OrderCriteriaDTO order = new OrderCriteriaDTO();
			order.setPropertyname(DEFAULT_SORT_FIELD.toUpperCase());
			order.setAscending(false);
			GridSortOrder<OrderReportDataDTO> sortOrder = new GridSortOrder<>(this.datGrid_Orders.getColumn(DEFAULT_SORT_FIELD), SortDirection.DESCENDING);
			sortOrderList = new ArrayList<>();
			sortOrderList.add(sortOrder);
			this.datGrid_Orders.setSortOrder(sortOrderList);
		}
	}

	private OrderReportResultDTO executeService(BbrUI bbrUI)
	{
		Integer requestedPage = (!this.resetPageInfo && this.pagingManager.getCurrentPage() > 0) ? (Integer) this.pagingManager.getCurrentPage()
				: PortalConstants.DEFAULT_PAGE_NUMBER;
		OrderReportResultDTO result = null;
		if (this.initParam != null)
		{
			try
			{
				this.getTimingMngr().startTimer();
				this.initParam.setPageNumber(requestedPage);
				this.initParam.setOrderby(this.orderCriteria);
				this.initParam.setByfilter(true);
				this.initParam.setRows(this.MAX_ROWS_NUMBER);
				result = PucharseOrdersReport.clientCustomerWS.getOrderReportByVendorAndFilterWS(this.initParam);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return result;
	}

	private BaseResultDTO executeForwardOCSevice(BbrUI bbrUIParent)
	{
		BaseResultDTO result = null;
		if (this.initParam != null)
		{
			try
			{
				List<String> ocnumbers = this.getNumberOrders();
				if (ocnumbers != null && ocnumbers.size() > 0)
				{
					ResendInitParamDTO initParamResend = new ResendInitParamDTO();

					initParamResend.setOcnumbers(ocnumbers);

					result = (BaseResultDTO) PucharseOrdersReport.clientCustomerWS.doResendOCWS(initParamResend);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return result;
	}

	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{
		String errordescription = "";

		PucharseOrdersReport senderReport = (PucharseOrdersReport) sender;

		if (result != null)
		{
			OrderReportResultDTO reportResult = (OrderReportResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), true);

			if (!error.hasError())
			{
				ListDataProvider<OrderReportDataDTO> dataprovider = new ListDataProvider<>(Arrays.asList(reportResult.getOrders()));
				senderReport.datGrid_Orders.setDataProvider(dataprovider, senderReport.resetPageInfo);
				senderReport.updateButtons(false);

				if ((senderReport.resetPageInfo) && (reportResult.getPageInfo() != null))
				{
					PageInfoDTO pageInfo = reportResult.getPageInfo();
					BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
					senderReport.pagingManager.setPages(bbrPage, senderReport.resetPageInfo);
				}

				// End Tracking
				if (senderReport.trackReport == true)
				{
					senderReport.trackEvent(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription());
				}

				if (reportResult.getOrders().length < 1)
				{
					senderReport.askToOpenFilter(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
							I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_FILTERS, "empty_filter_question"));
				}
			}
			else
			{
				errordescription = BbrUtils.getInstance().substitute("{0} - {1} - Internal Error", error.getErrorCode(), error.getErrorMessage());
			}
		}

		if (errordescription.length() > 0 && senderReport.trackReport == true)
		{
			// Track Error
			senderReport.trackError(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription(), errordescription, null, this);
		}

		senderReport.stopWaiting();
	}

	private void doForwardOC(Object result, BbrWorkExecutor sender)
	{
		PucharseOrdersReport senderReport = (PucharseOrdersReport) sender;

		if (result != null)
		{
			BaseResultDTO reportResult = (BaseResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), false);

			if (!error.hasError())
			{
				String successful_operation=  I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "successful_operation");
				String fullMessage = successful_operation + " " + reportResult.getStatusmessage() + ".";
				this.showInfoMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "win_title_info"),
						fullMessage,"100%",
						() -> this.btn_Acction_ClickHandler());
			}
		}

		senderReport.stopWaiting();
	}

	private void btn_Acction_ClickHandler()
	{
		this.refreshGrid();
	}
	
	private List<String> getNumberOrders()
	{
		List<String> ocnumbers = null;

		if (!this.datGrid_Orders.getSelectedItems().isEmpty() && this.datGrid_Orders.getSelectedItems().size() > 0)
		{
			ocnumbers = new ArrayList<>();

			ArrayList<OrderReportDataDTO> selectedOrders = new ArrayList<>(this.datGrid_Orders.getSelectedItems());
			if (selectedOrders != null)
			{
				ocnumbers = selectedOrders.stream().map(oc -> oc.getOrdernumber().toString()).collect(Collectors.toList());
			}
		}

		return ocnumbers;
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
