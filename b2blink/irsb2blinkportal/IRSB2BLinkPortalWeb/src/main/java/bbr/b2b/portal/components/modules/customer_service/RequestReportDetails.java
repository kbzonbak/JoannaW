package bbr.b2b.portal.components.modules.customer_service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.SortEvent;
import com.vaadin.event.SortEvent.SortListener;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.logistic.report.data.dto.NotificationReportDTO;
import bbr.b2b.logistic.report.data.dto.NotificationReportInitParamDTO;
import bbr.b2b.logistic.report.data.dto.NotificationReportResultDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.styles.BbrStyles;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.utils.PortalDates;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import bbr.esb.services.webservices.facade.TicketReportDataResultDTO;
import cl.bbr.core.classes.basics.BbrPage;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.constants.CoreConstants;
import cl.bbr.core.classes.constants.DownloadFormats;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.utilities.BbrDateUtils;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.classes.utilities.NumericManager;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.paging.BbrPagingManager;
import cl.bbr.core.components.widgets.bbrpopupbutton.BbrPopupButton;

public class RequestReportDetails extends BbrWindow implements BbrWorkExecutor
{
	private static final long						serialVersionUID				= 409086468924554268L;

	private static final String						SKUBUYER						= "skubuyer";
	private static final String						SKUVENDOR						= "skuvendor";
	private static final String						DESCRIPTION						= "description";
	private static final String						QUANTITY						= "quantity";

	private Button									btn_Refresh						= null;
	private Button									btn_ExcelDownload				= null;
	private BbrPopupButton							btn_DownloadPopUp				= null;
	private Label									dayTime							= new Label();

	private BbrAdvancedGrid<NotificationReportDTO>	datGrid_NotificationsDetails	= null;
	private BbrPagingManager						pagingManager					= null;
	
	private Boolean									resetPageInfo					= true;
	private OrderCriteriaDTO[]						orderCriteria					= null;
	private final int								DEFAULT_PAGE_NUMBER				= 1;
	private final int								MAX_ROWS_NUMBER					= 50;
	private final String							DEFAULT_SORT_FIELD				= SKUBUYER;
	private CompanyDataDTO							selectedCompanyData				= null;
	private BbrWork<FileDownloadInfoResultDTO>		downloadsWork					= null;
	private BbrWork<NotificationReportResultDTO>	reportWork						= null;
	private TicketReportDataResultDTO				selectedItemTicketReportData	= null;

	public RequestReportDetails(BbrUI parentUI, TicketReportDataResultDTO selectedItem, CompanyDataDTO	selectedCompanyData)
	{
		super(parentUI);
		this.selectedItemTicketReportData = selectedItem;
		this.selectedCompanyData = selectedCompanyData;
	}

	@Override
	public void initializeView()
	{
		// Paging Manager
		this.pagingManager = new BbrPagingManager();
		this.pagingManager.setLocale(this.getUser().getLocale());
		this.pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> this.pagingChangeDetails_Listener(e), BbrPagingEvent.PAGE_CHANGED);

		// Barra de herramientas superior izq
		HorizontalLayout leftButtonsBar = new HorizontalLayout();
		leftButtonsBar.setSpacing(true);
		leftButtonsBar.setMargin(false);
		leftButtonsBar.setHeight("30px");
		leftButtonsBar.addStyleName("toolbar-layout");

		VerticalLayout cmp_DownloadProductsButtons = new VerticalLayout();
		cmp_DownloadProductsButtons.setMargin(new MarginInfo(false, true));
		cmp_DownloadProductsButtons.setSpacing(false);

