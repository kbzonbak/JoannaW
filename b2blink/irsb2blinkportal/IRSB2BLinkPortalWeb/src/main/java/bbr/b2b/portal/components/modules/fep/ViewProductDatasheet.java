package bbr.b2b.portal.components.modules.fep;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.LocalDateTimeRenderer;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.fep.common.data.classes.UserTypeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.UserTypeDTO;
import bbr.b2b.fep.cp.data.classes.CPGroupedAttributeValueDTO;
import bbr.b2b.fep.cp.data.classes.CPHistItemArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPHistItemDTO;
import bbr.b2b.fep.cp.data.classes.CPHistItemInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPItemAttributeValueDTO;
import bbr.b2b.fep.cp.data.classes.CPItemDTO;
import bbr.b2b.fep.cp.data.classes.CPItemTechnicalInfoArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsByIDsInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPRelatedProductArrayResultDTO;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.InitParamsUtil;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.FEPConstants;
import bbr.b2b.users.report.classes.CompanyDataDTO;
import cl.bbr.core.classes.constants.CoreConstants;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrHSeparator;
import cl.bbr.core.components.basics.BbrTabContent;
import cl.bbr.core.components.basics.BbrTabSheet;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.widgets.bbrpopupbutton.BbrPopupButton;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class ViewProductDatasheet extends BbrWindow implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long								serialVersionUID			= 4946776453603112345L;

	private static final String							WHEN							= "when";
	private static final String							USERNAME						= "username";
	private static final String							STATENAME					= "statename";
	private static final String							COMMENT						= "comment";

	private Button												btn_EditProduct			= null;

	private Button												btn_Download				= null;
	private Button												btn_Refresh					= null;

	private BbrTabSheet										tabInfo						= null;
	private BbrTabContent									tabContentFEPInfo			= null;
	private BbrTabContent									tabContentTrackingInfo	= null;
	private BbrAdvancedGrid<CPHistItemDTO>				datGrid_TrackingItems	= null;

	private BbrWork<CPItemTechnicalInfoArrayResultDTO>	reportWork					= null;
	private BbrWork<CPHistItemArrayResultDTO>			trackingInfoWork			= null;
	private BbrWork<FileDownloadInfoResultDTO>		downloadsWork				= null;

	private CompanyDataDTO									selectedProvider			= null;
	private CPItemDTO											selectedItem				= null;

	private VerticalLayout									pnlAttributesData			= null;
	private VerticalLayout									pnlAttributesImages		= null;

	private boolean											isFirstTrackingInfoLoad			= true;
	private boolean											isCalledFromManagementReport	= true;
	
	private BbrPopupButton								btn_DownloadProductsGrp			= null;
	private Button											btn_DownloadRelatedProduct				= null;
	
	private CPRelatedProductArrayResultDTO relatedProducts 			= null;
	private LinkedHashMap<Long, List<CPItemDTO>> relatedItemsMap 	= null;
	private LinkedHashMap<Long, List<Long>> relatedArticleTypeMap 	= null;
	private LinkedHashMap<Long, List<Long>> relatedProductsMap 		= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ViewProductDatasheet(BbrUI parent, CPItemDTO selectedItem, CompanyDataDTO selectedProvider, Boolean callingFromManagementReport)
	{
		super(parent);

		if ((selectedItem != null) && (selectedProvider != null))
		{
			this.selectedItem = selectedItem;
			this.selectedProvider = selectedProvider;
			this.isCalledFromManagementReport = callingFromManagementReport;
		}
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
		if (this.selectedItem != null)
		{
			// SECCIÓN ENCABEZADO
			
			//Información de los productos complementarios
			this.getRelatedProductsByItem();
			
			// Encabezado Izquierda

			// Descripción del Producto
			BbrTextField txt_ProductDescription = this.getInfoTextField("product_description");
			txt_ProductDescription.setValue((this.selectedItem.getDescription() != null) ? this.selectedItem.getDescription() : "");

			// Sección Plantilla
			BbrTextField txt_Template = this.getInfoTextField("articletype_name");
			txt_Template.setValue((this.selectedItem.getArticletypedescription() != null) ? this.selectedItem.getArticletypedescription() : "");

			// Encabezado Derecha
			// SKU
			BbrTextField txt_SKU = this.getInfoTextField("lbl_sku");
			txt_SKU.setValue((this.selectedItem.getSku() != null) ? this.selectedItem.getSku() : "");

			// Estado
			BbrTextField txt_State = this.getInfoTextField("state");
			txt_State.setValue((this.selectedItem.getCurrentstatename() != null) ? this.selectedItem.getCurrentstatename() : "");

			FormLayout frmLeftHeader = this.embedIntoForm(txt_ProductDescription, txt_Template);
			FormLayout frmRightHeader = this.embedIntoForm(txt_SKU, txt_State);

			HorizontalLayout pnlHeader = new HorizontalLayout();
			pnlHeader.setMargin(false);
			pnlHeader.setWidth("100%");
			pnlHeader.setHeight("90px");
			pnlHeader.addComponents(frmLeftHeader, new BbrHSeparator("20px"), frmRightHeader);
			pnlHeader.setExpandRatio(frmLeftHeader, 1F);
			pnlHeader.setExpandRatio(frmRightHeader, 1F);

			// Barra de herramientas superior izq
			HorizontalLayout leftButtonsBar = new HorizontalLayout();
			leftButtonsBar.setSpacing(true);
			leftButtonsBar.setMargin(false);
			leftButtonsBar.setHeight("30px");
			leftButtonsBar.addStyleName("toolbar-layout");

			this.btn_EditProduct = new Button("", EnumToolbarButton.EDIT_ALTERNATIVE.getResource());
			this.btn_EditProduct.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "edit_product_riched"));
			this.btn_EditProduct.addClickListener((ClickListener & Serializable) e -> this.btn_EditProduct_clickHandler(e));
			this.btn_EditProduct.addStyleName("toolbar-button");
			this.btn_EditProduct.setVisible(this.isCalledFromManagementReport);

			leftButtonsBar.addComponents(this.btn_EditProduct);
			
			//MPE
			VerticalLayout cmp_DownloadProductButtons = new VerticalLayout();
			cmp_DownloadProductButtons.setMargin(new MarginInfo(false, true));
			cmp_DownloadProductButtons.setSpacing(false);

			this.btn_Download = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_selected_products_in_pdf"));
			this.btn_Download.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_selected_products_in_pdf"));
			this.btn_Download.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadFile_downloadHandler(e));
			this.btn_Download.addStyleName("action-button");
			cmp_DownloadProductButtons.addComponent(this.btn_Download);
			
			this.btn_DownloadRelatedProduct = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_related_products"));
			this.btn_DownloadRelatedProduct.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "download_related_products"));
			this.btn_DownloadRelatedProduct.addClickListener((ClickListener & Serializable) e -> this.btn_DownloadFile_downloadHandler(e));
			this.btn_DownloadRelatedProduct.addStyleName("action-button");
			cmp_DownloadProductButtons.addComponent(this.btn_DownloadRelatedProduct);
			
			this.btn_DownloadProductsGrp = new BbrPopupButton("");
			this.btn_DownloadProductsGrp.setIcon(BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_DownloadPrimary.png"));
			this.btn_DownloadProductsGrp.addStyleName("toolbar-button");
			this.btn_DownloadProductsGrp.setContentLayout(cmp_DownloadProductButtons);
			this.btn_DownloadProductsGrp.setClosePopupOnOutsideClick(true);

			this.btn_Refresh = new Button("", EnumToolbarButton.REFRESH.getResource());
			this.btn_Refresh.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "module_refresh"));
			this.btn_Refresh.addClickListener((ClickListener & Serializable) e -> this.refreshReport_clickHandler(e));
			this.btn_Refresh.addStyleName("toolbar-button");

			HorizontalLayout rightButtonsBar = new HorizontalLayout();
			rightButtonsBar.addComponents(this.btn_DownloadProductsGrp, this.btn_Refresh);
			rightButtonsBar.setSpacing(true);
			rightButtonsBar.setMargin(false);
			rightButtonsBar.setHeight("30px");
			rightButtonsBar.addStyleName("toolbar-layout");

			HorizontalLayout toolBar = new HorizontalLayout();
			toolBar.setWidth("100%");
			toolBar.addComponents(leftButtonsBar, rightButtonsBar);
			toolBar.addStyleName("filter-toolbar");
			toolBar.setExpandRatio(leftButtonsBar, 1F);
			toolBar.setExpandRatio(rightButtonsBar, 1F);

			toolBar.setComponentAlignment(leftButtonsBar, Alignment.MIDDLE_LEFT);
			toolBar.setComponentAlignment(rightButtonsBar, Alignment.MIDDLE_RIGHT);

			this.createInfoTabs();

			HorizontalLayout pnlTabs = new HorizontalLayout();
			pnlTabs.setSizeFull();
			pnlTabs.setMargin(false);
			pnlTabs.addComponents(this.tabInfo);

			VerticalLayout mainLayout = new VerticalLayout();
			mainLayout.addComponents(pnlHeader, toolBar, pnlTabs);
			mainLayout.setSizeFull();
			mainLayout.setSpacing(true);
			mainLayout.setExpandRatio(pnlTabs, 1F);
			mainLayout.addStyleName("bbr-margin-windows");

			// Ventana
			this.setWidth(90, Unit.PERCENTAGE);
			this.setHeight(90, Unit.PERCENTAGE);
			this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "title_product_datasheet"));
			this.setContent(mainLayout);

			this.startWaiting();

			this.reportWork = new BbrWork<>(this, this.getBbrUIParent(), this);
			this.reportWork.addFunction(new Function<Object, CPItemTechnicalInfoArrayResultDTO>()
			{

				@Override
				public CPItemTechnicalInfoArrayResultDTO apply(Object t)
				{
					return executeService(ViewProductDatasheet.this.getBbrUIParent());
				}
			});
			this.executeBbrWork(this.reportWork);

	}
}


