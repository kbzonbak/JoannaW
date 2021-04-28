package bbr.b2b.portal.classes.wrappers.fep;

import bbr.b2b.fep.common.data.classes.ArticleTypeDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsByFilterInitParamDTO;
import bbr.b2b.users.report.classes.CompanyDataDTO;

public class ProductsManagementFilterSearch
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ProductsManagementFilterSearch(CompanyDataDTO selectedCompany, ArticleTypeDTO selectedTemplate, CPItemsByFilterInitParamDTO initParam)
	{
		super();
		this.selectedCompany = selectedCompany;
		this.selectedTemplate = selectedTemplate;
		this.initParam = initParam;
	}


	public ProductsManagementFilterSearch()
	{
		super();
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private CompanyDataDTO					selectedCompany	= null;
	private ArticleTypeDTO					selectedTemplate	= null;
	private CPItemsByFilterInitParamDTO	initParam			= null;


	public CompanyDataDTO getSelectedCompany()
	{
		return selectedCompany;
	}


	public void setSelectedCompany(CompanyDataDTO selectedCompany)
	{
		this.selectedCompany = selectedCompany;
	}


	public ArticleTypeDTO getSelectedTemplate()
	{
		return selectedTemplate;
	}


	public void setSelectedTemplate(ArticleTypeDTO selectedTemplate)
	{
		this.selectedTemplate = selectedTemplate;
	}


	public CPItemsByFilterInitParamDTO getInitParam()
	{
		return initParam;
	}


	public void setInitParam(CPItemsByFilterInitParamDTO initParam)
	{
		this.initParam = initParam;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

}
