package bbr.b2b.portal.components.filter_section.fep;

import java.util.ArrayList;
import java.util.Arrays;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import bbr.b2b.fep.cp.data.classes.CPItemStateArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemStateDTO;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;

public class PrivilegesFilterSection extends BbrVerticalSection<CPItemStateDTO>
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	/**
	 * 
	 */
	private static final long serialVersionUID = 5129425386873577513L;


	public PrivilegesFilterSection(BbrUI parent, String providerCode)
	{
		super(parent);
		this.providerCode = providerCode;
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private BbrComboBox<CPItemStateDTO>	cbx_ItemStatus	= null;
	private String								providerCode	= null;

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
		Label lbl_State = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_state"));
		lbl_State.addStyleName("bbr-panel-space");
		lbl_State.setWidth("200px");

		HorizontalLayout pnlStateHeader = new HorizontalLayout();
		pnlStateHeader.addStyleName("bbr-filter-label-header");
		pnlStateHeader.addComponents(lbl_State);
		pnlStateHeader.setWidth("100%");

		// Sección Estado
		Label lbl_Filter = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_filter"));
		lbl_Filter.setWidth("120px");

		this.cbx_ItemStatus = this.updateStateComboBox();

		HorizontalLayout pnlState = new HorizontalLayout();
		pnlState.addComponents(lbl_Filter, this.cbx_ItemStatus);
		pnlState.setExpandRatio(this.cbx_ItemStatus, 1F);
		pnlState.setWidth("100%");
		pnlState.addStyleName("bbr-panel-space");

		this.setWidth("400px");
		this.setHeight("70px");
		this.addStyleName("bbr-filter-sections");
		this.addStyleName("bbr-panel-space");
		this.setSpacing(false);
		this.addComponents(pnlStateHeader, pnlState);
	}


	@Override
	public CPItemStateDTO getSectionResult()
	{
		CPItemStateDTO result = null;

		if (validateData() == null)
		{
			result = this.cbx_ItemStatus.getSelectedValue();

		}

		return result;
	}


	@Override
	public String validateData()
	{
		String result = null;
		if (this.cbx_ItemStatus.getSelectedValue() == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "status_requires");
		}
		return result;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private BbrComboBox<CPItemStateDTO> updateStateComboBox()
	{
		BbrComboBox<CPItemStateDTO> result = null;

		if (this.getBbrUIParent().getUser() != null)
		{
			try
			{
				CPItemStateArrayResultDTO itemStates = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(this.getBbrUIParent()).getItemsStateByProviderWorkFlow(this.providerCode);
				result = new BbrComboBox<CPItemStateDTO>();

				result.setPlaceholder(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "select_state"));
				result.setItemCaptionGenerator(CPItemStateDTO::getName);
				result.setTextInputAllowed(false);
				result.setEmptySelectionAllowed(false);
				result.setWidth("255px");

				if ((itemStates != null) && (itemStates.getValues() != null) && (itemStates.getValues().length > 0))
				{
					result.setItems(itemStates.getValues());

					ArrayList<CPItemStateDTO> states = new ArrayList<CPItemStateDTO>(Arrays.asList(itemStates.getValues()));

					result.setItems(states);
					result.addStyleName("bbr-filter-fields");
					result.selectFirstItem();
				}
				else
				{
					CPItemStateDTO emptyResult = new CPItemStateDTO();
					emptyResult.setName(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "no_states_assigned"));

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
