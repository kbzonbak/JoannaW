package bbr.b2b.portal.modules.stockpool;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.BaseResultsDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.logistic.report.data.dto.DeletePendingItemsInitParamDTO;
import bbr.b2b.logistic.report.data.dto.PendingHomologationInitParamDTO;
import bbr.b2b.logistic.report.data.dto.PendingHomologationReportDTO;
import bbr.b2b.logistic.report.data.dto.PendingHomologationResultDTO;
import bbr.b2b.logistic.report.data.dto.PendingItemsDTO;
import bbr.b2b.logistic.report.data.dto.ResendDTO;
import bbr.b2b.logistic.report.data.dto.ResendTxInitParamDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.managers.FunctionalityMngr;
import bbr.b2b.portal.classes.styles.BbrStyles;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.BbrMessagesBoxUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.filters.stockpool.ProductWithoutHomologationFilter;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.utils.PortalDates;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.basics.BbrPage;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.constants.DownloadFormats;
import cl.bbr.core.classes.constants.TrackingConstants;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.utilities.BbrDateUtils;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.utilities.NumericManager;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrErrorList;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.paging.BbrPagingManager;
import cl.bbr.core.components.widgets.bbrpopupbutton.BbrPopupButton;

public class ProductWithoutHomologationReport extends BbrModule implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	private static final long								serialVersionUID				= 1717349615397710932L;

	// Constantes
	private static final String								REQUESTDATE						= "requestdate";
	private static final String								OCNUMBER						= "ocnumber";
	private static final String								QUANTITY						= "quantity";
	private static final String								BUYER							= "buyer";
	private static final String								SKUBUYER						= "skubuyer";
	private static final String								DESCRIPTION						= "description";

	private final String									DEFAULT_SORT_FIELD				= REQUESTDATE;
	private final static int								DEFAULT_PAGE_NUMBER				= 1;
	private final int										MAX_ROWS_NUMBER					= 100;
	// Variables
	private CompanyDataDTO									selectedCompany					= null;

	private ProductWithoutHomologationFilter				filterLayout					= null;
	private BbrWork<PendingHomologationResultDTO>			reportWork						= null;
	private BbrWork<FileDownloadInfoResultDTO>				downloadsWork					= null;
	private BbrWork<BaseResultsDTO>							reportWorkReinjectTX			= null;
	private BbrWork<BaseResultsDTO>							reportWorkPendingReprocessing	= null;

	private Boolean											trackReport						= true;
	private Boolean											resetPageInfo					= true;
	private VerticalLayout									mainLayout						= null;
	private BbrAdvancedGrid<PendingHomologationReportDTO>	datGrid_ProductsWithoutApproval	= null;
	private BbrPagingManager								pagingManager					= null;

	private Button											btn_ReinjectTX					= null;
	private Button											btn_PendingReprocessing			= null;
	private Button											btn_DownloadExcel				= null;
	private BbrPopupButton									btn_Download					= null;
	private BbrPopupButton									btn_Actions						= null;
	private Button											btn_Refresh						= null;

	ListDataProvider<PendingHomologationReportDTO>			dataproviderDatingReport		= null;
	private OrderCriteriaDTO[]								orderCriteria					= null;
	private PendingHomologationInitParamDTO					pendingHomologationinitParamDTO	= null;
	//private String											codeState						= null;
	private boolean											generateAutomatic				= false;
	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ProductWithoutHomologationReport(BbrUI bbrUIParent)
	{
		super(bbrUIParent);
		this.generateAutomatic = false;
	}

	public ProductWithoutHomologationReport(BbrUI bbrUIParent, FunctionalityMngr functionalityMngr, String codeState)
	{
		super(bbrUIParent);
		//this.codeState = codeState;
		this.generateAutomatic = true;
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
		this.filterLayout = new ProductWithoutHomologationFilter(this);
		this.filterLayout.initializeView();
		this.setBbrFilterContainer(this.filterLayout);

		// Paging Manager
		this.pagingManager = new BbrPagingManager();
		this.pagingManager.setLocale(this.getUser().getLocale());
		this.pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> this.pagingChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);

		// DOWNLOAD BUTTONS SECTION
		VerticalLayout cmp_DownloadButtons = new VerticalLayout();
		cmp_DownloadButtons.setMargin(new MarginInfo(false, true));
		cmp_DownloadButtons.setSpacing(false);

		// Botones de acción de la izquierda (...)
		VerticalLayout cmp_ActionButtons = new VerticalLayout();
		cmp_ActionButtons.setMargin(new MarginInfo(false, true));
		cmp_ActionButtons.setSpacing(false);

		// Reinyectar la TX
		this.btn_ReinjectTX = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "btn_reinject_tx"));
		this.btn_ReinjectTX.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "btn_reinject_tx"));
		this.btn_ReinjectTX.addClickListener((ClickListener & Serializable) e -> this.btn_ReinjectTX_clickHandler(e));
		this.btn_ReinjectTX.addStyleName("action-button");
		cmp_ActionButtons.addComponent(this.btn_ReinjectTX);

		// Eliminar el producto cmo pendiente de reporcesar
		this.btn_PendingReprocessing = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "btn_pending_reprocessing"));
		this.btn_PendingReprocessing.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "btn_pending_reprocessing"));
		this.btn_PendingReprocessing.addClickListener((ClickListener & Serializable) e -> this.btn_PendingReprocessing_clickHandler(e));
		this.btn_PendingReprocessing.addStyleName("action-button");
		cmp_ActionButtons.addComponent(this.btn_PendingReprocessing);

		this.btn_DownloadExcel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "download_excel_file"));
		this.btn_DownloadExcel.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "download_excel_file"));
		this.btn_DownloadExcel.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadFile_downloadHandler(e));
		this.btn_DownloadExcel.addStyleName("action-button");
		cmp_DownloadButtons.addComponent(this.btn_DownloadExcel);

		this.btn_Actions = new BbrPopupButton("");
		this.btn_Actions.setIcon(EnumToolbarButton.LIST.getResource());
		this.btn_Actions.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "aditional_actions"));
		this.btn_Actions.addStyleName("toolbar-button");
		this.btn_Actions.setContentLayout(cmp_ActionButtons);
		this.btn_Actions.setClosePopupOnOutsideClick(true);

		this.btn_Download = new BbrPopupButton("");
		this.btn_Download.setIcon(EnumToolbarButton.DOWNLOAD_PRIMARY.getResource());
		this.btn_Download.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "downloads"));
		this.btn_Download.addStyleName("toolbar-button");
		this.btn_Download.setContentLayout(cmp_DownloadButtons);
		this.btn_Download.setClosePopupOnOutsideClick(true);

		this.btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
		this.btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
		this.btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler(e));
		this.btn_Refresh.addStyleName(BbrStyles.TOOLBAR_BUTTON);
		this.btn_Refresh.setEnabled(false);

		// Reporte: Barra de herramientas superior izq
		HorizontalLayout leftButtonsBar = new HorizontalLayout();
		leftButtonsBar.addComponents(this.btn_Actions);
		leftButtonsBar.setSpacing(true);
		leftButtonsBar.setMargin(false);
		leftButtonsBar.setHeight("30px");
		leftButtonsBar.addStyleName(BbrStyles.TOOLBAR_LAYOUT);

		Button btn_Help = this.getHelpButton();

		HorizontalLayout rightButtonsBar = new HorizontalLayout();
		rightButtonsBar.addComponents(this.btn_Download, this.btn_Refresh, btn_Help);
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
		this.datGrid_ProductsWithoutApproval = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.datGrid_ProductsWithoutApproval.setSortable(false);

		this.initializeDataGridColumns();

		this.datGrid_ProductsWithoutApproval.addStyleName("report-grid");
		this.datGrid_ProductsWithoutApproval.setSizeFull();
		this.datGrid_ProductsWithoutApproval.setSelectionMode(SelectionMode.MULTI);
		this.datGrid_ProductsWithoutApproval.addSelectionListener((SelectionListener<PendingHomologationReportDTO> & Serializable) e -> selectionProductsWithoutApproval_gridHandler(e));
		this.datGrid_ProductsWithoutApproval.addSortListener((SortListener<GridSortOrder<PendingHomologationReportDTO>> & Serializable) e -> dataGridProductsWithoutApproval_sortHandler(e));

		this.mainLayout = new VerticalLayout();
		this.mainLayout.addStyleName("report-layout");
		this.mainLayout.setSizeFull();
		this.mainLayout.setMargin(false);
		this.mainLayout.addComponents(toolBar, this.datGrid_ProductsWithoutApproval, pagingManager);
		this.mainLayout.setExpandRatio(this.datGrid_ProductsWithoutApproval, 1F);
		this.addComponents(mainLayout);

		this.updateButtons(false);
		this.updateSortOrderCriteria(null);

		reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		reportWork.addFunction(new Function<Object, PendingHomologationResultDTO>()
		{
			@Override
			public PendingHomologationResultDTO apply(Object t)
			{
				return executeService(ProductWithoutHomologationReport.this.getBbrUIParent());
			}
		});
		
		if (generateAutomatic)
		{
			this.generateFilterClick();
		}
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				this.doUpdateReport(result, sender);
			}
			else if (triggerObject == this.btn_PendingReprocessing)
			{
				this.doValidationPendingReprocessingResult(result, sender);
			}
			else if (triggerObject == this.btn_ReinjectTX)
			{
				this.doValidationReinjectTXResult(result, sender);
			}
			else if (result != null && result instanceof FileDownloadInfoResultDTO)
			{
				this.doDownloadFile((FileDownloadInfoResultDTO) result, sender);
			}
		}
		else
		{
			ProductWithoutHomologationReport senderReport = (ProductWithoutHomologationReport) sender;
			if (!this.getBbrUIParent().hasAlertWindowOpen())
			{
				this.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));
			}
			senderReport.stopWaiting();
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	@Override
	public void filterApplied_handler(BbrFilterEvent event)
	{
		if ((event != null) && (event.getResultObject() != null))
		{
			this.selectedCompany = (CompanyDataDTO) event.getResultObject();
			this.trackReport = true;
			this.resetPageInfo = true;
			this.startWaiting();
			this.executeBbrWork(reportWork);
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
				return executeDownloadService(ProductWithoutHomologationReport.this.getBbrUIParent(), selectedFormat, btn_DownloadTriggerButton);
			}
		});

		this.startWaiting();
		this.executeBbrWork(downloadsWork);
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

	private void refreshReport_clickHandler(ClickEvent event)
	{
		this.reloadDatingsGrid();
	}

	private void updateButtons(Boolean isSelectionEvent)
	{
		if (this.datGrid_ProductsWithoutApproval != null)
		{
			if (!isSelectionEvent)
			{
				boolean isEmptyGrid = this.datGrid_ProductsWithoutApproval.isEmpty();
				this.btn_DownloadExcel.setEnabled(!isEmptyGrid);
			}

			boolean isEnable = this.datGrid_ProductsWithoutApproval.getSelectedItems() != null && this.datGrid_ProductsWithoutApproval.getSelectedItems().size() > 0;

			this.btn_PendingReprocessing.setEnabled(isEnable);
			this.btn_ReinjectTX.setEnabled(isEnable);

			this.btn_Refresh.setEnabled(selectedCompany != null);
		}
	}

	private void btn_DownloadFile_downloadHandler(ClickEvent e)
	{
		this.btn_DownloadTriggerButton = e.getButton();

		if (this.btn_DownloadTriggerButton == this.btn_DownloadExcel)
		{
			this.selectDownloadFileType(DownloadFormats.XLS, DownloadFormats.XLS, DownloadFormats.CSV);
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private void reloadDatingsGrid()
	{
		this.startWaiting();
		this.trackReport = false;
		this.resetPageInfo = false;
		this.executeBbrWork(reportWork);
	}

	private void initializeDataGridColumns()
	{
		// Fecha
		this.datGrid_ProductsWithoutApproval.addCustomColumn(item -> this.transformDate(item.getRequestdate(), false), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_date"), true)
				.setDescriptionGenerator(item -> this.transformDate(item.getRequestdate(), false), ContentMode.TEXT)
				.setComparator((item1, item2) -> compareDate(item1.getRequestdate(), item2.getRequestdate()))
				.setStyleGenerator(item -> BbrAlignment.CENTER.getValue())
				.setWidth(160)
				.setId(REQUESTDATE);

		// no. OC
		this.datGrid_ProductsWithoutApproval.addCustomColumn(PendingHomologationReportDTO::getOcnumber, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_oc_number"), true)
				.setDescriptionGenerator(PendingHomologationReportDTO::getOcnumber, ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(100)
				.setId(OCNUMBER);

		// Cantidad
		this.datGrid_ProductsWithoutApproval.addCustomColumn(i -> NumericManager.getFormatter(0).format(i.getQuantity()), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_quantity"), true)
				.setDescriptionGenerator(i -> NumericManager.getFormatter(0).format(i.getQuantity()), ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setWidth(100)
				.setId(QUANTITY);

		// Cliente
		this.datGrid_ProductsWithoutApproval.addCustomColumn(PendingHomologationReportDTO::getBuyer, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_buyer"), true)
				.setDescriptionGenerator(PendingHomologationReportDTO::getBuyer, ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue())
				.setWidth(140)
				.setId(BUYER);

		// SKU Cliente
		this.datGrid_ProductsWithoutApproval.addCustomColumn(PendingHomologationReportDTO::getSkubuyer, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_sku_buyer"), true)
				.setDescriptionGenerator(PendingHomologationReportDTO::getSkubuyer, ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue())
				.setWidth(120)
				.setId(SKUBUYER);

		// Descripcion Cliente
		this.datGrid_ProductsWithoutApproval.addCustomColumn(PendingHomologationReportDTO::getDescription, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_desc_buyer"), true)
				.setDescriptionGenerator(PendingHomologationReportDTO::getDescription, ContentMode.TEXT)
				.setStyleGenerator(item -> BbrAlignment.LEFT.getValue())
				.setId(DESCRIPTION);

	}

	public String transformDate(String strString, boolean includetime)
	{
		String result = "";
		if (strString != null)
		{
			LocalDateTime dateTime = PortalDates.getLocalDateTimeformatYYYYMMDD(strString);
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
			LocalDateTime dateTime1 = PortalDates.getLocalDateTimeformatYYYYMMDD(strString1);
			LocalDateTime dateTime2 = PortalDates.getLocalDateTimeformatYYYYMMDD(strString2);

			result = dateTime1.compareTo(dateTime2);

		}
		return result;
	}

	private String formatDate(LocalDateTime date)
	{
		return date != null ? DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(date) : "";
	}

	private PendingHomologationResultDTO executeService(BbrUI bbrUI)
	{
		Integer requestedPage = (!this.resetPageInfo && this.pagingManager.getCurrentPage() > 0) ? (Integer) this.pagingManager.getCurrentPage()
				: ProductWithoutHomologationReport.DEFAULT_PAGE_NUMBER;

		PendingHomologationResultDTO result = null;

		if (this.selectedCompany != null)
		{
			try
			{
				this.getTimingMngr().startTimer();

				this.pendingHomologationinitParamDTO = this.getPendingHomologation();
				this.pendingHomologationinitParamDTO.setPageNumber(requestedPage);
				this.pendingHomologationinitParamDTO.setPaginated(this.resetPageInfo);

				result = EJBFactory.getStockpoolEJBFactory().getStockpoolReportManagerLocal(bbrUI).getPendingHomologationReportWS(this.pendingHomologationinitParamDTO, bbrUI.getUser().getId());
			}
			catch (BbrUserException e)
			{
				AppUtils.getInstance().doLogOut(e.getMessage(), this.getBbrUIParent());
			}
			catch (BbrSystemException e)
			{
				e.printStackTrace();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return result;
	}

	private PendingHomologationInitParamDTO getPendingHomologation()
	{
		PendingHomologationInitParamDTO result = new PendingHomologationInitParamDTO();
		result.setVendorcode(this.selectedCompany.getRut());
		result.setByfilter(true);
		result.setRows(MAX_ROWS_NUMBER);
		result.setOrderby(this.orderCriteria);
		return result;
	}

	private FileDownloadInfoResultDTO executeDownloadService(BbrUI bbrUIParent, DownloadFormats selectedFormat, Button btn_DownloadTriggerButton)
	{
		FileDownloadInfoResultDTO fileResult = null;

		try
		{
			if (this.btn_DownloadTriggerButton == this.btn_DownloadExcel)
			{
				fileResult = EJBFactory.getStockpoolEJBFactory().getStockpoolReportManagerLocal(bbrUIParent).getExcelPendingHomologationReportWS(this.pendingHomologationinitParamDTO, selectedFormat.getValue(), bbrUIParent.getUser().getId(),
						bbrUIParent.getLocale());

			}
		}
		catch (BbrUserException | BbrSystemException e)
		{
			e.printStackTrace();
		}

		return fileResult;
	}

	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{
		String errordescription = "";

		ProductWithoutHomologationReport senderReport = (ProductWithoutHomologationReport) sender;

		if (result != null)
		{
			PendingHomologationResultDTO reportResult = (PendingHomologationResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), true);
			if (!error.hasError())
			{
				if (reportResult.getPendinghomologationitems() != null && reportResult.getPendinghomologationitems().length > 0)
				{
					dataproviderDatingReport = new ListDataProvider<>(Arrays.asList(reportResult.getPendinghomologationitems()));
					senderReport.datGrid_ProductsWithoutApproval.setDataProvider(dataproviderDatingReport, senderReport.resetPageInfo);
					senderReport.updateButtons(false);

					if (senderReport.resetPageInfo)
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
				}
				else
				{
					if (!senderReport.getBbrUIParent().hasAlertWindowOpen())
					{
						senderReport.askToOpenFilter(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
								I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_FILTERS, "empty_filter_question"));
					}
					dataproviderDatingReport = new ListDataProvider<>(Arrays.asList(new PendingHomologationReportDTO[0]));
					senderReport.datGrid_ProductsWithoutApproval.setDataProvider(dataproviderDatingReport, senderReport.resetPageInfo);
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

		this.stopWaiting();

		if (!btn_Refresh.isEnabled())
		{
			this.btn_Refresh.setEnabled(true);
		}
	}

	private void doDownloadFile(FileDownloadInfoResultDTO downloadResult, BbrWorkExecutor senderPostExecute)
	{
		ProductWithoutHomologationReport senderReport = (ProductWithoutHomologationReport) senderPostExecute;

		if (downloadResult != null)
		{
			BbrError error = ErrorsMngr.getInstance().getError(downloadResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				senderReport.downloadLinkFile(downloadResult);
			}
		}

		senderReport.stopWaiting();
	}

	private void selectionProductsWithoutApproval_gridHandler(SelectionEvent<PendingHomologationReportDTO> e)
	{
		this.updateButtons(true);
	}

	private void dataGridProductsWithoutApproval_sortHandler(SortEvent<GridSortOrder<PendingHomologationReportDTO>> e)
	{

		// this.startWaiting();
		this.updateSortOrderCriteria(e.getSortOrder());
		this.trackReport = false;
		this.resetPageInfo = true;
		this.executeBbrWork(reportWork);
	}

	private void updateSortOrderCriteria(List<GridSortOrder<PendingHomologationReportDTO>> sortOrderList)
	{
		if (sortOrderList != null && !sortOrderList.isEmpty())
		{
			ArrayList<OrderCriteriaDTO> resultList = new ArrayList<>();
			for (GridSortOrder<PendingHomologationReportDTO> sorOrder : sortOrderList)
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

			GridSortOrder<PendingHomologationReportDTO> sortOrder = new GridSortOrder<>(datGrid_ProductsWithoutApproval.getColumn(DEFAULT_SORT_FIELD), SortDirection.ASCENDING);
			sortOrderList = new ArrayList<GridSortOrder<PendingHomologationReportDTO>>();
			sortOrderList.add(sortOrder);

			this.datGrid_ProductsWithoutApproval.setSortOrder(sortOrderList);
		}
	}

	private void btn_ReinjectTX_clickHandler(ClickEvent e)
	{
		boolean statusIncorrectForReinjectTX = this.datGrid_ProductsWithoutApproval.getSelectedItems() != null && this.datGrid_ProductsWithoutApproval.getSelectedItems().size() > 0;
		if (!statusIncorrectForReinjectTX)
		{
			this.showErrorMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_LOGISTIC, "valid_reinject_tx_interface_message"));
		}
		else
		{
			BbrMessagesBoxUtils.showYesNoQuestionMessage(this.getBbrUIParent(), I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
					I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_LOGISTIC, "confirmation_reinject_tx_interface_action_question"), () -> this.doReinjectTXInterface(true),
					() -> this.doReinjectTXInterface(false));
		}
	}

	private void doReinjectTXInterface(boolean isAcept)
	{
		if (isAcept)
		{
			reportWorkReinjectTX = new BbrWork<>(this, this.getBbrUIParent(), this.btn_ReinjectTX);
			reportWorkReinjectTX.addFunction(new Function<Object, BaseResultsDTO>()
			{
				@Override
				public BaseResultsDTO apply(Object t)
				{
					return executeReinjectTXInterfaceService(ProductWithoutHomologationReport.this.getBbrUIParent(), ProductWithoutHomologationReport.this.btn_ReinjectTX);
				}
			});

			this.startWaiting();
			this.executeBbrWork(reportWorkReinjectTX);
		}
	}

	private BaseResultsDTO executeReinjectTXInterfaceService(BbrUI bbrUIParent, Button triggerButton)
	{
		BaseResultsDTO result = null;
		try
		{
			// Start Tracking
			this.getTimingMngr().startTimer();

			ResendTxInitParamDTO initParamDTO = this.getResendTxInitParamDTO();

			if (triggerButton == this.btn_ReinjectTX)
			{
				result = EJBFactory.getStockpoolEJBFactory().getStockpoolReportManagerLocal(bbrUIParent).reSendMessageWS(initParamDTO, this.getUser().getId());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

	private ResendTxInitParamDTO getResendTxInitParamDTO()
	{
		ResendTxInitParamDTO resentsTX = null;
		ArrayList<PendingHomologationReportDTO> lstProducts = this.datGrid_ProductsWithoutApproval.getSelectedItems().stream().collect(Collectors.toCollection(ArrayList::new));
		if (lstProducts != null)
		{
			List<ResendDTO> resend = new ArrayList<>();

			for (PendingHomologationReportDTO p : lstProducts)
			{
				ResendDTO resendTx = new ResendDTO();
				resendTx.setBuyerid(p.getBuyerid());
				resendTx.setOcnumber(p.getOcnumber());
				resendTx.setSkubuyer(p.getSkubuyer());
				resendTx.setVendorcode(this.selectedCompany.getRut());

				resend.add(resendTx);
			}

			resentsTX = new ResendTxInitParamDTO();
			resentsTX.setResend(resend);
		}

		return resentsTX;
	}

	private void btn_PendingReprocessing_clickHandler(ClickEvent e)
	{
		boolean statusIncorrectForPendingReprocessing = this.datGrid_ProductsWithoutApproval.getSelectedItems() != null && this.datGrid_ProductsWithoutApproval.getSelectedItems().size() > 0;
		if (!statusIncorrectForPendingReprocessing)
		{
			this.showErrorMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_LOGISTIC, "valid_pending_reprocessing_interface_message"));
		}
		else
		{
			BbrMessagesBoxUtils.showYesNoQuestionMessage(this.getBbrUIParent(), I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
					I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_LOGISTIC, "confirmation_pending_reprocessing_interface_action_question"), () -> this.doPendingReprocessingInterface(true),
					() -> this.doPendingReprocessingInterface(false));
		}
	}

	private void doPendingReprocessingInterface(boolean isAcept)
	{
		if (isAcept)
		{
			reportWorkPendingReprocessing = new BbrWork<>(this, this.getBbrUIParent(), this.btn_PendingReprocessing);
			reportWorkPendingReprocessing.addFunction(new Function<Object, BaseResultsDTO>()
			{
				@Override
				public BaseResultsDTO apply(Object t)
				{
					return executePendingReprocessingInterfaceService(ProductWithoutHomologationReport.this.getBbrUIParent(), ProductWithoutHomologationReport.this.btn_PendingReprocessing);
				}
			});

			this.startWaiting();
			this.executeBbrWork(reportWorkPendingReprocessing);
		}
	}

	private BaseResultsDTO executePendingReprocessingInterfaceService(BbrUI bbrUIParent, Button triggerButton)
	{
		BaseResultsDTO result = null;
		try
		{
			// Start Tracking
			this.getTimingMngr().startTimer();

			DeletePendingItemsInitParamDTO initParamDTO = new DeletePendingItemsInitParamDTO();
			initParamDTO.setPendingitemsdto(this.getpendingItems());
			initParamDTO.setResponsibleuser(this.getUser().getFullName());
			if (triggerButton == this.btn_PendingReprocessing)
			{
				result = EJBFactory.getStockpoolEJBFactory().getStockpoolReportManagerLocal(bbrUIParent).doDeletePendingItemsWS(initParamDTO, this.getUser().getId());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

	private List<PendingItemsDTO> getpendingItems()
	{
		List<PendingItemsDTO> lstPending = null;
		ArrayList<PendingHomologationReportDTO> lstProducts = this.datGrid_ProductsWithoutApproval.getSelectedItems().stream().collect(Collectors.toCollection(ArrayList::new));
		if (lstProducts != null)
		{
			lstPending = new ArrayList<>();

			for (PendingHomologationReportDTO p : lstProducts)
			{
				PendingItemsDTO pendingItem = new PendingItemsDTO();
				pendingItem.setId(p.getPendinghomologationid());
				pendingItem.setOcnumber(p.getOcnumber());
				pendingItem.setQuantity(p.getQuantity());
				pendingItem.setSkubuyer(p.getSkubuyer());
				pendingItem.setSkuvendor("");

				lstPending.add(pendingItem);
			}
		}

		return lstPending;
	}

	private void doValidationPendingReprocessingResult(Object result, BbrWorkExecutor sender)
	{
		ProductWithoutHomologationReport senderReport = (ProductWithoutHomologationReport) sender;

		if (result != null)
		{
			BaseResultsDTO reportResult = (BaseResultsDTO) result;

			// MULTIPLES ALERTAS DE MENSAJES
			boolean canOpenAlert = !senderReport.getBbrUIParent().hasAlertWindowOpen();

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), false);

			if (!error.hasError())
			{
				this.showInfoMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "successful_operation"));
			}
			else
			{
				if (reportResult.getBaseresults() != null && reportResult.getBaseresults().length > 0)
				{
					if (canOpenAlert)
					{
						BbrErrorList<BaseResultDTO> winErrors = new BbrErrorList<>(senderReport.getBbrUIParent(),
								reportResult.getBaseresults(), senderReport.getUser().getLocale());
						winErrors.setCloseButtonStyle("btn-generic");
						winErrors.setConverterFunction(new Function<BaseResultDTO, BbrError>()
						{
							@Override
							public BbrError apply(BaseResultDTO t)
							{
								BbrError result = new BbrError(t.getStatuscode(), t.getStatusmessage());

								return result;
							}
						});
						winErrors.initializeView();
						winErrors.setWidth("350px");
						winErrors.setHeight("350px");
						winErrors.open(true);
					}

				}
				else
				{
					ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), canOpenAlert);
				}
			}

		}

		senderReport.stopWaiting();

		senderReport.startWaiting();
		senderReport.executeBbrWork(reportWork);
	}

	private void doValidationReinjectTXResult(Object result, BbrWorkExecutor sender)
	{
		ProductWithoutHomologationReport senderReport = (ProductWithoutHomologationReport) sender;

		if (result != null)
		{
			BaseResultsDTO reportResult = (BaseResultsDTO) result;

			// MULTIPLES ALERTAS DE MENSAJES
			boolean canOpenAlert = !senderReport.getBbrUIParent().hasAlertWindowOpen();

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), false);

			if (!error.hasError())
			{
				this.showInfoMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "successful_operation"));
			}
			else
			{
				if (reportResult.getBaseresults() != null && reportResult.getBaseresults().length > 0)
				{
					if (canOpenAlert)
					{
						BbrErrorList<BaseResultDTO> winErrors = new BbrErrorList<>(senderReport.getBbrUIParent(),
								reportResult.getBaseresults(), senderReport.getUser().getLocale());
						winErrors.setCloseButtonStyle("btn-generic");
						winErrors.setConverterFunction(new Function<BaseResultDTO, BbrError>()
						{
							@Override
							public BbrError apply(BaseResultDTO t)
							{
								BbrError result = new BbrError(t.getStatuscode(), t.getStatusmessage());

								return result;
							}
						});
						winErrors.initializeView();
						winErrors.setWidth("350px");
						winErrors.setHeight("350px");
						winErrors.open(true);
					}

				}
				else
				{
					ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), canOpenAlert);
				}
			}

		}

		senderReport.stopWaiting();

		senderReport.startWaiting();
		senderReport.executeBbrWork(reportWork);
	}
	
	private void generateFilterClick()
	{
		ClickEvent e = new ClickEvent(filterLayout);
		filterLayout.buttonClick(e);
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
}