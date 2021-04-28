package bbr.b2b.portal.components.utils.customer;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.customer.report.classes.AssociateProductProviderProductClientInitParamDTO;
import bbr.b2b.customer.report.classes.NotLoadedProductDetailDTO;
import bbr.b2b.customer.report.classes.ProductProviderArrayResultDTO;
import bbr.b2b.customer.report.classes.ProductProviderDTO;
import bbr.b2b.customer.report.classes.SearchProductProviderInitParamDTO;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.BbrMessagesBoxUtils;
import bbr.b2b.portal.classes.utils.app.SearchFilterUtils;
import bbr.b2b.portal.classes.wrappers.customer.ControlPanelCardInfo;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.modules.customer.ControlPanelManagement;
import cl.bbr.core.classes.basics.SearchCriterion;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.grid.BbrAdvancedGrid;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class FindProduct extends BbrWindow implements BbrWorkExecutor
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	// Constants
	private static final long						serialVersionUID			= 3247626779164695209L;
	public static final String						ACCEPTED_CONFIRMATION_EVENT	= "accepted_confirmation_event";
	// Components
	private BbrComboBox<SearchCriterion>			cbx_Criteria				= null;
	private BbrTextField							txt_Value					= null;
	private FormLayout								fieldsForm					= null;
	private BbrAdvancedGrid<ProductProviderDTO>		dgd_Product					= null;
	private Button									btn_Select					= null;
	// Variables
	private BbrWork<ProductProviderArrayResultDTO>	searchProductWork			= null;
	private String									pvcode						= null;
	private ControlPanelCardInfo					cardInfo					= null;
	private NotLoadedProductDetailDTO				productSelected				= null;
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public FindProduct(BbrUI parent, String pvcode, ControlPanelCardInfo cardInfo, NotLoadedProductDetailDTO productSelected)
	{
		super(parent);
		this.pvcode = pvcode;
		this.cardInfo = cardInfo;
		this.productSelected = productSelected;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	public void initializeView()
	{
		this.cbx_Criteria = this.getSearchCriterionComboBox();

		this.txt_Value = new BbrTextField(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "value"));
		this.txt_Value.setMaxLength(50);
		this.txt_Value.setWidth("100%");
		this.txt_Value.addStyleName("bbr-filter-fields");

		Button btn_Search = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "search"), VaadinIcons.SEARCH);
		btn_Search.setStyleName("btn-filter-search");
		btn_Search.setClickShortcut(KeyCode.ENTER);
		btn_Search.addClickListener((ClickListener & Serializable) e -> search_clickHandler(e));

		// Form Component
		this.fieldsForm = new FormLayout();
		this.fieldsForm.addComponents(cbx_Criteria, txt_Value, btn_Search);

		this.dgd_Product = new BbrAdvancedGrid<>(this.getUser().getLocale());
		this.dgd_Product.addStyleName("report-grid");
		this.dgd_Product.setWidth("100%");
		this.dgd_Product.addSelectionListener(e -> toggleSelectBtn());
		this.initializeGridColumns();

		// Buttons Layout
		this.btn_Select = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "select"));
		this.btn_Select.addClickListener((ClickListener & Serializable) e -> select_clickHandler(e));
		this.btn_Select.setStyleName("primary");
		this.btn_Select.addStyleName("btn-generic");
		this.btn_Select.setWidth("140px");
		this.btn_Select.setEnabled(false);

		Button btn_Cancel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"));
		btn_Cancel.addClickListener(e -> close());
		btn_Cancel.setStyleName("primary");
		btn_Cancel.addStyleName("btn-generic");
		btn_Cancel.setWidth("140px");

		HorizontalLayout buttonsLayout = new HorizontalLayout(btn_Select, btn_Cancel);
		buttonsLayout.setWidth(100, Unit.PERCENTAGE);
		buttonsLayout.setComponentAlignment(btn_Select, Alignment.MIDDLE_LEFT);
		buttonsLayout.setComponentAlignment(btn_Cancel, Alignment.MIDDLE_RIGHT);
		buttonsLayout.addStyleName("buttons-layout");

		VerticalLayout gridLayout = new VerticalLayout();
		gridLayout.addComponents(dgd_Product, buttonsLayout);
		gridLayout.setExpandRatio(dgd_Product, 1F);
		gridLayout.setSpacing(true);
		gridLayout.setSizeFull();
		gridLayout.setMargin(false);

		// Vertical Layout for Components
		VerticalLayout mainLayout = new VerticalLayout(fieldsForm, gridLayout);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(gridLayout, 1F);
		mainLayout.addStyleName("form-vertical-container");
		mainLayout.setMargin(false);

		// Main Windows
		this.setWidth("450px");
		this.setHeight("480px");
		this.setResizable(false);
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "find_product"));
		this.setContent(mainLayout);

		this.txt_Value.focus();

	}

	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		FindProduct senderReport = (FindProduct) sender;
		if (result != null)
		{
			if (result instanceof ProductProviderArrayResultDTO)
			{
				senderReport.doUpdateReport(result, senderReport);
			}
		}
		else
		{
			if (!senderReport.getBbrUIParent().hasAlertWindowOpen())
			{
				senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));
			}

			senderReport.stopWaiting();
		}
	}

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************
	private void toggleSelectBtn()
	{
		boolean isSelectEnabled = (this.dgd_Product.getSelectedItems() != null && this.dgd_Product.getSelectedItems().size() > 0);

		this.btn_Select.setEnabled(isSelectEnabled);
	}

	private void select_clickHandler(ClickEvent e)
	{
		if (dgd_Product.getSelectedItems() != null && dgd_Product.getSelectedItems().size() > 0)
		{
			ProductProviderDTO company = (ProductProviderDTO) dgd_Product.getSelectedItems().iterator().next();
			this.selectCompany(company);
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "company_required"));
		}
	}

	private void search_clickHandler(ClickEvent e)
	{
		String errorMessage = validateData();
		if (errorMessage == null || errorMessage.length() <= 0)
		{
			try
			{
				this.doInitializeExecuteServiceAssociateProduct(this.cardInfo);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMessage);
		}
	}
	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private void selectCompany(ProductProviderDTO product)
	{
		if (product != null)
		{
			BbrMessagesBoxUtils.showConfirmationWarningMessage(this.getBbrUIParent(),
					(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning")),
					I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "homolog_product_message",
							this.validateText(this.productSelected.getCodprodclient()) + " " + this.validateText(this.productSelected.getDescprodclient()),
							this.validateText(product.getCode()) + " " + this.validateText(product.getDescription())),
					() -> this.acceptConfirmation_Handler(product));
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"),
					I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "company_required"));
		}
	}

	private void acceptConfirmation_Handler(ProductProviderDTO product)
	{
		BbrEvent event = new BbrEvent(ACCEPTED_CONFIRMATION_EVENT);
		AssociateProductProviderProductClientInitParamDTO initParam = this.getInitParamAssociateProduct(this.getBbrUIParent(), product);
		event.setResultObject(initParam);
		dispatchBbrEvent(event);
		this.close();
	}

	private String validateText(String text)
	{
		return text != null ? text : "";
	}

	private void initializeGridColumns()
	{
		this.dgd_Product.addColumn(ProductProviderDTO::getCode)
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "cmp_enterprise_code"));
		this.dgd_Product.addColumn(ProductProviderDTO::getDescription)
				.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "cmp_social_reason"));
	}

	private BbrComboBox<SearchCriterion> getSearchCriterionComboBox()
	{
		BbrComboBox<SearchCriterion> result = null;
		SearchCriterion[] searcCriterions = SearchFilterUtils.getInstance().getSearchCompanyCriteria();

		result = new BbrComboBox<SearchCriterion>(searcCriterions);

		result.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "criteria"));
		result.setItemCaptionGenerator(SearchCriterion::getDescription);
		result.setTextInputAllowed(false);
		result.setEmptySelectionAllowed(false);
		result.selectFirstItem();

		result.addStyleName("bbr-filter-fields");

		result.setTextInputAllowed(false);

		result.addValueChangeListener(new ValueChangeListener<SearchCriterion>()
		{
			private static final long serialVersionUID = -885062614408629961L;

			@Override
			public void valueChange(ValueChangeEvent<SearchCriterion> event)
			{
				if (cbx_Criteria.getSelectedValue() != null)
				{
					txt_Value.setValue("");
					txt_Value.focus();
				}
			}
		});
		result.setWidth("100%");

		return result;
	}

	private String validateData()
	{
		String result = "";
		String value = txt_Value.getValue().trim();
		if (value.length() < 3)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "field_required");
		}
		else if (value.length() > 255)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_SEARCH, "usr_invalid_search");
		}

		return result;
	}

	private void doInitializeExecuteServiceAssociateProduct(ControlPanelCardInfo cardInfo)
	{
		if (cardInfo.getSelectedPanel().equals(ControlPanelManagement.NOT_LOADED_PRODUCT))
		{
			searchProductWork = new BbrWork<>(this, this.getBbrUIParent(), this, true);
			searchProductWork.addFunction(new Function<Object, ProductProviderArrayResultDTO>()
			{
				@Override
				public ProductProviderArrayResultDTO apply(Object t)
				{
					return executeServiceAssociateProduct(FindProduct.this.getBbrUIParent(), cardInfo);
				}
			});

			this.startWaiting();
			this.executeBbrWork(searchProductWork);
		}
	}

	private ProductProviderArrayResultDTO executeServiceAssociateProduct(BbrUI bbrUIParent, ControlPanelCardInfo cardInfo)
	{
		ProductProviderArrayResultDTO result = null;

		try
		{
			SearchProductProviderInitParamDTO initParamDTO = getInitParam(bbrUIParent, cardInfo);
			result = EJBFactory.getCustomerEJBFactory().getCustomerManagerLocal(bbrUIParent).searchProviderProduct(initParamDTO);
		}
		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), bbrUIParent);
		}
		catch (BbrSystemException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

	private SearchProductProviderInitParamDTO getInitParam(BbrUI bbrUIParent, ControlPanelCardInfo cardInfo)
	{
		SearchProductProviderInitParamDTO result = new SearchProductProviderInitParamDTO();
		result.setLocale(this.getBbrUIParent().getUser().getLocale());
		result.setPvcode(this.pvcode);
		result.setClkey(cardInfo.getClientId());
		boolean isCodeOrDescription = cbx_Criteria.getSelectedValue().getId().equals(1);
		result.setCode(isCodeOrDescription);
		result.setSearch(this.txt_Value.getValue() != null ? this.txt_Value.getValue().toUpperCase() : "");
		return result;
	}

	private AssociateProductProviderProductClientInitParamDTO getInitParamAssociateProduct(BbrUI bbrUIParent, ProductProviderDTO product)
	{
		AssociateProductProviderProductClientInitParamDTO result = new AssociateProductProviderProductClientInitParamDTO();
		result.setLocale(this.getUser().getLocale());
		result.setPvkey(product.getPvkey());// respuesta
		result.setPrkey(product.getPrkey());// respuesta
		result.setClkey(cardInfo.getClientId());// ya lo tengo
		result.setConversion(1);
		result.setPccodigo(this.productSelected.getCodprodclient());// antes del
																	// buscadoir
		return result;
	}

	private void doUpdateReport(Object result, FindProduct senderReport)
	{
		if (result != null)
		{
			ProductProviderArrayResultDTO reportResult = (ProductProviderArrayResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				ProductProviderDTO[] provider = reportResult.getProductProviderDTOs();
				senderReport.setDataProvider(senderReport, Arrays.asList(provider));
			}
		}
		senderReport.stopWaiting();
	}

	private void setDataProvider(FindProduct senderReport, List<ProductProviderDTO> provider)
	{
		ListDataProvider<ProductProviderDTO> dataprovider = new ListDataProvider<>(provider);
		senderReport.dgd_Product.setDataProvider(dataprovider);
	}

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
