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

import bbr.b2b.common.adtclasses.classes.BaseInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPItemStateArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemStateDTO;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.InitParamsUtil;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrMessageBox;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;

public class UpdateProductState extends BbrWindow implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long							serialVersionUID	= -2928184568999564047L;

	private BbrComboBox<CPItemStateDTO>				cbx_States			= null;

	private BbrWork<CPItemStateArrayResultDTO>	templatesWork		= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public UpdateProductState(BbrUI parent)
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
		Label lbl_State = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "select_state"));
		lbl_State.setWidth("90px");

		this.cbx_States = this.getStatesComboBox();

		HorizontalLayout pnlState = new HorizontalLayout(lbl_State, this.cbx_States);
		pnlState.setExpandRatio(this.cbx_States, 1F);
		pnlState.setSizeFull();

		// Main Layout
		VerticalLayout optionsPanel = new VerticalLayout();
		optionsPanel.setWidth("100%");
		optionsPanel.setSpacing(true);
		optionsPanel.setMargin(false);
		optionsPanel.addComponents(pnlState);

		// Accept Button
		Button btn_Select = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "accept"));
		btn_Select.setStyleName("primary");
		btn_Select.addStyleName("btn-generic");
		btn_Select.setWidth("100%");
		btn_Select.addClickListener((ClickListener & Serializable) e -> btnSelect_clickHandler());
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
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "update_product_state"));
		this.setContent(mainLayout);

		this.startWaiting();

		templatesWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		templatesWork.addFunction(new Function<Object, CPItemStateArrayResultDTO>()
		{
			@Override
			public CPItemStateArrayResultDTO apply(Object t)
			{
				return executeGetStatesService(UpdateProductState.this.getBbrUIParent());
			}
		});

		this.executeBbrWork(this.templatesWork);
	}


	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		UpdateProductState bbrSender = (UpdateProductState) sender;

		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				bbrSender.doUpdateStates(result, sender);
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

	private void btnSelect_clickHandler()
	{
		BbrMessageBox.createQuestion(this.getBbrUIParent())
		.withYesButton((Runnable & Serializable) () -> this.handleChangeStateYes())
		.withNoButton()
		.withCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"))
		.withHtmlMessage(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_MODULES_FEP, "ask_change_request_state"))
		.open();
	}

	
	private void handleChangeStateYes()
	{
		String errorMsg = this.validateData();
		
		if (errorMsg == null)
		{
			CPItemStateDTO state = this.cbx_States.getValue();
			
			BbrEvent bbrEvent = new BbrEvent(BbrEvent.ITEM_UPDATED);
			bbrEvent.setResultObject(state);
			
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

	private CPItemStateArrayResultDTO executeGetStatesService(BbrUI bbrUI)
	{
		CPItemStateArrayResultDTO result = null;

		try
		{
			result = EJBFactory.getFEPEJBFactory()
			.getFEPCreateProductManagerLocal(this.getBbrUIParent())
			.getItemStates(InitParamsUtil.getBaseInitParamInstance(BaseInitParamDTO.class, this.getBbrUIParent()));
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


	private void doUpdateStates(Object result, BbrWorkExecutor sender)
	{
		UpdateProductState senderReport = (UpdateProductState) sender;

		if (result != null)
		{
			CPItemStateArrayResultDTO serviceResult = (CPItemStateArrayResultDTO) result;

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

		if (this.cbx_States.getValue() == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "templates_requires");
		}

		return result;
	}


	private BbrComboBox<CPItemStateDTO> getStatesComboBox()
	{
		BbrComboBox<CPItemStateDTO> result = null;

		if (this.getBbrUIParent().getUser() != null)
		{
			result = new BbrComboBox<CPItemStateDTO>();
			result.setItemCaptionGenerator(CPItemStateDTO::getName);
			result.setTextInputAllowed(false);
			result.setEmptySelectionAllowed(false);
			result.setWidth("100%");
		}

		return result;
	}


	private void updateComboBoxData(CPItemStateArrayResultDTO templatesData)
	{
		if ((templatesData != null) && (templatesData.getValues() != null) && (templatesData.getValues().length > 0))
		{
			this.cbx_States.setItems(templatesData.getValues());
			this.cbx_States.selectFirstItem();
			this.cbx_States.addStyleName("bbr-filter-fields");
		}
		else
		{
			CPItemStateDTO emptyResult = new CPItemStateDTO();
			emptyResult.setName(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "no_states_assigned"));

			this.cbx_States.setItems(emptyResult);
			this.cbx_States.setSelectedItem(emptyResult);
			this.cbx_States.setEnabled(false);
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
