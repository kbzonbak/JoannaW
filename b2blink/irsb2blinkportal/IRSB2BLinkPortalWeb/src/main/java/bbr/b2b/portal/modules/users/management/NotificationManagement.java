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
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.renderers.ComponentRenderer;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.portal.classes.constants.BbrPublishingConstants;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.jms.constants.MessageTopics;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.BbrMessagesBoxUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.filters.management.NotificationManagementFilter;
import bbr.b2b.portal.components.modules.users.management.notifications.CreateEditEmailNotification;
import bbr.b2b.portal.components.modules.users.management.notifications.CreateEditNewsNotification;
import bbr.b2b.portal.components.modules.users.management.notifications.CreateEditPopupNotification;
import bbr.b2b.portal.components.modules.users.management.notifications.CreateEditProceduresNotification;
import bbr.b2b.portal.components.modules.users.management.notifications.EditCompaniesSubscription;
import bbr.b2b.portal.components.modules.users.management.notifications.EditEventsSubscription;
import bbr.b2b.portal.components.modules.users.management.notifications.EditProfilesSubscription;
import bbr.b2b.portal.components.modules.users.management.notifications.EditUsersSubscription;
import bbr.b2b.portal.components.renderers.grid_details.NotificationStateRenderer;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.PopUpReportDataDTO;
import bbr.b2b.users.report.classes.PopUpReportInitParamsDTO;
import bbr.b2b.users.report.classes.PopUpReportResultDTO;
import bbr.b2b.users.report.classes.PublishingReportDataDTO;
import bbr.b2b.users.report.classes.PublishingReportInitParamsDTO;
import bbr.b2b.users.report.classes.PublishingReportResultDTO;
import bbr.b2b.users.report.classes.PublishingTypeDTO;
import bbr.b2b.users.report.classes.RuleTypeDTO;
import bbr.b2b.users.report.classes.RuleTypeResultDTO;
import cl.bbr.core.classes.basics.BbrPage;
import cl.bbr.core.classes.basics.BbrUser;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.constants.TrackingConstants;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.classes.events.BbrMessageEvent;
import cl.bbr.core.classes.events.BbrMessageEventListener;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrMessageBox;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.grid.renderer.DateRenderer;
import cl.bbr.core.components.paging.BbrPagingManager;
import cl.bbr.core.components.widgets.bbrpopupbutton.BbrPopupButton;

