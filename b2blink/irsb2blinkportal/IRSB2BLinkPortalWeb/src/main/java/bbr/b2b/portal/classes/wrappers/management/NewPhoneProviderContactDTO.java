package bbr.b2b.portal.classes.wrappers.management;

import bbr.b2b.users.report.classes.ContactPhoneTypeDTO;

public class NewPhoneProviderContactDTO
{
	private ContactPhoneTypeDTO	contactPhoneType	= null;
	private String				number				= null;

	public ContactPhoneTypeDTO getContactPhoneType()
	{
		return contactPhoneType;
	}

	public void setContactPhoneType(ContactPhoneTypeDTO contactPhoneType)
	{
		this.contactPhoneType = contactPhoneType;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}
}
