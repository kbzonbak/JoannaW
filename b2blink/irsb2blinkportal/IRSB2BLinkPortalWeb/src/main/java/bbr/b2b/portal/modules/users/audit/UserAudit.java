package bbr.b2b.portal.modules.users.audit;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.function.Function;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.BaseResultsDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrDateRange;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.UserAuditInitParamDTO;
import bbr.b2b.users.report.classes.UserAuditMinDateResultDTO;
import cl.bbr.core.classes.constants.DownloadFormats;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrErrorList;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;

public class UserAudit extends BbrModule implements BbrWorkExecutor
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants
	private static final long					serialVersionUID	= 4982315246309578782L;
	// Components
	private VerticalLayout						mainLayout			= null;
	private Button								btn_Upload			= null;
	private Button								btn_DownloadReport	= null;
	private BbrDateRange						pnlDates			= null;
	// Variables
	private BbrWork<UserAuditMinDateResultDTO>	minDateReportWork	= null;
	// private Boolean trackReport = true;
	private BbrWork<FileDownloadInfoResultDTO>	downloadsWork		= null;
	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public UserAudit(BbrUI bbrUIParent)
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
		// Encabezado Titulo

		Label lbl_HeadTitle = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_audit"));
		lbl_HeadTitle.addStyleName("header_style");

		Button btn_Help = this.getHelpButton();

		HorizontalLayout titleBar = new HorizontalLayout();
		titleBar.setWidth("100%");
		titleBar.addComponents(lbl_HeadTitle, btn_Help);
		titleBar.setComponentAlignment(lbl_HeadTitle, Alignment.MIDDLE_LEFT);
		titleBar.setComponentAlignment(btn_Help, Alignment.MIDDLE_RIGHT);
		titleBar.addStyleName("filter-toolbar");
		titleBar.addStyleName("toolbar-layout");
		titleBar.setExpandRatio(lbl_HeadTitle, 1F);

		// DOWNLOAD
		this.btn_DownloadReport = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "download"));
		this.btn_DownloadReport.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "download"));
		this.btn_DownloadReport.addStyleName("btn-primary");
		this.btn_DownloadReport.setWidth("140px");
		this.btn_DownloadReport.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadFile_downloadHandler(e));

		// DOWNLOAD END
		HorizontalLayout adminsButtons = new HorizontalLayout();
		adminsButtons.addStyleName("toolbar-layout");
		adminsButtons.setHeight("30px");

		Label innerTitle = new Label(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_audit"));
		innerTitle.addStyleName("header_style");
		// Rango de fechas

		this.pnlDates = new BbrDateRange.BbrDateRangeBuilder(this.getBbrUIParent())
				.withDateFormat("dd-MM-yyyy")
				.withDtfWidth("150px")
				.withStartDate(this.getStartDate(null))
				.withEndDate(this.getEndDate())
				.build();

		VerticalLayout pnlDateRangeAndDownload = new VerticalLayout();
		pnlDateRangeAndDownload.addComponents(this.pnlDates, this.btn_DownloadReport);
		pnlDateRangeAndDownload.setComponentAlignment(this.pnlDates, Alignment.MIDDLE_CENTER);
		pnlDateRangeAndDownload.setComponentAlignment(this.btn_DownloadReport, Alignment.MIDDLE_CENTER);
		pnlDateRangeAndDownload.setHeight("170px");
		pnlDateRangeAndDownload.setWidth("100%");
		pnlDateRangeAndDownload.setMargin(false);
		pnlDateRangeAndDownload.setSpacing(false);

		Panel panelDates = new Panel();
		panelDates.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_audit"));
		panelDates.setContent(pnlDateRangeAndDownload);

		HorizontalLayout cmp_DownloadAuditContent = new HorizontalLayout();
		cmp_DownloadAuditContent.setSizeFull();
		cmp_DownloadAuditContent.setMargin(new MarginInfo(true, false));
		cmp_DownloadAuditContent.setSpacing(false);
		cmp_DownloadAuditContent.setWidth("700px");
		cmp_DownloadAuditContent.addComponents(panelDates);
		cmp_DownloadAuditContent.setComponentAlignment(panelDates, Alignment.TOP_CENTER);

		// Main Layout
		this.mainLayout = new VerticalLayout();
		this.mainLayout.addStyleName("report-layout-no-filter");
		this.mainLayout.setSizeFull();
		this.mainLayout.setMargin(false);
		this.mainLayout.addComponents(titleBar, cmp_DownloadAuditContent);
		this.mainLayout.setComponentAlignment(cmp_DownloadAuditContent, Alignment.MIDDLE_CENTER);
		this.mainLayout.setExpandRatio(cmp_DownloadAuditContent, 1F);

		this.addComponent(this.mainLayout);

		this.minDateReportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		this.minDateReportWork.addFunction(new Function<Object, UserAuditMinDateResultDTO>()
		{
			@Override
			public UserAuditMinDateResultDTO apply(Object t)
			{
				return executeService(UserAudit.this.getBbrUIParent());
			}
		});
		this.startWaiting();
		this.executeBbrWork(this.minDateReportWork);
	}

	private LocalDateTime getStartDate(LocalDateTime startDate)
	{
		// default first day of month
		LocalDateTime firstDayOfMonth = LocalDateTime.now().withDayOfMonth(1);
		if (startDate == null || startDate.isBefore(firstDayOfMonth))
		{
			return firstDayOfMonth;
		}
		else
		{
			return startDate;
		}

	}

	private LocalDateTime getEndDate()
	{
		// until actual
		LocalDateTime today = LocalDateTime.now();
		return today;
	}

	@Override
	protected void downloadFormat_selectedHandler(BbrEvent event)
	{
		UserAuditInitParamDTO initParam = this.getDownloadInitParam();
		String message = validateInitParamData(initParam);
		if (message != null)
		{
			this.showErrorMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "win_title_error"), message);
		}
		else
		{
			initializeDownloadWork(initParam);
		}
	}

	private String validateInitParamData(UserAuditInitParamDTO initParam)
	{
		if (initParam.getMindate().isAfter(initParam.getMaxdate()))
		{
			return I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_GENERIC, "valid_range_date");
		}
		return null;
	}

	private void initializeDownloadWork(UserAuditInitParamDTO initParam)
	{
		downloadsWork = new BbrWork<>(this, this.getBbrUIParent(), btn_DownloadTriggerButton);
		downloadsWork.addFunction(new Function<Object, FileDownloadInfoResultDTO>()
		{
			@Override
			public FileDownloadInfoResultDTO apply(Object t)
			{
				return executeDownloadService(UserAudit.this.getBbrUIParent(), initParam, DownloadFormats.XLSX, btn_DownloadTriggerButton);
			}
		});

		this.startWaiting();
		this.executeBbrWork(downloadsWork);
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				this.doGetMinAuditDateReport(result, sender);
			}
			else if (triggerObject == this.btn_DownloadTriggerButton)
			{
				this.doDownloadFile(result, sender, triggerObject);
			}
			else if (triggerObject.equals(this.btn_Upload))
			{
				this.reportUploaded(result, sender);
			}
		}

		else
		{
			UserAudit senderReport = (UserAudit) sender;

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

		if (this.btn_DownloadTriggerButton == this.btn_DownloadReport)
		{
			this.downloadFormat_selectedHandler(null);
		}
	}

	private void refreshReport_clickHandler()
	{
		this.startWaiting();

		// this.trackReport = false;
		this.executeBbrWork(minDateReportWork);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
	private UserAuditMinDateResultDTO executeService(BbrUI bbrUIParent)
	{
		UserAuditMinDateResultDTO result = null;

		try
		{
			result = EJBFactory.getUserEJBFactory().getUserReportManagerLocal(bbrUIParent).getMinAuditDate();
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

	private void doGetMinAuditDateReport(Object result, BbrWorkExecutor sender)
	{
		UserAudit senderReport = (UserAudit) sender;

		if (result != null)
		{
			UserAuditMinDateResultDTO reportResult = (UserAuditMinDateResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				senderReport.pnlDates.updateStartDate(senderReport.getStartDate(reportResult.getMinauditdate()));
				senderReport.pnlDates.updateEndDate(senderReport.getEndDate());
				senderReport.pnlDates.updateRangeStartEndDate(reportResult.getMinauditdate(), senderReport.getEndDate());
			}
		}

		senderReport.stopWaiting();
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
	private UserAuditInitParamDTO getDownloadInitParam()
	{
		UserAuditInitParamDTO initParam = new UserAuditInitParamDTO();
		initParam.setMindate(this.pnlDates.getSinceDate());
		initParam.setMaxdate(this.pnlDates.getUntilDate());
		initParam.setLocale(this.getBbrUIParent().getLocale());
		return initParam;
	}

	private FileDownloadInfoResultDTO executeDownloadService(BbrUI bbrUIParent, UserAuditInitParamDTO initParam, DownloadFormats selectedFormat, Button downloadTriggerButton)
	{
		FileDownloadInfoResultDTO fileResult = null;
		if (selectedFormat != null)
		{
			try
			{
				if (this.btn_DownloadTriggerButton == this.btn_DownloadReport)
				{
					fileResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal(bbrUIParent).downloadUserAuditReport(initParam, bbrUIParent.getUser().getId());
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

	private void reportUploaded(Object result, BbrWorkExecutor sender)
	{
		UserAudit senderReport = (UserAudit) sender;

		if (result != null)
		{
			BaseResultsDTO reportResult = (BaseResultsDTO) result;

			// MULTIPLES ALERTAS DE MENSAJES
			boolean canOpenAlert = !senderReport.getBbrUIParent().hasAlertWindowOpen();

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), false);

			if (!error.hasError())
			{
				senderReport.showInfoMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "upload_completeness_successful"),
						null, () -> senderReport.refreshReport_clickHandler());
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
		senderReport.executeBbrWork(minDateReportWork);
	}

	private void doDownloadFile(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		UserAudit senderReport = (UserAudit) sender;
		if (senderReport != null)
		{
			senderReport.downloadLinkFile(result);
		}
	}

}
