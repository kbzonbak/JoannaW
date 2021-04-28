package bbr.b2b.portal.modules.fep;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

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
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.ItemClickListener;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeDataDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeInitParamDTO;
import bbr.b2b.fep.common.data.classes.AttributeDTO;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.fep.FepUtils;
import bbr.b2b.portal.classes.wrappers.fep.DateTypeRangeSelectionInfo;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.filters.fep.TemplatesFilter;
import bbr.b2b.portal.components.modules.fep.CreateOrEditTemplate;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.basics.BbrPage;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.constants.CoreConstants;
import cl.bbr.core.classes.constants.DownloadFormats;
import cl.bbr.core.classes.constants.TrackingConstants;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrMessageBox;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.grid.renderer.DateRenderer;
import cl.bbr.core.components.paging.BbrPagingManager;

public class TemplatesManagement extends BbrModule implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long							serialVersionUID			= 3708088987063446407L;

	private static final String						TEMPLATE_NAME				= "description";
	private static final String						STATE							= "active";
	private static final String						LAST_UPDATER_NAME			= "modifyby";
	private static final String						LAST_UPDATE_DATETIME		= "lastmodified";
	private static final String						CLIENT_NAME					= "clientinternalname";

	private VerticalLayout								mainLayout					= null;

	private BbrAdvancedGrid<ArticleTypeDataDTO>	datGrid_Templates			= null;
	private BbrPagingManager							pagingManager				= null;

	private ArticleTypeInitParamDTO					initParam					= null;

	private final int										DEFAULT_PAGE_NUMBER		= 1;
	private final int										MAX_ROWS_NUMBER			= 50;

	private final String									DEFAULT_SORT_FIELD		= TEMPLATE_NAME;

	private Button											btn_AddTemplate			= null;
	private Button											btn_EditTemplate			= null;
	private Button											btn_DeleteTemplates		= null;
	private Button											btn_DownloadTemplates	= null;
	private Button											btn_Refresh					= null;

	private Boolean										trackReport					= true;
	private Boolean										resetPageInfo				= true;

	private OrderCriteriaDTO[]							orderCriteria				= null;

	private BbrWork<ArticleTypeArrayResultDTO>	reportWork					= null;
	private BbrWork<BaseResultDTO>					deleteResourceWork		= null;
	private BbrWork<FileDownloadInfoResultDTO>	downloadsWork				= null;
	private String											type							= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public TemplatesManagement(BbrUI bbrUIParent, String type)
	{
		super(bbrUIParent);
		this.type = type;
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
		TemplatesFilter filterLayout = new TemplatesFilter(this, this.type);
		filterLayout.initializeView();
		this.setBbrFilterContainer(filterLayout);

		// Paging Manager
		this.pagingManager = new BbrPagingManager();
		this.pagingManager.setLocale(this.getUser().getLocale());
		this.pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> this.pagingChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);

		HorizontalLayout leftButtonsBar = new HorizontalLayout();
		leftButtonsBar.setSpacing(true);
		leftButtonsBar.setMargin(false);
		leftButtonsBar.setHeight("30px");
		leftButtonsBar.addStyleName("toolbar-layout");

		this.btn_AddTemplate = new Button("",
		BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_AddAlternative.png"));
		this.btn_AddTemplate.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "add_template"));
		this.btn_AddTemplate.addClickListener((ClickListener & Serializable) e -> this.btn_AddTemplate_clickHandler(e));
		this.btn_AddTemplate.addStyleName("toolbar-button");

		this.btn_EditTemplate = new Button("",
		BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_EditAlternative.png"));
		this.btn_EditTemplate.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "edit_template"));
		this.btn_EditTemplate.addClickListener((ClickListener & Serializable) e -> this.btn_EditTemplate_clickHandler(e));
		this.btn_EditTemplate.addStyleName("toolbar-button");

		this.btn_DeleteTemplates = new Button("",
		BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_Delete.png"));
		this.btn_DeleteTemplates.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "delete_template"));
		this.btn_DeleteTemplates.addClickListener((ClickListener & Serializable) e -> this.btn_DeleteAttribute_clickHandler(e));
		this.btn_DeleteTemplates.addStyleName("toolbar-button");

		leftButtonsBar.addComponents(this.btn_AddTemplate, btn_EditTemplate, btn_DeleteTemplates);

		this.btn_DownloadTemplates = new Button("",
		BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_DownloadPrimary.png"));
		this.btn_DownloadTemplates.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_template"));
		this.btn_DownloadTemplates.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadFile_downloadHandler(e));
		this.btn_DownloadTemplates.addStyleName("toolbar-button");

		this.btn_Refresh = new Button("", BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_Refresh.png"));
		this.btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
		this.btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler(e));
		this.btn_Refresh.addStyleName("toolbar-button");

		HorizontalLayout rightButtonsBar = new HorizontalLayout();
		rightButtonsBar.addComponents(this.btn_DownloadTemplates, this.btn_Refresh);
		rightButtonsBar.setSpacing(true);
		rightButtonsBar.setMargin(false);
		rightButtonsBar.setHeight("30px");
		rightButtonsBar.addStyleName("toolbar-layout");

		HorizontalLayout toolBar = new HorizontalLayout();
		toolBar.setWidth("100%");
		toolBar.addComponents(leftButtonsBar, rightButtonsBar);
		toolBar.addStyleName("filter-toolbar");
		toolBar.setExpandRatio(leftButtonsBar, 1F);
		toolBar.setExpandRatio(rightButtonsBar, 1F);

		toolBar.setComponentAlignment(leftButtonsBar, Alignment.MIDDLE_LEFT);
		toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);

		// Reporte: Grilla
		this.datGrid_Templates = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.datGrid_Templates.setSortable(false);

		this.initializeDataGridColumns();

		this.datGrid_Templates.addStyleName("report-grid");
		this.datGrid_Templates.setSizeFull();
		this.datGrid_Templates.setPagingManager(pagingManager, this.DEFAULT_SORT_FIELD);
		this.datGrid_Templates.setSelectionMode(SelectionMode.MULTI);
		this.datGrid_Templates.addSelectionListener((SelectionListener<ArticleTypeDataDTO> & Serializable) e -> selection_gridHandler(e));
		this.datGrid_Templates.addSortListener((SortListener<GridSortOrder<ArticleTypeDataDTO>> & Serializable) e -> dataGrid_sortHandler(e));
		this.datGrid_Templates.addItemClickListener((ItemClickListener<ArticleTypeDataDTO> & Serializable) e -> dgdItem_clickHandler(e));

		this.mainLayout = new VerticalLayout();
		mainLayout.addStyleName("report-layout");
		mainLayout.setSizeFull();
		mainLayout.setMargin(false);
		mainLayout.addComponents(toolBar, this.datGrid_Templates, pagingManager);
		mainLayout.setExpandRatio(this.datGrid_Templates, 1F);

		this.addComponents(mainLayout);

		this.updateButtons(false);

		reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		reportWork.addFunction(new Function<Object, ArticleTypeArrayResultDTO>()
		{
			@Override
			public ArticleTypeArrayResultDTO apply(Object t)
			{
				return executeService(TemplatesManagement.this.getBbrUIParent());
			}
		});
	}


	@Override
	public void filterApplied_handler(BbrFilterEvent event)
	{
		if ((event != null) && (event.getResultObject() != null))
		{
			this.initParam = (ArticleTypeInitParamDTO) event.getResultObject();

			this.trackReport = true;
			this.resetPageInfo = true;

			this.startWaiting();
			this.executeBbrWork(reportWork);
		}
	}


	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		TemplatesManagement bbrSender = (TemplatesManagement) sender;
		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				bbrSender.doUpdateReport(result, sender);
			}
			else if (triggerObject == this.btn_DownloadTriggerButton)
			{
				this.doDownloadFile(result, sender, triggerObject);
			}
			else if (triggerObject == btn_DeleteTemplates)
			{
				bbrSender.doDeleteReport(result, sender);
			}
		}
		else
		{
			bbrSender.showErrorMessage(I18NManager.getI18NString(bbrSender.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
			I18NManager.getI18NString(bbrSender.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));
		}
		bbrSender.stopWaiting();
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
				return executeDownloadService(TemplatesManagement.this.getBbrUIParent(), selectedFormat, btn_DownloadTriggerButton, null);
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

	private void btn_AddTemplate_clickHandler(ClickEvent event)
	{
		if (this.initParam != null)
		{
			CreateOrEditTemplate createOrEditWin = new CreateOrEditTemplate(this.getBbrUIParent(), null, false, this.type, this.initParam.getClientname());
			createOrEditWin.addBbrEventListener((BbrEventListener & Serializable) e -> article_createdHandler(e));
			createOrEditWin.initializeView();
			createOrEditWin.open(true);
		}
	}


	private void article_createdHandler(BbrEvent bbrEvent)
	{
		if (bbrEvent != null && bbrEvent.getEventType().equals(BbrEvent.ITEM_CREATED))
		{
			this.startWaiting();

			this.trackReport = false;
			this.resetPageInfo = true;
			this.executeBbrWork(reportWork);
		}
	}


	private void article_editedHandler(BbrEvent bbrEvent)
	{
		if (bbrEvent != null && bbrEvent.getEventType().equals(BbrEvent.ITEM_UPDATED))
		{
			this.startWaiting();

			this.trackReport = false;
			this.resetPageInfo = true;
			this.executeBbrWork(reportWork);
		}
	}


	private void btn_DownloadFile_downloadHandler(ClickEvent e)
	{
		this.btn_DownloadTriggerButton = e.getButton();
		if (this.btn_DownloadTriggerButton == this.btn_DownloadTemplates)
		{
			this.selectDownloadFileType(DownloadFormats.XLS, DownloadFormats.XLS, DownloadFormats.CSV);
		}
	}


	private void btn_EditTemplate_clickHandler(ClickEvent event)
	{
		if ((this.datGrid_Templates.getSelectedItems() != null) && (this.datGrid_Templates.getSelectedItems().size() == 1))
		{
			ArticleTypeDataDTO selectedTemplate = this.datGrid_Templates.getSelectedItems().iterator().next();
			this.viewCreateOrEditCtrl(selectedTemplate, true);
		}
	}


	private void btn_DeleteAttribute_clickHandler(ClickEvent event)
	{
		if ((this.datGrid_Templates.getSelectedItems() != null) && (this.datGrid_Templates.getSelectedItems().size() > 0))
		{
			ArrayList<ArticleTypeDataDTO> selectedTemplate = new ArrayList<>();
			selectedTemplate.addAll(this.datGrid_Templates.getSelectedItems());

			BbrMessageBox.createQuestion(TemplatesManagement.this.getBbrUIParent()).withYesButton(new Runnable()
			{
				@Override
				public void run()
				{
					deleteResourceWork = new BbrWork<>(TemplatesManagement.this, TemplatesManagement.this.getBbrUIParent(), btn_DeleteTemplates);
					deleteResourceWork.addFunction(new Function<Object, BaseResultDTO>()
					{
						@Override
						public BaseResultDTO apply(Object t)
						{
							return executeDeleteService(TemplatesManagement.this.getBbrUIParent(), selectedTemplate);
						}
					});

					TemplatesManagement.this.startWaiting();

					TemplatesManagement.this.executeBbrWork(deleteResourceWork);
				}
			}).withNoButton().withCaption(I18NManager.getI18NString(TemplatesManagement.this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"))
			.withHtmlMessage(I18NManager.getI18NString(TemplatesManagement.this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "ask_delete_template")).open();
		}
	}


	private void selection_gridHandler(SelectionEvent<ArticleTypeDataDTO> e)
	{
		this.updateButtons(true);
	}


	private void dgdItem_clickHandler(ItemClick<ArticleTypeDataDTO> e)
	{
		if (e.getMouseEventDetails().isDoubleClick() && e.getItem() != null)
		{
			this.viewCreateOrEditCtrl(e.getItem(), true);
		}
	}


	private void dataGrid_sortHandler(SortEvent<GridSortOrder<ArticleTypeDataDTO>> e)
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
		this.executeBbrWork(reportWork);
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
		this.datGrid_Templates.addCustomColumn(ArticleTypeDataDTO::getDescription, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_template_name"), true)
		.setId(TEMPLATE_NAME);
		this.datGrid_Templates.addCustomColumn(t -> this.getStateLabel(t), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_state"), true).setId(STATE);
		this.datGrid_Templates.addCustomColumn(ArticleTypeDataDTO::getModifyby, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_user_last_modification"), true)
		.setId(LAST_UPDATER_NAME);
		this.datGrid_Templates.addCustomColumn(ArticleTypeDataDTO::getLastmodified, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_date_last_modification"), true)
		.setStyleGenerator(creationdate -> BbrAlignment.CENTER.getValue()).setRenderer(new DateRenderer()).setId(LAST_UPDATE_DATETIME);
		this.datGrid_Templates.addCustomColumn(ArticleTypeDataDTO::getClientinternalname, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_client"), true)
		.setId(CLIENT_NAME).setWidth(200);
	}


	private void updateButtons(Boolean isSelectionEvent)
	{
		this.btn_EditTemplate.setEnabled(
		(this.datGrid_Templates.getSelectedItems() != null) && (this.datGrid_Templates.getSelectedItems().size() == 1) && !this.datGrid_Templates.getSelectedItems().isEmpty());

		this.btn_DeleteTemplates.setEnabled(this.datGrid_Templates.getSelectedItems() != null && !this.datGrid_Templates.getSelectedItems().isEmpty());

		this.btn_DownloadTemplates.setEnabled(initParam != null);
		this.btn_Refresh.setEnabled(initParam != null);
		this.btn_AddTemplate.setEnabled(initParam != null);
	}


	private ArticleTypeArrayResultDTO executeService(BbrUI bbrUI)
	{
		ArticleTypeArrayResultDTO result = null;

		if (this.initParam != null)
		{
			Integer requestedPage = (!resetPageInfo && this.pagingManager.getCurrentPage() > 0) ? (Integer) this.pagingManager.getCurrentPage() : this.DEFAULT_PAGE_NUMBER;

			try
			{
				// Start Tracking

				this.getTimingMngr().startTimer();
				result = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(bbrUI).getArticleTypeData(this.initParam, requestedPage, this.MAX_ROWS_NUMBER, resetPageInfo,
				orderCriteria, this.getBbrUIParent().getLocale().getLanguage());
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


	private String getStateLabel(ArticleTypeDataDTO templateItem)
	{
		String result = "";

		if (templateItem != null)
		{
			result = (templateItem.getActive()) ? I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "active")
			: I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "inactive");
		}

		return result;
	}


	private void viewCreateOrEditCtrl(ArticleTypeDataDTO selectedTemplate, boolean editionMode)
	{
		CreateOrEditTemplate createOrEditWin = new CreateOrEditTemplate(this.getBbrUIParent(), selectedTemplate, editionMode, this.type, this.initParam.getClientname());
		createOrEditWin.addBbrEventListener((BbrEventListener & Serializable) e -> article_editedHandler(e));
		createOrEditWin.initializeView();
		createOrEditWin.open(true);
	}


	private BaseResultDTO executeDeleteService(BbrUI bbrUIParent, ArrayList<ArticleTypeDataDTO> selectedResources)
	{
		BaseResultDTO result = null;
		if (selectedResources != null && selectedResources.size() > 0)
		{
			ArticleTypeDataDTO[] articles = new ArticleTypeDataDTO[selectedResources.size()];
			articles = selectedResources.toArray(articles);
			try
			{
				result = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(bbrUIParent).removeArticleTypes(articles, false, FepUtils.createPersonDTO(this.getUser()));
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


	protected FileDownloadInfoResultDTO executeDownloadService(BbrUI bbrUIParent, DownloadFormats selectedFormat, Button downloadTriggerButton, DateTypeRangeSelectionInfo rangeInfo)
	{
		FileDownloadInfoResultDTO fileResult = null;
		if (selectedFormat != null)
		{
			LocalDateTime localDateTime = LocalDateTime.now();
			int day = localDateTime.getDayOfMonth();
			int month = localDateTime.getMonth().getValue();
			int year = localDateTime.getYear();
			int hour = localDateTime.getHour();
			int minute = localDateTime.getMinute();
			int second = localDateTime.getSecond();
			String filename = "Plantillas_" + day + "-" + month + "-" + year + "_" + hour + "." + minute + "." + second;
			try
			{
				fileResult = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(bbrUIParent).downloadArticleType(this.initParam, this.orderCriteria, filename,
				bbrUIParent.getUser().getId(), selectedFormat.getValue(), bbrUIParent.getUser().getLocale());
				// fileResult =
				// EJBFactory.getMDMEJBFactory().getMDMCommonDataManagerLocal(bbrUIParent).downloadArticleType(initParam,
				// 1, MAX_ROWS_NUMBER, false, this.orderCriteria, filename,
				// bbrUIParent.getUser().getId(), selectedFormat.getValue(),
				// bbrUIParent.getUser().getLocale());
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

		TemplatesManagement senderReport = (TemplatesManagement) sender;

		if (result != null)
		{
			ArticleTypeArrayResultDTO reportResult = (ArticleTypeArrayResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), false);

			if (!error.hasError())
			{
				ArticleTypeDataDTO[] data = (reportResult.getValues() != null) ? reportResult.getValues() : new ArticleTypeDataDTO[0];

				ListDataProvider<ArticleTypeDataDTO> dataprovider = new ListDataProvider<>(Arrays.asList(data));

				senderReport.datGrid_Templates.setDataProvider(dataprovider, senderReport.resetPageInfo);
				senderReport.datGrid_Templates.getDataProvider().refreshAll();
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
			}
			else if (reportResult.getValues() == null || reportResult.getValues().length == 0)
			{
				ArticleTypeDataDTO[] data = new ArticleTypeDataDTO[0];
				ListDataProvider<ArticleTypeDataDTO> dataprovider = new ListDataProvider<>(Arrays.asList(data));
				senderReport.datGrid_Templates.setDataProvider(dataprovider, senderReport.resetPageInfo);
				senderReport.datGrid_Templates.getDataProvider().refreshAll();
				
				senderReport.askToOpenFilter(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
				I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_FILTERS, "empty_filter_question"));
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


	private void doDeleteReport(Object result, BbrWorkExecutor sender)
	{
		TemplatesManagement senderReport = (TemplatesManagement) sender;

		if (result != null)
		{
			BaseResultDTO reportResult = (BaseResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), true);

			this.stopWaiting();

			if (!error.hasError())
			{
				senderReport.showInfoMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
				I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_MODULES_GENERIC, "successful_operation"));

				senderReport.startWaiting();

				senderReport.trackReport = false;
				senderReport.resetPageInfo = true;
				senderReport.executeBbrWork(reportWork);
			}
			else
			{
				this.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"), error.getErrorMessage());
			}
		}
	}


	private void updateSortOrderCriteria(List<GridSortOrder<ArticleTypeDataDTO>> sortOrderList)
	{
		if (sortOrderList != null && !sortOrderList.isEmpty())
		{
			ArrayList<OrderCriteriaDTO> resultList = new ArrayList<>();
			for (GridSortOrder<ArticleTypeDataDTO> sorOrder : sortOrderList)
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

			GridSortOrder<ArticleTypeDataDTO> sortOrder = new GridSortOrder<>(datGrid_Templates.getColumn(DEFAULT_SORT_FIELD), SortDirection.ASCENDING);
			sortOrderList = new ArrayList<GridSortOrder<ArticleTypeDataDTO>>();
			sortOrderList.add(sortOrder);

			this.datGrid_Templates.setSortOrder(sortOrderList);
		}
	}


	private void doDownloadFile(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		TemplatesManagement senderReport = (TemplatesManagement) sender;

		if (senderReport != null)
		{
			senderReport.downloadLinkFile(result);
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
}
