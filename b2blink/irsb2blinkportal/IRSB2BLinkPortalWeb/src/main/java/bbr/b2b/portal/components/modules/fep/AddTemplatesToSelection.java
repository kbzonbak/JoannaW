package bbr.b2b.portal.components.modules.fep;

import java.io.Serializable;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.fep.common.data.classes.ArticleTypeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeDataDTO;
import bbr.b2b.fep.cp.data.classes.CPRelatedProductArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPRelatedProductDTO;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;

public class AddTemplatesToSelection extends BbrWindow
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	/**
	 * 
	 */
	private static final long				serialVersionUID	= -2928184568999564047L;

	private CPRelatedProductArrayResultDTO		relatedProducts		= null;
	private BbrComboBox<CPRelatedProductDTO>	cbx_Count		= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public AddTemplatesToSelection(BbrUI parent, CPRelatedProductArrayResultDTO		relatedProducts	)
	{
		super(parent);
		this.relatedProducts = relatedProducts;

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
		Label lbl_Description = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_description_template_to_selection"));
		lbl_Description.setWidth("100%");

		HorizontalLayout pnlDescription = new HorizontalLayout(lbl_Description);
		pnlDescription.setSizeFull();

		// Sección Plantilla
		Label lbl_Template = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_template"));
		lbl_Template.setWidth("90px");

		this.cbx_Count = this.getRelatedProductsComboBox();

		HorizontalLayout pnlTemplate = new HorizontalLayout(lbl_Template, this.cbx_Count);
		pnlTemplate.setExpandRatio(this.cbx_Count, 1F);
		pnlTemplate.setSizeFull();

		// Main Layout
		VerticalLayout optionsPanel = new VerticalLayout();
		optionsPanel.setWidth("100%");
		optionsPanel.setSpacing(true);
		optionsPanel.setMargin(false);
		optionsPanel.addComponents(pnlDescription, pnlTemplate);

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
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "title_add_template_to_selection"));
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
			CPRelatedProductDTO relatedProd = this.cbx_Count.getValue();

			BbrEvent bbrEvent = new BbrEvent(BbrEvent.ITEM_SELECTED);
			bbrEvent.setResultObject(relatedProd);

			dispatchBbrEvent(bbrEvent);
			this.close();
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
		if ((this.relatedProducts == null) || (this.relatedProducts.getValues() == null) || (relatedProducts.getValues().length == 0))
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "templates_requires");
		}

		return result;
	}

	private BbrComboBox<CPRelatedProductDTO> getRelatedProductsComboBox()
	{
		BbrComboBox<CPRelatedProductDTO> result = null;

		if (this.getBbrUIParent().getUser() != null)
		{
			result = new BbrComboBox<CPRelatedProductDTO>();
			result.setItemCaptionGenerator(CPRelatedProductDTO::getArticletypename);
			result.setTextInputAllowed(false);
			result.setEmptySelectionAllowed(false);
			result.setWidth("100%");

			if ((this.relatedProducts != null) && (this.relatedProducts.getValues() != null) && (relatedProducts.getValues().length > 0))
			{
				result.setItems(this.relatedProducts.getValues());
				result.selectFirstItem();
				result.addStyleName("bbr-filter-fields");
				// result.addValueChangeListener((e) -> filter_changeHandler(e,
				// template, result));
			}
			else
			{
				CPRelatedProductDTO emptyResult = new CPRelatedProductDTO();
				emptyResult.setCount(1);//(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "no_template_assigned"));

				result.setItems(emptyResult);
				result.setSelectedItem(emptyResult);
				result.setEnabled(false);
			}

		}
		return result;

	}
	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