public class NotificationManagement extends BbrModule implements BbrWorkExecutor
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	// Constants
	private static final long							serialVersionUID			= 7027502352762624910L;
	private final String								KEY_FIELD					= "pukey";
	private final String								TITLE_FIELD					= "title";
	// Components
	private BbrAdvancedGrid<PublishingReportDataDTO>	dgd_Publishings				= null;
	private BbrAdvancedGrid<PopUpReportDataDTO>			dgd_PublishingsPopUps		= null;
	private VerticalLayout								mainLayout					= null;
	private BbrPagingManager							pagingManager				= null;
	private Button										btn_AddNotificaction		= null;
	private Button										btn_EditNotificaction		= null;
	private Button										btn_EditSubscriptions		= null;
	private Button										btn_DeleteNotificaction		= null;
	private Button										btn_EnableNotificaction		= null;
	private Button										btn_DisableNotificaction	= null;
	private BbrPopupButton								btn_Edit					= null;
	private BbrPopupButton								btn_Actions					= null;
	// Variables
	private final int									DEFAULT_PAGE_NUMBER			= 1;
	private final int									MAX_ROWS_NUMBER				= 20;
	private PublishingReportInitParamsDTO				initParams					= null;
	private PopUpReportInitParamsDTO					initParamsPopUp				= null;
	private PublishingTypeDTO							selectedPublishingType		= null;
	private PublishingReportDataDTO						selectedPublishing			= null;
	private PublishingReportDataDTO						selectedPublishingPopup		= null;
	private BbrMessageEventListener						messagingListener			= null;
	private boolean										isPopup						= false;
	private BbrWork<PopUpReportResultDTO>				popupReportWork				= null;
	private BbrWork<PublishingReportResultDTO>			publishingReportWork		= null;
	private boolean										trackReport					= true;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public NotificationManagement(BbrUI bbrUIParent)
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
		// Filtro del reporte
		NotificationManagementFilter filterLayout = new NotificationManagementFilter(this);
		filterLayout.initializeView();
		this.setBbrFilterContainer(filterLayout);

		// Reporte
		// Barra de Herramientas
		this.btn_AddNotificaction = new Button("", EnumToolbarButton.ADD_ALTERNATIVE.getResource());
		this.btn_AddNotificaction.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "add_notification"));
		this.btn_AddNotificaction.addClickListener((ClickListener & Serializable) e -> this.addNotification_clickHandler(e));
		this.btn_AddNotificaction.addStyleName("toolbar-button");

		// EDIT SECTION
		VerticalLayout cmp_EditButtons = new VerticalLayout();
		cmp_EditButtons.setMargin(new MarginInfo(false, true));
		cmp_EditButtons.setSpacing(false);

		this.btn_EditNotificaction = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "edit_notification",
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "notifications_title")));
		this.btn_EditNotificaction.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "edit_notification",
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "notifications_title")));
		this.btn_EditNotificaction.addClickListener((ClickListener & Serializable) e -> this.editNotification_clickHandler(e));
		this.btn_EditNotificaction.addStyleName("action-button");
		cmp_EditButtons.addComponent(this.btn_EditNotificaction);

		this.btn_EditSubscriptions = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "edit_suscription"));
		this.btn_EditSubscriptions.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "edit_suscription"));
		this.btn_EditSubscriptions.addClickListener((ClickListener & Serializable) e -> this.editSubscriptions_clickHandler(e));
		this.btn_EditSubscriptions.addStyleName("action-button");
		cmp_EditButtons.addComponent(this.btn_EditSubscriptions);

		this.btn_Edit = new BbrPopupButton("");
		this.btn_Edit.setIcon(EnumToolbarButton.EDIT_ALTERNATIVE.getResource());
		this.btn_Edit.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "edit"));
		this.btn_Edit.addStyleName("toolbar-button");
		this.btn_Edit.setContentLayout(cmp_EditButtons);
		// END SECTION

		this.btn_DeleteNotificaction = new Button("", EnumToolbarButton.DELETE.getResource());
		this.btn_DeleteNotificaction.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "delete_notification"));
		this.btn_DeleteNotificaction.addClickListener((ClickListener & Serializable) e -> this.deleteNotification_clickHandler(e));
		this.btn_DeleteNotificaction.addStyleName("toolbar-button");

		// ACTION SECTION
		VerticalLayout cmp_ActionButtons = new VerticalLayout();
		cmp_ActionButtons.setMargin(new MarginInfo(false, true));
		cmp_ActionButtons.setSpacing(false);

		this.btn_EnableNotificaction = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "enable_notification"));
		this.btn_EnableNotificaction.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "enable_notification"));
		this.btn_EnableNotificaction.addClickListener((ClickListener & Serializable) e -> this.activateNotification_clickHandler());
		this.btn_EnableNotificaction.addStyleName("action-button");
		cmp_ActionButtons.addComponent(this.btn_EnableNotificaction);

		this.btn_DisableNotificaction = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "disable_notification"));
		this.btn_DisableNotificaction.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "disable_notification"));
		this.btn_DisableNotificaction.addClickListener((ClickListener & Serializable) e -> this.deactivateNotification_clickHandler(e));
		this.btn_DisableNotificaction.addStyleName("action-button");
		cmp_ActionButtons.addComponent(this.btn_DisableNotificaction);

		this.btn_Actions = new BbrPopupButton("");
		this.btn_Actions.setIcon(EnumToolbarButton.LIST.getResource());
		this.btn_Actions.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "aditional_actions"));
		this.btn_Actions.addStyleName("toolbar-button");
		this.btn_Actions.setContentLayout(cmp_ActionButtons);

		// END ACTION SECTION

		Button btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
		btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
		btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler());
		btn_Refresh.addStyleName("toolbar-button");

		HorizontalLayout leftButtonsBar = new HorizontalLayout();
		HorizontalLayout rightButtonsBar = new HorizontalLayout();

		leftButtonsBar.addComponents(this.btn_AddNotificaction, this.btn_Edit, btn_DeleteNotificaction, this.btn_Actions);
		leftButtonsBar.setSpacing(true);

		rightButtonsBar.addComponent(btn_Refresh);
		rightButtonsBar.setSpacing(true);

		HorizontalLayout toolBar = new HorizontalLayout();
		toolBar.setWidth("100%");
		toolBar.addComponents(leftButtonsBar, rightButtonsBar);
		toolBar.addStyleName("filter-toolbar");
		toolBar.setExpandRatio(leftButtonsBar, 1F);
		toolBar.setExpandRatio(rightButtonsBar, 1F);

		toolBar.setComponentAlignment(leftButtonsBar, Alignment.MIDDLE_LEFT);
		toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);

		// Paging Manager
		this.pagingManager = new BbrPagingManager();
		this.pagingManager.setLocale(this.getUser().getLocale());
		this.pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> pagingChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);

		// Grilla
		this.dgd_Publishings = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.dgd_Publishings.setSortable(false);
		this.dgd_Publishings.addStyleName("report-grid");
		this.dgd_Publishings.addStyleName("bbr-multi-grid");
		this.dgd_Publishings.setSizeFull();
		this.dgd_Publishings.setSelectionMode(SelectionMode.MULTI);
		this.dgd_Publishings.addItemClickListener((ItemClickListener<PublishingReportDataDTO> & Serializable) e -> dgdItem_clickHandler(e));
		this.dgd_Publishings.setPagingManager(pagingManager, this.KEY_FIELD);
		this.dgd_Publishings.addSortListener((SortListener<GridSortOrder<PublishingReportDataDTO>> & Serializable) e -> dgdSort_clickHandler(e));
		this.dgd_Publishings.addSelectionListener((SelectionListener<PublishingReportDataDTO> & Serializable) e -> updateToolBarButtons(e));
		this.initializePublishingGridColumns();

		this.dgd_PublishingsPopUps = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.dgd_PublishingsPopUps.setSortable(false);
		this.dgd_PublishingsPopUps.addStyleName("report-grid");
		this.dgd_PublishingsPopUps.addStyleName("bbr-multi-grid");
		this.dgd_PublishingsPopUps.setSizeFull();
		this.dgd_PublishingsPopUps.setSelectionMode(SelectionMode.MULTI);
		this.dgd_PublishingsPopUps.addItemClickListener((ItemClickListener<PublishingReportDataDTO> & Serializable) e -> dgdItem_clickHandler(e));
		this.dgd_PublishingsPopUps.setPagingManager(pagingManager, this.KEY_FIELD);
		this.dgd_PublishingsPopUps.addSortListener((SortListener<GridSortOrder<PopUpReportDataDTO>> & Serializable) e -> dgdSortPopUp_clickHandler(e));
		this.dgd_PublishingsPopUps.addSelectionListener((SelectionListener<PopUpReportDataDTO> & Serializable) e -> updateToolBarButtons(e));
		this.initializePopupGridColumns();

		this.setVisibilityGridsColumns(true);

		this.mainLayout = new VerticalLayout();
		this.mainLayout.addStyleName("report-layout");
		this.mainLayout.setSizeFull();
		this.mainLayout.setMargin(false);
		this.mainLayout.addComponents(toolBar, this.dgd_Publishings, this.dgd_PublishingsPopUps, this.pagingManager);
		this.mainLayout.setExpandRatio(this.dgd_Publishings, 1F);
		this.mainLayout.setExpandRatio(this.dgd_PublishingsPopUps, 1F);

		this.updateToolBarButtons(null);

		this.addComponents(mainLayout);

	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		NotificationManagement senderReport = (NotificationManagement) sender;
		if (result != null)
		{
			if (result instanceof PopUpReportResultDTO)
			{
				PopUpReportResultDTO reportResult = (PopUpReportResultDTO) result;
				senderReport.doUpdatePopupReport(senderReport, reportResult, true);
			}
			else if (result instanceof PublishingReportResultDTO)
			{
				PublishingReportResultDTO reportResult = (PublishingReportResultDTO) result;
				senderReport.doUpdatePublishingReport(senderReport, reportResult, true);
			}
		}
		else
		{
			if (!senderReport.getBbrUIParent().hasAlertWindowOpen())
				senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));

			senderReport.stopWaiting();
		}

	}

	@Override
	public void attach()
	{
		super.attach();
		this.messagingListener = (BbrMessageEventListener & Serializable) e -> bbrMessage_Listener(e);
		((BbrUI) UI.getCurrent()).getMessagingEventMngr().addBbrMessageListener(messagingListener, MessageTopics.PUBLISHING_ALL);
	}

	@Override
	public void detach()
	{
		super.detach();
		((BbrUI) UI.getCurrent()).getMessagingEventMngr().removeBbrMessageListener(messagingListener, MessageTopics.PUBLISHING_ALL);
	}

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************
	private void dgdSort_clickHandler(SortEvent<GridSortOrder<PublishingReportDataDTO>> e)
	{
		e.getSortOrder();
	}

	private void dgdSortPopUp_clickHandler(SortEvent<GridSortOrder<PopUpReportDataDTO>> e)
	{
		e.getSortOrder();
	}

	private void pagingChange_Listener(BbrPagingEvent e)
	{
		if (e != null && e.getEventType().equals(BbrPagingEvent.PAGE_CHANGED) && e.getResultObject() != null)
		{
			this.initParams.setPage(e.getResultObject().getCurrentPage());
			this.trackReport = false;
			if (this.isPopup)
			{
				this.executePopupService(this.getBbrUIParent(), false);

			}
			else
			{
				this.executePublishingService(this.getBbrUIParent(), false);
			}
		}
	}

	private void addNotification_clickHandler(ClickEvent event)
	{
		if (this.selectedPublishingType != null)
		{
			BbrWindow winCreateNotif = null;

			if (this.selectedPublishingType.getCode().equals(BbrPublishingConstants.EMAIL_PUBLISHING_CODE))
			{
				winCreateNotif = new CreateEditEmailNotification(this.getBbrUIParent(), null);
			}
			else if (this.selectedPublishingType.getCode().equals(BbrPublishingConstants.NEWS_PUBLISHING_CODE))
			{
				winCreateNotif = new CreateEditNewsNotification(this.getBbrUIParent(), null);
			}
			else if (this.selectedPublishingType.getCode().equals(BbrPublishingConstants.POPUP_PUBLISHING_CODE))
			{
				winCreateNotif = new CreateEditPopupNotification(this.getBbrUIParent(), null);
			}
			else if (this.selectedPublishingType.getProcedure())
			{
				winCreateNotif = new CreateEditProceduresNotification(this.getBbrUIParent(), null);
			}

			winCreateNotif.setData(this.selectedPublishingType);
			winCreateNotif.addBbrEventListener((BbrEventListener & Serializable) e -> updateNotifications_handler(e));
			winCreateNotif.open(true, true, this);
		}
	}

	@Override
	public void filterApplied_handler(BbrFilterEvent event)
	{
		if (event != null && event.getResultObject() != null)
		{
			this.selectedPublishingType = (PublishingTypeDTO) event.getResultObject();
			this.updateToolBarButtons(null);
			if (this.selectedPublishingType.getCode().equals(BbrPublishingConstants.POPUP_PUBLISHING_CODE))
			{
				this.isPopup = true;
				this.initializeReportWork(this.isPopup);
				this.setVisibilityGridsColumns(false);
			}
			else
			{
				this.isPopup = false;
				this.initializeReportWork(this.isPopup);
				this.setVisibilityGridsColumns(true);
			}
			this.doUpdateEditNotifiactionButtonCaption(this.selectedPublishingType);
		}
	}

	private void editNotification_clickHandler(ClickEvent event)
	{
		if (!this.isPopup)
		{
			if (this.dgd_Publishings.getSelectedItems() != null && this.dgd_Publishings.getSelectedItems().size() > 0)
			{
				ArrayList<PublishingReportDataDTO> items = new ArrayList<>(this.dgd_Publishings.getSelectedItems());
				if (items.size() == 1)
				{
					this.selectedPublishing = items.get(0);
					this.doEditNotification(this.selectedPublishing);
				}
				else
				{
					this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
							I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "only_one_notifications_requires"));
				}
			}
			else
			{
				this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
						I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "notification_required"));
			}
		}
		else
		{
			if (this.dgd_PublishingsPopUps.getSelectedItems() != null && this.dgd_PublishingsPopUps.getSelectedItems().size() > 0)
			{
				ArrayList<PopUpReportDataDTO> items = new ArrayList<>(this.dgd_PublishingsPopUps.getSelectedItems());
				if (items.size() == 1)
				{
					this.selectedPublishingPopup = items.get(0);
					this.doEditNotification(this.selectedPublishingPopup);
				}
				else
				{
					this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
							I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "only_one_notifications_requires"));
				}
			}
			else
			{
				this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
						I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "notification_required"));
			}
		}

	}

	private void editSubscriptions_clickHandler(ClickEvent event)
	{
		if ((this.dgd_Publishings.getSelectedItems() != null && this.dgd_Publishings.getSelectedItems().size() > 0) 
				|| (this.dgd_PublishingsPopUps.getSelectedItems() != null && this.dgd_PublishingsPopUps.getSelectedItems().size() > 0))
		{
			ArrayList<PublishingReportDataDTO> items = new ArrayList<>((!this.dgd_Publishings.getSelectedItems().isEmpty())? this.dgd_Publishings.getSelectedItems() 
					:this.dgd_PublishingsPopUps.getSelectedItems());
			if (items.size() == 1)
			{
				PublishingReportDataDTO selectedNotification = items.get(0);

				RuleTypeDTO rule = this.getRuleById(selectedNotification.getPukey());

				if (rule != null)
				{
					this.doEditSubscriptions(selectedNotification.getPukey(), rule);
				}
			}
			else
			{
				this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
						I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "only_one_notifications_requires"));
			}
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "notification_required"));
		}

	}

	private void refreshReport_clickHandler()
	{
		if (this.isPopup)
		{
			this.startWaiting();
			this.executeBbrWork(popupReportWork);
		}
		else
		{
			this.startWaiting();
			this.executeBbrWork(publishingReportWork);
		}
	}

	private void deleteNotification_clickHandler(ClickEvent event)
	{
		ArrayList<?> items;
		if (this.isPopup)
		{
			items = this.dgd_PublishingsPopUps.getAllSelectedsItems();
		}
		else
		{
			items = this.dgd_Publishings.getAllSelectedsItems();
		}
		doDeleteNotifications(items);
	}

	private void activateNotification_clickHandler()
	{
		ArrayList<?> items;
		if (this.isPopup)
		{
			items = this.dgd_PublishingsPopUps.getAllSelectedsItems();
		}
		else
		{
			items = this.dgd_Publishings.getAllSelectedsItems();
		}

		if (items != null && items.size() > 0)
		{
			String alertMessage = (items.size() == 1) ? I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "sure_enable_notification")
					: I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "sure_enable_notifications");

			BbrMessagesBoxUtils.showConfirmationMessage(this.getBbrUIParent(),
					I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_question"),
					alertMessage,
					(Runnable & Serializable) () -> this.doActivateNotifications(items));
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(NotificationManagement.this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "select_at_least_one_notification"));
		}
	}

	private void deactivateNotification_clickHandler(ClickEvent event)
	{
		ArrayList<?> items;
		if (this.isPopup)
		{
			items = this.dgd_PublishingsPopUps.getAllSelectedsItems();
		}
		else
		{
			items = this.dgd_Publishings.getAllSelectedsItems();
		}

		if (items != null && items.size() > 0)
		{
			String alertMessage = (items.size() == 1) ? I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "sure_disable_notification")
					: I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "sure_disable_notifications");

			BbrMessagesBoxUtils.showConfirmationMessage(this.getBbrUIParent(),
					I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_question"),
					alertMessage,
					(Runnable & Serializable) () -> doDeactivateNotifications(items));

		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "select_at_least_one_notification"));
		}
	}

	private void updateNotifications_handler(BbrEvent event)
	{
		if (event.getEventType().equals(BbrEvent.ITEM_CREATED) || event.getEventType().equals(BbrEvent.ITEM_UPDATED))
		{
			this.refreshReport_clickHandler();

			String successful = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "successful_operation");
			String askedit = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "ask_edit_notification");

			Long publishingId = (Long) event.getResultObject();

			RuleTypeDTO rule = this.getRuleById(publishingId);

			if (rule != null && !rule.getCode().equals(BbrPublishingConstants.RULE_TYPE_ALL_CODE))
			{
				if (event.getEventType().equals(BbrEvent.ITEM_UPDATED))
				{
					this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"), successful);
				}
				else
				{
					// FIXME Modificar a SHOW YES NO QUESTION MESSAGE
					BbrMessageBox
							.createQuestion(NotificationManagement.this.getBbrUIParent())
							.withYesButton(new Runnable()
							{
								@Override
								public void run()
								{
									if (rule != null)
									{
										NotificationManagement.this.doEditSubscriptions(publishingId, rule);
									}
								}
							})
							.withNoButton()
							.withCaption(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_question"))
							.withHtmlMessage(successful + askedit)
							.withWidthForAllButtons("100px")
							.open();
				}
			}
			else
			{
				this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"), successful);
			}
		}
	}

	private void dgdItem_clickHandler(ItemClick<PublishingReportDataDTO> event)
	{
		if (event.getMouseEventDetails().isDoubleClick())
		{
			if (event.getItem() != null)
			{
				PublishingReportDataDTO selectedNpotification = event.getItem();
				if (selectedNpotification != null)
				{
					doEditNotification(selectedNpotification);
				}
			}
		}
	}

	private void bbrMessage_Listener(BbrMessageEvent event)
	{
		if (event != null && event.getMessage() != null && event.getMessage().getSenderBbrUser() != null)
		{
			BbrUser sender = event.getMessage().getSenderBbrUser();
			Boolean trackReport = (sender.getId().equals(this.getUser().getId()));
			this.trackReport = trackReport;
			if (this.isPopup)
			{
				this.executePopupService(this.getBbrUIParent(), true);
			}
			else
			{
				this.executePublishingService(this.getBbrUIParent(), true);
			}

		}
	}
	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private PublishingReportInitParamsDTO getInitParams(PublishingTypeDTO selectedPublishing)
	{
		OrderCriteriaDTO order = new OrderCriteriaDTO(this.TITLE_FIELD.toUpperCase(), true);
		PublishingReportInitParamsDTO result = new PublishingReportInitParamsDTO();
		result.setPublishingtypeid(selectedPublishing.getId());
		result.setPage(this.DEFAULT_PAGE_NUMBER);
		result.setRows(this.MAX_ROWS_NUMBER);
		result.setOrderby(order);

		return result;
	}

	private PopUpReportInitParamsDTO getPopInitParams()
	{
		OrderCriteriaDTO order = new OrderCriteriaDTO(this.TITLE_FIELD.toUpperCase(), true);
		PopUpReportInitParamsDTO result = new PopUpReportInitParamsDTO();
		result.setPage(this.DEFAULT_PAGE_NUMBER);
		result.setRows(this.MAX_ROWS_NUMBER);
		result.setOrderby(order);

		return result;
	}

	private void initializePublishingGridColumns()
	{
		this.dgd_Publishings.addColumn(notification ->
		{
			NotificationStateRenderer imageComponent = new NotificationStateRenderer(this.getBbrUIParent(), notification);
			return imageComponent;
		}, new ComponentRenderer())
				.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "state"))
				.setWidth(90D)
				.setStyleGenerator(lastlogin -> BbrAlignment.CENTER.getValue());

		this.dgd_Publishings.addColumn(PublishingReportDataDTO::getLastchangedate)
				.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_update_date"))
				.setStyleGenerator(lastUpdate -> BbrAlignment.CENTER.getValue())
				.setRenderer(new DateRenderer())
				.setWidth(170D);
		this.dgd_Publishings.addColumn(PublishingReportDataDTO::getLastchangeuser)
				.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_update_user"));
		this.dgd_Publishings.addColumn(PublishingReportDataDTO::getTitle)
				.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "publishing_title"));
		this.dgd_Publishings.addColumn(PublishingReportDataDTO::getInitdate)
				.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "init_date"))
				.setStyleGenerator(lastUpdate -> BbrAlignment.CENTER.getValue())
				.setRenderer(new DateRenderer())
				.setWidth(170D);
		this.dgd_Publishings.addColumn(PublishingReportDataDTO::getEnddate)
				.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "end_date"))
				.setStyleGenerator(lastUpdate -> BbrAlignment.CENTER.getValue())
				.setRenderer(new DateRenderer())
				.setWidth(170D);
		this.dgd_Publishings.addColumn(PublishingReportDataDTO::getAssignedto)
				.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "to"));

	}

	private void initializePopupGridColumns()
	{
		this.dgd_PublishingsPopUps.addColumn(notification ->
		{
			return new NotificationStateRenderer(this.getBbrUIParent(), notification);
		}, new ComponentRenderer())
				.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "state"))
				.setWidth(90D)
				.setStyleGenerator(lastlogin -> BbrAlignment.CENTER.getValue());

		this.dgd_PublishingsPopUps.addColumn(PopUpReportDataDTO::getLastchangedate)
				.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_update_date"))
				.setStyleGenerator(lastUpdate -> BbrAlignment.CENTER.getValue())
				.setRenderer(new DateRenderer())
				.setWidth(170D);
		this.dgd_PublishingsPopUps.addColumn(PopUpReportDataDTO::getLastchangeuser)
				.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "last_update_user"));
		this.dgd_PublishingsPopUps.addColumn(PopUpReportDataDTO::getTitle)
				.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "publishing_title"));
		this.dgd_PublishingsPopUps.addColumn(PopUpReportDataDTO::getInitdate)
				.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "init_date"))
				.setStyleGenerator(lastUpdate -> BbrAlignment.CENTER.getValue())
				.setRenderer(new DateRenderer())
				.setWidth(170D);
		this.dgd_PublishingsPopUps.addColumn(PopUpReportDataDTO::getEnddate)
				.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "end_date"))
				.setStyleGenerator(lastUpdate -> BbrAlignment.CENTER.getValue())
				.setRenderer(new DateRenderer())
				.setWidth(170D);
		this.dgd_PublishingsPopUps.addColumn(PopUpReportDataDTO::getAssignedto)
				.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "to"));

		this.dgd_PublishingsPopUps.addColumn(PopUpReportDataDTO::getPopuptypename)
				.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "module_types"));

		this.dgd_PublishingsPopUps.addColumn(PopUpReportDataDTO::getCpname)
				.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "module_behavior"));

		this.dgd_PublishingsPopUps.addColumn(PopUpReportDataDTO::getModule)
				.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "module"));

	}

	private void initializeReportWork(boolean isPopup)
	{
		if (isPopup)
		{
			popupReportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
			popupReportWork.addFunction(new Function<Object, PopUpReportResultDTO>()
			{
				@Override
				public PopUpReportResultDTO apply(Object t)
				{
					return executePopupService(NotificationManagement.this.getBbrUIParent(), true);
				}
			});
			this.executeBbrWork(popupReportWork);
			this.startWaiting();

		}
		else
		{
			publishingReportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
			publishingReportWork.addFunction(new Function<Object, PublishingReportResultDTO>()
			{

				@Override
				public PublishingReportResultDTO apply(Object t)
				{
					return executePublishingService(NotificationManagement.this.getBbrUIParent(), true);
				}

			});
			this.executeBbrWork(publishingReportWork);
			this.startWaiting();

		}
	}

	private PublishingReportResultDTO executePublishingService(BbrUI bbrUI, boolean resetPageInfo)
	{
		PublishingReportResultDTO reportResult = null;
		try
		{
			this.initParams = this.getInitParams(this.selectedPublishingType);
			reportResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent()).getPublishingReport(initParams, resetPageInfo);
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

		return reportResult;
	}

	private PopUpReportResultDTO executePopupService(BbrUI bbrUI, boolean resetPageInfo)
	{
		PopUpReportResultDTO reportResult = null;
		try
		{
			this.initParamsPopUp = this.getPopInitParams();
			reportResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(bbrUI).getPopUpReport(initParamsPopUp, resetPageInfo);
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

		return reportResult;
	}

	private void doUpdateEditNotifiactionButtonCaption(PublishingTypeDTO selectedPublishingType)
	{
		this.btn_EditNotificaction.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "edit_notification", selectedPublishingType.getName()));
		this.btn_EditNotificaction.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "edit_notification", selectedPublishingType.getName()));
	}

	private void doEditSubscriptions(Long publishingId, RuleTypeDTO rule)
	{
		if (publishingId != null)
		{
			if (!rule.getCode().equals(BbrPublishingConstants.RULE_TYPE_ALL_CODE))
			{
				BbrWindow winAddNotificationSubscribers = null;

				switch (rule.getCode())
				{
					case BbrPublishingConstants.RULE_TYPE_USER_CODE:
						winAddNotificationSubscribers = new EditUsersSubscription(this.getBbrUIParent(), publishingId, rule);
						break;
					case BbrPublishingConstants.RULE_TYPE_COMPANY_CODE:
						winAddNotificationSubscribers = new EditCompaniesSubscription(this.getBbrUIParent(), publishingId, rule);
						break;
					case BbrPublishingConstants.RULE_TYPE_PROFILE_CODE:
						winAddNotificationSubscribers = new EditProfilesSubscription(this.getBbrUIParent(), publishingId, rule);
						break;
					case BbrPublishingConstants.RULE_TYPE_EVENT_CODE:
						winAddNotificationSubscribers = new EditEventsSubscription(this.getBbrUIParent(), publishingId, rule);
						break;
				}

				winAddNotificationSubscribers.addBbrEventListener((BbrEventListener & Serializable) e -> updateNotifications_handler(e));
				winAddNotificationSubscribers.open(true, true, this);
			}
		}
	}

	private void doUpdatePublishingReport(NotificationManagement senderReport, PublishingReportResultDTO reportResult, boolean resetPageInfo)
	{
		String errordescription = "";

		// Start Tracking
		senderReport.getTimingMngr().startTimer();

		if (reportResult != null)
		{
			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), true);
			if (!error.hasError())
			{
				ListDataProvider<PublishingReportDataDTO> dataprovider = new ListDataProvider<>(Arrays.asList(reportResult.getPublishingdata()));
				senderReport.dgd_Publishings.setDataProvider(dataprovider, resetPageInfo);
				if (resetPageInfo)
				{
					PageInfoDTO pageInfo = reportResult.getPageinfo();
					BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
					senderReport.pagingManager.setPages(bbrPage, resetPageInfo);
				}

				// End Tracking
				if (trackReport == true)
				{
					senderReport.trackEvent(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription());
				}

				if (reportResult.getPublishingdata().length == 0)
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
		if (errordescription.length() > 0 && trackReport == true)
		{
			// Track Error
			senderReport.trackError(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription(), errordescription, null, this);
		}
		senderReport.stopWaiting();
	}

	private void doUpdatePopupReport(NotificationManagement senderReport, PopUpReportResultDTO reportResult, boolean resetPageInfo)
	{
		String errordescription = "";
		// Start Tracking
		this.getTimingMngr().startTimer();
		if (reportResult != null)
		{
			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), true);
			if (!error.hasError())
			{
				ListDataProvider<PopUpReportDataDTO> dataprovider = new ListDataProvider<>(Arrays.asList(reportResult.getPopupdata()));
				senderReport.dgd_PublishingsPopUps.setDataProvider(dataprovider, resetPageInfo);

				if (resetPageInfo)
				{
					PageInfoDTO pageInfo = reportResult.getPageinfo();

					BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
					this.pagingManager.setPages(bbrPage, resetPageInfo);
				}

				// End Tracking
				if (trackReport == true)
				{
					this.trackEvent(TrackingConstants.REPORT_VIEW, this.getBbrFilterDescription());
				}

				if (reportResult.getPopupdata().length == 0)
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

		if (errordescription.length() > 0 && trackReport == true)
		{
			// Track Error
			senderReport.trackError(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription(), errordescription, null, this);
		}
		senderReport.stopWaiting();

	}

	private void setVisibilityGridsColumns(boolean isPublishingVisible)
	{
		this.dgd_Publishings.setVisible(isPublishingVisible);
		this.dgd_PublishingsPopUps.setVisible(!isPublishingVisible);
	}

	private void doEditNotification(PublishingReportDataDTO selectedNotification)
	{
		if (selectedNotification != null)
		{
			BbrWindow winEditNotification = null;

			if (this.selectedPublishingType.getCode().equals(BbrPublishingConstants.EMAIL_PUBLISHING_CODE))
			{
				winEditNotification = new CreateEditEmailNotification(this.getBbrUIParent(), selectedNotification);
			}
			else if (this.selectedPublishingType.getCode().equals(BbrPublishingConstants.NEWS_PUBLISHING_CODE))
			{
				winEditNotification = new CreateEditNewsNotification(this.getBbrUIParent(), selectedNotification);
			}
			else if (this.selectedPublishingType.getCode().equals(BbrPublishingConstants.POPUP_PUBLISHING_CODE))
			{
				winEditNotification = new CreateEditPopupNotification(this.getBbrUIParent(), (PopUpReportDataDTO) selectedNotification);
			}
			else if (this.selectedPublishingType.getProcedure())
			{
				winEditNotification = new CreateEditProceduresNotification(this.getBbrUIParent(), selectedNotification);
			}

			winEditNotification.setData(this.selectedPublishingType);
			winEditNotification.addBbrEventListener((BbrEventListener & Serializable) e -> updateNotifications_handler(e));
			winEditNotification.open(true, true, this);
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "notification_required"));
		}
	}

	private void doDeleteNotifications(List<?> items)
	{
		if (items != null && items.size() > 0)
		{
			String question = (items.size() == 1) ? I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "sure_delete_notification")
					: I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "sure_delete_notifications");

			// FIXME Modificar a SHOW YES NO QUESTION MESSAGE
			BbrMessageBox
					.createQuestion(NotificationManagement.this.getBbrUIParent())
					.withYesButton(new Runnable()
					{
						@Override
						public void run()
						{
							if (isPopup)
							{
								deleteSelectedPopups(items);
							}
							else
							{
								deleteSelectedNotifications(items);
							}
						}
					})
					.withCancelButton()
					.withCaption(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_question"))
					.withMessage(question)
					.open();
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "select_at_least_one_notification"));
		}
	}

	private Long[] getNotificationsIds(List<?> items)
	{
		Long[] result = null;

		if (items != null && items.size() > 0)
		{
			result = new Long[items.size()];

			for (int i = 0; i < items.size(); i++)
			{
				result[i] = ((PublishingReportDataDTO) items.get(i)).getPukey();
			}
		}

		return result;
	}

	private void deleteSelectedNotifications(List<?> items)
	{
		if (items != null && !this.isPopup)
		{
			try
			{
				Long[] keys = this.getNotificationsIds(items);
				if (keys != null)
				{
					BaseResultDTO deleteResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent()).doDeletePublishingById(keys);
					if (deleteResult.getStatuscode().equals("0"))
					{
						this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"),
								I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "successful_operation"),
								"", () -> this.refreshReport_clickHandler());

						this.executePublishingService(this.getBbrUIParent(), true);

					}
					else
					{
						this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), deleteResult.getStatusmessage(),
								"", () -> this.refreshReport_clickHandler());
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
				this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P200"));
			}

		}
	}

	private void deleteSelectedPopups(List<?> items)
	{
		if (items != null && this.isPopup)
		{
			try
			{
				Long[] keys = this.getNotificationsIds(items);
				if (keys != null)
				{
					BaseResultDTO deleteResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent()).doDeletePopUpById(keys);
					if (deleteResult.getStatuscode().equals("0"))
					{
						this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"),
								I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "successful_operation"));

						this.executePopupService(this.getBbrUIParent(), true);

						this.refreshReport_clickHandler();
					}
					else
					{
						this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), deleteResult.getStatusmessage());
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
				this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P200"));
			}

		}
	}

	private void doActivateNotifications(ArrayList<?> items)
	{
		if (items != null && items.size() > 0)
		{
			if (this.isPopup)
			{
				activateSelectedPopups(items);
			}
			else
			{
				activateSelectedNotifications(items);
			}
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "select_at_least_one_notification"));
		}
	}

	private void activateSelectedNotifications(ArrayList<?> items)
	{
		if (items != null && !this.isPopup)
		{
			try
			{
				Long[] keys = this.getNotificationsIds(items);
				if (keys != null)
				{
					BaseResultDTO deleteResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent()).doActivatePublishing(keys,
							this.getUser().getFullName());
					if (deleteResult.getStatuscode().equals("0"))
					{
						this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"),
								I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "successful_operation"),
								null, () -> this.refreshReport_clickHandler());

						this.executePublishingService(this.getBbrUIParent(), true);
					}
					else
					{
						this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), deleteResult.getStatusmessage());
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
				this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P200"));
			}

		}
	}

	private void activateSelectedPopups(ArrayList<?> items)
	{
		if (items != null && this.isPopup)
		{
			try
			{
				Long[] keys = this.getNotificationsIds(items);
				if (keys != null)
				{
					BaseResultDTO deleteResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent()).doActivatePopUp(keys,
							this.getUser().getFullName());
					if (deleteResult.getStatuscode().equals("0"))
					{
						this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"),
								I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "successful_operation"),
								null, () -> this.refreshReport_clickHandler());
						this.executePopupService(this.getBbrUIParent(), true);
					}
					else
					{
						this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), deleteResult.getStatusmessage());
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
				this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P200"));
			}

		}
	}

	private void doDeactivateNotifications(ArrayList<?> items)
	{
		if (items != null && items.size() > 0)
		{
			if (this.isPopup)
			{
				deactivateSelectedPopups(items);
			}
			else
			{
				deactivateSelectedNotifications(items);
			}
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "select_at_least_one_notification"));
		}
	}

	private void deactivateSelectedNotifications(ArrayList<?> items)
	{
		if (items != null && !this.isPopup)
		{
			try
			{
				Long[] keys = this.getNotificationsIds(items);
				if (keys != null)
				{
					BaseResultDTO deleteResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent()).doInactivatePublishing(keys,
							this.getUser().getFullName());
					if (deleteResult.getStatuscode().equals("0"))
					{
						this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"),
								I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "successful_operation"),
								null, () -> this.refreshReport_clickHandler());

						this.executePublishingService(this.getBbrUIParent(), true);
					}
					else
					{
						this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), deleteResult.getStatusmessage());
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
				this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P200"));
			}

		}
	}

	private void deactivateSelectedPopups(ArrayList<?> items)
	{
		if (items != null && this.isPopup)
		{
			try
			{
				Long[] keys = this.getNotificationsIds(items);
				if (keys != null)
				{
					BaseResultDTO deleteResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent()).doInactivatePopUp(keys,
							this.getUser().getFullName());
					if (deleteResult.getStatuscode().equals("0"))
					{
						this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"),
								I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "successful_operation"),
								null, () -> this.refreshReport_clickHandler());
						this.executePopupService(this.getBbrUIParent(), true);
					}
					else
					{
						this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), deleteResult.getStatusmessage());
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
				this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P200"));
			}

		}
	}

	private RuleTypeDTO getRuleById(Long publishingId)
	{
		RuleTypeDTO result = null;
		try
		{
			RuleTypeResultDTO ruleResult = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(this.getBbrUIParent()).getRuleTypeByPublishingId(publishingId);
			if (ruleResult != null && ruleResult.getRuletype() != null)
			{
				result = ruleResult.getRuletype();
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
			e.printStackTrace();
		}

		return result;
	}

	private void updateToolBarButtons(SelectionEvent<?> event)
	{
		boolean canAdd = false;
		boolean canEditNotification = false;
		boolean canEditSubscription = false;
		boolean canDelete = false;
		boolean canEnable = false;
		boolean canDisable = false;

		if (this.selectedPublishingType != null)
		{
			canAdd = true;

			if (event != null && event.getAllSelectedItems() != null && event.getAllSelectedItems().size() > 0)
			{
				canDelete = true;
				canEnable = true;
				canDisable = true;

				if (event.getAllSelectedItems().size() == 1)
				{
					canEditNotification = true;
					canEditSubscription = this.getCanEditSubscription(event);

				}
			}
		}

		if (this.btn_AddNotificaction != null)
		{
			this.btn_AddNotificaction.setEnabled(canAdd);
		}
		if (this.btn_EditNotificaction != null)
		{
			this.btn_EditNotificaction.setEnabled(canEditNotification);
		}
		if (this.btn_EditSubscriptions != null)
		{
			this.btn_EditSubscriptions.setEnabled(canEditSubscription);
		}
		if (this.btn_DeleteNotificaction != null)
		{
			this.btn_DeleteNotificaction.setEnabled(canDelete);
		}
		if (this.btn_EnableNotificaction != null)
		{
			this.btn_EnableNotificaction.setEnabled(canEnable);
		}
		if (this.btn_DisableNotificaction != null)
		{
			this.btn_DisableNotificaction.setEnabled(canDisable);
		}
	}

	private boolean getCanEditSubscription(SelectionEvent<?> event)
	{
		if (this.isPopup)
		{
			@SuppressWarnings("unchecked")
			SelectionEvent<PopUpReportDataDTO> eventResult = (SelectionEvent<PopUpReportDataDTO>) event;
			return !(eventResult.getFirstSelectedItem().get().getRulecode().equalsIgnoreCase(BbrPublishingConstants.RULE_TYPE_ALL_CODE));
		}
		else
		{
			@SuppressWarnings("unchecked")
			SelectionEvent<PublishingReportDataDTO> eventResult = (SelectionEvent<PublishingReportDataDTO>) event;
			return !(eventResult.getFirstSelectedItem().get().getRulecode().equalsIgnoreCase(BbrPublishingConstants.RULE_TYPE_ALL_CODE));
		}

	}

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
