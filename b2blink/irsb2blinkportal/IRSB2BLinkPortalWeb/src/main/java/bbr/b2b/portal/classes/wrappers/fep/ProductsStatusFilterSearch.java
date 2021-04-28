package bbr.b2b.portal.classes.wrappers.fep;

import bbr.b2b.fep.cp.data.classes.CPItemsByStatusInitParamDTO;
import bbr.b2b.users.report.classes.CompanyDataDTO;

public class ProductsStatusFilterSearch
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ProductsStatusFilterSearch(CompanyDataDTO selectedCompany, CPItemsByStatusInitParamDTO initParam)
	{
		super();
		this.selectedCompany = selectedCompany;
		this.initParam = initParam;
	}


	public ProductsStatusFilterSearch()
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
	private CPItemsByStatusInitParamDTO	initParam				= null;
	
	public CompanyDataDTO getSelectedCompany()
	{
		return selectedCompany;
	}


	public void setSelectedCompany(CompanyDataDTO selectedCompany)
	{
		this.selectedCompany = selectedCompany;
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
