package bbr.b2b.portal.components.modules.fep;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.fep.cp.data.classes.CPItemArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsByStatusInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPRelatedProductArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPRelatedProductDTO;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

/**
 * @author MPE 2019-10
 */
public class RelatedProductEditor extends BbrWindow implements BbrWorkExecutor
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long			serialVersionUID			= 3247626779164695209L;

	private CPItemDTO						product						= null;

	private BbrAdvancedGrid<CPItemDTO>	dgd_AvailableProducts;
	private BbrAdvancedGrid<CPItemDTO>	dgd_AssignedProducts;

	private ArrayList<CPItemDTO>		assignedProducts			= null;
	private ArrayList<CPItemDTO>		availableProducts			= null;

	private HashMap<Long, CPItemDTO>	originalAssignedProducts	= new HashMap<>();

	private CPItemsByStatusInitParamDTO				initParam				= null;

	private Button						btnAddItem					= new Button(VaadinIcons.ANGLE_RIGHT);
	private Button						btnAddItems					= new Button(VaadinIcons.ANGLE_DOUBLE_RIGHT);
	private Button						btnRemoveItem				= new Button(VaadinIcons.ANGLE_LEFT);
	private Button						btnRemoveItems				= new Button(VaadinIcons.ANGLE_DOUBLE_LEFT);

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
	public RelatedProductEditor(BbrUI parent, CPItemDTO item)
	{
		super(parent);
		this.product = item;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	public void initializeView()
	{
		this.getRelatedProductsByItem();
		this.updateAssignedProducts();
		
		this.updateAvailableProducts();

		//		// -------------PROFILES PANELS-------------//
		//
		//		// Available Panel
		Label lblAvailables = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "availables_products"));

		this.dgd_AvailableProducts = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.initializeAvailableProductsGridColumns();
		this.dgd_AvailableProducts.setSortable(false);
		this.dgd_AvailableProducts.setSelectionMode(SelectionMode.MULTI);
		this.dgd_AvailableProducts.addStyleName("report-grid");
		this.dgd_AvailableProducts.addStyleName("bbr-multi-grid");
		this.dgd_AvailableProducts.setSizeFull();
		this.dgd_AvailableProducts.addSelectionListener((SelectionListener<CPItemDTO> & Serializable) e -> addProfile_clickHandler(e));

		VerticalLayout availablePanel = new VerticalLayout();
		availablePanel.setSizeFull();
		availablePanel.addComponents(lblAvailables, this.dgd_AvailableProducts);
		availablePanel.setMargin(false);
		availablePanel.setExpandRatio(this.dgd_AvailableProducts, 1F);
		//
		//		// Assigned Panel
		Label lblAssigned = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "related_products"));

		this.dgd_AssignedProducts = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.initializeAssignedProductsGridColumns();

		this.dgd_AssignedProducts.setSortable(false);
		this.dgd_AssignedProducts.setSelectionMode(SelectionMode.MULTI);
		this.dgd_AssignedProducts.addStyleName("report-grid");
		this.dgd_AssignedProducts.addStyleName("bbr-multi-grid");
		this.dgd_AssignedProducts.setSizeFull();
		this.dgd_AssignedProducts.addSelectionListener((SelectionListener<CPItemDTO> & Serializable) e -> removeProfile_clickHandler(e));

		VerticalLayout assignedPanel = new VerticalLayout();
		assignedPanel.setSizeFull();
		assignedPanel.addComponents(lblAssigned, this.dgd_AssignedProducts);
		assignedPanel.setMargin(false);
		assignedPanel.setExpandRatio(this.dgd_AssignedProducts, 1F);
		//
		//		// Operations Buttons Panel
		VerticalLayout operationsButtonsPanel = new VerticalLayout();
		operationsButtonsPanel.setWidth("50px");
		operationsButtonsPanel.setMargin(false);

		this.btnAddItems.addClickListener((ClickListener & Serializable) e -> addAllProfiles_clickHandler(e));
		this.btnRemoveItems.addClickListener((ClickListener & Serializable) e -> removeAllProfiles_clickHandler(e));

		operationsButtonsPanel.addComponents(this.btnAddItems, this.btnRemoveItems);
		operationsButtonsPanel.setComponentAlignment(this.btnAddItems, Alignment.MIDDLE_CENTER);
		operationsButtonsPanel.setComponentAlignment(this.btnRemoveItems, Alignment.MIDDLE_CENTER);

		HorizontalLayout profilesPanel = new HorizontalLayout();
		profilesPanel.setHeight("100%");
		profilesPanel.setWidth("100%");
		profilesPanel.addStyleName("bbr-filter-sections");
		profilesPanel.addStyleName("bbr-panel-space");
		profilesPanel.addStyleName("bbr-margin-panel-zero-top");

		profilesPanel.addComponents(availablePanel, operationsButtonsPanel, assignedPanel);
		profilesPanel.setExpandRatio(availablePanel, 1F);
		profilesPanel.setExpandRatio(assignedPanel, 1F);
		profilesPanel.setComponentAlignment(operationsButtonsPanel, Alignment.MIDDLE_CENTER);

		HorizontalLayout formLayout = new HorizontalLayout(profilesPanel);
		formLayout.setExpandRatio(profilesPanel, 1F);
		formLayout.setSizeFull();
		formLayout.setSpacing(true);
		//
		//		// -------------BUTTONS PANEL-------------//
		Button btn_UpdateProfiles = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "save"));
		btn_UpdateProfiles.setStyleName("primary");
		btn_UpdateProfiles.addStyleName("btn-login");
		btn_UpdateProfiles.setWidth("150px");
		btn_UpdateProfiles.addClickListener((ClickListener & Serializable) e -> btnUpdateProfiles_clickHandler(e));
		btn_UpdateProfiles.setClickShortcut(KeyCode.ENTER);

		Button btn_Cancel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"));
		btn_Cancel.setStyleName("primary");
		btn_Cancel.addStyleName("btn-login");
		btn_Cancel.setWidth("150px");
		btn_Cancel.addClickListener((ClickListener & Serializable) e -> btnClose_clickHandler(e));

		HorizontalLayout buttonsPanel = new HorizontalLayout(btn_UpdateProfiles, btn_Cancel);
		buttonsPanel.setComponentAlignment(btn_UpdateProfiles, Alignment.MIDDLE_LEFT);
		buttonsPanel.setComponentAlignment(btn_Cancel, Alignment.MIDDLE_RIGHT);
		buttonsPanel.setWidth("400px");
		buttonsPanel.addStyleName("bbr-margin-panel");
		buttonsPanel.setSpacing(true);
		//
		//		// Main Layout
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.addComponents(formLayout, buttonsPanel);
		mainLayout.setComponentAlignment(formLayout, Alignment.TOP_CENTER);
		mainLayout.setComponentAlignment(buttonsPanel, Alignment.BOTTOM_CENTER);
		mainLayout.setExpandRatio(formLayout, 1F);
		mainLayout.setSpacing(true);
		mainLayout.addStyleName("bbr-margin-windows");

		this.updateDataSourceAndUI();
		// Main Windows
		this.setWidth("1200px");
		this.setHeight("500px");
		this.setResizable(false);
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "win_title_profile_editor"));
		this.setContent(mainLayout);
	}
	
	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject) {
		RelatedProductEditor bbrSender = (RelatedProductEditor) sender;

		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				//bbrSender.doUpdateReport(result, sender);
			}
		}
		else
		{
			bbrSender.showErrorMessage(I18NManager.getI18NString(bbrSender.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(bbrSender.getBbrUIParent(), BbrUtilsResources.RES_GENERIC,
			"unsuccessful_operation"));
		}

		bbrSender.stopWaiting();
		
	}


	private void initializeAvailableProductsGridColumns()
	{
		this.dgd_AvailableProducts.addColumn(CPItemDTO::getSku)
		.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "code"))
		.setDescriptionGenerator(CPItemDTO::getSku)
		.setWidth(180F);
		this.dgd_AvailableProducts.addColumn(CPItemDTO::getDescription)
		.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "description"))
		.setDescriptionGenerator(CPItemDTO::getDescription);
	}

	private void initializeAssignedProductsGridColumns()
	{
		this.dgd_AssignedProducts.addColumn(CPItemDTO::getSku)
		.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "code"))
		.setDescriptionGenerator(CPItemDTO::getSku)
		.setWidth(180F);
		this.dgd_AssignedProducts.addColumn(CPItemDTO::getDescription)
		.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "description"))
		.setDescriptionGenerator(CPItemDTO::getDescription);

		this.dgd_AssignedProducts.addComponentColumn(item ->
		{
			BbrTextField ctext = this.getValuesByRelatedProduct(item);
			ctext.setWidth("90px");
			//			ctext.setValue("1");		

			return new HorizontalLayout()
			{
				private static final long serialVersionUID = 1L;

				{
					setSpacing(true);
					addComponent(ctext);
				}
			};
		}).setCaption("Cantidad").setExpandRatio(1);
	}

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	private void btnClose_clickHandler(ClickEvent event)
	{
		this.close();
	}

	private void btnUpdateProfiles_clickHandler(ClickEvent e)
	{
		Long[] toAdd = this.getProductsToAdd();
		this.doUpdateRelatedProducts(toAdd);
		
		//Long[] toremove = this.getProductsToRemove();
		//this.doUpdateProfiles(toadd, toremove);
	}

	private void addProfile_clickHandler(SelectionEvent<CPItemDTO> e)
	{
		ArrayList<CPItemDTO> items = new ArrayList<>(dgd_AvailableProducts.getSelectedItems());
		for (CPItemDTO item : items)
		{
			item = removeAvailableProductById(item.getId());

			if (assignedProducts == null)
			{
				assignedProducts = new ArrayList<>();
			}

			assignedProducts.add(item);
		}

		updateDataSourceAndUI();
	}
	//
	private void addAllProfiles_clickHandler(ClickEvent e)
	{
		if (availableProducts != null && availableProducts.size() > 0)
		{
			for (CPItemDTO profile : availableProducts)
			{
				if (assignedProducts == null)
				{
					assignedProducts = new ArrayList<>();
				}

				assignedProducts.add(profile);
			}

			availableProducts = new ArrayList<>();

			updateDataSourceAndUI();
		}
	}
	//
	private void removeProfile_clickHandler(SelectionEvent<CPItemDTO> e)
	{
		ArrayList<CPItemDTO> items = new ArrayList<>(dgd_AssignedProducts.getSelectedItems());
		for (CPItemDTO item : items)
		{
			item = removeAssignedProfileById(item.getId());

			if (availableProducts == null)
			{
				availableProducts = new ArrayList<>();
			}

			availableProducts.add(item);
		}

		updateDataSourceAndUI();
	}
	//
	private void removeAllProfiles_clickHandler(ClickEvent e)
	{
		if (assignedProducts != null && assignedProducts.size() > 0)
		{
			for (CPItemDTO item : assignedProducts)
			{
				if (availableProducts == null)
				{
					availableProducts = new ArrayList<>();
				}

				availableProducts.add(item);
			}

			assignedProducts = new ArrayList<>();

			updateDataSourceAndUI();
		}
	}
	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	
	private void doUpdateRelatedProducts (Long[] toAdd){
		String message = "";
		try
		{
			if (toAdd != null && toAdd.length > 0)
			{
//				BaseResultDTO addResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().addUserProfileRelations(this.item.getId(), toAdd, this.getUser().getId());
//				if (addResult != null)
//				{
//					message = I18NManager.getErrorMessageBaseResult(addResult); /
//				else
//				{
//					// -> Error userResult = null
//					message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
//				}
				
			}
		}
		catch (Exception e) // Error no controlado
		{
			message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
		}

		if (message.length() > 0)
		{
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), message);
		}
		else
		{
			message = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "successful_operation");
			this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"), message);
			BbrEvent editEvent = new BbrEvent(BbrEvent.ITEM_UPDATED);
			this.dispatchBbrEvent(editEvent);
			this.close();
		}
	}
	/*private void doUpdateProfiles(Long[] toAdd, Long[] toRemove)
	{
		String message = "";
		try
		{
			if (toAdd != null && toAdd.length > 0)
			{
				BaseResultDTO addResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().addUserProfileRelations(this.item.getId(), toAdd, this.getUser().getId());
				if (addResult != null)
				{
					message = I18NManager.getErrorMessageBaseResult(addResult); // <--
																				// Obtiene
																				// el
																				// mensaje
																				// de
																				// error.
																				// ""
																				// si
																				// no
																				// hay
																				// errores.
				}
				else
				{
					// -> Error userResult = null
					message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
				}
			}
			if (message.length() <= 0 && toRemove != null && toRemove.length > 0)
			{
				BaseResultDTO removeResult = EJBFactory.getUserEJBFactory().getUserReportManagerLocal().deleteUserProfileRelations(this.item.getId(), toRemove, this.getUser().getId());
				if (removeResult != null)
				{
					message = I18NManager.getErrorMessageBaseResult(removeResult); // <--
																					// Obtiene
																					// el
																					// mensaje
																					// de
																					// error.
																					// ""
																					// si
																					// no
																					// hay
																					// errores.
				}
				else
				{
					// -> Error userResult = null
					message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
				}
			}

		}
		catch (Exception e) // Error no controlado
		{
			message = I18NManager.getI18NString(BbrUtilsResources.RES_SYSTEM, "U1");
		}

		if (message.length() > 0)
		{
			this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), message);
		}
		else
		{
			message = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "successful_operation");
			this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"), message);
			BbrEvent editEvent = new BbrEvent(BbrEvent.ITEM_UPDATED);
			this.dispatchBbrEvent(editEvent);
			this.close();
		}
	}*/
	//
	private void updateAssignedProducts()
	{
		assignedProducts = null;

		try
		{

			if (this.relatedItemsMap != null && this.relatedItemsMap.get(this.product.getId()) != null && this.relatedItemsMap.get(this.product.getId()).size() > 0)
			{
				assignedProducts = new ArrayList<>(this.relatedItemsMap.get(this.product.getId()));

				if (assignedProducts != null && assignedProducts.size() > 0)
				{
					for (CPItemDTO item : assignedProducts)
					{
						originalAssignedProducts.put(item.getId(), item);
					}
				}
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
			if (this.product != null && this.product.getId() > 0){
				Long [] ids = new Long[1];
				ids[0] = this.product.getId();

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
	//
	private void updateAvailableProducts()
	{
		this.availableProducts = null;

		try
		{
			this.initParam = new CPItemsByStatusInitParamDTO();
			this.initParam.setProvidercode(this.product.getProvidercode());
			this.initParam.setStatus(1);
			this.initParam.setUsertypeid(-1L);
			
			CPItemArrayResultDTO itemsResult = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal().getItemsByStatusData(this.initParam, this.getUser().getId());

			if (itemsResult != null && itemsResult.getValues() != null)
			{
				availableProducts = new ArrayList<>(Arrays.asList(itemsResult.getValues()));
				
				if (this.assignedProducts != null && this.assignedProducts.size() > 0){
					
					List<CPItemDTO> operatedList = new ArrayList<>();
					for (Long id: this.relatedProductsMap.get(this.product.getId())){
						availableProducts.stream()
						  .filter(item -> item.getId().equals(id))
						  .forEach(item -> {
						    operatedList.add(item);
						});
					}
					
					availableProducts.removeAll(operatedList);
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
	//
	private CPItemDTO removeAvailableProductById(Long productid)
	{
		CPItemDTO result = null;

		if (availableProducts != null && availableProducts.size() > 0)
		{
			result = availableProducts.stream()
					.filter(profile -> profile.getId().equals(productid))
					.findFirst().map(p ->
					{
						availableProducts.remove(p);
						return p;
					}).get();
		}

		return result;
	}
	//
	private CPItemDTO removeAssignedProfileById(Long productid)
	{
		CPItemDTO result = null;

		if (assignedProducts != null && assignedProducts.size() > 0)
		{
			result = assignedProducts.stream()
					.filter(profile -> profile.getId().equals(productid))
					.findFirst().map(p ->
					{
						assignedProducts.remove(p);
						return p;
					}).get();
		}

		return result;
	}
	//
	private void updateDataSourceAndUI()
	{
		ListDataProvider<CPItemDTO> assignedDataprovider = new ListDataProvider<>(assignedProducts != null ? assignedProducts : new ArrayList<>());
		dgd_AssignedProducts.setDataProvider(assignedDataprovider);

		ListDataProvider<CPItemDTO> availableDataprovider = new ListDataProvider<>(availableProducts != null ? availableProducts : new ArrayList<>());
		dgd_AvailableProducts.setDataProvider(availableDataprovider);

		btnAddItem.setEnabled((availableProducts != null && availableProducts.size() > 0));
		btnAddItems.setEnabled((availableProducts != null && availableProducts.size() > 0));
		btnRemoveItem.setEnabled((assignedProducts!= null && assignedProducts.size() > 0));
		btnRemoveItems.setEnabled((assignedProducts != null && assignedProducts.size() > 0));
	}
	//
	private Long[] getProductsToAdd()
	{
		Long[] result = null;
		ArrayList<Long> products = new ArrayList<>();

		if (assignedProducts != null && assignedProducts.size() > 0)
		{
			for (CPItemDTO item : assignedProducts)
			{
				if (originalAssignedProducts.get(item.getId()) == null)
				{
					products.add(item.getId());
				}
			}
		}

		if (products.size() > 0)
		{
			result = new Long[products.size()];

			result = products.toArray(result);
		}

		return result;
	}
	//
	private Long[] getProductsToRemove()
	{
		Long[] result = null;
		ArrayList<Long> products = new ArrayList<>();

		if (availableProducts != null && availableProducts.size() > 0)
		{
			for (CPItemDTO item : availableProducts)
			{
				if (originalAssignedProducts.get(item.getId()) != null)
				{
					products.add(item.getId());
				}
			}
		}

		if (products.size() > 0)
		{
			result = new Long[products.size()];

			result = products.toArray(result);
		}

		return result;
	}

	private BbrTextField getValuesByRelatedProduct(CPItemDTO item){

		final BbrTextField result = new BbrTextField ();
		CPRelatedProductDTO relatedProductDTO = null;

		if (this.getBbrUIParent().getUser() != null){

			result.setWidth("70%");
			result.setHeight("90%");
			result.setValue("1");

			if (this.relatedProductsMap != null && this.relatedProductsMap.size() > 0 && this.product != null && this.product.getId() > 0){
				//Buscar si el producto complementario (item) existe en el mapa
				List<Long> relatedIds = this.relatedProductsMap.get( this.product.getId() );

				if (relatedIds.contains(item.getId())){

					if (this.relatedproducts != null && this.relatedproducts.getValues() != null && this.relatedproducts.getValues().length > 0){

						relatedProductDTO = Arrays.stream(this.relatedproducts.getValues())
								.filter(x -> x.getMainproductid().equals(this.product.getId()) 
										&& x.getRelatedproductid().equals(item.getId()))
								.findFirst()
								.orElse(null);


						result.setValue(Integer.toString(relatedProductDTO.getCount()));

					}

				}

				result.addValueChangeListener( e -> e.getValue());

			}else{
				result.setValue("1");
			}
		}

		return result;
	}

	//	// ****************************************************************************************
	//	// ENDING SECTION ----> PRIVATE METHODS
	//	// ****************************************************************************************

}
