package bbr.b2b.portal.components.filter_section.fep;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import bbr.b2b.fep.common.data.classes.ClientArrayResultDTO;
import bbr.b2b.fep.common.data.classes.ClientDTO;
import bbr.b2b.fep.common.data.classes.ClientInitParamDTO;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.FEPConstants;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;

public class ClientsFilterSection extends BbrVerticalSection<ClientDTO>
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	/**
	 * 
	 */
	private static final long serialVersionUID = 5129425386873577513L;

	public ClientsFilterSection(BbrUI parent)
	{
		super(parent);
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private BbrComboBox<ClientDTO> cbx_Clients = null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	@Override
	public void initializeView()
	{
		// Header
		Label lbl_Client = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_client"));
		lbl_Client.addStyleName("bbr-panel-space");
		lbl_Client.setWidth("200px");

		HorizontalLayout pnlHeader = new HorizontalLayout();
		pnlHeader.addStyleName("bbr-filter-label-header");
		pnlHeader.addComponents(lbl_Client);
		pnlHeader.setWidth("100%");

		// Seleccion
		Label lbl_Filter = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_filter"));
		lbl_Filter.setWidth("120px");

		this.cbx_Clients = this.updateComboBox();

		HorizontalLayout pnlState = new HorizontalLayout();
		pnlState.addComponents(lbl_Filter, this.cbx_Clients);
		pnlState.setExpandRatio(this.cbx_Clients, 1F);
		pnlState.setWidth("100%");
		pnlState.addStyleName("bbr-panel-space");

		this.setWidth("400px");
		this.setHeight("70px");
		this.addStyleName("bbr-filter-sections");
		this.addStyleName("bbr-panel-space");
		this.setSpacing(false);
		this.addComponents(pnlHeader, pnlState);
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
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "client_required");
		}
		return result;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private BbrComboBox<ClientDTO> updateComboBox()
	{
		BbrComboBox<ClientDTO> result = null;

		if (this.getBbrUIParent().getUser() != null)
		{
			try
			{
				ClientInitParamDTO initparam = new ClientInitParamDTO();
				
				String userType = this.getBbrUIParent().getUser().getTypeDescription();
				
				initparam.setIsRetail(!userType.equals(FEPConstants.NO_RETAIL_CLIENT) ? true : null);
				
				ClientArrayResultDTO clientsResult = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(this.getBbrUIParent()).getAllClients(initparam);
				
				result = new BbrComboBox<ClientDTO>();
				result.setItemCaptionGenerator(ClientDTO::getName);
				result.setTextInputAllowed(false);
				result.setEmptySelectionAllowed(false);
				result.setWidth("255px");

				if ((clientsResult != null) && (clientsResult.getValues() != null) && (clientsResult.getValues().length > 0))
				{
					result.setItems(clientsResult.getValues());
					result.addStyleName("bbr-filter-fields");
					result.selectFirstItem();
				}
				else
				{
					ClientDTO emptyResult = new ClientDTO();
					emptyResult.setName(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "no_clients_available"));

					result.setItems(emptyResult);
					result.setSelectedItem(emptyResult);
					result.setEnabled(false);
				}
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

		return result;

	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
