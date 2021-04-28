package bbr.b2b.portal.modules.customer;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.BaseResultsDTO;
import bbr.b2b.common.adtclasses.classes.FileUploadInitParamDTO;
import bbr.b2b.customer.report.classes.AddOrUpdatePendingFileInitParamDTO;
import bbr.b2b.customer.report.classes.PendingLoadFilesResultDTO;
import bbr.b2b.customer.report.classes.UploadMastersLoadInitParamDTO;
import bbr.b2b.portal.classes.constants.BbrAppConstants;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.UploadMessageBox;
import bbr.b2b.portal.classes.wrappers.customer.CustomerLoadMastersInfo;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer.BbrHInputFieldContainerBuilder;
// import bbr.b2b.portal.classes.utils.app.commercial.CommercialUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.filters.customer_service.ControlPanelFilter;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.customer.CustomerServiceConstants;
import bbr.b2b.portal.utils.FileHandlerUtils;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.classes.files_transfer.BbrFileUploader;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrErrorList;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;

public class MastersLoadManagement extends BbrModule implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants
	private static final long					serialVersionUID				= -3947159600615633249L;
	public static final String					LAST_INVENTORY_SEND				= "last_inventory_send";
	public static final String					LAST_SALES_SEND					= "last_sales_send";
	public static final String					LATE_SALES						= "late_sales";
	public static final String					NOT_LOADED_PRODUCT				= "not_loaded_product";
	public static final String					NOT_LOADED_LOCAL				= "not_loaded_local";
	public static final String					PENDING_REPROCESS_SALES			= "pending_reprocess_sales";
	public static final String					PENDING_REPROCESS_INVENTORY		= "pending_reprocess_inventory";

	public static final String					SUBLABEL_STYLE					= "sublabel";
	public static final String					PANEL_MASTERSLOAD_CONTENT_STYLE	= "panel-mastersload-content";
	public static final String					MARGIN_STYLE					= "margin-10";
	public static final String					PADDING_STYLE					= "padding-15";
	public static final String					BBR_MARGIN_WINDOWS_STYLE		= "bbr-margin-windows";
	public static final String					CONTROL_PANEL_GRID_STYLE		= "control-panel-grid bbr-overflow-auto";
	public static final String					MASTERSLOAD_CONTENT_STYLE		= "mastersload-content";
	// Components
	private BbrWork<PendingLoadFilesResultDTO>	reportWork						= null;
	private HorizontalLayout					content							= null;
	private Panel								panelLoads						= null;
	private ControlPanelFilter					filterLayout					= null;
	// Variables
	private CompanyDataDTO						selectedCompany					= null;
	private BbrWork<BaseResultsDTO>				uploadWork						= null;
	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public MastersLoadManagement(BbrUI bbrUIParent)
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
		filterLayout = new ControlPanelFilter(this);
		filterLayout.initializeView();
		this.setBbrFilterContainer(filterLayout);

		// CONTENT LAYOUT
		this.content = new HorizontalLayout();
		this.content.setSizeFull();

		// MASTERS LOAD
		this.panelLoads = this.initializeMasterLoadsCards(this, new PendingLoadFilesResultDTO());

		this.content.addComponent(panelLoads);
		this.content.setComponentAlignment(panelLoads, Alignment.MIDDLE_CENTER);

		Button btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
		btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
		btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler(e));
		btn_Refresh.addStyleName("toolbar-button");

		HorizontalLayout rightButtonsBar = new HorizontalLayout();
		rightButtonsBar.addComponents(btn_Refresh);
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
		mainLayout.addComponents(toolBar);
		mainLayout.addComponents(this.content);
		mainLayout.setExpandRatio(this.content, 1F);

		this.addComponents(mainLayout);

		reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		reportWork.addFunction(new Function<Object, PendingLoadFilesResultDTO>()
		{
			@Override
			public PendingLoadFilesResultDTO apply(Object t)
			{
				return executeService(MastersLoadManagement.this.getBbrUIParent());
			}
		});

		this.generateFilterClick();
	}

	@Override
	public void filterApplied_handler(BbrFilterEvent event)
	{
		if ((event != null) && (event.getResultObject() != null))
		{
			this.selectedCompany = (CompanyDataDTO) event.getResultObject();

			this.startWaiting();
			this.executeBbrWork(reportWork);
		}
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		MastersLoadManagement senderReport = (MastersLoadManagement) sender;

		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				senderReport.doUpdateReport(result, senderReport);
			}
			else if (triggerObject instanceof Component)
			{
				senderReport.reportUploaded(result, senderReport);
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

	private void uploadExcelFinished_handler(FinishedEvent e, Component trigger, String fileType, Long uploadId)
	{
		uploadWork = new BbrWork<>(this, this.getBbrUIParent(), trigger);
		uploadWork.addFunction(new Function<Object, BaseResultsDTO>()
		{
			@Override
			public BaseResultsDTO apply(Object t)
			{
				FileUploadInitParamDTO initParams = new FileUploadInitParamDTO();
				initParams.setFileName(e.getFilename());
				initParams.setFilePath(MastersLoadManagement.this.getUploadPathByType(fileType));

				return executeUploadService(MastersLoadManagement.this.getBbrUIParent(), initParams, fileType, uploadId);
			}
		});

		this.startWaiting();
		this.executeBbrWork(uploadWork);
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	// public String getFolderByType(String fileType)
	// {
	// String result = "";
	//
	// if (fileType.equalsIgnoreCase(PortalConstants.HOMOLOGATORFILE))
	// {
	// result = "HOMOLOGADOR_PRODUCTOS/";
	// }
	// else if (fileType.equalsIgnoreCase(PortalConstants.LOCALFILE))
	// {
	// result = "MAESTRA_LOCALES/";
	// }
	// else if (fileType.equalsIgnoreCase(PortalConstants.PRODUCTFILE))
	// {
	// result = "MAESTRA_PRODUCTOS/";
	// }
	//
	// return result;
	// }

	private void showUploadProcessHandler(CustomerLoadMastersInfo info, Component trigger)
	{
		// Content Label
		String caption = I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_CUSTOMER, "pending_file_exists", "\"" + info.getPendingFilename() + "\"", "\"" + info.getLoadDate() + "\"", "\"" + info.getUserName() + "\"");
		String fileType = info.getFileType();
		UploadMessageBox uploadMessageBox = new UploadMessageBox(this.getBbrUIParent(), I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"),
				caption);
		uploadMessageBox.setMessageBoxWidth("420px");
		uploadMessageBox.setMessageBoxHeight("250px");
		uploadMessageBox.setUploadButtonCaption(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "yes"));
		uploadMessageBox.setCloseButtonCaption(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "no"));
		uploadMessageBox.setPathToUpload(this.getUploadPathByType(fileType));
		uploadMessageBox.addBbrEventListener((BbrEventListener & Serializable) e -> uploadHandler(e, trigger, info));
		uploadMessageBox.open(true, true, this);
	}

	private void uploadHandler(BbrEvent e, Component trigger, CustomerLoadMastersInfo info)
	{
		FinishedEvent eventResult = (FinishedEvent) e.getResultObject();
		this.uploadExcelFinished_handler(eventResult, trigger, info.getFileType(), info.getId());
	}

	private String getUploadPathByType(String fileType)
	{
		return BbrAppConstants.getMastersLoadPathByType(fileType);
	}

	private VerticalLayout getUploadCard(CustomerLoadMastersInfo info)
	{
		Component cmp_UploadMastersLoad = null;
		if (info.isButtonOrUpload())
		{
			Button btn_UploadMastersLoad = new Button("", EnumToolbarButton.UPLOAD_ALTERNATIVE.getResource());
			btn_UploadMastersLoad.setDescription(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_CUSTOMER, "upload_csv_file"));
			btn_UploadMastersLoad.addClickListener((ClickListener & Serializable) e -> this.showUploadProcessHandler(info, btn_UploadMastersLoad));
			btn_UploadMastersLoad.addStyleName("toolbar-button");

			cmp_UploadMastersLoad = btn_UploadMastersLoad;
		}
		else if (!info.isButtonOrUpload())
		{
			String fileType = info.getFileType();
			FileHandlerUtils.createPathIfNotExists(this.getUploadPathByType(fileType));
			BbrFileUploader uploaderReceiver = new BbrFileUploader(this.getUploadPathByType(fileType));
			Upload upl_UploadMastersLoad = new Upload(null, uploaderReceiver);
			upl_UploadMastersLoad.setDescription(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_CUSTOMER, "upload_csv_file"));
			upl_UploadMastersLoad.addStyleName("bbr-upload-excel-button");
			upl_UploadMastersLoad.addFinishedListener((FinishedListener & Serializable) e -> uploadExcelFinished_handler(e, upl_UploadMastersLoad, info.getFileType(), info.getId()));
			upl_UploadMastersLoad.setEnabled(true);
			cmp_UploadMastersLoad = upl_UploadMastersLoad;
		}

		BbrHInputFieldContainer pnlMastersLoad = new BbrHInputFieldContainerBuilder()
				.withCaption(info.getCaption())
				.withLabelWidth("280px")
				.withComponent(cmp_UploadMastersLoad)
				.build();
		pnlMastersLoad.setComponentAlignment(pnlMastersLoad.getLabel(), Alignment.MIDDLE_CENTER);
		pnlMastersLoad.setComponentAlignment(cmp_UploadMastersLoad, Alignment.MIDDLE_CENTER);

		VerticalLayout infoContainerLabelsMastersLoad = new VerticalLayout();
		infoContainerLabelsMastersLoad.setMargin(false);
		infoContainerLabelsMastersLoad.setSpacing(false);

		if (info.getPendingFilename() != null)
		{
			String pending = info.getPendingFilename();
			Label lbl_PendingLoadFileName = new Label(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_CUSTOMER, "pending_file_for_load") + pending);
			lbl_PendingLoadFileName.addStyleName(SUBLABEL_STYLE);
			infoContainerLabelsMastersLoad.addComponent(lbl_PendingLoadFileName);
		}

		if (info.getLoadDate() != null)
		{
			String loadDate = info.getLoadDate();
			Label lbl_PendingLoadDateTime = new Label(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_CUSTOMER, "pending_load_date") + loadDate);
			lbl_PendingLoadDateTime.addStyleName(SUBLABEL_STYLE);
			infoContainerLabelsMastersLoad.addComponent(lbl_PendingLoadDateTime);
		}

		VerticalLayout infoContainerMastersLoad = new VerticalLayout();
		infoContainerMastersLoad.setMargin(false);
		infoContainerMastersLoad.setSizeFull();
		infoContainerMastersLoad.setStyleName(MASTERSLOAD_CONTENT_STYLE);
		infoContainerMastersLoad.addStyleName(PADDING_STYLE);
		infoContainerMastersLoad.addComponent(pnlMastersLoad);
		infoContainerMastersLoad.addComponent(infoContainerLabelsMastersLoad);
		return infoContainerMastersLoad;
	}

	private PendingLoadFilesResultDTO executeService(BbrUI bbrUIParent)
	{
		PendingLoadFilesResultDTO result = new PendingLoadFilesResultDTO();

		try
		{
			String pvcode = this.getPvCode();
			result = EJBFactory.getCustomerEJBFactory().getCustomerManagerLocal(bbrUIParent).getPendingLoadFilesByProvider(pvcode);
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

	private void reportUploaded(Object result, BbrWorkExecutor sender)
	{
		MastersLoadManagement senderReport = (MastersLoadManagement) sender;

		if (result != null)
		{
			BaseResultsDTO reportResult = (BaseResultsDTO) result;

			// MULTIPLES ALERTAS DE MENSAJES
			boolean canOpenAlert = !senderReport.getBbrUIParent().hasAlertWindowOpen();

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), false);

			if (!error.hasError())
			{
				senderReport.showInfoMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_MODULES_COMMERCIAL, "file_processed"),
						null,
						() -> senderReport.generateFilterClick());
			}
			else
			{
				if (reportResult.getBaseresults() != null && reportResult.getBaseresults().length > 0)
				{
					if (canOpenAlert)
					{
						BbrErrorList<BaseResultDTO> winErrors = new BbrErrorList<>(senderReport.getBbrUIParent(),
								reportResult.getBaseresults(),
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
	}

	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{
		MastersLoadManagement senderReport = (MastersLoadManagement) sender;

		if (result != null)
		{
			PendingLoadFilesResultDTO reportResult = (PendingLoadFilesResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				Panel oldPanel = senderReport.panelLoads;
				senderReport.panelLoads = initializeMasterLoadsCards(senderReport, reportResult);
				senderReport.content.replaceComponent(oldPanel, senderReport.panelLoads);
			}
		}
		senderReport.stopWaiting();
	}

	private Panel initializeMasterLoadsCards(MastersLoadManagement senderReport, PendingLoadFilesResultDTO reportResult)
	{
		// MAESTRO DE PRODUCTOS
		CustomerLoadMastersInfo infoMasterOfProducts = new CustomerLoadMastersInfo();
		infoMasterOfProducts.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_CUSTOMER, "pending_masters_product"));
		infoMasterOfProducts.setFileType(CustomerServiceConstants.PRODUCTFILE);
		if (reportResult.getProduct() != null && reportResult.getProduct().getMasterLoadDTO() != null)
		{
			String username = reportResult.getProduct().getMasterLoadDTO().getUsername() != null ? reportResult.getProduct().getMasterLoadDTO().getUsername()
					: reportResult.getProduct().getMasterLoadDTO().getUseremail() != null ? reportResult.getProduct().getMasterLoadDTO().getUseremail() : "";
			infoMasterOfProducts.setUserName(username);
			infoMasterOfProducts.setId(reportResult.getProduct().getMasterLoadDTO().getId());
			infoMasterOfProducts.setPendingFilename(reportResult.getProduct().getMasterLoadDTO().getFilenameprov());
			infoMasterOfProducts.setLoadDate(senderReport.formatDate(reportResult.getProduct().getMasterLoadDTO().getUploaddate()));
		}
		else
		{
			infoMasterOfProducts.setButtonOrUpload(false);
		}

		VerticalLayout infoContainerMastersLoad = getUploadCard(infoMasterOfProducts);

		// HOMOLOGADOR DE PRODUCTOS
		CustomerLoadMastersInfo infoHomologo = new CustomerLoadMastersInfo();
		infoHomologo.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_CUSTOMER, "pending_product_homologo"));
		infoHomologo.setFileType(CustomerServiceConstants.HOMOLOGATORFILE);
		if (reportResult.getHomologation() != null && reportResult.getHomologation().getMasterLoadDTO() != null)
		{
			String username = reportResult.getHomologation().getMasterLoadDTO().getUsername() != null ? reportResult.getHomologation().getMasterLoadDTO().getUsername()
					: reportResult.getHomologation().getMasterLoadDTO().getUseremail() != null ? reportResult.getHomologation().getMasterLoadDTO().getUseremail() : "";
			infoHomologo.setUserName(username);
			infoHomologo.setId(reportResult.getHomologation().getMasterLoadDTO().getId());
			infoHomologo.setPendingFilename(reportResult.getHomologation().getMasterLoadDTO().getFilenameprov());
			infoHomologo.setLoadDate(senderReport.formatDate(reportResult.getHomologation().getMasterLoadDTO().getUploaddate()));
		}
		else
		{
			infoHomologo.setButtonOrUpload(false);
		}

		VerticalLayout infoContainerProductsHomologo = getUploadCard(infoHomologo);

		// MAESTRO DE TIENDAS
		CustomerLoadMastersInfo infoMasterStore = new CustomerLoadMastersInfo();
		infoMasterStore.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_CUSTOMER, "pending_master_stores"));
		infoMasterStore.setFileType(CustomerServiceConstants.LOCALFILE);
		if (reportResult.getLocation() != null && reportResult.getLocation().getMasterLoadDTO() != null)
		{
			String username = reportResult.getLocation().getMasterLoadDTO().getUsername() != null ? reportResult.getLocation().getMasterLoadDTO().getUsername()
					: reportResult.getLocation().getMasterLoadDTO().getUseremail() != null ? reportResult.getLocation().getMasterLoadDTO().getUseremail() : "";
			infoMasterStore.setUserName(username);
			infoMasterStore.setId(reportResult.getLocation().getMasterLoadDTO().getId());
			infoMasterStore.setPendingFilename(reportResult.getLocation().getMasterLoadDTO().getFilenameprov());
			infoMasterStore.setLoadDate(senderReport.formatDate(reportResult.getLocation().getMasterLoadDTO().getUploaddate()));
		}
		else
		{
			infoMasterStore.setButtonOrUpload(false);
		}

		VerticalLayout infoContainerMastersStore = getUploadCard(infoMasterStore);

		VerticalLayout loadCards = new VerticalLayout();
		loadCards.setMargin(false);
		loadCards.setSizeFull();
		loadCards.setSpacing(false);
		loadCards.addComponents(infoContainerMastersLoad, infoContainerProductsHomologo, infoContainerMastersStore);
		loadCards.setComponentAlignment(infoContainerMastersLoad, Alignment.MIDDLE_CENTER);
		loadCards.setComponentAlignment(infoContainerProductsHomologo, Alignment.MIDDLE_CENTER);
		loadCards.setComponentAlignment(infoContainerMastersStore, Alignment.MIDDLE_CENTER);

		Panel panelLoads = new Panel();
		panelLoads.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_CUSTOMER, "pending_masters_load"));
		panelLoads.setCaptionAsHtml(true);
		panelLoads.setStyleName(PANEL_MASTERSLOAD_CONTENT_STYLE);
		panelLoads.setContent(loadCards);
		panelLoads.setWidth("450px");

		return panelLoads;

	}

	private BaseResultsDTO executeUploadService(BbrUI bbrUIParent, FileUploadInitParamDTO initParams, String fileType, Long uploadId)
	{
		BaseResultsDTO result = null;

		if (initParams != null)
		{
			try
			{
				// Start Tracking
				this.getTimingMngr().startTimer();

				String pvcode = this.getPvCode();
				boolean isAddOrUpdate = uploadId != null ? false : true;
				AddOrUpdatePendingFileInitParamDTO addInitParamDTO = new AddOrUpdatePendingFileInitParamDTO();
				addInitParamDTO.setFilenameprov(initParams.getFileName());
				addInitParamDTO.setLocale(bbrUIParent.getUser().getLocale());
				addInitParamDTO.setUseremail(bbrUIParent.getUser().getEmail());
				addInitParamDTO.setUsername(bbrUIParent.getUser().getFullName());
				addInitParamDTO.setPvcode(pvcode);
				addInitParamDTO.setAdd(isAddOrUpdate);
				addInitParamDTO.setFilenamebbr(getFormattedFilename(fileType, pvcode));
				if (!isAddOrUpdate)
				{
					addInitParamDTO.setId(uploadId);
				}

				UploadMastersLoadInitParamDTO initParamDTO = new UploadMastersLoadInitParamDTO();
				initParamDTO.setFileUploadInitParamDTO(initParams);
				initParamDTO.setAddOrUpdatePendingFileInitParamDTO(addInitParamDTO);
				initParamDTO.setUserId(bbrUIParent.getUser().getId());
				initParamDTO.setFileType(fileType);
				result = EJBFactory.getCustomerEJBFactory().getCustomerManagerLocal(bbrUIParent).doUploadMasters(initParamDTO);
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

	private String getFormattedFilename(String fileType, String pvcode)
	{
		return fileType + "_" + pvcode + "_" + this.formatDateTimeStamp(LocalDateTime.now()) + ".csv";
	}

	private String getPvCode()
	{
		return this.selectedCompany != null ? this.selectedCompany.getRut() : null;
	}

	private void generateFilterClick()
	{
		ClickEvent e = new ClickEvent(filterLayout);
		filterLayout.buttonClick(e);
	}

	private String formatDate(LocalDateTime date)
	{
		return date != null ? DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(date) : "";
	}

	private String formatDateTimeStamp(LocalDateTime date)
	{
		return date != null ? DateTimeFormatter.ofPattern("yyyyMMddHHMMSS").format(date) : "";
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
