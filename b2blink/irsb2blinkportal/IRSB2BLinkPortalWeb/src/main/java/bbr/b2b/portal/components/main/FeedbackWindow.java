package bbr.b2b.portal.components.main;

import java.io.Serializable;
import java.util.function.Function;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.portal.classes.basics.BbrOptionalCommentAlertDTO;
import bbr.b2b.portal.classes.beans.SessionBean;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.ui.MainUI;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.BbrMessagesBoxUtils;
import bbr.b2b.portal.components.basics.BbrOptionalCommentAlert;
import bbr.b2b.portal.components.basics.BbrOptionalCommentAlert.BbrOptionalCommentAlertBuilder;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsConstants;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.wrapper.FeedbackItemDTO;
import bbr.b2b.users.report.classes.UserDTO;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.work.BbrWork;
import cl.bbr.core.classes.work.BbrWorkExecutor;
import cl.bbr.core.components.basics.BbrUI;

public class FeedbackWindow implements BbrWorkExecutor
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants
	public static final String		LEFT_FIXED_BUTTON_STYLE	= "left-fixed-button";
	// Components
	private BbrOptionalCommentAlert	addUserRequestCtrl		= null;
	private BbrUI					bbrUI					= null;
	// Variables
	private String					moduleName				= null;
	private BbrWork<BaseResultDTO>	feedbackWork			= null;
	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================
	public FeedbackWindow(MainUI mainUI)
	{
		this.bbrUI = (BbrUI) mainUI;
		this.moduleName = getModuleName(mainUI);
		this.initializeFeedbackWindow();
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================
	@Override
	public void postExecuteWork(Object result, BbrWorkExecutor sender, Object triggerObject)
	{
		if (result != null)
		{
			if (triggerObject instanceof BbrWorkExecutor)
			{
				this.doUpdateView(result, sender);
			}
		}
		else
		{
			FeedbackWindow senderReport = (FeedbackWindow) sender;

			if (!senderReport.bbrUI.hasAlertWindowOpen())
				senderReport.bbrUI.showErrorMessage(I18NManager.getI18NString(senderReport.bbrUI, BbrUtilsResources.RES_GENERIC, "windows_title_error"),
						I18NManager.getI18NString(senderReport.bbrUI, BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));

		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================
	private void executeFeedbackWork_Handler(BbrUI bbrUI, FeedbackItemDTO feedbackItem)
	{
		feedbackWork = new BbrWork<>(this, this.bbrUI, this);
		feedbackWork.addFunction(new Function<Object, BaseResultDTO>()
		{
			@Override
			public BaseResultDTO apply(Object t)
			{
				return executeFeedBackService(FeedbackWindow.this.bbrUI, feedbackItem);
			}
		});

		this.executeBbrWork(feedbackWork);
		this.bbrUI.startWaiting();
	}

	private void confirmFeedbackMessage_EventHandler(BbrUI bbrUI, String moduleName, BbrEvent e)
	{
		if (BbrOptionalCommentAlert.BTN_ACCEPT.equals(e.getEventType()))
		{
			BbrOptionalCommentAlertDTO feedbackResult = (BbrOptionalCommentAlertDTO) e.getResultObject();
			// se inicializa el item
			FeedbackItemDTO feedbackItem = (FeedbackItemDTO) feedbackResult.getData();
			// se inicializa el comentario
			feedbackItem.setComment(feedbackResult.getComment());

			BbrMessagesBoxUtils.showConfirmationMessage(bbrUI,
					I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_GENERIC, "windows_title_question"),
					I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_GENERIC, "confirm_your_opinion",
							moduleName,
							feedbackItem.getComment()),
					() -> this.executeFeedbackWork_Handler(bbrUI, feedbackItem));
		}
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private String getModuleName(MainUI mainUI)
	{
		return mainUI.getSelectedFuncionalityNameFromUI();
	}

	private void initializeFeedbackWindow()
	{
		System.out.println("Su opinión del modulo " + moduleName);

		addUserRequestCtrl = new BbrOptionalCommentAlertBuilder(bbrUI)
				.withTitle(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_GENERIC, "your_opinion_title"))
				.withMessage(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_GENERIC, "your_opinion_message", moduleName))
				.withPrimaryButtonCaption(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_GENERIC, "send"))
				.withCommentPlaceholder(I18NManager.getI18NString(bbrUI, BbrUtilsResources.RES_GENERIC, "add_comment_mandatory"))
				.withCommentHeight("80px")
				.withIsShowWishContinue(false)
				.withStyleMainLayout("bbr-margin-windows-10")
				.withHeightWindow("310px")
				.withWidthWindow("420px")
				.withMaxLengthTextArea(500)
				.withIsValidateAccept(true)
				.withData((Object) new FeedbackItemDTO(moduleName, bbrUI.getUser().getEnterpriseDescription(), bbrUI.getUser().getEnterpriseCode()))
				.build();
		addUserRequestCtrl.addBbrEventListener((BbrEventListener & Serializable) e -> this.confirmFeedbackMessage_EventHandler(bbrUI, moduleName, e));
		addUserRequestCtrl.initializeView();
		addUserRequestCtrl.open(true);

	}

	private BaseResultDTO executeFeedBackService(BbrUI bbrUI, FeedbackItemDTO feedbackItem)
	{
		BaseResultDTO result = null;

		SessionBean sessionBean = (SessionBean) bbrUI.getSessionBean(BbrUtilsConstants.SESSION_BEAN_NAME);
		UserDTO userSession = sessionBean.getUser();
		try
		{
			result = EJBFactory.getUserEJBFactory().getFeedbackManagerLocal(bbrUI).sendFeedback(userSession, feedbackItem);
		}
		catch (BbrUserException ex)
		{
			AppUtils.getInstance().doLogOut(ex.getMessage(), bbrUI);
		}
		catch (BbrSystemException ex)
		{
			ex.printStackTrace();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return result;
	}

	private void doUpdateView(Object result, BbrWorkExecutor sender)
	{
		FeedbackWindow senderReport = (FeedbackWindow) sender;

		if (result != null)
		{
			BaseResultDTO salesReportResult = (BaseResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(salesReportResult, senderReport.bbrUI, !senderReport.bbrUI.hasAlertWindowOpen());

			if (!error.hasError())
			{
				this.closeFeedbackWindow();
				senderReport.bbrUI.showInfoMessage(I18NManager.getI18NString(senderReport.bbrUI, BbrUtilsResources.RES_GENERIC, "windows_title_info"),
						I18NManager.getI18NString(senderReport.bbrUI, BbrUtilsResources.RES_GENERIC, "successful_operation_your_opinion"),
						null,
						() ->
						{
							this.bbrUI.stopWaiting();
						});
			}
		}
	}

	private void closeFeedbackWindow()
	{
		addUserRequestCtrl.close();
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
