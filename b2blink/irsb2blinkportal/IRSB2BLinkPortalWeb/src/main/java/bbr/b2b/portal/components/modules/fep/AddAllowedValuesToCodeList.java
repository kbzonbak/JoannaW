package bbr.b2b.portal.components.modules.fep;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.wrappers.fep.CodeListParamsInfo;
import bbr.b2b.portal.components.utils.fep.LanguagesAttributesValue;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class AddAllowedValuesToCodeList extends BbrWindow
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long							serialVersionUID	= -6793423909998436245L;

	private BbrTextField									txt_Description	= null;
	private BbrTextField									txt_Code				= null;

	private ArrayList<LanguagesAttributesValue>	currentValues		= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public AddAllowedValuesToCodeList(BbrUI parent, ArrayList<LanguagesAttributesValue> currentValues)
	{
		super(parent);
		this.currentValues = currentValues;
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

		Label lbl_Description = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_description"));
		lbl_Description.setWidth("120px");

		this.txt_Description = new BbrTextField();
		this.txt_Description.setWidth(100, Unit.PERCENTAGE);
		this.txt_Description.setRestrict("a-z,A-Z,0-9,(,), ,_,&,\\-");
		this.txt_Description.addStyleName("bbr-filter-fields");

		HorizontalLayout pnlDescription = new HorizontalLayout(lbl_Description, this.txt_Description);
		pnlDescription.setExpandRatio(this.txt_Description, 1F);
		pnlDescription.setSizeFull();

		Label lbl_Code = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_code"));
		lbl_Code.setWidth("120px");

		this.txt_Code = new BbrTextField();
		this.txt_Code.setWidth(100, Unit.PERCENTAGE);
		this.txt_Code.setRestrict("a-z,A-Z,0-9");
		this.txt_Code.addStyleName("bbr-filter-fields");

		HorizontalLayout pnlCode = new HorizontalLayout(lbl_Code, this.txt_Code);
		pnlCode.setExpandRatio(this.txt_Code, 1F);
		pnlCode.setSizeFull();

		// Main Layout
		VerticalLayout optionsPanel = new VerticalLayout();
		optionsPanel.setWidth("100%");
		optionsPanel.setSpacing(true);
		optionsPanel.setMargin(false);
		optionsPanel.addComponents(pnlDescription, pnlCode);

		// Accept Button
		Button btn_Select = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "add"));
		btn_Select.setStyleName("primary");
		btn_Select.addStyleName("btn-generic");
		btn_Select.setWidth("100%");
		btn_Select.addClickListener((ClickListener & Serializable) e -> btnSelect_clickHandler(e));
		btn_Select.setClickShortcut(KeyCode.ENTER);

		// Cancel Button
		Button btn_Cancel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "close"));
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
		this.setHeight(200, Unit.PIXELS);
		this.setResizable(false);
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "title_add_allowed_values"));
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
			String description = this.txt_Description.getValue();
			String code = this.txt_Code.getValue();
			CodeListParamsInfo codeListParamsInfo = new CodeListParamsInfo(description, code);

			BbrEvent bbrEvent = new BbrEvent(BbrEvent.ITEM_SELECTED);
			bbrEvent.setResultObject(codeListParamsInfo);

			dispatchBbrEvent(bbrEvent);
			this.txt_Description.clear();
			this.txt_Code.clear();
			this.txt_Description.focus();
			this.txt_Code.focus();
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
		List <String> codeValues = new ArrayList<String>();
		
		if (this.currentValues.size() > 0)
		{
			for (LanguagesAttributesValue av : this.currentValues)
			{
				codeValues.addAll(av.getMapLanguages().values().stream().map(a->this.getCodeStringFromAttValue(a.getValue().trim())).collect(Collectors.toList()));
			}
		}
		
		if (this.txt_Description.getValue().isEmpty())
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "description_requires");
		}
		
		if (this.txt_Code.getValue().isEmpty())
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "code_requires");
		}
		else if ((codeValues != null) && (codeValues.contains(this.txt_Code.getValue().trim())))
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "code_must_be_different");
		}

		return result;
	}
	
	
	private String getCodeStringFromAttValue(String attributeValue)
	{
		String result = "";
		
		if(attributeValue != null)
		{
			// El formato del valor del atributo como está presente en la grilla es 'desc_con_espacios [codigo]'
			
			String [] valueParts = attributeValue.split(" ");
			String codePart		= valueParts[valueParts.length-1];
			result 					= codePart.substring(1, codePart.length()-1);
		}
		
		return result;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
