package bbr.b2b.portal.fep.commercial.managers.interfaces;

import bbr.b2b.fep.commercial.data.classes.CategoryArrayResultDTO;
import bbr.b2b.fep.commercial.data.classes.GetMarksByProviderAndCategoryInitParamDTO;
import bbr.b2b.fep.commercial.data.classes.GetProductByPropertyArrayResultDTO;
import bbr.b2b.fep.commercial.data.classes.GetProductLabelByProviderAndCategoryDataInitParamDTO;
import bbr.b2b.fep.commercial.data.classes.LabelValueArrayResultDTO;
import bbr.b2b.fep.commercial.data.classes.MarkArrayResultDTO;
import bbr.b2b.portal.managers.interfaces.IGenericEJBInterface;

public interface IFEPCommercialReportManager extends IGenericEJBInterface {
	
	
	
	//navegacion por categorias de productos
	public CategoryArrayResultDTO getCategoryProductDataByLevelAndProvider(Boolean catRetailer, String pvcode, Integer level);
	public CategoryArrayResultDTO getCategoryProductDataByLevelAndProvider(Boolean catRetailer, String pvcode, Integer level, Long catkey);
	
	
	//buscador de productos
	public GetProductByPropertyArrayResultDTO getProductsByProperty(String searchValue, Boolean catRetailer, String pvcode, Integer criterion);
	
	//marcas del proveedor
	public MarkArrayResultDTO getMarksByProviderAndCategory (GetMarksByProviderAndCategoryInitParamDTO initparamDTO);
	
	public LabelValueArrayResultDTO getProductLabelByProviderAndCategoryData(GetProductLabelByProviderAndCategoryDataInitParamDTO initParamW);
	

}