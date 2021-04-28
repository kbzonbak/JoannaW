package bbr.b2b.portal.components.modules.mdm.sections;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import bbr.b2b.fep.cp.data.classes.CPItemArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsByStatusInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPRelatedProductArrayResultDTO;
import bbr.b2b.portal.classes.constants.BbrFilterSectionConstants;
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

public class SelectionProductsSection extends BbrVerticalSection<CompanyDTO>
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************

	private static final long				serialVersionUID			= 8066356496179778873L;

	private static final String				PRKEY						= "prkey";
	private static final String				DESCRIPTION					= "description";

	private BbrAdvancedGrid<CPItemDTO>	datGrid_ProductsFounded		= null;

	private BbrTextField					txt_FilterProduct			= null;

	private ArrayList<CPItemDTO>		dsProductsFounded			= new ArrayList<>();

	private Label							lblCounterProductFounded	= new Label();

	private CPItemDTO                       mainProduct 			= null;

	private CPItemsByStatusInitParamDTO				initParam				= null;

	private ArrayList<CPItemDTO>		assignedProducts			= null;

	private ArrayList<CPItemDTO>		availableProducts			= null;

	private CPRelatedProductArrayResultDTO relatedproducts 			= null;
	private LinkedHashMap<Long, List<CPItemDTO>> relatedItemsMap 	= null;
	private LinkedHashMap<Long, List<Long>> relatedArticleTypeMap 	= null;
	private LinkedHashMap<Long, List<Long>> relatedProductsMap 		= null;

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public SelectionProductsSection(BbrUI parent, CPItemDTO mainProduct)
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
		this.updateAvailableProducts();
		
		// Sección Título
		Label lbl_Title = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_section_products_title"));
		lbl_Title.setWidth("100%");
		lbl_Title.addStyleName("bold_style");

		// Sección Pasos
		Label lbl_Steps = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_step1"));
		lbl_Steps.setWidth("80px");
		lbl_Steps.addStyleNames("italic", "bold", "padding-top-5");

		// Sección Título + Sección Pasos
		HorizontalLayout pnl_Header = new HorizontalLayout(lbl_Title, lbl_Steps);
		pnl_Header.setExpandRatio(lbl_Title, 1F);
		pnl_Header.setWidth("100%");

		// Sección Descripción del Paso 2
		Label lbl_Description = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_section_product_description"));
		lbl_Description.setWidth("100%");

		HorizontalLayout pnl_Description = new HorizontalLayout(lbl_Description);
		pnl_Description.setWidth("100%");

		// Filtro Productos
		this.txt_FilterProduct = new BbrTextField();
		this.txt_FilterProduct.setPlaceholder(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "phld_product_to_search"));
		this.txt_FilterProduct.setHeight("30px");
		this.txt_FilterProduct.setWidth("100%");

		this.txt_FilterProduct.addValueChangeListener(e -> valueTxtFilter_Handler());

		HorizontalLayout pnl_Filter = new HorizontalLayout(this.txt_FilterProduct);
		pnl_Filter.setWidth("70%");

		Label lbl_Filter = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_filter"));
		lbl_Filter.setSizeFull();

		HorizontalLayout pnl_LabelFilter = new HorizontalLayout(lbl_Filter);
		pnl_LabelFilter.setWidth("60px");
		pnl_LabelFilter.setComponentAlignment(lbl_Filter, Alignment.MIDDLE_RIGHT);
		//
		HorizontalLayout pnl_SelectProduct = new HorizontalLayout(pnl_LabelFilter, pnl_Filter);
		pnl_SelectProduct.setExpandRatio(pnl_Filter, 1F);
		pnl_SelectProduct.setWidth("100%");

		this.datGrid_ProductsFounded = new BbrAdvancedGrid<>(this.getBbrUIParent().getUser().getLocale());
		this.datGrid_ProductsFounded.setSortable(false);

		this.initializeProductsDataGridColumns();

		this.datGrid_ProductsFounded.addStyleName("report-grid");
		this.datGrid_ProductsFounded.setSizeFull();
		this.datGrid_ProductsFounded.setSelectionMode(SelectionMode.MULTI);
		this.datGrid_ProductsFounded.addSelectionListener((SelectionListener<CPItemDTO> & Serializable) e -> selectedProduct_handler(e));
		// this.datGrid_Products.addSortListener((SortListener<GridSortOrder<ConstraintTypeDTO>>
		// & Serializable) e -> dataGrid_sortHandler(e));
		// this.datGrid_Products.addItemClickListener((ItemClickListener<ConstraintTypeDTO>
		// & Serializable) e -> dgdItem_clickHandler(e));

		HorizontalLayout pnlCounterProductToAdd = new HorizontalLayout();
		pnlCounterProductToAdd.setWidth("100%");
		pnlCounterProductToAdd.setHeight("30px");

		pnlCounterProductToAdd.addComponent(this.lblCounterProductFounded);
		pnlCounterProductToAdd.setComponentAlignment(this.lblCounterProductFounded, Alignment.MIDDLE_LEFT);

		this.updateCountersLabel(null);
		
		this.updateDataSourceAndUI();

		this.setMargin(false);
		this.setSpacing(true);
		this.addStyleName("bbr-filter-sections");
		this.addStyleName("bbr-panel-space");
		this.addComponents(pnl_Header, pnl_Description, pnl_SelectProduct, this.datGrid_ProductsFounded, pnlCounterProductToAdd);
		this.setComponentAlignment(pnl_SelectProduct, Alignment.MIDDLE_CENTER);
		this.setExpandRatio(this.datGrid_ProductsFounded, 1F);
		this.setSizeFull();
	}

	@Override
	public CompanyDTO getSectionResult()
	{
		CompanyDTO company = null;

		if (validateData() == null)
		{
			// company = this.slc_Provider.getValue();

		}

		return company;

	}

	@Override
	public String validateData()
	{
		String result = null;

		// if (this.slc_Provider.isEmpty())
		// {
		// result = I18NManager.getI18NString(getBbrUIParent(),
		// BbrUtilsResources.RES_MODULES_FEP, "required_provider");
		// }

		return result;
	}

	@Override
	public void setSectionData(Object result)
	{
		@SuppressWarnings("unchecked")
		ArrayList<CPItemDTO> productsList = (ArrayList<CPItemDTO>) result;
		this.dsProductsFounded = productsList;

		this.updateGridData(this.dsProductsFounded);

		updateCountersLabel(this.dsProductsFounded);
	}

	private void valueTxtFilter_Handler()
	{
		String pattern = SelectionProductsSection.this.txt_FilterProduct.getValue();
		SelectionProductsSection.this.filterProducts(pattern);
	}

	public void deselectProducts(ProductsAssignment result)
	{
		CPItemDTO product = result.getItemSelected();
		this.datGrid_ProductsFounded.deselect(product);

		this.dsProductsFounded.removeIf(a -> a.getId().equals(product.getId()));
		this.datGrid_ProductsFounded.getDataProvider().refreshAll();

	}

	public void selectProducts(ArrayList<CPItemDTO> productsToSelect)
	{
		productsToSelect.forEach(r ->
		{
			this.datGrid_ProductsFounded.select(r);
		});
		this.datGrid_ProductsFounded.getDataProvider().refreshAll();

	}

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

	private void initializeProductsDataGridColumns()
	{
		this.datGrid_ProductsFounded.addCustomColumn(CPItemDTO::getSku, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_product_key"), true).setId(PRKEY);
		this.datGrid_ProductsFounded.addCustomColumn(CPItemDTO::getDescription, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_description_of_product"), true).setId(DESCRIPTION);
	}

	private void filterProducts(String pattern)
	{
		List<CPItemDTO> list = this.dsProductsFounded.stream().filter(p -> p.getDescription().toLowerCase().contains(pattern.toLowerCase()) || p.getSku().toString().contains(pattern)).collect(Collectors.toList());

		this.updateGridData(list);
		this.updateCountersLabel(list);
	}

	private void updateGridData(List<CPItemDTO> list)
	{
		ListDataProvider<CPItemDTO> dataprovider = new ListDataProvider<>(list);
		this.datGrid_ProductsFounded.setDataProvider(dataprovider);
		this.datGrid_ProductsFounded.getDataProvider().refreshAll();
	}

	private void selectedProduct_handler(SelectionEvent<CPItemDTO> event)
	{
		BbrSectionEvent bbrEvent = new BbrSectionEvent(BbrFilterSectionConstants.FS_PRODUCT);
		bbrEvent.setResultObject(event);
		dispatchBbrSectionEvent(bbrEvent);

	}

	private void updateCountersLabel(List<CPItemDTO> list)
	{
		if (list != null && list.size() > 0)
		{
			this.lblCounterProductFounded.setValue(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_footer_total") + ": " + String.valueOf(list.size()));
		}
		else
		{
			this.lblCounterProductFounded.setValue(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_footer_total") + ": 0");
		}
	}

	private void updateAvailableProducts()
	{
		this.availableProducts = null;

		try
		{
			this.initParam = new CPItemsByStatusInitParamDTO();
			this.initParam.setProvidercode(this.mainProduct.getProvidercode());
			this.initParam.setStatus(1);
			this.initParam.setUsertypeid(-1L);

			CPItemArrayResultDTO itemsResult = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal().getItemsByStatusData(this.initParam, this.getBbrUIParent().getUser().getId());

			if (itemsResult != null && itemsResult.getValues() != null)
			{
				dsProductsFounded = new ArrayList<>(Arrays.asList(itemsResult.getValues()));

				if (this.assignedProducts != null && this.assignedProducts.size() > 0){

					List<CPItemDTO> operatedList = new ArrayList<>();
					for (Long id: this.relatedProductsMap.get(this.mainProduct.getId())){
						dsProductsFounded.stream()
						.filter(item -> (item.getId().equals(id) || item.getId().equals(this.mainProduct.getId())))
						.forEach(item -> {
							operatedList.add(item);
						});
					}

					dsProductsFounded.removeAll(operatedList);
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

		// ****************************************************************************************
		// ENDING SECTION ----> PRIVATE METHODS
		// ****************************************************************************************

	}
	
	private void updateDataSourceAndUI()
	{
//		ListDataProvider<CPItemDTO> assignedDataprovider = new ListDataProvider<>(assignedProducts != null ? assignedProducts : new ArrayList<>());
//		dgd_AssignedProducts.setDataProvider(assignedDataprovider);

		ListDataProvider<CPItemDTO> availableDataprovider = new ListDataProvider<>(dsProductsFounded != null ? dsProductsFounded : new ArrayList<>());
		datGrid_ProductsFounded.setDataProvider(availableDataprovider);
//
//		btnAddItem.setEnabled((availableProducts != null && availableProducts.size() > 0));
//		btnAddItems.setEnabled((availableProducts != null && availableProducts.size() > 0));
//		btnRemoveItem.setEnabled((assignedProducts!= null && assignedProducts.size() > 0));
//		btnRemoveItems.setEnabled((assignedProducts != null && assignedProducts.size() > 0));
		
		updateCountersLabel(this.dsProductsFounded);
	}
}
