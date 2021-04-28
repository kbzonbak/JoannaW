package bbr.b2b.portal.classes.wrappers.management;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.users.UserManagerUtils;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.UserDTO;
import cl.bbr.core.classes.basics.Rut;
import cl.bbr.core.classes.utilities.BbrUtils;

public class NewUserInfo
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// Constants
	private boolean	isExternalRetail	= true;
	// Variables
	private String	userName			= null;
	private String	userLastName		= null;
	private Rut		enterpriseCode		= null;
	private Long	enterpriseKey		= null;
	private Rut		userCode			= null;
	private String	position			= null;
	private String	mail				= null;
	private String	phone				= null;
	private Boolean	allCompanies		= null;
	private Boolean	allLocals			= null;
	private Boolean	isRetailType		= null;
	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================
	public NewUserInfo(NewUserInfoBuilder builder)
	{
		initializeUserInfo(builder);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
	private void initializeUserInfo(NewUserInfoBuilder builder)
	{
		this.userName = builder.userName;
		this.userLastName = builder.userLastName;
		this.enterpriseCode = builder.enterpriseCode;
		this.userCode = builder.userCode;
		this.position = builder.position;
		this.mail = builder.mail;
		this.phone = builder.phone;
		this.allCompanies = builder.allCompanies;
		this.allLocals = builder.allLocals;
		this.enterpriseKey = builder.enterpriseKey;

		this.isExternalRetail = builder.isExternalRetail;
		this.isRetailType = builder.isRetailType;
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
	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PUBLIC METHODS
	// =====================================================================================================================================
	public String getMail()
	{
		return mail;
	}

	public String getPhone()
	{
		return phone;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PUBLIC METHODS
	// =====================================================================================================================================

	public String validateData(boolean isRetailOrBbrType)
	{
		String result = null;
		if (isRetailOrBbrType)
		{
			// isRetailType() &&
			if (this.isRetailType != null && (this.isRetailType && !this.isExternalRetail))
			{
				// VALIDAR SI HAY DATOS
				if ((this.userName != null && this.userName.length() > 0) && (this.userLastName != null && this.userLastName.length() > 0)
						&& (this.position != null && this.position.length() > 0)
						&& (this.mail != null && this.mail.length() > 0) && (this.phone != null))
				{

					if (!this.isValidField(this.userName, 50)) // -> Nombre
					{
						result = I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_name");
					}
					else if (!this.isValidField(this.userLastName, 50))// -> Apellido
					{
						result = I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_last_name");
					}
					else if (!this.isValidField(this.position, 200))// ->Cargo
					{
						result = I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_position");
					}
					else if (!BbrUtils.getInstance().checkEmailAddress(this.mail))// ->Email
					{
						result = I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_mail");
					}
				}
				else
				{
					// -> Campos vacíos.
					result = I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "msg_missing_fields");
				}
			}
			else
			{
				if ((this.mail != null && this.mail.length() > 0))
				{
					if (!BbrUtils.getInstance().checkEmailAddress(this.mail))// ->Email
					{
						result = I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_mail");
					}
				}
				else
				{
					// -> Campos vacíos.
					result = I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "msg_missing_fields");
				}

			}

		}
		else
		{
			// VALIDAR SI HAY DATOS
			if ((this.userName != null && this.userName.length() > 0) && (this.userLastName != null && this.userLastName.length() > 0)
					&& (this.enterpriseCode != null && this.userCode != null) && (this.position != null && this.position.length() > 0)
					&& (this.mail != null && this.mail.length() > 0) && (this.phone != null))
			{

				if (!this.isValidField(this.userName, 50)) // -> Nombre
				{
					result = I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_name");
				}
				else if (!this.isValidField(this.userLastName, 50))// ->
																	// Apellido
				{
					result = I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_last_name");
				}
				else if (!this.isValidField(this.position, 200))// ->Cargo
				{
					result = I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_position");
				}
				else if (!BbrUtils.getInstance().checkEmailAddress(this.mail))// ->Email
				{
					result = I18NManager.getI18NString(BbrUtilsResources.RES_LOGIN, "valid_mail");
				}
			}
			else
			{
				// -> Campos vacíos.
				result = I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "msg_missing_fields");
			}
		}
		return result;
	}

	public UserDTO toUserDTO()
	{
		UserDTO result = new UserDTO();

		result.setLogid(String.valueOf(this.userCode.getRut()));
		result.setName(this.userName);
		result.setLastname(this.userLastName);
		result.setPersonalid(this.userCode.getRutString());
		result.setPosition(this.position);
		result.setEmail(this.mail);
		result.setPhone(this.phone);
		result.setAllenterprises(this.allCompanies);
		result.setAlllocals(this.allLocals);
		result.setEmkey(this.enterpriseKey);
		return result;
	}

	public static class NewUserInfoBuilder
	{
		private String	userName			= null;
		private String	userLastName		= null;
		private Rut		enterpriseCode		= null;
		private Long	enterpriseKey		= null;
		private Rut		userCode			= null;
		private String	position			= null;
		private String	mail				= null;
		private String	phone				= null;
		private Boolean	allCompanies		= null;
		private Boolean	allLocals			= null;

		private boolean	isExternalRetail	= true;
		private Boolean	isRetailType		= null;

		public NewUserInfoBuilder withEnterpriseKey(Long enterpriseKey)
		{
			this.enterpriseKey = enterpriseKey;
			return this;
		}

		public NewUserInfoBuilder withIsRetailType(Boolean isRetailType)
		{
			this.isRetailType = isRetailType;
			return this;
		}

		public NewUserInfoBuilder withIsExternalRetail()
		{
			this.isExternalRetail = UserManagerUtils.getIsExternalRetail();
			return this;
		}

		public NewUserInfoBuilder withAllLocals(Boolean allLocals)
		{
			this.allLocals = allLocals;
			return this;
		}

		public NewUserInfoBuilder withAllCompanies(Boolean allCompanies)
		{
			this.allCompanies = allCompanies;
			return this;
		}

		public NewUserInfoBuilder withPhone(String phone)
		{
			this.phone = phone;
			return this;
		}

		public NewUserInfoBuilder withMail(String mail)
		{
			this.mail = mail;
			return this;
		}

		public NewUserInfoBuilder withPosition(String position)
		{
			this.position = position;
			return this;
		}

		public NewUserInfoBuilder withUserCode(String userCode)
		{
			this.userCode = userCode != null ? new Rut(userCode) : null;
			return this;
		}

		public NewUserInfoBuilder withEnterpriseCode(String enterpriseCode)
		{
			this.enterpriseCode = enterpriseCode != null ? new Rut(enterpriseCode) : null;
			return this;
		}

		public NewUserInfoBuilder withUserLastname(String userLastName)
		{
			this.userLastName = userLastName;
			return this;
		}

		public NewUserInfoBuilder withUsername(String userName)
		{
			this.userName = userName;
			return this;
		}

		public NewUserInfo build()
		{
			return new NewUserInfo(this);
		}
	}
}
