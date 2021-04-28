package bbr.b2b.portal.modules.customer;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.customer.report.classes.AssociateProductProviderProductClientInitParamDTO;
import bbr.b2b.customer.report.classes.CustomerServiceControlPanelResultDTO;
import bbr.b2b.customer.report.classes.LastInventorySendDTO;
import bbr.b2b.customer.report.classes.LastSalesSendDTO;
import bbr.b2b.customer.report.classes.LateSalesDTO;
import bbr.b2b.customer.report.classes.NotLoadedLocalDTO;
import bbr.b2b.customer.report.classes.NotLoadedProductDTO;
import bbr.b2b.customer.report.classes.PendingReprocessInventoryDTO;
import bbr.b2b.customer.report.classes.PendingReprocessSalesDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.constants.ModulesCodes;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.ui.MainUI;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.wrappers.customer.ControlPanelCardInfo;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.filters.customer_service.ControlPanelFilter;
import bbr.b2b.portal.components.modules.customer_service.CustomerInfoCard;
import bbr.b2b.portal.components.modules.customer_service.CustomerInfoCard.CustomerInfoCardBuilder;
import bbr.b2b.portal.components.modules.customer_service.CustomerLateSalesDetails;
import bbr.b2b.portal.components.modules.customer_service.CustomerNotLoadedLocal;
import bbr.b2b.portal.components.modules.customer_service.CustomerNotLoadedProduct;
import bbr.b2b.portal.components.utils.customer.FindProduct;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.basics.BbrUI;

