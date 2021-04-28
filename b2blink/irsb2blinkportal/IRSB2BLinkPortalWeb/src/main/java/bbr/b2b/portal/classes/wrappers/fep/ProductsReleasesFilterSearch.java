package bbr.b2b.portal.classes.wrappers.fep;

import bbr.b2b.fep.common.data.classes.UserTypeDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsByStatusInitParamDTO;
import bbr.b2b.users.report.classes.CompanyDataDTO;

public class ProductsReleasesFilterSearch
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ProductsReleasesFilterSearch(CompanyDataDTO selectedCompany, UserTypeDTO selectedRole, CPItemsByStatusInitParamDTO initParam)
	{
		super();
		this.selectedCompany = selectedCompany;
		this.selectedRole	= selectedRole;
		this.initParam = initParam;
	}


	public ProductsReleasesFilterSearch()
	{
		super();
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private CompanyDataDTO					selectedCompany		= null;
	private UserTypeDTO						selectedRole			= null;
	private CPItemsByStatusInitParamDTO	initParam				= null;
	
	public CompanyDataDTO getSelectedCompany()
	{
		return selectedCompany;
	}


	public void setSelectedCompany(CompanyDataDTO selectedCompany)
	{
		this.selectedCompany = selectedCompany;
	}


	public UserTypeDTO getSelectedRole()
	{
		return selectedRole;
	}


	public void setSelectedRole(UserTypeDTO selectedRole)
	{
		this.selectedRole = selectedRole;
	}


	public CPItemsByStatusInitParamDTO getInitParam()
	{
		return initParam;
	}


	public void setInitParam(CPItemsByStatusInitParamDTO initParam)
	{
		this.initParam = initParam;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

}
