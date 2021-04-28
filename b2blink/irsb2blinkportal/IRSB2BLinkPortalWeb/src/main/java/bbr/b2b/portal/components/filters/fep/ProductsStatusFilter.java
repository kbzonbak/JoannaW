package bbr.b2b.portal.components.filters.fep;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.fep.cp.data.classes.CPItemsByStatusInitParamDTO;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.FilterHeaderUtils;
import bbr.b2b.portal.classes.wrappers.fep.ProductStateMultiFilterSectionInfo;
import bbr.b2b.portal.classes.wrappers.fep.ProductsStatusFilterSearch;
import bbr.b2b.portal.components.filter_section.fep.ProductStateMultiFilterSection;
import bbr.b2b.portal.components.filter_section.generic.ProviderFilterSection;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.FEPConstants;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.widgets.bbrfilter.BbrFilterContainer;

public class ProductsStatusFilter extends BbrFilterContainer implements Button.ClickListener
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long					serialVersionUID			= 454561307266771625L;

	private ProviderFilterSection				providerFilterSection	= null;
	private ProductStateMultiFilterSection	productFilterSection		= null;
	private Button									btn_FilterSearch			= null;


	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	public ProductsStatusFilter(BbrModule parentModule)
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
			ProductsStatusFilterSearch filterSearch = this.getFilterSearch();
			bbrFilter.setResultObject(filterSearch);
			dispatchBbrFilterEvent(bbrFilter);
		}
		else
		{
			// Faltan datos en el filtro seleccionado
			ProductsStatusFilter.this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
		}
	}


	public void initializeView()
	{
		// Seccion Proveedor
		providerFilterSection = new ProviderFilterSection(this.getBbrUIParent());
		providerFilterSection.setCompanyDefault(false);
		providerFilterSection.initializeView();

		// Seccion Producto (Estado/SKU/Fecha Creacion/Fecha Modificación)
		productFilterSection = new ProductStateMultiFilterSection(this.getBbrUIParent());
		productFilterSection.initializeView();

		// Botón de Búsqueda
		btn_FilterSearch = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "generate_report"), this);
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
		mainLayout.addComponents(providerFilterSection, productFilterSection, pnlSearchButton, pnlFill);
		mainLayout.setExpandRatio(pnlFill, 1F);

		this.addStyleName("bbr-filter");
		this.setWidth("400px");
		this.setHeight("195px");
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
		else if (this.productFilterSection.validateData() != null)
		{
			errorResult = this.productFilterSection.validateData();
		}

		return errorResult;
	}


	private BbrFilterEvent getBbrFilterEventObject()
	{
		CompanyDataDTO selectedCompany = this.providerFilterSection.getSectionResult();
		ProductStateMultiFilterSectionInfo productSectionInfo = this.productFilterSection.getSectionResult();

		BbrFilterEvent result = new BbrFilterEvent(BbrFilterEvent.FILTER_APPLIED);

		result.setCaption(FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.COMMERCIAL_RESOURCES, "company", true) + selectedCompany.getName());

		if(productSectionInfo.getFilterType().getId().equals(FEPConstants.STATE_FILTER_VALUE))
		{
			String stateCaption = FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.FEP_ATTRIBUTES, "state", false) + productSectionInfo.getStatus().getDescription();	
			result.setSecondaryCaption(stateCaption);
		}
		else if(productSectionInfo.getFilterType().getId().equals(FEPConstants.SKU_FILTER_VALUE))
		{
			String skuCaption = FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.FEP_ATTRIBUTES, "lbl_sku", false) + productSectionInfo.getSku();	
			result.setSecondaryCaption(skuCaption);
		}
		else if(productSectionInfo.getFilterType().getId().equals(FEPConstants.UPDATE_DATE_FILTER_VALUE) || productSectionInfo.getFilterType().getId().equals(FEPConstants.CREATION_DATE_FILTER_VALUE))
		{
			if (productSectionInfo.getSelectedStartDate() != null && productSectionInfo.getSelectedEndDate() != null)
			{
				String periodCaption = FilterHeaderUtils.getPeriodCaption(FilterHeaderUtils.FINANCES_RESOURCES, "period",
				productSectionInfo.getSelectedStartDate(),
				productSectionInfo.getSelectedEndDate());
				result.setSecondaryCaption(periodCaption);
			}
		}
		
		return result;
	}


	private ProductsStatusFilterSearch getFilterSearch()
	{
		CPItemsByStatusInitParamDTO initParam = new CPItemsByStatusInitParamDTO();
		CompanyDataDTO selectedCompany = this.providerFilterSection.getSectionResult();
		ProductStateMultiFilterSectionInfo productSectionInfo = this.productFilterSection.getSectionResult();

		initParam.setProviderid(selectedCompany.getId());
		initParam.setProvidercode(selectedCompany.getRut());
		
		if (productSectionInfo.getFilterType().getId().equals(FEPConstants.STATE_FILTER_VALUE))
		{
			initParam.setStatus((productSectionInfo.getStatus() != null) ? productSectionInfo.getStatus().getId() : null);
		}
		else if(productSectionInfo.getFilterType().getId().equals(FEPConstants.SKU_FILTER_VALUE))
		{
			initParam.setSkus(productSectionInfo.getSku());
		}
		else if (productSectionInfo.getFilterType().getId().equals(FEPConstants.CREATION_DATE_FILTER_VALUE))
		{
			initParam.setCreatedsince(productSectionInfo.getSelectedStartDate());
			initParam.setCreateduntil(productSectionInfo.getSelectedEndDate());
		}
		else if (productSectionInfo.getFilterType().getId().equals(FEPConstants.UPDATE_DATE_FILTER_VALUE))
		{
			initParam.setModifiedsince(productSectionInfo.getSelectedStartDate());
			initParam.setModifieduntil(productSectionInfo.getSelectedEndDate());
		}

		initParam.setWithdetails(false);

		ProductsStatusFilterSearch result = new ProductsStatusFilterSearch(selectedCompany, initParam);

		return result;
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
