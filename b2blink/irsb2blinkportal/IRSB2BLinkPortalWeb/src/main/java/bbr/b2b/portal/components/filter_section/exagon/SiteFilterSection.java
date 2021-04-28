package bbr.b2b.portal.components.filter_section.exagon;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import bbr.b2b.portal.classes.basics.SiteExagoBIDTO;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.components.basics.BbrUI;

public class SiteFilterSection extends BbrVerticalSection<SiteExagoBIDTO>
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	/**
	 * 
	 */
	private static final long serialVersionUID = -3535793338089595812L;

	public SiteFilterSection(BbrUI parent)
	{
		super(parent);
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private ComboBox<SiteExagoBIDTO> cbx_Sites = null;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	@Override
	public void initializeView()
	{
		Label lbl_ProviderHeader = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "site"));
		lbl_ProviderHeader.addStyleName("bbr-panel-space");
		lbl_ProviderHeader.setWidth("220px");

		HorizontalLayout pnlProvidersHeader = new HorizontalLayout();
		pnlProvidersHeader.addStyleName("bbr-filter-label-header");
		pnlProvidersHeader.addComponents(lbl_ProviderHeader);
		pnlProvidersHeader.setExpandRatio(lbl_ProviderHeader, 1F);
		pnlProvidersHeader.setWidth("100%");

		Label lbl_Provider = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "site"));
		lbl_Provider.setWidth("120px");

		cbx_Sites = this.getSitesCombBox();

		HorizontalLayout pnlProviderSubSection = new HorizontalLayout();
		pnlProviderSubSection.setWidth("100%");
		pnlProviderSubSection.addComponents(lbl_Provider, this.cbx_Sites);
		pnlProviderSubSection.setExpandRatio(this.cbx_Sites, 1F);
		pnlProviderSubSection.addStyleName("bbr-panel-space");

		this.setWidth("400px");
		this.addStyleName("bbr-filter-sections");
		this.addStyleName("bbr-panel-space");
		this.setSpacing(false);
		this.addComponents(pnlProvidersHeader, pnlProviderSubSection);
	}

	@Override
	public SiteExagoBIDTO getSectionResult()
	{
		return this.cbx_Sites.getSelectedItem().orElse(null);
	}

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private ComboBox<SiteExagoBIDTO> getSitesCombBox()
	{
		ComboBox<SiteExagoBIDTO> result = null;
		result = new ComboBox<SiteExagoBIDTO>();
		result.setTextInputAllowed(false);
		result.setItemCaptionGenerator(SiteExagoBIDTO::getName);
		result.setEmptySelectionAllowed(false);
		result.setWidth("255px");
		result.addStyleName("bbr-filter-fields");
		List<SiteExagoBIDTO> items = new ArrayList<SiteExagoBIDTO>();
		Map<String, Integer> mapSites = this.getMapSites();
		items = mapSites.keySet().stream().map(item -> new SiteExagoBIDTO(item, mapSites.get(item))).collect(Collectors.toList());
		result.setItems(items);
		result.setSelectedItem(items.get(0));
		result.setEnabled(true);
		return result;
	}

	private Map<String, Integer> getMapSites()
	{
		Map<String, Integer> mapSites = new LinkedHashMap<>();
		mapSites.put("TODOS", 0);
		mapSites.put("ABCDIN", 1);
		mapSites.put("INKAFARMA", 3);
		mapSites.put("UNIMARC (con db2)", 4);
		mapSites.put("PARIS CHILE", 5);
		mapSites.put("FASA CHILE", 13);
		mapSites.put("UNIMARC", 15);
		mapSites.put("SPSA", 17);
		mapSites.put("OECHSLE", 18);
		mapSites.put("PARIS CHILE (HISTORICO)", 19);
		mapSites.put("BENAVIDES (HISTORICO)", 22);
		mapSites.put("PARIS PERU", 23);
		mapSites.put("HITES", 25);
		mapSites.put("CONSTRUMART", 26);
		mapSites.put("SUPER ARGENTINA", 27);
		mapSites.put("PROMART", 30);
		mapSites.put("BENAVIDES", 31);
		mapSites.put("SHOPPING MOBILE", 32);
		mapSites.put("SUPER CL", 33);
		mapSites.put("MI FARMA", 34);
		mapSites.put("OTRAS SOCIEDADES", 37);
		mapSites.put("LA POLAR", 38);
		mapSites.put("CENCOSUD SUPER PE 2.0", 40);
		mapSites.put("CENCOSUD SUPER COLOMBIA", 44);
		return mapSites;
	}

	@Override
	public String validateData()
	{
		return null;
	}

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
