package bbr.b2b.portal.modules.fep;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.ChartOptions;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Cursor;
import com.vaadin.addon.charts.model.DataLabels;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.Lang;
import com.vaadin.addon.charts.model.LayoutDirection;
import com.vaadin.addon.charts.model.Legend;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.VerticalAlign;
import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.SortEvent;
import com.vaadin.event.SortEvent.SortListener;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.components.grid.ItemClickListener;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.fep.cp.data.classes.CPItemByArticleInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsByArticleTypeArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsByArticleTypeDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsByFilterInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsResumeDataDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsResumeDataResultDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.wrappers.fep.ProductsManagementFilterSearch;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.filters.fep.ProductsManagementFilter;
import bbr.b2b.portal.components.modules.fep.ViewProductInformation;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.FEPConstants;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.basics.BbrPage;
import cl.bbr.core.classes.basics.BbrUser;
import cl.bbr.core.classes.constants.DownloadFormats;
import cl.bbr.core.classes.constants.TrackingConstants;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.utilities.NumericManager;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrVerticalLayout;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.paging.BbrPagingManager;

public class ProductsManagement extends BbrModule implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long										serialVersionUID				= 1282944624651942272L;
	private static final String									ARTICLE_TYPE_NAME				= "articletypename";
	private static final String									PENDING_COUNT					= "pendingcount";
	private static final String									APPROVED_COUNT					= "approvedcount";
	private static final String									REJECTED_COUNT					= "rejectedcount";
	private final int													DEFAULT_PAGE_NUMBER			= 1;
	private final int													MAX_ROWS_NUMBER				= 200;
	private final String												DEFAULT_SORT_FIELD			= ARTICLE_TYPE_NAME;

	private VerticalLayout											mainLayout						= null;
	private BbrAdvancedGrid<CPItemsByArticleTypeDTO>		datGrid_Products				= null;
	private Button														btn_ProductInformation		= null;
	private Button														btn_DownloadProductsResume	= null;
	private Button														btn_Refresh						= null;
	private Chart														chart_ProductState			= null;
	private BbrVerticalLayout										chartLayout						= null;

	private BbrPagingManager										pagingManager					= null;
	private CPItemsByFilterInitParamDTO							initParamFilter				= null;
	private CPItemByArticleInitParamDTO							initParamArticle				= null;
	private Boolean													trackReport						= true;
	private Boolean													resetPageInfo					= true;
	private OrderCriteriaDTO[]										orderCriteria					= null;
	private BbrWork<CPItemsByArticleTypeArrayResultDTO>	reportWork						= null;
	private BbrWork<CPItemsResumeDataResultDTO>				chartWork						= null;
	private BbrWork<FileDownloadInfoResultDTO>				downloadsWork					= null;
	private CompanyDataDTO											selectedProvider				= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ProductsManagement(BbrUI bbrUIParent)
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
	public void initializeView()
	{
		BbrUser user = this.getUser();

		if (user != null)
		{
			// Filtro
			ProductsManagementFilter filterLayout = new ProductsManagementFilter(this);
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

			this.btn_ProductInformation = new Button("", EnumToolbarButton.SEARCH_PRIMARY.getResource());
			this.btn_ProductInformation.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "product_information"));
			this.btn_ProductInformation.addClickListener((ClickListener & Serializable) e -> this.btn_ProductInformation_clickHandler(e));
			this.btn_ProductInformation.addStyleName("toolbar-button");

			leftButtonsBar.addComponents(this.btn_ProductInformation);

			this.btn_DownloadProductsResume = new Button("", EnumToolbarButton.DOWNLOAD_PRIMARY.getResource());
			this.btn_DownloadProductsResume.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_products_resume"));
			this.btn_DownloadProductsResume.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadFile_downloadHandler(e));
			this.btn_DownloadProductsResume.addStyleName("toolbar-button");

			this.btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
			this.btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
			this.btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler(e));
			this.btn_Refresh.addStyleName("toolbar-button");

			// Barra superior dercha
			HorizontalLayout rightButtonsBar = new HorizontalLayout();
			rightButtonsBar.addComponents(this.btn_DownloadProductsResume, this.btn_Refresh);
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
			this.datGrid_Products = new BbrAdvancedGrid<>(this.getUser().getLocale());
			this.datGrid_Products.setSortable(false);

			this.initializeDataGridColumns();

			this.datGrid_Products.addStyleName("report-grid");
			this.datGrid_Products.setSizeFull();
			this.datGrid_Products.setPagingManager(pagingManager, this.DEFAULT_SORT_FIELD);
			this.datGrid_Products.setSelectionMode(SelectionMode.SINGLE);
			this.datGrid_Products.addSelectionListener((SelectionListener<CPItemsByArticleTypeDTO> & Serializable) e -> selection_gridHandler(e));
			this.datGrid_Products.addSortListener((SortListener<GridSortOrder<CPItemsByArticleTypeDTO>> & Serializable) e -> dataGrid_sortHandler(e));
			this.datGrid_Products.addItemClickListener((ItemClickListener<CPItemsByArticleTypeDTO> & Serializable) e -> dgdItem_clickHandler(e));

			VerticalLayout gridLayout = new VerticalLayout();
			gridLayout.setSizeFull();
			gridLayout.setMargin(false);
			gridLayout.addComponents(this.datGrid_Products, pagingManager);
			gridLayout.setExpandRatio(this.datGrid_Products, 1F);

			this.initializeChart();

			HorizontalLayout dataLayout = new HorizontalLayout();
			dataLayout.setSizeFull();
			dataLayout.setMargin(false);
			dataLayout.addComponents(gridLayout, chartLayout);
			dataLayout.setExpandRatio(gridLayout, 1F);
			dataLayout.setExpandRatio(chartLayout, 1F);

			this.mainLayout = new VerticalLayout();
			mainLayout.addStyleName("report-layout");
			mainLayout.setSizeFull();
			mainLayout.setMargin(false);
			mainLayout.addComponents(toolBar, dataLayout);
			mainLayout.setExpandRatio(dataLayout, 2F);

			this.addComponents(mainLayout);

			this.updateButtons(false);

			this.updateSortOrderCriteria(null);

			reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
			reportWork.addFunction(new Function<Object, CPItemsByArticleTypeArrayResultDTO>()
			{
				@Override
				public CPItemsByArticleTypeArrayResultDTO apply(Object t)
				{
					return executeService(ProductsManagement.this.getBbrUIParent());
				}
			});

			chartWork = new BbrWork<>(this, this.getBbrUIParent(), chartLayout);
			chartWork.addFunction(new Function<Object, CPItemsResumeDataResultDTO>()
			{
				@Override
				public CPItemsResumeDataResultDTO apply(Object t)
				{
					return executeChartService(ProductsManagement.this.getBbrUIParent());
				}
			});
		}
		else
		{
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P0016"));
		}
	}


	@Override
	public void filterApplied_handler(BbrFilterEvent event)
	{
		if ((event != null) && (event.getResultObject() != null))
		{
			this.resetPageInfo = true;
			this.trackReport = true;

			ProductsManagementFilterSearch filterSearch = (ProductsManagementFilterSearch) event.getResultObject();
			
			if(filterSearch != null)
			{
				this.initParamFilter = filterSearch.getInitParam();

				this.initParamArticle = new CPItemByArticleInitParamDTO();
				this.initParamArticle.setProviderid(this.initParamFilter.getProviderid());
				this.initParamArticle.setProvidercode(this.initParamFilter.getProvidercode());
				this.initParamArticle.setTrade(this.initParamFilter.getTradefilter());
				this.initParamArticle.setSkus(this.initParamFilter.getSkufilter());

				this.initParamArticle.setOrderBy(this.orderCriteria);
				this.initParamArticle.setRows(MAX_ROWS_NUMBER);

				this.initParamFilter.setOrderBy(this.orderCriteria);
				this.initParamFilter.setRows(MAX_ROWS_NUMBER);

				this.selectedProvider = filterSearch.getSelectedCompany();

				this.startWaiting();
				this.executeBbrWork(reportWork);

				this.chartLayout.startWaiting();
				this.executeBbrWork(chartWork);
			}
		}
	}


	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		ProductsManagement bbrSender = (ProductsManagement) sender;
		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				bbrSender.doUpdateReport(result, sender);
			}
			if (triggerObject instanceof BbrVerticalLayout)
			{
				this.doUpdateChart(result, sender);
			}
			else if (triggerObject == this.btn_DownloadTriggerButton)
			{
				bbrSender.doDownloadFile(result, sender, triggerObject);
			}
		}
		else
		{
			bbrSender.showErrorMessage(I18NManager.getI18NString(bbrSender.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
			I18NManager.getI18NString(bbrSender.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));
		}
//		bbrSender.stopWaiting();
//		bbrSender.chartLayout.stopWaiting();
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
				return executeDownloadService(ProductsManagement.this.getBbrUIParent(), selectedFormat);
			}
		});

		this.startWaiting();
		this.executeBbrWork(downloadsWork);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	private void btn_DownloadFile_downloadHandler(ClickEvent e)
	{
		this.btn_DownloadTriggerButton = e.getButton();
		
		if (this.btn_DownloadTriggerButton == this.btn_DownloadProductsResume)
		{
			this.selectDownloadFileType(DownloadFormats.XLS, DownloadFormats.XLS, DownloadFormats.CSV);
		}
	}


	private void btn_ProductInformation_clickHandler(ClickEvent event)
	{
		if ((this.datGrid_Products.getSelectedItems() != null) && (this.datGrid_Products.getSelectedItems().size() > 0))
		{
			CPItemsByArticleTypeDTO selectedProduct = this.datGrid_Products.getSelectedItems().iterator().next();

			ViewProductInformation productInfoWin = new ViewProductInformation(this.getBbrUIParent(), selectedProduct, this.selectedProvider, this.initParamFilter);
			productInfoWin.addBbrEventListener((BbrEventListener & Serializable) e -> this.products_updateHandler(e)); 
			productInfoWin.open(true, true, this);
		}
	}


	private void selection_gridHandler(SelectionEvent<CPItemsByArticleTypeDTO> e)
	{
		this.updateButtons(true);
	}


	private void dgdItem_clickHandler(ItemClick<CPItemsByArticleTypeDTO> e)
	{
		if (e.getMouseEventDetails().isDoubleClick() && e.getItem() != null)
		{
			this.btn_ProductInformation_clickHandler(null);
		}
	}


	private void dataGrid_sortHandler(SortEvent<GridSortOrder<CPItemsByArticleTypeDTO>> e)
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
		this.startWaiting();

		this.trackReport = false;
		this.resetPageInfo = false;
		//this.executeBbrWork(reportWork);
		
		this.startWaiting();
		this.executeBbrWork(reportWork);

		this.chartLayout.startWaiting();
		this.executeBbrWork(chartWork);
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
	// =====================================================================================================================================
	// ENDING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private void initializeDataGridColumns()
	{
		this.datGrid_Products
		.addCustomColumn(CPItemsByArticleTypeDTO::getArticletypename, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_article_type_name"), true)
		.setId(ARTICLE_TYPE_NAME);
		
		this.datGrid_Products.addCustomComponentColumn(item -> this.getGridLinkButton(item, PENDING_COUNT),I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_pending"), true)
		.setStyleGenerator(item -> "v-align-right")
		.setId(PENDING_COUNT);
		
		this.datGrid_Products.addCustomComponentColumn(item -> this.getGridLinkButton(item, APPROVED_COUNT),I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_approved"), true)
		.setStyleGenerator(item -> "v-align-right")
		.setId(APPROVED_COUNT);
		
		this.datGrid_Products.addCustomComponentColumn(item -> this.getGridLinkButton(item, REJECTED_COUNT),I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_rejected"), true)
		.setStyleGenerator(item -> "v-align-right")
		.setId(REJECTED_COUNT);
		
		HeaderRow newHeader = this.datGrid_Products.prependHeaderRow();

		newHeader.join(PENDING_COUNT, APPROVED_COUNT, REJECTED_COUNT)
		.setHtml(BbrUtils.getGridHeaderFormattedCellText(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_item_count"), true));
	}
	
	
	private Component getGridLinkButton(CPItemsByArticleTypeDTO itemData, String columnID)
	{
		Component result = null;
		Boolean enableLink = false;
		TemplateDetailData buttonData = null;
		
		if(itemData != null)
		{
			switch(columnID) 
			{
				case PENDING_COUNT:
					result = (itemData.getPendingcount() > 0) ? new Button(NumericManager.getFormatter(0).format(itemData.getPendingcount())) : new Label(NumericManager.getFormatter(0).format(itemData.getPendingcount()));
					enableLink = (itemData.getPendingcount() > 0);
					buttonData = new TemplateDetailData(itemData, FEPConstants.PENDING_STATE_VALUE);
					break;

				case APPROVED_COUNT:
					result = (itemData.getApprovedcount() > 0) ? new Button(NumericManager.getFormatter(0).format(itemData.getApprovedcount())) : new Label(NumericManager.getFormatter(0).format(itemData.getApprovedcount()));
					enableLink = (itemData.getApprovedcount() > 0);
					buttonData = new TemplateDetailData(itemData, FEPConstants.APPROVED_STATE_VALUE);
					break;
					
				case REJECTED_COUNT:
					result = (itemData.getRejectedcount() > 0) ? new Button(NumericManager.getFormatter(0).format(itemData.getRejectedcount())) : new Label(NumericManager.getFormatter(0).format(itemData.getRejectedcount()));
					enableLink = (itemData.getRejectedcount() > 0);
					buttonData = new TemplateDetailData(itemData, FEPConstants.REJECTED_STATE_VALUE);
					break;
			}
			
			if(enableLink && (result instanceof Button))
			{
				result.addStyleName("grid-link-button-blue");
				((Button)result).setData(buttonData);
				((Button)result).addClickListener((ClickListener & Serializable)e -> this.linkButton_clickHandler(e));
			}
		}
		
		return result;
	}
	
	
	private void linkButton_clickHandler(ClickEvent event)
	{
		if((event != null) && (event.getButton() != null) && (event.getButton().getData() instanceof TemplateDetailData))
		{
			CPItemsByArticleTypeDTO selectedProduct = ((TemplateDetailData)event.getButton().getData()).getTemplate();
			
			CPItemsByFilterInitParamDTO auxInitParam = (CPItemsByFilterInitParamDTO) BbrUtils.getInstance().copyObject(this.initParamFilter);
			auxInitParam.setStatus(((TemplateDetailData)event.getButton().getData()).getState());
			
			ViewProductInformation productInfoWin = new ViewProductInformation(this.getBbrUIParent(), selectedProduct, this.selectedProvider, auxInitParam);
			productInfoWin.addBbrEventListener((BbrEventListener & Serializable) e -> this.products_updateHandler(e)); 
			productInfoWin.open(true, true, this);
		}
	}


	private void initializeChart()
	{
		Lang es = new Lang();
		es.setNoData(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "chart_no_data"));
		ChartOptions.get().setLang(es);

		this.chart_ProductState = new Chart(ChartType.PIE);

		Tooltip tooltip = new Tooltip();
		tooltip.setValueDecimals(0);
		tooltip.setPointFormat("<b>{point.percentage}%</b>");

		PlotOptionsPie plotOptions = new PlotOptionsPie();
		plotOptions.setAllowPointSelect(true);
		plotOptions.setCursor(Cursor.POINTER);
		plotOptions.setShowInLegend(true);
		plotOptions.setDataLabels(new DataLabels(false));

		Legend legend = new Legend(true);
		legend.setAlign(HorizontalAlign.RIGHT);
		legend.setVerticalAlign(VerticalAlign.MIDDLE);
		legend.setLayout(LayoutDirection.VERTICAL);

		Configuration conf = this.chart_ProductState.getConfiguration();
		conf.setTitle(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "product_list_chart_title"));
		conf.setTooltip(tooltip);
		conf.setPlotOptions(plotOptions);
		conf.setLegend(legend);

		this.chart_ProductState.drawChart(conf);

		this.chartLayout = new BbrVerticalLayout(this.getBbrUIParent());
		this.chartLayout.setSizeFull();
		this.chartLayout.setMargin(false);
		this.chartLayout.addComponents(this.chart_ProductState);
	}


	private void updateButtons(Boolean isSelectionEvent)
	{
		this.btn_ProductInformation.setEnabled(this.datGrid_Products.getSelectedItems() != null && this.datGrid_Products.getSelectedItems().size() == 1
		&& !this.datGrid_Products.getSelectedItems().isEmpty());

		this.btn_DownloadProductsResume.setEnabled(initParamFilter != null);
		this.btn_Refresh.setEnabled(initParamFilter != null);
	}


	private void updateChartSerie(CPItemsResumeDataDTO[] itemsResumeDataDTO)
	{
		DataSeries series = new DataSeries();
		if (itemsResumeDataDTO != null)
		{
			for (CPItemsResumeDataDTO mpItemsResumeDataDTO : itemsResumeDataDTO)
			{
				DataSeriesItem seriePending = new DataSeriesItem(mpItemsResumeDataDTO.getStatename(), mpItemsResumeDataDTO.getItemcount());

				series.add(seriePending);
			}
		}

		Configuration config = chart_ProductState.getConfiguration();
		config.setSeries(series);
		chart_ProductState.drawChart(config);
	}


	private CPItemsByArticleTypeArrayResultDTO executeService(BbrUI bbrUI)
	{
		CPItemsByArticleTypeArrayResultDTO result = null;

		if (this.initParamFilter != null)
		{
			Integer requestedPage = (!this.resetPageInfo && this.pagingManager.getCurrentPage() > 0) ? (Integer) this.pagingManager.getCurrentPage() : this.DEFAULT_PAGE_NUMBER;
			try
			{
				// Start Tracking
				this.getTimingMngr().startTimer();
				this.initParamFilter.setPageNumber(requestedPage);
				this.initParamFilter.setNeedPageInfo(this.resetPageInfo);
				result = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(bbrUI).getItemsByArticleTypeResume(this.initParamFilter, bbrUI.getUser().getId());
			}
			catch (BbrUserException e)
			{
				AppUtils.getInstance().doLogOut();
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


	private CPItemsResumeDataResultDTO executeChartService(BbrUI bbrUI)
	{
		CPItemsResumeDataResultDTO result = null;

		if (this.initParamArticle != null)
		{
			Integer requestedPage = (!this.resetPageInfo && this.pagingManager.getCurrentPage() > 0) ? (Integer) this.pagingManager.getCurrentPage() : this.DEFAULT_PAGE_NUMBER;
			try
			{
				// Start Tracking
				this.getTimingMngr().startTimer();
				this.initParamArticle.setPageNumber(requestedPage);
				this.initParamArticle.setNeedPageInfo(this.resetPageInfo);
				result = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(bbrUI).getItemsResumeData(this.initParamArticle);
			}
			catch (BbrUserException e)
			{
				AppUtils.getInstance().doLogOut();
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


	protected FileDownloadInfoResultDTO executeDownloadService(BbrUI bbrUIParent, DownloadFormats selectedFormat)
	{
		FileDownloadInfoResultDTO fileResult = null;
		if (selectedFormat != null)
		{
			try
			{
				if (this.btn_DownloadTriggerButton == this.btn_DownloadProductsResume)
				{
					fileResult = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(bbrUIParent).downloadItemsByArticleTypeResume(this.initParamFilter, bbrUIParent.getUser().getId(), selectedFormat.getValue());
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


	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{
		String errordescription = "";

		ProductsManagement senderReport = (ProductsManagement) sender;

		if (result != null)
		{
			CPItemsByArticleTypeArrayResultDTO reportResult = (CPItemsByArticleTypeArrayResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), true);

			if (!error.hasError())
			{
				CPItemsByArticleTypeDTO[] data = (reportResult.getValues() != null) ? reportResult.getValues() : new CPItemsByArticleTypeDTO[0];

				ListDataProvider<CPItemsByArticleTypeDTO> dataprovider = new ListDataProvider<>(Arrays.asList(data));

				senderReport.datGrid_Products.setDataProvider(dataprovider, senderReport.resetPageInfo);
				senderReport.updateButtons(false);

				if (reportResult.getPageinfo() != null && senderReport.resetPageInfo)
				{
					PageInfoDTO pageInfo = reportResult.getPageinfo();
					BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
					senderReport.pagingManager.setPages(bbrPage, senderReport.resetPageInfo);
				}

				// End Tracking
				if (senderReport.trackReport == true)
				{
					senderReport.trackEvent(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription());
				}

				if (reportResult.getValues() == null || reportResult.getValues().length == 0)
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


	private void doUpdateChart(Object result, BbrWorkExecutor sender)
	{
		String errordescription = "";

		ProductsManagement senderReport = (ProductsManagement) sender;

		if (result != null)
		{
			CPItemsResumeDataResultDTO reportResult = (CPItemsResumeDataResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), true);

			if (!error.hasError())
			{
				senderReport.updateChartSerie(reportResult.getResume());
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
		senderReport.chartLayout.stopWaiting();
	}


	private void updateSortOrderCriteria(List<GridSortOrder<CPItemsByArticleTypeDTO>> sortOrderList)
	{
		if (sortOrderList != null && !sortOrderList.isEmpty())
		{
			ArrayList<OrderCriteriaDTO> resultList = new ArrayList<>();
			for (GridSortOrder<CPItemsByArticleTypeDTO> sorOrder : sortOrderList)
			{
				OrderCriteriaDTO order = new OrderCriteriaDTO();
				order.setPropertyname(sorOrder.getSorted().getId().toUpperCase());
				order.setAscending(sorOrder.getDirection() == SortDirection.ASCENDING);
				resultList.add(order);
			}
			orderCriteria = resultList.toArray(new OrderCriteriaDTO[resultList.size()]);
		}

		else
		{
			OrderCriteriaDTO order = new OrderCriteriaDTO();
			order.setPropertyname(DEFAULT_SORT_FIELD.toUpperCase());
			order.setAscending(true);
			orderCriteria = new OrderCriteriaDTO[] { order };

			GridSortOrder<CPItemsByArticleTypeDTO> sortOrder = new GridSortOrder<>(datGrid_Products.getColumn(DEFAULT_SORT_FIELD), SortDirection.ASCENDING);
			sortOrderList = new ArrayList<GridSortOrder<CPItemsByArticleTypeDTO>>();
			sortOrderList.add(sortOrder);

			this.datGrid_Products.setSortOrder(sortOrderList);
		}
	}


	private void doDownloadFile(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		ProductsManagement senderReport = (ProductsManagement) sender;
		if (senderReport != null)
		{
			senderReport.downloadLinkFile(result);
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
	
	class TemplateDetailData
	{
		private CPItemsByArticleTypeDTO template;
		private Integer state;
		
		public TemplateDetailData(CPItemsByArticleTypeDTO selectedTemplate, Integer selectedState)
		{
			this.template = selectedTemplate;
			this.state = selectedState;
		}
		
		public CPItemsByArticleTypeDTO getTemplate()
		{
			return template;
		}

		public Integer getState()
		{
			return state;
		}
	}
	
	private void products_updateHandler(BbrEvent bbrEvent)
	{
		if (bbrEvent != null )
		{
			this.startWaiting();
			this.executeBbrWork(reportWork);
			
			this.chartLayout.startWaiting();
			this.executeBbrWork(chartWork);
			
		}
	}
	
}

 
