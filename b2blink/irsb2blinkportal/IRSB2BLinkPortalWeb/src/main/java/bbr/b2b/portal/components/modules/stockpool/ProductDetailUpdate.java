package bbr.b2b.portal.components.modules.stockpool;

import java.io.Serializable;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.logistic.report.data.dto.AvailableStockReportDTO;
import bbr.b2b.logistic.report.data.dto.UpdateItemInitParamDTO;
import bbr.b2b.logistic.report.data.dto.UpdateItemResultDTO;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;
import cl.bbr.core.components.widgets.bbrtextfield.client.RestrictRange;

public class ProductDetailUpdate extends BbrWindow
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long		serialVersionUID	= 3247623456164698909L;

	private BbrTextField			txt_vendorSku		= null;
	private BbrTextField			txt_Description		= null;
	private BbrTextField			txt_AvailableStock	= null;
	private BbrTextField			txt_CriticalStock	= null;
	private BbrTextField			txt_Threshold		= null;
	private AvailableStockReportDTO	reportdto			= null;
	private CheckBox				chkBox_active		= null;
	private boolean					activeproduct;
	private String					vendorcode			= "";

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public ProductDetailUpdate(BbrUI parent, AvailableStockReportDTO reportdto, String vendorcode)
	{
		super(parent);
		this.reportdto = reportdto;
		this.vendorcode = vendorcode;
		this.activeproduct = reportdto.isActive();
	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	public void initializeView()
	{

		this.txt_vendorSku = this.getInfoTextField("col_sku", 73, true);
		FormLayout frmVendorsku = this.embedIntoForm(this.txt_vendorSku);
		this.txt_vendorSku.setValue(this.reportdto.getSku() != null ? this.reportdto.getSku() : "");
		this.txt_vendorSku.setEnabled(true);

		this.txt_Description = this.getInfoTextField("col_description", 85, true);
		FormLayout frmDescription = this.embedIntoForm(this.txt_Description);
		this.txt_Description.setValue(this.reportdto.getDescription() != null ? this.reportdto.getDescription() : "");
		this.txt_Description.setEnabled(true);

		this.txt_AvailableStock = this.getInfoTextField("col_available_stock_excel", 94, true);
		FormLayout frmAvailablestock = this.embedIntoForm(this.txt_AvailableStock);
		this.txt_AvailableStock.setValue(this.reportdto.getAvailablestock() != null ? this.reportdto.getAvailablestock().toString() : "");
		this.txt_AvailableStock.setEnabled(true);

		this.txt_CriticalStock = this.getInfoTextField2("col_available_critical_stock_excel", 86, true);
		FormLayout frmCriticalstock = this.embedIntoForm(this.txt_CriticalStock);
		this.txt_CriticalStock.setValue(this.reportdto.getCriticalstock() != null ? this.reportdto.getCriticalstock().toString() : "");
		this.txt_CriticalStock.setReadOnly(false);
		this.txt_CriticalStock.setRestrict(RestrictRange.NUMBERS);
		this.txt_CriticalStock.setEnabled(true);

		this.txt_Threshold = this.getInfoTextField2("col_variation", 81, true);
		FormLayout frmThreshold = this.embedIntoForm(this.txt_Threshold);
		this.txt_Threshold.setValue(this.reportdto.getThreshold() != null ? this.reportdto.getThreshold().toString() : "");
		this.txt_Threshold.setReadOnly(false);
		this.txt_Threshold.setRestrict(RestrictRange.NUMBERS);
		this.txt_Threshold.setEnabled(true);
	
		
		Label lbl_chkBox = new Label();
		//lbl_chkBox.setValue(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_activevev"));
		lbl_chkBox.setWidth("133px");
		lbl_chkBox.setHeight("150px");
		lbl_chkBox.setCaptionAsHtml(true);
		lbl_chkBox.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_activevev"));

		this.chkBox_active = new CheckBox();
		this.chkBox_active.setCaption("SI / NO");
		this.chkBox_active.addValueChangeListener((ValueChangeListener<Boolean> & Serializable) e -> this.cbx_OnlyActiveProducts_ValueChangeHandler(e));
		this.chkBox_active.setValue(this.activeproduct);

		HorizontalLayout chkboxLayout = new HorizontalLayout();
		chkboxLayout.setSizeFull();
		chkboxLayout.addComponents(lbl_chkBox, this.chkBox_active);
		chkboxLayout.setExpandRatio(this.chkBox_active, 1F);//

		VerticalLayout productLayout = new VerticalLayout(frmVendorsku, frmDescription, frmAvailablestock, frmCriticalstock, frmThreshold, chkboxLayout);
		//
		// Label lbl_UserName = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_COMMERCIAL, "name"));
		// lbl_UserName.setWidth("100px");

		// HorizontalLayout userNameLayout = new HorizontalLayout(lbl_UserName, txt_CategoryName);
		productLayout.setWidth("100%");
		// productLayout.setExpandRatio(txt_CategoryName, 1F);

		// User Last Name Field
		// txt_CategoryCode = new BbrTextField();
		// txt_CategoryCode.addStyleName("generic-text-field");
		// txt_CategoryCode.setWidth("100%");
		// txt_CategoryCode.setRestrict(ValidData.RESTRICT_SPECIAL);

		// Accept User Button
		Button btn_RegisterUser = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "save"));
		btn_RegisterUser.setStyleName("primary");
		btn_RegisterUser.addStyleName("btn-generic");
		btn_RegisterUser.setWidth("100%");
		btn_RegisterUser.addClickListener((ClickListener & Serializable) e -> btnCreateOrEdit_clickHandler(e));

		// Cancel Button
		Button btn_Cancel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"));
		btn_Cancel.setStyleName("primary");
		btn_Cancel.addStyleName("btn-generic");
		btn_Cancel.setWidth("100%");
		btn_Cancel.addClickListener((ClickListener & Serializable) e -> btnClose_clickHandler(e));

		HorizontalLayout buttonsPanel = new HorizontalLayout(btn_RegisterUser, btn_Cancel);
		buttonsPanel.addStyleName("bbr-buttons-panel");

		buttonsPanel.setWidth("300px");
		buttonsPanel.setSpacing(true);

		// Main Layout
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.addComponents(productLayout, buttonsPanel);
		mainLayout.setComponentAlignment(buttonsPanel, Alignment.BOTTOM_CENTER);
		mainLayout.setMargin(false);

		String winTitle = "Detalle de Produto";

		// Main Windows
		this.setWidth(500, Unit.PIXELS);
		this.setHeight(410, Unit.PIXELS);
		this.setResizable(false);

		this.setCaption(winTitle);
		this.setContent(mainLayout);
		this.addStyleName("win-details");
		txt_CriticalStock.focus();
	}

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************
	private void btnCreateOrEdit_clickHandler(ClickEvent event)
	{
		
		String errorMsg = validateData();
		
		if (errorMsg == null || errorMsg.length() > 0)
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
		}else{
		
			UpdateItemInitParamDTO initParamDTO = new UpdateItemInitParamDTO();
			initParamDTO.setActive(this.activeproduct);
			initParamDTO.setNewcriticalstock(Integer.valueOf(this.txt_CriticalStock.getValue()));
			initParamDTO.setSkuvendor(this.txt_vendorSku.getValue());
			initParamDTO.setVariation(Integer.valueOf(this.txt_Threshold.getValue()));
			initParamDTO.setVendorcode(this.vendorcode);
			
			UpdateItemResultDTO resultDTO = new UpdateItemResultDTO();
			try
			{
				// Start Tracking
				this.getTimingMngr().startTimer();
				resultDTO = EJBFactory.getStockpoolEJBFactory().getStockpoolReportManagerLocal(this.getBbrUIParent()).doUpdateItem(initParamDTO, this.getBbrUIParent().getUser().getId());
			}
			catch (Exception e)
			{
				e.printStackTrace();
				this.showErrorMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "error_webservice"));
			}
			
			if(resultDTO.getStatuscode().equals("-1")){
				String message = resultDTO.getStatusmessage();
				this.showErrorMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_error"), message);
				
			}
			if(resultDTO.getStatuscode().equals("0")){
				String message = resultDTO.getStatusmessage();
				this.showInfoMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_info"), message);
			}
			BbrEvent itemupdated = new BbrEvent("itemupdated");
			this.close();
			this.dispatchBbrEvent(itemupdated);
		}

	}
	
	private void btnClose_clickHandler(ClickEvent event)
	{
		this.close();
	}
	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