@Override
public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
{
	if (result != null)
	{
		ViewProductDatasheet bbrSender = (ViewProductDatasheet) sender;
		if (triggerObject instanceof BbrWorkExecutor)
		{
			bbrSender.doUpdateReport(result, sender);
		}
		else if (triggerObject == this.tabContentTrackingInfo)
		{
			bbrSender.doUpdateTrackingInfo(result, sender);
		}
		else if (triggerObject == this.btn_DownloadTriggerButton)
		{
			bbrSender.doDownloadFile(result, sender, triggerObject);
		}
	}
	else
	{
		ViewProductDatasheet senderReport = (ViewProductDatasheet) sender;

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

private void btn_DownloadFile_downloadHandler(ClickEvent e)
{
	this.btn_DownloadTriggerButton = e.getButton();

	if (this.btn_DownloadTriggerButton == this.btn_Download || this.btn_DownloadTriggerButton == this.btn_DownloadRelatedProduct)
	{
		this.downloadsWork = new BbrWork<>(this, this.getBbrUIParent(), this.btn_DownloadTriggerButton);
		this.downloadsWork.addFunction(new Function<Object, FileDownloadInfoResultDTO>()
		{
			@Override
			public FileDownloadInfoResultDTO apply(Object t)
			{
				return executeDownloadService(ViewProductDatasheet.this.getBbrUIParent(), ViewProductDatasheet.this.btn_DownloadTriggerButton, null);
			}
		});

		this.startWaiting();
		this.executeBbrWork(this.downloadsWork);
	}
}


private void btn_EditProduct_clickHandler(ClickEvent event)
{
	try
	{
		UserTypeArrayResultDTO userRoles = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(this.getBbrUIParent()).getUserTypes();
		UserTypeDTO commmercialRole;

		if ((userRoles != null) && (userRoles.getValues() != null) && (userRoles.getValues().length > 0))
		{
			commmercialRole = Arrays.stream(userRoles.getValues()).filter(ur -> ur.getInternalname().equals(FEPConstants.UCDM_ROLE_NAME)).findAny().orElse(null);

			if (commmercialRole != null)
			{
				CPItemDTO[] selectedProdsArray = { this.selectedItem };

				ProductsReleaseDetails detailsWin = new ProductsReleaseDetails(this.getBbrUIParent(), selectedProdsArray, this.selectedProvider, commmercialRole, this.selectedItem.getArticletypeid(), true);
				detailsWin.setParentModule(this.getParentModule());
				detailsWin.addBbrEventListener((BbrEventListener & Serializable) bbrEvent -> this.requestUpdatedHandler(bbrEvent));
				detailsWin.open(true, true, this.getParentModule());
			}
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
}


private void requestUpdatedHandler(BbrEvent bbrEvent)
{
	if (bbrEvent != null && bbrEvent.getEventType().equals(BbrEvent.ITEM_UPDATED))
	{
		this.updateReport();
	}
}


private void refreshReport_clickHandler(ClickEvent event)
{
	this.updateReport();
}

// =====================================================================================================================================
// ENDING SECTION ----> EVENTS HANDLERS
// =====================================================================================================================================


// =====================================================================================================================================
// BEGINNING SECTION ----> PRIVATE METHODS
// =====================================================================================================================================

private FileDownloadInfoResultDTO executeDownloadService(BbrUI bbrUIParent, Button downloadTriggerButton, Object extraData)
{
	FileDownloadInfoResultDTO fileResult = null;

	try
	{
		CPItemsByIDsInitParamDTO downloadInitParam = this.getInitParam();
		
		if (this.btn_DownloadTriggerButton == this.btn_Download)
		{
			fileResult = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(bbrUIParent).downloadPdfProductDatasheet(downloadInitParam, bbrUIParent.getUser().getId());
			
		} else if(this.btn_DownloadTriggerButton == this.btn_DownloadRelatedProduct){
			
			CPItemDTO[] items = new CPItemDTO[] { selectedItem };
			fileResult = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(bbrUIParent).downloadRelatedProducts(downloadInitParam.getArticletypeid(), items, bbrUIParent.getUser().getId());
		}
	}
	catch (BbrUserException ex)
	{
		AppUtils.getInstance().doLogOut(ex.getMessage(), this.getBbrUIParent());
	}
	catch (BbrSystemException ex)
	{
		ex.printStackTrace();
	}
	catch (Exception ex)
	{
		ex.printStackTrace();
	}

	return fileResult;
}


private CPItemTechnicalInfoArrayResultDTO executeService(BbrUI bbrUIParent)
{
	CPItemTechnicalInfoArrayResultDTO result = null;
	if (this.selectedItem != null)
	{
		try
		{
			CPItemsByIDsInitParamDTO initParams = getInitParam();
			result = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(bbrUIParent).getItemTechnicalInfoByID(initParams);
		}
		catch (BbrUserException ex)
		{
			AppUtils.getInstance().doLogOut(ex.getMessage(), this.getBbrUIParent());
		}
		catch (BbrSystemException ex)
		{
			ex.printStackTrace();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	return result;
}


private CPHistItemArrayResultDTO executeAdditionalInfoService(BbrUI bbrUIParent)
{
	CPHistItemArrayResultDTO result = null;

	if (this.selectedItem != null)
	{
		try
		{
			CPHistItemInitParamDTO initParam = new CPHistItemInitParamDTO();
			initParam.setItemid(this.selectedItem.getId());
			initParam.setLocale(this.getBbrUIParent().getLocale());

			result = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(bbrUIParent).getHistItemByID(initParam);
		}
		catch (BbrUserException ex)
		{
			AppUtils.getInstance().doLogOut(ex.getMessage(), this.getBbrUIParent());
		}
		catch (BbrSystemException ex)
		{
			ex.printStackTrace();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	return result;
}


private void createInfoTabs()
{
	this.tabInfo = new BbrTabSheet();
	this.tabInfo.setSizeFull();
	this.tabInfo.addSelectedTabChangeListener((SelectedTabChangeListener & Serializable) st -> this.selectedTab_changeHandler());

	// Tab Info FEP

	this.pnlAttributesImages = new VerticalLayout();
	this.pnlAttributesImages.setWidth("100%");
	this.pnlAttributesImages.setMargin(false);
	this.pnlAttributesImages.addStyleName("auto-height");

	this.pnlAttributesData = new VerticalLayout();
	this.pnlAttributesData.setWidth("100%");
	this.pnlAttributesData.setMargin(false);
	this.pnlAttributesData.addStyleName("auto-height");

	HorizontalLayout pnlAttributesLeft = new HorizontalLayout();
	pnlAttributesLeft.setSizeFull();
	pnlAttributesLeft.setMargin(false);
	pnlAttributesLeft.addStyleName("scrollable-layout");
	pnlAttributesLeft.addStyleName("bbr-margin-windows-10");
	pnlAttributesLeft.addComponents(this.pnlAttributesImages);
	
	HorizontalLayout pnlAttributesRigth = new HorizontalLayout();
	pnlAttributesRigth.setSizeFull();
	pnlAttributesRigth.setMargin(false);
	pnlAttributesRigth.addStyleName("scrollable-layout");
	pnlAttributesRigth.addStyleName("bbr-margin-windows-10");
	pnlAttributesRigth.addComponents(this.pnlAttributesData);
	
	HorizontalLayout pnlAttributesAll = new HorizontalLayout();
	pnlAttributesAll.setSizeFull();
	pnlAttributesAll.addComponents(pnlAttributesLeft, pnlAttributesRigth);
	pnlAttributesAll.setMargin(false);
	
	this.tabContentFEPInfo = new BbrTabContent();
	this.tabContentFEPInfo.setSizeFull();
	this.tabContentFEPInfo.addComponent(pnlAttributesAll);

	this.tabInfo.addBbrTab(this.tabContentFEPInfo, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "product_info"), false);

	// Tab Info Tracking

	this.datGrid_TrackingItems = new BbrAdvancedGrid<>(this.getUser().getLocale());
	datGrid_TrackingItems.setSortable(false);

	this.datGrid_TrackingItems.addCustomColumn(CPHistItemDTO::getUsername, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_user"), true)
	.setId(USERNAME);

	this.datGrid_TrackingItems.addCustomColumn(CPHistItemDTO::getWhen, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_date"), true)
	.setStyleGenerator(item -> "v-align-center")
	.setRenderer(new LocalDateTimeRenderer("dd-MM-yyyy HH:mm"))
	.setId(WHEN);

	this.datGrid_TrackingItems.addCustomColumn(CPHistItemDTO::getStatename, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_state"), true)
	.setId(STATENAME);

	this.datGrid_TrackingItems.addCustomColumn(CPHistItemDTO::getComment, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_comment"), true)
	.setDescriptionGenerator(CPHistItemDTO::getComment)
	.setId(COMMENT);

	this.datGrid_TrackingItems.addStyleName("report-grid");
	this.datGrid_TrackingItems.setSizeFull();
	this.datGrid_TrackingItems.setSelectionMode(SelectionMode.NONE);

	this.tabContentTrackingInfo = new BbrTabContent();
	this.tabContentTrackingInfo.setSizeFull();
	this.tabContentTrackingInfo.addComponent(this.datGrid_TrackingItems);
	this.tabContentTrackingInfo.setExpandRatio(this.datGrid_TrackingItems, 1F);

	this.tabInfo.addBbrTab(this.tabContentTrackingInfo, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "product_tracking"), false);
}


private void selectedTab_changeHandler()
{
	if (this.tabInfo.getSelectedBbrTabContent().equals(this.tabContentTrackingInfo) && (this.isFirstTrackingInfoLoad))
	{
		this.startWaiting();

		this.trackingInfoWork = new BbrWork<>(this, this.getBbrUIParent(), this.tabContentTrackingInfo);
		this.trackingInfoWork.addFunction(new Function<Object, CPHistItemArrayResultDTO>()
		{
			@Override
			public CPHistItemArrayResultDTO apply(Object t)
			{
				return executeAdditionalInfoService(ViewProductDatasheet.this.getBbrUIParent());
			}
		});
		this.executeBbrWork(this.trackingInfoWork);
	}
}


private void doDownloadFile(Object result, BbrWorkExecutor sender, Object triggerObject)
{
	ViewProductDatasheet senderReport = (ViewProductDatasheet) sender;
	if (senderReport != null)
	{
		senderReport.downloadLinkFile(result);
		senderReport.updateButtons(false);
	}
}


private void updateButtons(Boolean isSelectionEvent)
{
	this.btn_Download.setEnabled(this.selectedItem != null);

	this.btn_EditProduct.setEnabled(this.selectedItem != null);
	this.btn_Refresh.setEnabled(this.selectedItem != null);
}


private void doUpdateReport(Object result, BbrWorkExecutor sender)
{
	ViewProductDatasheet senderReport = (ViewProductDatasheet) sender;

	if (result != null)
	{
		//CPItemTechnicalInfoResultDTO reportResult = (CPItemTechnicalInfoResultDTO) result;
		CPItemTechnicalInfoArrayResultDTO reportResult = (CPItemTechnicalInfoArrayResultDTO) result;

		BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), true);

		if (!error.hasError())
		{
			if (reportResult != null)
			{
				Arrays.stream(reportResult.getValues()).forEach(item -> {
					
					List<CPGroupedAttributeValueDTO> items = item.getGroupsInfo().stream()
																				 .sorted(Comparator.comparingInt(CPGroupedAttributeValueDTO::getVisualorder))
																				 .collect(Collectors.toList());
					List<CPItemAttributeValueDTO> images = item.getImageAttributeValues();
					senderReport.updateAttributesDataPanel(items, item.getItem());
					senderReport.updateAttributesImagesPanel(images, item.getItem());
					
				});
				
			}

			this.updateButtons(false);
		}
	}

	senderReport.stopWaiting();
}


private void doUpdateTrackingInfo(Object result, BbrWorkExecutor sender)
{
	ViewProductDatasheet senderReport = (ViewProductDatasheet) sender;

	if (result != null)
	{
		CPHistItemArrayResultDTO reportResult = (CPHistItemArrayResultDTO) result;

		BbrError error = ErrorsMngr.getInstance().getError(reportResult, this.getBbrUIParent(), true);

		if (!error.hasError())
		{
			if (reportResult != null)
			{
				ListDataProvider<CPHistItemDTO> dataProvider = new ListDataProvider<>(Arrays.asList(reportResult.getHistory()));
				senderReport.datGrid_TrackingItems.setDataProvider(dataProvider);
				senderReport.datGrid_TrackingItems.getDataProvider().refreshAll();
			}
		}
	}

	senderReport.stopWaiting();

	this.isFirstTrackingInfoLoad = false;
}


private BbrTextField getInfoTextField(String captionResourceName)
{
	BbrTextField textField = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, captionResourceName));
	textField.addStyleName("bbr-text-field-read-only");
	textField.setWidth(100, Unit.PERCENTAGE);
	textField.setReadOnly(true);

	return textField;
}


private void updateReport()
{
	this.startWaiting(2);
	this.executeBbrWork(reportWork);
	this.executeBbrWork(trackingInfoWork);
}


private CPItemsByIDsInitParamDTO getInitParam()
{
	CPItemsByIDsInitParamDTO result = null;
	if (selectedItem != null)
	{
		result = InitParamsUtil.getBaseInitParamInstance(CPItemsByIDsInitParamDTO.class, this.getBbrUIParent());
		
//		if (relatedProducts != null && relatedProducts.getValues() != null && this.relatedProducts.getValues().length > 0){
//			
//			List<Long> relatedIds = new ArrayList<> (relatedProductsMap.get(selectedItem.getId()));
//			
//			result.setItemids(relatedIds.toArray(new Long [relatedIds.size()]));
//					
//		} else {
			
			result.setItemids(new Long[] { selectedItem.getId() });
//		}
		
		result.setArticletypeid(selectedItem.getArticletypeid());
		//result.setStateid(selectedItem.getCurrentstateid());
		result.setWithdetails(true);
	}

	return result;
}


private void updateAttributesDataPanel(List<CPGroupedAttributeValueDTO> items, CPItemDTO product)
{
	String lblvalue = product.getVisualorder().equals(1) ? "Producto Principal - SKU: " +  product.getSku() 
														 : "Producto Complementario - SKU: " + product.getSku() + " (Cant: " + product.getRelatedcount() + ")";
	//this.pnlAttributesData.removeAllComponents();
	this.pnlAttributesData.setSizeFull();
	if (items != null && !items.isEmpty())
	{
		Label lblSku = new Label(lblvalue);
		lblSku.addStyleName("data-panel-name-value-blue");
		this.pnlAttributesData.addComponent(lblSku);
		this.pnlAttributesData.setComponentAlignment(lblSku, Alignment.BOTTOM_RIGHT);
		
		for (CPGroupedAttributeValueDTO groupedItem : items)
		{
			GroupedAttributeViewer groupedViewer = new GroupedAttributeViewer(groupedItem);
			this.pnlAttributesData.addComponent(groupedViewer.getLayout());
		}
	}
}


private void updateAttributesImagesPanel(List<CPItemAttributeValueDTO> images, CPItemDTO product)
{
	String lblvalue = product.getVisualorder().equals(1) ? "Producto Principal - SKU: " +  product.getSku() 
	 													 : "Producto Complementario - SKU: " + product.getSku() + " (Cant: " + product.getRelatedcount() + ")";
	
	//this.pnlAttributesImages.removeAllComponents();
	this.pnlAttributesImages.setSizeFull();
	if (images != null && !images.isEmpty())
	{
		Label lblSku = new Label(lblvalue);
		lblSku.addStyleName("data-panel-name-value-blue");
		this.pnlAttributesImages.addComponent(lblSku);
		this.pnlAttributesImages.setComponentAlignment(lblSku, Alignment.BOTTOM_RIGHT);
		
		for (CPItemAttributeValueDTO image : images)
		{
			ImageAttributeViewer imageViewer = new ImageAttributeViewer(this.getBbrUIParent(), image);
			this.pnlAttributesImages.addComponent(imageViewer.getLayout());
		}
	}
	/*else
	{
		
		Label lblName = new Label("");
		lblName.addStyleName("data-panel-name-label");
		lblName.setWidth("120px");
		lblName.addStyleName("truncate");

		Label lblValue = new Label("Valor");
		lblValue.addStyleName("data-panel-name-value");

		Image imgImagen = new Image();
		imgImagen.setSource(BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUIParent(), CoreConstants.PATH_BASE_IMAGES_UTILS + "no_image.jpg"));
		imgImagen.setWidth("100%");
		imgImagen.setHeight("150px");
		imgImagen.addStyleName("data-panel-image");

		HorizontalLayout pnlHeader = new HorizontalLayout();
		pnlHeader.setWidth("100%");
		pnlHeader.setHeight("30px");
		pnlHeader.setMargin(false);
		pnlHeader.addStyleName("data-panel");
		pnlHeader.addComponent(lblName);

		HorizontalLayout pnlAttribute = new HorizontalLayout();
		pnlAttribute.setMargin(true);
		pnlAttribute.setSpacing(false);
		pnlAttribute.setWidth("100%");
		pnlAttribute.setHeight("100%");
		pnlAttribute.addStyleName("data-panel-row");
		pnlAttribute.addComponents(imgImagen);
		pnlAttribute.setComponentAlignment(imgImagen, Alignment.MIDDLE_CENTER);

		VerticalLayout layout = new VerticalLayout(pnlHeader, pnlAttribute);
		layout.setComponentAlignment(pnlAttribute, Alignment.MIDDLE_CENTER);
		layout.setMargin(false);
		layout.setSpacing(false);
		layout.setId("pnl_id");

		this.pnlAttributesImages.addComponents(layout);
		
	}*/
}

private void getRelatedProductsByItem()
{
	try
	{
		if (this.selectedItem != null && this.selectedItem.getId() > 0){
			Long [] ids = new Long[1];
			ids[0] = this.selectedItem.getId();

			this.relatedProducts = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal().getRelatedProductsByMainProducts(ids);

			if (relatedProducts != null && relatedProducts.getValues() != null && relatedProducts.getValues().length > 0){
				this.relatedProductsMap = relatedProducts.getRelatedProductsMap();
				this.relatedItemsMap = relatedProducts.getRelatedItemsMap();
				this.relatedArticleTypeMap = relatedProducts.getRelatedArticleTypeMap();

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
// =====================================================================================================================================
// ENDING SECTION ----> PRIVATE METHODS
// =====================================================================================================================================

}
