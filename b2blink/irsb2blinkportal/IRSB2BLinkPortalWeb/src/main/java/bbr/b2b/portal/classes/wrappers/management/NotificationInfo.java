package bbr.b2b.portal.classes.wrappers.management;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.AddPublishingInitParamsDTO;
import bbr.b2b.users.report.classes.AttachmentInitParamsDTO;
import bbr.b2b.users.report.classes.RuleTypeDTO;
import bbr.b2b.users.report.classes.UpdatePublishingInitParamsDTO;
import cl.bbr.core.classes.basics.BbrUser;
import cl.bbr.core.classes.files_transfer.BbrFileWrapper;

public class NotificationInfo
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
	private Boolean						isActive			= null;

	private AttachmentInitParamsDTO[]	toAddAttachments	= null;
	private AttachmentInitParamsDTO[]	toRemoveAttachments	= null;

	public BbrUser getUser()
	{
		return user;
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

	public AttachmentInitParamsDTO[] getToAddAttachments()
	{
		return toAddAttachments;
	}

	public void setToAddAttachments(AttachmentInitParamsDTO[] attachments)
	{
		this.toAddAttachments = attachments;
	}

	public AttachmentInitParamsDTO[] getToRemoveAttachments()
	{
		return toRemoveAttachments;
	}

	public void setToRemoveAttachments(AttachmentInitParamsDTO[] attachments)
	{
		this.toRemoveAttachments = attachments;
	}

	public Boolean getIsActive()
	{
		return isActive;
	}

	public void setIsActive(Boolean isActive)
	{
		this.isActive = isActive;
	}

	public NotificationInfo(BbrUser user, String title, LocalDateTime initdate, LocalDateTime enddate, String htmlcontent, Long imageid, Long publishingTypeId, RuleTypeDTO ruleType, ArrayList<BbrFileWrapper> toAddAttachmentWrappers,
			ArrayList<BbrFileWrapper> toRemoveAttachmentWrappers, Boolean isActive)
	{
		super();
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
		this.toAddAttachments = this.getAttachmentsByWrapper(toAddAttachmentWrappers);
		this.toRemoveAttachments = this.getAttachmentsByWrapper(toRemoveAttachmentWrappers);
		this.isActive = isActive;

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
		else if (this.initdate.plusDays(1L).isAfter(this.enddate))
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_range");
		}
		else if (this.ruleType == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_rule");
		}
		else if (this.title == null || this.title.trim().length() <= 0)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_title");
		}
		else if (this.htmlcontent == null || this.htmlcontent.trim().length() <= 0)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_body");
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
		else if (this.initdate.plusDays(1L).isAfter(this.enddate))
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_range");
		}
		else if (this.ruleType == null)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_rule");
		}
		else if (this.title == null || this.title.trim().length() <= 0)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_title");
		}
		else if (this.htmlcontent == null || this.htmlcontent.trim().length() <= 0)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_body");
		}

		return result;
	}

	public AddPublishingInitParamsDTO toPublishingDTO()
	{
		AddPublishingInitParamsDTO result = new AddPublishingInitParamsDTO();

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
		result.setAttachments(this.toAddAttachments);
		result.setIsActive(this.isActive);
		return result;
	}

	public UpdatePublishingInitParamsDTO toUpdatePublishingDTO()
	{
		UpdatePublishingInitParamsDTO result = new UpdatePublishingInitParamsDTO();

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
		result.setAttachments(this.toAddAttachments);
		result.setPublishingId(publishingId);

		return result;
	}

	public Long getPublishingId()
	{
		return publishingId;
	}

	public void setPublishingId(Long publishingId)
	{
		this.publishingId = publishingId;
	}

	public AttachmentInitParamsDTO[] getAttachmentsByWrapper(ArrayList<BbrFileWrapper> files)
	{
		AttachmentInitParamsDTO[] result = null;

		if (files != null && files.size() > 0)
		{
			result = new AttachmentInitParamsDTO[files.size()];

			for (int i = 0; i < files.size(); i++)
			{
				AttachmentInitParamsDTO attach = new AttachmentInitParamsDTO();
				attach.setFileid(files.get(i).getId());
				attach.setFilename(files.get(i).getRealFileName());
				attach.setSize(files.get(i).getFileSize().intValue());
				result[i] = attach;
			}
		}

		return result;
	}

}
