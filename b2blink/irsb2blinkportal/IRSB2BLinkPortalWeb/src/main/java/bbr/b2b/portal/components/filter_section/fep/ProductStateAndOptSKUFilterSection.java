package bbr.b2b.portal.components.filter_section.fep;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.server.Resource;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.fep.FepUtils;
import bbr.b2b.portal.classes.wrappers.fep.StateAndSKUFilterSectionInfo;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.FEPConstants;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.constants.CoreConstants;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

/**
 * Representa la sección de un filtro relacionada con los datos de los
 * productos.
 */
public class ProductStateAndOptSKUFilterSection extends BbrVerticalSection<StateAndSKUFilterSectionInfo>
{

	private static final long serialVersionUID = -7659625480567661782L;


	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTOR
	// ****************************************************************************************
	public ProductStateAndOptSKUFilterSection(BbrUI parent)
	{
		super(parent);
	}

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTOR
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************

	private BbrComboBox<SearchCriterion>	cbx_ItemStatus	= null;
	
	public BbrComboBox<SearchCriterion> getCbx_ItemStatus()
	{
		return cbx_ItemStatus;
	}

	private BbrTextField						txt_Sku			= null;
	private HorizontalLayout 				pnlSkuAndHelp	= null;

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
		Label lbl_Products = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_product"));
		lbl_Products.addStyleName("bbr-panel-space");
		lbl_Products.setWidth("200px");

		HorizontalLayout pnlProductsHeader = new HorizontalLayout();
		pnlProductsHeader.addStyleName("bbr-filter-label-header");
		pnlProductsHeader.addComponents(lbl_Products);
		pnlProductsHeader.setWidth("100%");

		// Sección Estado
		Label lbl_ProductState = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_state"));
		lbl_ProductState.setWidth("120px");

		this.cbx_ItemStatus = this.updateStateComboBox();

		HorizontalLayout pnlProductsState = new HorizontalLayout();
		pnlProductsState.addComponents(lbl_ProductState, this.cbx_ItemStatus);
		pnlProductsState.setExpandRatio(this.cbx_ItemStatus, 1F);
		pnlProductsState.setWidth("100%");
		pnlProductsState.addStyleName("bbr-panel-space");

		// Sección Sku
		Label lbl_Sku = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_sku"));
		lbl_Sku.setWidth("120px");

		this.txt_Sku = new BbrTextField();
		this.txt_Sku.setWidth("232px");
		this.txt_Sku.setHeight("30px");

		// Botón info Sku
		Resource resource = BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_Help.png");
		Image image = new Image(null, resource);
		image.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "sku_help"));
		image.setWidth("20px");
		image.addStyleName("padding-top-4");

		HorizontalLayout pnlSku = new HorizontalLayout();
		pnlSku.addComponents(lbl_Sku, this.txt_Sku);
		pnlSku.setExpandRatio(txt_Sku, 1F);
		pnlSku.addStyleName("padding-right-4");

		this.pnlSkuAndHelp = new HorizontalLayout();
		this.pnlSkuAndHelp.addComponents(pnlSku, image);
		this.pnlSkuAndHelp.setSpacing(false);
		this.pnlSkuAndHelp.setExpandRatio(image, 1F);
		this.pnlSkuAndHelp.addStyleName("bbr-panel-space");
		this.pnlSkuAndHelp.setSizeFull();
		this.pnlSkuAndHelp.setMargin(false);
		this.pnlSkuAndHelp.setVisible(false);

		this.setWidth("400px");
		this.addStyleName("bbr-filter-sections");
		this.addStyleName("bbr-panel-space");
		this.setSpacing(false);
		this.addComponents(pnlProductsHeader, pnlProductsState, this.pnlSkuAndHelp);
	}


	@Override
	public StateAndSKUFilterSectionInfo getSectionResult()
	{
		StateAndSKUFilterSectionInfo result = null;
		if (this.validateData() == null)
		{
			SearchCriterion searchCriterion = (this.cbx_ItemStatus != null) ? this.cbx_ItemStatus.getSelectedValue() : null;
			String sku = this.txt_Sku.getValue();

			result = new StateAndSKUFilterSectionInfo(searchCriterion, sku);
		}

		return result;
	}


	@Override
	public String validateData()
	{
		String result = null;
		String sku = this.txt_Sku.getValue();
		
		this.cbx_ItemStatus.getSelectedValue();
		
		if (this.cbx_ItemStatus == null || this.cbx_ItemStatus.getSelectedValue() == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "status_requires");
		}
		else if (sku == null || !this.isARegularExpresion(sku))
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "sku_requires");
		}

		return result;
	}

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENT HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENT HANDLERS
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

	private boolean isARegularExpresion(String patern)
	{
		// Esta expresion regular permite filtrar cadenas que comiencen con número o "*",
		// no terminen en ";" usando este caracter(";") como separador de los números,
		// tambien se puede usar el * en cualquier parte de la cadena, la cadena de un solo caracter "*" no es valida
		
		boolean result = false;
		
		if (patern.trim().length() == 0)
		{
			result = true;
		}
		else if (patern.trim().length() <= 100)
		{
			Pattern patron = Pattern.compile("^[0-9, *]+(;?[0-9,*]*)*[^;]$");
			Matcher match = patron.matcher(patern);
			result = match.find();
		}

		return result;
	}


	private BbrComboBox<SearchCriterion> updateStateComboBox()
	{
		SearchCriterion[] searchCriteria = FepUtils.getRequestStatesCriteria();
		BbrComboBox<SearchCriterion> result = new BbrComboBox<SearchCriterion>(searchCriteria);
		result.setItemCaptionGenerator(SearchCriterion::getDescription);
		result.setTextInputAllowed(false);
		result.addSelectionListener((SingleSelectionListener<SearchCriterion>) s -> this.stateSelectionHandler(s));
		result.selectFirstItem();
		result.setEmptySelectionAllowed(false);
		result.setWidth("255px");

		return result;
	}
	
	
	private void stateSelectionHandler(SingleSelectionEvent<SearchCriterion> event)
	{
		if((event != null) && event.getSelectedItem().isPresent() && (this.pnlSkuAndHelp != null))
		{
			SearchCriterion selectedState = event.getSelectedItem().get();
			this.pnlSkuAndHelp.setVisible(selectedState.getId().equals(FEPConstants.APPROVED_STATE_VALUE));
		}
	}

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
