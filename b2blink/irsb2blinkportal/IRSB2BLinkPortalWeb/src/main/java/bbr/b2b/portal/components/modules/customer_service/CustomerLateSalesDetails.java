package bbr.b2b.portal.components.modules.customer_service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.SortEvent;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.customer.report.classes.LateSalesDetailDTO;
import bbr.b2b.customer.report.classes.LateSalesDetailInitParamDTO;
import bbr.b2b.customer.report.classes.LateSalesDetailResultDTO;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.wrappers.customer.ControlPanelCardInfo;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.modules.customer.ControlPanelManagement;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.basics.BbrPage;
import cl.bbr.core.classes.constants.BbrAlignment;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.grid.renderer.ShortDateRenderer;
import cl.bbr.core.components.paging.BbrPagingManager;

public class CustomerLateSalesDetails extends BbrWindow implements BbrWorkExecutor
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants
	private static final long					serialVersionUID			= -6330012324770348082L;
	private static final String					BBR_MARGIN_WINDOWS_STYLE	= "bbr-margin-windows";
	// Components
	private BbrPagingManager					pagingManager				= null;
	private BbrAdvancedGrid<LateSalesDetailDTO>	dgd_LateSales				= null;
	// Variables
	private ControlPanelCardInfo				cardInfo					= null;
	private BbrWork<LateSalesDetailResultDTO>	lateSalesDetailsWork		= null;
	private boolean								resetPageInfo				= true;
	private CompanyDataDTO						selectedCompany				= null;
	private static final int					DEFAULT_PAGE_NUMBER			= 1;
	private static final int					MAX_ROWS_NUMBER				= 50;
	private static final String					CLIENTNAME					= "clientname";
	private static final String					DEFAULT_SORT_FIELD			= "date";
	private List<LateSalesDetailDTO>			providerDetails				= new ArrayList<>();
	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public CustomerLateSalesDetails(BbrUI parent, ControlPanelCardInfo cardInfo, CompanyDataDTO selectedCompany)
	{
		super(parent);
		this.cardInfo = cardInfo;
		this.selectedCompany = selectedCompany;

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
		// Paging Manager
		this.pagingManager = new BbrPagingManager();
		this.pagingManager.setLocale(this.getUser().getLocale());
		this.pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> pagingChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);

		// Grilla
		this.dgd_LateSales = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.dgd_LateSales.addStyleName("report-grid");
		this.dgd_LateSales.setSizeFull();
		this.dgd_LateSales.setPagingManager(pagingManager, DEFAULT_SORT_FIELD);
		this.dgd_LateSales.addColumn(LateSalesDetailDTO::getClientname)
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "client"))
				.setStyleGenerator(creationdate -> BbrAlignment.CENTER.getValue())
				.setId(CLIENTNAME);
		this.dgd_LateSales.addColumn(LateSalesDetailDTO::getDate)
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "date"))
				.setStyleGenerator(creationdate -> BbrAlignment.CENTER.getValue())
				.setRenderer(new ShortDateRenderer())
				.setId(DEFAULT_SORT_FIELD);
		this.dgd_LateSales.addSortListener(this::dataGrid_sortHandler);

		VerticalLayout mainContent = new VerticalLayout();
		mainContent.addStyleName(BBR_MARGIN_WINDOWS_STYLE);
		mainContent.addComponents(this.dgd_LateSales, this.pagingManager);
		mainContent.setExpandRatio(this.dgd_LateSales, 1f);
		mainContent.setSizeFull();
		this.setContent(mainContent);
		this.setCaption(this.cardInfo.getCaption());
		this.setWidth("500px");
		this.setHeight("450px");

		// Iniciar servicios despues de dibujar la ventana para que cargue
		this.doInitializeExecuteServiceLastSalesSend(cardInfo);
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		CustomerLateSalesDetails senderReport = (CustomerLateSalesDetails) sender;
		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				senderReport.doUpdateReport(result, senderReport);
			}
		}
		else
		{
			if (!senderReport.getBbrUIParent().hasAlertWindowOpen())
			{
				senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));
			}

			senderReport.stopWaiting();
		}

	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================
	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
	private void doInitializeExecuteServiceLastSalesSend(ControlPanelCardInfo cardInfo)
	{
		if (cardInfo.getSelectedPanel().equals(ControlPanelManagement.LATE_SALES))
		{
			lateSalesDetailsWork = new BbrWork<>(this, this.getBbrUIParent(), this, true);
			lateSalesDetailsWork.addFunction(new Function<Object, LateSalesDetailResultDTO>()
			{
				@Override
				public LateSalesDetailResultDTO apply(Object t)
				{
					return executeServiceLastSalesSend(CustomerLateSalesDetails.this.getBbrUIParent(), cardInfo);
				}
			});

			this.startWaiting();
			this.executeBbrWork(lateSalesDetailsWork);
		}
	}

	private LateSalesDetailResultDTO executeServiceLastSalesSend(BbrUI bbrUIParent, ControlPanelCardInfo cardInfo)
	{
		LateSalesDetailResultDTO result = null;

		try
		{
			LateSalesDetailInitParamDTO initParamDTO = getInitParamDetails(bbrUIParent, cardInfo);
			result = EJBFactory.getCustomerEJBFactory().getCustomerManagerLocal(bbrUIParent).getLateSalesByClient(initParamDTO);
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

	private LateSalesDetailInitParamDTO getInitParamDetails(BbrUI bbrUIParent, ControlPanelCardInfo cardInfo)
	{
		String pvcode = this.selectedCompany != null ? this.selectedCompany.getRut() : null;
		LateSalesDetailInitParamDTO initParamDTO = new LateSalesDetailInitParamDTO();
		Integer requestedPage = (!resetPageInfo && this.pagingManager.getCurrentPage() > 0) ? (Integer) this.pagingManager.getCurrentPage() : DEFAULT_PAGE_NUMBER;
		initParamDTO.setPageNumber(requestedPage);
		initParamDTO.setPvcode(pvcode);
		initParamDTO.setClkey(cardInfo.getClientId());
		initParamDTO.setCount(cardInfo.getValue() != null ? Integer.valueOf(cardInfo.getValue()) : 0);
		initParamDTO.setLocale(bbrUIParent.getLocale());
		initParamDTO.setNeedPageInfo(true);
		initParamDTO.setRows(MAX_ROWS_NUMBER);

		return initParamDTO;
	}

	private void pagingChange_Listener(BbrPagingEvent e)
	{
		if ((e != null) && e.getEventType().equals(BbrPagingEvent.PAGE_CHANGED) && (e.getResultObject() != null))
		{
			this.resetPageInfo = false;
			this.startWaiting();
			this.executeBbrWork(lateSalesDetailsWork);
		}
	}

	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{
		CustomerLateSalesDetails senderReport = (CustomerLateSalesDetails) sender;

		if (result != null)
		{
			LateSalesDetailResultDTO reportResult = (LateSalesDetailResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				LateSalesDetailDTO[] provider = reportResult.getLateSalesDetailDTOs();
				senderReport.setDetailsDataProvider(senderReport, Arrays.asList(provider));

				if (senderReport.resetPageInfo)
				{
					PageInfoDTO pageInfo = reportResult.getPageInfoDTO();

					BbrPage bbrPage = new BbrPage(pageInfo.getPagenumber(), pageInfo.getTotalpages(), pageInfo.getRows(), pageInfo.getTotalrows());
					senderReport.pagingManager.setPages(bbrPage, senderReport.resetPageInfo);

				}
			}
		}
		senderReport.stopWaiting();
	}

	private void setDetailsDataProvider(CustomerLateSalesDetails senderReport, List<LateSalesDetailDTO> provider)
	{
		senderReport.providerDetails = provider;
		ListDataProvider<LateSalesDetailDTO> dataprovider = new ListDataProvider<>(senderReport.providerDetails);
		senderReport.dgd_LateSales.setDataProvider(dataprovider, senderReport.resetPageInfo);
	}

	private void dataGrid_sortHandler(SortEvent<GridSortOrder<LateSalesDetailDTO>> e)
	{
		if (e.isUserOriginated() && pagingManager.getTotalsPages() > 1)
		{
			this.resetPageInfo = false;
			this.executeBbrWork(this.lateSalesDetailsWork);
		}
	}

}
