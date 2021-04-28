package bbr.b2b.portal.classes.utils.app;

import bbr.b2b.portal.classes.constants.BbrPublishingConstants;
import bbr.b2b.users.report.classes.PopUpReportDataDTO;
import bbr.b2b.users.report.classes.PopUpTemplateDTO;
import bbr.b2b.users.report.classes.PopUpTypeDTO;

public class SelectedPopupUtils
{
	private PopUpTypeDTO		popUpType			= null;

	private PopUpTemplateDTO	popUpTemplate		= null;
	private PopUpReportDataDTO	notification		= null;
	private Boolean				acceptedOrClosed	= null;

	// notification
	public void setNotification(PopUpReportDataDTO notification)
	{
		this.notification = notification;
	}

	public Boolean isHtmlcontentTemplateImage()
	{
		// you need to preSet selectedNotification
		return notification.getHtmlcontent() != null
				&& !notification.getHtmlcontent().isEmpty()
				&& notification.getPopuptemplatecode().equalsIgnoreCase(BbrPublishingConstants.POPUP_TEMPLATE_2);
	}

	// template
	public void setPopUpTemplate(PopUpTemplateDTO popUpTemplate)
	{
		this.popUpTemplate = popUpTemplate;
	}

	// el template 1 se refiere al formato estandar
	public Boolean isPopupTemplate1()
	{
		return popUpTemplate != null ? this.popUpTemplate.getCode().equalsIgnoreCase(BbrPublishingConstants.POPUP_TEMPLATE_1)
				: (this.notification != null) ? this.notification.getPopuptemplatecode().equalsIgnoreCase(BbrPublishingConstants.POPUP_TEMPLATE_1) : null;
	}

	// el template 1 se refiere al formato Imagen
	public Boolean isPopupTemplateImage()
	{
		return popUpTemplate != null ? popUpTemplate.getCode().equalsIgnoreCase(BbrPublishingConstants.POPUP_TEMPLATE_2)
				: (notification != null) ? notification.getPopuptemplatecode().equalsIgnoreCase(BbrPublishingConstants.POPUP_TEMPLATE_2) : null;
	}

	public Boolean isValidHtmlPathForImage()
	{
		return (notification != null) ? (notification.getHtmlcontent().length() > 0) && this.isPresentFile() : false;
	}

	public boolean isPresentFile()
	{
		return ((ImagesBufferedUtils.getBufferedImageFromFile(notification.getHtmlcontent()) != null) ? true : false);
	}

	// type
	public void setPopUpType(PopUpTypeDTO popUpType)
	{
		this.popUpType = popUpType;
	}

	public Boolean isPopupTypeInf()
	{
		return popUpType != null ? popUpType.getCode().equalsIgnoreCase(BbrPublishingConstants.POPUP_TYPE_CODE_INF)
				: (notification != null) ? notification.getPopuptypecode().equalsIgnoreCase(BbrPublishingConstants.POPUP_TYPE_CODE_INF) : false;
	}

	public Boolean isPopupTypeAuditUsu()
	{
		return popUpType != null ? popUpType.getCode().equalsIgnoreCase(BbrPublishingConstants.POPUP_TYPE_CODE_AUDIT_USU)
				: (notification != null) ? notification.getPopuptypecode().equalsIgnoreCase(BbrPublishingConstants.POPUP_TYPE_CODE_AUDIT_USU) : false;
	}

	public Boolean isPopupTypeAuditEmp()
	{
		return popUpType != null ? popUpType.getCode().equalsIgnoreCase(BbrPublishingConstants.POPUP_TYPE_CODE_AUDIT_EMP)
				: (notification != null) ? notification.getPopuptypecode().equalsIgnoreCase(BbrPublishingConstants.POPUP_TYPE_CODE_AUDIT_EMP) : false;
	}

	public Boolean isPopupTypeAudit()
	{
		return this.isPopupTypeAuditUsu() || this.isPopupTypeAuditEmp();
	}

	public Boolean isPopupModeLink()
	{
		return (notification.getCpcode() != null) ? notification.getCpcode().equalsIgnoreCase(BbrPublishingConstants.POPUP_MODE_LINK) : false;
	}

	public Boolean isPopupModeBlock()
	{
		return (notification.getCpcode() != null) ? notification.getCpcode().equalsIgnoreCase(BbrPublishingConstants.POPUP_MODE_BLOQUEA) : false;
	}

	public Boolean isPopupModeAllow()
	{
		return (notification.getCpcode() != null) ? notification.getCpcode().equalsIgnoreCase(BbrPublishingConstants.POPUP_MODE_PERMITE) : false;
	}

	public Boolean isLinkAvailable()
	{
		return (this.isPopupModeLink() || this.isPopupModeBlock() || this.isPopupModeAllow());
	}

	public Boolean getAcceptedOrClosed()
	{
		return acceptedOrClosed;
	}

	public void setAcceptedOrClosed(Boolean acceptedOrClosed)
	{
		this.acceptedOrClosed = acceptedOrClosed;
	}

}
