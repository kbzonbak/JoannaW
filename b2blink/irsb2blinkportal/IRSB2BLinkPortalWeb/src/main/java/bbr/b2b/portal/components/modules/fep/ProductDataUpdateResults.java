package bbr.b2b.portal.components.modules.fep;

import java.io.Serializable;
import java.util.Arrays;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.fep.common.data.classes.ErrorInfoDTO;
import bbr.b2b.fep.cp.data.classes.CPItemDTO;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrVSeparator;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;

public class ProductDataUpdateResults extends BbrWindow
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long					serialVersionUID			= -2763198731473494799L;

	private BbrAdvancedGrid<CPItemDTO>		datGrid_SuccessfulItems	= null;
	private BbrAdvancedGrid<ErrorInfoDTO>	datGrid_ErrorItems		= null;

	VerticalLayout									successSection				= null;
	VerticalLayout									errorsSection				= null;

	private CPItemDTO[]							succesfulItems				= null;
	private ErrorInfoDTO[]						errorItems					= null;
	private Boolean								isCalledFromApproval		= false;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ProductDataUpdateResults(BbrUI parent, CPItemDTO[] succesfulItems, ErrorInfoDTO[] errorItems, Boolean fromApproval)
	{
		super(parent);

		if ((succesfulItems != null) || (errorItems != null))
		{
			this.succesfulItems = succesfulItems;
			this.errorItems = errorItems;
			this.isCalledFromApproval = fromApproval;
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
		if ((this.succesfulItems != null) && (this.succesfulItems.length > 0))
		{
			// Seccion grilla exitos

			String successLabel 	= (this.isCalledFromApproval) ? 	I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "product_update_success_buyer_msg") : 
																					I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "product_update_success_msg");
			String retailerName 	= I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "b2b_retail_name");
			successLabel 			= BbrUtils.getInstance().substitute(successLabel, "", retailerName);
			
			Label lblSuccessesDescription = new Label(successLabel, ContentMode.HTML);
			lblSuccessesDescription.setWidth("100%");
			
			Label lblSuccessesDataGridHeadline = new Label(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "successfully_updated"));
			lblSuccessesDataGridHeadline.setWidth("100%");
			
			this.datGrid_SuccessfulItems = new BbrAdvancedGrid<>(this.getUser().getLocale());
			this.datGrid_SuccessfulItems.setSortable(false);

			this.initializeSuccessItemsDataGridColumns();

			this.datGrid_SuccessfulItems.addStyleName("report-grid");
			this.datGrid_SuccessfulItems.setSizeFull();
			this.datGrid_SuccessfulItems.setSelectionMode(SelectionMode.NONE);

			this.successSection = new VerticalLayout(lblSuccessesDescription, new BbrVSeparator("7px"), lblSuccessesDataGridHeadline, this.datGrid_SuccessfulItems);
			this.successSection.setExpandRatio(this.datGrid_SuccessfulItems, 1F);
			this.successSection.setMargin(false);
			this.successSection.setSizeFull();
		}

		if ((this.errorItems != null) && (this.errorItems.length > 0))
		{
			// Seccion grilla errores

			Label lblErrorsDescription = new Label(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "product_update_error_msg"));
			lblErrorsDescription.setWidth("100%");
			
			Label lblErrorsDataGridHeadline = new Label(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "errors_in_update"));
			lblErrorsDataGridHeadline.setWidth("100%");
			
			this.datGrid_ErrorItems = new BbrAdvancedGrid<>(this.getUser().getLocale());
			this.datGrid_ErrorItems.setSortable(false);

			this.initializeErrorItemsDataGridColumns();

			this.datGrid_ErrorItems.addStyleName("report-grid");
			this.datGrid_ErrorItems.setSizeFull();
			this.datGrid_ErrorItems.setSelectionMode(SelectionMode.NONE);

			this.errorsSection = new VerticalLayout(lblErrorsDescription, new BbrVSeparator("7px"), lblErrorsDataGridHeadline, this.datGrid_ErrorItems);
			this.errorsSection.setExpandRatio(this.datGrid_ErrorItems, 1F);
			this.errorsSection.setMargin(false);
			this.errorsSection.setSizeFull();
		}

		// Main Layout

		VerticalLayout mainLayout = new VerticalLayout();

		if(this.successSection != null)
		{
			mainLayout.addComponent(this.successSection);
			mainLayout.setExpandRatio(this.successSection, 1F);
		}

		if(this.errorsSection != null)
		{
			if(this.successSection != null)
			{
				// Si existen ambos tipos de items, espaciar las tablas
				
				mainLayout.addComponent(new BbrVSeparator("15px"));
			}
			
			mainLayout.addComponent(this.errorsSection);
			mainLayout.setExpandRatio(this.errorsSection, 1F);
		}
		
		Button acceptButton = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "accept"));
		acceptButton.setWidth("100px");
		acceptButton.addClickListener((ClickListener & Serializable) e -> this.close());
		
		HorizontalLayout acceptBtnLayout = new HorizontalLayout(acceptButton);
		acceptBtnLayout.setWidth("100%");
		acceptBtnLayout.setComponentAlignment(acceptButton, Alignment.MIDDLE_CENTER);
		
		mainLayout.addComponents(new BbrVSeparator("5px"), acceptBtnLayout);
		mainLayout.setSizeFull();
		mainLayout.setSpacing(true);
		mainLayout.setStyleName("bbr-margin-windows");

		// Ventana

		this.setWidth("900px");
		this.setHeight(((this.succesfulItems != null) && (this.succesfulItems.length > 0) && (this.errorItems != null) && (this.errorItems.length > 0)) ? "800px" : "400px");
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "title_product_information"));
		this.setContent(mainLayout);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private void initializeSuccessItemsDataGridColumns()
	{
		this.datGrid_SuccessfulItems.addCustomColumn(CPItemDTO::getId, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_request_number"), true)
		.setWidth(120L)
		.setDescriptionGenerator(cp -> cp.getId().toString());
		
//		this.datGrid_SuccessfulItems.addCustomColumn(CPItemDTO::getVendorcode, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_product_key"), true)
//		.setDescriptionGenerator(CPItemDTO::getVendorcode);
		
		this.datGrid_SuccessfulItems.addCustomColumn(CPItemDTO::getDescription, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "col_description_of_product"), true)
		.setWidthUndefined()
		.setDescriptionGenerator(CPItemDTO::getDescription);
	
		ListDataProvider<CPItemDTO> dataprovider = new ListDataProvider<>(Arrays.asList(this.succesfulItems));
		this.datGrid_SuccessfulItems.setDataProvider(dataprovider);
	}


	private void initializeErrorItemsDataGridColumns()
	{
		this.datGrid_ErrorItems.addCustomColumn(ErrorInfoDTO::getRownumber, I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "row"), true)
		.setWidth(100L)
		.setDescriptionGenerator(cp -> Integer.valueOf(cp.getRownumber()).toString());
		
		this.datGrid_ErrorItems.addCustomColumn(ErrorInfoDTO::getColumnname, I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "attribute"), true)
		.setWidth(250L)
		.setDescriptionGenerator(ErrorInfoDTO::getColumnname);

		this.datGrid_ErrorItems.addCustomColumn(ErrorInfoDTO::getStatusmessage, I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "problem"), true)
		.setWidthUndefined()
		.setDescriptionGenerator(ErrorInfoDTO::getStatusmessage);
		
		ListDataProvider<ErrorInfoDTO> dataprovider = new ListDataProvider<>(Arrays.asList(this.errorItems));
		this.datGrid_ErrorItems.setDataProvider(dataprovider);
	}

	//
	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
