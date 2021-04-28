package bbr.b2b.portal.components.modules.fep;

import java.io.Serializable;
import java.util.function.Function;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeArrayResultDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeDataDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeInitParamDTO;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.FEPConstants;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;

public class CreateProduct extends BbrWindow implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long							serialVersionUID		= -2928184568999564047L;

	private BbrComboBox<ArticleTypeDataDTO>		cbx_Templates			= null;

	private BbrWork<ArticleTypeArrayResultDTO>	templatesWork			= null;

	private static final String						TEMPLATE_NAME			= "description";
	private final String									DEFAULT_SORT_FIELD	= TEMPLATE_NAME;
	private final int										DEFAULT_PAGE_NUMBER	= 1;
	private final int										MAX_ROWS_NUMBER		= 100;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public CreateProduct(BbrUI parent)
	{
		super(parent);
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
		// Sección Plantilla
		Label lbl_Template = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "lbl_template"));
		lbl_Template.setWidth("90px");

		this.cbx_Templates = this.getTemplatesComboBox();

		HorizontalLayout pnlTemplate = new HorizontalLayout(lbl_Template, this.cbx_Templates);
		pnlTemplate.setExpandRatio(this.cbx_Templates, 1F);
		pnlTemplate.setSizeFull();

		// Main Layout
		VerticalLayout optionsPanel = new VerticalLayout();
		optionsPanel.setWidth("100%");
		optionsPanel.setSpacing(true);
		optionsPanel.setMargin(false);
		optionsPanel.addComponents(pnlTemplate);

		// Accept Button
		Button btn_Select = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "next"));
		btn_Select.setStyleName("primary");
		btn_Select.addStyleName("btn-generic");
		btn_Select.setWidth("100%");
		btn_Select.addClickListener((ClickListener & Serializable) e -> btnSelect_clickHandler(e));
		btn_Select.setClickShortcut(KeyCode.ENTER);

		// Cancel Button
		Button btn_Cancel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "close"));
		btn_Cancel.setStyleName("primary");
		btn_Cancel.addStyleName("btn-generic");
		btn_Cancel.setWidth("100%");
		btn_Cancel.addClickListener((ClickListener & Serializable) e -> btnClose_clickHandler(e));

		HorizontalLayout buttonsPanel = new HorizontalLayout(btn_Select, btn_Cancel);
		buttonsPanel.addStyleName("bbr-buttons-panel");
		buttonsPanel.setWidth("300px");
		buttonsPanel.setSpacing(true);

		// Main Layout
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.addComponents(optionsPanel, buttonsPanel);
		mainLayout.setComponentAlignment(buttonsPanel, Alignment.BOTTOM_CENTER);
		mainLayout.setExpandRatio(buttonsPanel, 1F);
		mainLayout.setMargin(false);
		mainLayout.addStyleName("bbr-margin-windows");

		// Ventana
		this.setWidth(500, Unit.PIXELS);
		this.setHeight(180, Unit.PIXELS);
		this.setResizable(false);
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "add_product"));
		this.setContent(mainLayout);

		this.startWaiting();

		templatesWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		templatesWork.addFunction(new Function<Object, ArticleTypeArrayResultDTO>()
		{
			@Override
			public ArticleTypeArrayResultDTO apply(Object t)
			{
				return executeGetTemplatesService(CreateProduct.this.getBbrUIParent());
			}
		});

		this.executeBbrWork(this.templatesWork);
	}


	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		CreateProduct bbrSender = (CreateProduct) sender;
		
		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				bbrSender.doUpdateTemplates(result, sender);
			}
		}
		else
		{
			bbrSender.showErrorMessage(I18NManager.getI18NString(bbrSender.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
			I18NManager.getI18NString(bbrSender.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));
		}
		
		bbrSender.stopWaiting();
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================
	
	private void btnSelect_clickHandler(ClickEvent event)
	{
		String errorMsg = this.validateData();
		if (errorMsg == null)
		{
			ArticleTypeDataDTO template = this.cbx_Templates.getValue();

			BbrEvent bbrEvent = new BbrEvent(BbrEvent.ITEM_SELECTED);
			bbrEvent.setResultObject(template);

			dispatchBbrEvent(bbrEvent);
			this.close();
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), errorMsg);
		}
	}


	private void btnClose_clickHandler(ClickEvent event)
	{
		this.close();
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	
	private ArticleTypeArrayResultDTO executeGetTemplatesService(BbrUI bbrUI)
	{
		ArticleTypeArrayResultDTO result = null;

		Integer requestedPage = DEFAULT_PAGE_NUMBER;
		Integer maxRow = MAX_ROWS_NUMBER;

		ArticleTypeInitParamDTO initParam = new ArticleTypeInitParamDTO();
		initParam.setActive(true);
		initParam.setDescription(null);
		initParam.setClientname(FEPConstants.INTERNAL_CLIENT_NAME);
		initParam.setType(FEPConstants.ARTICLETYPE_CLASS_NAME_CP);

		OrderCriteriaDTO ordercriteria[] = new OrderCriteriaDTO[1];
		OrderCriteriaDTO order = new OrderCriteriaDTO();
		order.setPropertyname(DEFAULT_SORT_FIELD);
		order.setAscending(true);
		ordercriteria[0] = order;

		try
		{
			result = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(this.getBbrUIParent()).getArticleTypeData(initParam, requestedPage, maxRow, false, ordercriteria, this.getBbrUIParent().getLocale().getLanguage());
		}
		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut();
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

	
	private void doUpdateTemplates(Object result, BbrWorkExecutor sender)
	{
		CreateProduct senderReport = (CreateProduct) sender;

		if (result != null)
		{
			ArticleTypeArrayResultDTO serviceResult = (ArticleTypeArrayResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(serviceResult, this.getBbrUIParent(), true);

			if (!error.hasError())
			{
				senderReport.updateComboBoxData(serviceResult);
			}
		}

		senderReport.stopWaiting();
	}
	

	private String validateData()
	{
		String result = null;
		
		if(this.cbx_Templates.getValue() == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "templates_requires");
		}

		return result;
	}


	private BbrComboBox<ArticleTypeDataDTO> getTemplatesComboBox()
	{
		BbrComboBox<ArticleTypeDataDTO> result = null;

		if (this.getBbrUIParent().getUser() != null)
		{
			result = new BbrComboBox<ArticleTypeDataDTO>();
			result.setItemCaptionGenerator(ArticleTypeDataDTO::getDescription);
			result.setTextInputAllowed(false);
			result.setEmptySelectionAllowed(false);
			result.setWidth("100%");
		}
		
		return result;
	}
	
	
	private void updateComboBoxData(ArticleTypeArrayResultDTO templatesData)
	{
		if ((templatesData != null) && (templatesData.getValues() != null) && (templatesData.getValues().length > 0))
		{
			this.cbx_Templates.setItems(templatesData.getValues());
			this.cbx_Templates.selectFirstItem();
			this.cbx_Templates.addStyleName("bbr-filter-fields");
		}
		else
		{
			ArticleTypeDataDTO emptyResult = new ArticleTypeDataDTO();
			emptyResult.setDescription(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "no_template_assigned"));

			this.cbx_Templates.setItems(emptyResult);
			this.cbx_Templates.setSelectedItem(emptyResult);
			this.cbx_Templates.setEnabled(false);
		}
	}
	
	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
