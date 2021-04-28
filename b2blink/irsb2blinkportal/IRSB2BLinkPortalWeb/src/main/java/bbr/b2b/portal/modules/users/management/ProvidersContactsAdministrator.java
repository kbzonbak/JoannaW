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
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.ItemClickListener;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.filters.management.ProvidersContactsAdministratorFilter;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.ContactPhoneDTO;
import bbr.b2b.users.report.classes.ContactProviderPositionDTO;
import bbr.b2b.users.report.classes.ProviderContactReportDataDTO;
import bbr.b2b.users.report.classes.ProviderContactReportInitParamDTO;
import bbr.b2b.users.report.classes.ProviderContactReportResultDTO;
import cl.bbr.core.classes.basics.BbrPage;
import cl.bbr.core.classes.constants.DownloadFormats;
import cl.bbr.core.classes.constants.TrackingConstants;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.paging.BbrPagingManager;

public class ProvidersContactsAdministrator extends BbrModule implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants
	private static final long								serialVersionUID		= -8302542039677601479L;
	private static final String								PROVIDERCODE			= "providercode";
	private static final String								SOCIALREASON			= "socialreason";
	private static final String								CONTACTNAME				= "contactname";
	private static final String								POSITIONS				= "positions";
	private static final String								PHONES					= "phones";
	private static final String								EMAIL					= "email";
	private static final String								UPDATEDBY				= "updatedby";
	private static final String								LASTUPDATE				= "lastupdate";
	private final String									SEPARATOR				= " / ";
	// Components
	private VerticalLayout									mainLayout;
	private BbrAdvancedGrid<ProviderContactReportDataDTO>	dgd_ProvidersContacts	= null;
	private BbrPagingManager								pagingManager			= null;
	private Button											btn_DownloadExcel		= null;
	private Button											btn_DownloadBarCodes	= null;
	private Button											btn_DownloadSourceData	= null;
	private Button											btn_Refresh				= null;
	// Variables
	private ProviderContactReportInitParamDTO				initParam				= null;
	private final int										DEFAULT_PAGE_NUMBER		= 1;
	private final int										MAX_ROWS_NUMBER			= 20;
	private final String									DEFAULT_SORT_FIELD		= PROVIDERCODE;
	private OrderCriteriaDTO[]								usersOrderCriteria		= null;
	private Boolean											trackReport				= true;
	private Boolean											resetPageInfo			= true;
	private BbrWork<ProviderContactReportResultDTO>			reportWork				= null;
	private BbrWork<FileDownloadInfoResultDTO>				downloadsWork			= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ProvidersContactsAdministrator(BbrUI bbrUIParent)
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
		ProvidersContactsAdministratorFilter filterLayout = new ProvidersContactsAdministratorFilter(this);
		filterLayout.initializeView();
		this.setBbrFilterContainer(filterLayout);

		// Paging Manager

		this.pagingManager = new BbrPagingManager();
		this.pagingManager.setLocale(this.getUser().getLocale());
		this.pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> this.pagingChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);

		// Reporte: Barra de herramientas superior izq

		HorizontalLayout leftButtonsBar = new HorizontalLayout();
		leftButtonsBar.setSpacing(true);
		leftButtonsBar.setMargin(false);
		leftButtonsBar.setHeight("30px");
		leftButtonsBar.addStyleName("toolbar-layout");

		this.btn_DownloadExcel = new Button("", EnumToolbarButton.DOWNLOAD_PRIMARY.getResource());
		this.btn_DownloadExcel.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "download_report"));
		this.btn_DownloadExcel.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadFile_downloadHandler(e));
		this.btn_DownloadExcel.addStyleName("toolbar-button");

		this.btn_Refresh = new Button("",
				EnumToolbarButton.REFRESH.getResource());
		this.btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
		this.btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler(e));
		this.btn_Refresh.addStyleName("toolbar-button");

		HorizontalLayout rightButtonsBar = new HorizontalLayout();
		rightButtonsBar.addComponents(this.btn_DownloadExcel, this.btn_Refresh);
		rightButtonsBar.setSpacing(true);
		rightButtonsBar.setMargin(false);
		rightButtonsBar.setHeight("30px");
		rightButtonsBar.addStyleName("toolbar-layout");

		HorizontalLayout toolBar = new HorizontalLayout();
		toolBar.setWidth("100%");
		toolBar.addComponents(leftButtonsBar, rightButtonsBar);
		toolBar.addStyleName("filter-toolbar");
		toolBar.setExpandRatio(leftButtonsBar, 1F);
		toolBar.setExpandRatio(rightButtonsBar, 1F);

		toolBar.setComponentAlignment(leftButtonsBar, Alignment.MIDDLE_LEFT);
		toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);

		// Reporte: Grilla

		this.dgd_ProvidersContacts = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.dgd_ProvidersContacts.setSortable(false);
		// this.dgd_ProvidersContacts.setHasRowsDetails(true);
		// this.dgd_ProvidersContacts.setRowsDetailsGenerator(new
		// ProviderContactAdministratorDetailsGenerator());
		this.initializeDataGridColumns();

		this.dgd_ProvidersContacts.addStyleName("report-grid");
		this.dgd_ProvidersContacts.setSizeFull();
		this.dgd_ProvidersContacts.setPagingManager(pagingManager, this.DEFAULT_SORT_FIELD);
		this.dgd_ProvidersContacts.setSelectionMode(SelectionMode.SINGLE);
		this.dgd_ProvidersContacts.addSelectionListener((SelectionListener<ProviderContactReportDataDTO> & Serializable) e -> selection_gridHandler(e));
		this.dgd_ProvidersContacts.addSortListener((SortListener<GridSortOrder<ProviderContactReportDataDTO>> & Serializable) e -> dataGrid_sortHandler(e));
		this.dgd_ProvidersContacts.addItemClickListener((ItemClickListener<ProviderContactReportDataDTO> & Serializable) e -> dgdItem_clickHandler(e));

		this.mainLayout = new VerticalLayout();
		this.mainLayout.addStyleName("report-layout");
		this.mainLayout.setSizeFull();
		this.mainLayout.setMargin(false);
		this.mainLayout.addComponents(toolBar, this.dgd_ProvidersContacts, this.pagingManager);
		this.mainLayout.setExpandRatio(this.dgd_ProvidersContacts, 1F);

		this.addComponents(mainLayout);

		this.updateButtons(false);
		this.updateSortOrderCriteria(null);

		reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		reportWork.addFunction(new Function<Object, ProviderContactReportResultDTO>()
		{
			@Override
			public ProviderContactReportResultDTO apply(Object t)
			{
				return executeService(ProvidersContactsAdministrator.this.getBbrUIParent());
			}
		});

	}

	@Override
	public void filterApplied_handler(BbrFilterEvent event)
	{
		if ((event != null) && (event.getResultObject() != null))
		{
			this.initParam = (ProviderContactReportInitParamDTO) event.getResultObject();

			this.trackReport = true;
			this.resetPageInfo = true;

			this.startWaiting();
			this.executeBbrWork(reportWork);
		}
	}

	@Override
	protected void downloadFormat_selectedHandler(BbrEvent event)
	{
		DownloadFormats selectedFormat = ((event != null) && (event.getResultObject() instanceof DownloadFormats)) ? (DownloadFormats) event.getResultObject() : null;

		downloadsWork = new BbrWork<>(this, this.getBbrUIParent(), btn_DownloadTriggerButton);
		downloadsWork.addFunction(new Function<Object, FileDownloadInfoResultDTO>()
		{
			@Override
			public FileDownloadInfoResultDTO apply(Object t)
			{
				return executeDownloadService(ProvidersContactsAdministrator.this.getBbrUIParent(), selectedFormat, btn_DownloadTriggerButton, null);
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
				this.doUpdateReport(result, sender);
			}
			else if (triggerObject instanceof Button)
			{
				this.doDownloadFile(result, sender, triggerObject);
			}
		}
		else
		{
			ProvidersContactsAdministrator senderReport = (ProvidersContactsAdministrator) sender;

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

		if (this.btn_DownloadTriggerButton == this.btn_DownloadExcel || this.btn_DownloadTriggerButton == this.btn_DownloadBarCodes)
		{
			this.selectDownloadFileType(DownloadFormats.XLS, DownloadFormats.XLS, DownloadFormats.CSV);
		}
		else if (this.btn_DownloadTriggerButton == this.btn_DownloadSourceData)
		{
			this.downloadDataSource();
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

	private void selection_gridHandler(SelectionEvent<ProviderContactReportDataDTO> e)
	{
		this.updateButtons(true);
	}

	private void dataGrid_sortHandler(SortEvent<GridSortOrder<ProviderContactReportDataDTO>> e)
	{
		if (e.isUserOriginated() && pagingManager.getTotalsPages() > 1)
		{
			this.startWaiting();

			this.updateSortOrderCriteria(e.getSortOrder());
			this.trackReport = false;
			this.resetPageInfo = true;
			this.executeBbrWork(reportWork);
		}
	}

	private void dgdItem_clickHandler(ItemClick<ProviderContactReportDataDTO> e)
	{
		if (e.getMouseEventDetails().isDoubleClick() && e.getItem() != null)
		{
			// this.viewProductDetails(e.getItem());
		}
	}

	private void refreshReport_clickHandler(ClickEvent event)
	{
		this.startWaiting();

		this.trackReport = false;
		this.resetPageInfo = false;
		this.executeBbrWork(reportWork);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private void initializeDataGridColumns()
	{
		this.dgd_ProvidersContacts
				.addCustomColumn(ProviderContactReportDataDTO::getProvidercode, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_provider_code_col"), true)
				.setWidth(120)
				.setId(PROVIDERCODE);
		this.dgd_ProvidersContacts
				.addCustomColumn(ProviderContactReportDataDTO::getSocialreason, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_social_reason_provider_col"),
						true)
				.setWidth(160)
				.setId(SOCIALREASON);
		this.dgd_ProvidersContacts
				.addCustomColumn(p -> p.getName() + " " + p.getLastname(), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_contact_name"), true)
				.setWidth(160)
				.setId(CONTACTNAME);
		this.dgd_ProvidersContacts
				.addCustomColumn(c -> this.showAllContactPositions(c, false), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_positions"), true)
				.setDescriptionGenerator(c -> this.showAllContactPositions(c, true))
				.setWidth(200)
				.setId(POSITIONS);
		this.dgd_ProvidersContacts
				.addCustomColumn(c -> this.showAllContactPhones(c, false), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_phones"), true)
				.setDescriptionGenerator(c -> this.showAllContactPhones(c, true))
				.setWidth(160)
				.setId(PHONES);
		this.dgd_ProvidersContacts
				.addCustomColumn(ProviderContactReportDataDTO::getEmail, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_email"), true)
				.setWidthUndefined()
				.setId(EMAIL);
		this.dgd_ProvidersContacts
				.addCustomColumn(p -> p.getLogname() + " " + p.getLogapellido(), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_updated_by"), true)
				.setWidth(160)
				.setId(UPDATEDBY);
		this.dgd_ProvidersContacts
				.addCustomColumn(ProviderContactReportDataDTO::getFecha, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "cp_last_update_col"), true)
				.setWidth(160)
				.setId(LASTUPDATE);
	}

	private String showAllContactPositions(ProviderContactReportDataDTO c, boolean isToolTip)
	{
		StringBuilder result = new StringBuilder();
		ContactProviderPositionDTO[] positions = c.getPositions() != null ? c.getPositions() : new ContactProviderPositionDTO[0];
		if (!isToolTip)
		{
			for (ContactProviderPositionDTO pos : positions)
			{
				String separator = (!pos.equals(positions[positions.length - 1])) ? this.SEPARATOR : "";
				String concatArea = (pos.getArea() != null && !pos.getArea().isEmpty()) ? " [" + pos.getArea() + "] " : "";
				result.append(pos.getName() + " " + concatArea + separator);
			}
		}
		else
		{
			for (ContactProviderPositionDTO contactProviderPositionDTO : positions)
			{
				String concat = (contactProviderPositionDTO.getArea() != null && !contactProviderPositionDTO.getArea().isEmpty()) ? " [" + contactProviderPositionDTO.getArea() + "] " : "";
				result.append(contactProviderPositionDTO.getName() + concat + "\n");
			}
		}

		return result.toString();
	}

	private String showAllContactPhones(ProviderContactReportDataDTO c, boolean isToolTip)
	{
		StringBuilder result = new StringBuilder();
		ContactPhoneDTO[] phones = c.getPhones() != null ? c.getPhones() : new ContactPhoneDTO[0];

		if (!isToolTip)
		{
			for (ContactPhoneDTO pho : phones)
			{
				String separator = (!pho.equals(phones[phones.length - 1])) ? this.SEPARATOR : "";
				result.append(pho.getNumber() + " " + separator);
			}

		}
		else
		{
			for (ContactPhoneDTO pho : phones)
			{
				result.append(pho.getNumber() + " " + "\n");
			}
		}

		return result.toString();
	}

	private void updateButtons(Boolean isSelectionEvent)
	{
		// this.btn_ProductDetails.setEnabled(this.datGrid_Products.getSelectedItems()
		// != null &&
		// !this.datGrid_Products.getSelectedItems().isEmpty());

		if (!isSelectionEvent)
		{
			Boolean isEmptyGrid = this.dgd_ProvidersContacts.isEmpty();

			this.btn_DownloadExcel.setEnabled(!isEmptyGrid);
			// this.btn_DownloadSourceData.setEnabled(!isEmptyGrid);
			// this.btn_DownloadBarCodes.setEnabled(!isEmptyGrid);
		}
		this.btn_Refresh.setEnabled(initParam != null);
	}

	private void updateSortOrderCriteria(List<GridSortOrder<ProviderContactReportDataDTO>> sortOrderList)
	{
		if (sortOrderList != null && !sortOrderList.isEmpty())
		{
			ArrayList<OrderCriteriaDTO> resultList = new ArrayList<>();
			for (GridSortOrder<ProviderContactReportDataDTO> sorOrder : sortOrderList)
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

			GridSortOrder<ProviderContactReportDataDTO> sortOrder = new GridSortOrder<>(dgd_ProvidersContacts.getColumn(DEFAULT_SORT_FIELD), SortDirection.ASCENDING);
			sortOrderList = new ArrayList<GridSortOrder<ProviderContactReportDataDTO>>();
			sortOrderList.add(sortOrder);

			this.dgd_ProvidersContacts.setSortOrder(sortOrderList);
		}
	}

	private ProviderContactReportResultDTO executeService(BbrUI bbrUI)
	{
		ProviderContactReportResultDTO result = null;

		if (this.initParam != null)
		{

			Integer requestedPage = (!resetPageInfo && this.pagingManager.getCurrentPage() > 0) ? (Integer) this.pagingManager.getCurrentPage() : this.DEFAULT_PAGE_NUMBER;
			this.initParam.setPagenumber(requestedPage);
			this.initParam.setRows(MAX_ROWS_NUMBER);
			this.initParam.setOrder(this.usersOrderCriteria);

			try
			{
				// Start Tracking

				this.getTimingMngr().startTimer();
				result = EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(bbrUI)
						.getProviderContactReportByProviderAndPosition(this.initParam, true, bbrUI.getUser().getId());
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

	private FileDownloadInfoResultDTO executeDownloadService(BbrUI bbrUIParent, DownloadFormats selectedFormat, Button downloadTriggerButton, Object extraData)
	{
		FileDownloadInfoResultDTO fileResult = null;
		if (selectedFormat != null)
		{
			try
			{
				if (this.btn_DownloadTriggerButton == this.btn_DownloadExcel)
				{
					fileResult = EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(bbrUIParent).downloadProviderContactReportByProviderAndPosition(
							this.initParam, this.getBbrUIParent().getUser().getId(), this.getBbrUIParent().getLocale(), selectedFormat.getValue());
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

	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{
		String errordescription = "";

		ProvidersContactsAdministrator senderReport = (ProvidersContactsAdministrator) sender;

		if (result != null)
		{
			ProviderContactReportResultDTO reportResult = (ProviderContactReportResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				ListDataProvider<ProviderContactReportDataDTO> dataprovider = new ListDataProvider<>(Arrays.asList(reportResult.getContactData()));

				senderReport.dgd_ProvidersContacts.setDataProvider(dataprovider, senderReport.resetPageInfo);
				senderReport.updateButtons(false);

				if (senderReport.resetPageInfo)
				{
					PageInfoDTO pageInfo = reportResult.getPageInfoDTO();
					if (pageInfo != null)
					{

						BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
						senderReport.pagingManager.setPages(bbrPage, senderReport.resetPageInfo);

					}
				}

				// End Tracking
				if (senderReport.trackReport == true)
				{
					senderReport.trackEvent(TrackingConstants.REPORT_VIEW, senderReport.getBbrFilterDescription());
				}

				if (reportResult.getContactData().length == 0)
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

	private void doDownloadFile(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		ProvidersContactsAdministrator senderReport = (ProvidersContactsAdministrator) sender;
		if (senderReport != null)
		{
			senderReport.downloadLinkFile(result);
		}
	}

	private void downloadDataSource()
	{
		downloadsWork = new BbrWork<>(this, this.getBbrUIParent(), btn_DownloadTriggerButton);
		downloadsWork.addFunction(new Function<Object, FileDownloadInfoResultDTO>()
		{
			@Override
			public FileDownloadInfoResultDTO apply(Object t)
			{
				return executeDownloadService(ProvidersContactsAdministrator.this.getBbrUIParent(), DownloadFormats.CSV, btn_DownloadTriggerButton, null);
			}
		});

		this.startWaiting();
		this.executeBbrWork(downloadsWork);
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
}
