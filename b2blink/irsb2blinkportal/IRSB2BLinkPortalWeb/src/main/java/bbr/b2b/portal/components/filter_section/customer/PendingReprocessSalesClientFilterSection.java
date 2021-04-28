package bbr.b2b.portal.components.filter_section.customer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import bbr.b2b.customer.report.classes.PendingReprocessSalesDTO;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.wrappers.customer.ControlPanelCardInfo;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer.BbrHInputFieldContainerBuilder;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;

public class PendingReprocessSalesClientFilterSection extends BbrVerticalSection<PendingReprocessSalesDTO>
{

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	// Constants
	private static final String						STYLE_BBR_PANEL_SPACE	= "bbr-panel-space";
	private static final long						serialVersionUID		= -8569131291169324776L;
	// Components
	private BbrComboBox<PendingReprocessSalesDTO>	cbx_Clients				= null;
	// Variables
	private ControlPanelCardInfo								cardInfo				= null;
	private CompanyDataDTO							provider				= null;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************
	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public PendingReprocessSalesClientFilterSection(BbrUI parent, ControlPanelCardInfo cardInfo)
	{
		super(parent);
		this.cardInfo = cardInfo;
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	@Override
	public void initializeView()
	{
		Label lbl_ProviderHeader = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "client"));
		lbl_ProviderHeader.addStyleName("bbr-panel-space");
		lbl_ProviderHeader.setWidth("220px");

		HorizontalLayout pnlProvidersHeader = new HorizontalLayout();
		pnlProvidersHeader.addStyleName("bbr-filter-label-header");
		pnlProvidersHeader.addComponents(lbl_ProviderHeader);
		pnlProvidersHeader.setWidth("100%");

		this.cbx_Clients = this.getClientsComboBox();

		BbrHInputFieldContainer pnlOriginLocalSubsection = new BbrHInputFieldContainerBuilder()
				.withCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "client"))
				.withLabelWidth("120px")
				.withComponent(this.cbx_Clients)
				.build();
		pnlOriginLocalSubsection.addStyleName(STYLE_BBR_PANEL_SPACE);

		HorizontalLayout pnlProviderSubSection = new HorizontalLayout();
		pnlProviderSubSection.setWidth("100%");
		pnlProviderSubSection.addComponents(pnlOriginLocalSubsection);
		pnlProviderSubSection.setExpandRatio(pnlOriginLocalSubsection, 1F);
		pnlProviderSubSection.addStyleName("bbr-panel-space");

		this.setWidth("400px");
		this.addStyleName("bbr-filter-sections");
		this.addStyleName("bbr-panel-space");
		this.setSpacing(false);
		this.addComponents(pnlProvidersHeader, pnlProviderSubSection);
	}

	@Override
	public PendingReprocessSalesDTO getSectionResult()
	{
		PendingReprocessSalesDTO result = null;
		if (this.cbx_Clients.getSelectedValue() != null)
		{
			result = this.cbx_Clients.getSelectedValue();
		}
		return result;
	}

	@Override
	public String validateData()
	{
		String result = null;
		PendingReprocessSalesDTO selectedClient = this.cbx_Clients.getSelectedValue();

		if (selectedClient == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "provider_requires");
		}

		return result;
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
	private BbrComboBox<PendingReprocessSalesDTO> getClientsComboBox()
	{
		BbrComboBox<PendingReprocessSalesDTO> result = null;
		result = this.initializeCombobox();
		if (this.cardInfo != null)
		{
			PendingReprocessSalesDTO[] items = this.cardInfo.getPendingReprocessSales();
			List<PendingReprocessSalesDTO> clients = new ArrayList<PendingReprocessSalesDTO>(Arrays.asList(items));
			clients.add(0, this.getItemDefault());
			Optional<PendingReprocessSalesDTO> select = clients.stream().filter(e -> e.getClkey().equals(this.cardInfo.getClientId())).findFirst();
			result = this.setComboboxItemsAndSelect(result, clients, select.get());
		}

		return result;

	}

	private BbrComboBox<PendingReprocessSalesDTO> setComboboxItemsAndSelect(BbrComboBox<PendingReprocessSalesDTO> result, List<PendingReprocessSalesDTO> companies, PendingReprocessSalesDTO selectedItem)
	{
		result.setItems(companies);
		if (selectedItem != null)
		{
			result.setSelectedItem(selectedItem);
		}
		else
		{
			result.selectFirstItem();
		}
		return result;
	}

	private BbrComboBox<PendingReprocessSalesDTO> initializeCombobox()
	{
		BbrComboBox<PendingReprocessSalesDTO> result = new BbrComboBox<>();
		result.setItemCaptionGenerator(PendingReprocessSalesDTO::getClientname);
		result.setTextInputAllowed(false);
		result.setEmptySelectionAllowed(false);
		result.setWidth("255px");
		result.addStyleName("bbr-filter-fields");
		return result;
	}

	private PendingReprocessSalesDTO getItemDefault()
	{
		PendingReprocessSalesDTO defaultValue = new PendingReprocessSalesDTO();
		defaultValue.setClientname(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "all_upper"));
		defaultValue.setClkey(-1L);
		PendingReprocessSalesDTO[] items = this.cardInfo != null
				? this.cardInfo.getPendingReprocessSales() : new PendingReprocessSalesDTO[0];
		Long pendingReprocessSalesItemsMapTotal = Arrays.stream(items)
				.collect(Collectors.summingLong(x -> x.getCount()));
		defaultValue.setCount(pendingReprocessSalesItemsMapTotal);
		return defaultValue;
	}

	@Override
	public void setSectionData(Object data)
	{
		if (this.provider != (CompanyDataDTO) data)
		{
			this.provider = (CompanyDataDTO) data;
		}
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
