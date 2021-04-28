package bbr.b2b.portal.components.filter_section.fep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vaadin.server.Resource;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeDataDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeInitParamDTO;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.fep.FepUtils;
import bbr.b2b.portal.classes.wrappers.fep.FEPProductFilterSectionInfo;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.FEPConstants;
import bbr.b2b.users.report.classes.CompanyDataDTO;
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
public class FEPProductFilterSection extends BbrVerticalSection<FEPProductFilterSectionInfo>
{

	private static final long serialVersionUID = -7659625480567661782L;


	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTOR
	// ****************************************************************************************

	public FEPProductFilterSection(BbrUI parent)
	{
		super(parent);
	}

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTOR
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************

	private BbrComboBox<SearchCriterion>		cbx_ItemStatus			= null;
	private BbrComboBox<ArticleTypeDataDTO>	cbx_Templates			= null;
	private BbrTextField								txt_Sku					= null;
	private BbrTextField								txt_Trademark			= null;
	private CompanyDataDTO							provider					= null;

	private static final String					TEMPLATE_NAME			= "description";
	private final String								DEFAULT_SORT_FIELD	= TEMPLATE_NAME;
	private final int									DEFAULT_PAGE_NUMBER	= 1;
	private final int									MAX_ROWS_NUMBER		= 100;

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

		HorizontalLayout pnlSkuHelp = new HorizontalLayout();
		pnlSkuHelp.addComponents(pnlSku, image);
		pnlSkuHelp.setSpacing(false);
		pnlSkuHelp.setExpandRatio(image, 1F);
		pnlSkuHelp.addStyleName("bbr-panel-space");
		pnlSkuHelp.setSizeFull();
		pnlSkuHelp.setMargin(false);

		// Sección Marca
		Label lbl_Trademark = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_trademark"));
		lbl_Trademark.setWidth("120px");

		this.txt_Trademark = new BbrTextField();
		this.txt_Trademark.setWidth("232px");
		this.txt_Trademark.setHeight("30px");

