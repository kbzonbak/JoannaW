package bbr.b2b.portal.components.modules.users.management.users;

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
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.BbrMessagesBoxUtils;
import bbr.b2b.portal.classes.utils.users.UserManagerUtils;
import bbr.b2b.portal.components.basics.BbrHLabelContainerGenerator;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.UserDataDTO;
import bbr.b2b.users.report.classes.UserReportDataDTO;
import bbr.b2b.users.report.classes.UserReportInitParamDTO;
import cl.bbr.core.classes.basics.BbrPage;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.paging.BbrPagingManager;
import cl.bbr.core.interfaces.EntityEditor;

public class CopyProfiles extends BbrWindow implements EntityEditor<UserDataDTO>, BbrWorkExecutor
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants
	private static final long				serialVersionUID			= 7912774319198883632L;
	private static final String				USERNAME					= "username";
	private static final String				LASTNAME					= "lastname";
	private static final String				EMAIL						= "email";
	private static final String				PERSON_ID					= "person_id";
	private static final String				STATE						= "state";
	private static final String				USER_TYPE					= "user_type";
	private static final String				COMPANY_CODE				= "company_code";
	private static final String				COMPANY_NAME				= "company_name";
	private static final String				copyProfilesTriggerObject	= "copyprofilestriggerobject";
	// Components
	private BbrHLabelContainerGenerator		txt_UserName				= null;
	private BbrHLabelContainerGenerator		txt_UserLastName			= null;
	private BbrHLabelContainerGenerator		txt_UserType				= null;
	private BbrHLabelContainerGenerator		txt_Email					= null;
	private BbrHLabelContainerGenerator		txt_CompanyCode				= null;
	private BbrHLabelContainerGenerator		txt_Enterprise				= null;
	private BbrPagingManager				pagingManager				= null;
	private BbrAdvancedGrid<UserDataDTO>	dgd_Users					= null;
	private Button							btn_Confirm					= null;
	// Variables
	private final String					LOG_ID_FIELD				= "logid";
	private final String					DEFAULT_SORT_FIELD			= USERNAME;
	private final int						DEFAULT_PAGE_NUMBER			= 1;
	private final int						MAX_ROWS_NUMBER				= 50;
	private Long							companyTypeSelected			= null;
	private int								filterTypeSelected;
	private UserReportInitParamDTO			initParams					= null;
	private Boolean							trackReport					= true;
	private Boolean							resetPageInfo				= true;
	private BbrWork<UserReportDataDTO>		reportWork					= null;
	private BbrWork<BaseResultDTO>			copyProfilesWork			= null;
	private OrderCriteriaDTO[]				usersOrderCriteria			= null;
	private UserDataDTO						item						= null;
	private UserDataDTO						selectedFinalUser			= null;
	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================
	public CopyProfiles(BbrUI parent, UserDataDTO item, int filterTypeSelected, Long companyTypeSelected)
	{
		super(parent);
		this.setItem(item);
		this.filterTypeSelected = filterTypeSelected;
		this.companyTypeSelected = companyTypeSelected;
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
		this.txt_UserName = new BbrHLabelContainerGenerator(I18NManager.getI18NString(this.getBbrUIParent(),
				BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_name"),
				this.item.getUsername(),
				"100%");
		this.txt_UserLastName = new BbrHLabelContainerGenerator(I18NManager.getI18NString(this.getBbrUIParent(),
				BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_lastname"),
				this.item.getLastname(),
				"100%");
		this.txt_Enterprise = new BbrHLabelContainerGenerator(I18NManager.getI18NString(this.getBbrUIParent(),
				BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_enterprise"),
				this.item.getCompanyname(),
				"100%");

		VerticalLayout firstColumn = new VerticalLayout(this.txt_UserName, this.txt_UserLastName, this.txt_Enterprise);
		firstColumn.setSizeFull();
		firstColumn.setMargin(false);

		this.txt_Email = new BbrHLabelContainerGenerator(I18NManager.getI18NString(this.getBbrUIParent(),
				BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_email"),
				this.item.getEmail(),
				"100%");
		this.txt_UserType = new BbrHLabelContainerGenerator(I18NManager.getI18NString(this.getBbrUIParent(),
				BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_type"),
				this.item.getUsertype(),
				"100%");
		this.txt_CompanyCode = new BbrHLabelContainerGenerator(I18NManager.getI18NString(this.getBbrUIParent(),
				BbrUtilsResources.RES_MODULES_MANAGEMENT, "company_code"),
				this.item.getCompanycode(),
				"100%");

		VerticalLayout secondColumn = new VerticalLayout(this.txt_Email, this.txt_UserType, this.txt_CompanyCode);
		secondColumn.setSizeFull();
		secondColumn.setMargin(false);

		HorizontalLayout userData = new HorizontalLayout(firstColumn, secondColumn);
		userData.setWidth("100%");
		userData.setMargin(false);
		userData.setStyleName("margin-bottom-20");

		// Paging Manager
		this.pagingManager = new BbrPagingManager();
		this.pagingManager.setLocale(this.getBbrUIParent().getUser().getLocale());
		this.pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> pagingChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);

		// Grilla
		this.dgd_Users = new BbrAdvancedGrid<>(this.getBbrUIParent().getUser().getLocale());
		this.dgd_Users.addSortListener(e -> dataGrid_sortHandler(e));
		this.dgd_Users.setHasRowsDetails(true);
		this.initializeGridColumns();

		this.dgd_Users.addStyleName("report-grid");
		this.dgd_Users.setSizeFull();
		this.dgd_Users.setSelectionMode(SelectionMode.SINGLE);
		this.dgd_Users.setPagingManager(pagingManager, this.LOG_ID_FIELD);
		this.dgd_Users.addSelectionListener((SelectionListener<UserDataDTO> & Serializable) e -> this.item_SelectionHandler(e));

		this.btn_Confirm = new Button(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "confirm"));
		this.btn_Confirm.setStyleName("primary");
		this.btn_Confirm.addStyleName("btn-login");
		this.btn_Confirm.setWidth("150px");
		this.btn_Confirm.addClickListener((ClickListener & Serializable) e -> this.confirmation_clickHandler(e));
		this.btn_Confirm.setEnabled(false);

		Button btn_Close = new Button(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "close"));
		btn_Close.addStyleName("btn-generic");
		btn_Close.setWidth("150px");
		btn_Close.addClickListener((ClickListener & Serializable) e -> this.close_ClickHandler());
		HorizontalLayout buttonsPanel = new HorizontalLayout(btn_Confirm, btn_Close);
		buttonsPanel.setComponentAlignment(btn_Confirm, Alignment.MIDDLE_RIGHT);
		buttonsPanel.setWidth("100%");
		buttonsPanel.setHeight("30px");
		buttonsPanel.setSpacing(true);

		Label label = new Label(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "win_title_copy_profiles"));
		label.setWidth("100%");
		// Main Layout
		VerticalLayout mainLayout = new VerticalLayout(userData, this.dgd_Users, pagingManager, buttonsPanel);
		mainLayout.setSizeFull();
		mainLayout.setComponentAlignment(buttonsPanel, Alignment.MIDDLE_CENTER);
		mainLayout.setExpandRatio(this.dgd_Users, 1F);
		mainLayout.setSpacing(true);
		mainLayout.addStyleName("bbr-margin-windows");

		// Main Windows
		this.setWidth("950px");
		this.setHeight("550px");
		this.setResizable(true);
		this.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "win_title_copy_profiles"));
		this.setContent(mainLayout);
		this.updateUserSortOrderCriteria(null);

		this.reportWork = new BbrWork<UserReportDataDTO>(this, this.getBbrUIParent(), this);
		this.reportWork.addFunction(new Function<Object, UserReportDataDTO>()
		{
			@Override
			public UserReportDataDTO apply(Object t)
			{
				return executeService(CopyProfiles.this.getBbrUIParent());
			}
		});

		this.initParams = this.updateInitParams();
		this.startWaiting();
		this.executeBbrWork(this.reportWork);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================
	@Override
	public void setItem(UserDataDTO item)
	{
		this.item = item;
	}

	@Override
	public UserDataDTO getItem()
	{
		return item;
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		if (result != null)
		{
			if (result instanceof UserReportDataDTO)
			{
				this.doUpdateReport(result, sender);
			}
			else if (triggerObject == CopyProfiles.copyProfilesTriggerObject)
			{
				this.updateCopyProfiles(result, sender);
			}
		}
		else
		{
			CopyProfiles senderReport = (CopyProfiles) sender;

			if(!senderReport.getBbrUIParent().hasAlertWindowOpen())
			senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));

			senderReport.stopWaiting();
		}
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	public void filterApplied_handler(BbrFilterEvent event)
	{
		if ((event != null) && (event.getResultObject() != null))
		{
			initParams = (UserReportInitParamDTO) event.getResultObject();
		}
	}

	private void pagingChange_Listener(BbrPagingEvent e)
	{
		if ((e != null) && e.getEventType().equals(BbrPagingEvent.PAGE_CHANGED) && (e.getResultObject() != null))
		{
			this.startWaiting();

			initParams.setPageNumber(e.getResultObject().getCurrentPage());

			this.trackReport = false;
			this.resetPageInfo = false;
			this.executeBbrWork(reportWork);
		}
	}

	private void dataGrid_sortHandler(SortEvent<GridSortOrder<UserDataDTO>> e)
	{
		if (e.isUserOriginated() && pagingManager.getTotalsPages() > 1)
		{
			this.updateUserSortOrderCriteria(e.getSortOrder());
			initParams.setPageNumber(DEFAULT_PAGE_NUMBER);

			this.trackReport = false;
			this.resetPageInfo = false;
			this.executeBbrWork(reportWork);
		}
	}

	private void confirmation_clickHandler(ClickEvent event)
	{
		if (this.dgd_Users.getSelectedItems() != null && this.dgd_Users.getSelectedItems().size() > 0)
		{
			String userFullText = UserManagerUtils.getValidFullnameOrEmailByUser(selectedFinalUser);
			String alertMessage = I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "sure_copy_profiles", 
					item.getUsername(),
					item.getLastname(), 
					userFullText, 
					"");

			BbrMessagesBoxUtils.showConfirmationMessage(this.getBbrUIParent(),
					I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_question"),
					alertMessage,
					(Runnable & Serializable) () -> this.copyProfiles());
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
					I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "select_at_least_one"));
		}
	}

	private void close_ClickHandler()
	{
		this.close();
	}

	private void item_SelectionHandler(SelectionEvent<UserDataDTO> e)
	{
		if (this.dgd_Users.getSelectedItems() != null && this.dgd_Users.getSelectedItems().size() > 0)
		{
			ArrayList<UserDataDTO> items = new ArrayList<>(this.dgd_Users.getSelectedItems());
			if (items.size() == 1)
			{
				this.selectedFinalUser = items.get(0);
				this.btn_Confirm.setEnabled(true);
			}
			else
			{
				this.btn_Confirm.setEnabled(false);
			}
		}
		else
		{
			this.btn_Confirm.setEnabled(false);
		}
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
	private UserReportInitParamDTO updateInitParams()
	{
		if (this.initParams == null)
		{
			this.initParams = new UserReportInitParamDTO();
			this.initParams.setPageNumber(this.DEFAULT_PAGE_NUMBER);
			this.initParams.setRows(this.MAX_ROWS_NUMBER);
			this.initParams.setFilterType(this.filterTypeSelected);
			this.initParams.setCompanyTypeId(this.companyTypeSelected);
			this.trackReport = true;
			this.resetPageInfo = true;
		}
		return initParams;
	}

	private void initializeGridColumns()
	{
		this.dgd_Users.addColumn(UserDataDTO::getUsername).setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_name"))
				.setId(USERNAME);
		this.dgd_Users.addColumn(UserDataDTO::getLastname)
				.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_lastname")).setId(LASTNAME);
		this.dgd_Users.addColumn(UserDataDTO::getEmail).setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_email"))
				.setId(EMAIL);
		this.dgd_Users.setFrozenColumnCount(3);
		this.dgd_Users.addColumn(UserDataDTO::getPersonalid)
				.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_person_id")).setId(PERSON_ID);
		this.dgd_Users.addColumn(user -> stateDescription_function(user))
				.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_state")).setId(STATE);
		this.dgd_Users.addColumn(UserDataDTO::getUsertype).setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_type"))
				.setId(USER_TYPE);
		this.dgd_Users.addColumn(UserDataDTO::getCompanycode)
				.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "company_code")).setId(COMPANY_CODE);
		this.dgd_Users.addColumn(UserDataDTO::getCompanyname)
				.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "company_name")).setId(COMPANY_NAME);
	}

	private void updateUserSortOrderCriteria(List<GridSortOrder<UserDataDTO>> sortOrderList)
	{
		if (sortOrderList != null && !sortOrderList.isEmpty())
		{
			ArrayList<OrderCriteriaDTO> resultList = new ArrayList<>();
			for (GridSortOrder<UserDataDTO> sorOrder : sortOrderList)
			{
				OrderCriteriaDTO order = new OrderCriteriaDTO();
				order.setPropertyname(sorOrder.getSorted().getId().toUpperCase());
				order.setAscending(sorOrder.getDirection() == SortDirection.ASCENDING);
				resultList.add(order);
			}
			this.usersOrderCriteria = resultList.toArray(new OrderCriteriaDTO[resultList.size()]);
		}
		else
		{
			OrderCriteriaDTO order = new OrderCriteriaDTO();
			order.setPropertyname(DEFAULT_SORT_FIELD.toUpperCase());
			order.setAscending(true);
			this.usersOrderCriteria = new OrderCriteriaDTO[] { order };

			GridSortOrder<UserDataDTO> sortOrder = new GridSortOrder<>(this.dgd_Users.getColumn(this.DEFAULT_SORT_FIELD), SortDirection.ASCENDING);
			sortOrderList = new ArrayList<GridSortOrder<UserDataDTO>>();
			sortOrderList.add(sortOrder);

			this.dgd_Users.setSortOrder(sortOrderList);
		}
	}

	private String stateDescription_function(UserDataDTO user)
	{
		String result = "";
		if (user != null)
		{
			result = (user.isStateindicator() == true) ? I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "active")
					: I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "inactive");
		}

		return result;
	}

	private UserReportDataDTO executeService(BbrUI bbrUI)
	{
		UserReportDataDTO result = null;
		if (this.initParams != null)
		{
			try
			{
				// Start Tracking
				result = EJBFactory.getUserEJBFactory().getUserReportManagerLocal(bbrUI).getUserReport(this.initParams, bbrUI.getUser().getTypeID(),
						bbrUI.getUser().getEnterpriseId(), true, this.usersOrderCriteria);
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

	private void copyProfiles()
	{
		this.copyProfilesWork = new BbrWork<>(this, this.getBbrUIParent(), CopyProfiles.copyProfilesTriggerObject);
		this.copyProfilesWork.addFunction(new Function<Object, BaseResultDTO>()
		{
			@Override
			public BaseResultDTO apply(Object t)
			{
				return executeCopyProfilesService(CopyProfiles.this.getBbrUIParent());
			}
		});
		this.executeBbrWork(copyProfilesWork);
		this.startWaiting();
	}

	private BaseResultDTO executeCopyProfilesService(BbrUI parentUI)
	{
		BaseResultDTO result = null;
		try
		{
			result = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().addUserProfileLikeUser(this.selectedFinalUser.getId(), item.getId());
		}
		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), parentUI);
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

	private void updateCopyProfiles(Object result, BbrWorkExecutor sender)
	{
		CopyProfiles senderResult = (CopyProfiles) sender;
		BaseResultDTO reportResult = (BaseResultDTO) result;

		BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderResult.getBbrUIParent(), !senderResult.getBbrUIParent().hasAlertWindowOpen());

		if (!error.hasError())
		{
			
			String userFullText = UserManagerUtils.getValidFullnameOrEmailByUser(selectedFinalUser);
					
			// Operación Exitosa.
			String resultMessage = I18NManager.getI18NString(senderResult.getBbrUIParent(), BbrUtilsResources.RES_MODULES_MANAGEMENT, "user_successfully_copied_profiles",
					senderResult.item.getUsername(),
					senderResult.item.getLastname(),
					userFullText,
					"");

			senderResult.showInfoMessage(I18NManager.getI18NString(senderResult.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"), resultMessage);
		}

		this.stopWaiting();
	}

	private UserDataDTO[] removeElements(UserDataDTO[] input, UserDataDTO deleteMe)
	{
		List<UserDataDTO> result = new ArrayList<UserDataDTO>();
		for (UserDataDTO item : input)
		{
			if (!deleteMe.getId().equals(item.getId()))
			{
				result.add(item);
			}
		}

		UserDataDTO[] stockArr = new UserDataDTO[result.size()];
		stockArr = result.toArray(stockArr);
		return stockArr;
	}

	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{
		String errordescription = "";

		CopyProfiles senderResult = (CopyProfiles) sender;
		if (result != null)
		{
			UserReportDataDTO reportResult = (UserReportDataDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderResult.getBbrUIParent(), !senderResult.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				UserDataDTO[] userDataDTOs = senderResult.removeElements(reportResult.getUserReport(), senderResult.item);
				ListDataProvider<UserDataDTO> dataprovider = new ListDataProvider<>(Arrays.asList(userDataDTOs));
				senderResult.dgd_Users.setDataProvider(dataprovider, senderResult.resetPageInfo);
				if (senderResult.resetPageInfo)
				{
					PageInfoDTO pageInfo = reportResult.getPageInfoDTO();

					BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows() , pageInfo.getTotalrows()> 0 ? pageInfo.getTotalrows() -1 :pageInfo.getTotalrows());
					senderResult.pagingManager.setPages(bbrPage, senderResult.resetPageInfo);

				}

			}
			else
			{
				errordescription = BbrUtils.getInstance().substitute("{0} - {1} - Internal Error", error.getErrorCode(), error.getErrorMessage());
			}
		}

		if (errordescription.length() > 0 && senderResult.trackReport == true)
		{
			// Track Error
			// senderResult.trackError(TrackingConstants.REPORT_VIEW,
			// senderResult.getBbrFilterDescription(),
			// errordescription, null, this);
		}

		this.stopWaiting();
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
