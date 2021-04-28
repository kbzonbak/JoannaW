package bbr.b2b.portal.components.popups;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.ui.MainUI;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.app.ImagesBufferedUtils;
import bbr.b2b.portal.classes.utils.app.SelectedPopupUtils;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.AuditPopUpInitParamDTO;
import bbr.b2b.users.report.classes.PopUpReportDataDTO;
import cl.bbr.core.classes.errors.BbrError;
import cl.bbr.core.classes.errors.ErrorsMngr;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;

public class WinPopup extends BbrWindow
{

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long	serialVersionUID	= -2720489231076965593L;

	private PopUpReportDataDTO	currentPopup		= null;

	private SelectedPopupUtils	selectedPopupUtils	= new SelectedPopupUtils();
	private boolean				isAcceptClose		= false;
	private boolean				isModuleBloqued		= false;
	private String				codeModuleSelected	= null;
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	public void setIsModuleBloqued(boolean isModuleBloqued)
	{
		this.isModuleBloqued = isModuleBloqued;
	}

	public void setCodeModuleSelected(String codeModuleSelected)
	{
		this.codeModuleSelected = codeModuleSelected;
	}

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public WinPopup(BbrUI parent, PopUpReportDataDTO currentPopup)
	{
		super(parent);
		this.currentPopup = currentPopup;
	}

	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> INTERFACE IMPLEMENTATIONS
	// ****************************************************************************************

	// ****************************************************************************************
	// ENDING SECTION ----> INTERFACE IMPLEMENTATIONS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	@Override
	public void initializeView()
	{
		if (currentPopup != null)
		{
			this.selectedPopupUtils.setNotification(this.currentPopup);
			PopupSlidePanel popupPanel = new PopupSlidePanel(this.getBbrUIParent(), currentPopup, () -> handlerAcceptEvent());
			popupPanel.setHeight("110%");
			popupPanel.setWidth("100%");
			// Main Layout
			VerticalLayout mainLayout = new VerticalLayout();
			mainLayout.addComponent(popupPanel);
			mainLayout.setMargin(false);
			mainLayout.setSizeFull();

			if (this.selectedPopupUtils.isPopupTemplateImage())
			{
				String windowCaption = (this.currentPopup.getTitle() != null) ? this.currentPopup.getTitle()
						: I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "win_popup");
				this.setCaption(windowCaption);
			}
			else
			{
				this.setCaption(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "win_popup"));
			}

