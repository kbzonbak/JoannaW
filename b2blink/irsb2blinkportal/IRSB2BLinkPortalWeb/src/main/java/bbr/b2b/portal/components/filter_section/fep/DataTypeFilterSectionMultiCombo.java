package bbr.b2b.portal.components.filter_section.fep;

import java.util.Set;

import org.vaadin.addons.ComboBoxMultiselect;

import com.vaadin.event.selection.MultiSelectionEvent;
import com.vaadin.event.selection.MultiSelectionListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import bbr.b2b.fep.common.data.classes.UserDataTypeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.UserDataTypeDTO;
import bbr.b2b.portal.classes.constants.BbrFilterSectionConstants;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.container.BbrSectionEvent;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.components.basics.BbrUI;

public class DataTypeFilterSectionMultiCombo extends BbrVerticalSection<Set<UserDataTypeDTO>>
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	private static final long serialVersionUID = -594225171247809567L;

	public DataTypeFilterSectionMultiCombo(BbrUI parent)
	{
		super(parent);
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private ComboBoxMultiselect<UserDataTypeDTO> cbx_DataTypes = null;
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	@Override
	public void initializeView()
	{
		Label lbl_DataTypeHeader = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_data_types"));
		lbl_DataTypeHeader.addStyleName("bbr-panel-space");

		HorizontalLayout pnlDataTypeHeader = new HorizontalLayout();
		pnlDataTypeHeader.addStyleName("bbr-filter-label-header");
		pnlDataTypeHeader.addComponent(lbl_DataTypeHeader);
		pnlDataTypeHeader.setWidth("100%");

		Label lbl_Filter = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_filter"));
		lbl_Filter.setWidth("120px");

		this.cbx_DataTypes = this.getDataTypesComboBox();

		HorizontalLayout pnlProviderSubSection = new HorizontalLayout();
		pnlProviderSubSection.setWidth("100%");
		pnlProviderSubSection.addComponents(lbl_Filter, this.cbx_DataTypes);
		pnlProviderSubSection.setExpandRatio(this.cbx_DataTypes, 1F);
		pnlProviderSubSection.addStyleName("bbr-panel-space");

		this.setWidth("400px");
		this.addStyleName("bbr-filter-sections");
		this.addStyleName("bbr-panel-space");
		this.setSpacing(false);
		this.addComponents(pnlDataTypeHeader, pnlProviderSubSection);

	}

	@Override
	public Set<UserDataTypeDTO> getSectionResult()
	{
		Set<UserDataTypeDTO> result = null;
		if (validateData() == null)
		{
			result = this.cbx_DataTypes.getSelectedItems();
		}
		return result;
	}

	@Override
	public String validateData()
	{
		String result = null;
		Set<UserDataTypeDTO> selectedDataType = this.cbx_DataTypes.getSelectedItems();

		if (selectedDataType == null || selectedDataType.size() < 1)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "data_type_requires");
		}
		
		return result;
	}

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private ComboBoxMultiselect<UserDataTypeDTO> getDataTypesComboBox()
	{
		ComboBoxMultiselect<UserDataTypeDTO> result = new ComboBoxMultiselect<>();
		result.setItemCaptionGenerator(UserDataTypeDTO::getName);

		// Initialize the ComboBoxMultiselect
		result.setPlaceholder(I18NManager.getI18NString(this.getBbrUIParent(),BbrUtilsResources.RES_MODULES_FEP, "phld_select_data_types"));
		result.setTextInputAllowed(false);
		result.setSelectAllButtonCaption(I18NManager.getI18NString(this.getBbrUIParent(),BbrUtilsResources.RES_MODULES_GENERIC, "select_all"));
		result.setClearButtonCaption(I18NManager.getI18NString(this.getBbrUIParent(),BbrUtilsResources.RES_MODULES_GENERIC, "deselect_all"));
		result.showSelectAllButton(true);
		result.showClearButton(true);
		result.setWidth("255px");
		result.setEnabled(false);
		result.addStyleName("bbr-filter-fields");

		if (this.getBbrUIParent().getUser() != null)
		{
			try
			{
				UserDataTypeArrayResultDTO dataTypesResult = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal().getUserDataTypes(this.getBbrUIParent().getLocale().getLanguage(), true);

				if ((dataTypesResult != null) && (dataTypesResult.getValues() != null) && (dataTypesResult.getValues().length > 0))
				{
					result.setItems(dataTypesResult.getValues());
					result.addSelectionListener(new MultiSelectionListener<UserDataTypeDTO>()
					{
						private static final long serialVersionUID = -6243139164039330931L;

						@Override
						public void selectionChange(MultiSelectionEvent<UserDataTypeDTO> event)
						{
							if (DataTypeFilterSectionMultiCombo.this.cbx_DataTypes != null && DataTypeFilterSectionMultiCombo.this.cbx_DataTypes.getSelectedItems() != null)
							{
								BbrSectionEvent bbrEvent = new BbrSectionEvent(BbrFilterSectionConstants.FS_DATA_TYPE);
								bbrEvent.setResultObject(DataTypeFilterSectionMultiCombo.this.cbx_DataTypes.getSelectedItems());
								dispatchBbrSectionEvent(bbrEvent);
							}

						}
					});
					result.setEnabled(true);
					result.select(dataTypesResult.getValues());

				}
				else
				{
					UserDataTypeDTO emptyResult = new UserDataTypeDTO();
					emptyResult.setName(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "no_data_types_assigned"));

//					result.setItems(emptyResult);
//					result.select(emptyResult);
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
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
