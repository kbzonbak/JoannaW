package bbr.b2b.portal.components.filter_section.management;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.vaadin.addons.ComboBoxMultiselect;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.PositionDTO;
import bbr.b2b.users.report.classes.PositionResultDTO;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.components.basics.BbrUI;

public class PositionFilterSection extends BbrVerticalSection<List<PositionDTO>>
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================
	private static final long serialVersionUID = 2086813609291810040L;

	public PositionFilterSection(BbrUI parent)
	{
		super(parent);
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private ComboBoxMultiselect<PositionDTO>	cbx_Positions		= null;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	@Override
	public void initializeView()
	{
		Label lbl_ProviderHeader = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "positions"));
		lbl_ProviderHeader.addStyleName("bbr-panel-space");
		lbl_ProviderHeader.setWidth("220px");

		HorizontalLayout pnlProvidersHeader = new HorizontalLayout();
		pnlProvidersHeader.addStyleName("bbr-filter-label-header");
		pnlProvidersHeader.addComponents(lbl_ProviderHeader);
		pnlProvidersHeader.setExpandRatio(lbl_ProviderHeader, 1F);
		pnlProvidersHeader.setWidth("100%");

		Label lbl_Provider = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "position"));
		lbl_Provider.setWidth("120px");
		
		ComboBoxMultiselect<PositionDTO> positions = this.getPositionsCombBox();
		this.cbx_Positions = positions;
		
		HorizontalLayout pnlProviderSubSection = new HorizontalLayout();
		pnlProviderSubSection.setWidth("100%");
		pnlProviderSubSection.addComponents(lbl_Provider, this.cbx_Positions);
		pnlProviderSubSection.setExpandRatio(this.cbx_Positions, 1F);
		pnlProviderSubSection.addStyleName("bbr-panel-space");

		this.setWidth("400px");
		this.addStyleName("bbr-filter-sections");
		this.addStyleName("bbr-panel-space");
		this.setSpacing(false);
		this.addComponents(pnlProvidersHeader, pnlProviderSubSection);
	}

	
	@Override
	public List<PositionDTO> getSectionResult()
	{
		List<PositionDTO> result = null;
		if (this.cbx_Positions.getSelectedItems() != null)
		{
			result = new ArrayList<>(this.cbx_Positions.getSelectedItems());
		}
		return result;
	}

	@Override
	public String validateData()
	{
		String result = null;
		if (this.cbx_Positions != null)
		{
			List<PositionDTO> selectedCompany = new ArrayList<>(this.cbx_Positions.getSelectedItems());

			if (selectedCompany.size() < 1)
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "position_requires");
			}
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
	private ComboBoxMultiselect<PositionDTO> getPositionsCombBox()
	{
		ComboBoxMultiselect<PositionDTO> result =  null;
		try
		{
			PositionDTO emptyResult = new PositionDTO();
			emptyResult.setName(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "no_positions_assigned"));
		
			result = new ComboBoxMultiselect<PositionDTO>();
			result.setTextInputAllowed(true);
			result.setItemCaptionGenerator(PositionDTO::getName);
			result.setSelectAllButtonCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "select_all"));
			result.setClearButtonCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "deselect_all"));
			result.showSelectAllButton(true);
			result.showClearButton(true);
			result.setWidth("255px");
			result.addStyleName("bbr-filter-fields");
			
			PositionResultDTO positionsResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal(this.getBbrUIParent()).getAllPosition();
			positionsResult.getPositionDTOs();
			BbrError error = ErrorsMngr.getInstance().getError(positionsResult, this.getBbrUIParent(), !this.getBbrUIParent().hasAlertWindowOpen());
			if (!error.hasError())
			{
				List<PositionDTO> items = new ArrayList<PositionDTO>();

				PositionDTO[] results = positionsResult.getPositionDTOs();
				items.addAll(Arrays.asList(results));
				
				result.setItems(items);
				result.setEnabled(true);
				
			}
			else
			{
				result.setItems(emptyResult);
				result.select(emptyResult);
				result.setEnabled(false);
			}
			
		}
		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), this.getBbrUIParent());
		}

		catch (BbrSystemException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
