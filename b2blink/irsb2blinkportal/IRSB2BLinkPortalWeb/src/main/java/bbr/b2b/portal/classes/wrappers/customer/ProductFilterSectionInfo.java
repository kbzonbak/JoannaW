package bbr.b2b.portal.classes.wrappers.customer;

import bbr.b2b.fep.commercial.data.classes.MarkDTO;

public class ProductFilterSectionInfo
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
//	private CategoryItem	productCategory			= null;
	private MarkDTO			tradeMark				= null;
	private Boolean			isOnlyActiveProducts	= null;
	private Boolean			isRetailerCategory		= null;

//	public CategoryItem getProductCategory()
//	{
//		return productCategory;
//	}
//
//	public void setProductCategory(CategoryItem productCategory)
//	{
//		this.productCategory = productCategory;
//	}

	public MarkDTO getTradeMark()
	{
		return tradeMark;
	}

	public void setTradeMark(MarkDTO tradeMark)
	{
		this.tradeMark = tradeMark;
	}

	public Boolean isOnlyActiveProducts()
	{
		return this.isOnlyActiveProducts;
	}

	public void setIsOnlyActiveProducts(Boolean isOnlyActiveProducts)
	{
		this.isOnlyActiveProducts = isOnlyActiveProducts;
	}

	public Boolean isRetailerCategory()
	{
		return this.isRetailerCategory;
	}

	public void setIsRetailerCategory(Boolean isRetailerCategory)
	{
		this.isRetailerCategory = isRetailerCategory;
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public ProductFilterSectionInfo( MarkDTO tradeMark, Boolean isOnlyActiveProducts, Boolean isRetailerCategory)
	{
		super();
//		this.productCategory = productCategory;
		this.tradeMark = tradeMark;
		this.isOnlyActiveProducts = isOnlyActiveProducts;
		this.isRetailerCategory = isRetailerCategory;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================
}
