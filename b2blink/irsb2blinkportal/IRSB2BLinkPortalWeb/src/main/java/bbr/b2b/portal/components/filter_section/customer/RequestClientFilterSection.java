package bbr.b2b.portal.components.filter_section.customer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.wrappers.customer.RequestClientReportFilterSectionInfo;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer.BbrHInputFieldContainerBuilder;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.customer.CustomerServiceConstants;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import bbr.esb.services.webservices.facade.ScoreCardManagerServer;
import bbr.esb.services.webservices.facade.ScoreCardManagerServerService;
import bbr.esb.services.webservices.facade.SiteDTO;
import bbr.esb.services.webservices.facade.SitesFilterDTO;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;

public class RequestClientFilterSection extends BbrVerticalSection<RequestClientReportFilterSectionInfo>
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	private static final long serialVersionUID = -8569131291169324776L;

	public RequestClientFilterSection(BbrUI parent)
	{
		super(parent);
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private CompanyDataDTO			selectedCompany	= null;

	private BbrComboBox<SiteDTO>	cbx_Clients		= null;
	private HorizontalLayout		pnlSearchPanel	= null;
	private List<SiteDTO>			listSite		= new ArrayList<>();

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	@Override
	public void initializeView()
	{

		// Header
		Label lbl_ClientHeader = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "client"));
		lbl_ClientHeader.addStyleName("bbr-panel-space");
		lbl_ClientHeader.setWidth("200px");

		HorizontalLayout pnlSearchPanel = new HorizontalLayout();
		pnlSearchPanel.addStyleName("bbr-filter-label-header");
		pnlSearchPanel.addComponents(lbl_ClientHeader);
		pnlSearchPanel.setWidth("100%");

		Label lbl_Filter = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "client"));
		lbl_Filter.setWidth("120px");

		HorizontalLayout pnlClient = this.getServicePanel();
		this.criterionSelectionHandler(null);
		HorizontalLayout pnlClientHeader = new HorizontalLayout();
		pnlClientHeader.addStyleName("bbr-filter-label-header");
		pnlClientHeader.addComponents(lbl_ClientHeader, pnlSearchPanel);
		pnlClientHeader.setExpandRatio(pnlSearchPanel, 1F);
		pnlClientHeader.setWidth("100%");

		this.setWidth("400px");
		this.addStyleName("bbr-filter-sections");
		this.addStyleName("bbr-panel-space");
		this.setSpacing(false);
		this.addComponents(pnlClientHeader, pnlClient);
	}


	private void criterionSelectionHandler(SingleSelectionEvent<SearchCriterion> event)
	{
		if (this.cbx_Clients != null)
		{
			this.updateSelectedServiceValue();
		}
	}
	
	private void updateSelectedServiceValue()
	{
		try
		{
			SitesFilterDTO sitiesfilter = this.getServiceDTOWS();
			this.listSite = sitiesfilter.getSites();
			// TODOS
			SiteDTO allSities = new SiteDTO();
			allSities.setName("TODOS");
			allSities.setRetailname("TODOS");
			allSities.setId(-1L);
			;
			allSities.setCode("-1");

			if (this.listSite != null && this.listSite.size() > 0)
			{
				if (this.cbx_Clients != null)
				{
					this.listSite.add(allSities);
					this.cbx_Clients.setItems(this.listSite);
					this.cbx_Clients.setSelectedItem(allSities);
					this.cbx_Clients.setEnabled(true);
				}
			}
			// no hay errores pero de todas formas hay que agregar todos.
			else
			{
				if (this.cbx_Clients != null)
				{
					List<SiteDTO> tmpSite = new ArrayList<>();
					tmpSite.add(allSities);

					this.cbx_Clients.setItems(tmpSite);
					this.cbx_Clients.setSelectedItem(allSities);
					this.cbx_Clients.setEnabled(true);
				}
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public RequestClientReportFilterSectionInfo getSectionResult()
	{
		RequestClientReportFilterSectionInfo result = null;

		if (this.validateData() == null)
		{
			SiteDTO selectedSities = this.cbx_Clients.getSelectedValue();

			result = new RequestClientReportFilterSectionInfo(selectedSities);
		}

		return result;
	}

	@Override
	public String validateData()
	{
		String result = null;
		SiteDTO selectedSities = this.cbx_Clients.getSelectedValue();

		if (selectedSities == null || selectedSities.getId() == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "sitie_requires");
		}

		return result;
	}

	@Override
	public void setSectionData(Object data)
	{
		if (this.selectedCompany != (CompanyDataDTO) data)
		{
			super.setSectionData(data);
			this.selectedCompany = (CompanyDataDTO) data;
			this.criterionSelectionHandler(null);
		}
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
	private SitesFilterDTO getServiceDTOWS()
	{
		ScoreCardManagerServer loginClientPort;
		SitesFilterDTO servicedata = null;
		try
		{
			URL url = new URL(CustomerServiceConstants.getInstance().getScoreCardWebServiceEndpointPath());
			ScoreCardManagerServerService loginClient = new ScoreCardManagerServerService(url);
			loginClientPort = loginClient.getScoreCardManagerServerPort();
			servicedata = loginClientPort.getSites(this.selectedCompany.getRut());
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return servicedata;
	}

	private HorizontalLayout getServicePanel()
	{
		if (this.cbx_Clients == null)
		{
			this.cbx_Clients = new BbrComboBox<SiteDTO>();

			this.cbx_Clients.setItemCaptionGenerator(SiteDTO::getRetailname);
			this.cbx_Clients.setTextInputAllowed(false);
			this.cbx_Clients.setEmptySelectionAllowed(false);
			this.cbx_Clients.addStyleName("bbr-filter-fields");
			this.cbx_Clients.setWidth("255px");
			this.cbx_Clients.setHeight("30px");
		}
	
		BbrHInputFieldContainer result = new BbrHInputFieldContainerBuilder()
				.withCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "client"))
				.withLabelWidth("120px")
				.withComponent(this.cbx_Clients)
				.build();
		return result;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