			String width = "";
			String height = "";
			if (this.selectedPopupUtils.isPopupTemplateImage())
			{
				if (this.selectedPopupUtils.isValidHtmlPathForImage())
				{
					int marginHeight = 33;
					int marginWidth = 25;
					int checkAcceptSpace = 44;
					int minWidth = 400;
					String pixelUnit = "px";

					BufferedImage bImage = ImagesBufferedUtils.getBufferedImageFromFile(this.currentPopup.getHtmlcontent());
					width = (Integer.valueOf(bImage.getWidth()) < minWidth) ? minWidth + pixelUnit : (Integer.valueOf(bImage.getWidth()) + marginWidth + pixelUnit);
					if (this.selectedPopupUtils.isPopupTypeInf())
					{
						height = Integer.valueOf(bImage.getHeight()) + marginHeight + 15 + pixelUnit;
					}
					else if (this.selectedPopupUtils.isPopupTypeAudit())
					{
						height = Integer.valueOf(bImage.getHeight()) + marginHeight + checkAcceptSpace + pixelUnit;
					}
				}
				else
				{
					width = "600px";
					height = "300px";
					this.setDescription(I18NManager.getI18NString(this.getBbrUIParent(), BbrUtilsResources.RES_GENERIC, "error_failed_to_load_image"), ContentMode.HTML);
					this.addStyleName("bbr-margin-windows");
				}

			}
			else
			{
				width = "600px";
				height = "500px";
				this.addStyleName("bbr-margin-windows");
			}
			this.setWidth(width);
			this.setHeight(height);
			this.setResizable(false);
			this.setContent(mainLayout);
			this.addCloseListener((CloseListener & Serializable) e -> this.handlerCloseEvent(e));

		}
	}

	private void handlerCloseEvent(Event e)
	{
		if (this.selectedPopupUtils.isPopupTypeAudit() && !this.isAcceptClose)
		{
			this.doAuditServiceWork(false);
			System.out.println("From Window dispara evento de CLOSE AUDITA: " + this.selectedPopupUtils.isPopupTypeAudit());
		}
		this.close();
	}

	private void handlerAcceptEvent()
	{
		this.isAcceptClose = true;
		if (this.selectedPopupUtils.isPopupTypeAudit())
		{
			this.doAuditServiceWork(true);
			System.out.println("From Slide dispara evento de ACEPTAR AUDITA: " + this.selectedPopupUtils.isPopupTypeAudit());
		}
		this.close();
	}

	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// ENDING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

	private void doUpdateSystem(Object result, WinPopup senderReport, Object triggerObject)
	{
		boolean action = (boolean) triggerObject;
		if (result != null)
		{
			BaseResultDTO reportResult = (BaseResultDTO) result;

			BbrError error = ErrorsMngr.getInstance().getError(reportResult, senderReport.getBbrUIParent(), !senderReport.getBbrUIParent().hasAlertWindowOpen());

			if (!error.hasError())
			{
				System.out.println("pasa o redirige " + action);

				if (senderReport.selectedPopupUtils.isPopupModeBlock())
				{
					System.out.println("Bloquea: " + senderReport.selectedPopupUtils.isPopupModeBlock());
					if (!action)
					{
						senderReport.close();
						AppUtils.getInstance().doLogOut("", senderReport.getBbrUIParent());
					}
					else
					{
						senderReport.close();
						if (!this.isModuleBloqued)
						{
							MainUI mainUI = (MainUI) senderReport.getBbrUIParent();
							// codeModuleSelected NULL IS POPUP MODULE PORTAL
							mainUI.showSelectedFuncionalityCodeFromUI(senderReport.codeModuleSelected);
						}
					}
				}
			}
			else
			{
				AppUtils.getInstance().doLogOut(error.getErrorMessage(), this.getBbrUIParent());
				this.stopWaiting();
				this.close();
			}
		}

	}

	private void doAuditServiceWork(boolean action)
	{
		this.executeAuditService(this.getBbrUIParent(), action);
	}

	protected BaseResultDTO executeAuditService(BbrUI bbrUIParent, boolean action)
	{
		BaseResultDTO result = null;
		try
		{
			AuditPopUpInitParamDTO initParamDTO = this.getAuditPopUpInitParam(action);
			result = EJBFactory.getUserEJBFactory().getPublishingManagerLocal(bbrUIParent).getAuditPopUp(initParamDTO, bbrUIParent.getUser().getId());
			if (result != null)
			{
				this.doUpdateSystem(result, this, action);
			}
			else
			{
				if (!bbrUIParent.hasAlertWindowOpen())
					bbrUIParent.showErrorMessage(I18NManager.getI18NString(bbrUIParent, BbrUtilsResources.RES_GENERIC, "windows_title_error"),
							I18NManager.getI18NString(bbrUIParent, BbrUtilsResources.RES_GENERIC, "unsuccessful_operation"));
			}
		}
		catch (BbrUserException ex)
		{
			AppUtils.getInstance().doLogOut(ex.getMessage(), bbrUIParent);
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

	private AuditPopUpInitParamDTO getAuditPopUpInitParam(boolean action)
	{
		AuditPopUpInitParamDTO result = new AuditPopUpInitParamDTO();
		result.setAction(action);
		result.setPukey(this.currentPopup.getPukey());
		result.setPoupuptypeid(this.currentPopup.getTpokey());
		return result;
	}

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
}
