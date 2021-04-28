package bbr.b2b.portal.components.modules.fep;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.fep.common.data.classes.AttributeConstraintDataDTO;
import bbr.b2b.fep.common.data.classes.ConstraintParameterDTO;
import bbr.b2b.fep.common.data.classes.ConstraintTypeDTO;
import bbr.b2b.fep.common.data.classes.ConstraintTypesResultDTO;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.wrappers.fep.ConstrainParamsInfo;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.FEPConstants;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;
import cl.bbr.core.components.widgets.bbrtextfield.client.RestrictRange;

public class AddConstrains extends BbrWindow
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long						serialVersionUID	= -1632432553054785093L;

	private BbrComboBox<ConstraintTypeDTO>			cbx_Constrains		= null;
	private VerticalLayout							pnlValuesSection	= null;
	private BbrTextField							txt_FirstValue		= null;
	private BbrTextField							txt_SecondValue		= null;
	private ConstrainParamsInfo						params				= null;
	private ConstraintTypesResultDTO				constrains			= null;
	private ArrayList<AttributeConstraintDataDTO>	constrainsSelected	= null;
	private HorizontalLayout						pnlFirstValue		= null;
	private HorizontalLayout						pnlSecondValue		= null;
	private List<ConstraintTypeDTO>					constrainsAvalaible	= null;
	private boolean									firstCondition		= false;
	private boolean									secondCondition		= false;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public AddConstrains(BbrUI parent, ConstraintTypesResultDTO constrains, ArrayList<AttributeConstraintDataDTO> constrainsSelected)
	{
		super(parent);
		this.constrains = constrains;
		this.constrainsSelected = constrainsSelected;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	@Override
	public void initializeView()
	{
		// Sección Header

		Label lbl_ConstrainName = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_restriction_name"));
		lbl_ConstrainName.setWidth("120px");

		this.cbx_Constrains = this.getRestrictionsComboBox();
		this.pnlFirstValue = new HorizontalLayout();

		HorizontalLayout pnlConstrainName = new HorizontalLayout(lbl_ConstrainName, this.cbx_Constrains);
		pnlConstrainName.setExpandRatio(this.cbx_Constrains, 1F);
		pnlConstrainName.setSizeFull();

		Label lblFirstValue = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_first_value"));
		lblFirstValue.setWidth("120px");
		this.txt_FirstValue = new BbrTextField();
		this.txt_FirstValue.setWidth(100, Unit.PERCENTAGE);
		this.txt_FirstValue.addStyleName("bbr-filter-fields");

		this.pnlFirstValue.addComponents(lblFirstValue, this.txt_FirstValue);
		this.pnlFirstValue.setExpandRatio(this.txt_FirstValue, 1F);
		this.pnlFirstValue.setSizeFull();

		this.pnlSecondValue = new HorizontalLayout();

		Label lblSecondValue = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_second_value"));
		lblSecondValue.setWidth("120px");

		this.txt_SecondValue = new BbrTextField();
		this.txt_SecondValue.setWidth(100, Unit.PERCENTAGE);
		this.txt_SecondValue.addStyleName("bbr-filter-fields");

		this.pnlSecondValue.addComponents(lblSecondValue, this.txt_SecondValue);
		this.pnlSecondValue.setExpandRatio(this.txt_SecondValue, 1F);
		this.pnlSecondValue.setSizeFull();

		this.pnlValuesSection = new VerticalLayout();
		this.pnlValuesSection.addComponentsAndExpand(this.pnlFirstValue, this.pnlSecondValue);
		this.pnlValuesSection.setMargin(false);

		// Main Layout
		VerticalLayout optionsPanel = new VerticalLayout();
		optionsPanel.setWidth("100%");
		optionsPanel.setSpacing(true);
		optionsPanel.setMargin(false);
		optionsPanel.addComponents(pnlConstrainName, this.pnlValuesSection);

		this.updateParametersFields();
		this.restrictFields();
		
		// Accept Button
		Button btn_Select = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "accept"));
		btn_Select.setStyleName("primary");
		btn_Select.addStyleName("btn-generic");
		btn_Select.setWidth("100%");
		btn_Select.addClickListener((ClickListener & Serializable) e -> btnSelect_clickHandler(e));

		// Cancel Button
		Button btn_Cancel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"));
		btn_Cancel.setStyleName("primary");
		btn_Cancel.addStyleName("btn-generic");
		btn_Cancel.setWidth("100%");
		btn_Cancel.addClickListener((ClickListener & Serializable) e -> btnClose_clickHandler(e));

		HorizontalLayout buttonsPanel = new HorizontalLayout(btn_Select, btn_Cancel);
		buttonsPanel.addStyleName("bbr-buttons-panel");
		buttonsPanel.setWidth("300px");
		buttonsPanel.setSpacing(true);

		// Main Layout
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.addComponents(optionsPanel, buttonsPanel);
		mainLayout.setComponentAlignment(buttonsPanel, Alignment.BOTTOM_CENTER);
		mainLayout.setExpandRatio(buttonsPanel, 1F);
		mainLayout.setMargin(false);
		mainLayout.addStyleName("bbr-margin-windows");

		// Ventana
		this.setWidth(500, Unit.PIXELS);
		this.setHeight(250, Unit.PIXELS);
		this.setResizable(false);
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "title_add_constrains"));
		this.setContent(mainLayout);

	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================
	private void btnSelect_clickHandler(ClickEvent event)
	{
		String errorMsg = this.validateData();
		if (errorMsg == null)
		{
			ConstraintTypeDTO constrain = this.cbx_Constrains.getSelectedValue();

			params = new ConstrainParamsInfo(constrain, this.txt_FirstValue.getValue(), this.txt_SecondValue.getValue());
			BbrEvent bbrEvent = new BbrEvent(BbrEvent.ITEM_SELECTED);
			bbrEvent.setResultObject(params);

			dispatchBbrEvent(bbrEvent);
			close();
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
		}
	}

	private void btnClose_clickHandler(ClickEvent event)
	{
		this.close();
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
	private String validateData()
	{
		String result = null;
		ConstraintTypeDTO selectedConstrain = this.cbx_Constrains.getSelectedValue();

		if (selectedConstrain == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "constrain_requires");
		}

		if (selectedConstrain.getParams() != null && selectedConstrain.getParams().length >= 1 && this.txt_FirstValue.isEmpty())
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "first_value_requires");
		}
		if (selectedConstrain.getParams() != null && selectedConstrain.getParams().length == 2 && this.txt_SecondValue.isEmpty())
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "second_value_requires");
		}

		return result;
	}

	private BbrComboBox<ConstraintTypeDTO> getRestrictionsComboBox()
	{
		BbrComboBox<ConstraintTypeDTO> result = null;

		if (this.getBbrUIParent().getUser() != null)
		{
			result = new BbrComboBox<ConstraintTypeDTO>();
			result.setItemCaptionGenerator(ConstraintTypeDTO::getName);
			result.setTextInputAllowed(false);
			result.setEmptySelectionAllowed(false);
			result.setWidth("100%");

			if ((this.constrains != null) && (this.constrains.getValues() != null) && (this.constrains.getValues().length > 0))
			{
				this.constrainsAvalaible = Arrays.stream(this.constrains.getValues()).collect(Collectors.toList());
				if (this.constrainsSelected != null && this.constrainsSelected.size() > 0)
				{
					List<Long> consTypeidList = this.constrainsSelected.stream().map(c -> c.getConstypeid()).collect(Collectors.toList());
					this.constrainsAvalaible = Arrays.stream(this.constrains.getValues()).filter(constraint -> !consTypeidList.contains(constraint.getId())).collect(Collectors.toList());
				}

				if (constrainsAvalaible != null && constrainsAvalaible.size() > 0)
				{
					result.setItems(constrainsAvalaible);
					result.selectFirstItem();
					result.addValueChangeListener((e) -> filter_changeHandler(e));
					result.addStyleName("bbr-filter-fields");
				}
				else
				{
					selectConstraintTypeByDefault(result);
				}
			}
			else
			{
				selectConstraintTypeByDefault(result);
			}
		}

		return result;

	}

	private void selectConstraintTypeByDefault(BbrComboBox<ConstraintTypeDTO> result)
	{
		ConstraintTypeDTO emptyResult = new ConstraintTypeDTO();
		emptyResult.setName(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "no_constrains_assigned"));

		result.setItems(emptyResult);
		result.setSelectedItem(emptyResult);
		result.setEnabled(false);
	}

	private void filter_changeHandler(ValueChangeEvent<ConstraintTypeDTO> e)
	{
		this.updateParametersFields();
		this.restrictFields();
	}

	private void updateParametersFields()
	{
		this.firstCondition = this.cbx_Constrains.getSelectedValue().getParams() != null ? this.cbx_Constrains.getSelectedValue().getParams().length >= 1 : false;
		this.secondCondition = this.cbx_Constrains.getSelectedValue().getParams() != null ? this.cbx_Constrains.getSelectedValue().getParams().length == 2 : false;
		this.pnlFirstValue.setVisible(this.firstCondition);
		this.pnlSecondValue.setVisible(this.secondCondition);
	}

	private void restrictFields()
	{
		BbrTextField textField = null;
		ConstraintParameterDTO param = null;
		if (this.firstCondition)
		{
			textField = this.txt_FirstValue; 
			param = this.cbx_Constrains.getSelectedValue().getParams()[0];
		}
		if (this.secondCondition)
		{
			textField = this.txt_SecondValue; 
			param = this.cbx_Constrains.getSelectedValue().getParams()[1];
		}
		
		if (textField!= null && textField.isVisible())
		{
			switch (param.getBasictypeinternalname())
			{
				case FEPConstants.ATTRIBUTE_BASIC_TYPE_INTEGER:
					textField.setRestrict(RestrictRange.NUMBERS);
					break;
				case FEPConstants.ATTRIBUTE_BASIC_TYPE_FLOAT:
					textField.setRestrict(RestrictRange.DOUBLE);
					break;
				case FEPConstants.ATTRIBUTE_BASIC_TYPE_STRING:
					textField.setRestrict(RestrictRange.ALL_LETTERS_SPACE);
					break;
				default:
					break;
			}

		}

	}
	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
