package bbr.b2b.portal.classes.basics;

public class LoginInfo 
{
	private String userName = "";
	private String password = "";
	private String newPassword = "";
	private String confirmNewPassword = "";
	
	public String getNewPassword() 
	{
		return newPassword;
	}

	public void setNewPassword(String newPassword) 
	{
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() 
	{
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) 
	{
		this.confirmNewPassword = confirmNewPassword;
	}

	public String getUserName() 
	{
		return userName;
	}

	public void setUserName(String userName) 
	{
		this.userName = userName;
	}

	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}

	public LoginInfo(String user, String password) 
	{
		this(user,password,"","");
	}

	public LoginInfo(String user, String password, String newPassword, String confirmNewPassword) 
	{
		this.userName 			= user;
		this.password 			= password;
		this.newPassword 		= newPassword;
		this.confirmNewPassword = confirmNewPassword;
	}

	

}
