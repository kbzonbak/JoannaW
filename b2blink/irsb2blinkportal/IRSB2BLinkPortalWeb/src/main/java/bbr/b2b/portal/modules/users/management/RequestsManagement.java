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
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.portal.classes.basics.OptionalCommentAlertDTO;
import bbr.b2b.portal.classes.beans.SessionBean;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.BbrMessagesBoxUtils;
import bbr.b2b.portal.classes.utils.users.UserManagerUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.basics.OptionalCommentAlert;
import bbr.b2b.portal.constants.BbrUtilsConstants;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.RequestAdminRTActionInitParamDTO;
import bbr.b2b.users.report.classes.RequestAdminRTReportResultDTO;
import bbr.b2b.users.report.classes.RequestReportInitParamDTO;
import bbr.b2b.users.report.classes.RequestRetailReportDTO;
import cl.bbr.core.classes.basics.BbrPage;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.constants.TrackingConstants;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
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

public class RequestsManagement extends BbrModule implements BbrWorkExecutor
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************

	private static final long						serialVersionUID	= 7027502352762624910L;

	private static final String						REQUEST_NUMBER		= "request_number";
	private static final String						REQUEST_DATE		= "request_date";
	private static final String						USER_ENTERPRISE		= "user_enterprise";
	private static final String						REQUESTED_PROFILE	= "requested_profile";
	private static final String						REQUESTER_FULL_NAME	= "requester_full_name";
	private static final String						ADM_ENTERPRISE		= "adm_enterprise";
	private static final String						ACTION				= "action";
	private static final String						STATE				= "state";

	private final String							DEFAULT_SORT_FIELD	= REQUEST_NUMBER;

	private BbrAdvancedGrid<RequestRetailReportDTO>	dgd_Requests		= null;
	private Button									btn_Approve			= null;
	private Button									btn_Reject			= null;

	private final int								DEFAULT_PAGE_NUMBER	= 1;
	private final int								MAX_ROWS_NUMBER		= 100;

	private RequestReportInitParamDTO				initParam			= null;
	private BbrWork<RequestAdminRTReportResultDTO>	reportWork			= null;

	private Boolean									trackReport			= true;
	private BbrPagingManager						pagingManager		= null;
	private Boolean									resetPageInfo		= true;
	private RequestRetailReportDTO					userRequestSelected	= null;
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	public RequestsManagement(BbrUI bbrUIParent)
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

		// Paging Manager
		this.pagingManager = new BbrPagingManager();
		this.pagingManager.setLocale(this.getUser().getLocale());
		this.pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> this.pagingChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);

		// Reporte
		// Barra de Herramientas
		this.btn_Approve = new Button("",
				EnumToolbarButton.ACTIVATE_ALTERNATIVE.getResource());
		this.btn_Approve.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_request_approve"));
		this.btn_Approve.addClickListener((ClickListener & Serializable) e -> this.viewApprove_clickHandler(e));
		this.btn_Approve.addStyleName("toolbar-button");
		this.btn_Approve.setEnabled(false);

		this.btn_Reject = new Button("", EnumToolbarButton.DELETE.getResource());
		this.btn_Reject.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_request_reject"));
		this.btn_Reject.addClickListener((ClickListener & Serializable) e -> this.viewReject_clickHandler(e));
		this.btn_Reject.addStyleName("toolbar-button");
		this.btn_Reject.setEnabled(false);

		Button btn_Refresh = new Button("",
				EnumToolbarButton.REFRESH.getResource());
		btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
		btn_Refresh.addClickListener((ClickListener & Serializable) e -> refreshReport_clickHandler());
		btn_Refresh.addStyleName("toolbar-button");

		HorizontalLayout leftButtonsBar = new HorizontalLayout();
		leftButtonsBar.addComponents(this.btn_Approve, this.btn_Reject);
		leftButtonsBar.setSpacing(true);
		leftButtonsBar.setMargin(false);
		leftButtonsBar.setHeight("30px");
		leftButtonsBar.addStyleName("toolbar-layout");
		leftButtonsBar.setComponentAlignment(this.btn_Approve, Alignment.MIDDLE_LEFT);
		leftButtonsBar.setComponentAlignment(this.btn_Reject, Alignment.MIDDLE_LEFT);

		HorizontalLayout rightButtonsBar = new HorizontalLayout();
		rightButtonsBar.setSpacing(true);
		rightButtonsBar.setHeight("35px");
		rightButtonsBar.addComponent(btn_Refresh);
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
		this.dgd_Requests = new BbrAdvancedGrid<>(this.getLocale());
		this.initializeGridColumns();
		this.dgd_Requests.addStyleName("report-grid");
		this.dgd_Requests.setSizeFull();
		this.dgd_Requests.setSelectionMode(SelectionMode.SINGLE);
		this.dgd_Requests.addSelectionListener((SelectionListener<RequestRetailReportDTO> & Serializable) e -> this.selection_gridHandler(e));
		this.dgd_Requests.addSortListener((SortListener<GridSortOrder<RequestRetailReportDTO>> & Serializable) e -> this.dataGrid_sortHandler(e));
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addStyleName("report-layout");
		mainLayout.setSizeFull();
		mainLayout.setMargin(false);
		mainLayout.addComponents(toolBar, dgd_Requests);
		mainLayout.setExpandRatio(dgd_Requests, 1F);

		this.addComponents(mainLayout);
		this.updateSortOrderCriteria(null);
		this.reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		this.reportWork.addFunction(new Function<Object, RequestAdminRTReportResultDTO>()
		{
			@Override
			public RequestAdminRTReportResultDTO apply(Object t)
			{
				return executeService(RequestsManagement.this.getBbrUIParent());
			}
		});
		this.startWaiting();
		this.resetPageInfo = true;
		this.executeBbrWork(this.reportWork);
	}

	private void selection_gridHandler(SelectionEvent<RequestRetailReportDTO> e)
	{
		if (e.getAllSelectedItems().size() > 0)
		{
			this.userRequestSelected = e.getAllSelectedItems().iterator().next();
		}
		this.updateButtons();
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		if (result != null)
		{
			this.doUpdateReport(result, sender);
		}
		else
		{
			RequestsManagement senderReport = (RequestsManagement) sender;

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

	private void dataGrid_sortHandler(SortEvent<GridSortOrder<RequestRetailReportDTO>> e)
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

	private void viewApprove_clickHandler(ClickEvent event)
	{
		this.viewApprove();
	}

	private void viewReject_clickHandler(ClickEvent event)
	{
		this.viewReject();
	}

	private void viewApprove()
	{
		String winCaption = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_request_win_approve", this.userRequestSelected.getNro().toString());

		OptionalCommentAlert approveUserRequestCtrl = new OptionalCommentAlert(this.getBbrUIParent(),
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "win_title_info"),
				winCaption,
				this.userRequestSelected.getNro(),
				true);

		approveUserRequestCtrl.addBbrEventListener((BbrEventListener & Serializable) e -> this.getOptionalCommentAlertResponseApprove(e, approveUserRequestCtrl));
		approveUserRequestCtrl.open(true, true, this);
	}

	private void viewReject()
	{
		String winCaption = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_request_win_reject", this.userRequestSelected.getNro().toString());

		OptionalCommentAlert approveUserRequestCtrl = new OptionalCommentAlert(this.getBbrUIParent(),
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "win_title_info"),
				winCaption,
				this.userRequestSelected.getNro(),
				false);

		approveUserRequestCtrl.addBbrEventListener((BbrEventListener & Serializable) e -> this.getOptionalCommentAlertResponseReject(e));
		approveUserRequestCtrl.open(true, true, this);
	}

	private void refreshReport_clickHandler()
	{
		this.startWaiting();
		this.trackReport = true;
		this.executeBbrWork(reportWork);
	}

	private void getOptionalCommentAlertResponseApprove(BbrEvent result, OptionalCommentAlert optionalCommentAlert)
	{
		OptionalCommentAlertDTO resultDTO = (OptionalCommentAlertDTO) result.getResultObject();

		if (result.getEventType().equals(OptionalCommentAlertDTO.BTN_ACCEPT))
		{
			optionalCommentAlert.close();
			this.doApproveUserRequest(resultDTO);
		}
		else if (result.getEventType().equals(OptionalCommentAlertDTO.BTN_CANCEL))
		{
			if (resultDTO.getComment().length() > 0)
			{
				this.viewApproveConfirmationWindow(resultDTO, optionalCommentAlert);
			}
			else
			{
				optionalCommentAlert.close();
			}
		}
	}

	private void viewApproveConfirmationWindow(OptionalCommentAlertDTO resultDTO, OptionalCommentAlert optionalCommentAlert)
	{

		if (this.dgd_Requests.getSelectedItems() != null && this.dgd_Requests.getSelectedItems().size() > 0)
		{
			ArrayList<RequestRetailReportDTO> items = new ArrayList<>(this.dgd_Requests.getSelectedItems());

			String alertMessage = (items.size() == 1) ? I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "sure_cancel")
					: I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "sure_cancel");

			BbrMessagesBoxUtils.showConfirmationMessage(this.getBbrUIParent(),
					I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_question"),
					alertMessage,
					(Runnable & Serializable) () -> optionalCommentAlert.close());

		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "select_at_least_one"));
		}
	}

	private void getOptionalCommentAlertResponseReject(BbrEvent result)
	{
		OptionalCommentAlertDTO resultDTO = (OptionalCommentAlertDTO) result.getResultObject();
		this.doRejectUserRequest(resultDTO);
	}

	private void doRejectUserRequest(OptionalCommentAlertDTO resultDTO)
	{
		String errorDescription = "";
		RequestAdminRTActionInitParamDTO addRequestAdminPVInitParam = new RequestAdminRTActionInitParamDTO();

		try
		{
			if (resultDTO != null)
			{
				addRequestAdminPVInitParam = this.getRequestAdminRTActionInitParam();

				addRequestAdminPVInitParam.setComment(resultDTO.getComment());

				BaseResultDTO result = EJBFactory.getUserEJBFactory().getRequestManagerLocal(this.getBbrUIParent()).rejectRequestByAdminRT(addRequestAdminPVInitParam);

				BaseResultDTO reportResult = (BaseResultDTO) result;

				BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), true);

				if (!error.hasError())
				{
					errorDescription += I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_request_win_rejected",
							this.userRequestSelected.getNro().toString());
					this.getBbrUIParent().showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"), errorDescription);
					this.refreshReport_clickHandler();
				}
				else
				{
					errorDescription = BbrUtils.getInstance().substitute("{0} - {1} - Internal Error", error.getErrorCode(), error.getErrorMessage());
				}
			}

		}
		catch (Exception e) // Error no controlado
		{
			errorDescription = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
		}
	}

	private void doApproveUserRequest(OptionalCommentAlertDTO resultDTO)
	{
		String errorDescription = "";
		RequestAdminRTActionInitParamDTO addRequestAdminPVInitParam = new RequestAdminRTActionInitParamDTO();

		try
		{
			if (resultDTO != null)
			{
				addRequestAdminPVInitParam = this.getRequestAdminRTActionInitParam();

				addRequestAdminPVInitParam.setComment(resultDTO.getComment());

				BaseResultDTO result = EJBFactory.getUserEJBFactory().getRequestManagerLocal(this.getBbrUIParent()).approveRequestByAdminRT(addRequestAdminPVInitParam);

				BaseResultDTO reportResult = (BaseResultDTO) result;

				BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), true);

				if (!error.hasError())
				{
					errorDescription += I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_request_win_approved",
							this.userRequestSelected.getNro().toString());
					this.getBbrUIParent().showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"), errorDescription);
					this.refreshReport_clickHandler();
				}
				else
				{
					errorDescription = BbrUtils.getInstance().substitute("{0} - {1} - Internal Error", error.getErrorCode(), error.getErrorMessage());
				}
			}

		}
		catch (Exception e) // Error no controlado
		{
			errorDescription = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
		}
	}
	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

	private RequestReportInitParamDTO getInitParam()
	{
		RequestReportInitParamDTO initParam = new RequestReportInitParamDTO();
		initParam.setUserid(this.getBbrUIParent().getUser().getId());
		initParam.setRows(MAX_ROWS_NUMBER);
		initParam.setPageNumber(DEFAULT_PAGE_NUMBER);
		initParam.setPaginated(true);
		return initParam;
	}

	private RequestAdminRTActionInitParamDTO getRequestAdminRTActionInitParam()
	{
		RequestAdminRTActionInitParamDTO initParam = new RequestAdminRTActionInitParamDTO();
		initParam.setExternalRetail(UserManagerUtils.getIsExternalRetail());
		initParam.setAdminid(this.getBbrUIParent().getUser().getId());
		initParam.setPrkey(this.userRequestSelected.getPrkey());
		initParam.setRequestId(this.userRequestSelected.getNro());
		SessionBean sessionBean = (SessionBean) this.getBbrUIParent().getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
		initParam.setAccessToken(sessionBean.getAccessToken());
		return initParam;
	}

	private void initializeGridColumns()
	{
		try
		{
			this.dgd_Requests
					.addCustomColumn(RequestRetailReportDTO::getNro, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_request_number"), true)
					.setStyleGenerator(item -> BbrAlignment.CENTER.getValue()).setId(REQUEST_NUMBER);

			this.dgd_Requests
					.addCustomColumn(RequestRetailReportDTO::getUserfullname, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_requester_full_name"), true)
					.setStyleGenerator(item -> BbrAlignment.LEFT.getValue()).setId(REQUESTER_FULL_NAME);

			this.dgd_Requests
					.addCustomColumn(RequestRetailReportDTO::getCompany, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_enterprise"), true)
					.setStyleGenerator(item -> BbrAlignment.LEFT.getValue()).setId(USER_ENTERPRISE);

			this.dgd_Requests.addCustomColumn(RequestRetailReportDTO::getPerfilsolicitado,
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_requested_profile"), true).setStyleGenerator(item -> BbrAlignment.LEFT.getValue())
					.setId(REQUESTED_PROFILE);

			this.dgd_Requests
					.addCustomColumn(RequestRetailReportDTO::getFechasolicitud, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_request_date"), true)
					.setRenderer(new DateRenderer()).setStyleGenerator(item -> BbrAlignment.CENTER.getValue()).setId(REQUEST_DATE);

			this.dgd_Requests.addCustomColumn(RequestRetailReportDTO::getAccion, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_action"), true)
					.setStyleGenerator(item -> BbrAlignment.CENTER.getValue()).setId(ACTION);

			this.dgd_Requests
					.addCustomColumn(RequestRetailReportDTO::getAdminpvfullname, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "col_adm_enterprise"), true)
					.setStyleGenerator(item -> BbrAlignment.LEFT.getValue()).setId(ADM_ENTERPRISE);

			this.dgd_Requests.addCustomColumn(RequestRetailReportDTO::getEstado, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_state"), true)
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
			this.btn_Approve.setEnabled(isEnable);
			this.btn_Reject.setEnabled(isEnable);
		}
	}

	private RequestAdminRTReportResultDTO executeService(BbrUI bbrUI)
	{
		RequestAdminRTReportResultDTO result = new RequestAdminRTReportResultDTO();
		this.initParam = this.getInitParam();
		if (this.initParam != null)
		{

			try
			{
				// Start Tracking
				this.getTimingMngr().startTimer();
				result = EJBFactory.getUserEJBFactory().getRequestManagerLocal(bbrUI).getRequestReportForAdminRT(this.initParam);
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

		RequestsManagement senderReport = (RequestsManagement) sender;

		senderReport.updateButtons();
		if (result != null)
		{
			RequestAdminRTReportResultDTO reportResult = (RequestAdminRTReportResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				ListDataProvider<RequestRetailReportDTO> dataprovider = new ListDataProvider<>(Arrays.asList(reportResult.getRequests()));

				senderReport.dgd_Requests.setDataProvider(dataprovider);

				if (resetPageInfo)
				{
					PageInfoDTO pageInfo = reportResult.getPageInfoDTO();

					BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
					senderReport.pagingManager.setPages(bbrPage, senderReport.resetPageInfo);

				}
				if (reportResult.getRequests().length == 0)
				{
					if (!senderReport.getBbrUIParent().hasAlertWindowOpen())
					{
						BbrMessagesBoxUtils.showConfirmationMessage(senderReport.getBbrUIParent(), I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
								I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "empty_filter_question_request_management"),
								null, () -> senderReport.refreshReport_clickHandler(), null);
					}
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

	private void updateSortOrderCriteria(List<GridSortOrder<RequestRetailReportDTO>> sortOrderList)
	{
		if (sortOrderList != null && !sortOrderList.isEmpty())
		{
			ArrayList<OrderCriteriaDTO> resultList = new ArrayList<>();
			for (GridSortOrder<RequestRetailReportDTO> sorOrder : sortOrderList)
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
			GridSortOrder<RequestRetailReportDTO> sortOrder = new GridSortOrder<>(this.dgd_Requests.getColumn(DEFAULT_SORT_FIELD), SortDirection.DESCENDING);
			sortOrderList = new ArrayList<GridSortOrder<RequestRetailReportDTO>>();
			sortOrderList.add(sortOrder);

			this.dgd_Requests.setSortOrder(sortOrderList);
		}
	}

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