public class ControlPanelManagement extends BbrModule implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants
	private static final long								serialVersionUID			= -3947159600615633249L;
	public static final String								LAST_INVENTORY_SEND			= "last_inventory_send";
	public static final String								LAST_SALES_SEND				= "last_sales_send";
	public static final String								LATE_SALES					= "late_sales";
	public static final String								NOT_LOADED_PRODUCT			= "not_loaded_product";
	public static final String								NOT_LOADED_LOCAL			= "not_loaded_local";
	public static final String								PENDING_REPROCESS_SALES		= "pending_reprocess_sales";
	public static final String								PENDING_REPROCESS_INVENTORY	= "pending_reprocess_inventory";
	public static final String								CONTROL_PANEL_GRID_STYLE	= "control-panel-grid bbr-overflow-auto";
	// Components
	private BbrWork<CustomerServiceControlPanelResultDTO>	reportWork					= null;
	private CompanyDataDTO									selectedCompany				= null;
	private CssLayout										content						= null;
	private BbrWork<BaseResultDTO>							associateProductWork		= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ControlPanelManagement(BbrUI bbrUIParent)
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
		// Filtro
		ControlPanelFilter filterLayout = new ControlPanelFilter(this);
		filterLayout.initializeView();
		this.setBbrFilterContainer(filterLayout);

		// CONTENT LAYOUT
		this.content = new CssLayout();
		this.content.setStyleName(CONTROL_PANEL_GRID_STYLE);
		this.content.setSizeFull();

		CustomerServiceControlPanelResultDTO initialResult = this.getInitialEmptyResultDTO();
		this.initializeInfoCards(initialResult, this);

		Button btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
		btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
		btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler(e));
		btn_Refresh.addStyleName("toolbar-button");

		HorizontalLayout rightButtonsBar = new HorizontalLayout();
		rightButtonsBar.addComponents(btn_Refresh);
		rightButtonsBar.setSpacing(true);
		rightButtonsBar.setMargin(false);
		rightButtonsBar.setHeight("30px");
		rightButtonsBar.addStyleName("toolbar-layout");

		HorizontalLayout toolBar = new HorizontalLayout();
		toolBar.setWidth("100%");
		toolBar.addComponents(rightButtonsBar);
		toolBar.addStyleName("filter-toolbar");
		toolBar.setExpandRatio(rightButtonsBar, 1F);
		toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);

		// Reporte: Contenido

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addStyleName("report-layout");
		mainLayout.setSizeFull();
		mainLayout.setMargin(false);
		mainLayout.addComponents(toolBar);
		mainLayout.addComponents(this.content);
		mainLayout.setExpandRatio(this.content, 1F);

		this.addComponents(mainLayout);

		reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		reportWork.addFunction(new Function<Object, CustomerServiceControlPanelResultDTO>()
		{
			@Override
			public CustomerServiceControlPanelResultDTO apply(Object t)
			{
				return executeService(ControlPanelManagement.this.getBbrUIParent());
			}
		});
	}

	@Override
	public void filterApplied_handler(BbrFilterEvent event)
	{
		if ((event != null) && (event.getResultObject() != null))
		{
			this.selectedCompany = (CompanyDataDTO) event.getResultObject();

			executeReportWork();
		}
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		ControlPanelManagement senderReport = (ControlPanelManagement) sender;

		if (result != null)
		{
			if (result instanceof CustomerServiceControlPanelResultDTO)
			{
				senderReport.doUpdateReport(result, senderReport);
			}
			else if (result instanceof BaseResultDTO)
			{
				senderReport.doUpdateReportAssociated(result, senderReport);
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
	// BEGINNING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================
	private void refreshReport_clickHandler(ClickEvent event)
	{
		this.executeReportWork();
	}

	private void executeReportWork()
	{
		this.startWaiting();

		this.executeBbrWork(reportWork);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
	private CustomerServiceControlPanelResultDTO getInitialEmptyResultDTO()
	{
		CustomerServiceControlPanelResultDTO result = new CustomerServiceControlPanelResultDTO();
		result.setLastInventorySendDTOs(new LastInventorySendDTO[0]);
		result.setLastSalesSendDTOs(new LastSalesSendDTO[0]);
		result.setLateSalesDTOs(new LateSalesDTO[0]);
		result.setNotLoadedProductDTOs(new NotLoadedProductDTO[0]);
		result.setNotLoadedLocalDTOs(new NotLoadedLocalDTO[0]);
		result.setPendingReprocessSalesDTOs(new PendingReprocessSalesDTO[0]);
		result.setPendingReprocessInventoryDTOs(new PendingReprocessInventoryDTO[0]);
		return result;
	}

	private CustomerServiceControlPanelResultDTO executeService(BbrUI bbrUIParent)
	{
		CustomerServiceControlPanelResultDTO result = new CustomerServiceControlPanelResultDTO();

		try
		{
			String pvcode = this.selectedCompany != null ? this.selectedCompany.getRut() : null;
			result = EJBFactory.getCustomerEJBFactory().getCustomerManagerLocal(bbrUIParent).getCustomerServiceControlPanel(bbrUIParent.getUser().getId(), pvcode);
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

	private void doUpdateReport(Object result, BbrWorkExecutor sender)
	{

		ControlPanelManagement senderReport = (ControlPanelManagement) sender;

		if (result != null)
		{
			CustomerServiceControlPanelResultDTO reportResult = (CustomerServiceControlPanelResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				senderReport.initializeInfoCards(reportResult, senderReport);
			}
		}
		senderReport.stopWaiting();
	}

	private void doUpdateReportAssociated(Object result, BbrWorkExecutor sender)
	{

		ControlPanelManagement senderReport = (ControlPanelManagement) sender;

		if (result != null)
		{
			BaseResultDTO reportResult = (BaseResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				senderReport.stopWaiting();
				senderReport.executeReportWork();
			}
		}
		else
		{
			senderReport.stopWaiting();
		}
	}

	private void initializeInfoCards(CustomerServiceControlPanelResultDTO result, ControlPanelManagement senderReport)
	{
		// PANEL A
		LastInventorySendDTO[] lastInventorySendArray = result.getLastInventorySendDTOs() != null
				? result.getLastInventorySendDTOs()
				: new LastInventorySendDTO[0];

		List<ControlPanelCardInfo> lastInventorySendItemsList = this.itemsCardsLastInventorySendResultMapper((Object[]) lastInventorySendArray);
		CustomerInfoCard carda = new CustomerInfoCardBuilder(this.getBbrUIParent())
				.withCaption(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_MODULES_CUSTOMER, LAST_INVENTORY_SEND))
				.withItems(lastInventorySendItemsList)
				.withSelectedPanel(LAST_INVENTORY_SEND)
				.build();
		carda.setStyleName("panel-a");
		carda.setHeight("200px");

		// PANEL B
		LastSalesSendDTO[] lastSalesSendArray = result.getLastSalesSendDTOs() != null
				? result.getLastSalesSendDTOs()
				: new LastSalesSendDTO[0];
		Map<String, String> lastSalesSendItemsMap = Arrays.stream(lastSalesSendArray)
				.sorted((a, b) -> b.getDate().isBefore(a.getDate()) ? 0 : 1)
				.collect(Collectors.toMap(x -> x.getClientname(), x -> this.formatDate(x.getDate())));

		List<ControlPanelCardInfo> lastSalesSendItemsList = this.itemsCardsResultMapper(lastSalesSendItemsMap);
		CustomerInfoCard cardb = new CustomerInfoCardBuilder(this.getBbrUIParent())
				.withCaption(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_MODULES_CUSTOMER, LAST_SALES_SEND))
				.withItems(lastSalesSendItemsList)
				.withSelectedPanel(LAST_SALES_SEND)
				.build();
		cardb.setStyleName("panel-b");
		cardb.setHeight("200px");

		// PANEL 1
		LateSalesDTO[] lateSalesArray = result.getLateSalesDTOs() != null ? result.getLateSalesDTOs() : new LateSalesDTO[0];
		Long lastSalesMapTotal = Arrays.stream(lateSalesArray).collect(Collectors.summingLong(x -> x.getCount()));

		List<ControlPanelCardInfo> lateSalesItems = this.itemsCardsNumbersResultMapper(lateSalesArray);
		CustomerInfoCard card1 = new CustomerInfoCardBuilder(this.getBbrUIParent())
				.withCaption(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_MODULES_CUSTOMER, LATE_SALES))
				.withItems(lateSalesItems)
				.withTotal(lastSalesMapTotal)
				.withSelectedPanel(LATE_SALES)
				.withActionClickListener((e) -> this.openLateSalesDetails((ControlPanelCardInfo) e.getButton().getData()))
				.build();
		card1.setStyleName("panel-1");
		card1.setHeight("200px");
		card1.setWidth("200px");

		// PANEL 2
		NotLoadedProductDTO[] notLoadedProductArray = result.getNotLoadedProductDTOs() != null
				? result.getNotLoadedProductDTOs()
				: new NotLoadedProductDTO[0];
		Long notLoadedProductItemsMapTotal = Arrays.stream(notLoadedProductArray)
				.collect(Collectors.summingLong(x -> x.getCount()));

		List<ControlPanelCardInfo> notLoadedProductItems = this.itemsCardsNumbersResultMapper(notLoadedProductArray);
		CustomerInfoCard card2 = new CustomerInfoCardBuilder(this.getBbrUIParent())
				.withCaption(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_MODULES_CUSTOMER, NOT_LOADED_PRODUCT))
				.withItems(notLoadedProductItems)
				.withTotal(notLoadedProductItemsMapTotal)
				.withSelectedPanel(NOT_LOADED_PRODUCT)
				.withActionClickListener((e) -> this.openNotLoadedProduct((ControlPanelCardInfo) e.getButton().getData()))
				.build();
		card2.setStyleName("panel-2");
		card2.setHeight("200px");

		// PANEL 3
		NotLoadedLocalDTO[] notLoadedLocalArray = result.getNotLoadedLocalDTOs() != null
				? result.getNotLoadedLocalDTOs()
				: new NotLoadedLocalDTO[0];
		Long notLoadedLocalItemsMapTotal = Arrays.stream(notLoadedLocalArray)
				.collect(Collectors.summingLong(x -> x.getCount()));

		List<ControlPanelCardInfo> notLoadedLocalItems = this.itemsCardsNumbersResultMapper(notLoadedLocalArray);
		CustomerInfoCard card3 = new CustomerInfoCardBuilder(this.getBbrUIParent())
				.withCaption(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_MODULES_CUSTOMER, NOT_LOADED_LOCAL))
				.withItems(notLoadedLocalItems)
				.withTotal(notLoadedLocalItemsMapTotal)
				.withSelectedPanel(NOT_LOADED_LOCAL)
				.withActionClickListener((e) -> this.openNotLoadedLocal((ControlPanelCardInfo) e.getButton().getData()))
				.build();
		card3.setStyleName("panel-3");
		card3.setHeight("200px");

		// PANEL 4
		PendingReprocessSalesDTO[] pendingReprocessSalesArray = result.getPendingReprocessSalesDTOs() != null
				? result.getPendingReprocessSalesDTOs()
				: new PendingReprocessSalesDTO[0];
		Long pendingReprocessSalesItemsMapTotal = Arrays.stream(pendingReprocessSalesArray)
				.collect(Collectors.summingLong(x -> x.getCount()));

		List<ControlPanelCardInfo> pendingReprocessSalesItems = this.itemsCardsNumbersResultMapper(pendingReprocessSalesArray);
		CustomerInfoCard card4 = new CustomerInfoCardBuilder(this.getBbrUIParent())
				.withCaption(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_MODULES_CUSTOMER, PENDING_REPROCESS_SALES))
				.withItems(pendingReprocessSalesItems)
				.withTotal(pendingReprocessSalesItemsMapTotal)
				.withSelectedPanel(PENDING_REPROCESS_SALES)
				.withActionClickListener((e) -> this.openPendingReprocessSales(pendingReprocessSalesArray, (ControlPanelCardInfo) e.getButton().getData()))
				.build();
		card4.setStyleName("panel-4");
		card4.setHeight("200px");

		// PANEL 5
		PendingReprocessInventoryDTO[] pendingReprocessInventory = result.getPendingReprocessInventoryDTOs() != null
				? result.getPendingReprocessInventoryDTOs()
				: new PendingReprocessInventoryDTO[0];
		Long pendingReprocessInventoryItemsMapTotal = Arrays.stream(pendingReprocessInventory)
				.collect(Collectors.summingLong(PendingReprocessInventoryDTO::getCount));

		List<ControlPanelCardInfo> pendingReprocessInventoryItems = this.itemsCardsNumbersResultMapper(pendingReprocessInventory);
		CustomerInfoCard card5 = new CustomerInfoCardBuilder(this.getBbrUIParent())
				.withCaption(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_MODULES_CUSTOMER, PENDING_REPROCESS_INVENTORY))
				.withItems(pendingReprocessInventoryItems)
				.withTotal(pendingReprocessInventoryItemsMapTotal)
				.withSelectedPanel(PENDING_REPROCESS_INVENTORY)
				.withActionClickListener((e) -> this.openPendingReprocessInventory(pendingReprocessInventory, (ControlPanelCardInfo) e.getButton().getData()))
				.build();
		card5.setStyleName("panel-5");
		card5.setHeight("200px");

		// ORDEN
		if (senderReport.content != null && senderReport.content.getComponentCount() > 0)
		{
			senderReport.content.removeAllComponents();
		}
		senderReport.content.addComponent(carda);
		senderReport.content.addComponent(card1);
		senderReport.content.addComponent(card2);
		senderReport.content.addComponent(card3);
		senderReport.content.addComponent(cardb);
		senderReport.content.addComponent(card4);
		senderReport.content.addComponent(card5);
	}

	private void openLateSalesDetails(ControlPanelCardInfo data)
	{
		if (data != null)
		{
			CustomerLateSalesDetails customerLateSalesDetailsWindow = new CustomerLateSalesDetails(this.getBbrUIParent(), data, this.selectedCompany);
			customerLateSalesDetailsWindow.open(true, true, this);
		}
	}

	private void openNotLoadedProduct(ControlPanelCardInfo data)
	{
		if (data != null)
		{
			CustomerNotLoadedProduct customerNotLoadedProductWindow = new CustomerNotLoadedProduct(this.getBbrUIParent(), data, this.selectedCompany);
			customerNotLoadedProductWindow.addBbrEventListener(this::acceptedConfirmation_Handler);
			customerNotLoadedProductWindow.open(true, true, this);
		}
	}

	private void acceptedConfirmation_Handler(BbrEvent bbrEvent)
	{
		if (bbrEvent != null && bbrEvent.getResultObject() != null && bbrEvent.getEventType().equals(FindProduct.ACCEPTED_CONFIRMATION_EVENT))
		{
			AssociateProductProviderProductClientInitParamDTO initParam = (AssociateProductProviderProductClientInitParamDTO) bbrEvent.getResultObject();
			this.doInitializeExecuteServiceAssociateProductWork(initParam);
		}
	}

	private void openNotLoadedLocal(ControlPanelCardInfo data)
	{
		if (data != null)
		{
			CustomerNotLoadedLocal customerNotLoadedLocalWindow = new CustomerNotLoadedLocal(this.getBbrUIParent(), data, this.selectedCompany);
			customerNotLoadedLocalWindow.open(true, true, this);
		}
	}

	private void openPendingReprocessSales(PendingReprocessSalesDTO[] pendingReprocessSalesArray, ControlPanelCardInfo data)
	{
		if (data != null && pendingReprocessSalesArray != null)
		{
			try
			{
				data.setPendingReprocessSales(pendingReprocessSalesArray);
				MainUI main = (MainUI) this.getBbrUIParent();
				main.showModuleFromCardInfo(ModulesCodes.CUS_REPVTA, data);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private void openPendingReprocessInventory(PendingReprocessInventoryDTO[] pendingReprocessInventory, ControlPanelCardInfo data)
	{
		if (data != null && pendingReprocessInventory != null)
		{
			try
			{
				data.setPendingReprocessInventory(pendingReprocessInventory);
				MainUI main = (MainUI) this.getBbrUIParent();
				main.showModuleFromCardInfo(ModulesCodes.CUS_REPINV, data);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private String formatDate(LocalDateTime date)
	{
		return date != null ? DateTimeFormatter.ofPattern("dd-MM-yyyy").format(date) : "";
	}

	private List<ControlPanelCardInfo> itemsCardsLastInventorySendResultMapper(Object[] list)
	{
		List<ControlPanelCardInfo> result = new ArrayList<>();
		if (list instanceof LastInventorySendDTO[])
		{
			LastInventorySendDTO[] itemList = (LastInventorySendDTO[]) list;
			for (LastInventorySendDTO cardInfoItem : itemList)
			{
				ControlPanelCardInfo info = new ControlPanelCardInfo();
				info.setName(cardInfoItem.getClientname());
				info.setValue(cardInfoItem.getDate() != null ? this.formatDate(cardInfoItem.getDate()) : "");
				info.setLate(cardInfoItem.isLate());
				result.add(info);
			}
		}
		return result;
	}

	private List<ControlPanelCardInfo> itemsCardsResultMapper(Map<String, String> map)
	{
		List<ControlPanelCardInfo> result = new ArrayList<>();
		for (String key : map.keySet())
		{
			String value = map.get(key);
			// crea objeto para la carta
			ControlPanelCardInfo info = new ControlPanelCardInfo();
			info.setName(key);
			info.setValue(value);

			result.add(info);
		}
		return result;
	}

	private List<ControlPanelCardInfo> itemsCardsNumbersResultMapper(Object[] list)
	{
		List<ControlPanelCardInfo> result = new ArrayList<>();

		if (list != null)
		{
			// Ventas atrasadas
			if (list instanceof LateSalesDTO[])
			{
				LateSalesDTO[] itemList = (LateSalesDTO[]) list;
				for (LateSalesDTO cardInfoItem : itemList)
				{
					ControlPanelCardInfo info = new ControlPanelCardInfo();
					info.setName(cardInfoItem.getClientname());
					info.setValue(String.valueOf(cardInfoItem.getCount()));
					info.setClientId(cardInfoItem.getClkey());
					result.add(info);
				}
			}
			else if (list instanceof NotLoadedProductDTO[])
			{
				// Productos sin homologar
				NotLoadedProductDTO[] itemList = (NotLoadedProductDTO[]) list;
				for (NotLoadedProductDTO cardInfoItem : itemList)
				{
					ControlPanelCardInfo info = new ControlPanelCardInfo();
					info.setName(cardInfoItem.getClientname());
					info.setValue(String.valueOf(cardInfoItem.getCount()));
					info.setClientId(cardInfoItem.getClkey());
					result.add(info);
				}
			}
			else if (list instanceof NotLoadedLocalDTO[])
			{
				// Locales sin homologar
				NotLoadedLocalDTO[] itemList = (NotLoadedLocalDTO[]) list;
				for (NotLoadedLocalDTO cardInfoItem : itemList)
				{
					ControlPanelCardInfo info = new ControlPanelCardInfo();
					info.setName(cardInfoItem.getClientname());
					info.setValue(String.valueOf(cardInfoItem.getCount()));
					info.setClientId(cardInfoItem.getClkey());
					result.add(info);
				}
			}
			else if (list instanceof PendingReprocessSalesDTO[])
			{
				// Reproceso ventas pendientes
				PendingReprocessSalesDTO[] itemList = (PendingReprocessSalesDTO[]) list;
				for (PendingReprocessSalesDTO cardInfoItem : itemList)
				{
					ControlPanelCardInfo info = new ControlPanelCardInfo();
					info.setName(cardInfoItem.getClientname());
					info.setValue(String.valueOf(cardInfoItem.getCount()));
					info.setClientId(cardInfoItem.getClkey());
					result.add(info);
				}
			}
			else if (list instanceof PendingReprocessInventoryDTO[])
			{
				// Reproceso ventas pendientes
				PendingReprocessInventoryDTO[] itemList = (PendingReprocessInventoryDTO[]) list;
				for (PendingReprocessInventoryDTO cardInfoItem : itemList)
				{
					ControlPanelCardInfo info = new ControlPanelCardInfo();
					info.setName(cardInfoItem.getClientname());
					info.setValue(String.valueOf(cardInfoItem.getCount()));
					info.setClientId(cardInfoItem.getClkey());
					result.add(info);
				}
			}

		}
		return result;
	}

	private void doInitializeExecuteServiceAssociateProductWork(AssociateProductProviderProductClientInitParamDTO initParamDTO)
	{
		if (initParamDTO != null)
		{
			associateProductWork = new BbrWork<>(this, this.getBbrUIParent(), this, true);
			associateProductWork.addFunction(new Function<Object, BaseResultDTO>()
			{
				@Override
				public BaseResultDTO apply(Object t)
				{
					return executeServiceAssociateProduct(ControlPanelManagement.this.getBbrUIParent(), initParamDTO);
				}
			});

			this.startWaiting();
			this.executeBbrWork(associateProductWork);
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
	private BaseResultDTO executeServiceAssociateProduct(BbrUI bbrUIParent, AssociateProductProviderProductClientInitParamDTO initParamDTO)
	{
		BaseResultDTO result = new BaseResultDTO();

		try
		{
			result = EJBFactory.getCustomerEJBFactory().getCustomerManagerLocal(bbrUIParent).associateProductProviderProductClient(initParamDTO);
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

}
