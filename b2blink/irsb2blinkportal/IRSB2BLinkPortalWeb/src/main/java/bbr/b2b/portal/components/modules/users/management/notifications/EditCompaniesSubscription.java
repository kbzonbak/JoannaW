package bbr.b2b.portal.components.modules.users.management.notifications;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.BaseResultsDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.portal.classes.constants.BbrAppConstants;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.AssignedCompaniesArrayResultDTO;
import bbr.b2b.users.report.classes.AssignedRelationshipsInitParamDTO;
import bbr.b2b.users.report.classes.CompanyDTO;
import bbr.b2b.users.report.classes.RuleTypeDTO;
import bbr.b2b.users.report.classes.UploadUserPublishingFileInitParamDTO;
import bbr.b2b.users.report.classes.UserPublishingRelationshipsInitParamsDTO;
import cl.bbr.core.classes.basics.BbrPage;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.files_transfer.AdvancedFileDownloader;
import cl.bbr.core.classes.files_transfer.AdvancedFileDownloader.AdvancedDownloaderListener;
import cl.bbr.core.classes.files_transfer.AdvancedFileDownloader.DownloaderEvent;
import cl.bbr.core.classes.files_transfer.BbrFileUploader;
import cl.bbr.core.components.basics.BbrHSeparator;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.paging.BbrPagingManager;

