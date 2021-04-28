package bbr.b2b.portal.components.modules.logistic;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterCell;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.renderers.NumberRenderer;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.common.util.NumberUtils;
import bbr.b2b.logistic.buyorders.data.dto.HeaderOrderDetailDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderDetailReportInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderDetailReportResultDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderPreDeliveryDetailDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderProductDetailDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderReportDataDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderReportInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.ResendInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.TotalPredeliveryDTO;
import bbr.b2b.logistic.buyorders.data.dto.TotalProductsDTO;
import bbr.b2b.logistic.rest.client.CustomerLogisticRestFulWSClient;
import bbr.b2b.portal.classes.constants.BbrAppConstants;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.i18n.HasI18n;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.managers.FunctionalityMngr;
import bbr.b2b.portal.classes.styles.BbrStyles;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.PortalConstants;
import cl.bbr.core.classes.basics.BbrPage;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.utilities.NumericManager;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrTabContent;
import cl.bbr.core.components.basics.BbrTabSheet;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrVSeparator;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.paging.BbrPagingManager;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class PucharseOrderDetails extends BbrWindow implements BbrWorkExecutor, HasI18n
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// Constants
	private static final long							serialVersionUID				= 631911393053940795L;

	private static final DateTimeFormatter				DD_MM_YYYY						= DateTimeFormatter.ofPattern("dd-MM-yyyy");

	// TAB - Detalle OrderDetailReportDataDTO
	//
	private static final String							OC_NUMBER						= "ocnumber";
	private static final String							POS								= "pos";
	private static final String							COD_PRODUCTO					= "codproducto";
	private static final String							COD_PROD_PROVEEDOR				= "codprodproveedor";
	private static final String							DESCRIPCION						= "descripcion";
	private static final String							UMC								= "umc";
	private static final String							UMBXUMC							= "umbxumc";
	private static final String							COSTO_UNITARIO					= "costounitario";
	private static final String							SOLICITADO						= "solicitado";
	private static final String							COSTO_FINAL						= "costofinal";

	// TAB Predistribución OrderPreDeliveryDetailDTO
	//
	private static final String							DISTR_COD_LOCAL					= "codlocal";
	private static final String							DISTR_NOMBRE_LOCAL				= "nombrelocal";
	private static final String							DISTR_COD_PRODUCTO				= "codproducto";
	private static final String							DISTR_COD_PROD_PROVEEDOR		= "codprodproveedor";
	private static final String							DISTR_DESCRIPCION				= "descripcion";
	private static final String							DISTR_SOLICITADO				= "solicitado";

	// Components
	private Button										btn_ForwardOC					= null;

	private BbrAdvancedGrid<OrderProductDetailDTO>		datGrid_OrderDetails			= null;
	private BbrAdvancedGrid<OrderPreDeliveryDetailDTO>	datGrid_OrderPredistributed		= null;
	private BbrPagingManager							detailsPagingManager			= null;
	private BbrTabSheet									tabNav_OrderDetails				= null;
	private FooterRow									footer_OrderDetailsDetails		= null;
	private FooterRow									footer_PredistributedDetails	= null;
	private BbrTabContent								tabCont_Details					= null;
	private BbrTabContent								tabCont_PreDistributed			= null;

	// Header
	private BbrTextField								txt_Client						= null;
	private BbrTextField								txt_EmittedDate					= null;
	private BbrTextField								txt_NumberOrder					= null;

	private BbrTextField								txt_TypeOC						= null;
	private BbrTextField								txt_OrderStatus					= null;

	private BbrTextField								txt_ReceptionDate				= null;
	private BbrTextField								txt_LocalDelivery				= null;
	private Label										lbl_Parcial						= new Label();

	// Variables
	private final int									MAX_ROWS_NUMBER					= 20;
	private OrderDetailReportInitParamDTO				initParamOrderDetail			= null;
	private final String								DEFAULT_SORT_FIELD				= POS;
	private BbrWork<OrderDetailReportResultDTO>			detailsWork						= null;
	private BbrWork<BaseResultDTO>						doForwardOCWork					= null;
	private OrderReportDataDTO							selectedOrder					= null;
	private boolean										resetPageDetailsInfo			= false;
	private boolean										resetPagePreDistributedInfo		= false;

	private BbrUI										parentUI						= null;

	private OrderReportInitParamDTO						initParamReportDTO				= null;
	private static CustomerLogisticRestFulWSClient		clientCustomerWS				= null;
	// ==============================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public PucharseOrderDetails(BbrUI parentUI, OrderReportDataDTO selectedOrder, OrderReportInitParamDTO initParamReportDTO, FunctionalityMngr functionalityMngr)
	{
		super(parentUI);
		this.parentUI = parentUI;
		this.selectedOrder = selectedOrder;
		this.initParamReportDTO = initParamReportDTO;
		PucharseOrderDetails.clientCustomerWS = CustomerLogisticRestFulWSClient.getInstance(BbrAppConstants.CUSTOMER_URL);
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
		// Paging Manager
		this.detailsPagingManager = new BbrPagingManager();
		this.detailsPagingManager.setLocale(this.getUser().getLocale());
		this.detailsPagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> this.pagingChangeDetails_Listener(e), BbrPagingEvent.PAGE_CHANGED);

		// Sección Header
		VerticalLayout headerLabels = this.getOrderHeaderInfo(selectedOrder);
		headerLabels.setMargin(false);

		HorizontalLayout leftButtonsProductsBar = new HorizontalLayout();
		leftButtonsProductsBar.setSpacing(false);
		leftButtonsProductsBar.setMargin(false);
		leftButtonsProductsBar.setHeight("30px");
		leftButtonsProductsBar.addStyleName(BbrStyles.TOOLBAR_LAYOUT);

		this.btn_ForwardOC = new Button("", EnumToolbarButton.ACTION_RIGHT_PRIMARY.getResource());
		this.btn_ForwardOC.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "view_forward_oc"));
		this.btn_ForwardOC.addClickListener((ClickListener & Serializable) e -> this.btn_ForwardOC_clickHandler(e));
		this.btn_ForwardOC.addStyleName(BbrStyles.TOOLBAR_BUTTON);
		this.btn_ForwardOC.setEnabled(false);

		HorizontalLayout rightButtonsBar = new HorizontalLayout();
		rightButtonsBar.addComponents(this.btn_ForwardOC);
		rightButtonsBar.setSpacing(true);
		rightButtonsBar.setMargin(false);
		rightButtonsBar.setHeight("30px");
		rightButtonsBar.addStyleName(BbrStyles.TOOLBAR_LAYOUT);

		HorizontalLayout toolBar = new HorizontalLayout();
		toolBar.setWidth("100%");
		toolBar.addComponents(rightButtonsBar);
		toolBar.addStyleName("filter-toolbar");
		toolBar.setExpandRatio(rightButtonsBar, 1F);

		toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);

		// Sección DataGrid
		// -> Detalles
		this.datGrid_OrderDetails = new BbrAdvancedGrid<>(this.getBbrUIParent().getUser().getLocale());
		this.datGrid_OrderDetails.setSortable(false);

		this.initializeDetailsDataGridColumns();
		this.initializeDetailsSummaryGridColumns();

		this.datGrid_OrderDetails.addStyleName("report-grid");
		this.datGrid_OrderDetails.setSizeFull();
		this.datGrid_OrderDetails.setPagingManager(detailsPagingManager, this.DEFAULT_SORT_FIELD);
		this.datGrid_OrderDetails.setSelectionMode(SelectionMode.SINGLE);

		this.tabNav_OrderDetails = new BbrTabSheet();
		this.tabNav_OrderDetails.setSizeFull();
		this.tabNav_OrderDetails.addStyleName("main-tab-sheet");

		this.tabCont_Details = new BbrTabContent();
		this.tabCont_Details.addComponents(this.datGrid_OrderDetails, this.detailsPagingManager, new BbrVSeparator("5px"));
		this.tabCont_Details.setExpandRatio(this.datGrid_OrderDetails, 1F);
		this.tabCont_Details.setExpandRatio(this.detailsPagingManager, 0.1F);
		this.tabCont_Details.setSizeFull();
		this.tabNav_OrderDetails.addBbrTab(tabCont_Details, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "tab_oc"), false);

		// -> Predistribuida -> Se crea solo si el tipo de orden de compra
		// es predistribuida
		this.getGridPredistributed();

		// Main LayoutreportRow,
		VerticalLayout mainLayout = new VerticalLayout(toolBar, headerLabels, tabNav_OrderDetails);
		mainLayout.setSizeFull();
		mainLayout.setSpacing(true);
		mainLayout.setExpandRatio(this.tabNav_OrderDetails, 1F);
		mainLayout.addStyleName("bbr-margin-windows");
		mainLayout.addStyleName("bbr-module-top");

		this.updateSortOrderCriteria(null);

		this.setHeight(600, Unit.PIXELS);
		this.setWidth(1100, Unit.PIXELS);
		this.setResizable(true);
		this.setResponsive(true);
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "title_order_details_window"));
		this.setContent(mainLayout);

		this.initializeOrderDetailsInitParam();

		detailsWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		detailsWork.addFunction(new Function<Object, OrderDetailReportResultDTO>()
		{
			@Override
			public OrderDetailReportResultDTO apply(Object t)
			{
				return executeService(PucharseOrderDetails.this.getBbrUIParent());
			}
		});

		doForwardOCWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		doForwardOCWork.addFunction(new Function<Object, BaseResultDTO>()
		{
			@Override
			public BaseResultDTO apply(Object t)
			{
				return executeForwardOCSevice(PucharseOrderDetails.this.getBbrUIParent());
			}
		});

		this.startWaiting(1);

		this.resetPageDetailsInfo = true;
		this.resetPagePreDistributedInfo = true;

		this.executeBbrWork(detailsWork);
	}

	public void open(boolean isModal, boolean executeInizializate)
	{
		super.open(isModal);
		if (executeInizializate)
		{
			this.initializeView();
		}
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		PucharseOrderDetails senderReport = (PucharseOrderDetails) sender;

		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				if (result instanceof OrderDetailReportResultDTO)
				{
					this.doUpdateReport(result, sender);
					this.doUpdateReportPredelivery(result, sender);
				}
				else if (result instanceof BaseResultDTO)
				{
					this.doForwardOC(result, sender);
				}
			}
		}
		else
		{
			this.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));

			senderReport.stopWaiting();
		}
	}

	@Override
	public String getI18n(String resource)
	{
		String result = "";

		if (resource != null)
		{
			result = I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_LOGISTIC, resource);
		}

		return result;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	private void doForwardOC(Object result, BbrWorkExecutor sender)
	{
		PucharseOrderDetails senderReport = (PucharseOrderDetails) sender;

		if (result != null)
		{
			BaseResultDTO reportResult = (BaseResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), false);

			if (!error.hasError())
			{
				String successful_operation = I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "successful_operation");
				String fullMessage = successful_operation + " " + reportResult.getStatusmessage() + ".";
				this.showInfoMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "win_title_info"),
						fullMessage, "100%",
						() -> this.btn_Acction_ClickHandler());
			}
		}

		senderReport.stopWaiting();
	}

	private void btn_Acction_ClickHandler()
	{
		BbrEvent bbrEvent = new BbrEvent(BbrEvent.ITEM_UPDATED);
		PucharseOrderDetails.this.dispatchBbrEvent(bbrEvent);
		this.close();
	}

	private void updateButtons(Boolean isSelectionEvent)
	{
		if (this.selectedOrder.getValid())
		{
			this.btn_ForwardOC.setEnabled(true);
		}
	}

	private void pagingChangeDetails_Listener(BbrPagingEvent e)
	{
		if ((e != null) && e.getEventType().equals(BbrPagingEvent.PAGE_CHANGED) && (e.getResultObject() != null))
		{
			this.startWaiting();
			this.resetPageDetailsInfo = false;
			this.executeBbrWork(detailsWork);
		}
	}

	private void updateSortOrderCriteria(List<GridSortOrder<OrderProductDetailDTO>> sortOrderList)
	{
		if (sortOrderList != null && !sortOrderList.isEmpty())
		{
			ArrayList<OrderCriteriaDTO> resultList = new ArrayList<>();
			for (GridSortOrder<OrderProductDetailDTO> sorOrder : sortOrderList)
			{
				OrderCriteriaDTO order = new OrderCriteriaDTO();
				order.setPropertyname(sorOrder.getSorted().getId().toUpperCase());
				order.setAscending(sorOrder.getDirection() == SortDirection.ASCENDING);
				resultList.add(order);
			}
			resultList.toArray(new OrderCriteriaDTO[resultList.size()]);
		}
		else
		{
			OrderCriteriaDTO order = new OrderCriteriaDTO();
			order.setPropertyname(DEFAULT_SORT_FIELD.toUpperCase());
			order.setAscending(true);
			GridSortOrder<OrderProductDetailDTO> sortOrder = new GridSortOrder<>(datGrid_OrderDetails.getColumn(DEFAULT_SORT_FIELD), SortDirection.ASCENDING);
			sortOrderList = new ArrayList<GridSortOrder<OrderProductDetailDTO>>();
			sortOrderList.add(sortOrder);
			this.datGrid_OrderDetails.setSortOrder(sortOrderList);
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private void getGridPredistributed()
	{

		// Reporte -> Grilla Predistribución -> TabPredistribución
		this.datGrid_OrderPredistributed = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.datGrid_OrderPredistributed.setSortable(false);

		this.initializePredistributeDataGridColumns();
		this.initializePredistributeDetailsSummaryGridColumns();

		this.datGrid_OrderPredistributed.addStyleName("report-grid");
		this.datGrid_OrderPredistributed.setSizeFull();
		this.datGrid_OrderPredistributed.setSortable(false);
		this.datGrid_OrderPredistributed.setSelectionMode(SelectionMode.SINGLE);

		// Tab -> Predistribución
		this.tabCont_PreDistributed = new BbrTabContent();
		this.tabCont_PreDistributed.addComponents(this.datGrid_OrderPredistributed, new BbrVSeparator("5px"));
		this.tabCont_PreDistributed.setExpandRatio(this.datGrid_OrderPredistributed, 1F);
		this.tabCont_PreDistributed.setSizeFull();
		this.tabNav_OrderDetails.addBbrTab(this.tabCont_PreDistributed, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "tab_cita_asignada"), false);
	}

	private VerticalLayout getOrderHeaderInfo(OrderReportDataDTO order)
	{
		///////////////////// ONE ROW /////////////////////
		// -> Numero Orden
		this.txt_NumberOrder = this.getInfoTextField("number_order", 99F, true);
		this.txt_NumberOrder.setValue("");
		this.txt_NumberOrder.setReadOnly(true);

		FormLayout frmNumberOrder = this.embedIntoForm(this.txt_NumberOrder);

		// -> Estado
		this.txt_OrderStatus = this.getInfoTextField("status", 83F, true);
		this.txt_OrderStatus.setValue("");
		this.txt_OrderStatus.setReadOnly(true);

		FormLayout frmOrderStatus = this.embedIntoForm(this.txt_OrderStatus);

		// -> Fecha Emision
		this.txt_EmittedDate = this.getInfoTextField("emitted_date", 100F, true);
		this.txt_EmittedDate.setValue("");
		this.txt_EmittedDate.setReadOnly(true);
		FormLayout frmEmittedDate = this.embedIntoForm(this.txt_EmittedDate);

		HorizontalLayout OneRowLayout = new HorizontalLayout(frmNumberOrder, frmOrderStatus, frmEmittedDate);
		OneRowLayout.setMargin(false);
		OneRowLayout.setWidth("100%");
		OneRowLayout.setHeight("30px");

		///////////////////// TWO ROW /////////////////////

		// -> tipo
		this.txt_TypeOC = this.getInfoTextField("type", 75F, true);
		this.txt_TypeOC.setValue("");
		this.txt_TypeOC.setReadOnly(true);

		FormLayout frmType = this.embedIntoForm(this.txt_TypeOC);

		// -> Comprador
		this.txt_Client = this.getInfoTextField("client", 83F, true);
		this.txt_Client.setValue("");
		this.txt_Client.setReadOnly(true);

		FormLayout frmClient = this.embedIntoForm(this.txt_Client);

		// -> Fecha Entrega
		this.txt_ReceptionDate = this.getInfoTextField("reception_date", 107F, true);
		this.txt_ReceptionDate.setValue("");
		this.txt_ReceptionDate.setReadOnly(true);
		FormLayout frmReceptionDate = this.embedIntoForm(this.txt_ReceptionDate);

		HorizontalLayout TwoRowLayout = new HorizontalLayout(frmType, frmClient, frmReceptionDate);
		TwoRowLayout.setMargin(false);
		TwoRowLayout.setWidth("100%");
		TwoRowLayout.setHeight("30px");

		///////////////////// THREE ROW /////////////////////
		// -> Lugar de Entrega

		this.txt_LocalDelivery = this.getInfoTextField("delivery_location", 102F, true);
		this.txt_LocalDelivery.setValue("");
		this.txt_LocalDelivery.setReadOnly(true);
		FormLayout frmLocalDelivery = this.embedIntoForm(this.txt_LocalDelivery);

		HorizontalLayout hLayoutBar = new HorizontalLayout();
		hLayoutBar.setSpacing(true);
		hLayoutBar.setMargin(false);
		hLayoutBar.setHeight("30px");
		hLayoutBar.addStyleName("toolbar-layout");

		this.lbl_Parcial.setCaption("");

		// this.lbl_Parcial.addStyleName("lbl_red_underline");
		this.lbl_Parcial.setStyleName("lbl_red_underline");

		hLayoutBar.addComponent(this.lbl_Parcial);
		hLayoutBar.setComponentAlignment(lbl_Parcial, Alignment.TOP_CENTER);

		if (!order.getComplete())
		{
			this.lbl_Parcial.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "label_parcial_message"));
		}

		HorizontalLayout ThreeRowLayout = null;
		ThreeRowLayout = new HorizontalLayout(frmLocalDelivery, hLayoutBar);
		ThreeRowLayout.setMargin(false);
		ThreeRowLayout.setWidth("100%");
		ThreeRowLayout.setHeight("30px");

		////////////////////// ADD ROWS TO VERTICAL LAYOUT
		////////////////////// //////////////////////////

		VerticalLayout result = new VerticalLayout(OneRowLayout, TwoRowLayout, ThreeRowLayout);
		result.setSpacing(true);
		result.setMargin(false);
		result.setWidth("100%");

		return result;
	}

	private void setOrderHeaderInfo(HeaderOrderDetailDTO headerdetail)
	{
		if (headerdetail != null)
		{
			this.txt_NumberOrder.setValue(String.valueOf(headerdetail.getOcnumber()));
			this.txt_OrderStatus.setValue(String.valueOf(headerdetail.getState()));
			this.txt_ReceptionDate.setValue(headerdetail.getReceptiondate());
			this.txt_Client.setValue(
					(headerdetail.getClientname() != null) ? headerdetail.getClientname() : "");
			this.txt_TypeOC.setValue(String.valueOf(headerdetail.getType()));
			this.txt_EmittedDate.setValue(headerdetail.getEmitted());
			this.txt_LocalDelivery.setValue(
					((headerdetail.getDeliverylocation() != null) && (headerdetail.getDeliverylocation().length() > 0)) ? headerdetail.getDeliverylocation() : "");
		}

	}

	private void initializeDetailsDataGridColumns()
	{
		// Pos.
		this.datGrid_OrderDetails.addCustomColumn(OrderProductDetailDTO::getPos, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_oc_pos"), true)
				.setDescriptionGenerator(oc -> NumberUtils.formatDouble(oc.getPos()), ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(80)
				.setId(POS);

		// Código Producto
		this.datGrid_OrderDetails
				.addCustomColumn(OrderProductDetailDTO::getCodproducto, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_oc_cod_prod"), true)
				.setDescriptionGenerator(item -> String.valueOf(item.getCodproducto()), ContentMode.TEXT)
				.setWidth(105)
				.setId(COD_PRODUCTO);

		// Código Prov
		this.datGrid_OrderDetails
				.addCustomColumn(OrderProductDetailDTO::getCodprodproveedor, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_oc_cod_prov"), true)
				.setDescriptionGenerator(item -> String.valueOf(item.getCodprodproveedor()), ContentMode.TEXT)
				.setWidth(105)
				.setId(COD_PROD_PROVEEDOR);

		// Descripcion
		this.datGrid_OrderDetails.addCustomColumn(OrderProductDetailDTO::getDescripcion, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_oc_descrption"), true)
				.setDescriptionGenerator(item -> String.valueOf(item.getDescripcion()), ContentMode.TEXT)
				.setId(DESCRIPCION);

		// UMC
		this.datGrid_OrderDetails.addCustomColumn(OrderProductDetailDTO::getUmc, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_oc_umc"), true)
				.setDescriptionGenerator(item -> String.valueOf(item.getUmc()), ContentMode.TEXT)
				.setWidth(90)
				.setId(UMC);

		// UMB x UMC
		this.datGrid_OrderDetails.addCustomColumn(OrderProductDetailDTO::getUmbxumc, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_oc_umbxumc"), true)
				.setDescriptionGenerator(item -> String.valueOf(item.getUmbxumc()), ContentMode.TEXT)
				.setWidth(100)
				.setId(UMBXUMC);

		// Costo\nUnitario
		this.datGrid_OrderDetails.addCustomColumn(OrderProductDetailDTO::getCostounitario, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_oc_costo_unitario"), true)
				.setDescriptionGenerator(order -> String.valueOf(order.getCostounitario()), ContentMode.TEXT)
				.setRenderer(NumericManager.getNumberRenderer(1))
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(95)
				.setId(COSTO_UNITARIO);

		// Solicitado Q
		this.datGrid_OrderDetails.addCustomColumn(OrderProductDetailDTO::getSolicitado, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_requested_units_short"), true)
				.setDescriptionGenerator(order -> String.valueOf(order.getSolicitado()), ContentMode.TEXT)
				.setRenderer(NumericManager.getNumberRenderer(1))
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(90)
				.setId(SOLICITADO);

		// CostoTotal
		this.datGrid_OrderDetails.addCustomColumn(OrderProductDetailDTO::getCostofinal, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_oc_costo_total"), true)
				.setDescriptionGenerator(order -> String.valueOf(order.getCostofinal()), ContentMode.TEXT)
				.setRenderer(NumericManager.getNumberRenderer(1))
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(105)
				.setId(COSTO_FINAL);
	}

	private void initializePredistributeDataGridColumns()
	{
		// Cód Local
		this.datGrid_OrderPredistributed.addCustomColumn(OrderPreDeliveryDetailDTO::getCodlocal, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_oc_cod_local"), true)
				.setDescriptionGenerator(item -> String.valueOf(item.getCodlocal()), ContentMode.TEXT)
				.setId(DISTR_COD_LOCAL);

		// Cód Local
		this.datGrid_OrderPredistributed.addCustomColumn(OrderPreDeliveryDetailDTO::getNombrelocal, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_oc_nombre_local"), true)
				.setDescriptionGenerator(item -> String.valueOf(item.getNombrelocal()), ContentMode.TEXT)
				.setId(DISTR_NOMBRE_LOCAL);

		//// Cód Producto
		this.datGrid_OrderPredistributed.addCustomColumn(item -> AppUtils.getInstance().getFormattedDate(item.getCodproducto()), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_oc_cod_prod"), true)
				.setStyleGenerator(item -> BbrAlignment.CENTER.getValue())
				.setId(DISTR_COD_PRODUCTO);

		// Código Prov
		this.datGrid_OrderPredistributed.addCustomColumn(OrderPreDeliveryDetailDTO::getCodprodproveedor, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_oc_cod_prov"), true)
				.setDescriptionGenerator(item -> String.valueOf(item.getCodprodproveedor()), ContentMode.TEXT)
				.setId(DISTR_COD_PROD_PROVEEDOR);

		// Descripcion
		this.datGrid_OrderPredistributed.addCustomColumn(OrderPreDeliveryDetailDTO::getDescripcion, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_oc_descrption"), true)
				.setDescriptionGenerator(item -> String.valueOf(item.getDescripcion()), ContentMode.TEXT)
				.setId(DISTR_DESCRIPCION);

		// Solicitado Q
		this.datGrid_OrderPredistributed.addCustomColumn(OrderPreDeliveryDetailDTO::getSolicitado, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_requested_units_short"), true)
				// .setDescriptionGenerator(item -> NumericManager.getFormatter(0).format(item.getSolicitado()), ContentMode.TEXT)
				.setRenderer(new NumberRenderer(NumericManager.getFormatter(0)))
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(110)
				.setId(DISTR_SOLICITADO);
	}

	private void initializeDetailsSummaryGridColumns()
	{
		// Creamos una fila como footerRow para mostrar al final del grid
		// los totales
		this.footer_OrderDetailsDetails = this.datGrid_OrderDetails.appendFooterRow();

		// Le indicamos que una celda ocupará desde posición hasta descripción, y
		// tendrá HTML para mostrar el label "TOTAL"

		FooterCell totalCell = this.footer_OrderDetailsDetails.join(POS, COD_PRODUCTO, COD_PROD_PROVEEDOR, DESCRIPCION, UMC, UMBXUMC, COSTO_UNITARIO, SOLICITADO);
		totalCell.setHtml(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "total"));
		totalCell.setStyleName("grid-footer-total right-text");

		// Acá indicamos cuales son las celdas que tendrán totales
		this.footer_OrderDetailsDetails.getCell(COSTO_FINAL).setStyleName("grid-footer-right");

	}

	private void initializePredistributeDetailsSummaryGridColumns()
	{
		// Creamos una fila como footerRow para mostrar al final del grid
		// los totales
		this.footer_PredistributedDetails = this.datGrid_OrderPredistributed.appendFooterRow();

		// Le indicamos que una celda ocupará desde posición hasta descripción, y
		// tendrá HTML para mostrar el label "TOTAL"

		FooterCell totalCell = this.footer_PredistributedDetails.join(DISTR_COD_LOCAL, DISTR_NOMBRE_LOCAL, DISTR_COD_PRODUCTO, DISTR_COD_PROD_PROVEEDOR, DISTR_DESCRIPCION);
		totalCell.setHtml(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "total"));
		totalCell.setStyleName("grid-footer-total right-text");

		// Acá indicamos cuales son las celdas que tendrán totales
		this.footer_PredistributedDetails.getCell(DISTR_SOLICITADO).setStyleName("grid-footer-right");

	}

	private void updateOrderTotals(TotalProductsDTO totalregproducts, PucharseOrderDetails senderReport)
	{
		if (totalregproducts != null)
		{
			senderReport.footer_OrderDetailsDetails.getCell(COSTO_FINAL).setText(NumericManager.getFormatter(0).format(totalregproducts.getCostototal()));
		}
	}

	private void updatePredistributedTotals(TotalPredeliveryDTO totalPredelivery, PucharseOrderDetails senderReport)
	{
		if (totalPredelivery != null)
		{
			senderReport.footer_PredistributedDetails.getCell(DISTR_SOLICITADO).setText(NumericManager.getFormatter(0).format(totalPredelivery.getTotalquantity()));
		}
	}

	private OrderDetailReportResultDTO executeService(BbrUI parentUI)
	{
		OrderDetailReportResultDTO result = null;
		Integer requestedPage = PortalConstants.DEFAULT_PAGE_NUMBER;

		if (!resetPageDetailsInfo)
		{
			requestedPage = (this.detailsPagingManager.getCurrentPage() > 0) ? (Integer) this.detailsPagingManager.getCurrentPage() : PortalConstants.DEFAULT_PAGE_NUMBER;
		}
		if (this.initParamOrderDetail != null)
		{
			try
			{
				// VendorDTO
				this.initParamOrderDetail.setPageNumber(requestedPage);
				this.initParamOrderDetail.setOcnumber(this.selectedOrder.getOrdernumber());
				this.initParamOrderDetail.setVendorcode(this.initParamReportDTO.getVendorcode());
				this.initParamOrderDetail.setClientcode(this.initParamReportDTO.getClientrut());
				this.initParamOrderDetail.setRows(MAX_ROWS_NUMBER);
				this.initParamOrderDetail.setPaginated(true);
				this.initParamOrderDetail.setByfilter(true);

				result = PucharseOrderDetails.clientCustomerWS.getOrderDetailByOrder(this.initParamOrderDetail);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return result;
	}

	private void initializeOrderDetailsInitParam()
	{
		this.initParamOrderDetail = new OrderDetailReportInitParamDTO();
	}

	private void doUpdateReportPredelivery(Object result, BbrWorkExecutor sender)
	{
		PucharseOrderDetails senderReport = (PucharseOrderDetails) sender;
		OrderDetailReportResultDTO preDeliveryReportResult = (OrderDetailReportResultDTO) result;

		if (result != null)
		{
			BbrError error = ErrorsMngr.getInstance().getError(result, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());
			if (!error.hasError())
			{
				try
				{
					preDeliveryReportResult.setPredeliverydetail(
							(preDeliveryReportResult.getPredeliverydetail() != null) ? preDeliveryReportResult.getPredeliverydetail() : new OrderPreDeliveryDetailDTO[0]);
					ListDataProvider<OrderPreDeliveryDetailDTO> dataproviderPreDelivery = new ListDataProvider<>(Arrays.asList(preDeliveryReportResult.getPredeliverydetail()));
					senderReport.datGrid_OrderPredistributed.setDataProvider(dataproviderPreDelivery, senderReport.resetPagePreDistributedInfo);

					if (preDeliveryReportResult.getTotalpredelivery() != null)
					{
						senderReport.updatePredistributedTotals(preDeliveryReportResult.getTotalpredelivery(), senderReport);
					}
				}
				catch (Exception e)
				{
					System.out.println("Falló al intentar setear el dataprovider del detalle de órden (Grid de predelivery). \n");
					this.doUpdateReportPredelivery(result, senderReport);
				}
			}
		}

		senderReport.stopWaiting();
	}

	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{
		PucharseOrderDetails senderReport = (PucharseOrderDetails) sender;
		OrderDetailReportResultDTO reportResult = (OrderDetailReportResultDTO) result;

		if (result != null)
		{
			BbrError error = ErrorsMngr.getInstance().getError(result, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());
			if (!error.hasError())
			{

				ListDataProvider<OrderProductDetailDTO> dataproviderOrderDetail;
				if (reportResult.getProductdetail() != null && reportResult.getProductdetail().length > 0)
				{
					dataproviderOrderDetail = new ListDataProvider<>(Arrays.asList(reportResult.getProductdetail()));
					senderReport.datGrid_OrderDetails.markAsDirtyRecursive();
					senderReport.datGrid_OrderDetails.setDataProvider(dataproviderOrderDetail, senderReport.resetPageDetailsInfo);
					senderReport.setOrderHeaderInfo(reportResult.getHeaderdetail());
					senderReport.updateButtons(false);

					if (reportResult.getTotalproducts() != null)
					{
						senderReport.updateOrderTotals(reportResult.getTotalproducts(), senderReport);
					}
					
					if (reportResult.getPageInfoproduct() != null)
					{
						PageInfoDTO pageInfo = reportResult.getPageInfoproduct();
						BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
						senderReport.detailsPagingManager.setPages(bbrPage, senderReport.resetPageDetailsInfo);
					}
				}
				else
				{
					dataproviderOrderDetail = new ListDataProvider<>(Arrays.asList(new OrderProductDetailDTO[0]));
					senderReport.datGrid_OrderDetails.setDataProvider(dataproviderOrderDetail, senderReport.resetPageDetailsInfo);
				}
			}
		}
		senderReport.stopWaiting();
	}

	private BbrTextField getInfoTextField(String captionResourceName, Float width, boolean isToRight)
	{
		BbrTextField textField = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, captionResourceName));
		textField.addStyleName(BbrStyles.BBR_TEXT_FIELD_READ_ONLY);
		textField.setWidth(width, Unit.PERCENTAGE);
		textField.setReadOnly(true);

		if (isToRight)
		{
			textField.addStyleName("float-right");
		}

		return textField;
	}

	private void btn_ForwardOC_clickHandler(ClickEvent event)
	{
		this.startWaiting();
		this.executeBbrWork(doForwardOCWork);
	}

	private BaseResultDTO executeForwardOCSevice(BbrUI bbrUIParent)
	{
		BaseResultDTO result = null;
		if (this.initParamReportDTO != null)
		{
			try
			{
				List<String> ocnumbers = this.getNumberOrders();
				if (ocnumbers != null && ocnumbers.size() > 0)
				{
					ResendInitParamDTO initParamResend = new ResendInitParamDTO();

					initParamResend.setOcnumbers(ocnumbers);

					result = (BaseResultDTO) PucharseOrderDetails.clientCustomerWS.doResendOCWS(initParamResend);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return result;
	}

	private List<String> getNumberOrders()
	{
		List<String> ocnumbers = null;

		if (this.selectedOrder != null)
		{
			ocnumbers = new ArrayList<>();
			ocnumbers.add(this.selectedOrder.getOrdernumber().toString());

		}

		return ocnumbers;
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
