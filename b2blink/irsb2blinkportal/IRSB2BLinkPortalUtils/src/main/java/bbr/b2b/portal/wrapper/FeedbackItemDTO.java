package bbr.b2b.portal.wrapper;

public class FeedbackItemDTO
{
	String	comment			= null;
	String	funcionality	= null;
	String	enterprise		= null;
	String	enterpriseCode	= null;

	public FeedbackItemDTO()
	{
	}

	public FeedbackItemDTO(String funcionality, String enterprise, String enterpriseCode)
	{
		this.funcionality = funcionality;
		this.enterprise = enterprise;
		this.enterpriseCode = enterpriseCode;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public String getFuncionality()
	{
		return funcionality;
	}

	public void setFuncionality(String funcionality)
	{
		this.funcionality = funcionality;
	}

	public String getEnterprise()
	{
		return enterprise;
	}

	public void setEnterprise(String enterprise)
	{
		this.enterprise = enterprise;
	}

	public String getEnterpriseCode()
	{
		return enterpriseCode;
	}

	public void setEnterpriseCode(String enterpriseCode)
	{
		this.enterpriseCode = enterpriseCode;
	}

}
