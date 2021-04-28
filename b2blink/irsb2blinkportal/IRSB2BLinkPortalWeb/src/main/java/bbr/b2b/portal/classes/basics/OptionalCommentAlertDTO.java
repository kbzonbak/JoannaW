package bbr.b2b.portal.classes.basics;

public class OptionalCommentAlertDTO
{
	public static final String	BTN_ACCEPT	= "btnAccept";
	public static final String	BTN_CANCEL	= "btnCancel";
	private String				comment		= null;
	private Long				id			= null;

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

}
