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
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.fep.common.data.classes.ErrorInfoArrayResultDTO;
import bbr.b2b.fep.common.data.classes.UserTypeDTO;
import bbr.b2b.fep.cp.data.classes.CPItemDTO;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.fep.FepUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrVSeparator;
import cl.bbr.core.components.basics.BbrWindow;

public class RejectProductItemRequest extends BbrWindow implements BbrWorkExecutor
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private static final long						serialVersionUID	= -2928184568999564047L;

	private CPItemDTO[]								selectedItems		= null;
	private UserTypeDTO								selectedUserRole	= null;
	private TextArea 									txtA_Comment		= null;
	private BbrWork<ErrorInfoArrayResultDTO>	rejectRequestWork	= null;
	private Boolean									isFinalRejection	= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public RejectProductItemRequest(BbrUI parent, CPItemDTO[] selectedItems, UserTypeDTO selectedUserRole, Boolean isFinalRejection)
	{
		super(parent);
		this.selectedItems = selectedItems;
		this.isFinalRejection = isFinalRejection;
		this.selectedUserRole = selectedUserRole;
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
		// Main Layout
		
		Label lblPrompt = new Label(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "rejection_reason"));
		
		this.txtA_Comment = new TextArea();
		this.txtA_Comment.setHeight(100, Unit.PERCENTAGE);
		this.txtA_Comment.setWidth(100, Unit.PERCENTAGE);
		
		VerticalLayout rejectionPanel = new VerticalLayout();
		rejectionPanel.setWidth("100%");
		rejectionPanel.setSpacing(true);
		rejectionPanel.setMargin(false);
		rejectionPanel.addComponents(lblPrompt, new BbrVSeparator("10px"), this.txtA_Comment);

		// Accept Button
		Button btn_Select = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "accept"));
		btn_Select.setStyleName("primary");
		btn_Select.addStyleName("btn-generic");
		btn_Select.setWidth("100%");
		btn_Select.addClickListener((ClickListener & Serializable) e -> btnAccept_clickHandler(e));
		btn_Select.setClickShortcut(KeyCode.ENTER);

		// Cancel Button
		Button btn_Cancel = new Button(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"));
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
		mainLayout.addComponents(rejectionPanel, buttonsPanel);
		mainLayout.setComponentAlignment(buttonsPanel, Alignment.BOTTOM_CENTER);
		mainLayout.setExpandRatio(buttonsPanel, 1F);
		mainLayout.setMargin(false);
		mainLayout.addStyleName("bbr-margin-windows");

		// Ventana
		this.setWidth(550, Unit.PIXELS);
		this.setHeight(300, Unit.PIXELS);
		this.setResizable(false);
		this.setCaption(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "add_product"));
		this.setContent(mainLayout);

		this.rejectRequestWork = new BbrWork<>(this, this.getBbrUIParent(), this);
		this.rejectRequestWork.addFunction(new Function<Object, ErrorInfoArrayResultDTO>()
		{
			@Override
			public ErrorInfoArrayResultDTO apply(Object t)
			{
				return executeRejectionService(RejectProductItemRequest.this.getBbrUIParent());
			}
		});
	}


	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		RejectProductItemRequest bbrSender = (RejectProductItemRequest) sender;

		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				bbrSender.doProcessRejection((ErrorInfoArrayResultDTO)result, sender);
			}
		}
		else
		{
			bbrSender.showErrorMessage(I18NManager.getI18NString(bbrSender.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"), I18NManager.getI18NString(bbrSender.getBbrUIParent(), BbrUtilsResources.RES_GENERIC,
			"unsuccessful_operation"));
		}

		bbrSender.stopWaiting();
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================


	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	private void btnAccept_clickHandler(ClickEvent event)
	{
		if((this.txtA_Comment.getValue() != null) && (this.txtA_Comment.getValue().trim().length() > 0))
		{
			this.startWaiting();
			this.executeBbrWork(this.rejectRequestWork);
		}
		else
		{
			this.showWarningMessage(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "windows_title_warning"), 
											I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_FEP, "rejection_comment_required"));
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

	private ErrorInfoArrayResultDTO executeRejectionService(BbrUI bbrUI)
	{
		ErrorInfoArrayResultDTO result = null;
		
		try
		{
			result = EJBFactory.getFEPEJBFactory().getFEPCreateProductManagerLocal(bbrUI).rejectItems(this.selectedUserRole.getId(), selectedItems, FepUtils.createPersonDTO(this.getUser()), this.txtA_Comment.getValue().trim(), this.isFinalRejection);
		}
		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), this.getBbrUIParent());
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


	private void doProcessRejection(ErrorInfoArrayResultDTO result, BbrWorkExecutor sender)
	{
		RejectProductItemRequest senderReport = (RejectProductItemRequest) sender;

		if (senderReport != null)
		{
			if (result != null)
			{
				BbrError error = ErrorsMngr.getInstance().getError(result, senderReport.getBbrUIParent(), false);

				if (!error.hasError())
				{
					senderReport.showInfoMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_info"),
														  I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "successful_operation"));
					
					this.dispatchBbrEvent(new BbrEvent(BbrEvent.ITEMS_UPDATED));
					this.close();
				}
				else
				{
					senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
					result.getStatusmessage());
				}
			}
			else
			{
				senderReport.showErrorMessage(I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "windows_title_error"),
														I18NManager.getI18NString(senderReport.getBbrUIParent(), BbrUtilsResources.RES_SYSTEM, "P200"));
			}
		}
		
		senderReport.stopWaiting();
	}
	

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
