//package bbr.b2b.portal.components.filter_section.customer;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//
//import org.apache.commons.lang3.StringUtils;
//
//import com.vaadin.data.HasValue.ValueChangeEvent;
//import com.vaadin.data.HasValue.ValueChangeListener;
//import com.vaadin.icons.VaadinIcons;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Button.ClickListener;
//import com.vaadin.ui.CheckBox;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Label;
//
//import bbr.b2b.fep.commercial.data.classes.GetMarksByProviderAndCategoryInitParamDTO;
//import bbr.b2b.fep.commercial.data.classes.MarkArrayResultDTO;
//import bbr.b2b.fep.commercial.data.classes.MarkDTO;
//import bbr.b2b.portal.classes.factory.EJBFactory;
//import bbr.b2b.portal.classes.i18n.I18NManager;
//import bbr.b2b.portal.classes.utils.app.AppUtils;
//import bbr.b2b.portal.classes.utils.commercial.CategoryUtils;
//import bbr.b2b.portal.classes.utils.commercial.CommercialUtils;
//import bbr.b2b.portal.classes.wrappers.commercial.CategoryItem;
//import bbr.b2b.portal.classes.wrappers.customer.ProductFilterSectionInfo;
//import bbr.b2b.portal.components.basics.BbrSystemException;
//import bbr.b2b.portal.components.basics.BbrUserException;
//import bbr.b2b.portal.components.basics.BbrValidateCommercialException;
//import bbr.b2b.portal.components.utils.commercial.SelectProductCategory;
//import bbr.b2b.portal.components.utils.customer.FindProduct;
//import bbr.b2b.portal.constants.BbrUtilsResources;
//import bbr.b2b.users.report.classes.CompanyDataDTO;
//import cl.bbr.core.classes.container.BbrSectionEvent;
//import cl.bbr.core.classes.container.BbrVerticalSection;
//import cl.bbr.core.classes.errors.BbrError;
//import cl.bbr.core.classes.errors.ErrorsMngr;
//import cl.bbr.core.classes.events.BbrEvent;
//import cl.bbr.core.classes.events.BbrEventListener;
//import cl.bbr.core.components.basics.BbrComboBox;
//import cl.bbr.core.components.basics.BbrHSeparator;
//import cl.bbr.core.components.basics.BbrItemSelectField;
//import cl.bbr.core.components.basics.BbrUI;
//
///**
// * Representa la sección de un filtro relacionada con los datos de los
// * productos.
// */
//public class ProductFilterSection extends BbrVerticalSection<ProductFilterSectionInfo>
//{
//
//	// ****************************************************************************************
//	// BEGINNING SECTION ----> CONSTRUCTOR
//	// ****************************************************************************************
//	/**
//	 * Crea una nueva instancia de la sección de productos.
//	 *
//	 * @param enableCategoriesSelection
//	 *            Define si se mostrará la subsección de Categorías
//	 * @param enableTrademarkSelection
//	 *            Define si se mostrará la subsección de Marcas
//	 * @param enableActiveProductsSelection
//	 *            Define si se mostrará la subsección de Productos Activos
//	 */
//	public ProductFilterSection(BbrUI parent, Boolean enableCategoriesSelection, Boolean enableTrademarkSelection, Boolean enableActiveProductsSelection)
//	{
//		super(parent);
//		this.enableCategoriesSelection = enableCategoriesSelection;
//		this.enableTrademarkSelection = enableTrademarkSelection;
//		this.enableActiveProductsSelection = enableActiveProductsSelection;
//	}
//
//	private ProductFilterSection(ProductFilterSectionBuilder builder)
//	{
//		super(builder.parent);
//		this.enableCategoriesSelection = builder.isCategoriesSelectionEnabled;
//		this.enableTrademarkSelection = builder.isTrademarkSelectionEnabled;
//		this.enableActiveProductsSelection = builder.isActiveProductsSelectionEnabled;
//		this.isCategoryHeaderEnabled = builder.isCategoryHeaderEnabled;
//		this.searchByRetailerCategory = builder.searchByRetailerCategory;
//	}
//
//	// ****************************************************************************************
//	// BEGINNING SECTION ----> CONSTRUCTOR
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION ----> PROPERTIES
//	// ****************************************************************************************
//	private static final long					serialVersionUID				= 1186685095866858478L;
//
//	public String								CBX_ONLY_ACTIVE_PRODUCTS_EVENT	= "CBX_ONLY_ACTIVE_PRODUCTS_EVENT";
//
//	private Label								lbl_CategoryHierarchy			= null;
//
//	private BbrComboBox<MarkDTO>				cbx_Trademarks					= null;
//
//	private CheckBox							chkBox_OnlyActiveProducts		= null;
//
//	private BbrItemSelectField<CategoryItem>	selF_SelectProductCategory		= null;
//
//	private CompanyDataDTO						provider						= null;
//
//	private Boolean								searchByRetailerCategory		= true;
//
//	private Boolean								enableCategoriesSelection		= true;
//
//	private Boolean								enableTrademarkSelection		= true;
//
//	private Boolean								enableActiveProductsSelection	= true;
//
//	private ArrayList<MarkDTO>					allTrademarks					= null;
//
//	private Boolean								isCategoryHeaderEnabled			= true;
//
//	private int									maxLevel						= 1;
//
//	private int									minLevel						= 1;
//
//	private boolean								isByRange						= false;
//
//	private CategoryItem						defaultCatProductItem;
//
//	private String								categoryTitle					= "";
//
//	private String								categoryPlaceHolder				= "";
//
//	private String								categoryErrorMessage			= null;
//
//	/**
//	 * Define un rango para los niveles de los nodos
//	 * 
//	 * @author Richard Lozada
//	 * @param maxLevel
//	 * @param minLevel
//	 */
//	public void setRangeToLevels(int minLevel, int maxLevel)
//	{
//		this.minLevel = minLevel;
//		this.maxLevel = maxLevel;
//		this.isByRange = true;
//	}
//
//	public void setCategoryTitle(String categoryTitle)
//	{
//		this.categoryTitle = categoryTitle;
//	}
//
//	public void setCategoryPlaceHolder(String categoryPlaceHolder)
//	{
//		this.categoryPlaceHolder = categoryPlaceHolder;
//	}
//
//	public void setCategoryErrorMessage(String categoryErrorMessage)
//	{
//		this.categoryErrorMessage = categoryErrorMessage;
//	}
//
//	// ****************************************************************************************
//	// ENDING SECTION ----> PROPERTIES
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION ----> OVERRIDDEN METHODS
//	// ****************************************************************************************
//	@Override
//	public void initializeView()
//	{
//		// Header y Links
//
//		Label lbl_Products = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "products"));
//		lbl_Products.addStyleName("bbr-panel-space");
//		lbl_Products.setWidth("200px");
//
//		HorizontalLayout pnlLinks = this.getLinksPanel();
//		pnlLinks.setWidth("100%");
//		HorizontalLayout pnlProductsHeader = new HorizontalLayout();
//		pnlProductsHeader.addStyleName("bbr-filter-label-header");
//		pnlProductsHeader.addComponents(lbl_Products, pnlLinks);
//		pnlProductsHeader.setExpandRatio(pnlLinks, 1F);
//		pnlProductsHeader.setWidth("100%");
//
//		// Sección Categorias, Marcas y Prod. Activos
//		HorizontalLayout pnlCategorySubsection = this.getCategorySelectionPanel();
//		HorizontalLayout pnlTrademarksSubsection = this.getTrademarksSelectionPanel();
//		HorizontalLayout pnlActiveProdsSubsection = this.getActiveProdsSelectionPanel();
//
//		this.setWidth("400px");
//		this.addStyleName("bbr-filter-sections");
//		this.addStyleName("bbr-panel-space");
//		this.setSpacing(false);
//		this.addComponents(pnlProductsHeader, pnlCategorySubsection, pnlTrademarksSubsection, pnlActiveProdsSubsection);
//
//		this.updateCategoriesSelector();
//	}
//
//	@Override
//	public ProductFilterSectionInfo getSectionResult()
//	{
//		CategoryItem productCategory = (this.enableCategoriesSelection) ? this.selF_SelectProductCategory.getValue() : null;
//		MarkDTO tradeMark = (this.enableTrademarkSelection) ? this.cbx_Trademarks.getSelectedValue() : null;
//		Boolean isOnlyActiveProducts = (this.enableActiveProductsSelection) ? this.chkBox_OnlyActiveProducts.getValue() : false;
//		Boolean isRetailerCategory = this.searchByRetailerCategory;
//
//		ProductFilterSectionInfo result = new ProductFilterSectionInfo(productCategory, tradeMark, isOnlyActiveProducts, isRetailerCategory);
//
//		return result;
//	}
//
//	@Override
//	public String validateData()
//	{
//		String result = null;
//
//		if (this.enableCategoriesSelection)
//		{
//			CategoryItem productCategory = this.selF_SelectProductCategory.getValue();
//
//			if (productCategory == null || !this.selF_SelectProductCategory.isEnabled())
//			{
//				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "product_requires");
//			}
//		}
//
//		// Se valida, pues si result != null se debe evitar sobreescribir
//		// el error registrado
//
//		if ((result == null) && this.enableTrademarkSelection)
//		{
//			MarkDTO tradeMark = this.cbx_Trademarks.getSelectedValue();
//
//			if (tradeMark == null || !this.cbx_Trademarks.isEnabled())
//			{
//				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "trademark_requires");
//			}
//		}
//
//		if (isByRange)
//		{
//			CategoryItem productCategory = this.selF_SelectProductCategory.getValue();
//			if (productCategory.getLevel() < this.minLevel || productCategory.getLevel() > this.maxLevel)
//			{
//				result = this.categoryErrorMessage != null ? this.categoryErrorMessage : I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "category_level_requires");
//			}
//		}
//
//		return result;
//	}
//
//	@Override
//	public void setSectionData(Object data)
//	{
//		if (data instanceof Boolean)
//		{
//			if (this.chkBox_OnlyActiveProducts != null)
//			{
//				Boolean dataValue = (Boolean) data;
//				if (dataValue)
//				{
//					this.chkBox_OnlyActiveProducts.setValue(!dataValue);
//				}
//			}
//		}
//		else
//		{
//			if (this.provider != (CompanyDataDTO) data)
//			{
//
//				super.setSectionData(data);
//				this.provider = (CompanyDataDTO) data;
//				if (this.enableTrademarkSelection)
//				{
//					if (this.cbx_Trademarks != null)
//					{
//						this.updateTradeMarkComboBox(this.provider);
//					}
//				}
//				if (this.enableCategoriesSelection)
//				{
//					this.updateCategoriesSelector();
//				}
//
//			}
//		}
//
//	}
//
//	// ****************************************************************************************
//	// ENDING SECTION ----> OVERRIDDEN METHODS
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION ----> EVENT HANDLERS
//	// ****************************************************************************************
//	private void btnChangeHierarchy_clickHandler(ClickEvent event)
//	{
//		if (this.enableCategoriesSelection)
//		{
//			this.searchByRetailerCategory = !this.searchByRetailerCategory;
//
//			String selectedCategory = (this.searchByRetailerCategory) ? I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "retailer_hierarchy")
//					: I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "provider_hierarchy");
//			this.lbl_CategoryHierarchy.setValue(selectedCategory);
//
//			this.selF_SelectProductCategory.setValue(CategoryUtils.getRootCategoryItem());
//		}
//		this.setProductCategoryEnableByTradeMark();
//		this.updateTradeMarkItems(this.allTrademarks != null ? this.allTrademarks : this.getTrademarkItemAll());
//	}
//
//	private void btn_SearchProduct_clickHandler(ClickEvent event)
//	{
//		FindProduct winFindProduct = new FindProduct(this.getBbrUIParent(), this.provider.getRut());
//		winFindProduct.initializeView();
//		winFindProduct.addBbrEventListener((BbrEventListener & Serializable) e -> this.productCategorySelectedHandler(e));
//		winFindProduct.open(true);
//	}
//
//	private void productCategorySelectedHandler(BbrEvent event)
//	{
//		this.selectProductCategory(event.getResultObject());
//	}
//
//	private void selF_SelectCategory_categorySelectedHandler(BbrEvent event)
//	{
//		if (this.provider != null)
//		{
//			SelectProductCategory winSelect = new SelectProductCategory(this.getBbrUIParent(), this.provider.getRut(), this.searchByRetailerCategory);
//			if (isByRange)
//			{
//				winSelect.setRangeToLevels(this.minLevel, this.maxLevel);
//			}
//			winSelect.initializeView();
//			winSelect.addBbrEventListener((BbrEventListener & Serializable) e -> this.productCategorySelectedHandler(e));
//			winSelect.open(true);
//		}
//	}
//
//	// ****************************************************************************************
//	// BEGINNING SECTION ----> EVENT HANDLERS
//	// ****************************************************************************************
//
//	// ****************************************************************************************
//	// BEGINNING SECTION ----> PRIVATE METHODS
//	// ****************************************************************************************
//	private HorizontalLayout getLinksPanel()
//	{
//		HorizontalLayout pnlLinks = null;
//
//		if (this.enableCategoriesSelection && !isByRange)
//		{
//
//			pnlLinks = new HorizontalLayout();
//			if (this.isCategoryHeaderEnabled)
//			{
//				Button btn_ChangeHierarchy = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "change_hierarchy"));
//				btn_ChangeHierarchy.addStyleName("link-filter-button");
//				btn_ChangeHierarchy.addClickListener((ClickListener & Serializable) e -> this.btnChangeHierarchy_clickHandler(e));
//
//				Button btn_SearchProduct = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "search_product"));
//				btn_SearchProduct.addStyleName("link-filter-button");
//				btn_SearchProduct.addClickListener((ClickListener & Serializable) e -> this.btn_SearchProduct_clickHandler(e));
//
//				pnlLinks.addComponents(btn_ChangeHierarchy, btn_SearchProduct);
//				pnlLinks.setExpandRatio(btn_SearchProduct, 1F);
//				pnlLinks.setSpacing(true);
//			}
//		}
//
//		else
//		{
//			pnlLinks = new HorizontalLayout();
//			pnlLinks.setVisible(false);
//		}
//
//		return pnlLinks;
//	}
//
//	private HorizontalLayout getCategorySelectionPanel()
//	{
//		HorizontalLayout pnlCategorySubsection = null;
//
//		if (this.enableCategoriesSelection)
//		{
//			this.lbl_CategoryHierarchy = new Label(!StringUtils.isBlank(this.categoryTitle) ? this.categoryTitle : I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "retailer_hierarchy"));
//			this.lbl_CategoryHierarchy.setWidth("120px");
//
//			this.selF_SelectProductCategory = new BbrItemSelectField<CategoryItem>();
//			this.selF_SelectProductCategory.addBbrEventListener((BbrEventListener & Serializable) e -> this.selF_SelectCategory_categorySelectedHandler(e));
//			this.selF_SelectProductCategory.setFieldName("description");
//			this.selF_SelectProductCategory.setDescriptionName("path");
//			this.selF_SelectProductCategory.setButtonIcon(VaadinIcons.FOLDER_OPEN_O);
//			this.selF_SelectProductCategory.addStyleName("bbr-filter-fields");
//			this.selF_SelectProductCategory.addStyleName("fixed-item-select-field-label");
//			this.selF_SelectProductCategory.setWidth("255px");
//
//			this.defaultCatProductItem = CategoryUtils
//					.getRootCategoryItemWithName(!StringUtils.isBlank(this.categoryPlaceHolder) ? this.categoryPlaceHolder : I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "define_family_or_section"));
//			this.selF_SelectProductCategory.setValue(this.defaultCatProductItem);
//
//			pnlCategorySubsection = new HorizontalLayout();
//			pnlCategorySubsection.setWidth("100%");
//			pnlCategorySubsection.addComponents(this.lbl_CategoryHierarchy, this.selF_SelectProductCategory);
//			pnlCategorySubsection.setExpandRatio(this.selF_SelectProductCategory, 1F);
//			pnlCategorySubsection.addStyleName("bbr-panel-space");
//		}
//
//		else
//		{
//			pnlCategorySubsection = new HorizontalLayout();
//			pnlCategorySubsection.setVisible(false);
//		}
//
//		return pnlCategorySubsection;
//	}
//
//	private HorizontalLayout getTrademarksSelectionPanel()
//	{
//		HorizontalLayout pnlFormatsSubsection = null;
//
//		if (this.enableTrademarkSelection)
//		{
//			Label lbl_Trademarks = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "trademark"));
//			lbl_Trademarks.setWidth("120px");
//
//			this.initializeTradeMarkCombobox();
//			this.updateTradeMarkComboBox(this.provider);
//
//			pnlFormatsSubsection = new HorizontalLayout();
//			pnlFormatsSubsection.setWidth("100%");
//			pnlFormatsSubsection.addComponents(lbl_Trademarks, this.cbx_Trademarks);
//			pnlFormatsSubsection.setExpandRatio(this.cbx_Trademarks, 1F);
//			pnlFormatsSubsection.addStyleName("bbr-panel-space");
//		}
//
//		else
//		{
//			pnlFormatsSubsection = new HorizontalLayout();
//			pnlFormatsSubsection.setVisible(false);
//		}
//
//		return pnlFormatsSubsection;
//	}
//
//	private HorizontalLayout getActiveProdsSelectionPanel()
//	{
//		HorizontalLayout pnlActiveProductsSubsection = null;
//
//		if (this.enableActiveProductsSelection)
//		{
//			this.chkBox_OnlyActiveProducts = new CheckBox(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "only_active_products"));
//			this.chkBox_OnlyActiveProducts.addValueChangeListener((ValueChangeListener<Boolean> & Serializable) e -> this.cbx_OnlyActiveProducts_ValueChangeHandler(e));
//			pnlActiveProductsSubsection = new HorizontalLayout();
//			pnlActiveProductsSubsection.setWidth("100%");
//			pnlActiveProductsSubsection.addComponents(new BbrHSeparator("120px"), this.chkBox_OnlyActiveProducts);
//			pnlActiveProductsSubsection.setExpandRatio(this.chkBox_OnlyActiveProducts, 1F);
//			pnlActiveProductsSubsection.addStyleName("bbr-panel-space");
//		}
//
//		else
//		{
//			pnlActiveProductsSubsection = new HorizontalLayout();
//			pnlActiveProductsSubsection.setVisible(false);
//		}
//
//		return pnlActiveProductsSubsection;
//	}
//
//	private void cbx_OnlyActiveProducts_ValueChangeHandler(ValueChangeEvent<Boolean> e)
//	{
//		BbrSectionEvent alertResponseEvent = new BbrSectionEvent(this.CBX_ONLY_ACTIVE_PRODUCTS_EVENT);
//		alertResponseEvent.setResultObject(e.getValue());
//		this.dispatchBbrSectionEvent(alertResponseEvent);
//	}
//
//	private void updateTradeMarkComboBox(CompanyDataDTO selectedProvider)
//	{
//		if (selectedProvider != null)
//		{
//			try
//			{
//				CategoryItem categoryItem = this.getCategoryItem();
//				GetMarksByProviderAndCategoryInitParamDTO initparamDTO = this.getInitParam(selectedProvider, categoryItem);
//				MarkArrayResultDTO trademarksResult = EJBFactory.getCommercialEJBFactory().getCommercialReportManagerLocal().getMarksByProviderAndCategory(initparamDTO);
//
//				BbrError error = ErrorsMngr.getInstance().getError(trademarksResult, this.getBbrUIParent(), false);
//
//				if (!error.hasError() && (trademarksResult != null) && (trademarksResult.getMarks() != null) && (trademarksResult.getMarks().length > 0))
//				{
//					ArrayList<MarkDTO> tempTrademarks = this.getTrademarkItemAll();
//
//					for (MarkDTO trademarkItem : trademarksResult.getMarks())
//					{
//						tempTrademarks.add(trademarkItem);
//					}
//					this.updateTradeMarkItems(tempTrademarks);
//
//					if (categoryItem.getCode() == null || categoryItem.getCode().equals(CategoryUtils.ALL_CATEGORIES_OPTION_CODE))
//					{
//						this.allTrademarks = tempTrademarks;
//					}
//					if (this.cbx_Trademarks != null && this.enableTrademarkSelection)
//					{
//						this.cbx_Trademarks.setEnabled(true);
//						this.setProductCategoryEnableByTradeMark();
//					}
//				}
//				else
//				{
//					if (!this.getBbrUIParent().hasAlertWindowOpen())
//					{
//						String caption = I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error");
//						String messageErrorCode = I18NManager.getErrorMessageBaseResult(this.getBbrUIParent(), trademarksResult);
//
//						String message = !StringUtils.isBlank(messageErrorCode) ? messageErrorCode : I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation");
//
//						this.getBbrUIParent().showErrorMessage(caption, message);
//					}
//					if (this.cbx_Trademarks != null)
//					{
//						this.cbx_Trademarks.setEnabled(false);
//					}
//					if (this.selF_SelectProductCategory != null)
//					{
//						this.selF_SelectProductCategory.setEnabled(false);
//					}
//				}
//			}
//			catch (BbrUserException e)
//			{
//				AppUtils.getInstance().doLogOut(e.getMessage(), this.getBbrUIParent());
//			}
//			catch (BbrValidateCommercialException ex)
//			{
//				AppUtils.getInstance().showCommerialIsLoadingMessage(this.getBbrUIParent(), ex.getCode());
//			}
//			catch (BbrSystemException e)
//			{
//				e.printStackTrace();
//			}
//			catch (Exception e)
//			{
//				e.printStackTrace();
//			}
//		}
//	}
//
//	private void setProductCategoryEnableByTradeMark()
//	{
//		if (this.selF_SelectProductCategory != null)
//		{
//			this.selF_SelectProductCategory.setEnabled(this.cbx_Trademarks.isEnabled());
//		}
//	}
//
//	private void initializeTradeMarkCombobox()
//	{
//		this.cbx_Trademarks = new BbrComboBox<>();
//		this.cbx_Trademarks.setItemCaptionGenerator(MarkDTO::getTrademark);
//		this.cbx_Trademarks.setTextInputAllowed(false);
//		this.cbx_Trademarks.setEmptySelectionAllowed(false);
//		this.cbx_Trademarks.addStyleName("bbr-filter-fields");
//		this.cbx_Trademarks.setWidth("255px");
//		this.cbx_Trademarks.setHeight("30px");
//		this.cbx_Trademarks.setEnabled(false);
//	}
//
//	private CategoryItem getCategoryItem()
//	{
//		CategoryItem categoryItem = this.selF_SelectProductCategory != null && this.selF_SelectProductCategory.getValue() != null ? this.selF_SelectProductCategory.getValue() : new CategoryItem();
//
//		return categoryItem;
//	}
//
//	private ArrayList<MarkDTO> getTrademarkItemAll()
//	{
//		MarkDTO allTrademarksOption = new MarkDTO();
//		allTrademarksOption.setTrademark(CommercialUtils.ALL_TRADEMARKS_DESCRIPTION);
//
//		ArrayList<MarkDTO> tempTrademarks = new ArrayList<>();
//		tempTrademarks.add(allTrademarksOption);
//
//		return tempTrademarks;
//	}
//
//	private void updateTradeMarkItems(ArrayList<MarkDTO> tempTrademarks)
//	{
//		if (tempTrademarks.size() > 1)
//		{
//			this.cbx_Trademarks.removeAllItems();
//			this.cbx_Trademarks.setItems(tempTrademarks);
//			this.cbx_Trademarks.selectFirstItem();
//		}
//	}
//
//	private GetMarksByProviderAndCategoryInitParamDTO getInitParam(CompanyDataDTO selectedProvider, CategoryItem categoryItem)
//	{
//		GetMarksByProviderAndCategoryInitParamDTO initParamDTO = new GetMarksByProviderAndCategoryInitParamDTO();
//		initParamDTO.setPvcode(selectedProvider.getRut());
//		initParamDTO.setCatProdRetailer(this.searchByRetailerCategory);// retailer
//																		// true
//		initParamDTO.setKeyProduct(categoryItem.getId() != null ? categoryItem.getId() : -1);// -1
//																								// todaas
//																								// o
//																								// key
//																								// cat
//																								// o
//																								// local
//		initParamDTO.setLevelProduct(categoryItem.getLevel() != null ? categoryItem.getLevel() : -1);// si
//																										// key
//																										// es
//																										// -1
//																										// se
//																										// ignora-
//																										// nivel
//																										// de
//																										// categoria
//
//		if (categoryItem.hasProvisionalChild() != null && categoryItem.getChildrenObtained() != null && categoryItem.hasProvisionalChild().equals(false) && categoryItem.getChildrenObtained().equals(false))
//		{
//			initParamDTO.setProductOrCategory(true);
//		}
//		else
//		{
//			initParamDTO.setProductOrCategory(false);
//		}
//		return initParamDTO;
//	}
//
//	private void updateCategoriesSelector()
//	{
//		if ((this.enableTrademarkSelection) && (this.enableCategoriesSelection) && (this.selF_SelectProductCategory != null))
//		{
//			this.selF_SelectProductCategory.setValue((this.cbx_Trademarks.isEnabled()) ? CategoryUtils.getRootCategoryItem() : CategoryUtils.getNoCategoriesItem());
//		}
//		else if (!(this.enableTrademarkSelection) && (this.enableCategoriesSelection) && (this.selF_SelectProductCategory != null))
//		{
//			this.selF_SelectProductCategory.setValue(this.defaultCatProductItem);
//		}
//	}
//
//	private void selectProductCategory(Object category)
//	{
//		CategoryItem categoryItem = null;
//
//		// Si el item seleccionado proviene del buscador de productos,
//		// adaptarlo y presentarlo. De lo contrario, si
//		// proviene del árbol, simplemente basta con presentarlo
//
//		if (!(category instanceof CategoryItem))
//		{
//			categoryItem = CategoryUtils.createCategoryItem(category, null);
//		}
//
//		else
//		{
//			categoryItem = (CategoryItem) category;
//		}
//
//		if (categoryItem != null)
//		{
//			this.setProductCategoryValue(categoryItem);
//
//			if (enableTrademarkSelection)
//			{
//				this.updateTradeMarkComboBox(this.provider);
//			}
//		}
//	}
//
//	private void setProductCategoryValue(CategoryItem categoryItem)
//	{
//		this.selF_SelectProductCategory.setValue(null);
//		this.selF_SelectProductCategory.setValue(categoryItem);
//	}
//
//	// ****************************************************************************************
//	// ENDING SECTION ----> PRIVATE METHODS
//	// ****************************************************************************************
//
//	public static class ProductFilterSectionBuilder
//	{
//		private Boolean	isCategoryHeaderEnabled				= true;
//
//		private Boolean	isCategoriesSelectionEnabled		= true;
//		private Boolean	isTrademarkSelectionEnabled			= true;
//		private Boolean	isActiveProductsSelectionEnabled	= true;
//
//		private Boolean	searchByRetailerCategory			= true;
//
//		private BbrUI	parent								= null;
//
//		public ProductFilterSectionBuilder(BbrUI parent, Boolean isCategoriesSelectionEnabled, Boolean isTrademarkSelectionEnabled, Boolean isActiveProductsSelectionEnabled)
//		{
//			this.parent = parent;
//			this.isCategoriesSelectionEnabled = isCategoriesSelectionEnabled;
//			this.isTrademarkSelectionEnabled = isTrademarkSelectionEnabled;
//			this.isActiveProductsSelectionEnabled = isActiveProductsSelectionEnabled;
//		}
//
//		public ProductFilterSectionBuilder setCategoryHeaderEnabled(boolean isCategoryHeaderEnabled)
//		{
//			this.isCategoryHeaderEnabled = isCategoryHeaderEnabled;
//			return this;
//		}
//
//		public ProductFilterSectionBuilder setSearchByRetailerCategory(boolean searchByRetailerCategory)
//		{
//			this.searchByRetailerCategory = searchByRetailerCategory;
//			return this;
//		}
//
//		public ProductFilterSection build()
//		{
//			return new ProductFilterSection(this);
//		}
//	}
//
//}
