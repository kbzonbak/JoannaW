package bbr.b2b.portal.classes.wrappers.fep;

import bbr.b2b.fep.common.data.classes.ArticleTypeResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemArrayResultDTO;

public class ProductCreationDetailsResult
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================
	public ProductCreationDetailsResult(ArticleTypeResultDTO articleTypeResult, CPItemArrayResultDTO cpItemArrayResult)
	{
		super();
		this.articleTypeResult = articleTypeResult;
		this.cpItemArrayResult = cpItemArrayResult;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private ArticleTypeResultDTO	articleTypeResult	= null;
	private CPItemArrayResultDTO	cpItemArrayResult	= null;

	public ArticleTypeResultDTO getArticleTypeResult()
	{
		return articleTypeResult;
	}

	public void setArticleTypeResult(ArticleTypeResultDTO articleTypeResult)
	{
		this.articleTypeResult = articleTypeResult;
	}

	public CPItemArrayResultDTO getCpItemArrayResult()
	{
		return cpItemArrayResult;
	}

	public void setcpItemArrayResult(CPItemArrayResultDTO cpItemArrayResult)
	{
		this.cpItemArrayResult = cpItemArrayResult;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

}
