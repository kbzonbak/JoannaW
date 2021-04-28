package bbr.b2b.portal.classes.wrappers.management;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
//import bbr.b2b.trac.report.classes.AddTicketInitParamDTO;
import cl.bbr.core.classes.basics.BbrUser;

public class VendorTicketInfo 
{
	private BbrUser user 			= null 	;
	private String title 			= null	;
	private String htmlcontent		= null	;
	
	
	public BbrUser getUser() {
		return user;
	}

	public void setUser(BbrUser user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHtmlcontent() {
		return htmlcontent;
	}

	public void setHtmlcontent(String htmlcontent) {
		this.htmlcontent = htmlcontent;
	}

	public VendorTicketInfo(BbrUser user, String title, String htmlcontent) 
	{
		super();
		this.user = user;
		this.title = title;
		this.htmlcontent = htmlcontent;
	}

	public String validateCreateData()
	{
		String result = null ;
		
		if(this.title == null || this.title.trim().length() <= 0)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_title");
		}
		else if (this.htmlcontent == null || this.htmlcontent.trim().length() <= 0)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_body");
		}
		
		return result;
	}
//	public AddTicketInitParamDTO toAddTicketInitParamDTO()
//	{
//		AddTicketInitParamDTO result = new AddTicketInitParamDTO();
//		
//		result.setVendorId(this.user.getEnterpriseId());
//		result.setVendorName(this.user.getEnterpriseDescription());
//		result.setUserId(this.user.getId());
//		result.setReference(this.title.trim());
//		result.setDescription(this.htmlcontent);
//		
//		return result;
//	}


	

}
