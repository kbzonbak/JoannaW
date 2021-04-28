package bbr.b2b.portal.classes.wrappers.management;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.CompanyDTO;
import bbr.b2b.users.report.classes.UserDTO;
import cl.bbr.core.classes.utilities.BbrUtils;

public class EditUserInfo
{
	private String		userName		= "";
	private String		userLastName	= "";
	private String		position		= "";
	private String		mail			= "";
	private String		phone			= "";
	private UserDTO		user			= null;
	private CompanyDTO	company			= null;
	private Boolean		allCompanies	= null;
	private Boolean		allLocals		= null;

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getUserLastName()
	{
		return userLastName;
	}

	public void setUserLastName(String userLastName)
	{
		this.userLastName = userLastName;
	}

	public String getPosition()
	{
		return position;
	}

	public void setPosition(String position)
	{
		this.position = position;
	}

	public String getMail()
	{
		return mail;
	}

	public void setMail(String mail)
	{
		this.mail = mail;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public UserDTO getUser()
	{
		return user;
	}

	public void setUser(UserDTO user)
	{
		this.user = user;
	}

	public CompanyDTO getCompany()
	{
		return company;
	}

	public void setCompany(CompanyDTO company)
	{
		this.company = company;
	}

	public Boolean getAllCompanies()
	{
		return allCompanies;
	}

	public void setAllCompanies(Boolean allCompanies)
	{
		this.allCompanies = allCompanies;
	}

	public Boolean getAllLocals()
	{
		return allLocals;
	}

	public void setAllLocals(Boolean allLocals)
	{
		this.allLocals = allLocals;
	}

	public EditUserInfo(UserDTO user, String userName, String userLastName, CompanyDTO company, String mail, String phone, String position, Boolean allCompanies, Boolean allLocals)
	{
		super();
		this.user = user;
		this.userName = userName;
		this.userLastName = userLastName;
		this.company = company;
		this.mail = mail;
		this.phone = phone;
		this.position = position;
		this.allCompanies = allCompanies;
		this.allLocals = allLocals;
	}

	public String validateData()
	{
		String result = null;

		if ((this.userName != null && this.userName.length() > 0) && (this.userLastName != null && this.userLastName.length() > 0)
				&& (this.position != null && this.position.length() > 0) && (this.mail != null && this.mail.length() > 0)
				&& (this.phone != null))
		{

			if (!this.isValidField(this.userName, 50)) // -> Nombre
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_name");
			}
			else if (!this.isValidField(this.userLastName, 50))// -> Apellido
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_last_name");
			}
			else if (company == null)// -> Empresa
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_company");
			}
			else if (!BbrUtils.getInstance().checkEmailAddress(this.mail))// ->
																			// Email
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_mail");
			}
			// else if(this.phone.length() > 0 &&
			// !BbrUtils.getInstance().isPhoneRegEx(phone))// -> Tel�fono
			// {
			// result = I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN,
			// "valid_phone");
			// }
			else if (!this.isValidField(this.position, 200))// ->
																								// Cargo
			{
				result = I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_position");
			}

		}
		else
		{
			// -> Campos vac�os.
			result = I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "msg_missing_fields");
		}

		return result;
	}

	public UserDTO getUpdatedUser()
	{
		this.user.setName(this.getUserName());
		this.user.setLastname(this.getUserLastName());
		this.user.setEmkey(this.getCompany().getId());
		this.user.setEmail(this.getMail());
		this.user.setPhone(this.getPhone());
		this.user.setPosition(this.getPosition());
		this.user.setAllenterprises(this.getAllCompanies());
		this.user.setAlllocals(this.getAllLocals());

		return this.user;
	}

	private boolean isValidField(String field, int maxLength)
	{
		boolean result = true;
		if ((field.length() > maxLength))
		{

			result = false;
		}

		return result;
	}
}