		this.btn_ExcelDownload = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "download_excel_file"));
		this.btn_ExcelDownload.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "download_excel_file"));
		this.btn_ExcelDownload.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadFile_downloadHandler(e));
		this.btn_ExcelDownload.addStyleName("action-button");
		cmp_DownloadProductsButtons.addComponent(this.btn_ExcelDownload);

		this.btn_DownloadPopUp = new BbrPopupButton("");
		this.btn_DownloadPopUp.setIcon(BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_DownloadPrimary.png"));
		this.btn_DownloadPopUp.addStyleName("toolbar-button");
		this.btn_DownloadPopUp.setContentLayout(cmp_DownloadProductsButtons);
		this.btn_DownloadPopUp.setClosePopupOnOutsideClick(true);

		this.btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
		this.btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
		this.btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler(e));
		this.btn_Refresh.addStyleName("toolbar-button");

		// Barra superior dercha
		HorizontalLayout rightButtonsBar = new HorizontalLayout();
		rightButtonsBar.addComponents(this.btn_DownloadPopUp, this.btn_Refresh);
		rightButtonsBar.setSpacing(true);
		rightButtonsBar.setMargin(false);
		rightButtonsBar.setHeight("30px");
		rightButtonsBar.addStyleName(BbrStyles.TOOLBAR_LAYOUT);
		rightButtonsBar.setComponentAlignment(this.btn_DownloadPopUp, Alignment.MIDDLE_RIGHT);

		// Barra superior
		HorizontalLayout toolBar = new HorizontalLayout();
		toolBar.setWidth("100%");
		toolBar.addComponents(leftButtonsBar, this.dayTime, rightButtonsBar);

		toolBar.addStyleName("filter-toolbar");
		toolBar.setExpandRatio(leftButtonsBar, 1F);
		toolBar.setExpandRatio(this.dayTime, 1F);
		toolBar.setExpandRatio(rightButtonsBar, 1F);

		toolBar.setComponentAlignment(leftButtonsBar, Alignment.MIDDLE_LEFT);
		toolBar.setComponentAlignment(dayTime, Alignment.MIDDLE_CENTER);
		toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);

		// Reporte: Grilla
		this.datGrid_NotificationsDetails = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.datGrid_NotificationsDetails.setSortable(false);
		this.datGrid_NotificationsDetails.setSizeFull();
		this.datGrid_NotificationsDetails.addStyleName("report-grid");
		this.datGrid_NotificationsDetails.addSortListener((SortListener<GridSortOrder<NotificationReportDTO>> & Serializable) e -> dataGrid_sortHandler(e));

		this.initializeMatchableDocumentDataGridColumns();

		// Main Layout

		VerticalLayout mainLayout = new VerticalLayout(toolBar, this.datGrid_NotificationsDetails, this.pagingManager);
		mainLayout.setSizeFull();
		mainLayout.setMargin(false);
		mainLayout.setExpandRatio(this.datGrid_NotificationsDetails, 1F);
		mainLayout.addStyleName("bbr-margin-windows");

		// Ventana

		this.setWidth("1100px");
		this.setHeight("600px");
		this.setResizable(false);
		this.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_LOGISTIC, "title_details_notifications", this.selectedItemTicketReportData.getCliente()));
		this.setContent(mainLayout);

		this.updateSortOrderMatchableDocumentCriteria(null);
		this.updateButtons(false);
		reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		reportWork.addFunction(new Function<Object, NotificationReportResultDTO>()
		{
			@Override
			public NotificationReportResultDTO apply(Object t)
			{
				return executetaxDocumentsService(RequestReportDetails.this.getBbrUIParent());
			}
		});

		this.startWaiting();
		this.executeBbrWork(reportWork);
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor senderPostExecute, Object triggerObject)
	{
		if (result != null)
		{
			if (result instanceof NotificationReportResultDTO)
			{
				this.doUpdateReport(result, senderPostExecute);
			}
			else if (triggerObject == this.btn_ExcelDownload)
			{
				this.doDownloadFile(result, senderPostExecute, triggerObject);
			}
		}
	}

	@Override
	protected void downloadFormat_selectedHandler(BbrEvent event)
	{
		DownloadFormats selectedFormat = ((event != null) && (event.getResultObject() instanceof DownloadFormats)) ? (DownloadFormats) event.getResultObject() : null;

		downloadsWork = new BbrWork<>(this, this.getBbrUIParent(), btn_DownloadTriggerButton);
		downloadsWork.addFunction(new Function<Object, FileDownloadInfoResultDTO>()
		{
			@Override
			public FileDownloadInfoResultDTO apply(Object t)
			{
				return executeDownloadService(RequestReportDetails.this.getBbrUIParent(), selectedFormat, btn_DownloadTriggerButton);
			}
		});

		this.startWaiting();
		this.executeBbrWork(downloadsWork);
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{
		RequestReportDetails senderReport = (RequestReportDetails) sender;

		if (result != null)
		{
			NotificationReportResultDTO reportResult = (NotificationReportResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				ListDataProvider<NotificationReportDTO> dataprovider = new ListDataProvider<>(Arrays.asList(reportResult.getNotificationreport()));

				senderReport.datGrid_NotificationsDetails.setDataProvider(dataprovider, senderReport.resetPageInfo);
				senderReport.updateButtons(false);
				//senderReport.dayTime.setValue(this.transformDate(reportResult.getReportdate(), true));
				if (senderReport.resetPageInfo)
				{
					PageInfoDTO pageInfo = reportResult.getPageInfo();

					BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
					senderReport.pagingManager.setPages(bbrPage, senderReport.resetPageInfo);
				}
			}
		}
		this.stopWaiting();

	}

	
	
	private void updateButtons(Boolean isSelectionEvent)
	{
		boolean isEmptyGrid = this.datGrid_NotificationsDetails.isEmpty();
		this.btn_ExcelDownload.setEnabled(!isEmptyGrid);
	}
	
	private NotificationReportResultDTO executetaxDocumentsService(BbrUI bbrUIParent)
	{
		NotificationReportResultDTO result = null;
		NotificationReportInitParamDTO initParams = this.getNotificationReportInitParam();

		if (initParams != null)
		{
			try
			{
				result = EJBFactory.getStockpoolEJBFactory().getStockpoolReportManagerLocal(bbrUIParent).getNotificationReportWS(initParams, bbrUIParent.getUser().getId());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}

	private NotificationReportInitParamDTO getNotificationReportInitParam()
	{
		NotificationReportInitParamDTO initParams = new NotificationReportInitParamDTO();
		Integer requestedPage = (!resetPageInfo && pagingManager.getCurrentPage() > 0) ? (Integer) pagingManager.getCurrentPage() : this.DEFAULT_PAGE_NUMBER;

		initParams.setRows(MAX_ROWS_NUMBER);
		initParams.setPageNumber(requestedPage);
		initParams.setByfilter(true);
		initParams.setPaginated(true);
		initParams.setOrderby(this.orderCriteria);
		//initParams.setPageNumber(DEFAULT_PAGE_NUMBER);
		initParams.setTicketnumber(this.selectedItemTicketReportData.getNrosolicitud());
		initParams.setVendorcode(this.selectedCompanyData.getRut());
		String strNotificationid = this.selectedItemTicketReportData.getReferencia().replace("SPL_", "");
		Long notificationid = Long.parseLong(strNotificationid);
		initParams.setNotificationid(notificationid);

		return initParams;
	}

	private void initializeMatchableDocumentDataGridColumns()
	{

		// SKU Retails
		this.datGrid_NotificationsDetails.addCustomColumn(NotificationReportDTO::getSkubuyer, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_sku_retails"), true)
				.setDescriptionGenerator(NotificationReportDTO::getSkubuyer, ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(200)
				.setId(SKUBUYER);

		// SKU Proveedor
		this.datGrid_NotificationsDetails.addCustomColumn(NotificationReportDTO::getSkuvendor, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_sku_proveedor"), true)
				.setDescriptionGenerator(NotificationReportDTO::getSkuvendor, ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(200)
				.setId(SKUVENDOR);

		// Descripcion
		this.datGrid_NotificationsDetails.addCustomColumn(NotificationReportDTO::getDescription, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_description"), true)
				.setDescriptionGenerator(NotificationReportDTO::getDescription, ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue())
				.setId(DESCRIPTION);

//		// Cantidad
//		this.datGrid_NotificationsDetails.addCustomColumn(i -> (i.getQuantity() != null) ? NumericManager.getFormatter(0).format(i.getQuantity()) : "", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_quantity"), true)
//				.setDescriptionGenerator(i -> (i.getQuantity() != null) ? NumericManager.getFormatter(0).format(i.getQuantity()) : "", ContentMode.TEXT)
//				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
//				.setWidth(200)
//				.setId(QUANTITY);
		
		// Cantidad
		this.datGrid_NotificationsDetails.addCustomColumn(NotificationReportDTO::getQuantity, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_quantity"), true)
				.setDescriptionGenerator(i -> NumericManager.getFormatter(0).format(i.getQuantity()), ContentMode.TEXT)
				.setRenderer(NumericManager.getNumberRenderer(0))
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(110)
				.setId(QUANTITY);
	}

	public String transformDate(String strString, boolean includetime)
	{
		String result = "";
		if (strString != null)
		{
			LocalDateTime dateTime = PortalDates.getLocalDateTimeformatT(strString);
			if (includetime)
			{
				result = this.formatDate(dateTime);
			}
			else
			{
				result = BbrDateUtils.getInstance().toShortDateTime(dateTime);
			}

		}

		return result;
	}

	public int compareDate(String strString1, String strString2)
	{
		int result = 0;

		if (strString1 != null && strString2 != null)
		{
			LocalDateTime dateTime1 = PortalDates.getLocalDateTimeformatT(strString1);
			LocalDateTime dateTime2 = PortalDates.getLocalDateTimeformatT(strString2);

			result = dateTime1.compareTo(dateTime2);

		}
		return result;
	}

	private void updateSortOrderMatchableDocumentCriteria(List<GridSortOrder<NotificationReportDTO>> sortOrderList)
	{
		if (sortOrderList != null && !sortOrderList.isEmpty())
		{
			ArrayList<OrderCriteriaDTO> resultList = new ArrayList<>();
			for (GridSortOrder<NotificationReportDTO> sorOrder : sortOrderList)
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

			GridSortOrder<NotificationReportDTO> sortOrder = new GridSortOrder<>(datGrid_NotificationsDetails.getColumn(DEFAULT_SORT_FIELD), SortDirection.DESCENDING);
			sortOrderList = new ArrayList<GridSortOrder<NotificationReportDTO>>();
			sortOrderList.add(sortOrder);

			this.datGrid_NotificationsDetails.setSortOrder(sortOrderList);
		}
	}
	
	private void dataGrid_sortHandler(SortEvent<GridSortOrder<NotificationReportDTO>> e)
	{
		if (e.isUserOriginated() && pagingManager.getTotalsPages() > 1)
		{
			this.startWaiting();
			this.updateSortOrderMatchableDocumentCriteria(e.getSortOrder());
			//this.trackReport = false;
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

	private void btn_DownloadFile_downloadHandler(ClickEvent e)
	{
		this.btn_DownloadTriggerButton = e.getButton();

		if (this.btn_DownloadTriggerButton == this.btn_ExcelDownload)
		{
			this.selectDownloadFileType(DownloadFormats.XLS, DownloadFormats.XLS, DownloadFormats.CSV);
		}
	}

	protected FileDownloadInfoResultDTO executeDownloadService(BbrUI bbrUIParent, DownloadFormats selectedFormat, Button downloadTriggerButton)
	{
		FileDownloadInfoResultDTO fileResult = null;
		if (selectedFormat != null)
		{
			try
			{
				if (this.btn_DownloadTriggerButton == this.btn_ExcelDownload)
				{
					NotificationReportInitParamDTO initParams = this.getNotificationReportInitParam();

					fileResult = EJBFactory.getStockpoolEJBFactory().getStockpoolReportManagerLocal(bbrUIParent).downloadNotificationReport(initParams, selectedFormat.getValue(), bbrUIParent.getUser().getId(), bbrUIParent.getLocale());
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}

		return fileResult;
	}

	private void doDownloadFile(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		RequestReportDetails senderReport = (RequestReportDetails) sender;
		if (senderReport != null)
		{
			senderReport.downloadLinkFile(result);
		}
	}

	protected void downloadLinkFile(Object fileInfo)
	{
		downloadLinkFile(fileInfo);
	}

	private String formatDate(LocalDateTime date)
	{
		return date != null ? DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(date) : "";
	}
	
	private void pagingChangeDetails_Listener(BbrPagingEvent e)
	{
		if ((e != null) && e.getEventType().equals(BbrPagingEvent.PAGE_CHANGED) && (e.getResultObject() != null))
		{
			this.startWaiting();
			this.resetPageInfo = false;
			this.executeBbrWork(reportWork);
		}
	}

}
