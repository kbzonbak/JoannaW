package bbr.b2b.portal.components.filters.fep;

import java.io.Serializable;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.fep.common.data.classes.ArticleTypeDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsByFilterInitParamDTO;
import bbr.b2b.portal.classes.constants.BbrFilterSectionConstants;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.FilterHeaderUtils;
import bbr.b2b.portal.classes.wrappers.fep.FEPPeriodFilterSectionInfo;
import bbr.b2b.portal.classes.wrappers.fep.FEPProductFilterSectionInfo;
import bbr.b2b.portal.classes.wrappers.fep.ProductsManagementFilterSearch;
import bbr.b2b.portal.components.filter_section.fep.FEPPeriodFilterSection;
import bbr.b2b.portal.components.filter_section.fep.FEPProductFilterSection;
import bbr.b2b.portal.components.filter_section.generic.ProviderFilterSection;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.container.BbrSectionEvent;
import cl.bbr.core.classes.container.BbrSectionEventListener;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.widgets.bbrfilter.BbrFilterContainer;

public class ProductsManagementFilter extends BbrFilterContainer implements Button.ClickListener
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long			serialVersionUID			= 454561307266771625L;

	private ProviderFilterSection		providerFilterSection	= null;
	private FEPProductFilterSection	productFilterSection		= null;
	private FEPPeriodFilterSection	periodFilterSection		= null;
	private Button							btn_FilterSearch			= null;


	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	public ProductsManagementFilter(BbrModule parentModule)
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
			CPItemsByFilterInitParamDTO initParam = this.getInitParam();
			
			ArticleTypeDTO selectedTemplate = (this.productFilterSection.getSectionResult() != null) ? this.productFilterSection.getSectionResult().getTemplate() : null;
			CompanyDataDTO selectedProvider = this.providerFilterSection.getSectionResult();
			
			ProductsManagementFilterSearch filterInfo = new ProductsManagementFilterSearch(selectedProvider, selectedTemplate, initParam);

			bbrFilter.setResultObject(filterInfo);
			dispatchBbrFilterEvent(bbrFilter);
		}
		else
		{
			// Faltan datos en el filtro seleccionado
			ProductsManagementFilter.this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
		}
	}


	public void initializeView()
	{
		// Seccion Producto
		productFilterSection = new FEPProductFilterSection(this.getBbrUIParent());
		productFilterSection.initializeView();

		// Seccion Proveedor
		providerFilterSection = new ProviderFilterSection(this.getBbrUIParent());
		providerFilterSection.addBbrSectionListener(BbrFilterSectionConstants.FS_PROVIDER, (BbrSectionEventListener & Serializable) e -> this.companyChange_Listener(e));
		providerFilterSection.setCompanyDefault(false);
		providerFilterSection.initializeView();

		// Seccion Periodo
		periodFilterSection = new FEPPeriodFilterSection(this.getBbrUIParent());
		periodFilterSection.initializeView();

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
		mainLayout.addComponents(providerFilterSection, productFilterSection, periodFilterSection, pnlSearchButton, pnlFill);
		mainLayout.setExpandRatio(pnlFill, 1F);

		this.addStyleName("bbr-filter");
		this.setWidth("400px");
		this.setHeight("372px");
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
		else if (this.periodFilterSection.validateData() != null)
		{
			errorResult = this.periodFilterSection.validateData();
		}

		return errorResult;
	}


	private BbrFilterEvent getBbrFilterEventObject()
	{
		CompanyDataDTO selectedCompany = this.providerFilterSection.getSectionResult();
		FEPProductFilterSectionInfo productSectionInfo = this.productFilterSection.getSectionResult();
		FEPPeriodFilterSectionInfo periodSectionInfo = this.periodFilterSection.getSectionResult();

		BbrFilterEvent result = new BbrFilterEvent(BbrFilterEvent.FILTER_APPLIED);

		result.setCaption(FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.COMMERCIAL_RESOURCES, "company", true) + selectedCompany.getName());

		String stateCaption = FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.FEP_ATTRIBUTES, "state", false) + productSectionInfo.getStatus().getDescription();
		String templateCaption = FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.FEP_ATTRIBUTES, "lbl_template", false) + productSectionInfo.getTemplate().getDescription();

		String periodCaption = "";

		if (periodSectionInfo.getSelectedStartDate() != null && periodSectionInfo.getSelectedEndDate() != null)
		{
			periodCaption = FilterHeaderUtils.getPeriodCaption(FilterHeaderUtils.FINANCES_RESOURCES, "period", periodSectionInfo.getSelectedStartDate(),
			periodSectionInfo.getSelectedEndDate());
		}

		result.setSecondaryCaption(stateCaption + templateCaption + periodCaption);

		return result;
	}


	private CPItemsByFilterInitParamDTO getInitParam()
	{
		CPItemsByFilterInitParamDTO initParam = new CPItemsByFilterInitParamDTO();
		CompanyDataDTO selectedCompany = this.providerFilterSection.getSectionResult();
		FEPProductFilterSectionInfo productSectionInfo = this.productFilterSection.getSectionResult();
		FEPPeriodFilterSectionInfo periodSectionInfo = this.periodFilterSection.getSectionResult();
		
		initParam.setProviderid(selectedCompany != null && selectedCompany.getId() != null ? selectedCompany.getId() : -1L);
		initParam.setProvidercode(selectedCompany != null && selectedCompany.getRut() != null ? selectedCompany.getRut() : " ");
		initParam.setStatus(productSectionInfo.getStatus().getId());
		initParam.setSkufilter(productSectionInfo.getSku());
		initParam.setTradefilter(productSectionInfo.getTradeMark());
		initParam.setArticletypeid(productSectionInfo.getTemplate() != null && productSectionInfo.getTemplate().getId() != null ? productSectionInfo.getTemplate().getId() : -1L);
		
		if (periodSectionInfo.getDateOption().getId() == 0)
		{
			initParam.setCreatedsince(periodSectionInfo.getSelectedStartDate());
			initParam.setCreateduntil(periodSectionInfo.getSelectedEndDate());
		}
		else if (periodSectionInfo.getDateOption().getId() == 1)
		{
			initParam.setModifiedsince(periodSectionInfo.getSelectedStartDate());
			initParam.setModifieduntil(periodSectionInfo.getSelectedEndDate());
		}

		initParam.setWithdetails(false);

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
		this.productFilterSection.setSectionData((CompanyDataDTO) e.getResultObject());
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENT HANDLERS
	// =====================================================================================================================================

}
