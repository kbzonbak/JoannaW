package bbr.b2b.portal.components.modules.fep;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.fep.commercial.data.classes.ProductsRecordsResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemDTO;
import bbr.b2b.fep.mp.data.classes.MPItemErrorArrayResultDTO;
import bbr.b2b.portal.classes.constants.BbrFilterSectionConstants;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.wrappers.fep.ProductsAssignment;
import bbr.b2b.portal.classes.wrappers.fep.ProductsAssignmentList;
import bbr.b2b.portal.components.modules.mdm.sections.ProductAssignmentSection;
import bbr.b2b.portal.components.modules.mdm.sections.SelectionProductsSection;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyDTO;
import cl.bbr.core.classes.container.BbrSectionEvent;
import cl.bbr.core.classes.container.BbrSectionEventListener;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrHSeparator;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;

public class AddRelatedProduct extends BbrWindow implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	private static final long					serialVersionUID		= 1050650438067710484L;

	private Button								btn_Save				= null;

	private ArrayList<CPItemDTO>			productsList			= new ArrayList<CPItemDTO>();
	private SelectionProductsSection			sec_SelectionProducts	= null;
	private ProductAssignmentSection			sec_ProductAssignment	= null;

	private CompanyDTO							companySelected			= null;
	
	private CPItemDTO                           mainproduct = null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public AddRelatedProduct(BbrUI parent, CPItemDTO mainproduct)
	{
		super(parent);
		this.mainproduct = mainproduct;
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
//		// Sección de Selección de Proveedor
//		this.sec_SearchProvider = new SearchProviderSection(this.getBbrUIParent());
//		this.sec_SearchProvider.initializeView();
//		this.sec_SearchProvider.addBbrSectionListener(BbrFilterSectionConstants.FS_PROVIDER, (BbrSectionEventListener & Serializable) e -> this.providerChange_Listener(e));

		// Sección de Selección de Productos
		this.sec_SelectionProducts = new SelectionProductsSection(this.getBbrUIParent(), this.mainproduct);
		this.sec_SelectionProducts.initializeView();
		this.sec_SelectionProducts.addBbrSectionListener(BbrFilterSectionConstants.FS_PRODUCT, (BbrSectionEventListener & Serializable) e -> this.productChange_Listener(e));

		// Sección de Asignacion de Plantillas
		this.sec_ProductAssignment = new ProductAssignmentSection(this.getBbrUIParent(), this.mainproduct);
		this.sec_ProductAssignment.initializeView();
		this.sec_ProductAssignment.addBbrSectionListener(BbrFilterSectionConstants.FS_TEMPLATE, (BbrSectionEventListener & Serializable) e -> this.templateChange_Listener(e));

		VerticalLayout pnl_LeftPanel = new VerticalLayout(this.sec_SelectionProducts);
		pnl_LeftPanel.setExpandRatio(this.sec_SelectionProducts, 1F);
		pnl_LeftPanel.setSizeFull();
		pnl_LeftPanel.setSpacing(true);
		pnl_LeftPanel.setMargin(false);

		HorizontalLayout pnl_ContentGrids = new HorizontalLayout(pnl_LeftPanel, this.sec_ProductAssignment);
		pnl_ContentGrids.setSizeFull();

		// Accept Button
		this.btn_Save = new Button(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "save"));
		this.btn_Save.setStyleName("primary");
		this.btn_Save.addStyleName("btn-generic");
		this.btn_Save.setWidth("150px");
		this.btn_Save.addClickListener((ClickListener & Serializable) e -> btn_SaveAttribute_clickHandler(e));

		// Cancel Button
		Button btn_Cancel = new Button(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "cancel"));
		btn_Cancel.setStyleName("primary");
		btn_Cancel.addStyleName("btn-generic");
		btn_Cancel.setWidth("150px");
		btn_Cancel.addClickListener((ClickListener & Serializable) e -> btn_Cancel_clickHandler(e));

		HorizontalLayout buttonsPanel = new HorizontalLayout(this.btn_Save, new BbrHSeparator("40px"), btn_Cancel);
		buttonsPanel.addStyleName("bbr-buttons-panel");
		buttonsPanel.setWidth("600px");
		buttonsPanel.setSpacing(true);

		//this.articleTypes = this.getArticleTypes();

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addComponents(pnl_ContentGrids, buttonsPanel);
		mainLayout.setSizeFull();
		mainLayout.setSpacing(true);
		mainLayout.setExpandRatio(pnl_ContentGrids, 1F);
		mainLayout.setComponentAlignment(buttonsPanel, Alignment.BOTTOM_CENTER);
		mainLayout.addStyleName("bbr-margin-windows");

		// Ventana
		this.setWidth(90, Unit.PERCENTAGE);
		this.setHeight(90, Unit.PERCENTAGE);
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "title_product_information"));
		this.setContent(mainLayout);
	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		if (result != null)
		{
			AddRelatedProduct bbrSender = (AddRelatedProduct) sender;
			if (triggerObject == this.btn_Save)
			{
				bbrSender.updateSavedAttribute(result, sender);
			}
			else if (triggerObject == this.sec_SelectionProducts)
			{
				this.doReportProductUpdate(result, sender, triggerObject);
			}
		}
		else
		{
			AddRelatedProduct senderReport = (AddRelatedProduct) sender;

			this.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));

			senderReport.stopWaiting();
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	private void btn_Cancel_clickHandler(ClickEvent event)
	{
		this.close();
	}

	private void btn_SaveAttribute_clickHandler(ClickEvent e)
	{
		ProductsAssignmentList prodAssignmentList = new ProductsAssignmentList ();
		
		String errorMsg = this.validateData();

		if ((errorMsg == null) || (errorMsg.length() == 0))
		{
			ArrayList<ProductsAssignment> productAssignment = this.sec_ProductAssignment.getSectionResult();
			BbrEvent bbrEvent = new BbrEvent(BbrEvent.ITEM_SELECTED);
			
			if (productAssignment != null && productAssignment.size() > 0 ){
				
				prodAssignmentList.setValues(productAssignment.toArray(new ProductsAssignment[productAssignment.size()]));
				
			}
			
			bbrEvent.setResultObject(prodAssignmentList);

			dispatchBbrEvent(bbrEvent);
			this.close();
				
		}
		else
		{
			AddRelatedProduct.this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
		}
	}

	private void productChange_Listener(BbrSectionEvent e)
	{
		this.sec_ProductAssignment.setCompanyData(this.companySelected);
		//this.sec_ProductAssignment.setArticleTypes(this.articleTypes);
		this.sec_ProductAssignment.setSectionData(e);
	}

	private void templateChange_Listener(BbrSectionEvent e)
	{
		ProductsAssignment template = (ProductsAssignment) e.getResultObject();
		this.sec_SelectionProducts.deselectProducts(template);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private void doReportProductUpdate(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		AddRelatedProduct senderReport = (AddRelatedProduct) sender;

		if (result != null)
		{
			ProductsRecordsResultDTO reportResult = (ProductsRecordsResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), true);

			if (!error.hasError())
			{
				CPItemDTO[] productArray = null;//reportResult.getProductlists();
				this.productsList.clear();
				this.productsList = new ArrayList<CPItemDTO>(Arrays.stream(productArray).collect(Collectors.toList()));

				this.sec_SelectionProducts.setSectionData(this.productsList);
				this.updateSelectedProducts();
			}

		}
		senderReport.stopWaiting();
	}

	private String validateData()
	{
		String errorResult = null;

//		if (this.sec_SearchProvider.validateData() != null)
//		{
//			errorResult = this.sec_SearchProvider.validateData();
//		}

		return errorResult;
	}

	private void updateSavedAttribute(Object result, BbrWorkExecutor sender)
	{
		AddRelatedProduct senderReport = (AddRelatedProduct) sender;
		BbrEvent bbrEvent = new BbrEvent(BbrEvent.ITEM_CREATED);

		if (result != null)
		{
			MPItemErrorArrayResultDTO reportResult = (MPItemErrorArrayResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), false);

			if (!error.hasError())
			{
				if (!this.getBbrUIParent().hasAlertWindowOpen())
				{
					this.showInfoMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
							I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "successful_operation"));
				}
				this.dispatchBbrEvent(bbrEvent);
				this.close();
			}
			else
			{
				this.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"), error.getErrorMessage());
			}

		}
		senderReport.stopWaiting();
	}

	private void updateSelectedProducts()
	{
		ArrayList<ProductsAssignment> productsToAdd = this.sec_ProductAssignment.getProductsToAdd();
		ArrayList<CPItemDTO> productsToSelect = new ArrayList<>();

		if (this.productsList != null && this.productsList.size() > 0)
		{
			for (CPItemDTO productList : this.productsList)
			{
				if (productsToAdd != null && productsToAdd.size() > 0)
				{
					// for (TemplatesAssignment f : productsToAdd)
					productsToAdd.forEach(f ->
					{
						if (productList.getId().equals(f.getItemSelected().getId()))
						{
							productsToSelect.add(productList);
						}
					});
				}
			}
		}
		this.sec_SelectionProducts.selectProducts(productsToSelect);
	}

	//
	//
	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
