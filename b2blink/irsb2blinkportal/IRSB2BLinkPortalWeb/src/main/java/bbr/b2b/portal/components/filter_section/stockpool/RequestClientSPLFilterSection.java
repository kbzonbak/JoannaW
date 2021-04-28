package bbr.b2b.portal.components.filter_section.stockpool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import bbr.b2b.logistic.report.data.dto.BuyerDTO;
import bbr.b2b.logistic.report.data.dto.BuyerFilterDTO;
import bbr.b2b.logistic.report.data.dto.ClientsFilterInitParamDTO;
import bbr.b2b.logistic.rest.client.IRSB2BStockPoolRestFulWSClient;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.wrappers.stockpool.RequestClientSPLReportFilterSectionInfo;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer.BbrHInputFieldContainerBuilder;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;

public class RequestClientSPLFilterSection extends BbrVerticalSection<RequestClientSPLReportFilterSectionInfo>
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	private static final long	serialVersionUID	= -8569131291169324776L;
	private String				clientName			= null;

	public RequestClientSPLFilterSection(BbrUI parent, String clienName)
	{
		super(parent);
		this.clientName = clienName;
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private CompanyDataDTO			selectedCompany	= null;

	private BbrComboBox<BuyerDTO>	cbx_Clients		= null;
	private List<BuyerDTO>			listSite		= new ArrayList<>();

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

		Label lbl_Filter = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "client"));
		lbl_Filter.setWidth("120px");

		HorizontalLayout pnlClient = this.getServicePanel();
		this.criterionSelectionHandler(null);
		HorizontalLayout pnlClientHeader = new HorizontalLayout();
		pnlClientHeader.addStyleName("bbr-filter-label-header");
		pnlClientHeader.addComponents(lbl_ClientHeader);
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
			BuyerFilterDTO sitiesfilter = this.getServiceDTOWS();
			this.listSite.addAll(Arrays.asList(sitiesfilter.getBuyers()));
			// TODOS
			BuyerDTO allSities = new BuyerDTO();
			allSities.setName("TODOS");
			allSities.setSitename("TODOS");
			allSities.setId(-1L);
			allSities.setCode("-1");

			if (this.listSite != null && this.listSite.size() > 0)
			{
				if (this.cbx_Clients != null)
				{
					BuyerDTO selectedBuyer = null;
					if (this.clientName != null)
					{
						for (BuyerDTO buyer : this.listSite)
						{
							if (buyer.getName().equals(this.clientName))
							{
								selectedBuyer= buyer;
								break;
							}
						}
					}
					else
					{
						selectedBuyer = null;
					}
					this.listSite.add(allSities);
					this.cbx_Clients.setItems(this.listSite);
					if(selectedBuyer!= null)
					{
						this.cbx_Clients.setSelectedItem(selectedBuyer);
					}
					else
					{
						this.cbx_Clients.setSelectedItem(allSities);
					}
					
					this.cbx_Clients.setEnabled(true);
				}
			}
			// no hay errores pero de todas formas hay que agregar todos.
			else
			{
				if (this.cbx_Clients != null)
				{
					List<BuyerDTO> tmpSite = new ArrayList<>();
					tmpSite.add(allSities);

					this.cbx_Clients.setItems(tmpSite);
					this.cbx_Clients.setSelectedItem(allSities);
					this.cbx_Clients.setEnabled(true);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public RequestClientSPLReportFilterSectionInfo getSectionResult()
	{
		RequestClientSPLReportFilterSectionInfo result = null;

		if (this.validateData() == null)
		{
			BuyerDTO selectedSities = this.cbx_Clients.getSelectedValue();

			result = new RequestClientSPLReportFilterSectionInfo(selectedSities);
		}

		return result;
	}

	@Override
	public String validateData()
	{
		String result = null;
		BuyerDTO selectedSities = this.cbx_Clients.getSelectedValue();

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
	private BuyerFilterDTO getServiceDTOWS()
	{
		BuyerFilterDTO servicedata = null;
		try
		{
			ClientsFilterInitParamDTO initparam = new ClientsFilterInitParamDTO();
			initparam.setVendorcode(this.selectedCompany.getRut());
			servicedata = IRSB2BStockPoolRestFulWSClient.getInstance().getClientsByVendorCodeWS(initparam);
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
			this.cbx_Clients = new BbrComboBox<BuyerDTO>();

			this.cbx_Clients.setItemCaptionGenerator(BuyerDTO::getName);
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
