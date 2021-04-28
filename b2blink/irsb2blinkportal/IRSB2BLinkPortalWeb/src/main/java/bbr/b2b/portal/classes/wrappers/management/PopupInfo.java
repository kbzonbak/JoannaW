package bbr.b2b.portal.classes.wrappers.management;

import java.time.LocalDateTime;
import java.util.Date;

import bbr.b2b.portal.classes.constants.BbrPublishingConstants;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.AddPopUpInitParamsDTO;
import bbr.b2b.users.report.classes.PopUpBehaviorDTO;
import bbr.b2b.users.report.classes.PopUpTemplateDTO;
import bbr.b2b.users.report.classes.PopUpTypeDTO;
import bbr.b2b.users.report.classes.RuleTypeDTO;
import bbr.b2b.users.report.classes.UpdatePopUpInitParamsDTO;
import bbr.b2b.users.report.classes.UserTreeFunctionalityDTO;
import cl.bbr.core.classes.basics.BbrUser;

public class PopupInfo
{
	private BbrUser						user				= null;
	private String						title				= null;
	private Date						publishingdate		= null;
	private LocalDateTime				initdate			= null;
	private LocalDateTime				enddate				= null;
	private String						htmlcontent			= null;
	private Long						imageid				= null;
	private String						owner				= null;
	private String						lastUser			= null;
	private Long						publishingTypeId	= null;
	private RuleTypeDTO					ruleType			= null;
	private Long						publishingId		= null;
	private UserTreeFunctionalityDTO	functionality		= null;
	private PopUpBehaviorDTO			popUpBehavior		= null;
	private PopUpTypeDTO				popUpType			= null;
	private PopUpTemplateDTO			popUpTemplate		= null;
	private String						link				= null;
	private Boolean						isActive			= null;

	public PopUpTypeDTO getPopUpType()
	{
		return popUpType;
	}

	public void setPopUpType(PopUpTypeDTO popUpType)
	{
		this.popUpType = popUpType;
	}

	public PopUpTemplateDTO getPopUpTemplate()
	{
		return popUpTemplate;
	}

	public void setPopUpTemplate(PopUpTemplateDTO popUpTemplate)
	{
		this.popUpTemplate = popUpTemplate;
	}

	public BbrUser getUser()
	{
		return user;
	}

	public UserTreeFunctionalityDTO getFunctionality()
	{
		return functionality;
	}

	public void setFunctionality(UserTreeFunctionalityDTO functionality)
	{
		this.functionality = functionality;
	}

	public PopUpBehaviorDTO getPopUpBehavior()
	{
		return popUpBehavior;
	}

	public void setPopUpBehavior(PopUpBehaviorDTO popUpBehavior)
	{
		this.popUpBehavior = popUpBehavior;
	}

