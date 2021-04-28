package bbr.b2b.portal.classes.wrappers.fep;

import bbr.b2b.fep.common.data.classes.ArticleTypeDataDTO;
import cl.bbr.core.classes.basics.SearchCriterion;

public class FEPProductFilterSectionInfo
{
	private String								sku						= null;
	private String								tradeMark				= null;
	private ArticleTypeDataDTO				selectedTemplate		= null;
	private SearchCriterion					status					= null;


	public FEPProductFilterSectionInfo(SearchCriterion	status, String sku, String tradeMark, ArticleTypeDataDTO selectedTemplate)
	{
		super();
		this.sku = sku;
		this.tradeMark = tradeMark;
		this.selectedTemplate = selectedTemplate;
		this.status = status;
	}

	public String getSku()
	{
		return sku;
	}


	public void setSku(String sku)
	{
		this.sku = sku;
	}


	public String getTradeMark()
	{
		return tradeMark;
	}


	public void setTradeMark(String tradeMark)
	{
		this.tradeMark = tradeMark;
	}


	public ArticleTypeDataDTO getTemplate()
	{
		return selectedTemplate;
	}


	public void setTemplate(ArticleTypeDataDTO selectedTemplate)
	{
		this.selectedTemplate = selectedTemplate;
	}
	
	public SearchCriterion getStatus()
	{
		return status;
	}

	public void setStatus(SearchCriterion status)
	{
		this.status = status;
	}
}
