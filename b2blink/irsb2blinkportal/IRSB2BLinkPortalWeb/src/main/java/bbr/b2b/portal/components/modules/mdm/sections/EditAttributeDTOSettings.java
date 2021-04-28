package bbr.b2b.portal.components.modules.mdm.sections;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.fep.common.data.classes.AttributeDTO;
import bbr.b2b.fep.common.data.classes.AttributeTypeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.AttributeTypeDTO;
import bbr.b2b.fep.common.data.classes.ClientArrayResultDTO;
import bbr.b2b.fep.common.data.classes.ClientDTO;
import bbr.b2b.fep.common.data.classes.ClientInitParamDTO;
import bbr.b2b.fep.common.data.classes.UserDataTypeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.UserDataTypeDTO;
import bbr.b2b.portal.classes.constants.BbrFilterSectionConstants;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.fep.FepUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.FEPConstants;
import bbr.b2b.portal.utils.FEPUtils;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.container.BbrHorizontalSection;
import cl.bbr.core.classes.container.BbrSectionEvent;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrHSeparator;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class EditAttributeDTOSettings extends BbrHorizontalSection<AttributeDTO>
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long					serialVersionUID		= 8230123120450174629L;

	private BbrTextField							txt_B2BName				= null;
	private BbrTextField							txt_Unit					= null;
	private BbrTextField							txt_ProviderName		= null;
	private HorizontalLayout					pnlUnit					= null;

	private TextArea								txtA_HelpProvide		= null;

	private BbrComboBox<UserDataTypeDTO>	cbx_DataType			= null;
	private BbrComboBox<SearchCriterion>	cbx_State				= null;
	private AttributeDTO							selectedAttribute		= null;
	private UserDataTypeDTO						userDataTypeSelected	= null;
	private UserDataTypeArrayResultDTO		dataTypesResult		= null;
	private BbrComboBox<AttributeTypeDTO>	cbx_Type					= null;
	private BbrComboBox<ClientDTO>			cbx_Client				= null;

	private List<AttributeTypeDTO>			lstAttributeType		= null;
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public EditAttributeDTOSettings(BbrUI parent, UserDataTypeArrayResultDTO dataTypesResult,
	UserDataTypeDTO userDataTypeSelected, AttributeDTO selectedAttribute)
	{
		super(parent);
		this.dataTypesResult = dataTypesResult;
		this.userDataTypeSelected = userDataTypeSelected;
		this.selectedAttribute = selectedAttribute;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION ----> INTERFACE IMPLEMENTATIONS
	// ****************************************************************************************
	@Override
	public void initializeView()
	{
		// Sección Nombre B2B
		Label lbl_B2BName = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_b2b_name"));
		lbl_B2BName.setWidth("120px");
		lbl_B2BName.addStyleName("bold_style");

		this.txt_B2BName = new BbrTextField();
		this.txt_B2BName.setWidth(100, Unit.PERCENTAGE);
		this.txt_B2BName.addStyleName("bbr-filter-fields");
		this.txt_B2BName.setRequiredIndicatorVisible(true);
		this.txt_B2BName.setAutoComplete(false);

		HorizontalLayout pnlB2BName = new HorizontalLayout(lbl_B2BName, txt_B2BName);
		pnlB2BName.setExpandRatio(txt_B2BName, 1F);
		pnlB2BName.setSizeFull();

		// Cliente
		Label lbl_Client = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_client"));
		lbl_Client.setWidth("120px");
		lbl_Client.addStyleName("bold_style");

		this.cbx_Client = this.getClientsComboBox();

		HorizontalLayout pnlClient = new HorizontalLayout(lbl_Client, this.cbx_Client);
		pnlClient.setExpandRatio(this.cbx_Client, 1F);
		pnlClient.setSizeFull();
		
		// Estado
		Label lbl_State = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_state"));
		lbl_State.setWidth("120px");
		lbl_State.addStyleName("bold_style");

		this.cbx_State = this.getSearchCriterionStateComboBox();

		HorizontalLayout pnlState = new HorizontalLayout(lbl_State, this.cbx_State);
		pnlState.setExpandRatio(this.cbx_State, 1F);
		pnlState.setSizeFull();

		// Tipo de dato
		Label lbl_DataType = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_data_type"));
		lbl_DataType.setWidth("120px");
		lbl_DataType.addStyleName("bold_style");

		this.cbx_DataType = this.getDataTypesComboBox();

		HorizontalLayout pnlDataType = new HorizontalLayout(lbl_DataType, this.cbx_DataType);
		pnlDataType.setExpandRatio(this.cbx_DataType, 1F);
		pnlDataType.setSizeFull();

		// Tipo de Atributo
		Label lbl_Type = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_attribute_type"));
		lbl_Type.setWidth("120px");
		lbl_Type.addStyleName("bold_style");

		this.cbx_Type = this.getTypesComboBox();

		HorizontalLayout pnlAttributeType = new HorizontalLayout(lbl_Type, this.cbx_Type);
		pnlAttributeType.setExpandRatio(this.cbx_Type, 1F);
		pnlAttributeType.setSizeFull();

		// Sección unidad de medida
		Label lbl_Unit = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_unit"));
		lbl_Unit.setWidth("120px");
		lbl_Unit.addStyleName("bold_style");

		this.txt_Unit = new BbrTextField();
		this.txt_Unit.setWidth(100, Unit.PERCENTAGE);
		this.txt_Unit.addStyleName("bbr-filter-fields");
		this.txt_Unit.setMaxLength(20);
		this.txt_Unit.setAutoComplete(false);

		this.pnlUnit = new HorizontalLayout(lbl_Unit, txt_Unit);
		this.pnlUnit.setExpandRatio(txt_Unit, 1F);
		this.pnlUnit.setSizeFull();
		this.pnlUnit.setVisible(false);

		// Vertical ---- Nombre B2B + Tipo de dato + Tipo de Atributo
		VerticalLayout pnlFirstColumn = new VerticalLayout();
		pnlFirstColumn.setSizeFull();
		pnlFirstColumn.setMargin(false);
		pnlFirstColumn.addComponents(pnlB2BName, pnlClient, pnlState, pnlDataType, pnlAttributeType, pnlUnit);

		// Glosa de proveedor
		Label lbl_ProviderName = new Label(
		I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "provider_comment"));
		lbl_ProviderName.setWidth("120px");
		lbl_ProviderName.addStyleName("bold_style");

		this.txt_ProviderName = new BbrTextField();
		this.txt_ProviderName.setWidth(100, Unit.PERCENTAGE);
		this.txt_ProviderName.addStyleName("bbr-filter-fields");
		this.txt_ProviderName.setRequiredIndicatorVisible(true);
		this.txt_ProviderName.setAutoComplete(false);

		HorizontalLayout pnlProviderName = new HorizontalLayout(lbl_ProviderName, txt_ProviderName);
		pnlProviderName.setExpandRatio(txt_ProviderName, 1F);
		pnlProviderName.setSizeFull();

		// Ayuda a proveedor
		Label lbl_HelpProvider = new Label(
		I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_help_provider"));
		lbl_HelpProvider.setWidth("120px");
		lbl_HelpProvider.addStyleName("bold_style");

		this.txtA_HelpProvide = new TextArea();
		this.txtA_HelpProvide.setWidth(97, Unit.PERCENTAGE);
		this.txtA_HelpProvide.setHeight("70px");
		this.txtA_HelpProvide.setMaxLength(100);

		HorizontalLayout pnl_HelpProvider = new HorizontalLayout(lbl_HelpProvider, this.txtA_HelpProvide);
		pnl_HelpProvider.setExpandRatio(this.txtA_HelpProvide, 1F);
		pnl_HelpProvider.setWidth(100, Unit.PERCENTAGE);

		// Nota de restriccion
		HorizontalLayout space = new HorizontalLayout();
		space.setWidth("120px");

		String constraintNoteText = BbrUtils.getInstance().substitute("<p><strong>{0}</strong>: {1}</p>",
		I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_note"),
		I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_constraint_note"));
		Label lbl_ConstraintNote = new Label(constraintNoteText, ContentMode.HTML);
		lbl_ConstraintNote.setWidth(100, Unit.PERCENTAGE);

		HorizontalLayout pnl_NoteConstraint = new HorizontalLayout(space, lbl_ConstraintNote);
		pnl_NoteConstraint.setExpandRatio(lbl_ConstraintNote, 1F);
		pnl_NoteConstraint.setWidth(100, Unit.PERCENTAGE);

		// Vertical ----Glosa de proveedor + Estado + Ayuda a proveedor + Nota
		// de restriccion
		VerticalLayout pnl_SecondColumn = new VerticalLayout(pnlProviderName, pnl_HelpProvider, pnl_NoteConstraint);
		pnl_SecondColumn.setSizeFull();
		pnl_SecondColumn.setMargin(false);

		BbrHSeparator separatorH = new BbrHSeparator("1px");
		separatorH.setHeight("100%");

		this.updateFormData();

		this.setMargin(false);
		this.addStyleName("bbr-margin-windows-zero-bottom");
		this.addComponents(pnlFirstColumn, separatorH, pnl_SecondColumn);
		this.setExpandRatio(pnlFirstColumn, 1F);
		this.setExpandRatio(pnl_SecondColumn, 1F);
		this.setWidth("100%");
	}


	@Override
	public AttributeDTO getSectionResult()
	{

		if (validateData() == null)
		{
			UserDataTypeDTO dataType = cbx_DataType.getSelectedValue();
			int active = cbx_State.getSelectedValue().getId();

			this.selectedAttribute.setInternalname(this.txt_B2BName.getValue());
			this.selectedAttribute.setVendorname(this.txt_ProviderName.getValue());
			this.selectedAttribute.setDatatypeid(dataType.getId());
			this.selectedAttribute.setActive(active == 1 ? true : false);
			this.selectedAttribute.setUserhelp(this.txtA_HelpProvide.getValue());
			this.selectedAttribute.setAttributetypeid(this.cbx_Type.getSelectedValue().getId());
			this.selectedAttribute.setUnit(this.txt_Unit.getValue());
		}

		return this.selectedAttribute;

	}


	@Override
	public String validateData()
	{
		String result = null;

		if (this.txt_B2BName.isEmpty())
		{
			result = I18NManager.getI18NString(getBbrUIParent(),
			BbrUtilsResources.RES_MODULES_FEP,
			"required_b2b_name");
		}
		else if (this.txt_ProviderName.isEmpty())
		{
			result = I18NManager.getI18NString(getBbrUIParent(),
			BbrUtilsResources.RES_MODULES_FEP,
			"required_provider_name");
		}
		else if (cbx_DataType.getSelectedValue() == null)
		{
			result = I18NManager.getI18NString(getBbrUIParent(),
			BbrUtilsResources.RES_MODULES_FEP,
			"invalid_data_type");
		}

		return result;
	}


	@Override
	public void setSectionData(Object data)
	{
		super.setSectionData(data);
		if (data != null)
		{
			this.selectedAttribute = (AttributeDTO) data;
			this.updateFormData();
		}
	}
	// ****************************************************************************************
	// ENDING SECTION ----> INTERFACE IMPLEMENTATIONS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private BbrComboBox<UserDataTypeDTO> getDataTypesComboBox()
	{
		BbrComboBox<UserDataTypeDTO> result = new BbrComboBox<UserDataTypeDTO>();
		result.setItemCaptionGenerator(UserDataTypeDTO::getName);
		result.setPlaceholder("Inserte Tipo de Dato");
		result.setTextInputAllowed(false);
		result.setEmptySelectionAllowed(false);
		result.setWidth(100, Unit.PERCENTAGE);
		result.setEnabled(false);
		result.addStyleName("bbr-filter-fields");
		result.setRequiredIndicatorVisible(true);

		if (this.getBbrUIParent().getUser() != null)
		{

			if ((dataTypesResult != null) && (dataTypesResult.getValues() != null)
			&& (dataTypesResult.getValues().length > 0))
			{
				List<UserDataTypeDTO> userDataTypeList = Arrays.stream(dataTypesResult.getValues())
				.sorted(Comparator.comparing(UserDataTypeDTO::getName))
				.collect(Collectors.toList());
				result.setItems(userDataTypeList);
				result.setEnabled(true);
			}
			else
			{
				UserDataTypeDTO emptyResult = new UserDataTypeDTO();
				emptyResult.setName(
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "no_data_types_assigned"));
				result.setItems(emptyResult);
			}
		}

		result.addValueChangeListener((e) -> dataType_changeHandler(e));

		return result;

	}


	private void dataType_changeHandler(ValueChangeEvent<UserDataTypeDTO> e)
	{
		if (EditAttributeDTOSettings.this.cbx_DataType != null && EditAttributeDTOSettings.this.cbx_DataType.getSelectedValue() != null)
		{

			BbrSectionEvent bbrEvent = new BbrSectionEvent(BbrFilterSectionConstants.FS_DATA_TYPE);

			this.pnlUnit.setVisible(this.cbx_DataType.getSelectedValue() != null ? FEPUtils.attributeIsaNumber(this.cbx_DataType.getSelectedValue().getInternalname()) : false);

			bbrEvent.setResultObject(EditAttributeDTOSettings.this.cbx_DataType.getSelectedValue());
			dispatchBbrSectionEvent(bbrEvent);

		}
	}


	private BbrComboBox<AttributeTypeDTO> getTypesComboBox()
	{
		AttributeTypeArrayResultDTO typeResult = null;
		BbrComboBox<AttributeTypeDTO> result = new BbrComboBox<AttributeTypeDTO>();
		result.setItemCaptionGenerator(AttributeTypeDTO::getName);
		result.setPlaceholder(
		I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "placeholder_attribute_type"));
		result.setTextInputAllowed(false);
		result.setEmptySelectionAllowed(false);
		result.setWidth(100, Unit.PERCENTAGE);
		result.setRequiredIndicatorVisible(true);
		result.setEnabled(false);
		result.addStyleNames("placeholder-input-style", "bbr-filter-fields");

		try
		{
			// Start Tracking
			typeResult = EJBFactory.getFEPEJBFactory()
			.getFEPCommonDataManagerLocal(this.getBbrUIParent())
			.getAttributeTypes(this.getBbrUIParent().getLocale().getLanguage());
			if ((typeResult != null) && (typeResult.getValues() != null) && (typeResult.getValues().length > 0))
			{
				lstAttributeType = Arrays.stream(typeResult.getValues())
				.sorted(Comparator.comparing(AttributeTypeDTO::getName))
				.collect(Collectors.toList());
				result.setItems(lstAttributeType);
				result.setEnabled(true);
			}
			else
			{
				AttributeTypeDTO emptyResult = new AttributeTypeDTO();
				emptyResult.setName(
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "no_data_types_assigned"));
				result.setItems(emptyResult);
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
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;

	}


	private BbrComboBox<SearchCriterion> getSearchCriterionStateComboBox()
	{
		SearchCriterion[] searcCriterions = FepUtils.getStatesCriteria();
		BbrComboBox<SearchCriterion> result = new BbrComboBox<SearchCriterion>(searcCriterions);
		result.setItemCaptionGenerator(SearchCriterion::getDescription);
		result.setTextInputAllowed(false);
		result.setEmptySelectionAllowed(false);
		result.setRequiredIndicatorVisible(true);
		result.setWidth(100, Unit.PERCENTAGE);
		return result;
	}

	
	private BbrComboBox<ClientDTO> getClientsComboBox() 
	{
		BbrComboBox<ClientDTO> result = null;

		if (this.getBbrUIParent().getUser() != null)
		{
			try
			{
				ClientInitParamDTO initpram = new ClientInitParamDTO();
				
				String userType = this.getBbrUIParent().getUser().getTypeDescription();
				
				initpram.setIsRetail(!userType.equals(FEPConstants.INTERNAL_CLIENT_NAME) ? true : null);
				
				ClientArrayResultDTO clientsResult = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(this.getBbrUIParent()).getAllClients(initpram);
				
				result = new BbrComboBox<ClientDTO>();
				result.setItemCaptionGenerator(ClientDTO::getName);
				result.setRequiredIndicatorVisible(true);
				result.setWidth(100, Unit.PERCENTAGE);
				result.setEnabled(false);

				if ((clientsResult != null) && (clientsResult.getValues() != null) && (clientsResult.getValues().length > 0))
				{
					result.setItems(clientsResult.getValues());
					result.addStyleName("bbr-filter-fields");
					
					ClientDTO selectedClient = Arrays.stream(clientsResult.getValues()).filter(c -> c.getCompanycode().equals(this.selectedAttribute.getClientcompanycode())).findAny().orElse(null);
					
					if(selectedClient != null)
					{
						result.setSelectedItem(selectedClient);
					}
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
	

	private void updateFormData()
	{
		if (this.selectedAttribute != null)
		{
			this.txt_B2BName.setValue(
			this.selectedAttribute.getInternalname() != null ? this.selectedAttribute.getInternalname() : " ");
			this.cbx_State.selectItem(this.selectedAttribute.getActive() ? 0 : 1);
			if (userDataTypeSelected != null)
			{
				this.cbx_DataType.setSelectedItem(userDataTypeSelected);

			}

			if (this.selectedAttribute.getAttributetypeid() != null
			&& lstAttributeType != null)
			{
				lstAttributeType.stream()
				.filter(v -> v.getId().equals(this.selectedAttribute.getAttributetypeid()))
				.findAny()
				.ifPresent(d -> {
					this.cbx_Type.setSelectedItem(d);
				});
			}

			this.txt_ProviderName.setValue(
			this.selectedAttribute.getVendorname() != null ? this.selectedAttribute.getVendorname() : " ");
			this.txtA_HelpProvide.setValue(
			this.selectedAttribute.getUserhelp() != null ? this.selectedAttribute.getUserhelp() : " ");
			this.txt_Unit.setValue(
			this.selectedAttribute.getUnit() != null ? this.selectedAttribute.getUnit() : " ");

		}
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
