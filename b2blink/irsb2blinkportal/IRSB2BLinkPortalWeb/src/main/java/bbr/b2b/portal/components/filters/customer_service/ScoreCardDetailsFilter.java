package bbr.b2b.portal.components.filters.customer_service;

import static bbr.b2b.portal.classes.utils.customer.CustomerSearchCriteriaFilterUtils.CLIENT;
import static bbr.b2b.portal.classes.utils.customer.CustomerSearchCriteriaFilterUtils.ORDER_NUMBER;
import static bbr.b2b.portal.classes.utils.customer.CustomerSearchCriteriaFilterUtils.ORDER_STATE;
import static bbr.b2b.portal.classes.utils.customer.CustomerSearchCriteriaFilterUtils.SHIPPING_DATE;

import java.io.Serializable;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.constants.BbrFilterSectionConstants;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.DateFormats;
import bbr.b2b.portal.classes.utils.app.FilterHeaderUtils;
import bbr.b2b.portal.classes.wrappers.customer.CustomerCriterionDTO;
import bbr.b2b.portal.classes.wrappers.customer.CustomerOrdersDetails;
import bbr.b2b.portal.components.filter_section.customer.ScoreCardDetailCriterionFilterSection;
import bbr.b2b.portal.components.filter_section.generic.ProviderFilterSection;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.container.BbrSectionEvent;
import cl.bbr.core.classes.container.BbrSectionEventListener;
import cl.bbr.core.classes.events.BbrFilterEvent;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.widgets.bbrfilter.BbrFilterContainer;

public class ScoreCardDetailsFilter extends BbrFilterContainer implements Button.ClickListener
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long						serialVersionUID		= 8489213839800376590L;
	private ProviderFilterSection					providerFilterSection	= null;
	private ScoreCardDetailCriterionFilterSection	criterionFilterSection	= null;
	private String									stateSelected			= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	public ScoreCardDetailsFilter(BbrModule parentModule, String stateSelected)
	{
		super(parentModule);
		this.stateSelected = stateSelected;
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
			ScoreCardDetailsFilter.this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
		}
	}

	public void initializeView()
	{
		// Seccion Proveedor
		providerFilterSection = new ProviderFilterSection(this.getBbrUIParent());
		providerFilterSection.initializeView();
		providerFilterSection.addBbrSectionListener(BbrFilterSectionConstants.FS_PROVIDER, (BbrSectionEventListener & Serializable) e -> this.companyChange_Listener(e));
		providerFilterSection.setSearchProvider(true);

		// Sección Criterio
		criterionFilterSection = new ScoreCardDetailCriterionFilterSection(this.getBbrUIParent(), providerFilterSection.getSectionResult(), this.stateSelected);
		criterionFilterSection.initializeView();

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
		mainLayout.addComponent(criterionFilterSection);
		mainLayout.addComponent(pnlSearchButton);
		mainLayout.addComponent(pnlFill);
		mainLayout.setExpandRatio(pnlFill, 1F);

		this.addStyleName("bbr-filter");
		this.setWidth("400px");
		this.setHeight("210px");
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
		if (this.criterionFilterSection.validateData() != null)
		{
			errorResult = this.criterionFilterSection.validateData();
		}

		return errorResult;
	}

	private BbrFilterEvent getBbrFilterEventObject()
	{
		CustomerOrdersDetails itemResult = new CustomerOrdersDetails();

		CompanyDataDTO selectedCompany = this.providerFilterSection.getSectionResult();
		CustomerCriterionDTO selectedCriterion = this.criterionFilterSection.getSectionResult();
		BbrFilterEvent result = new BbrFilterEvent(BbrFilterEvent.FILTER_APPLIED);

		result.setCaption(FilterHeaderUtils.getFilterCaption(FilterHeaderUtils.COMMERCIAL_RESOURCES, "provider", true) + selectedCompany.getName());

		String filterTypeCaption = "";
		switch (selectedCriterion.getTypeSearch().getId())
		{
			case CLIENT:
				filterTypeCaption = selectedCriterion.getTypeSearch().getDescription() + FilterHeaderUtils.CAPTION_SUFIX +
						selectedCriterion.getSite().getRetailname();
				break;
			case SHIPPING_DATE:
				filterTypeCaption = selectedCriterion.getTypeSearch().getDescription() + FilterHeaderUtils.CAPTION_SUFIX +
						DateFormats.formatDate(selectedCriterion.getStartDateTime())
						+ FilterHeaderUtils.CAPTION_PREFIX +
						DateFormats.formatDate(selectedCriterion.getEndDateTime());
				break;
			case ORDER_NUMBER:
				filterTypeCaption = selectedCriterion.getTypeSearch().getDescription() + FilterHeaderUtils.CAPTION_SUFIX +
						selectedCriterion.getOrderNumber();
				break;
			case ORDER_STATE:
				filterTypeCaption = selectedCriterion.getTypeSearch().getDescription() + FilterHeaderUtils.CAPTION_SUFIX +
						selectedCriterion.getOrderState().getDescription();
				break;

		}

		result.setSecondaryCaption(FilterHeaderUtils.CAPTION_PREFIX + filterTypeCaption);
		itemResult.setCompany(selectedCompany);
		itemResult.setCriterion(selectedCriterion);
		result.setResultObject(itemResult);
		return result;
	}

	private void companyChange_Listener(BbrSectionEvent e)
	{
		this.criterionFilterSection.setSectionData((CompanyDataDTO) e.getResultObject());
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
