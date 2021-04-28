package bbr.b2b.portal.components.filter_section.fep;

import java.util.Arrays;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import bbr.b2b.fep.common.data.classes.UserTypeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.UserTypeDTO;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.ProfileArrayResultDTO;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;

public class UserRolesFilterSection extends BbrVerticalSection<UserTypeDTO>
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	/**
	 * 
	 */
	private static final long serialVersionUID = 5129425386873577513L;


	public UserRolesFilterSection(BbrUI parent, String providerCode, Boolean rolesByProfiles)
	{
		super(parent);
		this.providerCode = providerCode;
		this.rolesByProfiles = rolesByProfiles;
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private BbrComboBox<UserTypeDTO>	cbx_UserRoles		= null;
	private String							providerCode		= null;
	private Boolean						rolesByProfiles	= null;

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
		Label lbl_Role = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "user_role"));
		lbl_Role.addStyleName("bbr-panel-space");
		lbl_Role.setWidth("200px");

		HorizontalLayout pnlStateHeader = new HorizontalLayout();
		pnlStateHeader.addStyleName("bbr-filter-label-header");
		pnlStateHeader.addComponents(lbl_Role);
		pnlStateHeader.setWidth("100%");

		// Sección Estado
		Label lbl_Filter = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_filter"));
		lbl_Filter.setWidth("120px");

		this.cbx_UserRoles = this.updateRoleComboBox();

		HorizontalLayout pnlState = new HorizontalLayout();
		pnlState.addComponents(lbl_Filter, this.cbx_UserRoles);
		pnlState.setExpandRatio(this.cbx_UserRoles, 1F);
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
	public UserTypeDTO getSectionResult()
	{
		UserTypeDTO result = null;

		if (validateData() == null)
		{
			result = this.cbx_UserRoles.getSelectedValue();
		}

		return result;
	}


	@Override
	public String validateData()
	{
		String result = null;
		
		if (this.cbx_UserRoles.getSelectedValue() == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "role_required");
		}
		
		return result;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private BbrComboBox<UserTypeDTO> updateRoleComboBox()
	{
		BbrComboBox<UserTypeDTO> result = null;

		if (this.getBbrUIParent().getUser() != null)
		{
			try
			{
				UserTypeArrayResultDTO itemStates;
				
				if(this.rolesByProfiles)
				{
					ProfileArrayResultDTO userProfiles = EJBFactory.getUserEJBFactory().getFunctionalityManagerLocal(this.getBbrUIParent()).getAllProfileAssigedToUser(this.getBbrUIParent().getUser().getId());
					String[] profileNames = Arrays.stream(userProfiles.getProfiles()).map(p -> p.getCode()).toArray(String[]::new);
					
					itemStates = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(this.getBbrUIParent()).getRolesByUserProfiles(String.join(";", profileNames));
				}
				else
				{
					itemStates = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(this.getBbrUIParent()).getRolesByProviderWorkFlow(this.providerCode);
				}

				result = new BbrComboBox<UserTypeDTO>();
				result.setItemCaptionGenerator(UserTypeDTO::getName);
				result.setTextInputAllowed(false);
				result.setEmptySelectionAllowed(false);
				result.setWidth("255px");

				if ((itemStates != null) && (itemStates.getValues() != null) && (itemStates.getValues().length > 0))
				{
					result.setItems(itemStates.getValues());
					result.addStyleName("bbr-filter-fields");
					result.selectFirstItem();
				}
				else
				{
					UserTypeDTO emptyResult = new UserTypeDTO();
					emptyResult.setName(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "no_roles_available"));

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
