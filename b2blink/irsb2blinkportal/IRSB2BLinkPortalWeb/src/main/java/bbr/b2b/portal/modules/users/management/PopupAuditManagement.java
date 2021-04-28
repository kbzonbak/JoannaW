package bbr.b2b.portal.modules.users.management;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.SortEvent;
import com.vaadin.event.SortEvent.SortListener;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.filters.management.PopupAuditManagementFilter;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.AuditPopUpReportDTO;
import bbr.b2b.users.report.classes.AuditPopUpReportInitParamDTO;
import bbr.b2b.users.report.classes.AuditPopUpReportResultDTO;
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
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.grid.renderer.DateRenderer;
import cl.bbr.core.components.paging.BbrPagingManager;

public class PopupAuditManagement extends BbrModule implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants
	private static final long						serialVersionUID		= -8893380133105891408L;
	private static final String						TITLE_COLUMN_ID			= "title";
	private static final String						TYPE_COLUMN_ID			= "type";
	private static final String						USER_ID_COLUMN_ID		= "userid";
	private static final String						USERNAME_COLUMN_ID		= "username";
	private static final String						USERLASTNAME_COLUMN_ID	= "userlastname";
	private static final String						USEREMAIL_COLUMN_ID		= "useremail";
	private static final String						COMPANYCODE_COLUMN_ID	= "companycode";
	private static final String						COMPANYNAME_COLUMN_ID	= "companyname";
	private static final String						ACTIONDATE_COLUMN_ID	= "actiondate";
	private static final String						ACTION_COLUMN_ID		= "action";
	private static final String						DEFAULT_SORT_FIELD		= ACTIONDATE_COLUMN_ID;
	// Components
	private VerticalLayout							mainLayout;
	private BbrAdvancedGrid<AuditPopUpReportDTO>	dgd_PopupsAudit			= null;
	private BbrPagingManager						pagingManager			= null;
	private Button									btn_Refresh				= null;
	private Button									btn_DownloadExcel		= null;
	// Variables
	private AuditPopUpReportInitParamDTO			initParam				= null;
	private final int								DEFAULT_PAGE_NUMBER		= 1;
	private final int								MAX_ROWS_NUMBER			= 200;
	private OrderCriteriaDTO[]						orderCriteria			= null;
	private Boolean									trackReport				= true;
	private Boolean									resetPageInfo			= true;
	private BbrWork<AuditPopUpReportResultDTO>		reportWork				= null;
	private BbrWork<FileDownloadInfoResultDTO>		downloadsWork			= null;
	private String									titleSelected			= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public PopupAuditManagement(BbrUI bbrUIParent)
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
		// Filtro
		PopupAuditManagementFilter filterLayout = new PopupAuditManagementFilter(this);
		filterLayout.initializeView();
		this.setBbrFilterContainer(filterLayout);

		// Paging Manager
		this.pagingManager = new BbrPagingManager();
		this.pagingManager.setLocale(this.getUser().getLocale());
		this.pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> this.pagingChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);

		// Reporte: Barra de herramientas superior izq
		HorizontalLayout leftButtonsBar = new HorizontalLayout();
		leftButtonsBar.setSpacing(true);
		leftButtonsBar.setMargin(false);
		leftButtonsBar.setHeight("30px");
		leftButtonsBar.addStyleName("toolbar-layout");

		this.btn_DownloadExcel = new Button("", EnumToolbarButton.DOWNLOAD_PRIMARY.getResource());
		this.btn_DownloadExcel.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "download_report"));
		this.btn_DownloadExcel.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadFile_downloadHandler(e));
		this.btn_DownloadExcel.addStyleName("toolbar-button");
		// END DOWNLOAD BUTTONS SECTION

		this.btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
		this.btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
		this.btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler());
		this.btn_Refresh.addStyleName("toolbar-button");

		HorizontalLayout rightButtonsBar = new HorizontalLayout();
		rightButtonsBar.addComponents(this.btn_DownloadExcel, this.btn_Refresh);
		rightButtonsBar.setSpacing(true);
		rightButtonsBar.setMargin(false);
		rightButtonsBar.setHeight("30px");
		rightButtonsBar.addStyleName("toolbar-layout");

		rightButtonsBar.setComponentAlignment(this.btn_Refresh, Alignment.MIDDLE_RIGHT);

		HorizontalLayout toolBar = new HorizontalLayout();
		toolBar.setWidth("100%");
		toolBar.addComponents(leftButtonsBar, rightButtonsBar);
		toolBar.addStyleName("filter-toolbar");
		toolBar.setExpandRatio(leftButtonsBar, 1F);
		toolBar.setExpandRatio(rightButtonsBar, 1F);

		toolBar.setComponentAlignment(leftButtonsBar, Alignment.MIDDLE_LEFT);
		toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);

		// Reporte: Grilla

		this.dgd_PopupsAudit = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.dgd_PopupsAudit.setSortable(true);

		this.initializeDataGridColumns();

		this.dgd_PopupsAudit.addStyleName("report-grid");
		this.dgd_PopupsAudit.setSizeFull();
		this.dgd_PopupsAudit.setPagingManager(this.pagingManager, DEFAULT_SORT_FIELD);
		this.dgd_PopupsAudit.setSelectionMode(SelectionMode.SINGLE);
		this.dgd_PopupsAudit.addSelectionListener((SelectionListener<AuditPopUpReportDTO> & Serializable) e -> this.selection_gridHandler());
		this.dgd_PopupsAudit.addSortListener((SortListener<GridSortOrder<AuditPopUpReportDTO>>) e -> this.dataGrid_sortHandler(e));

		this.mainLayout = new VerticalLayout();
		this.mainLayout.addStyleName("report-layout");
		this.mainLayout.setSizeFull();
		this.mainLayout.setMargin(false);
		this.mainLayout.addComponents(toolBar, this.dgd_PopupsAudit, this.pagingManager);
		this.mainLayout.setExpandRatio(this.dgd_PopupsAudit, 1F);

		this.addComponents(mainLayout);

		this.updateButtons(false);
		this.updateSortOrderCriteria(null);

		reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		reportWork.addFunction(new Function<Object, AuditPopUpReportResultDTO>()
		{
			@Override
			public AuditPopUpReportResultDTO apply(Object t)
			{
				return executeService(PopupAuditManagement.this.getBbrUIParent());
			}
		});
	}

	@Override
	public void filterApplied_handler(BbrFilterEvent event)
	{
		if ((event != null) && (event.getResultObject() != null))
		{
			this.initParam = (AuditPopUpReportInitParamDTO) event.getResultObject();
			this.titleSelected = initParam.getTitle();
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

		if (this.btn_DownloadTriggerButton == this.btn_DownloadExcel)
		{
			downloadsWork = new BbrWork<>(this, this.getBbrUIParent(), btn_DownloadTriggerButton);
			downloadsWork.addFunction(new Function<Object, FileDownloadInfoResultDTO>()
			{
				@Override
				public FileDownloadInfoResultDTO apply(Object t)
				{
					return executeDownloadService(PopupAuditManagement.this.getBbrUIParent(), selectedFormat);
				}
			});

			this.startWaiting();
			this.executeBbrWork(downloadsWork);
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
			else if (triggerObject instanceof Button)
			{
				this.doDownloadFile(result, sender);
			}
		}
		else
		{
			PopupAuditManagement senderReport = (PopupAuditManagement) sender;

			if (!senderReport.getBbrUIParent().hasAlertWindowOpen())
				senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
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

	private void btn_DownloadFile_downloadHandler(ClickEvent e)
	{
		this.btn_DownloadTriggerButton = e.getButton();

		if (this.btn_DownloadTriggerButton == this.btn_DownloadExcel)
		{
			this.selectDownloadFileType(DownloadFormats.XLSX, DownloadFormats.XLSX, DownloadFormats.CSV);
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

	private void selection_gridHandler()
	{
		this.updateButtons(true);
	}

	private void dataGrid_sortHandler(SortEvent<GridSortOrder<AuditPopUpReportDTO>> e)
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

	private void refreshReport_clickHandler()
	{
		this.startWaiting();

		this.trackReport = false;
		this.resetPageInfo = true;
		this.executeBbrWork(reportWork);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
	private void initializeDataGridColumns()
	{
		if (this.dgd_PopupsAudit.getColumns().size() > 1)
			this.dgd_PopupsAudit.removeAllColumns();

		String titleSelected = this.titleSelected != null ? this.titleSelected : "";

		this.dgd_PopupsAudit.addCustomColumn((item) ->
		{
			return titleSelected;
		}, I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_app_title"), true)
				.setId(TITLE_COLUMN_ID);

		this.dgd_PopupsAudit.addCustomColumn(AuditPopUpReportDTO::getType, I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_app_type"), true)
				.setWidth(170)
				.setDescriptionGenerator(item -> item.getType() != null ? item.getType() : "")
				.setId(TYPE_COLUMN_ID);

		this.dgd_PopupsAudit.addCustomColumn(AuditPopUpReportDTO::getUserid, I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_app_user_id"), true)
				.setWidth(100)
				.setDescriptionGenerator(item -> item.getUserid() != null ? item.getUserid() : "")
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setId(USER_ID_COLUMN_ID);

		this.dgd_PopupsAudit.addCustomColumn(AuditPopUpReportDTO::getUsername, I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_app_firstname"), true)
				.setWidth(100)
				.setDescriptionGenerator(item -> item.getUsername() != null ? item.getUsername() : "")
				.setId(USERNAME_COLUMN_ID);

		this.dgd_PopupsAudit.addCustomColumn(AuditPopUpReportDTO::getUserlastname, I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_app_lastname"), true)
				.setWidth(100)
				.setDescriptionGenerator(item -> item.getUserlastname() != null ? item.getUserlastname() : "")
				.setId(USERLASTNAME_COLUMN_ID);

		this.dgd_PopupsAudit.addCustomColumn(AuditPopUpReportDTO::getUseremail, I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_app_email"), true)
				.setWidthUndefined()
				.setDescriptionGenerator(item -> item.getUseremail() != null ? item.getUseremail() : "")
				.setId(USEREMAIL_COLUMN_ID);

		this.dgd_PopupsAudit.addCustomColumn(AuditPopUpReportDTO::getCompanycode, I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_app_enterprise_id"), true)
				.setWidth(120)
				.setDescriptionGenerator(item -> item.getCompanycode() != null ? item.getCompanycode() : "")
				.setStyleGenerator(item -> BbrAlignment.RIGHT.getValue())
				.setId(COMPANYCODE_COLUMN_ID);

		this.dgd_PopupsAudit.addCustomColumn(AuditPopUpReportDTO::getConpanyname, I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_app_social_reason"), true)
				.setWidth(120)
				.setDescriptionGenerator(item -> item.getConpanyname() != null ? item.getConpanyname() : "")
				.setId(COMPANYNAME_COLUMN_ID);

		this.dgd_PopupsAudit.addCustomColumn(AuditPopUpReportDTO::getActiondate, I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_app_action_date"))
				.setStyleGenerator(lastupdate -> BbrAlignment.CENTER.getValue())
				.setRenderer(new DateRenderer())
				.setWidth(160)
				.setId(ACTIONDATE_COLUMN_ID);

		this.dgd_PopupsAudit.addCustomColumn(AuditPopUpReportDTO::getAction, I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_app_action"), true)
				.setStyleGenerator(item -> BbrAlignment.CENTER.getValue())
				.setDescriptionGenerator(item -> item.getAction() != null ? item.getAction() : "")
				.setWidthUndefined()
				.setId(ACTION_COLUMN_ID);

	}

	private void updateButtons(Boolean isSelectionEvent)
	{

		if (!isSelectionEvent)
		{
			Boolean isEmptyGrid = this.dgd_PopupsAudit.isEmpty();

			this.btn_DownloadExcel.setEnabled(!isEmptyGrid);
		}

		this.btn_Refresh.setEnabled(initParam != null);
	}

	private void updateSortOrderCriteria(List<GridSortOrder<AuditPopUpReportDTO>> sortOrderList)
	{
		if (sortOrderList != null && !sortOrderList.isEmpty())
		{
			ArrayList<OrderCriteriaDTO> resultList = new ArrayList<>();

			for (GridSortOrder<AuditPopUpReportDTO> sortOrder : sortOrderList)
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
			this.orderCriteria = new OrderCriteriaDTO[] { order };

			GridSortOrder<AuditPopUpReportDTO> sortOrder = new GridSortOrder<>(this.dgd_PopupsAudit.getColumn(DEFAULT_SORT_FIELD), SortDirection.DESCENDING);
			sortOrderList = new ArrayList<>();
			sortOrderList.add(sortOrder);

			this.dgd_PopupsAudit.setSortOrder(sortOrderList);
		}
	}

	private void doDownloadFile(Object result, BbrWorkExecutor sender)
	{
		PopupAuditManagement senderReport = (PopupAuditManagement) sender;
		if (senderReport != null)
		{
			senderReport.downloadLinkFile(result);
		}
	}

	private AuditPopUpReportResultDTO executeService(BbrUI bbrUIParent)
	{
		AuditPopUpReportResultDTO result = null;

		if (this.initParam != null)
		{
			try
			{
				AuditPopUpReportInitParamDTO initParamDTO = getAuditPopUpReportInitParam(bbrUIParent);
				result = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(bbrUIParent).getAuditPopUpReport(initParamDTO);
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
		}

		return result;
	}

	private AuditPopUpReportInitParamDTO getAuditPopUpReportInitParam(BbrUI bbrUIParent)
	{
		AuditPopUpReportInitParamDTO initParamDTO = this.initParam;
		initParamDTO.setNeedPageInfo(true);
		initParamDTO.setOrderBy(orderCriteria);
		initParamDTO.setRows(this.MAX_ROWS_NUMBER);
		initParamDTO.setPageNumber((!resetPageInfo && this.pagingManager.getCurrentPage() > 0) ? (Integer) this.pagingManager.getCurrentPage() : this.DEFAULT_PAGE_NUMBER);
		initParamDTO.setLocale(bbrUIParent.getLocale());
		return initParamDTO;
	}

	private FileDownloadInfoResultDTO executeDownloadService(BbrUI bbrUIParent, DownloadFormats selectedFormat)
	{
		FileDownloadInfoResultDTO fileResult = null;
		if (selectedFormat != null)
		{
			try
			{
				if (this.btn_DownloadTriggerButton == this.btn_DownloadExcel)
				{
					fileResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(bbrUIParent).downloadAuditPopUpReport(this.initParam,
							selectedFormat.getValue(),
							bbrUIParent.getUser().getId());
				}
			}
			catch (BbrUserException ex)
			{
				AppUtils.getInstance().doLogOut(ex.getMessage(), bbrUIParent);
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

		PopupAuditManagement senderReport = (PopupAuditManagement) sender;

		if (result != null)
		{
			AuditPopUpReportResultDTO reportResult = (AuditPopUpReportResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				ListDataProvider<AuditPopUpReportDTO> dataprovider = new ListDataProvider<>(Arrays.asList(reportResult.getAuditPopUpReportDTOs()));

				senderReport.initializeDataGridColumns();
				senderReport.dgd_PopupsAudit.setDataProvider(dataprovider, senderReport.resetPageInfo);
				senderReport.updateButtons(false);
				if (senderReport.resetPageInfo)
				{
					PageInfoDTO pageInfo = reportResult.getPageInfoDTO() != null ? reportResult.getPageInfoDTO() : new PageInfoDTO();

					BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(),
							pageInfo.getTotalpages(), pageInfo.getRows(),
							pageInfo.getTotalrows());

					senderReport.pagingManager.setPages(bbrPage, senderReport.resetPageInfo);

				}

				if (senderReport.resetPageInfo && reportResult.getAuditPopUpReportDTOs().length == 0)
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

		if (errordescription.length() > 0 && senderReport.trackReport)
		{
			// Track Error
			senderReport.trackError(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription(), errordescription, null, this);
		}

		senderReport.stopWaiting();
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
}
