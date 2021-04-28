package bbr.b2b.portal.classes.wrappers.management;

import java.util.Arrays;
import java.util.List;

import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.components.basics.BbrInfoMessageList;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.PositionDTO;
import bbr.b2b.users.report.classes.PositionResultDTO;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.classes.utilities.BbrUtils;
import cl.bbr.core.components.basics.BbrMessageBox;
import cl.bbr.core.components.basics.BbrUI;

public class ProviderContactOperationInfo
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants
	private String		missingContactsWithObligatoryPositions	= "U1601";
	private String		contactsUnconfirmed				= "U1700";
	private static String	defaultWidth					= "100px";
	// Components
	private BbrUI			parentUI					= null;
	private BbrInfoMessageList	errorList					= null;
	// Variables
	private PositionDTO[]	obligatoryPositions				= null;

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================
	public ProviderContactOperationInfo(BbrUI parentUI)
	{
		this.parentUI = parentUI;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public boolean showPendingContactsPositions(boolean showErrorListMessage)
	{
		return this.showPendingContactsPositions(null, null, showErrorListMessage);
	}

	public boolean showPendingContactsPositions(Runnable missingContactsAction, Runnable contactsUnconfirmedAction, boolean showErrorListMessage)
	{
		boolean result = false;
		PositionResultDTO obligatoryPostionResult = null;
		obligatoryPostionResult = executeService(this.parentUI);

		if (obligatoryPostionResult != null)
		{
			BbrError error = ErrorsMngr.getInstance().getError(obligatoryPostionResult, this.parentUI, false);
			// RECORDAR QUE LA CONDICION ES SOLO error.hasError()
			if (error.hasError())
			{
				if (error.getErrorCode().equals(this.missingContactsWithObligatoryPositions))
				{
					result = true;
					if (showErrorListMessage)
					{
						String firstMessage = I18NManager.getI18NString(this.parentUI, BbrUtilsResources.RES_SYSTEM, missingContactsWithObligatoryPositions);
						String secondMessage = I18NManager.getI18NString(this.parentUI, BbrUtilsResources.RES_MODULES_MANAGEMENT,
						missingContactsWithObligatoryPositions + "_2_following_options");
						String thirdMessage = I18NManager.getI18NString(this.parentUI, BbrUtilsResources.RES_MODULES_MANAGEMENT,
						missingContactsWithObligatoryPositions + "_3_following_options");
						String fourdMessage = I18NManager.getI18NString(this.parentUI, BbrUtilsResources.RES_MODULES_MANAGEMENT,
						missingContactsWithObligatoryPositions + "_4_following_options");
						this.errorList = new BbrInfoMessageList(this.parentUI, obligatoryPostionResult.getPositionDTOs(), firstMessage, secondMessage,
						thirdMessage, fourdMessage, (Runnable) missingContactsAction != null ? missingContactsAction : () -> this.doNothing());
						this.errorList.initializeView();
						this.errorList.open(true);
					}
				}
				else if (error.getErrorCode().equals(this.contactsUnconfirmed))
				{
					result = true;

					String validationMessage = I18NManager.getI18NString(this.parentUI, BbrUtilsResources.RES_SYSTEM, contactsUnconfirmed);
					if (!this.parentUI.hasAlertWindowOpen())
					{
						this.showAcceptMessageBox(this.parentUI, I18NManager.getI18NString(this.parentUI, BbrUtilsResources.RES_GENERIC, "windows_title_info"),
						validationMessage, (Runnable) contactsUnconfirmedAction != null ? contactsUnconfirmedAction : () -> this.doNothing());
					}
				}
				else
				{
					String errordescription = BbrUtils.getInstance().substitute("{0} - {1} - Internal Error", error.getErrorCode(), error.getErrorMessage());
					this.parentUI.showErrorMessage(I18NManager.getI18NString(this.parentUI, BbrUtilsResources.RES_GENERIC, "windows_title_error"), errordescription);
				}

			}
		}
		return result;
	}

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
	private void doNothing()
	{
	}

	private PositionResultDTO executeService(BbrUI parentUI)
	{
		PositionResultDTO obligatoryPostionResult = null;
		try
		{
			obligatoryPostionResult = EJBFactory.getUserEJBFactory().getContactB2BReportManagerLocal(parentUI)
			.validateContactProviders(parentUI.getUser().getEnterpriseId(), parentUI.getUser().getId());
		}
		catch (BbrUserException e)
		{
			AppUtils.getInstance().doLogOut(e.getMessage(), parentUI);
		}
		catch (BbrSystemException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return obligatoryPostionResult;
	}

	private void showAcceptMessageBox(BbrUI bbrUI, String caption, String message, Runnable listener)
	{
		BbrMessageBox.createInfo(bbrUI).withOkButton(listener).withCaption(caption).withHtmlMessage(message).withWidthForAllButtons(defaultWidth).open();
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
	public List<PositionDTO> getObligatoryPositionsList()
	{
		PositionResultDTO obligatoryPostionResult = executeService(this.parentUI);
		BbrError error = ErrorsMngr.getInstance().getError(obligatoryPostionResult, this.parentUI, false);
		if (error.hasError())
		{
			if (error.getErrorCode().equals(this.missingContactsWithObligatoryPositions))
			{
				this.obligatoryPositions = obligatoryPostionResult.getPositionDTOs() != null ? obligatoryPostionResult.getPositionDTOs() : new PositionDTO[0];
			}
		}

		return obligatoryPositions != null ? Arrays.asList(obligatoryPositions) : null;
	}
}
