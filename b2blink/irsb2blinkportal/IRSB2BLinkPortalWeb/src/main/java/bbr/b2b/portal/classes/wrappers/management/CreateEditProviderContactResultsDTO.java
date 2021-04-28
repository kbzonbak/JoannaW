package bbr.b2b.portal.classes.wrappers.management;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.users.report.classes.ContactPhoneTypeResultDTO;
import bbr.b2b.users.report.classes.ContactPositionArrayResultDTO;

public class CreateEditProviderContactResultsDTO extends BaseResultDTO
{
	private static final long				serialVersionUID			= 1540012346865502473L;
	private ContactPositionArrayResultDTO	contactPositionArrayResult	= null;
	private ContactPhoneTypeResultDTO		contactPhoneTypeResult		= null;

	public ContactPositionArrayResultDTO getContactPositionArrayResult()
	{
		return contactPositionArrayResult;
	}

	public void setContactPositionArrayResult(ContactPositionArrayResultDTO contactPositionArrayResult)
	{
		this.contactPositionArrayResult = contactPositionArrayResult;
	}

	public ContactPhoneTypeResultDTO getContactPhoneTypeResult()
	{
		return contactPhoneTypeResult;
	}

	public void setContactPhoneTypeResult(ContactPhoneTypeResultDTO contactPhoneTypeResult)
	{
		this.contactPhoneTypeResult = contactPhoneTypeResult;
	}

}
