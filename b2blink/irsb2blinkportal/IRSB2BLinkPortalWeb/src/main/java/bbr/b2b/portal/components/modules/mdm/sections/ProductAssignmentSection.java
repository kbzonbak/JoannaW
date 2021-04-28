package bbr.b2b.portal.components.modules.mdm.sections;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.selection.MultiSelectionEvent;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import bbr.b2b.fep.cp.data.classes.CPItemDTO;
import bbr.b2b.fep.cp.data.classes.CPRelatedProductArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPRelatedProductDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.wrappers.fep.ProductsAssignment;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyDTO;
import cl.bbr.core.classes.container.BbrSectionEvent;
import cl.bbr.core.classes.container.BbrVerticalSection;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class ProductAssignmentSection extends BbrVerticalSection<ArrayList<ProductsAssignment>>
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************

	private static final long						serialVersionUID				= 8066356496179778873L;

	private static final String						PROVIDER_NAME_RUT				= "provider_name_rut";
	private static final String						PRODUCT_DESCRIPTION_CODE		= "product_description_code";

	private ArrayList<ProductsAssignment>			dsProductsToAdd					= new ArrayList<>();
	private Label									lblCounterProductToAdd			= new Label();
	private BbrAdvancedGrid<ProductsAssignment>	datGrid_ProductsToAdd			= null;
	private CompanyDTO								companySelected					= null;
	private CPRelatedProductArrayResultDTO				relatedProducts					= null;
	private Button									btn_RemoveSelectedAttributes	= null;
	//private Button									btn_AddTemplate					= null;

	private static final String						SELECTED_DEFAULT_COUNT				= "1";

	private CPRelatedProductArrayResultDTO relatedproducts 			= null;
	private LinkedHashMap<Long, List<CPItemDTO>> relatedItemsMap 	= null;
	private LinkedHashMap<Long, List<Long>> relatedArticleTypeMap 	= null;
	private LinkedHashMap<Long, List<Long>> relatedProductsMap 		= null;

	private CPItemDTO						mainProduct						= null;

	private Set<CPItemDTO>		assignedProducts			= null;

	private HashMap<Long, CPItemDTO>	originalAssignedProducts	= new HashMap<>();
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public ProductAssignmentSection(BbrUI parent, CPItemDTO mainProduct)
	{
		super(parent);
		this.mainProduct = mainProduct;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	@Override
	public void initializeView()
	{
		this.getRelatedProductsByItem();

		// Sección Título
		Label lbl_Title = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_section_related_product_title"));
		lbl_Title.setWidth("100%");
		lbl_Title.addStyleName("bold_style");

		// Sección Pasos
		Label lbl_Steps = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_step2"));
		lbl_Steps.setWidth("80px");
		lbl_Steps.addStyleNames("italic", "bold", "padding-top-5");

		// Sección Título + Sección Pasos
		HorizontalLayout pnl_Header = new HorizontalLayout(lbl_Title, lbl_Steps);
		pnl_Header.setExpandRatio(lbl_Title, 1F);
		pnl_Header.setWidth("100%");

		// Sección Descripción del Paso 3
		Label lbl_Description = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_section_related_product_description"));
		lbl_Description.setWidth("100%");

		HorizontalLayout pnl_Description = new HorizontalLayout(lbl_Description);
		pnl_Description.setWidth("100%");

		// Barra de herramientas superior izq
		HorizontalLayout leftButtonsBar = new HorizontalLayout();
		leftButtonsBar.setSpacing(true);
		leftButtonsBar.setMargin(false);
		leftButtonsBar.setHeight("30px");
		// leftButtonsBar.addStyleName("toolbar-layout");

		// Boton Quitar Atributos Seleccionados
		this.btn_RemoveSelectedAttributes = new Button("", EnumToolbarButton.DELETE.getResource());
		this.btn_RemoveSelectedAttributes.setDescription(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "btn_remove_generic_attribute"));
		this.btn_RemoveSelectedAttributes.addClickListener((ClickListener & Serializable) e -> this.btn_RemoveAttribute_clickHandler(e));
		this.btn_RemoveSelectedAttributes.addStyleName("toolbar-button");

//		this.btn_AddTemplate = new Button("", EnumToolbarButton.LIST.getResource());
//		this.btn_AddTemplate.setDescription(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "btn_add_allowed_values"));
//		this.btn_AddTemplate.addClickListener((ClickListener & Serializable) e -> this.btn_AddTemplate_clickHandler(e));
//		this.btn_AddTemplate.addStyleName("toolbar-button");

		leftButtonsBar.addComponents(this.btn_RemoveSelectedAttributes);

		HorizontalLayout rightButtonsBar = new HorizontalLayout();
		rightButtonsBar.setSpacing(true);
		rightButtonsBar.setMargin(false);
		rightButtonsBar.setHeight("30px");

		HorizontalLayout toolBar = new HorizontalLayout();
		toolBar.setWidth("100%");
		toolBar.addComponents(leftButtonsBar, rightButtonsBar);
		toolBar.setExpandRatio(leftButtonsBar, 1F);
		toolBar.setExpandRatio(rightButtonsBar, 1F);

		toolBar.setComponentAlignment(leftButtonsBar, Alignment.MIDDLE_LEFT);
		toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);

		// Grilla Productos seleccionados
		this.datGrid_ProductsToAdd = new BbrAdvancedGrid<>(this.getBbrUIParent().getLocale());

		this.datGrid_ProductsToAdd.addStyleName("report-grid");
		this.datGrid_ProductsToAdd.setSortable(false);
		this.datGrid_ProductsToAdd.setSelectionMode(SelectionMode.MULTI);
		this.datGrid_ProductsToAdd.setWidth("100%");
		this.datGrid_ProductsToAdd.addSelectionListener((SelectionListener<ProductsAssignment> & Serializable) e ->
		{
			this.updateButtons();
		});

		HorizontalLayout pnlCounterProductToAdd = new HorizontalLayout();
		pnlCounterProductToAdd.setWidth("100%");
		pnlCounterProductToAdd.setHeight("30px");

		pnlCounterProductToAdd.addComponent(this.lblCounterProductToAdd);
		pnlCounterProductToAdd.setComponentAlignment(this.lblCounterProductToAdd, Alignment.MIDDLE_LEFT);
		//

		this.updateCountersLabel();
		this.updateButtons();

		this.updateAssignedProducts();

		this.initializeProductsToAddDataGridColumns();

		this.setMargin(false);
		this.setSpacing(true);
		this.addStyleName("bbr-filter-sections");
		this.addStyleName("bbr-panel-space");
		this.addComponents(pnl_Header, pnl_Description, toolBar, this.datGrid_ProductsToAdd, pnlCounterProductToAdd);
		this.setExpandRatio(this.datGrid_ProductsToAdd, 1F);
		this.setSizeFull();
	}

	@Override
	public ArrayList<ProductsAssignment> getSectionResult()
	{
		ArrayList<ProductsAssignment> templateAssignment = null;

		if (validateData() == null)
		{
			templateAssignment = this.dsProductsToAdd;
		}

		return templateAssignment;

	}

	@Override
	public String validateData()
	{
		String result = null;

		if (this.dsProductsToAdd.isEmpty())
		{
			result = I18NManager.getI18NString(getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "required_template_assignment");
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setSectionData(Object result)
	{
		BbrSectionEvent e = (BbrSectionEvent) result;

		SelectionEvent<CPItemDTO> selectionEvent = (SelectionEvent<CPItemDTO>) e.getResultObject();
		MultiSelectionEvent<CPItemDTO> event = (MultiSelectionEvent<CPItemDTO>) selectionEvent;

		//this.updateToAssignSelection(event.getAddedSelection(), event.getRemovedSelection(), selectionEvent.isUserOriginated());
		this.updateToAssignSelection(event.getAddedSelection(), event.getRemovedSelection(), false);

		updateCountersLabel();
		updateButtons();
	}

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************
	public void setCompanyData(CompanyDTO companySelected)
	{
		this.companySelected = companySelected;
	}

	public void setRelatedProducts(CPRelatedProductArrayResultDTO relatedProducts)
	{
		this.relatedProducts = relatedProducts;
	}

	public ArrayList<ProductsAssignment> getProductsToAdd()
	{
		return this.dsProductsToAdd;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
//	private void btn_AddTemplate_clickHandler(ClickEvent event)
//	{
//		if (this.relatedProducts != null)
//		{
//			AddTemplatesToSelection templatesToSelectionWin = new AddTemplatesToSelection(this.getBbrUIParent(), this.relatedProducts);
//			//templatesToSelectionWin.addBbrEventListener((BbrEventListener & Serializable) bbrEvent -> addTemplateToSelection_handler(bbrEvent));
//			templatesToSelectionWin.initializeView();
//			templatesToSelectionWin.open(true);
//		}
//	}

	private void initializeProductsToAddDataGridColumns()
	{
		/*this.datGrid_ProductsToAdd.addCustomColumn(t -> this.getFullDescription(t.getItemSelected().getSku(), t.getItemSelected().getProvidercode()), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_product_key"), true)
		.setId(PROVIDER_NAME_RUT);

		this.datGrid_ProductsToAdd.addCustomColumn(t -> this.getFullDescription(t.getItemSelected().getDescription(), t.getItemSelected().getProvidercode()), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_description_of_product"), true)
		.setId(PRODUCT_DESCRIPTION_CODE);*/
		
		this.datGrid_ProductsToAdd.addCustomColumn(t -> t.getItemSelected().getSku(), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_product_key"), true).setId(PROVIDER_NAME_RUT);
		
		this.datGrid_ProductsToAdd.addCustomColumn(t -> t.getItemSelected().getDescription(), I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_description_of_product"), true).setId(PRODUCT_DESCRIPTION_CODE);

		this.datGrid_ProductsToAdd.addComponentColumn(related ->
		{
		
			BbrTextField cText = this.getValuesByRelatedProduct(related);
			cText.setWidth("120px");

			return new HorizontalLayout()
			{
				private static final long serialVersionUID = 1L;

				{
					setSpacing(true);
					addComponent(cText);
				}
			};
		}).setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "related_count")).setExpandRatio(1);

	}

	private void btn_RemoveAttribute_clickHandler(ClickEvent event)
	{
		this.datGrid_ProductsToAdd.getSelectedItems().forEach(template ->
		{
			this.dsProductsToAdd.remove(template);

//			BbrSectionEvent bbrEvent = new BbrSectionEvent(BbrFilterSectionConstants.FS_TEMPLATE);
//			bbrEvent.setResultObject(template);
//			dispatchBbrSectionEvent(bbrEvent);

		});

		this.updateToAddDataSource();
		this.updateCountersLabel();
	}

	//	private void addTemplateToSelection_handler(BbrEvent bbrEvent)
	//	{
	//		if (bbrEvent.getResultObject() != null)
	//		{
	//			CPRelatedProductDTO relatedProduct = (CPRelatedProductDTO) bbrEvent.getResultObject();
	//			for (ProductsAssignment ProductsAssignment : this.datGrid_ProductsToAdd.getSelectedItems())
	//			{
	//				ProductsAssignment.setRelatedProduct(relatedProduct);
	//			}
	//			this.datGrid_ProductsToAdd.getDataProvider().refreshAll();
	//
	//		}
	//	}

	private void updateButtons()
	{
		this.btn_RemoveSelectedAttributes.setEnabled(this.datGrid_ProductsToAdd.getSelectedItems() != null && !this.datGrid_ProductsToAdd.getSelectedItems().isEmpty());
		//this.btn_AddTemplate.setEnabled(this.datGrid_ProductsToAdd.getSelectedItems() != null && !this.datGrid_ProductsToAdd.getSelectedItems().isEmpty());
	}

	private String getFullDescription(String name, String code)
	{
		return name + " [" + code + "]";
	}

	private void updateToAssignSelection(Set<CPItemDTO> addedSelection, Set<CPItemDTO> removedSelection, boolean isUserOriginated)
	{
		if (addedSelection != null && addedSelection.size() > 0)
		{
			addedSelection.forEach(prod ->
			{
				if (this.dsProductsToAdd != null && !this.containSameAttrib(prod))
				{
					ProductsAssignment related = new ProductsAssignment();
					related.setItemSelected(prod);

					this.dsProductsToAdd.add(related);
				}
			});
			this.updateToAddDataSource();
		}
		else if (removedSelection != null && removedSelection.size() > 0 && isUserOriginated)
		{
			removedSelection.forEach(attrib ->
			{
				if (this.dsProductsToAdd != null)
				{
					this.dsProductsToAdd.removeIf(a -> a.getItemSelected().getId().equals(attrib.getId()));
				}
			});
			this.updateToAddDataSource();
		}

		this.updateCountersLabel();
	}

	private boolean containSameAttrib(CPItemDTO prod)
	{
		return this.dsProductsToAdd.stream().filter(a -> a.getItemSelected().getId().equals(prod.getId())).findAny().isPresent();
	}

	private void updateToAddDataSource()
	{
		ListDataProvider<ProductsAssignment> dataprovider = new ListDataProvider<>(this.dsProductsToAdd);
		this.datGrid_ProductsToAdd.setDataProvider(dataprovider);
	}

	private void updateCountersLabel()
	{
		if (this.dsProductsToAdd != null && this.dsProductsToAdd.size() > 0)
		{
			this.lblCounterProductToAdd.setValue(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_footer_total") + ": " + String.valueOf(dsProductsToAdd.size()));
		}
		else
		{
			this.lblCounterProductToAdd.setValue(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_footer_total") + ": 0");
		}
	}

	//	private BbrComboBox<CPRelatedProductDTO> getRelatedProductsComboBox(ProductsAssignment related)
	//	{
	//		final BbrComboBox<CPRelatedProductDTO> result = new BbrComboBox<CPRelatedProductDTO>();
	//
	//		if (this.getBbrUIParent().getUser() != null)
	//		{
	//			result.setItemCaptionGenerator(CPRelatedProductDTO::getArticletypename);
	//			result.setTextInputAllowed(false);
	//			result.setEmptySelectionAllowed(false);
	//			result.setWidth("100%");
	//
	//			if ((relatedProducts != null) && (relatedProducts.getValues() != null) && (relatedProducts.getValues().length > 0))
	//			{
	//				result.setItems(this.relatedProducts.getValues());
	//
	//				Long selectedItemId = -1L;
	//				if (related.getRelatedProduct() != null)
	//				{
	//					selectedItemId = related.getRelatedProduct().getRelatedproductid();
	//				}
	//				else
	//				{
	//					if(this.selectedRelatedProd != null)
	//					{
	//						selectedItemId = this.selectedRelatedProd.getRelatedproductid(); 
	//					}
	//				}
	//				
	//				CPRelatedProductDTO selectedItem = this.getSelectedArticleType(this.relatedProducts.getValues(), selectedItemId);
	//				result.setSelectedItem(selectedItem);
	//
	//				result.addStyleName("bbr-filter-fields");
	//				result.addValueChangeListener((e) -> filter_changeHandler(e, related, result));
	//			}
	//			else
	//			{
	//				CPRelatedProductDTO emptyResult = new CPRelatedProductDTO();
	//				emptyResult.setArticletypename(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "no_template_assigned"));
	//
	//				result.setItems(emptyResult);
	//				result.setSelectedItem(emptyResult);
	//				result.setEnabled(false);
	//			}
	//			related.setRelatedProduct(result.getValue());
	//			result.setDescription(result.getValue().getArticletypename());
	//
	//		}
	//		return result;
	//
	//	}

	private BbrTextField getValuesByRelatedProduct(ProductsAssignment item){

		final BbrTextField result = new BbrTextField ();
		CPRelatedProductDTO relatedProductDTO = null;
		
		if (item.getCount() == 0)
			item.setCount(Integer.parseInt(SELECTED_DEFAULT_COUNT));

		if (this.getBbrUIParent().getUser() != null){

			result.setWidth("70%");
			result.setHeight("90%");
			result.setValue(SELECTED_DEFAULT_COUNT);

			if (this.relatedProductsMap != null && this.relatedProductsMap.size() > 0 && this.mainProduct != null && this.mainProduct.getId() > 0){
				//Buscar si el producto complementario (item) existe en el mapa
				List<Long> relatedIds = this.relatedProductsMap.get( this.mainProduct.getId() );

				if (relatedIds.contains(item.getItemSelected().getId())){

					if (this.relatedproducts != null && this.relatedproducts.getValues() != null && this.relatedproducts.getValues().length > 0){

						relatedProductDTO = Arrays.stream(this.relatedproducts.getValues())
								.filter(x -> x.getMainproductid().equals(this.mainProduct.getId()) 
										&& x.getRelatedproductid().equals(item.getItemSelected().getId()))
								.findFirst()
								.orElse(null);
						
						item.setCount(relatedProductDTO.getCount());
						result.setValue(Integer.toString(relatedProductDTO.getCount()));

					}

				}

			}else{
				item.setCount(Integer.parseInt(SELECTED_DEFAULT_COUNT));
				result.setValue(SELECTED_DEFAULT_COUNT);
			}
			
			result.addValueChangeListener((e) -> filter_changeHandler(e, item, result));
			result.setValueChangeMode(ValueChangeMode.EAGER);
		}

		return result;
	}

	private void updateAssignedProducts()
	{
		assignedProducts = null;

		try
		{

			if (this.relatedItemsMap != null && this.relatedItemsMap.get(this.mainProduct.getId()) != null && this.relatedItemsMap.get(this.mainProduct.getId()).size() > 0)
			{
				assignedProducts = new HashSet<>(this.relatedItemsMap.get(this.mainProduct.getId()));

				if (assignedProducts != null && assignedProducts.size() > 0)
				{
					for (CPItemDTO item : assignedProducts)
					{
						originalAssignedProducts.put(item.getId(), item);
					}
				}

				this.updateToAssignSelection(assignedProducts, null, false);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void getRelatedProductsByItem()
	{
		try
		{
			if (this.mainProduct != null && this.mainProduct.getId() > 0){
				Long [] ids = new Long[1];
				ids[0] = this.mainProduct.getId();

				this.relatedproducts = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal().getRelatedProductsByMainProducts(ids);

				if (relatedproducts != null && relatedproducts.getValues() != null && relatedproducts.getValues().length > 0){

					this.relatedProductsMap = relatedproducts.getRelatedProductsMap();
					this.relatedItemsMap = relatedproducts.getRelatedItemsMap();
					this.relatedArticleTypeMap = relatedproducts.getRelatedArticleTypeMap();

				}
			}

		}
		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), this.getBbrUIParent());
		}
		catch (BbrSystemException e)
		{
			e.printStackTrace();
		}

	}
	
	private void filter_changeHandler(ValueChangeEvent<String> e, ProductsAssignment item, BbrTextField result)
	{
		String value = e.getValue() != null && !e.getValue().isEmpty() ? e.getValue() : SELECTED_DEFAULT_COUNT;
		item.setCount(Integer.valueOf(value));
		result.setValue(value);
	}
	
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

	//	private CPRelatedProductDTO getSelectedArticleType(CPRelatedProductDTO[] relatedProducts, Long selectedProductId)
	//	{
	//		CPRelatedProductDTO result = null;
	//
	//		if (relatedProducts != null && relatedProducts.length > 0)
	//		{
	//			for (CPRelatedProductDTO related : relatedProducts)
	//			{
	//				if (related.getRelatedproductid().equals(selectedProductId))
	//				{
	//					result = related;
	//					break;
	//				}
	//			}
	//			if (result == null)
	//			{
	//				result = relatedProducts[0];
	//			}
	//		}
	//
	//		return result;
	//	}

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
