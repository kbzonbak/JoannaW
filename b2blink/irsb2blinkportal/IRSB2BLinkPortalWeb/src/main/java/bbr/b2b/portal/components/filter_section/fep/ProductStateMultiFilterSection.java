package bbr.b2b.portal.components.filter_section.fep;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.datefield.DateTimeResolution;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.fep.FepUtils;
import bbr.b2b.portal.classes.wrappers.fep.ProductStateMultiFilterSectionInfo;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.FEPConstants;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.constants.CoreConstants;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrDateTimeField;
import cl.bbr.core.components.basics.BbrHSeparator;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class ProductStateMultiFilterSection extends BbrVerticalSection<ProductStateMultiFilterSectionInfo>
{

	private static final long serialVersionUID = -7659625480567661782L;


	// ==================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTOR
	// ==================================================================================================
	public ProductStateMultiFilterSection(BbrUI parent)
	{
		super(parent);
	}

	// ==================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTOR
	// ==================================================================================================

	// ==================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// ==================================================================================================

	private BbrComboBox<SearchCriterion>	cbx_FilterCriteria	= null;
	private BbrComboBox<SearchCriterion>	cbx_ItemStatus			= null;
	private HorizontalLayout					pnlProductsState		= null;
	private HorizontalLayout					pnlSKUAndHelp			= null;
	private HorizontalLayout					pnlPeriodsSubsection	= null;
	private BbrTextField							txt_Sku					= null;
	private BbrDateTimeField					datFld_SinceDate		= null;
	private BbrDateTimeField					datFld_UntilDate		= null;

	// ==================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// ==================================================================================================


	// ==================================================================================================
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ==================================================================================================
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

		// Sección Criterio

		Label lbl_Filter = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_filter"));
		lbl_Filter.setWidth("120px");

		this.cbx_FilterCriteria = this.updateCriteriaComboBox();

		HorizontalLayout pnlFilterCriteria = new HorizontalLayout();
		pnlFilterCriteria.addComponents(lbl_Filter, this.cbx_FilterCriteria);
		pnlFilterCriteria.setExpandRatio(this.cbx_FilterCriteria, 1F);
		pnlFilterCriteria.setWidth("100%");
		pnlFilterCriteria.addStyleName("bbr-panel-space");

		this.pnlProductsState = this.getStateSection();

		this.pnlSKUAndHelp = this.getSKUSection();
		this.pnlSKUAndHelp.setVisible(false);

		this.pnlPeriodsSubsection = this.getPeriodsSelectionPanel();
		this.pnlPeriodsSubsection.setVisible(false);

		this.setWidth("400px");
		this.addStyleName("bbr-filter-sections");
		this.addStyleName("bbr-panel-space");
		this.setSpacing(false);
		this.addComponents(pnlProductsHeader, pnlFilterCriteria, this.pnlProductsState, this.pnlSKUAndHelp, this.pnlPeriodsSubsection);
	}


	@Override
	public ProductStateMultiFilterSectionInfo getSectionResult()
	{
		ProductStateMultiFilterSectionInfo result = null;
		
		if (this.validateData() == null)
		{
			SearchCriterion filterCriterion = (this.cbx_FilterCriteria != null) ? this.cbx_FilterCriteria.getSelectedValue() : null;
			SearchCriterion stateCriterion = (this.cbx_ItemStatus != null) ? this.cbx_ItemStatus.getSelectedValue() : null;
			String sku = (this.txt_Sku != null) ? this.txt_Sku.getValue() : null;
			LocalDateTime sinceDate = (this.datFld_SinceDate != null) ? this.datFld_SinceDate.getValue() : null;
			LocalDateTime untilDate = (this.datFld_UntilDate != null) ? this.datFld_UntilDate.getValue() : null;
			
			result = new ProductStateMultiFilterSectionInfo(filterCriterion, stateCriterion, sku, sinceDate, untilDate);
		}

		return result;
	}


	@Override
	public String validateData()
	{
		String result = null;
		SearchCriterion selectedFilterType = this.cbx_FilterCriteria.getSelectedValue();
		
		if (selectedFilterType.getId().equals(FEPConstants.STATE_FILTER_VALUE) && 
			(this.cbx_ItemStatus == null) || (this.cbx_ItemStatus.getSelectedValue() == null))
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "status_requires");
		}
		else if (selectedFilterType.getId().equals(FEPConstants.SKU_FILTER_VALUE) && 
				  ((this.txt_Sku.getValue() == null) || !this.isARegularExpresion(this.txt_Sku.getValue())))
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "sku_requires");
		}
		else if (selectedFilterType.getId().equals(FEPConstants.CREATION_DATE_FILTER_VALUE) || selectedFilterType.getId().equals(FEPConstants.UPDATE_DATE_FILTER_VALUE))
		{
			if (this.datFld_SinceDate == null)
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "valid_since_date");
			}
			else if (this.datFld_UntilDate == null)
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "valid_until_date");
			}
			else 
			{
				LocalDateTime selectedStartDate = this.datFld_SinceDate.getOptionalValue().orElseGet(null);
				LocalDateTime selectedEndDate = this.datFld_UntilDate.getOptionalValue().orElseGet(null);
				
				if(selectedStartDate != null)
				{
					if(selectedEndDate != null)
					{
						if(selectedStartDate.isAfter(selectedEndDate))
						{
							result = I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_GENERIC, "valid_range_date");	
						}
					}
					else
					{
						result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "valid_until_date");
					}
				}
				else
				{
					result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "valid_since_date");
				}
			}
		}

		return result;
	}

	// ==================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ==================================================================================================

	// ==================================================================================================
	// BEGINNING SECTION ----> EVENT HANDLERS
	// ==================================================================================================

	// ==================================================================================================
	// BEGINNING SECTION ----> EVENT HANDLERS
	// ==================================================================================================


	// ==================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ==================================================================================================

	private boolean isARegularExpresion(String patern)
	{
		// Esta expresion regular permite filtrar cadenas que comiencen con número
		// o "*",
		// no terminen en ";" usando este caracter(";") como separador de los
		// números,
		// tambien se puede usar el * en cualquier parte de la cadena, la cadena
		// de un solo caracter "*" no es valida

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


	private BbrComboBox<SearchCriterion> updateCriteriaComboBox()
	{
		SearchCriterion[] searchCriteria = FepUtils.getProductStateFilterCriteria();
		BbrComboBox<SearchCriterion> result = new BbrComboBox<SearchCriterion>(searchCriteria);
		result.setItemCaptionGenerator(SearchCriterion::getDescription);
		result.setTextInputAllowed(false);
		result.addSelectionListener((SingleSelectionListener<SearchCriterion>) s -> this.criterionSelectionHandler(s));
		result.selectFirstItem();
		result.setEmptySelectionAllowed(false);
		result.setWidth("255px");

		return result;
	}


	private void criterionSelectionHandler(SingleSelectionEvent<SearchCriterion> event)
	{
		if(this.cbx_FilterCriteria != null)
		{
			SearchCriterion selectedState = this.cbx_FilterCriteria.getSelectedValue();

			switch (selectedState.getId())
			{
				case FEPConstants.STATE_FILTER_VALUE:
					this.pnlPeriodsSubsection.setVisible(false);
					this.pnlSKUAndHelp.setVisible(false);
					this.pnlProductsState.setVisible(true);
					this.cbx_ItemStatus.selectFirstItem();
					break;

				case FEPConstants.SKU_FILTER_VALUE:
					this.pnlPeriodsSubsection.setVisible(false);
					this.pnlProductsState.setVisible(false);
					this.pnlSKUAndHelp.setVisible(true);
					this.txt_Sku.setValue("");
					break;

				case FEPConstants.CREATION_DATE_FILTER_VALUE:
				case FEPConstants.UPDATE_DATE_FILTER_VALUE:
					this.pnlProductsState.setVisible(false);
					this.pnlSKUAndHelp.setVisible(false);
					this.pnlPeriodsSubsection.setVisible(true);

					LocalDateTime currentDate = LocalDateTime.now();
					LocalDateTime firstDayOfMonth = currentDate.withDayOfMonth(1);

					this.datFld_SinceDate.setValue(firstDayOfMonth);
					this.datFld_UntilDate.setValue(currentDate);
					break;

				default:
					break;
			}
		}
	}


	private HorizontalLayout getStateSection()
	{
		// Sección Estado
		Label lbl_ProductState = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_state"));
		lbl_ProductState.setWidth("120px");

		if (this.cbx_ItemStatus == null)
		{
			SearchCriterion[] searchCriteria = FepUtils.getRequestStatesCriteria();
			this.cbx_ItemStatus = new BbrComboBox<SearchCriterion>(searchCriteria);
			this.cbx_ItemStatus.setItemCaptionGenerator(SearchCriterion::getDescription);
			this.cbx_ItemStatus.setTextInputAllowed(false);
			this.cbx_ItemStatus.selectFirstItem();
			this.cbx_ItemStatus.setEmptySelectionAllowed(false);
			this.cbx_ItemStatus.setWidth("255px");
		}

		HorizontalLayout pnlProductsState = new HorizontalLayout();
		pnlProductsState.addComponents(lbl_ProductState, this.cbx_ItemStatus);
		pnlProductsState.setExpandRatio(this.cbx_ItemStatus, 1F);
		pnlProductsState.setWidth("100%");
		pnlProductsState.addStyleName("bbr-panel-space");

		return pnlProductsState;
	}


	private HorizontalLayout getSKUSection()
	{
		// Sección Sku
		Label lbl_Sku = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_sku"));
		lbl_Sku.setWidth("120px");

		this.txt_Sku = new BbrTextField();
		this.txt_Sku.setWidth("232px");
		this.txt_Sku.setHeight("25px");

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

		HorizontalLayout pnlSKUAndHelp = new HorizontalLayout();
		pnlSKUAndHelp.addComponents(pnlSku, image);
		pnlSKUAndHelp.setSpacing(false);
		pnlSKUAndHelp.setExpandRatio(image, 1F);
		pnlSKUAndHelp.addStyleName("bbr-panel-space");
		pnlSKUAndHelp.setSizeFull();
		pnlSKUAndHelp.setMargin(false);

		return pnlSKUAndHelp;
	}


	private HorizontalLayout getPeriodsSelectionPanel()
	{
		// Selección Períodos

		this.datFld_SinceDate = new BbrDateTimeField();
		this.datFld_SinceDate.setParseErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_start_date"));
		this.datFld_SinceDate.setLocale(this.getBbrUIParent().getUser().getLocale());
		this.datFld_SinceDate.setDateFormat("dd-MM-yyyy");
		this.datFld_SinceDate.setResolution(DateTimeResolution.DAY);
		this.datFld_SinceDate.setWidth("125px");
		this.datFld_SinceDate.setHeight("35px");
		this.datFld_SinceDate.setTextFieldEnabled(false);

		Label lblSince = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "start_date"));

		HorizontalLayout pnlSinceSubsection = new HorizontalLayout();
		pnlSinceSubsection = new HorizontalLayout();
		pnlSinceSubsection.addComponents(lblSince, this.datFld_SinceDate);
		pnlSinceSubsection.setSpacing(true);

		this.datFld_UntilDate = new BbrDateTimeField();
		this.datFld_UntilDate.setParseErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_end_date"));
		this.datFld_UntilDate.setLocale(this.getBbrUIParent().getUser().getLocale());
		this.datFld_UntilDate.setDateFormat("dd-MM-yyyy");
		this.datFld_UntilDate.setResolution(DateTimeResolution.DAY);
		this.datFld_UntilDate.setWidth("125px");
		this.datFld_UntilDate.setHeight("35px");
		this.datFld_UntilDate.setTextFieldEnabled(false);

		Label lblUntil = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_GENERIC, "end_date"));

		HorizontalLayout pnlUntilSubsection = new HorizontalLayout();
		pnlUntilSubsection = new HorizontalLayout();
		pnlUntilSubsection.addComponents(lblUntil, this.datFld_UntilDate);
		pnlUntilSubsection.setSpacing(true);

		HorizontalLayout result = new HorizontalLayout();
		result.setWidth("400px");
		result.addComponents(pnlSinceSubsection, new BbrHSeparator("10px"), pnlUntilSubsection);
		result.setExpandRatio(pnlUntilSubsection, 1F);
		result.setSpacing(true);
		result.addStyleName("bbr-panel-space");

		return result;
	}

	// ==================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// ==================================================================================================

}