	public void setUser(BbrUser user)
	{
		this.user = user;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Date getPublishingdate()
	{
		return publishingdate;
	}

	public void setPublishingdate(Date publishingdate)
	{
		this.publishingdate = publishingdate;
	}

	public LocalDateTime getInitdate()
	{
		return initdate;
	}

	public void setInitdate(LocalDateTime initdate)
	{
		this.initdate = initdate;
	}

	public LocalDateTime getEnddate()
	{
		return enddate;
	}

	public void setEnddate(LocalDateTime enddate)
	{
		this.enddate = enddate;
	}

	public String getHtmlcontent()
	{
		return htmlcontent;
	}

	public void setHtmlcontent(String htmlcontent)
	{
		this.htmlcontent = htmlcontent;
	}

	public Long getImageid()
	{
		return imageid;
	}

	public void setImageid(Long imageid)
	{
		this.imageid = imageid;
	}

	public String getOwner()
	{
		return owner;
	}

	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	public String getLastUser()
	{
		return lastUser;
	}

	public void setLastUser(String lastUser)
	{
		this.lastUser = lastUser;
	}

	public Long getPublishingTypeId()
	{
		return publishingTypeId;
	}

	public void setPublishingTypeId(Long publishingTypeId)
	{
		this.publishingTypeId = publishingTypeId;
	}

	public RuleTypeDTO getRuleType()
	{
		return ruleType;
	}

	public void setRuleType(RuleTypeDTO ruleType)
	{
		this.ruleType = ruleType;
	}

	public Boolean getIsActive()
	{
		return isActive;
	}

	public void setIsActive(Boolean isActive)
	{
		this.isActive = isActive;
	}

	public Long getPublishingId()
	{
		return publishingId;
	}

	public void setPublishingId(Long publishingId)
	{
		this.publishingId = publishingId;
	}

	public String getLink()
	{
		return link;
	}

	public void setLink(String link)
	{
		this.link = link;
	}

	public PopupInfo(BbrUser user, String title, LocalDateTime initdate, LocalDateTime enddate, String htmlcontent, Long imageid, Long publishingTypeId, RuleTypeDTO ruleType, UserTreeFunctionalityDTO functionality, PopUpBehaviorDTO popUpBehavior,
			String link, Boolean isActive, PopUpTemplateDTO popUpTemplate, PopUpTypeDTO popUpType)
	{
		this.user = user;
		this.title = title;
		this.initdate = initdate;
		this.enddate = enddate;
		this.htmlcontent = htmlcontent;
		this.imageid = imageid;
		this.owner = user.getFullName();
		this.lastUser = user.getFullName();
		this.publishingTypeId = publishingTypeId;
		this.ruleType = ruleType;
		this.functionality = functionality;
		this.popUpBehavior = popUpBehavior;
		this.link = link;
		this.isActive = isActive;
		this.popUpTemplate = popUpTemplate;
		this.popUpType = popUpType;
	}

	public PopupInfo(PopupInfoBuilder popupInfoBuilder)
	{
	}

	public static class PopupInfoBuilder
	{
		public PopupInfo build()
		{
			return new PopupInfo(this);
		}
	}

	public String validateCreateData()
	{
		String result = null;

		if (this.initdate == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_start_date");
		}
		else if (this.initdate.isBefore(LocalDateTime.now()))
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_start_date_range");
		}
		else if (this.enddate == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_end_date");
		}
		else if (this.ruleType == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_rule");
		}
		else if (this.initdate.plusDays(1L).isAfter(this.enddate))
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_range");
		}
		else if (this.title == null || this.title.trim().length() <= 0)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_title");
		}
		else if (this.htmlcontent == null || this.htmlcontent.trim().length() <= 0)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_body");
		}
		else if (this.functionality == null || (!this.functionality.isTerminal() && !this.functionality.getParentkey().equals(0L)))
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_module");
		}
		else if (this.popUpBehavior == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_behavior");
		}

		return result;
	}

	public String validateEditData()
	{
		String result = null;

		if (this.initdate == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_start_date");
		}
		else if (this.enddate == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_end_date");
		}
		else if (this.ruleType == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_rule");
		}
		else if (this.initdate.plusDays(1L).isAfter(this.enddate))
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_range");
		}
		else if (this.title == null || this.title.trim().length() <= 0)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_title");
		}
		else if (this.htmlcontent == null || this.htmlcontent.trim().length() <= 0)
		{
			if (this.popUpTemplate.getCode().equalsIgnoreCase(BbrPublishingConstants.POPUP_TEMPLATE_1))
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_body");
			}
			if (this.popUpTemplate.getCode().equalsIgnoreCase(BbrPublishingConstants.POPUP_TEMPLATE_2))
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_image");
			}
		}
		else if (this.functionality == null || (!this.functionality.isTerminal() && !this.functionality.getParentkey().equals(0L)))
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_module");
		}
		else if (this.popUpBehavior == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_behavior");
		}
		else if (this.popUpBehavior != null && this.popUpBehavior.getCode().equals(BbrPublishingConstants.POPUP_MODE_LINK))
		{
			if (this.link.length() < 1)
			{
				String example = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "example_link_placeholder");
				result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_link", example);
			}
		}
		else if (this.popUpTemplate != null && this.popUpTemplate.getCode().equals(BbrPublishingConstants.POPUP_TEMPLATE_2))
		{
			// FORMATO : IMAGEN
			// SI EL COMPORTAMIENTO ES : PERMITE ACCESO O BLOQUEA ACCESO
			// NO PIDE LINK
			// SI EL COMPORTAMIENTO ES : REDIRIGE LINK
			// SI PIDE LINK
			if (this.popUpBehavior != null &&
					(this.popUpBehavior.getCode().equals(BbrPublishingConstants.POPUP_MODE_LINK)
							|| this.popUpBehavior.getCode().equals(BbrPublishingConstants.POPUP_MODE_BLOQUEA)
							|| this.popUpBehavior.getCode().equals(BbrPublishingConstants.POPUP_MODE_PERMITE)))
			{
				if (this.link.length() < 1)
				{
					String example = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "example_link_placeholder");
					result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_link", example);
				}
			}
			// si le falta el link lanza error
			// if (this.link.length() < 1)
			// {
			// String example =
			// I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT,
			// "example_link_placeholder");
			// result =
			// I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT,
			// "valid_link", example);
			// }
		}
		return result;
	}

	public AddPopUpInitParamsDTO toAddPopUpInitParamsDTO()
	{
		AddPopUpInitParamsDTO result = new AddPopUpInitParamsDTO();

		result.setTitle(this.title.trim());
		result.setPublishingdate(LocalDateTime.now());
		result.setInitdate(this.initdate);
		result.setEnddate(this.enddate);
		result.setHtmlcontent(this.htmlcontent);
		result.setImageid(this.imageid);
		result.setOwner(this.owner);
		result.setLastUser(this.lastUser);
		result.setPublishingTypeId(this.publishingTypeId);
		result.setRuleTypeId(this.ruleType.getId());
		result.setLink(this.link);
		result.setFunctionalityid(this.functionality.getId());
		result.setPopupbehaviorid(this.popUpBehavior.getId());
		result.setIsActive(this.isActive);
		result.setPopuptemplateid(this.popUpTemplate.getId());
		result.setPopuptypeid(this.popUpType.getId());

		return result;
	}

	public UpdatePopUpInitParamsDTO toUpdatePopUpInitParamsDTO()
	{
		UpdatePopUpInitParamsDTO result = new UpdatePopUpInitParamsDTO();

		result.setTitle(this.title.trim());
		result.setPublishingdate(LocalDateTime.now());
		result.setInitdate(this.initdate);
		result.setEnddate(this.enddate);
		result.setHtmlcontent(this.htmlcontent);
		result.setImageid(this.imageid);
		result.setOwner(this.owner);
		result.setLastUser(this.lastUser);
		result.setPublishingTypeId(this.publishingTypeId);
		result.setRuleTypeId(this.ruleType.getId());
		result.setLink(this.link);
		result.setFunctionalityid(this.functionality.getId());
		result.setPopupbehaviorid(this.popUpBehavior.getId());
		result.setPopUpId(this.publishingId);
		result.setPopuptemplateid(this.popUpTemplate.getId());
		result.setPopuptypeid(this.popUpType.getId());

		return result;
	}

}
