package bbr.b2b.portal.components.filter_section.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;

import bbr.b2b.customer.report.classes.ClientDTO;
import bbr.b2b.portal.classes.i18n.HasI18n;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.styles.BbrStyles;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer.BbrHInputFieldContainerBuilder;
import bbr.b2b.portal.components.utils.customer.FindClient;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;
import cl.bbr.core.components.widgets.bbrtextfield.client.RestrictRange;

public class ProductCodeFilterSection extends BbrVerticalSection<String> implements HasI18n
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants
	private static final long	serialVersionUID	= 5129425386873577513L;
	private final int			MAX_SITES_SEARCH	= 10;
	// Components
	// private BbrComboBox<ClientDTO> cbx_Clients = null;
	private Button				btn_SearchCompany	= null;
	private HorizontalLayout	pnlSearchPanel		= null;
	// Variables
	private List<ClientDTO>		listOfSites			= new ArrayList<>();
	// private List<ClientDTO> maxSitesList = new ArrayList<>();
	private BbrTextField		txt_Value			= null;
	private CheckBox			chk_All				= null;
	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ProductCodeFilterSection(BbrUI parent)
	{
		super(parent);
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	@Override
	public void initializeView()
	{
		// Label Título
		this.pnlSearchPanel = new HorizontalLayout();
		// Header
		BbrHInputFieldContainer pnlHeader = new BbrHInputFieldContainerBuilder()
				.withWidth("100%")
				.withCaption(this.getI18n("sku_product"))
				.withLabelWidth("220px")
				.withLabelStyle(BbrStyles.BBR_PANEL_SPACE)
				.withComponent(this.pnlSearchPanel)
				.build();
		pnlHeader.addStyleName(BbrStyles.BBR_FILTER_LABEL_HEADER);

		// Seleccion
		// this.updateComboBox();

		this.txt_Value = new BbrTextField();
		this.txt_Value.setMaxLength(50);
		this.txt_Value.setRestrict(RestrictRange.NUMBERS);
		this.txt_Value.setWidth("100%");
		this.txt_Value.addStyleName(BbrStyles.BBR_FILTER_FIELDS);
		this.txt_Value.setEnabled(false);
		// this.txt_Value.setValue(this.getI18n("all"));

		this.chk_All = new CheckBox(this.getI18n("all"));
		this.chk_All.setValue(true);
		this.chk_All.setHeight("24px");
		this.chk_All.addStyleName("margin-top-5");
		this.chk_All.addValueChangeListener((ValueChangeListener<Boolean> & Serializable) e -> this.chkAll_ValueChangeHandler(e));

		HorizontalLayout valueNumber = new HorizontalLayout();
		valueNumber.setMargin(false);
		valueNumber.addComponent(this.txt_Value);
		valueNumber.addComponent(this.chk_All);

		BbrHInputFieldContainer pnlState = new BbrHInputFieldContainerBuilder()
				.withWidth("100%")
				.withCaption(this.getI18n("sku"))
				.withLabelWidth("120px")
				.withLabelStyle(BbrStyles.BBR_PANEL_SPACE)
				.withComponent(valueNumber)
				.build();

		this.setWidth("400px");
		this.setHeight("70px");
		this.addStyleName(BbrStyles.BBR_FILTER_SECTIONS);
		this.addStyleName(BbrStyles.BBR_PANEL_SPACE);
		this.setSpacing(false);
		this.addComponents(pnlHeader, pnlState);

		this.showSearchPnl(true);
	}

	private void chkAll_ValueChangeHandler(ValueChangeEvent<Boolean> e)
	{
		boolean current = (boolean) e.getValue();
		this.txt_Value.setEnabled(!current);
		if (!this.txt_Value.isEnabled())
		{
			this.txt_Value.setValue("");
		}
	}

	@Override
	public String getSectionResult()
	{
		String result = null;

		if (validateData() == null)
		{
			result = this.txt_Value.getValue();
		}

		return result;
	}

	@Override
	public String validateData()
	{
		String result = null;
		if (this.txt_Value.getValue().isEmpty() && !this.chk_All.getValue())
		{
			result = this.getI18n("code_required");
		}
		return result;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private void btn_SearchSite_clickHandler(ClickEvent e)
	{
		if (e != null)
		{
			FindClient winFindSite = new FindClient(this.getBbrUIParent(), this.listOfSites);
			winFindSite.initializeView();
			// winFindSite.addBbrEventListener(this::siteSelectedHandler);
			winFindSite.open(true);
		}
	}

	// private void updateSites(ClientDTO company)
	// {
	//
	// if (this.maxSitesList.contains(company))
	// {
	// this.cbx_Clients.setSelectedItem(company);
	// }
	// else
	// {
	// this.maxSitesList.add(0, company);
	// this.cbx_Clients.setSelectedItem(company);
	// }
	// }

	private void showSearchPnl(boolean show)
	{
		if (this.btn_SearchCompany == null)
		{
			if (show && this.listOfSites.size() > MAX_SITES_SEARCH)
			{
				this.btn_SearchCompany = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "search_sku"));
				this.btn_SearchCompany.addStyleName("link-filter-button");
				this.btn_SearchCompany.addClickListener(this::btn_SearchSite_clickHandler);

				this.pnlSearchPanel.setWidth("160px");
				this.pnlSearchPanel.addComponent(btn_SearchCompany);
				this.pnlSearchPanel.addStyleName("search-panel");
			}

		}
		else if (!show)
		{
			this.btn_SearchCompany = null;
			this.pnlSearchPanel.removeAllComponents();
		}
	}

	// private void siteSelectedHandler(BbrEvent bbrEvent)
	// {
	// if (bbrEvent != null && bbrEvent.getResultObject() != null)
	// {
	// ClientDTO company = (ClientDTO) bbrEvent.getResultObject();
	//
	// this.updateSites(company);
	// }
	// }

	@Override
	public String getI18n(String resource)
	{
		return I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_CUSTOMER, resource);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
