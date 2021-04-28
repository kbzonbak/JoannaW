package bbr.b2b.portal.fep.commercial.managers.interfaces;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.fep.commercial.data.classes.ProductByCodeResultDTO;
import bbr.b2b.fep.commercial.data.classes.ProductReportInitParamDTO;
import bbr.b2b.fep.commercial.data.classes.ProductsRecordsResultDTO;
import bbr.b2b.portal.managers.interfaces.IGenericEJBInterface;

public interface IFEPProductReportManager extends IGenericEJBInterface {
	
	//ficha productos
	public ProductsRecordsResultDTO getProductsRecords(ProductReportInitParamDTO initParamW, Integer pageNumber , Integer rows, OrderCriteriaDTO[] order, boolean filter, boolean ispaginated, Long uskey);
	
	public ProductByCodeResultDTO getProductByCode (String productcode);
	
}

