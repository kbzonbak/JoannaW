package bbr.b2b.portal.modules.users.management;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.Function;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.BaseResultsDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.FileUploadInitParamDTO;
import bbr.b2b.portal.classes.constants.BbrAppConstants;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.ContactB2BArrayResultDTO;
import bbr.b2b.users.report.classes.ContactB2BDTO;
import bbr.b2b.users.report.classes.ContactLogDataDTO;
import cl.bbr.core.classes.basics.BbrUser;
import cl.bbr.core.classes.constants.DownloadFormats;
import cl.bbr.core.classes.constants.TrackingConstants;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.files_transfer.BbrFileUploader;
import cl.bbr.core.classes.utilities.BbrDateUtils;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrHSeparator;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;

public class RetailsContactsManagement extends BbrModule implements BbrWorkExecutor
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long					serialVersionUID	= 7027502352762624910L;

	private BbrAdvancedGrid<ContactB2BDTO>		dgd_Contacts;
	private VerticalLayout						mainLayout;

	private final int							DEFAULT_PAGE_NUMBER	= 1;
	private final int							MAX_ROWS_NUMBER		= 20;

	private Label								lbl_ReportUpdate	= new Label();
	private BbrWork<FileDownloadInfoResultDTO>	downloadsWork		= null;
	private BbrWork<BaseResultsDTO>				uploadWork			= null;
	private Button								btn_DownloadExcel	= null;
	private Upload								btn_UploadExcel		= null;
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public RetailsContactsManagement(BbrUI bbrUIParent)
	{
		super(bbrUIParent);
	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	public void initializeView()
	{
		BbrUser user = this.getUser();
		if (user != null)
		{
			// Reporte
			// Barra de Herramientas
			Label lbl_ReportTitle = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "adm_retail_contact_header"));
			lbl_ReportTitle.addStyleName("header_style");

			HorizontalLayout leftButtonsBar = new HorizontalLayout(lbl_ReportTitle);
			leftButtonsBar.setComponentAlignment(lbl_ReportTitle, Alignment.MIDDLE_LEFT);
			leftButtonsBar.setSpacing(true);
			leftButtonsBar.setMargin(false);
			leftButtonsBar.setHeight("30px");

			leftButtonsBar.addStyleName("toolbar-layout");

			Button btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
			btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
			btn_Refresh.addClickListener((ClickListener & Serializable) e -> refreshReport_clickHandler(e));
			btn_Refresh.addStyleName("toolbar-button");

			this.btn_DownloadExcel = new Button("", EnumToolbarButton.DOWNLOAD_PRIMARY.getResource());
			this.btn_DownloadExcel.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "download_report"));
			this.btn_DownloadExcel.addClickListener((ClickListener & Serializable) e -> btn_DownloadFile_downloadHandler(e));
			this.btn_DownloadExcel.addStyleName("toolbar-button");

			BbrFileUploader uploaderReceiver = new BbrFileUploader(BbrAppConstants.getUploadPathOfUser(this.getUser().getId()));

			this.btn_UploadExcel = new Upload(null, uploaderReceiver);
			this.btn_UploadExcel.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "upload_excel_file"));
			this.btn_UploadExcel.addStyleName("bbr-upload-excel-button");
			this.btn_UploadExcel.addFinishedListener((FinishedListener & Serializable) e -> uploadExcelFinished_handler(e));

			HorizontalLayout rightButtonsBar = new HorizontalLayout();

			rightButtonsBar.addComponents(this.btn_UploadExcel, this.btn_DownloadExcel, new BbrHSeparator("20px"), btn_Refresh);
			rightButtonsBar.setSpacing(true);
			rightButtonsBar.setMargin(false);
			rightButtonsBar.setHeight("30px");
			rightButtonsBar.addStyleName("toolbar-layout");

			rightButtonsBar.setComponentAlignment(btn_Refresh, Alignment.MIDDLE_RIGHT);

			HorizontalLayout toolBar = new HorizontalLayout();
			toolBar.setWidth("100%");
			toolBar.addComponents(leftButtonsBar, rightButtonsBar);
			toolBar.addStyleName("filter-toolbar");
			toolBar.setExpandRatio(leftButtonsBar, 1F);
			toolBar.setExpandRatio(rightButtonsBar, 1F);

			toolBar.setComponentAlignment(leftButtonsBar, Alignment.MIDDLE_LEFT);
			toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);

			// Grilla
			this.dgd_Contacts = new BbrAdvancedGrid<>(this.getUser().getLocale());
			this.dgd_Contacts.setSortable(false);
			this.initializeGridColumns();
			this.dgd_Contacts.addStyleName("report-grid");
			this.dgd_Contacts.setSizeFull();

			this.mainLayout = new VerticalLayout();
			this.mainLayout.addStyleName("report-layout-no-filter");
			this.mainLayout.setSizeFull();
			this.mainLayout.setMargin(false);
			this.mainLayout.addComponents(toolBar, this.dgd_Contacts, this.lbl_ReportUpdate);
			this.mainLayout.setExpandRatio(this.dgd_Contacts, 1F);
			this.mainLayout.setComponentAlignment(this.lbl_ReportUpdate, Alignment.BOTTOM_RIGHT);
			this.updateReport(DEFAULT_PAGE_NUMBER, MAX_ROWS_NUMBER, false);

			this.addComponents(this.mainLayout);
		}
		else
		{
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P0016"));
		}

	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		if (result != null)
		{

			if (triggerObject == this.btn_DownloadTriggerButton)
			{
				this.doDownloadFile(result, sender, triggerObject);
			}
			else if (triggerObject.equals(btn_UploadExcel))
			{
				this.reportUploaded(result, sender);
			}
		}
		else
		{
			RetailsContactsManagement senderReport = (RetailsContactsManagement) sender;

			if (!senderReport.getBbrUIParent().hasAlertWindowOpen())
				senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));

			senderReport.stopWaiting();
		}
	}

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	private void reportUploaded(Object result, BbrWorkExecutor sender)
	{
		String errordescription = "";

		RetailsContactsManagement senderReport = (RetailsContactsManagement) sender;

		if (result != null)
		{
			BaseResultsDTO fileResult = (BaseResultsDTO) result;
			if (fileResult.getStatuscode().equals("0"))
			{
				this.updateReport(DEFAULT_PAGE_NUMBER, MAX_ROWS_NUMBER, false);
			}
			else
			{
				if (fileResult.getBaseresults() != null && fileResult.getBaseresults().length > 0)
				{
					String errormessage = "";
					for (BaseResultDTO baseResult : fileResult.getBaseresults())
					{
						BbrError error = ErrorsMngr.getInstance().getError(baseResult, null, false);

						errormessage += error.getErrorMessage() + "<br />";
					}

					this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), errormessage);

				}
				else
				{
					BbrError error = ErrorsMngr.getInstance().getError(fileResult, null, false);

					this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), error.getErrorMessage());
				}
			}
		}
		if (errordescription.length() > 0)
		{
			// Track Error
			senderReport.trackError(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription(), errordescription, null, this);
		}
		senderReport.stopWaiting();
		this.btn_UploadExcel.setEnabled(true);
	}

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************
	private void refreshReport_clickHandler(ClickEvent event)
	{
		this.updateReport(DEFAULT_PAGE_NUMBER, MAX_ROWS_NUMBER, false);
	}

	private void btn_DownloadFile_downloadHandler(ClickEvent e)
	{
		this.btn_DownloadTriggerButton = e.getButton();
		this.downloadDataSource();
	}

	private void downloadDataSource()
	{
		downloadsWork = new BbrWork<>(this, this.getBbrUIParent(), btn_DownloadTriggerButton);
		downloadsWork.addFunction(new Function<Object, FileDownloadInfoResultDTO>()
		{
			@Override
			public FileDownloadInfoResultDTO apply(Object t)
			{
				return executeDownloadService(RetailsContactsManagement.this.getBbrUIParent(), DownloadFormats.XLSX, btn_DownloadTriggerButton, null);
			}
		});

		this.startWaiting();
		this.executeBbrWork(downloadsWork);
	}

	private FileDownloadInfoResultDTO executeDownloadService(BbrUI bbrUIParent, DownloadFormats selectedFormat, Button downloadTriggerButton, Object extraData)
	{
		FileDownloadInfoResultDTO fileResult = null;
		if (selectedFormat != null)
		{
			try
			{
				if (this.btn_DownloadTriggerButton == this.btn_DownloadExcel)
				{
					fileResult = EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(bbrUIParent).downloadRetailerContacts(bbrUIParent.getUser().getId(), DownloadFormats.XLSX.getValue(), bbrUIParent.getUser().getLocale());
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
		RetailsContactsManagement senderReport = (RetailsContactsManagement) sender;
		if (senderReport != null)
		{
			senderReport.downloadLinkFile(result);
		}
	}

	private void uploadExcelFinished_handler(FinishedEvent e)
	{
		uploadWork = new BbrWork<>(this, this.getBbrUIParent(), btn_UploadExcel);
		uploadWork.addFunction(new Function<Object, BaseResultsDTO>()
		{
			@Override
			public BaseResultsDTO apply(Object t)
			{
				FileUploadInitParamDTO initParams = new FileUploadInitParamDTO();
				initParams.setFileName(e.getFilename());
				initParams.setFilePath(BbrAppConstants.getUploadPathOfUser(RetailsContactsManagement.this.getBbrUIParent().getUser().getId()));

				return executeUploadService(RetailsContactsManagement.this.getBbrUIParent(), initParams);
			}
		});

		this.startWaiting();
		this.executeBbrWork(uploadWork);
	}

	private BaseResultsDTO executeUploadService(BbrUI bbrUIParent, FileUploadInitParamDTO initParams)
	{
		BaseResultsDTO result = null;

		if (initParams != null)
		{
			try
			{
				// Start Tracking
				this.getTimingMngr().startTimer();
				result = EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(bbrUIParent).doUploadRetailerContacts(initParams, bbrUIParent.getUser().getId());
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

	// private void exuploadExcelFinished_handler(FinishedEvent e)
	// {
	// try
	// {
	// FileUploadInitParamDTO initParams = new FileUploadInitParamDTO();
	// initParams.setFileName(e.getFilename());
	// initParams.setFilePath(BbrAppConstants.getUploadPathOfUser(this.getUser().getId()));
	//
	// BaseResultsDTO fileResult =
	// EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal().doUploadRetailerContacts(initParams,
	// this.getUser().getId());
	//
	// if(fileResult != null)
	// {
	// if(fileResult.getStatuscode().equals("0"))
	// {
	// this.updateReport(DEFAULT_PAGE_NUMBER, MAX_ROWS_NUMBER, false);
	// }
	// else
	// {
	// if(fileResult.getBaseresults() != null &&
	// fileResult.getBaseresults().length > 0)
	// {
	// String errormessage = "";
	// for (BaseResultDTO baseResult : fileResult.getBaseresults())
	// {
	// BbrError error = ErrorsMngr.getInstance().getError(baseResult, null,
	// false);
	//
	// errormessage += error.getErrorMessage() + "<br />";
	// }
	//
	// this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC,
	// "windows_title_error"), errormessage);
	//
	// }
	// else
	// {
	// BbrError error = ErrorsMngr.getInstance().getError(fileResult, null,
	// false);
	//
	// this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC,
	// "windows_title_error"), error.getErrorMessage());
	// }
	// }
	// }
	// }
	// catch (BbrUserException ex)
	// {
	// AppUtils.getInstance().doLogOut(ex.getMessage());
	// }
	// catch (BbrSystemException ex)
	// {
	// ex.printStackTrace();
	// }
	//
	// }

	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private void initializeGridColumns()
	{
		this.dgd_Contacts.addColumn(ContactB2BDTO::getDepartment).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "area"));
		this.dgd_Contacts.addColumn(ContactB2BDTO::getName).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "names"));
		this.dgd_Contacts.addColumn(ContactB2BDTO::getLastname).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_names"));
		this.dgd_Contacts.addColumn(ContactB2BDTO::getPosition).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "position"));
		this.dgd_Contacts.addColumn(ContactB2BDTO::getPhone).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "phone"));
		this.dgd_Contacts.addColumn(ContactB2BDTO::getEmail).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "email"));
	}

	private void updateReport(int pageNumber, int rows, boolean isPaginated)
	{
		String errordescription = "";

		try
		{
			// Start Tracking
			this.getTimingMngr().startTimer();

			ContactB2BArrayResultDTO contactReportResult = EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(this.getBbrUIParent()).getRetailerContacts(pageNumber, rows, isPaginated);
			if (contactReportResult != null)
			{
				BbrError error = ErrorsMngr.getInstance().getError(contactReportResult, this.getBbrUIParent(), true);
				if (!error.hasError())
				{
					ListDataProvider<ContactB2BDTO> dataprovider = new ListDataProvider<>(Arrays.asList(contactReportResult.getContactB2BDTOs()));

					dgd_Contacts.setDataProvider(dataprovider);

					this.updateReportLogData(contactReportResult.getContactLogDataDTO());

					this.trackEvent(TrackingConstants.REPORT_VIEW, this.getBbrFilterDescription());
				}
				else
				{
					errordescription = BbrUtils.getInstance().substitute("{0} - {1} - Internal Error", error.getErrorCode(), error.getErrorMessage());
				}
			}

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
			errordescription = BbrUtils.getInstance().substitute("{0}", e.getMessage());
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P200"));
		}

		if (errordescription.length() > 0)
		{
			// Track Error
			this.trackError(TrackingConstants.REPORT_VIEW, this.getBbrFilterDescription(), errordescription, null, this);
		}
	}

	private void updateReportLogData(ContactLogDataDTO data)
	{
		this.lbl_ReportUpdate.setValue("");
		if (data != null && data.getFecha() != null)
		{
			String strDate = data.getFecha().format(BbrDateUtils.getInstance().getFormatterShortDateTime());

			this.lbl_ReportUpdate.setValue(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "adm_contact_retail_update_info", data.getName(), data.getLastname(), strDate));
		}

	}

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
