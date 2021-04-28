package bbr.b2b.portal.modules.users.management;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
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

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.jms.constants.MessageTopics;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.BbrMessagesBoxUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.filters.management.FaqManagementFilter;
import bbr.b2b.portal.components.modules.users.management.faqs.CreateFAQ;
import bbr.b2b.portal.components.modules.users.management.faqs.EditFAQ;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.FaqReportArrayResultDTO;
import bbr.b2b.users.report.classes.FaqReportDataDTO;
import bbr.b2b.users.report.classes.FaqReportInitParamDTO;
import bbr.b2b.users.report.classes.ProfileDTO;
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
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrMessageBox;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.grid.renderer.DateRenderer;

public class FaqManagement extends BbrModule implements BbrWorkExecutor
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long					serialVersionUID	= 7027502352762624910L;

	private BbrAdvancedGrid<FaqReportDataDTO>	dgd_Faqs;
	private VerticalLayout						mainLayout;
	private final String						QUESTION_FIELD		= "question";

	private Button								btn_AddFaq			= null;
	private Button								btn_EditFaq			= null;
	private Button								btn_DeleteFaq		= null;

	private final int							DEFAULT_PAGE_NUMBER	= 1;
	private final int							MAX_ROWS_NUMBER		= 20;

	private FaqReportInitParamDTO				initParams;

	private BbrMessageEventListener				messagingListener	= null;

	private BbrWork<FaqReportArrayResultDTO>	reportWork			= null;

	private Boolean								trackReport			= true;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public FaqManagement(BbrUI bbrUIParent)
	{
		super(bbrUIParent);
	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	@Override
	public void initializeView()
	{
		// Filtro del reporte
		FaqManagementFilter filterLayout = new FaqManagementFilter(this);
		filterLayout.initializeView();
		this.setBbrFilterContainer(filterLayout);

		// Reporte
		// Barra de Herramientas
		btn_AddFaq = new Button("", EnumToolbarButton.ADD_ALTERNATIVE.getResource());
		btn_AddFaq.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "add_faq"));
		btn_AddFaq.addClickListener((ClickListener & Serializable) e -> addFaq_clickHandler(e));
		btn_AddFaq.addStyleName("toolbar-button");

		btn_EditFaq = new Button("", EnumToolbarButton.EDIT_ALTERNATIVE.getResource());
		btn_EditFaq.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "edit_faq"));
		btn_EditFaq.addClickListener((ClickListener & Serializable) e -> editFaq_clickHandler(e));
		btn_EditFaq.addStyleName("toolbar-button");

		btn_DeleteFaq = new Button("", EnumToolbarButton.DELETE.getResource());
		btn_DeleteFaq.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "delete_faq"));
		btn_DeleteFaq.addClickListener((ClickListener & Serializable) e -> deleteFaq_clickHandler(e));
		btn_DeleteFaq.addStyleName("toolbar-button");

		Button btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
		btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
		btn_Refresh.addClickListener((ClickListener & Serializable) e -> refreshFaqs_clickHandler(e));
		btn_Refresh.addStyleName("toolbar-button");

		HorizontalLayout leftButtonsBar = new HorizontalLayout();
		HorizontalLayout rightButtonsBar = new HorizontalLayout();

		leftButtonsBar.addComponents(btn_AddFaq, btn_EditFaq, btn_DeleteFaq);
		leftButtonsBar.setSpacing(true);

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

		// Grilla
		dgd_Faqs = new BbrAdvancedGrid<>(this.getLocale());
		dgd_Faqs.setSortable(false);
		this.initializeGridColumns();
		dgd_Faqs.addStyleName("report-grid");
		dgd_Faqs.addStyleName("bbr-multi-grid");
		dgd_Faqs.setSizeFull();
		dgd_Faqs.setSelectionMode(SelectionMode.MULTI);
		dgd_Faqs.addItemClickListener((ItemClickListener<FaqReportDataDTO> & Serializable) e -> dgdItem_clickHandler(e));
		dgd_Faqs.addSelectionListener(new SelectionListener<FaqReportDataDTO>()
		{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void selectionChange(SelectionEvent<FaqReportDataDTO> event)
			{
				updateToolBarButtons(event);
			}
		});

		this.updateToolBarButtons(null);

		mainLayout = new VerticalLayout();
		mainLayout.addStyleName("report-layout");
		mainLayout.setSizeFull();
		mainLayout.setMargin(false);
		mainLayout.addComponents(toolBar, dgd_Faqs);
		mainLayout.setExpandRatio(dgd_Faqs, 1F);
		this.addComponents(mainLayout);

		reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		reportWork.addFunction(new Function<Object, FaqReportArrayResultDTO>()
		{
			@Override
			public FaqReportArrayResultDTO apply(Object t)
			{
				return executeService(FaqManagement.this.getBbrUIParent());
			}
		});
	}

	@Override
	public void attach()
	{
		super.attach();
		this.messagingListener = (BbrMessageEventListener & Serializable) e -> bbrMessage_Listener(e);
		((BbrUI) UI.getCurrent()).getMessagingEventMngr().addBbrMessageListener(messagingListener, MessageTopics.FAQS);
	}

	@Override
	public void detach()
	{
		super.detach();
		((BbrUI) UI.getCurrent()).getMessagingEventMngr().removeBbrMessageListener(messagingListener, MessageTopics.FAQS);
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
		}
		else
		{
			FaqManagement senderReport = (FaqManagement) sender;

			if(!senderReport.getBbrUIParent().hasAlertWindowOpen())
			senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));

			senderReport.stopWaiting();
		}
	}
	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************
	@Override
	public void filterApplied_handler(BbrFilterEvent event)
	{
		if (event != null && event.getResultObject() != null)
		{
			ProfileDTO selectedProfile = (ProfileDTO) event.getResultObject();
			initParams = this.getInitParams(selectedProfile);

			this.startWaiting();

			this.trackReport = true;
			this.executeBbrWork(reportWork);
		}
	}

	private void addFaq_clickHandler(ClickEvent event)
	{
		CreateFAQ winCreateFaq = new CreateFAQ(this.getBbrUIParent());
		winCreateFaq.initializeView();
		winCreateFaq.addBbrEventListener((BbrEventListener & Serializable) e -> updateFaq_handler(e));
		winCreateFaq.open(true);
	}

	private void editFaq_clickHandler(ClickEvent event)
	{
		FaqReportDataDTO selectedFaq = (FaqReportDataDTO) dgd_Faqs.getSelectedItems().iterator().next();

		doEditFaq(selectedFaq);
	}

	private void refreshFaqs_clickHandler(ClickEvent event)
	{
		this.startWaiting();

		this.trackReport = true;
		this.executeBbrWork(reportWork);
	}

	private void deleteFaq_clickHandler(ClickEvent event)
	{
		if (dgd_Faqs.getSelectedItems() != null && dgd_Faqs.getSelectedItems().size() > 0)
		{
			ArrayList<FaqReportDataDTO> items = new ArrayList<>(dgd_Faqs.getSelectedItems());

			ArrayList<Long> ids = new ArrayList<>();
			items.forEach((Consumer<FaqReportDataDTO> & Serializable) faq -> ids.add(faq.getFakey()));
			Long[] faqsIds = ids.toArray(new Long[ids.size()]);

			doDeleteFaq(faqsIds);

		}
	}

	private void updateFaq_handler(BbrEvent event)
	{
		this.startWaiting();

		this.trackReport = true;
		this.executeBbrWork(reportWork);
	}

	private void dgdItem_clickHandler(ItemClick<FaqReportDataDTO> event)
	{
		if (event.getMouseEventDetails().isDoubleClick())
		{
			FaqReportDataDTO selectedFaq = event.getItem();
			doEditFaq(selectedFaq);
		}
	}

	private void bbrMessage_Listener(BbrMessageEvent event)
	{
		if (event != null && event.getMessage() != null && event.getMessage().getSenderBbrUser() != null)
		{
			this.startWaiting();

			BbrUser sender = event.getMessage().getSenderBbrUser();
			this.trackReport = (sender.getId().equals(this.getUser().getId()));

			this.executeBbrWork(reportWork);
		}
	}
	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private FaqReportInitParamDTO getInitParams(ProfileDTO selectedProfile)
	{
		OrderCriteriaDTO order = new OrderCriteriaDTO(this.QUESTION_FIELD.toUpperCase(), true);
		FaqReportInitParamDTO result = new FaqReportInitParamDTO();
		result.setPrkey(selectedProfile.getId());
		result.setPageNumber(this.DEFAULT_PAGE_NUMBER);
		result.setRows(this.MAX_ROWS_NUMBER);
		result.setOrderby(new OrderCriteriaDTO[] { order });

		return result;
	}

	private void initializeGridColumns()
	{
		dgd_Faqs.addColumn(FaqReportDataDTO::getQuestion).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "question"));
		dgd_Faqs.addColumn(FaqReportDataDTO::getLastchange)
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "date_last_edition"))
				.setStyleGenerator(lastlogin -> BbrAlignment.CENTER.getValue())
				.setRenderer(new DateRenderer());
		dgd_Faqs.addColumn(FaqReportDataDTO::getResponsable).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "responsable"));
		dgd_Faqs.addColumn(FaqReportDataDTO::getCounter)
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "clicks"))
				.setStyleGenerator(lastlogin -> BbrAlignment.RIGHT.getValue())
				.setWidth(100D);
	}

	private void doEditFaq(FaqReportDataDTO selectedFaq)
	{
		if (selectedFaq != null)
		{
			EditFAQ winEditFaq = new EditFAQ(this.getBbrUIParent(), selectedFaq);
			winEditFaq.initializeView();
			winEditFaq.addBbrEventListener((BbrEventListener & Serializable) e -> updateFaq_handler(e));
			winEditFaq.open(true);
		}
		else
		{
			BbrMessageBox
					.createWarning(FaqManagement.this.getBbrUIParent())
					.withOkButton()
					.withCaption(I18NManager.getI18NString(FaqManagement.this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_warning"))
					.withMessage(I18NManager.getI18NString(FaqManagement.this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "faq_required"))
					.open();
		}
	}

	private void doDeleteFaq(Long[] faqsIds)
	{
		if (faqsIds != null && faqsIds.length > 0)
		{
			BbrMessagesBoxUtils.showConfirmationMessage(FaqManagement.this.getBbrUIParent(),
					I18NManager.getI18NString(FaqManagement.this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_question"),
					I18NManager.getI18NString(FaqManagement.this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "sure_delete_faq"),
					(Runnable & Serializable) () -> deleteSelectedFaq(faqsIds));
		}
		else
		{
			BbrMessageBox
					.createWarning(FaqManagement.this.getBbrUIParent())
					.withOkButton()
					.withCaption(I18NManager.getI18NString(FaqManagement.this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_warning"))
					.withMessage(I18NManager.getI18NString(FaqManagement.this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "faq_required"))
					.open();
		}
	}

	private void deleteSelectedFaq(Long[] faqsIds)
	{
		if (faqsIds != null && faqsIds.length > 0)
		{
			try
			{
				BaseResultDTO deleteResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().doDeleteFaqs(faqsIds);
				if (deleteResult.getStatuscode().equals("0"))
				{
					// Se elimin� el Faq sin problemas.
					BbrMessageBox
							.createInfo(FaqManagement.this.getBbrUIParent())
							.withOkButton()
							.withCaption(I18NManager.getI18NString(FaqManagement.this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"))
							.withMessage(I18NManager.getI18NString(FaqManagement.this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "faq_deleted"))
							.open();

					this.startWaiting();

					this.trackReport = true;
					this.executeBbrWork(reportWork);
				}
				else
				{
					BbrMessageBox
							.createError(FaqManagement.this.getBbrUIParent())
							.withCloseButton()
							.withCaption(I18NManager.getI18NString(FaqManagement.this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"))
							.withMessage(deleteResult.getStatusmessage())
							.open();
				}
			}
			catch (BbrUserException e)
			{
				AppUtils.getInstance().doLogOut(e.getMessage(), this.getBbrUIParent());
			}
			catch (BbrSystemException e)
			{
				e.printStackTrace();
				this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "P200"));
			}

		}
	}

	private void updateToolBarButtons(SelectionEvent<FaqReportDataDTO> event)
	{
		boolean canAdd = false;
		boolean canDelete = false;
		boolean canEdit = false;

		if (initParams != null)
		{
			canAdd = true;
		}

		if (event != null && event.getAllSelectedItems() != null && event.getAllSelectedItems().size() > 0)
		{
			canDelete = true;

			if (event.getAllSelectedItems().size() == 1)
			{
				canEdit = true;
			}
		}

		if (this.btn_AddFaq != null)
		{
			this.btn_AddFaq.setEnabled(canAdd);
		}
		if (this.btn_DeleteFaq != null)
		{
			this.btn_DeleteFaq.setEnabled(canDelete);
		}
		if (this.btn_EditFaq != null)
		{
			this.btn_EditFaq.setEnabled(canEdit);
		}
	}

	private FaqReportArrayResultDTO executeService(BbrUI bbrUI)
	{
		FaqReportArrayResultDTO result = null;
		if (this.initParams != null)
		{
			try
			{
				// Start Tracking
				this.getTimingMngr().startTimer();
				result = EJBFactory.getUserEJBFactory().getUserReportManagerLocal(bbrUI).getFaqReportByProfile(initParams, true);

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

	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{
		String errordescription = "";

		FaqManagement senderReport = (FaqManagement) sender;

		senderReport.updateToolBarButtons(null);
		if (result != null)
		{
			FaqReportArrayResultDTO reportResult = (FaqReportArrayResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				ListDataProvider<FaqReportDataDTO> dataprovider = new ListDataProvider<>(Arrays.asList(reportResult.getFaqreport()));

				senderReport.dgd_Faqs.setDataProvider(dataprovider);

				// End Tracking
				if (senderReport.trackReport == true)
				{
					senderReport.trackEvent(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription());
				}

				if (reportResult.getFaqreport().length == 0)
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

		this.stopWaiting();
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
