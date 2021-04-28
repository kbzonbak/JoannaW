package bbr.b2b.portal.classes.wrappers.management;

import java.util.ArrayList;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.ContactB2BDTO;
import bbr.b2b.users.report.classes.SendMailContactInitParamDTO;
import cl.bbr.core.classes.basics.BbrUser;
import cl.bbr.core.classes.files_transfer.BbrFileWrapper;

public class SendMailToRetailInfo 
{
	private ContactB2BDTO contact	= null	;
	private BbrUser user 			= null 	;
	private String subject 			= null	;
	private String htmlcontent		= null	;
	
	private String[] toAddAttachments = null;
	
	
	public BbrUser getUser() {
		return user;
	}

	public void setUser(BbrUser user) {
		this.user = user;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getHtmlcontent() {
		return htmlcontent;
	}

	public void setHtmlcontent(String htmlcontent) {
		this.htmlcontent = htmlcontent;
	}

	public String[] getToAddAttachments() {
		return toAddAttachments;
	}

	public void setToAddAttachments(String[] attachments) {
		this.toAddAttachments = attachments;
	}

	public ContactB2BDTO getContact() {
		return contact;
	}

	public void setContact(ContactB2BDTO contact) {
		this.contact = contact;
	}
	public SendMailToRetailInfo(BbrUser user, ContactB2BDTO contact, String subject, String htmlcontent, ArrayList<BbrFileWrapper> toAddAttachmentWrappers) 
	{
		super();
		this.user = user;
		this.contact = contact;
		this.subject = subject;
		this.htmlcontent = htmlcontent;
		this.toAddAttachments = this.getAttachmentsByWrapper(toAddAttachmentWrappers);
	}

	public String validateSendData()
	{
		String result = null ;
		
		if(this.subject == null || this.subject.trim().length() <= 0)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_subject");
		}
		else if (this.htmlcontent == null || this.htmlcontent.trim().length() <= 0)
		{
			result = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "valid_body");
		}
		
		return result;
	}

	public SendMailContactInitParamDTO toSendMailContactInitParamDTO()
	{
		SendMailContactInitParamDTO result = new SendMailContactInitParamDTO();
		
		result.setAttachments(this.toAddAttachments);
		
		result.setContactarea(contact.getDepartment());
		result.setContactmail(contact.getEmail());
		result.setContactname(contact.getName() + " " + contact.getLastname());
		result.setEmailuser(user.getEmail());
		result.setEnterpriseuser(user.getEnterpriseDescription());
		result.setMessage(this.htmlcontent);
		result.setName(user.getFullName());
		result.setPosition(user.getPosition());
		result.setSubject(this.subject.trim());
		
		return result;
	}

	public String[] getAttachmentsByWrapper(ArrayList<BbrFileWrapper> files)
	{
		String[] result = null;
		
		if(files!= null && files.size() > 0)
		{
			result = new String[files.size()];
			
			for (int i = 0; i < files.size(); i++) 
			{
				result[i] = files.get(i).getRealFileName();
			}
		}
		
		
		return result;
	}


	

}