public class EditCompaniesSubscription extends BbrWindow
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long			serialVersionUID	= 3247626779164695209L;

	private Long						publishingId;
	private RuleTypeDTO					rule;

	private BbrAdvancedGrid<CompanyDTO>	dgd_Companies;
	private BbrPagingManager			companyPagingManager;

	private ArrayList<CompanyDTO>		companies			= null;

	private final int					DEFAULT_PAGE_NUMBER	= 1;
	private final int					MAX_ROWS_NUMBER		= 20;

	private final String				COMPANY_CODE_FIELD	= "rut";

	private AdvancedFileDownloader		downloader			= null;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public EditCompaniesSubscription(BbrUI parent, Long publishingId, RuleTypeDTO rule)
	{
		super(parent);
		this.publishingId = publishingId;
		this.rule = rule;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	public void initializeView()
	{
		if (this.publishingId != null)
		{
			// Barra de Herramientas
			HorizontalLayout leftButtonsBar = new HorizontalLayout();
			HorizontalLayout rightButtonsBar = new HorizontalLayout();

			Button btn_DownloadCompanyList = new Button("", EnumToolbarButton.DOWNLOAD_PRIMARY.getResource());
			btn_DownloadCompanyList.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "suscription_download_company_list"));
			btn_DownloadCompanyList.addStyleName("toolbar-button");

			downloader = new AdvancedFileDownloader();
			downloader.extend(btn_DownloadCompanyList);
			downloader.addAdvancedDownloaderListener((AdvancedDownloaderListener & Serializable) e -> beforeDownload_handler(e));

			BbrFileUploader uploaderReceiver = new BbrFileUploader(BbrAppConstants.getUploadPathOfUser(this.getUser().getId()));

			Upload btnUploadExcelFile = new Upload(null, uploaderReceiver);
			btnUploadExcelFile.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "suscription_upload_company_list"));
			btnUploadExcelFile.addStyleName("bbr-upload-excel-button");
			btnUploadExcelFile.addFinishedListener((FinishedListener & Serializable) e -> uploadExcelFinished_handler(e));

			Button btn_DeleteAssignedCompany = new Button("", EnumToolbarButton.DELETE.getResource());
			btn_DeleteAssignedCompany.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "suscription_delete_company"));
			btn_DeleteAssignedCompany.addClickListener((ClickListener & Serializable) e -> deleteRelation_clickHandler(e));
			btn_DeleteAssignedCompany.addStyleName("toolbar-button");

			leftButtonsBar.setSpacing(true);
			leftButtonsBar.setMargin(false);
			leftButtonsBar.setHeight("30px");
			leftButtonsBar.addStyleName("toolbar-layout");

			leftButtonsBar.addComponents(btnUploadExcelFile, btn_DownloadCompanyList, new BbrHSeparator("20px"), btn_DeleteAssignedCompany);

			Button btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
			btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
			btn_Refresh.addClickListener((ClickListener & Serializable) e -> refreshReport_clickHandler(e));
			btn_Refresh.addStyleName("toolbar-button");

			rightButtonsBar.addComponents(btn_Refresh);
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

			// Paging Manager
			companyPagingManager = new BbrPagingManager();
			companyPagingManager.setLocale(this.getUser().getLocale());
			companyPagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> companyPagingChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);

			// Grilla
			dgd_Companies = new BbrAdvancedGrid<>(this.getUser().getLocale());
			this.initializeGridColumns();
			dgd_Companies.addStyleName("report-grid");
			dgd_Companies.addStyleName("bbr-multi-grid");
			dgd_Companies.setSizeFull();
			dgd_Companies.setHeight("100%");
			dgd_Companies.setSelectionMode(SelectionMode.MULTI);
			dgd_Companies.setPagingManager(companyPagingManager, this.COMPANY_CODE_FIELD);

			this.updateAllAssignedCompanies(DEFAULT_PAGE_NUMBER, MAX_ROWS_NUMBER, true);

			VerticalLayout mainLayout = new VerticalLayout();
			mainLayout.setSizeFull();
			mainLayout.addComponents(toolBar, dgd_Companies, companyPagingManager);
			mainLayout.setExpandRatio(dgd_Companies, 1F);
			mainLayout.addStyleName("bbr-margin-windows");
			mainLayout.setMargin(false);
			// Main Windows
			this.setWidth(1000, Unit.PIXELS);
			this.setHeight(600, Unit.PIXELS);
			this.setResizable(false);
			this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "create_notification_win_title") + " : " + rule.getDescription());
			this.setContent(mainLayout);
		}

	}
	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************
	private void companyPagingChange_Listener(BbrPagingEvent e)
	{
		if (e != null && e.getEventType().equals(BbrPagingEvent.PAGE_CHANGED) && e.getResultObject() != null)
		{
			this.updateAllAssignedCompanies(companyPagingManager.getCurrentPage(), MAX_ROWS_NUMBER, false);
		}
	}

	private void refreshReport_clickHandler(ClickEvent e)
	{
		this.updateAllAssignedCompanies(companyPagingManager.getCurrentPage(), MAX_ROWS_NUMBER, true);
	}

	private void beforeDownload_handler(DownloaderEvent e)
	{
		try
		{
			FileDownloadInfoResultDTO fileResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent()).getExcelCompanyPublishingRelationshipReport(publishingId);

			if (fileResult != null && fileResult.getDownloadfilename() != null)
			{
				downloader.setFilePath(fileResult.getRealfilename(), fileResult.getDownloadfilename());
			}
			else
			{
				this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "error_download"));
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

	}

	private void uploadExcelFinished_handler(FinishedEvent e)
	{
		try
		{
			UploadUserPublishingFileInitParamDTO initParams = new UploadUserPublishingFileInitParamDTO();
			initParams.setPublishingId(publishingId);
			initParams.setRuleTypeId(rule.getId());
			initParams.setFileName(e.getFilename());
			initParams.setFilePath(BbrAppConstants.getUploadPathOfUser(this.getUser().getId()));

			BaseResultsDTO fileResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent()).doUpdateCompanyPublishingRelationships(initParams);

			if (fileResult != null)
			{
				if (fileResult.getStatuscode().equals("0"))
				{
					this.updateAllAssignedCompanies(DEFAULT_PAGE_NUMBER, MAX_ROWS_NUMBER, true);
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
		}
		catch (BbrUserException ex)
		{
			AppUtils.getInstance().doLogOut(ex.getMessage(), this.getBbrUIParent());
		}
		catch (BbrSystemException ex)
		{
			ex.printStackTrace();
		}

	}

	private void deleteRelation_clickHandler(ClickEvent e)
	{
		ArrayList<CompanyDTO> items = dgd_Companies.getAllSelectedsItems();
		if (items != null && items.size() > 0)
		{
			try
			{
				ArrayList<Long> ids = new ArrayList<>();
				items.forEach((Consumer<CompanyDTO> & Serializable) company -> ids.add(company.getId()));
				Long[] companiesIds = ids.toArray(new Long[ids.size()]);

				UserPublishingRelationshipsInitParamsDTO initParams = new UserPublishingRelationshipsInitParamsDTO();
				initParams.setPublishingId(publishingId);
				initParams.setRuleTypeId(rule.getId());
				initParams.setKeys(companiesIds);

				BaseResultDTO deleteResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent()).doDeleteUserPublicationRelationshipsByRule(initParams);

				if (deleteResult != null)
				{
					if (deleteResult.getStatuscode().equals("0"))
					{
						this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "successful_operation"));

						this.updateAllAssignedCompanies(DEFAULT_PAGE_NUMBER, MAX_ROWS_NUMBER, true);
					}
					else
					{
						BbrError error = ErrorsMngr.getInstance().getError(deleteResult, null, false);
						this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), error.getErrorMessage());
					}
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
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "select_at_least_one"));
		}
	}

	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private void initializeGridColumns()
	{
		dgd_Companies.addColumn(CompanyDTO::getRut).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "rut"));
		dgd_Companies.addColumn(CompanyDTO::getName).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "social_reason"));
	}

	private void updateAllAssignedCompanies(int pagenumber, int rows, Boolean resetPageInfo)
	{
		try
		{
			AssignedRelationshipsInitParamDTO initParams = new AssignedRelationshipsInitParamDTO();
			initParams.setPageNumber(pagenumber);
			initParams.setRows(rows);
			initParams.setPublishingId(publishingId);

			AssignedCompaniesArrayResultDTO companiesResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().getAssignedCompaniesToPublishing(initParams, true);

			if (companiesResult != null && companiesResult.getAssignedCompanies() != null)
			{
				companies = new ArrayList<>(Arrays.asList(companiesResult.getAssignedCompanies()));

				ListDataProvider<CompanyDTO> dataprovider = new ListDataProvider<>(companies);
				dgd_Companies.setDataProvider(dataprovider, resetPageInfo);

				if (resetPageInfo)
				{
					PageInfoDTO pageInfo = companiesResult.getPageInfo();

					BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
					companyPagingManager.setPages(bbrPage, resetPageInfo);

				}
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

	}
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
