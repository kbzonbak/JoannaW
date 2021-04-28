package bbr.b2b.portal.components.filters.customer_service;

import java.io.Serializable;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.customer.report.classes.PendingReprocessInventoryDTO;
import bbr.b2b.customer.report.classes.PendingReprocessSalesDTO;
import bbr.b2b.portal.classes.constants.BbrFilterSectionConstants;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.FilterHeaderUtils;
import bbr.b2b.portal.classes.wrappers.customer.ControlPanelCardInfo;
import bbr.b2b.portal.classes.wrappers.customer.PendingReprocessInventorySectionInfo;
import bbr.b2b.portal.classes.wrappers.customer.PendingReprocessSalesSectionInfo;
import bbr.b2b.portal.components.filter_section.customer.PendingReprocessInventoryClientFilterSection;
import bbr.b2b.portal.components.filter_section.customer.PendingReprocessSalesClientFilterSection;
import bbr.b2b.portal.components.filter_section.generic.ProviderFilterSection;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.modules.customer.ControlPanelManagement;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.container.BbrSectionEvent;
import cl.bbr.core.classes.container.BbrSectionEventListener;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.widgets.bbrfilter.BbrFilterContainer;

public class PendingReprocessFilter extends BbrFilterContainer implements Button.ClickListener
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long								serialVersionUID				= 8489213839800376590L;
	private ProviderFilterSection							providerFilterSection			= null;
	private PendingReprocessSalesClientFilterSection		clientSalesFilterSection		= null;
	private PendingReprocessInventoryClientFilterSection	clientInventoryFilterSection	= null;
	private boolean											isSalesOrInventory				= true;
	private ControlPanelCardInfo							cardInfo						= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	public PendingReprocessFilter(BbrModule parentModule, ControlPanelCardInfo cardInfo)
	{
		super(parentModule);
		this.cardInfo = cardInfo;
	}
	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PUBLIC METHODS
	// =====================================================================================================================================

	@Override
	public void buttonClick(ClickEvent event)
	{
		String errorMsg = this.validateData();

		if ((errorMsg == null) || (errorMsg.length() == 0))
		{
			BbrFilterEvent bbrFilter = this.getBbrFilterEventObject();
			dispatchBbrFilterEvent(bbrFilter);
		}
		else
		{
			// Faltan datos en el filtro seleccionado
			PendingReprocessFilter.this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
		}
	}

	public void initializeView()
	{
		// Seccion Proveedor
		providerFilterSection = new ProviderFilterSection(this.getBbrUIParent());
		providerFilterSection.initializeView();
		providerFilterSection.addBbrSectionListener(BbrFilterSectionConstants.FS_PROVIDER, (BbrSectionEventListener & Serializable) e -> this.companyChange_Listener(e));

		this.setIsSalesOrInventory();
		// Secccion Clientes
		if (isSalesOrInventory)
		{
			clientSalesFilterSection = new PendingReprocessSalesClientFilterSection(this.getBbrUIParent(), this.cardInfo);
			clientSalesFilterSection.initializeView();
		}
		else
		{
			clientInventoryFilterSection = new PendingReprocessInventoryClientFilterSection(this.getBbrUIParent(), this.cardInfo);
			clientInventoryFilterSection.initializeView();

		}

		// Botón de Búsqueda
		Button btn_FilterSearch = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "generate_report"), this);
		btn_FilterSearch.setIcon(VaadinIcons.CHECK_CIRCLE_O);
		btn_FilterSearch.setStyleName("btn-filter-search");
		btn_FilterSearch.setClickShortcut(KeyCode.ENTER);

		VerticalLayout pnlSearchButton = new VerticalLayout();
		pnlSearchButton.setWidth("400px");
		pnlSearchButton.addStyleName("bbr-panel-space");
		pnlSearchButton.setSpacing(false);
		pnlSearchButton.setMargin(false);
		pnlSearchButton.addComponents(btn_FilterSearch);
		pnlSearchButton.setComponentAlignment(btn_FilterSearch, Alignment.BOTTOM_RIGHT);

		VerticalLayout pnlFill = new VerticalLayout();
		pnlFill.setMargin(false);

		// Main Layout

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addStyleName("bbr-filter-main-panel");
		mainLayout.setSizeFull();
		mainLayout.setMargin(false);
		mainLayout.setSpacing(false);
		mainLayout.addComponent(providerFilterSection);
		if (this.isSalesOrInventory)
		{
			mainLayout.addComponent(clientSalesFilterSection);
		}
		else
		{
			mainLayout.addComponent(clientInventoryFilterSection);
		}
		mainLayout.addComponent(pnlSearchButton);
		mainLayout.addComponent(pnlFill);
		mainLayout.setExpandRatio(pnlFill, 1F);

		this.addStyleName("bbr-filter");
		this.setWidth("400px");
		this.setHeight("170px");
		this.addComponent(mainLayout);
	}

	private void setIsSalesOrInventory()
	{
		if (this.cardInfo != null && this.cardInfo.getSelectedPanel().equals(ControlPanelManagement.PENDING_REPROCESS_SALES))
		{
			this.isSalesOrInventory = true;
		}
		else if (this.cardInfo != null && this.cardInfo.getSelectedPanel().equals(ControlPanelManagement.PENDING_REPROCESS_INVENTORY))
		{
			this.isSalesOrInventory = false;
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PUBLIC METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private String validateData()
	{
		String errorResult = null;

		if (this.providerFilterSection.validateData() != null)
		{
			errorResult = this.providerFilterSection.validateData();
		}
		else if (this.isSalesOrInventory && this.clientSalesFilterSection.validateData() != null)
		{
			errorResult = this.clientSalesFilterSection.validateData();
		}
		else if (!this.isSalesOrInventory && this.clientInventoryFilterSection.validateData() != null)
		{
			errorResult = this.clientInventoryFilterSection.validateData();
		}

		return errorResult;
	}

	private BbrFilterEvent getBbrFilterEventObject()
	{
		CompanyDataDTO selectedCompany = this.providerFilterSection.getSectionResult();
		BbrFilterEvent result = new BbrFilterEvent(BbrFilterEvent.FILTER_APPLIED);
		if (this.isSalesOrInventory)
		{
			PendingReprocessSalesDTO selectedClient = this.clientSalesFilterSection.getSectionResult();

			result.setCaption(FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.COMMERCIAL_RESOURCES, "company", true)
					+ selectedCompany.getName()
					+ FilterHeaderUtils.CAPTION_PREFIX
					+ FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.CUSTOMER_RESOURCES, "client", true)
					+ selectedClient.getClientname());
			PendingReprocessSalesSectionInfo info = new PendingReprocessSalesSectionInfo();
			info.setSelectedCompany(selectedCompany);
			info.setSelectedClient(selectedClient);

			result.setResultObject(info);
		}
		else
		{
			PendingReprocessInventoryDTO selectedClient = this.clientInventoryFilterSection.getSectionResult();

			result.setCaption(FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.COMMERCIAL_RESOURCES, "company", true)
					+ selectedCompany.getName()
					+ FilterHeaderUtils.CAPTION_PREFIX
					+ FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.CUSTOMER_RESOURCES, "client", true)
					+ selectedClient.getClientname());
			PendingReprocessInventorySectionInfo info = new PendingReprocessInventorySectionInfo();
			info.setSelectedCompany(selectedCompany);
			info.setSelectedClient(selectedClient);

			result.setResultObject(info);
		}

		return result;
	}

	private void companyChange_Listener(BbrSectionEvent e)
	{
		if (this.isSalesOrInventory)
		{
			this.clientSalesFilterSection.setSectionData((CompanyDataDTO) e.getResultObject());
		}
		else
		{
			this.clientInventoryFilterSection.setSectionData((CompanyDataDTO) e.getResultObject());
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

}
