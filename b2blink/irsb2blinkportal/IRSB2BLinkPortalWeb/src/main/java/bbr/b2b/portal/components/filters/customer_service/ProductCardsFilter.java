package bbr.b2b.portal.components.filters.customer_service;

import java.io.Serializable;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.customer.report.classes.ClientDTO;
import bbr.b2b.customer.report.classes.ProductReportInitParamDTO;
import bbr.b2b.portal.classes.constants.BbrFilterSectionConstants;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.FilterHeaderUtils;
import bbr.b2b.portal.components.filter_section.customer.ClientsFilterSection;
import bbr.b2b.portal.components.filter_section.customer.ProductCodeFilterSection;
// import bbr.b2b.portal.classes.utils.commercial.CategoryUtils;
// import bbr.b2b.portal.classes.utils.commercial.CommercialUtils;
// import bbr.b2b.portal.classes.wrappers.commercial.ProductFilterSectionInfo;
// import
// bbr.b2b.portal.components.filter_section.commercial.ProductFilterSection;
import bbr.b2b.portal.components.filter_section.generic.ProviderFilterSection;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.container.BbrSectionEvent;
import cl.bbr.core.classes.container.BbrSectionEventListener;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.widgets.bbrfilter.BbrFilterContainer;

public class ProductCardsFilter extends BbrFilterContainer implements Button.ClickListener
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long			serialVersionUID			= 8489213839800376590L;
	private ProviderFilterSection		providerFilterSection		= null;
	private ClientsFilterSection		clientsFilterSection		= null;
	private ProductCodeFilterSection	productCodeFilterSection	= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	public ProductCardsFilter(BbrModule parentModule)
	{
		super(parentModule);
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
			ProductReportInitParamDTO initParam = this.getInitParam();
			bbrFilter.setResultObject(initParam);
			dispatchBbrFilterEvent(bbrFilter);
		}
		else
		{
			// Faltan datos en el filtro seleccionado
			ProductCardsFilter.this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
		}
	}

	public void initializeView()
	{
		// Seccion Proveedor
		providerFilterSection = new ProviderFilterSection(this.getBbrUIParent());
		providerFilterSection.initializeView();
		providerFilterSection.addBbrSectionListener(BbrFilterSectionConstants.FS_PROVIDER, (BbrSectionEventListener & Serializable) e -> this.companyChange_Listener(e));

		clientsFilterSection = new ClientsFilterSection(this.getBbrUIParent());
		clientsFilterSection.initializeView();

		productCodeFilterSection = new ProductCodeFilterSection(this.getBbrUIParent());
		productCodeFilterSection.initializeView();

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
		mainLayout.addComponent(clientsFilterSection);
		mainLayout.addComponent(productCodeFilterSection);
		mainLayout.addComponent(pnlSearchButton);
		mainLayout.addComponent(pnlFill);
		mainLayout.setExpandRatio(pnlFill, 1F);

		this.addStyleName("bbr-filter");
		this.setWidth("400px");
		this.setHeight("245px");
		this.addComponent(mainLayout);
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
		else if (this.clientsFilterSection.validateData() != null)
		{
			errorResult = this.clientsFilterSection.validateData();
		}
		else if (this.productCodeFilterSection.validateData() != null)
		{
			errorResult = this.productCodeFilterSection.validateData();
		}
		return errorResult;
	}

	private BbrFilterEvent getBbrFilterEventObject()
	{
		CompanyDataDTO selectedCompany = this.providerFilterSection.getSectionResult();
		ClientDTO selectedClient = this.clientsFilterSection.getSectionResult();
		String selectedSKU = this.productCodeFilterSection.getSectionResult();

		BbrFilterEvent result = new BbrFilterEvent(BbrFilterEvent.FILTER_APPLIED);

		result.setCaption(FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.CUSTOMER_RESOURCES, "provider", true)
				+ selectedCompany.getName());

		String clientCaption = FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.CUSTOMER_RESOURCES, "client", false) +
				selectedClient.getClientname();

		String productProviderCode = FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.CUSTOMER_RESOURCES, "sku_product", false) +
				(selectedSKU.isEmpty() ? I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "all") : selectedSKU);

		result.setSecondaryCaption(clientCaption + productProviderCode);

		return result;
	}

	private ProductReportInitParamDTO getInitParam()
	{
		CompanyDataDTO selectedCompany = this.providerFilterSection.getSectionResult();
		ClientDTO selectedClient = this.clientsFilterSection.getSectionResult();
		String selectedSKU = this.productCodeFilterSection.getSectionResult();

		ProductReportInitParamDTO initParam = new ProductReportInitParamDTO();
		initParam.setLocale(this.getBbrUIParent().getLocale());
		initParam.setPvcode((selectedCompany != null) ? selectedCompany.getRut() : null);

		initParam.setProductprovidercode(selectedSKU.isEmpty() ? null : selectedSKU);
		initParam.setClkey(selectedClient.getId());
		return initParam;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

	private void companyChange_Listener(BbrSectionEvent e)
	{
		// this.productFilterSection.setSectionData((CompanyDataDTO)
		// e.getResultObject());
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

}