//	private void validateData()
//	{
//		// txt_CategoryName.setComponentError(null);
//		//
//		// String categoryName = txt_CategoryName.getValue().trim();
//		//
//		// if (categoryName == null || categoryName.length() <= 0) // -> Nombre
//		// // Categoría
//		// {
//		// txt_CategoryName.setComponentError(new UserError(I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_name")));
//		// }
//	}

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

	private BbrTextField getInfoTextField(String captionResourceName, int width, boolean isToRight)
	{
		BbrTextField textField = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, captionResourceName));
		textField.addStyleName("bbr-text-field-read-only");
		textField.setWidth(width, Unit.PERCENTAGE);
		textField.setReadOnly(true);
		if (isToRight)
		{
			textField.addStyleName("float-right");
		}
		return textField;
	}
	
	private BbrTextField getInfoTextField2(String captionResourceName, int width, boolean isToRight)
	{
		BbrTextField textField = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, captionResourceName));
		textField.addStyleName("bbr-text-field");
		textField.setWidth(width, Unit.PERCENTAGE);
		textField.setReadOnly(true);
		if (isToRight)
		{
			textField.addStyleName("float-right");
		}
		return textField;
	}

	private void cbx_OnlyActiveProducts_ValueChangeHandler(ValueChangeEvent<Boolean> e)

	{
		// BbrSectionEvent alertResponseEvent = new BbrSectionEvent(this.CBX_ONLY_ACTIVE_PRODUCTS_EVENT);
		// alertResponseEvent.setResultObject(e.getValue());
		activeproduct = e.getValue();
		// this.dispatchBbrSectionEvent(alertResponseEvent);
	}
	
	private String validateData()
	{
		String result = "";
		
		String criticalstock = this.txt_CriticalStock.getValue().trim();
		String variation = this.txt_Threshold.getValue().trim();
		
		if (criticalstock.length() < 1 || criticalstock.isEmpty())
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "field_crit_required");
		}
		
		if(variation.length() < 1 || variation.isEmpty()){
			
			result = I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "field_variation_required");
		}
		
		return result;
	}

}
