package bbr.b2b.portal.modules.users.management;

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
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.BaseResultsDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.FileUploadInitParamDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.portal.classes.constants.BbrAppConstants;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.UploadMessageBox;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.filters.management.CompaniesClassificationManagementFilter;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.utils.FileHandlerUtils;
import bbr.b2b.users.report.classes.CompanyClassificationInfoDTO;
import bbr.b2b.users.report.classes.CompanyClassificationReportInitParamDTO;
import bbr.b2b.users.report.classes.CompanyClassificationReportResultDTO;
import bbr.b2b.users.report.classes.LogInfoResultDTO;
import bbr.b2b.users.report.classes.LogInfoUserDTO;
import cl.bbr.core.classes.basics.BbrPage;
import cl.bbr.core.classes.constants.DownloadFormats;
import cl.bbr.core.classes.constants.TrackingConstants;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrErrorList;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.paging.BbrPagingManager;
import cl.bbr.core.components.widgets.bbrpopupbutton.BbrPopupButton;

public class CompaniesClassificationManagement extends BbrModule implements BbrWorkExecutor
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants
	private static final long								serialVersionUID				= 4982315246309578782L;
	private static final String								EMRUT_COLUMN_ID					= "emrut";
	private static final String								EMNAME_COLUMN_ID				= "emname";
	private static final String								CLASSIFICATIONDESC_COLUMN_ID	= "classificationdesc";
	private static final String								CLASSIFICATIONNAME_COLUMN_ID	= "classificationname";
	private Integer											companiesClasificationLogType	= 2;
	// Components
	private VerticalLayout									mainLayout						= null;
	private Button											btn_UploadMassive				= null;
	private Button											btn_UploadSelective				= null;
	private Button											btn_DownloadReport				= null;
	private Button											btn_DownloadTemplate			= null;
	private BbrPopupButton									btn_Upload						= null;
	private BbrPopupButton									btn_Download					= null;
	private Label											lbl_LastUpdate					= null;
	private BbrAdvancedGrid<CompanyClassificationInfoDTO>	dgd_Classifications				= null;
	private BbrPagingManager								pagingManager					= null;
	// Variables
	private final String									DEFAULT_SORT_FIELD				= EMRUT_COLUMN_ID;
	private final int										DEFAULT_PAGE_NUMBER				= 1;
	private final int										MAX_ROWS_NUMBER					= 200;
	private OrderCriteriaDTO[]								orderCriteria					= null;
	private BbrWork<CompanyClassificationReportResultDTO>	classificationsReportWork		= null;
	private BbrWork<LogInfoResultDTO>						getUpdateInfoWork				= null;
	private Boolean											trackReport						= true;
	// private Boolean resetPageInfo = true;
	private CompanyClassificationReportInitParamDTO			initParam						= null;
	private Boolean											resetPageInfo					= true;
	private BbrWork<FileDownloadInfoResultDTO>				downloadsWork					= null;
	private BbrWork<BaseResultsDTO>							uploadWork						= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public CompaniesClassificationManagement(BbrUI bbrUIParent)
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
		CompaniesClassificationManagementFilter filterLayout = new CompaniesClassificationManagementFilter(this);
		filterLayout.initializeView();
		this.setBbrFilterContainer(filterLayout);

		// Paging Manager

		this.pagingManager = new BbrPagingManager();
		this.pagingManager.setLocale(this.getUser().getLocale());
		this.pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> this.pagingChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);

		// Encabezado Titulo

		Label lbl_HeadTitle = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "registered_classifications_mandatorypositions_in_system"));
		lbl_HeadTitle.addStyleName("header_style");

		Button btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
		btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
		btn_Refresh.addClickListener((ClickListener & Serializable) e -> refreshReport_clickHandler());
		btn_Refresh.addStyleName("toolbar-button");

		// DOWNLOAD BUTTONS SECTION
		VerticalLayout cmp_DownloadButtons = new VerticalLayout();
		cmp_DownloadButtons.setMargin(new MarginInfo(false, true));
		cmp_DownloadButtons.setSpacing(false);

		this.btn_DownloadReport = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "download_report"));
		this.btn_DownloadReport.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "download_report"));
		this.btn_DownloadReport.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadFile_downloadHandler(e));
		this.btn_DownloadReport.addStyleName("action-button");
		cmp_DownloadButtons.addComponent(this.btn_DownloadReport);

		this.btn_DownloadTemplate = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "download_template"));
		this.btn_DownloadTemplate.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "download_template"));
		this.btn_DownloadTemplate.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadFile_downloadHandler(e));
		this.btn_DownloadTemplate.addStyleName("action-button");
		cmp_DownloadButtons.addComponent(this.btn_DownloadTemplate);

		this.btn_Download = new BbrPopupButton("");
		this.btn_Download.setIcon(EnumToolbarButton.DOWNLOAD_PRIMARY.getResource());
		this.btn_Download.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "downloads"));
		this.btn_Download.addStyleName("toolbar-button");
		this.btn_Download.setContentLayout(cmp_DownloadButtons);
		this.btn_Download.setClosePopupOnOutsideClick(true);

		// END DOWNLOAD BUTTONS SECTION

		// UPLOAD BUTTONS SECTION
		VerticalLayout cmp_UploadButtons = new VerticalLayout();
		cmp_UploadButtons.setMargin(new MarginInfo(false, true));
		cmp_UploadButtons.setSpacing(false);

		this.btn_UploadMassive = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "actualization_massive"));
		this.btn_UploadMassive.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "actualization_massive"));
		this.btn_UploadMassive.addClickListener((ClickListener & Serializable) e -> this.showUploadProcessHandler(true));
		this.btn_UploadMassive.addStyleName("action-button");
		cmp_UploadButtons.addComponent(this.btn_UploadMassive);

		this.btn_UploadSelective = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "actualization_selective"));
		this.btn_UploadSelective.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "actualization_selective"));
		this.btn_UploadSelective.addClickListener((ClickListener & Serializable) e -> this.showUploadProcessHandler(false));
		this.btn_UploadSelective.addStyleName("action-button");
		cmp_UploadButtons.addComponent(this.btn_UploadSelective);

		this.btn_Upload = new BbrPopupButton("");
		this.btn_Upload.setIcon(EnumToolbarButton.UPLOAD_ALTERNATIVE.getResource());
		this.btn_Upload.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "uploads"));
		this.btn_Upload.addStyleName("toolbar-button");
		this.btn_Upload.setContentLayout(cmp_UploadButtons);
		this.btn_Upload.setClosePopupOnOutsideClick(true);

		// END UPLOAD BUTTONS SECTION

		HorizontalLayout adminsButtons = new HorizontalLayout();
		adminsButtons.setHeight("30px");
		adminsButtons.addStyleName("toolbar-layout");
		adminsButtons.addComponents(this.btn_Download, this.btn_Upload);
		adminsButtons.setComponentAlignment(this.btn_Download, Alignment.MIDDLE_LEFT);
		adminsButtons.setComponentAlignment(this.btn_Upload, Alignment.MIDDLE_LEFT);

		// Grilla Perfiles

		this.dgd_Classifications = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.dgd_Classifications.setSortable(false);
		this.dgd_Classifications.setSelectionMode(SelectionMode.SINGLE);
		this.dgd_Classifications.setPagingManager(this.pagingManager, this.DEFAULT_SORT_FIELD);
		this.dgd_Classifications.addSortListener((SortListener<GridSortOrder<CompanyClassificationInfoDTO>> & Serializable) e -> this.dataGrid_sortHandler(e));
		this.initializeProfilesGridColumns();
		this.dgd_Classifications.addStyleName("report-grid");
		this.dgd_Classifications.setWidth("1050px");
		this.dgd_Classifications.setHeight("100%");

		// AQUI VAN LAS 2 GRILLAS
		HorizontalLayout dataGridsSpace = new HorizontalLayout();
		dataGridsSpace.addComponents(this.dgd_Classifications);
		dataGridsSpace.setComponentAlignment(this.dgd_Classifications, Alignment.MIDDLE_CENTER);
		dataGridsSpace.setHeight("100%");
		dataGridsSpace.setMargin(false);
		dataGridsSpace.setSpacing(false);

		// Texto Ultima Actualizacion

		this.lbl_LastUpdate = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_update_date") + ": ");

		VerticalLayout buttonsAndGrids = new VerticalLayout();
		buttonsAndGrids.addComponents(adminsButtons, dataGridsSpace, this.pagingManager, this.lbl_LastUpdate);
		buttonsAndGrids.setExpandRatio(adminsButtons, 0.1F);
		buttonsAndGrids.setExpandRatio(dataGridsSpace, 0.9F);
		buttonsAndGrids.setExpandRatio(this.lbl_LastUpdate, 0.1F);
		buttonsAndGrids.setComponentAlignment(adminsButtons, Alignment.MIDDLE_RIGHT);
		buttonsAndGrids.setComponentAlignment(this.lbl_LastUpdate, Alignment.MIDDLE_RIGHT);
		buttonsAndGrids.setHeight("100%");
		buttonsAndGrids.setWidth("1050px");
		buttonsAndGrids.setMargin(false);
		buttonsAndGrids.setSpacing(false);

		HorizontalLayout titleBar = new HorizontalLayout();
		titleBar.setWidth("100%");
		titleBar.addComponents(lbl_HeadTitle, btn_Refresh);
		titleBar.setComponentAlignment(lbl_HeadTitle, Alignment.MIDDLE_LEFT);
		titleBar.setComponentAlignment(btn_Refresh, Alignment.MIDDLE_RIGHT);
		titleBar.addStyleName("filter-toolbar");
		titleBar.addStyleName("toolbar-layout");
		titleBar.setExpandRatio(lbl_HeadTitle, 1F);

		// Main Layout

		this.mainLayout = new VerticalLayout();
		this.mainLayout.addStyleName("report-layout");
		this.mainLayout.setSizeFull();
		this.mainLayout.setMargin(false);
		this.mainLayout.addComponents(buttonsAndGrids);
		this.mainLayout.setComponentAlignment(buttonsAndGrids, Alignment.MIDDLE_CENTER);
		this.mainLayout.setExpandRatio(buttonsAndGrids, 1F);

		this.addComponent(this.mainLayout);

		this.updateButtons();
		this.updateSortOrderCriteria(null);
		this.classificationsReportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		this.classificationsReportWork.addFunction(new Function<Object, CompanyClassificationReportResultDTO>()
		{
			@Override
			public CompanyClassificationReportResultDTO apply(Object t)
			{
				return executeService(CompaniesClassificationManagement.this.getBbrUIParent());
			}
		});
	}

	private void showUploadProcessHandler(boolean isMassive)
	{
		// Content Label
		String caption = isMassive ? I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "actualization_massive_info")
				: I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "actualization_selective_info");

		UploadMessageBox uploadMessageBox = new UploadMessageBox(this.getBbrUIParent(), I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"),
				caption);
		uploadMessageBox.setMessageBoxHeight("200px");
		uploadMessageBox.setMessageBoxWidth("310px");
		uploadMessageBox.setUploadButtonCaption(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "accept"));
		uploadMessageBox.setCloseButtonCaption(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"));
		uploadMessageBox.addBbrEventListener((BbrEventListener & Serializable) e -> uploadHandler(e, isMassive));
		uploadMessageBox.open(true, true, this);
	}

	private void uploadHandler(BbrEvent e, boolean isMassive)
	{
		FinishedEvent eventResult = (FinishedEvent) e.getResultObject();
		boolean isValidFile = this.validateFileExtensions(eventResult.getFilename());
		if (isValidFile)
		{
			this.defineUploadService(eventResult.getFilename(), isMassive);
		}
		else
		{
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "validate_excel_extension"));
		}
	}

	private boolean validateFileExtensions(String filename)
	{
		boolean result = FileHandlerUtils.FilesFormats.XLSX.getValue().equals(FileHandlerUtils.getFileExtension(filename));
		// if (!result)
		// {
		// result =
		// FileHandlerUtils.FilesFormats.XLS.getValue().equals(FileHandlerUtils.getFileExtension(filename));
		// }
		return result;
	}

	@Override
	public void filterApplied_handler(BbrFilterEvent event)
	{
		if ((event != null) && (event.getResultObject() != null))
		{
			this.initParam = (CompanyClassificationReportInitParamDTO) event.getResultObject();

			this.trackReport = true;
			this.resetPageInfo = true;

			this.startWaiting();
			this.executeBbrWork(classificationsReportWork);
		}
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				this.doUpdateClassificationsReport(result, sender);
				this.refreshLastUpdateInfo();
			}
			else if (triggerObject.equals(this.btn_UploadMassive))
			{
				this.reportUploaded(result, sender, true);
			}
			else if (triggerObject.equals(this.btn_UploadSelective))
			{
				this.reportUploaded(result, sender, false);
			}
			else if (triggerObject == this.btn_DownloadTriggerButton)
			{
				this.doDownloadFile(result, sender, triggerObject);
			}
			else if (triggerObject == this.lbl_LastUpdate)
			{
				this.updateInfoLabel(result, sender);
			}
		}

		else
		{
			CompaniesClassificationManagement senderReport = (CompaniesClassificationManagement) sender;

			if (!senderReport.getBbrUIParent().hasAlertWindowOpen())
				senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));

			senderReport.stopWaiting();
		}
	}

	@Override
	protected void downloadFormat_selectedHandler(BbrEvent event)
	{
		// DownloadFormats selectedFormat = ((event != null) &&
		// (event.getResultObject() instanceof DownloadFormats)) ?
		// (DownloadFormats) event.getResultObject() : null;

		downloadsWork = new BbrWork<>(this, this.getBbrUIParent(), btn_DownloadTriggerButton);
		downloadsWork.addFunction(new Function<Object, FileDownloadInfoResultDTO>()
		{
			@Override
			public FileDownloadInfoResultDTO apply(Object t)
			{
				return executeDownloadService(CompaniesClassificationManagement.this.getBbrUIParent(), DownloadFormats.XLSX, btn_DownloadTriggerButton);
			}
		});

		this.startWaiting();
		this.executeBbrWork(downloadsWork);
	}

	private void reportUploaded(Object result, BbrWorkExecutor sender, Boolean isMassive)
	{
		CompaniesClassificationManagement senderReport = (CompaniesClassificationManagement) sender;

		if (result != null)
		{
			BaseResultsDTO reportResult = (BaseResultsDTO) result;

			// MULTIPLES ALERTAS DE MENSAJES
			boolean canOpenAlert = !senderReport.getBbrUIParent().hasAlertWindowOpen();

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), false);

			if (!error.hasError())
			{
				this.showInfoMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_MODULES_COMMERCIAL, "file_processed"));
			}
			else
			{
				if (reportResult.getBaseresults() != null && reportResult.getBaseresults().length > 0)
				{
					if (canOpenAlert)
					{
						BbrErrorList<BaseResultDTO> winErrors = new BbrErrorList<>(senderReport.getBbrUIParent(), reportResult.getBaseresults(),
								senderReport.getUser().getLocale());
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
						winErrors.setWidth("350px");
						winErrors.setHeight("350px");
						winErrors.open(true, true, this);
					}

				}
				else
				{
					ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), canOpenAlert);
				}
			}

		}

		senderReport.stopWaiting();

		this.startWaiting();
		this.executeBbrWork(classificationsReportWork);
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================
	private void defineUploadService(String fileName, boolean isMassive)
	{
		uploadWork = new BbrWork<>(this, this.getBbrUIParent(), isMassive ? this.btn_UploadMassive : this.btn_UploadSelective);
		uploadWork.addFunction(new Function<Object, BaseResultsDTO>()
		{
			@Override
			public BaseResultsDTO apply(Object t)
			{
				FileUploadInitParamDTO uploadInitParams = new FileUploadInitParamDTO();
				uploadInitParams.setFileName(fileName);
				uploadInitParams.setFilePath(BbrAppConstants.getUploadPathOfUser(CompaniesClassificationManagement.this.getBbrUIParent().getUser().getId()));

				return executeUploadService(CompaniesClassificationManagement.this.getBbrUIParent(), uploadInitParams, isMassive);
			}
		});

		this.startWaiting();
		this.executeBbrWork(uploadWork);
	}

	private BaseResultsDTO executeUploadService(BbrUI bbrUIParent, FileUploadInitParamDTO uploadInitParams, boolean isMassive)
	{
		BaseResultsDTO result = null;

		if (uploadInitParams != null)
		{
			try
			{
				LogInfoUserDTO loginfoDTO = getLogInfoUser(bbrUIParent);
				// Start Tracking
				this.getTimingMngr().startTimer();

				result = EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(bbrUIParent).doUpdateCompaniesClassificationExcel(uploadInitParams, isMassive,
						loginfoDTO);
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

	private LogInfoUserDTO getLogInfoUser(BbrUI bbrUI)
	{
		LogInfoUserDTO loginfo = new LogInfoUserDTO();
		loginfo.setUsername(bbrUI.getUser().getName());
		loginfo.setUserlastname(bbrUI.getUser().getLastName());
		loginfo.setWhen(LocalDateTime.now());
		return loginfo;
	}

	private void refreshReport_clickHandler()
	{
		this.startWaiting();

		this.trackReport = false;
		// this.resetPageInfo = true;
		this.executeBbrWork(classificationsReportWork);
	}

	private void pagingChange_Listener(BbrPagingEvent e)
	{
		if ((e != null) && e.getEventType().equals(BbrPagingEvent.PAGE_CHANGED) && (e.getResultObject() != null))
		{
			this.startWaiting();

			this.trackReport = false;
			this.resetPageInfo = false;
			this.executeBbrWork(classificationsReportWork);
		}
	}

	private void dataGrid_sortHandler(SortEvent<GridSortOrder<CompanyClassificationInfoDTO>> e)
	{
		if (e.isUserOriginated() && pagingManager.getTotalsPages() > 1)
		{
			this.startWaiting();

			this.updateSortOrderCriteria(e.getSortOrder());
			this.trackReport = false;
			this.resetPageInfo = true;
			this.executeBbrWork(classificationsReportWork);
		}
	}

	private void btn_DownloadFile_downloadHandler(ClickEvent e)
	{
		this.btn_DownloadTriggerButton = e.getButton();

		if (this.btn_DownloadTriggerButton == this.btn_DownloadReport)
		{
			this.downloadFormat_selectedHandler(null);
			// this.selectDownloadFileType(DownloadFormats.XLSX,
			// DownloadFormats.XLSX, DownloadFormats.CSV);
		}
		if (this.btn_DownloadTriggerButton == this.btn_DownloadTemplate)
		{
			this.downloadFormat_selectedHandler(null);
			// this.selectDownloadFileType(DownloadFormats.XLSX,
			// DownloadFormats.XLSX, DownloadFormats.CSV);
		}
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private void updateSortOrderCriteria(List<GridSortOrder<CompanyClassificationInfoDTO>> sortOrderList)
	{
		if (sortOrderList != null && !sortOrderList.isEmpty())
		{
			ArrayList<OrderCriteriaDTO> resultList = new ArrayList<>();

			for (GridSortOrder<CompanyClassificationInfoDTO> sortOrder : sortOrderList)
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

			GridSortOrder<CompanyClassificationInfoDTO> sortOrder = new GridSortOrder<>(this.dgd_Classifications.getColumn(DEFAULT_SORT_FIELD), SortDirection.DESCENDING);
			sortOrderList = new ArrayList<GridSortOrder<CompanyClassificationInfoDTO>>();
			sortOrderList.add(sortOrder);

			this.dgd_Classifications.setSortOrder(sortOrderList);
		}
	}

	private CompanyClassificationReportResultDTO executeService(BbrUI bbrUI)
	{
		CompanyClassificationReportResultDTO result = null;
		try
		{
			CompanyClassificationReportInitParamDTO initParam = this.getInitParamReport();

			// Start Tracking
			this.getTimingMngr().startTimer();
			result = EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(bbrUI).getCompanyClassificationReportByFilterType(initParam);
		}
		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), bbrUI);
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

	private CompanyClassificationReportInitParamDTO getInitParamReport()
	{
		CompanyClassificationReportInitParamDTO initParam = this.initParam != null ? this.initParam : new CompanyClassificationReportInitParamDTO();
		initParam.setNeedPageInfo(true);
		Integer requestedPage = (!resetPageInfo && this.pagingManager.getCurrentPage() > 0) ? (Integer) this.pagingManager.getCurrentPage() : this.DEFAULT_PAGE_NUMBER;
		initParam.setRows(this.MAX_ROWS_NUMBER);
		initParam.setPageNumber(requestedPage);
		initParam.setOrderBy(this.orderCriteria);
		return initParam;
	}

	private void doUpdateClassificationsReport(Object result, BbrWorkExecutor sender)
	{
		CompaniesClassificationManagement senderReport = (CompaniesClassificationManagement) sender;

		if (result != null)
		{
			CompanyClassificationReportResultDTO reportResult = (CompanyClassificationReportResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				CompanyClassificationInfoDTO[] classifications = reportResult.getCompanyClassificationInfoDTOs();
				ListDataProvider<CompanyClassificationInfoDTO> dataprovider = new ListDataProvider<>(Arrays.asList(classifications));

				this.dgd_Classifications.setDataProvider(dataprovider, false);

				this.updateButtons();

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

				if (reportResult.getCompanyClassificationInfoDTOs().length == 0)
				{
					senderReport.askToOpenFilter(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
							I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_FILTERS, "empty_filter_question"));
				}
			}
		}

		this.stopWaiting();
	}

	private void initializeProfilesGridColumns()
	{
		this.dgd_Classifications
				.addCustomColumn(CompanyClassificationInfoDTO::getEmrut, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_enterprise_code"))
				.setDescriptionGenerator(p -> p.getEmrut()).setWidth(250D).setId(EMRUT_COLUMN_ID);

		this.dgd_Classifications
				.addCustomColumn(CompanyClassificationInfoDTO::getEmname, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "company_social_reason"))
				.setDescriptionGenerator(p -> p.getEmname()).setWidth(250D).setId(EMNAME_COLUMN_ID);

		this.dgd_Classifications
				.addCustomColumn(CompanyClassificationInfoDTO::getClassificationname, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_classification_code"))
				.setDescriptionGenerator(p -> p.getClassificationname()).setWidth(250D).setId(CLASSIFICATIONNAME_COLUMN_ID);

		this.dgd_Classifications
				.addCustomColumn(CompanyClassificationInfoDTO::getClassificationdesc,
						I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_classification_description"))
				.setDescriptionGenerator(p -> p.getClassificationdesc())
				.setWidthUndefined().setId(CLASSIFICATIONDESC_COLUMN_ID);

	}

	private void refreshLastUpdateInfo()
	{
		this.getUpdateInfoWork = new BbrWork<>(this, this.getBbrUIParent(), this.lbl_LastUpdate);
		this.getUpdateInfoWork.addFunction(new Function<Object, LogInfoResultDTO>()
		{
			@Override
			public LogInfoResultDTO apply(Object t)
			{
				return executeLastUpdateInfoService(CompaniesClassificationManagement.this.getBbrUIParent());
			}
		});
		this.startWaiting();
		this.executeBbrWork(this.getUpdateInfoWork);
	}

	private LogInfoResultDTO executeLastUpdateInfoService(BbrUI bbrUI)
	{
		LogInfoResultDTO result = null;

		try
		{
			result = EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(bbrUI).getLastCompanyClassificationLogByType(this.companiesClasificationLogType);
		}

		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), bbrUI);
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

	private void updateInfoLabel(Object result, BbrWorkExecutor sender)
	{
		CompaniesClassificationManagement senderReport = (CompaniesClassificationManagement) sender;

		if (result != null)
		{
			LogInfoResultDTO callResult = (LogInfoResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(callResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				if ((callResult != null) && (callResult.getLoginfo() != null))
				{
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm");

					String newInfoLabel = I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_update_date") + ": "
							+ callResult.getLoginfo().getUsername() + " " + callResult.getLoginfo().getUserlastname() + " (" + dtf.format(callResult.getLoginfo().getWhen())
							+ ")";

					this.lbl_LastUpdate.setValue(newInfoLabel);
				}
			}
		}

		this.stopWaiting();
	}

	private void updateButtons()
	{
		if (this.dgd_Classifications != null)
		{
			Boolean state = !this.dgd_Classifications.isEmpty();
			this.btn_DownloadReport.setEnabled(state);
		}
	}

	private FileDownloadInfoResultDTO executeDownloadService(BbrUI bbrUIParent, DownloadFormats selectedFormat, Button downloadTriggerButton)
	{
		FileDownloadInfoResultDTO fileResult = null;
		if (selectedFormat != null)
		{
			try
			{
				if (this.btn_DownloadTriggerButton == this.btn_DownloadTemplate)
				{
					fileResult = EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(bbrUIParent)
							.downloadCompanyClassificationUpdateFileFormat(bbrUIParent.getUser().getId(), selectedFormat.getValue(), bbrUIParent.getUser().getLocale());
				}
				if (this.btn_DownloadTriggerButton == this.btn_DownloadReport)
				{
					CompanyClassificationReportInitParamDTO initParamDTO = new CompanyClassificationReportInitParamDTO();
					initParamDTO.setClasificationsId(this.initParam.getClasificationsId());
					initParamDTO.setFiltertype(this.initParam.getFiltertype());
					fileResult = EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(bbrUIParent).downloadCompanyClassificationExcelReport(initParamDTO,
							bbrUIParent.getUser().getId(), selectedFormat.getValue());
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

	private void doDownloadFile(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		CompaniesClassificationManagement senderReport = (CompaniesClassificationManagement) sender;
		if (senderReport != null)
		{
			senderReport.downloadLinkFile(result);
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
