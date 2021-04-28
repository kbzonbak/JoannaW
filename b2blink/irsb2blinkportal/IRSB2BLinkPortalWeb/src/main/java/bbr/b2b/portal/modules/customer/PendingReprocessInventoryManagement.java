package bbr.b2b.portal.modules.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.SortEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.customer.report.classes.PendingProcessInventoryDetailInitParamDTO;
import bbr.b2b.customer.report.classes.PendingReprocessInventoryDTO;
import bbr.b2b.customer.report.classes.PendingReprocessInventoryDetailDTO;
import bbr.b2b.customer.report.classes.PendingReprocessInventoryDetailResultDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.wrappers.customer.ControlPanelCardInfo;
import bbr.b2b.portal.classes.wrappers.customer.PendingReprocessInventorySectionInfo;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.filters.customer_service.PendingReprocessFilter;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.basics.BbrPage;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.constants.DownloadFormats;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.utilities.NumericManager;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.grid.renderer.ShortDateRenderer;
import cl.bbr.core.components.paging.BbrPagingManager;

public class PendingReprocessInventoryManagement extends BbrModule implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants
	private static final long									serialVersionUID			= -3947159600615633249L;
	public static final String									LAST_INVENTORY_SEND			= "last_inventory_send";
	public static final String									LAST_SALES_SEND				= "last_sales_send";
	public static final String									LATE_SALES					= "late_sales";
	public static final String									NOT_LOADED_PRODUCT			= "not_loaded_product";
	public static final String									NOT_LOADED_LOCAL			= "not_loaded_local";
	public static final String									PENDING_REPROCESS_SALES		= "pending_reprocess_sales";
	public static final String									PENDING_REPROCESS_INVENTORY	= "pending_reprocess_inventory";
	public static final String									CONTROL_PANEL_GRID_STYLE	= "control-panel-grid bbr-overflow-auto";
	private static final int									MAX_ROWS_NUMBER				= 50;
	private static final int									DEFAULT_PAGE_NUMBER			= 1;
	private static final String									DEFAULT_SORT_FIELD			= "date";
	// Components
	private Button												btn_Download				= null;
	private BbrPagingManager									pagingManager				= null;
	private BbrAdvancedGrid<PendingReprocessInventoryDetailDTO>	dgd_NotLoadedLocal			= null;
	// Variables
	private BbrWork<PendingReprocessInventoryDetailResultDTO>	reportWork					= null;
	private CompanyDataDTO										selectedCompany				= null;
	private List<PendingReprocessInventoryDetailDTO>			providerDetails				= new ArrayList<>();
	private ControlPanelCardInfo								cardInfo					= null;
	private BbrWork<FileDownloadInfoResultDTO>					downloadsWork				= null;
	private boolean												resetPageInfo				= true;
	private PendingReprocessInventoryDTO						selectedClient				= null;
	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public PendingReprocessInventoryManagement(BbrUI bbrUIParent)
	{
		super(bbrUIParent);
	}

	public PendingReprocessInventoryManagement(BbrUI bbrUIParent, ControlPanelCardInfo cardInfo)
	{
		super(bbrUIParent);
		this.cardInfo = cardInfo;
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
		PendingReprocessFilter filterLayout = new PendingReprocessFilter(this, this.cardInfo);
		filterLayout.initializeView();
		this.setBbrFilterContainer(filterLayout);

		this.btn_Download = new Button("", EnumToolbarButton.DOWNLOAD_PRIMARY.getResource());
		this.btn_Download.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "download_report"));
		this.btn_Download.addClickListener((ClickListener & Serializable) e -> btn_DownloadFile_downloadHandler(e));
		this.btn_Download.addStyleName("toolbar-button");

		// Paging Manager
		this.pagingManager = new BbrPagingManager();
		this.pagingManager.setLocale(this.getUser().getLocale());
		this.pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> pagingChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);

		// Grilla
		this.dgd_NotLoadedLocal = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.dgd_NotLoadedLocal.addStyleName("report-grid");
		this.dgd_NotLoadedLocal.setSizeFull();
		this.dgd_NotLoadedLocal.setPagingManager(pagingManager, DEFAULT_SORT_FIELD);
		this.initializeDataGridNotLoadedLocal();
		this.dgd_NotLoadedLocal.addSortListener(this::dataGrid_sortHandler);

		Button btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
		btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
		btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler(e));
		btn_Refresh.addStyleName("toolbar-button");

		HorizontalLayout rightButtonsBar = new HorizontalLayout();
		rightButtonsBar.addComponents(this.btn_Download, btn_Refresh);
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
		mainLayout.addComponent(toolBar);
		mainLayout.addComponent(this.dgd_NotLoadedLocal);
		mainLayout.setExpandRatio(this.dgd_NotLoadedLocal, 1F);
		mainLayout.addComponent(this.pagingManager);

		this.addComponents(mainLayout);

		reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		reportWork.addFunction(new Function<Object, PendingReprocessInventoryDetailResultDTO>()
		{
			@Override
			public PendingReprocessInventoryDetailResultDTO apply(Object t)
			{
				return executeService(PendingReprocessInventoryManagement.this.getBbrUIParent());
			}
		});

		if (this.cardInfo != null)
		{
			ClickEvent e = new ClickEvent(filterLayout);
			filterLayout.buttonClick(e);
		}
	}

	private void initializeDataGridNotLoadedLocal()
	{
		this.dgd_NotLoadedLocal.addCustomColumn(PendingReprocessInventoryDetailDTO::getDate,
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "date"))
				.setStyleGenerator(i -> BbrAlignment.CENTER.getValue())
				.setRenderer(new ShortDateRenderer())
				.setId(DEFAULT_SORT_FIELD);
		this.dgd_NotLoadedLocal.addCustomColumn(PendingReprocessInventoryDetailDTO::getClientcode,
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_client_code"))
				.setStyleGenerator(i -> BbrAlignment.LEFT.getValue())
				.setId("clientcode");
		this.dgd_NotLoadedLocal.addCustomColumn(PendingReprocessInventoryDetailDTO::getClientname,
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_client_name"))
				.setStyleGenerator(i -> BbrAlignment.LEFT.getValue())
				.setId("clientname");
		this.dgd_NotLoadedLocal.addCustomColumn(PendingReprocessInventoryDetailDTO::getSkuclient,
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "sku"))
				.setStyleGenerator(i -> BbrAlignment.LEFT.getValue())
				.setId("skuclient");
		this.dgd_NotLoadedLocal.addCustomColumn(PendingReprocessInventoryDetailDTO::getLocalclient,
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_local_code"))
				.setStyleGenerator(i -> BbrAlignment.LEFT.getValue())
				.setId("localclient");
		this.dgd_NotLoadedLocal.addCustomColumn(PendingReprocessInventoryDetailDTO::getStockunits,
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_stock_units"))
				.setStyleGenerator(i -> BbrAlignment.RIGHT.getValue())
				.setRenderer(new NumberRenderer(NumericManager.getFormatter(0)))
				.setId("stockunits");
		this.dgd_NotLoadedLocal.addCustomColumn(PendingReprocessInventoryDetailDTO::getStockamount,
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_stock_amount"))
				.setStyleGenerator(i -> BbrAlignment.RIGHT.getValue())
				.setRenderer(new NumberRenderer(NumericManager.getTwoDecimalFormatter()))
				.setId("stockamount");
		this.dgd_NotLoadedLocal.addCustomColumn(PendingReprocessInventoryDetailDTO::getTransit,
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "transit"))
				.setStyleGenerator(i -> BbrAlignment.CENTER.getValue())
				.setId("transit");
		this.dgd_NotLoadedLocal.addCustomColumn(PendingReprocessInventoryDetailDTO::getQ,
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "break"))
				.setStyleGenerator(i -> BbrAlignment.RIGHT.getValue())
				.setId("q");
		this.dgd_NotLoadedLocal.addCustomColumn(PendingReprocessInventoryDetailDTO::getInstock,
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "instock"))
				.setStyleGenerator(i -> BbrAlignment.RIGHT.getValue())
				.setId("isntock");
		this.dgd_NotLoadedLocal.addCustomColumn(PendingReprocessInventoryDetailDTO::getMix,
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "mix"))
				.setStyleGenerator(i -> BbrAlignment.RIGHT.getValue())
				.setId("mix");
		this.dgd_NotLoadedLocal.addCustomColumn(PendingReprocessInventoryDetailDTO::getDaysales,
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "daysales"))
				.setStyleGenerator(i -> BbrAlignment.RIGHT.getValue())
				.setId("daysales");
	}

	@Override
	public void filterApplied_handler(BbrFilterEvent event)
	{
		if ((event != null) && (event.getResultObject() != null))
		{
			PendingReprocessInventorySectionInfo info = (PendingReprocessInventorySectionInfo) event.getResultObject();
			this.selectedCompany = info.getSelectedCompany();
			this.selectedClient = info.getSelectedClient();

			this.startWaiting();
			this.executeBbrWork(reportWork);
		}
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		PendingReprocessInventoryManagement senderReport = (PendingReprocessInventoryManagement) sender;

		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				senderReport.doUpdateReport(result, senderReport);
			}
			else if (triggerObject == this.btn_DownloadTriggerButton)
			{
				senderReport.doDownloadFile(result, sender, triggerObject);
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
	// BEGINNING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================
	private void refreshReport_clickHandler(ClickEvent event)
	{
		this.startWaiting();

		this.executeBbrWork(reportWork);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private PendingReprocessInventoryDetailResultDTO executeService(BbrUI bbrUIParent)
	{
		PendingReprocessInventoryDetailResultDTO result = new PendingReprocessInventoryDetailResultDTO();

		try
		{
			PendingProcessInventoryDetailInitParamDTO initParamDTO = getInitParam(bbrUIParent);
			result = EJBFactory.getCustomerEJBFactory().getCustomerManagerLocal(bbrUIParent).getPendingReprocessInventoryDetail(initParamDTO);
		}
		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), bbrUIParent);
		}
		catch (BbrSystemException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

	private PendingProcessInventoryDetailInitParamDTO getInitParam(BbrUI bbrUIParent)
	{
		if (this.cardInfo == null)
		{
			this.cardInfo = new ControlPanelCardInfo();
			this.cardInfo.setClientId(-1L);
			this.cardInfo.setValue("0");
		}

		String pvcode = this.selectedCompany != null ? this.selectedCompany.getRut() : null;
		PendingProcessInventoryDetailInitParamDTO initParamDTO = new PendingProcessInventoryDetailInitParamDTO();
		initParamDTO.setClkey(this.selectedClient != null ? selectedClient.getClkey() : this.cardInfo.getClientId());
		initParamDTO.setCount(this.selectedClient != null ? new Long(selectedClient.getCount()).intValue() : Integer.valueOf(this.cardInfo.getValue()));
		initParamDTO.setLocale(bbrUIParent.getUser().getLocale());
		initParamDTO.setNeedPageInfo(true);
		Integer requestedPage = (!resetPageInfo && this.pagingManager.getCurrentPage() > 0) ? (Integer) this.pagingManager.getCurrentPage() : DEFAULT_PAGE_NUMBER;
		initParamDTO.setPageNumber(requestedPage);
		initParamDTO.setPvcode(pvcode);
		initParamDTO.setRows(MAX_ROWS_NUMBER);
		return initParamDTO;
	}

	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{

		PendingReprocessInventoryManagement senderReport = (PendingReprocessInventoryManagement) sender;

		if (result != null)
		{
			PendingReprocessInventoryDetailResultDTO reportResult = (PendingReprocessInventoryDetailResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				PendingReprocessInventoryDetailDTO[] provider = reportResult.getPendingReprocessInventoryDetailDTOs();
				senderReport.setDetailsDataProvider(senderReport, Arrays.asList(provider));

				if (senderReport.resetPageInfo)
				{
					PageInfoDTO pageInfo = reportResult.getPageInfoDTO();

					BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
					senderReport.pagingManager.setPages(bbrPage, senderReport.resetPageInfo);
				}
				// ESTO DEBE ABRIR EL FILTRO DE NUEVO AL NO ENCONTRAR
				// RESULTADOS;
				// ACA SE DEBEN EVALUAR LOS POSIBLES RESULTADOS
				// if (reportResult.getProductlists().length == 0)
				// {
				// senderReport.askToOpenFilter(I18NManager.getI18NString(senderReport.getBbrUIParent(),
				// BbrUtilsResources.RES_GENERIC, "windows_title_info"),
				// I18NManager.getI18NString(senderReport.getBbrUIParent(),
				// BbrUtilsResources.RES_FILTERS, "empty_filter_question"));
				// }
			}
		}
		senderReport.stopWaiting();
	}

	private void dataGrid_sortHandler(SortEvent<GridSortOrder<PendingReprocessInventoryDetailDTO>> e)
	{
		if (e.isUserOriginated() && pagingManager.getTotalsPages() > 1)
		{
			this.resetPageInfo = false;
			this.executeBbrWork(this.reportWork);
		}
	}

	private void pagingChange_Listener(BbrPagingEvent e)
	{
		if ((e != null) && e.getEventType().equals(BbrPagingEvent.PAGE_CHANGED) && (e.getResultObject() != null))
		{
			this.resetPageInfo = false;
			this.startWaiting();
			this.executeBbrWork(this.reportWork);
		}
	}

	private void setDetailsDataProvider(PendingReprocessInventoryManagement senderReport, List<PendingReprocessInventoryDetailDTO> provider)
	{
		senderReport.providerDetails = provider;
		ListDataProvider<PendingReprocessInventoryDetailDTO> dataprovider = new ListDataProvider<>(senderReport.providerDetails);
		senderReport.dgd_NotLoadedLocal.setDataProvider(dataprovider, senderReport.resetPageInfo);
	}

	private void btn_DownloadFile_downloadHandler(ClickEvent e)
	{
		this.btn_DownloadTriggerButton = e.getButton();

		downloadsWork = new BbrWork<>(this, this.getBbrUIParent(), btn_DownloadTriggerButton);
		downloadsWork.addFunction(new Function<Object, FileDownloadInfoResultDTO>()
		{
			@Override
			public FileDownloadInfoResultDTO apply(Object t)
			{
				return executeDownloadService(PendingReprocessInventoryManagement.this.getBbrUIParent(), DownloadFormats.XLSX, btn_DownloadTriggerButton);
			}
		});

		this.startWaiting();
		this.executeBbrWork(downloadsWork);
	}

	private FileDownloadInfoResultDTO executeDownloadService(BbrUI bbrUIParent, DownloadFormats selectedFormat, Button downloadTriggerButton)
	{
		FileDownloadInfoResultDTO fileResult = null;
		if (selectedFormat != null)
		{
			try
			{
				if (this.btn_DownloadTriggerButton == this.btn_Download)
				{
					String pvcode = this.selectedCompany != null ? this.selectedCompany.getRut() : null;
					PendingProcessInventoryDetailInitParamDTO initParamDTO = new PendingProcessInventoryDetailInitParamDTO();
					initParamDTO.setClkey(this.selectedClient != null ? selectedClient.getClkey() : this.cardInfo.getClientId());
					initParamDTO.setCount(this.selectedClient != null ? new Long(selectedClient.getCount()).intValue() : Integer.valueOf(this.cardInfo.getValue()));
					initParamDTO.setLocale(bbrUIParent.getUser().getLocale());
					initParamDTO.setNeedPageInfo(false);
					initParamDTO.setPvcode(pvcode);
					fileResult = EJBFactory.getCustomerEJBFactory().getCustomerManagerLocal(bbrUIParent).downloadPendingReprocessInventoryDetail(initParamDTO, bbrUIParent.getUser().getId());
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
		}

		return fileResult;
	}

	private void doDownloadFile(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		PendingReprocessInventoryManagement senderReport = (PendingReprocessInventoryManagement) sender;
		if (senderReport != null)
		{
			senderReport.downloadLinkFile(result);
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