		// Botón info Marca
		Image imageTradeMark = new Image(null, resource);
		imageTradeMark.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "trade_mark_help"));
		imageTradeMark.setWidth("20px");
		imageTradeMark.addStyleName("padding-top-4");

		HorizontalLayout pnlTrademark = new HorizontalLayout();
		pnlTrademark.addComponents(lbl_Trademark, this.txt_Trademark);
		pnlTrademark.setExpandRatio(txt_Trademark, 1F);
		pnlTrademark.addStyleName("padding-right-4");

		HorizontalLayout pnlTrademarkHelp = new HorizontalLayout();
		pnlTrademarkHelp.addComponents(pnlTrademark, imageTradeMark);
		pnlTrademarkHelp.setSpacing(false);
		pnlTrademarkHelp.setExpandRatio(imageTradeMark, 1F);
		pnlTrademarkHelp.addStyleName("bbr-panel-space");
		pnlTrademarkHelp.setSizeFull();
		pnlTrademarkHelp.setMargin(false);

		// Sección Estado
		Label lbl_Template = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_template"));
		lbl_Template.setWidth("120px");

		this.cbx_Templates = this.updateTemplatesComboBox();

		HorizontalLayout pnlTemplateSubsection = new HorizontalLayout();
		pnlTemplateSubsection.addComponents(lbl_Template, this.cbx_Templates);
		pnlTemplateSubsection.setExpandRatio(this.cbx_Templates, 1F);
		pnlTemplateSubsection.setWidth("100%");
		pnlTemplateSubsection.addStyleName("bbr-panel-space");

		this.setWidth("400px");
		this.addStyleName("bbr-filter-sections");
		this.addStyleName("bbr-panel-space");
		this.setSpacing(false);
		this.addComponents(pnlProductsHeader, pnlProductsState, pnlSkuHelp, pnlTrademarkHelp, pnlTemplateSubsection);
	}


	@Override
	public FEPProductFilterSectionInfo getSectionResult()
	{
		FEPProductFilterSectionInfo result = null;
		if (this.validateData() == null)
		{
			SearchCriterion status = this.cbx_ItemStatus.getSelectedValue();
			String sku = this.txt_Sku.getValue();
			String tradeMark = this.txt_Trademark.getValue();

			ArticleTypeDataDTO templateItem = this.cbx_Templates.getSelectedValue();

			result = new FEPProductFilterSectionInfo(status, sku, tradeMark, templateItem);
		}

		return result;
	}


	@Override
	public String validateData()
	{
		String result = null;

		String sku = this.txt_Sku.getValue();
		String tradeMark = this.txt_Trademark.getValue();

		if (this.cbx_ItemStatus == null || this.cbx_ItemStatus.getSelectedValue() == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "status_requires");
		}
		else if (sku == null || !this.isARegularExpresion(sku))
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "sku_requires");
		}
		else if (tradeMark == null || tradeMark.trim().length() >= 100)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "trade_mark_requires");
		}
		else if (this.cbx_Templates == null || this.cbx_Templates.getSelectedValue() == null || this.cbx_Templates.getSelectedValue().getId() == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "template_requires");
		}

		return result;
	}


	@Override
	public void setSectionData(Object data)
	{
		if (this.provider != (CompanyDataDTO) data)
		{
			super.setSectionData(data);

			this.provider = (CompanyDataDTO) data;
		}
	}

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

	private boolean isARegularExpresion(String patern)
	{
		// Esta expresion regular permite filtrar cadenas que comiencen con número
		// o "*",
		// no terminen en ";" usando este caracter(";") como separador de los
		// números,
		// tambien se puede usar el * en cualquier parte de la cadena,
		// la cadena de un solo caracter "*" no es valida
		boolean result = false;
		if (patern.trim().length() == 0)
		{
			result = true;
		}
		else if (patern.trim().length() <= 100)
		{
			Pattern patron = Pattern.compile("^[*]*[0-9]+[*]*(;[*]*[0-9]+[*]*)*$");
			Matcher match = patron.matcher(patern);
			result = match.find();
		}

		return result;
	}


	private BbrComboBox<SearchCriterion> updateStateComboBox()
	{
		ArrayList<SearchCriterion> criteria = new ArrayList<>();
		
		criteria.add(new SearchCriterion(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "all_upper"), -1));
		criteria.addAll(Arrays.asList(FepUtils.getRequestStatesCriteria()));
		SearchCriterion[] criteriaArray = criteria.toArray(new SearchCriterion[criteria.size()]);
		
		BbrComboBox<SearchCriterion> result = new BbrComboBox<SearchCriterion>(criteriaArray);
		result.setItemCaptionGenerator(SearchCriterion::getDescription);
		result.setTextInputAllowed(false);
		result.selectFirstItem();
		result.setEmptySelectionAllowed(false);
		result.setWidth("255px");

		return result;
	}


	private BbrComboBox<ArticleTypeDataDTO> updateTemplatesComboBox()
	{
		BbrComboBox<ArticleTypeDataDTO> result = null;

		result = new BbrComboBox<ArticleTypeDataDTO>();
		result.setItemCaptionGenerator(ArticleTypeDataDTO::getDescription);
		result.setTextInputAllowed(false);
		result.setEmptySelectionAllowed(false);
		result.setWidth("100%");

		Integer requestedPage = DEFAULT_PAGE_NUMBER;
		Integer maxRow = MAX_ROWS_NUMBER;

		ArticleTypeInitParamDTO initParam = new ArticleTypeInitParamDTO();
		initParam.setActive(true);
		initParam.setDescription(null);
		initParam.setType(FEPConstants.ARTICLETYPE_CLASS_NAME_CP);
		initParam.setClientname(FEPConstants.INTERNAL_CLIENT_NAME);

		OrderCriteriaDTO ordercriteria[] = new OrderCriteriaDTO[1];
		OrderCriteriaDTO order = new OrderCriteriaDTO();
		order.setPropertyname(DEFAULT_SORT_FIELD);
		order.setAscending(true);
		ordercriteria[0] = order;

		ArticleTypeArrayResultDTO templatesData;

		try
		{
			templatesData = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(this.getBbrUIParent()).getArticleTypeData(initParam,
			requestedPage,
			maxRow,
			false,
			ordercriteria,
			this.getBbrUIParent().getLocale().getLanguage());

			if ((templatesData != null) && (templatesData.getValues() != null) && (templatesData.getValues().length > 0))
			{
				ArticleTypeDataDTO allTemplatesOption = new ArticleTypeDataDTO();
				allTemplatesOption.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "all_upper_f"));
				allTemplatesOption.setId(-1L);

				ArrayList<ArticleTypeDataDTO> templatesList = new ArrayList<>();
				templatesList.add(allTemplatesOption);
				templatesList.addAll(Arrays.asList(templatesData.getValues()));

				result.setItems(templatesList);
				result.selectFirstItem();
				result.addStyleName("bbr-filter-fields");
			}
			else
			{
				ArticleTypeDataDTO emptyResult = new ArticleTypeDataDTO();
				emptyResult.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "no_template_assigned"));

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

		return result;
	}

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
