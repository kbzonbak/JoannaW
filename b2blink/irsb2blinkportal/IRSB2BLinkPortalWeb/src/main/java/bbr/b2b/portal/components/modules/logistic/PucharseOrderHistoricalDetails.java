package bbr.b2b.portal.components.modules.logistic;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.renderers.NumberRenderer;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.logistic.buyorders.data.dto.HistoryDetailDTO;
import bbr.b2b.logistic.buyorders.data.dto.HistoryInitParamDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderHistoryDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderReportDataDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderReportInitParamDTO;
import bbr.b2b.logistic.rest.client.CustomerLogisticRestFulWSClient;
import bbr.b2b.portal.classes.constants.BbrAppConstants;
import bbr.b2b.portal.classes.i18n.HasI18n;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.managers.FunctionalityMngr;
import bbr.b2b.portal.classes.styles.BbrStyles;
import bbr.b2b.portal.classes.utils.app.DateFormats;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.utils.PortalDates;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.constants.CoreConstants;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.utilities.BbrDateUtils;
import cl.bbr.core.classes.utilities.BbrNumberRenderer;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.utilities.NumericManager;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrTabContent;
import cl.bbr.core.components.basics.BbrTabSheet;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrVSeparator;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.grid.renderer.ShortDateRenderer;
import cl.bbr.core.components.paging.BbrPagingManager;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class PucharseOrderHistoricalDetails extends BbrWindow implements BbrWorkExecutor, HasI18n
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// Constants
	private static final long						serialVersionUID		= 631911393053940795L;

	private static final DateTimeFormatter			DD_MM_YYYY				= DateTimeFormatter.ofPattern("dd-MM-yyyy");

	// TAB - Detalle OrderDetailReportDataDTO
	//
	private static final String						OC_NUMBER				= "ocnumber";
	private static final String						EMMITED					= "emmited";
	private static final String						RECEPTION_DATE			= "receptiondate";
	private static final String						AMMOUNT					= "ammount";
	private static final String						QUANTITY				= "quantity";
	private static final String						VIEW					= "view";

	// Components

	private BbrAdvancedGrid<HistoryDetailDTO>		datGrid_OrderDetails	= null;
	private BbrPagingManager						detailsPagingManager	= null;
	private BbrTabSheet								tabNav_OrderDetails		= null;
	private BbrTabContent							tabCont_Details			= null;

	// Header
	private BbrTextField							txt_Client				= null;
	private BbrTextField							txt_EmittedDate			= null;
	private BbrTextField							txt_NumberOrder			= null;
	private BbrTextField							txt_TypeOC				= null;
	private BbrTextField							txt_SolicitadoQ			= null;
	private BbrTextField							txt_ReceptionDate		= null;
	private BbrTextField							txt_Solicitado$			= null;

	// Variables
	private HistoryInitParamDTO						initParamOrderDetail	= null;
	private final String							DEFAULT_SORT_FIELD		= EMMITED;
	private BbrWork<OrderHistoryDTO>				detailsWork				= null;
	private OrderReportDataDTO						selectedOrder			= null;
	private boolean									resetPageDetailsInfo	= false;

	private BbrUI									parentUI				= null;
	private OrderReportInitParamDTO					initParamReportDTO		= null;
	private static CustomerLogisticRestFulWSClient	clientCustomerWS		= null;
	// ==============================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public PucharseOrderHistoricalDetails(BbrUI parentUI, OrderReportDataDTO selectedOrder, OrderReportInitParamDTO initParamReportDTO, FunctionalityMngr functionalityMngr)
	{
		super(parentUI);
		this.parentUI = parentUI;
		this.selectedOrder = selectedOrder;
		this.initParamReportDTO = initParamReportDTO;
		PucharseOrderHistoricalDetails.clientCustomerWS = CustomerLogisticRestFulWSClient.getInstance(BbrAppConstants.CUSTOMER_URL);
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

		HorizontalLayout toolBar = new HorizontalLayout();
		toolBar.setWidth("100%");
		toolBar.addComponents(leftButtonsProductsBar);
		toolBar.setMargin(new MarginInfo(false, true));
		toolBar.addStyleName("filter-toolbar");
		toolBar.setExpandRatio(leftButtonsProductsBar, 1F);

		toolBar.setComponentAlignment(leftButtonsProductsBar, Alignment.MIDDLE_RIGHT);

		// Sección DataGrid
		// -> Detalles
		this.datGrid_OrderDetails = new BbrAdvancedGrid<>(this.getBbrUIParent().getUser().getLocale());
		this.datGrid_OrderDetails.setSortable(false);

		this.initializeDetailsDataGridColumns();

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
		this.tabNav_OrderDetails.addBbrTab(tabCont_Details, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "tab_oc_historical"), false);

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
		detailsWork.addFunction(new Function<Object, OrderHistoryDTO>()
		{
			@Override
			public OrderHistoryDTO apply(Object t)
			{
				return executeService(PucharseOrderHistoricalDetails.this.getBbrUIParent());
			}
		});

		this.startWaiting(1);

		this.resetPageDetailsInfo = true;
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
		PucharseOrderHistoricalDetails senderReport = (PucharseOrderHistoricalDetails) sender;

		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				if (result instanceof OrderHistoryDTO)
				{
					this.doUpdateReport(result, sender);
				}
			}
			else if (triggerObject instanceof Button)
			{
				this.doDownloadFile(result, sender, triggerObject);
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

	private void pagingChangeDetails_Listener(BbrPagingEvent e)
	{
		if ((e != null) && e.getEventType().equals(BbrPagingEvent.PAGE_CHANGED) && (e.getResultObject() != null))
		{
			this.startWaiting();
			this.resetPageDetailsInfo = false;
			this.executeBbrWork(detailsWork);
		}
	}

	private void updateSortOrderCriteria(List<GridSortOrder<HistoryDetailDTO>> sortOrderList)
	{
		if (sortOrderList != null && !sortOrderList.isEmpty())
		{
			ArrayList<OrderCriteriaDTO> resultList = new ArrayList<>();
			for (GridSortOrder<HistoryDetailDTO> sorOrder : sortOrderList)
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
			GridSortOrder<HistoryDetailDTO> sortOrder = new GridSortOrder<>(datGrid_OrderDetails.getColumn(DEFAULT_SORT_FIELD), SortDirection.ASCENDING);
			sortOrderList = new ArrayList<GridSortOrder<HistoryDetailDTO>>();
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

	private VerticalLayout getOrderHeaderInfo(OrderReportDataDTO order)
	{
		///////////////////// ONE ROW /////////////////////
		// -> Numero Orden
		this.txt_NumberOrder = this.getInfoTextField("number_order", 99F, true);
		this.txt_NumberOrder.setValue("");
		this.txt_NumberOrder.setReadOnly(true);

		FormLayout frmNumberOrder = this.embedIntoForm(this.txt_NumberOrder);

		// -> Cliente
		this.txt_Client = this.getInfoTextField("client", 89F, true);
		this.txt_Client.setValue("");
		this.txt_Client.setReadOnly(true);

		FormLayout frmClient = this.embedIntoForm(this.txt_Client);

		// -> Fecha Emision
		this.txt_EmittedDate = this.getInfoTextField("emitted_date", 69.5F, true);
		this.txt_EmittedDate.setValue("");
		this.txt_EmittedDate.setReadOnly(true);
		FormLayout frmEmittedDate = this.embedIntoForm(this.txt_EmittedDate);

		HorizontalLayout OneRowLayout = new HorizontalLayout(frmNumberOrder, frmClient, frmEmittedDate);
		OneRowLayout.setMargin(false);
		OneRowLayout.setWidth("100%");
		OneRowLayout.setHeight("30px");

		///////////////////// TWO ROW /////////////////////

		// -> tipo
		this.txt_TypeOC = this.getInfoTextField("type", 75.5F, true);
		this.txt_TypeOC.setValue("");
		this.txt_TypeOC.setReadOnly(true);

		FormLayout frmType = this.embedIntoForm(this.txt_TypeOC);

		// -> Solicitado Q
		this.txt_SolicitadoQ = this.getInfoTextField("requested_units_short", 100F, true);
		this.txt_SolicitadoQ.setValue("");
		this.txt_SolicitadoQ.setReadOnly(true);

		FormLayout frmSolicitado = this.embedIntoForm(this.txt_SolicitadoQ);

		// -> Fecha Recepcion B2B
		this.txt_ReceptionDate = this.getInfoTextField("oc_b2b_date_short", 100F, false);
		this.txt_ReceptionDate.setValue("");
		this.txt_ReceptionDate.setReadOnly(true);
		FormLayout frmReceptionDate = this.embedIntoForm(this.txt_ReceptionDate);

		HorizontalLayout TwoRowLayout = new HorizontalLayout(frmType, frmSolicitado, frmReceptionDate);
		TwoRowLayout.setMargin(false);
		TwoRowLayout.setWidth("100%");
		TwoRowLayout.setHeight("30px");

		///////////////////// THREE ROW /////////////////////
		// -> Solicitado $
		this.txt_Solicitado$ = this.getInfoTextField("solicitado_$", 89F, true);
		this.txt_Solicitado$.setValue("");
		this.txt_Solicitado$.setReadOnly(true);

		// FormLayout frmSolicitado$ = this.embedIntoForm(this.txt_Solicitado$);

		FormLayout frmSolicitadoS = new FormLayout();
		frmSolicitadoS.setMargin(false);
		frmSolicitadoS.setWidth(33, Unit.PERCENTAGE);
		frmSolicitadoS.addStyleName("generic-form");
		frmSolicitadoS.addComponents(txt_Solicitado$);

		HorizontalLayout ThreeRowLayout = new HorizontalLayout(frmSolicitadoS);
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

	private void setOrderHeaderInfo(OrderHistoryDTO headerOrderHistorical)
	{
		if (headerOrderHistorical != null && headerOrderHistorical.getHistoryheader() != null)
		{
			this.txt_NumberOrder.setValue(headerOrderHistorical.getHistoryheader().getOcnumber().trim());
			this.txt_Client.setValue((headerOrderHistorical.getHistoryheader().getClient() != null)? headerOrderHistorical.getHistoryheader().getClient().trim() : "");
			this.txt_EmittedDate.setValue((headerOrderHistorical.getHistoryheader().getEmitted() != null)?headerOrderHistorical.getHistoryheader().getEmitted():"");
			this.txt_TypeOC.setValue((headerOrderHistorical.getHistoryheader().getOctype() != null)? headerOrderHistorical.getHistoryheader().getOctype():"");
			this.txt_SolicitadoQ.setValue((headerOrderHistorical.getHistoryheader().getQuantity()!= null)? String.valueOf(headerOrderHistorical.getHistoryheader().getQuantity()):"");
			this.txt_Solicitado$.setValue((headerOrderHistorical.getHistoryheader().getAmmount()!=null)? String.valueOf(headerOrderHistorical.getHistoryheader().getAmmount()):"");

		}
	}

	private void initializeDetailsDataGridColumns()
	{
		// Fecha Emisión
		this.datGrid_OrderDetails.addCustomColumn(o -> this.transformDate(o.getEmitted()), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_oc_fecha_emision"), true)
				.setComparator((item1, item2) -> compareDate(item1.getEmitted(), item2.getEmitted()))
				.setStyleGenerator(item -> BbrAlignment.CENTER.getValue())
				//.setWidth(120)
				.setId(EMMITED);

		// Fecha Recepcionb2b link
		this.datGrid_OrderDetails.addCustomColumn(o -> this.transformDate(o.getReceptiondate()), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_oc_b2b_date"), true)
				.setComparator((item1, item2) -> compareDate(item1.getReceptiondate(), item2.getReceptiondate()))
				.setStyleGenerator(item -> BbrAlignment.CENTER.getValue())
				//.setWidth(160)
				.setId(RECEPTION_DATE);

		// Solicitado $
		this.datGrid_OrderDetails.addCustomColumn(HistoryDetailDTO::getAmmount, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_requested_amount_short"), true)
				.setDescriptionGenerator(item -> String.valueOf(item.getAmmount()), ContentMode.TEXT)
				.setRenderer(NumericManager.getNumberRenderer(1))
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(110)
				.setId(AMMOUNT);

		// Solicitado Q
		this.datGrid_OrderDetails.addCustomColumn(HistoryDetailDTO::getQuantity, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_requested_units_short"), true)
				.setDescriptionGenerator(order -> String.valueOf(order.getQuantity()), ContentMode.TEXT)
				.setRenderer(NumericManager.getNumberRenderer(1))
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(110)
				.setId(QUANTITY);

		// Historico
		this.datGrid_OrderDetails.addCustomComponentColumn((item -> this.getGridLinkButton(item, VIEW)),
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_view"), true)
				.setStyleGenerator(item -> BbrAlignment.CENTER.getValue())
				.setWidth(110)
				.setId(VIEW);
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

	private Button getGridLinkButton(HistoryDetailDTO itemData, String columnID)
	{
		Button result = null;

		if (itemData != null)
		{
			result = new Button("", BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_SearchPrimaryByGrilla.png"));
			result.addClickListener((ClickListener & Serializable) e -> this.linkButton_clickHandler(e));
			result.addStyleName("toolbar-button");

			result.setData(itemData);
		}

		return result;
	}

	private void linkButton_clickHandler(ClickEvent event)
	{
		if(event != null)
		{
			PucharseOrderDetails pucharseOrderDetailsCtrl = new PucharseOrderDetails(this.getBbrUIParent(), this.selectedOrder, this.initParamReportDTO, null);
			pucharseOrderDetailsCtrl.open(true, true);
		}
		
	}

	private OrderHistoryDTO executeService(BbrUI parentUI)
	{
		OrderHistoryDTO result = null;

		if (this.initParamOrderDetail != null)
		{
			try
			{
				this.initParamOrderDetail.setClientrut(this.initParamReportDTO.getClientrut());
				this.initParamOrderDetail.setOcnumber(this.selectedOrder.getOrdernumber().toString());
				this.initParamOrderDetail.setVendorcode(this.initParamReportDTO.getVendorcode());

				result = PucharseOrderHistoricalDetails.clientCustomerWS.getOrderHistoryWS(this.initParamOrderDetail);
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
		initParamOrderDetail = new HistoryInitParamDTO();
	}

	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{
		PucharseOrderHistoricalDetails senderReport = (PucharseOrderHistoricalDetails) sender;
		OrderHistoryDTO reportResult = (OrderHistoryDTO) result;

		if (result != null)
		{
			BbrError error = ErrorsMngr.getInstance().getError(result, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());
			if (!error.hasError())
			{

				ListDataProvider<HistoryDetailDTO> dataproviderOrderDetail;
				if (reportResult.getHistory() != null && reportResult.getHistory().length > 0)
				{
					dataproviderOrderDetail = new ListDataProvider<>(Arrays.asList(reportResult.getHistory()));
					senderReport.datGrid_OrderDetails.markAsDirtyRecursive();
					senderReport.datGrid_OrderDetails.setDataProvider(dataproviderOrderDetail, senderReport.resetPageDetailsInfo);
					senderReport.setOrderHeaderInfo(reportResult);

					// if (senderReport.resetPageDetailsInfo)
					// {
					// PageInfoDTO pageInfo = reportResult.getPageInfoproduct();
					// BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
					// senderReport.detailsPagingManager.setPages(bbrPage, senderReport.resetPageDetailsInfo);
					// }
				}
				else
				{
					dataproviderOrderDetail = new ListDataProvider<>(Arrays.asList(new HistoryDetailDTO[0]));
					senderReport.datGrid_OrderDetails.setDataProvider(dataproviderOrderDetail, senderReport.resetPageDetailsInfo);
				}
			}
		}
		senderReport.stopWaiting();
	}

	private void doDownloadFile(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		PucharseOrderHistoricalDetails senderReport = (PucharseOrderHistoricalDetails) sender;
		if (senderReport != null)
		{
			senderReport.downloadLinkFile(result);
		}
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

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
