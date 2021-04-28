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
import bbr.b2b.users.report.classes.AssignedRelationshipsInitParamDTO;
import bbr.b2b.users.report.classes.AssignedUsersArrayResultDTO;
import bbr.b2b.users.report.classes.RuleTypeDTO;
import bbr.b2b.users.report.classes.UploadUserPublishingFileInitParamDTO;
import bbr.b2b.users.report.classes.UserDTO;
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

public class EditUsersSubscription extends BbrWindow
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long			serialVersionUID	= 3247626779164695209L;

	private Long						publishingId;
	private RuleTypeDTO					rule;

	private BbrAdvancedGrid<UserDTO>	dgd_Users;
	private BbrPagingManager			userPagingManager;

	private final int					DEFAULT_PAGE_NUMBER	= 1;
	private final int					MAX_ROWS_NUMBER		= 20;

	private final String				USER_RUT_FIELD		= "logid";

	private Upload						btnUploadExcelFile	= null;
	private AdvancedFileDownloader		downloader			= null;
	private BbrFileUploader				uploaderReceiver	= null;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public EditUsersSubscription(BbrUI parent, Long publishingId, RuleTypeDTO rule)
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

			Button btn_DownloadUserList = new Button("", EnumToolbarButton.DOWNLOAD_PRIMARY.getResource());
			btn_DownloadUserList.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "suscription_download_user_list"));
			btn_DownloadUserList.addStyleName("toolbar-button");

			downloader = new AdvancedFileDownloader();
			downloader.extend(btn_DownloadUserList);
			downloader.addAdvancedDownloaderListener((AdvancedDownloaderListener & Serializable) e -> beforeDownload_handler(e));

			uploaderReceiver = new BbrFileUploader(BbrAppConstants.getUploadPathOfUser(this.getUser().getId()));

			btnUploadExcelFile = new Upload(null, uploaderReceiver);
			btnUploadExcelFile.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "suscription_upload_user_list"));
			btnUploadExcelFile.addStyleName("bbr-upload-excel-button");
			btnUploadExcelFile.addFinishedListener((FinishedListener & Serializable) e -> uploadExcelFinished_handler(e));

			Button btn_DeleteAssignedUser = new Button("", EnumToolbarButton.DELETE.getResource());
			btn_DeleteAssignedUser.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "suscription_delete_user"));
			btn_DeleteAssignedUser.addClickListener((ClickListener & Serializable) e -> deleteRelation_clickHandler(e));
			btn_DeleteAssignedUser.addStyleName("toolbar-button");

			leftButtonsBar.setSpacing(true);
			leftButtonsBar.setMargin(false);
			leftButtonsBar.setHeight("30px");
			leftButtonsBar.addStyleName("toolbar-layout");

			leftButtonsBar.addComponents(btnUploadExcelFile, btn_DownloadUserList, new BbrHSeparator("20px"), btn_DeleteAssignedUser);

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
			userPagingManager = new BbrPagingManager();
			userPagingManager.setLocale(this.getUser().getLocale());
			userPagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> userPagingChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);

			// Grilla
			dgd_Users = new BbrAdvancedGrid<>(this.getUser().getLocale());
			this.initializeGridColumns();
			dgd_Users.addStyleName("report-grid");
			dgd_Users.addStyleName("bbr-multi-grid");
			dgd_Users.setSizeFull();
			dgd_Users.setHeight("100%");
			dgd_Users.setSelectionMode(SelectionMode.MULTI);
			dgd_Users.setPagingManager(userPagingManager, this.USER_RUT_FIELD);

			this.updateAllAssignedUsers(DEFAULT_PAGE_NUMBER, MAX_ROWS_NUMBER, true);
			VerticalLayout mainLayout = new VerticalLayout();
			mainLayout.setSizeFull();
			mainLayout.addComponents(toolBar, dgd_Users, userPagingManager);
			mainLayout.setExpandRatio(dgd_Users, 1F);
			mainLayout.addStyleName("bbr-margin-windows");
			mainLayout.setMargin(false);
			// Main Windows
			this.setWidth(1000, Unit.PIXELS);
			this.setHeight(600, Unit.PIXELS);
			this.setResizable(false);
			this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "create_notification_win_title") + " : " + rule.getDescription());
			this.setContent(mainLayout);
			this.markAsDirtyRecursive();
		}

	}
	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************
	private void userPagingChange_Listener(BbrPagingEvent e)
	{
		if (e != null && e.getEventType().equals(BbrPagingEvent.PAGE_CHANGED) && e.getResultObject() != null)
		{
			this.updateAllAssignedUsers(userPagingManager.getCurrentPage(), MAX_ROWS_NUMBER, false);
		}
	}

	private void beforeDownload_handler(DownloaderEvent e)
	{
		try
		{
			FileDownloadInfoResultDTO fileResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent()).getExcelUserPublishingRelationshipReport(publishingId);

			if (fileResult != null && fileResult.getDownloadfilename() != null)
			{
				downloader.setFilePath(fileResult.getRealfilename(), fileResult.getDownloadfilename());

				btnUploadExcelFile.markAsDirty();
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

			BaseResultsDTO fileResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent()).doUpdateUserPublishingRelationships(initParams);

			if (fileResult != null)
			{
				if (fileResult.getStatuscode().equals("0"))
				{
					this.updateAllAssignedUsers(DEFAULT_PAGE_NUMBER, MAX_ROWS_NUMBER, true);
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
		ArrayList<UserDTO> items = dgd_Users.getAllSelectedsItems();
		if (items != null && items.size() > 0)
		{
			try
			{
				ArrayList<Long> ids = new ArrayList<>();
				items.forEach((Consumer<UserDTO> & Serializable) user -> ids.add(user.getId()));
				Long[] usersIds = ids.toArray(new Long[ids.size()]);

				UserPublishingRelationshipsInitParamsDTO initParams = new UserPublishingRelationshipsInitParamsDTO();
				initParams.setPublishingId(publishingId);
				initParams.setRuleTypeId(rule.getId());
				initParams.setKeys(usersIds);

				BaseResultDTO deleteResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent()).doDeleteUserPublicationRelationshipsByRule(initParams);

				if (deleteResult != null)
				{
					if (deleteResult.getStatuscode().equals("0"))
					{
						this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "successful_operation"));

						this.updateAllAssignedUsers(DEFAULT_PAGE_NUMBER, MAX_ROWS_NUMBER, true);
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

	private void refreshReport_clickHandler(ClickEvent e)
	{
		this.updateAllAssignedUsers(userPagingManager.getCurrentPage(), MAX_ROWS_NUMBER, true);
	}

	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private void initializeGridColumns()
	{
		this.dgd_Users.addColumn(UserDTO::getEmail).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_email"));
		this.dgd_Users.addColumn(UserDTO::getName).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_name"));
		this.dgd_Users.addColumn(UserDTO::getLastname).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_lastname"));
		this.dgd_Users.addColumn(UserDTO::getPosition).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_position"));
	}

	private void updateAllAssignedUsers(int pagenumber, int rows, Boolean resetPageInfo)
	{
		try
		{
			AssignedRelationshipsInitParamDTO initParams = new AssignedRelationshipsInitParamDTO();
			initParams.setPageNumber(pagenumber);
			initParams.setRows(rows);
			initParams.setPublishingId(publishingId);

			AssignedUsersArrayResultDTO usersResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().getAssignedUsersToPublishing(initParams, true);

			if (usersResult != null && usersResult.getAssignedUsers() != null)
			{
				ListDataProvider<UserDTO> dataprovider = new ListDataProvider<>(Arrays.asList(usersResult.getAssignedUsers()));
				dgd_Users.setDataProvider(dataprovider, resetPageInfo);

				if (resetPageInfo)
				{
					PageInfoDTO pageInfo = usersResult.getPageInfo();
					BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
					this.userPagingManager.setPages(bbrPage, resetPageInfo);

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
