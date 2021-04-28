package bbr.b2b.portal.modules.users.management;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.SortEvent;
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

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.filters.management.UserManagementRequestsFilter;
import bbr.b2b.portal.components.modules.users.management.user_request.DetailsUserRequest;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.RequestReportDTO;
import bbr.b2b.users.report.classes.RequestReportInitParamDTO;
import bbr.b2b.users.report.classes.RequestReportResultDTO;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.constants.TrackingConstants;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
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

public class UserRequestsManagement extends BbrModule implements BbrWorkExecutor
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	// Constants
	private static final long					serialVersionUID	= 7027502352762624910L;
	private static final String					REQUEST_NUMBER		= "request_number";
	private static final String					REQUEST_DATE		= "request_date";
	private static final String					USER_ENTERPRISE		= "user_enterprise";
	private static final String					REQUESTER_FULL_NAME	= "requester_full_name";
	private static final String					ACTION				= "action";
	private static final String					STATE				= "state";
	// Components
	private BbrAdvancedGrid<RequestReportDTO>	dgd_Requests		= null;
	private Button								btn_ViewDetails		= null;
	private BbrPagingManager					pagingManager		= null;
	// Variables
	private final String						DEFAULT_SORT_FIELD	= REQUEST_NUMBER;
	private final int							DEFAULT_PAGE_NUMBER	= 1;
	private final int							MAX_ROWS_NUMBER		= 100;
	private RequestReportInitParamDTO			initParam			= null;
	private BbrWork<RequestReportResultDTO>		reportWork			= null;
	private Boolean								trackReport			= true;
	private Boolean								resetPageInfo		= true;
	private RequestReportDTO					userRequestSelected	= null;
	private SearchCriterion						selectedCriterion	= null;
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	public UserRequestsManagement(BbrUI bbrUIParent)
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
		UserManagementRequestsFilter filterLayout = new UserManagementRequestsFilter(this);
		filterLayout.initializeView();
		this.setBbrFilterContainer(filterLayout);

		// Paging Manager
		this.pagingManager = new BbrPagingManager();
		this.pagingManager.setLocale(this.getUser().getLocale());
		this.pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> this.pagingChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);

		// Reporte
		// Barra de Herramientas
		this.btn_ViewDetails = new Button("", EnumToolbarButton.SEARCH_PRIMARY.getResource());
		this.btn_ViewDetails.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "view_details"));
		this.btn_ViewDetails.addClickListener((ClickListener & Serializable) e -> this.viewUserRequestDetails_clickHandler(e));
		this.btn_ViewDetails.addStyleName("toolbar-button");
		this.btn_ViewDetails.setEnabled(false);

		Button btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
		btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
		btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler(e));
		btn_Refresh.addStyleName("toolbar-button");

		HorizontalLayout buttonsBar = new HorizontalLayout();
		buttonsBar.addComponents(this.btn_ViewDetails, btn_Refresh);
		buttonsBar.setSpacing(true);
		buttonsBar.setMargin(false);
		buttonsBar.setHeight("35px");
		buttonsBar.setWidth("100%");
		buttonsBar.addStyleName("toolbar-layout");
		buttonsBar.setComponentAlignment(this.btn_ViewDetails, Alignment.BOTTOM_LEFT);
		buttonsBar.setComponentAlignment(btn_Refresh, Alignment.BOTTOM_RIGHT);

		HorizontalLayout toolBar = new HorizontalLayout();
		toolBar.setWidth("100%");
		toolBar.addComponents(buttonsBar);
		toolBar.addStyleName("filter-toolbar");
		toolBar.setExpandRatio(buttonsBar, 1F);

		// Grilla
		this.dgd_Requests = new BbrAdvancedGrid<>(this.getLocale());
		this.initializeGridColumns();
		this.dgd_Requests.addStyleName("report-grid");
		this.dgd_Requests.setSizeFull();
		this.dgd_Requests.setSelectionMode(SelectionMode.SINGLE);
		this.dgd_Requests.addItemClickListener((ItemClickListener<RequestReportDTO> & Serializable) e -> this.dgdItem_clickHandler(e));
		this.dgd_Requests.addSelectionListener((SelectionListener<RequestReportDTO> & Serializable) e -> this.selection_gridHandler(e));
		this.dgd_Requests.addSortListener(e -> dataGrid_sortHandler(e));
		this.updateButtons();
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addStyleName("report-layout");
		mainLayout.setSizeFull();
		mainLayout.setMargin(false);
		mainLayout.addComponents(toolBar, dgd_Requests);
		mainLayout.setExpandRatio(dgd_Requests, 1F);

		this.addComponents(mainLayout);
		this.updateSortOrderCriteria(null);
		this.reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		this.reportWork.addFunction(new Function<Object, RequestReportResultDTO>()
		{
			@Override
			public RequestReportResultDTO apply(Object t)
			{
				return executeService(UserRequestsManagement.this.getBbrUIParent());
			}
		});

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
			UserRequestsManagement senderReport = (UserRequestsManagement) sender;

			if (!senderReport.getBbrUIParent().hasAlertWindowOpen())
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
	private void selection_gridHandler(SelectionEvent<RequestReportDTO> e)
	{
		if (e.getAllSelectedItems().size() > 0)
		{
			this.userRequestSelected = e.getAllSelectedItems().iterator().next();
		}
		this.updateButtons();
	}

	@Override
	public void filterApplied_handler(BbrFilterEvent event)
	{
		try
		{
			if (event != null && event.getResultObject() != null)
			{
				selectedCriterion = (SearchCriterion) event.getResultObject();
				this.startWaiting();

				this.trackReport = true;
				this.executeBbrWork(reportWork);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
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

	private void viewUserRequestDetails_clickHandler(ClickEvent event)
	{
		this.viewUserRequestDetails();
	}

	private void dgdItem_clickHandler(ItemClick<RequestReportDTO> e)
	{
		if (e.getItem() != null)
		{
			this.userRequestSelected = e.getItem();
		}
		if (e.getMouseEventDetails().isDoubleClick())
		{
			this.viewUserRequestDetails();
		}
		this.updateButtons();
	}

	private void refreshReport_clickHandler(ClickEvent event)
	{
		this.startWaiting();

		this.trackReport = true;
		this.executeBbrWork(reportWork);
	}

	private void dataGrid_sortHandler(SortEvent<GridSortOrder<RequestReportDTO>> e)
	{
		if (e.isUserOriginated() && pagingManager.getTotalsPages() > 1)
		{
			this.updateSortOrderCriteria(e.getSortOrder());
			initParam.setPageNumber(DEFAULT_PAGE_NUMBER);

			this.trackReport = false;
			this.resetPageInfo = false;
			this.executeBbrWork(reportWork);
		}
	}
	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

	private void viewUserRequestDetails()
	{
		DetailsUserRequest winUserRequestDetails = new DetailsUserRequest(this.getBbrUIParent(), this.userRequestSelected.getNro());
		winUserRequestDetails.open(true, true, this);
	}

	private RequestReportInitParamDTO getInitParam()
	{
		RequestReportInitParamDTO initParam = new RequestReportInitParamDTO();
		initParam.setUserid(this.getBbrUIParent().getUser().getId());
		initParam.setRows(MAX_ROWS_NUMBER);
		initParam.setPageNumber(DEFAULT_PAGE_NUMBER);
		initParam.setPaginated(this.resetPageInfo);
		initParam.setFiltertype(this.selectedCriterion.getId());
		return initParam;
	}

	private void updateSortOrderCriteria(List<GridSortOrder<RequestReportDTO>> sortOrderList)
	{

		if (sortOrderList != null && !sortOrderList.isEmpty())
		{
			ArrayList<OrderCriteriaDTO> resultList = new ArrayList<>();
			for (GridSortOrder<RequestReportDTO> sorOrder : sortOrderList)
			{
				OrderCriteriaDTO order = new OrderCriteriaDTO();
				order.setPropertyname(sorOrder.getSorted().getId().toUpperCase());
				order.setAscending(sorOrder.getDirection() == SortDirection.DESCENDING);
				resultList.add(order);
			}
			resultList.toArray(new OrderCriteriaDTO[resultList.size()]);
		}
		else
		{
			OrderCriteriaDTO order = new OrderCriteriaDTO();
			order.setPropertyname(DEFAULT_SORT_FIELD.toUpperCase());
			order.setAscending(true);
			GridSortOrder<RequestReportDTO> sortOrder = new GridSortOrder<>(this.dgd_Requests.getColumn(DEFAULT_SORT_FIELD), SortDirection.DESCENDING);
			sortOrderList = new ArrayList<GridSortOrder<RequestReportDTO>>();
			sortOrderList.add(sortOrder);

			this.dgd_Requests.setSortOrder(sortOrderList);
		}
	}

	private void initializeGridColumns()
	{
		try
		{
			this.dgd_Requests.addCustomColumn(RequestReportDTO::getNro, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_request_number"), true)
					.setStyleGenerator(item -> BbrAlignment.CENTER.getValue()).setId(REQUEST_NUMBER);

			this.dgd_Requests
					.addCustomColumn(RequestReportDTO::getRequesterfullname, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_requester_full_name"), true)
					.setStyleGenerator(item -> BbrAlignment.LEFT.getValue()).setId(REQUESTER_FULL_NAME);

			this.dgd_Requests.addCustomColumn(RequestReportDTO::getCompany, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_enterprise"), true)
					.setStyleGenerator(item -> BbrAlignment.LEFT.getValue()).setId(USER_ENTERPRISE);

			this.dgd_Requests
					.addCustomColumn(RequestReportDTO::getFechasolicitud, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_request_date"), true)
					.setRenderer(new DateRenderer()).setStyleGenerator(item -> BbrAlignment.CENTER.getValue()).setId(REQUEST_DATE);

			this.dgd_Requests.addCustomColumn(RequestReportDTO::getAccion, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_action"), true)
					.setStyleGenerator(item -> BbrAlignment.CENTER.getValue()).setId(ACTION);

			this.dgd_Requests.addCustomColumn(RequestReportDTO::getEstado, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_state"), true)
					.setStyleGenerator(item -> BbrAlignment.CENTER.getValue()).setId(STATE);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private void updateButtons()
	{
		if (this.dgd_Requests != null)
		{
			boolean isEnable = this.dgd_Requests.getSelectedItems() != null && !this.dgd_Requests.getSelectedItems().isEmpty();
			this.btn_ViewDetails.setEnabled(isEnable);
		}
	}

	private RequestReportResultDTO executeService(BbrUI bbrUI)
	{
		RequestReportResultDTO result = null;

		this.initParam = this.getInitParam();
		if (this.initParam != null)
		{
			try
			{
				// Start Tracking
				this.getTimingMngr().startTimer();
				result = EJBFactory.getUserEJBFactory().getRequestManagerLocal(bbrUI).getRequestsByFilterType(this.initParam);
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
		}
		return result;
	}

	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{
		String errordescription = "";

		UserRequestsManagement senderReport = (UserRequestsManagement) sender;

		senderReport.updateButtons();
		if (result != null)
		{
			RequestReportResultDTO reportResult = (RequestReportResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				ListDataProvider<RequestReportDTO> dataprovider = new ListDataProvider<>(Arrays.asList(reportResult.getRequests()));

				senderReport.dgd_Requests.setDataProvider(dataprovider);

				// End Tracking
				if (senderReport.trackReport == true)
				{
					senderReport.trackEvent(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription());
				}

				if (reportResult.getRequests().length == 0)
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
