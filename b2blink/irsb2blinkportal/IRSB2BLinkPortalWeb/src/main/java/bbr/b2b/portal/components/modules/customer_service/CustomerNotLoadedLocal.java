package bbr.b2b.portal.components.modules.customer_service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.vaadin.data.provider.GridSortOrder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.SortEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.customer.report.classes.NotLoadedLocalDetailDTO;
import bbr.b2b.customer.report.classes.NotLoadedLocalDetailInitParamDTO;
import bbr.b2b.customer.report.classes.NotLoadedLocalDetailResultDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
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
import cl.bbr.core.classes.constants.DownloadFormats;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrPagingEvent;
import cl.bbr.core.classes.events.BbrPagingEventListener;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.paging.BbrPagingManager;

public class CustomerNotLoadedLocal extends BbrWindow implements BbrWorkExecutor
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants
	private static final long							serialVersionUID			= -6330012324770348082L;
	private static final String							BBR_MARGIN_WINDOWS_STYLE	= "bbr-margin-windows";
	// Components
	private BbrPagingManager							pagingManager				= null;
	private BbrAdvancedGrid<NotLoadedLocalDetailDTO>	dgd_NotLoadedLocal			= null;
	private Button										btn_Download				= null;
	private Button										btn_Search					= null;
	// Variables
	private ControlPanelCardInfo						cardInfo					= null;
	private BbrWork<NotLoadedLocalDetailResultDTO>		lateSalesDetailsWork		= null;
	private BbrWork<FileDownloadInfoResultDTO>			downloadsWork				= null;
	private boolean										resetPageInfo				= true;
	private CompanyDataDTO								selectedCompany				= null;
	private static final int							DEFAULT_PAGE_NUMBER			= 1;
	private static final int							MAX_ROWS_NUMBER				= 50;
	private static final String							DESCLOCALCLIENT				= "desclocalclient";
	private static final String							CODLOCALCLIENT				= "codlocalclient";
	private static final String							DEFAULT_SORT_FIELD			= "clientname";
	private List<NotLoadedLocalDetailDTO>				providerDetails				= new ArrayList<>();
	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public CustomerNotLoadedLocal(BbrUI parent, ControlPanelCardInfo cardInfo, CompanyDataDTO selectedCompany)
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
		this.btn_Search = new Button("", EnumToolbarButton.SEARCH_ALTERNATIVE.getResource());
		this.btn_Search.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "find_local"));
		// this.btn_Search.addClickListener((ClickListener & Serializable) e ->
		// btn_DownloadFile_downloadHandler(e));
		this.btn_Search.addClickListener((ClickListener & Serializable) e -> btn_Search_clickHandler(e));
		this.btn_Search.addStyleName("toolbar-button");

		this.btn_Download = new Button("", EnumToolbarButton.DOWNLOAD_PRIMARY.getResource());
		this.btn_Download.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "download_report"));
		this.btn_Download.addClickListener((ClickListener & Serializable) e -> btn_DownloadFile_downloadHandler(e));
		this.btn_Download.addStyleName("toolbar-button");

		HorizontalLayout buttonsBar = new HorizontalLayout();
		buttonsBar.setWidth("100%");
		buttonsBar.setHeight("30px");
		buttonsBar.setMargin(false);
		buttonsBar.addComponent(this.btn_Download);
		buttonsBar.setComponentAlignment(this.btn_Download, Alignment.MIDDLE_RIGHT);

		// Paging Manager
		this.pagingManager = new BbrPagingManager();
		this.pagingManager.setLocale(this.getUser().getLocale());
		this.pagingManager.addBbrPagingEventListener((BbrPagingEventListener & Serializable) e -> pagingChange_Listener(e), BbrPagingEvent.PAGE_CHANGED);

		// Grilla
		this.dgd_NotLoadedLocal = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.dgd_NotLoadedLocal.addStyleName("report-grid");
		this.dgd_NotLoadedLocal.setSizeFull();
		this.dgd_NotLoadedLocal.setPagingManager(pagingManager, DEFAULT_SORT_FIELD);
		this.dgd_NotLoadedLocal.addColumn(NotLoadedLocalDetailDTO::getClientname)
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "client"))
				.setStyleGenerator(i -> BbrAlignment.CENTER.getValue())
				.setId(DEFAULT_SORT_FIELD);
		this.dgd_NotLoadedLocal.addColumn(NotLoadedLocalDetailDTO::getCodlocalclient)
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "retail_code"))
				.setStyleGenerator(i -> BbrAlignment.CENTER.getValue())
				.setId(CODLOCALCLIENT);
		this.dgd_NotLoadedLocal.addColumn(NotLoadedLocalDetailDTO::getDesclocalclient)
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "retail_description"))
				.setStyleGenerator(i -> BbrAlignment.CENTER.getValue())
				.setId(DESCLOCALCLIENT);
		this.dgd_NotLoadedLocal.addSortListener(this::dataGrid_sortHandler);

		VerticalLayout mainContent = new VerticalLayout();
		mainContent.addStyleName(BBR_MARGIN_WINDOWS_STYLE);
		mainContent.addComponents(buttonsBar, this.dgd_NotLoadedLocal, this.pagingManager);
		mainContent.setExpandRatio(this.dgd_NotLoadedLocal, 1f);
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
		CustomerNotLoadedLocal senderReport = (CustomerNotLoadedLocal) sender;
		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				senderReport.doUpdateReport(result, senderReport);
			}
			else if (triggerObject == this.btn_DownloadTriggerButton)
			{
				senderReport.doDownloadFile(result, sender, triggerObject);
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
		if (cardInfo.getSelectedPanel().equals(ControlPanelManagement.NOT_LOADED_LOCAL))
		{
			lateSalesDetailsWork = new BbrWork<>(this, this.getBbrUIParent(), this, true);
			lateSalesDetailsWork.addFunction(new Function<Object, NotLoadedLocalDetailResultDTO>()
			{
				@Override
				public NotLoadedLocalDetailResultDTO apply(Object t)
				{
					return executeServiceLastSalesSend(CustomerNotLoadedLocal.this.getBbrUIParent(), cardInfo);
				}
			});

			this.startWaiting();
			this.executeBbrWork(lateSalesDetailsWork);
		}
	}

	private NotLoadedLocalDetailResultDTO executeServiceLastSalesSend(BbrUI bbrUIParent, ControlPanelCardInfo cardInfo)
	{
		NotLoadedLocalDetailResultDTO result = null;

		try
		{
			NotLoadedLocalDetailInitParamDTO initParamDTO = getInitParamDetails(bbrUIParent, cardInfo);
			result = EJBFactory.getCustomerEJBFactory().getCustomerManagerLocal(bbrUIParent).getNotLoadedLocalDetail(initParamDTO);
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

	private NotLoadedLocalDetailInitParamDTO getInitParamDetails(BbrUI bbrUIParent, ControlPanelCardInfo cardInfo)
	{
		String pvcode = this.selectedCompany != null ? this.selectedCompany.getRut() : null;
		NotLoadedLocalDetailInitParamDTO initParamDTO = new NotLoadedLocalDetailInitParamDTO();
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
		CustomerNotLoadedLocal senderReport = (CustomerNotLoadedLocal) sender;

		if (result != null)
		{
			NotLoadedLocalDetailResultDTO reportResult = (NotLoadedLocalDetailResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				NotLoadedLocalDetailDTO[] provider = reportResult.getNotLoadedLocalDetailDTOs();
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

	private void setDetailsDataProvider(CustomerNotLoadedLocal senderReport, List<NotLoadedLocalDetailDTO> provider)
	{
		senderReport.providerDetails = provider;
		ListDataProvider<NotLoadedLocalDetailDTO> dataprovider = new ListDataProvider<>(senderReport.providerDetails);
		senderReport.dgd_NotLoadedLocal.setDataProvider(dataprovider, senderReport.resetPageInfo);
	}

	private void dataGrid_sortHandler(SortEvent<GridSortOrder<NotLoadedLocalDetailDTO>> e)
	{
		if (e.isUserOriginated() && pagingManager.getTotalsPages() > 1)
		{
			this.resetPageInfo = false;
			this.executeBbrWork(this.lateSalesDetailsWork);
		}
	}

	private void btn_DownloadFile_downloadHandler(ClickEvent e)
	{
		this.btn_DownloadTriggerButton = e.getButton();

		downloadsWork = new BbrWork<>(this, this.getBbrUIParent(), btn_DownloadTriggerButton);
		downloadsWork.addFunction(new Function<Object, FileDownloadInfoResultDTO>()
		{
			@Override
			public FileDownloadInfoResultDTO apply(Object t)
			{
				return executeDownloadService(CustomerNotLoadedLocal.this.getBbrUIParent(), DownloadFormats.XLSX, btn_DownloadTriggerButton);
			}
		});

		this.startWaiting();
		this.executeBbrWork(downloadsWork);
	}

	private FileDownloadInfoResultDTO executeDownloadService(BbrUI bbrUIParent, DownloadFormats selectedFormat, Button downloadTriggerButton)
	{
		FileDownloadInfoResultDTO fileResult = null;
		if (selectedFormat != null)
		{
			try
			{
				if (this.btn_DownloadTriggerButton == this.btn_Download)
				{
					String pvcode = this.selectedCompany != null ? this.selectedCompany.getRut() : null;
					NotLoadedLocalDetailInitParamDTO initParamDTO = new NotLoadedLocalDetailInitParamDTO();
					initParamDTO.setClkey(this.cardInfo.getClientId());
					initParamDTO.setCount(Integer.valueOf(this.cardInfo.getValue()));
					initParamDTO.setLocale(bbrUIParent.getUser().getLocale());
					initParamDTO.setNeedPageInfo(false);
					initParamDTO.setPvcode(pvcode);
					fileResult = EJBFactory.getCustomerEJBFactory().getCustomerManagerLocal(bbrUIParent).downloadNotLoadedLocalDetail(initParamDTO, bbrUIParent.getUser().getId());
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

	private void doDownloadFile(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		CustomerNotLoadedLocal senderReport = (CustomerNotLoadedLocal) sender;
		if (senderReport != null)
		{
			senderReport.downloadLinkFile(result);
		}
	}

	private void btn_Search_clickHandler(ClickEvent e)
	{
		if (e != null)
		{
			// FindProduct winFindProvider = new
			// FindProduct(this.getBbrUIParent(), true);
			// winFindProvider.initializeView();
			// winFindProvider.addBbrEventListener(this::companySelectedHandler);
			// winFindProvider.open(true);
		}
	}

	// private void companySelectedHandler(BbrEvent bbrEvent)
	// {
	// if (bbrEvent != null && bbrEvent.getResultObject() != null)
	// {
	// CompanyDataDTO company = (CompanyDataDTO) bbrEvent.getResultObject();
	//
	// // this.updateCompanies(company);
	// }
	// }

}
