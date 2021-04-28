package bbr.b2b.portal.classes.wrappers.customer;

import bbr.b2b.users.report.classes.CompanyDataDTO;

public class CustomerOrdersDetails
{

	private CompanyDataDTO			company		= null;
	private CustomerCriterionDTO	criterion	= null;

	public CompanyDataDTO getCompany()
	{
		return company;
	}

	public void setCompany(CompanyDataDTO company)
	{
		this.company = company;
	}

	public CustomerCriterionDTO getCriterion()
	{
		return criterion;
	}

	public void setCriterion(CustomerCriterionDTO criterion)
	{
		this.criterion = criterion;
	}

}
