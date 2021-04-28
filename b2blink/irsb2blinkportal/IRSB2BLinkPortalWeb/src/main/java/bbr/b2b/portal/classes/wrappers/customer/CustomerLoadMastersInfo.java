package bbr.b2b.portal.classes.wrappers.customer;

public class CustomerLoadMastersInfo
{
	private Long	id					= null;
	private String	caption				= null;
	private String	message				= null;
	private String	userName			= null;
	private String	pendingFilename		= null;
	private String	realFilename		= null;
	private String	loadDate			= null;
	private String	fileType			= null;
	private boolean	isButtonOrUpload	= true;
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getFileType()
	{
		return fileType;
	}

	public void setFileType(String fileType)
	{
		this.fileType = fileType;
	}

	public boolean isButtonOrUpload()
	{
		return isButtonOrUpload;
	}

	public void setButtonOrUpload(boolean isButtonOrUpload)
	{
		this.isButtonOrUpload = isButtonOrUpload;
	}

	public String getCaption()
	{
		return caption;
	}

	public void setCaption(String caption)
	{
		this.caption = caption;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPendingFilename()
	{
		return pendingFilename;
	}

	public void setPendingFilename(String pendingFilename)
	{
		this.pendingFilename = pendingFilename;
	}

	public String getRealFilename()
	{
		return realFilename;
	}

	public void setRealFilename(String realFilename)
	{
		this.realFilename = realFilename;
	}

	public String getLoadDate()
	{
		return loadDate;
	}

	public void setLoadDate(String loadDate)
	{
		this.loadDate = loadDate;
	}

}
