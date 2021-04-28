package bbr.b2b.portal.components.filter_section.customer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;

import bbr.b2b.customer.report.classes.ClientArrayResultDTO;
import bbr.b2b.customer.report.classes.ClientDTO;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.HasI18n;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.styles.BbrStyles;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer.BbrHInputFieldContainerBuilder;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.utils.customer.FindClient;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;

public class ClientsFilterSection extends BbrVerticalSection<ClientDTO> implements HasI18n
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants
	private static final long		serialVersionUID	= 5129425386873577513L;
	private final int				MAX_SITES_SEARCH	= 10;
	// Components
	private BbrComboBox<ClientDTO>	cbx_Clients			= null;
	private Button					btn_SearchCompany	= null;
	private HorizontalLayout		pnlSearchPanel		= null;
	// Variables
	private List<ClientDTO>			listOfSites			= new ArrayList<>();
	private List<ClientDTO>			maxSitesList		= new ArrayList<>();
	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ClientsFilterSection(BbrUI parent)
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
				.withCaption(this.getI18n("client"))
				.withLabelWidth("220px")
				.withLabelStyle(BbrStyles.BBR_PANEL_SPACE)
				.withComponent(this.pnlSearchPanel)
				.build();
		pnlHeader.addStyleName(BbrStyles.BBR_FILTER_LABEL_HEADER);

		// Seleccion
		this.updateComboBox();
		BbrHInputFieldContainer pnlState = new BbrHInputFieldContainerBuilder()
				.withWidth("100%")
				.withCaption(this.getI18n("client"))
				.withLabelWidth("120px")
				.withLabelStyle(BbrStyles.BBR_PANEL_SPACE)
				.withComponent(this.cbx_Clients)
				.build();

		this.setWidth("400px");
		this.setHeight("70px");
		this.addStyleName(BbrStyles.BBR_FILTER_SECTIONS);
		this.addStyleName(BbrStyles.BBR_PANEL_SPACE);
		this.setSpacing(false);
		this.addComponents(pnlHeader, pnlState);

		this.showSearchPnl(true);
	}

	@Override
	public ClientDTO getSectionResult()
	{
		ClientDTO result = null;

		if (validateData() == null)
		{
			result = this.cbx_Clients.getSelectedValue();
		}

		return result;
	}

	@Override
	public String validateData()
	{
		String result = null;
		if (this.cbx_Clients.getSelectedValue() == null)
		{
			result = this.getI18n("client_required");
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
			winFindSite.addBbrEventListener(this::siteSelectedHandler);
			winFindSite.open(true);
		}
	}

	private void updateSites(ClientDTO company)
	{

		if (this.maxSitesList.contains(company))
		{
			this.cbx_Clients.setSelectedItem(company);
		}
		else
		{
			this.maxSitesList.add(0, company);
			this.cbx_Clients.setSelectedItem(company);
		}
	}

	private void showSearchPnl(boolean show)
	{
		if (this.btn_SearchCompany == null)
		{
			if (show && this.listOfSites.size() > MAX_SITES_SEARCH)
			{
				this.btn_SearchCompany = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_FILTERS, "search_client"));
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

	private void siteSelectedHandler(BbrEvent bbrEvent)
	{
		if (bbrEvent != null && bbrEvent.getResultObject() != null)
		{
			ClientDTO company = (ClientDTO) bbrEvent.getResultObject();

			this.updateSites(company);
		}
	}

	private void updateComboBox()
	{
		if (this.getBbrUIParent().getUser() != null)
		{
			try
			{
				ClientArrayResultDTO clientsResult = EJBFactory.getCustomerEJBFactory().getCustomerManagerLocal(this.getBbrUIParent()).getAllClients();

				this.updateComboboxData(clientsResult);
			}
			catch (BbrUserException e)
			{
				AppUtils.getInstance().doLogOut();
			}
			catch (BbrSystemException e)
			{
				e.printStackTrace();
			}
		}
	}

	private void updateComboboxData(ClientArrayResultDTO clientsResult)
	{
		if (this.cbx_Clients == null)
		{
			this.cbx_Clients = new BbrComboBox<ClientDTO>();
			this.cbx_Clients.setItemCaptionGenerator(ClientDTO::getClientname);
			this.cbx_Clients.setTextInputAllowed(false);
			this.cbx_Clients.setEmptySelectionAllowed(false);
			this.cbx_Clients.setWidth("255px");
		}

		if ((clientsResult != null) && (clientsResult.getClientDTOs() != null) && (clientsResult.getClientDTOs().length > 0))
		{
			ClientDTO[] items = clientsResult.getClientDTOs();
			List<ClientDTO> list = Arrays.asList(items);
			this.listOfSites.addAll(list);
			if (this.listOfSites.size() > MAX_SITES_SEARCH)
			{
				this.maxSitesList = this.listOfSites.stream()
						.sorted(Comparator.comparing(ClientDTO::getClientname))
						.limit(MAX_SITES_SEARCH)
						.collect(Collectors.toList());
				this.cbx_Clients.setItems(this.maxSitesList);
			}
			else
			{
				this.cbx_Clients.setItems(this.listOfSites);
			}
			this.cbx_Clients.addStyleName(BbrStyles.BBR_FILTER_FIELDS);
			this.cbx_Clients.selectFirstItem();
		}
		else
		{
			ClientDTO emptyResult = new ClientDTO();
			emptyResult.setClientname(this.getI18n("no_clients_available"));

			this.cbx_Clients.setItems(emptyResult);
			this.cbx_Clients.setSelectedItem(emptyResult);
			this.cbx_Clients.setEnabled(false);
		}
	}

	@Override
	public String getI18n(String resource)
	{
		return I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_CUSTOMER, resource);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
