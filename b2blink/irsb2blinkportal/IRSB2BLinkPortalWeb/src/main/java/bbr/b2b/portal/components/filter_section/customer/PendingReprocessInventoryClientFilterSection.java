package bbr.b2b.portal.components.filter_section.customer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import bbr.b2b.customer.report.classes.PendingReprocessInventoryDTO;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.wrappers.customer.ControlPanelCardInfo;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer.BbrHInputFieldContainerBuilder;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;

public class PendingReprocessInventoryClientFilterSection extends BbrVerticalSection<PendingReprocessInventoryDTO>
{

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	// Constants
	private static final long							serialVersionUID		= -8569131291169324776L;
	private static final String							STYLE_BBR_PANEL_SPACE	= "bbr-panel-space";
	// Components
	private BbrComboBox<PendingReprocessInventoryDTO>	cbx_Clients				= null;
	// Variables
	private CompanyDataDTO								provider				= null;
	private ControlPanelCardInfo									cardInfo				= null;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************
	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public PendingReprocessInventoryClientFilterSection(BbrUI parent, ControlPanelCardInfo cardInfo)
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
	public PendingReprocessInventoryDTO getSectionResult()
	{
		PendingReprocessInventoryDTO result = null;
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
		PendingReprocessInventoryDTO selectedClient = this.cbx_Clients.getSelectedValue();

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
	private BbrComboBox<PendingReprocessInventoryDTO> getClientsComboBox()
	{
		BbrComboBox<PendingReprocessInventoryDTO> result = null;
		result = this.initializeCombobox();
		if (this.cardInfo != null)
		{
			PendingReprocessInventoryDTO[] items = this.cardInfo.getPendingReprocessInventory();
			List<PendingReprocessInventoryDTO> clients = new ArrayList<PendingReprocessInventoryDTO>(Arrays.asList(items));
			clients.add(0, this.getItemDefault());
			Optional<PendingReprocessInventoryDTO> select = clients.stream().filter(e -> e.getClkey().equals(this.cardInfo.getClientId())).findFirst();
			result = this.setComboboxItemsAndSelect(result, clients, select.get());
		}

		return result;

	}

	private BbrComboBox<PendingReprocessInventoryDTO> setComboboxItemsAndSelect(BbrComboBox<PendingReprocessInventoryDTO> result, List<PendingReprocessInventoryDTO> companies, PendingReprocessInventoryDTO selectedItem)
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

	private BbrComboBox<PendingReprocessInventoryDTO> initializeCombobox()
	{
		BbrComboBox<PendingReprocessInventoryDTO> result = new BbrComboBox<>();
		result.setItemCaptionGenerator(PendingReprocessInventoryDTO::getClientname);
		result.setTextInputAllowed(false);
		result.setEmptySelectionAllowed(false);
		result.setWidth("255px");
		result.addStyleName("bbr-filter-fields");
		return result;
	}

	private PendingReprocessInventoryDTO getItemDefault()
	{
		PendingReprocessInventoryDTO defaultValue = new PendingReprocessInventoryDTO();
		defaultValue.setClientname(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "all_upper"));
		defaultValue.setClkey(-1L);
		PendingReprocessInventoryDTO[] items = this.cardInfo != null
				? this.cardInfo.getPendingReprocessInventory() : new PendingReprocessInventoryDTO[0];
		Long pendingReprocessInventoryItemsMapTotal = Arrays.stream(items)
				.collect(Collectors.summingLong(x -> x.getCount()));
		defaultValue.setCount(pendingReprocessInventoryItemsMapTotal);
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
